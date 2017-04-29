package pitayaa.nail.json.customer;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.common.JsonAddress;
import pitayaa.nail.json.common.JsonContact;
import pitayaa.nail.json.customer.elements.JsonCustomerDetail;
import pitayaa.nail.json.customer.elements.JsonCustomerMembership;
import pitayaa.nail.json.customer.elements.JsonCustomerPayment;

@Data
public class JsonCustomer {

	private UUID uuid;

	private JsonCustomerDetail customerDetail;

	private JsonContact contact;

	private JsonAddress address;

	//private JsonCustomerPayment jsonCustomerPayment;

	//private JsonCustomerMembership jsonCustomerMembership;

	private String username;
	private String password;

	private Date createdDate;

	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

}
