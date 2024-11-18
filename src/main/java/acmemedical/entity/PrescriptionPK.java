/********************************************************************************************************
 * File:  PrescriptionPK.java Course Materials CST 8277
 *
 * @author Teddy Yap
 *
 */
package acmemedical.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@SuppressWarnings("unused")
@Embeddable // PRPK01 - Defines this class as embeddable in other entities.
@Access(AccessType.FIELD) // Specifies that JPA annotations will be applied to fields directly.
public class PrescriptionPK implements Serializable {

	// Default serial version id, required for serializable classes.
	@Serial
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "physician_id", nullable = false) // Maps to "physician_id" column in the database.
	private int physicianId;

	@Basic(optional = false)
	@Column(name = "patient_id", nullable = false) // Maps to "patient_id" column in the database.
	private int patientId;

	// Default constructor
	public PrescriptionPK() {}

	// Parameterized constructor for easier initialization
	public PrescriptionPK(int physicianId, int patientId) {
		this.physicianId = physicianId;
		this.patientId = patientId;
	}

	// Getters and setters
	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	/**
	 * Overridden hashCode method to ensure consistency with equals
	 * Includes fields that contribute to the unique identity of the object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // Updated from calling super.hashCode() to use consistent hashing logic
		result = prime * result + Objects.hash(getPhysicianId(), getPatientId());
		return result;
	}

	/**
	 * Overridden equals method for logical comparison of objects
	 * Includes only fields that are part of the object's identity.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true; // If both objects are the same reference, return true
		}
		if (obj == null) {
			return false; // If the other object is null, return false
		}
		if (obj instanceof PrescriptionPK otherPrescriptionPK) {
			// Compare using only fields relevant to the composite key
			return Objects.equals(this.getPhysicianId(), otherPrescriptionPK.getPhysicianId()) &&
					Objects.equals(this.getPatientId(), otherPrescriptionPK.getPatientId());
		}
		return false; // Not the same class
	}

	/**
	 * Overridden toString method for better readability during debugging/logging.
	 */
	@Override
	public String toString() {
		return "PrescriptionPK [physicianId=" + physicianId + ", patientId=" + patientId + "]";
	}
}
