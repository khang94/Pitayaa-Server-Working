package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.packages.elements.PackageDtl;

public interface PackageDetailService {
	Optional<PackageModel> findOne(UUID id);

	List<PackageModel> findAllPackages(String salonId);

	public PackageDtl save(PackageDtl entity) throws Exception;
}
