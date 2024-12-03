package acmemedical.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PojoListenerPrePersistTest {

    private PojoListener pojoListener;
    private PojoBase pojoBase;

    // Concrete implementation of the abstract PojoBase class for testing
    private static class TestPojoBase extends PojoBase {
    }

    @BeforeEach
    void setUp() {
        pojoListener = new PojoListener();
        pojoBase = new TestPojoBase();
    }

    @Test
    void testSetCreatedOnDate() {
        // Act
        pojoListener.setCreatedOnDate(pojoBase);

        // Assert
        assertNotNull(pojoBase.getCreated(), "Created timestamp should not be null after @PrePersist.");
        assertNotNull(pojoBase.getUpdated(), "Updated timestamp should not be null after @PrePersist.");
        assertEquals(pojoBase.getCreated(), pojoBase.getUpdated(), "Created and updated timestamps should be identical during @PrePersist.");
    }
}

