package pitayaa.nail.msg.business.util.common;

public class CommonError {
	public static final Integer SUCCESS = 200;
	
	public static final Integer ERR_COMMON = 201;
	public static final Integer ERR_ACCOUNT_DISABLE = 205;
	public static final Integer ERR_INVALID_ACCOUNT = 207;
	public static final Integer ERR_INVALID_PASSWORD = 210;
	public static final Integer ERR_INVALID_IMEI = 211;
	public static final Integer ERR_INVALID_SERIAL = 212;
	public static final Integer ERR_INVALID_IMEI_OR_SERIAL = 213;
	public static final Integer ERR_NOT_LOGIN = 215;

	public static final Integer ERR_CANNOT_RESETDB = 241;
	public static final Integer ERR_INVALID_ACCOUNT_STATUS = 242;
	public static final Integer INFO_MAX_NUMBER_LOGIN_FAIL = 243;
	public static final Integer ERR_CONSTRAIN_VIOLATION = 244;
	
	public static final Integer ERR_CANNOT_ACCESS_DB = 245;
	public static final Integer ERR_INVALID_ACCOUNT_ROLE_TYPE = 246;
	
	public static final Integer ERR_UPLOAD_IMAGE_INVALID_FILE_TYPE = 300;
	public static final Integer ERR_UPLOAD_IMAGE_MEDIA_NOT_EXISTS = 301;
	
	public static final Integer ERR_ACCOUNT_EXPIRY_DATE = 202;
	public static final Integer ERR_ACCOUNT_EXPIRY_N_DATE = 203;
	
	public static final Integer ERR_LICENSE_INVALID_LICENSE= 400;
	public static final Integer ERR_LICENSE_LIMIT_SMS = 401;
	
	public static final Integer ERR_DEVICE_NOT_REGISTER= 500;
	public static final Integer ERR_DEVICE_IS_EXPIRE = 501;
	public static final Integer ERR_DEVICE_IS_EXIT = 502;
	
	public static final Integer ERR_EXPIRE_USE_SMS = 600;
	public static final Integer ERR_NO_SMS_REMIND = 601;
	
	public static final Integer ERR_SHOP_EXPIRE = 701;

}
