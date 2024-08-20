package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents a current patient with a physician and a date of last visit that extends the superclass patient. 
 * The user can access and manipulate data about the physician and the date of last visit via various getter and setter classes.
 * 
 * @author Nate LeMonnier and Noah Shewell
 * @version January 25, 2024
 */
public class CurrentPatient extends Patient {
	
	private int physician;
	private GregorianCalendar lastVisit;

	/** 
	 * CurrentPatient constructor, passes firstName, lastName, and UHealthID to the constructor of its superclass, Patient. 
	 * 
	 * @param firstName String first name of patient
	 * @param lastName String last name of patient
	 * @param uHealthID UHealthID object of patient. Relies on the UHealthID class to be created.
	 * @param physician int physician of patient
	 * @param lastVisit GregorianCalendar last visit of patient, contains a year, month, and day of month. 
	 */
	public CurrentPatient(String firstName, String lastName, UHealthID uHealthID, int physician, GregorianCalendar lastVisit) {
		super(firstName, lastName, uHealthID);
		this.physician = physician;
		this.lastVisit = lastVisit;
	}
	
	/**
	 * Getter method for physician
	 * 
	 * @return int physician of patient
	 */
	public int getPhysician() {
		return this.physician;
	}
	
	/**
	 * Getter method for lastVisit
	 * 
	 * @return GregorianCalendar of currentPatients last visit
	 */
	public GregorianCalendar getLastVisit() {
		return this.lastVisit;
	}
	
	/**
	 * Setter method for physician 
	 * 
	 * @param newPhysician generic type for new physician
	 */
	public void updatePhysician(int newPhysician) {
		this.physician = newPhysician;
	}
	
	/**
	 * Setter method for last visit
	 * 
	 * @param date GregorianCalendar new lastVisit
	 */
	public void updateLastVisit(GregorianCalendar date) {
		this.lastVisit = date;
	}

}
