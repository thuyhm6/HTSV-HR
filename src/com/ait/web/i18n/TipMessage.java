package com.ait.web.i18n;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

public class TipMessage {

	/**
	 * 返回不同操作时各种提示信息
	 * 
	 * @param
	 * @param errorCode
	 *            错误代码
	 * @author Pennix
	 */
	public static String getTipMessage(String tipCode,
			HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		if (admin.getLanguage().equals("zh")) {
			admin.setLanguagePreference("zh");
			admin.setCountryPreference("CN");
		} else if (admin.getLanguage().equals("ko")) {
			admin.setLanguagePreference("ko");
			admin.setCountryPreference("KR");
			
		} else if (admin.getLanguage().equals("en")){
			
			admin.setLanguagePreference("en");
			admin.setCountryPreference("US");
		}else {
			admin.setLanguagePreference("vi");
			admin.setCountryPreference("VN");
		}

		MessageSource messageSource = new MessageSource("", admin.getLocale(),
				"UTF-8");
		String tipMsg = messageSource.getMessage("undefinederror");// 未定义的提示信息

		Logger.getLogger(TipMessage.class).debug("tipCode : " + tipCode);
		try {
			tipMsg = messageSource.getMessage(tipCode);
			// tipMsg = new
			// String(messageSource.getMessage(tipCode).getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(TipMessage.class).debug(e.toString());
			tipMsg = "";
		}
		return tipMsg;
	}

	/**
	 * 返回不同操作时各种提示信息
	 * 
	 * @param
	 * @param errorCode
	 *            错误代码
	 * @author Pennix
	 */
	public static String getTipMessage(String tipCode, String language) {

		AdminBean admin = new AdminBean();

		if (!"".equals(language) && language.equals("zh")) {
			admin.setLanguagePreference("zh");
			admin.setCountryPreference("CN");
		} else if (!"".equals(language) && language.equals("ko")) {
			admin.setLanguagePreference("ko");
			admin.setCountryPreference("KR");

		} else if (!"".equals(language) && language.equals("en")){

			admin.setLanguagePreference("en");
			admin.setCountryPreference("US");
		} else{
			admin.setLanguagePreference("vi");
			admin.setCountryPreference("VN");
		}

		MessageSource messageSource = new MessageSource("", admin.getLocale(),
				"UTF-8");
		String tipMsg = messageSource.getMessage("undefinederror");// 未定义的提示信息

		Logger.getLogger(TipMessage.class).debug("tipCode : " + tipCode);
		try {
			tipMsg = messageSource.getMessage(tipCode);
			// tipMsg = new
			// String(messageSource.getMessage(tipCode).getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(TipMessage.class).debug(e.toString());
			tipMsg = "";
		}
		return tipMsg;
	}
}
