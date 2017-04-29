package pitayaa.nail.msg.business.appointment;

import java.util.List;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.json.appointment.JsonAppointment;

public interface AppointmentBus {

	JsonAppointment parseAppointmentToJson(Appointment appointment)
			throws Exception;

	Appointment createAppointment(Appointment appmBody) throws Exception;

	List<JsonAppointment> parseListAppointmentToJson(List<Appointment> lstAppm)
			throws Exception;


}
