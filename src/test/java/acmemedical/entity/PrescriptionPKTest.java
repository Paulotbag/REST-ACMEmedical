package acmemedical.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionPKTest {

    private PrescriptionPK prescriptionPK1;
    private PrescriptionPK prescriptionPK2;

    @BeforeEach
    void setUp() {
        prescriptionPK1 = new PrescriptionPK();
        prescriptionPK2 = new PrescriptionPK();
    }

    @Test
    void testSetAndGetPhysicianId() {
        prescriptionPK1.setPhysicianId(123);
        assertEquals(123, prescriptionPK1.getPhysicianId());
    }

    @Test
    void testSetAndGetPatientId() {
        prescriptionPK1.setPatientId(456);
        assertEquals(456, prescriptionPK1.getPatientId());
    }

    @Test
    void testEqualsSameObject() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);
        assertTrue(prescriptionPK1.equals(prescriptionPK1));
    }

    @Test
    void testEqualsDifferentObjectsSameValues() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        prescriptionPK2.setPhysicianId(123);
        prescriptionPK2.setPatientId(456);

        assertTrue(prescriptionPK1.equals(prescriptionPK2));
    }

    @Test
    void testEqualsDifferentObjectsDifferentValues() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        prescriptionPK2.setPhysicianId(789);
        prescriptionPK2.setPatientId(101);

        assertFalse(prescriptionPK1.equals(prescriptionPK2));
    }

    @Test
    void testEqualsWithNullObject() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        assertFalse(prescriptionPK1.equals(null));
    }

    @Test
    void testEqualsWithDifferentClassObject() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        String otherObject = "Not a PrescriptionPK";
        assertFalse(prescriptionPK1.equals(otherObject));
    }

    @Test
    void testHashCodeConsistency() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        int initialHashCode = prescriptionPK1.hashCode();
        assertEquals(initialHashCode, prescriptionPK1.hashCode());
    }

    @Test
    void testHashCodeSameValues() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        prescriptionPK2.setPhysicianId(123);
        prescriptionPK2.setPatientId(456);

        assertEquals(prescriptionPK1.hashCode(), prescriptionPK2.hashCode());
    }

    @Test
    void testHashCodeDifferentValues() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        prescriptionPK2.setPhysicianId(789);
        prescriptionPK2.setPatientId(101);

        assertNotEquals(prescriptionPK1.hashCode(), prescriptionPK2.hashCode());
    }

    @Test
    void testToString() {
        prescriptionPK1.setPhysicianId(123);
        prescriptionPK1.setPatientId(456);

        String expected = "PrescriptionPK [physicianId=123, patientId=456]";
        assertEquals(expected, prescriptionPK1.toString());
    }
}
