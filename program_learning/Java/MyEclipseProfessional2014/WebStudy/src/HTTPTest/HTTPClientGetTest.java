package HTTPTest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTPClientGetTest {

	public static void main(String[] args) {
		new HTTPClientGetThread().start();
		
	}

}

class HTTPClientGetThread extends Thread {
	//创建常规的httpclient
	private HttpClient httpClient = HttpClients.createDefault();
	
	public void run() {
		try {
			HttpGet httpGet = new HttpGet("http://115.29.151.149:8081/channel.php?action=program&no=2");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpResultEntity = httpResponse.getEntity();
			
			System.out.println("获得的信息是："+EntityUtils.toString(httpResultEntity, "UTF-8"));
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}