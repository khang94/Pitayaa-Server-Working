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
import pitayaa.nail.domain.packages.elements.PackageDetail;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.packageEntity.service.PackageService;

@Controller
public class PackageController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private PackageService packageService;

	@Autowired
	private JsonHttpService httpService;

	private JsonHttp jsonData;

	@RequestMapping(value = "packages/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initPackageModel() throws Exception {

		PackageModel packageEntity = (PackageModel) coreHelper.createModelStructure(new PackageModel());

		return ResponseEntity.ok(packageEntity);
	}

	@RequestMapping(value = "packagesDetail/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initPackageDetail() throws Exception {

		PackageDetail packageDetail = (PackageDetail) coreHelper.createModelStructure(new PackageDetail());

		return ResponseEntity.ok(packageDetail);
	}

	@RequestMapping(value = "packages", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody PackageModel packageModel) throws Exception {

		try {
			packageModel = packageService.save(packageModel);
			jsonData = httpService.getResponseSuccess(packageModel, "Saved package successfully...");
		} catch (Exception e) {
			jsonData = httpService.getResponseError(null, e.getMessage());
		}
		return new ResponseEntity<>(jsonData , jsonData.getHttpCode());
	}

	@RequestMapping(value = "packages/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody PackageModel packageModel,
			@PathVariable("ID") UUID id) throws Exception {

		try {
			packageModel = packageService.update(packageModel);
			jsonData = httpService.getResponseSuccess(packageModel, "Update package successfully...");

		} catch (Exception e) {
			e.getCause();
			jsonData = httpService.getResponseError("Update package failed ...", e.getMessage());
		}

		return new ResponseEntity<>(jsonData, jsonData.getHttpCode());

	}

	@RequestMapping(value = "packages/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPackagesBySalonId(@RequestParam("salonId") String salonId)
			throws Exception {

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
	
	@RequestMapping(value = "packages/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findPackageById(@PathVariable("Id") UUID id)
			throws Exception {

		try {
			Optional<PackageModel> packageData = packageService.findOne(id);
			if(packageData.isPresent()){
				jsonData = httpService.getResponseSuccess(packageData.get(), "Find package success");
			} else {
				jsonData = httpService.getNotFoundData("Not found this package...");
			}
		} catch (Exception e) {
			// TODO: handle exception
			jsonData = httpService.getResponseError("Cannot access to database...", e.getMessage());
		}

		return new ResponseEntity<>(jsonData , jsonData.getHttpCode());
	}

	@RequestMapping(value = "packages/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();

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