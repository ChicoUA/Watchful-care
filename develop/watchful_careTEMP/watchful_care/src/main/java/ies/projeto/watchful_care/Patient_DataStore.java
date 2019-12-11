package ies.projeto.watchful_care;

import java.util.*;

public class Patient_DataStore{
    List <Patient> patients;
    
    // default and parameterized constructor

    public Patient_DataStore(){
        this.patients = new ArrayList <Patient>();
    }

    public Patient_DataStore(List<Patient> patients){
        this.patients = patients;
    }


    public void addPatient(Patient p) {
        this.patients.add(p);
    }
    
    public List<Patient> getPatients(){
        return patients;
    }

    
    
   

}