package pitayaa.nail.msg.business.salon;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.salon.Salon;




public interface SalonBus {

	Salon createDefaultSalon(Account account, License license) throws Exception;

	Salon saveSalon(Salon salonBody);

	Salon updateSalon(Salon salonBody);








}
