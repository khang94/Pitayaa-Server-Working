package pitayaa.nail.msg.core.report.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.report.ReportDocs;
import pitayaa.nail.domain.report.elements.ReportPromotionData;
import pitayaa.nail.domain.report.elements.ReportSmsData;
import pitayaa.nail.msg.core.appointment.service.AppointmentService;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.integration.IntegrationService;
import pitayaa.nail.msg.core.promotion.service.PromotionService;

@Service
public class PromotionDataAnalystImpl implements PromotionDataAnalyst {
	
	@Autowired
	IntegrationService integrationService;
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	CoreHelper coreHelper;
	
	@Override
	public ReportDocs getPromotionData(String salonId , String from , String to) throws Exception {
		
		ReportDocs reportDocs = (ReportDocs) coreHelper.createModelStructure(new ReportDocs());
		
		// Get list appointment to get total
		List<Appointment> appointments = appointmentService.findAllAppointmentBySalon(salonId);
		
		// Get promotion Data
		ReportPromotionData promotionData= this.buildPromotionReport(salonId, from, to);
		
		// Get sms data
		ReportSmsData smsData = this.buildSmsReport(salonId, 100, this.getSmsQueue(salonId, from, to));
		
		// Bind data
		reportDocs.setTotalAppointments(appointments.size());
		reportDocs.setTotalNoShow(5);
		reportDocs.setPromotionData(promotionData);
		reportDocs.setSmsData(smsData);
		
		return reportDocs;
	}
	
	public ReportPromotionData buildPromotionReport(String salonId , String from , String to) throws Exception{
		
		List<Promotion> promotionActive = promotionService.findPromotionActive(salonId);
		List<Promotion> promotionSentOut = promotionService.findPromotionByConditions(salonId, CoreConstant.PROMOTION_CODE_SEND_OUT, from, to);
		List<Promotion> promotionUsed = promotionService.findPromotionByConditions(salonId, CoreConstant.PROMOTION_CODE_USED, from, to);
		
		ReportPromotionData promotionData = new ReportPromotionData();
		promotionData.setTotalAvailable(promotionActive.size());
		promotionData.setTotalSentOut(promotionSentOut.size());
		promotionData.setTotalUsed(promotionUsed.size());
		
		Integer promotionRemained = promotionData.getTotalAvailable() - promotionData.getTotalUsed();
		promotionData.setTotalRemained(promotionRemained);
		
		return promotionData;
	} 
	
	public ReportSmsData buildSmsReport(String salonId , Integer totalSms , List<SmsQueue> smsQueue){
		
		ReportSmsData smsData = new ReportSmsData();
		smsData.setTotal(totalSms);
		smsData.setTotalSent(smsQueue.size());
		smsData.setTotalRemain(totalSms - smsData.getTotalSent());
		smsData.setSalonId(salonId);
		
		return smsData;
	}
	
	@Override
	public List<SmsQueue> getSmsQueue(String salonId , String from , String to) throws Exception {
		return integrationService.getReportSms(salonId, from, to);
	}
}
