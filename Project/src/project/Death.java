package project;

import java.util.Random;

import pec.PEC;

class Death extends LifeEvent {

	//Constructor
	Death(Individual i, float param, float tatual) {
		super(i,param,tatual);
	}
	
	//Process (declaration and methods) to calculate the time (Stochastic process)
	static Random random = new Random();
	
	protected float SetMean(){
		float parame = this.param;
		float m = (float) (1.0 - Math.log(1.0-this.i.getComfort()))*parame;
		return m;
	}
	
	protected float expRandom() {
	float next = (float) random.nextDouble();
	float r =(float)( -this.mean*Math.log(1.0-next));
	return r;
	}
	
	//Method that will execute this type of event
	@Override
	public void Time(Population p, Grid g, PEC pec) {
		for(int j=0; j < p.individuals.size(); j++) {
			if(this.i.equals(p.individuals.get(j))) {
				p.RemIndiv(j);
				break;
			}
		}
	}
		
	@Override
	public String toString() {
		return "Death [t=" + t + ", i=" + i + ", param=" + param + ", mean=" + mean + "]\n";
	}


}
