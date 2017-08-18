package pitayaa.nail.msg.business.json;

import pitayaa.nail.json.http.JsonHttp;

public interface JsonHttpService {

	JsonHttp getResponseSuccess(Object object, String messageSuccess);

	JsonHttp getResponseError(String messageError, String exception);

	JsonHttp getNotFoundData(String messageNotFound);

	JsonHttp deleteData(String messageDelete);

	JsonHttp saveDataSuccess(Object object, String messageSaved);

}
