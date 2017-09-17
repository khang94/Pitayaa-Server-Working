package pitayaa.nail.msg.core.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.view.View;

@Service
public class CoreHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CoreHelper.class);
	
	public Object createModelStructure(Object emptyModel) throws Exception{
		List<Object> objects = new ArrayList<>();
		objects.add(emptyModel);
		while(objects.size()>0){
			Object loopObject = objects.get(0);
			objects.remove(0);
			// Field in Class
			Field[] fields = null;
			Method[] methods = null;
			if(loopObject.getClass().isAssignableFrom(String.class) ||
					loopObject.getClass().isAssignableFrom(Date.class) || 
					loopObject.getClass().isAssignableFrom(Boolean.class) || 
					loopObject.getClass().isAssignableFrom(boolean.class)){
				continue;
			} else {
				fields = loopObject.getClass().getDeclaredFields();
				methods = loopObject.getClass().getDeclaredMethods();
				
			}
			
			for(Field field : fields){
				//LOGGER.info("Field Name [" + field.getName() + "] & field type [" + field.getType() + "]");

				Class<?> clazzField = field.getType();
				if(List.class.isAssignableFrom(clazzField)){
					//Handle List
					List<Object> list = new ArrayList<>();
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Class<?> member = (Class<?>) listType.getActualTypeArguments()[0];
					Object memberObject = member.newInstance();
					list.add(memberObject);
					Method setMethod = getMethodByName(CoreConstant.SET+ field.getName(),methods);
					setMethod.invoke(loopObject,list);
					objects.add(memberObject);
				}else if(String.class.isAssignableFrom(clazzField) || Long.class.isAssignableFrom(clazzField)
						|| byte[].class.isAssignableFrom(clazzField) || Date.class.isAssignableFrom(clazzField)
						|| UUID.class.isAssignableFrom(clazzField) || Boolean.class.isAssignableFrom(clazzField)
						|| Double.class.isAssignableFrom(clazzField) || Integer.class.isAssignableFrom(clazzField)){
					continue;
				}else if(Object.class.isAssignableFrom(clazzField)){
					// handle object
					Method setMethod = getMethodByName(CoreConstant.SET+ field.getName(),methods);
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
						Method setMethod = getMethodByName(CoreConstant.SET+ field.getName(),methodSuppers);
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
						Method setMethod = getMethodByName(CoreConstant.SET+ field.getName(),methodSuppers);
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

	
	public String getTimeFolder(Date date){
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss");   

		String dateFormat = df.format(date);

		// Print what date is today!
		LOGGER.info("Date after modify: " + dateFormat);
		
		return dateFormat;
	}
	
    public void writeBytesToFileNio(byte[] bFile, String fileDest) {

    	File file = new File(fileDest);
        try {
            Path path = Paths.get(file.getPath());
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
	public String buildFileImageFromPath(String path  , View view){
		String fileName = "";
		String pathExtracted = "";
		String nameImage = this.getTimeFolder(new Date());
		if(view.getFileName() != null && view.getExtension() != null){
			//Date dateDefault = new Date();
			//String nameImage = dateDefault.toString();
			fileName = nameImage + CoreConstant.DOT + view.getExtension();
			fileName = CoreConstant.SLASH + fileName;
			pathExtracted = path + fileName;
		} else {
			//Date dateDefault = new Date();
			//String nameImage = dateDefault.toString();
			pathExtracted = path + CoreConstant.SLASH + view.getModuleId() + "_" + 
					nameImage + CoreConstant.DEFAULT_IMAGE_EXTENSION;
		}
		return pathExtracted;
	}
	
	public String buildFileNameFromPath(String path  , View view){
		String fileName = "";
		if(view.getFileName() != null && view.getExtension() != null){
			fileName = CoreConstant.UNDERLINE + view.getFileName() + CoreConstant.DOT + view.getExtension();
			path = path + fileName;
			return path;
		}
		return null;
	}
	
	public boolean createFolder(String path){
		LOGGER.info("Create folder in path [" + path + "]");
        boolean isCreated = false;
		File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
            	isCreated = true;
                LOGGER.info("Directory is created in path [" + path + "]");
            } else {
            	isCreated = false;
                LOGGER.info("Failed to create directory in path [" + path + "]");
            }
        }
        return isCreated;
	}

	public HashMap<String,String> getTimeFromDate (Date date){
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String year  = Integer.toString(localDate.getYear());
		String month = Integer.toString(localDate.getMonthValue());
		String day   = Integer.toString(localDate.getDayOfMonth());
		
		HashMap<String, String> mapTime = new HashMap<String,String>();
		mapTime.put("DAY", day);
		mapTime.put("MONTH", month);
		mapTime.put("YEAR", year);
		
		return mapTime;
	}
	
	public Map<String,String> buildStructureFolderByDate(String uid , Date date , String path){
		Map<String,String> mapTime = this.getTimeFromDate(date);
		
		String year = mapTime.get("YEAR");
		String month = mapTime.get("MONTH");
		String day = mapTime.get("DAY");
		
		String dynamicPath = "";
		
		dynamicPath = dynamicPath + CoreConstant.SLASH + uid;
		path = path + dynamicPath;
		this.createFolder(path);
		
		dynamicPath = dynamicPath + CoreConstant.SLASH + year;
		path = path + CoreConstant.SLASH + year;
		this.createFolder(path);
			
		dynamicPath = dynamicPath + CoreConstant.SLASH + month;
		path = path + CoreConstant.SLASH + month;
		this.createFolder(path);
			
		dynamicPath = dynamicPath + CoreConstant.SLASH + day;
		path = path + CoreConstant.SLASH + day;
		this.createFolder(path);
		
		Map<String,String> mapPath = new HashMap<String,String>();
		mapPath.put("STATIC_PATH", path);
		mapPath.put("DYNAMIC_PATH",dynamicPath);
		
		return mapPath;
	}
	
	public boolean deleteFile (String fileName){
		File file = new File(fileName);
		
		boolean result = false;
		if(file.delete()){
			result = true;
			LOGGER.info("Delete file [" + fileName + "] success");
		} else {
			LOGGER.info("Delete file [" + fileName + "] failed");
		}
		return result;
	}
	
	public Double getPercentage(Double value){
		value = this.roundDoubleValue(value, 2);
		
		value = value * 100;
		
		DecimalFormat df1 = new DecimalFormat("##.##"); 
		DecimalFormat df2 = new DecimalFormat("#.##"); 
		
		if(value < 10){
			value = Double.valueOf(df2.format(value));
		} else {
			value = Double.valueOf(df1.format(value));
		}
		
		return value;
	}
	
	public Double roundDoubleValue(Double value, int digits) {
		
		// Get result
		Double scale = Math.pow(10, digits);
		Double result = Math.round(value * scale) / scale;
		
		// Format
		DecimalFormat dtime = new DecimalFormat("#.####"); 
		result = Double.valueOf(dtime.format(value));
		
	    return result;
	}
	
	



}
