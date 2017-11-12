package pitayaa.nail.msg.core.view.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.view.View;
import pitayaa.nail.msg.core.view.repository.ViewRepository;

@Service
public class ViewServiceImpl implements ViewService {
	@Autowired
	ViewRepository repo;

	@Override
	public List<View> getAllByType(String uuid, int type) throws Exception {
		List<View> result = repo.getAllByType(uuid, type);
		return result;
	}

	@Override
	public View save(View dto) throws Exception {
		dto = repo.save(dto);
		return dto;
	}

	@Override
	public Optional<View> findOne(UUID id) {
		return Optional.ofNullable(repo.findOne(id));
	}

	@Override
	public void delete(View view) {
		repo.delete(view);
	}

}
