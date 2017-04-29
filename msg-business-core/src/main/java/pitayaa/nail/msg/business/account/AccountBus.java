package pitayaa.nail.msg.business.account;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.json.account.JsonAccount;

public interface AccountBus {

	Account registerAccount(Account account, License License , Account isExistUsername) throws Exception;

	Account loginProcess(Account account) throws Exception;

	JsonAccount parseAccountToJson(Account account) throws Exception;

	boolean validateRegister(Account isExistUsername) throws Exception;

}
