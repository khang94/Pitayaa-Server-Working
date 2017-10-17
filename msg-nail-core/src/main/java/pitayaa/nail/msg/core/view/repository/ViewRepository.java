package pitayaa.nail.msg.core.view.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.view.View;

@Service
@RepositoryRestResource(collectionResourceRel = "viewRest", path = "viewRest")

public interface ViewRepository extends
PagingAndSortingRepository<View, UUID>{
	@Query("SELECT s FROM View s where s.salonId = :salonId and s.imgType=:type")
	List<View> getAllByType(@Param("salonId") String salonId,@Param("type") int type);
}
