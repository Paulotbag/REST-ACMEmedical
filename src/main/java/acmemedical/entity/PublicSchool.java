/********************************************************************************************************
 * File:  PublicSchool.java Course materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

//PUSC01 - Add missing annotations; value 1 is public and value 0 is private.
@Entity
@DiscriminatorValue("1")  // Specifies the value to be used for the "discriminator" column in the database for this entity type.

//PUSC02 - Add JSON annotation if needed.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PublicSchool extends MedicalSchool implements Serializable {
	private static final long serialVersionUID = 1L;

	public PublicSchool() {
		super(true);
	}
}
