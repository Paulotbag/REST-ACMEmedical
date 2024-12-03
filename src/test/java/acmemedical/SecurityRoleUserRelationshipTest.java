package acmemedical;

import acmemedical.entity.SecurityRole;
import acmemedical.entity.SecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityRoleUserRelationshipTest {

    private SecurityRole securityRole;
    private SecurityUser user1;
    private SecurityUser user2;

    @BeforeEach
    void setUp() {
        securityRole = new SecurityRole();

        user1 = new SecurityUser();
        user1.setId(1);
        user1.setUsername("user1");

        user2 = new SecurityUser();
        user2.setId(2);
        user2.setUsername("user2");
    }

    @Test
    void testAddUserToRole() {
        securityRole.addUserToRole(user1);
        assertTrue(securityRole.getUsers().contains(user1));
    }

    @Test
    void testSetAndGetUsers() {
        securityRole.setUsers(Set.of(user1, user2));
        assertEquals(2, securityRole.getUsers().size());
        assertTrue(securityRole.getUsers().contains(user1));
        assertTrue(securityRole.getUsers().contains(user2));
    }

    @Test
    void testLazyLoadingUsers() {
        assertNotNull(securityRole.getUsers());
        assertTrue(securityRole.getUsers().isEmpty());
    }
}
