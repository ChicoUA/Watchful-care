package ies.projeto.watchful_care;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class emergency {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id2;
	private int patientId;
	private String message;
	private LocalDateTime datetime;
	
	public emergency(int patientId, String message) {
		this.patientId = patientId;
		this.message = message;
		this.datetime = LocalDateTime.now();
	}
	
	public emergency() {
		this.datetime = LocalDateTime.now();
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	
	
}
