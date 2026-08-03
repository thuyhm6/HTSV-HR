package com.ait.web.task;

import java.net.SocketException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.action.attendanceMintenance.ArAnnualLeaveCtroller;
import com.ait.pa.action.salary.C13SapCtroller;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.IpUtil;
import com.ait.web.util.UserConfiguration;

@Component
public class refreshC13DataTaskToSap {
	
	Logger logger = Logger.getLogger(refreshCodeMapTask.class);
	
	@Autowired
	CodeUtil codeUtil ;
	
	@Autowired
	private C13SapCtroller c13SapCtroller; 
	
	@Autowired
	private  ArAnnualLeaveCtroller arAnnualLeaveCtroller;
	
	 public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	/**
	* HR TO 销售系统
	*/
//	@Scheduled(cron = "0 0 2 * * ?")
	public void refreshDataTaskToSap() throws Exception{
		
	}
	
//	@Scheduled(cron = "0 0/2 0-23 * * ?")
	public void minuteTask()  throws Exception {
		//System.out.println("----分钟开始任务...................开始");
		
		//System.out.println("----分钟开始任务...................结束");
	}
	
	/**
	* C13 每天的Att数据
	*/
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0/30 * * * ?" )////0 30 17 ? * *
	public void refreshDataTaskToSap1() throws Exception{
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
		paramMap.put("CREATE_TYPE", "C13SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c13SapCtroller.doC13SapAtt();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* C13每天的考勤食堂 吃饭次数(C13不用此接口)
	*/
	/*@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 11 * * ?")
	public void refreshDataTaskToSap2() throws Exception{
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
		paramMap.put("CREATE_TYPE", "C13SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c13SapCtroller.doC13Arcard();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}*/

	/**
	* C13每天的传送SAP数据--EMY
	*/
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 45 22 * * ?")
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void refreshDataTaskToSap3() throws Exception{
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
		paramMap.put("CREATE_TYPE", "C13SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c13SapCtroller.doC13SapHrm();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* C13每天的传送销售系统 --EMC.txt  
	*/
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 45 23 * * ?")
	public void refreshDataTaskToSap4() throws Exception{
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
		paramMap.put("CREATE_TYPE", "C13SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c13SapCtroller.doC13EmcHrm();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
}
