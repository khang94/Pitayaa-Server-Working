package pitayaa.nail.msg.core.salon.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.salon.Salon;


@Service
@RepositoryRestResource(collectionResourceRel = "salonsRest", path = "salonsRest")
public interface SalonRepository extends
		PagingAndSortingRepository<Salon, UUID> { 
}
