package acmemedical;

import acmemedical.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionTest {

    private Prescription prescription;
    private PrescriptionPK prescriptionPK;
    private Physician physician;
    private Patient patient;
    private Medicine medicine;

    @BeforeEach
    void setUp() {
        prescription = new Prescription();
        prescriptionPK = new PrescriptionPK(1, 2);

        physician = new Physician();
        physician.setId(1);

        patient = new Patient();
        patient.setId(2);

        medicine = new Medicine();
        medicine.setId(3);
    }

    @Test
    void testSetAndGetId() {
        prescription.setId(prescriptionPK);
        assertEquals(prescriptionPK, prescription.getId());
    }

    @Test
    void testSetAndGetPhysician() {
        prescription.setPhysician(physician);
        assertEquals(physician, prescription.getPhysician());
        assertEquals(1, prescription.getId().getPhysicianId());
    }

    @Test
    void testSetAndGetPatient() {
        prescription.setPatient(patient);
        assertEquals(patient, prescription.getPatient());
        assertEquals(2, prescription.getId().getPatientId());
    }

    @Test
    void testSetAndGetMedicine() {
        prescription.setMedicine(medicine);
        assertEquals(medicine, prescription.getMedicine());
    }

    @Test
    void testSetAndGetNumberOfRefills() {
        prescription.setNumberOfRefills(3);
        assertEquals(3, prescription.getNumberOfRefills());
    }

    @Test
    void testSetAndGetPrescriptionInformation() {
        String info = "Take one pill daily after meals.";
        prescription.setPrescriptionInformation(info);
        assertEquals(info, prescription.getPrescriptionInformation());
    }
}