package com.ait.ess.action;

import java.util.LinkedHashMap;
import com.ait.ess.dao.InfoApplyLeaveDao;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ait.ess.service.EssEmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.DateUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 员工信息查看
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: EssEmpInfoCtroller.java
 * @Description:
 * @Create date: Feb 28, 2012 10:23:59 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 28, 2012 10:23:59 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/empinfo")
public class EssApplyInfoCtroller{
	Logger logger = Logger.getLogger(EssApplyInfoCtroller.class);
	@Autowired
	private EssEmpInfoSer empInfoSer;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;

	// 测试修改菜单
	@Autowired
	private ToolMenuSer toolMenuSer;

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	
	/**
	 * 查看员工基础信息（工作信息）(view Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEssApplyInfo")
	public ModelAndView viewEssPersonalInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		logger.info("viewEssApplyInfo.start...");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("sign", "1");
		modelMap.put("activity_type", request.getParameter("ACTIVITY"));
		
        
        if(request.getParameter("seach_sDate")==null||request.getParameter("seach_sDate")==""){
        	
        	modelMap.put("sDate","");
            modelMap.put("eDate","");
        }else{
        	modelMap.put("sDate",request.getParameter("seach_sDate"));
            modelMap.put("eDate",request.getParameter("seach_eDate"));
        	
        }
        

        if(request.getParameter("seach_ACTIVITY")==null||request.getParameter("seach_ACTIVITY")==""){
        	
        	
            modelMap.put("ACTIVITY","");
        }else{
        
            modelMap.put("ACTIVITY",request.getParameter("seach_ACTIVITY"));
        	
        }
		
		modelMap.put("personalApplyList", empInfoSer.getPersonalApplyList(request));

		modelMap.put("addressApplyList", empInfoSer.getEssAddressApplyList(request));
		modelMap.put("emergencyList", empInfoSer.getEssEmergencyList(request));
		modelMap.put("homeRelationApplyList", empInfoSer.getEssHomeRelationApplyList(request));
		modelMap.put("workApplyList", empInfoSer.getEssWorkApplyList(request));
		modelMap.put("productApplyList", empInfoSer.getEssProductApplyList(request));
		modelMap.put("educationApplyList", empInfoSer.getEssEducationApplyList(request));
		modelMap.put("qualificationApplyList", empInfoSer.getEssQualificationApplyList(request));
		modelMap.put("rewardList", empInfoSer.getEssRewardList(request));
		modelMap.put("getLocalName", admin.getLocalName());
		return new ModelAndView("/ess/empinfo/viewEssApplyInfo", modelMap);
	}
	
	/*
	 * viewApply  start 九个页面！！
	 */
	
	@RequestMapping(value = "/viewApplyAddressInfo")
	public ModelAndView viewApplyAddressInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());

		modelMap.put("AddressList", empInfoSer.getEssAddressApplyList(request));
  
		return new ModelAndView("/ess/empinfo/viewApplyAddressInfo", modelMap);
	}
	@RequestMapping(value = "/viewApplyPersonalInfo")
	public ModelAndView viewApplyPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("LOCAL_NAME", admin.getLocalName());
		modelMap.put("personalInfo", empInfoSer.getPersonalApplyObject(request));
  
		return new ModelAndView("/ess/empinfo/viewApplyPersonalInfo", modelMap);
	}
	@RequestMapping(value = "/viewApplyEducationInfo")
	public ModelAndView viewApplyEducationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("educationInfoList", empInfoSer.getEssEducationApplyObject(request));
		
		 Map list =(Map) empInfoSer.getEssEducationApplyObject(request);
			
			//String APPLY_TYPE_NUM= list.get("APPLY_TYPE_NUM").toString(); 判断是否是删除
			 
			 String file= (String)list.get("FILENOSSTR");
			 if(file!=null){
			 String[] FILENOSSTR =file.split(",");
			 for(int i =0;i<FILENOSSTR.length-1;i++){
				 LinkedHashMap fileParam = new LinkedHashMap();
					fileParam.put("APPLY_TYPE", "hrEducation");
					fileParam.put("FILE_NO", FILENOSSTR[i]);
					List fileLists = infoApplyLeaveDao.getEssFileList(fileParam);
					modelMap.put("fileList", fileLists);
			 	}
			 }

		return new ModelAndView("/ess/empinfo/viewApplyEducationInfo", modelMap);
	}
	@RequestMapping(value = "/viewApplyEmergencyAddress")
	public ModelAndView viewApplyEmergencyAddress(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("EmergencyAddressList", empInfoSer.getEssEmergencyList(request));

		return new ModelAndView("/ess/empinfo/viewApplyEmergencyAddress", modelMap);
	}
	@RequestMapping(value = "/viewApplyHomeRelation")
	public ModelAndView viewApplyHomeRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("homeRelationList", empInfoSer.getEssHomeRelationApplyList(request));

		
		return new ModelAndView("/ess/empinfo/viewApplyHomeRelation", modelMap);
	}
	@RequestMapping(value = "/viewApplyProductInfo")
	public ModelAndView viewApplyProductInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("ProductInfoList", empInfoSer.getEssProductApplyObject(request));

		return new ModelAndView("/ess/empinfo/viewApplyProductInfo", modelMap);
	}
	@RequestMapping(value = "/viewApplyQualificationInfo")
	public ModelAndView viewApplyQualificationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("qualificationInfo", empInfoSer.getEssQualificationApplyObject(request));
		

		 Map list =(Map) empInfoSer.getEssQualificationApplyObject(request);
			
			//String APPLY_TYPE_NUM= list.get("APPLY_TYPE_NUM").toString(); 判断是否是删除
			 
			 String file= (String)list.get("FILENOSSTR");
			 if(file!=null){
			 String[] FILENOSSTR =file.split(",");
			 for(int i =0;i<FILENOSSTR.length-1;i++){
				 LinkedHashMap fileParam = new LinkedHashMap();
					fileParam.put("APPLY_TYPE", "hrEducation");
					fileParam.put("FILE_NO", FILENOSSTR[i]);
					List fileLists = infoApplyLeaveDao.getEssFileList(fileParam);
					modelMap.put("fileList", fileLists);
			 	}
			 }



		return new ModelAndView("/ess/empinfo/viewApplyQualificationInfo", modelMap);
	}
	@RequestMapping(value = "/viewApplyRewardInfo")
	public ModelAndView viewApplyRewardInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("rewardInfo", empInfoSer.getEssRewardObject(request));

		return new ModelAndView("/ess/empinfo/viewApplyRewardInfo", modelMap);
	}
	@RequestMapping(value = "/viewApplyWorkInfo")
	public ModelAndView viewApplyWorkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("workExperienceList", empInfoSer.getEssWorkApplyObject(request));

		return new ModelAndView("/ess/empinfo/viewApplyWorkInfo", modelMap);
	}
	
	/*
	 * viewApply end
	 */
	
	
	
}
