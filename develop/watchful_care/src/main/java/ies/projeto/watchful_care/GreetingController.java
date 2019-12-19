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
	 public String Patient(@RequestParam(name="id") long patient_id, @RequestParam(name="bpm") long bpm_id, @RequestParam(name="temperature") long temp_id, @RequestParam(name="date") String date,Model model) {
		 int counter = 1;
		 LocalDateTime ldt = LocalDateTime.now();
		 healthData result = new healthData();
		 List<healthData> data = healthdata.findByPatientId((int)bpm_id);
		 List<String> tempos = new ArrayList<>();
		 List<Double> beats = new ArrayList<>();
		 double minBPM = 100000;
		 double maxBPM = 0;
		 double BPMsum = 0;
		 for(healthData hd : data) {
			 String hd_date = hd.getDatetime().toString().substring(0, 10);
			 if(hd_date.equals(date)) {
				tempos.add(hd.getDatetime().toString());
			 	beats.add(hd.getHeartBeat());
			 	BPMsum += hd.getHeartBeat();
			 	
			 	if(hd.getHeartBeat() < minBPM) {
					 minBPM = hd.getHeartBeat();
				 }
				 
				 if(hd.getHeartBeat() > maxBPM) {
					 maxBPM = hd.getHeartBeat();
				 }
			 	
			 }
		 }
		 
		 
		 LocalDateTime ldt2 = LocalDateTime.now();
		 temperatureData result2 = new temperatureData();
		 List<temperatureData> data2 = temperaturedata.findByPatientId((int)temp_id);
		 List<String> times = new ArrayList<>();
		 List<Float> temps = new ArrayList<>();
		 float minTemp = 1000000;
		 float maxTemp = 0;
		 float meanTemp = 0;
		 for(temperatureData td : data2) {
			 String td_date = td.getDatetime().toString().substring(0, 10);
			 if(td_date.contentEquals(date)) {
				 times.add(td.getDatetime().toString());
				 temps.add(td.getTemperature());
				 meanTemp += td.getTemperature();
				 
				 if(td.getTemperature() < minTemp) {
					 minTemp = td.getTemperature();
				 }
				 
				 if(td.getTemperature() > maxTemp) {
					 maxTemp = td.getTemperature();
				 }
				 
			 }
		 }
		 Patient p = patientRepository.findById(patient_id).orElseThrow();
		 model.addAttribute("name", p.getFirstName() + " " + p.getLastName());
		 model.addAttribute("date", date);
		 model.addAttribute("tempos", tempos);
		 model.addAttribute("bpms", beats);
		 model.addAttribute("times", times);
		 model.addAttribute("temps", temps);
		 model.addAttribute("temp_min", minTemp);
		 model.addAttribute("temp_max", maxTemp);
		 model.addAttribute("temp_mean", meanTemp / temps.size());
		 model.addAttribute("bpm_min", minBPM);
		 model.addAttribute("bpm_max", maxBPM);
		 model.addAttribute("bpm_mean", BPMsum / beats.size());

		 return "Patient";
	  }
	
	@GetMapping("/getInfo")
	public String patientinfo(@RequestParam(name="id") long patient_id, @RequestParam(name="bpm") long bpm_id, @RequestParam(name="temperature") long temp_id,Model model) {
		 int counter = 1;
		 LocalDateTime ldt = LocalDateTime.now();
		 healthData result = new healthData();
		 List<healthData> data = healthdata.findByPatientId((int)bpm_id);
		 Set<String> dates = new TreeSet<>();
		 for(healthData hd : data) {
			 String date = hd.getDatetime().toString();
			 date = date.substring(0, 10);
			 dates.add(date);
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
		 model.addAttribute("dates", dates);


		 return "patientinfo";
	  }
	
	
	@GetMapping("/getPatientByRoom")
	public String PatientRoom (@RequestParam(name="roomID") int RoomNumber, Model model) {
		
		long Target_patient_id = -1;
		
		List<Patient> patients = new ArrayList<>();
		patientRepository.findAll().iterator().forEachRemaining(patients::add);
		
		for (Patient p : patients){
			if (p.getRoomNumber() == RoomNumber){
				Target_patient_id = p.getPatientID();
			}
		}

		if (Target_patient_id != -1){
			Patient p = patientRepository.findById(Target_patient_id).orElseThrow();
			
			model.addAttribute("id", p.getPatientID());
			model.addAttribute("name", p.getFirstName() + " " + p.getLastName());
			model.addAttribute("age", p.getAge());
			model.addAttribute("bpm_id", p.getBpm_id());
			model.addAttribute("temp_id", p.getTemp_id());

			return "Patient";
		}
		else{
			return "EmptyRoomError";
		}
	
		

	}
	

	
	@GetMapping("/listAllPatients")
	  public String listPatients( Model model) {
		 
		 
		 model.addAttribute("patients", patientRepository.findAll());
		 
		 return "listPatients";
	  }

	@GetMapping("/addPatient")
	public String AddPatient (Model model){

		

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
