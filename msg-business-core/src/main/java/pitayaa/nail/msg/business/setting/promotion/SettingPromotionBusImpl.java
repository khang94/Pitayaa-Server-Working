package pitayaa.nail.msg.business.setting.promotion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingPromotion;
import pitayaa.nail.domain.setting.promotion.CustomerTurn;
import pitayaa.nail.domain.setting.promotion.DiscountSetting;
import pitayaa.nail.domain.setting.promotion.LoyaltyPoint;
import pitayaa.nail.msg.business.constant.PromotionConstant;
import pitayaa.nail.msg.business.helper.BusinessHelper;

@Service
public class SettingPromotionBusImpl implements SettingPromotionBus {

	@Autowired
	BusinessHelper busHelper;

	@Override
	public SettingPromotion getSettingPromotionDefault(String salonId) throws Exception{
		SettingPromotion settingPromote = (SettingPromotion) busHelper.createModelStructure(new SettingPromotion());
		
		// Pass salonId
		settingPromote.setSalonId(salonId);
		
		// Default push notification
		settingPromote.setIsPushNotification(false);
		
		// Init customer turn
		List<CustomerTurn> customerTurnList = this.initCustomerTurn(salonId);
		settingPromote.setCustomerTurn(customerTurnList);
		
		// Init Discount
		List<DiscountSetting> discountSetting = this.initDiscount();
		settingPromote.setDiscount(discountSetting);
		
		// Init key type
		settingPromote.setKey(PromotionConstant.KEY);
		settingPromote.setType(PromotionConstant.TYPE);
		
		// Init default point
		LoyaltyPoint loyaltyPoint = new LoyaltyPoint();
		loyaltyPoint.setLoyaltyPoint(10);
		loyaltyPoint.setMoneyExchange(1);
		loyaltyPoint.setUnit(PromotionConstant.MONEY_TYPE_USD);
		loyaltyPoint.setSalonId(salonId);
		settingPromote.setLoyaltyPoint(loyaltyPoint);
		
		// Init Point default
		settingPromote.setPointPromotionCode(10);
		settingPromote.setPointReferralCode(10);		
		settingPromote.setPointRegularTurn(20);
		
		return settingPromote;
	}
	
	private List<CustomerTurn> initCustomerTurn(String salonId){
		
		List<CustomerTurn> customerTurnList = new ArrayList<CustomerTurn>();
		CustomerTurn customerTurn = new CustomerTurn();
		
		// Time period 1
		customerTurn.setPeriod(2);
		customerTurn.setPeriodType(PromotionConstant.PERIOD_TYPE_WEEK);
		
		customerTurn.setTimeTurns(3);
		customerTurn.setTargetType(PromotionConstant.TARGET_LOYAL_CUSTOMER);
		customerTurn.setSalonId(salonId);
		customerTurnList.add(customerTurn);
		
		// Time period 2
		customerTurn = new CustomerTurn();
		customerTurn.setPeriod(1);
		customerTurn.setPeriodType(PromotionConstant.PERIOD_TYPE_MONTH);
		
		customerTurn.setTimeTurns(8);
		customerTurn.setTargetType(PromotionConstant.TARGET_VIP_CUSTOMER);
		customerTurn.setSalonId(salonId);
		customerTurnList.add(customerTurn);
		
		return customerTurnList;
	}
	
	private List<DiscountSetting> initDiscount(){
		List<DiscountSetting> discountList = new ArrayList<DiscountSetting>();
		DiscountSetting discount = new DiscountSetting();

		discount.setDiscountType(PromotionConstant.DISCOUNT_TYPE_1);
		discount.setDiscountLabel("10 %");
		discount.setDiscountPercent(0.1);
		discount.setLoyaltyPoint(100);
		discountList.add(discount);
		
		discount = new DiscountSetting();
		discount.setDiscountType(PromotionConstant.DISCOUNT_TYPE_2);
		discount.setDiscountLabel("15 %");
		discount.setDiscountPercent(0.15);
		discount.setLoyaltyPoint(500);
		discountList.add(discount);
		
		return discountList;
	}
}
