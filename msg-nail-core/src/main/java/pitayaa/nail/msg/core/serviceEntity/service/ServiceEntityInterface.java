package pitayaa.nail.msg.core.serviceEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.service.ServiceModel;

public interface ServiceEntityInterface {

	List<ServiceModel> getServicesBySalonId(String salonId);

	Optional<ServiceModel> findOne(UUID id);

	ServiceModel save(ServiceModel serviceBody) throws Exception;

	ServiceModel update(ServiceModel serviceSaved, ServiceModel serviceUpdated) throws Exception;

	void delete(ServiceModel serviceBody);

	ServiceModel initModel() throws Exception;
}
