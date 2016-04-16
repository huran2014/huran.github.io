package com.recommender_test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Recommender {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		String src = "info.csv";
		RecommenderIntro recommender = new RecommenderIntro(src);
		recommender.run();
	}

}

class RecommenderIntro
{
	protected String path;

	public RecommenderIntro(String path) {
		super();
		this.path = path;
	}
	
	public void run() throws IOException, TasteException
	{
		DataModel model = new FileDataModel(new File(path));
		if(model.getNumItems() >0) System.out.println("The number of item is "+model.getNumItems());
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
		GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(1, 1);
		for(RecommendedItem recommendation : recommendations)
		{
			System.out.println(recommendation);
		}
	}
}