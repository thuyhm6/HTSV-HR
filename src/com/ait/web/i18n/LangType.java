package com.ait.web.i18n;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.ait.web.exception.GlRuntimeException;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @author Administrator (wangliwei@ait.net.cn)
 * @Date 2007-8-29 下午11:01:40
 * @version 1.0
 * 
 */
public class LangType extends ManageType {

	private Locale locale = null;

	private String charsetName = null;

	private String module = null;

	public void initialize(String module, Locale locale, String charsetName) throws GlRuntimeException {
		if (locale == null) {

			Logger.getLogger(getClass()).error("message manage type is not valid... check your manage type and usage");
			throw new GlRuntimeException("message manage type is not valid... check your manage type and usage");
		}

		this.module = module;
		this.locale = locale;
		this.charsetName = charsetName;
	}

	public String getModuleName() {
		return module;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setCharset(String charsetName) {
		this.charsetName = charsetName;
	}

	public String getCharset() {
		return charsetName;
	}
}
