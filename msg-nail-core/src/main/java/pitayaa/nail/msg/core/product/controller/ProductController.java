package pitayaa.nail.msg.core.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.product.ProductModel;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.product.service.ProductService;
@Controller

public class ProductController {

	@Autowired
	private CoreHelper coreHelper;;
	
	@Autowired
	private ProductService productService;
	

	@RequestMapping(value = "product/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {
		
		ProductModel packageEntity = (ProductModel) coreHelper
				.createModelStructure(new ProductModel());

		return ResponseEntity.ok(packageEntity);
	}

	@RequestMapping(value = "products", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody ProductModel model) throws Exception {
	
		productService.save(model);
		return ResponseEntity.ok(model);
	}
	
	@RequestMapping(value = "products/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPackagesBySalonId(@RequestParam("salonId") String salonId) throws Exception {

		List<ProductModel> productList = productService.findAll(salonId);
		JsonHttp jsonHttp = new JsonHttp();
		if (productList!= null){
			jsonHttp.setCode(200);
			jsonHttp.setObject(productList);
			jsonHttp.setStatus("success");
			jsonHttp.setResponseMessage("get list success");
		} 
		
		else{
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setResponseMessage("get list failed");
		} 
		return ResponseEntity.ok(jsonHttp);
	}
	
}