package com.ait.report.hr.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.dao.EmpInfoDao;
import com.ait.report.ar.service.ArReportSer;
import com.ait.report.hr.dao.HrReportDao;
import com.ait.report.hr.service.HrReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.task.uploadHrToSapIFData;
import com.ait.web.util.IthrToPortalIfUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportCtroller.java
 * @Description: Controller Class HrReportCtroller.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/report/hr")
public class HrReportCtroller {
	Logger logger = Logger.getLogger(HrReportCtroller.class);
	@Autowired
	private HrReportSer hrReportSer;
    @Autowired
    private ArReportSer arReportSer;
    @Autowired
	private ToolMenuSer toolMenuSer;
    @Autowired
	private EmpInfoDao empInfoDao;
    @Autowired
	private HrReportDao hrReportDao;
    
    public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/{reportType}",method = RequestMethod.GET)
	public void getReport(){}
	
	/**
	 * 预览
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewReport",method = RequestMethod.POST)
	@ResponseBody
	public Map viewReport(HttpServletRequest request) throws Exception{
		logger.info("View Report...");
		return hrReportSer.viewReport(request);
	}
	
	/**
	 * 页面导出
	 */
	@RequestMapping(value = "/{reportType}",method = RequestMethod.POST)
	public ModelAndView expReport(HttpServletRequest request,ModelMap map) throws Exception{
		logger.info("Exp Report...");
		map.put("info", hrReportSer.viewReport(request));
		return new ModelAndView("",map);
	}
	
	/**
	 * 预览图表
	 */
	@RequestMapping(value = "/humanChart",method = RequestMethod.GET)
	public ModelAndView viewReportChart(HttpServletRequest request,ModelMap modelMap) throws Exception{
		String chart = hrReportSer.viewReportChart(request);
		modelMap.put("chart", chart);
		return new ModelAndView("/report/hr/humanChart",modelMap);
	}
	
	/**
	 * 人事报表 (HR report)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHrmReportsList")
	public ModelAndView viewHrmReportsList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List codeInfoTreeList =  this.arReportSer.getCodeListByParentCode(request,"22115") ;   //人事报表类型code_no
		List reportList =  this.arReportSer.getreportList(request) ;   //报表
		modelMap.put("codeInfoTreeList", codeInfoTreeList);
		modelMap.put("reportList", reportList);
		return new ModelAndView("/report/hr/viewHrmReportsList",modelMap);
	}
	
	/**
	 * 跳转到人事记录卡查询页面(jump the page for query the person info recode card)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonRecodeInfo")
	public ModelAndView viewPersonRecodeInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/report/hr/viewPersonRecodeInfo",modelMap);
	}
	
	/**
	 * 人事记录卡查询页面(person info recode card)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonRecodeList")
	public ModelAndView viewPersonRecodeList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		modelMap.put("IDCARD_NO", request.getParameter("seach_IDCARD_NO"));
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.getParameter("pageNum"):"1");
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.getParameter("numPerPage"):"10");
		
		modelMap.put("personEmpList",this.hrReportSer.getPersonRecodeList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.hrReportSer.getPersonRecodeListCnt(request));
		
		return new ModelAndView("/report/hr/viewPersonRecodeList",modelMap);
	}
	
	/**
	 * 人事记录卡(person record info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonRecodeCard")
	public ModelAndView viewPersonRecodeCard(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String person_id = request.getParameter("seach_PERSON_ID")!=null?request.getParameter("seach_PERSON_ID").toString():"";
		//如果person_id为空，人事记录卡为空
		if("".equals(person_id) || person_id==null){
			return new ModelAndView("/report/hr/viewPersonRecodeCard",modelMap);
		}
		LinkedHashMap linkMap = (LinkedHashMap)this.hrReportSer.getPersonRecordByPid(request);
		modelMap.put("personRecord",linkMap);
		
		modelMap.put("educationList",this.hrReportSer.getRecordEducationList(request));
		modelMap.put("educationListCnt",this.hrReportSer.getRecordEducationList(request).size());
		modelMap.put("contractList",this.hrReportSer.getRecordContractList(request));
		modelMap.put("contractListCnt",this.hrReportSer.getRecordContractList(request).size());
		modelMap.put("expInList",this.hrReportSer.getRecordExperienceInsideList(request));
		modelMap.put("expInListCnt",this.hrReportSer.getRecordExperienceInsideList(request).size());
		modelMap.put("expOutList",this.hrReportSer.getRecordExperienceOutsideList(request));
		modelMap.put("expOutListCnt",this.hrReportSer.getRecordExperienceOutsideList(request).size());
		modelMap.put("familyList",this.hrReportSer.getRecordFamilyList(request));
		modelMap.put("familyListCnt",this.hrReportSer.getRecordFamilyList(request).size());
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		modelMap.put("photoId", "viewPersonRecodeCard");
		
		return new ModelAndView("/report/hr/viewPersonRecodeCard",modelMap);
	}
	
	/**
	 * 员工在职证明(Certificate of Employment)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCertificateEmploymentInfo")
	public ModelAndView viewCertificateEmploymentInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//modelMap.put("certificateEmpList",this.hrReportSer.getCertificateEmploymentList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.hrReportSer.getCertificateEmploymentListCnt(request));
		return new ModelAndView("/report/hr/viewCertificateEmploymentInfo",modelMap);
	}
	
	/**
	 * 员工在职证明(Certificate of Employment)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCertificateList")
	public ModelAndView viewCertificateList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.getParameter("pageNum"):"1");
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.getParameter("numPerPage"):"10");
		
		modelMap.put("certificateEmpList",this.hrReportSer.getCertificateEmploymentList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.hrReportSer.getCertificateEmploymentListCnt(request));
		
		return new ModelAndView("/report/hr/viewCertificateList",modelMap);
	}
	
	/**
	 * 员工在职证明(Certificate of Employment)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCertificateEmploymentExcel")
	public ModelAndView viewCertificateEmploymentExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap linkMap = (LinkedHashMap)this.hrReportSer.getSysdate(request);
		modelMap.put("YEAR", linkMap.get("YEA").toString());
		modelMap.put("MONTH", linkMap.get("MON").toString());
		modelMap.put("DDATE", linkMap.get("DAT").toString());
		modelMap.put("DATE", linkMap.get("DDATE").toString());
		
		modelMap.put("COMPANY_NAME", admin.getCpnyName());
		modelMap.put("certificateEmpList",this.hrReportSer.getCertificateEmploymentExcelList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.hrReportSer.getCertificateEmploymentListCnt(request));
		
		return new ModelAndView("/report/hr/viewCertificateEmploymentExcel",modelMap);
	}
	
	/**
	 * 跳转到人事登记卡查询页面C11(jump the page for query the person info recode card)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/viewHrRegisterInfo")
	public ModelAndView viewHrRegisterInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		return new ModelAndView("/report/hr/viewHrRegisterInfo",modelMap);
	}
	
	/**
	 * C11人事登记卡，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHrRegisterListTranserExcel")
	public ModelAndView viewPaPayOffStatusAddupTranserExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List listInfo = this.hrReportSer.getPersonBasicInfo(request);
		List personBasicInfo = new ArrayList();
		for (int i = 0; i < listInfo.size(); i++) {
			HashMap map =(HashMap) listInfo.get(i);
			String personId = map.get("PERSON_ID").toString();
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("PERSON_ID", personId);
			List languageList = empInfoDao.getLanguageLevelList(paramMap);
			List educationList = hrReportDao.getRecordEducationList(paramMap);
			List qualificationList = empInfoDao.getQualificationList(paramMap);
			List homeRelationList = empInfoDao.getHomeRelationList(paramMap);
			List workExperienceList = empInfoDao.getWorkExperienceList(paramMap);
			
			map.put("languageList", languageList);
			map.put("languageListCnt", languageList.size()-3>0?3:languageList.size());
			map.put("educationList",educationList);
			map.put("educationListCnt", educationList.size()-4>0?4:educationList.size());
			map.put("qualificationList", qualificationList);
			map.put("qualificationListCnt", qualificationList.size()-3>0?3:qualificationList.size());
			map.put("homeRelationList", homeRelationList);
			map.put("homeRelationListCnt", homeRelationList.size()-3>0?3:homeRelationList.size());
			map.put("workExperienceList", workExperienceList);
			map.put("workExperienceListCnt", workExperienceList.size()-5>0?5:workExperienceList.size());
			personBasicInfo.add(map);
		}
		modelMap.put("personBasicInfo", personBasicInfo);

//		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
//		modelMap.put("personBasicInfo", linkMap);

		return new ModelAndView("/report/hr/viewHrRegisterListTranserExcel",modelMap);
	}
	
	/**
	 *  HRTOSAP 奖金
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDateForSapMoneyAwardInterface")
	@ResponseBody
	public String addPortalInfo4(HttpServletRequest request)
			throws Exception {
		String result = "Y";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
	   	List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getMoneyAwardForSap(request) ;
	   	
	   	Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String dateStrm = (new SimpleDateFormat("hh:mm:ss")).format(date);
		String dateStrm1 = dateStrm.replaceAll(":", "");
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
		//本地测试用地址
	    String portalPath = "E:";

  	 	portalMap.put("HR_MONEYAWARDFORSAP_1001_", empList);
  	 	String cpnyId = admin.getCpnyId();
		
	 	IthrToPortalIfUtil ifUtil = new IthrToPortalIfUtil(portalPath,portalMap,cpnyId);
	 	
	 	List<String> resultList = ifUtil.makeTxtForSapInterface("FI_BO"+dateStr+dateStrm1+".txt");
	 	request.setAttribute("portalResultList", resultList);

		return result;		
	}*/
}
