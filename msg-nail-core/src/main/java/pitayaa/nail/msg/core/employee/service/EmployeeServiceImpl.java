package pitayaa.nail.msg.core.employee.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.employee.Employee;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	EmployeeViewService viewService;
	
	@Autowired
	CoreHelper coreHelper;

	@Override
	public List<Employee> findAllEmployee(String salonId) {
		return employeeRepo.findAllEmployee(salonId);
	}

	@Override
	public Optional<Employee> findOne(UUID id) {
		return Optional.ofNullable(employeeRepo.findOne(id));
	}

	@Override
	public Employee save(Employee employeeBody) throws Exception {

		byte[] binaryImg = null;
		boolean isUploadImage = false;
		
		// Get stream image
		if (employeeBody.getView().getImgData().length > 0){
			binaryImg = employeeBody.getView().getImgData();
			isUploadImage = true;
		}
		// Hide Image
		employeeBody.getView().setImgData(null);	
		
		employeeBody = employeeRepo.save(employeeBody);
		
		if(isUploadImage  && employeeBody.getSalonId() != null){
			viewService.buildViewByDate(employeeBody, binaryImg);
		}

		return employeeBody;
	}

	@Override
	public Employee update(Employee employeeSaved , Employee employeeUpdated) throws Exception {
		
		byte[] binaryImg = null;
		boolean isUpdatedImage = false;
		
		// Update New Image
		if (employeeUpdated.getView().getImgData().length > 0){
			isUpdatedImage = true;
			binaryImg = employeeUpdated.getView().getImgData();
		}
		
		// Hide Image
		employeeUpdated.getView().setImgData(null);	
		
		if (isUpdatedImage && employeeSaved.getView().getImgData() != null){
			// Delete image from static path in local server
			coreHelper.deleteFile(employeeSaved.getView().getPathImage());
		}
		
		// Update hibernate
		employeeUpdated.setUuid(employeeSaved.getUuid());
		employeeUpdated.setVersion(employeeSaved.getVersion());
		employeeUpdated = employeeRepo.save(employeeUpdated);
		
		if(employeeUpdated.getSalonId() != null || !"".equalsIgnoreCase(employeeUpdated.getSalonId())){
			viewService.buildViewByDate(employeeUpdated, binaryImg);
		}
		
		return employeeUpdated;
	}
	
	@Override
	public Optional<Employee> findEmployee(String salonId, String pin) {
		// TODO Auto-generated method stub
		return employeeRepo.findAllEmployee(salonId, pin);
	}
}
