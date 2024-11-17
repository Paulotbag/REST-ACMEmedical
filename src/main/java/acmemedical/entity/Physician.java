/********************************************************************************************************
 * File:  Physician.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * 
 */
package acmemedical.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the physician database table.
 */
@SuppressWarnings("unused")

@Entity(name="Physician")
@Table(name="PHYSICIAN")
public class Physician extends PojoBase implements Serializable {
	public static final String ALL_PHYSICIANS_QUERY_NAME = null;
	private static final long serialVersionUID = 1L;

    public Physician() {
    	super();
    }


	@Column(name="firstName")
	private String firstName;


	@Column(name="lastName")
	private String lastName;


	@OneToMany(mappedBy = "physician", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalCertificate> medicalCertificates = new HashSet<>();


	@OneToMany(mappedBy = "physician", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Prescription> prescriptions = new HashSet<>();

//	//  I don't think we need this. but... This is reverse relationship
//	@OneToOne(mappedBy = "physician") // mappedBy indicates that this is the inverse side of the relationship with Security User.
//	private SecurityUser securityUser;

	/**
	 *
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 *
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 *
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 *
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 *
	 * @return medicalCertificates
	 */
    public Set<MedicalCertificate> getMedicalCertificates() {
		return medicalCertificates;
	}

	/**
	 *
	 * @param medicalCertificates
	 */
	public void setMedicalCertificates(Set<MedicalCertificate> medicalCertificates) {
		this.medicalCertificates = medicalCertificates;
	}

	/**
	 *
	 * @return prescriptions
	 */
    public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	/**
	 *
	 * @param prescriptions
	 */
	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	/**
	 *
	 * @param firstName
	 * @param lastName
	 */
	public void setFullName(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	//Inherited hashCode/equals is sufficient for this entity class

}