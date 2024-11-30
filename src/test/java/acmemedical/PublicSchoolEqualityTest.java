package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.PublicSchool;

import static org.junit.jupiter.api.Assertions.*;

class PublicSchoolEqualityTest {

    private PublicSchool publicSchool1;
    private PublicSchool publicSchool2;

    @BeforeEach
    void setUp() {
        publicSchool1 = new PublicSchool();
        publicSchool2 = new PublicSchool();
    }

    @Test
    void testEquality() {
        assertEquals(publicSchool1, publicSchool2);
    }
}

