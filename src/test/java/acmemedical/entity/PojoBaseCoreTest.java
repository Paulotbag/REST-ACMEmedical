package acmemedical.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PojoBaseCoreTest {

    private PojoBase pojoBase1;
    private PojoBase pojoBase2;

    private static class TestPojoBase extends PojoBase {
    }

    @BeforeEach
    void setUp() {
        pojoBase1 = new TestPojoBase();
        pojoBase2 = new TestPojoBase();
    }

    @Test
    void testSetAndGetId() {
        pojoBase1.setId(123);
        assertEquals(123, pojoBase1.getId());
    }

    @Test
    void testSetAndGetVersion() {
        pojoBase1.setVersion(1);
        assertEquals(1, pojoBase1.getVersion());
    }

    @Test
    void testSetAndGetCreated() {
        LocalDateTime createdDate = LocalDateTime.now();
        pojoBase1.setCreated(createdDate);
        assertEquals(createdDate, pojoBase1.getCreated());
    }

    @Test
    void testSetAndGetUpdated() {
        LocalDateTime updatedDate = LocalDateTime.now();
        pojoBase1.setUpdated(updatedDate);
        assertEquals(updatedDate, pojoBase1.getUpdated());
    }

    @Test
    void testEqualsAndHashCode() {
        pojoBase1.setId(1);
        pojoBase2.setId(1);

        assertTrue(pojoBase1.equals(pojoBase2));
        assertEquals(pojoBase1.hashCode(), pojoBase2.hashCode());
    }

    @Test
    void testEqualsDifferentId() {
        pojoBase1.setId(1);
        pojoBase2.setId(2);

        assertFalse(pojoBase1.equals(pojoBase2));
        assertNotEquals(pojoBase1.hashCode(), pojoBase2.hashCode());
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        assertFalse(pojoBase1.equals(null));
        assertFalse(pojoBase1.equals(new Object()));
    }
}
