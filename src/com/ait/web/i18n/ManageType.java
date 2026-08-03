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
public abstract class ManageType {

	public abstract void initialize(String module, Locale locale, String charsetName) throws GlRuntimeException;

	public abstract String getModuleName();

	public abstract Locale getLocale();

	public abstract void setLocale(Locale locale);

	public abstract void setModule(String module);

	public boolean isEmpty(String param) {
		return param == null || param.equals("");
	}

	public abstract void setCharset(String charsetName);

	public abstract String getCharset();
}