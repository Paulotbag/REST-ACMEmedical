package acmemedical.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PojoListenerPreUpdateTest {

    private PojoListener pojoListener;
    private PojoBase pojoBase;

    // Concrete implementation of the abstract PojoBase class for testing
    private static class TestPojoBase extends PojoBase {
    }

    @BeforeEach
    void setUp() {
        pojoListener = new PojoListener();
        pojoBase = new TestPojoBase();
        pojoBase.setCreated(LocalDateTime.now()); // Simulate an existing entity with a created timestamp
        pojoBase.setUpdated(LocalDateTime.now().minusDays(1)); // Set the updated timestamp to an earlier date
    }

    @Test
    void testSetUpdatedDate() {
        // Arrange
        LocalDateTime previousUpdated = pojoBase.getUpdated();

        // Act
        pojoListener.setUpdatedDate(pojoBase);

        // Assert
        assertNotNull(pojoBase.getUpdated(), "Updated timestamp should not be null after @PreUpdate.");
        assertTrue(pojoBase.getUpdated().isAfter(previousUpdated), "Updated timestamp should be more recent than the previous one.");
        assertEquals(pojoBase.getCreated(), pojoBase.getCreated(), "Created timestamp should remain unchanged during @PreUpdate.");
    }
}
