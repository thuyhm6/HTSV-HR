package com.ait.web.messages;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

public class Messages{
	public static String getMessage(HttpServletRequest request,String str){		
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ait/web/messages/messageSource.xml");	
		
		Locale locale = (Locale) WebUtils.getSessionAttribute(request, 
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		
		return ctx.getMessage(str, null, locale);
	}
	
	public static String getLanguage(HttpServletRequest request){		
		
		Locale locale = (Locale) WebUtils.getSessionAttribute(request, 
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(locale==null || locale.getLanguage() == "admin"){
			locale = request.getLocale();
		}
		
		return locale.getLanguage();
		//return "zh";
	}
}
