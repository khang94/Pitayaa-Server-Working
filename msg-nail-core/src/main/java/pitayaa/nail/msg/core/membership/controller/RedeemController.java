package pitayaa.nail.msg.core.membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pitayaa.nail.domain.membership.MembershipManagement;
import pitayaa.nail.domain.redeem.TransactionRedeem;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.membership.service.RedeemService;

@RestController
public class RedeemController {

	@Autowired
	RedeemService redeemService;

	@Autowired
	JsonHttpService httpService;

	@RequestMapping(value = "redeem/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initEmployeeModel() throws Exception {

		TransactionRedeem redeem = redeemService.initModel();
		return ResponseEntity.ok(redeem);
	}
}
