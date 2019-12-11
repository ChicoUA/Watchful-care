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
	
	 @GetMapping("/PatientInfo")
	  public String greeting(@RequestParam(name="id") long patient_id, Model model) {
		//try{
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
		//}
		/** 
		catch (MyResourceNotFoundException exc) {
			throw new ResponseStatusException(
			  HttpStatus.NOT_FOUND, "Patient Not Found", exc);
	   }
	    **/
	  }
	 
	 @GetMapping("/listAllPatients")
	  public String listPatients( Model model) {
		 
		 
		 model.addAttribute("patients", patientRepository.findAll());
		 
		 return "listPatients";
	  }
	 /**
	 @GetMapping("/addPatient")
	 public String greeting(@RequestParam(name="id") long patient_id, Model model) {
		 
	 }

	 
	 **/

	@GetMapping("/addPatient")
	public String AddPatient (Model model){

		// instanciar a patient class
		// return patientRepository.save(patient);

		//Patient patient = new Patient("John","Cena",37,1,23);

		Patient_DataStore pac_store = new Patient_DataStore();

		
		pac_store.addPatient(new Patient());
			
		

		model.addAttribute("form", pac_store);		

		return "save_pacient";
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
