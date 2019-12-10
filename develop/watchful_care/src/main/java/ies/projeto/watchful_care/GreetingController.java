package ies.projeto.watchful_care;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
	@Autowired
    private PatientRepository patientRepository;
	
	@Autowired
	private healthDataRepository healthdata;
	
	@Autowired
	private temperatureDataRepository temperaturedata;
	
	 @GetMapping("/greeting")
	  public String greeting(@RequestParam(name="id") long patient_id, @RequestParam(name="bpm") long bpm_id, @RequestParam(name="temperature") long temp_id, Model model) {
		 int counter = 1;
		 LocalDateTime ldt = LocalDateTime.now();
		 healthData result = new healthData();
		 List<healthData> data = healthdata.findByPatientId((int)bpm_id);
		 for(healthData hd : data) {
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
		 for(temperatureData td : data2) {
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
		 model.addAttribute("temperature", result2.getTemperature());
		 model.addAttribute("bpm", result.getHeartBeat());
		 model.addAttribute("latitude", result.getLatitude());
		 model.addAttribute("longitude", result.getLongitude());
		 return "greeting";
	  }
}
