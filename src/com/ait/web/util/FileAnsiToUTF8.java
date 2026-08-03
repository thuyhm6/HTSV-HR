package com.ait.web.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.stereotype.Component;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: FileAnsiToUTF8.java
 * @Description:
 * @Create date: 2012-11-26 上午11:14:16
 * @Create by: hj(hanjian@ait.net.cn)
 * @version 5.1
 */

@Component
public class FileAnsiToUTF8 {
	
	public void ansiToUTF8(File f) throws IOException{     
		if(!f.isFile()){         
			return;     
		}     
		byte[] bs=new byte[(int)f.length()];     
		
		FileInputStream fis=new FileInputStream(f);     
		try{         
			int io=0;         
			while(io<bs.length){             
				int n=fis.read(bs,io,bs.length-io);             
				if(n<=0){                 
					break;             
					}         
				}     
			}     
		finally{         
			fis.close();     
		}  
		
		String s=new String(bs,"GBK");
		bs=s.getBytes("UTF-8");     
		
		FileOutputStream fos=new FileOutputStream(f);  
		
		try{         
			fos.write(bs);     
			}     
		finally{         
			fos.close();     
			} 
		}		
	
	@SuppressWarnings("deprecation")
	public static String getFileCharacterEnding(File file) {

		String fileCharacterEnding = "UTF-8";

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());

		Charset charset = null;

		// File f = new File(filePath);

		try {
			charset = detector.detectCodepage(file.toURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (charset != null) {
			fileCharacterEnding = charset.name();
		}

		return fileCharacterEnding;
	}
                          	
	/** 
     * 复制单个文件(可更名复制) 
     * @param oldPathFile 准备复制的文件源 
     * @param newPathFile 拷贝到新绝对路径带文件名(注：目录路径需带文件名) 
     * @return 
     */  
    public static void CopySingleFile(String oldPathFile, String newPathFile) {  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPathFile);  
            if (oldfile.exists()) { //文件存在时  
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件  
                FileOutputStream fs = new FileOutputStream(newPathFile);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; //字节数 文件大小  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
	
}
