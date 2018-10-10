package project;

import java.util.List;
import java.util.Random;

import pec.PEC;
import people.People;

import java.util.ArrayList;

public class Population implements People {
	List<Individual> individuals = new ArrayList<Individual>();//list of individuals
	private static int ni; //initial number of individuals
	private static int nmaxi; //Maximum number of individuals
	public int natual; //current number of individuals
	private int nid=0; //id to assign to an individual
	private static float dparam, rparam, mparam; //different parameters of the events
	
	
	static Random random = new Random();
	
	/* (non-Javadoc)
	 * @see project.Sample#toString()
	 */
	@Override
	public String toString() {
		return "Population [individuals=" + individuals + ", ni=" + ni + ", nmaxi=" + nmaxi + ", natual=" + natual
				+ ", nid=" + nid + ", dparam=" + dparam + ", rparam=" + rparam + ", mparam=" + mparam + "]";
	}

	//Constructor
	Population(int ni, int nmaxi, int dparam, int rparam, int mparam) {
		Population.ni = ni;
		Population.nmaxi = nmaxi;
		this.natual = 0;
		Population.dparam = dparam;
		Population.rparam = rparam;
		Population.mparam = mparam;
	}

	//Remove an individual of the current list of individuals
	@Override
	public void RemIndiv(int index) {
		this.individuals.remove(index);
		this.natual--;
	}
	
	@Override
	public void AddIndiv(Individual i) {
		this.individuals.add(i);
	}

	//Add an individual to the list of individuals
	void AddIndividual(Individual i, float t, PEC list, Grid g) {
		
		//Check if the maximum number of individuals has been reached
		if(this.natual+1<=nmaxi) {
			ToPrint res = new ToPrint(i.getId(),i.getPospath(),i.getComfort());
			//Add a new individual (ID) to the best comforts list
			if(g.getHistory() == null)
				g.setHistory(res);
			if(res.getNumber() > g.getHistory().getNumber())
				g.setHistory(res);
			AddIndiv(i);
			this.natual++;
			this.nid++;
			
			// Create 3 new events for the new individual
			Death d = new Death(i,Population.dparam,t);
		
			//	if the time of new death event is greater than the final time of simulation, the list of events
			//won't be updated
			if(d.t < g.getTf()) {
				i.setDeath(d);
				list.addEvent(d);
				i.setDeathT(d.t);
			}
			Reproduction r = new Reproduction(i,Population.rparam,t);
			Move m = new Move(i,Population.mparam,t);
			//	if the time of new move and reproduction events is greater than the time of the death event,
			//the list of events won't be updated
			if(i.getDeath() != null && (d.t > r.t) ) {
				list.addEvent(r);
				i.setReproduction(r);
			}
			else if(r.t < g.getTf()) {
				list.addEvent(r);
				i.setReproduction(r);
			}

			if(i.getDeath() != null && (d.t > m.t) ) {
				list.addEvent(m);
				i.setMove(m);
			}
			else if(m.t < g.getTf()) {
				list.addEvent(m);
				i.setMove(m);
			}
		}
		else {//Epidemics happens in case the number of individuals in a population has been reached
			Epidemic(list);
		}
	}
	
	/* (non-Javadoc)
	 * @see project.Sample#Epidemic(pec.PEC)
	 */
	public void Epidemic(PEC p) {
		int id[] = new int[]{-1,-1,-1,-1,-1}; //best 5 iDs
		float c[] = new float[]{-1,-1,-1,-1,-1}; //best 5 comforts
		boolean f[] = new boolean[]{false, false, false, false, false}; //check if the index of id[] was already updated
		int count=0;
		
		//check which are the individuals with greater comfort and save them from "death"
		for(int i = 0; i < this.individuals.size(); i++) {
			Individual l = this.individuals.get(i);
			if(l.getComfort()>c[0] && (count > 4 || f[0] == false)) {
				c[0]= l.getComfort();
				id[0]=l.getId();
				f[0]=true;
				count++;
			}else if(l.getComfort()>c[1] && (count > 4 || f[1] == false)) {
				c[1]= l.getComfort();
				id[1]=l.getId();
				f[1]=true;
				count++;
			}else if(l.getComfort()>c[2] && (count > 4 || f[2] == false)) {
				c[2]= l.getComfort();
				id[2]=l.getId();
				f[2]=true;
				count++;
			}else if(l.getComfort()>c[3] && (count > 4 || f[3] == false)) {
				c[3]= l.getComfort();
				id[3]=l.getId();
				f[3]=true;
				count++;
			}else if(l.getComfort()>c[4] && (count > 4 || f[4] == false)) {
				c[4]= l.getComfort();
				id[4]=l.getId();
				f[4]=true;
				count++;
			}				
		}

		// Generate a random number, if an individual has a smaller comfort number, eliminate
	    for(int i = this.individuals.size()-1; i >0 ;i--) {
			Individual l = this.individuals.get(i);
			double r = random.nextFloat();
			if((l.getId() == id[0] || l.getId() == id[1] || l.getId() == id[2] || l.getId() == id[3] || l.getId() == id[4])) {
				continue;
			}
			else {
				if (r>this.individuals.get(i).getComfort()) {
					p.RemEvent(l.getDeath());
					p.RemEvent(l.getMove());
					p.RemEvent(l.getReproduction());
					RemIndiv(i);
				}
			}
	    }
	}

			

	///////////////////////Getters e Setters/////////////////////////
	

	@Override
	public int getNi() {
		return ni;
	}


	@Override
	public void setNi(int ni) {
		Population.ni = ni;
	}


	@Override
	public int getNmaxi() {
		return nmaxi;
	}


	@Override
	public void setNmaxi(int nmaxi) {
		Population.nmaxi = nmaxi;
	}


	public float getDparam() {
		return dparam;
	}


	public void setDparam(float dparam) {
		Population.dparam = dparam;
	}


	public float getRparam() {
		return rparam;
	}

	
	public void setRparam(float rparam) {
		Population.rparam = rparam;
	}


	public float getMparam() {
		return mparam;
	}


	public void setMparam(float mparam) {
		Population.mparam = mparam;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}
	
	
	
	
	
	/////////////////////////////////////
	


}
