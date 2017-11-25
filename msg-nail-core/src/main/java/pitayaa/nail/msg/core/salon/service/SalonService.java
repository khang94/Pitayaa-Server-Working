package pitayaa.nail.msg.core.salon.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.salon.Salon;

public interface SalonService {

	Optional<Salon> findOne(UUID id);

	Salon save(Salon salon) throws Exception;

	Salon initModel() throws Exception;

	Salon update(Salon salonUpdate, Salon salonOld) throws Exception;
	
	Salon extendLicense(UUID salonId,UUID licenseId,int type,int month) throws Exception;


	List<Salon> getAllSalon() throws Exception;
	
	void delete(Salon salon);

}
