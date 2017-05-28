package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packages.PackageModel;
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
	PackageDetailRepository pckDetailRepo;

	@Autowired
	ServiceRepository serviceRepo;

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

		// for (ServiceModel service : packageBody.get)
		/*for (PackageDtl p : packageBody.getPackageDtls()) {
			serviceRepo.save(p.getService());
			//pckDetailRepo.save(p);
		}*/
		packageBody = packageRepo.save(packageBody);

		if (packageBody.getView().getImgData() != null && !"".equalsIgnoreCase(
				packageBody.getView().getImgData().toString())) {
			viewService.buildView(packageBody);
		}

		return packageBody;
	}

	@Override
	public void delete(PackageModel packageBody) throws Exception {
		// TODO Auto-generated method stub
		packageRepo.delete(packageBody);
		
	}

}
