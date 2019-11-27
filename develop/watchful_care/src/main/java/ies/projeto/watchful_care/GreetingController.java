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
	
	 @GetMapping("/greeting")
	  public String greeting(@RequestParam(name="id") long patient_id, Model model) {
		 int counter = 1;
		 LocalDateTime ldt = LocalDateTime.now();
		 healthData result = new healthData();
		 List<healthData> data = healthdata.findByPatientId((int)patient_id);
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
		 Patient p = patientRepository.findById(patient_id).orElseThrow();
		 
		 model.addAttribute("id", patient_id);
		 model.addAttribute("name", p.getFirstName() + " " + p.getLastName());
		 model.addAttribute("age", p.getAge());
		 model.addAttribute("temperature", result.getTemperature());
		 model.addAttribute("bpm", result.getHeartBeat());
		 model.addAttribute("latitude", result.getLatitude());
		 model.addAttribute("longitude", result.getLongitude());
		 return "greeting";
	  }
}
