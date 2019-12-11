package ies.projeto.watchful_care;

import lombok.*;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class supportData {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id2;
	private double heartBeat;
    private double longitude;
    private double latitude;
    private float temperature;
    private String firstName;
    private String lastName;
    private int age;
    
	public supportData(double heartBeat, double longitude, double latitude, float temperature, String firstName,
			String lastName, int age) {
		this.heartBeat = heartBeat;
		this.longitude = longitude;
		this.latitude = latitude;
		this.temperature = temperature;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
    
    
    

	
    
}
