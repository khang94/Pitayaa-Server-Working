package pitayaa.nail.json.http;

import lombok.Data;

@Data
public class JsonHttp {
	
	public static final String SUCCESS="success";
	public static final String ERROR="error";
	
	private Object object;
	
	private String status;
	private int code;
	private String message;
}
