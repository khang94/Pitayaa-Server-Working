package pitayaa.nail.notification.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.common.KeyValueModel;


@Service
public class NotificationHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationHelper.class);
	
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
				Class<?> clazzField = field.getType();
				if(List.class.isAssignableFrom(clazzField)){
					//Handle List
					List<Object> list = new ArrayList<>();
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Class<?> member = (Class<?>) listType.getActualTypeArguments()[0];
					Object memberObject = member.newInstance();
					
					boolean specialObjectWrapper = (memberObject instanceof String) || (memberObject instanceof Integer)
												|| (memberObject instanceof Double);
					
					list.add(memberObject);
					Method setMethod = getMethodByName(NotificationConstant.SET+ field.getName(),methods);
					setMethod.invoke(loopObject,list);
					
					if (specialObjectWrapper){
						continue;
					}
					objects.add(memberObject);
				}else if(String.class.isAssignableFrom(clazzField) || Long.class.isAssignableFrom(clazzField)
						|| byte[].class.isAssignableFrom(clazzField) || Date.class.isAssignableFrom(clazzField)
						|| UUID.class.isAssignableFrom(clazzField) || Boolean.class.isAssignableFrom(clazzField)){
					continue;
				}else if(Object.class.isAssignableFrom(clazzField)){
					// handle object
					Method setMethod = getMethodByName(NotificationConstant.SET+ field.getName(),methods);
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
						Method setMethod = getMethodByName(NotificationConstant.SET+ field.getName(),methodSuppers);
						setMethod.invoke(loopObject,list);
						objects.add(memberObject);
					}/*else if(Date.class.isAssignableFrom(clazzField)){
						Method setMethod = getMethodByName(EmailConstant.SET+ field.getName(),methods);
						Date date = new Date();
						setMethod.invoke(loopObject, date);
					}*/else if(UUID.class.isAssignableFrom(clazzField) || Long.class.isAssignableFrom(clazzField)
							|| String.class.isAssignableFrom(clazzField)|| Date.class.isAssignableFrom(clazzField)
							|| byte[].class.isAssignableFrom(clazzField)){
						continue;
					}else if(Object.class.isAssignableFrom(clazzField)){
						// handle object
						Method setMethod = getMethodByName(NotificationConstant.SET+ field.getName(),methodSuppers);
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
	public Date getCurrentTimeGMT() {
		Date date = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		date = cal.getTime();
		LOGGER.info(date.toString());

		return date;
	}
	
	public Method getMethodByName(String name, Method[] methods){
		for(Method method : methods){
			if(method.getName().equalsIgnoreCase(name)){
				return method;
			}
		}
		return null;
	}
	public KeyValueModel initKeyValue(Class<?> clazzField){
			KeyValueModel keyValue = new KeyValueModel();
			keyValue.setKey(new String());
			keyValue.setValue(new String());
			return keyValue;
	}

	public boolean validateElement(List<KeyValueModel> lstElement) {
		if (lstElement.size() > 0) {
			for (KeyValueModel element : lstElement) {
				if (element.getValue() == null) {
					return false;
				}
			}
		}
		return true;
	}

	public String[] getData(List<KeyValueModel> lstKeyValue) {
		String[] result = null;
		if (validateElement(lstKeyValue)) {
			result = new String[lstKeyValue.size()];
			List<String> lstValue = new ArrayList<String>();
			for (KeyValueModel key : lstKeyValue) {
				if (key.getValue() != null) {
					String sendTo = key.getValue();
					lstValue.add(sendTo);
				}
			}
			lstValue.toArray(result);
		}
		return result;
	}
}
