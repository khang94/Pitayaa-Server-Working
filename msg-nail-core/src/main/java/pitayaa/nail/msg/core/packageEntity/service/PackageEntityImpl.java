package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packagedtl.PackageDtl;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.packageDtl.repository.PackageDtlRepository;
import pitayaa.nail.msg.core.packageEntity.repository.PackagesRepository;
import pitayaa.nail.msg.core.serviceEntity.repository.ServiceRepository;

@Service
public class PackageEntityImpl implements PackageEntityInterface {

	@Autowired
	PackagesRepository packageRepo;

	@Autowired
	PackageEntityViewService viewService;

	@Autowired
	PackageDtlRepository pckDetailRepo;

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

		byte[] binaryImg = null;
		boolean isUploadImage = false;
		
		// Get stream image
		if (packageBody.getView().getImgData().length > 0){
			binaryImg = packageBody.getView().getImgData();
			isUploadImage = true;
		}
		
		// Hide image
		packageBody.getView().setImgData(null);
		
		packageBody = packageRepo.save(packageBody);
		
		if(isUploadImage && packageBody.getSalonId() != null){
			viewService.buildViewByDate(packageBody, binaryImg);
		}
		packageBody = packageRepo.save(packageBody);
		
		if(packageBody.getView().getImgData() != null 
				&& !"".equalsIgnoreCase(packageBody.getView().getImgData().toString())){
			viewService.buildView(packageBody);
		}

		return packageBody;
	}
	
	@Override
	public PackageModel update(PackageModel packageSaved , PackageModel packageUpdated) throws Exception {
		
		byte[] binaryImg = null;
		boolean isUpdatedImage = false;
		
		// Update New Image
		if (packageUpdated.getView().getImgData().length > 0){
			isUpdatedImage = true;
			binaryImg = packageUpdated.getView().getImgData();
		}
		
		// Hide image
		packageUpdated.getView().setImgData(null);
		
		if (isUpdatedImage && packageSaved.getView().getImgData()!= null){
			// Delete image from static path in local server
			coreHelper.deleteFile(packageSaved.getView().getPathImage());
		}
		
		// Update hibernate
		packageUpdated.setUuid(packageSaved.getUuid());
		packageUpdated = packageRepo.save(packageSaved);
		
		if(isUpdatedImage && packageUpdated.getSalonId() != null ){
			viewService.buildViewByDate(packageUpdated, binaryImg);
		}
		
		return packageUpdated;
	}

}
