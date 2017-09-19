package pitayaa.nail.notification.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.base.KeyValue;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.notification.common.KeyValueModel;
import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.promotion.PromotionDataSms;
import pitayaa.nail.domain.promotion.PromotionKeyValue;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;
import pitayaa.nail.msg.business.util.common.RestTemplateHelper;
import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.common.SchedulerConstant;

@Service
public class JobHelper {

	public static final Logger LOGGER = LoggerFactory.getLogger(JobHelper.class);

	// @Autowired
	// RestTemplateHelper restTemplateHelper;

	private String getValueProperties(String propertiesName) {

		Properties prop = new Properties();
		InputStream input = null;
		String propertiesValue = null;
		try {
			input = AppointmentJob.class.getClassLoader().getResourceAsStream(NotificationConstant.PATH_APPOINTMENT);
			prop.load(input);
			propertiesValue = prop.getProperty(propertiesName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertiesValue;
	}

	/**
	 * Load list appointment
	 * 
	 * @return
	 */
	public List<Appointment> loadAppointments() {

		LOGGER.info("Call rest template .....Load list appointment .........");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put(NotificationConstant.STATUS_STRING, NotificationConstant.BUSINESS_STATUS_READY);

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.LOAD_LIST_APPOINTMENT);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Setting Sms : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Appointment>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Appointment>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}

	/**
	 * Load Get All Customer by Type & Salon
	 * 
	 * @return
	 */
	public List<CustomerSummary> loadCustomersByType(String salonId, String customerType) {

		LOGGER.info("Call rest template .....Load list customers .........");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put(NotificationConstant.SALON_ID, salonId);
		urlParameters.put(NotificationConstant.CUSTOMER_TYPE, customerType);
		urlParameters.put(NotificationConstant.OPERATION, NotificationConstant.OPERATION_REFRESH);

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.CUSTOMER_URI_SALON);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List List customers : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Customer>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		// Transform customers list to customer list summary
		List<Customer> customers = response.getBody();
		List<CustomerSummary> customersSummary = new ArrayList<CustomerSummary>();

		customers.stream().forEach(customer -> {
			CustomerSummary customerSummary = new CustomerSummary();
			customerSummary.setCustomerDetail(customer.getCustomerDetail());
			customerSummary.setContact(customer.getContact());
			customerSummary.setAddress(customer.getAddress());
			customerSummary.setCustomerRefID(customer.getUuid().toString());
			customersSummary.add(customerSummary);
		});

		return customersSummary;
	}

	/**
	 * Add sms to queue
	 * 
	 * @param customer
	 * @param settingSms
	 * @return
	 */
	public SmsQueue addSmsQueue(CustomerSummary customer, SettingSms settingSms) {
		SmsQueue queue = new SmsQueue();

		// Execute business
		queue.setCustomerId(customer.getCustomerRefID());
		queue.setSettingSmsId(settingSms.getUuid().toString());
		queue.setIsSend(true);
		queue.setSmsType(settingSms.getKey());
		queue.setCustomerType(settingSms.getKey());
		queue.setTimeUpdateSetting(settingSms.getUpdatedDate());
		queue.setSalonId(settingSms.getSalonId());
		queue.setSendTime(new Date());

		// Call API Function
		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.SMS_QUEUE);
		LOGGER.info("Get URL Load List Setting Sms : [" + url + "] to send request !");

		// Execute Request By Rest Template
		HttpEntity<SmsQueue> bodyRequest = new HttpEntity<>(queue, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SmsQueue> response = restTemplate.exchange(url, HttpMethod.POST, bodyRequest,
				new ParameterizedTypeReference<SmsQueue>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}
		return response.getBody();
	}

	/**
	 * Open queue sms
	 * 
	 * @param customerId
	 * @param settingSms
	 * @return
	 */
	public SmsQueue openQueue(String customerId, SettingSms settingSms) {

		// Call API Function
		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put("settingSmsId", settingSms.getUuid().toString());
		urlParameters.put("customerId", customerId);
		urlParameters.put("customerType", settingSms.getKey());

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.SMS_QUEUE);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Setting Sms : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<SmsQueue>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SmsQueue>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		List<SmsQueue> queues = response.getBody();

		if (queues.size() > 0) {
			return response.getBody().get(0);
		}

		return null;
	}

	/**
	 * Load list salon data
	 * 
	 * @return
	 */
	public List<Salon> loadListSalon() {
		LOGGER.info("Call rest template .....Load list appointment .........");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.SALON_URL);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Appointment : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Salon>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Salon>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}

	/**
	 * Load list customer by salon
	 * 
	 * @param salonId
	 * @return
	 */
	public List<Customer> loadListCustomerBySalon(String salonId) {
		LOGGER.info("Call rest template .....Load list customer ......... by salon id [" + salonId + "]");

		List<Customer> customerList = new ArrayList<Customer>();

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put(NotificationConstant.SALON_ID, salonId);
		urlParameters.put(NotificationConstant.OPERATION, NotificationConstant.OPERATION_REFRESH);

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.CUSTOMER_URI_SALON);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Appointment : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Customer>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>() {
				});
		if (response.getStatusCode().is2xxSuccessful() && response.getStatusCodeValue() == 200) {
			LOGGER.info("Get response successully from URL [" + url + "]");
			// customerList = (List<Customer>) response.getBody().getObject();
		}

		return response.getBody();
	}

	/**
	 * Load list setting sms
	 * 
	 * @return
	 */
	public List<SettingSms> loadSettingSms(String salonId) {

		LOGGER.info("Call rest template .....Load list setting sms .........");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put(NotificationConstant.SALON_ID, salonId);
		urlParameters.put(NotificationConstant.OPERATION, NotificationConstant.OPERATION_REFRESH);

		// url Variable
		Map<String, String> urlPathVariable = new HashMap<String, String>();

		String url = this.getValueProperties(NotificationConstant.SETTING_SMS_SALON);

		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Appointment : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<SettingSms>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SettingSms>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}

	/**
	 * Load list setting sms
	 * 
	 * @return
	 */
	public SmsModel sendSms(SmsModel smsBody) {

		LOGGER.info("Call rest template .....to send sms .........");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();

		// url Variable
		Map<String, String> urlPathVariable = new HashMap<String, String>();

		String url = this.getValueProperties(NotificationConstant.SMS_SEND);

		// RestTemplateHelper restTemplateHelper = new RestTemplateHelper();

		HttpEntity<SmsModel> bodyRequest = new HttpEntity<>(smsBody, headers);

		LOGGER.info("Get URL Send Sms : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SmsModel> response = restTemplate.exchange(url, HttpMethod.POST, bodyRequest,
				new ParameterizedTypeReference<SmsModel>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}

	/*
	 * Load list promotion code
	 */
	public List<Appointment> loadAppointment() {

		LOGGER.info("Call rest template .....Load list appointment .........");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put(NotificationConstant.STATUS_STRING, NotificationConstant.BUSINESS_STATUS_READY);

		// String url = this.urlLoadListAppm();
		String url = this.getValueProperties(NotificationConstant.LOAD_LIST_APPOINTMENT);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Appointment : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Appointment>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Appointment>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}

	/**
	 * Update appointment
	 * 
	 * @return
	 */
	public ResponseEntity<Appointment> updateAppointment(String appointmentId, Appointment appointmentUpdate) {

		LOGGER.info("Update appointment ID [" + appointmentId + "]");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();

		// Path variables
		Map<String, String> pathVars = new HashMap<String, String>();
		pathVars.put(NotificationConstant.ID_STRING, appointmentId);

		HttpEntity<Appointment> request = new HttpEntity<>(appointmentUpdate, headers);

		// Get URL
		String url = this.getValueProperties(NotificationConstant.UPDATE_APPOINTMENT);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlPathVariable(pathVars, url);
		LOGGER.info("Get URL Update Appointment : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Appointment> response = restTemplate.exchange(url, HttpMethod.PUT, request, Appointment.class,
				new ParameterizedTypeReference<Appointment>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}
		LOGGER.info("Update appointment success !");

		return response;
	}

	/**
	 * Load list appointment
	 * 
	 * @return
	 */
	public ResponseEntity<Salon> getSalonById(String Id) {

		LOGGER.info("Get Salon Information by Id [" + Id + "]");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();

		// Path variables
		Map<String, String> pathVars = new HashMap<String, String>();
		pathVars.put(NotificationConstant.ID_STRING, Id);

		// HttpEntity<String> request = new HttpEntity<>(input, createHeader());
		String url = this.getValueProperties(NotificationConstant.SALON_URI_ID);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlPathVariable(pathVars, url);
		LOGGER.info("Get Salon by URL : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Salon> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Salon>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response;
	}

	public Promotion getPromotionDeliver(String salonId, String type, String customerId) throws Exception {

		LOGGER.info("Get Promotion Deliver by salon Id [" + salonId + "]");

		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put("type", "emptyString");
		urlParameters.put("salonId", salonId);
		urlParameters.put("customerId", customerId);

		// Path variables
		Map<String, String> pathVars = new HashMap<String, String>();

		// HttpEntity<String> request = new HttpEntity<>(input, createHeader());
		String url = this.getValueProperties(NotificationConstant.PROMOTION_DELIVER);
		RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get Salon by URL : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Promotion> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Promotion>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}

	/**
	 * Bind data sms appointment
	 * 
	 * @param smsModel
	 * @param salon
	 * @param appointment
	 * @return
	 */

	public SmsModel bindDataSms(SmsModel smsModel, Salon salon, Appointment appointment) {
		LOGGER.info("Binding data for sms body .......");
		int count = 0;
		for (KeyValueModel keyValue : smsModel.getWordBinding()) {
			try {
				if (keyValue.getKey().equalsIgnoreCase("#CustomerName")) {
					keyValue.setValue(appointment.getCustomer().getCustomerDetail().getFirstName());
				}
			} catch (Exception ex) {
				LOGGER.info("[ERROR]  Customer name = null !");
			}
			try {
				if (keyValue.getKey().equalsIgnoreCase("#SalonName")) {
					keyValue.setValue(salon.getSalonDetail().getBusinessName());
				}
			} catch (Exception ex) {
				LOGGER.info("[ERROR] Salon name = null !");
			}
			try {
				if (keyValue.getKey().equalsIgnoreCase("#SalonPhone")) {
					keyValue.setValue(salon.getContact().getHomePhone());
				}
			} catch (Exception ex) {
				LOGGER.info("[ERROR] Salon Phone = null !");
			}
			try {
				if (keyValue.getKey().equalsIgnoreCase("#Date")) {
					keyValue.setValue(appointment.getStartTime().toString());
				}
			} catch (Exception ex) {
				LOGGER.info("[ERROR] Date = null !");
			}
			try {
				if (keyValue.getKey().equalsIgnoreCase("#Time")) {
					keyValue.setValue(appointment.getStartTime().toString());
				}
			} catch (Exception ex) {
				LOGGER.info("[ERROR] Time = null !");
			}
			smsModel.getWordBinding().set(count, keyValue);
			count++;
		}
		LOGGER.info("Finish binding data for SMS .............");
		return smsModel;

	}

	/*public SmsModel bindDataPromotionSms(SmsModel smsModel, Promotion promotion, CustomerSummary customer) {

		String content = smsModel.getHeader().getMessage();

		// Bind base infor

		// Customer Name
		if (content.contains(SchedulerConstant.KEYWORD_CUSTOMER_NAME)) {
			String fullname = customer.getCustomerDetail().getFirstName() + " "
					+ customer.getCustomerDetail().getLastName();
			content = content.replaceAll(SchedulerConstant.KEYWORD_CUSTOMER_NAME, fullname);
		}

		// Promotion Code
		if (content.contains(SchedulerConstant.KEYWORD_PROMOTION_CODE)) {
			content = content.replaceAll(SchedulerConstant.KEYWORD_PROMOTION_CODE, promotion.getCodeValue());
		}

		smsModel.getHeader().setMessage(content);
		return smsModel;

	}

	public SmsModel fulFillBodySms(SmsModel smsModel, CustomerSummary customer) throws Exception {

		// Get content
		String content = smsModel.getHeader().getMessage();

		// Init promotion & smsModel
		Promotion promotion = null;

		if (content.contains(SchedulerConstant.KEYWORD_CUSTOMER_NAME)
				|| content.contains(SchedulerConstant.KEYWORD_PROMOTION_CODE)) {
			promotion = this.getPromotionDeliver(smsModel.getSalonId(), "", smsModel.getModuleId());
			smsModel = this.bindDataPromotionSms(smsModel, promotion, customer);
		}
		return smsModel;
	}*/
	
	private PromotionDataSms getPromotionSmsMessage(PromotionDataSms promotionData) throws Exception {


		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}
		
		
		HttpEntity<PromotionDataSms> bodyRequest = new HttpEntity<>(promotionData, headers);


		String url = this.getValueProperties(NotificationConstant.PROMOTION_DELIVER);
		

		LOGGER.info("Get Promotion SMS by URL : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PromotionDataSms> response = restTemplate.exchange(url, HttpMethod.POST, bodyRequest,
				new ParameterizedTypeReference<PromotionDataSms>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}

		return response.getBody();
	}
	
	/**
	 * Bind data with keyword promotion
	 * @param promotionData
	 * @param content
	 * @return
	 */
	private String bindDataForSmsPromotion(PromotionDataSms promotionData , String content){
		
		for(KeyValue promoSms : promotionData.getKeyValues()){
			content = content.replaceAll(promoSms.getKey(), promoSms.getValue());
		}
		
		if(SchedulerConstant.KEYWORD_PROMOTION_CODE.equalsIgnoreCase(promotionData.getPromoKeyValue().getPromotionKey())){
			content = content.replaceAll(promotionData.getPromoKeyValue().getPromotionKey(), promotionData.getPromoKeyValue().getPromotionCode());
		}
		return content;
	}
	
	/**
	 * Fulfill all information need in the SMS
	 * @param smsModel
	 * @param customer
	 * @param settingSms
	 * @return
	 * @throws Exception
	 */
	public SmsModel fulFillBodySmsPromo(SmsModel smsModel , CustomerSummary customer, SettingSms settingSms) throws Exception{
		
		// Get content
		String content = smsModel.getHeader().getMessage();
		
		PromotionDataSms promotionData = this.initPromotionMessage(content, settingSms.getPromotionGroupId());
		promotionData.setSalonId(settingSms.getSalonId());
		promotionData.setCustomerId(customer.getCustomerRefID());
		
		promotionData = this.getPromotionSmsMessage(promotionData);
		
		content = this.bindDataForSmsPromotion(promotionData, content);
		smsModel.getHeader().setMessage(content);
		
		return smsModel;
	}
	
	

	/**
	 * Init promotion message
	 * @param message
	 * @param promotionGroupId
	 * @return
	 */
	private PromotionDataSms initPromotionMessage(String message , String promotionGroupId){
		KeyValue keyValue = null;
		PromotionKeyValue promoKeyValue = null;
		PromotionDataSms promotionData = new PromotionDataSms();;
		
		List<KeyValue> lstKey = new ArrayList<>();
		
		if(message.contains(SchedulerConstant.KEYWORD_CUSTOMER_NAME)){
			keyValue = new KeyValue();
			keyValue.setKey(SchedulerConstant.KEYWORD_CUSTOMER_NAME);
			lstKey.add(keyValue);
		}
		if(message.contains(SchedulerConstant.KEYWORD_SALON_NAME)){
			keyValue = new KeyValue();
			keyValue.setKey(SchedulerConstant.KEYWORD_SALON_NAME);
			lstKey.add(keyValue);
		}
		if(message.contains(SchedulerConstant.KEYWORD_SALON_EMAIL)){
			keyValue = new KeyValue();
			keyValue.setKey(SchedulerConstant.KEYWORD_SALON_EMAIL);
			lstKey.add(keyValue);
		}
		if(message.contains(SchedulerConstant.KEYWORD_PROMOTION_CODE)){
			promoKeyValue = new PromotionKeyValue();
			promoKeyValue.setPromotionKey(SchedulerConstant.KEYWORD_PROMOTION_CODE);
			promoKeyValue.setGroupId(promotionGroupId);
		}
		if(message.contains(SchedulerConstant.KEYWORD_SALON_PHONE)){
			keyValue = new KeyValue();
			keyValue.setKey(SchedulerConstant.KEYWORD_SALON_PHONE);
			lstKey.add(keyValue);
		}
		
		promotionData.setKeyValues(lstKey);
		promotionData.setPromoKeyValue(promoKeyValue);
		
		return promotionData;
		
	}

}
