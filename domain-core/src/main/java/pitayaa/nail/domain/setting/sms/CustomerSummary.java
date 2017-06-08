package pitayaa.nail.domain.setting.sms;

import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.common.Address;
import pitayaa.nail.domain.common.Contact;
import pitayaa.nail.domain.customer.elements.CustomerDetail;

@Data
@Entity
public class CustomerSummary {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	@Embedded
	private CustomerDetail customerDetail;

	@Embedded
	private Contact contact;

	@Embedded
	private Address address;
	
	private String customerRefID;

}
