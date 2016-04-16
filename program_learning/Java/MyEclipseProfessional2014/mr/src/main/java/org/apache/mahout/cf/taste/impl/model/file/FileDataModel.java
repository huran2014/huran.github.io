/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.cf.taste.impl.model.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.io.Closeables;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.AbstractDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.common.iterator.FileLineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A {@link DataModel} backed by a delimited file. This class expects a file where each line
 * contains a user ID, followed by item ID, followed by optional preference value, followed by
 * optional timestamp. Commas or tabs delimit fields:
 * </p>
 *
 * <p>{@code userID,itemID[,preference[,timestamp]]}</p>
 *
 * <p>
 * Preference value is optional to accommodate applications that have no notion of a
 * preference value (that is, the user simply expresses a
 * preference for an item, but no degree of preference).
 * </p>
 *
 * <p>
 * The preference value is assumed to be parseable as a {@code double}. The user IDs and item IDs are
 * read parsed as {@code long}s. The timestamp, if present, is assumed to be parseable as a
 * {@code long}, though this can be overridden via {@link #readTimestampFromString(String)}.
 * The preference value may be empty, to indicate "no preference value", but cannot be empty. That is,
 * this is legal:
 * </p>
 *
 * <p>{@code 123,456,,129050099059}</p>
 *
 * <p>But this isn't:</p>
 *
 * <p>{@code 123,456,129050099059}</p>
 *
 * <p>
 * It is also acceptable for the lines to contain additional fields. Fields beyond the third will be ignored.
 * An empty line, or one that begins with '#' will be ignored as a comment.
 * </p>
 *
 * <p>
 * This class will reload data from the data file when {@link #refresh(Collection)} is called, unless the file
 * has been reloaded very recently already.
 * </p>
 *
 * <p>
 * This class will also look for update "delta" files in the same directory, with file names that start the
 * same way (up to the first period). These files have the same format, and provide updated data that
 * supersedes what is in the main data file. This is a mechanism that allows an application to push updates to
 * {@link FileDataModel} without re-copying the entire data file.
 * </p>
 *
 * <p>
 * One small format difference exists. Update files must also be able to express deletes.
 * This is done by ending with a blank preference value, as in "123,456,".
 * </p>
 *
 * <p>
 * Note that it's all-or-nothing -- all of the items in the file must express no preference, or the all must.
 * These cannot be mixed. Put another way there will always be the same number of delimiters on every line of
 * the file!
 * </p>
 *
 * <p>
 * This class is not intended for use with very large amounts of data (over, say, tens of millions of rows).
 * For that, a JDBC-backed {@link DataModel} and a database are more appropriate.
 * </p>
 *
 * <p>
 * It is possible and likely useful to subclass this class and customize its behavior to accommodate
 * application-specific needs and input formats. See {@link #processLine(String, FastByIDMap, FastByIDMap, boolean)} and
 * {@link #processLineWithoutID(String, FastByIDMap, FastByIDMap)}
 */
public class FileDataModel extends AbstractDataModel {

  private static final Logger log = LoggerFactory.getLogger(FileDataModel.class);

  public static final long DEFAULT_MIN_RELOAD_INTERVAL_MS = 60 * 1000L; // 1 minute?
  private static final char COMMENT_CHAR = '#';
  private static final char[] DELIMIETERS = {',', '\t'};

  private final File dataFile;
  private long lastModified;
  private long lastUpdateFileModified;
  private final Splitter delimiterPattern;
  private final boolean hasPrefValues;
  private DataModel delegate;
  private final ReentrantLock reloadLock;
  private final boolean transpose;
  private final long minReloadIntervalMS;

  /**
   * @param dataFile
   *          file containing preferences data. If file is compressed (and name ends in .gz or .zip
   *          accordingly) it will be decompressed as it is read)
   * @throws FileNotFoundException
   *           if dataFile does not exist
   * @throws IOException
   *           if file can't be read
   *           最初始的构造函数，通过这个引入文档内的数据
   *           在传入前，会首先经过new File("info.csv")进行实例化File对象
   *           File对象对文件路径进行了审查，并存储了路径信息和
   */
  public FileDataModel(File dataFile) throws IOException {
    this(dataFile, false, DEFAULT_MIN_RELOAD_INTERVAL_MS);
  }
  
  /**
   * @param delimiterRegex If your data file don't use '\t' or ',' as delimiter, you can specify 
   * a custom regex pattern.
   */
  public FileDataModel(File dataFile, String delimiterRegex) throws IOException {
    this(dataFile, false, DEFAULT_MIN_RELOAD_INTERVAL_MS, delimiterRegex);
  }
  
  /**
   * @param transpose
   *          transposes user IDs and item IDs -- convenient for 'flipping' the data model this way
   *          用来判断是否转置用户和物品信息
   * @param minReloadIntervalMS
   *  
   *  the minimum interval in milliseconds after which a full reload of the original datafile is done
   *  when refresh() is called
   * @see #FileDataModel(File)
   */
  public FileDataModel(File dataFile, boolean transpose, long minReloadIntervalMS) throws IOException {
    this(dataFile, transpose, minReloadIntervalMS, null);
  }
  
  /**
   * @param delimiterRegex If your data file don't use '\t' or ',' as delimiters, you can specify 
   * user own using regex pattern.
   * @throws IOException
   * 从FileDataModel()进入时，首先通过File(String)将路径转换为File对象，再经过上一个FileDataModel(File)的return转换两次到该函数
   * 其中transpose=false; minReloadIntervalMS=60*1000L; delimiterRegex=null;
   */
  public FileDataModel(File dataFile, boolean transpose, long minReloadIntervalMS, String delimiterRegex)
    throws IOException {
	//只是检测文件属性是否正确，有没有正确建立绝对路径文件对象，并没有检测文档是否存在
    this.dataFile = Preconditions.checkNotNull(dataFile.getAbsoluteFile());
    //这句话才是真正检测文档是否存在，检测路径是否是文件夹的
    if (!dataFile.exists() || dataFile.isDirectory()) {
      throw new FileNotFoundException(dataFile.toString());
    }
    //检测的其实是dataFile的内容长度，即检测dataFile是否为空
    Preconditions.checkArgument(dataFile.length() > 0L, "dataFile is empty");
    //the minimum interval in milliseconds after which a full reload of the original datafile is done when refresh() is called
    //文件的更新时间间隔
    Preconditions.checkArgument(minReloadIntervalMS >= 0L, "minReloadIntervalMs must be non-negative");

    log.info("Creating FileDataModel for file {}", dataFile);

    //Returns the time that the file denoted by this abstract pathname was last modified.
    //即返回这个文档上次修改时间
    this.lastModified = dataFile.lastModified();
    //获取到相似文档最近更新的时间。
    this.lastUpdateFileModified = readLastUpdateFileModified();

    //一个专门遍历文档内容的迭代器
    FileLineIterator iterator = new FileLineIterator(dataFile, false);
    //读取第一行数据。peek方法默认返回第一个对应的对象
    String firstLine = iterator.peek();
    //反复循环，直到获得第一行有数据的内容
    while (firstLine.isEmpty() || firstLine.charAt(0) == COMMENT_CHAR) {
      iterator.next();
      firstLine = iterator.peek();
    }
    Closeables.close(iterator, true);

    char delimiter;
    //查看是否有设置分隔符，如果没有，就自动获取分隔符
    if (delimiterRegex == null) {
    	//获取分隔符
      delimiter = determineDelimiter(firstLine);
      //生成分隔符对象
      delimiterPattern = Splitter.on(delimiter);
    } else {
      delimiter = '\0';
      delimiterPattern = Splitter.onPattern(delimiterRegex);
      if (!delimiterPattern.split(firstLine).iterator().hasNext()) {
        throw new IllegalArgumentException("Did not find a delimiter(pattern) in first line");
      }
    }
    List<String> firstLineSplit = new ArrayList<>();
    //将第一行数据进行切分，并保存到ArrayList数组
    for (String token : delimiterPattern.split(firstLine)) {
      firstLineSplit.add(token);
    }
    // If preference value exists and isn't empty then the file is specifying pref values
    // 第一行数据满足数据需求，即：有3个数据；数据内容不为空
    hasPrefValues = firstLineSplit.size() >= 3 && !firstLineSplit.get(2).isEmpty();

    this.reloadLock = new ReentrantLock();
    //false
    this.transpose = transpose;
    //文件的最小更新时间间隔
    this.minReloadIntervalMS = minReloadIntervalMS;

    reload();
  }

  public File getDataFile() {
    return dataFile;
  }

  protected void reload() {
    if (reloadLock.tryLock()) {
      try {
        delegate = buildModel();
      } catch (IOException ioe) {
        log.warn("Exception while reloading", ioe);
      } finally {
        reloadLock.unlock();
      }
    }
  }

  protected DataModel buildModel() throws IOException {

	//获取文件最近的更新日期
    long newLastModified = dataFile.lastModified();
    //获取相关文件的最新更新时间
    long newLastUpdateFileModified = readLastUpdateFileModified();

    //查看最新更新文件更新时间是否符合更新间隔并且模型为空
    boolean loadFreshData = delegate == null || newLastModified > lastModified + minReloadIntervalMS;

    //将之前获得的最新更新时间保存下来
    long oldLastUpdateFileModifieid = lastUpdateFileModified;
    //更新该文档的最近更新时间
    lastModified = newLastModified;
    //更新相关文档的最近更新时间
    lastUpdateFileModified = newLastUpdateFileModified;

    FastByIDMap<FastByIDMap<Long>> timestamps = new FastByIDMap<>();

    //文件内容是否准确，存在用户偏好
    if (hasPrefValues) {
    	//DataModel为空，并且该文件的最近更新时间和其相关文档的更新时间差小于最小更新时间。
      if (loadFreshData) {
    	  //Collection是list和set的总结口
        FastByIDMap<Collection<Preference>> data = new FastByIDMap<>();
        //创建文本的迭代器
        FileLineIterator iterator = new FileLineIterator(dataFile, false);
        //生成datamodel模型
        processFile(iterator, data, timestamps, false);

        //遍历最新的相似文件，从最旧的开始读取，更新pref数据
        for (File updateFile : findUpdateFilesAfter(newLastModified)) {
        	//查看是否最新数据，更新数据
        	//FileLineIterator生成迭代器，不取消读取第一行
          processFile(new FileLineIterator(updateFile, false), data, timestamps, false);
        }
        
        /**
         * 返回GenericDataModel模型
         * 包含  和时间戳（null）
         * toDataMap的方法，将datamodel矩阵转换成GenericUserPreferenceArray矩阵的形式
         * 即：每一个userID，对应一个itemIDs数组和prefValues数组
         */
        return new GenericDataModel(GenericDataModel.toDataMap(data, true), timestamps);

      } else {

        FastByIDMap<PreferenceArray> rawData = ((GenericDataModel) delegate).getRawUserData();

        for (File updateFile : findUpdateFilesAfter(Math.max(oldLastUpdateFileModifieid, newLastModified))) {
          processFile(new FileLineIterator(updateFile, false), rawData, timestamps, true);
        }

        return new GenericDataModel(rawData, timestamps);

      }

    } else {

      if (loadFreshData) {

        FastByIDMap<FastIDSet> data = new FastByIDMap<>();
        FileLineIterator iterator = new FileLineIterator(dataFile, false);
        processFileWithoutID(iterator, data, timestamps);

        for (File updateFile : findUpdateFilesAfter(newLastModified)) {
          processFileWithoutID(new FileLineIterator(updateFile, false), data, timestamps);
        }

        return new GenericBooleanPrefDataModel(data, timestamps);

      } else {

        FastByIDMap<FastIDSet> rawData = ((GenericBooleanPrefDataModel) delegate).getRawUserData();

        for (File updateFile : findUpdateFilesAfter(Math.max(oldLastUpdateFileModifieid, newLastModified))) {
          processFileWithoutID(new FileLineIterator(updateFile, false), rawData, timestamps);
        }

        return new GenericBooleanPrefDataModel(rawData, timestamps);

      }

    }
  }

  /**
   * Finds update delta files in the same directory as the data file. This finds any file whose name starts
   * the same way as the data file (up to first period) but isn't the data file itself. For example, if the
   * data file is /foo/data.txt.gz, you might place update files at /foo/data.1.txt.gz, /foo/data.2.txt.gz,
   * etc.
   * 意即获取相同路径下的更新文件，增量获取
   */
  private Iterable<File> findUpdateFilesAfter(long minimumLastModified) {
    String dataFileName = dataFile.getName();
    //获取文件名中.出现的位置
    int period = dataFileName.indexOf('.');
    //若匹配到了‘.’，则仅去前面的文件名作为匹配字符串，若未匹配到‘.’，则该文件名即为匹配字符串
    String startName = period < 0 ? dataFileName : dataFileName.substring(0, period);
    //其实就是根据路径来找到父路径。
    File parentDir = dataFile.getParentFile();
    //定义一个新的treemap，用来存储文本信息
    Map<Long, File> modTimeToUpdateFile = new TreeMap<>();
    //在进行接口实例化的时候实例化方法。
    FileFilter onlyFiles = new FileFilter() {
      @Override
      public boolean accept(File file) {
        return !file.isDirectory();
      }
    };
    //parentDir.listFiles(onlyFiles)可以列举出所有的非文件夹文件
    for (File updateFile : parentDir.listFiles(onlyFiles)) {
      String updateFileName = updateFile.getName();
      //比较两个字符串是否相同或包含，若无第二个参数，则从第0个开始比对。若不同则返回false
      if (updateFileName.startsWith(startName)
    		  //equals类似于==，但是equals比较的是字符串是否相同，而==比较的是引用是否相同
          && !updateFileName.equals(dataFileName)
          //是否达到文档的更新时间。
          && updateFile.lastModified() >= minimumLastModified) {
    	  //在treemap中存放最后的更新时间和更新文档。treemap能够自动根据更新时间进行排序
        modTimeToUpdateFile.put(updateFile.lastModified(), updateFile);
      }
    }
    //返回文件列表，已经经过了排列
    return modTimeToUpdateFile.values();
  }

  private long readLastUpdateFileModified() {
	  //初始化mostRecentModification的值
    long mostRecentModification = Long.MIN_VALUE;
    //对获取到的每个更新文件进行遍历，获取到最大的更新时间（也就是最近的时间）
    for (File updateFile : findUpdateFilesAfter(0L)) {
      mostRecentModification = Math.max(mostRecentModification, updateFile.lastModified());
    }
    //返回最近的文件更新时间
    return mostRecentModification;
  }

  //获取分隔符代码，能够查看是否有分隔符并返回分隔符
  public static char determineDelimiter(String line) {
    for (char possibleDelimieter : DELIMIETERS) {
      if (line.indexOf(possibleDelimieter) >= 0) {
        return possibleDelimieter;
      }
    }
    throw new IllegalArgumentException("Did not find a delimiter in first line");
  }

  //
  protected void processFile(FileLineIterator dataOrUpdateFileIterator,
                             FastByIDMap<?> data,
                             FastByIDMap<FastByIDMap<Long>> timestamps,
                             boolean fromPriorData) {
    log.info("Reading file info...");
    int count = 0;
    //迭代文件中每一行的数据
    while (dataOrUpdateFileIterator.hasNext()) {
    	//将每一行的数据转换为String对象
      String line = dataOrUpdateFileIterator.next();
      if (!line.isEmpty()) {
    	  //在这里检测了一下文本内容是否为空，在函数里面还会检测一次。
        processLine(line, data, timestamps, fromPriorData);
        //每100万行打印一下log
        if (++count % 1000000 == 0) {
          log.info("Processed {} lines", count);
        }
      }
    }
    log.info("Read lines: {}", count);
  }

  /**
   * <p>
   * Reads one line from the input file and adds the data to a {@link FastByIDMap} data structure which maps user IDs
   * to preferences. This assumes that each line of the input file corresponds to one preference. After
   * reading a line and determining which user and item the preference pertains to, the method should look to
   * see if the data contains a mapping for the user ID already, and if not, add an empty data structure of preferences
   * as appropriate to the data.
   * </p>
   *
   * <p>
   * Note that if the line is empty or begins with '#' it will be ignored as a comment.
   * </p>
   *
   * @param line
   *          line from input data file
   * @param data
   *          all data read so far, as a mapping from user IDs to preferences
   * @param fromPriorData an implementation detail -- if true, data will map IDs to
   *  {@link PreferenceArray} since the framework is attempting to read and update raw
   *  data that is already in memory. Otherwise it maps to {@link Collection}s of
   *  {@link Preference}s, since it's reading fresh data. Subclasses must be prepared
   *  to handle this wrinkle.
   *  
   */
  protected void processLine(String line,
                             FastByIDMap<?> data, 
                             FastByIDMap<FastByIDMap<Long>> timestamps,
                             boolean fromPriorData) {

    // Ignore empty lines and comments
	  //如果该行内容为空或者为注释行(采用#注释)
    if (line.isEmpty() || line.charAt(0) == COMMENT_CHAR) {
      return;
    }
    
    //将文本内容进行切分，并分别存储为userID,itemID,preferenceValue，并检测是否还有时间戳，如果有则存储
    Iterator<String> tokens = delimiterPattern.split(line).iterator();
    String userIDString = tokens.next();
    String itemIDString = tokens.next();
    String preferenceValueString = tokens.next();
    //查看是否有时间戳，如果有，则返回时间戳，如果没有，则返回null
    boolean hasTimestamp = tokens.hasNext();
    String timestampString = hasTimestamp ? tokens.next() : null;

    //将userID,itemID转换成long型常数。
    long userID = readUserIDFromString(userIDString);
    long itemID = readItemIDFromString(itemIDString);

    //判断userID和itemID是否进行转置，如果是，则进行转置
    if (transpose) {
      long tmp = userID;
      userID = itemID;
      itemID = tmp;
    }

    // This is kind of gross but need to handle two types of storage
    // 采用Object进行声明是因为无法知道perference的真是存储形式
    // 获取到userID下的用户偏好值
    Object maybePrefs = data.get(userID);
    //判断如何进行存储数据，如果为真，则说明数据已经旧了，需要更新偏好数据。因此将userID和PrefernceArray绑定起来
    //如果为false，则说明目前读取的数据是最新数据，直接将userID和Collection绑定起来
    if (fromPriorData) {
      // Data are PreferenceArray

      PreferenceArray prefs = (PreferenceArray) maybePrefs;
      if (!hasTimestamp && preferenceValueString.isEmpty()) {
        // Then line is of form "userID,itemID,", meaning remove
        if (prefs != null) {
          boolean exists = false;
          int length = prefs.length();
          for (int i = 0; i < length; i++) {
            if (prefs.getItemID(i) == itemID) {
              exists = true;
              break;
            }
          }
          if (exists) {
            if (length == 1) {
              data.remove(userID);
            } else {
              PreferenceArray newPrefs = new GenericUserPreferenceArray(length - 1);
              for (int i = 0, j = 0; i < length; i++, j++) {
                if (prefs.getItemID(i) == itemID) {
                  j--;
                } else {
                  newPrefs.set(j, prefs.get(i));
                }
              }
              ((FastByIDMap<PreferenceArray>) data).put(userID, newPrefs);
            }
          }
        }

        removeTimestamp(userID, itemID, timestamps);

      } else {

        float preferenceValue = Float.parseFloat(preferenceValueString);

        boolean exists = false;
        if (prefs != null) {
          for (int i = 0; i < prefs.length(); i++) {
            if (prefs.getItemID(i) == itemID) {
              exists = true;
              prefs.setValue(i, preferenceValue);
              break;
            }
          }
        }

        if (!exists) {
          if (prefs == null) {
            prefs = new GenericUserPreferenceArray(1);
          } else {
            PreferenceArray newPrefs = new GenericUserPreferenceArray(prefs.length() + 1);
            for (int i = 0, j = 1; i < prefs.length(); i++, j++) {
              newPrefs.set(j, prefs.get(i));
            }
            prefs = newPrefs;
          }
          prefs.setUserID(0, userID);
          prefs.setItemID(0, itemID);
          prefs.setValue(0, preferenceValue);
          ((FastByIDMap<PreferenceArray>) data).put(userID, prefs);          
        }
      }

      addTimestamp(userID, itemID, timestampString, timestamps);

    } else {
      // Data are Collection<Preference>
      // 数据是最新数据，所以数据存储格式是collection格式
      Collection<Preference> prefs = (Collection<Preference>) maybePrefs;

      //如果没有时间戳并且不存在用户偏好
      if (!hasTimestamp && preferenceValueString.isEmpty()) {
        // Then line is of form "userID,itemID,", meaning remove
        if (prefs != null) {
          // remove pref
        	//生成prefs的迭代器
          Iterator<Preference> prefsIterator = prefs.iterator();
          //便利每个该用户下的偏好值，查看是否已经有该物品的偏好值，有的话就删除
          //针对无偏好的模型
          while (prefsIterator.hasNext()) {
            Preference pref = prefsIterator.next();
            if (pref.getItemID() == itemID) {
              prefsIterator.remove();
              break;
            }
          }
        }

        removeTimestamp(userID, itemID, timestamps);
        
      } else {
    	  //如果偏好属性是存在的
        float preferenceValue = Float.parseFloat(preferenceValueString);

        boolean exists = false;
        if (prefs != null) {
        	//遍历查看是否已经有该物品的偏好值，有的话就进行更新
        	//这里的pref是GenericPreference
          for (Preference pref : prefs) {
            if (pref.getItemID() == itemID) {
              exists = true;
              pref.setValue(preferenceValue);
              break;
            }
          }
        }

        if (!exists) {
          if (prefs == null) {
            prefs = new ArrayList<>(2);
            //将用户和prefs(Collection<Prefrence>)对应起来。
            ((FastByIDMap<Collection<Preference>>) data).put(userID, prefs);
          }
          //利用GenericPreference保存用户的偏好属性
          //并在prefs中导入prefs的列表中
          prefs.add(new GenericPreference(userID, itemID, preferenceValue));
        }
        //对数据添加时间戳
        //若数据原先有时间戳，则会更新时间戳，如果原先就没有，则不进行更改，时间戳置为空
        addTimestamp(userID, itemID, timestampString, timestamps);

      }

    }
  }

  protected void processFileWithoutID(FileLineIterator dataOrUpdateFileIterator,
                                      FastByIDMap<FastIDSet> data,
                                      FastByIDMap<FastByIDMap<Long>> timestamps) {
    log.info("Reading file info...");
    int count = 0;
    while (dataOrUpdateFileIterator.hasNext()) {
      String line = dataOrUpdateFileIterator.next();
      if (!line.isEmpty()) {
        processLineWithoutID(line, data, timestamps);
        if (++count % 100000 == 0) {
          log.info("Processed {} lines", count);
        }
      }
    }
    log.info("Read lines: {}", count);
  }

  protected void processLineWithoutID(String line,
                                      FastByIDMap<FastIDSet> data,
                                      FastByIDMap<FastByIDMap<Long>> timestamps) {

    if (line.isEmpty() || line.charAt(0) == COMMENT_CHAR) {
      return;
    }

    Iterator<String> tokens = delimiterPattern.split(line).iterator();
    String userIDString = tokens.next();
    String itemIDString = tokens.next();
    boolean hasPreference = tokens.hasNext();
    String preferenceValueString = hasPreference ? tokens.next() : "";
    boolean hasTimestamp = tokens.hasNext();
    String timestampString = hasTimestamp ? tokens.next() : null;

    long userID = readUserIDFromString(userIDString);
    long itemID = readItemIDFromString(itemIDString);

    if (transpose) {
      long tmp = userID;
      userID = itemID;
      itemID = tmp;
    }

    if (hasPreference && !hasTimestamp && preferenceValueString.isEmpty()) {
      // Then line is of form "userID,itemID,", meaning remove

      FastIDSet itemIDs = data.get(userID);
      if (itemIDs != null) {
        itemIDs.remove(itemID);
      }

      removeTimestamp(userID, itemID, timestamps);

    } else {

      FastIDSet itemIDs = data.get(userID);
      if (itemIDs == null) {
        itemIDs = new FastIDSet(2);
        data.put(userID, itemIDs);
      }
      itemIDs.add(itemID);

      addTimestamp(userID, itemID, timestampString, timestamps);

    }
  }

  private void addTimestamp(long userID,
                            long itemID,
                            String timestampString,
                            FastByIDMap<FastByIDMap<Long>> timestamps) {
    if (timestampString != null) {
      FastByIDMap<Long> itemTimestamps = timestamps.get(userID);
      if (itemTimestamps == null) {
        itemTimestamps = new FastByIDMap<>();
        timestamps.put(userID, itemTimestamps);
      }
      //返回整型数据
      long timestamp = readTimestampFromString(timestampString);
      itemTimestamps.put(itemID, timestamp);
    }
  }

  private static void removeTimestamp(long userID,
                                      long itemID,
                                      FastByIDMap<FastByIDMap<Long>> timestamps) {
    FastByIDMap<Long> itemTimestamps = timestamps.get(userID);
    if (itemTimestamps != null) {
      itemTimestamps.remove(itemID);
    }
  }

  /**
   * Subclasses may wish to override this if ID values in the file are not numeric. This provides a hook by
   * which subclasses can inject an {@link org.apache.mahout.cf.taste.model.IDMigrator} to perform
   * translation.
   */
  protected long readUserIDFromString(String value) {
    return Long.parseLong(value);
  }

  /**
   * Subclasses may wish to override this if ID values in the file are not numeric. This provides a hook by
   * which subclasses can inject an {@link org.apache.mahout.cf.taste.model.IDMigrator} to perform
   * translation.
   */
  protected long readItemIDFromString(String value) {
    return Long.parseLong(value);
  }

  /**
   * Subclasses may wish to override this to change how time values in the input file are parsed.
   * By default they are expected to be numeric, expressing a time as milliseconds since the epoch.
   */
  protected long readTimestampFromString(String value) {
    return Long.parseLong(value);
  }

  @Override
  public LongPrimitiveIterator getUserIDs() throws TasteException {
    return delegate.getUserIDs();
  }

  @Override
  public PreferenceArray getPreferencesFromUser(long userID) throws TasteException {
    return delegate.getPreferencesFromUser(userID);
  }

  @Override
  public FastIDSet getItemIDsFromUser(long userID) throws TasteException {
    return delegate.getItemIDsFromUser(userID);
  }

  @Override
  public LongPrimitiveIterator getItemIDs() throws TasteException {
    return delegate.getItemIDs();
  }

  @Override
  public PreferenceArray getPreferencesForItem(long itemID) throws TasteException {
    return delegate.getPreferencesForItem(itemID);
  }

  @Override
  public Float getPreferenceValue(long userID, long itemID) throws TasteException {
    return delegate.getPreferenceValue(userID, itemID);
  }

  @Override
  public Long getPreferenceTime(long userID, long itemID) throws TasteException {
    return delegate.getPreferenceTime(userID, itemID);
  }

  @Override
  public int getNumItems() throws TasteException {
    return delegate.getNumItems();
  }

  @Override
  public int getNumUsers() throws TasteException {
    return delegate.getNumUsers();
  }

  @Override
  public int getNumUsersWithPreferenceFor(long itemID) throws TasteException {
    return delegate.getNumUsersWithPreferenceFor(itemID);
  }

  @Override
  public int getNumUsersWithPreferenceFor(long itemID1, long itemID2) throws TasteException {
    return delegate.getNumUsersWithPreferenceFor(itemID1, itemID2);
  }

  /**
   * Note that this method only updates the in-memory preference data that this {@link FileDataModel}
   * maintains; it does not modify any data on disk. Therefore any updates from this method are only
   * temporary, and lost when data is reloaded from a file. This method should also be considered relatively
   * slow.
   */
  @Override
  public void setPreference(long userID, long itemID, float value) throws TasteException {
    delegate.setPreference(userID, itemID, value);
  }

  /** See the warning at {@link #setPreference(long, long, float)}. */
  @Override
  public void removePreference(long userID, long itemID) throws TasteException {
    delegate.removePreference(userID, itemID);
  }

  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    if (dataFile.lastModified() > lastModified + minReloadIntervalMS
        || readLastUpdateFileModified() > lastUpdateFileModified + minReloadIntervalMS) {
      log.debug("File has changed; reloading...");
      reload();
    }
  }

  @Override
  public boolean hasPreferenceValues() {
    return delegate.hasPreferenceValues();
  }

  @Override
  public float getMaxPreference() {
    return delegate.getMaxPreference();
  }

  @Override
  //获取最小的偏好值
  public float getMinPreference() {
	  //由于delegate是GenericDataModel类型数据，所以getMinPreference调用了GenericDataModel方法
    return delegate.getMinPreference();
  }

  @Override
  public String toString() {
    return "FileDataModel[dataFile:" + dataFile + ']';
  }

}
