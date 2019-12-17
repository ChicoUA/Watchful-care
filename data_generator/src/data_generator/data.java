package data_generator;
import java.io.Serializable;
import java.util.Random;


public class data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double temp;
	private int bpm;
	private double latitude;
	private double longitude;
	private static Random r = new Random();
	private double battery;
	
	protected data(double battery) {
		this.temp = 36 + (38 - 36) * r.nextDouble();
		this.bpm = r.nextInt(53 + 1) + 75; // [75 - 128]
		this.latitude = 75 + (77 - 75) * r.nextDouble();
		this.longitude = -113 + (-115 +113) * r.nextDouble();
		this.battery = battery;
	}
	
	protected data(String alert) {
		if(alert.equals("bpm")) {
			this.bpm = 200;
		}
		else {
			this.bpm = r.nextInt(53 + 1) + 75; // [75 - 128]
		}
		
		if(alert.equals("temperature")) {
			this.temp = 40;
		}
		else {
			this.temp = 36 + (38 - 36) * r.nextDouble();
		}
		this.latitude = 75 + (77 - 75) * r.nextDouble();
		this.longitude = -113 + (-115 +113) * r.nextDouble();
		this.battery = 100.0;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "/" + temp + "/" + bpm + "/" + latitude + "/" + longitude;
	}

	public double getBattery() {
		return battery;
	}

	public void setBattery(double battery) {
		this.battery = battery;
	}
	
	

	
	
	
	
	
}
