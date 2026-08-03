package com.ait.web.util;



import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.map.ObjectMapper;


public  class  JsonUtil {
	
	public static ObjectMapper objectMapper = new ObjectMapper();
	
	public static boolean prefixJson = false;
	
	public static JsonEncoding jsonEncoding=JsonEncoding.UTF8;
	

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}



	public static void setObjectMapper(ObjectMapper objectMapper1) {
		objectMapper = objectMapper1;
	}



	public static boolean isPrefixJson() {
		return prefixJson;
	}



	public static void setPrefixJson(boolean prefixJson1) {
		prefixJson = prefixJson1;
	}



	public static JsonEncoding getJsonEncoding() {
		return jsonEncoding;
	}



	public static void setJsonEncoding(JsonEncoding jsonEncoding1) {
		jsonEncoding = jsonEncoding1;
	}
	/**
	 * 将json字符串转换成List<LinkedHashMap<String,Object>>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<LinkedHashMap<String, Object>> getUpdateList(HttpServletRequest request)throws Exception{
		List<LinkedHashMap<String, Object>> list=objectMapper.readValue(getJsonString(request.getInputStream())
				, List.class);
		return list;
	}
	/**
	 * 将json字符串转换成List<LinkedHashMap<String,Object>>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<LinkedHashMap<String, Object>> getUpdateList(String JsonString)throws Exception{
		List<LinkedHashMap<String, Object>> list=objectMapper.readValue(JsonString
				, List.class);
		return list;
	}
	/**
	 * 将ajax传入的json对象重新utf-8编码返回
	 * @param sis 
	 * @return
	 * @throws IOException
	 */
	public static  synchronized String getJsonString(ServletInputStream sis) throws IOException {
      	byte b[] = new byte[1024];
      	int read = sis.readLine(b, 0, b.length);
      	StringBuffer line =new StringBuffer() ;
      	while(read>0)
      	{
      		line.append(new String(b, 0, read , "utf-8"));
      		read=sis.readLine(b, 0, b.length);
      	}
      	return line.toString();
      	}
	/**
	 * 将传入对象（map,list<map>）转换成json字符串
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String  writeInternal(Object o)throws Exception 
	{	String temp="";
		try
		{
			temp=objectMapper.writeValueAsString(o);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();  
		}
		return temp;
	}
}
