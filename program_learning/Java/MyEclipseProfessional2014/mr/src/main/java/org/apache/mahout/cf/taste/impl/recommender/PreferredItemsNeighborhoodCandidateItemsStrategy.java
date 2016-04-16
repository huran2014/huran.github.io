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

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

//PreferredItemsNeighborhoodCandidateItemsStrategy 是AbstractCandidateItemsStrategy的子类，而AbstractCandidateItemsStrategy调用了CandidateItemsStrategy的接口
public final class PreferredItemsNeighborhoodCandidateItemsStrategy extends AbstractCandidateItemsStrategy {

  /**
   * returns all items that have not been rated by the user and that were preferred by another user
   * that has preferred at least one item that the current user has preferred too
   * 实际调用的方法，但其实已经使用父类的方法进行了参数转换。
   * preferredItemIDs->itemIDs
   * datamodel->GenericDataModel
   * includeKnownItems->true
   */
  @Override
  protected FastIDSet doGetCandidateItems(long[] preferredItemIDs, DataModel dataModel, boolean includeKnownItems)
    throws TasteException {
	//新建possibleItemsIDs,
    FastIDSet possibleItemsIDs = new FastIDSet();
    //preferredItemIDs->itemIDs
    for (long itemID : preferredItemIDs) {
    	//itemPreferences->GenericItemPrefereceArray->(itemID,ids[],values)
      PreferenceArray itemPreferences = dataModel.getPreferencesForItem(itemID);
      //itemPreferences.length()->ids.length->itemID对应下的userIDs数目
      //就是对itemID有偏好值的userID数目
      int numUsersPreferringItem = itemPreferences.length();
      for (int index = 0; index < numUsersPreferringItem; index++) {
    	  /**
    	   * itemPreferences->GenericItemPreferenceArray
    	   * itemPreferences.getUserID(index)->userID
    	   * datamodel.getItemIDsFromUser(userID)->FastIDSet<itemID>
    	   * 返回所有用户有偏好值得所有有关的itemIDs
    	   */
        possibleItemsIDs.addAll(dataModel.getItemIDsFromUser(itemPreferences.getUserID(index)));
      }
    }
    if (!includeKnownItems) {
      possibleItemsIDs.removeAll(preferredItemIDs);
    }
    return possibleItemsIDs;
  }

}
