package com.ait.web.task;

import java.net.SocketException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.action.attendanceMintenance.ArAnnualLeaveCtroller;
import com.ait.web.exception.ConfigurationException;
import com.ait.web.util.IpUtil;

@Component
public class refreshAnnualTask {
	
	Logger logger = Logger.getLogger(refreshCodeMapTask.class);
	
	@Autowired
	private  ArAnnualLeaveCtroller arAnnualLeaveCtroller;
	
	/**
	* 每分钟一次的定时任务.
	*/
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0/5 9-19 * * ?")
	public void minuteTask() {
		//System.out.println("分钟开始任务...................开始");
		
		//System.out.println("分钟开始任务...................结束");
	}
	
	/**
	* 每小时一次的定时任务.
	*/
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void hourTask() {
		//System.out.println("小时开始任务......");
	}
	
	/**
	* 每天早1点的定时任务，生成Portal文件
	*/
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 4 * * ?")
	public void dayTask() {
		//System.out.println("每天开始任务.........开始");
		//只有39和36服务器才能每天自动生成portal的.txt文件
		/*String ip = "10.231.221.36";
		try {
			ip = IpUtil.getRealIp();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("IPADDRESS", ip);
		paramMap.put("CREATED_BY", "SYS");
		try {
			arAnnualLeaveCtroller.addPortalIp(paramMap);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		//只有39和36服务器才能每天自动生成portal的.txt文件
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//if("192.168.10.15".equals(ip) || "192.168.10.13".equals(ip)){
		if("192.168.10.13".equals(ip)){
			paramMap.put("MAKE_TYPE", "0");
			try {
				arAnnualLeaveCtroller.createArAnnualLeaveAuto(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		//System.out.println("每天开始任务.........结束");
	}
	
	/**
	* 
	*/
	@Scheduled(cron = "0 0 3 * * ?")
	public void dayTask2() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
