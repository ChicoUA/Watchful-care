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
    	
    	channel.exchangeDeclare("fanout_exchange", "fanout");
    	channel.queueDeclare("message_queue", false, false, false, null);
    	
    	ServerSocket ss = new ServerSocket(7779);
		System.out.println("ServerSocket awaiting connections...");
    	
    	while(true) {
    		
		
    		Socket socket = ss.accept();
    		System.out.println("Connection from " + socket + "!");
    		
    		new Thread(new workerThread(socket, "Multithreaded Server", channel)).start();
    		System.out.println("Ready for new Connection");
		
    	}
		
		
		
    }
}
