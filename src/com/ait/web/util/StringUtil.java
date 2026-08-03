package com.ait.web.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


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
 * @author
 * @version 1.0
 */

public class StringUtil {

	protected StringUtil() {
	}

	@SuppressWarnings("unchecked")
	public static String changeLanguage(Object param, Map mMap) {
		if (param == null)
			return "";
//		param = param.toString();
		param = param.toString().replaceAll("\\(", "\\( ");
		param = param.toString().replaceAll("\\)", " \\)");
		param = param.toString().replaceAll("\\,", " \\, ");
		String str[] = param.toString().trim().split(" ");
		for (int i = 0; i < str.length; i++) {
			if (str[i].indexOf("'") == -1) {
				if (mMap.get(str[i]) != null)
					str[i] = mMap.get(str[i].toUpperCase()) + "";
			}
		}
		String returnparam = "";
		for (int i = 0; i < str.length; i++) {
			returnparam += str[i] + " ";
		}
		return returnparam;
	}

	@SuppressWarnings("unchecked")
	public static List getSplitParams(String str, String split) {
		List temp = new ArrayList();
		String[] params = str.split(split);
		for (String param : params) {
			temp.add(param);
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public static Map getAliasSplitParams(String[] str, String split) {
		Map temp = new HashMap();
		if (str == null)
			return null;
		for (String param : str) {
			String[] alias = param.split(split);
			if (alias.length > 1)
				temp.put(alias[1], alias[0]);
		}
		return temp;
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

	public static Object checknvl(Object s, int defaultValue) {
		// TODO Auto-generated method stub
		if (s == null) {
			return defaultValue;
		} else {
			return s.toString().trim();
		}
	}
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();
	}
	
	 public static void main(String[] args){  
	        Socket connect = new Socket();  
	          
	        try {  
	            connect.connect(new InetSocketAddress("118.26.25.103", 1521),100);  
	              
	            boolean res = connect.isConnected();  
	            System.out.println("" + res);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }finally{  
	            try {  
	                connect.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
}
