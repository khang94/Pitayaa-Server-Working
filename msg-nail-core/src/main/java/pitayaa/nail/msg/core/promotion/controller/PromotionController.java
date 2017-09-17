package pitayaa.nail.msg.core.promotion.controller;

import java.util.HashMap;
import java.util.List;
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

import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.promotion.PromotionGroup;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.promotion.service.PromotionService;

@Controller
public class PromotionController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private JsonHttpService httpService;

	@RequestMapping(value = "promotions/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPromotionModel() throws Exception {

		Promotion promotion = (Promotion) coreHelper.createModelStructure(new Promotion());

		return ResponseEntity.ok(promotion);
	}
	
	@RequestMapping(value = "promotionsGroup/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPromotionGroupModel() throws Exception {

		PromotionGroup promotion = (PromotionGroup) coreHelper.createModelStructure(new PromotionGroup());

		return ResponseEntity.ok(promotion);
	}
	
	@RequestMapping(value = "promotions", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> generateCode(@RequestBody PromotionGroup promotionGroup) {
		
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			promotionGroup = promotionService.createPromotionGroup(promotionGroup);
			jsonHttp = httpService.getResponseSuccess(promotionGroup, "Saving promotion success....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", ex.getMessage());
		}

		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "promotions/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> generateCode(
			@PathVariable("Id") UUID id,
			@RequestBody PromotionGroup promotionGroup) {
		
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			promotionGroup = promotionService.updatePromotionGroup(id, promotionGroup);
			jsonHttp = httpService.getResponseSuccess(promotionGroup, "Update promotion success....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", ex.getMessage());
		}

		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "promotions/salons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> generateCode(@RequestParam("salonId") String salonId) {
		
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			List<PromotionGroup> promotions = promotionService.getPromotionGroupBySalonId(salonId);
			jsonHttp = httpService.getResponseSuccess(promotions, "get promotion success....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", ex.getMessage());
		}

		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}
	
	

	@RequestMapping(value = "promotions/generate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> generateCode(@RequestParam("number") int number,
			@RequestBody Promotion codeExpect) {
		boolean isGenSuccess = promotionService.generateCode(codeExpect, number);

		HttpStatus status = (isGenSuccess) ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;

		Resource<Boolean> resource = new Resource<>(isGenSuccess);
		return new ResponseEntity<>(resource, status);
	}
	
	@RequestMapping(value = "promotions/verify" , method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> verifyPromotionCode (@RequestParam("code") String code,
			@RequestParam("salonId") String salonId) throws Exception{
		
		HashMap<String, String> response = promotionService.verifyPromotionCode(code, salonId);
		
		JsonHttp jsonHttp = httpService.getResponseSuccess(response.get("isValid"), response.get("message"));
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "promotions/sms" , method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPromotionCode (@RequestParam("type") String type,
									@RequestParam("salonId") String salonId,
									@RequestParam("customerId") String customerId) throws Exception{
		
		Promotion promotionDeliver = promotionService.getPromotionCodeRandom(salonId, type , customerId);
		
		Resource<Promotion> resource = new Resource<Promotion>(promotionDeliver);
		
		return new ResponseEntity<>(promotionDeliver , HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
