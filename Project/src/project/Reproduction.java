package project;

import java.util.List;

import java.util.Random;

import coordinate.Coordinate;
import pec.PEC;

import java.util.ArrayList;

class Reproduction extends LifeEvent {

	//Constructor
	/**
	 * Constructor to inicialize a type Reproduction
	 * @param i
	 * @param param
	 * @param tatual
	 */
	Reproduction(Individual i, float param, float tatual) {
		super(i,param,tatual);
	}

	//Process (declaration and methods) to calculate the time (Stochastic process)
	static Random random = new Random();
	
	float expRandom() {
	float next =(float) random.nextDouble();
	float r =(float)( -this.mean*Math.log(1.0-next));
	return r;
	}
	
	float SetMean() {
		float parame = this.param;
		float m = (float) (1.0 - Math.log(this.i.getComfort()))*parame;
		return m;
	}

	//Method that will execute this type of event
	@Override
	public void Time(Population p, Grid g, PEC pec) {
		
		int lenght = 0;
		int cost = 0;
		Coordinate pa;
		double size=0;
		int aux=0;
		Individual ind;
		size = this.i.getPospath().size();
		if (size > 1) {
			//copy the path traveled and the other variables, to the son
			List<Coordinate> pospathe = new ArrayList<>(this.i.getPospath());
			lenght = this.i.getLenght();
			cost = this.i.getCost();
			pa = this.i.getPosatual();
			ind = new Individual(g,p.getNid(), pa , pospathe, lenght, cost);
			aux = (int)(Math.round(0.9*size)) + (int)(Math.round(0.1*size)*this.i.getComfort());
			//in case the number calculated is smaller than the size of dad's list
			if(aux < size) {
				//	Remove from the path, the number of positions needed and update the variables (cost, lenght,
				//comfort, dist)
				ind.RemoveAllpos(g, aux);
			}
			//Add the individual to the list of individuals
			p.AddIndividual(ind, this.t, pec, g);
		}
		else {//In case the "father" hasn't move yet, the son created will be a copy (with different ID)
			List<Coordinate> pospathe = new ArrayList<>();
			pospathe.add(g.getPi());
			//System.out.println("posiçao inicial: "+g.pi+" lista de passados: "+pospathe);
			ind = new Individual(g,p.getNid(), g.getPi(), pospathe, lenght, cost);
			p.AddIndividual(ind,this.t,pec, g);
		}
		//Create a new event reproduction
		Reproduction r = new Reproduction(this.i,this.param,this.t);
		
		//	if the death time of this individual is sooner than the new reproduction event, the list of events
		//won't be updated
		if(this.i.getDeath() != null) {
			if(this.i.getDeath().t > r.t) {
				pec.addEvent(r);
				this.i.setReproduction(r);
			}
		}
		else if(this.i.getDeath() == null && g.getTf() > r.t) {
			pec.addEvent(r);
			this.i.setReproduction(r);
		}

	}

	@Override
	public String toString() {
		return "Reproduction [t=" + t + ", i=" + i + ", param=" + param + ", mean=" + mean + "]\n";
	}

}
