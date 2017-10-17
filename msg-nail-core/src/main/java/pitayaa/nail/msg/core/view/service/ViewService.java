package pitayaa.nail.msg.core.view.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.view.View;

public interface ViewService {
	List<View> getAllByType(String uuid,int type) throws Exception;
	public View save(View dto) throws Exception;
	public Optional<View> findOne(UUID id);
	public void delete(View view);

}
