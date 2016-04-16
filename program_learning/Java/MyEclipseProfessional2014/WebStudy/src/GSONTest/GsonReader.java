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
			//���ȴ���json��������Ȼ���json�ļ��л�ȡ��������
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(new FileReader("Json/languages.json")).getAsJsonObject();
			
			//��ȡlanguage�������
			JsonObject languageObject = jsonObject.get("language").getAsJsonObject();
			
			//��ȡcat���ݺ�jsonarray����
			System.out.println("Language\tcat:"+languageObject.get("cat").getAsString());
			JsonArray lanArray = languageObject .get("lan").getAsJsonArray();
			for (int i = 0; i < lanArray.size(); i++) {
				System.out.println("��������������������������������������������������������������������������������������������������������������������������������������������������������������������������");
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
