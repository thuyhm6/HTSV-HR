package com.ait.web.i18n;

import java.util.Locale;
import java.util.Vector;
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
public class MessageSource {

	private Vector args = null;

	private Message msgInstance = null;

	/**
	 * structure Message Source
	 * 
	 * @throws GlRuntimeException
	 */
	public MessageSource() throws GlRuntimeException {
		initialize("", null, "");
	}

	/**
	 * structure Message Source
	 * 
	 * @param module
	 * @throws GlRuntimeException
	 */
	public MessageSource(String module) throws GlRuntimeException {
		initialize(module, null, "");
	}

	/**
	 * structure Message Source
	 * 
	 * @param locale
	 * @throws GlRuntimeException
	 */
	public MessageSource(Locale locale) throws GlRuntimeException {
		initialize("", locale, "");
	}

	/**
	 * structure Message Source
	 * 
	 * @param module
	 * @param locale
	 * @throws GlRuntimeException
	 */
	public MessageSource(String module, Locale locale) throws GlRuntimeException {
		initialize(module, locale, "");
	}

	/**
	 * structure Message Source
	 * 
	 * @param locale
	 * @param charsetName
	 * @throws GlRuntimeException
	 */
	public MessageSource(Locale locale, String charsetName) throws GlRuntimeException {
		initialize("", locale, charsetName);
	}

	/**
	 * structure Message Source
	 * 
	 * @param module
	 * @param locale
	 * @param charsetName
	 * @throws GlRuntimeException
	 */
	public MessageSource(String module, Locale locale, String charsetName) throws GlRuntimeException {
		initialize(module, locale, charsetName);
	}

	/**
	 * get international message 
	 * 
	 * @param code
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code) throws GlRuntimeException {
		return parseMessage(msgInstance.getMessage(code));
	}

	/**
	 * get international message 
	 * 
	 * @param code
	 * @param arg1
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code, String arg1) throws GlRuntimeException {
		args = new Vector(1);
		setArg(arg1);
		return parseMessage(msgInstance.getMessage(code));
	}

	/**
	 * get international message 
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code, String arg1, String arg2) throws GlRuntimeException {
		args = new Vector(2);
		setArg(arg1);
		setArg(arg2);
		return parseMessage(msgInstance.getMessage(code));
	}

	/**
	 * get international message 
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code, String arg1, String arg2, String arg3) throws GlRuntimeException {
		args = new Vector(3);
		setArg(arg1);
		setArg(arg2);
		setArg(arg3);
		return parseMessage(msgInstance.getMessage(code));
	}

	/**
	 * get international message 
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code, String arg1, String arg2, String arg3, String arg4) throws GlRuntimeException {
		args = new Vector(4);
		setArg(arg1);
		setArg(arg2);
		setArg(arg3);
		setArg(arg4);

		return parseMessage(msgInstance.getMessage(code));
	}

	/**
	 * get international message 
	 * 
	 * @param code
	 * @param params
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code, Vector params) throws GlRuntimeException {
		args = params;

		return parseMessage(msgInstance.getMessage(code));
	}

	/**
	 * parse message 
	 * 
	 * @param msg
	 * @return
	 * @throws GlRuntimeException
	 */
	private String parseMessage(String msg) throws GlRuntimeException {
		int numOfArgInFile = 0;
		int numOfArgInSource = 0;
		String remainder = msg;

		if (msg == null) {
			return "";
		}

		// get number or argument in resource file
		String temp = msg.replaceAll("@", "");
		numOfArgInFile = msg.length() - temp.length();

		// get number of argument in source
		if (args != null) {
			numOfArgInSource = args.size();
		}

		// validate argument number
		if (numOfArgInFile != numOfArgInSource) {

			Logger.getLogger(getClass()).info("numOfArgInFile: " + numOfArgInFile);
			Logger.getLogger(getClass()).info("numOfArgInSource: " + numOfArgInSource);
			Logger.getLogger(getClass()).error("International message [" + msg + "] parameter setting error!");
			throw new GlRuntimeException("International message [" + msg + "] parameter setting error!");
		}
		StringBuffer content = new StringBuffer();
		while (msg.length() > 0) {

			int position = msg.indexOf("@");

			if (position == -1) {
				content.append(msg);
				break;
			}

			if (position != 0) {
				content.append(msg.substring(0, position));
			}

			remainder = msg.substring(position + 1);

			String value = (String) args.firstElement();

			args.removeElement(value);

			content.append(value);

			msg = remainder;
		}

		return content.toString();
	}

	/**
	 * add argument
	 * 
	 * @param value
	 */
	private void setArg(String value) {
		if (value == null) {
			value = "";
		}
		args.addElement(value);
	}

	/**
	 * change Locale
	 * 
	 * @param locale
	 */
	public void changeLocale(Locale locale) {
		msgInstance.changeLocale(locale);
	}

	/**
	 * change Locale
	 * 
	 * @param locale
	 * @param charsetName
	 */
	public void changeLocale(Locale locale, String charsetName) {
		msgInstance.changeLocale(locale, charsetName);
	}

	/**
	 * change Module
	 * 
	 * @param module
	 */
	public void changeModule(String module) {
		msgInstance.changeModule(module);
	}

	/**
	 * initialize setting
	 * @param module
	 * @param locale
	 * @param charsetName
	 * @throws GlRuntimeException
	 */
	public void initialize(String module, Locale locale, String charsetName) throws GlRuntimeException {
		try {
			msgInstance = new MessageFile();
			msgInstance.initialize(module, locale, charsetName);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Error occurred while doing message operation. " + e);
			throw new GlRuntimeException("Error occurred while doing message operation", e);
		}
	}
}