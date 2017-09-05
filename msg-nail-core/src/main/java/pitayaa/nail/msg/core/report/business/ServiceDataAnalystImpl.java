package pitayaa.nail.msg.core.report.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.report.detail.ServiceData;
import pitayaa.nail.domain.report.elements.KeyData;
import pitayaa.nail.domain.report.elements.ReportServiceData;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.msg.core.appointment.service.AppointmentService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.common.TimeUtils;

@Service
public class ServiceDataAnalystImpl implements ServiceDataAnalyst {
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	CoreHelper coreHelper;
	
	@Override
	public ReportServiceData getServicesData(String salonId , String from , String to) throws Exception {
		
		// Get period
		Date fromDate = TimeUtils.getEndDate(from);
		Date toDate = TimeUtils.getEndDate(to);
		
		List<Appointment> appointments = appointmentService.findAllAppointmentByConditions(salonId, fromDate, toDate);
		List<ServiceModel> servicesUsed = this.getTotalServicesUsed(appointments);
		List<KeyData> keyDatas = this.getTopOrderService(appointments,salonId, from, to, 5);
		
		ReportServiceData reportData = (ReportServiceData) coreHelper.createModelStructure(new ReportServiceData());
		

		reportData.setServicesData(buildServiceData(servicesUsed.size() , keyDatas));
		reportData.setSalonId(salonId);
		reportData.setTotalServices(servicesUsed.size());
		
		return reportData;
	}
	
	private List<ServiceModel> getTotalServicesUsed(List<Appointment> appointments){
		
		List<ServiceModel> services = new ArrayList<>();
		appointments.stream().forEach(appmt ->{
			appmt.getServicesGroup().stream().forEach(service ->{
				services.add(service);
			});
		});
		
		return services;
	}
	
	private List<ServiceData> buildServiceData(int size , List<KeyData> keyDatas){
		
		List<ServiceData> services = new ArrayList<>();
		for(KeyData keyData : keyDatas){
			
			ServiceData data = new ServiceData();
			
			data.setServiceId(keyData.getUuid());
			data.setServiceName(keyData.getName());
			
			data.setNumber(keyData.getCounter());
			
			// Get rating
			Double rating = Double.valueOf(keyData.getCounter()/Double.valueOf(size));
			rating = coreHelper.roundDoubleValue(rating, 4);
			
			data.setRate(rating);
			data.setPercentage(coreHelper.getPercentage(rating));
			
			services.add(data);
		}
		return services;
	}

	private List<KeyData> getTopOrderService(List<Appointment> appointments, String salonId , String from , String to, int selectTop ) throws Exception{
		

		
		// Build hash map service to counter the time service have been used
		HashMap<KeyData , Integer> topServices = new HashMap<KeyData , Integer>();
		
		appointments.stream().forEach(appmt ->{
				
			appmt.getServicesGroup().stream().forEach(service ->{
				
				KeyData keyData = new KeyData();
				
				keyData.setUuid(service.getUuid().toString());
				keyData.setName(service.getServiceName());
				
				Integer counter = topServices.get(keyData);
				counter = (counter == null) ? 1 : (counter + 1);
				
				topServices.put(keyData, counter);
				
			});
		});
		
		List<KeyData> orderData = buildKeyDataSorted(topServices);
		List<KeyData> getTopServices = new ArrayList<>();
		if(selectTop < orderData.size()){
			for (KeyData data : orderData){
				if(getTopServices.size() < selectTop){
					getTopServices.add(data);
				} else {
					break;
				}
			}
		} else {
			return orderData;
		}

		// Get key data sorted
		return getTopServices;
		
	}
	

}
