package pitayaa.nail.msg.core.salonLicense.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.salon.elements.SalonLicense;


@Service
@RepositoryRestResource(collectionResourceRel = "salonsRest", path = "salonsRest")
public interface SalonLicenseRepository extends
		PagingAndSortingRepository<SalonLicense, UUID> { 
}
