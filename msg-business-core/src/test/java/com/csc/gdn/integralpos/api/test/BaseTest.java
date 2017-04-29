/*
package com.csc.gdn.integralpos.api.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.csc.gdn.integralpos.document.application.DocumentCenterApplication;
import com.csc.gdn.integralpos.document.common.DocumentHelper;
import com.csc.gdn.integralpos.document.service.IDocumentService;
import com.csc.gdn.integralpos.domain.dms.DocumentModel;
import com.csc.gdn.integralpos.msgcommon.utility.resttemplate.RestTemplateHelper;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DocumentCenterApplication.class })
@AutoConfigureMockMvc
@SpringBootTest
public abstract class BaseTest {

	private final String PATH_DOCUMENT = "document-center.json";
	private final String PATH_PARTIAL_DOCUMENT = "partial-document.json";
	private final String URL_CONTAIN_ID = "http://localhost/attachments/";
	private final String URL_GET_TOKEN = "http://localhost:9999/ife-infra-oauth2/oauth/token?"
			+ "grant_type=password&client_id=aviva-web&username=tle3@ipos.com&password=P@ssword123";

	private final String HEADER_AUTHORIZE = "Basic YXZpdmEtd2ViOmF2aXZhLXNlY3JldA==";
	private final String AUTHORIZE_VALUE = "YXZpdmEtd2ViOmF2aXZhLXNlY3JldA==";
	private final String AUTHORIZE_NAME = "Authorization";
	private final String TOKEN_TYPE = "bearer";
	private final String ACCESS_TOKEN = "access_token";

	public static String document;
	public static String partialDocument;

	@Autowired
	public IDocumentService docService;

	public TestRestTemplate restTemplate;
	
	@Autowired
	public DocumentHelper documentHelper;

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	public RestTemplateHelper restTemplateHelper;

	@Before
	public void setup() throws IOException {
		document = readFileToString(PATH_DOCUMENT);
		partialDocument = readFileToString(PATH_PARTIAL_DOCUMENT);
	}

	public String readFileToString(String path) throws IOException {
		String targetFileStr = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(path), "UTF-8");
		return targetFileStr;
	}

	private String getIDFromLink(String link) {
		if (link.contains(URL_CONTAIN_ID)) {
			link = link.replace(URL_CONTAIN_ID, "");
		}
		return link;
	}

	public String getIDFromMVCResult(MvcResult mvcResult) throws UnsupportedEncodingException {
		String jsonModel = mvcResult.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject(jsonModel);

		String selfLink = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
		String uid = this.getIDFromLink(selfLink);
		
		int var = uid.indexOf("?");
		uid = uid.substring(0, var);

		return uid;
	}

	public HttpHeaders getAccessToken() {

		restTemplate = new TestRestTemplate();
		
		// Set headers
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(AUTHORIZE_NAME, HEADER_AUTHORIZE);
		HttpEntity<String> httpEntity = new HttpEntity<String>("" , httpHeaders);



		// pathVariables
		List<String> pathVariables = new ArrayList<String>();
		pathVariables.add(AUTHORIZE_VALUE);

		
		
		// Execute Request By Rest Template
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL_GET_TOKEN, 
				HttpMethod.POST, httpEntity, String.class);

		// Convert Response body String to json object
		String access_token = JsonPath.read(responseEntity.getBody(), "$.access_token");
		String token_type = JsonPath.read(responseEntity.getBody() , "$.token_type");

		// Get result
		HttpHeaders result = new HttpHeaders();
		result.add(this.AUTHORIZE_NAME, token_type + " " + access_token);

		return result;
	}

}
*/