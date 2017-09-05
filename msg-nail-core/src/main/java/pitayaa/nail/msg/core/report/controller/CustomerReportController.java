package pitayaa.nail.msg.core.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.report.elements.ReportCustomerData;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.report.service.ReportService;

@Controller
public class CustomerReportController {
	
	@Autowired
	ReportService reportService;

	@Autowired
	JsonHttpService httpService;
	
	@RequestMapping(value = "reports/customers/sample", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getReportSample() throws Exception {

		String message = "Welcome to Pitayaa Report";
		Resource<String> resource = new Resource<String>(message);
		return ResponseEntity.ok(resource);
	}
	
	@RequestMapping(value = "reports/customers", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getReportCustomers(
			@RequestParam(required = false, defaultValue = "")String salonId,
			@RequestParam(required = false, defaultValue = "") String from,
			@RequestParam(required = false, defaultValue = "") String to) throws Exception {

		JsonHttp data = new JsonHttp();
		
		try {
			ReportCustomerData customerData = reportService.getCustomersData(salonId , from , to);
			data = httpService.getResponseSuccess(customerData, "get customer data report successfully");
		} catch (Exception ex){
			data = httpService.getResponseError("", ex.getMessage());
		}
		return new ResponseEntity<>(data , data.getHttpCode());
	}
	
	
	
	
}
