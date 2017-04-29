package pitayaa.nail.msg.core.product.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pitayaa.nail.domain.product.ProductModel;

@RepositoryRestResource(collectionResourceRel = "productRest", path = "productRest")
public interface ProductRepository
		extends
			PagingAndSortingRepository<ProductModel, UUID> {
	@Query("Select p from ProductModel p where p.salonId = :salonId")
	List<ProductModel> findAll(@Param("salonId") String salonId);
}