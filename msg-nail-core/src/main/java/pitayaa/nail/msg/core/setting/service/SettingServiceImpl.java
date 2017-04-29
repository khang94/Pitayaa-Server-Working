package pitayaa.nail.msg.core.setting.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pitayaa.nail.msg.core.common.CoreConstant;


@Service
public class SettingServiceImpl implements SettingService{

	private static final Logger LOGGER = LoggerFactory.getLogger(SettingServiceImpl.class);
	
	@Override
	public void initStructureFolder(){
		List<String> lstPathFolder = this.getLstPathFolder();
		LOGGER.info(lstPathFolder.toString());
		
		createFolderByPath(lstPathFolder , null);
	}
	
	@Override
	public String getFolderStoreProperties(String propertiesName) {

		Properties prop = new Properties();
		InputStream input = null;
		String propertiesValue = null;
		try {
			input = SettingServiceImpl.class.getClassLoader()
					.getResourceAsStream(CoreConstant.PATH_FILE_PROPERTIES);
			prop.load(input);
			propertiesValue = prop.getProperty(propertiesName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertiesValue;
	}
	
	private List<String> getLstPathFolder(){
		
		LOGGER.info("Create structure folder ..........");
		String path_store_folder = this.getFolderStoreProperties(CoreConstant.PATH_FOLDER_STORE);
		String sub_level = this.getFolderStoreProperties(CoreConstant.SUB_LEVEL);
		int subLevel = 0;
		
		// Parse to int
		try {
			subLevel = Integer.parseInt(sub_level);
		} catch (Exception ex){
			ex.getCause();
		}
		
		// Get Parent Folder
		String parentFolder = this.getFolderStoreProperties(CoreConstant.PARENT_FOLDER);
		
		// Get all sub folder name
		Map<String, String> mapSubFolderProperty = new HashMap<String , String>();
		for(int i = 0 ; i < subLevel ; i++){
			String subFolderName = parentFolder + CoreConstant.SUB_FOLDER_GENERIC + i;
			String valueSubFolderName = this.getFolderStoreProperties(subFolderName);
			if (valueSubFolderName != null){
				mapSubFolderProperty.put(subFolderName,valueSubFolderName);
			}
		}
		
		//build folder with 2-tier
		List<String> lstPathFolder = new ArrayList<String>();
		for (String valueSubfolder : mapSubFolderProperty.keySet()){
			String value = mapSubFolderProperty.get(valueSubfolder);
			String[] listSubfolder = value.split(",");
			for (int i = 0 ; i < listSubfolder.length ; i++ ){
				value = path_store_folder +CoreConstant.SLASH + parentFolder +CoreConstant.SLASH + listSubfolder[i];
				lstPathFolder.add(value);
			}
		}
		return lstPathFolder;
	}
	
	private void createFolderByPath(List<String> pathFolders , String parentPath){
		if(parentPath != null){
			LOGGER.info("Create folder in parent path ....");
	        File file = new File(parentPath);
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                LOGGER.info("Directory is created in path [" + parentPath + "]");
	            } else {
	                LOGGER.info("Failed to create directory in path [" + parentPath + "]");
	            }
	        }
		}
		for(String path : pathFolders){
			LOGGER.info("Create folder in list path ....");
	        File file = new File(path);
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                LOGGER.info("Directory is created in path [" + path + "]");
	            } else {
	            	parentPath = this.getParentPathFromSub(path);
	            	this.createFolderByPath(pathFolders, parentPath);
	                LOGGER.info("Failed to create directory in path [" + path + "]");
	            }
	        }
		}
	}
	
	@Override
	public void generateSubfolder(String path){
		
		// Get list subfolder
		String valuePathFolder = this.getFolderStoreProperties(CoreConstant.SALON_SUB_FOLDER);
		String[] subFolders = valuePathFolder.split(",");
		List<String> lstSubFolder = new ArrayList<String>();
		
		for(int i = 0 ; i < subFolders.length ; i++){
			String pathFolder = path + CoreConstant.SLASH + subFolders[i];
			lstSubFolder.add(pathFolder);
		}
		
		// Create Sub folder
		this.createFolderByPath(lstSubFolder, null);
	}
	
	public String getParentPathFromSub(String path){
		int lastIndexOfSlash = path.lastIndexOf(CoreConstant.SLASH);
		String result = path.substring(0, lastIndexOfSlash);
		return result;
	}
}
