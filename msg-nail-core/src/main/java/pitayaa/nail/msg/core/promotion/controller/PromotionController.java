package pitayaa.nail.msg.core.promotion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.promotion.service.PromotionService;

@Controller
public class PromotionController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private PromotionService promotionService;

	@RequestMapping(value = "promotions/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPromotionModel() throws Exception {

		Promotion promotion = (Promotion) coreHelper.createModelStructure(new Promotion());

		return ResponseEntity.ok(promotion);
	}

	@RequestMapping(value = "promotions/generate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> generateCode(@RequestParam("number") int number,
			@RequestBody Promotion codeExpect) {
		boolean isGenSuccess = promotionService.generateCode(codeExpect, number);

		HttpStatus status = (isGenSuccess) ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;

		Resource<Boolean> resource = new Resource<>(isGenSuccess);
		return new ResponseEntity<>(resource, status);
	}
	
	

}
