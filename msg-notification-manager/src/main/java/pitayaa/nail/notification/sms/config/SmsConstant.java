package pitayaa.nail.notification.sms.config;

import pitayaa.nail.notification.common.NotificationConstant;

public class SmsConstant extends NotificationConstant {

	/* Config firebase */
	public static final String SMS_MANAGER = "SmsManager";
	public static final String SMS_LOG = "SmsLog";

	/* Operation for sms */
	public static final String OPERATION_DELIVER = "DELIVER";
	public static final String OPERATION_CREATE = "CREATE";

	/* Sms properties plivo */
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String PHONE_NUMBER_NAME = "phoneNumberName";
	public static final String SMS_HOST = "sms.host";
	public static final String SMS_PORT = "sms.port";
	public static final String SMS_AUTH = "sms.auth";
	public static final String AUTH_ID = "authId";
	public static final String AUTH_TOKEN = "authToken";

	public static final String VERSION_API = "versionAPI";
	
	/* Sms properties for nexmo */
	public static final String API_KEY = "apiKey";
	public static final String API_SECRET = "apiSecret";

	/* Sms properties path */
	public static final String PATH_SMS_CONFIG_PLIVO = "properties/sms_config_plivo.properties";
	public static final String PATH_SMS_CONFIG_NEXMO = "properties/sms_config_nexmo.properties";
	
	public static final String SLASH = "/";
	
	/* Sms Config Template */
	public static final String PATH_SMS_TEMPLATE = "template/sms";
	public static final String PATH_SMS_CONFIG_TEMPLATE = "template/sms/SmsTemplateConfig.properties";
	public static final String SMS_APPOINTMENT_DATA = "sms.appointment.data";
	public static final String SMS_APPOINTMENT_TEMPLATE = "sms.appointment.template";
	public static final String SMS_RESPONSE_TEMPLATE = "sms.response.template";
	
	/* Syntax SMS Gateway API */
	public static final String SOURCE_PHONE = "src";
	public static final String DESTINY_PHONE = "dst";
	public static final String TEXT = "text";
	public static final String URL = "url";
	public static final String METHOD = "method";
	
	
	/* Status Sms */
	public static final String STATUS_SMS_DELIVERED = "DELIVERED";
	public static final String STATUS_SMS_NOT_DELIVERED = "NOT_DELIVERED";
	public static final String STATUS_SMS_FAILED_DELIVER = "FAILED_DELIVERED";
	public static final String STATUS_SMS_TEMPORARY_FAILED = "TEMPORARY_FAILED_DELIVERED";

	public static final String TEMPLATE_FILE_EXTENSION = ".text";
	public static final String TEMPLATE_FILE_SMS_EXTENSION = ".html";

	/* Sms Template Path */
	public static final String TEMPLATE_SMS_PATH = "sms_template/";
	
	/* Sms Template Format */
	public static final String TEMPLATE_SMS_APPOINTMENT = "SmsAppointment";
	public static final String TEMPLATE_SMS_RESPONSE = "SmsResponse";
	public static final String TEMPLATE_SMS_RESPONSE_STOP = "SmsResponseStop";
	public static final String TEMPLATE_SMS_RESPONSE_UNSTOP = "SmsResponseUnstop";
	
	/* Sms Feedback from customers */
	public static final String RESPONSE_STOP = "STOP";
	public static final String RESPONSE_UNSTOP = "UNSTOP";
	
	/* RECEIVE SMS */
	public static final String PHONE_SENDER = "msisdn"; 
	public static final String PHONE_TO = "to";
	public static final String MESSAGE_ID = "messageId";
	public static final String CONTENT_MESSAGE = "text";
	public static final String TYPE_MESSAGE = "type";
	public static final String TIMESTAMP_MESSAGE = "message-timestamp";
	
	/* Keyword to deliver */
	public static final String KEY_DELIVER_STOP = "#STOP";
	public static final String KEY_DELIVER_UNSTOP = "#UNSTOP";
	public static final String KEY_DELIVER_CONFIRM = "#CONFIRM";
	public static final String KEY_DELIVER_CANCEL = "#CANCEL";
	
	/** VALUE of Keyword to deliver */
	public static final String VALUE_DELIVER_STOP = "STOP";
	public static final String VALUE_DELIVER_UNSTOP = "UNSTOP";
	public static final String VALUE_DELIVER_CONFIRM = "CONFIRM";
	public static final String VALUE_DELIVER_CANCEL = "CANCEL";
	
	
	

	
}
