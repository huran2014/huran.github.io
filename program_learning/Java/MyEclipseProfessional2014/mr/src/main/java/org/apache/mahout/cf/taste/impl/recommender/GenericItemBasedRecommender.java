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

package org.apache.mahout.cf.taste.impl.recommender;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.FullRunningAverage;
import org.apache.mahout.cf.taste.impl.common.RefreshHelper;
import org.apache.mahout.cf.taste.impl.common.RunningAverage;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.MostSimilarItemsCandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.LongPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * <p>
 * A simple {@link org.apache.mahout.cf.taste.recommender.Recommender} which uses a given
 * {@link org.apache.mahout.cf.taste.model.DataModel} and
 * {@link org.apache.mahout.cf.taste.similarity.ItemSimilarity} to produce recommendations. This class
 * represents Taste's support for item-based recommenders.
 * </p>
 * 
 * <p>
 * The {@link org.apache.mahout.cf.taste.similarity.ItemSimilarity} is the most important point to discuss
 * here. Item-based recommenders are useful because they can take advantage of something to be very fast: they
 * base their computations on item similarity, not user similarity, and item similarity is relatively static.
 * It can be precomputed, instead of re-computed in real time.
 * </p>
 * 
 * <p>
 * Thus it's strongly recommended that you use
 * {@link org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity} with pre-computed similarities if
 * you're going to use this class. You can use
 * {@link org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity} too, which computes
 * similarities in real-time, but will probably find this painfully slow for large amounts of data.
 * </p>
 */
public class GenericItemBasedRecommender extends AbstractRecommender implements ItemBasedRecommender {
  
  private static final Logger log = LoggerFactory.getLogger(GenericItemBasedRecommender.class);
  
  private final ItemSimilarity similarity;
  private final MostSimilarItemsCandidateItemsStrategy mostSimilarItemsCandidateItemsStrategy;
  private final RefreshHelper refreshHelper;
  private EstimatedPreferenceCapper capper;//对象，存放了相似度中的最大和最小值

  private static final boolean EXCLUDE_ITEM_IF_NOT_SIMILAR_TO_ALL_BY_DEFAULT = true;

  /**
   * @param dataModel  从FileDataModel中传过来的是GeneralDataModel
   * @param similarity  从UncenteredCosineSimilarity传过来的类
   * @param candidateItemsStrategy 继承自Refreshable的接口，可以泛型所有调用该接口的类
   * @param mostSimilarItemsCandidateItemsStrategy 继承自Refreshable的接口，可以泛型所有调用该接口的类
   */
  public GenericItemBasedRecommender(DataModel dataModel,
                                     ItemSimilarity similarity,
                                     CandidateItemsStrategy candidateItemsStrategy,
                                     MostSimilarItemsCandidateItemsStrategy mostSimilarItemsCandidateItemsStrategy) {
    //查看两个对象是否为空，并对AbstractRecommender对象进行初始化，将datamodel和candidateItemsStrategy保存到AbstractRcommender对象中
	//因为datamodel和candidateItemsStrategy是用private封装的，所以无法被子类继承。
	super(dataModel, candidateItemsStrategy);
    Preconditions.checkArgument(similarity != null, "similarity is null");
    //将Similarity的具体类型保存下来，因为UncenteredCosineSimilarity类没有做什么具体的操作，具体数据都被AbstractSimilarity和AbstractItemSimilarity保存了下来
    this.similarity = similarity;
    //checkNotNull函数，若为空，则返回NullPointerException
    //如果为空，则返回IllegalArgumentException
    Preconditions.checkArgument(mostSimilarItemsCandidateItemsStrategy != null,
        "mostSimilarItemsCandidateItemsStrategy is null");
    //返回PreferredItemsNeighborhoodCandidateItemsStrategy对象
    //可以使用AbstractCandidateItemsStrategy的FastIDSet getCandidateItems(long[] itemIDs, DataModel dataModel)方法
    this.mostSimilarItemsCandidateItemsStrategy = mostSimilarItemsCandidateItemsStrategy;
    
    /**
     * Callable能够检查<T>中T是否存在，否则会抛异常
     * Callable通过call返回void类
     * 这句话本质是够造了一个dependency列表和一个none-fare同步锁（序列化有关）
     * Void 类是一个不可实例化的占位符类，它持有对表示 Java 关键字 void 的 Class 对象的引用。 
     */
    //重新定义了refreshRunnable的call方法
    this.refreshHelper = new RefreshHelper(new Callable<Void>() {
      @Override
      public Void call() {
    	//
        capper = buildCapper();
        return null;
      }
    });
    
    //FileDataModel中的属性datamodel存储的是GenericDataModel类型，此处传入的是FileDataModel
    //下面的操作将datamodel、similarity、PreferredItemsNeighborhoodCandidateItemsStrategy添加到dependency列表中
    refreshHelper.addDependency(dataModel);
    refreshHelper.addDependency(similarity);
    refreshHelper.addDependency(candidateItemsStrategy);
    refreshHelper.addDependency(mostSimilarItemsCandidateItemsStrategy);
    capper = buildCapper();
  }

  //实际调用的构造函数
  public GenericItemBasedRecommender(DataModel dataModel, ItemSimilarity similarity) {
    this(dataModel,
         similarity,
         //返回PreferredItemsNeighborhoodCandidateItemsStrategy对象
         //可以使用AbstractCandidateItemsStrategy的FastIDSet getCandidateItems(long userID, PreferenceArray preferencesFromUser, DataModel dataModel, boolean includeKnownItems) 方法
         AbstractRecommender.getDefaultCandidateItemsStrategy(),
         //返回PreferredItemsNeighborhoodCandidateItemsStrategy对象
         //可以使用AbstractCandidateItemsStrategy的FastIDSet getCandidateItems(long[] itemIDs, DataModel dataModel)方法
         getDefaultMostSimilarItemsCandidateItemsStrategy());
  }

  /**
   * 在这里有一个用法。interface类似于100%的抽象类，并且可以通过该接口泛型调用它的所有类型
   * @return 一个PreferredItemsNeighborhoodCandidateItemsStrategy实例对象
   */
  protected static MostSimilarItemsCandidateItemsStrategy getDefaultMostSimilarItemsCandidateItemsStrategy() {
    return new PreferredItemsNeighborhoodCandidateItemsStrategy();
  }

  public ItemSimilarity getSimilarity() {
    return similarity;
  }
  
  @Override
  /**
   * 虽然在真正使用的时候确实是输入了2个参数，但是该函数在父类里面已经进行了转化，
   * 将rescorer设置为null，将includeKnownItems设置为true，重新定位到该函数
   * resCorer->null
   * includeKnownItems->true
   */
  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer, boolean includeKnownItems)
    throws TasteException {
	//检测推荐的数量
    Preconditions.checkArgument(howMany >= 1, "howMany must be at least 1");
    log.debug("Recommending items for user ID '{}'", userID);

    //使用父类的getDataModel获取到datamodel
    //从FastByIDMap<GenericUserPreferenceArray>中提取出value来
    //preferencesFromUser->GenericUserPreferenceArray
    PreferenceArray preferencesFromUser = getDataModel().getPreferencesFromUser(userID);
    if (preferencesFromUser.length() == 0) {
      return Collections.emptyList();
    }

    /**
     * preferencesFromUser->GenericUserPreferenceArray
     * includeKnownItems->true
     * 返回所有与该userID有相同评价itemID的所有用户评价过得itemIDs
     */
    FastIDSet possibleItemIDs = getAllOtherItems(userID, preferencesFromUser, includeKnownItems);

    /**
     * userID->userID
     * preferencesFromUser->GenericUserPreferenceArray
     * 定义了Estimitor对象，并将userID和GenericUserPreferenceArray保存下来
     */
    TopItems.Estimator<Long> estimator = new Estimator(userID, preferencesFromUser);

    /**
     * howMany->所需要推荐的物品的数目
     * possibleItemIDs->所有userID有preferenceValue的itemIDs->FastIDSet
     * possibleItemIDs.iterator->所有userID有preferenceValue的itemIDs的迭代器
     * rescorer->null
     * estimator->Estimitor->(userID,GenericUserPreferenceArray)
     */
    List<RecommendedItem> topItems = TopItems.getTopItems(howMany, possibleItemIDs.iterator(), rescorer,
      estimator);

    log.debug("Recommendations are: {}", topItems);
    return topItems;
  }
  
  @Override
  public float estimatePreference(long userID, long itemID) throws TasteException {
    PreferenceArray preferencesFromUser = getDataModel().getPreferencesFromUser(userID);
    Float actualPref = getPreferenceForItem(preferencesFromUser, itemID);
    if (actualPref != null) {
      return actualPref;
    }
    return doEstimatePreference(userID, preferencesFromUser, itemID);
  }

  private static Float getPreferenceForItem(PreferenceArray preferencesFromUser, long itemID) {
    int size = preferencesFromUser.length();
    for (int i = 0; i < size; i++) {
      if (preferencesFromUser.getItemID(i) == itemID) {
        return preferencesFromUser.getValue(i);
      }
    }
    return null;
  }

  @Override
  public List<RecommendedItem> mostSimilarItems(long itemID, int howMany) throws TasteException {
    return mostSimilarItems(itemID, howMany, null);
  }
  
  @Override
  public List<RecommendedItem> mostSimilarItems(long itemID, int howMany,
                                                Rescorer<LongPair> rescorer) throws TasteException {
    TopItems.Estimator<Long> estimator = new MostSimilarEstimator(itemID, similarity, rescorer);
    return doMostSimilarItems(new long[] {itemID}, howMany, estimator);
  }
  
  @Override
  public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany) throws TasteException {
    TopItems.Estimator<Long> estimator = new MultiMostSimilarEstimator(itemIDs, similarity, null,
        EXCLUDE_ITEM_IF_NOT_SIMILAR_TO_ALL_BY_DEFAULT);
    return doMostSimilarItems(itemIDs, howMany, estimator);
  }
  
  @Override
  public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany,
                                                Rescorer<LongPair> rescorer) throws TasteException {
    TopItems.Estimator<Long> estimator = new MultiMostSimilarEstimator(itemIDs, similarity, rescorer,
        EXCLUDE_ITEM_IF_NOT_SIMILAR_TO_ALL_BY_DEFAULT);
    return doMostSimilarItems(itemIDs, howMany, estimator);
  }

  @Override
  public List<RecommendedItem> mostSimilarItems(long[] itemIDs,
                                                int howMany,
                                                boolean excludeItemIfNotSimilarToAll) throws TasteException {
    TopItems.Estimator<Long> estimator = new MultiMostSimilarEstimator(itemIDs, similarity, null,
        excludeItemIfNotSimilarToAll);
    return doMostSimilarItems(itemIDs, howMany, estimator);
  }

  @Override
  public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany,
                                                Rescorer<LongPair> rescorer,
                                                boolean excludeItemIfNotSimilarToAll) throws TasteException {
    TopItems.Estimator<Long> estimator = new MultiMostSimilarEstimator(itemIDs, similarity, rescorer,
        excludeItemIfNotSimilarToAll);
    return doMostSimilarItems(itemIDs, howMany, estimator);
  }

  @Override
  public List<RecommendedItem> recommendedBecause(long userID, long itemID, int howMany) throws TasteException {
    Preconditions.checkArgument(howMany >= 1, "howMany must be at least 1");

    DataModel model = getDataModel();
    TopItems.Estimator<Long> estimator = new RecommendedBecauseEstimator(userID, itemID);

    PreferenceArray prefs = model.getPreferencesFromUser(userID);
    int size = prefs.length();
    FastIDSet allUserItems = new FastIDSet(size);
    for (int i = 0; i < size; i++) {
      allUserItems.add(prefs.getItemID(i));
    }
    allUserItems.remove(itemID);

    return TopItems.getTopItems(howMany, allUserItems.iterator(), null, estimator);
  }
  
  private List<RecommendedItem> doMostSimilarItems(long[] itemIDs,
                                                   int howMany,
                                                   TopItems.Estimator<Long> estimator) throws TasteException {
    FastIDSet possibleItemIDs = mostSimilarItemsCandidateItemsStrategy.getCandidateItems(itemIDs, getDataModel());
    return TopItems.getTopItems(howMany, possibleItemIDs.iterator(), null, estimator);
  }
  
  //获取userID对itemID的估计偏好值
  protected float doEstimatePreference(long userID, PreferenceArray preferencesFromUser, long itemID)
    throws TasteException {
    double preference = 0.0;
    double totalSimilarity = 0.0;
    int count = 0;
    /**
     * 此处是计算similarity的值，调用了一直保存的similarity类名称->UncenteredCosineSimilarity
     * itemID->所要生成偏好值的itemID
     * preferencesFromUser.getIDs()->ids[]->itemIDs[]->userID用户所具有偏好值的itemID
     */
    double[] similarities = similarity.itemSimilarities(itemID, preferencesFromUser.getIDs());
    for (int i = 0; i < similarities.length; i++) {
      double theSimilarity = similarities[i];
      if (!Double.isNaN(theSimilarity)) {
        // Weights can be negative!
    	// preferencesFromUser->GenericUserPreferenceArray
    	// preferencesFromUser.getValue(i)->获取到user对该物品的偏好值preferenceValue
    	// itemID与itemIDs的相似度分别于itemIDs的偏好值相乘
        preference += theSimilarity * preferencesFromUser.getValue(i);
        // 将相似度的值进行相加
        totalSimilarity += theSimilarity;
        count++;
      }
    }
    // Throw out the estimate if it was based on no data points, of course, but also if based on
    // just one. This is a bit of a band-aid on the 'stock' item-based algorithm for the moment.
    // The reason is that in this case the estimate is, simply, the user's rating for one item
    // that happened to have a defined similarity. The similarity score doesn't matter, and that
    // seems like a bad situation.
    if (count <= 1) {
      return Float.NaN;
    }
    // userID 对itemID的估计偏好值
    float estimate = (float) (preference / totalSimilarity);
    if (capper != null) {
      //保证所得到的的偏好值不超过用户已经有的最大和最小偏好值
      estimate = capper.capEstimate(estimate);
    }
    return estimate;
  }

  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    refreshHelper.refresh(alreadyRefreshed);
  }
  
  @Override
  public String toString() {
    return "GenericItemBasedRecommender[similarity:" + similarity + ']';
  }

  private EstimatedPreferenceCapper buildCapper() {
	//本类虽继承了AbstractRecommender的类，但是没有继承数据，所以可以直接使用方法，但无法使用datamodel数据
    //返回父类中的datamodel数据
	DataModel dataModel = getDataModel();
    //检测是否是数据，排除（0/0）的情况
	//此处调用的是具体DATAModel类型的方法，最终还是回到了GenericDataModel的getMinPreference方法
    if (Float.isNaN(dataModel.getMinPreference()) && Float.isNaN(dataModel.getMaxPreference())) {
      return null;
    } else {
    	//将Preference的最大和最小值保存到EstimatedPreferenceCapper对象中
      return new EstimatedPreferenceCapper(dataModel);
    }
  }
  
  public static class MostSimilarEstimator implements TopItems.Estimator<Long> {
    
    private final long toItemID;
    private final ItemSimilarity similarity;
    private final Rescorer<LongPair> rescorer;
    
    public MostSimilarEstimator(long toItemID, ItemSimilarity similarity, Rescorer<LongPair> rescorer) {
      this.toItemID = toItemID;
      this.similarity = similarity;
      this.rescorer = rescorer;
    }
    
    @Override
    public double estimate(Long itemID) throws TasteException {
      LongPair pair = new LongPair(toItemID, itemID);
      if (rescorer != null && rescorer.isFiltered(pair)) {
        return Double.NaN;
      }
      double originalEstimate = similarity.itemSimilarity(toItemID, itemID);
      return rescorer == null ? originalEstimate : rescorer.rescore(pair, originalEstimate);
    }
  }
  
  private final class Estimator implements TopItems.Estimator<Long> {
    
    private final long userID;
    //preferencesFromUser->GenericUserPreferenceArray
    private final PreferenceArray preferencesFromUser;
    
    /**
     * @param userID
     * @param preferencesFromUser->GenericUserPreferenceArray
     */
    private Estimator(long userID, PreferenceArray preferencesFromUser) {
      this.userID = userID;
      this.preferencesFromUser = preferencesFromUser;
    }
    
    @Override
    //生成itemID的偏好值
    public double estimate(Long itemID) throws TasteException {
    	/**
    	 * userID->所要推荐的用户
    	 * preferencesFromUser->GenericUserPreferenceArray
    	 * itemID->所要比较的itemID
    	 */
      return doEstimatePreference(userID, preferencesFromUser, itemID);
    }
  }
  
  private static final class MultiMostSimilarEstimator implements TopItems.Estimator<Long> {
    
    private final long[] toItemIDs;
    private final ItemSimilarity similarity;
    private final Rescorer<LongPair> rescorer;
    private final boolean excludeItemIfNotSimilarToAll;
    
    private MultiMostSimilarEstimator(long[] toItemIDs, ItemSimilarity similarity, Rescorer<LongPair> rescorer,
        boolean excludeItemIfNotSimilarToAll) {
      this.toItemIDs = toItemIDs;
      this.similarity = similarity;
      this.rescorer = rescorer;
      this.excludeItemIfNotSimilarToAll = excludeItemIfNotSimilarToAll;
    }
    
    @Override
    public double estimate(Long itemID) throws TasteException {
      RunningAverage average = new FullRunningAverage();
      double[] similarities = similarity.itemSimilarities(itemID, toItemIDs);
      for (int i = 0; i < toItemIDs.length; i++) {
        long toItemID = toItemIDs[i];
        LongPair pair = new LongPair(toItemID, itemID);
        if (rescorer != null && rescorer.isFiltered(pair)) {
          continue;
        }
        double estimate = similarities[i];
        if (rescorer != null) {
          estimate = rescorer.rescore(pair, estimate);
        }
        if (excludeItemIfNotSimilarToAll || !Double.isNaN(estimate)) {
          average.addDatum(estimate);
        }
      }
      double averageEstimate = average.getAverage();
      return averageEstimate == 0 ? Double.NaN : averageEstimate;
    }
  }
  
  private final class RecommendedBecauseEstimator implements TopItems.Estimator<Long> {
    
    private final long userID;
    private final long recommendedItemID;

    private RecommendedBecauseEstimator(long userID, long recommendedItemID) {
      this.userID = userID;
      this.recommendedItemID = recommendedItemID;
    }
    
    @Override
    public double estimate(Long itemID) throws TasteException {
      Float pref = getDataModel().getPreferenceValue(userID, itemID);
      if (pref == null) {
        return Float.NaN;
      }
      double similarityValue = similarity.itemSimilarity(recommendedItemID, itemID);
      return (1.0 + similarityValue) * pref;
    }
  }
  
}
