package pitayaa.nail.msg.core.membership.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.membership.MembershipManagement;
import pitayaa.nail.domain.redeem.TransactionRedeem;

public interface MembershipService {

	MembershipManagement initModel() throws Exception;

	MembershipManagement save(MembershipManagement membershipBody) throws Exception;

	MembershipManagement createMembershipForNewCustomer(Customer customerBody) throws Exception;

	MembershipManagement updateMembershipForReturnCustomer(Customer customerBody) throws Exception;

	Optional<MembershipManagement> findOne(UUID uid);

	MembershipManagement operationWithRewards(MembershipManagement membershipData, String operation, Integer point)
			throws Exception;

	MembershipManagement makeTransaction(String operation, Integer point, Double cash,
			MembershipManagement membershipData) throws Exception;

	MembershipManagement removeTransaction(List<TransactionRedeem> transactions, MembershipManagement membershipData)
			throws Exception;

}
