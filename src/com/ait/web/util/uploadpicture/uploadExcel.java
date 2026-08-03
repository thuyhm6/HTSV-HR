package com.ait.web.util.uploadpicture;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.testng.log4testng.Logger;
import com.ait.web.util.UserConfiguration;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: uploadpic.java
 * @Description:
 * @Create date: 2012-2-31 下午13:35:18
 * @Create by: zhouyeqing(zhouyeqing@ait.net.cn)
 * @version 5.1
 */
public class uploadExcel {
	protected ByteArrayOutputStream temp_m_binArray = null; // 保存输入流数据的字节数组

	protected byte[] m_binArray = null;

	protected String ordata; // 保存输入流数据的字符串

	protected HttpServletRequest m_request = null; // 输入流request对象

	protected HttpServletResponse m_response = null;

	protected static ServletContext m_application = null;

	private int m_totalBytes = 0; // 记录输入数据流的长度

	private String rootpath; // website的uploadimage目录

	private String filepath; // 生成uploadimage相关分类子目录

	private String okpath; // 为生成文件保存的目录数值

	private String parameter; // 保存返回其他表单元素数值

	private String[] filename = null; // 多文件上传新生成的文件名数组

	private String[] filetype = null;

	private String[] filexname = null;

	private String[] oldfilename = null; // 多文件上传的文件原始名数组

	private boolean[] isup = null;

	private int[] start = null;

	private int[] tempStart = null;

	private int[] end = null;

	private int[] tempEnd = null;

	private int maxfilesize;

	int filenum;

	String server; // 服务器的OS
	
	private UserConfiguration userConfig;
	
	private static final String defaultSysFile = "/system.properties";
	
	public static void init(ServletConfig config) throws ServletException {
		m_application = config.getServletContext();
	}
	
	public uploadExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		m_totalBytes = 0;
		m_request = request;
		m_response = response;
		rootpath = request.getRealPath("");
		setServerOs(); // 服务器OS分析
		if("win".equals(server)){
			rootpath = rootpath + "\\resources\\temp\\files\\";
		}else{
			rootpath = rootpath + "/resources/temp/files";
		}
		maxfilesize = 1024 * 10;
		
	}


	/**
	 * 获取输入流数据方法
	 */
	public boolean getdata() throws IOException, ServletException {
		ServletInputStream inStream = null;
		boolean flag = true;
		try {
			inStream = m_request.getInputStream();
			temp_m_binArray = new ByteArrayOutputStream();
			m_totalBytes = m_request.getContentLength();
			if (m_totalBytes >= 100000000) {
				flag = false;
				return flag;
			}
			byte[] buffer = new byte[1024];
			int rc = 0;
			while ((rc = inStream.read(buffer, 0, 1024)) > 0) {
				temp_m_binArray.write(buffer, 0, rc);
			}
			m_binArray = temp_m_binArray.toByteArray();
			// photobyte = temp_m_binArray.toByteArray();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			temp_m_binArray.close();
			inStream.close();
		}
		return flag;
	}

	/**
	 * 提取表单其它元素值的方法
	 */
	public String getParameter(String name) {
		name = "\"" + name + "\"";
		int index = IndexOf(name, 0);
		if (index > 0) {
			int x = IndexOf("\n", index);
			x = IndexOf("\n", x + "\n".length());
			x = x + "\n".length();
			int y = IndexOf("-----------------------------", x);
			y = y - "\n".length() - 1;
			try {
				String temp = new String(m_binArray, x, y - x);
				byte[] tmp = temp.getBytes();
				parameter = new String(tmp, "iso-8859-1");
			} catch (Throwable t) {

			}
		} else {
			parameter = null;
		}
		return parameter;
	}

	/**
	 * 以下代码专为删除选中的文件
	 */
	public void deletefile(String path, String filename) {
		int i = rootpath.indexOf("\\");
		if (i > 0) { // WebServer的os为WINDOWS
			filepath = rootpath + "\\" + path;
			okpath = filepath + "\\";
		} else { // WebServer的os为unix/linux
			filepath = rootpath + "/" + path;
			okpath = filepath + "/";
		}
		try {
			String temp =filename;
			filename = temp;
		} catch (Exception e) {

		}
		filename = okpath + filename;
		File file1 = new File(filename);
		file1.delete();
	}

	/**
	 * @sandstorm 以下为EFS专用方法测试
	 */
	public void initFileComents() { // 初始化文件控件的各个参数
		setnum();
		String[] filename = new String[filenum];
		String[] filetype = new String[filenum];
		String[] filexname = new String[filenum];
		int[] start = new int[filenum];
		int[] end = new int[filenum];
		boolean[] isup = new boolean[filenum];
		int[] tempStart = new int[filenum];
		int[] tempEnd = new int[filenum];
		String[] oldfilename = new String[filenum];
		this.filename = filename;
		this.filetype = filetype;
		this.filexname = filexname;
		this.start = start;
		this.end = end;
		this.tempStart = tempStart;
		this.tempEnd = tempEnd;
		this.oldfilename = oldfilename;
		this.isup = isup;
	}

	private void setnum() { // 计算共有多少上传控件
		int index = 1;
		int y = 0;
		int z = 0;
		String temp = "filename=\"";
		while (index > 0) {
			index = IndexOf(temp, y);
			if (index > 0) {
				y = index + temp.length();
				z = z + 1;
			}
		}
		filenum = z;
	}

	private int IndexOf(String temp, int start) { // 正向byte查找
		byte[] d = temp.getBytes();
		int i = start;
		int j = 0;
		int x = -1;
		if (d.length > 1) {
			while (i < m_binArray.length) {
				if (m_binArray[i] == d[j]) {
					if (i + 1 < m_binArray.length - 1 && j + 1 < d.length - 1) {
						if (m_binArray[i + 1] == d[j + 1]) {
							j++;
							x = i;
						} else {
							j = 0;
							x = -1;
						}
					}
				}
				i++;
			}
			if (x >= 0) {
				x = x - d.length + 3;
			}
		} else {
			while (i < m_binArray.length) {
				if (m_binArray[i] == d[j]) {
					x = i;
					break;
				} else {
					x = -1;
				}
				i++;
			}
			if (x >= 0) {
				x = x - d.length + 1;
			}
		}
		return x;
	}

	private int LastIndexOf(String temp, int start) {
		byte[] d = temp.getBytes();
		int i = start;
		int j = 0;
		int x = -1;
		int y = 0;
		if (d.length > 1) {
			while (i >= 0) {
				if (m_binArray[i] == d[j]) {
					x = i;
					while (i + 1 < m_binArray.length - 1
							&& j + 1 <= d.length - 1) {
						if (m_binArray[++i] == d[++j]) {
							y++;
						} else {
							y = 0;
							j = 0; // 加一句
							i--; // 加一句
							break;
						}
					}
					if (y == d.length - 1) {
						x = i;
						i = -2;
						break;
					}
				}
				i--;
			}
			if (x >= 0) {
				x = x - d.length + 1;
			}
		} else {
			while (i >= 0) {
				if (m_binArray[i] == d[j]) {
					x = i;
					break;
				} else {
					x = -1;
				}
				i--;
			}
			if (x >= 0) {
				x = x - d.length + 1;
			}
		}
		return x;
	}

	public void disposeData(String empid) { // 分析提取文件控件的数据
		setTempSE();
		setStartPosition(); // 计算真实数据开始位置
		setEndPosition(); // 计算真实数据结束位置
		isup();
		setOldFileName(empid);
		setFileName(); // 设置生成文件名
	}

	private void setTempSE() { // 计算粗略的起始位置
		int index = 1;
		int y = 0;
		int z = 0;
		int index2 = 1;
		String temp = "Content-Type:";
		while (index > 0) {
			index = IndexOf(temp, y);
			if (index > 0) {
				y = index + temp.length();
				tempStart[z] = y;
				index2 = IndexOf("\n", index);
				tempEnd[z] = index2;
				z = z + 1;
			}
		}
	}

	private void setServerOs() { // 获取APP服务器的OS类型
		int i = rootpath.indexOf("\\");
		server = "win";
		if (i < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
	}

	private void setStartPosition() { // 获取每一个有数据的文件控件数据的开始位置
		int newindex = 0;
		for (int i = 0; i < filenum; i++) {
			newindex = IndexOf("\n", tempEnd[i] + "\n".length());
			start[i] = newindex + "\n".length();
		}
	}

	private void isup() { // 判断都有哪些文件控件真正上传了文件，判断标志就是用计算得到的文件起始位置作为判断依据
		for (int i = 0; i < filenum; i++) {
			float tempstart = start[i];
			float tempend = end[i];
			float flag = (tempend - tempstart) / 1024;
			if ((start[i] - end[i]) != 0 && flag <= maxfilesize) {
				isup[i] = true;
			} else {
				isup[i] = false;
			}
		}
	}

	private void setEndPosition() { // 获取每一个有数据的文件控件数据的结束位置
		int newindex = 0;
		for (int i = 0; i < filenum; i++) {
			newindex = IndexOf("-----------------------------", tempEnd[i]);
			end[i] = newindex - "\n".length() - 1;
		}
	}

	/*
	 * private void setEndPosition() { //获取每一个有数据的文件控件数据的结束位置 int index = 0; int
	 * newindex = 0; for (int i = 0; i < filenum; i++) { newindex =
	 * IndexOf("content-disposition", tempEnd[i]); //这个改了
	 * System.out.println(newindex); end[i] = newindex - 12; //这里改了
	 * 
	 * System.out.println(end[i]); } }
	 */

	private void setFileName() { // 设置文件名
		for (int i = 0; i < filenum; i++) {
			if (isup[i]) {
				setFileType();
				setFileExname();
				if (!filetype[i].equals("")) {
					filename[i] = filexname[i] + "." + filetype[i];
				} else {
					filename[i] = filexname[i];
				}
			} else {
				filename[i] = "";
			}
		}
	}

	private void setFileExname() { // 设置文件扩展名
		String addtemp = null;
		for (int i = 0; i < filenum; i++) {
			if (isup[i]) {
				int index = oldfilename[i].lastIndexOf(".");
				if (index > 0) {
					addtemp = oldfilename[i].substring(0, index);
				} else {
					addtemp = oldfilename[i];
				}
				filexname[i] = addtemp;
				// + java.lang.String.valueOf(System.currentTimeMillis()) + i;
			}
		}
	}

	private void setFileType() { // 获取文件类型
		String addtemp = null;
		for (int x = 0; x < filenum; x++) {
			if (isup[x]) {
				int index = oldfilename[x].lastIndexOf(".");
				if (index > 0) {
					addtemp = oldfilename[x].substring(index + 1);
				} else {
					addtemp = "";
				}
				filetype[x] = addtemp;
			}
		}
	}

	private void setOldFileName(String empid) { // 提取原始文件名
		int tmpstart = 0;
		int tmpend = 0;
		String tempstr = null;
		int newindex = 0;
		for (int i = 0; i < filenum; i++) {
			if (isup[i]) {
				tmpstart = LastIndexOf("filename=\"", tempStart[i]);
				newindex = LastIndexOf("\"", tempStart[i]);
				tmpend = newindex;
				tempstr = empid+".xls" ;//new String(m_binArray, tmpstart + "filename=\"".length(), tmpend - tmpstart - "filename=\"".length());
				newindex = tempstr.lastIndexOf("/");
				if (newindex > 0) { // 客户端不是WINDOWS
					oldfilename[i] = tempstr.substring(newindex + 1);
				} else {
					newindex = tempstr.lastIndexOf("\\");
					oldfilename[i] = tempstr.substring(newindex + 1);
				}
			} else {
				oldfilename[i] = "";
			}
		}
	}

	public void setFilePath(String subpath,String cpnyId) { // 设置文件上传分类路径
		if (server.equals("win")) { // WebServer的os为WINDOWS
			filepath = rootpath +"\\"+cpnyId;
			okpath = filepath + "\\";
		} else { // WebServer的os为unix/linux
			filepath = rootpath + "/"+cpnyId;
			okpath = filepath + "/";
		}
		File file = new File(filepath);
		// filepath="\\"+"\\"+"150.150.193.138"+"\\"+"lccEFS site"+"\\"+subpath;
		// okpath=filepath+"\\";
		// File file=new File(filepath);
		//file.mkdirs(); // 创建设置的子目录
		file.mkdir(); // 创建设置的子目录
	}

	public String WriteMdata() { // 写数据
		String sign="";
		for (int i = 0; i < 1; i++) {
			if (isup[i]) {
				try {
					// System.out.println(okpath + filename[i]);
					FileOutputStream fileout = new FileOutputStream(okpath + filename[i]);
					fileout.write(m_binArray, start[i], end[i] - start[i]);
					fileout.close();
					sign="success";
				} catch (Exception fe) {
					fe.printStackTrace();
					Logger.getLogger(getClass()).debug("inStream is not ok!");
					sign="fail";
				}
			}
		}
		/*
		 * try { FileOutputStream fileout1=new
		 * FileOutputStream(okpath+"test.txt"); fileout1.write(m_binArray);
		 * fileout1.close(); } catch (Exception fe) { }
		 */
		return sign;
	}

	public String[] getFilenames() {
		for (int i = 0; i < filenum; i++) {
			try {
				String temp =filename[i];
				filename[i] = temp;
			} catch (Exception e) {
			}
		}
		return filename;
	}

	public String[] getOldFileNames() {
		for (int i = 0; i < filenum; i++) {
			if (isup[i]) {
				try {
					String temp =oldfilename[i];
					oldfilename[i] = temp;
				} catch (Exception e) {
				}
			}
		}
		return oldfilename;
	}

	public byte[] getFileBytes() {
		byte[] photobyte = null;
		try {
			ByteArrayInputStream temp = new ByteArrayInputStream(m_binArray,
					start[0], end[0] - start[0]);
			photobyte = new byte[end[0] - start[0]];
			temp.read(photobyte);

		} catch (Exception e) {
			Logger.getLogger(getClass()).debug("Upload::getFileBytes Error:" + e);
		}
		return photobyte;
	}
}
