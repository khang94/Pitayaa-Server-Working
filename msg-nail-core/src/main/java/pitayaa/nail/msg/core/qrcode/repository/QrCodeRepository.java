package pitayaa.nail.msg.core.qrcode.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pitayaa.nail.domain.qrcode.QrCode;

@RepositoryRestResource(collectionResourceRel = "qrcodeRest", path = "qrcodeRest")
public interface QrCodeRepository extends PagingAndSortingRepository<QrCode, UUID> {

	@Query("Select qr from QrCode qr where qr.salonId = :salonId")
	List<QrCode> findAllBySalonId(@Param("salonId") String salonId);

	@Query("Select qr from QrCode qr where qr.salonId = :salonId and qr.moduleId = :moduleId and qr.userType = :userType")
	List<QrCode> findBySalonIdAndModuleId(@Param("salonId") String salonId, @Param("moduleId") String moduleId,
			@Param("userType") String userType);

	@Query("Select qr from QrCode qr where qr.moduleId = :moduleId")
	List<QrCode> findAllByModuleId(@Param("moduleId") String moduleId);

	@Query("Select qr from QrCode qr where qr.value = :value")
	List<QrCode> findByValue(@Param("value") String value);

	@Transactional
	@Modifying
	@Query("delete from QrCode qr where qr.moduleId = :moduleId and qr.value = :value and qr.userType = :userType")
	void deleteQrCodeByModuleIdAndQrKey(@Param("moduleId") String customerId, @Param("value") String value,
			@Param("userType") String type);
}