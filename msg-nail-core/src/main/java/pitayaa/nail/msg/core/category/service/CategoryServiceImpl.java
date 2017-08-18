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
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.hibernate.CriteriaRepository;
import pitayaa.nail.msg.core.hibernate.QueryCriteria;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository cateRepo;

	@Autowired
	CriteriaRepository criteriaRepo;
	
	@Autowired
	private CoreHelper coreHelper;
	
	@Autowired
	private CategoryViewService viewService;
	
	

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
	public Category save(Category category) throws Exception {
		
		byte[] binaryImg = null;
		boolean isUploadImage = false;
		
		// Get stream image
		if (category.getView().getImgData().length > 0){
			binaryImg = category.getView().getImgData();
			isUploadImage = true;
		}
		
		// Hide image
		category.getView().setImgData(null);
		
		category = cateRepo.save(category);
		
		if(isUploadImage && category.getSalonId() != null){
			viewService.buildViewByDate(category, binaryImg);
		}
		
		return category;
	}
	
	@Override
	public Category update(Category categorySaved , Category categoryUpdated) throws Exception {
		
		byte[] binaryImg = null;
		boolean isUpdatedImage = false;
		
		// Update New Image
		if (categoryUpdated.getView().getImgData() != null && 
				categoryUpdated.getView().getImgData().length > 0){
			isUpdatedImage = true;
			binaryImg = categoryUpdated.getView().getImgData();
		}
		
		// Hide image
		categoryUpdated.getView().setImgData(null);
		
		if (isUpdatedImage && categorySaved.getView().getImgData()!= null){
			// Delete image from static path in local server
			coreHelper.deleteFile(categorySaved.getView().getPathImage());
		}
		
		// Update hibernate
		categoryUpdated.setUuid(categorySaved.getUuid());
		categoryUpdated = cateRepo.save(categoryUpdated);
		
		if(isUpdatedImage && categoryUpdated.getSalonId() != null ){
			viewService.buildViewByDate(categoryUpdated, binaryImg);
		}
		
		return categoryUpdated;
	}

	@Override
	public Optional<Category> findOne(UUID id) {
		return Optional.ofNullable(cateRepo.findOne(id));
	}

	@Override
	public void delete(Category category) {
		cateRepo.delete(category);
	}
	
	@Override
	public Category initModel() throws Exception{
		Category category = (Category) coreHelper.createModelStructure(new Category());
		
		return category;
	}
}