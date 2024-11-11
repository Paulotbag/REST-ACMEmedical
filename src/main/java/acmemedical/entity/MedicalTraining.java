/********************************************************************************************************
 * File:  MedicalTraining.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @implemented by Paulo Ricardo Gomes Granjeiro
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

@SuppressWarnings("unused")

/**
 * The persistent class for the medical_training database table.
 */

@Entity(name = "MedicalTraining")
@Table(name = "MEDICAL_TRAINING")
public class MedicalTraining extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;


	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id") //I used the foreign key column name from medical_certificate table.
	private MedicalSchool school;

	// TODO MT04 - Add annotations for 1:1.  What should be the cascade and fetch types?
	@OneToOne(mappedBy="medicalTraining", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //field in the MedicalCertificate class. Maybe review the fetch type
	private MedicalCertificate certificate;

	@Embedded
	private DurationAndStatus durationAndStatus;

	public MedicalTraining() {
		durationAndStatus = new DurationAndStatus();
	}

	/**
	 *
	 * @return MedicalSchool - school
	 */
	public MedicalSchool getMedicalSchool() {
		return school;
	}

	/**
	 *
	 * @param school
	 */
	public void setMedicalSchool(MedicalSchool school) {
		this.school = school;
	}

	/**
	 *
	 * @return MedicalCertificate - certificate
	 */
	public MedicalCertificate getCertificate() {
		return certificate;
	}

	/**
	 *
	 * @param certificate
	 */
	public void setCertificate(MedicalCertificate certificate) {
		this.certificate = certificate;
	}

	/**
	 *
	 * @return DurationAndStatus - durationAndStatus
	 */
	public DurationAndStatus getDurationAndStatus() {
		return durationAndStatus;
	}

	/**
	 *
	 * @param durationAndStatus
	 */
	public void setDurationAndStatus(DurationAndStatus durationAndStatus) {
		this.durationAndStatus = durationAndStatus;
	}
	
	//Inherited hashCode/equals NOT sufficient for this Entity class
	/**
	 * Very important:  Use getter's for member variables because JPA sometimes needs to intercept those calls<br/>
	 * and go to the database to retrieve the value
	 * @return prime * result + Objects.hash(getId(), getDurationAndStatus())
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// Only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		
		// include DurationAndStatus in identity
		return prime * result + Objects.hash(getId(), getDurationAndStatus());
	}

	/**
	 *
	 * @param obj
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
		if (obj instanceof MedicalTraining otherMedicalTraining) {
			// See comment (above) in hashCode():  Compare using only member variables that are
			// truly part of an object's identity
			return Objects.equals(this.getId(), otherMedicalTraining.getId()) &&
				Objects.equals(this.getDurationAndStatus(), otherMedicalTraining.getDurationAndStatus());
		}
		return false;
	}
}
