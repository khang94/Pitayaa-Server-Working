package pitayaa.nail.msg.core.packageEntity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.packages.elements.PackageDetail;


@Service
@RepositoryRestResource(collectionResourceRel = "packagesDetailRest", path = "packagesDetailRest")
public interface PackageDetailRepository extends
		PagingAndSortingRepository<PackageDetail, UUID> { 
	
	@Query("Select p from PackageModel p where p.salonId = :salonId")
	List<PackageModel> findAllPackages(@Param("salonId") String salonId);
}
