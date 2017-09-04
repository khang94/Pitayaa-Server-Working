package pitayaa.nail.msg.core.appointment.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.json.appointment.JsonAppointment;

public interface AppointmentService {

	Appointment save(Appointment appmBody) throws Exception;
	
	Appointment update(Appointment appmBody) throws Exception;

	List<?> findAllByQuery(QueryCriteria query) throws ClassNotFoundException;

	Optional<Appointment> findOne(UUID id);

	List<Appointment> findAllAppointmentByStatus(String status) throws Exception;

	List<Appointment> findAllAppointmentBySalon(String salonId) throws Exception;

	List<Appointment> findAllAppointmentByConditions(String salonId, Date from, Date to) throws Exception;

}
