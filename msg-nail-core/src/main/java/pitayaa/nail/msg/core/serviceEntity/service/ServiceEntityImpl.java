package pitayaa.nail.msg.core.serviceEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.serviceEntity.repository.ServiceRepository;

@Service
public class ServiceEntityImpl implements ServiceEntityInterface {
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired
	private ServiceEntityViewService viewService;
	
	@Autowired
	private CoreHelper coreHelper;
	
	@Override
	public List<ServiceModel> getServicesBySalonId(String salonId){
		return serviceRepo.getServicesBySalonId(salonId);
	}
	
	@Override
	public Optional<ServiceModel> findOne(UUID id){
		return Optional.ofNullable(serviceRepo.findOne(id));
	}
	
	@Override
	public ServiceModel save(ServiceModel serviceBody) throws Exception {
		
		byte[] binaryImg = null;
		boolean isUploadImage = false;
		
		// Get stream image
		if (serviceBody.getView().getImgData().length > 0){
			binaryImg = serviceBody.getView().getImgData();
			isUploadImage = true;
		}
		
		// Hide image
		serviceBody.getView().setImgData(null);
		
		serviceBody = serviceRepo.save(serviceBody);
		
		if(isUploadImage && serviceBody.getSalonId() != null){
			viewService.buildViewByDate(serviceBody, binaryImg);
		}
		serviceBody = serviceRepo.save(serviceBody);
		
		if(serviceBody.getView().getImgData() != null 
				&& !"".equalsIgnoreCase(serviceBody.getView().getImgData().toString())){
			viewService.buildView(serviceBody);
		}

		return serviceBody;
	}
	
	@Override
	public ServiceModel update(ServiceModel serviceSaved , ServiceModel serviceUpdated) throws Exception {
		
		byte[] binaryImg = null;
		boolean isUpdatedImage = false;
		
		// Update New Image
		if (serviceUpdated.getView().getImgData().length > 0){
			isUpdatedImage = true;
			binaryImg = serviceUpdated.getView().getImgData();
		}
		
		// Hide image
		serviceUpdated.getView().setImgData(null);
		
		if (isUpdatedImage && serviceSaved.getView().getImgData()!= null){
			// Delete image from static path in local server
			coreHelper.deleteFile(serviceSaved.getView().getPathImage());
		}
		
		// Update hibernate
		serviceUpdated.setUuid(serviceSaved.getUuid());
		serviceUpdated = serviceRepo.save(serviceUpdated);
		
		if(isUpdatedImage && serviceUpdated.getSalonId() != null ){
			viewService.buildViewByDate(serviceUpdated, binaryImg);
		}
		
		return serviceUpdated;
	}
	
	@Override
	public void delete(ServiceModel serviceBody) {
		serviceRepo.delete(serviceBody);
		
	}
}
