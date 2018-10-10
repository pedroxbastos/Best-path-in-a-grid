package project;

import java.util.List;

import coordinate.Coordinate;
import coordinate.Edge;

import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

	private static int n,m; //dimension
    private static int cmax; //Maximum cost of an edge
	private static Coordinate pi, pf; //Initial and final points
	private static List<Edge> edges = new ArrayList<>();//list of special edges
	private static int nobs; //Number of obstacles
	private static Coordinate obs[]; //Vector of obstacles
	private static int tf; //final instant of simulation
	private static int k; //Comfort sensibility
	private ToPrint finish = null; //List of individuals (ID) that got to the final position
	private ToPrint history = null;//List of best comforts reached, by each individual (ID)

	//Constructor
	Grid(int n, int m, Coordinate pi, Coordinate pf, int tf, int nobs, int k) {
		Grid.n = n;
		Grid.m = m;
		Grid.pi = pi;
		Grid.pf = pf;
		Grid.tf = tf;
		Grid.obs = new Coordinate[nobs];
		Grid.nobs = nobs;
		Grid.k = k;
	}


	//Method to add a new obstacle to the vector
	void addObs(Coordinate p, int i) {
		if(i < Grid.nobs) {
			Grid.obs[i]=p;
		}
	}

	//Method to add a special edge to the list
	void AddSpecialEdge(Edge e) {
		Grid.edges.add(e);
	}

	//Method to "extract" the edges to the list, only with the initial and final points of the special zone
	void ExtractEdge(Edge e){
		boolean update = false; //Flag that says if the edge already exists in the list
		int columns = e.getPf().getX()-e.getPi().getX(); //Number of edges to add horizontally
		int rows = e.getPf().getY()-e.getPi().getY(); //Number of edges to add vertically
		
		if(rows>0) {
			//add edges with x = xf
			for(int i = 0; i < rows; i++) {
				Coordinate p0 = new Coordinate(e.getPf().getX(),e.getPi().getY()+i);
				Coordinate p1 = new Coordinate(e.getPf().getX(),e.getPi().getY()+i+1);
				Edge e1 = new Edge(p0,p1,e.getCost());
				//check if the edge already exists in the list
				for(int j = 0; j < this.getEdges().size(); j++) {
					//If yes, only the one with greater cost will stay
					if(e1.equals(this.getEdges().get(j)) && e1.getCost() > this.getEdges().get(j).getCost()) {
						this.getEdges().get(j).setCost(e1.getCost());
						update = true;
						break;
					}
				}
				//Add anyway if still doesn't exist
				if(update == false)
					this.AddSpecialEdge(e1);	
			}
		}
		//add edges with x = xi
		update = false;
		for(int i = 0; i < rows; i++) {
			Coordinate p0 = new Coordinate(e.getPi().getX(),e.getPi().getY()+i);
			Coordinate p1 = new Coordinate(e.getPi().getX(),e.getPi().getY()+i+1);
			Edge e1 = new Edge(p0,p1, e.getCost());
			for(int j = 0; j < this.getEdges().size(); j++) {
				if(e1.equals(this.getEdges().get(j)) && e1.getCost() > this.getEdges().get(j).getCost()) {
					this.getEdges().get(j).setCost(e1.getCost());
					update = true;
					break;
				}
			}
			if(update == false)
				this.AddSpecialEdge(e1);	
		}
		update = false;
		if(columns>0) {
			//add edges with y = yf
			for(int i = 0; i < columns; i++) {
				Coordinate p0 = new Coordinate(e.getPi().getX()+i,e.getPf().getY());
				Coordinate p1 = new Coordinate(e.getPi().getX()+i+1,e.getPf().getY());
				Edge e1 = new Edge(p0,p1, e.getCost());
				for(int j = 0; j < this.getEdges().size(); j++) {
					if(e1.equals(this.getEdges().get(j)) && e1.getCost() > this.getEdges().get(j).getCost()) {
						this.getEdges().get(j).setCost(e1.getCost());
						update = true;
						break;
					}
				}
				if(update == false)
					this.AddSpecialEdge(e1);	
			}
		}
		update = false;
		//add edges with y = yi
		for(int i = 0; i < columns; i++) {
			Coordinate p0 = new Coordinate(e.getPi().getX()+i,e.getPi().getY());
			Coordinate p1 = new Coordinate(e.getPi().getX()+i+1,e.getPi().getY());
			Edge e1 = new Edge(p0,p1, e.getCost());
			for(int j = 0; j < this.getEdges().size(); j++) {
				if(e1.equals(this.getEdges().get(j)) && e1.getCost() > this.getEdges().get(j).getCost()) {
					this.getEdges().get(j).setCost(e1.getCost());
					update = true;
					break;
				}
			}
			if(update == false)
				this.AddSpecialEdge(e1);	
		}

	}
	
	//Method that will dictate the individual to print in the next observation
	public ToPrint Result() {
		if(this.finish == null)
			return this.history;
		else 
			return this.finish;
	}


	@Override
	public String toString() {
		return "Grid [n=" + n + ", m=" + m + ", pi=" + pi + ", pf=" + pf + ", cmax=" + cmax + ", edges=" + edges
				+ ", nobs=" + nobs + ", obs=" + Arrays.toString(obs) + ", tf=" + tf + ", k=" + k + ", finish=" + finish
				+ ", history=" + history + "]";
	}

///// GETTERS e SETTERS /////////////////////////////////////
	
	int SetDist(Individual i) {
		int d = Math.abs(i.getPosfinal().getX()-i.getPosatual().getX())+Math.abs(i.getPosfinal().getY()-i.getPosatual().getY());
		return d;
	}
	
	void SetComfort(Individual i) {
		float p =(float)( 1 - (((i.getCost()) - (i.getLenght()) + 2.0)/((Grid.cmax-1)*(i.getLenght()) + 3.0)));
		float s =(float)( 1 - ((i.getDist())/((Grid.n)+(Grid.m)+1.0)));
		int k = (Grid.k);
		float comfort = (float)(Math.pow(p, k)*Math.pow(s, k));
		i.setComfort(comfort);
	}

	/* (non-Javadoc)
	 * @see project.Map#getN()
	 */
	public int getN() {
		return n;
	}


	/* (non-Javadoc)
	 * @see project.Map#setN(int)
	 */
	public void setN(int n) {
		Grid.n = n;
	}


	/* (non-Javadoc)
	 * @see project.Map#getM()
	 */
	public int getM() {
		return m;
	}


	/* (non-Javadoc)
	 * @see project.Map#setM(int)
	 */
	public void setM(int m) {
		Grid.m = m;
	}


	public int getCmax() {
		return cmax;
	}

	public void setCmax(int cmax) {
		Grid.cmax = cmax;
	}


	public Coordinate getPi() {
		return pi;
	}


	public void setPi(Coordinate pi) {
		Grid.pi = pi;
	}


	public Coordinate getPf() {
		return pf;
	}


	/* (non-Javadoc)
	 * @see project.Simulation#setPf(coordinate.Coordinate)
	 */
	public void setPf(Coordinate pf) {
		Grid.pf = pf;
	}


	public List<Edge> getEdges() {
		return edges;
	}


	public void setEdges(List<Edge> edges) {
		Grid.edges = edges;
	}


	public int getNobs() {
		return nobs;
	}


	public void setNobs(int nobs) {
		Grid.nobs = nobs;
	}


	public Coordinate[] getObs() {
		return obs;
	}

	public void setObs(Coordinate[] obs) {
		Grid.obs = obs;
	}


	public int getTf() {
		return tf;
	}


	public void setTf(int tf) {
		Grid.tf = tf;
	}


	public int getK() {
		return k;
	}


	public void setK(int k) {
		Grid.k = k;
	}


	public ToPrint getFinish() {
		return finish;
	}


	public void setFinish(ToPrint finish) {
		this.finish = finish;
	}


	public ToPrint getHistory() {
		return history;
	}


	public void setHistory(ToPrint history) {
		this.history = history;
	}
/////////////////////////////////////////////////////////////////



}

