package pitayaa.nail.msg.core.category.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.category.Category;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.category.repository.CategoryRepository;
import pitayaa.nail.msg.core.category.service.CategoryService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.controller.CustomerController;

@Controller
public class CategoryController {



	@Autowired
	private CategoryService cateService;

	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private JsonHttpService httpService;

	@RequestMapping(value = "categories/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initCategory() throws Exception {

		Category category = cateService.initModel();

		return ResponseEntity.ok(category);
	}

	@RequestMapping(value = "categories", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> save(@RequestBody Category categoryBody) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			categoryBody = cateRepo.save(categoryBody);
			jsonHttp = httpService.saveData("Save this category success....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("Save category failed...." , ex.getMessage());
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "categories/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("ID") UUID id) throws Exception {

		Optional<Category> categorySaved = cateService.findOne(id);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!categorySaved.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found this category");
			return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
		}
		
		jsonHttp = httpService.getResponseSuccess(categorySaved.get(), "Get category successfully !");
		
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "categories/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		Optional<Category> categorySaved = cateService.findOne(id);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!categorySaved.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found this services");
			return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
		}

		try {
			cateService.delete(categorySaved.get());
			jsonHttp = httpService.deleteData("Delete success.....");
			
		} catch (Exception ex) {
			jsonHttp = httpService.getResponseError("Delete failed...." , ex.getMessage());
		}
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "categories/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody Category categoryBody,
			@PathVariable("ID") UUID id) throws Exception {

		Optional<Category> categorySaved = cateService.findOne(id);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!categorySaved.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found this services");
			return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
		}
		
		
		try {
			Category cateResult = cateService.update(categorySaved.get() , categoryBody);	
			jsonHttp = httpService.getResponseSuccess(cateResult, "Update this category successfully !");
		} catch (Exception ex ) {
			jsonHttp = httpService.getResponseError("Update this category failed" , ex.getMessage());
		}
		
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "categories/byType", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getCategoriesByType(@RequestParam("salonId") String salonId,@RequestParam("type") String type) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			List<Category> categoryList = cateService.categoriesForGroup(type,salonId);
			jsonHttp.setCode(200);
			jsonHttp.setObject(categoryList);
			jsonHttp.setStatus("success");
			jsonHttp.setMessage("get list success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setMessage(e.getMessage());
		}
		
		return ResponseEntity.ok(jsonHttp);
	}
}
