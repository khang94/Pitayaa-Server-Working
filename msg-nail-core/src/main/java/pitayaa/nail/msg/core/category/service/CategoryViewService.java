package pitayaa.nail.msg.core.category.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.category.Category;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.category.repository.CategoryRepository;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.setting.service.SettingService;
import pitayaa.nail.msg.core.view.service.ViewService;

@Service
public class CategoryViewService implements ViewService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryViewService.class);

	@Autowired
	CategoryRepository cateRepo;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	public Category buildViewByDate(Category category, byte[] binaryImg) throws Exception {
		if (category.getView() == null) {
			LOGGER.info("View in this category = null");
			View viewBody = (View) coreHelper.createModelStructure(new View());
			category.setView(viewBody);
		}

		// Get path
		String pathConfig = settingService.getFolderStoreProperties(CoreConstant.PATH_FOLDER_STORE);

		Map<String, String> mapPath = coreHelper.buildStructureFolderByDate(category.getUuid().toString(),
				category.getUpdatedDate(), pathConfig);

		String staticPath = mapPath.get(CoreConstant.STATIC_PATH);
		String dynamicPath = mapPath.get(CoreConstant.DYNAMIC_PATH);

		// Create banner folder
		staticPath = staticPath + CoreConstant.SLASH + CoreConstant.VIEW_CATEGORY;
		dynamicPath = dynamicPath + CoreConstant.SLASH + CoreConstant.VIEW_CATEGORY;
		coreHelper.createFolder(staticPath);

		// Extract Image to Folder
		staticPath = coreHelper.buildFileImageFromPath(staticPath, category.getView());
		dynamicPath = coreHelper.buildFileImageFromPath(dynamicPath, category.getView());
		if (staticPath != null && binaryImg != null) {
			coreHelper.writeBytesToFileNio(binaryImg, staticPath);
		}

		// Update
		category.getView().setModuleId(category.getUuid().toString());
		category.getView().setPathImage(staticPath);
		category.getView().setPathImageServer(dynamicPath);
		cateRepo.save(category);

		return category;

	}

}
