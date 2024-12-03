/********************************************************************************************************
 * File:  MedicalTraining.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @implemented by Paulo Ricardo Gomes Granjeiro
 * 
 */
package acmemedical.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@SuppressWarnings("unused")

@Entity(name = "MedicalTraining")
@Table(name = "MEDICAL_TRAINING")
@NamedQuery(name = "MedicalTraining.findAll", query = "SELECT mt FROM MedicalTraining mt")
@NamedQuery(name = "MedicalTraining.findById", query = "SELECT mt FROM MedicalTraining mt WHERE mt.id = :id")
@AttributeOverride(name = "id", column = @Column(name = "training_id"))
public class MedicalTraining extends PojoBase implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_ID = null;


	@ManyToOne(cascade = CascadeType.MERGE, fetch = LAZY)
	@JoinColumn(name = "school_id") //I used the foreign key column name from medical_certificate table.
	private MedicalSchool school;

	@JsonBackReference("certificate-training")
	@OneToOne(mappedBy="medicalTraining", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //field in the MedicalCertificate class. Maybe review the fetch type
	private MedicalCertificate certificate;

	@Embedded
	private DurationAndStatus durationAndStatus;

	/**
	 * Default Constructor
	 */
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
	 * school
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
	 *  certificate
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
	 * durationAndStatus
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
	 * obj
	 * boolean
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