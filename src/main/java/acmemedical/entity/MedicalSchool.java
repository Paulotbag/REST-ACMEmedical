/********************************************************************************************************
 * File:  MedicalSchool.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @implemented by Azadeh Sadeghtehrani
 *
 */
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;

/**
 * The persistent class for the medical_school database table.
 */
@Entity
@Table(name = "medical_school")
@NamedQueries({
		@NamedQuery(
				name = "MedicalSchool.ALL_MEDICAL_SCHOOLS_QUERY_NAME",
				query = "SELECT ms FROM MedicalSchool ms"
		),
		@NamedQuery(
				name = "MedicalSchool.SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME",
				query = "SELECT ms FROM MedicalSchool ms WHERE ms.id = :param1"
		),
		@NamedQuery(
				name = "MedicalSchool.IS_DUPLICATE_QUERY_NAME",
				query = "SELECT COUNT(ms) FROM MedicalSchool ms WHERE ms.name = :param1"
		)
})
@DiscriminatorColumn(name = "public", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "isPublic"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = PublicSchool.class, name = "true"),
		@JsonSubTypes.Type(value = PrivateSchool.class, name = "false")
})
@AttributeOverride(name = "id", column = @Column(name = "school_id"))
public abstract class MedicalSchool extends PojoBase implements Serializable {

	public static final String IS_DUPLICATE_QUERY_NAME = "MedicalSchool.IS_DUPLICATE_QUERY_NAME";
	public static final String SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME = "MedicalSchool.SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME";
	public static final String ALL_MEDICAL_SCHOOLS_QUERY_NAME = "MedicalSchool.ALL_MEDICAL_SCHOOLS_QUERY_NAME";
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalTraining> medicalTrainings = new HashSet<>();

	@Column(name = "public", insertable = false, updatable = false)
	private boolean isPublic;

	/**
	 * Default constructor
	 */
	public MedicalSchool() {
		super();
	}

	/**
	 *
	 * @param isPublic
	 */
	public MedicalSchool(boolean isPublic) {
		this();
		this.isPublic = isPublic;
	}


	/**
	 *
	 * @return boolean isPublic
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 *
	 * @return Set<MedicalTraining> medicalTrainings
	 */
	public Set<MedicalTraining> getMedicalTrainings() {
		return medicalTrainings;
	}

	/**
	 *
	 * @param medicalTrainings
	 */
	public void setMedicalTrainings(Set<MedicalTraining> medicalTrainings) {
		this.medicalTrainings = medicalTrainings;
	}

	/**
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @return int prime * result + Objects.hash(getId(), getName())
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(getId(), getName());
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (obj instanceof MedicalSchool otherMedicalSchool) {
			return Objects.equals(this.getId(), otherMedicalSchool.getId()) &&
					Objects.equals(this.getName(), otherMedicalSchool.getName());
		}
		return false;
	}
}
