package pitayaa.nail.domain.membership;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.customer.elements.CustomerDetail;
import pitayaa.nail.domain.customer.elements.CustomerMembership;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.membership.elements.RewardInformation;
import pitayaa.nail.domain.redeem.TransactionRedeem;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class MembershipManagement {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	@Embedded
	private CustomerDetail customerDetail;

	@OneToOne(cascade = CascadeType.ALL)
	private CustomerMembership customerMembership;
	
	@OneToOne(cascade = CascadeType.ALL)
	private RewardInformation rewardInformation;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@OrderColumn
	private List<TransactionRedeem> transactionHistory;
	
	private String customerId;
	
	private String salonId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

}
