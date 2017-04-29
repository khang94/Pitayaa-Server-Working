package pitayaa.nail.msg.core.promotion.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.Promotion;

@Service
@RepositoryRestResource(collectionResourceRel = "promotionsRest", path = "promotionsRest")
public interface PromotionRepository extends PagingAndSortingRepository<Promotion, UUID> {

}
