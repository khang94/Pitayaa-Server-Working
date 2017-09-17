package pitayaa.nail.msg.core.promotion.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.PromotionGroup;

@Service
@RepositoryRestResource(collectionResourceRel = "promotionsGroupRest", path = "promotionsGroupRest")
public interface PromotionGroupRepository extends PagingAndSortingRepository<PromotionGroup, UUID> {

	@Query("Select p from PromotionGroup p where p.salonId = :salonId order by p.createdDate DESC")
	List<PromotionGroup> findBySalonId(@Param("salonId") String salonId);
}
