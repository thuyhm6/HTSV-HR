package com.ait.web.i18n;

import java.util.Locale;
import com.ait.web.exception.GlRuntimeException;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @author Administrator (wangliwei@ait.net.cn)
 * @Date 2007-8-29 下午11:01:40
 * @version 1.0
 * 
 */
public interface Message {

	public String getMessage(String code) throws GlRuntimeException;

	public void changeLocale(Locale locale);

	public void changeLocale(Locale locale, String charsetName);

	public void changeModule(String module);

	public void initialize(String module, Locale locale, String charsetName) throws GlRuntimeException;

	//public void refresh() throws GlRuntimeException;
}