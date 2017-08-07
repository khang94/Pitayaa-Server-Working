package pitayaa.nail.msg.business.json;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pitayaa.nail.json.http.JsonHttp;

@Service
public class JsonHttpServiceImpl implements JsonHttpService {

	@Override
	public JsonHttp getResponseSuccess(Object object , String messageSuccess){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(200);
		jsonHttp.setMessage(messageSuccess);
		jsonHttp.setObject(object);
		jsonHttp.setStatus("success");
		jsonHttp.setHttpCode(HttpStatus.OK);
		
		return jsonHttp;
	}
	
	@Override
	public JsonHttp getResponseError(String messageError , String exception){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(200);
		jsonHttp.setMessage(messageError);
		jsonHttp.setStatus("error");
		jsonHttp.setException(exception);
		jsonHttp.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR);
		
		return jsonHttp;
	}
	
	@Override
	public JsonHttp getNotFoundData(String messageNotFound){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(200);
		jsonHttp.setMessage(messageNotFound);
		jsonHttp.setStatus("Not Found");
		jsonHttp.setHttpCode(HttpStatus.NOT_FOUND);
		
		return jsonHttp;
	}
	
	@Override
	public JsonHttp deleteData(String messageDelete){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(204);
		jsonHttp.setMessage(messageDelete);
		jsonHttp.setStatus("success");
		jsonHttp.setHttpCode(HttpStatus.NO_CONTENT);
		
		return jsonHttp;
	}
	
	@Override 
	public JsonHttp saveData (String messageSaved){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(201);
		jsonHttp.setMessage(messageSaved);
		jsonHttp.setStatus("success");
		jsonHttp.setHttpCode(HttpStatus.CREATED);
		
		return jsonHttp;
	}

	
	
}
