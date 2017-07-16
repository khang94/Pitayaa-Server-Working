package pitayaa.nail.notification.scheduler.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;

@Service
@RepositoryRestResource(collectionResourceRel = "smsQueueRest", path = "smsQueueRest")
public interface SmsQueueRepository extends PagingAndSortingRepository<SmsQueue, UUID> {
	
	@Query("SELECT queue from SmsQueue queue where queue.customerId = :customerId and "
			+ "queue.settingSmsId = :settingSmsId and queue.customerType = :customerType order by updatedDate DESC")
	public List<SmsQueue> getQueue(@Param("customerId") String customerId , @Param("settingSmsId") String settingSmsId,
			@Param("customerType") String customerType);
	
	/*@Query("SELECT queue from SmsQueue queue where queue.customerId = :customerId and "
			+ "queue.settingSmsId = :settingSmsId and queue.customerType = :customerType order by updatedDate DESC")
	public List<SmsQueue> getTopQueue(@Param("customerId") String customerId , @Param("settingSmsId") String settingSmsId,
			@Param("customerType") String customerType);*/
	

}