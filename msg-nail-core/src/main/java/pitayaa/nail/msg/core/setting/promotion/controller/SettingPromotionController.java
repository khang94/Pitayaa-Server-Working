package pitayaa.nail.msg.core.setting.promotion.controller;

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

import pitayaa.nail.domain.setting.SettingPromotion;
import pitayaa.nail.msg.core.setting.promotion.service.SettingPromotionService;

@Controller
public class SettingPromotionController {

	@Autowired
	SettingPromotionService promotionService;

	@RequestMapping(value = "settingPromotion/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initPromotionSetting() throws Exception {

		SettingPromotion settingPromotion = promotionService.initModel();
		return ResponseEntity.ok(settingPromotion);
	}

	@RequestMapping(value = "settingPromotion", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> save(@RequestBody SettingPromotion settingPromote) throws Exception {

		SettingPromotion settingPromotion = promotionService.save(settingPromote);
		return ResponseEntity.ok(settingPromotion);
	}

	@RequestMapping(value = "settingPromotion/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> update(@RequestBody SettingPromotion settingPromote,
			@PathVariable("ID") UUID uid) throws Exception {

		Optional<SettingPromotion> savedSettingPromote = promotionService.findOne(uid);
		if (!savedSettingPromote.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		SettingPromotion settingPromotion = promotionService.save(settingPromote);
		return ResponseEntity.ok(settingPromotion);
	}

	@RequestMapping(value = "settingPromotion/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("ID") UUID uid) throws Exception {

		Optional<SettingPromotion> savedSettingPromote = promotionService.findOne(uid);
		if (!savedSettingPromote.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(savedSettingPromote.get());
	}

	@RequestMapping(value = "settingPromotion/salons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getBySalonId(@RequestParam("salonId") String salonId) throws Exception {

		SettingPromotion savedSettingPromote = promotionService.getSettingPromoteBySalonId(salonId);
		if (savedSettingPromote == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(savedSettingPromote);
	}
	
	@RequestMapping(value = "settingPromotion/salons", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> update(@RequestParam("salonId") String salonId,
			@RequestBody SettingPromotion promotionBody) throws Exception {

		SettingPromotion savedSettingPromote = promotionService.getSettingPromoteBySalonId(salonId);
		if (savedSettingPromote == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		savedSettingPromote = promotionService.update(savedSettingPromote, promotionBody);

		return ResponseEntity.ok(savedSettingPromote);
	}

}
