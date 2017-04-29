package pitayaa.nail.msg.core.account.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.elements.AccountLicense;


@Service
@RepositoryRestResource(collectionResourceRel = "accountsLicenseRest", path = "accountsLicenseRest")
public interface AccountLicenseRepository extends
		PagingAndSortingRepository<AccountLicense, UUID> { 
	

}
