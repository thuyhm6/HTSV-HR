package com.ait.web.util.uploadpicture;


import   java.io.*;   
import   java.util.Date;   
  
import   javax.servlet.*;   
import   javax.servlet.http.*;   
import   javax.servlet.ServletConfig;   
import   javax.servlet.ServletException;   
import   javax.servlet.http.HttpServlet;   
import   javax.servlet.jsp.PageContext;

import org.testng.log4testng.Logger;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpInfoCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
public class UploadFile extends HttpServlet {
 
   protected byte m_binArray[] = null;   
   protected HttpServletRequest m_request = null;   
   protected HttpServletResponse m_response = null;   
   protected ServletContext m_application = null;
  
   private int m_totalBytes    = 0;   
   private int m_currentIndex  = 0;   
   private int m_startData     = 0;   
   private int m_endData       = 0;   
   private String m_boundary   = null;
   private String m_savePath   = "";  
   
   /**
    * 保存上传文件名
    */
   private String s_fileName  = "";
     
   public UploadFile(){
    
	    m_totalBytes   = 0;   
	    m_currentIndex = 0;   
	    m_startData    = 0;   
	    m_endData      = 0;   
	    m_boundary     = new String();   
   }   
   
  
   public final void init(ServletConfig config) throws ServletException {
    
	   m_application = config.getServletContext();   
   }   
    

   public void service (HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
    
	    m_request  = request;   
	    m_response = response;   
   }   
   
   
   public final void initialize (PageContext pageContext)throws ServletException {
    
	    m_application = pageContext.getServletContext();   
	    m_request     = (HttpServletRequest)pageContext.getRequest();   
	    m_response    = (HttpServletResponse)pageContext.getResponse();   
   }   
   
   /**
    * 启动上传文件
    * @throws IOException
    * @throws ServletException
    */
   
   public void uploadData() throws IOException, ServletException {
    
       int totalRead = 0;   
       int readBytes = 0;   
       boolean found = false;   
       String dataHeader = new String();   
       String fieldName  = new String();   
     
       Logger.getLogger(getClass()).debug("user begin upload file...");
       Logger.getLogger(getClass()).debug("user IP:"+m_request.getRemoteAddr());  
     
       java.io.FileOutputStream fileout = null;   
       boolean isFile = false;   
       m_totalBytes = m_request.getContentLength();   
       m_binArray   = new byte[m_totalBytes];   
               
        for(; totalRead < m_totalBytes; totalRead += readBytes)  
		    try{
		    	m_request.getInputStream();   
		    	readBytes = m_request.getInputStream().read(m_binArray,totalRead,m_totalBytes - totalRead);   
		    }catch(Exception e){   
		     Logger.getLogger(getClass()).debug("get Data fail,time:"+new   Date().toLocaleString());
		     Logger.getLogger(getClass()).debug(e.toString());  
		    }
   
	    System.out.println("dataSize:"+totalRead);
   
	    for(; !found && m_currentIndex < m_totalBytes; m_currentIndex++){
		     if(m_binArray[m_currentIndex] == 13){  
		      found = true;   
		     }else{   
		      m_boundary = m_boundary + (char)m_binArray[m_currentIndex];
		     }
	    }
    
	    if(m_currentIndex == 1)   
	    		return;   
	    m_currentIndex++;
   
    do{   
     if(m_currentIndex >= m_totalBytes)   
      break;
     
     dataHeader = getDataHeader();  System.out.println(dataHeader+"********"); 
     m_currentIndex = m_currentIndex + 2;   
     isFile = dataHeader.indexOf("filename") > 0;   
     fieldName = getDataFieldValue(dataHeader, "name");   
      
     getDataSection();   
      
     if(isFile){
      
         try{
          
	       fileout = new FileOutputStream("c:"+getFilename(dataHeader));   System.out.println("new filanamepath:"+getFilename(dataHeader));
	       fileout.write(m_binArray,m_startData,m_endData-m_startData+1);  
	       Logger.getLogger(getClass()).debug("writeing:"+getFilename(dataHeader)+
	  	         "successful ,time" + new Date().toLocaleString());
     
	       //保存文件名
	       this.setFileName( getFilename2(dataHeader) );
	       
         }catch(Exception e){
        	 e.printStackTrace();
        	 Logger.getLogger(getClass()).debug("writeing file fail,time:"+new   Date().toLocaleString());
        	 Logger.getLogger(getClass()).debug(e.toString());
         }   
         finally{   
	       try{
	        
	           fileout.close();   
	       }catch(Exception e){   
	    	   Logger.getLogger(getClass()).debug(e.toString());
	       }   
         }   
     } 
      
     if((char)m_binArray[m_currentIndex + 1] == '-')   
      break;   
     m_currentIndex   =   m_currentIndex   +   2;
     
     
    }while(true);
    	Logger.getLogger(getClass()).debug("用户数据上传完毕,结束时间:"+new   Date().toLocaleString());
   }   
   
   /**
    * 
    * @param dataHeader
    * @param fieldName
    * @return
    */
   private String getDataFieldValue(String dataHeader, String fieldName){
    
    String token = new String();   
    String value = new String();   
    int pos   = 0;   
    int i     = 0;   
    int start = 0;   
    int end   = 0;
    
    token = String.valueOf((new StringBuffer(String.valueOf(fieldName))).append("=").append('"'));   
    pos   = dataHeader.indexOf(token);
    
    if(pos > 0){
     
     i = pos + token.length();   
     start = i;   
     token = "\"";   
     end   = dataHeader.indexOf(token,   i);
     
     if(start > 0 && end > 0)   
      value = dataHeader.substring(start, end);   
    }
    
    return value;   
   }   
   
   /**
    * 
    * @return
    */
   private String getDataHeader(){
    
    int start = m_currentIndex;   
    int end   = 0;   
    int len   = 0;   
    boolean found = false;
    
    while (!found) 
     if(m_binArray[m_currentIndex] == 13 && m_binArray[m_currentIndex + 2] == 13){
      
      found = true;   
      end   = m_currentIndex - 1;   
      m_currentIndex =  m_currentIndex + 2;   
     }else{
      
      m_currentIndex++;   
     }
           
    String dataHeader = new String(m_binArray, start, (end - start) + 1);   
    return dataHeader;   
   }   
   
   
   private void getDataSection(){
     
    boolean found     = false;   
    String dataHeader = new String();   
    int searchPos     = m_currentIndex;   
    int keyPos        = 0;   
    int boundaryLen   = m_boundary.length();   
    m_startData       = m_currentIndex;   
    m_endData         = 0;
    
    do{   
     if(searchPos >= m_totalBytes)   
      break;
     
     if(m_binArray[searchPos]   ==   (byte)m_boundary.charAt(keyPos)){
      
      if(keyPos == boundaryLen - 1){
       
       m_endData = ((searchPos - boundaryLen) + 1) - 3;   
       break;   
      }
      
      searchPos++;   
      keyPos++;
      
     }else{   
      searchPos++;   
      keyPos = 0;   
     }   
    }while(true);
    
    m_currentIndex = m_endData + boundaryLen + 3;   
   }   
   
   
   private String getFileExt(String fileName){
    
    String value = new String();   
    int start    = 0;   
    int end      = 0;
    
    if(fileName == null)   
     return null;
    
    start = fileName.lastIndexOf(46) + 1;   
    end   = fileName.length();   
    value = fileName.substring(start, end);
    
    if(fileName.lastIndexOf(46) > 0)   
     return value;   
    else   
     return ""; 
   }   
   
   
   private String getContentType(String dataHeader){
    
    String token = new String();   
    String value = new String();   
    int start = 0;   
    int end   = 0;   
    token = "Content-Type:";   
    start = dataHeader.indexOf(token) + token.length();
    
    if(start != -1){
     
     end = dataHeader.length();   
     value = dataHeader.substring(start, end);   
    }
    
    return value; 
   }   
   
   
   private String getTypeMIME(String ContentType){
    
    String value = new String();   
    int pos = 0;   
    pos = ContentType.indexOf("/");
    
    if(pos != -1)   
     return ContentType.substring(1, pos);   
    else   
     return ContentType;
   
   }   
   
   /**
    * 
    * @param ContentType
    * @return
    */     
   private String getSubTypeMIME(String ContentType){
    
    String value = new String();   
    int start = 0;   
    int end   = 0;   
    start = ContentType.indexOf("/") + 1;
    
    if(start != -1){
     
     end = ContentType.length();   
     return ContentType.substring(start, end);   
    }else{
     
     return ContentType;   
    }   
   }
   
   /**
    * 取文件名包括路径
    * @param dataHeader
    * @return
    */
   private String getFilename(String dataHeader){
    
    String filenametemp = getDataFieldValue(dataHeader, "filename");
    System.out.println("filepathorpath:"+filenametemp);
    
    if(m_savePath.lastIndexOf("\\")!=0)   
     m_savePath+="\\";   
        
    return m_savePath + filenametemp.substring(filenametemp.lastIndexOf("\\")+1);   
   } 
   
   /**
    * 取文件名的方法不包括路径
    * @param dataHeader
    * @return
    */
   private String getFilename2(String dataHeader){
    
    String filenametemp = getDataFieldValue(dataHeader, "filename");             
    return filenametemp.substring(filenametemp.lastIndexOf("\\")+1);   
    
   }
   
   /**
    * 设置服务器文件保存地址
    * @param path
    */
   public void setSavePath(String path){
    
    m_savePath = path;   
   }
   
   /**
    * 设置文件名的公共方法
    * @param filename
    */
   public void setFileName(String filename){
    
    this.s_fileName = filename;
   }
   
   /**
    * 得到文件名的公共方法
    * @return
    */
   public String getFileName(){
    
    return this.s_fileName;
   }
   
}



