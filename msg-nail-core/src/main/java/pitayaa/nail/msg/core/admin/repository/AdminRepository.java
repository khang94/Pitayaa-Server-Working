package pitayaa.nail.msg.core.admin.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.admin.Admin;

@Service
@RepositoryRestResource(collectionResourceRel = "adminRest", path = "adminRest")
public interface AdminRepository extends
		PagingAndSortingRepository<Admin, UUID> { 
	


}
