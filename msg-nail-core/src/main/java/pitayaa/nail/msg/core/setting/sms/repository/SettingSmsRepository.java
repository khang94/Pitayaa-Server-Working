package pitayaa.nail.msg.core.setting.sms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingSms;


@Service
@RepositoryRestResource(collectionResourceRel = "settingSmsRest", path = "settingSmsRest")
public interface SettingSmsRepository extends
		PagingAndSortingRepository<SettingSms, UUID> { 
	@Query("select ss from SettingSms ss where ss.salonId = :salonId ")
	List<SettingSms> getListSetting(@Param("salonId") String salonId);
}
