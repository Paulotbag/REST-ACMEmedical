package acmemedical;

import acmemedical.entity.PojoBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PojoBaseLifecycleTest {

    private PojoBase pojoBase;

    private static class TestPojoBase extends PojoBase {
    }

    @BeforeEach
    void setUp() {
        pojoBase = new TestPojoBase();
    }

    @Test
    void testVersionIncrement() {
        pojoBase.setVersion(1);
        pojoBase.setVersion(2);
        assertEquals(2, pojoBase.getVersion());
    }

    @Test
    void testTimestampUpdate() {
        LocalDateTime created = LocalDateTime.now();
        pojoBase.setCreated(created);

        LocalDateTime updated = LocalDateTime.now();
        pojoBase.setUpdated(updated);

        assertEquals(created, pojoBase.getCreated());
        assertEquals(updated, pojoBase.getUpdated());
    }

    @Test
    void testHashCodeStability() {
        pojoBase.setId(999);
        int initialHashCode = pojoBase.hashCode();

        pojoBase.setVersion(5); // Changing version should not affect hashCode
        assertEquals(initialHashCode, pojoBase.hashCode());
    }

    @Test
    void testEqualsWithSameIdAndDifferentVersion() {
        PojoBase otherPojoBase = new TestPojoBase();
        pojoBase.setId(42);
        otherPojoBase.setId(42);

        pojoBase.setVersion(1);
        otherPojoBase.setVersion(2);

        assertTrue(pojoBase.equals(otherPojoBase)); // ID defines equality, not version
    }
}