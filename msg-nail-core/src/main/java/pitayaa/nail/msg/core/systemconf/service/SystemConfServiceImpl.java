package pitayaa.nail.msg.core.systemconf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.systemconf.SystemConf;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.file.service.FileUpload;
import pitayaa.nail.msg.core.systemconf.repository.SystemConfRepository;
import pitayaa.nail.msg.core.view.service.ViewService;

@Service
public class SystemConfServiceImpl implements SystemConfService{
	
	@Autowired
	SystemConfRepository repo;
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	FileUpload fileUpload;
	
	@Override
	public Optional<SystemConf> findOne(UUID id) {
		return Optional.ofNullable(repo.findOne(id)) ;
		
	}
	@Override
	public SystemConf save(SystemConf model) throws Exception {
		return repo.save(model);
	}
	@Override
	public Optional<SystemConf> findModelBy(String salonId, String type,
			String key) throws Exception {
		return Optional.ofNullable(repo.findModelBy(salonId, type, key));
	}
	
	@Override
	public List<SystemConf> getAllConfigPreferences(String salonId) throws Exception {
		return repo.findAllBySalonId(salonId);
	}
	
	@Override
	public List<SystemConf> updateSystemConfiguration(String salonId , List<SystemConf> systemConfigurations) throws Exception {
		
		List<SystemConf> systemData = new ArrayList<>();
		
		for(SystemConf systemConfiguration : systemConfigurations){
			systemData.add(repo.save(systemConfiguration));
		}
		return systemData;
	}
	
	@Override
	public SystemConf uploadImageToSetting(View view , String salonId , SystemConf systemData) throws Exception {
		
		// Get path from file
		String pathImage = fileUpload.saveFile(view);
		
		// Save view
		view.setImgData(null);
		view = viewService.save(view);
		
		// Save system configuration
		systemData.setPathImage(pathImage);
		systemData.setViewId(view.getUuid().toString());
		
		return repo.save(systemData);
	}

}
