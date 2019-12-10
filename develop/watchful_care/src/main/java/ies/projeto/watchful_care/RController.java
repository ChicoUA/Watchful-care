package ies.projeto.watchful_care;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/add")
public class RController {
	@Autowired
    private PatientRepository patientRepository;
	
	@Autowired
	private healthDataRepository healthdata;
	
	@Autowired
	private temperatureDataRepository temperaturedata;

	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getPatients(){
    	List<Patient> employee = patientRepository.findAll();
    	return ResponseEntity.ok().body(employee);
    }
	
	@GetMapping("/data")
	public ResponseEntity<List<healthData>> getdata(@RequestParam(name = "id") int patient_id) {
    	List<healthData> data = healthdata.findByPatientId(patient_id);
    	return ResponseEntity.ok().body(data);
	}
	
	@GetMapping("/lattestdata")
	public ResponseEntity<healthData> getlattestData(@RequestParam(name = "id") int patient_id) {
		int counter = 1;
		LocalDateTime ldt = LocalDateTime.now();
		healthData result = new healthData();
    	List<healthData> data = healthdata.findByPatientId(patient_id);
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
    	return ResponseEntity.ok().body(result);
	}
	
	
	@PostMapping("/patients")
	public Patient createPatient(@Valid @RequestBody Patient patient) {
	    return patientRepository.save(patient);
	}
	
	@PostMapping("/bpm_data")
	public healthData createData(@Valid @RequestBody healthData hd) {
	    return healthdata.save(hd);
	}
	
	@PostMapping("/temp_data")
	public temperatureData createTempData(@Valid @RequestBody temperatureData td) {
		return temperaturedata.save(td);
	}

}
