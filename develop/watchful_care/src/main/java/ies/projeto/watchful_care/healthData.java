package ies.projeto.watchful_care;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class healthData{
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) long id;
    private @ManyToOne @JoinColumn(name="patient_id") Patient patient;
    private float temperature;
    private double heartBeat;
    private double longitude;
    private double latitude;
    private Date date;

    public healthData(float temperature, double heartBeat, double longitude, double latitude, Date date) {
        this.temperature=temperature;
        this.heartBeat=heartBeat;
        this.longitude=longitude;
        this.latitude=latitude;
        this.date=new Date();

    }
}
