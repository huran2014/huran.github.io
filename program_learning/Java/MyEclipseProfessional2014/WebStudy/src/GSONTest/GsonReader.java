package GSONTest;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GsonReader {

	public static void main(String[] args) {

		try {
			//遍枠幹秀json盾裂匂��隼朔貫json猟周嶄資函俊僕方象
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(new FileReader("Json/languages.json")).getAsJsonObject();
			
			//資函language蚊議方象
			JsonObject languageObject = jsonObject.get("language").getAsJsonObject();
			
			//資函cat方象才jsonarray方象
			System.out.println("Language\tcat:"+languageObject.get("cat").getAsString());
			JsonArray lanArray = languageObject .get("lan").getAsJsonArray();
			for (int i = 0; i < lanArray.size(); i++) {
				System.out.println("，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，");
				JsonObject lanObject = lanArray.get(i).getAsJsonObject();
				System.out.println("id:"+lanObject.get("id").getAsString());
				System.out.println("\tname:"+lanObject.get("name").getAsString());
				System.out.println("\tIDE:"+lanObject.get("IDE").getAsString());
				if (lanObject.has("Proficiency")) {
					System.out.println("\tProficiency:"+lanObject.get("Proficiency").getAsString());
				}
			}
			
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
