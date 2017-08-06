package pitayaa.nail.notification.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.helper.TextHelper;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.scheduler.JobHelper;
import pitayaa.nail.notification.scheduler.repository.SmsQueueRepository;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.service.ISmsService;

@Service
public class SmsQueueServiceImpl implements SmsQueueService {

	@Autowired
	SmsQueueRepository queueRepo;

	@Autowired
	ISmsService smsService;

	@Autowired
	NotificationHelper notificationHelper;

	@Autowired
	JsonHttpService httpService;
	
	@Autowired
	JobHelper jobHelper;

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsQueueServiceImpl.class);

	@Override
	public SmsQueue saveQueue(SmsQueue smsQueue) {

		SmsQueue queue = queueRepo.save(smsQueue);

		return queue;
	}

	@Override
	public List<SmsQueue> getQueue(String customerId, String settingSmsId, String customerType) {
		return queueRepo.getQueue(customerId, settingSmsId, customerType);
	}
	
	@Override
	public SmsQueue getTopQueue(String customerId, String settingSmsId, String customerType) {
		return queueRepo.getQueue(customerId, settingSmsId, customerType).get(0);
	}

	@Override
	public JsonHttp deliverMessage(SettingSms settingSms) throws Exception {

		List<CustomerSummary> customers = this.getListCustomer(settingSms);
		List<SmsModel> listMessageDeliver = this.buildListSms(settingSms , customers);
		
		for (SmsModel smsSend : listMessageDeliver) {
			smsSend = smsService.sendSms(smsSend);

			if (smsSend.getMeta().getStatus().equalsIgnoreCase(SmsConstant.STATUS_SMS_DELIVERED)) {
				LOGGER.info("Message send to phone [" + smsSend.getHeader().getToPhone() + "] successfully.");
			} else {
				LOGGER.info("Message send to phone [" + smsSend.getHeader().getToPhone() + "] failed.");
			}
		}

		JsonHttp jsonHttp = httpService.getResponseSuccess("", "Deliver all message success");
		
		return jsonHttp;
	}
	
	protected List<CustomerSummary> getListCustomer(SettingSms settingSms) throws Exception{
		String customerType = "";
		List<CustomerSummary> customers = new ArrayList<CustomerSummary>();
		
		// Get definite customer type
		if(NotificationConstant.CUSTOMER_PROMOTION.equalsIgnoreCase(settingSms.getKey())){
			if(!NotificationConstant.CUSTOMER_ALL.equalsIgnoreCase(settingSms.getSendSmsToGroup())){
				customerType = settingSms.getSendSmsToGroup();
			}		
		} else if (NotificationConstant.CUSTOMER_APPOINTMENT_REMIND.equalsIgnoreCase(settingSms.getKey())){
			
		} else {
			if(!settingSms.getCustomerGroups().getIsSendAll()){
				customerType = settingSms.getKey();
			}
		}
		
		customers = jobHelper.loadCustomersByType(settingSms.getSalonId(), customerType);
		return customers;
	}
	

	private List<SmsModel> buildListSms(SettingSms settingSms , List<CustomerSummary> customers) throws Exception {
		List<SmsModel> listMessageDeliver = new ArrayList<SmsModel>();

		if (customers.size() > 0) {
			for (CustomerSummary customerSummary : customers) {
				SmsModel smsBody = (SmsModel) notificationHelper.createModelStructure(new SmsModel());
				smsBody.getHeader()
						.setToPhone(TextHelper.formatPhoneNumber(customerSummary.getContact().getMobilePhone()));
				smsBody.setSalonId(settingSms.getSalonId());
				smsBody.getHeader().setMessage(settingSms.getContent());
				smsBody.setSmsType(NotificationConstant.SMS_PROMOTION);
				smsBody.setModuleId(customerSummary.getCustomerRefID());
				smsBody.setMessageFor(NotificationConstant.SMS_FOR_CUSTOMER);
				
				// Fulfill sms body
				smsBody = jobHelper.fulFillBodySms(smsBody, customerSummary);

				listMessageDeliver.add(smsBody);
			}
		}
		return listMessageDeliver;
	}
}
