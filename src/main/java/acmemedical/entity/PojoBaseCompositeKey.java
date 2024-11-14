/********************************************************************************************************
 * File:  PojoBaseCompositeKey.java Course Materials CST 8277
 *
 * @param <ID> - type of composite key used
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

/**
 * Abstract class that is the base of (class) hierarchy for all @Entity classes using composite keys
 */
@MappedSuperclass  // PC01 - Defines this class as the superclass of all entities with composite keys.
@EntityListeners(PojoBaseCompositeKeyListener.class)  // PC03 - Registers listener class for entity lifecycle events.
@Access(AccessType.FIELD)  // PC02 - Instructs JPA to place all annotations on fields.
public abstract class PojoBaseCompositeKey<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Version  // PC04 - Specifies that 'version' is used for optimistic locking.
    protected int version;

    @Column(updatable = false)  // PC05 - Marks 'created' as a non-updatable column in the database.
    protected LocalDateTime created;

    @Column  // PC06 - Specifies that 'updated' is a column in the database.
    protected LocalDateTime updated;

    public abstract ID getId();

    public abstract void setId(ID id);

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

        if (obj instanceof PojoBaseCompositeKey<?> otherPojoBaseComposite) {
            // Compare using only member variables that are truly part of an object's identity
            return Objects.equals(this.getId(), otherPojoBaseComposite.getId());
        }
        return false;
    }
}
