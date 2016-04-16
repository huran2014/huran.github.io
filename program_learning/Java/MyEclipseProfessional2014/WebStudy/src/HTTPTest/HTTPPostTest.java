package HTTPTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTPPostTest {

	public static void main(String[] args) {
		new HTTPPostThread().start();
		
	}

}

class HTTPPostThread extends Thread{
	
	private URL url;
	private String message;
	private static String mac_address="000100000bf7058f";
	
	@Override
	public void run() {
		try {
			//首先获取accessToken
			url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa95d7f1f6a32b3c7&secret=c6fa3957e1ef96a55a4e351b84a49d38");
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			StringBuilder stringBuilder = new StringBuilder();
			while ((message = bufferedReader.readLine()) != null) {
				stringBuilder.append(message);
			}
			bufferedReader.close();
			
			//然后构建Json解析器，解析Json，获取access_token
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(stringBuilder.toString());
			String access_token=jsonObject.get("access_token").toString();
			
			System.out.println("access_token="+access_token.substring(1, access_token.length()-1));
			
			/**
			 * 发送请求，注册硬件
			 */
			url = new URL("https://api.weixin.qq.com/device/authorize_device?access_token="+access_token.substring(1, access_token.length()-1));
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.addRequestProperty("encoding", "UTF-8");
			//设置httpURLConnection读入为true，httpURLConnection输出为true
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			
			//封装json数据
			JsonObject jsonObject1 = new JsonObject();
			jsonObject1.addProperty("device_num", "1");
			JsonArray jsonArray = new JsonArray();
			JsonObject jsonObjectMac = new JsonObject();
			jsonObjectMac.addProperty("id", mac_address);
			jsonObjectMac.addProperty("mac", "123456789ABC");
			jsonObjectMac.addProperty("connect_protocol", "4");
			jsonObjectMac.addProperty("auth_key", "");
			jsonObjectMac.addProperty("close_strategy", "3");
			jsonObjectMac.addProperty("conn_strategy", "1");
			jsonObjectMac.addProperty("crypt_method", "0");
			jsonObjectMac.addProperty("auth_ver", "0");
			jsonObjectMac.addProperty("manu_mac_pos", "-1");
			jsonObjectMac.addProperty("ser_mac_pos", "-1");
			jsonArray.add(jsonObjectMac);
			jsonObject1.add("device_list", jsonArray);
			jsonObject1.addProperty("op_type", "0");
//			jsonObject1.addProperty("product_id", "12222");
			System.out.println(jsonObject1.toString());
			
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
			//bufferedWriter.write("{\"device_num\":\"1\",\"device_id_list\":[\"000100002a19706f\"]}");
			bufferedWriter.write(jsonObject1.toString());
			bufferedWriter.flush();
			
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			stringBuilder = new StringBuilder();
			while((message = bufferedReader.readLine()) != null) {
				stringBuilder.append(message);
			}
			bufferedWriter.close();
			bufferedReader.close();
			System.out.println("获得的信息是："+stringBuilder.toString());
			
			/**
			 * 发送请求获取二维码
			 */
			url = new URL("https://api.weixin.qq.com/device/create_qrcode?access_token="+access_token.substring(1, access_token.length()-1));
			HttpURLConnection httpURLConnection1 = (HttpURLConnection) url.openConnection();
			httpURLConnection1.addRequestProperty("encoding", "UTF-8");
			//设置httpURLConnection读入为true，httpURLConnection输出为true
			httpURLConnection1.setDoInput(true);
			httpURLConnection1.setDoOutput(true);
			httpURLConnection1.setRequestMethod("POST");
			
			//封装json数据
			JsonObject jsonObject3 = new JsonObject();
			jsonObject3.addProperty("device_num", "1");
			JsonArray jsonArray3 = new JsonArray();
			jsonArray3.add(mac_address);
			jsonObject3.add("device_id_list", jsonArray3);
			
			BufferedWriter bufferedWriter3 = new BufferedWriter(new OutputStreamWriter(httpURLConnection1.getOutputStream()));
			//bufferedWriter.write("{\"device_num\":\"1\",\"device_id_list\":[\"000100002a19706f\"]}");
			bufferedWriter3.write(jsonObject3.toString());
			bufferedWriter3.flush();
			
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection1.getInputStream()));
			stringBuilder = new StringBuilder();
			while((message = bufferedReader.readLine()) != null) {
				stringBuilder.append(message);
			}
			bufferedWriter3.close();
			bufferedReader.close();
			
			//解析json数据
			JsonParser jsonParser2 = new JsonParser();
			JsonObject jsonObject_url = (JsonObject) jsonParser2.parse(stringBuilder.toString()); 
			JsonArray jsonArray2 = (JsonArray) jsonObject_url.get("code_list");
			Pattern pattern = Pattern.compile("\\\\");  
			System.out.println(((JsonObject) jsonArray2.get(0)).get("ticket"));
			Matcher matcher = pattern.matcher(((JsonObject) jsonArray2.get(0)).get("ticket").getAsString());  
			String url =  matcher.replaceAll("");
			System.out.println("获得的信息是："+url);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}