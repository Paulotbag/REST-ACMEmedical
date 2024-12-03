package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.MedicalSchool;

import static org.junit.jupiter.api.Assertions.*;

class MedicalSchoolPersistenceTest {

    private ConcreteMedicalSchool medicalSchool;

    @BeforeEach
    void setUp() {
        medicalSchool = new ConcreteMedicalSchool(true);
        medicalSchool.setId(1);
        medicalSchool.setName("Yale Medical School");
    }

    @Test
    void testPersistMedicalSchool() {
        // Simulate persisting the object (in a real test, this would interact with the database)
        assertNotNull(medicalSchool.getId());
        assertEquals("Yale Medical School", medicalSchool.getName());
    }

    static class ConcreteMedicalSchool extends MedicalSchool {
        public ConcreteMedicalSchool(boolean isPublic) {
            super(isPublic);
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
