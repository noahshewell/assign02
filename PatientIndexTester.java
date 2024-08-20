package assign02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains tests for the PatientIndex class.
 * 
 * @author Nate LeMonnier and Noah Shewell
 * @version Jan 25, 2024
 */
class PatientIndexTester {
	
	PatientIndex smallIndex;
	Patient p1;
	Patient p2;

	@BeforeEach
	void setup() {
		smallIndex = new PatientIndex();
		p1 = new Patient("Nate", "LeMonnier", new UHealthID("ABCD-1234"));
		p2 = new Patient("Noah", "Shewell", new UHealthID("EFGH-5678"));

		smallIndex.addPatient(p1);
		smallIndex.addPatient(p2);

	}
	
	@Test
	void testAddPatientNormal() {
		
		Patient p3 = new Patient("Bob", "Jones", new UHealthID("IJKL-9123"));
		smallIndex.addPatient(p3);

		assertEquals("Bob Jones", smallIndex.getName(new UHealthID("IJKL-9123")));
	}
	
	@Test
	void testRemovePatientNormal() {
		
		smallIndex.removePatient(p1);

		assertNull(smallIndex.getName(new UHealthID("ABCD-1234")));
	}
	
	@Test
	void testGetNameNormal() {
		assertEquals("Nate LeMonnier", smallIndex.getName(new UHealthID("ABCD-1234")));
	}
	
	@Test
	void testRemovePatientNull() {
		smallIndex.removePatient(new Patient("Doesn't" , "Exist", new UHealthID("ASDF-2345")));

		assertNull(smallIndex.getName(new UHealthID("ASDF-2345")));
	}
	
	@Test
	void testGetNameEdge() {
		assertNull(smallIndex.getName(new UHealthID("JAKF-2849")));
	}

}
