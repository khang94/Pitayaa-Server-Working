package pitayaa.nail.msg.business.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pitayaa.nail.json.http.JsonHttp;

@Service
public class JsonHttpServiceImpl implements JsonHttpService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonHttpServiceImpl.class);

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
		LOGGER.info("Error Content :[{}] , Error Detail [{}] :", messageError , exception);
		
		return jsonHttp;
	}
	
	@Override
	public JsonHttp getNotFoundData(String messageNotFound){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(200);
		jsonHttp.setMessage(messageNotFound);
		jsonHttp.setStatus("Not Found");
		jsonHttp.setHttpCode(HttpStatus.NOT_FOUND);
		LOGGER.info("Error Content :[{}] ", messageNotFound);
		
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
	public JsonHttp saveDataSuccess (Object object , String messageSaved){
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp.setCode(201);
		jsonHttp.setObject(object);
		jsonHttp.setMessage(messageSaved);
		jsonHttp.setStatus("success");
		jsonHttp.setHttpCode(HttpStatus.CREATED);
		
		return jsonHttp;
	}

	
	
}
