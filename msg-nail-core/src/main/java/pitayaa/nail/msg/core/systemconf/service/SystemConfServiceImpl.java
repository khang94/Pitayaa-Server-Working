package pitayaa.nail.msg.core.systemconf.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.systemconf.SystemConf;
import pitayaa.nail.msg.core.systemconf.repository.SystemConfRepository;

@Service
public class SystemConfServiceImpl implements SystemConfService{
	@Autowired
	SystemConfRepository repo;
	
	@Override
	public Optional<SystemConf> findOne(UUID id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repo.findOne(id)) ;
		
	}
	@Override
	public SystemConf save(SystemConf model) throws Exception {
		// TODO Auto-generated method stub
		return repo.save(model);
	}
	@Override
	public Optional<SystemConf> findModelBy(String salonId, String type,
			String key) throws Exception {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repo.findModelBy(salonId, type, key));
	}

}
