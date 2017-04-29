package pitayaa.nail.msg.core.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.msg.core.common.CoreHelper;

@Controller
public class CriteriaController {
	
	@Autowired
	CoreHelper coreHelper;
	
	@RequestMapping(value = "criteria/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		QueryCriteria criteria = (QueryCriteria) coreHelper
				.createModelStructure(new QueryCriteria());

		return new ResponseEntity<>(criteria, HttpStatus.OK);
	}
	

}
