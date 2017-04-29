package pitayaa.nail.msg.business.category;

import java.util.List;

import pitayaa.nail.domain.category.Category;

public interface CategoryBus {
	public List<Category> getListServiceCategoryDefault(String salonId) ;
	
}
