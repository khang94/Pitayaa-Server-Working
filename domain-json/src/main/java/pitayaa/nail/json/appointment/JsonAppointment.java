package pitayaa.nail.json.appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.appointment.elements.JsonNotificationSignal;
import pitayaa.nail.json.customer.JsonCustomer;
import pitayaa.nail.json.employee.JsonEmployee;
import pitayaa.nail.json.packages.JsonPackageModel;
import pitayaa.nail.json.promotion.JsonPromotion;
import pitayaa.nail.json.service.JsonServiceModel;

@Data
public class JsonAppointment {
	
	private UUID uuid;
	
	private JsonCustomer customer;
	
	private JsonEmployee employee;
	
	private List<JsonServiceModel> servicesGroup;
	
	private List<JsonPackageModel> packagesGroup;
	
	private JsonPromotion promotion;
	
	private String appointmentType;
	
	private Boolean isConfirm;
	
	private List<JsonNotificationSignal> notifyToCustomer;
	
	private List<JsonNotificationSignal> notifyToEmployee;

	private String status;
	
	private Date createdDate;
	
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	
	private Date startTime;
	private Date endTime;
	
	private String timezone;
	private String salonId;
	
	
	

}
