package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents the generic form of the CurrentPatient object. It is virtually the same as the CurrentPatient but the 
 * physician is a generic type. It extends the Patient class, which it passes information to in the constructor via super().
 * 
 * @param <Type> - dictates what variable type the physician for the object is going to be. 
 * @author Nate LeMonnier and Noah Shewell
 * @version January 25, 2024
 */
public class CurrentPatientGeneric<Type> extends Patient{
	private Type physician;
	private GregorianCalendar lastVisit;

	/**
	 * CurrentPatientGeneric constructor, passes firstName, lastName, and UHealthID to the constructor of its superclass, Patient. 
	 * 
	 * @param firstName String first name of patient
	 * @param lastName String last name of patient
	 * @param uHealthID UHealthID object of patient. Relies on the UHealthID class to be created.
	 * @param physician physician of patient with generic type. When the object is created, the type for physician is 
	 * decided by what is passed as a parameter. 
	 * @param lastVisit GregorianCalendar last visit of patient, contains a year, month, and day of month. 
	 */
	public CurrentPatientGeneric(String firstName, String lastName, UHealthID uHealthID, Type physician, GregorianCalendar lastVisit) {
		super(firstName, lastName, uHealthID);
		this.physician = physician;
		this.lastVisit = lastVisit;
	}
	
	/**
	 * Getter method for physician
	 * 
	 * @return generic type physician of patient
	 */
	public Type getPhysician() {
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
	public void updatePhysician(Type newPhysician) {
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
