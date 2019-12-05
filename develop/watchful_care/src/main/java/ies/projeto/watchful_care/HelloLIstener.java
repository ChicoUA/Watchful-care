package ies.projeto.watchful_care;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(HelloBinding.class)
public class HelloLIstener {
	@Autowired
	private healthDataRepository healthdata;
	
	@StreamListener(target = HelloBinding.GREETING)
    public void processHelloChannelGreeting(String msg) {
		String[] treatedMsg = msg.split("/");
		healthData hd = new healthData(Integer.parseInt(treatedMsg[0]), Float.parseFloat(treatedMsg[1]), Double.parseDouble(treatedMsg[2]), Double.parseDouble(treatedMsg[4]), Double.parseDouble(treatedMsg[3]));
		healthdata.save(hd);
		System.out.println("Saved new data: "+msg);
	}
}
