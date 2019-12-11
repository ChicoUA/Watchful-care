package ies.projeto.watchful_care;

import lombok.*;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class healthData{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id2;
    private int patientId;
    private double heartBeat;
    private double longitude;
    private double latitude;
    private LocalDateTime datetime;

    public healthData(int patientId, double heartBeat, double latitude, double longitude) {
    	this.patientId = patientId;
        this.heartBeat=heartBeat;
        this.longitude=longitude;
        this.latitude=latitude;
        this.datetime= LocalDateTime.now();

    }
    

	public double getHeartBeat() {
		return heartBeat;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public healthData() {
    	this.datetime= LocalDateTime.now();
    }

	public LocalDateTime getDatetime() {
		return datetime;
	}
    
    

}
