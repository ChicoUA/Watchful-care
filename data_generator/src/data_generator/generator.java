package data_generator;

import java.io.Serializable;

public class generator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int identifier;
	private data dt;
	private boolean temperature;
	private int beat;
	
	@Override
	public String toString() {
		if (this.temperature) {
			return "" + identifier + "/" + dt.getTemp() + "/" + dt.getBattery();
		}
		else
			return "" + identifier + "/" + dt.getBpm() + "/" + dt.getLatitude() + "/" + dt.getLongitude() + "/" + dt.getBattery();
	}

	public generator(String temp, int identifier) {
		this.identifier = identifier;
		this.dt = new data(100.0);
		if (temp.equals("bpm")) {
			this.temperature = false;
			this.beat = 5000;
		}
		else {
			this.temperature = true;
			this.beat = 10000;
		}
	}
	
	public generator(String temp, int identifier, String alert) {
		this.identifier = identifier;
		this.dt = new data(alert);
		if (temp.equals("bpm")) {
			this.temperature = false;
			this.beat = 5000;
		}
		else {
			this.temperature = true;
			this.beat = 10000;
		}
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public data getDt() {
		return dt;
	}

	public void setDt(data dt) {
		this.dt = dt;
	}
	
	public void generateNewData(double battery) {
		this.dt = new data(battery);
	}

	public int getBeat() {
		return beat;
	}

	public void setBeat(int beat) {
		this.beat = beat;
	}
	
	
}
