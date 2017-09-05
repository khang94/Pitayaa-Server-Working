package pitayaa.nail.msg.core.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.report.ReportDocs;
import pitayaa.nail.domain.report.elements.ReportCustomerData;
import pitayaa.nail.domain.report.elements.ReportServiceData;
import pitayaa.nail.msg.core.appointment.service.AppointmentService;
import pitayaa.nail.msg.core.report.business.CustomerDataAnalyst;
import pitayaa.nail.msg.core.report.business.PromotionDataAnalyst;
import pitayaa.nail.msg.core.report.business.ServiceDataAnalyst;

@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	CustomerDataAnalyst customerAnalyst;

	@Autowired
	ServiceDataAnalyst serviceAnalyst;
	
	@Autowired
	PromotionDataAnalyst promotionAnalyst;

	@Autowired
	AppointmentService appointmentService;

	@Override
	public ReportCustomerData getCustomersData(String salonId, String from, String to) throws Exception {

		return customerAnalyst.getCustomersData(salonId, from, to);
	}

	@Override
	public ReportServiceData getServicesData(String salonId, String from, String to) throws Exception {

		return serviceAnalyst.getServicesData(salonId, from, to);

	}

	@Override
	public ReportDocs getPromotionReport(String salonId, String from, String to) throws Exception {
		
		return promotionAnalyst.getPromotionData(salonId, from, to);		
	}

}
