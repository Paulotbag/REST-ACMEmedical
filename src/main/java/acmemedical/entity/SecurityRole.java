/********************************************************************************************************
 * File:  SecurityRole.java Course Materials CST 8277
 *
 * @author Teddy Yap
 *
 */
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
@NamedQuery(
        name = "SecurityRole.FIND_BY_NAME",
        query = "SELECT sr FROM SecurityRole sr WHERE sr.roleName = :roleName"
)
@SuppressWarnings("unused")
@Entity // SR01 - Defines this class as a JPA entity.
@Table(name = "security_role") // Maps the entity to the "security_role" table in the database.
public class SecurityRole implements Serializable {

    /** Explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L; // For Java 8–11, no @Serial is used here.

    @Id // SR02 - Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates primary key values.
    @Column(name = "id") // Maps to the "id" column in the database.
    protected int id;

    @Column(name = "role_name", nullable = false, unique = true) // SR03 - Maps to the "role_name" column.
    protected String roleName;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    protected Set<SecurityUser> users = new HashSet<>();

    /**
     *     Default constructor
     */
    public SecurityRole() {
        super();
    }

    /**
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SecurityUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SecurityUser> users) {
        this.users = users;
    }

    public void addUserToRole(SecurityUser user) {
        getUsers().add(user);
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
        if (obj instanceof SecurityRole otherSecurityRole) {
            // Compare using only fields relevant to identity.
            return Objects.equals(this.getId(), otherSecurityRole.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "SecurityRole [id=" + id + ", roleName=" + roleName + "]";
    }

}
