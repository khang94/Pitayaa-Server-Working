package pitayaa.nail.notification.sms.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.notification.sms.SmsReceive;
import pitayaa.nail.domain.notification.sms.elements.KeyValueIndex;
import pitayaa.nail.domain.notification.sms.elements.KeyWordDeliverManagement;
import pitayaa.nail.msg.business.util.common.StringUtil;
import pitayaa.nail.msg.business.util.common.ValidatePhoneNumber;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.repository.KeyDeliverRepository;
import pitayaa.nail.notification.sms.repository.SmsRepository;

@Service
public class InteractionServiceImpl implements InteractionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Autowired
	NotificationHelper notificationHelper;

	@Autowired
	SmsRepository smsRepository;
	
	@Autowired
	KeyDeliverRepository keyDeliverRepository;

	@Override
	public SmsModel findSmsByResponseKey(SmsReceive smsReceive) throws Exception {
		Assert.notNull(smsReceive);
		List<SmsModel> smsList = smsRepository.findSmsByResponseKey(smsReceive.getMessage());

		if (smsList.isEmpty()) {
			LOGGER.info("No sms has been delivered with key response [{}] ", smsReceive.getMessage());
			return null;
		}

		return smsList.get(0);
	}
	
	@Override
	public SmsModel findSmsByResponseKeyAndPhoneNumber(SmsReceive smsReceive) throws Exception {
		Assert.notNull(smsReceive);
		List<SmsModel> smsList = smsRepository.findSmsByResponseKeyAndPhoneNumber(smsReceive.getMessage() , smsReceive.getFromPhone());

		if (smsList.isEmpty()) {
			LOGGER.info("No sms has been delivered with key response [{}] and phone number [{}]", smsReceive.getMessage() ,
					smsReceive.getFromPhone());
			return null;
		}

		return smsList.get(0);
	}

	@Override
	public SmsModel buildSmsAutoResponseToCustomer(SmsReceive smsReceive) throws Exception {

		// Response to customer
		SmsModel smsResponse = (SmsModel) notificationHelper.createModelStructure(new SmsModel());

		String message = smsReceive.getMessage().trim();

		if (message.contains(SmsConstant.RESPONSE_STOP) && !message.contains(SmsConstant.RESPONSE_UNSTOP)) {
			smsResponse.getMeta().setTemplateId(SmsConstant.TEMPLATE_SMS_RESPONSE_STOP);
		} else if (message.contains(SmsConstant.RESPONSE_UNSTOP)) {
			smsResponse.getMeta().setTemplateId(SmsConstant.TEMPLATE_SMS_RESPONSE_UNSTOP);
		} else {
			smsResponse.getMeta().setTemplateId(SmsConstant.TEMPLATE_SMS_RESPONSE);
		}
		smsResponse.getHeader().setToPhone(smsReceive.getFromPhone());
		smsResponse.setMessageId(smsReceive.getMessageId());

		return smsResponse;
	}

	@Override
	public boolean isMessageReplyByCustomer(SmsModel smsBody) {
		if (smsBody.getInteractionData().getSmsReceive() != null
				&& smsBody.getInteractionData().getSmsReceive().getUuid() != null
				&& !StringUtil.isNullOrEmpty(smsBody.getInteractionData().getSmsReceive().getMessage())) {
			return true;
		}
		return false;
	}

	@Override
	public SmsModel findSmsByResponseKeyTest(String key) throws Exception {
		return smsRepository.findSmsByResponseKey(key).get(0);
	}
	
	@Override
	public synchronized SmsModel buildKeyDeliver(SmsModel smsBody) throws Exception {
		
		List<String> lstKey = buildListKey(smsBody.getHeader().getMessage());
		if(lstKey.isEmpty()){
			return smsBody;
		}
		
		List<KeyWordDeliverManagement> keyDelivers = keyDeliverRepository.findAllSmsKey(smsBody.getHeader().getToPhone());
		
		KeyWordDeliverManagement keyWordDeliver = null;
		
		for(String key : lstKey){
			
			// Has customer reply with this key
			boolean isCustomerReplyPreviousMessage = (keyDelivers.isEmpty()) ? false :
					isGetRespondFromCustomer(smsBody.getHeader().getToPhone() , keyDelivers.get(0) , key);
			boolean isKeyExist = (keyDelivers.isEmpty()) ? false : isKeyExist(keyDelivers.get(0) , key);
			
			if ((isCustomerReplyPreviousMessage || keyDelivers.isEmpty()) && isKeyExist || (!isKeyExist && keyDelivers.isEmpty())){
				keyWordDeliver = this.updateKeyWordDeliverByCondition(keyDelivers, smsBody, key);
				keyDelivers.add(keyWordDeliver);
			} else if (!isKeyExist) {
				KeyValueIndex keyValue = new KeyValueIndex();
				keyValue.setIndex(0);
				keyValue.setKey(key);
				keyDelivers.get(0).getKeyDelivers().add(keyValue);
				keyWordDeliver = keyDelivers.get(0);
				updateKeyWordDeliver(keyWordDeliver , key);
			} else {
				keyWordDeliver = keyDelivers.get(0);
			}
		}
		return this.bindKeyWord(keyWordDeliver, smsBody);
	}
	
	private Boolean isKeyExist(KeyWordDeliverManagement keyWord , String key){
		if(keyWord == null){}
		
		for(KeyValueIndex keyValueIndex : keyWord.getKeyDelivers()){
			if(key.equalsIgnoreCase(keyValueIndex.getKey())){
				return true;
			}
		}
		return false;
	}
	
	private KeyWordDeliverManagement updateKeyWordDeliverByCondition(List<KeyWordDeliverManagement> keyDelivers , 
			SmsModel smsBody , String key) throws Exception{
		KeyWordDeliverManagement keyWordDeliver = new KeyWordDeliverManagement();
		
		List<String> lstKey = new ArrayList<>();
		lstKey.add(key);
		

		// If key list is empty init new key
		if(keyDelivers.isEmpty()){
			keyWordDeliver = this.initInitialKeyWordDeliver(smsBody.getHeader().getMessage(),lstKey);
		} else {
			keyWordDeliver = keyDelivers.get(0);
		}

		keyWordDeliver.setPhoneNumber(smsBody.getHeader().getToPhone());
		
		return updateKeyWordDeliver(keyWordDeliver , key);
	}
	
	private boolean isGetRespondFromCustomer(String phoneNumber , KeyWordDeliverManagement keyWordDeliver , String key) {
		
		
		String keyResponse = "";
		for (KeyValueIndex keyValueIndex : keyWordDeliver.getKeyDelivers()){
			if(key.equalsIgnoreCase(keyValueIndex.getKey())){
				keyResponse = keyValueIndex.getKeyWordDeliver(); 
			}
			
		}
		
		List<SmsModel> smsList = smsRepository.isCustomerResponseWithKeyWord(phoneNumber , keyResponse);
		
		if(smsList.isEmpty()){
			return false;
		}
		return true;	
	}
	
	private SmsModel bindKeyWord(KeyWordDeliverManagement keyWord , SmsModel smsBody){
		String message = smsBody.getHeader().getMessage();
		List<String> lstKeyDeliver = new ArrayList<>();
		String word = "";
		
		for(KeyValueIndex keyValueIndex : keyWord.getKeyDelivers()){
			if(message.contains(keyValueIndex.getKey()) 
					&& SmsConstant.KEY_DELIVER_STOP.equalsIgnoreCase(keyValueIndex.getKey())
					&& !SmsConstant.KEY_DELIVER_UNSTOP.equalsIgnoreCase(keyValueIndex.getKey())){
			
				message = message.replace(SmsConstant.KEY_DELIVER_STOP,keyValueIndex.getKeyWordDeliver());
				lstKeyDeliver.add(keyValueIndex.getKeyWordDeliver());
				
			} else if (message.contains(keyValueIndex.getKey())
					&& SmsConstant.KEY_DELIVER_UNSTOP.equalsIgnoreCase(keyValueIndex.getKey())){
				
				message = message.replace(SmsConstant.KEY_DELIVER_UNSTOP,keyValueIndex.getKeyWordDeliver());
				lstKeyDeliver.add(keyValueIndex.getKeyWordDeliver());
			
			} else if (message.contains(keyValueIndex.getKey())
					&& SmsConstant.KEY_DELIVER_CONFIRM.equalsIgnoreCase(keyValueIndex.getKey())){
				
				message = message.replace(keyValueIndex.getKey(), keyValueIndex.getKeyWordDeliver());
				lstKeyDeliver.add(keyValueIndex.getKeyWordDeliver());
				
			} else if (message.contains(keyValueIndex.getKey())
					&& SmsConstant.KEY_DELIVER_CANCEL.equalsIgnoreCase(keyValueIndex.getKey())){
				
				message = message.replace(keyValueIndex.getKey(), keyValueIndex.getKeyWordDeliver());
				lstKeyDeliver.add(keyValueIndex.getKeyWordDeliver());
			}
		}
		
		smsBody.getHeader().setMessage(message);
		smsBody.getInteractionData().setKeyResponseDelivers(lstKeyDeliver);
		return smsBody;
	}
	
	/**
	 * Init key word deliver
	 * @param message
	 * @param lstKey
	 * @return
	 * @throws Exception
	 */
	private KeyWordDeliverManagement initInitialKeyWordDeliver(String message , List<String> lstKey) throws Exception {
		KeyWordDeliverManagement keyWordDeliver = 
				new KeyWordDeliverManagement();
		
		List<KeyValueIndex> lstKeyIndex = new ArrayList<>();
		for(String key : lstKey){
			KeyValueIndex keyValueIndex = new KeyValueIndex();
			keyValueIndex.setKey(key);
			keyValueIndex.setKeyWordDeliver("");
			keyValueIndex.setIndex(0);
			lstKeyIndex.add(keyValueIndex);
		}
		keyWordDeliver.setKeyDelivers(lstKeyIndex);
		
		return keyWordDeliver;
	}
	
	
	/**
	 * Update index of key value
	 * @param keyWordDeliver
	 * @return
	 * @throws Exception
	 */
	private KeyWordDeliverManagement updateKeyWordDelivers(KeyWordDeliverManagement keyWordDeliver , List<String> lstKey) throws Exception {

		
		for(String key : lstKey){
			boolean isNewKey = true;
			
			for(KeyValueIndex keyValueIndex : keyWordDeliver.getKeyDelivers()){
				
				int index = 0;
				if(keyValueIndex.getIndex() != null){
					index = keyValueIndex.getIndex();
				}
				
				if(keyValueIndex.getKey().equalsIgnoreCase(key)){
					isNewKey = false;
					keyValueIndex.setIndex(index + 1);
					String fullKeyDeliver = removeStrangeChar(keyValueIndex.getKey() + buildFullKey(index + 1));
					keyValueIndex.setKeyWordDeliver(fullKeyDeliver);
				}
			}
			
			// Init new key in the list
			if(isNewKey){
				KeyValueIndex keyValueIndex = new KeyValueIndex();
				keyValueIndex.setKey(key);
				keyValueIndex.setIndex(0);
				String fullKeyDeliver = removeStrangeChar(keyValueIndex.getKey() + buildFullKey(0));
				keyValueIndex.setKeyWordDeliver(fullKeyDeliver);
				keyWordDeliver.getKeyDelivers().add(keyValueIndex);
			}
			
		}
		return keyWordDeliver;
	}
	
	private KeyWordDeliverManagement updateKeyWordDeliver(KeyWordDeliverManagement keyWordDeliver , String key) throws Exception {

	
			boolean isNewKey = true;
			
			for(KeyValueIndex keyValueIndex : keyWordDeliver.getKeyDelivers()){
				
				int index = 0;
				if(keyValueIndex.getIndex() != null){
					index = keyValueIndex.getIndex();
				}
				
				if(keyValueIndex.getKey().equalsIgnoreCase(key)){
					isNewKey = false;
					keyValueIndex.setIndex(index + 1);
					String fullKeyDeliver = removeStrangeChar(keyValueIndex.getKey() + buildFullKey(index + 1));
					keyValueIndex.setKeyWordDeliver(fullKeyDeliver);
					
				}
			}
			
			// Init new key in the list
			if(isNewKey){
				KeyValueIndex keyValueIndex = new KeyValueIndex();
				keyValueIndex.setKey(key);
				keyValueIndex.setIndex(0);
				String fullKeyDeliver = removeStrangeChar(keyValueIndex.getKey() + buildFullKey(0));
				keyValueIndex.setKeyWordDeliver(fullKeyDeliver);
				keyWordDeliver.getKeyDelivers().add(keyValueIndex);
			}
		
		return keyDeliverRepository.save(keyWordDeliver);
	}
	
	private String removeStrangeChar(String s){
		if(s.contains("#")){
			s = s.replace("#", "");
		}
		return s;
	}
	
	/**
	 * Build full key ex : STOP01
	 * @param index
	 * @return
	 */
	private String buildFullKey(Integer index){
		String key = "";
		if(index < 10){
			key = "00" + String.valueOf(index);
		} else if (index >= 10 && index < 100){
			key = "0" + String.valueOf(index);
		} else {
			key = String.valueOf(index);
		}
		return key;
	}
	
	
	private List<String> buildListKey(String messageRespond) throws Exception {
		
		List<String> lstKey = new ArrayList<>();
		if(messageRespond.contains(SmsConstant.KEY_DELIVER_STOP)){
			lstKey.add(SmsConstant.KEY_DELIVER_STOP);
		}
		if(messageRespond.contains(SmsConstant.KEY_DELIVER_UNSTOP)){
			lstKey.add(SmsConstant.KEY_DELIVER_UNSTOP);
		}
		if(messageRespond.contains(SmsConstant.KEY_DELIVER_CONFIRM)){
			lstKey.add(SmsConstant.KEY_DELIVER_CONFIRM);
		}
		if(messageRespond.contains(SmsConstant.KEY_DELIVER_CANCEL)){
			lstKey.add(SmsConstant.KEY_DELIVER_CANCEL);
		}
		return lstKey;
	}
	
	
}
