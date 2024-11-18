package acmemedical.entity;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@SuppressWarnings("unused")
public class PojoListener {

	/**
	 * PL01: Lifecycle event method called just before the entity is persisted (INSERT operation).
	 *
	 * @param pojoBase The entity object being persisted.
	 */
	@PrePersist
	public void setCreatedOnDate(PojoBase pojoBase) {
		LocalDateTime now = LocalDateTime.now();
		// Initialize both 'created' and 'updated' fields during entity insertion
		pojoBase.setCreated(now); // Sets the creation timestamp
		pojoBase.setUpdated(now); // Sets the updated timestamp initially to match created timestamp
	}

	/**
	 * PL03: Lifecycle event method called just before the entity is updated (UPDATE operation).
	 *
	 * @param pojoBase The entity object being updated.
	 */
	@PreUpdate
	public void setUpdatedDate(PojoBase pojoBase) {
		// Updates only the 'updated' field during entity update
		pojoBase.setUpdated(LocalDateTime.now());
	}
}
