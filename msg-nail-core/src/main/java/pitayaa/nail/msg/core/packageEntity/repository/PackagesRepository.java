package pitayaa.nail.msg.core.packageEntity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packages.PackageModel;


@Service
@RepositoryRestResource(collectionResourceRel = "packagesRest", path = "packagesRest")
public interface PackagesRepository extends
		PagingAndSortingRepository<PackageModel, UUID> { 
	
	@Query("Select p from PackageModel p where p.salonId = :salonId")
	List<PackageModel> findAllPackages(@Param("salonId") String salonId);
}
