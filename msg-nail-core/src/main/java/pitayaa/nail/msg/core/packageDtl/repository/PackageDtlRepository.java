package pitayaa.nail.msg.core.packageDtl.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packagedtl.PackageDtl;

@Service
@RepositoryRestResource(collectionResourceRel = "packagesDetailRest", path = "packagesDetailRest")
public interface PackageDtlRepository
		extends
			PagingAndSortingRepository<PackageDtl, UUID> {

}
