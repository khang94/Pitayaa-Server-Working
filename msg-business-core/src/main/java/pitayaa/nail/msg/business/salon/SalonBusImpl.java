package pitayaa.nail.msg.business.salon;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.salon.elements.SalonLicense;
import pitayaa.nail.msg.business.constant.SalonConstant;
import pitayaa.nail.msg.business.helper.BusinessHelper;
import pitayaa.nail.msg.business.license.LicenseBusiness;
import pitayaa.nail.msg.business.util.common.DateUtil;

@Service
public class SalonBusImpl implements SalonBus {

	@Autowired
	BusinessHelper businessHelper;



	LicenseBusiness licenseBusinees;

	@Override
	public Salon saveSalon(Salon salonBody) {

		// Date timeNow = businessHelper.getTimeNow();

		// salonBody.setCreatedDate(timeNow);
		// salonBody.setUpdatedDate(timeNow);

		salonBody.setStatus(SalonConstant.STATUS_SALON_ACTIVE);

		return salonBody;
	}

	@Override
	public Salon updateSalon(Salon salonBody) {

		// Date timeNow = businessHelper.getTimeNow();
		// salonBody.setUpdatedDate(timeNow);
		return salonBody;

	}

	@Override
	public Salon createDefaultSalon(Account account, License license)
			throws Exception {

		// Get Timezone
		Date timeNow = businessHelper.getTimeNow();

		// get license trial

		// Create Salon License for Salon
		SalonLicense salonLicense = (SalonLicense) businessHelper
				.createModelStructure(new SalonLicense());
		salonLicense.setActiveDate(timeNow);
		salonLicense.setCreatedDate(timeNow);
		salonLicense.setUpdatedDate(timeNow);
		salonLicense.setExpiredDate(DateUtil.addDate(timeNow, 15));
		salonLicense.setLicense(license);
		salonLicense.setIsPay(SalonConstant.SALON_UNPAY);

		// Create Salon
		Salon salon = (Salon) businessHelper.createModelStructure(new Salon());
		salon.setCreatedDate(timeNow);
		salon.setUpdatedDate(timeNow);

		if (account.getContact() != null) {
			salon.setContact(account.getContact());
		}
		if (account.getAddress() != null) {
			salon.setAddress(account.getAddress());
		}
		salon.setDescription("SalonDefault");
		salon.getSalonDetail()
				.setBusinessName(account.getAccountDetail().getBusinessName());

		salon.setSalonLicense(salonLicense);

		return salon;
	}

}
