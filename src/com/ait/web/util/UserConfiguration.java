package com.ait.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ait.web.config.ConfigurationException;
import com.ait.web.exception.GlRuntimeException;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: UserConfiguration.java
 * @Description:
 * @Create date: 2012-2-20 下午03:50:21
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public class UserConfiguration {

	private static final Logger logger = Logger
			.getLogger(UserConfiguration.class);

	private Properties properties = null;

	private static UserConfiguration userConfig;

	/**
	 * 
	 */
	private UserConfiguration(String fileName) {
		loadProperties(fileName);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static UserConfiguration getInstance(String fileName) {
		if (userConfig != null)
			return userConfig;
		else
			return new UserConfiguration(fileName);
	}

	/**
	 * load properties file
	 * 
	 * @param fileName
	 */
	public void loadProperties(String fileName) {
		InputStream is = this.getClass().getResourceAsStream(fileName);
		try {
			properties = new Properties();

			properties.load(is);

		} catch (Exception e) {
			
			logger.error("Properties file [[" + fileName + "]] not exist");
			throw new GlRuntimeException("properties file [[" + fileName
					+ "]] not exist", e);
			

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("Close InputStream fail. " + e);
			} catch (Exception e) {
				logger.error("Close InputStream fail. " + e);
			}
		}
	}

	/**
	 * 
	 * @param key
	 * @return String
	 */
	public String getString(String key) throws ConfigurationException {

		if (properties == null)
			return null;
		Object o = properties.get(key);
		if (o == null) {
			logger.error("properties file key [[" + key + "]] not exist");
			throw new ConfigurationException("properties file key [[" + key
					+ "]] not exist");
		}
		return o.toString();
	}

	/**  
	 * 
	 * @param key
	 * @return int
	 */
	public int getInt(String key) throws ConfigurationException {
		if (properties == null)
			return 0;
		Object o = properties.get(key);
		if (o == null) {
			logger.error("properties file key [[" + key + "]] not exist");
			throw new ConfigurationException("properties file key [[" + key
					+ "]] not exist");
		}
		int i = Integer.parseInt(o.toString());
		return i;
	}

	/**
	 * 
	 * @param key
	 * @return long
	 */
	public long getLong(String key) throws ConfigurationException {
		if (properties == null)
			return 0;
		Object o = properties.get(key);
		if (o == null) {
			logger.error("properties file key [[" + key + "]] not exist");
			throw new ConfigurationException("properties file key [[" + key
					+ "]] not exist");
		}

		long l = Long.parseLong(o.toString());
		return l;
	}

	/**
	 * 
	 * @param key
	 * @return float
	 */
	public float getFloat(String key) throws ConfigurationException {
		if (properties == null)
			return 0;
		Object o = properties.get(key);
		if (o == null) {
			logger.error("properties file key [[" + key + "]] not exist");
			throw new ConfigurationException("properties file key [[" + key
					+ "]] not exist");
		}
		float f = Float.parseFloat(o.toString());
		return f;
	}

	/**
	 * 
	 * @param key
	 * @return double
	 */
	public double getDouble(String key) throws ConfigurationException {
		if (properties == null)
			return 0;
		Object o = properties.get(key);
		if (o == null) {
			logger.error("properties file key [[" + key + "]] not exist");
			throw new ConfigurationException("properties file key [[" + key
					+ "]] not exist");
		}
		double d = Double.parseDouble(o.toString());
		return d;
	}

	/**
	 * 
	 * @return Properties
	 */
	public Properties getProperties() {
		if (this.properties != null) {
			return this.properties;
		}
		return null;

	}
	
}
