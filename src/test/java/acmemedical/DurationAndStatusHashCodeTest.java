package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.DurationAndStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class DurationAndStatusHashCodeTest {

    private DurationAndStatus durationAndStatus;

    @BeforeEach
    void setUp() {
        durationAndStatus = new DurationAndStatus();
        durationAndStatus.setStartDate(LocalDateTime.now());
        durationAndStatus.setEndDate(LocalDateTime.now().plusDays(10));
        durationAndStatus.setActive((byte) 1);
    }

    @Test
    void testHashCode() {
        int hashCode = durationAndStatus.hashCode();
        assertNotNull(hashCode);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
