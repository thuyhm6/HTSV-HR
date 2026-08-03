package com.ait.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 下载Txt公用类
 * @author JackYu
 *
 */
public class DownloadToTxtUtil {
 //LOG定义
 private static final Log LOG=LogFactory.getLog(DownloadToTxtUtil.class);
 //下载文件编码
 private static final String FILE_DOWNLOAD_ENCODING="UTF-8";
 
 /**
  * 功能：TXT文件下载
  * 
  * @param data 下载字符串内容
  * @param response  对象
  * @param fileName 文件名称
  * @param 下载文件编码
  */
 public static void writeTxtToResponse(String data,
   HttpServletResponse response, String fileName) {
  writeTxtToResponse(data, response, fileName,FILE_DOWNLOAD_ENCODING);
 }
 
 /**
  * 功能：下载TXT文档 可指定下载编码方式
  * 
  * @param data 下载字符串内容
  * @param response 对象
  * @param fileName 文件名称
  * @param downloadEncoding 下载文件编码
  */
 public static void writeTxtToResponse(String data,
   HttpServletResponse response, String fileName,
   String downloadEncoding) {
	 OutputStream out = null;
	 try {
		   out = response.getOutputStream();
		   response.reset();
		   
		   response.setContentType("text/html; charset="+ FILE_DOWNLOAD_ENCODING);
		   String filename2 = fileName + ".txt";
		
		   //response.setHeader(......)这个设置是下载文件的名称为中文,不乱码
		   response.setHeader("Content-disposition", "attachment; filename="+ new String(filename2.getBytes("gb2312"), "iso8859-1"));
		   out.write(data.getBytes(downloadEncoding));
		   //out.clear();
		   //out = pageContext.pushBody();
	  } catch (Exception e) {
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   PrintStream ps = new PrintStream(baos);
		   e.printStackTrace(ps);
		   LOG.error("response writer error!");
		   LOG.error(new String(baos.toByteArray()));
		   // close stream
		   try {
		    baos.close();
		    ps.close();
		   } catch (IOException ex) {
		    ex.printStackTrace();
		   }
	  } finally {
		   try {
			   if (out != null){
				   out.flush();
			   }
		   } catch (Exception e) {
			   ByteArrayOutputStream baos = new ByteArrayOutputStream();
			   PrintStream ps = new PrintStream(baos);
			   e.printStackTrace(ps);
			   LOG.error("response  flush error!");
			   LOG.error(new String(baos.toByteArray()));
			   // close stream
			    try {
			    	baos.close();
			    	ps.close();
			    } catch (IOException ex) {
			    	ex.printStackTrace();
			    }
		   }
	  }
  }
 
}