package pitayaa.nail.msg.core.admin.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pitayaa.nail.domain.admin.Admin;

public interface AdminService {

	Admin save(Admin admin);

	Admin findAdmin(UUID id);

	Admin initModel() throws Exception;

	Page<Admin> findAll(Pageable pageable) throws Exception;

}
