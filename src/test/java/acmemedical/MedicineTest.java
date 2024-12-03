package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.Medicine;
import acmemedical.entity.Prescription;
import acmemedical.entity.PrescriptionPK;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MedicineTest {

    private Medicine medicine;
    private Prescription prescription1;
    private Prescription prescription2;

    @BeforeEach
    void setUp() {
        // Create a Medicine object
        medicine = new Medicine();
        medicine.setId(1);  // Assuming the ID is set manually for the test
        medicine.setDrugName("Aspirin");
        medicine.setManufacturerName("PharmaCorp");
        medicine.setDosageInformation("100mg once daily");

        // Create Prescription objects and associate them with the Medicine
        prescription1 = new Prescription();
        PrescriptionPK pk1 = new PrescriptionPK();
        pk1.setPhysicianId(1);  // Set physician ID for prescription 1
        pk1.setPatientId(1);  // Set patient ID for prescription 1
        prescription1.setId(pk1);
        prescription1.setPrescriptionInformation("Prescription 1 details");
        prescription1.setMedicine(medicine);  // Associate prescription1 with the medicine

        prescription2 = new Prescription();
        PrescriptionPK pk2 = new PrescriptionPK();
        pk2.setPhysicianId(2);  // Set physician ID for prescription 2
        pk2.setPatientId(2);  // Set patient ID for prescription 2
        prescription2.setId(pk2);
        prescription2.setPrescriptionInformation("Prescription 2 details");
        prescription2.setMedicine(medicine);  // Associate prescription2 with the medicine
    }


    @Test
    void testGettersAndSetters() {
        assertEquals(1, medicine.getId());
        assertEquals("Aspirin", medicine.getDrugName());
        assertEquals("PharmaCorp", medicine.getManufacturerName());
        assertEquals("100mg once daily", medicine.getDosageInformation());
    }

    @Test
    void testAddPrescription() {
        medicine.addPrescription(prescription1);
        assertTrue(medicine.getPrescriptions().contains(prescription1));
        assertEquals(1, medicine.getPrescriptions().size());
        assertEquals(medicine, prescription1.getMedicine());
    }

    @Test
    void testRemovePrescription() {
        medicine.addPrescription(prescription1);
        medicine.addPrescription(prescription2);

        medicine.removePrescription(prescription1);
        assertFalse(medicine.getPrescriptions().contains(prescription1));
        assertNull(prescription1.getMedicine());
        assertEquals(1, medicine.getPrescriptions().size());
    }

    @Test
    void testEqualsAndHashCode() {
        Medicine sameMedicine = new Medicine();
        sameMedicine.setId(1);
        sameMedicine.setDrugName("Aspirin");
        sameMedicine.setManufacturerName("PharmaCorp");

        assertEquals(medicine, sameMedicine);
        assertEquals(medicine.hashCode(), sameMedicine.hashCode());

        Medicine differentMedicine = new Medicine();
        differentMedicine.setId(2);
        assertNotEquals(medicine, differentMedicine);
    }

    @Test
    void testToString() {
        String expectedString = "Medicine [id=1, drugName=Aspirin, manufacturerName=PharmaCorp, dosageInformation=100mg once daily]";
        assertEquals(expectedString, medicine.toString());
    }

    @Test
    void testSetPrescriptions() {
        Set<Prescription> prescriptions = new HashSet<>();
        prescriptions.add(prescription1);
        prescriptions.add(prescription2);

        medicine.setPrescriptions(prescriptions);

        assertEquals(2, medicine.getPrescriptions().size());
        assertTrue(medicine.getPrescriptions().contains(prescription1));
        assertTrue(medicine.getPrescriptions().contains(prescription2));
    }
}
