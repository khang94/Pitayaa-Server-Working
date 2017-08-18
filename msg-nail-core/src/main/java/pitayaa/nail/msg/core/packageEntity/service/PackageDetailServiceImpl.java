package pitayaa.nail.msg.core.packageEntity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.packages.elements.PackageDetail;
import pitayaa.nail.msg.core.packageEntity.repository.PackageDetailRepository;

@Service
public class PackageDetailServiceImpl implements PackageDetailService {
	
	@Autowired
	PackageDetailRepository repo;

	@Override
	public Optional<PackageModel> findOne(UUID id) {
		return null;
	}

	@Override
	public List<PackageModel> findAllPackages(String salonId) {
		return null;
	}

	@Override
	public PackageDetail save(PackageDetail entity) throws Exception {
		return repo.save(entity);
	}

}
