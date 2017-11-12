package pitayaa.nail.msg.core.systemconf.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.systemconf.SystemConf;

@Service
@RepositoryRestResource(collectionResourceRel = "packagesRest", path = "packagesRest")
public interface SystemConfRepository  extends
PagingAndSortingRepository<SystemConf, UUID>  {
	
	@Query("Select s from SystemConf s where s.salonId = :salonId and s.type=:type and s.key=:key")
	SystemConf findModelBy(@Param("salonId") String salonId,@Param("type") String type,@Param("key") String key);
	
	@Query("Select s from SystemConf s where s.salonId = :salonId ")
	List<SystemConf> findAllBySalonId(@Param("salonId") String salonId);
}
