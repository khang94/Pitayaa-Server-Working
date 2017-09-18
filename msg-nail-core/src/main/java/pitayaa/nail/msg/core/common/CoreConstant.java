package pitayaa.nail.msg.core.common;

public class CoreConstant {
	public static final String SET = "set";
	public static final String GET = "get";
	
	/* Core Constants HTTP STATUS */
	public static final String HTTP_STATUS_SUCCESS = "success";
	public static final int HTTP_CODE_SUCCESS = 200;
	
	public static final String HTTP_STATUS_ERROR = "error";
	public static final int HTTP_CODE_ERROR = 401;
	
	/* Category for group Type */
	public static final String CATEGORY_SERVICE = "service";
	public static final String CATEGORY_PACKAGE = "package";
	public static final String CATEGORY_PRODUCT = "product";
	
	/* Search Character Special */
	public static final String PATTERN_CHARACTER = "(\\w+?)(:|<|>)(\\w+?),";
	
	/* Path folder store */
	public static final String PATH_FILE_PROPERTIES = "properties/config_folder_store.properties";
	public static final String SUB_LEVEL = "sub_level";
	public static final String PARENT_FOLDER = "parent_folder";
	public static final String SUB_FOLDER_GENERIC = "_sub_folder_";
	public static final String PATH_FOLDER_STORE = "folder_store_path";
	
	public static final String SLASH = "/";
	public static final String UNDERLINE = "_";
	public static final String DOT = ".";
	
	/* View Constant */
	public static final String VIEW_SALON = "salon";
	public static final String VIEW_CUSTOMER = "customers";
	public static final String VIEW_EMPLOYEE = "employees";
	public static final String VIEW_PACKAGE = "packages";
	public static final String VIEW_SERVICE = "services";
	public static final String VIEW_CATEGORY = "categories";
	
	/* SubFOlder salon */
	public static final String SALON_SUB_FOLDER = "salon_sub_folder_0";
	
	public static final String BANNER_SUB_FOLDER = "banner";
	
	/* PATH OBJECT TO GET PATH IMAGE */
	public static final String STATIC_PATH = "STATIC_PATH";
	public static final String DYNAMIC_PATH = "DYNAMIC_PATH";
	
	/* Default name image */
	public static final String DEFAULT_IMAGE_NAME = "Default";
	public static final String DEFAULT_IMAGE_EXTENSION = ".png";
	
	/* Key word */
	public static final String OPERATION_REFRESH = "refresh";
	
	/* Customer target type */
	public static final String CUSTOMER_TYPE_LOYAL = "LOYAL_CUSTOMER";
	public static final String CUSTOMER_TYPE_VIP = "VIP_CUSTOMER";
	
	/* Customer type */
	public static final String CUSTOMER_TYPE_NEW = "NEW";
	public static final String CUSTOMER_TYPE_RETURN = "RETURN";
	public static final String CUSTOMER_TYPE_APPOINTMENT = "APPOINTMENT";
	public static final String CUSTOMER_TYPE_CANCEL = "CANCEL";
	public static final String CUSTOMER_TYPE_REFERRAL = "REFERRAL";
	
	public static final String OPERATION_SIGN_IN = "SIGN_IN";
	
	/* Promotion constant*/
	public static final String PROMOTION_CODE_ACTIVE = "ACTIVE";
	public static final String PROMOTION_CODE_INACTIVE = "INACTIVE";
	public static final String PROMOTION_CODE_SEND_OUT = "SEND_OUT";
	public static final String PROMOTION_CODE_EXPIRED = "EXPIRED";
	public static final String PROMOTION_CODE_USED = "USED";
	
	/* PERCENTAGE */
	public static final String PERCENTAGE = "%";
	
	/* Promotion Keyword */
	public static final String KEYWORD_SALON_NAME = "#SalonName";
	public static final String KEYWORD_SALON_PHONE = "#SalonPhone";
	public static final String KEYWORD_SALON_EMAIL = "#SalonEmail";
	public static final String KEYWORD_CUSTOMER_NAME = "#CustomerName";
	public static final String KEYWORD_PROMOTION_CODE = "#PromotionCode";
}
