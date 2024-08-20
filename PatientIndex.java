package assign02;

import java.util.TreeMap;

/**
 * This class represents a TreeMap object containing a String name linked to a UHealthID object. The user can access and manipulate
 * data in the TreeMap object via numerous getter and setter methods. 
 * 
 * @author Noah Shewell and Nate LeMonnier
 * @version January 25, 2024
 */
public class PatientIndex {
	private TreeMap<UHealthID, String> member;

	public PatientIndex() {
		member = new TreeMap<UHealthID, String>((s1, s2) -> s1.toString().compareTo(s2.toString()));
	}

	/**
	 * Adds patient to patient index
	 * 
	 * @param p Patient to be added
	 */
	public void addPatient(Patient p) {
		String name = p.getFirstName() + " " + p.getLastName();
		UHealthID id = p.getUHealthID();
		member.put(id, name);
	}

	/**
	 * Removes patient from patient index
	 * 
	 * @param p Patient to be added
	 */
	public void removePatient(Patient p) {
		String name = p.getFirstName() + " " + p.getLastName();
		UHealthID id = p.getUHealthID();
		
		if (member.containsKey(id) && member.containsValue(name)) {
			 member.remove(id);
		}
	}

	/**
	 * Gets the name of the patient in patientIndex with given UHealthID
	 * 
	 * @param id UHealthID to get name of
	 * @return name of patient with given UHealthID
	 */
	public String getName(UHealthID id) {
		return member.get(id);
	}
}
