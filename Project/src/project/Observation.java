package project;

import pec.PEC;

class Observation extends Event{
	int num;
	//Event observation will only need a time as attribute
	Observation(Grid g, int count) {
		this.t = (float)((g.getTf()/20.0)*count);
		this.num = count;
	}


	@Override
	public String toString() {
		return "Observation [t=" + t + "]\n";
	}

	//Prints that will appear in the terminal
	@Override
	public void Time(Population p, Grid g, PEC list) {
		System.out.println("Observation number: "+num);
		System.out.println("Present instant: "+this.t);
		System.out.println("Number of realized events: "+list.getNum());
		System.out.println("Population size: "+p.natual);
		if(g.getFinish() != null) {
			System.out.println("Final point has been hit: yes");
			//ToPrint i = g.Result();
			System.out.println("Path of the best individual: "+g.getFinish().getPospath());
			System.out.println("Cost: "+g.getFinish().getNumber()+"\n");
		}
		else if (g.getHistory() != null) {
			System.out.println("Final point has been hit: no");
			//ToPrint i = g.Result();
			System.out.println("Path of the best individual "+ "with id: "+g.getHistory().getId()+" "+g.getHistory().getPospath());
			System.out.println("Comfort: "+g.getHistory().getNumber()+"\n");	
		}
		else
			System.out.println("Nobody moved");
	}
	
	
}
