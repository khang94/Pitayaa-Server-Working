package pitayaa.nail.msg.business.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;
import pitayaa.nail.domain.setting.sms.EmployeeSummary;
import pitayaa.nail.domain.setting.sms.SmsAppointmentReminder;
import pitayaa.nail.msg.business.constant.BusinessConstant;
import pitayaa.nail.msg.business.helper.BusinessHelper;

@Service
public class SettingSMSBusImpl implements SettingSMSBus {
	
	@Autowired
	BusinessHelper busHelper;

	@Override
	public List<SettingSms> getListSettingSMSDefault(String salonId) throws Exception {
		// TODO Auto-generated method stub
		List<SettingSms> lstSetting=new ArrayList<>();
		List<CustomerSummary> summaryCustomers = new ArrayList<CustomerSummary>();
		List<EmployeeSummary> summaryEmployees = new ArrayList<EmployeeSummary>();
				
		SettingSms smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_PROMOTION);
		smsContent.setNote(BusinessConstant.SMS_HOLIDAY);
		smsContent.setHolidayType("None");
		smsContent.setTimesRepeat(0);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		lstSetting.add(smsContent);

		//---------------
		
		smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_NEW);
		smsContent.setTimesRepeat(5);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		lstSetting.add(smsContent);
		//---------------
		
		smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_REFERRAL);
		smsContent.setTimesRepeat(5);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		lstSetting.add(smsContent);
		//---------------
		
		smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_RETURN);
		smsContent.setTimesRepeat(5);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		lstSetting.add(smsContent);
		//---------------
		
		
		smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_APPOINTMENT_REMIND);
		smsContent.setTimesRepeat(5);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getTemplateDetailForEmployee().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		List<SmsAppointmentReminder> appointments = this.initTimerAppointment();
		smsContent.setTimeReminder(appointments);
		
		lstSetting.add(smsContent);
		//---------------
		
		smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_APPOINTMENT_CANCEL);
		smsContent.setTimesRepeat(5);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		lstSetting.add(smsContent);
		//---------------
		
		smsContent = (SettingSms) busHelper.createModelStructure(new SettingSms());
		smsContent.setAutoSend(false);
		smsContent.setSalonId(salonId);
		smsContent.setType(BusinessConstant.CUSTOMER);
		smsContent.setKey(BusinessConstant.CUSTOMER_APPOINTMENT);
		smsContent.setTimesRepeat(5);
		smsContent.setMinutesRepeat(0);
		smsContent.setHoursRepeat(0);
		smsContent.setContent("");
		smsContent.getTemplateDetail().setTemplateActive(0);
		smsContent.getCustomerGroups().setCustomers(summaryCustomers);
		smsContent.getEmployeeGroups().setEmployees(summaryEmployees);
		
		lstSetting.add(smsContent);
		//---------------
		
		return lstSetting;
	}
	
	public List<SmsAppointmentReminder> initTimerAppointment(){
		
		List<SmsAppointmentReminder> appointments = new ArrayList<SmsAppointmentReminder>();
		SmsAppointmentReminder appointmentReminder = new SmsAppointmentReminder();
		
		appointmentReminder.setIndex(0);
		appointmentReminder.setIsActive(false);
		appointmentReminder.setSendOnTime("NONE");
		appointmentReminder.setTimer(0);
		appointments.add(appointmentReminder);
		//---------------
		
		appointmentReminder = new SmsAppointmentReminder();
		appointmentReminder.setIndex(1);
		appointmentReminder.setIsActive(false);
		appointmentReminder.setSendOnTime("NONE");
		appointmentReminder.setTimer(0);
		appointments.add(appointmentReminder);
		//---------------
		
		
		appointmentReminder = new SmsAppointmentReminder();
		appointmentReminder.setIndex(2);
		appointmentReminder.setIsActive(false);
		appointmentReminder.setSendOnTime("NONE");
		appointmentReminder.setTimer(0);
		appointments.add(appointmentReminder);
		//---------------
		
		appointmentReminder = new SmsAppointmentReminder();
		appointmentReminder.setIndex(3);
		appointmentReminder.setIsActive(false);
		appointmentReminder.setSendOnTime("NONE");
		appointmentReminder.setTimer(0);
		appointments.add(appointmentReminder);
		//---------------
		
		return appointments;
	}

}
