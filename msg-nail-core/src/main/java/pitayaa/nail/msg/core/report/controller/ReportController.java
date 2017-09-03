package pitayaa.nail.msg.core.report.controller;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {

	@RequestMapping(value = "reports/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getReportSample() throws Exception {

		String message = "Welcome to Pitayaa Report";
		Resource<String> resource = new Resource<String>(message);
		return ResponseEntity.ok(resource);
	}
}
