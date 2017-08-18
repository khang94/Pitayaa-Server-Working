package pitayaa.nail.msg.core.packageEntity.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.packageEntity.repository.PackagesRepository;
import pitayaa.nail.msg.core.salon.service.SalonService;
import pitayaa.nail.msg.core.setting.service.SettingService;

@Service
public class PackageEntityViewService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PackageEntityViewService.class);

	@Autowired
	PackagesRepository packageModelRepo;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	@Autowired
	SalonService salonService;

	public PackageModel buildView(PackageModel packageModel) throws Exception {
		if (packageModel.getView() == null) {
			LOGGER.info("View in this package = null");
			View viewBody = (View) coreHelper.createModelStructure(new View());
			packageModel.setView(viewBody);
		}

		// Get path
		String path = this.buildPathFile(packageModel);

		// Extract Image to Folder
		String pathImage = coreHelper.buildFileNameFromPath(path,
				packageModel.getView());
		

		// Extract Image to Folder
		if (pathImage != null && packageModel.getView().getImgData().length > 0) {
			coreHelper.writeBytesToFileNio(packageModel.getView().getImgData(),pathImage);
			packageModel.getView().setPathImage(pathImage);
		}

		// Update packageModel
		packageModel.getView().setImgData(null);
		packageModelRepo.save(packageModel);

		return packageModel;
	}

	private String buildPackageModelFileName(String uid, Date date) {
		String formatTime = coreHelper.getTimeFolder(date);
		return CoreConstant.VIEW_PACKAGE + CoreConstant.UNDERLINE + uid
				+ CoreConstant.UNDERLINE + formatTime;
	}

	private String getPathBySalonId(String salonId) {
		Salon salonSaved = salonService.findOne(UUID.fromString(salonId)).get();
		return salonSaved.getView().getPathImage();
	}

	private String buildPathFile(PackageModel packageModel) {

		// Get path
		String path = this.getPathBySalonId(packageModel.getSalonId())
				+ CoreConstant.SLASH + CoreConstant.VIEW_PACKAGE;

		// Build file Name
		String fileName = this.buildPackageModelFileName(packageModel.getUuid()
				.toString(), packageModel.getCreatedDate());
		path = path + CoreConstant.SLASH + fileName;
		LOGGER.info("Folder name [" + fileName + "] in path [" + path + "]");

		// Return path
		return path;
	}

}
