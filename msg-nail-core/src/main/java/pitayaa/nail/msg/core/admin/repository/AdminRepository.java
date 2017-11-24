package pitayaa.nail.msg.core.admin.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.admin.Admin;

@Service
@RepositoryRestResource(collectionResourceRel = "adminRest", path = "adminRest")
public interface AdminRepository extends
		PagingAndSortingRepository<Admin, UUID> { 
	@Query("SELECT s FROM Admin s where s.username = :username and s.password = :password")
	Admin loginAdmin(@Param("username") String username,@Param("password") String password);


}
