package project;

import java.util.List;

import coordinate.Coordinate;

import java.util.ArrayList;


public class ToPrint {
	// This class will be only used to print the best individuals in the terminal,
	//preserving only some information from each individual
	
	private int id; //ID of the individual
	private List<Coordinate> pospath = new ArrayList<>();//list of Coordinates
	private float number; //value of comfort/cost
	
	//Constructor
	ToPrint(int id, List<Coordinate> pospath, float number) {
		this.id = id;
		this.pospath = pospath;
		this.number = number;
	}

	@Override
	public String toString() {
		return "Path of the best fit individual: " + pospath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	//Compare the ID of two different objects of this class
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToPrint other = (ToPrint) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	//////////////////Getters e Setters//////////////////////////////

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Coordinate> getPospath() {
		return pospath;
	}

	public void setPospath(List<Coordinate> pospath) {
		this.pospath = pospath;
	}

	public float getNumber() {
		return number;
	}

	public void setNumber(float number) {
		this.number = number;
	}
	
	////////////////////////////////////
	
}
