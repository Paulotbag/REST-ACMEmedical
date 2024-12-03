/********************************************************************************************************
 * File:  Physician.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * 
 */
package acmemedical.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@NamedQuery(name = "Physician.findAll", query = "SELECT p FROM Physician p")
public class Physician extends PojoBase implements Serializable {
	public static final String ALL_PHYSICIANS_QUERY_NAME = null;
	private static final long serialVersionUID = 1L;

    public Physician() {
    	super();
    }


	@Basic(optional = false)
	@Column(name="first_name")
	private String firstName;


	@Basic(optional = false)
	@Column(name="last_name")
	private String lastName;

	@JsonIgnore
	@OneToMany(mappedBy = "physician", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //Paulo: I changed mappedBy from "physician" to "owner"
	private Set<MedicalCertificate> medicalCertificates = new HashSet<>();



	@JsonManagedReference("physician-prescriptions")
	@OneToMany(mappedBy = "physician", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Prescription> prescriptions = new HashSet<>();

//	//  I don't think we need this. but... This is reverse relationship
//	@JsonBackReference("physician-user")
//	@OneToOne(mappedBy = "physician")
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