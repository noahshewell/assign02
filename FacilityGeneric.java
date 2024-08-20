package assign02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * This class represents a facility of multiple CurrentPatientGeneric types. The class contains a generic ArrayList that holds 
 * generic CurrentPatientGeneric types. The user can manipulate data in the facility using multiple getter and setter
 * methods.
 * 
 * @param <Type> dictates the physician type for the CurrentPatientGeneric object. 
 * @author Nate LeMonnier and Noah Shewell
 * @version January 25, 2024
 */
public class FacilityGeneric<Type> {

	private ArrayList<CurrentPatientGeneric> patientList;

	/**
	 * Creates an empty facility record.
	 */
	public FacilityGeneric() {
		this.patientList = new ArrayList<CurrentPatientGeneric>();
	}

	/**
	 * Adds the given patient to the list of patients, avoiding duplicates.
	 *
	 * @param patient - patient to be added to this record
	 * @return true if the patient was added, false if the patient was not added
	 *         because they already exist in the record
	 */
	public boolean addPatient(CurrentPatientGeneric patient) {
		if (patientList.contains(patient)) {
			return false;
		}
		patientList.add(patient);
		return true;
	}

	/**
	 * Retrieves the patient with the given UHealthID.
	 *
	 * @param UHealthID of patient to be retrieved
	 * @return the patient with the given ID, or null if no such patient exists in
	 *         the record
	 */
	public CurrentPatientGeneric lookupByUHID(UHealthID patientID) {
		for (CurrentPatientGeneric pat : patientList) {
			if (pat.getUHealthID().equals(patientID)) {
				return pat;
			}
		}
		return null;
	}

	/**
	 * Retrieves the patient(s) with the given physician.
	 *
	 * @param physician - physician of patient(s) to be retrieved
	 * @return a list of patient(s) with the given physician (in any order), or an
	 *         empty list if no such patients exist in the record
	 */
	public ArrayList<CurrentPatientGeneric<Type>> lookupByPhysician(Type physician) {
		ArrayList<CurrentPatientGeneric<Type>> patients = new ArrayList<CurrentPatientGeneric<Type>>();
		for (CurrentPatientGeneric<Type> pat : patientList) {
			if (pat.getPhysician().equals(physician)) {
				patients.add(pat);
			}
		}
		return patients;
	}

	/**
	 * Retrieves the patient(s) with last visits older than a given date.
	 *
	 * NOTE: If the last visit date equals this date, do not add the patient.
	 *
	 * @param date - cutoff date later than visit date of all returned patients.
	 * @return a list of patient(s) with last visit date before cutoff (in any
	 *         order), or an empty list if no such patients exist in the record
	 */
	public ArrayList<CurrentPatientGeneric<Type>> getInactivePatients(GregorianCalendar date) {
		ArrayList<CurrentPatientGeneric<Type>> patients = new ArrayList<CurrentPatientGeneric<Type>>();
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getLastVisit().compareTo(date) < 0) {
				patients.add(patientList.get(i));
			}
		}
		return patients;
	}

	/**
	 * Retrieves a list of physicians assigned to patients at this facility.
	 *
	 * * NOTE: Do not put duplicates in the list. Make sure each physician is only
	 * added once.
	 *
	 * @return a list of physician(s) assigned to current patients, or an empty list
	 *         if no patients exist in the record
	 */
	public ArrayList<Type> getPhysicianList() {
		ArrayList<Type> physicians = new ArrayList<>();
		for (CurrentPatientGeneric pat : patientList) {
			if (!(physicians.contains(pat.getPhysician()))) {
				physicians.add((Type) pat.getPhysician());
			}
		}
		return physicians;
	}

	/**
	 * Sets the physician of a patient with the given UHealthID.
	 *
	 * NOTE: If no patient with the ID exists in the collection, then this method
	 * has no effect.
	 *
	 * @param patientID - UHealthID of patient to modify
	 * @param physician - identifier of patient's new physician
	 */
	public void setPhysician(UHealthID patientID, int physician) {
		for (CurrentPatientGeneric pat : patientList)
			if (pat.getUHealthID().equals(patientID))
				pat.updatePhysician(physician);
	}

	/**
	 * Sets the last visit date of a patient with the given UHealthID.
	 *
	 * NOTE: If no patient with the ID exists in the collection, then this method
	 * has no effect.
	 *
	 * @param patientID - UHealthID of patient to modify
	 * @param date      - new date of last visit
	 */
	public void setLastVisit(UHealthID patientID, GregorianCalendar date) {
		for (CurrentPatientGeneric pat : patientList)
			if (pat.getUHealthID().equals(patientID))
				pat.updateLastVisit(date);
	}

	/**
	 * Returns the list of current patients in this facility, sorted by uHealthID in
	 * lexicographical order.
	 */
	public ArrayList<CurrentPatientGeneric<Type>> getOrderedByUHealthID() {
		ArrayList<CurrentPatientGeneric<Type>> patientListCopy = new ArrayList<CurrentPatientGeneric<Type>>();
		for (CurrentPatientGeneric<Type> patient : patientList) {
			patientListCopy.add(patient);
		}
		sort(patientListCopy, new OrderByUHealthID());
		return patientListCopy;
	}

	/**
	 * Returns the list of current patients in this facility with a date of last
	 * visit later than a cutoff date, sorted by name (last name, breaking ties with
	 * first name) Breaks ties in names using uHealthIDs (lexicographical order).
	 * Note: see the OrderByName class started for you below!
	 *
	 * @param cutoffDate - value that a patient's last visit must be later than to
	 *                   be included in the returned list
	 */
	public ArrayList<CurrentPatientGeneric<Type>> getRecentPatients(GregorianCalendar cutoffDate) {
		ArrayList<CurrentPatientGeneric<Type>> patientListCopy = new ArrayList<CurrentPatientGeneric<Type>>();
		for (CurrentPatientGeneric<Type> pat : patientList) {
			patientListCopy.add(pat);
		}
		sort(patientListCopy, new OrderByName());
		return patientListCopy;
	}

	/**
	 * Performs a SELECTION SORT on the input ArrayList.
	 *
	 * 1. Finds the smallest item in the list. 2. Swaps the smallest item with the
	 * first item in the list. 3. Reconsiders the list to be the remaining unsorted
	 * portion (second item to Nth item) and repeats steps 1, 2, and 3.
	 */
	private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c) {
		for (int i = 0; i < list.size() - 1; i++) {
			int j, minIndex;
			for (j = i + 1, minIndex = i; j < list.size(); j++) {
				if (c.compare(list.get(j), list.get(minIndex)) < 0) {
					minIndex = j;
				}
			}
			ListType temp = list.get(i);
			list.set(i, list.get(minIndex));
			list.set(minIndex, temp);
		}
	}

	/**
	 * Comparator that defines an ordering among current patients using their
	 * uHealthIDs. uHealthIDs are guaranteed to be unique, making a tie-breaker
	 * unnecessary.
	 */
	protected class OrderByUHealthID implements Comparator<CurrentPatientGeneric<Type>> {
		/**
		 * Returns a negative value if lhs (left-hand side) is less than rhs (right-hand
		 * side). Returns a positive value if lhs is greater than rhs. Returns 0 if lhs
		 * and rhs are equal.
		 */
		public int compare(CurrentPatientGeneric<Type> lhs, CurrentPatientGeneric<Type> rhs) {
			return lhs.getUHealthID().toString().compareTo(rhs.getUHealthID().toString());
		}
	}

	/**
	 * Comparator that defines an ordering among current patients using their names.
	 * Compares by last name, then first name (if last names are the same), then
	 * uHealthID (if both names are the same). uHealthIDs are guaranteed to be
	 * unique.
	 */
	protected class OrderByName implements Comparator<CurrentPatientGeneric<Type>> {

		@Override
		public int compare(CurrentPatientGeneric<Type> o1, CurrentPatientGeneric<Type> o2) {
			return (o1.getLastName() + o1.getFirstName() + o1.getUHealthID())
					.compareTo(o2.getLastName() + o2.getFirstName() + o2.getUHealthID());
		}
	}

}
