package pitayaa.nail.msg.business.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ModelConverter {

	public static <T> T convert(Object object, Class<T> targetType) {
		T result = null;
		if (object != null) {
			try {
				result = targetType.newInstance();
			} catch (InstantiationException e1) {
				return null;
			} catch (IllegalAccessException e1) {
				return null;
			}
			Class<?> objClass = object.getClass();
			Field[] fields = targetType.getDeclaredFields();
			for (Field field : fields) {
				try {
					getSetter(targetType, field.getName(), field.getType())
							.invoke(result,
									getGetter(objClass, field.getName())
											.invoke(object));
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	public static <T> List<T> convert(List<?> listObject, Class<T> targetType) {
		List<T> result = new ArrayList<T>();
		if (listObject == null || listObject.size() == 0)
			return result;
		for (Object object : listObject) {
			result.add(convert(object, targetType));
		}
		return result;
	}

	public static Method getGetter(Class<?> clazz, String fieldName) {
		if (clazz == null || fieldName == null) {
			return null;
		}
		try {
			return clazz.getMethod("get"
					+ fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1));
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getSetter(Class<?> clazz, String fieldName,
			Class<?> fieldType) {
		if (clazz == null || fieldName == null) {
			return null;
		}
		try {
			return clazz.getMethod(
					"set" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1), fieldType);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

}
