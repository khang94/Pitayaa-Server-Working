package pitayaa.nail.msg.business.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.category.Category;

@Service

public class CategoryBusImpl implements CategoryBus {

	@Override
	public List<Category> getListServiceCategoryDefault(String salonId) {
		// TODO Auto-generated method stub
		List<Category> lst=new ArrayList<>();
		Category model=new Category();
		model.setCategoryCode("GN");
		model.setCategoryName("Gail Nail");
		model.setCateType(1);
		model.setSalonId(salonId);
		lst.add(model);
		
		//
		 model=new Category();
		model.setCategoryCode("GN");
		model.setCategoryName("Nail Brush");
		model.setCateType(1);
		model.setSalonId(salonId);
		lst.add(model);
		
		//
		 model=new Category();
		model.setCategoryCode("GN");
		model.setCategoryName("Full Sets");
		model.setCateType(1);
		model.setSalonId(salonId);
		lst.add(model);
		
		//
	    model=new Category();
		model.setCategoryCode("GN");
		model.setCategoryName("Manicure");
		model.setCateType(1);
		model.setSalonId(salonId);
		lst.add(model);
		
		//
		
		return lst;
	}

	

}
