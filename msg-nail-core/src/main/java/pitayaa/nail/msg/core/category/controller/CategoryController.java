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
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.core.category.repository.CategoryRepository;
import pitayaa.nail.msg.core.category.service.CategoryService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.controller.CustomerController;

@Controller
public class CategoryController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private CategoryService cateService;

	@Autowired
	private CategoryRepository cateRepo;

	@RequestMapping(value = "categories/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Category account = (Category) coreHelper.createModelStructure(new Category());

		return ResponseEntity.ok(account);
	}

	@RequestMapping(value = "categories", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createCustomer(@RequestBody Category categoryBody) throws Exception {

		Category category = cateService.save(categoryBody);

		if (category.equals(null)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Resource<Category> response = new Resource<Category>(category);
		response.add(linkTo(methodOn(CategoryController.class).findOne(category.getUuid())).withSelfRel());

		return new ResponseEntity<Resource<Category>>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "categories/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("ID") UUID id) throws Exception {

		Optional<Category> category = cateService.findOne(id);

		if (!category.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Resource<Category> response = new Resource<Category>(category.get());
		response.add(linkTo(methodOn(CustomerController.class).findOne(category.get().getUuid())).withSelfRel());
		return new ResponseEntity<>(category.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "categories/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		Optional<Category> category = cateService.findOne(id);

		if (!category.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "categories/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody Category categoryBody,
			@PathVariable("ID") UUID id) throws Exception {

		Category category = cateService.save(categoryBody);

		if (category.equals(null)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Resource<Category> response = new Resource<Category>(category);
		response.add(linkTo(methodOn(CustomerController.class).findOne(category.getUuid())).withSelfRel());

		return new ResponseEntity<Resource<Category>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "categories/byType", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getCategoriesByType(@RequestParam("salonId") String salonId,@RequestParam("type") String type) throws Exception {

		List<Category> categoryList = cateService.categoriesForGroup(type,salonId);
		JsonHttp jsonHttp = new JsonHttp();
		if (categoryList != null && categoryList.size() > 0) {
			jsonHttp.setCode(200);
			jsonHttp.setObject(categoryList);
			jsonHttp.setStatus("success");
			jsonHttp.setMessage("get list success");
		}

		else {
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setMessage("get list failed");
		}

		return ResponseEntity.ok(jsonHttp);
	}
}
