package ies.projeto.watchful_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WatchfulCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchfulCareApplication.class, args);
	}

}
