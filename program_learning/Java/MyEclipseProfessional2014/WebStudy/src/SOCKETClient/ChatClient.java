package SOCKETClient;

import java.awt.EventQueue;

public class ChatClient {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClientJPanal frame = new ChatClientJPanal();
					frame.setVisible(true);
					ChatManager.getChatManager().setChatClientJPanal(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
