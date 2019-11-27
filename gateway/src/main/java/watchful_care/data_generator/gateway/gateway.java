package watchful_care.data_generator.gateway;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import data_generator.generator;

public class gateway 
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, InterruptedException
    {
    	ConnectionFactory factory = new ConnectionFactory();
    	factory.setUsername("admin");
    	factory.setPassword("1234");
    	factory.setVirtualHost("/");
    	factory.setHost("192.168.80.129");
    	factory.setPort(5672);
    	Connection conn = factory.newConnection();
    	Channel channel = conn.createChannel();
    	
    	channel.queueDeclare("message_queue", false, false, false, null);
    	
    	ServerSocket ss = new ServerSocket(7779);
		System.out.println("ServerSocket awaiting connections...");
		
		Socket socket = ss.accept();
		System.out.println("Connection from " + socket + "!");
		
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		
		
		while(true) {
			
			generator listOfgenerators = (generator) objectInputStream.readObject();
			System.out.println(listOfgenerators);
			byte[] data = listOfgenerators.toString().getBytes();
		    
		    channel.basicPublish("", "message_queue.anonymous.gnBs6h45Tf2jsNrqQc5SOQ", null, data);
		    System.out.println("Message sent to queue");

		}
    }
}
