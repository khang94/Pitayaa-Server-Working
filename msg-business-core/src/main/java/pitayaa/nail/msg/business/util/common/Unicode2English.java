package pitayaa.nail.msg.business.util.common;

import java.util.HashMap;

public class Unicode2English {

	/** The codau tokhongdau. */
	static HashMap<String, String> codauTokhongdau = null;

	/** The codau toncr decimal. */
	static HashMap<String, String> codauToncrDecimal = null;

	/** The codau to ut f8 literal. */
	static HashMap<String, String> codauToUTF8Literal = null;

	/** The codau. */
    static String codau[] =
            { "à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ậ", "ẩ", "ẫ", "ă", "ằ",
                    "ắ", "ặ", "ẳ", "ẵ", "è", "é", "ẹ", "ẻ", "ẽ", "ê", "ề", "ế",
                    "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ", "ò", "ó", "ọ", "ỏ",
                    "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ơ", "ờ", "ớ", "ợ", "ở",
                    "ỡ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ",
                    "ỳ", "ý", "ỵ", "ỷ", "ỹ", "đ", "À", "Á", "Ạ", "Ả", "Ã", "Â",
                    "Ầ", "Ấ", "Ậ", "Ẩ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ", "È",
                    "É", "Ẹ", "Ẻ", "Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í",
                    "Ị", "Ỉ", "Ĩ", "Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ",
                    "Ổ", "Ỗ", "Ơ", "Ờ", "Ớ", "Ợ", "Ở", "Ỡ", "Ù", "Ú", "Ụ", "Ủ",
                    "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ", "Ỳ", "Ý", "Ỵ", "Ỷ", "Ỹ",
                    "Đ", "ê", "ù", "à" };
    
	/** The codau. */
	static String MAX_CO_DAU[] = { "à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ậ", "ẩ",
			"ẫ", "ă", "ằ", "ắ", "ặ", "ẳ", "ẵ", "è", "é", "ẹ", "ẻ", "ẽ", "ê",
			"ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ", "ò", "ó", "ọ",
			"ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ơ", "ờ", "ớ", "ợ", "ở",
			"ỡ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ", "ỳ",
			"ý", "ỵ", "ỷ", "ỹ", "đ", "À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ",
			"Ậ", "Ẩ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ", "È", "É", "Ẹ", "Ẻ",
			"Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ", "Ò",
			"Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ", "Ơ", "Ờ", "Ớ",
			"Ợ", "Ở", "Ỡ", "Ù", "Ú", "Ụ", "Ủ", "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử",
			"Ữ", "Ỳ", "Ý", "Ỵ", "Ỷ", "Ỹ", "Đ", "ê", "ù", "à",

			"á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ậ", "ẩ", "ẫ", "ă",
			"ằ", "ắ", "ặ", "ẳ", "ẵ", "è", "é", "ẹ", "ẻ", "ẽ", "ê",
			"ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ", "ò",
			"ó", "ọ", "ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ơ",
			"ờ", "ớ", "ợ", "ở", "ỡ", "ù", "ú", "ụ", "ủ", "ũ", "ư",
			"ừ", "ứ", "ự", "ử", "ữ", "ỳ", "ý", "ỵ", "ỷ", "ỹ", "đ",
			"À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ", "Ậ", "Ẩ", "Ẫ",
			"Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ", "È", "É", "Ẹ", "Ẻ", "Ẽ",
			"Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ",
			"Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ",
			"Ơ", "Ờ", "Ớ", "Ợ", "Ở", "Ỡ", "Ù", "Ú", "Ụ", "Ủ", "Ũ",
			"Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ", "Ỳ", "Ý", "Ỵ", "Ỷ", "Ỹ",
			"Đ" };

	/** The cd. */
    static String cd[] =
            { ">", "<", "'", "'", "'", "''", "&", " ", "...", "À", "Á", "Â", "Ã", "È", "É", "Ê",
                    "Ì", "Í", "Ð", "Ò", "Ó", "Ô", "Õ", "Ù", "Ú", "Ý", "à", "á",
                    "â", "ã", "è", "é", "ê", "ì", "í", "ò", "ó", "ô", "õ", "ù",
                    "ú", "ý"

            };

    /** The Character entities. */
    static String CharacterEntities[] =
            { "&gt;", "&lt;", "&apos;", "&ldquo;", "&rdquo;", "&quot;", "&amp;", "&nbsp;", "&hellip;",
                    "&Agrave;", "&Aacute;", "&Acirc;", "&Atilde;", "&Egrave;",
                    "&Eacute;", "&Ecirc;", "&Igrave;", "&Iacute;", "&ETH;",
                    "&Ograve;", "&Oacute;", "&Ocirc;", "&Otilde;", "&Ugrave;",
                    "&Uacute;", "&Yacute;", "&agrave;", "&aacute;", "&acirc;",
                    "&atilde;", "&egrave;", "&eacute;", "&ecirc;", "&igrave;",
                    "&iacute;", "&ograve;", "&oacute;", "&ocirc;", "&otilde;",
                    "&ugrave;", "&uacute;", "&yacute;" };

    /** The UT f8 literal. */
    static String UTF8Literal[] =
            { "Ã ", "Ã¡", "áº¡", "áº£", "Ã£", "Ã¢", "áº§", "áº¥", "áº­", "áº©",
                    "áº«", "Äƒ", "áº±", "áº¯", "áº·", "áº³", "áºµ", "Ã¨", "Ã©",
                    "áº¹", "áº»", "áº½", "Ãª", "á»", "áº¿", "á»‡", "á»ƒ",
                    "á»…", "Ã¬", "Ã­", "á»‹", "á»‰", "Ä©", "Ã²", "Ã³", "á»",
                    "á»", "Ãµ", "Ã´", "á»“", "á»‘", "á»™", "á»•", "á»—", "Æ¡",
                    "á»", "á»›", "á»£", "á»Ÿ", "á»¡", "Ã¹", "Ãº", "á»¥",
                    "á»§", "Å©", "Æ°", "á»«", "á»©", "á»±", "á»­", "á»¯",
                    "á»³", "Ã½", "á»µ", "á»·", "á»¹", "Ä‘", "Ã€", "Ã", "áº ",
                    "áº¢", "Ãƒ", "Ã‚", "áº¦", "áº¤", "áº¬", "áº¨", "áºª", "Ä‚",
                    "áº°", "áº®", "áº¶", "áº²", "áº´", "Ãˆ", "Ã‰", "áº¸",
                    "áºº", "áº¼", "ÃŠ", "á»€", "áº¾", "á»†", "á»‚", "á»„",
                    "ÃŒ", "Ã", "á»Š", "á»ˆ", "Ä¨", "Ã’", "Ã“", "á»Œ", "á»Ž",
                    "Ã•", "Ã”", "á»’", "á»", "á»˜", "á»”", "á»–", "Æ ", "á»œ",
                    "á»š", "á»¢", "á»ž", "á» ", "Ã™", "Ãš", "á»¤", "á»¦", "Å¨",
                    "Æ¯", "á»ª", "á»¨", "á»°", "á»¬", "á»®", "á»²", "Ã",
                    "á»´", "á»¶", "á»¸", "Ä", "Ãª", "Ã¹", "Ã " };

    /** The NCR decimal. */
    static String NCRDecimal[] =
            { "&#224;", "&#225;", "&#7841;", "&#7843;", "&#227;", "&#226;",
                    "&#7847;", "&#7845;", "&#7853;", "&#7849;", "&#7851;",
                    "&#259;", "&#7857;", "&#7855;", "&#7863;", "&#7859;",
                    "&#7861;", "&#232;", "&#233;", "&#7865;", "&#7867;",
                    "&#7869;", "&#234;", "&#7873;", "&#7871;", "&#7879;",
                    "&#7875;", "&#7877;", "&#236;", "&#237;", "&#7883;",
                    "&#7881;", "&#297;", "&#242;", "&#243;", "&#7885;",
                    "&#7887;", "&#245;", "&#244;", "&#7891;", "&#7889;",
                    "&#7897;", "&#7893;", "&#7895;", "&#417;", "&#7901;",
                    "&#7899;", "&#7907;", "&#7903;", "&#7905;", "&#249;",
                    "&#250;", "&#7909;", "&#7911;", "&#361;", "&#432;",
                    "&#7915;", "&#7913;", "&#7921;", "&#7917;", "&#7919;",
                    "&#7923;", "&#253;", "&#7925;", "&#7927;", "&#7929;",
                    "&#273;", "&#192;", "&#193;", "&#7840;", "&#7842;",
                    "&#195;", "&#194;", "&#7846;", "&#7844;", "&#7852;",
                    "&#7848;", "&#7850;", "&#258;", "&#7856;", "&#7854;",
                    "&#7862;", "&#7858;", "&#7860;", "&#200;", "&#201;",
                    "&#7864;", "&#7866;", "&#7868;", "&#202;", "&#7872;",
                    "&#7870;", "&#7878;", "&#7874;", "&#7876;", "&#204;",
                    "&#205;", "&#7882;", "&#7880;", "&#296;", "&#210;",
                    "&#211;", "&#7884;", "&#7886;", "&#213;", "&#212;",
                    "&#7890;", "&#7888;", "&#7896;", "&#7892;", "&#7894;",
                    "&#416;", "&#7900;", "&#7898;", "&#7906;", "&#7902;",
                    "&#7904;", "&#217;", "&#218;", "&#7908;", "&#7910;",
                    "&#360;", "&#431;", "&#7914;", "&#7912;", "&#7920;",
                    "&#7916;", "&#7918;", "&#7922;", "&#221;", "&#7924;",
                    "&#7926;", "&#7928;", "&#272;", "&#234;", "&#249;",
                    "&#224;"

            };

    /** The java unicode. */
    static String javaUnicode[] =
            { "à", "á", "\u1EA1", "\u1EA3", "ã", "â", "\u1EA7", "\u1EA5",
                    "\u1EAD", "\u1EA9", "\u1EAB", "\u0103", "\u1EB1", "\u1EAF",
                    "\u1EB7", "\u1EB3", "\u1EB5", "è", "é", "\u1EB9", "\u1EBB",
                    "\u1EBD", "ê", "\u1EC1", "\u1EBF", "\u1EC7", "\u1EC3",
                    "\u1EC5", "ì", "í", "\u1ECB", "\u1EC9", "\u0129", "ò", "ó",
                    "\u1ECD", "\u1ECF", "õ", "ô", "\u1ED3", "\u1ED1", "\u1ED9",
                    "\u1ED5", "\u1ED7", "\u01A1", "\u1EDD", "\u1EDB", "\u1EE3",
                    "\u1EDF", "\u1EE1", "ù", "ú", "\u1EE5", "\u1EE7", "\u0169",
                    "\u01B0", "\u1EEB", "\u1EE9", "\u1EF1", "\u1EED", "\u1EEF",
                    "\u1EF3", "ý", "\u1EF5", "\u1EF7", "\u1EF9", "\u0111", "À",
                    "Á", "\u1EA0", "\u1EA2", "Ã", "Â", "\u1EA6", "\u1EA4",
                    "\u1EAC", "\u1EA8", "\u1EAA", "\u0102", "\u1EB0", "\u1EAE",
                    "\u1EB6", "\u1EB2", "\u1EB4", "È", "É", "\u1EB8", "\u1EBA",
                    "\u1EBC", "Ê", "\u1EC0", "\u1EBE", "\u1EC6", "\u1EC2",
                    "\u1EC4", "Ì", "Í", "\u1ECA", "\u1EC8", "\u0128", "Ò", "Ó",
                    "\u1ECC", "\u1ECE", "Õ", "Ô", "\u1ED2", "\u1ED0", "\u1ED8",
                    "\u1ED4", "\u1ED6", "\u01A0", "\u1EDC", "\u1EDA", "\u1EE2",
                    "\u1EDE", "\u1EE0", "Ù", "Ú", "\u1EE4", "\u1EE6", "\u0168",
                    "\u01AF", "\u1EEA", "\u1EE8", "\u1EF0", "\u1EEC", "\u1EEE",
                    "\u1EF2", "Ý", "\u1EF4", "\u1EF6", "\u1EF8", "\u0110", "ê",
                    "ù", "à" };

	/** The khongdau. */
	static String MAX_KHONG_DAU[] = { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "e", "e", "e", "e", "e", "e",
			"e", "e", "e", "e", "e", "i", "i", "i", "i", "i", "o", "o", "o",
			"o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o",
			"o", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "y",
			"y", "y", "y", "y", "d", "A", "A", "A", "A", "A", "A", "A", "A",
			"A", "A", "A", "A", "A", "A", "A", "A", "A", "E", "E", "E", "E",
			"E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I", "I", "O",
			"O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O",
			"O", "O", "O", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U",
			"U", "Y", "Y", "Y", "Y", "Y", "D", "e", "u", "a",

			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e",
			"e", "i", "i", "i", "i", "i", "o", "o", "o", "o", "o", "o", "o",
			"o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "u", "u", "u",
			"u", "u", "u", "u", "u", "u", "u", "u", "y", "y", "y", "y", "y",
			"d", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
			"A", "A", "A", "A", "A", "E", "E", "E", "E", "E", "E", "E", "E",
			"E", "E", "E", "I", "I", "I", "I", "I", "O", "O", "O", "O", "O",
			"O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "U",
			"U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "Y", "Y", "Y",
			"Y", "Y", "D" };

	/** The khongdau. */
	static String khongdau[] = { "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "e", "e", "e", "e", "e",
			"e", "e", "e", "e", "e", "e", "i", "i", "i", "i", "i", "o", "o",
			"o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o",
			"o", "o", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u",
			"y", "y", "y", "y", "y", "d", "A", "A", "A", "A", "A", "A", "A",
			"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "E", "E", "E",
			"E", "E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I", "I",
			"O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O",
			"O", "O", "O", "O", "U", "U", "U", "U", "U", "U", "U", "U", "U",
			"U", "U", "Y", "Y", "Y", "Y", "Y", "D", "e", "u", "a" };

	/**
	 * Inits the.
	 */
	public static void init() {
		codauTokhongdau = new HashMap<String, String>();
		codauToncrDecimal = new HashMap<String, String>();
		codauToncrDecimal = new HashMap<String, String>();
		for (int i = 0; i < codau.length; i++) {
			// codauTokhongdau.put(codau[i], khongdau[i]);
			codauToncrDecimal.put(codau[i], NCRDecimal[i]);
			codauToncrDecimal.put(codau[i], UTF8Literal[i]);

		}
		for (int i = 0; i < codau.length; i++) {
			codauTokhongdau.put(MAX_CO_DAU[i], MAX_KHONG_DAU[i]);

		}

	}

	/**
	 * Codau2khongdau.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String codau2khongdau(String input) {
		if (codauTokhongdau == null) {
			init();
		}
		input = input.trim();

		for (int i = 0; i < codau.length; i++) {
			input = input.replace(codau[i], khongdau[i]);
		}
		// for(int i=0;i<input.length();i++){
		// input=input.replace(input[i], khongdau[]);
		// }
		return input;
	}

	/**
	 * Unicode2ncrdecimal.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String unicode2ncrdecimal(String input) {
		if (StringUtil.isNullOrEmpty(input))
			return "";
		input = input.trim();
		for (int i = 0; i < codau.length; i++) {
			input = input.replace(codau[i], NCRDecimal[i]);
		}
		// for(int i=0;i<input.length();i++){
		// input=input.replace(input[i], khongdau[]);
		// }
		return input;
	}
	
	/**
	 * Ncrdecimal2unicode.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String ncrdecimal2unicode(String input) {
		if (StringUtil.isNullOrEmpty(input))
			return "";
		input = input.trim();
		for (int i = 0; i < NCRDecimal.length; i++) {
			input = input.replace(NCRDecimal[i], codau[i]);
		}
		return input;
	}

	/**
	 * Unicode2java unicode.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String unicode2javaUnicode(String input) {
		if (StringUtil.isNullOrEmpty(input))
			return "";
		input = input.trim();
		for (int i = 0; i < codau.length; i++) {
			input = input.replace(codau[i], javaUnicode[i]);
		}
		// for(int i=0;i<input.length();i++){
		// input=input.replace(input[i], khongdau[]);
		// }
		return input;
	}

	/**
	 * Entities2 unicode.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String entities2Unicode(String input) {
		if (StringUtil.isNullOrEmpty(input))
			return "";
		input = input.trim();
		for (int i = 0; i < CharacterEntities.length; i++) {
			input = input.replace(CharacterEntities[i], cd[i]);
		}

		return input;
	}

	/**
	 * Unicode2 ut f8 literal.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String unicode2UTF8Literal(String input) {
		if (StringUtil.isNullOrEmpty(input))
			return "";
		input = input.trim();
		for (int i = 0; i < codau.length; i++) {
			input = input.replace(codau[i], UTF8Literal[i]);
		}
		// for(int i=0;i<input.length();i++){
		// input=input.replace(input[i], khongdau[]);
		// }
		return input;
	}

	/**
	 * Removes the dau.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String removeDau(String input) {
		if (StringUtil.isNullOrEmpty(input))
			return "";
		input = input.trim();
		for (int i = 0; i < MAX_CO_DAU.length; i++) {
			input = input.replace(MAX_CO_DAU[i], MAX_KHONG_DAU[i]);
		}
		return input;
	}

	/**
	 * Gets the codau tokhongdau.
	 * 
	 * @return the codau tokhongdau
	 */
	public static HashMap<String, String> getCodauTokhongdau() {
		return codauTokhongdau;
	}

	/**
	 * Gets the codau toncr decimal.
	 * 
	 * @return the codau toncr decimal
	 */
	public static HashMap<String, String> getCodauToncrDecimal() {
		return codauToncrDecimal;
	}

	/**
	 * Gets the codau to ut f8 literal.
	 * 
	 * @return the codau to ut f8 literal
	 */
	public static HashMap<String, String> getCodauToUTF8Literal() {
		return codauToUTF8Literal;
	}

	// //<?
	// //function RemoveSign($str)
	// //{
	// $coDau=array("à","á","ạ","ả","ã","â","ầ","ấ","ậ","ẩ","ẫ","ă",
	// "ằ","ắ","ặ","ẳ","ẵ",
	// "è","é","ẹ","ẻ","ẽ","ê","ề" ,"ế","ệ","ể","ễ",
	// "ì","í","ị","ỉ","ĩ",
	// "ò","ó","ọ","ỏ","õ","ô","ồ","ố","ộ","ổ","ỗ","ơ"
	// ,"ờ","ớ","ợ","ở","ỡ",
	// "ù","ú","ụ","ủ","ũ","ư","ừ","ứ","ự","ử","ữ",
	// "ỳ","ý","ỵ","ỷ","ỹ",
	// "đ",
	// "À","Á","Ạ","Ả","Ã","Â","Ầ","Ấ","Ậ","Ẩ","Ẫ","Ă"
	// ,"Ằ","Ắ","Ặ","Ẳ","Ẵ",
	// "È","É","Ẹ","Ẻ","Ẽ","Ê","Ề","Ế","Ệ","Ể","Ễ",
	// "Ì","Í","Ị","Ỉ","Ĩ",
	// "Ò","Ó","Ọ","Ỏ","Õ","Ô","Ồ","Ố","Ộ","Ổ","Ỗ","Ơ"
	// ,"Ờ","Ớ","Ợ","Ở","Ỡ",
	// "Ù","Ú","Ụ","Ủ","Ũ","Ư","Ừ","Ứ","Ự","Ử","Ữ",
	// "Ỳ","Ý","Ỵ","Ỷ","Ỹ",
	// "Đ","ê","ù","à");
	// //
	// $khongDau=array("a","a","a","a","a","a","a","a","a","a","a"
	// ,"a","a","a","a","a","a",
	// "e","e","e","e","e","e","e","e","e","e","e",
	// "i","i","i","i","i",
	// "o","o","o","o","o","o","o","o","o","o","o","o"
	// ,"o","o","o","o","o",
	// "u","u","u","u","u","u","u","u","u","u","u",
	// "y","y","y","y","y",
	// "d",
	// "A","A","A","A","A","A","A","A","A","A","A","A"
	// ,"A","A","A","A","A",
	// "E","E","E","E","E","E","E","E","E","E","E",
	// "I","I","I","I","I",
	// "O","O","O","O","O","O","O","O","O","O","O","O"
	// ,"O","O","O","O","O",
	// "U","U","U","U","U","U","U","U","U","U","U",
	// "Y","Y","Y","Y","Y",
	// "D","e","u","a");
	// return str_replace($coDau,$khongDau,$str);
	// }
	// ?>
	// Reply
	/*public static void main(String[] args) {
		init();
		String s = Unicode2English.removeDau("Đinh á à");
		System.out.println(s);
	}*/
}
