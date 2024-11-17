/********************************************************************************************************
 * File:  PojoBase.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * 
 */ 
package acmemedical.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Abstract class that is base of (class) hierarchy for all @Entity classes
 */
@MappedSuperclass  // PB01 - Defines this class as the superclass of all entities.
@EntityListeners(PojoCompositeListener.class)  // PB03 - Registers listener class for entity lifecycle events.
@Access(AccessType.FIELD)  // PB02 - Instructs JPA to place all annotations on fields.
public abstract class PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id  // PB04 - Identifies 'id' as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PB04 - Configures automatic generation of IDs.
    protected int id;

    @Version  // PB05 - Specifies that 'version' is used for optimistic locking.
    protected int version;

    @Column(updatable = false)  // PB06 - Marks 'created' as a non-updatable column in the database.
    protected LocalDateTime created;

    @Column  // PB07 - Specifies that 'updated' is a column in the database.
    protected LocalDateTime updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
    
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    /**
     * Very important:  Use getter's for member variables because JPA sometimes needs to intercept those calls<br/>
     * and go to the database to retrieve the value
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        /* Enhanced instanceof - yeah!
         * As of JDK 14, no need for additional 'silly' cast:
            if (animal instanceof Cat) {
                Cat cat = (Cat) animal;
                cat.meow();
                // Other class Cat operations ...
            }
         */
        if (obj instanceof PojoBase otherPojoBase) {
            // See comment (above) in hashCode():  Compare using only member variables that are
            // truly part of an object's identity
            return Objects.equals(this.getId(), otherPojoBase.getId());
        }
        return false;
    }
}