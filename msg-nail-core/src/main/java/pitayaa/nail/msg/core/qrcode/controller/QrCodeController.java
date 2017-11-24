package pitayaa.nail.msg.core.qrcode.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pitayaa.nail.domain.qrcode.QrCode;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.qrcode.service.QrCodeService;

@RestController
public class QrCodeController {

	@Autowired
	QrCodeService qrService;

	@Autowired
	JsonHttpService httpService;
	
	@RequestMapping(value = "qrCode/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initQRCode() throws Exception {

		QrCode qrCode = qrService.initModel();
		return ResponseEntity.ok(qrCode);
	}
	
	@RequestMapping(value = "qrCode/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("Id") UUID id) throws Exception {

		Optional<QrCode> qrCode = qrService.findOne(id);
		if(qrCode.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JsonHttp jsonHttp = new JsonHttp();
		jsonHttp = httpService.getResponseSuccess(qrCode.get(),"Get data successfully...");
		
		return new ResponseEntity<>(jsonHttp.getObject(),jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "qrCode", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findByConditions(@RequestParam(name = "salonId",defaultValue = "",required = false) String salonId,
				@RequestParam(name="customerId",defaultValue = "",required = false) String customerId) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(qrService.findBySalonIdOrCustomerId(salonId, customerId), "Get data successfully...");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "error");
		}
		
		return new ResponseEntity<>(jsonHttp,jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "qrCode/customer", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findQrCodeForCustomer(@RequestParam("customerId") String customerId) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(qrService.findQrCodeForCustomer(customerId), "Get data successfully...");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "error");
		}
		
		return new ResponseEntity<>(jsonHttp,jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "qrCode/services", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createQrCode(@RequestParam("operation") String operation,
			@RequestParam("customerId") String customerId,
			@RequestParam(name="qr",defaultValue = "",required = false) String qr) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(qrService.actionQrCodeForCustomer(customerId, qr, operation), "Get data successfully...");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", ex.getMessage());
		}
		
		return new ResponseEntity<>(jsonHttp,jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "qrCode", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> save(@RequestBody QrCode qrCode) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			jsonHttp = httpService.getResponseSuccess(qrService.save(qrCode), "Get data successfully...");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", ex.getMessage());
		}
		
		return new ResponseEntity<>(jsonHttp,jsonHttp.getHttpCode());
	}
	
	
}
