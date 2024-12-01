package acmemedical;

import com.fasterxml.jackson.databind.ObjectMapper;

import acmemedical.entity.Physician;
import acmemedical.entity.SecurityRole;
import acmemedical.entity.SecurityUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserTest {

    private SecurityUser user;
    private Physician physician;
    private SecurityRole role;

    @BeforeEach
    void setUp() {
        user = new SecurityUser();
        user.setId(1);
        user.setUsername("testUser");
        user.setPwHash("hashedPassword");

        physician = new Physician();
        physician.setId(101);
        physician.setLastName("Dr. Smith");
        user.setPhysician(physician);

        role = new SecurityRole();
        role.setRoleName("ADMIN");
        Set<SecurityRole> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("hashedPassword", user.getPwHash());
        assertEquals(physician, user.getPhysician());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void testAddRole() {
        SecurityRole newRole = new SecurityRole();
        newRole.setRoleName("USER");
        user.addRole(newRole);
        assertTrue(user.getRoles().contains(newRole));
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void testEqualsAndHashCode() {
        SecurityUser sameUser = new SecurityUser();
        sameUser.setId(1);
        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());

        SecurityUser differentUser = new SecurityUser();
        differentUser.setId(2);
        assertNotEquals(user, differentUser);
    }

    @Test
    void testToString() {
        String expectedString = "SecurityUser [id=1, username=testUser]";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testJsonSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        // Verify JSON content
        assertTrue(jsonString.contains("\"id\":1"));
        assertTrue(jsonString.contains("\"username\":\"testUser\""));
        assertTrue(jsonString.contains("\"physician\":{\"id\":101"));  // Assuming Physician has a getId method
        assertTrue(jsonString.contains("\"roles\":[\"ADMIN\"]"));
    }
}
