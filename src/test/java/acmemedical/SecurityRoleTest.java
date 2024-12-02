package acmemedical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acmemedical.entity.SecurityRole;
import acmemedical.entity.SecurityUser;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityRoleTest {

    private SecurityRole role;
    private SecurityUser user;

    @BeforeEach
    void setUp() {
        role = new SecurityRole();
        role.setId(1);
        role.setRoleName("ADMIN");

        user = new SecurityUser();
        user.setId(101);
        user.setUsername("testUser");

        Set<SecurityUser> users = new HashSet<>();
        users.add(user);
        role.setUsers(users);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getRoleName());
        assertTrue(role.getUsers().contains(user));
    }

    @Test
    void testAddUserToRole() {
        SecurityUser newUser = new SecurityUser();
        newUser.setId(102);
        newUser.setUsername("newUser");

        role.addUserToRole(newUser);
        assertTrue(role.getUsers().contains(newUser));
        assertEquals(2, role.getUsers().size());
    }

    @Test
    void testEqualsAndHashCode() {
        SecurityRole sameRole = new SecurityRole();
        sameRole.setId(1);
        sameRole.setRoleName("ADMIN");

        assertEquals(role, sameRole);
        assertEquals(role.hashCode(), sameRole.hashCode());

        SecurityRole differentRole = new SecurityRole();
        differentRole.setId(2);
        assertNotEquals(role, differentRole);
    }

    @Test
    void testToString() {
        String expectedString = "SecurityRole [id=1, roleName=ADMIN]";
        assertEquals(expectedString, role.toString());
    }

    @Test
    void testSetUsers() {
        Set<SecurityUser> newUsers = new HashSet<>();
        SecurityUser user1 = new SecurityUser();
        user1.setId(103);
        user1.setUsername("user1");

        SecurityUser user2 = new SecurityUser();
        user2.setId(104);
        user2.setUsername("user2");

        newUsers.add(user1);
        newUsers.add(user2);
        role.setUsers(newUsers);

        assertEquals(2, role.getUsers().size());
        assertTrue(role.getUsers().contains(user1));
        assertTrue(role.getUsers().contains(user2));
    }
}
