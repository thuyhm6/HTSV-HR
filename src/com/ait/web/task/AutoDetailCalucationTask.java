package com.ait.web.task;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.dao.ArDetailCalulateDao;
import com.ait.web.util.IpUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ReadCardTaskC12.java
 * @Description:
 * @Create date: 2012-7-13 上午11:14:16
 * @Create by: hj(hanjian@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unused")
@Component
public class AutoDetailCalucationTask {

	Logger logger = Logger.getLogger(ReadCardTask.class);
	
	@Autowired	
	 private ArDetailCalulateDao arDetailCalulateDao;
	
	/**
	 *  从X点到Y点之间，每N分钟一次的定时任务.
	 * @throws com.ait.web.config.ConfigurationException 
	*/
	@Scheduled(cron = "0 0/5 9-19 * * ?")
	public void minuteTask() {
		//System.out.println("分钟开始任务...................开始");
		
		//System.out.println("分钟开始任务...................结束");
	}
	
	/**
	 *  每天的N点执行一次
	 * @throws com.ait.web.config.ConfigurationException 
	*/
	@Scheduled(cron = "0 0 2 * * ?")
	public void refreshDataTaskToSap() throws Exception{
		//System.out.println("分钟开始任务...................开始");
		
		//System.out.println("分钟开始任务...................结束");
	}
	
	/**
	* 每小时一次的定时任务.
	*/
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void hourTask() {
		//System.out.println("小时任务......开始");
		
		//System.out.println("小时任务......结束");
	}
	
	//（数组里的法人）每天自动进行明细计算
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 6 * * ?")
	//@Scheduled(cron = "0 0,30,0 4,10,14 * * ?")
	//@Scheduled(cron = "0 0/1 * * * ?" )  //每分钟 都执行。（测试用）
	public void detailCalcAuto() {
		//自动读取
		logger.debug("...明细计算，自动任务开始..." + new Date()+"===================");
		//只有36服务器才能每天自动生成ar考勤机的人事、刷卡数据、部门数据
		String ip = "10.231.221.37";
		try {
			ip = IpUtil.getRealIp();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		//增加法人的时候只需在字符串数组里添加法人ID即可
		String cpnys[] = {"C01","C02","C03","C04","C05","C11","C12","C13"};
		//String cpnys[] = {"C02","C03","C04","C05","C11","C12","C13"};//C01，江苏玛特因人太多，暂不执行每天的明细计算
		//39开发服务器以后定时任务暂不执行，只执行36或者37一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36服务器才能每天自动生成portal的.txt文件
		if("192.168.10.23".equals(ip) || "192.168.10.15".equals(ip)){
			String cpny_id = "C01";
			String returnString = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Calendar date = Calendar.getInstance();
			//获取当前日期的前一天日期
			date.add(Calendar.DAY_OF_MONTH, -1);
			String dateStr = dateFormat.format(date);
			for(int i=0;i<cpnys.length;i++){
				cpny_id = cpnys[i];
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("from_date", dateStr);
				paramMap.put("to_date", dateStr);
				paramMap.put("caltype", "dept");//自动做明细计算默认以部门类型进行，所以各个法人首部门必须以为cpny_id+"1"开始
				paramMap.put("supervisorId", "");
				paramMap.put("deptid", cpny_id+"1");//部门以首部门开始（cpny_id+"1"）
				paramMap.put("sonDeptFlag", "YES");//包含子部门
				paramMap.put("interCpnyID", cpny_id);
				paramMap.put("empid", "");
				//自动以部门类型计算所有员工的明细计算，计算结果没有保存
				returnString = arDetailCalulateDao.detailCalculate(paramMap) ;
			}
		}
	}
}
