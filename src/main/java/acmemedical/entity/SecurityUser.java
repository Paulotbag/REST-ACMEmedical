/********************************************************************************************************
 * File:  SecurityUser.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * 
 */
package acmemedical.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")

/**
 * User class used for (JSR-375) Jakarta EE Security authorization/authentication
 */

@Entity(name = "SecurityUser")
@Table(name = "SECURITY_USER")
@NamedQuery(
        name = "SecurityUser.userByName",
        query = "SELECT u FROM SecurityUser u WHERE u.username = :param1"
)
public class SecurityUser implements Serializable, Principal {
    /** Explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "username")
    protected String username;

    @Column(name = "password_hash")
    protected String pwHash;

    @OneToOne
    @JoinColumn(name = "physician_id", referencedColumnName="id")  // This is the column name of the Foreign Key
    protected Physician physician;



    @ManyToMany
    @JoinTable(
            name = "user_has_role",   // Name of the intermediate table
            joinColumns = @JoinColumn(name = "user_id"),  // Foreign key for the user_id (the one that comes from SecurityUser)
            inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key for the role_id (the one that comes from SecurityRole table)
    )
    protected Set<SecurityRole> roles = new HashSet<SecurityRole>();


    public SecurityUser() {
        super();
    }

    /**
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return String
     */
    public String getPwHash() {
        return pwHash;
    }

    /**
     *
     * @param pwHash
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    // TODO SU07 - Setup custom JSON serializer
    public Set<SecurityRole> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     */
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    /**
     *
     * @return Physician
     */
    public Physician getPhysician() {
        return physician;
    }

    /**
     *
     * @param physician
     */
    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    // Principal
    /**
     *
     * @return String
     */
    @Override
    public String getName() {
        return getUsername();
    }

    /**
     *
     * @return int
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        // Only include member variables that really contribute to an object's identity
        // i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
        // they shouldn't be part of the hashCode calculation
        return prime * result + Objects.hash(getId());
    }

    /**
     *
     * @param obj {@code Principal} to compare with.
     *
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SecurityUser otherSecurityUser) {
            // See comment (above) in hashCode():  Compare using only member variables that are
            // truly part of an object's identity
            return Objects.equals(this.getId(), otherSecurityUser.getId());
        }
        return false;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SecurityUser [id = ").append(id).append(", username = ").append(username).append("]");
        return builder.toString();
    }
    
}
