package pitayaa.nail.msg.core.category.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.category.Category;

public interface CategoryService {

	Category save(Category category);

	Optional<Category> findOne(UUID id);

	void delete(Category category);

	List<Category> categoriesForGroup(String groupType, String salonId);

}