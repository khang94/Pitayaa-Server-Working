package pitayaa.nail.msg.business.util.security;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogUtils {
	private static Logger serverErrorLog = Logger.getLogger("com.viettel.vinamilk.log.error.server");
	private static Logger serverInfoLog = Logger.getLogger("com.viettel.vinamilk.log.info.server");
	private static Logger serverDebugLog = Logger.getLogger("com.viettel.vinamilk.log.debug.server");
	private static Logger clientErrorLog = Logger.getLogger("com.viettel.vinamilk.log.error.client");
	private static Logger getSQLFileLog = Logger.getLogger("com.viettel.vinamilk.log.getsqlfile.client");
	private static Logger synDataLog = Logger.getLogger("com.viettel.vinamilk.log.syndata.client");
	private static Logger transactionExecutor = Logger.getLogger("com.viettel.vinamilk.log.syn.transactionExecutor");
	private static Logger tunningLog = Logger.getLogger("com.viettel.vinamilk.aop.tunningLog");

	/*public static void logError(Throwable e, String message) {
		if (serverErrorLog.isEnabledFor(Level.ERROR)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(Configuration.getAppName()).append("\",");
			sb.append("\"").append(toCSVCell(message)).append("\",");

			sb.append("\"");
			if (null != e) {
				StackTraceElement[] st = e.getStackTrace();

				for (int i = st.length - 1; i >= 0; i--) {
					sb.append(st[i]).append("\n");
				}

				sb.append("\",");
			}

			sb.append("\"").append(new Date()).append("\"");
			serverErrorLog.error(sb.toString());
		}
	}*/
	
	@SuppressWarnings("rawtypes")
	public static void logError(final Class clazz, Throwable e, String ... logs) {
		if (serverErrorLog.isEnabledFor(Level.ERROR)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(clazz != null ? clazz.getName(): "").append("\",");
			sb.append("\"").append(toCSVCell(logs)).append("\",").append("\"");
			
			if (null != e) {
				StackTraceElement[] st = e.getStackTrace();
				
				for (int i = st.length - 1; i >= 0; i--) {
					sb.append(st[i]).append("\n");
				}
				
				sb.append("\",");
			}
			
			serverErrorLog.error(sb.toString());
		}
	}

	/*public static void logInfo(String className, String message) {
		if (serverInfoLog.isEnabledFor(Level.INFO)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(Configuration.getAppName()).append("\",");
			sb.append("\"").append(className).append("\",");
			sb.append("\"").append(toCSVCell(message)).append("\",");

			sb.append("\"").append(new Date()).append("\"");
			serverInfoLog.info(sb.toString());
		}
	}*/
	
	@SuppressWarnings("rawtypes")
	public static void logInfo(Class clazz, String ... message) {
		if (serverInfoLog.isEnabledFor(Level.INFO)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(clazz == null? "" : clazz.getName()).append("\",");
			sb.append("\"").append(toCSVCell(message)).append("\",");
			
			serverInfoLog.info(sb.toString());
		}
	}
	
	/*public static void logDebug(Throwable e, String message) {
		if (serverDebugLog.isEnabledFor(Level.DEBUG)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(Configuration.getAppName()).append("\",")
				.append("\"").append(toCSVCell(message)).append("\",")
				.append("\"");
			
			if (null != e) {
				StackTraceElement[] st = e.getStackTrace();
				
				for (int i = st.length - 1; i >= 0; i--) {
					sb.append(st[i]).append("\n");
				}
				
				sb.append("\",");
			}
			
			serverDebugLog.debug(sb.toString());
		}
	}*/
	
	@SuppressWarnings("rawtypes")
	public static void logDebug(Class clazz, Throwable e, String... message) {
		if (serverDebugLog.isEnabledFor(Level.DEBUG)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(clazz != null ? clazz.getName(): "").append("\",")
				.append("\"").append(toCSVCell(message)).append("\",")
				.append("\"");
			
			if (null != e) {
				StackTraceElement[] st = e.getStackTrace();
				
				for (int i = st.length - 1; i >= 0; i--) {
					sb.append(st[i]).append("\n");
				}
				
				sb.append("\",");
			}
			
			sb.append("\"");
			
			serverDebugLog.debug(sb.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	public static void logGetSQLFile(Class clazz, Long shopId,
			Long clientId, String imei, String ... message) {
		if (getSQLFileLog.isEnabledFor(Level.INFO)) {
			StringBuilder sb = new StringBuilder();

			sb.append("\"").append(clazz != null ? clazz.getName(): "").append("\",");
			sb.append("\"").append(shopId).append("\",");
			sb.append("\"").append(clientId).append("\",");
			sb.append("\"").append(imei).append("\",");
			sb.append("\"").append(toCSVCell(message)).append("\"");

			getSQLFileLog.info(sb.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	public static void logSynData(Class clazz, Long shopId,
			Long clientId, String imei, Long lastLogId, String... message) {
		if (synDataLog.isEnabledFor(Level.INFO)) {
			StringBuilder sb = new StringBuilder();

			sb.append("\"").append(clazz != null ? clazz.getName() : "").append("\",");
			sb.append("\"").append(shopId).append("\",");
			sb.append("\"").append(clientId).append("\",");
			sb.append("\"").append(imei).append("\",");
			sb.append("\"").append(lastLogId).append("\",");
			sb.append("\"").append(toCSVCell(message)).append("\"");

			synDataLog.info(sb.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	public static void logTransactionExecutor(Class clazz, Throwable e, 
			Long shopId, Long clientId, String imei, String transactionCode, String... message) {
		if (transactionExecutor.isEnabledFor(Level.INFO)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(clazz != null ? clazz.getName(): "").append("\",");
			sb.append("\"").append(shopId).append("\",");
			sb.append("\"").append(clientId).append("\",");
			sb.append("\"").append(toCSVCell(message)).append("\",");
			
			sb.append("\"");
			if (null != e) {
				StackTraceElement[] st = e.getStackTrace();

				for (int i = st.length - 1; i >= 0; i--) {
					sb.append(st[i]).append("\n");
				}

				sb.append("\",");
			}

			transactionExecutor.info(sb.toString());
		}
	}

	public static void tunningLog(String service, String staffId, String input,
			String output, Date beginTime, Date endTime, long executeTime) {
		if (tunningLog.isEnabledFor(Level.INFO)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(service).append("\",");
			sb.append("\"").append(staffId).append("\",");
			sb.append("\"").append(toCSVCell(input)).append("\",");
			sb.append("\"").append(toCSVCell(output)).append("\",");

			sb.append("\"").append(beginTime).append("\",");
			sb.append("\"").append(endTime).append("\",");
			sb.append("\"").append(executeTime).append("\",");

			tunningLog.info(sb.toString());
		}
	}

	public static void logMobileClientError(String platform, String model,
			String version, String errName, String description) {
		
		if (clientErrorLog.isEnabledFor(Level.ERROR)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\"").append(platform).append("\",");
			sb.append("\"").append(model).append("\",");
			sb.append("\"").append(toCSVCell(errName)).append("\",");
			sb.append("\"").append(toCSVCell(description)).append("\",");
			sb.append("\"").append(new Date()).append("\",");
			sb.append("\"").append(version).append("\"");

			clientErrorLog.error(sb.toString());
		}
	}

	
	
	public static String getStackTraceString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		
		if (null != e) {
			StackTraceElement[] st = e.getStackTrace();

			for (int i = st.length - 1; i >= 0; i--) {
				sb.append(st[i]).append("\n");
			}

			sb.append("\",");
		}
		return sb.toString();
	}

	private static String toCSVCell(String input) {
		if (input == null) {
			return "";
		}

		return input.replaceAll("\"", "\"\"");
	}
	
	private static String toCSVCell(String ... input) {
		if (input == null || input.length <= 0) {
			return "";
		} else{
			StringBuilder strBuil = new StringBuilder();
			for(String item : input){
				strBuil.append(item);
			}
			
			return strBuil.toString().replace("\"", "\"\"");
		}
	}

	public static String getErrorMessage(Exception ex) {
		StringBuilder errorMessage = new StringBuilder();
		
		if (ex.getCause() != null) {
			if (ex.getCause().getCause() != null) {
				errorMessage.append(ex.getMessage())
					.append("\n")
					.append(ex.getCause().getMessage())
					.append("\n")
					.append(ex.getCause().getCause().getMessage());
			} else {
				errorMessage.append(ex.getCause())
					.append("\n")
					.append(ex.getCause().getMessage());
			}
		} else {
			errorMessage.append(ex.getMessage());
		}
		
		return errorMessage.toString();
	}
}
