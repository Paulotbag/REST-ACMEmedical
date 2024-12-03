package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.PrivateSchool;
import jakarta.persistence.DiscriminatorValue;

import static org.junit.jupiter.api.Assertions.*;

class PrivateSchoolTest {

    private PrivateSchool privateSchool;

    @BeforeEach
    void setUp() {
        privateSchool = new PrivateSchool();
    }

    @Test
    void testIsPublic() {
        //assertFalse(privateSchool.isPublic());
    }

    @Test
    void testDiscriminatorValue() {
        assertEquals("0", privateSchool.getClass().getAnnotation(DiscriminatorValue.class).value());
    }
}
