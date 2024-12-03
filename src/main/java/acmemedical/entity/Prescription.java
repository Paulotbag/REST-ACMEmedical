package acmemedical.entity;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "prescription")
@Access(AccessType.FIELD)
@NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p")
public class Prescription extends PojoBaseCompositeKey<PrescriptionPK> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L; // No @Serial annotation for Java 8–11 compatibility

	@EmbeddedId
	private PrescriptionPK id;

	@MapsId("physicianId")
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "physician_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference("physician-prescriptions")
	private Physician physician;

	@MapsId("patientId")
	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
	private Patient patient;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicine_id", nullable = false) //Paulo: I removed the referencedColumnName = "id" from here.
	private Medicine medicine;

	@Column(name = "number_of_refills")
	private int numberOfRefills;

	@Column(length = 100, name = "prescription_information")
	private String prescriptionInformation;


	public Prescription() {
		id = new PrescriptionPK();
	}

	/**
	 *
	 * @return PrescriptionPK id
	 */
	@Override
	public PrescriptionPK getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 */
	@Override
	public void setId(PrescriptionPK id) {
		this.id = id;
	}

	/**
	 *
	 * @return Physician  - physician
	 */
	public Physician getPhysician() {
		return physician;
	}

	/**
	 *
	 * @param physician
	 */
	public void setPhysician(Physician physician) {
		id.setPhysicianId(physician.getId());
		this.physician = physician;
	}

	/**
	 *
	 * @return Patient patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 *
	 * @param patient
	 */
	public void setPatient(Patient patient) {
		id.setPatientId(patient.getId());
		this.patient = patient;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public int getNumberOfRefills() {
		return numberOfRefills;
	}

	public void setNumberOfRefills(int numberOfRefills) {
		this.numberOfRefills = numberOfRefills;
	}

	public String getPrescriptionInformation() {
		return prescriptionInformation;
	}

	public void setPrescriptionInformation(String prescriptionInformation) {
		this.prescriptionInformation = prescriptionInformation;
	}

	// The default hashCode and equals are sufficient for this entity class
}
