package com.ait.web.task;

import java.io.File;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ait.ar.action.attendanceMintenance.ArMacMasterCtroller;
import com.ait.ar.dao.ArReadCardDao;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.IpUtil;
import com.ait.web.util.ReadFile;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ReadCardTask.java
 * @Description:
 * @Create date: 2012-7-13 上午11:14:16
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Component
public class ReadCardTask {
	Logger logger = Logger.getLogger(ReadCardTask.class);
	
	@Autowired
	ArMacMasterCtroller arMacMasterCtroller;
	
	@Autowired
	private ArReadCardDao arReadCardDao;
	
	/**
	* 每分钟一次的定时任务.
	 * @throws com.ait.web.config.ConfigurationException 
	*/
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
	
	/*   *******************标准考勤机--------begin-------定时任务执行*********************    */
	/**
	* 考勤机的定时执行任务计划-----(所有使用标准考勤机的法人都可以使用)
	* ------------------自动读取还未读取过的---------人事数据-------------------
	* 
	*/
//	@Scheduled(cron = "0 0/5 6-23 * * ?")
	public void refreshArHrmMaster() {
		//只有36服务器才能每天自动生成ar考勤机的人事、刷卡数据、部门数据
		String ip = "10.231.221.36";
		try {
			ip = IpUtil.getRealIp();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		//增加法人的时候只需在字符串数组里添加法人ID即可
		String cpnys[] = {"C01","C02","C03","C04","C05","C11","C12","C13"};
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36、39服务器才能每天自动执行接口
		if("192.168.10.23".equals(ip) || "192.168.10.15".equals(ip)){
		//if("192.168.10.23".equals(ip)){
			String cpny_id = "C13";
			for(int i=0;i<cpnys.length;i++){
				cpny_id = cpnys[i];
				arMacMasterCtroller.doArHrmMasterInfo(cpny_id);
			}
		}
	}

	/**
	* 考勤机的定时执行任务计划-----(所有使用标准考勤机的法人都可以使用)
	* ------------------自动读取还未读取过的---------刷卡数据-------------------
	* 
	*/
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void refreshArMacRecord() {
		//只有36服务器才能每天自动生成ar考勤机的人事、刷卡数据、部门数据
		String ip = "10.231.221.36";
		try {
			ip = IpUtil.getRealIp();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		//增加法人的时候只需在字符串数组里添加法人ID即可
		String cpnys[] = {"C01","C02","C03","C04","C05","C11","C12","C13"};
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36、39服务器才能每天自动执行接口
		if("192.168.10.23".equals(ip) || "192.168.10.15".equals(ip)){
		//if("192.168.10.23".equals(ip)){
			String cpny_id = "C13";
			for(int i=0;i<cpnys.length;i++){
				cpny_id = cpnys[i];
				arMacMasterCtroller.doArCardRecordInfo(cpny_id);
			}
		}
	}
	
	/**
	* 考勤机的定时执行任务计划-----(所有使用标准考勤机的法人都可以使用)
	* ------------------自动读取还未读取过的---------部门数据-------------------
	* 
	*/
//	@Scheduled(cron = "0 0/5 * * * ?")
	public void refreshArDeptMaster() {
		//只有36服务器才能每天自动生成ar考勤机的人事、刷卡数据、部门数据
		String ip = "10.231.221.36";
		try {
			ip = IpUtil.getRealIp();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		//增加法人的时候只需在字符串数组里添加法人ID即可
		String cpnys[] = {"C01","C02","C03","C04","C05","C11","C12","C13"};
		//39开发服务器以后定时任务暂不执行，只执行36一个正式服务器
		//39服务器IP地址是：192.168.10.15（开发服务器）
		//36服务器IP地址是：192.168.10.23（正式服务器）
		//37服务器IP地址是：192.168.10.13（正式服务器）
		//只有36、39服务器才能每天自动执行接口
		if("192.168.10.23".equals(ip) || "192.168.10.15".equals(ip)){
		//if("192.168.10.23".equals(ip)){
			String cpny_id = "C13";
			for(int i=0;i<cpnys.length;i++){
				cpny_id = cpnys[i];
				arMacMasterCtroller.doArDeptMasterInfo(cpny_id);
			}
		}
	}
	/*   *******************标准考勤机--------end-------定时任务执行*********************    */
	
	
	
	//-----------begin-----------不使用标准考勤机的法人，自动读取刷卡数据任务---------begin-----------
	//C11定时读取刷卡数据程序
//	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0,30,0 4,10,14 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void refreshC11AttData() {
		//自动读取
		logger.debug("开始更新数据......" + new Date()+"=========C11==========");
		//文件目录
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		String cardFilePath = "";
		String server = "";
		String username = "";
		String password = "";
		String ftpPath = "";
		try {
			server = config.getString("ar.ftp.card.server");
			username = config.getString("ar.ftp.card.username");
			password = config.getString("ar.ftp.card.password");
			ftpPath = config.getString("ar.ftp.card.path");
			cardFilePath = config.getString("ar.local.card.path");
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		//从ftp读取文件
		ReadFile.downloadFtpFiles(server,username,password,ftpPath,cardFilePath);
		List readTxtFileName = ReadFile.readTxtFileName(cardFilePath);
		//循环文件名 拼接路径
		if(readTxtFileName != null){
			for(int z=0;z<readTxtFileName.size();z++){
				//文件绝对路径
				String fileFullPath = cardFilePath + readTxtFileName.get(z);
				boolean flag = true;
				List params = new ArrayList();
				try {
					//List中存放map map中以PARAM_i 的形式存放数据
					//重新组装 加入其它参数
					List paramList = ReadFile.readTxtFileForMap(fileFullPath);
					if(paramList != null){
						for(int i=0;i<paramList.size();i++){
							Map param = new LinkedHashMap();
							param = (LinkedHashMap)paramList.get(i);
							if(param.get("PARAM_0") != null && param.get("PARAM_1") != null){
								param.put("PERSON_ID", param.get("PARAM_0"));
								param.put("R_TIME", param.get("PARAM_1"));
								param.put("DOOR_TYPE", param.get("PARAM_2")!=null?param.get("PARAM_2").toString():"");
								param.put("CPNY_ID", "C11");
								param.put("CREATED_BY", "AUTO");
								params.add(param);
							}
						}
					}
					arReadCardDao.insertMacRecordList(params);
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				}
				//如果读取成功 则删除文件
				if(flag){	
					File file = new File(fileFullPath);
					file.delete();
				}
			}
		}
	}
	
	//C12定时读取刷卡数据程序
//	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0,30,0 4,10,14 * * ?")
	//@Scheduled(cron = "0 0/5 * * * ?" )  //每分钟 都执行。（测试用）
	public void refreshC12AttData() {
		//自动读取
		logger.debug("开始更新数据......" + new Date()+"========C12===========");
		//文件目录
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		String cardFilePath = "";
		String server = "";
		String username = "";
		String password = "";
		String ftpPath = "";
		try {
			server = config.getString("ar.ftp.c12.card.server");
			username = config.getString("ar.ftp.c12.card.username");
			password = config.getString("ar.ftp.c12.card.password");
			ftpPath = config.getString("ar.ftp.c12.card.path");
			cardFilePath = config.getString("ar.local.c12.card.path");					
			
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		//从ftp读取文件
		ReadFile.downloadFtpFiles(server,username,password,ftpPath,cardFilePath);
		List readTxtFileName = ReadFile.readTxtFileName(cardFilePath);
		//循环文件名 拼接路径
		if(readTxtFileName != null){
			for(int z=0;z<readTxtFileName.size();z++){
				//文件绝对路径
				String fileFullPath = cardFilePath + readTxtFileName.get(z);
				boolean flag = true;
				List params = new ArrayList();
				try {
					//List中存放map map中以PARAM_i 的形式存放数据
					//重新组装 加入其它参数
					List paramList = ReadFile.readTxtFileForMap(fileFullPath);
					if(paramList != null){
						for(int i=0;i<paramList.size();i++){
							Map param = new LinkedHashMap();
							param = (LinkedHashMap)paramList.get(i);
							if(param.get("PARAM_0") != null && param.get("PARAM_1") != null){
								param.put("PERSON_ID", param.get("PARAM_0"));
								param.put("R_TIME", param.get("PARAM_1"));
								param.put("DOOR_TYPE", param.get("PARAM_2")!=null?param.get("PARAM_2").toString():"");
								param.put("CPNY_ID", "C12");
								param.put("CREATED_BY", "AUTO");
								params.add(param);
							}
						}
					}
					arReadCardDao.insertC12MacRecordList(params);
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				}
				//如果读取成功 则删除文件
				if(flag){	
					File file = new File(fileFullPath);
					file.delete();
				}
			}
		}
	}
	
	
	//C13定时读取刷卡数据程序
//	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0,30,0 4,10,14 * * ?")
	public void refreshDataC13() {
		//自动读取
		logger.debug("C13.....开始更新数据......" + new Date()+"========C13===========");
		//文件目录
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		String cardFilePath = "";
		String server = "";
		String username = "";
		String password = "";
		String ftpPath = "";
		try {
			server = config.getString("ar.ftp.c13.card.server");
			username = config.getString("ar.ftp.c13.card.username");
			password = config.getString("ar.ftp.c13.card.password");
			ftpPath = config.getString("ar.ftp.c13.card.path");
			cardFilePath = config.getString("ar.local.c13.card.path");
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		//从ftp读取文件
		ReadFile.downloadFtpFiles(server,username,password,ftpPath,cardFilePath);
		List readTxtFileName = ReadFile.readTxtFileName(cardFilePath);
		//循环文件名 拼接路径
		if(readTxtFileName != null){
			for(int z=0;z<readTxtFileName.size();z++){
				//文件绝对路径
				String fileFullPath = cardFilePath + readTxtFileName.get(z);
				boolean flag = true;
				List params = new ArrayList();
				try {
					//List中存放map map中以PARAM_i 的形式存放数据
					//重新组装 加入其它参数
					List paramList = ReadFile.readTxtFileForMap(fileFullPath);
					if(paramList != null){
						for(int i=0;i<paramList.size();i++){
							Map param = new LinkedHashMap();
							param = (LinkedHashMap)paramList.get(i);
							if(param.get("PARAM_0") != null && param.get("PARAM_1") != null){
								param.put("PERSON_ID", param.get("PARAM_0"));
								param.put("R_TIME", param.get("PARAM_1"));
								param.put("DOOR_TYPE", param.get("PARAM_2")!=null?param.get("PARAM_2").toString():"");
								param.put("CPNY_ID", "C13");
								param.put("CREATED_BY", "AUTO");
								params.add(param);
							}
						}
					}
					arReadCardDao.insertC13MacRecordList(params);
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				}
				//如果读取成功 则删除文件
				if(flag){	
					File file = new File(fileFullPath);
					file.delete();
				}
			}
		}
	}
	//----------------------不使用标准考勤机的法人，自动读取刷卡数据任务---------begin-----------
}
