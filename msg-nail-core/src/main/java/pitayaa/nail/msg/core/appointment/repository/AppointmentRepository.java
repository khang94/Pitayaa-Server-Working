package pitayaa.nail.msg.core.appointment.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.appointment.Appointment;

@Service
@RepositoryRestResource(collectionResourceRel = "appointmentsRest", path = "appointmentsRest")
public interface AppointmentRepository extends
		PagingAndSortingRepository<Appointment, UUID> {

	@Query("Select appm from Appointment appm where appm.status = :status")
	List<Appointment> findAllAppmByStatus(@Param("status") String status);
	
	@Query("Select appm from Appointment appm where appm.salonId= :salonId order by appm.createdDate DESC")
	List<Appointment> findAllAppmBySalon(@Param("salonId") String salonId);
	
	@Query("Select appm from Appointment appm where appm.salonId= :salonId and appm.createdDate between :from and :to")
	List<Appointment> findAllAppmByByConditions(@Param("salonId") String salonId , @Param("from") Date from,@Param("to") Date to);
	
	@Query("Select appm from Appointment appm where appm.customer.contact.email = :email")
	List<Appointment> findAllTurnCustomer(@Param("email") String email);

}