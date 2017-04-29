package pitayaa.nail.msg.core.appointment.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.msg.business.appointment.AppointmentBus;
import pitayaa.nail.msg.core.appointment.business.AppointmentBusiness;
import pitayaa.nail.msg.core.appointment.repository.AppointmentRepository;
import pitayaa.nail.msg.core.hibernate.CriteriaRepository;
import pitayaa.nail.msg.core.hibernate.SearchCriteria;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepo;

	@Autowired
	AppointmentBus appointmentBus;

	@Autowired
	AppointmentBusiness appmBusThis;

	@Autowired
	CriteriaRepository appmCriteria;

	@Override
	public List<Appointment> findAllAppointmentByStatus(String status) throws Exception {
		List<Appointment> lstAppointment = appointmentRepo.findAllAppmByStatus(status);
		return lstAppointment;
	}
	
	@Override
	public List<Appointment> findAllAppointmentBySalon(String salonId) throws Exception{
		List<Appointment> lstAppointment = appointmentRepo.findAllAppmBySalon(salonId);
		return lstAppointment;
	}

	@Override
	public Appointment save(Appointment appmBody) throws Exception {

		// Execute business
		appmBody = appmBusThis.executeCreateAppm(appmBody);

		// Save to DB
		appmBody = appointmentRepo.save(appmBody);

		return appmBody;
	}
	
	@Override
	public Appointment update(Appointment appmBody) throws Exception {

		// Save to DB
		appmBody = appointmentRepo.save(appmBody);

		return appmBody;
	}

	@Override
	public List<?> findAllByQuery(QueryCriteria query)
			throws ClassNotFoundException {
		SearchCriteria sc = appmCriteria.extractQuery(query.getQuery());
		sc.setEntity(query.getObject());
		return appmCriteria.searchCriteria(sc);
	}

	@Override
	public Optional<Appointment> findOne(UUID id) {
		return Optional.ofNullable(appointmentRepo.findOne(id));
	}
}
