package pitayaa.nail.msg.core.membership.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.redeem.TransactionRedeem;

@Service
@RepositoryRestResource(collectionResourceRel = "redeemRest", path = "redeemRest")
public interface TransactionRedeemRepository extends PagingAndSortingRepository<TransactionRedeem, UUID> {

}
