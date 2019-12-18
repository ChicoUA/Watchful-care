package watchful_care.data_generator.gateway;

import java.io.DataInputStream;
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
		//ObjectInputStream objectInputStream = null;
		DataInputStream dataInputStream = null;
		dataInputStream = new DataInputStream(inputStream);
		
		
		while(true) {
			
			String listOfgenerators = null;
			try {
				//listOfgenerators = (generator) objectInputStream.readObject();
				listOfgenerators = dataInputStream.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block

				break;
			}
			System.out.println(listOfgenerators);
			byte[] data = listOfgenerators.getBytes();
		    
		    try {
				channel.basicPublish("", "message_queue.anonymous.cfWjv6v_S-qD3aimeQKglA", null, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		    System.out.println("Message sent to queue");

		}
		
	}
	
}
