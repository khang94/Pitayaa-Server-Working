package pitayaa.nail.msg.core.setting.promotion.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingPromotion;


@Service
@RepositoryRestResource(collectionResourceRel = "settingPromotionRest", path = "settingPromotionRest")
public interface SettingPromotionRepository extends
		PagingAndSortingRepository<SettingPromotion, UUID> { 
	@Query("SELECT sp FROM SettingPromotion sp where sp.salonId = :salonId")
	SettingPromotion getSettingPromotion(@Param("salonId") String salonId);
}
