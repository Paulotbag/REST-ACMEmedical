package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.MedicalSchool;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteMedicalSchoolEqualityTest {

    private ConcreteMedicalSchool school1;
    private ConcreteMedicalSchool school2;

    @BeforeEach
    void setUp() {
        school1 = new ConcreteMedicalSchool(true);
        school2 = new ConcreteMedicalSchool(true);
    }

    @Test
    void testEquality() {
        assertEquals(school1, school2);
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
