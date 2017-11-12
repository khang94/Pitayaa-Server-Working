package pitayaa.nail.msg.core.membership.service;

import java.util.List;

import pitayaa.nail.domain.redeem.TransactionRedeem;

public interface RedeemService {

	TransactionRedeem initModel() throws Exception;

	TransactionRedeem save(TransactionRedeem transactionRedeem) throws Exception;

	void deleteTransaction(List<TransactionRedeem> transactionRedeem) throws Exception;

}
