package pitayaa.nail.msg.core.packageDtl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.packagedtl.PackageDtl;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.msg.core.common.CoreHelper;

@Controller
public class PackageDtlController {

	@Autowired
	private CoreHelper coreHelper;
	
	@RequestMapping(value = "packageDtl/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		PackageDtl packageDtlEntity = (PackageDtl) coreHelper
				.createModelStructure(new PackageDtl());

		return ResponseEntity.ok(packageDtlEntity);
	}
	
}
