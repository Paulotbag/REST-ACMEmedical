package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.DurationAndStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class DurationAndStatusSetterTest {

    private DurationAndStatus durationAndStatus;

    @BeforeEach
    void setUp() {
        durationAndStatus = new DurationAndStatus();
        durationAndStatus.setStartDate(LocalDateTime.now());
        durationAndStatus.setEndDate(LocalDateTime.now().plusDays(10));
        durationAndStatus.setActive((byte) 1);
    }

    @Test
    void testSetStartDate() {
        LocalDateTime newStartDate = LocalDateTime.now().plusDays(1);
        durationAndStatus.setStartDate(newStartDate);
        assertEquals(newStartDate, durationAndStatus.getStartDate());
    }

    @Test
    void testSetEndDate() {
        LocalDateTime newEndDate = LocalDateTime.now().plusDays(5);
        durationAndStatus.setEndDate(newEndDate);
        assertEquals(newEndDate, durationAndStatus.getEndDate());
    }

    @Test
    void testSetActive() {
        durationAndStatus.setActive((byte) 0);
        assertEquals((byte) 0, durationAndStatus.getActive());
    }
}
