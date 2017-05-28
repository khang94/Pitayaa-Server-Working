package pitayaa.nail.msg.core.category.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.category.Category;


@Service
@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends
		PagingAndSortingRepository<Category, UUID> { 
	
	@Query("Select C from Category C where C.cateType = 1 and C.salonId = :salonId") // Services
	List<Category> categoriesForService(@Param("salonId") String salonId);
	
	@Query("Select C from Category C where C.cateType = 2 and C.salonId = :salonId") // Packages
	List<Category> categoriesForPackages(@Param("salonId") String salonId);
	
	@Query("Select C from Category C where C.cateType = 3 and C.salonId = :salonId") // Products
	List<Category> categoriesForProduct( @Param("salonId") String salonId);
}