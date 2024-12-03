package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.PublicSchool;

import static org.junit.jupiter.api.Assertions.*;

class PublicSchoolHashCodeTest {

    private PublicSchool publicSchool;

    @BeforeEach
    void setUp() {
        publicSchool = new PublicSchool();
    }

    @Test
    void testHashCode() {
        int hashCode = publicSchool.hashCode();
        assertNotNull(hashCode);
    }
}
