package GSONTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonWriter {

	public static void main(String[] args) {

		JsonObject languageObject = new JsonObject();
		languageObject.addProperty("cat", "IT");
		
		JsonArray jsonArray = new JsonArray();
		JsonObject lan1Object = new JsonObject();
		lan1Object.addProperty("id", 1);
		lan1Object.addProperty("name", "Java");
		lan1Object.addProperty("IDE", "MyEclipse");
		jsonArray.add(lan1Object);

		JsonObject lan2Object = new JsonObject();
		lan2Object.addProperty("id", 2);
		lan2Object.addProperty("name", "C++");
		lan2Object.addProperty("IDE", "Visual Studio");
		jsonArray.add(lan2Object);
		
		JsonObject lan3Object = new JsonObject();
		lan3Object.addProperty("id", 3);
		lan3Object.addProperty("name", "Python");
		lan3Object.addProperty("IDE", "PythonIDE");
		jsonArray.add(lan3Object);
		
		languageObject.add("lan", jsonArray);
		
		System.out.println(languageObject.toString());
	}

}
