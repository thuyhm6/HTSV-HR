package com.ait.ar.action.attendanceMintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArMacMasterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.exception.ConfigurationException;
import com.ait.web.util.IthrC01ToPortalIfUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: arMacMasterCtroller.java
 * @Description:
 * @Create date: 2013-8-26 下午15:49:39
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArMacMasterCtroller {
	Logger logger = Logger.getLogger(ArMacMasterCtroller.class);
	@Autowired
	private ArMacMasterSer arMacMasterSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/*------------------刷卡机--人事信息--接口-----------begin-----------*/
	/**
	 * 刷卡机--人事Master接口(hr master)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArHrmMasterSendList")
	public ModelAndView viewArHrmMasterSendList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
	   AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	   List hrmMasterList = (ArrayList)this.arMacMasterSer.getArHrmMasterList(request);
	   int hrmMasterListCnt = this.arMacMasterSer.getArHrmMasterListCnt(request);
	   
	   modelMap.put("hrmMasterList",hrmMasterList);
	   modelMap.put(UiUtil.TOTAL_COUNT_NAME,hrmMasterListCnt);
	   
	   modelMap.put("defaultCpny", admin.getCpnyId().toString()) ;
	   modelMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"") ;
	   modelMap.put("COMPANY_NAME", admin.getCpnyName()!=null?admin.getCpnyName().toString():"") ;
	 
	   modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "494")) ;
	   
		return new ModelAndView("/ar/attendanceMintenance/viewArHrmMasterSendList",modelMap);
	}
	
	/**
	 * 刷卡机--人事Master信息导出页面(export the info about the interface)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArHrmMasterExcel")
	public ModelAndView viewArHrmMasterExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List hrmMasterList = this.arMacMasterSer.getArHrmMasterExcelList(request);
		modelMap.put("hrmMasterList",hrmMasterList);
		
		return new ModelAndView("/ar/attendanceMintenance/viewArHrmMasterExcel", modelMap);
	}
	
	/**
	 * 刷卡机--人事Master接口执行（手动的，页面控制）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addHrmMasterInfo")
	@ResponseBody
	public String addHrmMasterInfo(HttpServletRequest request)throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId().toString():"A");//默认为系统自动更新auto
		String result = "";
		result = this.arMacMasterSer.addHrmMasterInfo(paramMap);
		//插入接口执行日志
		paramMap.put("IF_TYPE", "HR");//更新人事部分
		paramMap.put("SEND_TYPE", "H");//手动更新
		paramMap.put("SEND_RESULT", result);
		//如果日志更新成功返回Y(都成功)，返回E则是更新日志失败
		result = this.arMacMasterSer.addArMacLogInfo(paramMap);
		
		return result;		
	}
	
	/**
	 * 刷卡机--人事Master接口执行（自动）
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public void doArHrmMasterInfo(String cpny_id){
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", cpny_id);
		paramMap.put("MAS_SEND_TYPE", "N");//自动任务只更新有信息修改的人员
		paramMap.put("CREATED_BY", "system");//自动更新auto时是系统自动任务
		
		String result = "";
		result = this.arMacMasterSer.addHrmMasterInfo(paramMap);
		//插入接口执行日志
		paramMap.put("IF_TYPE", "HR");//更新人事部分
		paramMap.put("SEND_TYPE", "A");//自动更新
		paramMap.put("SEND_RESULT", result);
		//如果日志更新成功返回Y(都成功)，返回E则是更新日志失败
		result = this.arMacMasterSer.addArMacLogInfo(paramMap);
	}
	/*------------------刷卡机--人事信息--接口-----------end-----------*/
	
	/*------------------刷卡机--刷卡数据信息--接口-------begin-----------*/
	/**
	 * 考勤机--读取刷卡数据（get the ar card data from mac）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArCardRecordSendList")
	public ModelAndView viewArCardRecordSendList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List cardMacList = this.arMacMasterSer.getArCardMacNoList(request);
		
		List cardRecordList = this.arMacMasterSer.getArCardRecordList(request);
	    int cardRecordListCnt = this.arMacMasterSer.getArCardRecordListCnt(request);
	   
	    modelMap.put("cardMacList",cardMacList);
	    modelMap.put("cardRecordList",cardRecordList);
	    modelMap.put(UiUtil.TOTAL_COUNT_NAME,cardRecordListCnt);
	   
	    modelMap.put("defaultCpny", admin.getCpnyId().toString()) ;
	    modelMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"") ;
	    modelMap.put("COMPANY_NAME", admin.getCpnyName()!=null?admin.getCpnyName().toString():"") ;
		 
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "495")) ;
		
		return new ModelAndView("/ar/attendanceMintenance/viewArCardRecordSendList",modelMap);
	}
	
	/**
	 * 考勤机--读取刷卡数据（get the ar card data from mac），导出用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArCardRecordExcel")
	public ModelAndView viewArCardRecordExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List cardRecordList = this.arMacMasterSer.getArCardRecordExcelList(request);
		modelMap.put("cardRecordList",cardRecordList);
		
		return new ModelAndView("/ar/attendanceMintenance/viewArCardRecordExcel", modelMap);
	}
	
	/**
	 * 刷卡机--刷卡数据读取（手动的，页面控制）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCardRecordInfo")
	@ResponseBody
	public String addCardRecordInfo(HttpServletRequest request)throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		//读取刷卡数据的操作人（发送状态更新时用到），默认为A（自动--定时任务执行的）
		paramMap.put("SEND_MAKED", admin.getPersonId()!=null?admin.getPersonId().toString():"A");
		//默认查询未读取过的刷卡数据
		if(paramMap.get("READ_TYPE")==null || "".equals(paramMap.get("READ_TYPE").toString())){
			paramMap.put("READ_TYPE", "N");
		}
		String result = "";
		result = this.arMacMasterSer.addCardRecordInfo(paramMap);
		//插入接口执行日志
		paramMap.put("IF_TYPE", "AR");//更新人事部分
		paramMap.put("SEND_TYPE", "H");//手动更新
		paramMap.put("SEND_RESULT", result);
		//如果日志更新成功返回Y(都成功)，返回E则是更新日志失败
		result = this.arMacMasterSer.addArMacLogInfo(paramMap);
		return result;		
	}
	
	/**
	 * 刷卡机--刷卡数据读取（自动）
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public void doArCardRecordInfo(String cpny_id){
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", cpny_id);
		paramMap.put("READ_TYPE", "N");//自动任务只更新有信息修改的人员
		paramMap.put("SEND_MAKED", "system");
		paramMap.put("CREATED_BY", "system");//自动更新auto时是系统自动任务
		
		String result = "";
		result = this.arMacMasterSer.addCardRecordInfo(paramMap);
		//插入接口执行日志
		paramMap.put("IF_TYPE", "AR");//更新人事部分
		paramMap.put("SEND_TYPE", "A");//自动更新
		paramMap.put("SEND_RESULT", result);
		//如果日志更新成功返回Y(都成功)，返回E则是更新日志失败
		result = this.arMacMasterSer.addArMacLogInfo(paramMap);
	}
	/*------------------刷卡机--刷卡数据信息--接口--------end----------*/
	
	/*------------------刷卡机--部门数据信息--接口--------begin----------*/
	/**
	 * 刷卡机--部门数据读取（自动）
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public void doArDeptMasterInfo(String cpny_id){
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", cpny_id);
		paramMap.put("MAS_SEND_TYPE", "N");//自动任务只更新有信息修改的部门
		paramMap.put("CREATED_BY", "system");//自动更新auto时是系统自动任务
		
		String result = "";
		result = this.arMacMasterSer.addDeptMasterInfo(paramMap);
		//插入接口执行日志
		paramMap.put("IF_TYPE", "DEPT");//更新部门部分
		paramMap.put("SEND_TYPE", "A");//自动更新
		paramMap.put("SEND_RESULT", result);
		//如果日志更新成功返回Y(都成功)，返回E则是更新日志失败
		result = this.arMacMasterSer.addArMacLogInfo(paramMap);
	}
	/*------------------刷卡机--部门数据信息--接口--------end----------*/
	
	
	/* ------------标准考勤机指纹信息----------------begin-------------- */
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArFingerPrintList")
	public ModelAndView viewArFingerPrintList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		List fingerPringList = this.arMacMasterSer.getArFingerPrintList(request);
	    int fingerPringListCnt = this.arMacMasterSer.getArFingerPrintListCnt(request);
	   
	    modelMap.put("fingerPringList",fingerPringList);
	    modelMap.put(UiUtil.TOTAL_COUNT_NAME,fingerPringListCnt);
	   
	    modelMap.put("defaultCpny", admin.getCpnyId().toString()) ;
	    modelMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"") ;
	    modelMap.put("COMPANY_NAME", admin.getCpnyName()!=null?admin.getCpnyName().toString():"") ;
		 
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "495")) ;
		
		return new ModelAndView("/ar/attendanceMintenance/viewArFingerPrintList",modelMap);
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac），导出用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArFingerPrintExcel")
	public ModelAndView viewArFingerPrintExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List fingerPringList = this.arMacMasterSer.getArFingerPrintExcelList(request);
		modelMap.put("fingerPringList",fingerPringList);
		
		return new ModelAndView("/ar/attendanceMintenance/viewArFingerPrintExcel", modelMap);
	}
	/* ------------标准考勤机指纹信息----------------end-------------- */
}
