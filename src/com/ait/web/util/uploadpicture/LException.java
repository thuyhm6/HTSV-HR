package com.ait.web.util.uploadpicture;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpInfoCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
public class LException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Throwable rootCause;

	private boolean isFirst;

	public LException() {
		isFirst = true;
	}

	public LException(String s) {
		super(s);
		isFirst = true;
	}

	public LException(String s, Throwable throwable) {
		super(s);
		isFirst = true;
		rootCause = throwable;
		isFirst = false;
	}

	public LException(Throwable throwable) {
		this();
		rootCause = throwable;
		isFirst = false;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public String getStackTraceString() {
		StringWriter stringwriter = new StringWriter();
		printStackTrace(new PrintWriter(stringwriter));
		return stringwriter.toString();
	}

	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	@Override
	public void printStackTrace(PrintStream printstream) {
		synchronized (printstream) {
			super.printStackTrace(printstream);
			if (rootCause != null)
				rootCause.printStackTrace(printstream);
			if (isFirst || !(rootCause instanceof LException))
				printstream.println("-----------------------------");
		}
	}

	@Override
	public void printStackTrace(PrintWriter printwriter) {
		synchronized (printwriter) {
			super.printStackTrace(printwriter);
			if (rootCause != null)
				rootCause.printStackTrace(printwriter);
			if (isFirst || !(rootCause instanceof LException))
				printwriter.println("-----------------------------");
		}
	}
}
