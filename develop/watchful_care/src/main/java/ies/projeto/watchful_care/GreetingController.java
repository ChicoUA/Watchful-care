package ies.projeto.watchful_care;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
public class GreetingController {
	@Autowired
    private PatientRepository patientRepository;
	
	@Autowired
	private healthDataRepository healthdata;
	
	@Autowired
	private temperatureDataRepository temperaturedata;
	
	@GetMapping("/PatientInfo")
	 public String Patient(@RequestParam(name="id") long patient_id, @RequestParam(name="bpm") long bpm_id, @RequestParam(name="temperature") long temp_id,Model model) {
		 int counter = 1;
		 LocalDateTime ldt = LocalDateTime.now();
		 healthData result = new healthData();
		 List<healthData> data = healthdata.findByPatientId((int)bpm_id);
		 List<String> tempos = new ArrayList<>();
		 List<Double> beats = new ArrayList<>();
		 for(healthData hd : data) {
			 tempos.add(hd.getDatetime().toString());
			 beats.add(hd.getHeartBeat());
			 if(counter == 1) {
				 ldt = hd.getDatetime();
				 result = hd;
			 }
			 else {
				 if(ldt.isBefore(hd.getDatetime())) {
					 ldt = hd.getDatetime();
					 result = hd;
				 }
			 }	
		 }
		 
		 
		 LocalDateTime ldt2 = LocalDateTime.now();
		 temperatureData result2 = new temperatureData();
		 List<temperatureData> data2 = temperaturedata.findByPatientId((int)temp_id);
		 List<String> times = new ArrayList<>();
		 List<Float> temps = new ArrayList<>();
		 for(temperatureData td : data2) {
			 times.add(td.getDatetime().toString());
			 temps.add(td.getTemperature());
			 if(counter == 1) {
				 ldt2 = td.getDatetime();
				 result2 = td;
			 }
			 else {
				 if(ldt.isBefore(td.getDatetime())) {
					 ldt = td.getDatetime();
					 result2 = td;
				 }
			 }	
		 }
		 Patient p = patientRepository.findById(patient_id).orElseThrow();
		 model.addAttribute("id", patient_id);
		 model.addAttribute("name", p.getFirstName() + " " + p.getLastName());
		 model.addAttribute("age", p.getAge());
		 model.addAttribute("bpm_id", p.getBpm_id());
		 model.addAttribute("temp_id", p.getTemp_id());
		 model.addAttribute("bpm", result.getHeartBeat());
		 model.addAttribute("temp", result2.getTemperature());
		 model.addAttribute("latitude", result.getLatitude());
		 model.addAttribute("longitude", result.getLongitude());
		 model.addAttribute("tempos", tempos);
		 model.addAttribute("bpms", beats);
		 model.addAttribute("times", times);
		 model.addAttribute("temps", temps);

		 return "Patient";
	  }

	@GetMapping("/listAllPatients")
	  public String listPatients( Model model) {
		 
		 
		 model.addAttribute("patients", patientRepository.findAll());
		 
		 return "listPatients";
	  }

	@GetMapping("/addPatient")
	public String AddPatient (Model model){

		// instanciar a patient class
		// return patientRepository.save(patient);

		//Patient patient = new Patient("John","Cena",37,1,23);

		Patient_DataStore pac_store = new Patient_DataStore();

		
		pac_store.addPatient(new Patient());
			
		

		model.addAttribute("form", pac_store);		

		return "save_patient";
	}	


	@PostMapping ("/addPatient/save")
	public String savePatients(@ModelAttribute Patient_DataStore form, Model model) {
		
		for (Patient p : form.getPatients()){
			patientRepository.save(p);
		}
		
    	model.addAttribute("patients", patientRepository.findAll());
    	return "listPatients";	
	}


	@GetMapping("/editPatient")
	public String showEditForm(Model model) {
		List<Patient> patients = new ArrayList<>();
		patientRepository.findAll().iterator().forEachRemaining(patients::add);
	
		model.addAttribute("form", new Patient_DataStore(patients));
		return "editPatients";
	}


	// deleting function missing
	@GetMapping("/DeletePatient")
	public String DeletePatient( @RequestParam(name="id") long patient_id, Model model) {
   		patientRepository.deleteById(patient_id);
		//assertThat(patientRepository.count()).isEqualTo(1);
		model.addAttribute("patients", patientRepository.findAll());
		return "listPatients";
		
		
    }
 
    @GetMapping("/DeleteAllPatients")
    public String deleteAllPatients(Model model) {
        patientRepository.deleteAll();
		//assertThat(patientRepository.count()).isEqualTo(0);
		model.addAttribute("patients", patientRepository.findAll());
		return "listPatients";
    }



	 

}
