package pitayaa.nail.msg.core.membership.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.membership.MembershipManagement;


@Service
@RepositoryRestResource(collectionResourceRel = "membershipRest", path = "membershipRest")
public interface MembershipRepository extends
		PagingAndSortingRepository<MembershipManagement, UUID> { 
	

}
