package pitayaa.nail.msg.core.account.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.json.account.JsonAccount;
import pitayaa.nail.json.account.JsonAccountLogin;

public interface AccountService {

	Account saveAccount(
			pitayaa.nail.domain.account.Account accountBody);

	JsonAccount registerAccount(
			pitayaa.nail.domain.account.Account accountBody) throws Exception;

	Account findAccount(UUID id);

	JsonAccount loginProcess(JsonAccountLogin jsonAccountLogin) throws Exception;

	Page<Account> findAll(Pageable pageable) throws Exception;

}
