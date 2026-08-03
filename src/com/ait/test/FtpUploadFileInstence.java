package com.ait.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.ait.web.config.ConfigurationException;
import com.ait.web.util.UserConfiguration;


public class FtpUploadFileInstence {
	
	private static final Logger log = Logger.getLogger(FtpUploadFileInstence.class);

	private int retryCount;

	private FTPClient ftpClient;

	private String ftpAddr;

	private int ftpPort;

	private String userName;

	private String userPass;

	private String sourceFilePath;
	
	private String targetFilePath;

	private boolean successFlag = false;

	private String uploadStatus = "uploading";

	private StringBuilder ftpResult = new StringBuilder();

	private CountingInputStream inputStream;
	
	private String targetFilePathPortal;
	
	private String server;
	
	private String remoteServer;
	
	private String empPhotoName;
	
	private String targetFullpath;
	
	private String targetFullPortalpath;
	
	public static int FTP_MAX_RETRY_TIMES = 3;

	public FtpUploadFileInstence() {
		
		log.info("Initializing...");
		
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		
		try {
			
			this.ftpClient = new FTPClient();
			this.ftpPort = 21;
			this.retryCount = 0;
			this.ftpResult.append("Ready to do Upload");
			this.ftpAddr = config.getString("hrm.photo.ip");
			this.sourceFilePath = config.getString("hrm.photo.temp.path");
			this.targetFilePath = config.getString("hrm.photo.path");
			this.targetFilePathPortal = config.getString("hrm.photo.portal.path");
			this.userName = config.getString("hrm.photo.username");
			this.userPass = config.getString("hrm.photo.password");
			
			int i = sourceFilePath.indexOf("\\");
			
			if (i < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}else{
				server = "win";//windows
			}
			
			int j = targetFilePath.indexOf("\\");
			
			if (j < 0) { // WebServer的os为unix/linux
				remoteServer = "u/l";
			}else{
				remoteServer = "win";//windows
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setFtpAddr(String ftpAddr) {
		this.ftpAddr = ftpAddr;
		log.debug("Ftp Address Set to : " + ftpAddr);
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
		log.debug("Ftp [" + this.ftpAddr + "] Port Set to : " + ftpPort);
	}

	public void setUserName(String userName) {
		this.userName = userName;
		log.debug("Ftp [" + this.ftpAddr + "] UserName Set to : " + userName);
	}

	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
		log.debug("Ftp file source path : " + sourceFilePath);
	}
	
	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
		log.debug("Ftp file target path : " + targetFilePath);
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
		log.debug("Ftp [" + this.ftpAddr + "] Password Set to : " + userPass);
	}

	public String getFtpResult() {
		return this.ftpResult.toString();
	}

	public long getUploadedSize() {
		if (this.inputStream == null)
			return 0L;
		else
			return inputStream.getByteCount();
	}

	public String getSourceFilePath() {
		return this.sourceFilePath;
	}
	
	public String getTargetFilePath() {
		return this.targetFilePath;
	}

	public String getFtpAddr() {
		return this.ftpAddr;
	}

	public boolean isSuccess() {
		return this.successFlag;
	}

	public String getTargetFilePathPortal() {
		return this.targetFilePathPortal;
	}

	public void setTargetFilePathPortal(String targetFilePathPortal) {
		this.targetFilePathPortal = targetFilePathPortal;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getEmpPhotoName() {
		return empPhotoName;
	}

	public void setEmpPhotoName(String empPhotoName) {
		this.empPhotoName = empPhotoName;
	}

	public String getTargetFullpath() {
		return targetFullpath;
	}

	public void setTargetFullpath(String targetFullpath) {
		this.targetFullpath = targetFullpath;
	}

	public String getTargetFullPortalpath() {
		return targetFullPortalpath;
	}

	public void setTargetFullPortalpath(String targetFullPortalpath) {
		this.targetFullPortalpath = targetFullPortalpath;
	}

	public String process(String cpnyID, String empID) {

		String flag = "0";
		
		empPhotoName = empID + ".jpg";
		
		//拼接文件全路径
		if("win".equals(server)){
			sourceFilePath = sourceFilePath + "\\" + cpnyID + "\\" + empPhotoName;
		}else{
			sourceFilePath = sourceFilePath + "/" + cpnyID + "/" + empPhotoName;
		}
		
		if("win".equals(remoteServer)){
			targetFullpath = targetFilePath + "\\" + cpnyID;
			targetFullPortalpath = targetFilePathPortal + "\\" + cpnyID;
		}else{
			targetFullpath = targetFilePath + "/" + cpnyID;
			targetFullPortalpath = targetFilePathPortal + "/" + cpnyID;
		}
		
		for (this.retryCount = 1; this.retryCount <= FtpUploadFileInstence.FTP_MAX_RETRY_TIMES; ++this.retryCount){
			try {
				if (this.retryCount != 0) {
					this.ftpResult.append("\n Upload file retry " + retryCount + " time.");
				}
				
				flag = this.doUpload(cpnyID);
				if ("1".equals(flag)){
					break;
				}
			} catch (Exception e) {
				log.error("FTP Error #" + this.retryCount + " : " + e.toString());
				this.ftpResult.append("\nFTP Error #" + this.retryCount + " : " + e.toString());
				e.printStackTrace();
				this.uploadStatus = "fail";
			}
		}
		
//		删除本地文件夹
		File file2 = new File(this.sourceFilePath);
		file2.delete();
		
		return flag;	
	}

	/**
	 * Do the FTP Operation
	 * 
	 * @return false-fail; true-succeed
	 * @throws Exception
	 */
	private final String doUpload(String cpnyID) throws Exception {
		
		String flag = "0";
		
		try {
			ftpClient.connect(this.ftpAddr, this.ftpPort);
			log.debug("Ftp [" + this.ftpAddr + "] Connected : [" + ftpClient.getReplyString() + "]");
			this.ftpResult.append("\nFtp [" + this.ftpAddr + "] Connected.");
			
			ftpClient.login(this.userName, this.userPass);
			log.debug("Ftp [" + this.ftpAddr + "] Logged In : [" + ftpClient.getReplyString() + "]");
			this.ftpResult.append("\nFtp [" + this.ftpAddr + "] Logged In.");
			
			FileInputStream inputStream=new FileInputStream(this.sourceFilePath);
//			ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
			log.debug("[" + ftpClient.getReplyString() + "]");
			
			ftpClient.changeWorkingDirectory(targetFilePath);
//			if(!ftpClient.changeWorkingDirectory(targetFullpath)){
//				
//				if(ftpClient.makeDirectory(targetFullpath)){
//					ftpClient.changeWorkingDirectory(targetFullpath);
//				}
//			}
			
			if(ftpClient.storeFile(empPhotoName, inputStream)){
				
				ftpClient.changeWorkingDirectory(targetFilePathPortal);
//				if(!ftpClient.changeWorkingDirectory(targetFullPortalpath)){
//					if(ftpClient.makeDirectory(targetFullPortalpath)){
//						ftpClient.changeWorkingDirectory(targetFullPortalpath);
//					}
//				}
				
				if(ftpClient.storeFile(empPhotoName, inputStream)){
					flag = "1";
				}else{
					flag = "-4";//portal上传失败
					log.debug("[upload portal fail]");
				}
			}else{
				flag = "-3";//picture上传失败
				log.debug("[upload picture fail]");
			}
			
			IOUtils.closeQuietly(inputStream);

		} catch (Exception e) {
			log.debug("[" + ftpClient.getReplyString() + "]");
			this.ftpResult.append("[" + ftpClient.getReplyString() + "]");
			throw e;
		} finally {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
			log.debug("FtpClient [" + this.ftpAddr + "] Logged Out");
			this.ftpResult.append("\nFtpClient [" + this.ftpAddr + "] Logged Out");
			IOUtils.closeQuietly(this.inputStream);
		}
		return flag;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}
}