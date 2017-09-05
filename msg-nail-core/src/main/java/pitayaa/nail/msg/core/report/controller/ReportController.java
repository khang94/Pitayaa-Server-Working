package pitayaa.nail.msg.core.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.report.ReportDocs;
import pitayaa.nail.domain.report.elements.ReportServiceData;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.report.service.ReportService;
import pitayaa.nail.msg.core.report.service.ReportServiceImpl;

@Controller
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	ReportServiceImpl reportTest;
	
	@Autowired
	JsonHttpService httpService;

	@RequestMapping(value = "reports/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getReportSample() throws Exception {

		String message = "Welcome to Pitayaa Report";
		Resource<String> resource = new Resource<String>(message);
		return ResponseEntity.ok(resource);
	}
	
	@RequestMapping(value = "reports/services", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getReportServices(
			@RequestParam(required = false, defaultValue = "")String salonId,
			@RequestParam(required = false, defaultValue = "") String from,
			@RequestParam(required = false, defaultValue = "") String to) throws Exception {

		JsonHttp data = new JsonHttp();
		
		try {
			ReportServiceData serviceData = reportService.getServicesData(salonId, from, to);
			data = httpService.getResponseSuccess(serviceData, "get services data report successfully");
		} catch (Exception ex){
			data = httpService.getResponseError("", ex.getMessage());
		}
		return new ResponseEntity<>(data , data.getHttpCode());
	}
	
	@RequestMapping(value = "reports/promotions", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getReportPromotions(
			@RequestParam(required = false, defaultValue = "")String salonId,
			@RequestParam(required = false, defaultValue = "") String from,
			@RequestParam(required = false, defaultValue = "") String to) throws Exception {

		JsonHttp data = new JsonHttp();
		
		try {
			ReportDocs reportPromotionDocs = reportService.getPromotionReport(salonId, from, to);
			data = httpService.getResponseSuccess(reportPromotionDocs, "get promotion data report successfully");
		} catch (Exception ex){
			data = httpService.getResponseError("", ex.getMessage());
		}
		return new ResponseEntity<>(data , data.getHttpCode());
	}
}
