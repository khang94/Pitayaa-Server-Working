package pitayaa.nail.msg.business.helper;

public class TextHelper {
	
	public static String formatPhoneNumber(String phone){
		//String result = "";
		
		while(phone.contains("(") || phone.contains(")") || phone.contains(" ")
				|| phone.contains("-")){
			phone = phone.replace("(", "");
			phone = phone.replace(")", "");
			phone = phone.replace(" ", "");
			phone = phone.replace("-", "");
		}
			
		return phone;
	}
	
	public static void main(String[] args){
		String phone = "((301)) 979 2555";
		String result = formatPhoneNumber(phone);
		System.out.println(result);
	}

}