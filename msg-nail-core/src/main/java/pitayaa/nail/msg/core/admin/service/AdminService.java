package pitayaa.nail.msg.core.admin.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.admin.Admin;

public interface AdminService {

	Admin save(Admin admin);

	Admin register(Admin admin) throws Exception;

	
	Admin findAdmin(UUID id);

	Admin initModel() throws Exception;

	Page<Admin> findAll(Pageable pageable) throws Exception;
	
	Admin login(Admin admin) throws Exception;
	void delete(
			Admin admin);


}
