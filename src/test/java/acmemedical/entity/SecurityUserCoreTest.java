package acmemedical.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserCoreTest {

    private SecurityUser securityUser;
    private Physician physician;

    @BeforeEach
    void setUp() {
        securityUser = new SecurityUser();
        physician = new Physician();
        physician.setId(100);
    }

    @Test
    void testSetAndGetId() {
        securityUser.setId(1);
        assertEquals(1, securityUser.getId());
    }

    @Test
    void testSetAndGetUsername() {
        securityUser.setUsername("testUser");
        assertEquals("testUser", securityUser.getUsername());
    }

    @Test
    void testSetAndGetPhysician() {
        securityUser.setPhysician(physician);
        assertEquals(physician, securityUser.getPhysician());
    }

    @Test
    void testSetAndGetRoles() {
        SecurityRole role1 = new SecurityRole();
        role1.setRoleName("ADMIN");

        SecurityRole role2 = new SecurityRole();
        role2.setRoleName("USER");

        securityUser.setRoles(Set.of(role1, role2));
        assertEquals(2, securityUser.getRoles().size());
        assertTrue(securityUser.getRoles().contains(role1));
        assertTrue(securityUser.getRoles().contains(role2));
    }

    @Test
    void testAddRole() {
        SecurityRole role = new SecurityRole();
        role.setRoleName("ADMIN");

        securityUser.addRole(role);
        assertTrue(securityUser.getRoles().contains(role));
    }
}
