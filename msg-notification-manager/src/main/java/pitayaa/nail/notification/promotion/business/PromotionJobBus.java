package pitayaa.nail.notification.promotion.business;

import java.text.ParseException;

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;

public interface PromotionJobBus {

	boolean isTimeToSend(SettingSms settingSms, CustomerSummary customerSummary);

	boolean isTimeToSendGroups(SettingSms settingSms) throws ParseException;

	boolean checkTimeProcess(SettingSms settingSms, CustomerSummary customerSummary) throws ParseException;

}
