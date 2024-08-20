package assign02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * This class contains tests for Facility.
 *
 * @author Eric Heisler, Nate LeMonnier, Noah Shewell
 * @version Jan 25, 2024
 */
public class FacilityTester {

	private Facility emptyFacility, verySmallFacility, smallFacility, largeFacility;
	private UHealthID uHID1, uHID2, uHID3;
	private GregorianCalendar date1, date2, date3;

	@BeforeEach
	void setUp() throws Exception {
		Random random = new Random();

		uHID1 = new UHealthID("AAAA-1111");
		uHID2 = new UHealthID("BCBC-2323");
		uHID3 = new UHealthID("HRHR-7654");

		date1 = new GregorianCalendar(2023, 0, 1);
		date2 = new GregorianCalendar(2023, 3, 17);
		date3 = new GregorianCalendar(2022, 8, 21);

		emptyFacility = new Facility();

		verySmallFacility = new Facility();
		verySmallFacility.addPatient(new CurrentPatient("Jane", "Doe", uHID1, 1010101, date1));
		verySmallFacility.addPatient(new CurrentPatient("Drew", "Hall", uHID2, 3232323, date2));
		verySmallFacility.addPatient(new CurrentPatient("Riley", "Nguyen", uHID3, 9879876, date3));

		smallFacility = new Facility();
		smallFacility.addAll("src/assign02/small_patient_list.txt");

		largeFacility = new Facility();

		for (int j = 0; j < 15; j++) {

			char[] first = new char[4];
			char[] last = new char[4];
			char[] uHIDBegin = new char[4];
			int uHIDEnd = random.nextInt(1111, 9999);
			int physRandom = random.nextInt(1111111, 9999999);
			int yearRandom = random.nextInt(2017, 2025);
			int monthRandom = random.nextInt(1, 13);
			int dayRandom = random.nextInt(1, 30);

			for (int i = 0; i < 4; i++) {

				first[i] = (char) random.nextInt(65, 90);
				last[i] = (char) random.nextInt(65, 90);
				uHIDBegin[i] = (char) random.nextInt(65, 90);
			}

			largeFacility.addPatient(new CurrentPatient(new String(first), new String(last),
					new UHealthID((new String(uHIDBegin) + "-" + Integer.toString(uHIDEnd))), physRandom,
					new GregorianCalendar(yearRandom, monthRandom, dayRandom)));
		}

		largeFacility.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("ABCD-1234"), 1010101,
				new GregorianCalendar(2024, 1, 24)));

	}

	// Empty Facility tests --------------------------------------------------------

	@Test
	public void testEmptyLookupUHID() {
		assertNull(emptyFacility.lookupByUHID(uHID1));
	}

	@Test
	public void testEmptyLookupPhysician() {
		ArrayList<CurrentPatient> patients = emptyFacility.lookupByPhysician(1010101);
		assertEquals(0, patients.size());
	}

	@Test
	public void testEmptySetVisit() {
		// ensure no exceptions thrown
		emptyFacility.setLastVisit(uHID2, date3);
	}

	@Test
	public void testEmptySetPhysician() {
		// ensure no exceptions thrown
		emptyFacility.setPhysician(uHID2, 1010101);
	}

	@Test
	public void testEmptyGetInactivePatients() {
		ArrayList<CurrentPatient> patients = emptyFacility.getInactivePatients(date3);
		assertEquals(0, patients.size());
	}

	// Very small facility tests ---------------------------------------------------

	@Test
	public void testVerySmallLookupUHID() {
		Patient expected = new Patient("Drew", "Hall", new UHealthID("BCBC-2323"));
		CurrentPatient actual = verySmallFacility.lookupByUHID(new UHealthID("BCBC-2323"));
		assertEquals(expected, actual);
	}

	@Test
	public void testVerySmallLookupPhysicianCount() {
		ArrayList<CurrentPatient> actualPatients = verySmallFacility.lookupByPhysician(9879876);
		assertEquals(1, actualPatients.size());
	}

	@Test
	public void testVerySmallLookupPhysicianPatient() {
		Patient expectedPatient = new Patient("Riley", "Nguyen", new UHealthID("HRHR-7654"));
		ArrayList<CurrentPatient> actualPatients = verySmallFacility.lookupByPhysician(9879876);
		assertEquals(expectedPatient, actualPatients.get(0));
	}

	@Test
	public void testVerySmallAddNewPatient() {
		assertTrue(verySmallFacility
				.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("BBBB-2222"), 1010101, date1)));
	}

	@Test
	public void testVerySmallUpdatePhysician() {
		verySmallFacility.lookupByUHID(uHID1).updatePhysician(9090909);
		CurrentPatient patient = verySmallFacility.lookupByUHID(uHID1);
		assertEquals(9090909, patient.getPhysician());
	}

	@Test
	public void testVerySmallAddNewPatientPatientExists() {
		assertFalse(verySmallFacility.addPatient(new CurrentPatient("Jane", "Doe", uHID1, 1010101, date1)));
	}

	@Test
	public void testSetPhysVerySmall() {
		verySmallFacility.setPhysician(new UHealthID("AAAA-1111"), 11111);

		assertEquals(11111, verySmallFacility.lookupByPhysician(11111).get(0).getPhysician());
	}
	
	@Test
	public void testSetDateVerySmall() {
		verySmallFacility.setLastVisit(new UHealthID("AAAA-1111"), new GregorianCalendar(2024, 1, 11));

		assertEquals(new GregorianCalendar(2024, 1, 11), verySmallFacility.lookupByPhysician(1010101).get(0).getLastVisit());
	}

	// Small facility tests
	// -------------------------------------------------------------------------

	@Test
	public void testSmallLookupPhysicianCount() {
		ArrayList<CurrentPatient> actualPatients = smallFacility.lookupByPhysician(8888888);
		assertEquals(2, actualPatients.size());
	}

	@Test
	public void testSmallLookupPhysicianPatient() {
		Patient expectedPatient1 = new Patient("Kennedy", "Miller", new UHealthID("QRST-3456"));
		Patient expectedPatient2 = new Patient("Taylor", "Miller", new UHealthID("UVWX-7890"));

		ArrayList<CurrentPatient> actualPatients = smallFacility.lookupByPhysician(8888888);
		assertTrue(actualPatients.contains(expectedPatient1) && actualPatients.contains(expectedPatient2));
	}

	@Test
	public void testSmallGetInactivePatients() {
		ArrayList<CurrentPatient> actual = smallFacility.getInactivePatients(new GregorianCalendar(2020, 0, 0));
		assertEquals(9, actual.size());
	}

	@Test
	public void testSmallGetPhysicianList() {
		ArrayList<Integer> actual = smallFacility.getPhysicianList();
		assertEquals(7, actual.size());
	}

	@Test
	public void testSmallAddNewPatientPatientExists() {
		assertFalse(smallFacility.addPatient(new CurrentPatient("Chang-Hau", "Hsu", new UHealthID("MOON-3769"), 9999999,
				new GregorianCalendar(2022, 6, 6))));
	}

	@Test
	public void testSetPhysSmall() {
		smallFacility.setPhysician(new UHealthID("MOON-3769"), 11111);

		assertEquals(11111, smallFacility.lookupByPhysician(11111).get(0).getPhysician());
	}
	
	@Test
	public void testSetDateSmall() {
		smallFacility.setLastVisit(new UHealthID("MOON-3769"), new GregorianCalendar(2024, 1, 11));

		assertEquals(new GregorianCalendar(2024, 1, 11), smallFacility.lookupByPhysician(9999999).get(0).getLastVisit());
	}

	// Large facility tests
	// -------------------------------------------------------------------------

	@Test
	public void testLargeLookupPhysicianCount() {
		ArrayList<CurrentPatient> actualPatients = largeFacility.lookupByPhysician(1010101);
		assertEquals(1, actualPatients.size());
	}

	@Test
	public void testLargeLookupPhysicianPatient() {
		Patient expectedPatient1 = new Patient("Jane", "Doe", new UHealthID("ABCD-1234"));

		ArrayList<CurrentPatient> actualPatients = largeFacility.lookupByPhysician(1010101);
		assertTrue(actualPatients.contains(expectedPatient1));
	}

	@Test
	public void testLargeGetInactivePatients() {
		ArrayList<CurrentPatient> actual = largeFacility.getInactivePatients(new GregorianCalendar(2017, 0, 0));
		assertEquals(0, actual.size());
	}

	@Test
	public void testLargeGetPhysicianList() {
		ArrayList<Integer> actual = largeFacility.getPhysicianList();
		assertEquals(16, actual.size());
	}

	@Test
	public void testLargeAddNewPatientPatientExists() {
		assertFalse(largeFacility.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("ABCD-1234"), 1010101,
				new GregorianCalendar(2024, 1, 24))));
	}
	
	@Test
	public void testSetPhysLarge() {
		largeFacility.setPhysician(new UHealthID("ABCD-1234"), 11111);
		assertEquals(11111, largeFacility.lookupByPhysician(11111).get(0).getPhysician());
	}
	
	@Test
	public void testSetDateLarge() {
		largeFacility.setLastVisit(new UHealthID("ABCD-1234"), new GregorianCalendar(2024, 1, 11));

		assertEquals(new GregorianCalendar(2024, 1, 11), largeFacility.lookupByPhysician(1010101).get(0).getLastVisit());
	}
	
	
}
