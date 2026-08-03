package com.ait.web.util;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.Date; 
import java.util.Hashtable; 
import java.util.List;
import java.util.Map; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
 

/**
 * <p>
 * Title: String utility
 * </p>
 * 
 * <p>
 * Description: do some string operation
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: AIT
 * </p>
 * 
 * @author Chengwei
 * @version 1.0
 */

public class StringUtil1 {
	private static String CHARSET_CN = "GBK";

	@SuppressWarnings("unused")
	private static String CHARSET_EN = "UTF-8";

	private static String CHARSET_ISO = "ISO-8859-1";

	protected StringUtil1() {
	}

	public static String checkNull(Object s) {
		return checkNull(s, "");
	}

	public static String checkNull(Object s, String defaultValue) {
		if (s == null) {
			return defaultValue;
		} else {
			return s.toString().trim();
		}
	}

	/**
	 * 将日期转为字符串
	 * 
	 * @param date
	 *            传入的日期参数
	 * @return 格式为2006-08-03的字符串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期按指定格式转为字符串
	 * 
	 * @param date
	 *            传入的日期参数
	 * @param format
	 *            指定的格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null)
			return "";
		else
			return new SimpleDateFormat(format).format(date);
	}
	public static String getDateFormat(String str) { 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date(); 
		try{
		date=simpleDateFormat.parse(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return formatDate(date);
	} 
	/**
	 * 将日期按指定格式转为字符串
	 * 
	 * @param date
	 *            传入的日期参数
	 * @param format
	 *            指定的格式
	 * @return
	 */
	public static String formatDate(Date date, Time time, String format) {
		String[] formats = format.split(" ");
		if (date == null)
			return "";
		else
			return new SimpleDateFormat(formats[0]).format(date) + " " + new SimpleDateFormat(formats[1]).format(time);
	}

	/**
	 * replace: this method replace the old string by the new string if and only
	 * if the old string is contained in the orgString. In that case, the old
	 * string part in the orgString will be replaced by the new string ,
	 * otherwise, the orgString will be returned without any change.
	 * 
	 * @param orgString
	 *            String
	 * @param oldString
	 *            String
	 * @param newString
	 *            String
	 * @return String
	 */
	public static String replace(String orgString, String oldString, String newString) {
		if (orgString == null) {
			return "";
		}
		int s = 0;
		int e = 0;
		int oldLen = oldString.length();
		StringBuffer result = new StringBuffer((int) (orgString.length() * 1.2));
		while ((e = orgString.indexOf(oldString, s)) >= 0) {
			result.append(orgString.substring(s, e));
			result.append(newString);
			s = e + oldLen;
		}
		result.append(orgString.substring(s));
		return result.toString();
	}

	public static String regExpReplace(String s, String reg, String to) throws RESyntaxException {
		RE re = new RE(reg);
		while (re.match(s)) {
			s = s.replaceAll(re.getParen(0), to);
		}
		return s;
	}

/*	public static Hashtable toHash(String s) {
		Hashtable<Object,Object> hash = new Hashtable<Object,Object>();
		s = regExpReplace(StringUtil.toCN(s), "\n", ",");
		String[] b = null;
		String[] a = s.split(",");
		if (a.length > 0) {
			for (int i = 0; i < a.length; i++) {
				b = regExpReplace(a[i], "	", ",").split(",");
				if (b.length > 1) {
					hash.put(b[0], b[1]);
				}
			}
		}
		return hash;
	}*/

	@SuppressWarnings("unchecked")
	public static Hashtable stringToHash(String s) {
		Hashtable hash = new Hashtable();
		int tagA = s.indexOf("\r\n");
		int tagB = s.indexOf(",");
		int tagC = s.indexOf("，");
		int tagD = s.indexOf(";");
		int tagE = s.indexOf(".");
		int tagF = s.indexOf("。");
		
		if(tagA!=-1 ){
			String[] a = s.split("\r\n");
			if (a.length > 0) {
				for (int i = 0; i < a.length; i++) {
						hash.put(i, a[i].trim());
				}
			}
		}if(tagB!=-1 ){
			String[] b = s.split(",");
			if (b.length > 0) {
				for (int i = 0; i < b.length; i++) {
						hash.put(i, b[i].trim());
				}
			}
		}if(tagC!=-1 ){
			String[] c = s.split("，");
			if (c.length > 0) {
				for (int i = 0; i < c.length; i++) {
						hash.put(i, c[i].trim());
				}
			}
		}if(tagD!=-1 ){
			String[] c = s.split(";");
			if (c.length > 0) {
				for (int i = 0; i < c.length; i++) {
						hash.put(i, c[i].trim());
				}
			}
		}if(tagE!=-1 ){
			String[] c = s.split("[.]");
			if (c.length > 0) {
				for (int i = 0; i < c.length; i++) {
						hash.put(i, c[i].trim());
				}
			}
		}if(tagF!=-1 ){
			String[] c = s.split("。");
			if (c.length > 0) {
				for (int i = 0; i < c.length; i++) {
						hash.put(i, c[i].trim());
				}
			}
		}if(tagA==-1&&tagB==-1&&tagC==-1&&tagD==-1&&tagE==-1&&tagF==-1){
			hash.put(0,s);
		}
		return hash;
	}
	
	public static String toCN(String s) {

		return checkNull(s);
	}

	public static String toCN(String s, String r) {
		// try {
		// return new String(checkNull(s, r).getBytes(CHARSET_ISO), CHARSET_CN);
		// } catch (UnsupportedEncodingException ex) {
		// return r;
		// }
		return checkNull(s, r);
	}

	public static String[] toCN(String[] s) {
		if (s == null) {
			return null;
		}
		String[] result = null;
		for (int i = 0; i < s.length; i++) {
			result[i] = toCN(checkNull(s[i]));
		}
		return result;
	}

	public static String editNbsp(String s) {
		if (checkNull(s).equals("") || s.equals("null &gt; ")) {
			return "&nbsp;";
		}
		return s;
	}

	public static String toISO(String s) {
		return toISO(s, "");
	}

	public static String toISO(String s, String r) {
		try {
			return new String(checkNull(s, r).getBytes(CHARSET_CN), CHARSET_ISO);
		} catch (UnsupportedEncodingException ex) {
			return r;
		}
	}

	/**
	 * 把字符串转换成Unicode码
	 * 
	 * @param strText
	 *            待转换的字符串
	 * @param code
	 *            转换前字符串的编码，如"GBK"
	 * @return 转换后的Unicode码字符串
	 */
	public static String toUnicode(String strText, String code) throws UnsupportedEncodingException {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		strText = new String(strText.getBytes("8859_1"), code);
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			if (intAsc > 128) {
				strHex = Integer.toHexString(intAsc);
				strRet = strRet + "&#x" + strHex + ";";
			} else {
				strRet = strRet + c;
			}
		}
		return strRet;
	}

	public static String substring(String s, int len) {
		return substring(s, len, "");
	}

	public static String substring(String s, int len, String appendString) {
		s = checkNull(s, "");
		if (s.length() > len) {
			return s.substring(0, len) + appendString;
		} else {
			return s;
		}
	}

	public static String parseDate(String date) {
		String result = date;
		date = date.trim();
		if (date.indexOf("/") >= 0) {
			result = replace(date, "/", "-");
		}
		if (date.indexOf(".") >= 0) {
			result = replace(date, ".", "-");
		}
		if (result.equals(""))
			return result;

		if (result.indexOf("-") == -1) {
			result = result + "-01-01";
		} else {
			if (result.lastIndexOf("-") == result.indexOf("-"))
				result += "-01";
		}

		int firstDash = result.indexOf("-");
		int lastDash = result.lastIndexOf("-");
		String month = result.substring(firstDash + 1, lastDash);
		String day = result.substring(lastDash + 1);
		String year = result.substring(0, firstDash);
		if (month.length() < 2)
			month = "0" + month;
		if (day.length() < 2)
			day = "0" + day;
		result = year + "-" + month + "-" + day;

		return result;
	}

	/**
	 * Checks if String contains a search String
	 * 
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.isBlank(null)      = true
	 *  StringUtils.isBlank(&quot;&quot;)        = true
	 *  StringUtils.isBlank(&quot; &quot;)       = true
	 *  StringUtils.isBlank(&quot;bob&quot;)     = false
	 *  StringUtils.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since common-lang
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String parseDeptID(String deptID) {
		String v_deptid = "";
		v_deptid = deptID;
		// if(v_deptid.endsWith("0")){
		// v_deptid = v_deptid.substring(0,v_deptid.length()-1);
		// }
		// if(v_deptid.endsWith("0")){
		// v_deptid = parseDeptID(v_deptid);
		// }
		// else v_deptid +="%";
		return v_deptid;
	}

	/**
	 * Finds first index of a substring in the given source string with ignored
	 * case.
	 * 
	 * @param src
	 *            source string for examination
	 * @param subS
	 *            substring to find
	 * @return index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 */
	public static int indexOfIgnoreCase(String src, String subS) {
		return indexOfIgnoreCase(src, subS, 0);
	}

	/**
	 * Finds first index of a substring in the given source string with ignored
	 * case. This seems to be the fastest way doing this, with common string
	 * length and content (of course, with no use of Boyer-Mayer type of
	 * algorithms). Other implementations are slower: getting char array frist,
	 * lowercasing the source string, using String.regionMatch etc.
	 * 
	 * @param src
	 *            source string for examination
	 * @param subS
	 *            substring to find
	 * @param startIndex
	 *            starting index from where search begins
	 * @return index of founded substring or -1 if substring is not found
	 */
	public static int indexOfIgnoreCase(String src, String subS, int startIndex) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		int total = src.length() - sublen + 1;

		for (int i = startIndex; i < total; i++) {
			int j = 0;

			while (j < sublen) {
				char source = Character.toLowerCase(src.charAt(i + j));

				if (sub.charAt(j) != source) {
					break;
				}

				j++;
			}

			if (j == sublen) {
				return i;
			}
		}

		return -1;
	}

	/*public static List getName(String s) {
		List<Object> list = new ArrayList<Object>();
		s = regExpReplace(StringUtil.toCN(s), "\n", ",");
		String[] a = s.split(",");
		String b = "";
		if (a.length > 0) {
			for (int i = 0; i < a.length; i++) {
				b = a[i];
				b = b.replaceAll("&nbsp;", "");
				b = b.replaceAll("\u0000", "");

				list.add(b);
			}
		}

		return list;

	}
*/
	public static String changeLanguage(String param, Map mMap) {
		if (param == null)
			return "";
		param = param.replaceAll("\\(", "\\( ");
		param = param.replaceAll("\\)", " \\)");
		param = param.replaceAll("\\,", " \\, ");
		String str[] = param.trim().split(" ");
		for (int i = 0; i < str.length; i++) {
			if (str[i].indexOf("'") == -1) {
				if (mMap.get(str[i]) != null)
					str[i] = mMap.get(str[i].toUpperCase()) + "";
			}
		}
		param = "";
		for (int i = 0; i < str.length; i++) {
			param += str[i] + " ";
		}
		return param;
	}

	public static String replaceHtml(String s, String s1, String s2) {
		if (s == null || s1 == null || s2 == null)
			return "";
		int i = 0; 
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int k = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += k;
			int j;
			for (j = i; (i = s.indexOf(s1, i)) > 0; j = i) {
				stringbuffer.append(ac, j, i - j).append(ac1);
				i += k;
			}

			stringbuffer.append(ac, j, ac.length - j);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	public static final String htmlToCode(String s) {
		if (s == null) {
			return "";
		} else {
			s = replaceHtml(s, "<p>", "\n\n");
			s = replaceHtml(s, "<br>", "\r\n");
			s = replaceHtml(s, "&nbsp;&nbsp;&nbsp;&nbsp;", "\t");
			s = replaceHtml(s, "&nbsp;", "   ");
			s = replaceHtml(s, "&lt;", "<");
			s = replaceHtml(s, "&gt;", ">");
			s = replaceHtml(s, "&amp;", "&");
			return s;
		}
	}

	public static final String codeToHtml(String s) {
		if (s == null) {
			return "";
		} else {
			
			s = replaceHtml(s, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
			s = replaceHtml(s, "   ", "&nbsp;");
			s = replaceHtml(s, "<", "&lt;");
			s = replaceHtml(s, ">", "&gt;");
			s = replaceHtml(s, "&", "&amp;");
			s = replaceHtml(s, "\n\n", "<p>");
			s = replaceHtml(s, "\r\n", "<br>");
			return s;
		}
	}
	// public static String htmlToCode(String s) {
	// if (s == null) {
	// return "";
	// } else {
	// s = s.replaceAll("\n\n", "<p>");
	// s = s.replaceAll("\r\n", "<br>");
	// s = s.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
	// s = s.replaceAll(" ", "&nbsp;");
	// s = s.replaceAll("<", "&lt;");
	// s = s.replaceAll(">", "&gt;");
	// s = s.replaceAll("&", "&amp;");
	// return s;
	// }
	// }

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static final String replaceBlank(String str) {
		if (str == null) {
			return "";
		} else {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
			Matcher m = p.matcher(str);
			str = m.replaceAll(""); 
			return str;
		}
	}

}
