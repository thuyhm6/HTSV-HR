package com.ait.ess.action;

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

import com.ait.ar.dao.ArMonthCalculateDao;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.ess.service.ArMacRecordApplySer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.PersonInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMacRecordApplyCtroller.java
 * @Description:
 * @Create date: 2013-8-26 下午15:49:39
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/recordApply")
public class ArMacRecordApplyCtroller {
	Logger logger = Logger.getLogger(ArMacRecordApplyCtroller.class);
	
	@Autowired
	PersonInfoSer personInfoSer;
	@Autowired
	EmpInfoSer empInfoSer;
	@Autowired
	ArMacRecordApplySer arMacRecordApplySer;
	@Autowired
	InfoApplySer infoApplySer;
	@Autowired
	private AffirmLeaveApplySer affirmApplySer;
	@Autowired
	DynamicGroupSer dynamicGroupSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	@Autowired
	private ArMonthCalculateDao arMonthCalculateDao;
	
	@SuppressWarnings("unused")
	@Autowired
	private ToolMenuSer toolMenuSer;
	

	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private CycleSer cycleSer ;
	/*------------------ess-------in/out进出门刷卡数据申请----------start----------*/
	/**
	 * 显示刷卡申请页面(view mac record apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArCardRecordApplyView")
	public ModelAndView addArCardRecordApplyView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("APPLY_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("R_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		List affirmorList = this.arMacRecordApplySer.getAffirmorList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("arGetMacApplyCnt", infoApplySer.arGetMacApplyCnt(request));
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		
		return new ModelAndView("/ess/recordApply/addArCardRecordApplyView", modelMap);
	}

	/**
	 * 刷卡申请 (add mac record apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addArMacRecordApply")
	@ResponseBody
	public Map addArMacRecordApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			
			paramMap.put("MONTHSTR",this.infoApplyLeaveDao.getCurrentMonth(paramMap));
			
			String flag = infoApplyLeaveDao.arValidLastMonth(paramMap);
			if("OK".equals(flag)){
				int result = arMacRecordApplySer.addArMacRecordApply(request);
					if (result == 1) {
						map.put("navTabId", "ess0204");
						map.put("message", "刷卡申请成功!");//刷卡申请成功!
						map.put("statusCode", "200");
						map.put("callbackType", "closeCurrent");
					}
			}else{
				map.put("message", flag); 
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "刷卡申请保存出错,请重新申请!");//刷卡申请保存出错,请重新申请!
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 查看审批者信息(view affirmor information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArMacRecordAffirmor")
	public ModelAndView viewArMacRecordAffirmor(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List affirmorList = this.arMacRecordApplySer.getAffirmorList(request);
		modelMap.put("affirmorList", affirmorList);
		return new ModelAndView(
				"/ess/recordApply/viewArMacRecordAffirmor", modelMap);
	}
	
	/**
	 * 显示批量刷卡数据申请(view batch mac record apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArMacRecordBatchApplyPersonList")
	public ModelAndView viewArMacRecordBatchApplyPersonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("BATCH_APPLY_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("BATCH_R_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		List list = arMacRecordApplySer.getPersonList(request);
		modelMap.put("personList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.arMacRecordApplySer.getPersonListCnt(request));
 		
		return new ModelAndView("/ess/recordApply/viewArMacRecordBatchApplyPersonList",
				modelMap);
	}

	/**
	 * 批量刷卡申请 (add batch mac record apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBatchArMacRecordApply")
	@ResponseBody
	public Map addBatchOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = arMacRecordApplySer.addArMacRecordApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0204");
				map.put("message", "批量添加漏刷卡申请成功！");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量添加漏刷卡申请失败，请重试！");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	/*------------------ess-------in/out进出门刷卡数据---申请----------end----------*/
	
	/*------------------ess-------in/out进出门刷卡数据---审批----------start----------*/
	/**
	 * 进出门刷卡申请审批列表(ar mac record apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMacRecordAffirmList")
	public ModelAndView viewArMacRecordAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("loginUser", admin.getPersonId()!=null?admin.getPersonId().toString():admin.getUsername().toString());
		modelMap.put("arMacRecordList", this.arMacRecordApplySer.getArMacRecordAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.arMacRecordApplySer.getArMacRecordAffirmListCnt(request));

		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
			modelMap.put("AFFIRM_FLAG", "10");
		}
		return new ModelAndView("/ess/recordApply/viewArMacRecordAffirmList",
				modelMap);
	}
	
	
	/**
	 * 进出门刷卡申请Check列表(ar mac record apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMacRecordCheckAffirmList")
	public ModelAndView viewArMacRecordCheckAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List maclist = arMacRecordApplySer.getArMacRecordAffirmCheckViewList(request);
		modelMap.put("arMacRecordList", maclist);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacRecordAffirmCheckViewListCnt(request));
		
		return new ModelAndView("/ess/recordApply/viewArMacRecordCheckAffirmList",
				modelMap);
	}
	
	/**
	 * check漏刷卡信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkApplyCheckInfo")
	public ModelAndView viewcheckApplyCheckInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List applyorList = arMacRecordApplySer.getArMacRecordAffirmViewListBySingle(request);
		//is_check 用来判断查看或提交权限
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());

		Map recordInfo = null;
		if(applyorList != null && applyorList.size() > 0){
			recordInfo = (Map)applyorList.get(0);
			modelMap.put("RecordInfo", applyorList.get(0));
		}
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		String essCheckNo = "";
		//找到需要审批的审批编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(admin.getPersonId().equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("is_check", is_check);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( recordInfo != null  && "Y".equals(StringUtil.checkNull(recordInfo.get("BATCH_YN")))){
			modelMap.put("arMacBatchAffirmList", arMacRecordApplySer.getArMacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/ess/recordApply/checkApplyCheckInfo", modelMap);
	}
	/**
	 * 通过/否决进/出门刷卡申请(pass or reject ar mac record Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveArMacRecordApply")
	@ResponseBody
	public Map<String, Object> approveArMacRecordApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = arMacRecordApplySer.approveArMacRecordApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0209");
				map.put("message", "漏刷卡审批成功！"); 
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "漏刷卡审批出错，请重新操作！");//"加班申请审批出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量通过/否决进/出门刷卡申请(batch pass or reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveArMacRecordApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveArMacRecordApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = arMacRecordApplySer.approveArMacRecordApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0209");
				map.put("message", "审批成功");//"批量进/出门刷卡申请审批成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "审批失败");//"批量进/出门刷卡申请审批出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * in/out进出门刷卡数据信息申请(view ar mac record information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMacRecordInfoList")
	public ModelAndView viewArMacRecordInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		List maclist = arMacRecordApplySer.getArMacRecordAffirmViewList(request,"N");
		modelMap.put("arMacRecordList",maclist );	
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacRecordAffirmViewListCnt(request,"N"));
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "68"));
		
		return new ModelAndView("/ess/recordApply/viewArMacRecordInfoList", modelMap);
	}
	
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delArMacRecordApplyInfo")
	@ResponseBody
	public Map<String, Object> delArMacRecordApplyInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = arMacRecordApplySer.delArMacRecordApplyInfo(request);
		if (result) {
			map.put("navTabId", "ess0204");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}
	/*------------------ess-------in/out进出门刷卡数据审批----------end----------*/
	
	
	/**
	 * 查看完整漏刷卡事由信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMACContentInfo")
	public ModelAndView viewMACContentInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		 
		List maclist = arMacRecordApplySer.getArMacRecordAffirmViewList(request,"");
		if(maclist != null && maclist.size() > 0){
			LinkedHashMap applyMap = (LinkedHashMap)maclist.get(0);
			modelMap.put("APPLY_NO", applyMap.get("APPLY_NO").toString());
			modelMap.put("APPLY_REMARK", StringUtil.checkNull(applyMap.get("REMARK")));
		}
		
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/ess/recordApply/viewMACContentInfo", modelMap);
	}
	
	
	/**
	 * 查看完整审批详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyAffirmInfo")
	public ModelAndView viewFullApplyAffirmInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		

		Map recordInfo = arMacRecordApplySer.getArMacRecordApplyInfoForDisplay(request);
		
		modelMap.put("arMacRecord", recordInfo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( recordInfo != null  && "Y".equals(StringUtil.checkNull(recordInfo.get("BATCH_YN")))){
			modelMap.put("arMacBatchAffirmList", arMacRecordApplySer.getArMacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/ess/recordApply/viewFullApplyAffirmInfo", modelMap);
	}
	/**(view leave information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMACApplyAffirmorList")
	public ModelAndView viewMACApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		List applyorList = arMacRecordApplySer.getArMacRecordAffirmViewListBySingle(request);
		Map recordInfo = null;
		if(applyorList != null && applyorList.size() > 0){
			recordInfo = (Map)applyorList.get(0);
			modelMap.put("RecordInfo", applyorList.get(0));
		}
		modelMap.put("applyorList", applyorList);
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);

		String essAffirmNo = "";
		//找到需要审批的审批编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(admin.getPersonId().equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		if( recordInfo != null  && "Y".equals(StringUtil.checkNull(recordInfo.get("BATCH_YN")))){
			modelMap.put("arMacBatchAffirmList", arMacRecordApplySer.getArMacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/ess/recordApply/viewMACApplyAffirmorList", modelMap);
	}
	
	
	/**
	 * Check---漏刷卡申请  
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkApplyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map checkApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("漏刷卡申请Check成功！",request));//Check成功
			map.put("navTabId", "ess0310");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("漏刷卡申请Check失败！",request));//Check失败
		}
		return map;
	}
	
	/**
	 * 漏刷卡申请-上传附件
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadApplicationFile")
	public ModelAndView openFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView("/ess/recordApply/uploadApplicationFile",modelMap);
	}

	/**
	 * 批量漏刷卡
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArMacRecordBatchInfoList")
	public ModelAndView viewArMacRecordBatchInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);

		List maclist = arMacRecordApplySer.getArMacRecordAffirmViewList(request,"Y");
		modelMap.put("arMacRecordList",maclist );	
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacRecordAffirmViewListCnt(request,"Y"));
		
		return new ModelAndView("/ess/recordApply/viewArMacRecordBatchInfoList", modelMap);
	}
	
	/**
	 * 批量漏刷卡申请导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLArMacModule")
	public void exportBatchLArMacModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("申请人");
		aliasNameList.add("申请日期");
		aliasNameList.add("打卡时间");
		aliasNameList.add("进出门类型(IN/OUT)");
		aliasNameList.add("备注");
		aliasNameList.add(" ");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000003");
		map.put("CELL1", "张XX");
		map.put("CELL2", "2014-07-10");
		map.put("CELL3", "08:20");
		map.put("CELL4", "IN");
		map.put("CELL5", "忘打卡");
		map.put("CELL6", " ");
		list.add(map);
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"ess_mac_record");
	}
	

	/**
	 * 漏刷卡导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelEssArMacDataList")
	public ModelAndView viewImportExcelEssArMacDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paEssArMacTempList = this.arMacRecordApplySer.getEssArMacTempList(request);
		int paEssArMacTempCnt = this.arMacRecordApplySer.getEssArMacTempCnt(request , "T");
		int errorCnt = this.arMacRecordApplySer.getEssArMacTempCnt(request , "E");
		
		modelMap.put("paEssArMacTempList", paEssArMacTempList);
		modelMap.put("paEssArMacTempCnt", paEssArMacTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEssArMacTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paEssArMacTempCnt);
		return new ModelAndView("/ess/recordApply/viewImportExcelEssArMacDataList", modelMap);
	}
	

	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelEssArMacEmpData")
	@ResponseBody
	public Map submitImportExcelEssArMacEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.arMacRecordApplySer.submitImportExcelEssArMacEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "ess0301");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	

	/**
	 * 批量删除漏刷卡申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delArMacApplyInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delArMacApplyInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "删除";
		if("1".equals(op_flag)){
			msg = "提交";
		}
		try {
			result = arMacRecordApplySer.delArMacApplyInBatchForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
				map.put("message", msg + "成功！");//"批量删除休假申请审批成功!"
				map.put("statusCode", "200");
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} 
		map.put("result", result);
		return map;
	}
	

	/**
	 * 查看完整审批详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyAffirmInfo1")
	public ModelAndView viewFullApplyAffirmInfo1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		Map recordInfo = arMacRecordApplySer.getArMacRecordApplyInfoForDisplay(request);
			
		modelMap.put("arMacRecord", recordInfo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( recordInfo != null  && "Y".equals(StringUtil.checkNull(recordInfo.get("BATCH_YN")))){
			modelMap.put("arMacBatchAffirmList", arMacRecordApplySer.getArMacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/ess/recordApply/viewFullApplyAffirmInfo1", modelMap);
	}
	

	/**
	 * 批量加班审批者调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyAffirmorForBatchArMac")
	@ResponseBody
	public Map modifyAffirmorForBatchArMac(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifyAffirmorForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0301");
				map.put("message", "操作成功");//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "操作失败");
			map.put("statusCode", "300");
		}
		return map;
	}
	

	/**
	 * 取消已审核通过的漏刷卡申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelCardApply")
	@ResponseBody
	public String cancelCardApply(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = arMacRecordApplySer.cancelCardApply(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
}
