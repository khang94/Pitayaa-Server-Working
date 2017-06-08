package pitayaa.nail.notification.scheduler;

import java.util.HashMap;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.config.BeanConfiguration;



public class QuartJob {
	public static ApplicationContext applicationContext; 
	public static void activeJob() throws SchedulerException{
		 applicationContext = 
				   new AnnotationConfigApplicationContext(BeanConfiguration.class);
		  //JobDetail job = JobBuilder.newJob(AppointmentJob.class).withIdentity("job1", "group1").build();
		  //JobDetail job2 = simpleSchedule().simpleSchedule();
		 
		 HashMap<String, Object> appointmentSetting = initAppointmentJob();

		 HashMap<String, Object> promotionSetting =  initPromotionJob();
		  
		  
		  // Tell quartz to schedule the job using our trigger
		  Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		  scheduler.start();
		  
		  // Scheduler for appointment
		  /*scheduler.scheduleJob(
				  (JobDetail) appointmentSetting.get(NotificationConstant.APPOINTMENT_JOB),
				  (Trigger) appointmentSetting.get(NotificationConstant.APPOINTMENT_TRIGGER));*/
		  
		  // Scheduler for promotion job
		  scheduler.scheduleJob(
				  (JobDetail) promotionSetting.get(NotificationConstant.PROMOTION_JOB),
				  (Trigger) promotionSetting.get(NotificationConstant.PROMOTION_TRIGGER));
		  
	}
	
	public static HashMap<String, Object> initAppointmentJob(){
		HashMap<String , Object> appointmentJob = new HashMap<String, Object>(); 
		JobDetail job = JobBuilder.newJob(AppointmentJob.class).withIdentity("job1", "group1").build();
		
		  // Trigger the job to run now, and then repeat every 40 seconds
		  Trigger trigger = TriggerBuilder.
				  newTrigger().
				  withIdentity("trigger1", "group1").
				  startNow()
				  .withSchedule(simpleSchedule().withIntervalInSeconds(300).repeatForever()).build();
		
		appointmentJob.put(NotificationConstant.APPOINTMENT_JOB, job);
		appointmentJob.put(NotificationConstant.APPOINTMENT_TRIGGER, trigger);
		return appointmentJob;
	}
	
	public static HashMap<String, Object> initPromotionJob(){

		HashMap<String , Object> promotionJob = new HashMap<String, Object>(); 
		JobDetail job = JobBuilder.newJob(PromotionJob.class).withIdentity("job1", "group2").build();
		
		  // Trigger the job to run now, and then repeat every 40 seconds
		  Trigger trigger = TriggerBuilder.
				  newTrigger().
				  withIdentity("trigger1", "group2").
				  startNow()
				  .withSchedule(simpleSchedule().withIntervalInSeconds(120).repeatForever()).build();
		promotionJob.put(NotificationConstant.PROMOTION_JOB, job);
		promotionJob.put(NotificationConstant.PROMOTION_TRIGGER, trigger);
		return promotionJob;
	}
	
	public static void main(String[] args) throws SchedulerException {
		
		  JobDetail job = JobBuilder.newJob(AppointmentJob.class).withIdentity("job1", "group1").build();
		  //JobDetail job2 = simpleSchedule().simpleSchedule();
		  
		  // Trigger the job to run now, and then repeat every 40 seconds
		  Trigger trigger = TriggerBuilder.
				  newTrigger().
				  withIdentity("trigger1", "group1").
				  startNow()
				  .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever()).build();
		  // Tell quartz to schedule the job using our trigger
		  Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		  scheduler.start();
		  scheduler.scheduleJob(job, trigger);
		 }
		 private static SimpleScheduleBuilder simpleSchedule() {
		  System.out.println("schedule is running");
		  SimpleScheduleBuilder builder = SimpleScheduleBuilder.repeatHourlyForever();
		  return builder;
		 }

}
