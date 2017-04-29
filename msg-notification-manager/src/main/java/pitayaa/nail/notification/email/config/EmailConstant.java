package pitayaa.nail.notification.email.config;

import pitayaa.nail.notification.common.NotificationConstant;

public class EmailConstant extends NotificationConstant{
	
	
	/* Config firebase */
	public static final String EMAIL_MANAGER = "EmailManager";
	
	/* Operation for email */
	public static final String OPERATION_DELIVER = "DELIVER";
	public static final String OPERATION_CREATE = "CREATE";
	
	/* Email properties */
	public static final String SMTP_AUTH = "mail.smtp.auth";
	public static final String STARTTLS = "mail.smtp.starttls.enable";
	public static final String SMTP_HOST = "mail.smtp.host";
	public static final String SMTP_PORT = "mail.smtp.port";
	public static final String DEBUG = "mail.debug";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	/* Email properties path */
	public static final String PATH_MAIL_CONFIG = "properties/email_config.properties";
	
	/* Status Email */
	public static final String STATUS_EMAIL_DELIVERED = "DELIVERED";
	public static final String STATUS_EMAIL_NOT_DELIVERED = "NOT_DELIVERED";
	public static final String STATUS_EMAIL_FAILED_DELIVER = "FAILED_DELIVERED";
	
	public static final String TEMPLATE_FILE_EXTENSION = ".html";
	
	/* Email Template Path */
	public static final String TEMPLATE_EMAIL_PATH = "email_template/";
}
