/********************************************************************************************************
 * File:  Patient.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * 
 */
package acmemedical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")

/**
 * The persistent class for the patient database table.
 */
@Entity(name = "Patient")
@Table(name = "PATIENT")
@NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
public class Patient extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;


	@Basic(optional = false)
	@Column(name="first_name")
	private String firstName;


	@Basic(optional = false)
	@Column(name="last_name")
	private String lastName;


	@Column(name = "year_of_birth")
	private int year;


	@Column(name = "home_address")
	private String address;


	@Column(name = "height_cm")
	private int height;


	@Column(name = "weight_kg")
	private int weight;


	@Column(name = "smoker")
	private byte smoker;


	@JsonIgnore
	@OneToMany(mappedBy = "patient", cascade= CascadeType.ALL, fetch = FetchType.LAZY) //
	private Set<Prescription> prescriptions = new HashSet<>();

	public Patient() {
		super();
	}

	/**
	 * Constructor Patient
	 * @param firstName
	 * @param lastName
	 * @param year
	 * @param address
	 * @param height
	 * @param weight
	 * @param smoker
	 */
	public Patient(String firstName, String lastName, int year, String address, int height, int weight, byte smoker) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.year = year;
		this.address = address;
		this.height = height;
		this.weight = weight;
		this.smoker = smoker;
	}

	/**
	 *
	 * @param firstName
	 * @param lastName
	 * @param year
	 * @param address
	 * @param height
	 * @param weight
	 * @param smoker
	 * @return
	 */
	public Patient setPatient(String firstName, String lastName, int year, String address, int height, int weight, byte smoker) {
		setFirstName(firstName);
		setLastName(lastName);
		setYear(year);
		setAddress(address);
		setHeight(height);
		setWeight(weight);
		setSmoker(smoker);
		return this;
	}

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
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 *
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 *
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 *
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 *
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 *
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 *
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 *
	 * @return smoker
	 */
	public byte getSmoker() {
		return smoker;
	}

	/**
	 *
	 * @param smoker
	 */
	public void setSmoker(byte smoker) {
		this.smoker = smoker;
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
	public void setPrescription(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	//Inherited hashCode/equals is sufficient for this Entity class

}