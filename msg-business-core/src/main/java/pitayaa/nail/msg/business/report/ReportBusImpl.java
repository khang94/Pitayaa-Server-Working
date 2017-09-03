package pitayaa.nail.msg.business.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.report.detail.CustomerData;
import pitayaa.nail.domain.report.elements.ReportCustomerData;
import pitayaa.nail.msg.business.constant.CustomerConstant;
import pitayaa.nail.msg.business.helper.BusinessHelper;

@Service
public class ReportBusImpl implements ReportBus {
	
	@Autowired
	BusinessHelper businessHelper;

	@Override
	public ReportCustomerData initCustomerData() throws Exception{
		ReportCustomerData customerData = (ReportCustomerData) businessHelper.createModelStructure(new ReportCustomerData());
		
		Integer totalType = 5;
		
		List<CustomerData> customers = new ArrayList<>();
		CustomerData data = new CustomerData();
		data.setCustomerType(CustomerConstant.CUSTOMER_TYPE_NEW);
		data.setNumber(0);
		data.setRate(0.0);
		customers.add(data);
		
		data = new CustomerData();
		data.setCustomerType(CustomerConstant.CUSTOMER_TYPE_REFERRAL);
		data.setNumber(0);
		data.setRate(0.0);
		customers.add(data);
		
		data = new CustomerData();
		data.setCustomerType(CustomerConstant.CUSTOMER_TYPE_RETURN);
		data.setNumber(0);
		data.setRate(0.0);
		customers.add(data);
		
		data = new CustomerData();
		data.setCustomerType(CustomerConstant.CUSTOMER_TYPE_APPOINTMENT);
		data.setNumber(0);
		data.setRate(0.0);
		customers.add(data);
		
		data = new CustomerData();
		data.setCustomerType(CustomerConstant.CUSTOMER_TYPE_CANCEL);
		data.setNumber(0);
		data.setRate(0.0);
		customers.add(data);
		
		customerData.setCustomersData(customers);
		customerData.setTotalType(totalType);
		
		return customerData;
	}
}
