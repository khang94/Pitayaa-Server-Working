package pitayaa.nail.notification.sms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.sms.SmsModel;

@Service
@RepositoryRestResource(collectionResourceRel = "smsRest", path = "smsRest")
public interface SmsRepository extends
		PagingAndSortingRepository<SmsModel, UUID> {

	@Query("Select sms from SmsModel sms where sms.salonId = :salonId")
	List<SmsModel> findAllSms(@Param("salonId")String salonId);
	
	@Query("Select sms from SmsModel sms where sms.messageId = :messageId")
	SmsModel findByMessageId(@Param("messageId") String messageId);
	
	@Query("Select sms from SmsModel sms where sms.moduleId = :moduleId and sms.messageFor = :messageFor "
			+ "order by sms.meta.updatedDate DESC")
	List<SmsModel> findAllSms(@Param("moduleId") String moduleId ,@Param("messageFor") String messageFor);
}