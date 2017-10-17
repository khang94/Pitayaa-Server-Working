package pitayaa.nail.msg.core.file.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.enumtype.ImageType;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.setting.service.SettingService;

@Service
public class FileUploadImpl implements FileUpload {
	@Autowired
	SettingService settingService;
	

	@Autowired
	CoreHelper coreHelper;
	
	

	@Override
	public String saveFile(View view) throws Exception {
		// TODO Auto-generated method stub
		//get timestamp is name of image
		String fileName=(new Date()).getTime()+"";
		view.setFileName(fileName);
		String pathConfig = settingService
				.getFolderStoreProperties(CoreConstant.PATH_FOLDER_STORE);

		Map<String,String> mapPath = coreHelper.buildStructureFolderByDate(view.getSalonId(), new Date(),pathConfig);
		
		String staticPath = mapPath.get(CoreConstant.STATIC_PATH);
		String dynamicPath = mapPath.get(CoreConstant.DYNAMIC_PATH);
		String imageType = "common";

		//Create customer folder
		if(ImageType.SMALL_BANNER==ImageType.parseValue(view.getImgType())||ImageType.LARGE_BANNER==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_BANNER;
		}else if(ImageType.LOGO==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_LOGO;

		}else if(ImageType.CUSTOMER==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_CUSTOMER;

		}else if(ImageType.SERVICE==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_SERVICE;

		}else if(ImageType.PACKAGE==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_PACKAGE;

		}else if(ImageType.PRODUCT==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_PRODUCT;

		}else if(ImageType.CATEGORY==ImageType.parseValue(view.getImgType())){
			imageType=CoreConstant.VIEW_CATEGORY;

		}
		staticPath = staticPath + '/' + imageType;
		dynamicPath = dynamicPath + '/' + imageType;
		coreHelper.createFolder(staticPath);
		
		// save Image to Folder
		staticPath = coreHelper.buildFileImageFromPath(staticPath,
				view);
		dynamicPath = coreHelper.buildFileImageFromPath(dynamicPath,
				view);
		
		if (staticPath != null && view.getImgData() != null) {
			coreHelper.writeBytesToFileNio(view.getImgData(),
					staticPath);
		}
		return dynamicPath;	
		}
}
