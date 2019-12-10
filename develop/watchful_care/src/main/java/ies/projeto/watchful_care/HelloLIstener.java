package ies.projeto.watchful_care;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(HelloBinding.class)
public class HelloLIstener {
	@Autowired
	private healthDataRepository healthdata;
	
	@Autowired
	private temperatureDataRepository temperaturedata;
	
	@StreamListener(target = HelloBinding.GREETING)
    public void processHelloChannelGreeting(String msg) {
		String[] treatedMsg = msg.split("/");
		if(treatedMsg.length == 4) {
			healthData hd = new healthData(Integer.parseInt(treatedMsg[0]), Double.parseDouble(treatedMsg[1]), Double.parseDouble(treatedMsg[2]), Double.parseDouble(treatedMsg[3]));
			healthdata.save(hd);
		}
		else {
			temperatureData td = new temperatureData(Integer.parseInt(treatedMsg[0]), Float.parseFloat(treatedMsg[1]));
			temperaturedata.save(td);
		}
		System.out.println("Saved new data: "+msg);
	}
}
