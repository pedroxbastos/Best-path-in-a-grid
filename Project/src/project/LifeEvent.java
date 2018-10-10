package project;

abstract class LifeEvent extends Event{
	//Abstract subclass that extends the abstract class, event
	//This class only refers to life events, such as death, reproduction and move
	protected Individual i; //Individual associated
	float param; //Parameter to calculate the mean
	float mean; //Mean to calculate the time

	LifeEvent(Individual i, float param, float tatual) {
		this.i = i;
		this.param = param;
		this.mean = SetMean();
		this.t = (float) expRandom()+tatual;
	}
	//Declaration of methods to calculate the time
	abstract float expRandom();

	abstract float SetMean();
	
}
