package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.packages.PackageModel;

public interface PackageService {

	Optional<PackageModel> findOne(UUID id);

	List<PackageModel> findAllPackages(String salonId);

	PackageModel save(PackageModel packageBody) throws Exception;
	void delete (PackageModel packageBody) throws Exception;

	PackageModel update(PackageModel packageBody) throws Exception;

}
