package project;

import pec.PEC;


public class Data {
	//Class only used to aggregate the different types that will be used in a simulation (grid, population and pec)
	
	public static Grid g;
	public static Population people;
	public static PEC list;
	
	//Constructor
	/**
	 * @param g
	 * @param people
	 * @param liste
	 */
	public Data(Grid g, Population people, PEC liste) {
		Data.g = g;
		Data.people = people;
		Data.list = liste;
	}
	
	@Override
	public String toString() {
		return "Total [g=" + g + ", people=" + people + "]";
	}

}
