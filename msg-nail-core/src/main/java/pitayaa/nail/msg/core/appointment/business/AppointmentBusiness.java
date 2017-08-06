package pitayaa.nail.msg.core.appointment.business;

import java.util.List;
import java.util.UUID;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.service.ServiceModel;

public interface AppointmentBusiness {

	Appointment executeCustomer(UUID customerId, Appointment appmBody) throws Exception;

	Appointment executeEmployee(UUID employeeId, Appointment appmBody) throws Exception;

	Appointment executeServices(List<ServiceModel> lstService,
			Appointment appmBody);

	Appointment executePackages(List<PackageModel> lstPackage,
			Appointment appmBody);

	Appointment executeCreateAppm(Appointment appmBody) throws Exception;

	Appointment executePromotions(Promotion promotion, Appointment appmBody) throws Exception;

}
