package pitayaa.nail.msg.core.packageEntity.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.packages.elements.PackageDtl;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.packageEntity.service.PackageDetailService;
import pitayaa.nail.msg.core.packageEntity.service.PackageService;
import pitayaa.nail.msg.core.serviceEntity.service.ServiceEntityInterface;

@Controller
public class PackageController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private PackageService packageService;
	
	@Autowired
	private ServiceEntityInterface serviceEntity;
	
	@Autowired
	private PackageDetailService packageDtlEntity;

	@RequestMapping(value = "packages/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initPackageModel() throws Exception {
		
		PackageModel packageEntity = (PackageModel) coreHelper
				.createModelStructure(new PackageModel());

		return ResponseEntity.ok(packageEntity);
	}
	
	@RequestMapping(value = "packagesDetail/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initPackageDetail() throws Exception {
		
		PackageDtl packageDetail = (PackageDtl) coreHelper
				.createModelStructure(new PackageDtl());

		return ResponseEntity.ok(packageDetail);
	}

	@RequestMapping(value = "packages", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody PackageModel packageModel) throws Exception {
		JsonHttp jsonHttp = new JsonHttp();

		try {
			for(PackageDtl dtl:packageModel.getPackageDtls()){
				Optional<ServiceModel> model=serviceEntity.findOne(dtl.getService().getUuid());
				dtl.setService(model.get());
				dtl=packageDtlEntity.save(dtl);

			}
			packageModel=packageService.save(packageModel);
			jsonHttp.setCode(200);
			jsonHttp.setObject(packageModel);
			jsonHttp.setStatus("success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(jsonHttp);
	}
	
	@RequestMapping(value = "packages/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody PackageModel packageModel,
			@PathVariable("ID") UUID id) throws Exception {
		
		JsonHttp jsonHttp = new JsonHttp();
		try {
			for(PackageDtl dtl:packageModel.getPackageDtls()){
				Optional<ServiceModel> model=serviceEntity.findOne(dtl.getService().getUuid());
				dtl.setService(model.get());
				dtl=packageDtlEntity.save(dtl);

			}
			packageModel=packageService.save(packageModel);
			jsonHttp.setCode(200);
			jsonHttp.setObject(packageModel);
			jsonHttp.setStatus("success");
			
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setMessage(e.getMessage());
			jsonHttp.setStatus("error");
		}
		

		return new ResponseEntity<>(jsonHttp, HttpStatus.OK);

	}
	
	@RequestMapping(value = "packages/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPackagesBySalonId(@RequestParam("salonId") String salonId) throws Exception {

		
		JsonHttp jsonHttp = new JsonHttp();
		try {
			List<PackageModel> packageList = packageService.findAllPackages(salonId);
			jsonHttp.setCode(200);
			jsonHttp.setObject(packageList);
			jsonHttp.setStatus("success");
			jsonHttp.setMessage("get list success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setMessage(e.getMessage());
		}
		
		return ResponseEntity.ok(jsonHttp);
	}
	
	@RequestMapping(value = "packages/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		JsonHttp jsonHttp=new JsonHttp();

		try {
			Optional<PackageModel> packageModel = packageService.findOne(id);

			if (packageModel.isPresent()) {
				packageService.delete(packageModel.get());
			}
			jsonHttp.setCode(200);
			jsonHttp.setStatus("success");
		} catch (Exception e) {
			// TODO: handle exception
			jsonHttp.setMessage(e.getMessage());
			jsonHttp.setStatus("error");
		}
		return new ResponseEntity<>(jsonHttp, HttpStatus.OK);

	}
	
}