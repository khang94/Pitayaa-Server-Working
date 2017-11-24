package pitayaa.nail.msg.core.membership.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pitayaa.nail.domain.membership.MembershipManagement;
import pitayaa.nail.domain.redeem.TransactionRedeem;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.membership.service.MembershipService;

@RestController
public class MembershipController {

	@Autowired
	MembershipService membershipService;

	@Autowired
	JsonHttpService httpService;

	@RequestMapping(value = "membership/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initMembershipModel() throws Exception {

		MembershipManagement membership = membershipService.initModel();
		return ResponseEntity.ok(membership);
	}
	
	@RequestMapping(value = "membership", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> saveMembership(@RequestBody MembershipManagement membershipBody) throws Exception {

		MembershipManagement membership = membershipService.save(membershipBody);
		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(membership, "save data successfully....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "save data failed....");
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "membership/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> saveMembership(@PathVariable("Id") UUID id , @RequestBody MembershipManagement membershipBody) throws Exception {

		Optional<MembershipManagement> membership = membershipService.findOne(id);
		if(!membership.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(membershipService.save(membershipBody), "save data successfully....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "save data failed....");
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "membership/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> saveMembership(@PathVariable("Id") UUID id) throws Exception {

		Optional<MembershipManagement> membership = membershipService.findOne(id);
		if(!membership.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(membership.get(), "get data successfully....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "get data failed....");
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "membership/{Id}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> operationWithRewards(@PathVariable("Id") UUID id,
			@RequestParam("operation") String operation , @RequestParam("point") Integer point,
			@RequestParam(name = "customerId" , required = false , defaultValue = "") String customerId) throws Exception {

		Optional<MembershipManagement> membership = membershipService.findOne(id);
		if(!membership.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(membershipService.operationWithRewards(membership.get(), operation, point , customerId), "update data successfully....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "get data failed....");
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "membership/{Id}/transaction", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> operationWithRewards(@PathVariable("Id") UUID id,
			@RequestBody List<TransactionRedeem> transactionRedeem) throws Exception {

		Optional<MembershipManagement> membership = membershipService.findOne(id);
		if(!membership.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(membershipService.removeTransaction(transactionRedeem, membership.get()), "remove data successfully....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "remove data failed....");
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	
}
