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
		
		System.out.println(args[1]);
		if(args[2].equals("temperature") || args[2].equals("bpm")){
			generator gen1 = new generator((String)args[0], Integer.parseInt(args[1]), args[2]);
			System.out.println(gen1);
			out.writeObject(gen1);
			out.reset();

		}
		else {
			generator gen1 = new generator((String)args[0], Integer.parseInt(args[1]));
		
		/*
		generator gen2 = new generator();
		generator gen3 = new generator();
		*/
		
		
			while(true) {
				System.out.println(gen1);

			
				out.writeObject(gen1);
				out.reset();
				double battery = gen1.getDt().getBattery() - 10.0;
				
				if(battery <= 0) {
					System.out.println("Battery empty, shutting down...");
					break;
				}
			
				gen1.generateNewData(battery);

			
				Thread.sleep(gen1.getBeat());
			}
		}
	}

}
