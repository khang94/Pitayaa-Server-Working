package pitayaa.nail.msg.core.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.report.elements.ReportCustomerData;
import pitayaa.nail.msg.business.report.ReportBus;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.TimeUtils;
import pitayaa.nail.msg.core.customer.service.CustomerService;

@Service
public class ReportServiceImpl implements ReportService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	CustomerService customerService;

	@Autowired
	ReportBus reportBus;

	@Override
	public ReportCustomerData getCustomersData(String salonId , String from , String to) throws Exception {

		// Init report data
		ReportCustomerData customerData = reportBus.initCustomerData();
		customerData.setSalonId(salonId);
		

		if(!"".equalsIgnoreCase(from) && !"".equalsIgnoreCase(to)){
			// Get period time
			Date startDate = TimeUtils.getStartDate(from);
			Date endDate = TimeUtils.getEndDate(to);
			
			getCustomersDataByTime(customerData, salonId, startDate, endDate);
		} else {
			getCustomersDataAll(customerData, salonId);
		}

		return customerData;
	}
	
	private ReportCustomerData getCustomersDataAll(ReportCustomerData customerData , String salonId){
		
		// Caculate business
		List<Customer> totalCustomers = customerService.findAllCustomer(salonId);
		
		customerData.getCustomersData().stream().forEach(data -> {
			List<Customer> customerDetail = customerService.findAllCustomer(salonId,
					data.getCustomerType().toUpperCase());
			data.setNumber(customerDetail.size());

			// Get rating
			Double rating = Double.valueOf(customerDetail.size()) / Double.valueOf(totalCustomers.size());
			rating = (double) Math.round(rating * 100) / 100;

			data.setRate(rating);

			// Get percentage
			rating = rating * 100;
			data.setPercentage((new Double(rating)).intValue());
		});
		
		customerData.setTotalCustomers(totalCustomers.size());
		
		return customerData;
	}
	
	private ReportCustomerData getCustomersDataByTime(ReportCustomerData customerData , String salonId ,Date from , Date to) throws Exception{
		
		// Caculate business
		List<Customer> totalCustomers = customerService.findCustomerByCondition(salonId , from , to);
		
		customerData.getCustomersData().stream().forEach(data -> {
			List<Customer> customerDetail = new ArrayList<>();
			
			try {
				customerDetail = customerService.findCustomerByCondition(salonId,
							data.getCustomerType().toUpperCase() , from , to);
			} catch (Exception ex) {
				LOGGER.info("ERROR [{}]" , ex.getMessage());
			}
			data.setNumber(customerDetail.size());

			// Get rating
			Double rating = Double.valueOf(customerDetail.size()) / Double.valueOf(totalCustomers.size());
			rating = (double) Math.round(rating * 100) / 100;

			data.setRate(rating);

			// Get percentage
			rating = rating * 100;
			data.setPercentage((new Double(rating)).intValue());
		});
		
		customerData.setTotalCustomers(totalCustomers.size());
		
		return customerData;
	}
}
