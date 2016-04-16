package stczwd.recommendation.ItemCF;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

//import org.apache.mahout.common.RandomUtils;

public class Itemcf {

	public static void main(String[] args) throws IOException, TasteException {
		// TODO Auto-generated method stub
		RecommenderIntro Item_reco = new RecommenderIntro();
		// Item_reco.run_user();
		Item_reco.run_item();
	}

}

class RecommenderIntro {

	public void run_item() throws IOException, TasteException {
		DataModel model = new FileDataModel(new File("data/intro.csv"));
		ItemSimilarity similarity = new UncenteredCosineSimilarity(model);
		Recommender recommender = new GenericItemBasedRecommender(model,
				similarity);
		List<RecommendedItem> recommendations = recommender.recommend(1, 1);
		for (RecommendedItem recommendation : recommendations) {
			System.out
					.println("The itemCF recommendation is " + recommendation);
		}
	}

	public void run_user() throws IOException, TasteException {
		DataModel model = new FileDataModel(new File("data/intro.csv"));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(100,
				similarity, model);
		Recommender recommender = new GenericUserBasedRecommender(model,
				neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(1, 3);
		for (RecommendedItem recommendation : recommendations) {
			System.out
					.println("The userCF recommendation is " + recommendation);
		}
	}

	public void run_user_elevator() throws IOException, TasteException {
		// 通过File构造函数，将路径转换为File对象
		DataModel model = new FileDataModel(new File("data/intro.csv"));
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {

			@Override
			public Recommender buildRecommender(DataModel model)
					throws TasteException {

				UserSimilarity similarity = new PearsonCorrelationSimilarity(
						model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
						similarity, model);

				Recommender recommender = new GenericUserBasedRecommender(
						model, neighborhood, similarity);

				List<RecommendedItem> recommendations = recommender.recommend(
						1, 1);

				for (RecommendedItem recommendation : recommendations) {
					System.out.println("The userCF recommendation is :"
							+ recommendation);
				}
				return recommender;
			}
		};

		this.CF_evaluate(recommenderBuilder, model);
	}

	public void CF_evaluate(final RecommenderBuilder recommenderBuilder,
			DataModel model) throws TasteException {
		// RandomUtils.useTestSeed();

		RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();

		IRStatistics stats = evaluator.evaluate(recommenderBuilder, null,
				model, null, 2,
				GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
		System.out.println(stats.getPrecision());
		System.out.println(stats.getRecall());
	}
}