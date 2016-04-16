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

package org.apache.mahout.cf.taste.impl.similarity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.common.RefreshHelper;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.similarity.PreferenceInferrer;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.google.common.base.Preconditions;

import stczwd.database.mysql.MysqlConnect;

/** Abstract superclass encapsulating functionality that is common to most implementations in this package. */
abstract class AbstractSimilarity extends AbstractItemSimilarity implements UserSimilarity {

  private PreferenceInferrer inferrer;
  private final boolean weighted;
  private final boolean centerData;
  private int cachedNumItems;
  private int cachedNumUsers;
  private final RefreshHelper refreshHelper;
  private MysqlConnect mysql;

  /**
   * <p>
   * Creates a possibly weighted {@link AbstractSimilarity}.
   * UncenteredCosineSimilarity: Weighting->UNWEIGHTED; centerData->false
   * this.weighted = false; DataModel->GenericDataModel
   * 
   *   public int getNumUsers() {
   * 		return userIDs.length;
   *		}
   *   public int getNumItems() {
   * 		return itemIDs.length;
   *		}
   * </p>
   */
  AbstractSimilarity(final DataModel dataModel, Weighting weighting, boolean centerData) throws TasteException {
    //将dataModel存到了this.refreshHelper的dependencies的list中，并设置了ReentrantLock锁
	super(dataModel);
    this.weighted = weighting == Weighting.WEIGHTED;
    this.centerData = centerData;
    //获取UserID和ItemID总数目
    this.cachedNumItems = dataModel.getNumItems();
    this.cachedNumUsers = dataModel.getNumUsers();
    //更新一下refreshHelper.refreshRunnable的值
    //refreshRunnable对应于newCallable对象，其方法是call，获得itemID和userID数目
    this.refreshHelper = new RefreshHelper(new Callable<Object>() {
      @Override
      public Object call() throws TasteException {
        cachedNumItems = dataModel.getNumItems();
        cachedNumUsers = dataModel.getNumUsers();
        return null;
      }
    });
//    this.mysql = new MysqlConnect("123.57.223.22","stczwd");
  }

  final PreferenceInferrer getPreferenceInferrer() {
    return inferrer;
  }
  
  @Override
  public final void setPreferenceInferrer(PreferenceInferrer inferrer) {
    Preconditions.checkArgument(inferrer != null, "inferrer is null");
    refreshHelper.addDependency(inferrer);
    refreshHelper.removeDependency(this.inferrer);
    this.inferrer = inferrer;
  }
  
  final boolean isWeighted() {
    return weighted;
  }
  
  /**
   * <p>
   * Several subclasses in this package implement this method to actually compute the similarity from figures
   * computed over users or items. Note that the computations in this class "center" the data, such that X and
   * Y's mean are 0.
   * </p>
   * 
   * <p>
   * Note that the sum of all X and Y values must then be 0. This value isn't passed down into the standard
   * similarity computations as a result.
   * </p>
   * 
   * @param n
   *          total number of users or items
   * @param sumXY
   *          sum of product of user/item preference values, over all items/users preferred by both
   *          users/items
   * @param sumX2
   *          sum of the square of user/item preference values, over the first item/user
   * @param sumY2
   *          sum of the square of the user/item preference values, over the second item/user
   * @param sumXYdiff2
   *          sum of squares of differences in X and Y values
   * @return similarity value between -1.0 and 1.0, inclusive, or {@link Double#NaN} if no similarity can be
   *         computed (e.g. when no items have been rated by both users
   */
  abstract double computeResult(int n, double sumXY, double sumX2, double sumY2, double sumXYdiff2);
  
  @Override
  public double userSimilarity(long userID1, long userID2) throws TasteException {
    DataModel dataModel = getDataModel();
    PreferenceArray xPrefs = dataModel.getPreferencesFromUser(userID1);
    PreferenceArray yPrefs = dataModel.getPreferencesFromUser(userID2);
    int xLength = xPrefs.length();
    int yLength = yPrefs.length();
    
    if (xLength == 0 || yLength == 0) {
      return Double.NaN;
    }
    
    long xIndex = xPrefs.getItemID(0);
    long yIndex = yPrefs.getItemID(0);
    int xPrefIndex = 0;
    int yPrefIndex = 0;
    
    double sumX = 0.0;
    double sumX2 = 0.0;
    double sumY = 0.0;
    double sumY2 = 0.0;
    double sumXY = 0.0;
    double sumXYdiff2 = 0.0;
    int count = 0;
    
    boolean hasInferrer = inferrer != null;
    
    while (true) {
      int compare = xIndex < yIndex ? -1 : xIndex > yIndex ? 1 : 0;
      if (hasInferrer || compare == 0) {
        double x;
        double y;
        if (xIndex == yIndex) {
          // Both users expressed a preference for the item
          x = xPrefs.getValue(xPrefIndex);
          y = yPrefs.getValue(yPrefIndex);
        } else {
          // Only one user expressed a preference, but infer the other one's preference and tally
          // as if the other user expressed that preference
          if (compare < 0) {
            // X has a value; infer Y's
            x = xPrefs.getValue(xPrefIndex);
            y = inferrer.inferPreference(userID2, xIndex);
          } else {
            // compare > 0
            // Y has a value; infer X's
            x = inferrer.inferPreference(userID1, yIndex);
            y = yPrefs.getValue(yPrefIndex);
          }
        }
        sumXY += x * y;
        sumX += x;
        sumX2 += x * x;
        sumY += y;
        sumY2 += y * y;
        double diff = x - y;
        sumXYdiff2 += diff * diff;
        count++;
      }
      if (compare <= 0) {
        if (++xPrefIndex >= xLength) {
          if (hasInferrer) {
            // Must count other Ys; pretend next X is far away
            if (yIndex == Long.MAX_VALUE) {
              // ... but stop if both are done!
              break;
            }
            xIndex = Long.MAX_VALUE;
          } else {
            break;
          }
        } else {
          xIndex = xPrefs.getItemID(xPrefIndex);
        }
      }
      if (compare >= 0) {
        if (++yPrefIndex >= yLength) {
          if (hasInferrer) {
            // Must count other Xs; pretend next Y is far away
            if (xIndex == Long.MAX_VALUE) {
              // ... but stop if both are done!
              break;
            }
            yIndex = Long.MAX_VALUE;
          } else {
            break;
          }
        } else {
          yIndex = yPrefs.getItemID(yPrefIndex);
        }
      }
    }
    
    // "Center" the data. If my math is correct, this'll do it.
    double result;
    if (centerData) {
      double meanX = sumX / count;
      double meanY = sumY / count;
      // double centeredSumXY = sumXY - meanY * sumX - meanX * sumY + n * meanX * meanY;
      double centeredSumXY = sumXY - meanY * sumX;
      // double centeredSumX2 = sumX2 - 2.0 * meanX * sumX + n * meanX * meanX;
      double centeredSumX2 = sumX2 - meanX * sumX;
      // double centeredSumY2 = sumY2 - 2.0 * meanY * sumY + n * meanY * meanY;
      double centeredSumY2 = sumY2 - meanY * sumY;
      result = computeResult(count, centeredSumXY, centeredSumX2, centeredSumY2, sumXYdiff2);
    } else {
      result = computeResult(count, sumXY, sumX2, sumY2, sumXYdiff2);
    }
    
    if (!Double.isNaN(result)) {
      result = normalizeWeightResult(result, count, cachedNumItems);
    }
    return result;
  }
  
  @Override
  //再经过一步，计算两个itemID之间的相似度
  //其中itemID1是所要计算的itemID，itemID2是用户具有偏好值的itemID
  public final double itemSimilarity(long itemID1, long itemID2) throws TasteException {
	//dataModel->GenericDataModel->FastByIdMap<GenericUserPreferenceArray>
    DataModel dataModel = getDataModel();
    //xPrefs->GenericItemPreferenceArray->对itemID1有偏好值的GenericPreference列表
    PreferenceArray xPrefs = dataModel.getPreferencesForItem(itemID1);
    //yPrefs->GenericItemPreferenceArray->对itemID2有偏好值的GenericPreference列表
    PreferenceArray yPrefs = dataModel.getPreferencesForItem(itemID2);
    //分别获取对itemID1/itemID2有偏好值的userID的数目
    int xLength = xPrefs.length();
    int yLength = yPrefs.length();
    
    if (xLength == 0 || yLength == 0) {
      return Double.NaN;
    }

    //xPrefs->GenericItemPreferenceArray->对itemID1有偏好值的GenericPreference列表
    //yPrefs->GenericItemPreferenceArray->对itemID2有偏好值的GenericPreference列表
    //xIndex->获取ids[]的第一个userID
    long xIndex = xPrefs.getUserID(0);
    long yIndex = yPrefs.getUserID(0);
    
    //当前userID对应的值，用来获取与userID对应的preferenceValue
    int xPrefIndex = 0;
    int yPrefIndex = 0;
    
    double sumX = 0.0;
    double sumX2 = 0.0;
    double sumY = 0.0;
    double sumY2 = 0.0;
    double sumXY = 0.0;
    //Ε(x-y)²
    double sumXYdiff2 = 0.0;
    int count = 0;
    
    // No, pref inferrers and transforms don't apply here. I think.
    
    while (true) {
      //首先判断两个userID的大小，查看是否是同一个userID
      int compare = xIndex < yIndex ? -1 : xIndex > yIndex ? 1 : 0;
      if (compare == 0) {
        // Both users expressed a preference for the item
    	//获取与userID对应的preference
        double x = xPrefs.getValue(xPrefIndex);
        double y = yPrefs.getValue(yPrefIndex);
        sumXY += x * y;
        sumX += x;
        sumX2 += x * x;
        sumY += y;
        sumY2 += y * y;
        double diff = x - y;
        sumXYdiff2 += diff * diff;
        count++;
      }
      /**
       * 存在三种情况，只要xIndex与yIndex不相等，就自动进行调整，因为事先已经对userIDs[]进行了排序，所以获取下一个userID即可
       * 1、xIndex<yIndex  ->则获取itemID1对应的下一个userID
       * 2、xIndex>yIndex  ->则获取itemID2对应的下一个userID
       * 3、xIndex=yIndex  ->相等的情况下，上面公式已经进行了计算，只需进行下一步的数据即可，即xIndex与yIndex均获取下一个userID
       */
      if (compare <= 0) {
        if (++xPrefIndex == xLength) {
          break;
        }
        xIndex = xPrefs.getUserID(xPrefIndex);
      }
      if (compare >= 0) {
        if (++yPrefIndex == yLength) {
          break;
        }
        yIndex = yPrefs.getUserID(yPrefIndex);
      }
    }

    double result;
    if (centerData) {
      // See comments above on these computations
      double n = (double) count;
      double meanX = sumX / n;
      double meanY = sumY / n;
      // double centeredSumXY = sumXY - meanY * sumX - meanX * sumY + n * meanX * meanY;
      double centeredSumXY = sumXY - meanY * sumX;
      // double centeredSumX2 = sumX2 - 2.0 * meanX * sumX + n * meanX * meanX;
      double centeredSumX2 = sumX2 - meanX * sumX;
      // double centeredSumY2 = sumY2 - 2.0 * meanY * sumY + n * meanY * meanY;
      double centeredSumY2 = sumY2 - meanY * sumY;
      result = computeResult(count, centeredSumXY, centeredSumX2, centeredSumY2, sumXYdiff2);
    } else {
      //使用具体的similarity的计算相似度方法
      result = computeResult(count, sumXY, sumX2, sumY2, sumXYdiff2);
    }
    
    if (!Double.isNaN(result)) {
      /*
       * cachedNumUsers->GenericDataModel中userID的总数目
       * result->itemID1与itemID2之间的相似度
       * count->itemID1与itemID2之间相同userID的个数
       * 检测result是否准确，是否存在权重。
       */
      result = normalizeWeightResult(result, count, cachedNumUsers);
    }
    return result;
  }

  @Override
  /**
   * 计算itemID与itemIDs[]的相似度，其中itemIDs是用户所具有偏好值的itemID
   * itemID1->所要计算相似度的itemID
   * itemID2s->用户所具有偏好值的itemIDs
   */
  public double[] itemSimilarities(long itemID1, long[] itemID2s) throws TasteException {
	  MysqlConnect mysql = new MysqlConnect("123.57.223.22","stczwd");
	  //获取到目前用户所具有偏好值的itemID的数量
	  int length = itemID2s.length;
	  //result用来盛放与用户itemID对应的相似度
	  double[] result = new double[length];
	  for (int i = 0; i < length; i++) {
		  //检查数据库中是否存在两个物品的数据，如果不存在，则进行添加，如果存在，则不进行重复计算
		  ResultSet itemSimilarityCheckresult=mysql.databasecheck("select * from ItemSimilarity300 where `ItemID1`="+itemID1+" and `ItemID2`="+itemID2s[i]);
		  try {
				if(!itemSimilarityCheckresult.next()){
					//计算itemID与每个userID下的itemID的相似度
					result[i] = itemSimilarity(itemID1, itemID2s[i]);
					//将得到的itemID1和itemID2s[i]以及其对应的itemSimilarity保存到数据库内
					if(!Double.isNaN(result[i])){
						String sqloperation="insert into ItemSimilarity300(ItemID1,ItemID2,itemSimilarity) values("+itemID1+", "+itemID2s[i]+", "+result[i]+")";
						System.out.println(itemID1+"\t"+itemID2s[i]+"\t"+result[i]);
						mysql.databaseoperation(sqloperation);
					}
				}
				else {
					result[i]=itemSimilarityCheckresult.getDouble("itemSimilarity");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	mysql.databaseclose();
    return result;
  }
  
  public double itemIncrementSimilarities(long itemID1, long itemID2) throws TasteException {
	  ResultSet itemSimilarityCheckresult=mysql.databasecheck("select * from mysqlmodel where `ItemID`="+itemID1);
	  return itemID2;
  }
  
  final double normalizeWeightResult(double result, int count, int num) {
    double normalizedResult = result;
    //weighted->Weighting.UNWEIGHTED
    if (weighted) {
      double scaleFactor = 1.0 - (double) count / (double) (num + 1);
      if (normalizedResult < 0.0) {
        normalizedResult = -1.0 + scaleFactor * (1.0 + normalizedResult);
      } else {
        normalizedResult = 1.0 - scaleFactor * (1.0 - normalizedResult);
      }
    }
    // Make sure the result is not accidentally a little outside [-1.0, 1.0] due to rounding:
    //检测是否相似度的值是否正确
    if (normalizedResult < -1.0) {
      normalizedResult = -1.0;
    } else if (normalizedResult > 1.0) {
      normalizedResult = 1.0;
    }
    return normalizedResult;
  }
  
  @Override
  public final void refresh(Collection<Refreshable> alreadyRefreshed) {
    super.refresh(alreadyRefreshed);
    refreshHelper.refresh(alreadyRefreshed);
  }
  
  @Override
  public final String toString() {
    return this.getClass().getSimpleName() + "[dataModel:" + getDataModel() + ",inferrer:" + inferrer + ']';
  }
  
}
