package pitayaa.nail.msg.core.membership.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.redeem.TransactionRedeem;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.membership.repository.TransactionRedeemRepository;

@Service
public class RedeemServiceImpl implements RedeemService {
	
	@Autowired
	CoreHelper coreHelper;
	
	@Autowired
	TransactionRedeemRepository redeemRepo;
	
	@Autowired
	MembershipService membershipService;
	
	@Override
	public TransactionRedeem initModel() throws Exception {
		TransactionRedeem transactionRedeem = (TransactionRedeem) coreHelper.createModelStructure(new TransactionRedeem());
		
		return transactionRedeem;
	}
	
	@Override
	public TransactionRedeem save(TransactionRedeem transactionRedeem) throws Exception {
		return redeemRepo.save(transactionRedeem);
	} 
	
	@Override
	public void deleteTransaction(List<TransactionRedeem> transactionRedeem) throws Exception {
		
		for (TransactionRedeem transaction : transactionRedeem){
			redeemRepo.delete(transaction);
		}
	} 


}
