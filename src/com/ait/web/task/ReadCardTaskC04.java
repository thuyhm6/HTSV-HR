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
 * @fileName: ReadCardTaskC04.java
 * @Description:读取C04考勤TXT，read txt from C04
 * @Create date: 2012-10-11 上午11:14:16
 * @Create by: hj(hanjian@ait.net.cn)
 * @version 1.1
 */
@Component
public class ReadCardTaskC04 {

	Logger logger = Logger.getLogger(ReadCardTask.class);
	
	@Autowired
	private ArReadCardDao arReadCardDao;
	
	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "0 0,0,0 4,10,14 * * ?")
	//@Scheduled(cron = "0 0 0/2 * * ?")
	//@Scheduled(cron = "0 0/1 * * * ?" )
	
	public void refreshData() {
		//自动读取
		logger.debug("开始更新C04考勤数据......" + new Date()+"===================");
		
		//文件目录
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		
		String cardFilePath = "";
		String server = "";
		String username = "";
		String password = "";
		String ftpPath = "";
		try {
			server = config.getString("ar.ftp.c04.card.server");
			username = config.getString("ar.ftp.c04.card.username");
			password = config.getString("ar.ftp.c04.card.password");
			ftpPath = config.getString("ar.ftp.c04.card.path");
			cardFilePath = config.getString("ar.local.c04.card.path");			
			
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
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
							if(param.get("PARAM_0") != null && param.get("PARAM_2") != null){
								param.put("CARD_NO", param.get("PARAM_0"));
								param.put("DOOR_TYPE", param.get("PARAM_1"));
								param.put("R_TIME", param.get("PARAM_2"));
								param.put("CPNY_ID", "C04");
								param.put("CREATED_BY", "A");
								params.add(param);
							}
						}
					}
					
					arReadCardDao.insertMacRecordListC04(params);
					
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
	}
}
