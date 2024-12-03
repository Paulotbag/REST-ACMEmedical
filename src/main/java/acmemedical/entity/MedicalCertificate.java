/********************************************************************************************************
 * File:  MedicalCertificate.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * Implemented: Paulo Granjeiro
 */
package acmemedical.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;

@SuppressWarnings("unused")

/**
 * The persistent class for the medical_certificate database table.
 */
@Entity(name = "MedicalCertificate")
@Table(name = "MEDICAL_CERTIFICATE")
@NamedQuery(name = "MedicalCertificate.findAll", query = "SELECT mc FROM MedicalCertificate mc")
@NamedQueries({
		@NamedQuery(name = MedicalCertificate.ID_CARD_QUERY_NAME, query = "SELECT mc FROM MedicalCertificate mc WHERE mc.id = :id")
})
@AttributeOverride(name = "id", column = @Column(name = "certificate_id"))
public class MedicalCertificate extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ID_CARD_QUERY_NAME = "MedicalCertificate.findById";

	@JsonManagedReference("certificate-training")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "training_id")//I used the foreign key column name from medical_certificate table. referencedColumnName is the name of the ID column in the training table.. Paulo: I removed the referencedColumnName="training_id"
	private MedicalTraining medicalTraining;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "physician_id")
	private Physician physician;

//	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY) //I am not sure about MERGE. Maybe should use ALL
//	@JoinColumn(name = "physician_id") //I used the foreign key column name from medical_certificate table.
//	private Physician owner;


	@Basic
	@Column(name = "signed")
	private byte signed;

	/**
	 * Default Constructor
	 */
	public MedicalCertificate() {
		super();
	}

	/**
	 * Constructor MeficalCertificate
	 * @param medicalTraining
	 * @param owner
	 * @param signed
	 */
	public MedicalCertificate(MedicalTraining medicalTraining, Physician owner, byte signed) {
		this();
		this.medicalTraining = medicalTraining;
		this.physician = owner;
		this.signed = signed;
	}

	/**
	 *
	 * @return medicalTraining
	 */
	public MedicalTraining getMedicalTraining() {
		return medicalTraining;
	}

	/**
	 *
	 * @param medicalTraining
	 */
	public void setMedicalTraining(MedicalTraining medicalTraining) {
		this.medicalTraining = medicalTraining;
	}

	/**
	 *
	 * @return owner
	 */
	public Physician getOwner() {
		return physician;
	}

	/**
	 *
	 * @param owner
	 */
	public void setOwner(Physician owner) {
		this.physician = owner;
	}

	/**
	 *
	 * @return signed
	 */
	public byte getSigned() {
		return signed;
	}

	/**
	 *
	 * @param signed
	 */
	public void setSigned(byte signed) {
		this.signed = signed;
	}

	/**
	 *
	 * @param signed
	 */
	public void setSigned(boolean signed) {
		this.signed = (byte) (signed ? 0b0001 : 0b0000);
	}
	
	//Inherited hashCode/equals is sufficient for this entity class

}