package ies.projeto.watchful_care;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "data")
@Entity
public class Patient {

    private @Id @GeneratedValue long id;
    private String firstName;
    private String lastName;
    private int age;
    private int bpm_id;
    private int temp_id;
    private LocalDateTime datetime;


    public Patient(String firstName, String lastName, int age, int bpm_id, int temp_id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.bpm_id = bpm_id;
        this.temp_id = temp_id;
        this.datetime = LocalDateTime.now();

    }
    
    public Patient() {
    	
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}


}