package com.ait.report.hr.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.report.hr.dao.HrReportC01Dao;
import com.ait.report.hr.service.HrReportC01Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportSerImpl.java
 * @Description: implement Class HrReportSerImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Service
public class HrReportC01SerImpl implements HrReportC01Ser {
	Logger logger = Logger.getLogger(HrReportSerImpl.class);
	@Autowired
	private HrReportC01Dao hrReportDao;
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");

	/**
	 * 人事登记卡C11   导出Excel
	 * @param request
	 * @return List
	 */
	@Override
	@SuppressWarnings("unchecked")
	
	public List getGradeLevelList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> empList = new ArrayList<LinkedHashMap>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId().toString()) ;
		
		retrunList = hrReportDao.getGradeLevelList(paramMap); 
		return retrunList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getEmpPaRiseListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		
		return hrReportDao.getEmpPaRiseListCnt(paramMap);
	}
	
	/**
	 * 人事登记卡C11   导出Excel
	 * @param request
	 * @return List
	 * @throws ConfigurationException 
	 */
	@Override
	@SuppressWarnings("unchecked")
	
	public List getEmpPaRiseExcelList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> empList = new ArrayList<LinkedHashMap>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"C01") ;
		
		/*String PhotoPath = "ftp://10.231.221.36";
		try {
			PhotoPath = config.getString("hrm.photo.read.ftp");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}*/
		String CPNY_ID = admin.getCpnyId()!=null?admin.getCpnyId().toString():"C01";
		
		empList = hrReportDao.getEmpPaRiseList(paramMap);
		for(LinkedHashMap empMap: empList){
			//照片路径
			//String EMPID = empMap != null && empMap.get("EMPID") != null ? empMap.get("EMPID").toString() : "";
			//empMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
			//根据工号查询该员工社外工作经历
			empMap.put("interLanguage", admin.getLanguage()!=null?admin.getLanguage().toString():"zh");
			List<LinkedHashMap> outsideExperList = new ArrayList<LinkedHashMap>();
			outsideExperList = hrReportDao.getOutExperienceList(empMap);
			int outsideNum = outsideExperList!=null?outsideExperList.size():0;
			//根据工号查询该员工社内工作经历
			List<LinkedHashMap> insideExperList = new ArrayList<LinkedHashMap>();
			insideExperList = hrReportDao.getInsideExperienceList(empMap);
			int insideNum = insideExperList!=null?insideExperList.size():0;
			//社内工作经历、社外工作经历，取较大者赋予给experNum
			int experNum = 0;
			if(insideNum>=outsideNum){
				experNum =  insideNum;
			}else{
				experNum =  outsideNum;
			}
			empMap.put("experNum", experNum);
			//把社外工作经历、社内工作经历两个list组合成一个list
			List<LinkedHashMap> returnMapList = new ArrayList<LinkedHashMap>();
			for(int i=0;i<experNum;i++){
				LinkedHashMap returnMap = new LinkedHashMap();
				if(insideNum >= outsideNum){
					if(i<outsideNum){
						Map outsideMap = new LinkedHashMap();
						outsideMap = new LinkedHashMap(outsideExperList.get(i));
						returnMap.put("OUT_CPNY_NAME", outsideMap.get("CPNY_NAME")!=null?outsideMap.get("CPNY_NAME").toString():"");
						returnMap.put("OUT_POST_NAME", outsideMap.get("POST_NAME")!=null?outsideMap.get("POST_NAME").toString():"");
						returnMap.put("OUT_START_DATE", outsideMap.get("START_DATE")!=null?outsideMap.get("START_DATE").toString():"");
						returnMap.put("OUT_END_DATE", outsideMap.get("END_DATE")!=null?outsideMap.get("END_DATE").toString():"");
						
						Map insideMap = new LinkedHashMap();
						insideMap = new LinkedHashMap(insideExperList.get(i));
						returnMap.put("IN_CPNY_NAME", insideMap.get("DEPT_DISTINGUISH_NAME")!=null?insideMap.get("DEPT_DISTINGUISH_NAME").toString():"");
						returnMap.put("IN_DEPT_NAME", insideMap.get("DEPARTMENT")!=null?insideMap.get("DEPARTMENT").toString():"");
						returnMap.put("IN_POST_NAME", insideMap.get("POST_NAME")!=null?insideMap.get("POST_NAME").toString():"");
						returnMap.put("IN_PA_BASIC_DATA", insideMap.get("PA_BASIC_DATA")!=null?insideMap.get("PA_BASIC_DATA").toString():"");
						returnMap.put("IN_START_DATE", insideMap.get("START_DATE")!=null?insideMap.get("START_DATE").toString():"");
						returnMap.put("IN_END_DATE", insideMap.get("END_DATE")!=null?insideMap.get("END_DATE").toString():"");
					
						returnMapList.add(returnMap);
					}else{
						Map insideMap = new LinkedHashMap();
						insideMap = new LinkedHashMap(insideExperList.get(i));
						returnMap.put("IN_CPNY_NAME", insideMap.get("DEPT_DISTINGUISH_NAME")!=null?insideMap.get("DEPT_DISTINGUISH_NAME").toString():"");
						returnMap.put("IN_DEPT_NAME", insideMap.get("DEPARTMENT")!=null?insideMap.get("DEPARTMENT").toString():"");
						returnMap.put("IN_POST_NAME", insideMap.get("POST_NAME")!=null?insideMap.get("POST_NAME").toString():"");
						returnMap.put("IN_PA_BASIC_DATA", insideMap.get("PA_BASIC_DATA")!=null?insideMap.get("PA_BASIC_DATA").toString():"");
						returnMap.put("IN_START_DATE", insideMap.get("START_DATE")!=null?insideMap.get("START_DATE").toString():"");
						returnMap.put("IN_END_DATE", insideMap.get("END_DATE")!=null?insideMap.get("END_DATE").toString():"");
					
						returnMapList.add(returnMap);
					}
				}else if(outsideNum > insideNum){
					if(i<insideNum){
						Map outsideMap = new LinkedHashMap();
						outsideMap = new LinkedHashMap(outsideExperList.get(i));
						returnMap.put("OUT_CPNY_NAME", outsideMap.get("CPNY_NAME")!=null?outsideMap.get("CPNY_NAME").toString():"");
						returnMap.put("OUT_POST_NAME", outsideMap.get("POST_NAME")!=null?outsideMap.get("POST_NAME").toString():"");
						returnMap.put("OUT_START_DATE", outsideMap.get("START_DATE")!=null?outsideMap.get("START_DATE").toString():"");
						returnMap.put("OUT_END_DATE", outsideMap.get("END_DATE")!=null?outsideMap.get("END_DATE").toString():"");
						
						Map insideMap = new LinkedHashMap();
						insideMap = new LinkedHashMap(insideExperList.get(i));
						
						returnMap.put("IN_CPNY_NAME", insideMap.get("DEPT_DISTINGUISH_NAME")!=null?insideMap.get("DEPT_DISTINGUISH_NAME").toString():"");
						returnMap.put("IN_DEPT_NAME", insideMap.get("DEPARTMENT")!=null?insideMap.get("DEPARTMENT").toString():"");
						returnMap.put("IN_POST_NAME", insideMap.get("POST_NAME")!=null?insideMap.get("POST_NAME").toString():"");
						returnMap.put("IN_PA_BASIC_DATA", insideMap.get("PA_BASIC_DATA")!=null?insideMap.get("PA_BASIC_DATA").toString():"");
						returnMap.put("IN_START_DATE", insideMap.get("START_DATE")!=null?insideMap.get("START_DATE").toString():"");
						returnMap.put("IN_END_DATE", insideMap.get("END_DATE")!=null?insideMap.get("END_DATE").toString():"");

						returnMapList.add(returnMap);
					}else{
						Map outsideMap = new LinkedHashMap();
						outsideMap = new LinkedHashMap(outsideExperList.get(i));
						returnMap.put("OUT_CPNY_NAME", outsideMap.get("CPNY_NAME")!=null?outsideMap.get("CPNY_NAME").toString():"");
						returnMap.put("OUT_POST_NAME", outsideMap.get("POST_NAME")!=null?outsideMap.get("POST_NAME").toString():"");
						returnMap.put("OUT_START_DATE", outsideMap.get("START_DATE")!=null?outsideMap.get("START_DATE").toString():"");
						returnMap.put("OUT_END_DATE", outsideMap.get("END_DATE")!=null?outsideMap.get("END_DATE").toString():"");
						
						returnMapList.add(returnMap);
					}
				}
			}
			//两个list组合成一个list之后，把第一个索引里的值放入empMap中，再从组合list（returnMapList）中去掉第一个索引里的对象
			Map tempMap = new LinkedHashMap();
			if(returnMapList.size()>=1){
				tempMap = new LinkedHashMap(returnMapList.get(0));
				empMap.put("OUT_CPNY_NAME", tempMap.get("OUT_CPNY_NAME")!=null?tempMap.get("OUT_CPNY_NAME").toString():"");
				empMap.put("OUT_POST_NAME", tempMap.get("OUT_POST_NAME")!=null?tempMap.get("OUT_POST_NAME").toString():"");
				empMap.put("OUT_START_DATE", tempMap.get("OUT_START_DATE")!=null?tempMap.get("OUT_START_DATE").toString():"");
				empMap.put("OUT_END_DATE", tempMap.get("OUT_END_DATE")!=null?tempMap.get("OUT_END_DATE").toString():"");
				
				empMap.put("IN_CPNY_NAME", tempMap.get("IN_CPNY_NAME")!=null?tempMap.get("IN_CPNY_NAME").toString():"");
				empMap.put("IN_DEPT_NAME", tempMap.get("IN_DEPT_NAME")!=null?tempMap.get("IN_DEPT_NAME").toString():"");
				empMap.put("IN_POST_NAME", tempMap.get("IN_POST_NAME")!=null?tempMap.get("IN_POST_NAME").toString():"");
				empMap.put("IN_PA_BASIC_DATA", tempMap.get("IN_PA_BASIC_DATA")!=null?tempMap.get("IN_PA_BASIC_DATA").toString():"");
				empMap.put("IN_START_DATE", tempMap.get("IN_START_DATE")!=null?tempMap.get("IN_START_DATE").toString():"");
				empMap.put("IN_END_DATE", tempMap.get("IN_END_DATE")!=null?tempMap.get("IN_END_DATE").toString():"");
				//去掉第一个索引里的对象
				returnMapList.remove(0);
			}
			
			if(returnMapList.size()>=1){
				empMap.put("experienceList", returnMapList);
			}
			retrunList.add(empMap);
		}
		return retrunList;
	}

	/**
	 * 乐天玛特--在职人数统计信息报表,Excel导出用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpOnStatusExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_ONSTATUS")!=null?paramMap.get("YEAR_ONSTATUS").toString():"";
		String month = paramMap.get("MONTH_ONSTATUS")!=null?paramMap.get("MONTH_ONSTATUS").toString():"";
		paramMap.put("PA_MONTH", year+'-'+month);
		paramMap.put("YEAR_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		//调用整理数据的方法
		returnList = this.getEmpOnStatusExcelListPre(paramMap) ;
		return returnList ;
	}
	//在职人数信息统计，对在职、参保、计薪数据进行整理
	@SuppressWarnings({ "unused", "unchecked" })
	public List getEmpOnStatusExcelListPre(Map paramMap) {
		List returnList = new ArrayList() ;
		
		List<LinkedHashMap> distinguishList = new ArrayList<LinkedHashMap>() ;
		distinguishList = hrReportDao.getAllDistinguishList(paramMap) ;
		for(LinkedHashMap dataMap: distinguishList){
			LinkedHashMap onStatusMap = new LinkedHashMap();
			LinkedHashMap isCalcMap = new LinkedHashMap();
			LinkedHashMap paCalcMap = new LinkedHashMap();
			
			String dept_distinguish = dataMap.get("DEPT_DISTINGUISH")!=null?dataMap.get("DEPT_DISTINGUISH").toString():"";
			String dept_all_no = dataMap.get("DEPT_ALL_NO")!=null?dataMap.get("DEPT_ALL_NO").toString():"";
			if(dept_distinguish!=null && !"".equals(dept_distinguish)){
				//查询整个公司范围（所有C01范围内的员工）
				if("COMPANY_TOTAL".equals(dept_distinguish)){
					paramMap.remove("DIS_TYPE");
					paramMap.put("DIS_TYPE", "COMPANY");
				//查询总部范围（dept_type：上海总部、上海本部、PB，三个部分）
				}else if("ZONGFEN_TOTAL".equals(dept_distinguish) && "zongbu".equals(dept_all_no)){
					paramMap.remove("DIS_TYPE");
					paramMap.put("DIS_TYPE", "ZONGBU");
				//查询分店范围（dept_type：营运1部，营运2部，营运3部，营运4部，四个部分）
				}else if("ZONGFEN_TOTAL".equals(dept_distinguish) && "fendian".equals(dept_all_no)){
					paramMap.remove("DIS_TYPE");
					paramMap.put("DIS_TYPE", "FENDIAN");
				//查询区域范围（dept_type：上海总部、上海本部、PB、营运1部，营运2部，营运3部，营运4部，七个部分中的一个）
				}else if("QUYU_TOTAL".equals(dept_distinguish)){
					paramMap.remove("DIS_TYPE");
					paramMap.remove("DEPT_TYPE");
					paramMap.put("DIS_TYPE", "DEPT_TYPE");
					paramMap.put("DEPT_TYPE", dataMap.get("DEPT_TYPE")!=null?dataMap.get("DEPT_TYPE").toString():"");
				//查询门店范围（dept_distinguish_no中的其中一个）
				}else{
					paramMap.remove("DIS_TYPE");
					paramMap.remove("DEPT_DISTINGUISH_NO");
					paramMap.put("DIS_TYPE", "DEPT_DISTINGUISH_NO");
					paramMap.put("DEPT_DISTINGUISH_NO", dataMap.get("DEPT_DISTINGUISH_NO")!=null?dataMap.get("DEPT_DISTINGUISH_NO").toString():"");
				}
			}
			//在职人员数统计（包括：1.合同工；2.劳务工；3.小时工；4.学生；5.所有人；）含有（离职人数、离职率=（离职人数/所有人））
			onStatusMap = (LinkedHashMap)hrReportDao.getEmpOnStatusCntByDisType(paramMap) ;
			//1.合同工
			dataMap.put("CON_CNT", onStatusMap.get("CON_CNT")!=null?onStatusMap.get("CON_CNT").toString():"0");
			//2.劳务工
			dataMap.put("DIS_CNT", onStatusMap.get("DIS_CNT")!=null?onStatusMap.get("DIS_CNT").toString():"0");
			//3.小时工
			dataMap.put("HOU_CNT", onStatusMap.get("HOU_CNT")!=null?onStatusMap.get("HOU_CNT").toString():"0");
			//4.学生
			dataMap.put("STU_CNT", onStatusMap.get("STU_CNT")!=null?onStatusMap.get("STU_CNT").toString():"0");
			//5.所有人
			dataMap.put("ALL_CNT", onStatusMap.get("ALL_CNT")!=null?onStatusMap.get("ALL_CNT").toString():"0");
			
			//6.离职人数
			dataMap.put("LIZHI_CNT", onStatusMap.get("LIZHI_CNT")!=null?onStatusMap.get("LIZHI_CNT").toString():"0");
			//7.离职率
			dataMap.put("LIZHI_PET", onStatusMap.get("LIZHI_PET")!=null?onStatusMap.get("LIZHI_PET").toString():"0");
			
			//参保人员数
			isCalcMap = (LinkedHashMap)hrReportDao.getEmpCalIsCntByDisType(paramMap) ;
			dataMap.put("IS_CNT", isCalcMap.get("IS_CNT")!=null?isCalcMap.get("IS_CNT").toString():"0");
			dataMap.put("DIANLI_CNT", isCalcMap.get("DIANLI_CNT")!=null?isCalcMap.get("DIANLI_CNT").toString():"0");
			dataMap.put("DISPATCH_CNT", isCalcMap.get("DISPATCH_CNT")!=null?isCalcMap.get("DISPATCH_CNT").toString():"0");
			//计薪人员数
			paCalcMap = (LinkedHashMap)hrReportDao.getEmpCalPaCntByDisType(paramMap) ;
			dataMap.put("PA_CNT", paCalcMap.get("PA_CNT")!=null?paCalcMap.get("PA_CNT").toString():"0");
			
			//加入到returnList中
			returnList.add(dataMap);
		}
		
		return returnList ;
	}
	
	/**
	 * 员工在离职查询(query the emp status info)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalStatusInfoList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo()!=null?admin.getUserNo().toString():admin.getUsername());
		String deptno = request.getParameter("seach_DEPTNO_STATUS")!=null?request.getParameter("seach_DEPTNO_STATUS").toString():"";
		if(deptno==null || "".equals(deptno)){
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				hrReportDao.getPersonalStatusInfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = hrReportDao.getPersonalStatusInfoList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 员工在离职查询数量(query the emp status info count)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonalStatusInfoListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo()!=null?admin.getUserNo().toString():admin.getUsername());
		String deptno = request.getParameter("seach_DEPTNO_STATUS")!=null?request.getParameter("seach_DEPTNO_STATUS").toString():"";
		if(deptno==null || "".equals(deptno)){
			return 0;
		}
		returnInt = hrReportDao.getPersonalStatusInfoListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 员工在离职查询，Excel导出用(query the emp status info)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalStatusInfoExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo()!=null?admin.getUserNo().toString():admin.getUsername());
		if(request.getParameter("seach_DEPTNO_STATUS")==null || "".equals(request.getParameter("seach_DEPTNO_STATUS")!=null?request.getParameter("seach_DEPTNO_STATUS").toString():"")){
			return returnList;
		}
		returnList = hrReportDao.getPersonalStatusInfoList(paramMap) ;
		return returnList ;
	}
}
