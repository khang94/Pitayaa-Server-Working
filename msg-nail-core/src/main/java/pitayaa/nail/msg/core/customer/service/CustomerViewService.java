package pitayaa.nail.msg.core.customer.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.repository.CustomerRepository;
import pitayaa.nail.msg.core.salon.service.SalonService;
import pitayaa.nail.msg.core.setting.service.SettingService;

@Service
public class CustomerViewService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerViewService.class);

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	@Autowired
	SalonService salonService;

	public Customer buildView(Customer customer) throws Exception {
		if (customer.getView() == null) {
			LOGGER.info("View in this customer = null");
			View viewBody = (View) coreHelper.createModelStructure(new View());
			customer.setView(viewBody);
		}

		// Get path
		String path = this.buildPathFile(customer);


		// Extract Image to Folder
		String pathImage = coreHelper.buildFileNameFromPath(path,
				customer.getView());
		customer.getView().setPathImage(pathImage);

		// Extract Image to Folder
		if (pathImage != null) {
			coreHelper.writeBytesToFileNio(customer.getView().getImgData(),
					pathImage);
		}

		// Update customer
		customer.getView().setImgData(null);
		customerRepo.save(customer);

		return customer;
	}
	
	public Customer buildViewByDate(Customer customer , byte[] binaryImage) throws Exception {
		// Get path
		String pathConfig = settingService
				.getFolderStoreProperties(CoreConstant.PATH_FOLDER_STORE);

		Map<String,String> mapPath = coreHelper.buildStructureFolderByDate(customer.getSalonId().toString() , customer.getUpdatedDate(),pathConfig);
		
		String staticPath = mapPath.get(CoreConstant.STATIC_PATH);
		String dynamicPath = mapPath.get(CoreConstant.DYNAMIC_PATH);
		
		//Create customer folder
		staticPath = staticPath + CoreConstant.SLASH + CoreConstant.VIEW_CUSTOMER;
		dynamicPath = dynamicPath + CoreConstant.SLASH + CoreConstant.VIEW_CUSTOMER;
		coreHelper.createFolder(staticPath);
		
		// Extract Image to Folder
		customer.getView().setModuleId(customer.getUuid().toString());
		staticPath = coreHelper.buildFileImageFromPath(staticPath,
				customer.getView());
		dynamicPath = coreHelper.buildFileImageFromPath(dynamicPath,
				customer.getView());
		
		// Update

		customer.getView().setPathImage(staticPath);
		customer.getView().setPathImageServer(dynamicPath);
		customerRepo.save(customer);
		if (staticPath != null && binaryImage != null) {
			coreHelper.writeBytesToFileNio(binaryImage,
					staticPath);
		}
		
		return customer;
	} 

	private String buildCustomerFileName(String uid, Date date) {
		String formatTime = coreHelper.getTimeFolder(date);
		return CoreConstant.VIEW_CUSTOMER + CoreConstant.UNDERLINE + uid
				+ CoreConstant.UNDERLINE + formatTime;
	}

	private String getPathBySalonId(String salonId) {
		Salon salonSaved = salonService.findOne(UUID.fromString(salonId)).get();
		return salonSaved.getView().getPathImage();
	}

	private String buildPathFile(Customer customer) {

		// Get path
		String path = this.getPathBySalonId(customer.getSalonId())
				+ CoreConstant.SLASH + CoreConstant.VIEW_CUSTOMER;

		// Build file Name
		String fileName = this.buildCustomerFileName(customer.getUuid()
				.toString(), customer.getCreatedDate());
		path = path + CoreConstant.SLASH + fileName;
		LOGGER.info("Folder name [" + fileName + "] in path [" + path + "]");

		// Return path
		return path;
	}

}
