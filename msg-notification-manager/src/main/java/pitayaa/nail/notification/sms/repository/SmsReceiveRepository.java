package pitayaa.nail.notification.sms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.sms.SmsReceive;

@Service
@RepositoryRestResource(collectionResourceRel = "smsReceiveRest", path = "smsReceiveRest")
public interface SmsReceiveRepository extends
		PagingAndSortingRepository<SmsReceive, UUID> {

	@Query("Select sms from SmsReceive sms where sms.salonId = :salonId")
	List<SmsReceive> findAllSms(String salonId);
}