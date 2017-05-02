package pitayaa.nail.msg.business.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.msg.business.constant.BusinessConstant;

@Service
public class SettingSMSBusImpl implements SettingSMSBus {

	@Override
	public List<SettingSms> getListSettingSMSDefaul(String salonId) {
		// TODO Auto-generated method stub
		List<SettingSms> lstSetting=new ArrayList<>();
				
		SettingSms smsAllCustomer=new SettingSms();
		smsAllCustomer.setAutoSend(false);
		smsAllCustomer.setSalonId(salonId);
		smsAllCustomer.setType(BusinessConstant.CUSTOMER);
		smsAllCustomer.setKey(BusinessConstant.CUSTOMER_PROMOTION);
		smsAllCustomer.setNote(BusinessConstant.SMS_HOLIDAY);
		smsAllCustomer.setHolidayType("None");
		smsAllCustomer.setTimesRepeat(0);
		smsAllCustomer.setMinutesRepeat(0);
		smsAllCustomer.setHoursRepeat(0);
		lstSetting.add(smsAllCustomer);
		smsAllCustomer.setContent("");
		//---------------
		
		SettingSms smsNewCustomer=new SettingSms();
		smsNewCustomer.setAutoSend(false);
		smsNewCustomer.setSalonId(salonId);
		smsAllCustomer.setType(BusinessConstant.CUSTOMER);
		smsNewCustomer.setKey(BusinessConstant.CUSTOMER_NEW);
		smsNewCustomer.setTimesRepeat(5);
		smsAllCustomer.setMinutesRepeat(0);
		smsAllCustomer.setHoursRepeat(0);
		smsNewCustomer.setContent("");

		lstSetting.add(smsNewCustomer);
		//---------------
		
		SettingSms smsReferralCustomer=new SettingSms();
		smsReferralCustomer.setAutoSend(false);
		smsReferralCustomer.setSalonId(salonId);
		smsAllCustomer.setType(BusinessConstant.CUSTOMER);
		smsReferralCustomer.setKey(BusinessConstant.CUSTOMER_REFERRAL);
		smsReferralCustomer.setTimesRepeat(5);
		smsAllCustomer.setMinutesRepeat(0);
		smsAllCustomer.setHoursRepeat(0);
		smsReferralCustomer.setContent("");

		lstSetting.add(smsReferralCustomer);
		//---------------
		
		SettingSms smsReturnCustomer=new SettingSms();
		smsReturnCustomer.setAutoSend(false);
		smsReturnCustomer.setSalonId(salonId);
		smsAllCustomer.setType(BusinessConstant.CUSTOMER);
		smsReturnCustomer.setKey(BusinessConstant.CUSTOMER_RETURN);
		smsReturnCustomer.setTimesRepeat(5);
		smsAllCustomer.setMinutesRepeat(0);
		smsAllCustomer.setHoursRepeat(0);
		smsReturnCustomer.setContent("");

		lstSetting.add(smsReturnCustomer);
		//---------------
		
		
		SettingSms smsAppointmentCustomer=new SettingSms();
		smsAppointmentCustomer.setAutoSend(false);
		smsAppointmentCustomer.setSalonId(salonId);
		smsAllCustomer.setType(BusinessConstant.CUSTOMER);
		smsAppointmentCustomer.setKey(BusinessConstant.CUSTOMER_APPOINTMENT_REMIND);
		smsAppointmentCustomer.setTimesRepeat(5);
		smsAllCustomer.setMinutesRepeat(0);
		smsAllCustomer.setHoursRepeat(0);
		smsAppointmentCustomer.setContent("");

		lstSetting.add(smsAppointmentCustomer);
		//---------------
		
		SettingSms smsAppointmentCustomerCancel=new SettingSms();
		smsAppointmentCustomerCancel.setAutoSend(false);
		smsAppointmentCustomerCancel.setSalonId(salonId);
		smsAllCustomer.setType(BusinessConstant.CUSTOMER);
		smsAppointmentCustomerCancel.setKey(BusinessConstant.CUSTOMER_APPOINTMENT_CANCEL);
		smsAppointmentCustomerCancel.setTimesRepeat(5);
		smsAllCustomer.setMinutesRepeat(0);
		smsAllCustomer.setHoursRepeat(0);
		smsAppointmentCustomerCancel.setContent("");

		lstSetting.add(smsAppointmentCustomerCancel);
		//---------------
		
		return lstSetting;
	}

}
