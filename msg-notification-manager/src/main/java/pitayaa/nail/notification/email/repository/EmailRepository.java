package pitayaa.nail.notification.email.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.email.EmailModel;

@Service
@RepositoryRestResource(collectionResourceRel = "emailsRest", path = "emailsRest")
public interface EmailRepository extends
		PagingAndSortingRepository<EmailModel, UUID> { 
	
	@Query("Select email from EmailModel email where email.salonId = :salonId")
	List<EmailModel> findAllEmail(String salonId);
}