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

    private @Id @GeneratedValue(strategy = GenerationType.AUTO) long id;
    private String firstName;
    private String lastName;
    private int age;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<healthData> data;

    public Patient(){}

    public Patient(String firstName, String lastName, int age, healthData data){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.data = Stream.of(data).collect(Collectors.toSet());
        this.data.forEach(x -> x.setPatient(this));
    }



}