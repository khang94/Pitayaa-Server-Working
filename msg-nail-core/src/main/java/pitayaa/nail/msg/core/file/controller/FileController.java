package pitayaa.nail.msg.core.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.view.View;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.core.file.service.FileUpload;
import pitayaa.nail.msg.core.view.service.ViewService;

@Controller
public class FileController {

	@Autowired
	FileUpload fileUpload;
	@Autowired
	ViewService viewService;
	@RequestMapping(value = "file/upload", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> upload(
			@RequestBody View view) throws Exception {
		JsonHttp data = new JsonHttp();
		try {
			String path = fileUpload.saveFile(view);
			view.setPathImageServer(path);
			view=viewService.save(view);
			view.setImgData(null);
			data.setStatus("success");
			data.setObject(view);

		} catch (Exception e) {
			data.setMessage(e.getMessage());
			data.setStatus("error");
		}
		return ResponseEntity.ok(data);

	}
}
