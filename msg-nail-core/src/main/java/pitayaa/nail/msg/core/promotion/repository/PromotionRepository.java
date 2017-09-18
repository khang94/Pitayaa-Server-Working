package pitayaa.nail.msg.core.promotion.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.Promotion;

@Service
@RepositoryRestResource(collectionResourceRel = "promotionsRest", path = "promotionsRest")
public interface PromotionRepository extends PagingAndSortingRepository<Promotion, UUID> {

	@Query("Select p from Promotion p where p.salonId = :salonId "
			+ 						"	and p.codeValue = :codeValue "
			+ 						"	and p.status = :status ")
	List<Promotion> findPromotionCode(@Param("salonId") String salonId, 
									  @Param("codeValue") String codeValue , 
									  @Param("status") String status);
	
	@Query("Select p from Promotion p where "
			+ 						"		p.salonId = :salonId "
			+ 						"	and p.status = :status ")
	List<Promotion> findActivePromotionCode( @Param("salonId") String salonId, 
									   		 @Param("status") String status);
	
	@Query("Select p from Promotion p where "
			+ 						"		p.salonId = :salonId "
			+ 						"	and p.groupId = :groupId ")
	List<Promotion> findPromotionByGroup (@Param("salonId") String salonId,
											@Param("groupId") String groupId);
	
	@Query("Select p from Promotion p where "
			+ 						"		p.salonId = :salonId "
			+ 						"	and p.groupId = :groupId "
			+ 						"	and p.status = :status ")
	List<Promotion> findPromotionByGroupAndStatus (@Param("salonId") String salonId,
											@Param("groupId") String groupId,
											@Param("status") String status);
	
	@Query("Select p from Promotion p where "
			+ 						"		p.salonId = :salonId "
			+ 						"	and p.status = :status"
			+ 						" 	and p.updatedDate between :from and :to")
	List<Promotion> findPromotionByConditions( @Param("salonId") String salonId, 
									   		 @Param("status") String status,
									   		@Param("from") Date from,
									   		@Param("to") Date to);
	
	@Query("Select p from Promotion p where "
			+ 						"		p.salonId = :salonId "
			+ 						"	and p.codeValue = :codeValue ")
	List<Promotion> findByCode( @Param("salonId") String salonId, 
									   @Param("codeValue") String codeValue);
	
	@Query("Select p from Promotion p where "
			+ 						"		p.salonId = :salonId "
			+ 						"	and p.codeValue = :codeValue ")
	List<Promotion> findPromotionCode( @Param("salonId") String salonId, 
								@Param("codeValue") String codeValue);
	
	
	
}
