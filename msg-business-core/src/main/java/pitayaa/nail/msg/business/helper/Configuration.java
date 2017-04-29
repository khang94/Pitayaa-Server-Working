package pitayaa.nail.msg.business.helper;


public class Configuration {
	/*public static ResourceBundle getResourceBundle(String language) {
        return ResourceBundle.getBundle(AppSetting.getStringValue("resource.bundle"), new Locale(language));
    }
	
	public static String getResourceLanguage(){
		return AppSetting.getStringValue("resource.language");
	}
	
    public static String getResourceString(String language, String key, Object... params) {
        ResourceBundle rs = getResourceBundle(language);
        String text = rs.getString(key);
        
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
            	if(params[i] != null) {
            		text = text.replace("{" + i + "}", params[i].toString());
            	}
            }
        }

        return text;
    }
    
    public static final String getUploadImageUrl() {
		return AppSetting.getStringValue("upload.imageUrl");
	}
    
	public static final String getLocalUploadFile() {
		return AppSetting.getStringValue("upload.serverpath");
	}

	public static final String getUploadServerAddress() {
		return AppSetting.getStringValue("upload.serverpath.ext");
	}

	// syn config
	public static String getSqliteConfigFileName() {
		return AppSetting.getStringValue("sync.createClientDB.configFile");
	}

	public static String getSqliteExConfigFileName() {
		return AppSetting.getStringValue("sync.createClientDB.configFileExt");
	}

	public static String getSqliteURLUploadFile() {
		return AppSetting.getStringValue("sync.createClientDB.url");
	}

	public static String getSqliteDBName() {
		return AppSetting.getStringValue("sync.createClientDB.dbName");
	}

	public static String getSqliteLocalPath() {
		return AppSetting.getStringValue("sync.createClientDB.serverPath");
	}

	public static int getMaxRownumSyn() {
		return AppSetting.getIntegerValue("sync.sendDataToClient.maxRownumSend");
	}

	public static int getMaxDateSyn() {
		return AppSetting.getIntegerValue("sync.sendDataToClient.maxDateSync");
	}

	public static int getFetchSize() {
		return AppSetting.getIntegerValue("sync.sendDataToClient.statementFetchSize");
	}

	public static int getMaxHourGetFile() {
		return AppSetting.getIntegerValue("sync.createClientDB.dbAvailableInHour");
	}

	public static int getMaxNumberLoginFail() {
		return AppSetting.getIntegerValue("sys.login.maxNumberLoginFail");
	}

	public static String getConfigSynFileName() {
		return AppSetting.getStringValue("config_syn_file_name");
	}
	
	// sms config
	public static String getSMSKey(){
		String value = AppSetting.getStringValue("sms.key");
		
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		} else{
			return EncryptionUtils.decrypt(value,EncryptionUtils.getKey());
		}
	}
	
	public static String getSMSSecret(){
		String value = AppSetting.getStringValue("sms.secret");
		
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		} else{
			return EncryptionUtils.decrypt(value,EncryptionUtils.getKey());
		}
	}
	
	public static String getSMSSenderId(){
		return AppSetting.getStringValue("sms.sender.id");
	}
	
	public static Integer getDefaultTimeBeforeSendSms(){
		return AppSetting.getIntegerValue("sms.timeBeforeSend");
	}
	
	public static Integer getSendSmsWorkerInQueue(){
		return AppSetting.getIntegerValue("sms.numWorkerInQueue");
	}
	
	public static String getRSAPublicKeyPath(){
		return AppSetting.getStringValue("rsa.public.path");
	}
	
	public static String getRSAPrivateKeyPath(){
		return AppSetting.getStringValue("rsa.private.path");
	}
	
	public static Integer getMemcachedTimeRemoveTokenInSecond(){
		return AppSetting.getIntegerValue("memcached.timeRemoveTokenInSecond");
	}
	
	public static String getMemcachedHost(){
		return AppSetting.getStringValue("memcached.host");
	}
	
	public static Integer getMemcachedPort(){
		return AppSetting.getIntegerValue("memcached.port");
	}*/
}