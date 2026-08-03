package com.ait.web.util;

/**
 * 自定义异常(self-defined exception)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: CommonException.java
 * @Description:
 * @Create date: Feb 17, 2012 9:56:53 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 9:56:53 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 自定义异常(self-defined exception)
	 */
	public CommonException() {
		super();
	}

	/**
	 * 自定义异常(self-defined exception)
	 * 
	 * @param message
	 */
	public CommonException(String message) {
		super(message);
	}

	/**
	 * 自定义异常(self-defined exception)
	 * 
	 * @param message
	 * @param e
	 */
	public CommonException(String message, Exception e) {
		super(message, e);
	}
}