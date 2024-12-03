package acmemedical.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.SerializerProvider;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "SecurityUser.FIND_BY_PHYSICIAN_ID",
                query = "SELECT su FROM SecurityUser su WHERE su.physician.id = :physicianId"
        ),
        @NamedQuery(
                name = "SecurityUser.userByName",
                query = "SELECT su FROM SecurityUser su WHERE su.username = :param1"
        )
})
@Entity
@Table(name = "SECURITY_USER")
@JsonSerialize(using = SecurityUser.SecurityUserSerializer.class)
public class SecurityUser implements Serializable, Principal {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "username", nullable = false, unique = true)
    protected String username;

    @Column(name = "password_hash", nullable = false)
    protected String pwHash;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id", referencedColumnName = "id", nullable = true)
    protected Physician physician;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_has_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<SecurityRole> roles = new HashSet<>();

    public SecurityUser() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    public void addRole(SecurityRole role) {
        roles.add(role);
    }

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SecurityUser that = (SecurityUser) obj;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "SecurityUser{id=" + id + ", username='" + username + "'}";
    }

    public static class SecurityUserSerializer extends JsonSerializer<SecurityUser> {
        @Override
        public void serialize(SecurityUser user, JsonGenerator gen, SerializerProvider serializers) {
            try {
                gen.writeStartObject();
                gen.writeNumberField("id", user.getId());
                gen.writeStringField("username", user.getUsername());
                if (user.getPhysician() != null) {
                    gen.writeObjectField("physician", user.getPhysician());
                }
                gen.writeArrayFieldStart("roles");
                for (SecurityRole role : user.getRoles()) {
                    gen.writeString(role.getRoleName());
                }
                gen.writeEndArray();
                gen.writeEndObject();
            } catch (Exception e) {
                throw new RuntimeException("Error serializing SecurityUser", e);
            }
        }
    }
}
