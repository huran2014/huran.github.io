package HTTPTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HTTPGetTest {

	public static void main(String[] args) {
		new GetTest("http://115.29.151.149:8081/channel.php?action=program&no=2").start();
		
	}

}

class GetTest extends Thread{
	private URL url;
	private String message;
	
	public GetTest(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			StringBuilder stringBuilder = new StringBuilder();
			while ((message = bufferedReader.readLine()) != null) {
				stringBuilder.append(message);
			}
			bufferedReader.close();
			
			System.out.println(stringBuilder.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}