package pitayaa.nail.msg.core.appointment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.msg.core.appointment.service.AppointmentService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.service.CustomerService;

@Controller
public class AppointmentController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private AppointmentService appmService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "appointments/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Appointment appointment = (Appointment) coreHelper.createModelStructure(new Appointment());

		return ResponseEntity.ok(appointment);
	}

	@RequestMapping(value = "appointments/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findAllSalon(
			@RequestParam(name = "salonId", defaultValue = "", required = false) String salonId,
			@RequestParam(name = "status", defaultValue = "", required = false) String status) throws Exception {

		List<Appointment> appointmentLst = new ArrayList<Appointment>();
		if(!"".equalsIgnoreCase(salonId)){
			appointmentLst = appmService.findAllAppointmentBySalon(salonId);
		} else if (!"".equalsIgnoreCase(status)){
			appointmentLst = appmService.findAllAppointmentByStatus(status);
		}

		return ResponseEntity.ok(appointmentLst);
	}

	@RequestMapping(value = "appointments/search", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> findAll(@RequestBody QueryCriteria query) throws Exception {

		List<?> appointmentLst = (List<Appointment>) appmService.findAllByQuery(query);
		return ResponseEntity.ok(appointmentLst);
	}

	@RequestMapping(value = "appointments/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createAppointment(@RequestBody Appointment appmBody) throws Exception {

		Appointment appm = appmService.save(appmBody);
		return ResponseEntity.ok(appm);
	}

	@RequestMapping(value = "appointments/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateAppointment(@PathVariable("Id") UUID uid,
			@RequestBody Appointment appmBody) throws Exception {

		Optional<Appointment> appointmentSaved = appmService.findOne(uid);

		if (!appointmentSaved.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		appmBody = appmService.update(appmBody);

		return ResponseEntity.ok(appmBody);
	}

	@RequestMapping(value = "appointments/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("Id") UUID uid) throws Exception {

		Optional<Appointment> appointmentSaved = appmService.findOne(uid);

		if (!appointmentSaved.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(appointmentSaved.get());
	}
}
