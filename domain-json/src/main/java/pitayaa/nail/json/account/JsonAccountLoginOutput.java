package pitayaa.nail.json.account;

import java.util.List;

import lombok.Data;
import pitayaa.nail.json.account.elements.JsonAccountDetail;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.json.salon.JsonSalon;

@Data
public class JsonAccountLoginOutput {
	
	private JsonAccountLogin jsonAccountLogin;
	private JsonAccountDetail jsonAccountDetail;
	private List<JsonSalon> salonGroup;
	private JsonHttp jsonHttp;

}
