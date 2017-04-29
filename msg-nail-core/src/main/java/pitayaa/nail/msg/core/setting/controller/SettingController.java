package pitayaa.nail.msg.core.setting.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pitayaa.nail.msg.core.setting.service.SettingService;

@Controller
public class SettingController {

	@Autowired
	SettingService settingService;

	@RequestMapping(value = "setting/initStructureFolder", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initStructure() throws Exception {
		
		settingService.initStructureFolder();

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "setting/uploadTest",consumes = {"multipart/form-data"}, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> testMultipartFunction(@RequestParam("file") MultipartFile file) throws Exception {
		
		
		byte[] encodedFile = Base64.getEncoder().encode(file.getBytes());
		 
		String base64 = new String (encodedFile);
		System.out.println(base64);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
