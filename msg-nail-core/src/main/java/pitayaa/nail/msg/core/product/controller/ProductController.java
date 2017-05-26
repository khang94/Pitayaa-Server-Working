package pitayaa.nail.msg.core.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

	
	@RequestMapping(value = "products/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getProductBySalonId(@RequestParam("salonId") String salonId) throws Exception {
		JsonHttp jsonHttp = new JsonHttp();

		try{
			List<ProductModel> productList = productService.findAll(salonId);
			jsonHttp.setObject(productList);
			jsonHttp.setStatus("success");
			jsonHttp.setMessage("get list success");
		}catch(Exception e){
			jsonHttp.setStatus("error");
			jsonHttp.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(jsonHttp);
	}
	
	@RequestMapping(value = "products", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody ProductModel model) throws Exception {
		JsonHttp jsonHttp = new JsonHttp();

		try {
			model= productService.save(model);
			jsonHttp.setCode(200);
			jsonHttp.setObject(model);
			jsonHttp.setStatus("success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setMessage(e.getMessage());
			jsonHttp.setStatus("error");
		}
		

		return new ResponseEntity<>(jsonHttp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "products/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody ProductModel model,
			@PathVariable("ID") UUID id) throws Exception {
		JsonHttp jsonHttp = new JsonHttp();
		try {

			
			model= productService.save(model);	
			jsonHttp.setCode(200);
			jsonHttp.setObject(model);
			jsonHttp.setStatus("success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setMessage(e.getMessage());
			jsonHttp.setStatus("error");
		}
		

		return new ResponseEntity<>(jsonHttp, HttpStatus.OK);

	}
	

	@RequestMapping(value = "products/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		JsonHttp jsonHttp=new JsonHttp();

		try {
			Optional<ProductModel> optionProduct = productService.findOne(id);

			if (optionProduct.isPresent()) {
				productService.delete(optionProduct.get());
			}
			jsonHttp.setCode(200);
			jsonHttp.setObject(optionProduct.get());
			jsonHttp.setStatus("success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setMessage(e.getMessage());
			jsonHttp.setStatus("error");
		}
		return new ResponseEntity<>(jsonHttp, HttpStatus.OK);

	}
	
}