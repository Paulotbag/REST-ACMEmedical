package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.PrivateSchool;

import static org.junit.jupiter.api.Assertions.*;

class PrivateSchoolEqualityTest {

    private PrivateSchool privateSchool1;
    private PrivateSchool privateSchool2;

    @BeforeEach
    void setUp() {
        privateSchool1 = new PrivateSchool();
        privateSchool2 = new PrivateSchool();
    }

    @Test
    void testEquality() {
        assertEquals(privateSchool1, privateSchool2);
    }
}

