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

public class IthrC01ToPortalIfUtil {
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
	
	/**
	 * 所有的key 向map里 put值的时候使用次nameList
	 */
	private static final String[] nameList = {
		"HR_EMPLOYEE_1001_",
		"HR_COMPANY_1003_",
		"HR_DEPARTMENT_1004_",
		
		"HR_POST_GRADE_1005_",
		"HR_DUTY_1006_",
		"HR_POSITION_1007_",
		
		"PA_SUMMARY_1001_",
		"HR_DEPARTMENT_1002_",
		
		"HR_EMPLOYEE_1001_",
		"HR_DEPTFORMESS_1001_",
		"HR_USERINFOFORMESS_1001_",
		"HR_PERINFOFORSAP_1001_",
		"HR_MONEYAWARDFORSAP_1001_",
		"HR_PERSONFORMESS_1001_"
		
	};
	//C02专用
	private static final String[] sapNameList={
		"PA_SUMMARY_1001_",
		"HR_DEPARTMENT_1002_",
		"HR_EMPLOYEE_1001_",
		"HR_DEPTFORMESS_1001_",
		"HR_USERINFOFORMESS_1001_",
		"HR_PERINFOFORSAP_1001_",
		"HR_MONEYAWARDFORSAP_1001_",
		"HR_PERSONFORMESS_1001_"
	};
	//C01 Sap 人事信息
	private static final String[] sapHrC01List={
		"SAP_HR_EMPLOYEE_1002_"
	};
	//C01 sap 部门信息
	private static final String[] sapDepartC01List={
		"SAP_HR_DEPARTMENT_1004_"
	};
	//C01 GMD 人事信息
	private static final String[] mdHrC01List={
		"GMD_HR_EMPLOYEE_1001_"
	};
	//C01 Sap 工资信息   SUMMARY  信息
	private static final String[] sapPaC01SummaryList={
		"SAP_PA_SUMMARY_1001_"
        
	};
	//C01 Sap 工资信息   FIRM  信息
	private static final String[] sapPaC01FirmList={
		"SAP_PA_FIRMBANKING_1003_"
        
	};
	
	//C11的att文件
	private static final String[] attC11NameList={
		"DEPT",
		"USERINFO"
	};
	//C11的每天吃饭次数
	private static final String[] eatC11NameList={
		"AREATCOUNT"
	};
	//C11的SAP数据
	private static final String[] sapC11NameList={
		"EMY",
		"FI_SA"
	};
	//C11的EMC数据  （销售）
	private static final String[] emcC11NameList={
		"EMC"
	};
	
	public IthrC01ToPortalIfUtil(String ftpPath,Map<String , List<String[]>> map,String cpnyId,String ftpSapPath,String ftpMdPath,String sapType){
		this.ftpPath = ftpPath;
		this.map = map;
		this.cpnyId = cpnyId;
		this.sapType = sapType;
		this.ftpSapPath = ftpSapPath;
		this.ftpMdPath = ftpMdPath;
	}
	
	/**
	 * 生成txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForMap(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		for(String ispName : nameList){
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
					OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + ispName+dateStr+".txt"),"UTF-8");
					ouf.write( htmls.toString());
					ouf.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					result = ispName+"make Txt error : file write error.";
					e.printStackTrace();
				}
				result = ispName+" make Txt ok.";
			}else{
				result = ispName+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 生成txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForSap(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		for(String ispName : sapNameList){
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
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}else{
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}
					
					FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
					ftpUploadFileSample.upload(cpnyId+"_"+ispName+dateStr+".txt");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
					e.printStackTrace();
				}
				result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
			}else{
				result = "2,"+ispName+dateStr+","+ispName+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * C01生成txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC01Sap(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		if(sapType!=null && "SAP_HRM".equals(sapType)){
			//生成SAP人事信息
			for(String ispName : sapHrC01List){
				List<String[]> paraNameList = map.get(ispName);
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName + dataCnt + "_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
		}else if(sapType!=null && "SAP_DEPARTMENT".equals(sapType)){
			//生成SAP部门信息
			for(String ispName : sapDepartC01List){
				List<String[]> paraNameList = map.get(ispName);
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName + dataCnt + "_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
		}else if(sapType!=null && "GMD_HRM".equals(sapType)){
			//生成MD人事信息
			for(String ispName : mdHrC01List){
				List<String[]> paraNameList = map.get(ispName);
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName + dataCnt + "_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName+ dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName+ dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpMdPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
		}else if(sapType!=null && "C01SUMMARY".equals(sapType)){
			//生成SAP工资信息的SUMMARY信息
			for(String ispName : sapPaC01SummaryList){
				List<String[]> paraNameList = map.get(ispName);
				//文件名加6位数字
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName +dataCnt+"_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
		}else if(sapType!=null && "C01FIRM".equals(sapType)){
			//生成SAP工资信息
			for(String ispName : sapPaC01FirmList){
				List<String[]> paraNameList = map.get(ispName);
				//文件名加6位数字
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName +dataCnt+"_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
		//定时任务执行时(C01HRM)要执行SAP人事、SAP部门、GMD人事三个
		}else{
			//生成SAP人事信息
			for(String ispName : sapHrC01List){
				List<String[]> paraNameList = map.get(ispName);
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName + dataCnt + "_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
			//生成SAP部门信息
			for(String ispName : sapDepartC01List){
				List<String[]> paraNameList = map.get(ispName);
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName + dataCnt + "_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName + dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpSapPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
			//生成MD人事信息
			for(String ispName : mdHrC01List){
				List<String[]> paraNameList = map.get(ispName);
				String dataCnt = "0";
				if(paraNameList!=null){
					if(paraNameList.size()>0){
						dataCnt = paraNameList.size()+"";
					}
				}
				int num = dataCnt.length(); 
				if(num<6){
					for(int i=1;i<=6-num;i++){
						dataCnt = "0" + dataCnt;
					}
				}
				ispName = ispName + dataCnt + "_";
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
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + cpnyId + "_" + ispName+ dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}else{
							OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + cpnyId + "_" + ispName+ dateStr+".txt"),"UTF-8");
							ouf.write( htmls.toString());
							ouf.close();
						}
						
						FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
						ftpUploadFileSample.uploadC01(cpnyId+"_"+ispName+dateStr+".txt",ftpMdPath);
					} catch (Exception e) {
						result = "2,"+ispName+dateStr+","+ispName+"make Txt error : file write error.";
						e.printStackTrace();
					}
					result = "1,"+ispName+dateStr+","+ispName+" make Txt ok.";
				}else{
					result = "2,"+ispName+dateStr+","+ispName+" data is null.";
				}
				resultList.add(result);
			}
		}
		return resultList;
	}
	
	/**
	 * C11生成att的txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC11Att(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : attC11NameList){
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
					ftpUploadFileSample.uploadC11(ispName+dateStr+"001.txt",ftpMdPath);
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
	 * C11生成吃饭次数txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC11Eat(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : eatC11NameList){
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
					ftpUploadFileSample.uploadC11(dateStr+"("+sapType+")"+".txt",ftpMdPath);
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
	 * C11生成SAP的txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC11Sap(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		String dateStr1 = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String dateStrm = (new SimpleDateFormat("HH:mm:ss")).format(date);
		String dateStrm1 = dateStrm.replaceAll(":", "");
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : sapC11NameList){
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
						ftpUploadFileSample.uploadC11(ispName+dateStr1+"01"+dateStrm1+".txt",ftpSapPath);
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
						ftpUploadFileSample.uploadC11(ispName+dateStr+".txt",ftpSapPath);
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
	 * C11生成att的txt文件方法
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForC11Emc(){
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		
		//生成SAP工资信息
		for(String ispName : emcC11NameList){
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
					ftpUploadFileSample.uploadC11(ispName+dateStr+"001.txt",ftpMdPath);
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
