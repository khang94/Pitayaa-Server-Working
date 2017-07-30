package pitayaa.nail.msg.business.customer;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.msg.business.constant.CustomerConstant;

@Service
public class CustomerServiceBusinessImpl implements CustomerServiceBusiness{
	
	@Override
	public Customer initDefaultCustomer(Customer customerBody){
		
		// Default Information
		//customerBody.
		//customerBody.getCustomerDetail().setFullName(CustomerConstant.NAME_DEFAULT);
		//customerBody.getCustomerDetail().setFirstName(CustomerConstant.NAME_DEFAULT);
		//customerBody.getCustomerDetail().setLastName(CustomerConstant.NAME_DEFAULT);
		customerBody.getCustomerDetail().setNote(CustomerConstant.NAME_DEFAULT);
		
		return customerBody;
	}
}
