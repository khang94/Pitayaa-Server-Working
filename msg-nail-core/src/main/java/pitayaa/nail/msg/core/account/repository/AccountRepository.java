package pitayaa.nail.msg.core.account.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.Account;

@Service
@RepositoryRestResource(collectionResourceRel = "accountsRest", path = "accountsRest")
public interface AccountRepository extends
		PagingAndSortingRepository<Account, UUID> { 
	
	@Query("select acc from Account acc where acc.contact.email = :email and acc.password = :password")
	Account loginProcess(@Param("email") String email,
			@Param("password") String password);
	
	@Query("select acc from Account acc where acc.contact.email = :email ")
	Account findAccountByEmail(@Param("email") String email);
	
	@Query("select acc from Account acc where acc.contact.mobilePhone = :mobilePhone ")
	Account findAccountByPhone(@Param("mobilePhone") String mobilePhone);

}
