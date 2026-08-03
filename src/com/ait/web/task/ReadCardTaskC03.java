package com.ait.web.task;

import java.io.File;
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
import com.ait.web.util.ReadFile;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ReadCardTaskC03.java
 * @Description:
 * @Create date: 2012-7-13 上午11:14:16
 * @Create by: hj(hanjian@ait.net.cn)
 * @version 1.1
 */
@Component
public class ReadCardTaskC03 {

	/**
	 * 定时从FTP读取txt格式文件数据存入到数据库
	 */
	Logger logger = Logger.getLogger(ReadCardTaskC03.class);
	
	@Autowired
	private ArReadCardDao arReadCardDao;

	// 定时任务执行方法
	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "0 07,08,09 19,20,21 * * ?")
	//@Scheduled(cron = "0 0/2 * * * ?")
	///@Scheduled(cron = "0 0 4 * * ?")
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
			server = config.getString("ar.ftp.c03.card.server");
			username = config.getString("ar.ftp.c03.card.username");
			password = config.getString("ar.ftp.c03.card.password");
			ftpPath = config.getString("ar.ftp.c03.card.path");
			cardFilePath = config.getString("ar.local.c03.card.path");			
			
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
		
		
		ReadFile.downloadFtpFiles(server,username,password,ftpPath,cardFilePath);
		//ReadFileTxt.downloadFtpFiles();// 从FTP服务器开始读取TXT文件
		logger.debug("C03从FTP服务器读取TXT文本成功！" + new Date());
		
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
							if(param.get("PARAM_1") != null && param.get("PARAM_2") != null){
								param.put("ID_CARD_NO", param.get("PARAM_1"));
								param.put("R_TIME_IN",  String.valueOf(param.get("PARAM_2")) + String.valueOf(param.get("PARAM_4")));
								param.put("R_TIME_OUT", String.valueOf(param.get("PARAM_2")) + String.valueOf(param.get("PARAM_5")));
								params.add(param);
							}
						}
					}
					
					arReadCardDao.insertMacRecordListC03(params);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
		
		
		
/*		// 循环文件名 拼接路径
		if (readTxtFileName != null) {
			for (int z = 0; z < readTxtFileName.size(); z++) {
				// 文件绝对路径
				String fileFullPath = cardFilePath + readTxtFileName.get(z);
				switch (z) {
				case 1:
					List paramSSHR_1001List = ReadFileTxt
							.readSSHR_1001TxtFile(fileFullPath);
					ReadFileTxt.saveSSHR_1001Txt(paramSSHR_1001List);
				case 2:
					List paramSSHR_1002List = ReadFileTxt
							.readSSHR_1002TxtFile(fileFullPath);
					ReadFileTxt.saveSSHR_1002Txt(paramSSHR_1002List);
				case 3:
					List paramSSHR_1003List = ReadFileTxt
							.readSSHR_1003TxtFile(fileFullPath);
					ReadFileTxt.saveSSHR_1003Txt(paramSSHR_1003List);
				}
			}
		}*/

	}

}
