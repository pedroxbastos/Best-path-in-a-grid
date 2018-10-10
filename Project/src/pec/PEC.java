package pec;

import java.util.List;

import project.Event;

/**
 * @author basto
 * Interface PEC - List of events
 */
public interface PEC {
	
	/**
	 * Method to increment the number of events realized
	 */
	public void incrementnumEvents();
	
	/**
	 * Method to pass through and execute  the list of events
	 * @param pec (list of events)
	 * @return The event to execute or null if there's no more events in the list
	 */
	public abstract Event Events(PEC pec);
	
	/**
	 * Method that add an event to the list
	 * @param e (type Event)
	 * @return true if an event the add of the event was well succeded
	 */
	public boolean addEvent(Event e);
	
	/**
	 * Remove an event from the list
	 * @param e (Event)
	 */
	public abstract void RemEvent(Event e);
	
	//Getters and Setters
	/**
	 * @return the number of events realized
	 */
	public abstract int getNum();

	/**
	 * @return the size of the list of events
	 */
	public abstract int getSize();
	
	/**
	 * @return the list of events
	 */
	public List<Event> getEvents();
	
	/**
	 * Set a list of events
	 * @param events (list of events)
	 */
	public void setEvents(List<Event> events);
	@Override
	public abstract String toString();
	

}
