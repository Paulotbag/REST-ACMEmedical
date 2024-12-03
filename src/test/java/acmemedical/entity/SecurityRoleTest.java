package acmemedical.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityRoleTest {

    private SecurityRole role1;
    private SecurityRole role2;

    @BeforeEach
    void setUp() {
        role1 = new SecurityRole();
        role2 = new SecurityRole();
    }

    @Test
    void testSetAndGetId() {
        role1.setId(1);
        assertEquals(1, role1.getId());
    }

    @Test
    void testSetAndGetRoleName() {
        role1.setRoleName("ADMIN");
        assertEquals("ADMIN", role1.getRoleName());
    }

    @Test
    void testEqualsSameId() {
        role1.setId(1);
        role2.setId(1);
        assertTrue(role1.equals(role2));
    }

    @Test
    void testEqualsDifferentId() {
        role1.setId(1);
        role2.setId(2);
        assertFalse(role1.equals(role2));
    }

    @Test
    void testHashCodeSameId() {
        role1.setId(1);
        role2.setId(1);
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    void testHashCodeDifferentId() {
        role1.setId(1);
        role2.setId(2);
        assertNotEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    void testToString() {
        role1.setId(1);
        role1.setRoleName("USER");
        String expected = "SecurityRole [id=1, roleName=USER]";
        assertEquals(expected, role1.toString());
    }
}
