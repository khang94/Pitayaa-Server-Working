package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.packages.PackageModel;

public interface PackageEntityInterface {

	Optional<PackageModel> findOne(UUID id);

	List<PackageModel> findAllPackages(String salonId);

	PackageModel save(PackageModel packageBody) throws Exception;

	PackageModel update(PackageModel packageSaved, PackageModel packageUpdated) throws Exception;

}
