package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.DurationAndStatus;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class DurationAndStatusTest {

    private DurationAndStatus durationAndStatus;

    @BeforeEach
    void setUp() {
        durationAndStatus = new DurationAndStatus();
        durationAndStatus.setStartDate(LocalDateTime.now());
        durationAndStatus.setEndDate(LocalDateTime.now().plusDays(10));
        durationAndStatus.setActive((byte) 1);
    }

    @Test
    void testGetStartDate() {
        assertNotNull(durationAndStatus.getStartDate());
    }

    @Test
    void testSetEndDate() {
        LocalDateTime newEndDate = LocalDateTime.now().plusDays(5);
        durationAndStatus.setEndDate(newEndDate);
        assertEquals(newEndDate, durationAndStatus.getEndDate());
    }

    @Test
    void testIsActive() {
        assertTrue(durationAndStatus.getActive() == 1);
    }

    @Test
    void testEquals() {
        DurationAndStatus anotherStatus = new DurationAndStatus();
        anotherStatus.setStartDate(durationAndStatus.getStartDate());
        anotherStatus.setEndDate(durationAndStatus.getEndDate());
        anotherStatus.setActive(durationAndStatus.getActive());
        assertTrue(durationAndStatus.equals(anotherStatus));
    }
}
