/********************************************************************************************************
 * File:  Medicine.java Course Materials CST 8277
 *
 * Author: Teddy Yap
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("unused")

/**
 * The persistent class for the medicine database table.
 */
@Entity(name = "Medicine")
@Table(name = "MEDICINE")
@NamedQuery(name = "Medicine.findAll", query = "SELECT m FROM Medicine m")
@AttributeOverride(name = "id", column = @Column(name = "medicine_id"))
public class Medicine extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "drug_name", nullable = false, length = 50)
	private String drugName;

	@Basic(optional = false)
	@Column(name = "manufacturer_name", nullable = false, length = 50)
	private String manufacturerName;

	@Basic(optional = false)
	@Column(name = "dosage_information", nullable = false, length = 100)
	private String dosageInformation;

	@Transient
	private String chemicalName;

	@Transient
	private String genericName;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "medicine")
	private Set<Prescription> prescriptions = new HashSet<>();

	/**
	 * Default Constructor
	 */
	public Medicine() {
		super();
	}

	/**
	 * Constructor
	 * @param drugName
	 * @param manufacturerName
	 * @param dosageInformation
	 * @param prescriptions
	 */
	public Medicine(String drugName, String manufacturerName, String dosageInformation, Set<Prescription> prescriptions) {
		this();
		this.drugName = drugName;
		this.manufacturerName = manufacturerName;
		this.dosageInformation = dosageInformation;
		this.prescriptions = prescriptions;
	}

	/**
	 *
	 * @return String drugName
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 *
	 * @param drugName
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	/**
	 *
	 * @return String manufacturerName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 *
	 * @param manufacturerName
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 *
	 * @return String dosageInformation
	 */
	public String getDosageInformation() {
		return dosageInformation;
	}

	/**
	 *
	 * @param dosageInformation
	 */
	public void setDosageInformation(String dosageInformation) {
		this.dosageInformation = dosageInformation;
	}

	/**
	 *
	 * @return String chemicalName
	 */
	public String getChemicalName() {
		return chemicalName;
	}

	/**
	 *
	 * @param chemicalName
	 */
	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	/**
	 *
	 * @return String genericName
	 */
	public String getGenericName() {
		return genericName;
	}

	/**
	 *
	 * @param genericName
	 */
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	/**
	 *
	 * @return Set<Prescription> prescriptions
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

	// Helper method to add a Prescription to the Medicine

	/**
	 *
	 * @param prescription
	 */
	public void addPrescription(Prescription prescription) {
		this.prescriptions.add(prescription);
		prescription.setMedicine(this);
	}

	// Helper method to remove a Prescription from the Medicine

	/**
	 *
	 * @param prescription
	 */
	public void removePrescription(Prescription prescription) {
		this.prescriptions.remove(prescription);
		prescription.setMedicine(null);
	}

	/**
	 *
	 * @param drugName
	 * @param manufacturerName
	 * @param dosageInformation
	 */
	public void setMedicine(String drugName, String manufacturerName, String dosageInformation) {
		setDrugName(drugName);
		setManufacturerName(manufacturerName);
		setDosageInformation(dosageInformation);
	}

	/**
	 *
	 * @return int Objects.hash(getId(), drugName, manufacturerName)
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getId(), drugName, manufacturerName);
	}

	/**
	 *
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Medicine))
			return false;
		Medicine other = (Medicine) obj;
		return Objects.equals(getId(), other.getId()) &&
			   Objects.equals(drugName, other.drugName) &&
			   Objects.equals(manufacturerName, other.manufacturerName);
	}

	/**
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		return "Medicine [id=" + getId() + ", drugName=" + drugName + ", manufacturerName=" + manufacturerName +
			   ", dosageInformation=" + dosageInformation + "]";
	}
}