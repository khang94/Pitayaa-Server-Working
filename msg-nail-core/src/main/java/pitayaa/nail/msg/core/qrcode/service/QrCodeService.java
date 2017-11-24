package pitayaa.nail.msg.core.qrcode.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.qrcode.QrCode;

public interface QrCodeService {

	QrCode initModel() throws Exception;

	List<QrCode> findAllBySalonId(String salonId) throws Exception;

	QrCode findBySalonIdAndCustomerId(String salonId, String customerId) throws Exception;

	Optional<QrCode> findOne(UUID uid);

	List<QrCode> findBySalonIdOrCustomerId(String salonId, String customerId) throws Exception;

	QrCode activeQrCode(Customer customer, String qrCode) throws Exception;

	QrCode generateQrCode(Customer customer, String qrCode) throws Exception;

	QrCode actionQrCodeForCustomer(String customerId, String qrCode, String operation) throws Exception;

	QrCode save(QrCode qrCode) throws Exception;

	QrCode findQrCodeForCustomer(String customerId) throws Exception;

	void resetQrCodeForCustomer(Customer customer, String qrCode) throws Exception;

}
