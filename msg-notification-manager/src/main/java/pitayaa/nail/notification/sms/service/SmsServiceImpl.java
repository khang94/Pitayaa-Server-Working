package pitayaa.nail.notification.sms.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.notification.common.KeyValueModel;
import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.notification.sms.SmsReceive;
import pitayaa.nail.msg.business.util.common.ValidatePhoneNumber;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.scheduler.JobHelper;
import pitayaa.nail.notification.scheduler.QuartJob;
import pitayaa.nail.notification.sms.api.nexmo.SendSmsNexmo;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.repository.SmsReceiveRepository;
import pitayaa.nail.notification.sms.repository.SmsRepository;

@Service
@Data
public class SmsServiceImpl implements SmsService {

	@Autowired
	NotificationHelper notificationHelper;

	@Autowired
	SmsRepository smsRepository;

	@Autowired
	SmsReceiveRepository smsReceiveRepo;

	@Autowired
	public SendSmsNexmo smsNexmoAPI;
	
	@Autowired
	public InteractionService interactionService;
	
	@Autowired
	public JobHelper jobHelper;

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

	/**
	 * 
	 */
	@Override
	public SmsModel initModelSms() throws Exception {
		SmsModel emailModel = (SmsModel) notificationHelper.createModelStructure(new SmsModel());

		return emailModel;
	}
	
	@Override
	public SmsReceive processResponseFromCustomer(SmsReceive smsReceive) throws Exception {
		LOGGER.info("Handle Sms Receive with Message ID [" + smsReceive.getMessage() + "]");
		SmsModel smsOriginal = interactionService.findSmsByResponseKeyAndPhoneNumber(smsReceive);
		
		// Check is customer reply to this sms
		boolean isSmsReply = interactionService.isMessageReplyByCustomer(smsOriginal);
		
		if(smsOriginal == null){
			return null;
		}
		
		if (isSmsReply){
			LOGGER.info("This message with id [{}] has been already reply . This reply is invalid and cannot process ...", smsOriginal.getUuid());
			return null;
		}
		
		// Auto response to customer
		autoResponseToCustomer(smsReceive);
		
		// Collect response & update to customer
		collectResponseFromCustomerAndUpdate(smsReceive.getMessage() , smsOriginal);
		
		// Update original sms
		smsOriginal.getInteractionData().setSmsReceive(smsReceive);
		
		// Update sms
		smsOriginal = smsRepository.save(smsOriginal);
		
		return smsOriginal.getInteractionData().getSmsReceive();
	}
	
	private void collectResponseFromCustomerAndUpdate(String messageRespond , SmsModel smsOriginal) throws Exception {
		
		String getKeyResponse = "";
		if(messageRespond.contains(SmsConstant.VALUE_DELIVER_STOP) && !messageRespond.contains(SmsConstant.VALUE_DELIVER_UNSTOP)){
			getKeyResponse = SmsConstant.VALUE_DELIVER_STOP;
		} else if (messageRespond.contains(SmsConstant.VALUE_DELIVER_UNSTOP)){
			getKeyResponse = SmsConstant.VALUE_DELIVER_UNSTOP;
		} else if (messageRespond.contains(SmsConstant.VALUE_DELIVER_CONFIRM)){
			getKeyResponse = SmsConstant.VALUE_DELIVER_CONFIRM;
		} else if (messageRespond.contains(SmsConstant.VALUE_DELIVER_CANCEL)){
			getKeyResponse = SmsConstant.VALUE_DELIVER_CANCEL;
		}
		
		try {
			jobHelper.processResponseForCustomer(smsOriginal.getModuleId(), getKeyResponse);
			LOGGER.info("Process response [{}] for customer Id [{}] successfully ",smsOriginal.getModuleId() , getKeyResponse);
		} catch (Exception ex){
			LOGGER.info("There is an error while processing repsonse from customer...[{}] ", ex.getMessage());
		}

	}

	
	@Override
	public SmsModel autoResponseToCustomer(SmsReceive smsReceive) throws Exception{
		
		// Build body response
		SmsModel smsResponse = interactionService.buildSmsAutoResponseToCustomer(smsReceive);
		
		// Response to Customer
		smsResponse = this.sendSms(smsResponse);
		
		return smsResponse;
	}
	
	

	@Override
	public SmsModel createSms(SmsModel smsModel) throws Exception {

		// Default empty sms receive management
		smsModel.getInteractionData().setSmsReceive(null);

		// Save POJO
		return smsRepository.save(smsModel);
	}

	@Override
	public SmsModel findOne(UUID uid) throws Exception {
		LOGGER.info("Find email with ID [" + uid + "]");

		// Refresh Email In Old Thread
		return smsRepository.findOne(uid);
	}

	@Override
	public Properties getPropertiesSmsConfig() {

		// Get config properties for Email
		// String phoneNumber =
		// this.getValueProperties(SmsConstant.PHONE_NUMBER);
		String phoneNumberName = this
		 .getValueProperties(SmsConstant.PHONE_NUMBER_NAME);
		//String srcPhone = this.getValueProperties(SmsConstant.SOURCE_PHONE);
		String isAuth = this.getValueProperties(SmsConstant.SMS_AUTH);
		String host = this.getValueProperties(SmsConstant.SMS_HOST);
		String port = this.getValueProperties(SmsConstant.SMS_PORT);
		String apiKey = this.getValueProperties(SmsConstant.API_KEY);
		String apiSecret = this.getValueProperties(SmsConstant.API_SECRET);
		String sourcePhone = this.getValueProperties(SmsConstant.SOURCE_PHONE);
		String versionApi = this.getValueProperties(SmsConstant.VERSION_API);
		LOGGER.info("Getting properties config for sms service : host = [" + host + "], \n" + "port = [" + port
				+ "] with is authentication = [" + isAuth + "]");

		// Assign value to properties
		Properties properties = new Properties();
		//properties.setProperty(SmsConstant.SOURCE_PHONE, srcPhone);
		// properties.setProperty(SmsConstant.PHONE_NUMBER, phoneNumber);
		 properties.setProperty(SmsConstant.PHONE_NUMBER_NAME,
				 phoneNumberName);
		properties.setProperty(SmsConstant.SMS_AUTH, isAuth);
		properties.setProperty(SmsConstant.SMS_HOST, host);
		properties.setProperty(SmsConstant.SMS_PORT, port);
		properties.setProperty(SmsConstant.API_KEY, apiKey);
		properties.setProperty(SmsConstant.API_SECRET, apiSecret);
		properties.setProperty(SmsConstant.SOURCE_PHONE, sourcePhone);
		properties.setProperty(SmsConstant.VERSION_API, versionApi);
		LOGGER.info("Get properties config successfully !");

		return properties;

	}

	@Override
	public SmsModel sendSms(SmsModel smsModel) throws IOException {

		// Get properties config for sms
		Properties properties = this.getPropertiesSmsConfig();

		// Check template
		String templateId = smsModel.getMeta().getTemplateId();
		String templateMessage = (templateId != null) ? this.findTemplateInClasspath(templateId)
				: smsModel.getHeader().getMessage();

		// Binding data
		smsModel.getHeader().setMessage(templateMessage);

		// Get phone number
		smsModel.getHeader().setFromPhone(properties.getProperty(SmsConstant.SOURCE_PHONE));

		// Call API
		if (smsNexmoAPI == null) {
			smsNexmoAPI = QuartJob.applicationContext.getBean(SendSmsNexmo.class);
		}

		// Validate phone number
		appendCodeAreaToPhone(smsModel);
		
		smsModel = smsNexmoAPI.sendSmsAPI(smsModel, properties.getProperty(SmsConstant.API_KEY),
				properties.getProperty(SmsConstant.API_SECRET));


		// Update sms to db log
		if (smsRepository == null) {
			smsRepository = QuartJob.applicationContext.getBean(SmsRepository.class);
		}
		

		
		smsModel = smsRepository.save(smsModel);

		return smsModel;
	}
	
	private SmsModel appendCodeAreaToPhone(SmsModel smsModel) {
		String to = smsModel.getHeader().getToPhone();
		to = ValidatePhoneNumber.appendCodeAreaToPhone(to);
		smsModel.getHeader().setToPhone(to);
		
		return smsModel;
	}
	
	@Override
	public SmsModel saveSms(SmsModel smsModel) throws IOException {

		if (smsRepository == null) {
			smsRepository = QuartJob.applicationContext.getBean(SmsRepository.class);
		}
		smsModel = smsRepository.save(smsModel);

		return smsModel;
	}

	@Override
	public String getValueProperties(String propertiesName) {

		Properties prop = new Properties();
		InputStream input = null;
		String propertiesValue = null;
		try {
			input = SmsServiceImpl.class.getClassLoader().getResourceAsStream(SmsConstant.PATH_SMS_CONFIG_NEXMO);
			prop.load(input);
			propertiesValue = prop.getProperty(propertiesName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertiesValue;
	}

	@Override
	public String getSmsTemplateConfig(String propertiesName) {
		Properties prop = new Properties();
		InputStream input = null;
		String propertiesValue = null;
		try {
			input = SmsServiceImpl.class.getClassLoader().getResourceAsStream(SmsConstant.PATH_SMS_CONFIG_TEMPLATE);
			prop.load(input);
			propertiesValue = prop.getProperty(propertiesName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertiesValue;
	}

	@Override
	public String findTemplateInClasspath(String templateId) throws IOException {

		String templateFileName = "";
		String path = "";

		// Get Template Name
		if (SmsConstant.SMS_APPOINTMENT.equalsIgnoreCase(templateId)) {
			templateFileName = this.getSmsTemplateConfig(SmsConstant.SMS_APPOINTMENT_TEMPLATE);
			path = SmsConstant.PATH_SMS_TEMPLATE + SmsConstant.SLASH + templateFileName
					+ SmsConstant.TEMPLATE_FILE_SMS_EXTENSION;
		} else if (SmsConstant.TEMPLATE_SMS_RESPONSE.equalsIgnoreCase(templateId)){
			templateFileName = this.getSmsTemplateConfig(SmsConstant.SMS_RESPONSE_TEMPLATE);
			path = SmsConstant.PATH_SMS_TEMPLATE + SmsConstant.SLASH + templateFileName
					+ SmsConstant.TEMPLATE_FILE_SMS_EXTENSION;
		} else if (SmsConstant.TEMPLATE_SMS_RESPONSE_STOP.equalsIgnoreCase(templateId)){
			path = SmsConstant.PATH_SMS_TEMPLATE + SmsConstant.SLASH + templateId
					+ SmsConstant.TEMPLATE_FILE_SMS_EXTENSION;
		} else if (SmsConstant.TEMPLATE_SMS_RESPONSE_UNSTOP.equalsIgnoreCase(templateId)){
			templateFileName = this.getSmsTemplateConfig(SmsConstant.SMS_RESPONSE_TEMPLATE);
			path = SmsConstant.PATH_SMS_TEMPLATE + SmsConstant.SLASH + templateId
					+ SmsConstant.TEMPLATE_FILE_SMS_EXTENSION;
		}
		LOGGER.info("Find template [" + templateFileName + "] in path [" + path + "]");

		InputStream input = SmsServiceImpl.class.getClassLoader().getResourceAsStream(path);

		String content = IOUtils.toString(input, StandardCharsets.UTF_8);

		return content;
	}

	@Override
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@Override
	public SmsModel initAppointmentSms(SmsModel smsBody) {

		// Get Config Data from template
		String dynamicData = this.getSmsTemplateConfig(SmsConstant.SMS_APPOINTMENT_DATA);
		String[] wordLoad = dynamicData.split(",");

		// Init Word Load
		List<KeyValueModel> wordLoadKey = new ArrayList<KeyValueModel>();
		for (int i = 0; i < wordLoad.length; i++) {
			KeyValueModel keyValue = new KeyValueModel();
			keyValue.setKey(wordLoad[i]);
			wordLoadKey.add(keyValue);
		}

		smsBody.setWordBinding(wordLoadKey);

		return smsBody;
	}

	private String bindingDataSms(SmsModel smsBody, String contentTemplate) {
		for (KeyValueModel word : smsBody.getWordBinding()) {

			if (contentTemplate.contains(word.getKey()) && (word.getKey() != null) && (word.getValue() != null)) {
				contentTemplate = contentTemplate.replaceAll(word.getKey(), word.getValue());
			}
		}
		return contentTemplate;
	}

	private SmsModel buildSmsModel(String templateId, SmsModel smsBody) throws IOException {
		String smsContent = "";
		if (SmsConstant.SMS_APPOINTMENT.equalsIgnoreCase(templateId)) {
			smsContent = this.bindingDataSms(smsBody, smsContent);
			smsBody.getHeader().setMessage(smsContent);
			
		} else {
			smsContent = this.findTemplateInClasspath(templateId);
			smsBody.getHeader().setMessage(smsContent);
		}
		return smsBody;
	}
	
	@Override
	public List<SmsModel> findAll (String moduleId , String messageFor){
		if (smsRepository == null) {
			smsRepository = QuartJob.applicationContext.getBean(SmsRepository.class);
		}
		return smsRepository.findAllSms(moduleId , messageFor);
	}
	
	

}
