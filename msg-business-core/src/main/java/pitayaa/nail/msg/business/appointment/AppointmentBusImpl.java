package pitayaa.nail.msg.business.appointment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.json.appointment.JsonAppointment;
import pitayaa.nail.msg.business.helper.BusinessHelper;
import pitayaa.nail.msg.business.helper.GsonCustom;

import com.google.gson.Gson;

@Service
public class AppointmentBusImpl implements AppointmentBus {

	@Autowired
	BusinessHelper businessHelper;
	
	@Override
	public Appointment createAppointment(Appointment appmBody) throws Exception {
		return appmBody;
		
	}
	
	
	@Override
	public JsonAppointment parseAppointmentToJson (Appointment appointment) throws Exception{
		
		GsonCustom gsonCustom = businessHelper.getJsonFromObject(appointment);
		
		// Extract gson custom
		Gson gson = gsonCustom.getGsonObject();
		String jsonInString = gsonCustom.getJsonString();
		
		// Parse to json Account
		JsonAppointment jsonAppointment = gson.fromJson(jsonInString, JsonAppointment.class);
		
		return jsonAppointment;
	}
	
	@Override
	public List<JsonAppointment> parseListAppointmentToJson (List<Appointment> lstAppm) throws Exception{
		
/*		GsonCustom gsonCustom = businessHelper.getJsonFromObject(lstAppm);
		
		// Extract gson custom
		Gson gson = gsonCustom.getGsonObject();
		String jsonInString = gsonCustom.getJsonString();*/
		
		// Parse to json Account
		List<JsonAppointment> jsonAppmList = new ArrayList<JsonAppointment>();
		for (Appointment appm : lstAppm){
			GsonCustom gsonCustom = businessHelper.getJsonFromObject(appm);
			
			Gson gson = gsonCustom.getGsonObject();
			String jsonString = gsonCustom.getJsonString();
			
			JsonAppointment jsonAppointment = gson.fromJson(jsonString, JsonAppointment.class);
			jsonAppmList.add(jsonAppointment);
		}
		return jsonAppmList;
	}

}
