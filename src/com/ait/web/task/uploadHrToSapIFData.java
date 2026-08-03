package com.ait.web.task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.web.config.ConfigurationException;
import com.ait.web.util.UserConfiguration;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftp.FTP;

@SuppressWarnings("unused")
public class uploadHrToSapIFData {

	/**
	 * Please refer to http://www.zehon.com/FTPs_samples.htm for its full source code
	 * @param args
	 */
	private String host;//ftp addr
	
	private String username;//ftp user
	
	private String password;//ftp pass
	
	private String destFolder;//ftp file addr
	
	private String portalFolder;//ftp portal file addr
	
	private String tempPath;//临时路径
	
	private String portalSapFile;//ftp portal file addr
	
	private String filePath;//临时路径
	
	private String server = "";
	
	private UserConfiguration userConfig;
	
	private static final String defaultSysFile = "/system.properties";
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	
	public void uploadHrToSapIFData(String destFolder) throws ServletException {
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		try {
			host = config.getString("hrm.photo.ip");
			username = config.getString("hrm.file.tosap.username");
			password = config.getString("hrm.file.tosap.password");
			destFolder = config.getString(destFolder);
			tempPath = config.getString("hrm.file.tosap.temp.path");
			int i = tempPath.indexOf("\\");
			if (i < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}else{
				server = "win";//windows
			}
		}catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String upload(String fileName,String ftpUrl) throws ConfigurationException {
		String portalPath = config.getString(ftpUrl);//"hrm.file.tosap.portal.ToAtt"
		//拼接公司信息
		destFolder = portalPath;
		String flag = "0";
		InputStream is = null;
		InputStream is1 = null;
		String createFilePath = "";
		createFilePath=fileName;
		createFilePath = tempPath +fileName;
		File localFile = new File(createFilePath);
		
		try {
			long length = localFile.length();
			if(length <= 2097152){
				
				is = new BufferedInputStream(new FileInputStream(localFile));
				int status = FTP.sendFile(is, fileName, destFolder, host, username, password);
				if(FileTransferStatus.SUCCESS == status){
					flag = "1";
				}
				else if(FileTransferStatus.FAILURE == status){
					flag = "-3";//picture上传失败
				}	
			}else{
				flag = "-5";//文件过大
			}
			//上传完成 删除临时文件
			//File localFile1 = new File(createFilePath);
			//localFile1.delete();	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}	
		return flag;
	}
}