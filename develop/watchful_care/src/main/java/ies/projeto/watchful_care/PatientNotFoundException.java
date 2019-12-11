package ies.projeto.watchful_care;

public class PatientNotFoundException extends RuntimeException {

	PatientNotFoundException(int id) {
		super("Could not find Patient " + id);
	}
}