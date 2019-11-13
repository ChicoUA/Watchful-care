package data_generator;

import java.io.Serializable;

public class generator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int id = 0;
	private int identifier;
	private data dt;
	
	@Override
	public String toString() {
		return "generator [identifier=" + identifier + ", dt=" + dt + "]";
	}

	public generator() {
		this.identifier = id++;
		this.dt = new data();
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
	
	public void generateNewData() {
		this.dt = new data();
	}
	
	
}
