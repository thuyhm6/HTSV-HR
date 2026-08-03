package com.ait.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.log4testng.Logger;

import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftp.FTP;

public class ReadFile {	
	private static String server = ""; //server	
	private static String username = ""; //ftp user
	private static String password = ""; //ftp pass
	private static String ftpPath = ""; //ftp 文件路径
	private static String localPath = ""; //本地地址
	//读取文件名List
	@SuppressWarnings("unchecked")
	public static List readTxtFileName(String path) {
		List nameList = new ArrayList();
		File f = new File(path);
		if (!f.exists()) {
			Logger.getLogger(ReadFile.class).debug("not exists");
		}
		File fa[] = f.listFiles();
		if(fa != null){
			for (int i = 0; i < fa.length; i++){
				File perFile = fa[i];
				if (perFile.isFile()){
					nameList.add(perFile.getName());
				}
			}
		}
		return nameList;
	}
	//读取文件内容 返回List 方法通用
	@SuppressWarnings("unchecked")
	public static List readTxtFileForMap(String path){
		@SuppressWarnings("unused")
		String readStr = "";
        String read;
        FileReader fileread;
        List paramList = new ArrayList();
        try {
            fileread = new FileReader(path);
            BufferedReader bufread = new BufferedReader(fileread);
            try {
                while ((read = bufread.readLine()) != null) {
                	String content[] = read.split("\t");
                	if(content != null){
                		//以tab作为分隔符 
                		Map param = new LinkedHashMap(); 
                		for(int i=0;i<content.length;i++){
                			if(content[i].trim() != null && !"".equals(content[i].trim())){
                				param.put("PARAM_"+i, content[i].trim());
                			}
                		}
                		paramList.add(param);
                	}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return paramList;
    }
	
	//FTP读取文件 到本地 并删除远程文件
	public static int downloadFtpFiles(String inserver,String inusername,String inpassword,String inftpPath,String inlocalPath){
		Logger logger = Logger.getLogger(ReadFile.class);
		int flag = 1;
		server = inserver;
		username = inusername;
		password = inpassword;
		ftpPath = inftpPath;
		localPath = inlocalPath;
		//新建一个文件夹  
		/*try {
				String filePath = localPath;        
				File myFilePath = new File(filePath);        
				if (!myFilePath.exists()) {          
					myFilePath.mkdir();    
					myFilePath.setExecutable(true)  ; 
					myFilePath.setWritable(true)  ;
					myFilePath.setReadable(true); 
				}      
		} catch (Exception e) {        
				System.out.println("新建文件夹操作出错");        
				e.printStackTrace();      
		}
		try {
				String filePath = localPath;        
				File myFilePath = new File(filePath);        
				if (!myFilePath.exists()) {          
					FTP.createFolder("c04/localCardfile/", "/workarea/lotte_ss/lotte/resources/temp/", "127.0.0.1", "ftpuser", "ftpuser1");
				}      
		} catch (Exception e) {        
				System.out.println("新建文件夹操作出错");        
				e.printStackTrace();      
		}   
		*/
		try {
			//读取该文件夹下所有文件
			String[] f = FTP.getFileNamesInFolder(ftpPath, server, username, password);
			logger.debug("txt 文件数量......" + f.length+"==================="+username+"==="+password);
			if(f!=null){
				for(int i=0;i<f.length;i++){
					//由FTP 复制到FTP
					//FTP.copyFile("/ftp_file/picture/C11/" + f[i], "F:\\ces", "10.231.221.39", "ftpuser", "ftpuser1");
					//download 方法
					int status = FTP.getFile(f[i], ftpPath, server, username, password, localPath);
					if(FileTransferStatus.SUCCESS == status){
						//FTP 读取完毕删除远程文件
						FTP.deleteFile(f[i], ftpPath, server, username, password);
					}
				}
			}
		} catch (FileTransferException e) {
			e.printStackTrace();
			flag = 0;
		}
		return flag;
    }
	
}
