package com.ait.web.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: LGRuntimeException.java
 * @Description: 运行期异常类，须将catch的异常转化为此类，然后throw
 * @Create date: 2012-2-20 下午04:00:24
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public class LGRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 264300063347740193L;
	
	public LGRuntimeException(String message){
		super(message);
	}
	
	public LGRuntimeException(String message, Throwable rootCause) {
		super(message,rootCause);
	}
	
	public String getStackTraceString(){
		StringWriter sw = new StringWriter();
		printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	public void printStackTrace(PrintStream ps){		
		synchronized(ps){
			super.printStackTrace(ps);
		}
		
	}
	
	public void printStackTrace(PrintWriter pw){
		synchronized(pw){
			super.printStackTrace(pw);
		}
	}
}
