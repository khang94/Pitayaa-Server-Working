package pitayaa.nail.msg.business.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import pitayaa.nail.msg.business.constant.BusinessConstant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class BusinessHelper {
	
	public Object createModelStructure(Object emptyModel) throws Exception{
		List<Object> objects = new ArrayList<>();
		objects.add(emptyModel);
		while(objects.size()>0){
			Object loopObject = objects.get(0);
			objects.remove(0);
			// Field in Class
			Field[] fields = loopObject.getClass().getDeclaredFields();
			Method[] methods = loopObject.getClass().getDeclaredMethods();
			for(Field field : fields){
				if(field.getName().contains("value")){
					//LOGGER.info("bugg");
					break;
				}
				Class<?> clazzField = field.getType();
				if(List.class.isAssignableFrom(clazzField)){
					//Handle List
					List<Object> list = new ArrayList<>();
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Class<?> member = (Class<?>) listType.getActualTypeArguments()[0];
					Object memberObject = member.newInstance();
					list.add(memberObject);
					Method setMethod = getMethodByName(BusinessConstant.SET+ field.getName(),methods);
					setMethod.invoke(loopObject,list);
					objects.add(memberObject);
				}/*else if(Date.class.isAssignableFrom(clazzField)){
					Method setMethod = getMethodByName(EmailConstant.SET+ field.getName(),methods);
					Date date = new Date();
					setMethod.invoke(loopObject, date);
				}*/else if(String.class.isAssignableFrom(clazzField) || Long.class.isAssignableFrom(clazzField)
						|| byte[].class.isAssignableFrom(clazzField) || Date.class.isAssignableFrom(clazzField)
						|| UUID.class.isAssignableFrom(clazzField) || Boolean.class.isAssignableFrom(clazzField)
						|| Double.class.isAssignableFrom(clazzField) || Integer.class.isAssignableFrom(clazzField)){
					continue;
				}else if(Object.class.isAssignableFrom(clazzField)){
					// handle object
					Method setMethod = getMethodByName(BusinessConstant.SET+ field.getName(),methods);
					Object fieldObject = clazzField.newInstance();
					setMethod.invoke(loopObject, fieldObject);
					objects.add(fieldObject);
					
					
				}
			}
			//Filed in Supper Class
			Class<?> supperClazz = loopObject.getClass().getSuperclass();
			while(supperClazz!=null){
				Field[] fieldSuppers = supperClazz.getDeclaredFields();
				Method[] methodSuppers = supperClazz.getDeclaredMethods();
				for(Field field : fieldSuppers){
					Class<?> clazzField = field.getType();
					if(List.class.isAssignableFrom(clazzField)){
						//Handle List
						List<Object> list = new ArrayList<>();
						ParameterizedType listType = (ParameterizedType) field.getGenericType();
						Class<?> member = (Class<?>) listType.getActualTypeArguments()[0];
						Object memberObject = member.newInstance();
						list.add(memberObject);
						Method setMethod = getMethodByName(BusinessConstant.SET+ field.getName(),methodSuppers);
						setMethod.invoke(loopObject,list);
						objects.add(memberObject);
					}/*else if(Date.class.isAssignableFrom(clazzField)){
						Method setMethod = getMethodByName(EmailConstant.SET+ field.getName(),methods);
						Date date = new Date();
						setMethod.invoke(loopObject, date);
					}*/else if(UUID.class.isAssignableFrom(clazzField) || Long.class.isAssignableFrom(clazzField)
							|| String.class.isAssignableFrom(clazzField)|| Date.class.isAssignableFrom(clazzField)){
						continue;
					}else if(Object.class.isAssignableFrom(clazzField)){
						// handle object
						Method setMethod = getMethodByName(BusinessConstant.SET+ field.getName(),methodSuppers);
						Object fieldObject = clazzField.newInstance();
						setMethod.invoke(loopObject, fieldObject);
						objects.add(fieldObject);
					}
				}
				supperClazz = supperClazz.getSuperclass();
			}
		}
		
		return emptyModel;
	}
	
	public Method getMethodByName(String name, Method[] methods){
		for(Method method : methods){
			if(method.getName().equalsIgnoreCase(name)){
				return method;
			}
		}
		return null;
	}

	
	public Date getTimeNow(){
		ZonedDateTime now = ZonedDateTime.now( ZoneOffset.systemDefault());
		Date dateNow = Date.from(now.toInstant());
		return dateNow;
	}
	
	public String getZonedDateTimeNow(){
		ZonedDateTime now = ZonedDateTime.now( ZoneOffset.systemDefault());
		return now.toString();
	}
	
	public GsonCustom getJsonFromObject(Object ob) throws JsonProcessingException{
		// Create system constructor
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();

		//Get object as String & build gson
		String jsonInString = mapper.writeValueAsString(ob);
		gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
		 
		// Assign value to gson custom
		GsonCustom gsonUsing = new GsonCustom();
		gsonUsing.setJsonString(jsonInString);
		gsonUsing.setGsonObject(gson);
		
		// return
		return gsonUsing;
	}
}
