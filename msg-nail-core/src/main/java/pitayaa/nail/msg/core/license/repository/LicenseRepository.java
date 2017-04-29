package pitayaa.nail.msg.core.license.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.license.License;


@Service
@RepositoryRestResource(collectionResourceRel = "licensesRest", path = "licensesRest")
public interface LicenseRepository extends
		PagingAndSortingRepository<License, UUID> { 
	
	@Query("Select L from License L where L.isTrial = true ")
	License getLicenseTrial();
}
