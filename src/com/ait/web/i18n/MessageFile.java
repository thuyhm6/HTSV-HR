package com.ait.web.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright: AIT (c) Company: AIT
 * 
 * @author Administrator (wangliwei@ait.net.cn)
 * @Date 2007-8-29 下午11:01:40
 * @version 1.0
 * 
 */
public class MessageFile implements Message {

	public ResourceBundle resourceBundle;

	public ManageType typeInstance = null;

	private UserConfiguration userConfig;

	private static final String defaultSysFile = "/system.properties";

	/**
	 * Đọc file messages_*.properties theo UTF-8 (Java 8 mặc định đọc ISO-8859-1 làm lỗi font tiếng Việt/Hàn/Trung).
	 * Properties.load(Reader) vẫn hiểu các ký tự viết dạng escape unicode nếu có.
	 */
	private static final ResourceBundle.Control UTF8_CONTROL = new ResourceBundle.Control() {
		@Override
		public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
				throws IllegalAccessException, InstantiationException, IOException {
			if (!"java.properties".equals(format)) {
				return super.newBundle(baseName, locale, format, loader, reload);
			}
			String resourceName = toResourceName(toBundleName(baseName, locale), "properties");
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream == null) {
				return null;
			}
			try {
				return new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
			} finally {
				stream.close();
			}
		}
	};

	/**
	 * structure message based on file
	 * 
	 * @throws GlRuntimeException
	 */
	public MessageFile() throws GlRuntimeException {

		typeInstance = new LangType();
		userConfig = UserConfiguration.getInstance(defaultSysFile);
	}

	/**
	 * structure message based on file
	 * 
	 * @param sysFile
	 * @throws GlRuntimeException
	 */
	public MessageFile(String sysFile) throws GlRuntimeException {

		typeInstance = new LangType();
		userConfig = UserConfiguration.getInstance(sysFile);
	}

	/**
	 * initialize
	 * 
	 * @param module
	 * @param locale
	 * @param charsetName
	 * @throws GlRuntimeException
	 */
	public void initialize(String module, Locale locale, String charsetName) throws GlRuntimeException {
		typeInstance.initialize(module, locale, charsetName);
	}

	/**
	 * get international message
	 * 
	 * @param code
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getMessage(String code) throws GlRuntimeException {

		String message = null;
		getResourceBundle();

		try {

			if (!typeInstance.getCharset().equals("")) {
				/*message = new String(resourceBundle.getString(code).getBytes("iso-8859-1"), typeInstance.getCharset());
				message = new String(resourceBundle.getString(code).getBytes("ISO-8859-1"), typeInstance.getCharset());
				message = new String(resourceBundle.getString(code).getBytes("GBK"), typeInstance.getCharset());*/
				// Bundle đã được đọc bằng UTF-8 (xem UTF8_CONTROL) nên lấy trực tiếp, không chuyển đổi byte nữa
				message = resourceBundle.getString(code);
			} else {
				message = resourceBundle.getString(code);
			}
		} catch (MissingResourceException mre) {
			Logger.getLogger(getClass()).error("Can't find such a Message Code in Message File. " + mre);
			return "";
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Can't find such a Message Code in Message File. " + e);
			return "";
		}
		return message;
	}

	public void changeLocale(Locale locale) {
		typeInstance.setLocale(locale);
	}

	public void changeLocale(Locale locale, String charsetName) {
		typeInstance.setLocale(locale);
		typeInstance.setCharset(charsetName);
	}

	public void changeModule(String module) {
		typeInstance.setModule(module);
	}

	/**
	 * get resource base name
	 * 
	 * @return
	 * @throws GlRuntimeException
	 */
	public String getBaseName() throws GlRuntimeException {
		try {
			//取国际化信息路径
			return userConfig.getString("resource.file.name");
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Fail to get resource name from system configration file. " + e.toString());
			throw new GlRuntimeException("Fail to get resource name from system configration file", e);
		}
	}

	/**
	 * get resource bundle
	 * 
	 * @return
	 * @throws GlRuntimeException
	 */
	public void getResourceBundle() throws GlRuntimeException {
		try {
			resourceBundle = ResourceBundle.getBundle(getBaseName() , typeInstance.getLocale(), UTF8_CONTROL);
		} catch (MissingResourceException mre) {

			Logger.getLogger(getClass()).error("Fail to get Resourcebundle. " + mre.toString());
			throw new GlRuntimeException("Fail to get Resourcebundle", mre);
		} catch (NullPointerException ne) {

			Logger.getLogger(getClass()).error("Fail to get Resourcebundle. " + ne.toString());
			throw new GlRuntimeException("Fail to get Resourcebundle", ne);
		} catch (Exception e) {

			Logger.getLogger(getClass()).error("Fail to get Resourcebundle. " + e.toString());
			throw new GlRuntimeException("Fail to get Resourcebundle", e);
		}
	}

	public String getAppendString(String str) {
		if (str == null || str.equals(""))
			return "";
		else
			return "_" + str;
	}
}