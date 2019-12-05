package ies.projeto.watchful_care;

import lombok.*;

import javax.persistence.*;
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


    public Patient(String firstName, String lastName, int age, healthData data){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;

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