package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.PublicSchool;
import jakarta.persistence.DiscriminatorValue;

import static org.junit.jupiter.api.Assertions.*;

class PublicSchoolTest {

    private PublicSchool publicSchool;

    @BeforeEach
    void setUp() {
        publicSchool = new PublicSchool();
    }

    @Test
    void testIsPublic() {
        assertTrue(publicSchool.isPublic());
    }

    @Test
    void testDiscriminatorValue() {
        assertEquals("1", publicSchool.getClass().getAnnotation(DiscriminatorValue.class).value());
    }
}
