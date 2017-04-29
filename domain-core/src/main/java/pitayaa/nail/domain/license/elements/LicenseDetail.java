package pitayaa.nail.domain.license.elements;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data
@Entity
public class LicenseDetail {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	@Version
	Long version;

	@OneToOne(cascade = CascadeType.ALL)
	private Price licensePrice;

	@Embedded
	private Term licenseTerm; // Detail of Term
	
	private String licenseType; // For salon , customer or account
	
	private String note;
}
