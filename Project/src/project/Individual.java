package project;

import java.util.ArrayList;
import java.util.List;

import coordinate.Coordinate;
import coordinate.Edge;

public class Individual {
	private int id;
	private int cost; //Actual cost of the path
	private int lenght; //Lenght of the path
	private int dist; //Distance between the current point and the final point in the grid
	private float comfort; // Comfort of the individual
	private Coordinate posatual; // current position of the individual
	private Coordinate posfinal; //final position to reach in the grid
	private List<Coordinate> pospath = new ArrayList<>();//List of the current path of the individual
	private double bestc; // Best comfort reached
	private float deathT; // Time of the death
	private Death death; // Event Death associated to the individual
	private Move move; // Event Move associated to the individual
	private Reproduction reproduction; // Event Reproduction associated to the individual


	@Override
	public String toString() {
		return "Individual [id=" + id + ", cost=" + cost + ", lenght=" + lenght + ", dist=" + dist + ", comfort="
				+ comfort + ", posatual=" + posatual + ", posfinal=" + posfinal + ", pospath=" + pospath + ", bestc="
				+ bestc + "]";
	}
	
	//Constructor
	Individual(Grid g, int id, Coordinate pi, List<Coordinate> lc, int lenght, int cost ) {
		this.pospath = lc;
		this.id = id;
		this.posatual = pi;
		this.posfinal = g.getPf();
		this.lenght = lenght;
		this.cost = cost;
		this.dist = g.SetDist(this);
		g.SetComfort(this);
		this.bestc = this.comfort;
	}	
	
	//Add a position to the current path
	void Addpos(Coordinate p) {
		this.pospath.add(p);
	}
	
	// Method to update the different variables of the individual (cost, lenght, dist and comfort)
	void Updatevar(Coordinate pi, Coordinate pf, Grid g) {
		boolean flag=false;
		
		
		Edge e1 = new Edge(pi,pf,0);
		Edge e2 = new Edge(pf,pi,0);
		
		//Increment the cost
		for(int i=0; i < g.getEdges().size(); i++) {
			if((g.getEdges().get(i)).equals(e1) || g.getEdges().get(i).equals(e2)) {
				this.cost=this.cost+g.getEdges().get(i).getCost();
				flag=true;
				break;
			}
		}
		if(flag==false)
			this.cost++;
		
		//new lenght, dist and comfort
		this.lenght=this.pospath.size()-1;
		g.SetDist(this);
		g.SetComfort(this);
		
	}
	
	//Method to remove positions from the current path: - In case the individual passes again in a position
	//													- To reduce the list of a father and pass it to a son
	void RemoveAllpos(Grid g, int index) {
		//Updating the cost
		for(int i = index; i<this.pospath.size()-1 ; i++) {
			Coordinate pi = this.pospath.get(i);
			Coordinate pf = this.pospath.get(i+1);
			boolean Specialzone = false;
			Edge e1 = new Edge(pi,pf,0);
			Edge e2 = new Edge(pf,pi,0);
			for(int j=0; j < g.getEdges().size(); j++) {
				if((g.getEdges().get(j)).equals(e1) || g.getEdges().get(j).equals(e2)) {
					this.cost=this.cost-g.getEdges().get(j).getCost();
					Specialzone=true;
					break;
				}
			}
			if(Specialzone==false)
				this.cost--;
		}
		
		//Remove the positions since the point index until the end of list
		if((index+1)==(this.pospath.size()-1))
			this.pospath.remove(this.pospath.size()-1);
		else
			this.pospath.subList(index+1, this.pospath.size()).clear();
		
		//update the lenght, current position, dist and comfort
		this.lenght = this.pospath.size()-1;
		this.posatual=this.pospath.get(this.pospath.size()-1);
		g.SetDist(this);
		g.SetComfort(this);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	//Compare the ID of two different individuals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individual other = (Individual) obj;
		if (id != other.id)
			return false;
		return true;
	}


///////////////////////// Getters e Setters /////////////////////////
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public float getComfort() {
		return comfort;
	}

	public void setComfort(float comfort) {
		this.comfort = comfort;
	}

	public Coordinate getPosatual() {
		return posatual;
	}

	public void setPosatual(Coordinate posatual) {
		this.posatual = posatual;
	}

	public Coordinate getPosfinal() {
		return posfinal;
	}

	public void setPosfinal(Coordinate posfinal) {
		this.posfinal = posfinal;
	}

	public List<Coordinate> getPospath() {
		return pospath;
	}

	public void setPospath(List<Coordinate> pospath) {
		this.pospath = pospath;
	}

	public double getBestc() {
		return bestc;
	}

	public void setBestc(double bestc) {
		this.bestc = bestc;
	}

	public float getDeathT() {
		return deathT;
	}

	public void setDeathT(float deathT) {
		this.deathT = deathT;
	}

	public Death getDeath() {
		return death;
	}

	public void setDeath(Death death) {
		this.death = death;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Reproduction getReproduction() {
		return reproduction;
	}

	public void setReproduction(Reproduction reproduction) {
		this.reproduction = reproduction;
	}


	///////////////////////////////////////////////////////

	
}

