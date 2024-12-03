package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.MedicalSchool;

import static org.junit.jupiter.api.Assertions.*;

class MedicalSchoolTest {

    private ConcreteMedicalSchool medicalSchool;

    @BeforeEach
    void setUp() {
        medicalSchool = new ConcreteMedicalSchool(true);
        medicalSchool.setId(1);
        medicalSchool.setName("Johns Hopkins Medical School");
    }

    @Test
    void testGetName() {
        assertEquals("Johns Hopkins Medical School", medicalSchool.getName());
    }

    @Test
    void testSetName() {
        medicalSchool.setName("Mayo Clinic Medical School");
        assertEquals("Mayo Clinic Medical School", medicalSchool.getName());
    }

    @Test
    void testIsPublic() {
        //assertTrue(medicalSchool.isPublic());
    }

    @Test
    void testEquality() {
        ConcreteMedicalSchool duplicateSchool = new ConcreteMedicalSchool(true);
        duplicateSchool.setId(1);
        duplicateSchool.setName("Johns Hopkins Medical School");
        assertEquals(medicalSchool, duplicateSchool);
    }

    static class ConcreteMedicalSchool extends MedicalSchool {
        public ConcreteMedicalSchool(boolean isPublic) {
            super(isPublic);
        }
    }
}