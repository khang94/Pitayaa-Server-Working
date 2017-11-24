package pitayaa.nail.msg.core.qrcode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.qrcode.QrCode;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.service.CustomerService;
import pitayaa.nail.msg.core.qrcode.repository.QrCodeRepository;

@Service
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	QrCodeRepository qrRepo;
	
	@Autowired
	CoreHelper coreHelper;
	
	@Autowired
	CustomerService customerService;
	
	@Override
	public QrCode initModel() throws Exception {
		QrCode qrCode = (QrCode) coreHelper.createModelStructure(new QrCode());
		return qrCode;
	}
	
	@Override
	public List<QrCode> findAllBySalonId(String salonId) throws Exception {
		return qrRepo.findAllBySalonId(salonId);
	}
	
	@Override
	public Optional<QrCode> findOne(UUID uid) {
		return Optional.ofNullable(qrRepo.findOne(uid));
	}
	
	@Override
	public QrCode findBySalonIdAndCustomerId(String salonId , String customerId) throws Exception {
		
		List<QrCode> qrCodes = qrRepo.findBySalonIdAndModuleId(salonId, customerId , CoreConstant.QR_CODE_USER_CUSTOMER);
		if(qrCodes.isEmpty()){
			return null;
		}
		return qrCodes.get(0);
	}
	
	@Override
	public List<QrCode> findBySalonIdOrCustomerId(String salonId , String customerId) throws Exception {
		
		List<QrCode> qrCodes = new ArrayList<>();
		if (!"".equalsIgnoreCase(salonId) && "".equalsIgnoreCase(customerId)){
			qrCodes = qrRepo.findAllBySalonId(salonId);
		} else if (!"".equalsIgnoreCase(salonId) && !"".equalsIgnoreCase(customerId)){
			qrCodes = qrRepo.findBySalonIdAndModuleId(salonId, customerId , CoreConstant.QR_CODE_USER_CUSTOMER);
		} else if ("".equalsIgnoreCase(salonId) && !"".equalsIgnoreCase(customerId)){
			qrCodes = qrRepo.findAllByModuleId(customerId);
		}
		
		return qrCodes;
	}
	
	@Override
	public QrCode findQrCodeForCustomer(String customerId) throws Exception {
		List<QrCode> qrCodes = new ArrayList<>();
		qrCodes = qrRepo.findAllByModuleId(customerId);
		if(qrCodes.isEmpty()){
			return null;
		}
		return qrCodes.get(0);
	}
	
	@Override
	public QrCode actionQrCodeForCustomer(String customerId ,String qrCode, String operation) throws Exception {
		
		Optional<Customer> customerSaved = customerService.findOne(UUID.fromString(customerId));
		if(!customerSaved.isPresent()){
			throw new Exception ("Customer id [" + customerId + "] does not exist to create qr code....");
		}
		QrCode qrCreated = new QrCode();
		
		if(CoreConstant.QR_CODE_ACTIVE.equalsIgnoreCase(operation)){
			qrCreated = this.activeQrCode(customerSaved.get(), qrCode);
		} else if (CoreConstant.QR_CODE_GENERATE.equalsIgnoreCase(operation)){
			qrCreated = this.generateQrCode(customerSaved.get(), qrCode);
		} else if (CoreConstant.QR_CODE_RESET.equalsIgnoreCase(operation)){
			this.resetQrCodeForCustomer(customerSaved.get(), qrCode);
			qrCreated = null;
		}
		return qrCreated;
	}
	
	@Override
	public void resetQrCodeForCustomer(Customer customer , String qrCode) throws Exception {
		
		// Update qr code for customer
		customer.setQrCode("");
		customerService.update(customer, customer);
		
		// Delete qr code out of repository
		qrRepo.deleteQrCodeByModuleIdAndQrKey(customer.getUuid().toString(), qrCode , CoreConstant.QR_CODE_USER_CUSTOMER);
	}
	
	@Override
	public QrCode activeQrCode(Customer customer , String qrCode) throws Exception {
		
		// Update qr for customer
		customer.setQrCode(qrCode);
		customerService.update(customer, customer);
		
		// Update customer Id to qr
		List<QrCode> qr = qrRepo.findByValue(qrCode);
		if(qr.isEmpty()){
			throw new Exception ("Qr Code [" + qrCode + "] is invalid ");
		}
		
		QrCode qrActive = qr.get(0);
		qrActive.setModuleId(customer.getUuid().toString());
		qrActive.setUserType(CoreConstant.QR_CODE_USER_CUSTOMER);

		return qrRepo.save(qrActive);
	}
	
	@Override
	public QrCode generateQrCode(Customer customer , String qrCode) throws Exception {
		
		QrCode qrGenerate = new QrCode();
		qrGenerate.setModuleId(customer.getUuid().toString());
		qrGenerate.setUserType(CoreConstant.QR_CODE_USER_CUSTOMER);
		qrGenerate.setType(CoreConstant.QR_CODE);
		qrGenerate.setSalonId(customer.getSalonId());
		
		// Random value qr code
		UUID qr = UUID.randomUUID();
		qrGenerate.setValue(qr.toString());
		
		// Update qr code to customer
		customer.setQrCode(qr.toString());
		customerService.update(customer, customer);
		
		return qrRepo.save(qrGenerate);
	}
	
	@Override
	public QrCode save(QrCode qrCode) throws Exception {
		return qrRepo.save(qrCode);
	}
}
