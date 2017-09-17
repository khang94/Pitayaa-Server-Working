package pitayaa.nail.msg.core.promotion.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.Promotion;

@Service
public class PromotionServiceHelper {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public List<Promotion> generateCode(Promotion codeExpect, int number) {

		List<Promotion> promotionLst = new ArrayList<Promotion>();
		for (int i = 0; i < number; i++) {
			String codeValue = this.randomString(6);
			Promotion promoCode = new Promotion();
			
			promoCode.setExpireFrom(codeExpect.getExpireFrom());
			promoCode.setExpireTo(codeExpect.getExpireTo());
			promoCode.setSalonId(codeExpect.getSalonId());
			promoCode.setMessage(codeExpect.getMessage());
			
			promoCode.setCodeValue(codeValue);
			promoCode.setStatus(codeExpect.getStatus());
			promoCode.setPromotionDiscount(codeExpect.getPromotionDiscount());
			promotionLst.add(promoCode);
		}

		return promotionLst;
	}
}
