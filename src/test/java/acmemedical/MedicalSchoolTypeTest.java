package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.MedicalSchool;
import acmemedical.entity.PrivateSchool;
import acmemedical.entity.PublicSchool;

import static org.junit.jupiter.api.Assertions.*;

class MedicalSchoolTypeTest {

    private MedicalSchool publicSchool;
    private MedicalSchool privateSchool;

    @BeforeEach
    void setUp() {
        publicSchool = new PublicSchool();
        privateSchool = new PrivateSchool();
    }

    @Test
    void testPublicSchoolType() {
        assertTrue(publicSchool instanceof PublicSchool);
        assertFalse(publicSchool instanceof PrivateSchool);
    }

    @Test
    void testPrivateSchoolType() {
        assertTrue(privateSchool instanceof PrivateSchool);
        assertFalse(privateSchool instanceof PublicSchool);
    }

    @Test
    void testMedicalSchoolTypeEquality() {
        assertNotEquals(publicSchool.getClass(), privateSchool.getClass());
    }
}
