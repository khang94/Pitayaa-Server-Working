package pitayaa.nail.notification.common;

public class NotificationConstant {

	/* Type of Request */
	public static final String POST_REQUEST = "POST";
	public static final String GET_REQUEST = "GET";
	public static final String DELETE_REQUEST = "DELETE";
	public static final String PATCH_REQUEST = "PATCH";
	
	/* Firebase Config */
	public static final String SET = "set";
	//public static final String SERVICE_ACCOUNT_PATH = "config/pitayaa_account2.json";
	//public static final String URL_DATABASE = "https://pitayaa-3fd3a.firebaseio.com/";
	
	/* Rest template */
	public static final String ACCEPT_HEADER = "Accept";
	
	/* Slash */
	public static final String SLASH = "/";
	
	/* Config Appointment */
	public static final String PATH_APPOINTMENT = "properties/api_config.properties";
	
	/* Appointment */
	public static final String LOAD_LIST_APPOINTMENT = "appointment.loadlist";
	public static final String UPDATE_APPOINTMENT = "appointment.update";
	
	// Salon 
	public static final String SALON_URI_ID = "salon.getById";
	public static final String SALON_URL = "salon.getAll";
	
	/* Business Status Appointment */
	public static final String BUSINESS_STATUS_READY = "READY_TO_NOTIFY";
	public static final String BUSINESS_STATUS_PENDING = "PENDING_BY_CUSTOMER";
	public static final String BUSINESS_STATUS_CONFIRM = "CONFIRMED";
	public static final String BUSINESS_STATUS_SIGNAL_CUSTOMER = "SendToCustomer";
	public static final String BUSINESS_STATUS_SIGNAL_EMPLOYEE = "SendToEmployee";
	
	public static final String STATUS_STRING = "status";
	public static final String ID_STRING = "Id";
	
	/* Template Type */
	public static final String TEMPLATE_SMS_APPOINTMENT = "SMS_APPOINTMENT";
	
	/* Notification Constant */
	public static final String APPOINTMENT_JOB = "APPOINTMENT_JOB";
	public static final String APPOINTMENT_TRIGGER = "APPOINTMENT_TRIGGER";
	
	public static final String PROMOTION_JOB = "PROMOTION_JOB";
	public static final String PROMOTION_TRIGGER = "PROMOTION_TRIGGER";
	
	public static final String SETTING_SMS_SALON = "settingsms.bysalon";
	
	/* Customer URI */
	public static final String CUSTOMER_URI_SALON = "customer.bysalon";
	
	public static final String SALON_ID = "salonId";
	
	/* Send Sms api */
	public static final String SMS_SEND = "sms.send";
	
	/* Url Sms Queue */
	public static final String SMS_QUEUE = "sms.queue";
	
	/* Sms summary type */
	public static final String SMS_PROMOTION = "PROMOTION";
	public static final String SMS_APPOINTMENT = "APPOINTMENT";
	
	/* Sms for */
	public static final String SMS_FOR_CUSTOMER = "CUSTOMER";
	public static final String SMS_FOR_EMPLOYEE = "EMPLOYEE";
	
	/* Notify to customer type */
	public static final String CUSTOMER_NEW = "NEW";
	public static final String CUSTOMER_RETURN = "RETURN";
	public static final String CUSTOMER_REFERRAL = "REFERRAL";
	public static final String CUSTOMER_APPOINTMENT_REMIND = "APPOINTMENT_REMIND";
	public static final String CUSTOMER_PROMOTION = "PROMOTION";
}
