package main;

import project.Grid;
import project.Population;
import project.ReadXML;
import project.Data;
import pec.PEC;
import project.Event;
import project.ToPrint;

public class Main {

	public static void main(String[] args) {
		
		  //Read the input file and save the data
		  ReadXML.readXML(args[0]);
		  
		  //Separate the date in the different types of objects
		  Grid g = Data.g;
		  Population p = Data.people;
		  PEC pec = Data.list;
		  
		  // Auxiliar to pass through the list of events
		  Event e;
		  
		  // Pass through the list of events and execute them until the final one (last observation/population
		  //without individuals)
		  while(true) {
			    e=pec.Events(pec);
		  		if(e==null)
		  			break;//no more events
			  	if(p.natual == 0)
			  		break;//no more individuals in the population
				e.Time(p, g, pec);//execute the event
				pec.incrementnumEvents();//increment the number of events realized
				pec.RemEvent(e);//remove the event from the list of events
		  }
		  //get the best individual in the entire simulation and print the path
		  ToPrint best = g.Result();
		  System.out.println(best);
	}

}
