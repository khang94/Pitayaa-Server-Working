package pitayaa.nail.notification.sms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.sms.elements.KeyWordDeliverManagement;

@Service
@RepositoryRestResource(collectionResourceRel = "smsDeliverRest", path = "smsDeliverRest")
public interface KeyDeliverRepository extends
		PagingAndSortingRepository<KeyWordDeliverManagement, UUID> {

	@Query("Select sms from KeyWordDeliverManagement sms where sms.phoneNumber = :phoneNumber ")
	List<KeyWordDeliverManagement> findAllSmsKey(@Param("phoneNumber") String phoneNumber);
}