package pitayaa.nail.msg.core.membership.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.membership.MembershipManagement;
import pitayaa.nail.domain.membership.elements.RewardInformation;
import pitayaa.nail.domain.redeem.TransactionRedeem;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.service.CustomerService;
import pitayaa.nail.msg.core.membership.repository.MembershipRepository;
import pitayaa.nail.msg.core.setting.promotion.service.SettingPromotionService;

@Service
public class MembershipServiceImpl implements MembershipService {

	@Autowired
	MembershipRepository membershipRepository;

	@Autowired
	SettingPromotionService settingPromoService;

	@Autowired
	RedeemService redeemService;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	CustomerService customerService;

	@Override
	public MembershipManagement initModel() throws Exception {
		MembershipManagement membershipModel = (MembershipManagement) coreHelper
				.createModelStructure(new MembershipManagement());

		return membershipModel;
	}

	@Override
	public Optional<MembershipManagement> findOne(UUID uid) {
		return Optional.ofNullable(membershipRepository.findOne(uid));
	}

	@Override
	public MembershipManagement updateMembershipForReturnCustomer(Customer customerBody) throws Exception {

		MembershipManagement membership = membershipRepository
				.findOne(UUID.fromString(customerBody.getCustomerMembership().getMembershipId()));

		// Update some mandatory information
		membership.setCustomerMembership(customerBody.getCustomerMembership());
		membership.setCustomerDetail(customerBody.getCustomerDetail());
		membership.setCustomerId(customerBody.getUuid().toString());

		// Get cash from reward
		Double cash = settingPromoService.convertRewardToCash(customerBody.getSalonId(),
				customerBody.getCustomerMembership().getPoint());

		// Update reward
		membership.getRewardInformation().setLastSpending(customerBody.getCustomerMembership().getLastSpending());
		membership.getRewardInformation().setTotalEquivalentCash(cash);

		Integer totalRewards = membership.getRewardInformation().getTotalRewards()
				+ customerBody.getCustomerMembership().getPoint();
		membership.getRewardInformation().setTotalRewards(totalRewards);

		Double totalSpending = membership.getRewardInformation().getTotalSpending()
				+ customerBody.getCustomerMembership().getSpending();
		membership.getRewardInformation().setTotalSpending(totalSpending);

		RewardInformation rewardInformation = membership.getRewardInformation();
		membership.getRewardInformation()
				.setAvailableRewards(rewardInformation.getTotalRewards() - rewardInformation.getUsedRewards());

		return membershipRepository.save(membership);
	}

	@Override
	public MembershipManagement createMembershipForNewCustomer(Customer customerBody) throws Exception {

		// Init membership
		MembershipManagement membership = this.initModel();
		membership.setCustomerDetail(customerBody.getCustomerDetail());
		membership.setSalonId(customerBody.getSalonId());

		// Get cash from reward
		Double cash = settingPromoService.convertRewardToCash(customerBody.getSalonId(),
				customerBody.getCustomerMembership().getPoint());

		// Init Reward Information
		RewardInformation rewardInformation = new RewardInformation();

		rewardInformation.setTotalEquivalentCash(cash);
		rewardInformation.setTotalRewards(customerBody.getCustomerMembership().getPoint());
		rewardInformation.setUsedRewards(0);
		rewardInformation.setTotalSpending(customerBody.getCustomerMembership().getSpending());
		rewardInformation.setLastSpending(0.0);
		rewardInformation.setAvailableRewards(rewardInformation.getTotalRewards() - rewardInformation.getUsedRewards());

		membership.setRewardInformation(rewardInformation);

		List<TransactionRedeem> transactionHistory = new ArrayList<TransactionRedeem>();
		membership.setTransactionHistory(transactionHistory);

		return membershipRepository.save(membership);
	}

	@Override
	public MembershipManagement save(MembershipManagement membershipBody) throws Exception {
		return membershipRepository.save(membershipBody);
	}

	@Override
	public MembershipManagement operationWithRewards(MembershipManagement membershipData, String operation,
			Integer point , String customerId) throws Exception {

		if (CoreConstant.ADD_POINTS.equalsIgnoreCase(operation)) {

			// Update rewards
			Integer reward = membershipData.getRewardInformation().getTotalRewards() + point;
			membershipData.getRewardInformation().setTotalRewards(reward);

			Integer availableReward = membershipData.getRewardInformation().getAvailableRewards() + point;
			membershipData.getRewardInformation().setAvailableRewards(availableReward);

			// Get cash from reward
			Double cashExchange = settingPromoService.convertRewardToCash(membershipData.getSalonId(), point);
			//Double cashTotal = cashExchange + membershipData.getRewardInformation().getTotalEquivalentCash();
			Double cashTotal = settingPromoService.convertRewardToCash(membershipData.getSalonId(), availableReward);

			/// Update cash
			membershipData.getRewardInformation().setTotalEquivalentCash(cashTotal);

			// Make Transaction
			this.makeTransaction(operation, point, cashExchange, membershipData);

		} else if (CoreConstant.SUBTRACT_POINTS.equalsIgnoreCase(operation)) {
			
			if(membershipData.getRewardInformation().getAvailableRewards() < point){
				throw new Exception ("There is an error while substract points ");
			}

			// Update reward
			Integer availableReward = membershipData.getRewardInformation().getAvailableRewards() - point;
			membershipData.getRewardInformation().setAvailableRewards(availableReward);

			Integer usedReward = membershipData.getRewardInformation().getUsedRewards() + point;
			membershipData.getRewardInformation().setUsedRewards(usedReward);

			// Get cash from reward
			Double cashExchange = settingPromoService.convertRewardToCash(membershipData.getSalonId(), point);
			//Double cashTotal = membershipData.getRewardInformation().getTotalEquivalentCash() - cashExchange;
			Double cashTotal = settingPromoService.convertRewardToCash(membershipData.getSalonId(), availableReward);

			/// Update cash
			membershipData.getRewardInformation().setTotalEquivalentCash(cashTotal);

			// Make Transaction
			this.makeTransaction(operation, point, cashExchange, membershipData);
		}

		if(!StringUtils.isNullOrEmpty(customerId)){
			Optional<Customer> customer = customerService.findOne(UUID.fromString(customerId));
			if(customer.isPresent()){
				updateAvailablePointToCustomer(membershipData, customer.get());
			}
		}

		return membershipRepository.save(membershipData);
	}

	private void updateAvailablePointToCustomer(MembershipManagement membershipData, Customer customer) {

		// Update point to customer
		if (customer.getCustomerMembership().getPoint() != membershipData.getRewardInformation()
				.getAvailableRewards()) {
			customer.getCustomerMembership().setPoint(membershipData.getRewardInformation().getAvailableRewards());
		}

		// Update to membership
		membershipData.setCustomerMembership(customer.getCustomerMembership());
	}

	@Override
	public MembershipManagement removeTransaction(List<TransactionRedeem> transactions,
			MembershipManagement membershipData) throws Exception {

		// Remove constraint

		membershipData.getTransactionHistory().remove(transactions.get(0));
		membershipData = membershipRepository.save(membershipData);

		// Remove out of database
		redeemService.deleteTransaction(transactions);

		return membershipData;
	}

	@Override
	public MembershipManagement makeTransaction(String operation, Integer point, Double cash,
			MembershipManagement membershipData) throws Exception {

		List<TransactionRedeem> transactions = new ArrayList<TransactionRedeem>();

		TransactionRedeem transaction = new TransactionRedeem();
		transaction.setAction(operation);
		transaction.setPoint(point);
		transaction.setCash(cash);
		transaction.setSalonId(membershipData.getSalonId());
		transaction.setCustomerId(membershipData.getCustomerId());

		transactions.add(transaction);
		transactions.addAll(membershipData.getTransactionHistory());
		membershipData.setTransactionHistory(transactions);

		transaction = redeemService.save(transaction);

		return membershipData;
	}
}
