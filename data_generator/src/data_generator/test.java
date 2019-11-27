package data_generator;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*; 

public class test {
    private OutputStreamWriter out = null;

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 7779); 
		System.out.println("Connected!");
		
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(outputStream);
		
		generator gen1 = new generator();
		/*
		generator gen2 = new generator();
		generator gen3 = new generator();
		*/
		
		
		while(true) {
			System.out.println(gen1);

			
			out.writeObject(gen1);
			out.reset();
			
			gen1.generateNewData();

			
			Thread.sleep(5000);
		}
	}

}
