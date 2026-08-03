package com.ait.web.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ait.test.FtpUploadFileSample;

public class IthrC12ToPortalIfUtil {
	/**
	 * 生成TXT文件 的目录 
	 * 例如：d://ftp//txt//   ****最后必须有//  不能是d://ftp//txt
	 */
	private String ftpPath;
	private String cpnyId;
	
	private String sapType;
	private String ftpSapPath;
	private String ftpMdPath;
	/**
	 * 存放用于生成所有txt文件的map
	 * map<key,value>
	 * 其中key为 _HR_POST_GRADE_1031_ 这样的结构 基本是按照  
	 * 下划线+表名+下划线+编号+下划线 
	 * 这个规则命名的
	 * 所有的key都在下面的 nameList中定义
	 * 
	 * 其中value是 String数组的list
	 * 一个数组是数据库一条查询出的记录
	 * 一个list是 一个表所有需要插入或更新的数据
	 * 
	 */
	private Map<String , List<String[]>> map ;
	
	//C12的att文件
	private static final String[] attC12NameList={
		"DEPT",
		"USERINFO"
	};
	//C12的每天吃饭次数
	private static final String[] eatC12NameList={
		"AREATCOUNT"
	};
	//C12的SAP数据
	private static final String[] sapC12NameList={
		"EMY",
		"FI_SA"
	};
	//C12的EMC数据  （销售）
	private static final String[] emcC12NameList={
		"EMC"
	};
	
	public IthrC12ToPortalIfUtil(String ftpPath,Map<String , List<String[]>> map,String cpnyId,String ftpSapPath,String ftpMdPath,String sapType){
		this.ftpPath = ftpPath;
		this.map = map;
		this.cpnyId = cpnyId;
		this.sapType = sapType;
		this.ftpSapPath = ftpSapPath;
		this.ftpMdPath = ftpMdPath;
	}
	
	/**
	 * C12生成att的txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC12Att(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : attC12NameList){
			List<String[]> paraNameList = map.get(ispName);
			if(paraNameList!=null && paraNameList.size()>0){
				StringBuilder htmls = new StringBuilder();
				htmls.append("");
				int i=1;
				for(String[] paraInfo : paraNameList){
					int j=1;
					for(String para : paraInfo){
						if(j<paraInfo.length)
							htmls.append(para+"\t");
						else
							htmls.append(para);
						j++;
					}
					if(i<paraNameList.size())
					htmls.append("\r\n");
					i++;
				}
				try {
					int k = ftpPath.indexOf("\\");
					if(k>0){
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + ispName + dateStr+"001.txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}else{
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + ispName + dateStr+"001.txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}
					
					FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
					ftpUploadFileSample.uploadC12(ispName+dateStr+"001.txt",ftpMdPath);
				} catch (Exception e) {
					result = "2,"+ispName+dateStr+"001"+","+ispName+"make Txt error : file write error.";
					e.printStackTrace();
				}
				result = "1,"+ispName+dateStr+"001"+","+ispName+" make Txt ok.";
			}else{
				result = "2,"+ispName+dateStr+"001"+","+ispName+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * C12生成吃饭次数txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC12Eat(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : eatC12NameList){
			List<String[]> paraNameList = map.get(ispName);
			if(paraNameList!=null && paraNameList.size()>0){
				StringBuilder htmls = new StringBuilder();
				htmls.append("");
				int i=1;
				for(String[] paraInfo : paraNameList){
					int j=1;
					for(String para : paraInfo){
						if(j<paraInfo.length)
							htmls.append(para+"\t");
						else
							htmls.append(para);
						j++;
					}
					if(i<paraNameList.size())
					htmls.append("\r\n");
					i++;
				}
				try {
					int k = ftpPath.indexOf("\\");
					if(k>0){
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + dateStr+"("+sapType+")"+".txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}else{
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + dateStr+"("+sapType+")"+".txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}
					
					FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
					ftpUploadFileSample.uploadC12(dateStr+"("+sapType+")"+".txt",ftpMdPath);
				} catch (Exception e) {
					result = "2,"+dateStr+"("+sapType+")"+","+dateStr+"("+sapType+")"+"make Txt error : file write error.";
					e.printStackTrace();
				}
				result = "1,"+dateStr+"("+sapType+")"+","+dateStr+"("+sapType+")"+" make Txt ok.";
			}else{
				result = "2,"+dateStr+"("+sapType+")"+","+dateStr+"("+sapType+")"+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * C12生成SAP的txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC12Sap(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		String dateStr1 = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String dateStrm = (new SimpleDateFormat("HH:mm:ss")).format(date);
		String dateStrm1 = dateStrm.replaceAll(":", "");
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : sapC12NameList){
			if(ispName!=null && "EMY".equals(ispName)){
				List<String[]> paraNameList = map.get(ispName);
				if(paraNameList!=null && paraNameList.size()>0){
					StringBuilder htmls = new StringBuilder();
					htmls.append("");
					int i=1;
					for(String[] paraInfo : paraNameList){
						int j=1;
						for(String para : paraInfo){
							if(j<paraInfo.length)
								htmls.append(para+"\t");
							else
								htmls.append(para);
							j++;
						}
						if(i<paraNameList.size())
						htmls.append("\r\n");
						i++;
					}
					try {
						int k = ftpPath.indexOf("\\");
						if(k>0){
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + ispName+dateStr1+"01"+dateStrm1+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + ispName + dateStr1+"01"+dateStrm1+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC12(ispName+dateStr1+"01"+dateStrm1+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr1+"01"+dateStrm1+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr1+"01"+dateStrm1+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr1+"01"+dateStrm1+","+ispName+" data is null.";
				}
			}else if(ispName!=null && "FI_SA".equals(ispName)){
				List<String[]> paraNameList = map.get(ispName);
				if(paraNameList!=null && paraNameList.size()>0){
					StringBuilder htmls = new StringBuilder();
					htmls.append("");
					int i=1;
					for(String[] paraInfo : paraNameList){
						int j=1;
						for(String para : paraInfo){
							if(j<paraInfo.length)
								htmls.append(para+"\t");
							else
								htmls.append(para);
							j++;
						}
						if(i<paraNameList.size())
						htmls.append("\r\n");
						i++;
					}
					try {
						int k = ftpPath.indexOf("\\");
						if(k>0){
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC12(ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * C12生成att的txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC12Emc(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : emcC12NameList){
			List<String[]> paraNameList = map.get(ispName);
			if(paraNameList!=null && paraNameList.size()>0){
				StringBuilder htmls = new StringBuilder();
				htmls.append("");
				int i=1;
				for(String[] paraInfo : paraNameList){
					int j=1;
					for(String para : paraInfo){
						if(j<paraInfo.length)
							htmls.append(para+"\t");
						else
							htmls.append(para);
						j++;
					}
					if(i<paraNameList.size())
					htmls.append("\r\n");
					i++;
				}
				try {
					int k = ftpPath.indexOf("\\");
					if(k>0){
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + ispName + dateStr+"001.txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}else{
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + ispName + dateStr+"001.txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}
					
					FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
					ftpUploadFileSample.uploadC12(ispName+dateStr+"001.txt",ftpMdPath);
				} catch (Exception e) {
					result = "2,"+ispName+dateStr+"001"+","+ispName+"make Txt error : file write error.";
					e.printStackTrace();
				}
				result = "1,"+ispName+dateStr+"001"+","+ispName+" make Txt ok.";
			}else{
				result = "2,"+ispName+dateStr+"001"+","+ispName+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		String path = "c://";
		String ftpSapPath = "c://";
		String ftpMdPath = "c://";
		String cpnyId = "C02";
		String sapType = "C01HR";
		List<String[]> cpnyList = new ArrayList<String[]>();
		String[] cpnyInfo1 = {"A10","乐天华邦（北京）饮料有限公司","","","1"};
		cpnyList.add(cpnyInfo1);
		
		List<String[]> dutyList = new ArrayList<String[]>();
		String[] dutyInfo1 = {"C03","14561","SV","","","1"};
		dutyList.add(dutyInfo1);
		
		Map<String , List<String[]>> map = new HashMap<String, List<String[]>>();
		map.put("PA_SUMMARY_1001_", cpnyList);
		map.put("HR_DEPARTMENT_1002_", dutyList);
		IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(path,map,cpnyId,ftpSapPath,ftpMdPath,sapType);
		List<String> resultList = ifUtil.makeTxtForSap();
		for(String result:resultList){
			System.out.println(result); 
			
		}	
	}
}
