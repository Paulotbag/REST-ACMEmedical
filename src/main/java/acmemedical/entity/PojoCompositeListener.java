/********************************************************************************************************
 * File:  PojoCompositeListener.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * 
 */
package acmemedical.entity;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@SuppressWarnings("unused")
public class PojoCompositeListener { 

    // PCL01 - Use @PrePersist to specify actions before the entity is INSERT'd into the database.
    @PrePersist
    public void setCreatedOnDate(PojoBaseCompositeKey<?> pojoBaseComposite) {
        LocalDateTime now = LocalDateTime.now();
        // PCL02 - Set the 'created' field before the entity is inserted into the database.
        pojoBaseComposite.setCreated(now);
    }

    // PCL03 - Use @PreUpdate to specify actions before the entity is UPDATE'd into the database.
    @PreUpdate
    public void setUpdatedDate(PojoBaseCompositeKey<?> pojoBaseComposite) {
        // PCL04 - Set the 'updated' field before the entity is updated in the database.
        pojoBaseComposite.setUpdated(LocalDateTime.now());
    }
}