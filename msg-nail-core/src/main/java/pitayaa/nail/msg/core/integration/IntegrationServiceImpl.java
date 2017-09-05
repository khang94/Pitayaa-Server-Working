package pitayaa.nail.msg.core.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.msg.business.util.common.RestTemplateHelper;
import pitayaa.nail.msg.core.properties.CoreProperties;

@Service
public class IntegrationServiceImpl implements IntegrationService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IntegrationServiceImpl.class);

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	RestTemplateHelper restTemplateHelper;

	@Override
	public List<SmsQueue> getReportSms(String salonId, String from, String to) throws Exception {

		// Call API Function
		Map<String, String> headersMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		for (String header : headers.keySet()) {
			headersMap.put(header, headers.getFirst(header));
		}

		// urlParameters
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put("salonId", salonId);
		urlParameters.put("from", from);
		urlParameters.put("to", to);
		
		

		String url = coreProperties.getReportAPI();
		url = restTemplateHelper.buildUrlRequestParam(urlParameters, url);
		LOGGER.info("Get URL Load List Queue Sms : [" + url + "] to send request !");

		// Execute Request By Rest Template
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<SmsQueue>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SmsQueue>>() {
				});
		if (response.getStatusCode().is2xxSuccessful()) {
			LOGGER.info("Get response successully from URL [" + url + "]");
		}
		return response.getBody();
	}
}
