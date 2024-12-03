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
<<<<<<< HEAD

=======
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
