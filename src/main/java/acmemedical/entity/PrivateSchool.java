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

//PRSC01 - Add missing annotations; value 1 is public and value 0 is private.
@Entity
@DiscriminatorValue("0")  // Specifies the value to be used for the "discriminator" column in the database for this entity type.

//PRSC02 - Add JSON annotation if needed.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrivateSchool extends MedicalSchool implements Serializable {
	private static final long serialVersionUID = 1L;

	public PrivateSchool() {
		super(false);

	}
}