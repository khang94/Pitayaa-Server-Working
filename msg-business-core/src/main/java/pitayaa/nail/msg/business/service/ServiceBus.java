package pitayaa.nail.msg.business.service;

import java.util.List;

import pitayaa.nail.domain.service.ServiceModel;

public interface ServiceBus {

	List<ServiceModel> getListServiceDefault(String salonId) throws Exception;


}
