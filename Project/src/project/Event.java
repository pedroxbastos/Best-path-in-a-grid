package project;

import pec.PEC;

public abstract class Event {
	
	
	protected float t; //Time of the Event
	
	//Declaration of the abstract method that will execute the type of event
	public abstract void Time(Population p, Grid g, PEC list);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(t);
		return result;
	}
	
	//Compare the time of 2 different events
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (Float.floatToIntBits(t) != Float.floatToIntBits(other.t))
			return false;
		return true;
	}

	/////////Getters e Setters //////////////////////////
	public float getT() {
		return t;
	}

	public void setT(float t) {
		this.t = t;
	}
	/////////////////////////////////////////////////////
	

}
