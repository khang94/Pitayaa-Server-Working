package pitayaa.nail.msg.core.customer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
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

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.repository.CustomerRepository;
import pitayaa.nail.msg.core.customer.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JsonHttpService httpService;

	private JsonHttp data;

	@RequestMapping(value = "customers/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Customer customer = (Customer) coreHelper.createModelStructure(new Customer());

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@RequestMapping(value = "customers", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createCustomer(@RequestBody Customer customerBody,
			@RequestParam(value = "operation", required = false, defaultValue = "") String operation) throws Exception {

		Customer customer = null;
		try {
			customer = customerService.save(customerBody);
			data = httpService.getResponseSuccess(customer, "Create customer successfully...");
		} catch (Exception ex){
			data = httpService.getResponseError("Error", ex.getMessage());
		}

		return new ResponseEntity<>(data, data.getHttpCode());
	}

	@RequestMapping(value = "customers/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> createCustomer(@RequestBody Customer customerUpdate,
			@PathVariable("ID") UUID id,
			@RequestParam(name = "oldPass", defaultValue = "",required = false) String oldPass,
			@RequestParam(name = "operation" , required = false) String operation) throws Exception {

		Optional<Customer> savedCustomer = customerService.findOne(id);
		Customer customer = null;
		
		if (!savedCustomer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			
			if(!"".equalsIgnoreCase(oldPass)){
				customer = customerService.updatePassword(savedCustomer.get(), customerUpdate, oldPass);
			} else {
				customer = customerService.update(savedCustomer.get(), customerUpdate);
			}			
			data = httpService.getResponseSuccess(customer, "update successful");
		} catch (Exception e) {
			data = httpService.getResponseError(e.getMessage(), "");
		}
		
		if(CoreConstant.OPERATION_REFRESH.equalsIgnoreCase(operation)){
			return new ResponseEntity<>(customer , HttpStatus.OK);
		}
		
		
		
		

		return new ResponseEntity<>(data, data.getHttpCode());
	}

	@RequestMapping(value = "customers/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("ID") UUID id) throws Exception {

		Optional<Customer> customer = customerService.findOne(id);

		if (!customer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Resource<Customer> response = new Resource<Customer>(customer.get());
		response.add(linkTo(methodOn(CustomerController.class).findOne(customer.get().getUuid())).withSelfRel());
		return new ResponseEntity<>(customer.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "customers/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		Optional<Customer> customer = customerService.findOne(id);

		if (!customer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "customers/search", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> findAll(@RequestBody QueryCriteria query) throws Exception {

		List<Customer> customerLst = (List<Customer>) customerService.findAllByQuery(query);
		return ResponseEntity.ok(customerLst);
	}

	@RequestMapping(value = "customers/salons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findAllCustomerBySalonId(@RequestParam("salonId") String salonId,
			@RequestParam(name = "type", required = false, defaultValue = "") String customerType,
			@RequestParam(name = "operation", required = false, defaultValue = "") String operation) throws Exception {

		List<Customer> lstCustomer = customerService.findAllCustomer(salonId, customerType);
		JsonHttp jsonHttp = httpService.getResponseSuccess(lstCustomer, "Getting list data successfully...");

		if (operation.equalsIgnoreCase(CoreConstant.OPERATION_REFRESH)) {
			return new ResponseEntity<>(lstCustomer, jsonHttp.getHttpCode());
		}

		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}



	@RequestMapping(value = "customers/findByQrcode", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> findByQrcode(@RequestBody Customer customerBody) throws Exception {

		try {
			Optional<Customer> opCustomer = customerService.findByQrcode(customerBody.getQrCode(),
					customerBody.getSalonId());
			if(opCustomer.isPresent()){
				data = httpService.getResponseSuccess(opCustomer.get(), "Get customer by qr success");
			} else {
				data = httpService.getResponseError("Cannot find this", "");
			}
			
		} catch (Exception e) {
			data = httpService.getResponseError("Cannot find this", e.getMessage());

		}


		
		
		return new ResponseEntity<>(data , data.getHttpCode());
	}

	@RequestMapping(value = "customers/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> customerLogin(@RequestBody HashMap<String, Object> model) throws Exception {
		JsonHttp json = new JsonHttp();
		try {
			String email = (String) model.get("email");
			String password = (String) model.get("password");
			String salonId = (String) model.get("salonId");

			String encPassword = EncryptionUtils.encodeMD5(password, email);
			Optional<Customer> opCustomer = customerService.login(email, encPassword, salonId);
			if (opCustomer.isPresent()) {
				json.setObject(opCustomer.get());
				json.setStatus(JsonHttp.SUCCESS);
			} else {
				throw new Exception("Wrong email or password");
			}
		} catch (Exception e) {
			json.setStatus(JsonHttp.ERROR);
			json.setMessage(e.getMessage());

		}

		return ResponseEntity.ok(json);
	}

}
