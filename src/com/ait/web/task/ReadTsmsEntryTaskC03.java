package com.ait.web.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.dao.ArReadCardDao;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.FileAnsiToUTF8;
import com.ait.web.util.ReadFile;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ReadTsmsEntryTaskC03.java
 * @Description:
 * @Create date: 2012-11-17 上午11:14:16
 * @Create by: hj(hanjian@ait.net.cn)
 * @version 1.1
 */
@Component
public class ReadTsmsEntryTaskC03 {

	/**
	 * 定时从FTP读取txt格式文件数据存入到数据库
	 */
	Logger logger = Logger.getLogger(ReadTsmsEntryTaskC03.class);
	
	@Autowired
	private ArReadCardDao arReadCardDao;
	@Autowired
	private FileAnsiToUTF8 fileAnsiToUTF8;	
	
	// 定时任务执行方法
	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "0 07,08,09 19,20,21 * * ?")
	//@Scheduled(cron = "0 0/1 * * * ?")
	//@Scheduled(cron = "0 0/3 9-19 * * ?")
//	@Scheduled(cron = "0 0 2 * * ?")
	public void refreshData() throws Exception {
		// 自动读取
		logger.debug("C03开始从FTP服务器读取数据......" + new Date());
		//文件目录
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		String cardFilePath = "";
		String server = "";
		String username = "";
		String password = "";
		String ftpPath = "";
		try {
			server = config.getString("ar.ftp.c03.tsms.entry.server");
			username = config.getString("ar.ftp.c03.tsms.entry.username");
			password = config.getString("ar.ftp.c03.tsms.entry.password");
			ftpPath = config.getString("ar.ftp.c03.tsms.entry.path");
			cardFilePath = config.getString("ar.local.c03.tsms.entry.path");
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
		ReadFile.downloadFtpFiles(server,username,password,ftpPath,cardFilePath);
		//ReadFileTxt.downloadFtpFiles();// 从FTP服务器开始读取TXT文件
		logger.debug("C03从FTP服务器读取TSMS TXT文本成功！" + new Date());
		List readTxtFileName = ReadFile.readTxtFileName(cardFilePath);
		//循环文件名 拼接路径
		if(readTxtFileName != null){
			for(int z=0;z<readTxtFileName.size();z++){
				//通过文件名判断是入职数据，还是离职数据
				String tempFileSubName = (readTxtFileName.get(z)).toString().substring(0,17);
				if(tempFileSubName.equals("C03TSMS_SSHR_1001")){
					//文件绝对路径
					String fileFullPath = cardFilePath + readTxtFileName.get(z);
					boolean flag = true;
					List params = new ArrayList();
					try {
						String fileType = fileAnsiToUTF8.getFileCharacterEnding(new File(fileFullPath));
						logger.debug("C03 TXT 文件编码判断......" + fileType);
						if(!fileType.equals("UTF-8")){
							logger.debug("C03 TXT 文件编码转换......");
							//文件类型从 ANSI 转换为 UTF-8
							fileAnsiToUTF8.ansiToUTF8(new File(fileFullPath));							
						}
						//List中存放map map中以PARAM_i 的形式存放数据
						//重新组装 加入其它参数
						List paramList = ReadFile.readTxtFileForMap(fileFullPath);
						if(paramList != null){
							for(int i=0;i<paramList.size();i++){	
								Map param = new LinkedHashMap();
								param = (LinkedHashMap)paramList.get(i);
								if(param.get("PARAM_0") != null ){
									param.put("IN_LOCAL_NAME", param.get("PARAM_0"));
									param.put("IN_JOIN_COMPANY_DATE", param.get("PARAM_1"));
									param.put("IN_DATE_STARTED", param.get("PARAM_1"));
									param.put("IN_DEPT_NO", param.get("PARAM_2"));
									param.put("IN_ID_CARD_NO", param.get("PARAM_3"));
									param.put("IN_POSITION_NO", param.get("PARAM_4"));
									
									logger.debug("IN_LOCAL_NAME......" + param.get("PARAM_0"));
									params.add(param);
								}
							}
						}
						arReadCardDao.insertEmpInfoListC03(params);
					} catch (Exception e) {
						e.printStackTrace();
						flag = false;
					}	
					//如果读取成功 则删除文件
					if(flag){
						File file = new File(fileFullPath);
						file.delete();
					}					
				}else{
					
				}
			}
		}	
		//循环文件名 拼接路径
		if(readTxtFileName != null){
			for(int z=0;z<readTxtFileName.size();z++){
				//通过文件名判断是入职数据，还是离职数据
				String tempFileSubName = (readTxtFileName.get(z)).toString().substring(0,17);
				if(tempFileSubName.equals("C03TSMS_SSHR_1002")){
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
								if(param.get("PARAM_1") != null && param.get("PARAM_2") != null){
									param.put("ID_CARD_NO", param.get("PARAM_1"));
									param.put("R_DATE", param.get("PARAM_2"));
									params.add(param);
								}
							}
						}
						arReadCardDao.insertEmpResignListC03(params);
					} catch (Exception e) {
						e.printStackTrace();
						flag = false;
					}		
					//如果读取成功 则删除文件
					if(flag){
						File file = new File(fileFullPath);
						file.delete();
					}					
				}else{
					
				}
			}
		}		
	}
}