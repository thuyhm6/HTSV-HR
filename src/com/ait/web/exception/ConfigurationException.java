package com.ait.web.exception;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = -1672960344458038670L;

	public ConfigurationException(String msg) {
		super(msg);
	}

	public ConfigurationException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}

}
