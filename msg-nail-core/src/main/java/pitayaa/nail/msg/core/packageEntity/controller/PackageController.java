package pitayaa.nail.msg.core.packageEntity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.packageEntity.service.PackageEntityInterface;

@Controller
public class PackageController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private PackageEntityInterface packageService;

	@RequestMapping(value = "packages/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		PackageModel packageEntity = (PackageModel) coreHelper
				.createModelStructure(new PackageModel());

		return ResponseEntity.ok(packageEntity);
	}

	@RequestMapping(value = "packages", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody PackageModel packageModel) throws Exception {

		packageService.save(packageModel);
		return ResponseEntity.ok(packageModel);
	}
}
