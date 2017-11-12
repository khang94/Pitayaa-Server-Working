package pitayaa.nail.msg.business.util.common;

public class ValidatePhoneNumber {

	public static String formatPhoneNumber(String phoneNumber) throws Exception {
		return "";
	}
	
	public static String appendCodeAreaToPhone(String to) {
		
		if(!to.startsWith("1") && !to.startsWith("84") && !to.startsWith("9") && !to.startsWith("16")){
			to = "1" + to;
		} else if (to.startsWith("123") || to.startsWith("9") || to.startsWith("16")){
			to = "84" + to;
		}
		return to;
	}
}
