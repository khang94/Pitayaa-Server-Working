package pitayaa.nail.msg.business.util.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateHelper {

	public final static Logger LOGGER = LoggerFactory.getLogger(RestTemplateHelper.class);

	public String buildUrlRequestParam(Map<String, String> params, String url) {

		LOGGER.info("Prepare for building url with params ");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

		// Query parameters
		for (String key : params.keySet()) {
			builder.queryParam(key, params.get(key));
		}

		url = builder.buildAndExpand(url).toUriString();
		LOGGER.info("Url after build with params : [" + url + "]");
		return url;
	}
	
	public String buildUrlPathVariable(Map<String , String> vars , String url){
		
		LOGGER.info("Prepare for building url with path variables ");
		
		for(String key : vars.keySet()){
			String pathVars = "{" + key + "}";
			if(url.contains(pathVars)){
				url = url.replace(pathVars, vars.get(key));
			}
		}
		
		return url;
	}
}
