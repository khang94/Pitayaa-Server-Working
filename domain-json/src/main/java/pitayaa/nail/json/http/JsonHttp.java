package pitayaa.nail.json.http;

import lombok.Data;

@Data
public class JsonHttp {
	
	private Object object;
	
	private String status;
	private int code;
	private String responseMessage;
}
