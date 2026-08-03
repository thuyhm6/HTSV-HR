package com.ait.web.util.uploadpicture;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpInfoCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */

public class NotExistException extends LException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * MessageException constructor comment.
	 */
	public NotExistException() {
		super();
	}

	/**
	 * MessageException constructor comment.
	 * 
	 * @param s
	 *            java.lang.String
	 */
	public NotExistException(String s) {
		super(s);
	}

	/**
	 * MessageException constructor comment.
	 * 
	 * @param message
	 *            java.lang.String
	 * @param rootCause
	 *            java.lang.Throwable
	 */
	public NotExistException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	/**
	 * MessageException constructor comment.
	 * 
	 * @param rootCause
	 *            java.lang.Throwable
	 */
	public NotExistException(Throwable rootCause) {
		super(rootCause);
	}
}
