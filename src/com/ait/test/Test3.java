package com.ait.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Test3 {
	public static void main(String[] args) {
		try {
			URL url = new URL("http://127.0.0.1/sys/refreshCodeMap");
			URLConnection connection = url.openConnection();
			
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			
			httpConn.connect() ;
			
			//System.out.println(httpConn.getResponseMessage()) ;
			
			
		} catch (Exception e) {
			e.printStackTrace() ;
		}
	}

}
