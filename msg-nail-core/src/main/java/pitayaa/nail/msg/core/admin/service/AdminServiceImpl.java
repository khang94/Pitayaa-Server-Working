package pitayaa.nail.msg.core.admin.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.admin.Admin;
import pitayaa.nail.msg.core.admin.repository.AdminRepository;
import pitayaa.nail.msg.core.common.CoreHelper;

@Service  
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	CoreHelper coreHelper;
	
	@Override
	public Admin initModel() throws Exception {
		Admin adminModel = (Admin) coreHelper.createModelStructure(new Admin());
		
		return adminModel;
	}
	
	@Override
	public Admin save(Admin admin) {
		return adminRepo.save(admin);
	}

	@Override
	public Admin findAdmin(UUID id) {
		return adminRepo.findOne(id);
	}
	
	@Override
	public Page<Admin> findAll(Pageable pageable) throws Exception{
		return adminRepo.findAll(pageable);
		
	}

}
