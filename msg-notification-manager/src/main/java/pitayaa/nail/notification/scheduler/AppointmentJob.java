package pitayaa.nail.notification.scheduler;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.appointment.elements.NotificationSignal;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.service.ISmsService;
import pitayaa.nail.notification.sms.service.SmsServiceImpl;

public class AppointmentJob implements Job {

	public final static Logger LOGGER = LoggerFactory.getLogger(AppointmentJob.class);

	@Autowired
	JobHelper jobHelper;

	@Autowired
	NotificationHelper notificationHelper;

	@Autowired
	ISmsService smsService;

	public AppointmentJob() {
		jobHelper = new JobHelper();
		notificationHelper = new NotificationHelper();
		smsService = new SmsServiceImpl();
		LOGGER.info("Init Appointment Job");
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// Call rest template to appointment
		List<Appointment> lstAppointment = jobHelper.loadAppointments();

		System.out.println(lstAppointment);
		// Get list appointment need to notify
		try {
			lstAppointment = notifyForEachAppm(lstAppointment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Appointment> notifyForEachAppm(List<Appointment> lstAppm)
			throws Exception {

		// Notify for each appointment
		for (Appointment appm : lstAppm) {

			SmsModel smsBody = this.buildSmsBody(appm);
			//smsBody = smsService.sendSms(smsBody);

			smsBody.getMeta().setStatus(SmsConstant.STATUS_SMS_DELIVERED);
			if (SmsConstant.STATUS_SMS_DELIVERED.equalsIgnoreCase(smsBody
					.getMeta().getStatus())) {

				// Update signal
				NotificationSignal signal = this.updateNotificationSignal();
				appm.getNotifyToCustomer().add(signal);

				// Update Status of Appointment
				appm.setStatus(NotificationConstant.BUSINESS_STATUS_PENDING);
				jobHelper.updateAppointment(appm.getUuid().toString(), appm);
			}
		}

		return lstAppm;
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
	private SmsModel buildSmsBody(Appointment appm) throws Exception {
		
		LOGGER.info("Building SMS Body ........");
		
		// Get salon Name
		Salon salon = jobHelper.getSalonById(appm.getSalonId()).getBody();
		
		// Create Sms Frame for Send
		ApplicationContext ctx = QuartJob.applicationContext;
		ISmsService smsService = ctx.getBean(ISmsService.class);
		SmsModel smsBody = smsService.initModelSms();
		
		smsBody = smsService.initAppointmentSms(smsBody);
		smsBody = jobHelper.bindDataSms(smsBody, salon, appm);
		
		String to = appm.getCustomer().getContact().getMobilePhone();
		smsBody.getMeta().setTemplateId(SmsConstant.SMS_APPOINTMENT);
		smsBody.getHeader().setToPhone(to);
		smsBody.setSalonId(appm.getSalonId());
		
		LOGGER.info("Finish building SMS Body ....");

		return smsBody;
	}

}
