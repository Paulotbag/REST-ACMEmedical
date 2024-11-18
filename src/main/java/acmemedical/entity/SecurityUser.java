/********************************************************************************************************
 * File:  SecurityUser.java Course Materials CST 8277
 *
 * @author Teddy Yap
 *
 */
package acmemedical.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
@Entity(name = "SecurityUser") // Defines this class as a JPA entity.
@Table(name = "SECURITY_USER") // Maps this entity to the "SECURITY_USER" table in the database.
@NamedQuery(
        name = "SecurityUser.userByName",
        query = "SELECT u FROM SecurityUser u WHERE u.username = :param1"
)
public class SecurityUser implements Serializable, Principal {
    /** Explicit set serialVersionUID */
    @Serial
    private static final long serialVersionUID = 1L; // Removed @Serial for compatibility with Java 8–11.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates IDs.
    @Column(name = "id") // Maps this field to the "id" column in the database.
    protected int id;

    @Column(name = "username", nullable = false, unique = true) // Username must be unique and non-nullable.
    protected String username;

    @Column(name = "password_hash", nullable = false) // Password hash must be non-nullable.
    protected String pwHash;

    @OneToOne
    @JoinColumn(name = "physician_id", referencedColumnName = "id") // Maps to the "id" column in the Physician table.
    protected Physician physician;

    @ManyToMany
    @JoinTable(
            name = "user_has_role",   // Name of the intermediate table.
            joinColumns = @JoinColumn(name = "user_id"),  // Foreign key for "user_id" (SecurityUser).
            inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key for "role_id" (SecurityRole).
    )
    protected Set<SecurityRole> roles = new HashSet<>();

    // Default constructor
    public SecurityUser() {
        super();
    }

    // Getters and setters
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

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    public void addRole(SecurityRole role) {
        roles.add(role);
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    // Implements Principal interface
    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hash(getId());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SecurityUser otherSecurityUser) {
            return Objects.equals(this.getId(), otherSecurityUser.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "SecurityUser [id=" + id + ", username=" + username + "]";
    }
}
