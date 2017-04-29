package pitayaa.nail.msg.core.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.msg.core.promotion.repository.PromotionRepository;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	public PromotionServiceHelper serviceHelper;

	@Autowired
	public PromotionRepository promotionRepo;

	@Override
	public boolean generateCode(Promotion codeExpect, int number) {
		List<Promotion> lstCode = serviceHelper.generateCode(codeExpect, number);
		boolean isGenSuccess = false;
		if (lstCode.size() == number) {

			for (Promotion p : lstCode) {
				promotionRepo.save(p);
			}
			isGenSuccess = true;
		}
		return isGenSuccess;
	}

	@Override
	public Promotion save(Promotion promotion) {
		return promotionRepo.save(promotion);
	}

}
