package com.ait.test;

/**
 * This <code>UploadFileSample</code> class 
 * This sample demonstrates how to upload a file via FTPs
 * This sample uses a convenient method, for a Spring-able version of this refer to FTPsClient
 * @see com.zehon.ftp.FTPsClient
 * Please refer to http://www.zehon.com/features_ftps.htm for more information about our FTPs.
 * @author Zehon Team (we're happy to serve you!)  <a href="http://www.zehon.com/">http://www.zehon.com/</a>
 * 
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.ait.web.config.ConfigurationException;
import com.ait.web.util.FileAnsiToUTF8;
import com.ait.web.util.UserConfiguration;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftp.FTP;
@SuppressWarnings("unused")
public class FtpUploadFileSample {
	
	@Autowired
	private FileAnsiToUTF8 fileAnsiToUTF8;	
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
	private String temp;//临时路径
	private String server = "";
	private UserConfiguration userConfig;
	private static final String defaultSysFile = "/system.properties";
	
	public FtpUploadFileSample( ) throws ServletException {
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		//String servicePath = "resources\\photo";
		try {
			host = config.getString("hrm.file.ip");
			username = config.getString("hrm.file.username");
			password = config.getString("hrm.file.password");
			filePath = config.getString("hrm.file.temp.path");
			portalSapFile = config.getString("hrm.file.portal.path");
			temp = config.getString("hrm.file.tosap.portal.Temp");
			
			int i = filePath.indexOf("\\");
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
	
	public FtpUploadFileSample(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		//String servicePath = "resources\\photo";
		try {
			host = config.getString("hrm.photo.ip");
			username = config.getString("hrm.photo.username");
			password = config.getString("hrm.photo.password");
			destFolder = config.getString("hrm.photo.path");
			portalFolder = config.getString("hrm.photo.portal.path");
			tempPath = config.getString("hrm.photo.temp.path");
			temp = config.getString("hrm.file.tosap.portal.Temp");
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
	
	public String upload(String cpnyID, String empID) {
		//拼接公司信息
		destFolder = destFolder + cpnyID;
		String flag = "0";
		InputStream is = null;
		InputStream is1 = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = tempPath + "\\" + cpnyID + "\\" + empID + ".jpg";
		}else{
			createFilePath = tempPath + "/" + cpnyID + "/" + empID + ".jpg";
		}
		File localFile = new File(createFilePath);
		try {
			long length = localFile.length();
			if(length <= 2097152){
				is = new BufferedInputStream(new FileInputStream(localFile));
				int status = FTP.sendFile(is, cpnyID + empID + ".jpg", destFolder, host, username, password);
				//int status = FTPs.sendFile(createFilePath, destFolder, empID + ".jpg", host, username, password);
				if(FileTransferStatus.SUCCESS == status){
					flag = "1";
				}else if(FileTransferStatus.FAILURE == status){
					flag = "-3";//picture上传失败
				}
				//上传portal文件
				if(flag.equals("1")){
					is1 = new BufferedInputStream(new FileInputStream(localFile));
					int status1 = FTP.sendFile(is1, cpnyID + empID + ".jpg", portalFolder, host, username, password);
					//int status1 = FTPs.sendFile(createFilePath, portalFolder, empID + ".jpg", host, username, password);
					if(FileTransferStatus.SUCCESS == status1){
						flag = "1";
					}else if(FileTransferStatus.FAILURE == status1){
						flag = "-4";//portal上传失败
					}
				}
			}else{
				flag = "-5";//文件过大
			}
			//上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	
	public String upload(String txtname) {
		String flag = "0";
		InputStream is = null;
		InputStream is1 = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = filePath + "\\" + txtname;
		}else{
			createFilePath = filePath +"/" + txtname;
		}
		File localFile = new File(createFilePath);
		try {
			//上传portal文件
			is1 = new BufferedInputStream(new FileInputStream(localFile));
	        int status1 = FTP.sendFile(is1, txtname, portalSapFile, host, username, password);
			if(FileTransferStatus.SUCCESS == status1){
				flag = "1";
			}else if(FileTransferStatus.FAILURE == status1){
				flag = "-4";//portal上传失败
			}
			//上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	
	//C01的SAP、MD上传专用
	public String uploadC01(String txtname,String sapPath) throws ConfigurationException {
		String flag = "0";
		InputStream is = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = filePath + "\\" + txtname;
		}else{
			createFilePath = filePath +"/" + txtname;
		}
			
		File localFile = new File(createFilePath);
		try {
			//上传portal文件
			is = new BufferedInputStream(new FileInputStream(localFile));
	        int status1 = FTP.sendFile(is, txtname, sapPath, host, username, password);
			if(FileTransferStatus.SUCCESS == status1){
				flag = "1";
			}else if(FileTransferStatus.FAILURE == status1){
				flag = "-4";//portal上传失败
			}
			File localFile1 = new File(createFilePath);
			//上传完成 删除临时文件
			localFile1.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	
	//C11的ATT、SAP、MD、MC上传专用
	public String uploadC11(String txtname,String sapPath) throws ConfigurationException {
		String flag = "0";
		InputStream is = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = filePath + "\\" + txtname;
		}else{
			createFilePath = filePath +"/" + txtname;
		}
		File localFile = new File(createFilePath);
		try {
			//上传portal文件
			is = new BufferedInputStream(new FileInputStream(localFile));
			//临时文件备份出
			//int statusTemp = FTP.sendFile(is, txtname, temp, host, username, password);
			//传给ftp的文件
	        int status1 = FTP.sendFile(is, txtname, sapPath, host, username, password);
	        if(FileTransferStatus.SUCCESS == status1){
				flag = "1";
			}else if(FileTransferStatus.FAILURE == status1){
				flag = "-4";//portal上传失败
			}
	        //上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	
	//C12的ATT、SAP、MD、MC上传专用
	@SuppressWarnings("static-access")
	public String uploadC12(String txtname,String sapPath) throws ConfigurationException {
		String flag = "0";
		InputStream is = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = filePath + "\\" + txtname;
		}else{
			createFilePath = filePath +"/" + txtname;
		}
		File localFile = new File(createFilePath);
		//文件类型从 ANSI 转换为 UTF-8
		String fileType = fileAnsiToUTF8.getFileCharacterEnding(localFile);
		if(!fileType.equals("UTF-8")){
			try {
				fileAnsiToUTF8.ansiToUTF8(localFile);
			} catch (IOException e) {
				e.printStackTrace();
			}							
		}
		try {
			//上传portal文件
			is = new BufferedInputStream(new FileInputStream(localFile));
			//临时文件备份出
			//int statusTemp = FTP.sendFile(is, txtname, temp, host, username, password);
			//传给ftp的文件
	        int status1 = FTP.sendFile(is, txtname, sapPath, host, username, password);
	        if(FileTransferStatus.SUCCESS == status1){
				flag = "1";
			}else if(FileTransferStatus.FAILURE == status1){
				flag = "-4";//portal上传失败
			}
	        //上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	
	//C13的ATT、SAP、MD、MC上传专用
	@SuppressWarnings("static-access")
	public String uploadC13(String txtname,String sapPath) throws ConfigurationException {
		String flag = "0";
		InputStream is = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = filePath + "\\" + txtname;
		}else{
			createFilePath = filePath +"/" + txtname;
		}
		File localFile = new File(createFilePath);
		//文件类型从 ANSI 转换为 UTF-8
		String fileType = fileAnsiToUTF8.getFileCharacterEnding(localFile);
		if(!fileType.equals("UTF-8")){
			try {
				fileAnsiToUTF8.ansiToUTF8(localFile);
			} catch (IOException e) {
				e.printStackTrace();
			}							
		}
		try {
			//上传portal文件
			is = new BufferedInputStream(new FileInputStream(localFile));
			//临时文件备份出
			//int statusTemp = FTP.sendFile(is, txtname, temp, host, username, password);
			//传给ftp的文件
	        int status1 = FTP.sendFile(is, txtname, sapPath, host, username, password);
	        if(FileTransferStatus.SUCCESS == status1){
				flag = "1";
			}else if(FileTransferStatus.FAILURE == status1){
				flag = "-4";//portal上传失败
			}
	        //上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	
	public String uploadPaTemple(String txtname,String cpnyid) {
		String flag = "0";
		InputStream is = null;
		InputStream is1 = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = "\\workarea\\lotte_ss\\lotte" + "\\" + txtname;
		}else{
			createFilePath = "/workarea/lotte_ss/lotte" +"/" + txtname;
		}
		File localFile = new File(createFilePath);
		try {
			//上传portal文件
			is1 = new BufferedInputStream(new FileInputStream(localFile));
			String toPathString="/workarea/lotte_ss/lotte/resources/reportFile/"+cpnyid+"/";
	        int status1 = FTP.sendFile(is1, txtname, toPathString , host, username, password);
			if(FileTransferStatus.SUCCESS == status1){
				flag = "1";
			}else if(FileTransferStatus.FAILURE == status1){
				flag = "-4"; 
			}
			//上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileTransferException e){
			flag = "-2";//文件传输失败
			e.printStackTrace();
		}
		return flag;
	}
	public String uploadForPa(String cpnyID, String txtname) {
		 
               
		destFolder = "/workarea/lotte_ss/lotte/resources/reportFile/"+cpnyID ;
		String flag = "0";
		InputStream is = null;
		InputStream is1 = null;
		String createFilePath = "";
		if("win".equals(server)){
			createFilePath = "\\workarea\\lotte_ss\\lotte\\resources\\reportFile" + "\\" + cpnyID + "\\" + txtname + ".jasper";
		}else{
			createFilePath = "/workarea/lotte_ss/lotte/resources/reportFile" + "/" + cpnyID + "/" + txtname + ".jasper";
		}
		File localFile = new File(createFilePath);
		try {
			long length = localFile.length();
			 
				is = new BufferedInputStream(new FileInputStream(localFile));
				int status = FTP.sendFile(is, txtname+ ".jasper", destFolder, host, username, password);
				//int status = FTPs.sendFile(createFilePath, destFolder, empID + ".jpg", host, username, password);
				if(FileTransferStatus.SUCCESS == status){
					flag = "1";
				}else if(FileTransferStatus.FAILURE == status){
					flag = "-3";//picture上传失败
				}
				 
			 
			//上传完成 删除临时文件
			File localFile1 = new File(createFilePath);
			localFile1.delete();
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
