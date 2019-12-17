package watchful_care.data_generator.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import com.rabbitmq.client.Channel;

import data_generator.generator;

public class workerThread implements Runnable{
	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected Channel channel = null;

	
	
	public workerThread(Socket clientSocket, String serverText, Channel channel) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.channel = channel;
	}



	@Override
	public void run() {
		InputStream inputStream = null;
		try {
			inputStream = this.clientSocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while(true) {
			
			generator listOfgenerators = null;
			try {
				listOfgenerators = (generator) objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block

				break;
			}
			System.out.println(listOfgenerators);
			byte[] data = listOfgenerators.toString().getBytes();
		    
		    try {
				channel.basicPublish("", "message_queue.anonymous.lp-JgHYhReWIPfXyVFw-pg", null, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		    System.out.println("Message sent to queue");

		}
		
	}
	
}
