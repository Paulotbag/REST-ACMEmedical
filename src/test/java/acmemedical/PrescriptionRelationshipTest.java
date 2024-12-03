package acmemedical;

import acmemedical.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionRelationshipTest {

    private Prescription prescription;
    private Physician physician;
    private Patient patient;
    private Medicine medicine;

    @BeforeEach
    void setUp() {
        prescription = new Prescription();

        physician = new Physician();
        physician.setId(1);

        patient = new Patient();
        patient.setId(2);

        medicine = new Medicine();
        medicine.setId(3);
    }

    @Test
    void testPhysicianRelationship() {
        prescription.setPhysician(physician);
        assertNotNull(prescription.getPhysician());
        assertEquals(physician.getId(), prescription.getId().getPhysicianId());
    }

    @Test
    void testPatientRelationship() {
        prescription.setPatient(patient);
        assertNotNull(prescription.getPatient());
        assertEquals(patient.getId(), prescription.getId().getPatientId());
    }

    @Test
    void testMedicineRelationship() {
        prescription.setMedicine(medicine);
        assertNotNull(prescription.getMedicine());
        assertEquals(medicine, prescription.getMedicine());
    }

    @Test
    void testEmbeddedIdConsistency() {
        prescription.setPhysician(physician);
        prescription.setPatient(patient);

        PrescriptionPK id = prescription.getId();
        assertEquals(1, id.getPhysicianId());
        assertEquals(2, id.getPatientId());
    }
}