package ies.projeto.watchful_care;

import lombok.*;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class temperatureData {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id2;
    private int patientId;
    private float temperature;
    private LocalDateTime datetime;

    public temperatureData(int patientId, float temperature) {
    	this.patientId = patientId;
        this.temperature=temperature;
        this.datetime= LocalDateTime.now();

    }
    
    public temperatureData() {
        this.datetime= LocalDateTime.now();
    }
    
    public float getTemperature() {
		return temperature;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}
}
