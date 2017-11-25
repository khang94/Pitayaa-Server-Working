package pitayaa.nail.notification.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import pitayaa.nail.domain.appointment.elements.NotificationSignal;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;
import pitayaa.nail.msg.business.helper.TextHelper;
import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.promotion.business.PromotionJobBus;
import pitayaa.nail.notification.promotion.business.PromotionJobBusImpl;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.repository.SmsRepository;
import pitayaa.nail.notification.sms.service.InteractionService;
import pitayaa.nail.notification.sms.service.SmsService;
import pitayaa.nail.notification.sms.service.SmsServiceImpl;

public class PromotionJob implements Job {

	public final static Logger LOGGER = LoggerFactory.getLogger(PromotionJob.class);

	@Autowired
	JobHelper jobHelper;

	public static JobHelper jobHelperTest;

	@Autowired
	NotificationHelper notificationHelper;

	@Autowired
	SmsService smsService;
	
	@Autowired
	InteractionService interactionService;

	@Autowired
	PromotionJobBus promotionJobBus;

	public PromotionJob() {
		jobHelper = new JobHelper();
		notificationHelper = new NotificationHelper();
		smsService = new SmsServiceImpl();
		promotionJobBus = new PromotionJobBusImpl();
		LOGGER.info("Init Promotion Job");
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {

		// Get all salon map with customer
		HashMap<Salon, List<Customer>> mapSalonCustomer = this.settingSmsDetail();

		// Get all setting map with salon
		HashMap<Salon, List<SettingSms>> mapSettingSalon = this.notifySmsSetting(mapSalonCustomer);

		// Execute process
		this.executeNotifyProcess(mapSalonCustomer, mapSettingSalon);

	}

	public void executeNotifyProcess(HashMap<Salon, List<Customer>> mapSalonCustomer,
			HashMap<Salon, List<SettingSms>> mapSetting) {

		LOGGER.info("Execute notify process start.....");
		mapSetting.entrySet().stream().forEach(key -> {

			// Get setting list & customer in the same salon
			List<SettingSms> settingSms = this.filterSettingSms(mapSetting.get(key.getKey()));
			LOGGER.info(
					"Salon ID [" + key.getKey().getUuid() + "] has active [" + settingSms.size() + "] services sms");

			// Get list customer of salon
			List<Customer> customers = mapSalonCustomer.get(key.getKey());
			LOGGER.info(
					"Salon ID [" + key.getKey().getUuid() + "] have [" + customers.size() + "] number of customers !");

			// Execute to notify
			if (settingSms.size() > 0 && customers.size() > 0) {
				try {
					this.executeNotify(customers, settingSms);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void executeNotify(List<Customer> customers, List<SettingSms> settingSms) throws ParseException,Exception {

		// Filter customer
		//HashMap<String, List<Customer>> customerList = this.filterCustomerByType(customers);

		settingSms.stream().forEach(setting -> {

			LOGGER.info(
					"Notify for customer type [" + setting.getKey() + "] in salon ID [" + setting.getSalonId() + "]");

			// Notify to All Customer by Promotion
			try {
				notifyToCustomer(setting);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (setting.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_PROMOTION)) {
			}

			// Notify to new Customer
			if (setting.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_NEW)) {
				// notifyToCustomer(setting);
			}
			// Notify to return customer
			else if (setting.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_RETURN)) {
				// notifyToCustomer(customerList.get(NotificationConstant.CUSTOMER_RETURN),
				// setting,
				// NotificationConstant.CUSTOMER_RETURN);
			}
			// Notify to referral customer
			else if (setting.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_REFERRAL)) {
				// notifyToCustomer(customerList.get(NotificationConstant.CUSTOMER_REFERRAL),
				// setting,
				// NotificationConstant.CUSTOMER_REFERRAL);
			}
			// Notify to appointment customer
			else if (setting.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_APPOINTMENT_REMIND)) {
				// notifyToCustomer(customerList.get(NotificationConstant.CUSTOMER_APPOINTMENT_REMIND),
				// setting,
				// NotificationConstant.CUSTOMER_APPOINTMENT_REMIND);
			}
		});
	}

	public HashMap<String, List<Customer>> filterCustomerByType(List<Customer> customers) {
		List<Customer> newCustomers = new ArrayList<Customer>();
		List<Customer> returnCustomers = new ArrayList<Customer>();
		List<Customer> referralCustomers = new ArrayList<Customer>();
		List<Customer> appointmentCustomers = new ArrayList<Customer>();

		HashMap<String, List<Customer>> mapCustomerType = new HashMap<String, List<Customer>>();

		try {
			customers.stream().forEach(customer -> {

				if (customer.getClass().equals(Customer.class)) {

					try {
						// filter new customer
						if (customer.getCustomerDetail().getCustomerType()
								.equalsIgnoreCase(NotificationConstant.CUSTOMER_NEW)) {
							Customer filterCus = new Customer();
							filterCus = customer;
							newCustomers.add(filterCus);
						}
						// filter return customer
						else if (customer.getCustomerDetail().getCustomerType()
								.equalsIgnoreCase(NotificationConstant.CUSTOMER_RETURN)) {
							Customer filterCus = new Customer();
							filterCus = customer;
							returnCustomers.add(filterCus);
						}
						// filter referral customer
						else if (customer.getCustomerDetail().getCustomerType()
								.equalsIgnoreCase(NotificationConstant.CUSTOMER_REFERRAL)) {
							Customer filterCus = new Customer();
							filterCus = customer;
							referralCustomers.add(filterCus);
						}
						// filter appointment customer
						else if (customer.getCustomerDetail().getCustomerType()
								.equalsIgnoreCase(NotificationConstant.CUSTOMER_APPOINTMENT_REMIND)) {
							Customer filterCus = new Customer();
							filterCus = customer;
							appointmentCustomers.add(filterCus);
						}
					} catch (Exception ex) {
						LOGGER.info(ex.getMessage());
					}
				}

			});
		} catch (Exception ex) {
			ex.getMessage();
		}
		mapCustomerType.put(NotificationConstant.CUSTOMER_NEW, newCustomers);
		mapCustomerType.put(NotificationConstant.CUSTOMER_RETURN, returnCustomers);
		mapCustomerType.put(NotificationConstant.CUSTOMER_REFERRAL, referralCustomers);
		mapCustomerType.put(NotificationConstant.CUSTOMER_APPOINTMENT_REMIND, appointmentCustomers);

		return mapCustomerType;
	}
	
	public void deliverMessageToCustomer(List<CustomerSummary> customers , SettingSms settingSms){
		
		customers.stream().forEach(customer -> {
			
			// Create Sms Frame for Send			
			boolean isCorrectTime = false;
			try {
				isCorrectTime = promotionJobBus.checkTimeProcess(settingSms, customer);
			} catch (Exception ex){
				LOGGER.info(ex.getMessage());
			}
			
			// Check whether customer does not want to continue get message or not.
			Boolean isCustomerBelongStopList = (SmsConstant.RESPONSE_STOP.equalsIgnoreCase(customer.getCustomerDetail().getRespond())) ? true : false;
			
			LOGGER.info("Customer belong to stop list [{}]", isCustomerBelongStopList);
			if (isCorrectTime && !isCustomerBelongStopList) {
				try {
					// Build sms body
					SmsModel smsBody = this.buildSmsBody(customer, settingSms);

					// Validate sms body
					boolean isSmsValid = this.validateSmsBody(smsBody);

					if (isSmsValid) {
						LOGGER.info("This sms for customer ID [" + customer.getUuid()
								+ "] is valid . Send message.....");

						// Function send
						SmsModel result = jobHelper.sendSms(smsBody);

						if (result.getMeta().getStatus().equalsIgnoreCase(SmsConstant.STATUS_SMS_DELIVERED)) {

							LOGGER.info("This message for customer ID [" + customer.getUuid()
									+ "] has been deliveried success .!");

							// Create Sms Queue
							SmsQueue queue = jobHelper.addSmsQueue(customer, settingSms);
							if (queue.getUuid() != null) {
								LOGGER.info("Create queue success with ID [" + queue.getUuid()
										+ "] for customer ID [" + customer.getUuid() + "]" + " in salon ["
										+ settingSms.getSalonId() + "]");
							}

						} else {
							LOGGER.info("This message has deliveried failed.");
						}
					} else {
						LOGGER.info("Sms body for customer [" + customer.getUuid().toString() + "] is invalid !");
					}
				} catch (Exception ex) {
					LOGGER.info(ex.toString());
				}
			}
		});
	}

	public void notifyToCustomer(SettingSms settingSms) throws Exception{

		List<CustomerSummary> customers = new ArrayList<CustomerSummary>();
		
		if(NotificationConstant.CUSTOMER_PROMOTION.equalsIgnoreCase(settingSms.getKey())){
			if(settingSms.getSendSmsToGroup().equalsIgnoreCase(NotificationConstant.CUSTOMER_ALL)){
				customers = jobHelper.loadCustomersByType(settingSms.getSalonId(), "");
			} else {
				customers = jobHelper.loadCustomersByType(settingSms.getSalonId(), settingSms.getSendSmsToGroup());
			}
		} else {
			if (settingSms.getCustomerGroups().getIsSendAll()) {
				customers = jobHelper.loadCustomersByType(settingSms.getSalonId(), settingSms.getKey());
			} else {
				customers = settingSms.getCustomerGroups().getCustomers();
				List<String> listIdCustomers = this.buildListId(customers);
				customers = jobHelper.getLatestDataFromCustomer(listIdCustomers);
			}
		}

		if (customers.size() > 0) {
			this.deliverMessageToCustomer(customers, settingSms);
		}
	}
	
	private List<String> buildListId(List<CustomerSummary> customers){
		List<String> listId = new ArrayList<String>();
		for (CustomerSummary customerSummary : customers){
			listId.add(customerSummary.getCustomerRefID());
		}
		
		return listId;
	}

	public List<SettingSms> filterSettingSms(List<SettingSms> settingSms) {

		// Filter list
		List<SettingSms> filterList = new ArrayList<SettingSms>();
		settingSms.stream().forEach(setting -> {
			if (setting.isAutoSend()) {
				filterList.add(setting);
			}
		});

		if (filterList.size() > 0) {
			LOGGER.info("Finish filter list setting sms ! Ready to send sms ......");
		} else {
			LOGGER.info("It have not yet come time to send sms !");
		}
		return filterList;
	}

	public HashMap<Salon, List<SettingSms>> notifySmsSetting(HashMap<Salon, List<Customer>> mapSalonCustomer) {

		HashMap<Salon, List<SettingSms>> mapSetting = new HashMap<Salon, List<SettingSms>>();

		Set<Entry<Salon, List<Customer>>> mapData = mapSalonCustomer.entrySet();

		mapData.stream().forEach(key -> {
			key.getKey();
			List<SettingSms> settingSms = jobHelper.loadSettingSms(key.getKey().getUuid().toString());

			mapSetting.put(key.getKey(), settingSms);
		});

		return mapSetting;
	}

	public HashMap<Salon, List<Customer>> settingSmsDetail() {

		// Init Hashmap to put to list
		HashMap<Salon, List<Customer>> mapSalonCustomer = new HashMap<Salon, List<Customer>>();

		// Get all salon
		List<Salon> lstSalon = jobHelper.loadListSalon();

		lstSalon.stream().forEach(salon -> {

			// Get all Customer for Salon By Salon Id
			List<Customer> lstCustomer = jobHelper.loadListCustomerBySalon(salon.getUuid().toString());

			// Init HashMap
			mapSalonCustomer.put(salon, lstCustomer);
		});

		return mapSalonCustomer;
	}

	/**
	 * Update nofication signal
	 * 
	 * @return
	 */
	private NotificationSignal updateNotificationSignal() {
		NotificationSignal signal = new NotificationSignal();
		signal.setSend(true);
		signal.setReply(false);
		signal.setSignalStatus(NotificationConstant.BUSINESS_STATUS_SIGNAL_CUSTOMER);

		return signal;
	}

	/**
	 * Update nofication signal
	 * 
	 * @return
	 * @throws Exception
	 */
	private SmsModel buildSmsBody(CustomerSummary customer, SettingSms settingSms) throws Exception {

		LOGGER.info("Building SMS Body ........");
		SmsModel smsBody = null;

		// Get salon Name
		try {

			// Create Sms Frame for Send
			ApplicationContext ctx = QuartJob.applicationContext;
			SmsService smsService = ctx.getBean(SmsService.class);
			smsBody = smsService.initModelSms();

			String to = customer.getContact().getMobilePhone();
			if (customer.getContact().getMobilePhone() == null) {
				// throws Exception ("Customer phone has been null , please
				// check again to update to use this feature !");
			}

			smsBody.getHeader().setToPhone(TextHelper.formatPhoneNumber(to));
			smsBody.setSalonId(settingSms.getSalonId());
			smsBody.getHeader().setMessage(settingSms.getContent());
			smsBody.setSmsType(NotificationConstant.SMS_PROMOTION);
			smsBody.setModuleId(customer.getCustomerRefID());
			smsBody.setMessageFor(NotificationConstant.SMS_FOR_CUSTOMER);
			
			// Fullfill with word binding
			smsBody = jobHelper.fulFillBodySmsPromo(smsBody, customer , settingSms);
			
			// Build key deliver
			if(interactionService == null){
				interactionService = QuartJob.applicationContext.getBean(InteractionService.class);
			}
			smsBody = interactionService.buildKeyDeliver(smsBody);

			LOGGER.info("Finish building SMS Body ....");
		} catch (Exception ex) {
			ex.getMessage();
			ex.getCause();
		}
		return smsBody;
	}
	

	private String getMessage(SettingSms setting) {

		String message = "";
		if (setting.getTemplateDetail().getTemplateActive() == 0
				&& setting.getTemplateDetail().getTemplate1() != null) {
			message = setting.getTemplateDetail().getTemplate1();
		} else if (setting.getTemplateDetail().getTemplateActive() == 1
				&& setting.getTemplateDetail().getTemplate2() != null) {
			message = setting.getTemplateDetail().getTemplate2();
		} else if (setting.getTemplateDetail().getTemplateActive() == 2
				&& setting.getTemplateDetail().getTemplate3() != null) {
			message = setting.getTemplateDetail().getTemplate3();
		} else if (setting.getTemplateDetail().getTemplateActive() == 3
				&& setting.getTemplateDetail().getTemplate4() != null) {
			message = setting.getTemplateDetail().getTemplate4();
		}
		return message;
	}

	private boolean validateSmsBody(SmsModel smsBody) {
		boolean isBodyValid = true;

		if (smsBody.getHeader().getMessage() == null || smsBody.getHeader().getMessage().equals("")) {
			isBodyValid = false;
		} else if (smsBody.getHeader().getToPhone() == null || smsBody.getHeader().getToPhone().equals("")) {
			isBodyValid = false;
		}
		return isBodyValid;
	}

	private boolean isSendPermission(CustomerSummary customer, SettingSms settingSms) {

		// Init flag
		boolean isHavePermission = false;

		// Get queue sms
		SmsQueue queue = jobHelper.openQueue(customer.getCustomerRefID(), settingSms);

		// IF this sms have not been send , will check from first sign in to
		// current time
		if (queue == null) {
			isHavePermission = true;
		}

		if (settingSms.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_NEW)
				|| settingSms.getKey().equalsIgnoreCase(NotificationConstant.CUSTOMER_REFERRAL)) {
			return isHavePermission;
		}

		try {
			// Check last time send sms
			isHavePermission = TimeUtils.checkLastTimeSms(queue.getCreatedDate(), settingSms);
		} catch (Exception ex) {
			LOGGER.info("There is error when check last time send sms");
		}
		return isHavePermission;
	}

}
