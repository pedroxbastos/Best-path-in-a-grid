package people;

import java.util.ArrayList;
import java.util.List;

import project.Individual;

public interface People {

	List<Individual> individuals = new ArrayList<Individual>();//list of individuals

	int getNi();

	void setNi(int ni);

	int getNmaxi();

	void setNmaxi(int nmaxi);
	
	void RemIndiv(int index);
	
	void AddIndiv(Individual i);

}