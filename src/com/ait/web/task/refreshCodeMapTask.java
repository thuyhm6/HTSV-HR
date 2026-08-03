package com.ait.web.task;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.web.util.CodeUtil;

@Component
public class refreshCodeMapTask {
	
	Logger logger = Logger.getLogger(refreshCodeMapTask.class);
	
	@Autowired
	CodeUtil codeUtil ;
	
	/**
	* 每天早6,12,18点的定时任务.
	*/
 //	@Scheduled(cron = "0 0 6,12,18 * * ?")
	public void refreshCodeMap() {
		logger.debug("开始刷新codeMap任务......" + new Date());
		System.out.println("定时任务开始=====================================================================================");
		System.out.println("定时任务结束=====================================================================================");
//		codeUtil.initCode() ;
		
		/*
		 * 远程调用刷新代码
		
		try {
			URL url = new URL("http://127.0.0.1/sys/refreshCodeMap");
			URLConnection connection = url.openConnection();
			
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			
			httpConn.connect() ;
			
			//System.out.println(httpConn.getResponseMessage()) ;
			
			
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		
		 */
		
	}
}
