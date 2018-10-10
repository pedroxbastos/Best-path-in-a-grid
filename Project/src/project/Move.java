package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import coordinate.Coordinate;
import pec.PEC;

class Move extends LifeEvent {

	//Constructor (attributes inherited from the classes that this event extends)
	Move(Individual i, float param, float tatual) {
		super(i,param,tatual);
		if(this.i == null) System.exit(-1);
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
	public void Time(Population p, Grid g, PEC list) {
		
		//auxiliar flags
		boolean l = true;
		double n=0;
		Coordinate vec[] = new Coordinate[4]; //vector of the possible positions to move
		
		//calculate n
		if(this.i.getPosatual().getY() > 1) {//check if the individual can go down
			//if yes, check if there's any obstacle in that position
			Coordinate pa = new Coordinate(this.i.getPosatual().getX(),this.i.getPosatual().getY()-1);
			for(int j=0; j < g.getNobs(); j++) {
				if(pa.equals(g.getObs()[j])) {
					l = false;
					break;
				}
			}
			//In case there no obstacle, add the position to the vector and increment n
			if (l == true) {
				vec[(int)n] = pa;
				n++;
			}
			
		}
		l=true;
		 //Similar process for the remaining 3 positions
		if(this.i.getPosatual().getX() < g.getN()) {
			
			Coordinate pa = new Coordinate(this.i.getPosatual().getX()+1,this.i.getPosatual().getY());
			for(int j=0; j < g.getNobs(); j++) {
				if(pa.equals(g.getObs()[j])) {
					l = false;
					break;
				}
			}
			
			if (l == true) {
				vec[(int)n] = pa;
				n++;
			}
			
		}
		l=true;
		
		if(this.i.getPosatual().getY() < g.getM()) {
			Coordinate pa = new Coordinate(this.i.getPosatual().getX(),this.i.getPosatual().getY()+1);
			for(int j=0; j < g.getNobs(); j++) {
				if(pa.equals(g.getObs()[j])) {
					l = false;
					break;
				}
			}
			
			if (l == true) {
				vec[(int)n] = pa;
				n++;
			}
			
		}
		l=true;
		
		if(this.i.getPosatual().getX() > 1){
			Coordinate pa = new Coordinate(this.i.getPosatual().getX()-1,this.i.getPosatual().getY());
			for(int j=0; j < g.getNobs(); j++) {
				if(pa.equals(g.getObs()[j])) {
					l = false;
					break;
				}
			}
			
			if (l == true) {
				vec[(int)n] = pa;
				n++;
			}
			
		}
		
		float r = random.nextFloat(); //generate a random number between 0 and 1
		double num = 0.0;
		boolean repete = false; //auxiliar flag to check if the position is new in the path
		// Calculate to which position the individual is going to move
		for(double i = 0.0; i<n; i++) {
			if(r <= ((i+1.0)/n)) {
				num = i;
				
				// Check if the individual has already been in that position, in case that's true,
				//remove the positions ahead in the path and "restarts" from that position
				double size = this.i.getPospath().size();
				int j = 0;
				//if the path is empty, there's no need to verify this
				if(size>1) {
					
					for(j=0; j < size ; j++) {
						if(vec[(int)num].equals(this.i.getPospath().get(j))) {
							// If the path only have 2 coordinates, it means that the individual is coming back
							//to the start
							if(size==2) {
								List<Coordinate> newlist = new ArrayList<>();
								newlist.add(g.getPi());
								int lenght = 0;
								int cost = 0;
								Individual ind = new Individual(g,this.i.getId(), g.getPi(), newlist, lenght, cost);
								ind.setDeath(this.i.getDeath());
								ind.setMove(this.i.getMove());
								ind.setReproduction(this.i.getReproduction());
								this.i = ind;
								repete = true;
							}							
							
							else if(size>2) {
								// this method will remove the positions ahead in the path, from the repeated
								//position. It will also update the variables of each individual
								this.i.RemoveAllpos(g, j);
								repete=true;
							}
							break; //if the individual already found an equal position in the path, it won't repeat
						}
					}
				}
				break;
			}
		}
		
		//Update the individual with the new position and consequently, modify the variables.
		//Only need to do this, if the move is to a position that isn't in the path traveled. 
		if(n>0 && repete == false) {
			this.i.Addpos(vec[(int)num]);
			this.i.setPosatual(vec[(int)num]);
			this.i.Updatevar(this.i.getPospath().get(this.i.getPospath().size()-2), this.i.getPospath().get(this.i.getPospath().size()-1) , g);
		}
		
		//Create a new Move event
		Move m = new Move(this.i,this.param,this.t);
		
			//	if the death time of this individual is sooner than the new move event, the list of events
			//won't be updated
		
		/*if(this.i.getDeath() == null && m.t < g.getTf()) {
			list.addEvent(m);
			this.i.setMove(m);
		}*/
		if(this.i.getDeath() != null) {
			if(this.i.getDeath().t > m.t) {
				list.addEvent(m);
				this.i.setMove(m);
			}
		}
		else if(this.i.getDeath() == null && g.getTf() > m.t) {
			list.addEvent(m);
			this.i.setMove(m);
		}

			
		//If the new comfort is greater than the previous greater, update the best comfort reached by an individual
		if(this.i.getComfort() > g.getHistory().getNumber()) {
			this.i.setBestc(this.i.getComfort());
			List<Coordinate> lc = new ArrayList<>(this.i.getPospath());
			float comfort = this.i.getComfort();
			ToPrint res = new ToPrint(this.i.getId(), lc, comfort);
			g.setHistory(res);
		}
		//If the new path cost is smaller than the previous best one, update the best cost individual
		if(this.i.getPosatual().equals(this.i.getPosfinal()) && (g.getFinish()==null || this.i.getCost() < g.getFinish().getNumber())) {
			List<Coordinate> lc = new ArrayList<>(this.i.getPospath());
			float cost = this.i.getCost();
			ToPrint res = new ToPrint(this.i.getId(), lc, cost);
			g.setFinish(res);
		}

	}

	@Override
	public String toString() {
		return "Move [t=" + t + ", i=" + i + ", param=" + param + ", mean=" + mean + "]\n";
	}
	
	

}
