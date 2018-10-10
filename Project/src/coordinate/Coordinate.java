package coordinate;

public class Coordinate {
	private int x, y; //Coordinates of a point

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Setters e Getters/////////////////
	
	/**
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param 
	 */
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

/////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	//Compare 2 different Coordinates
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + "]";
	}
	
	

}
