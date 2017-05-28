package pitayaa.nail.msg.core.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.category.Category;
import pitayaa.nail.msg.core.category.repository.CategoryRepository;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.hibernate.CriteriaRepository;
import pitayaa.nail.msg.core.hibernate.QueryCriteria;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository cateRepo;

	@Autowired
	CriteriaRepository criteriaRepo;

	@Override
	public List<Category> categoriesForGroup(String groupType,String salonId ) {
		List<Category> result = new ArrayList<Category>();
		if (CoreConstant.CATEGORY_SERVICE.equalsIgnoreCase(groupType)) {
			result = cateRepo.categoriesForService(salonId);
		}
		if (CoreConstant.CATEGORY_PACKAGE.equalsIgnoreCase(groupType)) {
			result = cateRepo.categoriesForPackages(salonId);
		}
		if (CoreConstant.CATEGORY_PRODUCT.equalsIgnoreCase(groupType)) {
			result = cateRepo.categoriesForProduct(salonId);
		}
		return result;
	}

	@Override
	public Category save(Category category) {
		return cateRepo.save(category);
	}

	@Override
	public Optional<Category> findOne(UUID id) {
		return Optional.ofNullable(cateRepo.findOne(id));
	}

	@Override
	public void delete(Category category) {
		cateRepo.delete(category);
	}
}