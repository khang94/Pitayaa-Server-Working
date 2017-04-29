package pitayaa.nail.msg.core.setting.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.domain.setting.SettingSms;

@Service
@RepositoryRestResource(collectionResourceRel = "settingRest", path = "settingRest")
public interface SettingRepository extends PagingAndSortingRepository<SettingSms, UUID> {
	@Query("SELECT s FROM ServiceModel s where s.salonId = :salonId")
	List<ServiceModel> getServicesBySalonId(@Param("salonId") String salonId);
}
