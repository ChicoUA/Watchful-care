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
	public ResponseEntity<supportData> getlattestData(@RequestParam(name="id") long patient_id, @RequestParam(name="bpm") long bpm_id, @RequestParam(name="temperature") long temp_id) {
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
		 
		supportData supportdata = new supportData(result.getHeartBeat(), result.getLongitude(), result.getLatitude(),
				result2.getTemperature(), p.getFirstName(), p.getLastName(), p.getAge());
		
    	return ResponseEntity.ok().body(supportdata);
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
