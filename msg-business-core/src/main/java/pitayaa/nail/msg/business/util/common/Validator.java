package pitayaa.nail.msg.business.util.common;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static final String USER_TAG_REGEX = "@\\[(\\d*):([a-zA-Z \\*\\d\\sÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳýỵỷỹ]*)]";
	public static final String USER_TAG_NOTI_REGEX = "@\\[(\\d*):([a-zA-Z \\*\\d\\sÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳýỵỷỹ]*):(true|false)]";
	public static final String SQL_REGEXP_LIKE_VIETTEL_PATTERN = "^84(97|98|163|164|165|166|167|168|169|96)";
	public static final String SQL_REGEXP_LIKE_VTELMBIVNA_PATTERN = "^84(90|91|93|94|97|98|120|121|122|123|125|126|127|128|129|163|164|165|166|167|168|169|96|124)";
	
    public static boolean validateImageContentType(String contentType) {
        return contentType.startsWith("image");
    }

    public static boolean isValidLocationPath(String path) {
        Pattern p = Pattern.compile("^[A-Za-z0-9_\\-@]+$");
        Matcher m = p.matcher(path);

        boolean matchFound = m.matches();
        return matchFound;
    }

    public static boolean isExistBanCharacters(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            return false;
        }
        Pattern p = Pattern.compile("[^<>]+");
        Matcher m = p.matcher(path);

        boolean matchFound = !m.matches();
        return matchFound;
    }

    public static boolean isValidPrice(String price) {
        // can only accept 0-9,. (temp)
        if (StringUtil.isNullOrEmpty(price)) {
            return true;
        }
        Pattern p = Pattern.compile("^[0-9.]+$");
        Matcher m = p.matcher(price);

        boolean matchFound = m.matches();
        return matchFound;
    }

    public static boolean isExistSpecialCharacters(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            return false;
        }
        Pattern p = Pattern.compile("[^<>!/@#$%&*(){}?]+");
        Matcher m = p.matcher(path);

        boolean matchFound = !m.matches();
        return matchFound;
    }

    public static boolean isExistOpenCloseTag(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            return false;
        }
        Pattern p = Pattern.compile("[^<>]+");
        Matcher m = p.matcher(path);

        boolean matchFound = !m.matches();
        return matchFound;
    }
    
    /**
     * Validate mobile phone number. A mobile phone number is considered valid
     * if: <br/>
     * - has 10 digits and begin with 09 <br/>
     * - has 11 digits and begin with 01 <br/>
     * - has 11 digits and begin with 849 <br/>
     * - has 12 digits and begin with 841 <br/>
     * - has 12 digits and begin with +849 <br/>
     * - has 13 digits and begin with +841
     * 
     * @param mobileNumber
     *            the mobile phone number
     * @return true if argument is a valid mobile phone number, false otherwise
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        mobileNumber = mobileNumber.trim();
        final String prefix849 = "849";
        final String prefix849plus = "+849";
        final String prefix841 = "841";
        final String prefix841plus = "+841";
        final String prefix09 = "09";
        final String prefix01 = "01";
        boolean result = false;

        if (!StringUtil.isNullOrEmpty(mobileNumber)
                && canBePhoneNumber(mobileNumber)) {
            int length = mobileNumber.length();
            switch (length) {
            case 10:
                if (mobileNumber.startsWith(prefix09)) {
                    result = true;
                }
                break;
            case 11:
                if (mobileNumber.startsWith(prefix01)
                        || mobileNumber.startsWith(prefix849)) {
                    result = true;
                }
                break;
            case 12:
                if (mobileNumber.startsWith(prefix841)
                        || mobileNumber.startsWith(prefix849plus)) {
                    result = true;
                }
                break;
            case 13:
                if (mobileNumber.startsWith(prefix841plus)) {
                    result = true;
                }
                break;
            }
        }

        return result;
    }

    public static boolean canBePhoneNumber(String phonenumber) {
        if (StringUtil.isNullOrEmpty(phonenumber)) {
            return false;
        }
        Pattern p = Pattern.compile("^[09|01|849|841|+849|+841][0-9]+$");
        Matcher m = p.matcher(phonenumber);

        boolean matchFound = m.matches();
        return matchFound;
    }
   
   public static String parseMobileNumber(String mobileNumber) {
	   final String countryCode = "84";
	   final String countryCodePlus = "+84";
	   final String _9x = "9";
	   final String _09x = "09";
	   final String _1x = "1";
	   final String _01x = "01";
	   
	   if (mobileNumber == null) {
		   return null;
	   }
	   mobileNumber = mobileNumber.trim();
	   
	   if(mobileNumber.startsWith(countryCodePlus)) {
		   mobileNumber = mobileNumber.substring(1);
	   }
	   
	   String validatePhone = null;
	   
	   // phone start with "84" or "+84"
	   if (mobileNumber.startsWith(countryCode)) {
		   boolean isMobileNumber = (mobileNumber.length() == 11 && mobileNumber.substring(2).startsWith(_9x))
			   || (mobileNumber.length() == 12 && mobileNumber.substring(2).startsWith(_1x));
		   
		   if (isMobileNumber) {
			   validatePhone = mobileNumber;
		   } 
	   } 
	   // phone = "09xxxxxxxx" (like: 0987868686)
	   else if (mobileNumber.length() == 10 && mobileNumber.startsWith(_09x)) {
		   validatePhone = countryCode + mobileNumber.substring(1);
	   } 
	   
	   // phone = "1xxxxxxxx" (like: 01696999999)
	   else if (mobileNumber.length() == 11 && mobileNumber.startsWith(_01x)) {
		   validatePhone = countryCode + mobileNumber.substring(1);
	   }
	   
	   return validatePhone;        
   }
    
    public static boolean validateEmail(String value) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
        Matcher m = p.matcher(value);

        boolean matchFound = m.matches();
        return matchFound;
    }

    public static boolean validateUrl(String value) {
        Pattern p = Pattern.compile("(http|https|ftp):\\/\\/(([A-Z0-9][A-Z0-9_-]*)(\\.[A-Z0-9][A-Z0-9_-]*)+)(:(\\d+))?\\/?");
        Matcher m = p.matcher(value);

        boolean matchFound = m.matches();
        return matchFound;
    }

    public static boolean validateNumber(String value) {
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = p.matcher(value);

        boolean matchFound = m.matches();
        return matchFound;
    }

    public static boolean validateUsername(String name) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+([_][A-Za-z0-9]+)*$");
        Matcher m = p.matcher(name);

        boolean matchFound = m.matches();
        return matchFound;
    }
    
    public static boolean isViettelPhoneNumber(String phoneNumber) {
        boolean match = false;
        if(canBePhoneNumber(phoneNumber)) {
            String formatedPhone = parseMobileNumber(phoneNumber);
            if(formatedPhone != null) {
                Pattern p = Pattern.compile("^84(98|97|163|164|165|166|167|168|169|96)[0-9]*$");
                Matcher m = p.matcher(formatedPhone);
                match = m.matches();
            }
        }
        return match;
    }
    
    public static boolean isViettelMobiVinaPhoneNumber(String phoneNumber) {
        boolean match = false;
        if(canBePhoneNumber(phoneNumber)) {
            String formatedPhone = parseMobileNumber(phoneNumber);
            if(formatedPhone != null) {
                Pattern p = Pattern.compile("^84(98|97|163|164|165|166|167|168|169|90|93|122|126|121|128|120|91|94|123|125|127|129|96|124)[0-9]*$");
                Matcher m = p.matcher(formatedPhone);
                match = m.matches();
            }
        }
        return match;
    }

    public static boolean isValidDisplayName(String displayName) {
		if (displayName == null || displayName.trim().equals(""))
			return true;
//		final String DISPLAY_NAME_REGEX = "^[a-zA-Z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệếỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳýỵỷỹ]+$";
		final String DISPLAY_NAME_REGEX  = "^[a-zA-Z \\sÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳýỵỷỹ]+$";
		Pattern pattern = Pattern.compile(DISPLAY_NAME_REGEX);
		Matcher matcher = pattern.matcher(displayName.trim());
		return matcher.matches();
	}
    
    public static Set<Integer> splitUserTag(String content) {
    	final Set<Integer> userIdLst = new HashSet<Integer>(); 
	    if(StringUtil.isNullOrEmpty(content)){
	        return userIdLst;
	    }
		final Pattern p = Pattern.compile(USER_TAG_REGEX,Pattern.CASE_INSENSITIVE);
		final Matcher m = p.matcher(content);
		// Check all occurance
		 while(m.find()){
		     userIdLst.add(Integer.parseInt(m.group(1)));
         }
		return userIdLst;
	}
    
    public static String adjustPhone(String phone) {
		if(StringUtil.isNullOrEmpty(phone)){
			return "";
		}
		String[] lstChar = {"-","."," "};
		int first = 0;
		int last = 0;
		for (String ch : lstChar) {
			first = phone.indexOf(ch);
			last = phone.lastIndexOf(ch);
			if(first > 0 && last < phone.length() - 1){
				phone = phone.replaceAll(ch, "");
			}
			
		}
		return phone;
	}
    
    public static String replaceUserTagText(String content) {
    	if(StringUtil.isNullOrEmpty(content)){
			return "";
		}
        return content.replaceAll(USER_TAG_REGEX,"@$2");
    }

    public static String replaceUserTagNotiText(String content) {
    	if(StringUtil.isNullOrEmpty(content)){
			return "";
		}
        return content.replaceAll(USER_TAG_NOTI_REGEX,"$2");
    }
}
