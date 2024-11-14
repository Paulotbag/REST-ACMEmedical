/********************************************************************************************************
 * File:  PrivateSchool.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("0")  // Specifies the value to be used for the "discriminator" column in the database for this entity type.

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrivateSchool extends MedicalSchool implements Serializable {
	private static final long serialVersionUID = 1L;

	public PrivateSchool() {
		super(false);

	}
}