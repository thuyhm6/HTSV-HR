package com.ait.web.task;

import java.net.SocketException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.action.attendanceMintenance.ArAnnualLeaveCtroller;
import com.ait.pa.action.salary.C12SapCtroller;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.IpUtil;
import com.ait.web.util.UserConfiguration;

@Component
public class refreshC12DataTaskToSap {
	
	Logger logger = Logger.getLogger(refreshCodeMapTask.class);
	
	@Autowired
	CodeUtil codeUtil ;
	@Autowired
	private  ArAnnualLeaveCtroller arAnnualLeaveCtroller;
	@Autowired
	private C12SapCtroller c12SapCtroller; 
	
	 public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	/**
	* HR TO 销售系统
	*/
//	@Scheduled(cron = "0 0 2 * * ?")
	public void refreshDataTaskToSap() throws Exception{
		
	}
	
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0/2 0-23 * * ?")
	public void minuteTask()  throws Exception {
		//System.out.println("----分钟开始任务...................开始");
		
		//System.out.println("----分钟开始任务...................结束");
	}
	
	/**
	* C12 每天的Att数据
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
		paramMap.put("CREATE_TYPE", "C12SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c12SapCtroller.doC12SapAtt();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* C12每天的考勤食堂 吃饭次数,每天自动进行初始化，
	* 之后再根据初始化的数据和人事担当控制的数据进行发送食堂。
	*/
	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "0 0/3 4-23 * * ?")
//	@Scheduled(cron = "0 0 10 * * ?")
	public void initMealCountDataTask() throws Exception{
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
		paramMap.put("CREATE_TYPE", "C12SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36,39服务器才能每天自动执行自动任务
		if("192.168.10.23".equals(ip) || "192.168.10.15".equals(ip)){
			c12SapCtroller.initMealCountC12Auto();
		}
	}
	
	/**
	* C12每天的考勤食堂 吃饭次数初始化完毕之后，这里进行发送
	*/
	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "0 0/5 4-23 * * ?")
//	@Scheduled(cron = "0 0 11 * * ?")
	public void sendMealCountDataToShiTangTask() throws Exception{
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
		paramMap.put("CREATE_TYPE", "C12SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36,39服务器才能每天自动执行自动任务
		if("192.168.10.23".equals(ip) || "192.168.10.15".equals(ip)){
			try {
				c12SapCtroller.doC12Arcard();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	* C12每天的传送SAP数据
	*/
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 30 22 * * ?")
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
		paramMap.put("CREATE_TYPE", "C12SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c12SapCtroller.doC12SapHrm();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* C12每天的传送销售系统 EMC.txt  
	*/
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 30 23 * * ?")
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
		paramMap.put("CREATE_TYPE", "C12SAP");
		arAnnualLeaveCtroller.addPortalIp(paramMap);
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		//if("192.168.10.23".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.23".equals(ip)){
			try {
				c12SapCtroller.doC12EmcHrm();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
}
