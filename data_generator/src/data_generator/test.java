package data_generator;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*; 

public class test {
    private OutputStreamWriter out = null;

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 7776); 
		System.out.println("Connected!");
		
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		
		generator gen1 = new generator();
		generator gen2 = new generator();
		generator gen3 = new generator();
		
		List<generator> lista = new ArrayList<>();
		lista.add(gen1);
		lista.add(gen2);
		lista.add(gen3);
		
		
		
		while(true) {
			System.out.println(gen1);
			System.out.println(gen2);
			System.out.println(gen3);
			
			objectOutputStream.writeObject(gen1);
			objectOutputStream.writeObject(gen2);
			objectOutputStream.writeObject(gen3);
			
			gen1.generateNewData();
			gen2.generateNewData();
			gen3.generateNewData();
			
			Thread.sleep(5000);
		}
	}

}
