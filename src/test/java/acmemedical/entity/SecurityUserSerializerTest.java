package acmemedical.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserSerializerTest {

    private SecurityUser securityUser;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        securityUser = new SecurityUser();
        securityUser.setId(1);
        securityUser.setUsername("testUser");

        SecurityRole adminRole = new SecurityRole();
        adminRole.setRoleName("ADMIN");

        SecurityRole userRole = new SecurityRole();
        userRole.setRoleName("USER");

        securityUser.setRoles(Set.of(adminRole, userRole));

        Physician physician = new Physician();
        physician.setId(100);
        securityUser.setPhysician(physician);
    }

    @Test
    void testCustomSerialization() throws JsonProcessingException {
        // Serialize SecurityUser to JSON
        String json = objectMapper.writeValueAsString(securityUser);

        // Expected JSON structure
        String expectedJson = """
                {
                    "id":1,
                    "username":"testUser",
                    "physician":{
                        "id":100
                    },
                    "roles":["ADMIN","USER"]
                }
                """;

        // Compare actual and expected JSON (ignoring whitespace)
        assertEquals(expectedJson.replaceAll("\\s", ""), json.replaceAll("\\s", ""));
    }
}
