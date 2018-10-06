package com.hd.cloud.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @ClassName: StringUtil
 * @Description: string util
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午5:04:10
 *
 */
public class StringUtil {

	private final static byte B_10000000 = 128 - 256;
	private final static byte B_11000000 = 192 - 256;
	private final static byte B_11100000 = 224 - 256;
	private final static byte B_11110000 = 240 - 256;
	private final static byte B_00011100 = 28;
	private final static byte B_00000011 = 3;
	private final static byte B_00111111 = 63;
	private final static byte B_00001111 = 15;
	private final static byte B_00111100 = 60;

	/**
	 * 把 ASCII 字符的字符串转换为十六进制值 UTF16
	 *
	 * @param s
	 * @return
	 */
	public static String stringToHexInUtf16(String s) {
		char[] chars = s.toCharArray();
		String next;
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			next = Integer.toHexString((int) chars[i]);
			// Unfortunately, toHexString doesn't pad with zeroes, so we have
			// to.
			for (int j = 0; j < (4 - next.length()); j++) {
				output.append("0");
			}
			output.append(next);
		}
		return output.toString();
	}

	/**
	 * 把 ASCII 字符的字符串转换为十六进制值 UTF8
	 *
	 * @param s
	 * @return
	 */

	public static String stringToHexInUtf8(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 全角转半角
	 * 
	 * @Title: ToDBC
	 * @param:
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return String
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static String EncodeUCS2(String str) throws UnsupportedEncodingException {

		byte[] bytes = new byte[0];
		bytes = str.getBytes("UTF-16BE");
		StringBuffer reValue = new StringBuffer();
		StringBuffer tem = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			tem.delete(0, tem.length());
			tem.append(Integer.toHexString(bytes[i] & 0xFF));
			if (tem.length() == 1) {
				tem.insert(0, '0');
			}
			reValue.append(tem);
		}
		return reValue.toString().toUpperCase();
	}

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}

	/* ------------------------------------------------------------ */
	/**
	 * Test if a string is null or only has whitespace characters in it.
	 * <p>
	 * Note: uses codepoint version of {@link Character#isWhitespace(int)} to
	 * support Unicode better.
	 * 
	 * <pre>
	 *   isBlank(null)   == true
	 *   isBlank("")     == true
	 *   isBlank("\r\n") == true
	 *   isBlank("\t")   == true
	 *   isBlank("   ")  == true
	 *   isBlank("a")    == false
	 *   isBlank(".")    == false
	 *   isBlank(";\n")  == false
	 * </pre>
	 * 
	 * @param str
	 *            the string to test.
	 * @return true if string is null or only whitespace characters, false if
	 *         non-whitespace characters encountered.
	 */
	public static boolean isBlank(String str) {
		if (str == null) {
			return true;
		}
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (!Character.isWhitespace(str.codePointAt(i))) {
				// found a non-whitespace, we can stop searching now
				return false;
			}
		}
		// only whitespace
		return true;
	}

	public static String listToString(List list, String string) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i));
				sb.append(string);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: isNumeric
	 * @param:
	 * @Description: 判断字符串是否纯数字
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: isNumeric
	 * @param:
	 * @Description: 基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了
	 * @return boolean
	 */
	public static int getWordCountRegex(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}

	/**
	 * 将"null"转换成null
	 * 
	 * @param str
	 * @return
	 */
	public static String getRealNullString(String str) {
		return "null".equals(str) ? null : str;
	}

	/**
	 * 拼接两个json字符串为一个字符串(字符串+null会将null变成null字符串)
	 * 
	 * @param firstJson
	 *            第一个json
	 * @param secondJson
	 *            第二个json
	 * @return String
	 */
	public static String assembledJson(String firstJson, String secondJson) {
		if (secondJson != null) {
			secondJson = secondJson.replace("{", "");
			secondJson = secondJson.replace("}", "");
		}
		if (firstJson != null) {
			firstJson = firstJson.replace("{", "");
			firstJson = firstJson.replace("}", "");
		}

		if (firstJson != null && secondJson != null) {
			firstJson = firstJson + "," + secondJson;
		} else if (firstJson == null && secondJson != null) {
			firstJson = secondJson;
		}

		return "{" + firstJson + "}";
	}

	/**
	 * 
	 * @Title: trim @Description: 去掉字符串前后空格(包括全角和半角) @param @param
	 * str @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String trim(String str) {
		int i = str.length();
		int j = 0;
		char[] arrayOfChar = str.toCharArray();
		// \u0020 半角空格、\u3000全角空格
		while ((j < i) && (arrayOfChar[j] == '\u0020' || arrayOfChar[j] == '\u3000')) {
			j++;
		}
		while ((j < i) && (arrayOfChar[(i - 1)] == '\u0020' || arrayOfChar[(i - 1)] == '\u3000')) {
			i--;
		}
		return (j > 0) || (i < str.length()) ? str.substring(j, i) : str;
	}

	/**
	 * @author li nian jun
	 * @param pre
	 * @param source
	 * @return 如果source以pre开头，返回去除pre的的值，否则，直接返回source
	 */
	public static String removePre(String pre, String source) {
		if (StringUtils.isEmpty(pre))
			return source;
		if (StringUtils.isEmpty(source))
			return source;
		if (source.startsWith(pre)) {
			return source.substring(pre.length());
		} else {
			return source;
		}
	}

}