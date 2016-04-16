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

package org.apache.mahout.common.iterator;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import com.google.common.base.Charsets;
import com.google.common.collect.AbstractIterator;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import org.apache.mahout.cf.taste.impl.common.SkippingIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Iterates over the lines of a text file. This assumes the text file's lines are delimited in a manner
 * consistent with how {@link BufferedReader} defines lines.
 * <p/>
 * This class will uncompress files that end in .zip or .gz accordingly, too.
 */
public final class FileLineIterator extends AbstractIterator<String> implements SkippingIterator<String>, Closeable {

  private final BufferedReader reader;

  private static final Logger log = LoggerFactory.getLogger(FileLineIterator.class);

  /**
   * Creates a {@link FileLineIterator} over a given file, assuming a UTF-8 encoding.
   *
   * @throws java.io.FileNotFoundException if the file does not exist
   * @throws IOException
   *           if the file cannot be read
   */
  public FileLineIterator(File file) throws IOException {
    this(file, Charsets.UTF_8, false);
  }

  /**
   * Creates a {@link FileLineIterator} over a given file, assuming a UTF-8 encoding.
   *
   * @throws java.io.FileNotFoundException if the file does not exist
   * @throws IOException                   if the file cannot be read
   */
  public FileLineIterator(File file, boolean skipFirstLine) throws IOException {
    this(file, Charsets.UTF_8, skipFirstLine);
  }

  /**
   * Creates a {@link FileLineIterator} over a given file, using the given encoding.
   *
   * @throws java.io.FileNotFoundException if the file does not exist
   * @throws IOException                   if the file cannot be read
   * 构造一个文档的迭代器，用来迭代文档每一行的内容
   * getFileInputStream(file)，将文档转换成输入流，并针对zip和gz压缩文件生成专门的输入流
   */
  public FileLineIterator(File file, Charset encoding, boolean skipFirstLine) throws IOException {
    this(getFileInputStream(file), encoding, skipFirstLine);
  }

  public FileLineIterator(InputStream is) throws IOException {
    this(is, Charsets.UTF_8, false);
  }

  public FileLineIterator(InputStream is, boolean skipFirstLine) throws IOException {
    this(is, Charsets.UTF_8, skipFirstLine);
  }

  public FileLineIterator(InputStream is, Charset encoding, boolean skipFirstLine) throws IOException {
    //创建reader输入流
	  //InputStreamReader(is,encoding)将生成一个匿名的输入流类，并且以UTF-8的形式进行编码
	  reader = new BufferedReader(new InputStreamReader(is, encoding));
    if (skipFirstLine) {
      reader.readLine();
    }
  }

  public FileLineIterator(InputStream is, Charset encoding, boolean skipFirstLine, String filename)
    throws IOException {
    InputStream compressedInputStream;

    if ("gz".equalsIgnoreCase(Files.getFileExtension(filename.toLowerCase()))) {
      compressedInputStream = new GZIPInputStream(is);
    } else if ("zip".equalsIgnoreCase(Files.getFileExtension(filename.toLowerCase()))) {
      compressedInputStream = new ZipInputStream(is);
    } else {
      compressedInputStream = is;
    }

    reader = new BufferedReader(new InputStreamReader(compressedInputStream, encoding));
    if (skipFirstLine) {
      reader.readLine();
    }
  }

  //可以将文件化作输入流，文件每一行作为一次输入，分为通常输入流，gzip文档输入流，zip文档输入流
  static InputStream getFileInputStream(File file) throws IOException {
    InputStream is = new FileInputStream(file);
    String name = file.getName();
    if ("gz".equalsIgnoreCase(Files.getFileExtension(name.toLowerCase()))) {
      return new GZIPInputStream(is);
    } else if ("zip".equalsIgnoreCase(Files.getFileExtension(name.toLowerCase()))) {
      return new ZipInputStream(is);
    } else {
      return is;
    }
  }

  @Override
  protected String computeNext() {
    String line;
    try {
      line = reader.readLine();
    } catch (IOException ioe) {
      try {
        close();
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
      throw new IllegalStateException(ioe);
    }
    return line == null ? endOfData() : line;
  }


  @Override
  public void skip(int n) {
    try {
      for (int i = 0; i < n; i++) {
        if (reader.readLine() == null) {
          break;
        }
      }
    } catch (IOException ioe) {
      try {
        close();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  @Override
  public void close() throws IOException {
    endOfData();
    Closeables.close(reader, true);
  }

}
