package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.packages.elements.PackageDetail;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.packageEntity.repository.PackageDetailRepository;
import pitayaa.nail.msg.core.packageEntity.repository.PackagesRepository;
import pitayaa.nail.msg.core.serviceEntity.repository.ServiceRepository;

@Service
public class PackageServiceImpl implements PackageService {

	@Autowired
	PackagesRepository packageRepo;

	@Autowired
	PackageEntityViewService viewService;

	@Autowired
	PackageDetailRepository packageDetailRepo;

	@Autowired
	ServiceRepository serviceRepo;
	
	@Autowired
	CoreHelper coreHelper;

	@Override
	public List<PackageModel> findAllPackages(String salonId) {
		return packageRepo.findAllPackages(salonId);
	}

	@Override
	public Optional<PackageModel> findOne(UUID id) {
		return Optional.ofNullable(packageRepo.findOne(id));
	}

	@Override
	public PackageModel save(PackageModel packageBody) throws Exception {

		
		for(PackageDetail dtl : packageBody.getPackageDetails()){
			ServiceModel service = serviceRepo.findOne(dtl.getService().getUuid());
			dtl.setService(service);
			packageDetailRepo.save(dtl);
		}
		
		packageBody = packageRepo.save(packageBody);

		if (packageBody.getView().getImgData() != null && !"".equalsIgnoreCase(
				packageBody.getView().getImgData().toString())) {
			viewService.buildViewByDate(packageBody, packageBody.getView().getImgData());
		}

		return packageBody;
	}
	
	@Override
	public PackageModel update(PackageModel packageBody) throws Exception {

		byte[] binaryImg = null;
		boolean isUpdatedImage = false;
		
		// Update New Image
		if (packageBody.getView().getImgData().length > 0){
			isUpdatedImage = true;
			binaryImg = packageBody.getView().getImgData();
		}

		// Update package detail
		for(PackageDetail dtl : packageBody.getPackageDetails()){
			ServiceModel service = serviceRepo.findOne(dtl.getService().getUuid());
			dtl.setService(service);
			packageDetailRepo.save(dtl);
		}
		
		// Hide image
		packageBody.getView().setImgData(null);
		
		if (isUpdatedImage && packageBody.getView().getImgData()!= null){
			// Delete image from static path in local server
			coreHelper.deleteFile(packageBody.getView().getPathImage());
		}
		
		packageBody = packageRepo.save(packageBody);
		

		if (isUpdatedImage) {
			viewService.buildViewByDate(packageBody, binaryImg);
		}

		return packageBody;
	}

	@Override
	public void delete(PackageModel packageBody) throws Exception {
		// TODO Auto-generated method stub
		packageRepo.delete(packageBody);
		
	}

}
