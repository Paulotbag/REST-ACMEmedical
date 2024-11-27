package acmemedical.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity(name = "MedicalTraining")
@Table(name = "MEDICAL_TRAINING")
@NamedQueries({
		@NamedQuery(
				name = "MedicalTraining.ALL_MEDICAL_TRAININGS_QUERY",
				query = "SELECT mt FROM MedicalTraining mt"
		)
})
public class MedicalTraining extends PojoBase implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public static final String ALL_MEDICAL_TRAININGS_QUERY = "MedicalTraining.ALL_MEDICAL_TRAININGS_QUERY";

	@ManyToOne(cascade = CascadeType.MERGE, fetch = LAZY)
	@JoinColumn(name = "school_id") // Foreign key column from medical_certificate table.
	private MedicalSchool school;

	@OneToOne(mappedBy = "medicalTraining", cascade = CascadeType.ALL) // Field in the MedicalCertificate class.
	private MedicalCertificate certificate;

	@Embedded
	private DurationAndStatus durationAndStatus;

	public MedicalTraining() {
		durationAndStatus = new DurationAndStatus();
	}

	public MedicalSchool getMedicalSchool() {
		return school;
	}

	public void setMedicalSchool(MedicalSchool school) {
		this.school = school;
	}

	public MedicalCertificate getCertificate() {
		return certificate;
	}

	public void setCertificate(MedicalCertificate certificate) {
		this.certificate = certificate;
	}

	public DurationAndStatus getDurationAndStatus() {
		return durationAndStatus;
	}

	public void setDurationAndStatus(DurationAndStatus durationAndStatus) {
		this.durationAndStatus = durationAndStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(getId(), getDurationAndStatus());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof MedicalTraining otherMedicalTraining) {
			return Objects.equals(this.getId(), otherMedicalTraining.getId()) &&
					Objects.equals(this.getDurationAndStatus(), otherMedicalTraining.getDurationAndStatus());
		}
		return false;
	}
}
