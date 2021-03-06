package pitayaa.nail.msg.core.customer.controller;

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

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.json.http.JsonHttp;
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
	private CustomerRepository customerRepo;

	@RequestMapping(value = "customers/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Customer customer = (Customer) coreHelper
				.createModelStructure(new Customer());

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@RequestMapping(value = "customers", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createCustomer(
			@RequestBody Customer customerBody) throws Exception {

		Customer customer = customerService.save(customerBody);

		if (customer.equals(null)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		/*Resource<Customer> response = new Resource<Customer>(customer);
		response.add(linkTo(
				methodOn(CustomerController.class).findOne(customer.getUuid()))
				.withSelfRel());*/

		return new ResponseEntity<Customer>(customer,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "customers/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> createCustomer(
			@RequestBody Customer customerUpdate, @PathVariable("ID") UUID id)
			throws Exception {

		Optional<Customer> savedCustomer = customerService.findOne(id);
		
		if(!savedCustomer.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Customer customer = customerService.update(savedCustomer.get() , customerUpdate);

		/*Resource<Customer> response = new Resource<Customer>(customer);
		response.add(linkTo(
				methodOn(CustomerController.class).findOne(customer.getUuid()))
				.withSelfRel());*/

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@RequestMapping(value = "customers/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("ID") UUID id)
			throws Exception {

		Optional<Customer> customer = customerService.findOne(id);

		if (!customer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Resource<Customer> response = new Resource<Customer>(customer.get());
		response.add(linkTo(
				methodOn(CustomerController.class).findOne(customer.get().getUuid()))
				.withSelfRel());
		return new ResponseEntity<>(customer.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "customers/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id)
			throws Exception {

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

	@RequestMapping(value = "customers/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findAllCustomerBySalonId(
			@RequestParam("salonId") String salonId) throws Exception {

		List<Customer> lstCustomer = customerService
				.findAllCustomer(salonId);
		JsonHttp jsonHttp = new JsonHttp();
		if (lstCustomer != null && lstCustomer.size() > 0) {
			jsonHttp.setCode(200);
			jsonHttp.setObject(lstCustomer);
			jsonHttp.setStatus("success");
			jsonHttp.setResponseMessage("get list success");
		}

		else {
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setResponseMessage("get list failed");
		}

		return new ResponseEntity<>(jsonHttp, HttpStatus.OK);
	}
	@RequestMapping(value = "customers/sms/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findAllCustomerSmsBySalonId(
			@RequestParam("salonId") String salonId) throws Exception {

		List<Customer> lstCustomer = customerService
				.findAllCustomer(salonId);
		JsonHttp jsonHttp = new JsonHttp();
		if (lstCustomer != null && lstCustomer.size() > 0) {
			jsonHttp.setCode(200);
			jsonHttp.setObject(lstCustomer);
			jsonHttp.setStatus("success");
			jsonHttp.setResponseMessage("get list success");
		}

		else {
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setResponseMessage("get list failed");
		}

		return new ResponseEntity<>(lstCustomer, HttpStatus.OK);
	}

}
