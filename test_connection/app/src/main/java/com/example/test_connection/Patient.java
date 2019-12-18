package com.example.test_connection;

public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private int bpm_id;
    private int temp_id;
    private int id;

    public Patient(String firstName, String lastName, int age, int bpm_id, int temp_id, int id){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.bpm_id = bpm_id;
        this.temp_id = temp_id;
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

    public int getBpm_id() {
        return bpm_id;
    }

    public int getTemp_id() {
        return temp_id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", bpm_id=" + bpm_id +
                ", temp_id=" + temp_id +
                ", id=" + id +
                '}';
    }
}
