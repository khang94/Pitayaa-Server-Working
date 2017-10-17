package pitayaa.nail.msg.core.salon.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.salon.repository.SalonRepository;
import pitayaa.nail.msg.core.setting.service.SettingService;
import pitayaa.nail.msg.core.view.service.ViewService;

@Service
public class SalonViewService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SalonViewService.class);

	@Autowired
	SalonRepository salonRepo;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	public Salon buildView(Salon salon) throws Exception {
		if (salon.getView() == null) {
			LOGGER.info("View in this salon = null");
			View viewBody = (View) coreHelper.createModelStructure(new View());
			salon.setView(viewBody);
		}

		
		// Get path
		String path = this.buildPathFolder(salon);
		salon.getView().setPathImage(path);

		// Create folder for salon
		boolean isCreated = coreHelper.createFolder(path);
		String message = (isCreated) ? "Create folder in path [" + path
				+ "] success " : "Create folder failed";
		LOGGER.info(message);

		// Extract Image to Folder
		String pathImage = coreHelper.buildFileImageFromPath(path,
				salon.getView());
		if (pathImage != null) {
			coreHelper.writeBytesToFileNio(salon.getView().getImgData(),
					pathImage);
		}
		
		// Generate Structure for salon
		settingService.generateSubfolder(path);

		// Update salon
		salon.getView().setImgData(null);
		salonRepo.save(salon);

		return salon;
	}
	
	public Salon buildViewByDate(Salon salon , byte[] binaryImg) throws Exception {
		if (salon.getView() == null) {
			LOGGER.info("View in this salon = null");
			View viewBody = (View) coreHelper.createModelStructure(new View());
			salon.setView(viewBody);
		}
		
		// Get path
		String pathConfig = settingService
				.getFolderStoreProperties(CoreConstant.PATH_FOLDER_STORE);

		Map<String,String> mapPath = coreHelper.buildStructureFolderByDate(salon.getUuid().toString() , salon.getUpdatedDate(),pathConfig);
		
		String staticPath = mapPath.get(CoreConstant.STATIC_PATH);
		String dynamicPath = mapPath.get(CoreConstant.DYNAMIC_PATH);
		
		//Create banner folder
		staticPath = staticPath + CoreConstant.SLASH + CoreConstant.BANNER_SUB_FOLDER;
		dynamicPath = dynamicPath + CoreConstant.SLASH + CoreConstant.BANNER_SUB_FOLDER;
		coreHelper.createFolder(staticPath);
		
		
		// Extract Image to Folder
		staticPath = coreHelper.buildFileImageFromPath(staticPath,
				salon.getView());
		dynamicPath = coreHelper.buildFileImageFromPath(dynamicPath,
				salon.getView());
		if (staticPath != null && binaryImg != null) {
			coreHelper.writeBytesToFileNio(binaryImg,
					staticPath);
		}
		
		// Update
		salon.getView().setModuleId(salon.getUuid().toString());
		salon.getView().setPathImage(staticPath);
		salon.getView().setPathImageServer(dynamicPath);
		salonRepo.save(salon);
		
		return salon;

	}
	


	private String buildSalonFolderName(String uid, Date date) {
		String formatTime = coreHelper.getTimeFolder(date);
		return CoreConstant.VIEW_SALON + CoreConstant.UNDERLINE + uid
				+ CoreConstant.UNDERLINE + formatTime;
	}

	private String buildPathFolder(Salon salon) {

		// Get path
		String path = settingService
				.getFolderStoreProperties(CoreConstant.PATH_FOLDER_STORE);

		// Build folder Name
		String folderName = this.buildSalonFolderName(salon.getUuid()
				.toString(), salon.getCreatedDate());
		path = path + CoreConstant.SLASH + folderName;
		LOGGER.info("Folder name [" + folderName + "] in path [" + path + "]");

		// Return path
		return path;
	}

}
