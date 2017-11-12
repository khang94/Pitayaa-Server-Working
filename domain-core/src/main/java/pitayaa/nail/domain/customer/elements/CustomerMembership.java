package pitayaa.nail.domain.customer.elements;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
public class CustomerMembership {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;
	private String status;
	private String note;
	private String memberType;
	private Double lastSpending;
	private Double spending;
	private Integer point;
	private Integer rating;
	private Integer totalTimeVisit;
	
	private String membershipId;

}
