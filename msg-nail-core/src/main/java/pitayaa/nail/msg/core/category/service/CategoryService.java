package pitayaa.nail.msg.core.category.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.category.Category;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.msg.core.hibernate.QueryCriteria;

public interface CategoryService {

	List<Category> categoriesForGroup(String groupType);
	
	Category save(Category category);

	Optional<Category> findOne(UUID id);

	void delete(Category category);

}