package pitayaa.nail.msg.core.common;

import org.springframework.stereotype.Service;

import pitayaa.nail.json.http.JsonHttp;

@Service
public class HttpHelper {

	public JsonHttp executeSuccess(JsonHttp jsonHttp, String result) {

		if (CoreConstant.HTTP_STATUS_SUCCESS.equalsIgnoreCase(result)){
			jsonHttp.setCode(CoreConstant.HTTP_CODE_SUCCESS);
			jsonHttp.setStatus(CoreConstant.HTTP_STATUS_SUCCESS);
		} else {
			jsonHttp.setCode(CoreConstant.HTTP_CODE_ERROR);
			jsonHttp.setStatus(CoreConstant.HTTP_STATUS_ERROR);
		}
		return jsonHttp;
	}
}
