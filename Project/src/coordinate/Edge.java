package coordinate;

public class Edge {
	//An edge is a group of two coordinates with a cost associated
	
	private Coordinate pi, pf;
	private int cost;
	
	//Constructor
	public Edge(Coordinate pi, Coordinate pf, int cost) {
		this.pi = pi;
		this.pf = pf;
		this.cost = cost;
	}
	
	//Setters e Getters/////////////////

	public Coordinate getPi() {
		return pi;
	}

	public void setPi(Coordinate pi) {
		this.pi = pi;
	}

	public Coordinate getPf() {
		return pf;
	}

	public void setPf(Coordinate pf) {
		this.pf = pf;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

/////////////////////////////////////////////////

	@Override
	public String toString() {
		return "Edge [pi=" + pi + ", pf=" + pf + ", cost=" + cost + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pf == null) ? 0 : pf.hashCode());
		result = prime * result + ((pi == null) ? 0 : pi.hashCode());
		return result;
	}

	//Compare the Coordinates of two different edges
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (pf == null) {
			if (other.pf != null)
				return false;
		} else if (!pf.equals(other.pf))
			return false;
		if (pi == null) {
			if (other.pi != null)
				return false;
		} else if (!pi.equals(other.pi))
			return false;
		return true;
	}


}
