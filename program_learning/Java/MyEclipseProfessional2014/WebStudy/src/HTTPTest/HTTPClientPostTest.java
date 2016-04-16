package HTTPTest;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTPClientPostTest {

	public static void main(String[] args) {
		new HttpClientPostThread().start();
	}

}

class HttpClientPostThread extends Thread {
	private HttpClient httpClient = HttpClients.createDefault();

	public void run() {
		try {
			//首先获取accessToken
			HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa95d7f1f6a32b3c7&secret=c6fa3957e1ef96a55a4e351b84a49d38");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpResultEntity = httpResponse.getEntity();
			String accessToken = EntityUtils.toString(httpResultEntity, "UTF-8");

			//然后构建Json解析器，解析Json，获取access_token
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(accessToken);
			String access_token=jsonObject.get("access_token").toString();

			//然后，封装要发送的json信息
			JsonObject jsonObjectPost = new JsonObject();
			jsonObjectPost.addProperty("device_num", "1");
			JsonArray jsonArray = new JsonArray();
			jsonArray.add("000100002a19706f");
			jsonObjectPost.add("device_id_list", jsonArray);

			//最后，通过Post获取DeviceID的二维码
			HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/device/create_qrcode?access_token="+access_token.substring(1, access_token.length()-1));
			StringEntity stringEntity = new StringEntity(jsonObjectPost.toString());    
			stringEntity.setContentEncoding("UTF-8");    
			stringEntity.setContentType("application/json");    
			httpPost.setEntity(stringEntity);
			httpResponse = httpClient.execute(httpPost);
			httpResultEntity = httpResponse.getEntity();
			String QRCodes = EntityUtils.toString(httpResultEntity,"UTF-8");
			
			//解析获得的json数据，获得ticket信息
			JsonObject jsonObject_ticket = (JsonObject) jsonParser.parse(QRCodes);
			JsonArray ticketJsonArray = jsonObject_ticket.get("code_list").getAsJsonArray();
			for (int i = 0; i <ticketJsonArray.size(); i++) {
				JsonObject ticketJsonObject = (JsonObject) ticketJsonArray.get(i);
				String ticket = ticketJsonObject.get("ticket").getAsString();
//				ticket = charReplace(ticket, "\\", "");
				System.out.println("device_id:  "+ticketJsonObject.get("device_id").getAsString()+",\n\tticket:  "+ticket);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	 
    public  String charReplace(String string,String match,String replace){  
        Pattern pattern = Pattern.compile(match);  
        Matcher matcher = pattern.matcher(string);  
        String string_replace = matcher.replaceAll(replace);  
        return string_replace;
    }  
}