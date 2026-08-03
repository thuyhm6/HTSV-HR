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

public class IthrToPortalIfUtil {
	/**
	 * 生成TXT文件 的目录 
	 * 例如：d://ftp//txt//   ****最后必须有//  不能是d://ftp//txt
	 */
	private String ftpPath;
	
	private String cpnyId;
	
	
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
		"HR_PERSONFORMESS_1001_",
		"HR_EATINGCNTFORMESS_1001_"
		
	};
	
	private static final String[] sapNameList={
		"PA_SUMMARY_1001_",
		"HR_DEPARTMENT_1002_",
		"HR_EMPLOYEE_1001_",
		"HR_DEPTFORMESS_1001_",
		"HR_USERINFOFORMESS_1001_",
		"HR_PERINFOFORSAP_1001_",
		"HR_MONEYAWARDFORSAP_1001_",
		"HR_PERSONFORMESS_1001_",
		"HR_EATINGCNTFORMESS_1001_"
	};
	
	private static final String[] sapNameC01List={
		"PA_SUMMARY_C011001_",
        "PA_ACTUAL_C011002_",
        "HR_EMPINFO_C011003_",
        "HR_EMPPOST_C011004_",
        "ORG_DEPART_C011005_"
	};
	
	public IthrToPortalIfUtil(String ftpPath,Map<String , List<String[]>> map,String cpnyId){
		this.ftpPath = ftpPath;
		this.map = map;
		this.cpnyId = cpnyId;
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
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String result = "";
		List<String> resultList = new ArrayList<String>();
		for(String ispName : sapNameC01List){
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
	 * 生成txt文件方法 HR TO 销售系统
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForSapInterface(String filedName){
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
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + filedName),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}else{
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + filedName),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}
					FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
					ftpUploadFileSample.upload(filedName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					result = "2," + filedName + "make Txt error : file write error.";
					e.printStackTrace();
				}
				result = "1,"+filedName+"make Txt ok.";
			}else{
				result = "2,"+filedName+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	
	/**
	 * 生成txt文件方法 HR TO 销售系统
	 * @return  所有生成 txt文件 的结果
	 */
	public List<String> makeTxtForSapInterfaceANSI(String filedName){
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
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "\\" + filedName),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}else{
						OutputStreamWriter ouf = new OutputStreamWriter(new FileOutputStream(ftpPath + "/" + filedName),"UTF-8");
						ouf.write( htmls.toString());
						ouf.close();
					}
					FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample();
					ftpUploadFileSample.upload(filedName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					result = "2," + filedName + "make Txt error : file write error.";
					e.printStackTrace();
				}
				result = "1,"+filedName+"make Txt ok.";
			}else{
				result = "2,"+filedName+" data is null.";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	
	public static void main(String[] args) {
//		String path = "c://";
//		List<String[]> cpnyList = new ArrayList<String[]>();
//		String[] cpnyInfo1 = {"A10","乐天华邦（北京）饮料有限公司","",
//				"","1"};
//		cpnyList.add(cpnyInfo1);
//		
//		List<String[]> dutyList = new ArrayList<String[]>();
//		String[] dutyInfo1 = {"C03","14561","SV",
//				"","","1"};
//		dutyList.add(dutyInfo1);
//		
//		
//		
//		Map<String , List<String[]>> map = new HashMap<String, List<String[]>>();
//		map.put("HR_COMPANY_1003_", cpnyList);
//		map.put("HR_DUTY_1006_", dutyList);
//		IthrToPortalIfUtil ifUtil = new IthrToPortalIfUtil(path,map);
//		List<String> resultList = ifUtil.makeTxtForMap();
//		for(String result:resultList){
//			System.out.println(result);
//		}
		
		String path = "c://";
		String cpnyId = "C02";
		List<String[]> cpnyList = new ArrayList<String[]>();
		String[] cpnyInfo1 = {"A10","乐天华邦（北京）饮料有限公司","",
				"","1"};
		cpnyList.add(cpnyInfo1);
		
		List<String[]> dutyList = new ArrayList<String[]>();
		String[] dutyInfo1 = {"C03","14561","SV",
				"","","1"};
		dutyList.add(dutyInfo1);
		
		
		
		Map<String , List<String[]>> map = new HashMap<String, List<String[]>>();
		map.put("PA_SUMMARY_1001_", cpnyList);
		map.put("HR_DEPARTMENT_1002_", dutyList);
		IthrToPortalIfUtil ifUtil = new IthrToPortalIfUtil(path,map,cpnyId);
		List<String> resultList = ifUtil.makeTxtForSap();
		for(String result:resultList){
			System.out.println(result); 
			
		}	
	}
}
