package pec;

import project.Event;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * @author basto
 * Implementation of the interface PEC (list of events)
 */
public class Eventslist implements PEC {
	//Implementation of the interface PEC
	
	/**
	 * list of events
	 */
	List<Event> events = new ArrayList<>(); //list of events
	/**
	 * Number of events realized in a simulation
	 */
	public int num; //number of events realized in a simulation
	

	/**
	 * Method to increment the number of events realized
	 */
	public void incrementnumEvents() {
		this.num++;
	}
	//Constructor
	/**
	 * Constructor to inicialize the list of events and the num
	 * @param events (list of events)
	 * @param num (number of events realized. Most of times initialized a 0)
	 */
	public Eventslist(List<Event> events, int num) {
		this.events = events;
		this.num = num;
	}
	
	//Method to pass through the list of events
	/**
	 * Method to pass through the list of events
	 * @param pec (list of events)
	 * @return e (Event to execute) or null (if doesn't exist any event)
	 */
	public Event Events(PEC pec){
		Iterator<Event> it=getEvents().iterator();
		Event e;
		
		while(it.hasNext()){
			//e=(Event)it.next();
			e=events.get(0);
			return e;		
		}
		return null;
	 }

	//Method that add an event to the list by ascending order of time
	public boolean addEvent(Event e) {
		if (e == null) return false;
		float aux;
		Iterator<Event> it=getEvents().iterator();
		Event e1;
		int i = 0;
        if (this.getEvents().size() == 0) {
            this.getEvents().add(e);
            return true;
        }
        else if (this.getEvents().get(getSize()-1).getT() < e.getT()) {
            this.getEvents().add(this.getEvents().size(), e);
            return true;
        }
        while(it.hasNext()) {
			e1=(Event)it.next();
			aux=e1.getT();
			if(aux<e.getT()) {
				i++;
				continue;
			}
			else {
				this.getEvents().add(i, e);
				return true;
			}
        }
        return true;
	}
	
	//Remove an event from the list
	public void RemEvent(Event e) {
		this.getEvents().remove(e);
	}
	
	//Setters and Getters////////////////
	
	public int getNum() {
		return this.num;
	}
	

    public int getSize() {
    	return this.getEvents().size();
    }
    
	public List<Event> getEvents() {
		return events;
	}
	
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	///////////////////////////////////
	
	@Override
	public String toString() {
		return "PEC [events=" + events + "]";
	}

}
