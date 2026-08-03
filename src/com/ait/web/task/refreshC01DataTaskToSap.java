package com.ait.web.task;

import java.net.SocketException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.action.attendanceMintenance.ArAnnualLeaveCtroller;
import com.ait.pa.action.salary.PortalCtroller;
import com.ait.report.hr.service.HrReportSer;
import com.ait.web.exception.ConfigurationException;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.IpUtil;
import com.ait.web.util.UserConfiguration;

@Component
public class refreshC01DataTaskToSap {
	
	Logger logger = Logger.getLogger(refreshCodeMapTask.class);
	
	@Autowired
	CodeUtil codeUtil ;
	
	@Autowired
	private  HrReportSer hrReportSer;
	@Autowired
	private  ArAnnualLeaveCtroller arAnnualLeaveCtroller;
	@Autowired
	private PortalCtroller portalCtroller; 
	 
	/**
	* 每分钟一次的定时任务.
	 * @throws com.ait.web.config.ConfigurationException 
	*/

	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0/5 9-19 * * ?")
	public void minuteTask() {
		//System.out.println("分钟开始任务...................开始");
		
		//System.out.println("分钟开始任务...................结束");
	}
//	@Scheduled(cron = "0 0 2 * * ?")
	public void refreshDataTaskToSap() throws Exception{

		//System.out.println("分钟开始任务...................结束");

	}
	
	/**
	* 每小时一次的定时任务.
	*/
//	@Scheduled(cron = "0 0 0/1 * * ?")
	public void hourTask() {
		//System.out.println("小时任务......开始");
		
		//System.out.println("小时任务......结束");
	}
	
	/**
	* C01的SAP定时执行任务计划
	*/
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0 2 * * ?")
	public void refreshC01TaskToSap() {
		//System.out.println("每天开始任务.........开始");
		//只有39和36服务器才能每天自动生成portal的.txt文件
		String ip = "10.231.221.36";
		try {
			ip = IpUtil.getRealIp();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("IPADDRESS", ip);
		paramMap.put("CREATED_BY", "SYS");
		paramMap.put("CREATE_TYPE", "C01SAP");
		try {
			arAnnualLeaveCtroller.addPortalIp(paramMap);
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				portalCtroller.doC01SapPortal();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			} catch (com.ait.web.config.ConfigurationException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("每天开始任务.........结束");
	}
	
	/**
	* HR TO SAP
	*/
//	@Scheduled(cron = "0 0 9 * * ?")
	public void refreshDataTaskToSap2() throws Exception{

	}

}
