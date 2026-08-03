package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ShiftSer;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.service.AffirmApplySer;
import com.ait.ess.service.AnnualadjustmentInfoSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.promoter.service.PromoterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 信息决裁
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: AffirmApplyCtroller.java
 * @Description:
 * @Create date: Apr 6, 2012 12:32:56 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Apr 6, 2012 12:32:56 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/affirmApply")
public class AffirmApplyCtroller {

	Logger logger = Logger.getLogger(AffirmApplyCtroller.class);

	@Autowired
	private AffirmApplySer affirmApplySer;
	
	@Autowired
	private InfoApplyDao infoApplyDao;
	
	@Autowired
	private InfoApplyLeaveSer infoApplySerOt;
	@Autowired
	private InfoApplySer infoApplySer;
	
	@Autowired
	private EmpInfoSer empInfoSer;
	
	@Autowired
	private ShiftSer shiftSer;
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;
	
	@Autowired
	private AnnualadjustmentInfoSer annualadjustmentInfoSer;

	
	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private PromoterSer promoterSer;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOtAffirmList")
	public ModelAndView viewOtAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewOtAffirmList",modelMap);
	}
	
	/**
	 * 加班申请P决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPOtAffirmList")
	public ModelAndView viewPOtAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		 
		//List list = this.affirmApplySer.getOtAffirmListTwo(request);
		
		
		//modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmListF(request,list));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtAffirmListFinalCnt(request,list));
		modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		if(request.getParameter("parentParam") != null){
			String tempStr = StringUtil.checkNull(request.getParameter("parentParam")) ;
			String[] tempStrs =  tempStr.split("@");
			String[] tempStrsForMap = null ;
			for (int i = 0; i < tempStrs.length; i++) {
				if(tempStrs[i].indexOf("=") != -1){
					tempStrsForMap = tempStrs[i].split("=");
					if(tempStrsForMap.length == 2)
						modelMap.put(""+tempStrsForMap[0],tempStrsForMap[1] );
				}
			}
		}

		return new ModelAndView("/ess/affirmApply/viewPOtAffirmList",modelMap);
	}
	
	/**
	 * 加班申请L决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLOtAffirmList")
	public ModelAndView viewLOtAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request); 
		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("codeList", codeList);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		String firstFlag = request.getParameter("firstFlag");
//		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
//			modelMap.put("AFFIRM_FLAG", "14014307");
//		}else {
//			modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG"));
//		}
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_TIME")==""||request.getParameter("seach_FROM_TIME")==null )&& (request.getParameter("seach_TO_TIME")==""||request.getParameter("seach_TO_TIME")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_TIME",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_TIME",last);
			}
		}
		
//        if(firstFlag != null && !"".equals(firstFlag)){
        	modelMap.put("DEPTNO",request.getParameter("seach_DEPTNO"));
    		modelMap.put("SHIFT_NO",request.getParameter("seach_SHIFT_NO"));
    		modelMap.put("empName",request.getParameter("dwz.person.empName"));
    		modelMap.put("empInfo",request.getParameter("dwz.person.empInfo"));
    		modelMap.put("partYn",request.getParameter("seach_partYn"));
        	modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmList(request));
//		}else {
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
//		}
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		
		return new ModelAndView("/ess/affirmApply/viewLOtAffirmList",modelMap);
	}
	
	/**
	 * 年假调整 决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAnnualadjustmentAffirmList")
	public ModelAndView viewAnnualadjustmentAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		
		modelMap.put("annualadjustmentInfoList",annualadjustmentInfoSer.getAnnualadjustmentAffirmapplyList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getAnnualadjustmentAffirmapplyListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());

		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
			modelMap.put("AFFIRM_FLAG", "10");
		}
		return new ModelAndView("/ess/affirmApply/viewAnnualadjustmentAffirmList",modelMap);
	}
	
	/**
	 * 批量通过/否决加班申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveOvertimeApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveOvertimeApplyInBatch(request);
			if (result == 1) {
			
				map.put("navTabId", "ess3415");
				map.put("formId", "viewLOtAffirmList");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_success",request));
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量通过/否决P加班申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> approvePOvertimeApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveOvertimeApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0238");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_success",request));//"批量加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_fail",request));//"批量加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量通过/否决L加班申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveLOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveLOvertimeApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveOvertimeApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0239");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_success",request));//"批量加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_fail",request));//"批量加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量通过/否决年假调整申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appAnnualadjustmentApplyInBatch")
	@ResponseBody
	public Map<String, Object> appAnnualadjustmentApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.appAnnualadjustmentApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0214");
				map.put("message", "批量年假调整审批成功！");//"批量加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量年假调整决裁出错，请重新操作！");//"批量加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	
	
	/**
	 * 查看完整加班事由信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyAffirmorList")
	public ModelAndView viewFullApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplySerOt.getOtApplyPersonal(request);
		modelMap.put("infoApplyOt", infoApplyOt);
		List applyorList = infoApplySer.getApplyorByApplyNoList(request);
		String names = "";
		String name = "";
		String OT_TIME_TYPE = "";
		String APPLY_TYPE = "";
		for(int i=0;i<applyorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)applyorList.get(i);
			name = applyorMap.get("LOCAL_NAME").toString();
			OT_TIME_TYPE = applyorMap.get("OT_TIME_TYPE").toString();
			APPLY_TYPE = applyorMap.get("APPLY_TYPE").toString();
			names = name + ",";
		}
		
		names = names.substring(0, names.length()-1);
		modelMap.put("names", names);
		modelMap.put("OT_TIME_TYPE", OT_TIME_TYPE);
		modelMap.put("APPLY_TYPE", APPLY_TYPE);
		
		modelMap.put("applyorList", applyorList);
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		if(infoApplyOt != null && infoApplyOt.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyOt.get("APPLY_TYPE"))){
			modelMap.put("OtBatchAffirmList", infoApplySerOt.getOtBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySerOt.getOtBatchAffirmInfoCnt(request));

		}
		 
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("defaultCpny", admin.getCpnyId());
		if(request.getParameter("parentParam") != null){
			modelMap.put("parentParam", request.getParameter("parentParam"));
		}
		
		return new ModelAndView("/ess/affirmApply/viewFullApplyAffirmorList", modelMap);
	}
	
	
	/**
	 * 查看完整年假调整信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullAnnuApplyAffirmorList")
	public ModelAndView viewFullAnnuApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");

		LinkedHashMap annuMap = null;
		List annulist = annualadjustmentInfoSer.getAnnuapplyListByApplyno(request);
		if(annulist.size() > 0){
			annuMap = (LinkedHashMap)annulist.get(0);
			modelMap.put("annumap", annuMap);
		}
		modelMap.put("APPLY_NO",paramMap.get("APPLY_NO"));
		modelMap.put("PERSON_ID", admin.getPersonId());
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( annuMap != null  && "Y".equals(StringUtil.checkNull(annuMap.get("BATCH_YN")))){
			modelMap.put("arVacBatchAffirmList", annualadjustmentInfoSer.getArVacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getArVacBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/ess/affirmApply/viewFullAnnuApplyAffirmorList", modelMap);
	}
	
	
	@RequestMapping(value = "/viewAnnCheckList")
	public ModelAndView viewAnnCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List cwachecklist = this.annualadjustmentInfoSer.getAnnuCheckList(request);
		modelMap.put("annualadjustmentInfoList", cwachecklist);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.annualadjustmentInfoSer.getAnnuCheckListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/affirmApply/viewAnnCheckList",modelMap);
	}
	
	
	/**
	 * 决裁---加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOtAffirmInfo")
	@ResponseBody
	public Map addOtAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentParam", request.getParameter("parentParam")); 
		String flag = request.getParameter("AFFIRM_FLAG");
		String type2 = request.getParameter("ottime_type_wq");
		try {
			int result = affirmApplySer.approveOvertimeApply(request);
			if (result == 1) {
				//map.put("navTabId", "ess0214");
				if(type2.equals("L")){
					map.put("navTabId", "ess0239");
				}else if(type2.equals("P")){
					map.put("navTabId", "ess0238");
				}else{
					map.put("navTabId", "ess0214");
				}
				
				
				if(flag.equals("1")){
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.shenpi",request));//"保存加班申请成功!"

				}
				if(flag.equals("2")){
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.baocun",request));//"保存加班申请成功!"

				}
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.shenpi.failure",request));//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 查看-加班信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyCheckInfo")
	public ModelAndView viewFullApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List applyorList = infoApplySer.getApplyorByApplyNoList(request);
		String names = "";
		String name = "";
		LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplySerOt.getOtApplyPersonal(request);
		modelMap.put("infoApplyOt", infoApplyOt);
		
		modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmList(request));//加班决裁 
		for(int i=0;i<applyorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)applyorList.get(i);
			name = applyorMap.get("LOCAL_NAME").toString();
			names = name + ",";
		}
		names = names.substring(0, names.length()-1);
		modelMap.put("names", names);
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		if(infoApplyOt != null && infoApplyOt.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyOt.get("APPLY_TYPE"))){
			modelMap.put("OtBatchAffirmList", infoApplySerOt.getOtBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySerOt.getOtBatchAffirmInfoCnt(request));

		}
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/affirmApply/viewFullApplyCheckInfo", modelMap);
	}
	
	
	/**
	 * 查看-加班信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyCheckInfoList")
	public ModelAndView viewFullApplyCheckInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List applyorList = infoApplySer.getApplyorByApplyNoList(request);
		String names = "";
		String name = "";
		LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplySerOt.getOtApplyPersonal(request);
		modelMap.put("infoApplyOt", infoApplyOt);
		
		modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmList(request));//加班决裁 
		for(int i=0;i<applyorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)applyorList.get(i);
			name = applyorMap.get("LOCAL_NAME").toString();
			names = name + ",";
		}
		names = names.substring(0, names.length()-1);
		modelMap.put("names", names);
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		if(infoApplyOt != null && infoApplyOt.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyOt.get("APPLY_TYPE"))){
			modelMap.put("OtBatchAffirmList", infoApplySerOt.getOtBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySerOt.getOtBatchAffirmInfoCnt(request));

		}
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/affirmApply/viewFullApplyCheckInfoList", modelMap);
	}
	
	
	/**
	 * 添加check信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyRemarkInfo")
	public ModelAndView viewFullApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		//用于返回，找到应该刷新的页面
		
		if(paramMap.get("search_ot_time_type").equals("P")){
			modelMap.put("PAGE_FLAG", "P_OTAPPLY" );
		}else{
			modelMap.put("PAGE_FLAG", paramMap.get("PAGE_FLAG"));
		}
		 
		return new ModelAndView("/ess/affirmApply/viewFullApplyRemarkInfo", modelMap);
	}
	/**
	 * 添加check信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtApplyRemarkInfo")
	public ModelAndView viewOtApplyRemarkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		
		return new ModelAndView("/ess/affirmApply/viewOtApplyRemarkInfo", modelMap);
	}
	
	/**
	 * 决裁---加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addApplyCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addApplyCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		String page_flag = paramMap.get("PAGE_FLAG") == null ? "" : paramMap.get("PAGE_FLAG").toString();
		int result = affirmApplySer.addApplyCheckList(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("添加Checkor者成功！",request));//添加Checkor者成功！
			if("LEAVE_APPLY".equals(page_flag)){
				map.put("navTabId", "ess0242_affirm");
			}
			if("L_OTAPPLY".equals(page_flag)){
				map.put("navTabId", "ess0239_affirm");
			}
			if("P_OTAPPLY".equals(page_flag)){
				map.put("navTabId", "ess0238_affirm");
			}
			if("PROVEAPP".equals(page_flag)){
				map.put("navTabId", "proApp_3666");
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("添加Checkor者失败！",request));//添加Checkor者失败！
		}
		return map;
	}
	
	/**
	 * 决裁---加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addApplyCwaCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addApplyCwaCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		int result = affirmApplySer.addApplyCheckList(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("添加Checkor者成功！",request));//添加Checkor者成功！
			map.put("navTabId", "ess0305_affirm");
			map.put("callbackType", "closeCurrent");
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("添加Checkor者失败！",request));//添加Checkor者失败！
		}
		return map;
	}
	
	
	/**
	 * 决裁---加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addApplyMACCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addApplyMACCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		int result = affirmApplySer.addApplyCheckList(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("添加Checkor者成功！",request));//添加Checkor者成功！
			map.put("navTabId", "ess0209_affirm");
			map.put("callbackType", "closeCurrent");
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("添加Checkor者失败！",request));//添加Checkor者失败！
		}
		return map;
	}
	
	
	
	/**
	 * 决裁---年假调整申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAuunAffirmInfo")
	@ResponseBody
	public Map addAuunAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = affirmApplySer.appAUUNApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0304");
				map.put("message", "年假调整决裁成功！");
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "年假调整决裁出错，请重新决裁！" + e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 加班申请check列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOtCheckList")
	public ModelAndView viewOtCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("otCheckList", this.affirmApplySer.getOtCheckList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtCheckListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/affirmApply/viewOtCheckList",modelMap);
	}
	
	/**
	 * 加班P申请check列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPOtCheckList")
	public ModelAndView viewPOtCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("otCheckList", this.affirmApplySer.getOtCheckList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtCheckListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/affirmApply/viewPOtCheckList",modelMap);
	}
	
	/**
	 * 加班L申请check列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLOtCheckList")
	public ModelAndView viewLOtCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("otCheckList", this.affirmApplySer.getOtCheckList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtCheckListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/affirmApply/viewLOtCheckList",modelMap);
	}
	
	/**
	 * check加班信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkApplyCheckInfo")
	public ModelAndView checkApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());
	
		
		LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplySerOt.getOtApplyPersonal(request);
		modelMap.put("infoApplyOt", infoApplyOt);

		List applyorList = infoApplySer.getApplyorByApplyNoList(request);
		String names = "";
		String name = "";
		String essCheckNo = "";
		for(int i=0;i<applyorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)applyorList.get(i);
			name = applyorMap.get("LOCAL_NAME").toString();
			names = name + ",";
		 
			
		}
		names = names.substring(0, names.length()-1);
		modelMap.put("names", names);
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(admin.getPersonId().equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
				}
				break;
			}
		}
		modelMap.put("is_check", is_check);
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		

		if(infoApplyOt != null && infoApplyOt.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyOt.get("APPLY_TYPE"))){
			modelMap.put("OtBatchAffirmList", infoApplySerOt.getOtBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySerOt.getOtBatchAffirmInfoCnt(request));

		}
		return new ModelAndView("/ess/affirmApply/checkApplyCheckInfo", modelMap);
	}
	
	/**
	 * Check---加班申请  (check overtime apply)
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
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.check",request));//Check成功
			map.put("navTabId", "ess0248");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_failure.check",request));//Check失败
		}
		return map;
	}
	
	
	
	
	
	/**
	 * 加班申请--编辑列表(overtime apply edit list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEditLOtApplyList")
	public ModelAndView viewEditLOtApplyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("APPLY_TYPE", "L");
		modelMap.put("otApplyList", this.affirmApplySer.getEditOtApplyList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getEditOtApplyListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEditLOtApplyList",modelMap);
	}
	

	/**
	 * 加班申请 批量修改--编辑列表(overtime apply edit list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEditLOtApplyBatchList")
	public ModelAndView viewEditLOtApplyBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("APPLY_TYPE", "L");
		modelMap.put("otApplyList", this.affirmApplySer.getEditOtApplyBatchList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getEditOtApplyListBatchCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEditLOtApplyBatchList",modelMap);
	}
	
	
	/**
	 * 加班申请--编辑列表(overtime apply edit list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEditPOtApplyList")
	public ModelAndView viewEditPOtApplyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("APPLY_TYPE", "P");
		modelMap.put("otApplyList", this.affirmApplySer.getEditOtApplyList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getEditOtApplyListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEditPOtApplyList",modelMap);
	}
	
	/**
	 * 批量修改P加班申请信息 (update ot apply in batch)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePOtApplyInBatch")
	@ResponseBody
	public Map updatePOtApplyInBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.updateBatchOtApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0213");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.piliang",request));//批量修改加班申请成功!
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_failure.piliang",request));//批量修改加班申请失败,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量修改L加班申请信息 (update ot apply in batch)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateLOtApplyInBatch")
	@ResponseBody
	public Map updateLOtApplyInBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.updateBatchOtApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0231");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.piliang",request));//批量修改加班申请成功!
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_failure.piliang",request));//批量修改加班申请失败,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 跳转单个加班申请(view overtime apply for update)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditLOtApplyInfo")
	public ModelAndView viewEditLOtApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if(admin!=null){
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastLastMonth = format.format(c.getTime());
        String llastDate = (String)infoApplySer.getLLastMonthLastDay(request);
        lastLastMonth = llastDate!=null?llastDate:lastLastMonth;


        
        //  查询当前锁定月的上月份。
        LinkedHashMap dateMap = new LinkedHashMap();
    	dateMap.put("FLAG", "S");
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("PERSON_ID", admin.getPersonId());
		String arStartDateStr=this.infoApplyDao.getCurrentArDate(dateMap);
		GregorianCalendar LLASTMONTH1   = DateUtil.ParseGregorianCalendar(arStartDateStr);
		LLASTMONTH1.add(2, -1);
		 lastLastMonth=DatatypeFactory.newInstance().newXMLGregorianCalendar(LLASTMONTH1).toString().substring(0, 10);
		 modelMap.put("LLASTMONTH", lastLastMonth);
        
		List applyorList = this.affirmApplySer.getEditOtApplyList(request);
		modelMap.put("otApplyInfo", applyorList.get(0));
		//List otDeductTimeList = this.infoApplySer.getOtDeductTimeList(request) ;
		//modelMap.put("otDeductTimeList", otDeductTimeList);
		List affirmorList = this.infoApplySer.getAffirmorByApplyNoList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		
		return new ModelAndView("/ess/affirmApply/viewEditLOtApplyInfo", modelMap);
	}
	
	/**
	 * 跳转单个加班申请(view overtime apply for update)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditPOtApplyInfo")
	public ModelAndView viewEditPOtApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if(admin!=null){
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastLastMonth = format.format(c.getTime());
        String llastDate = (String)infoApplySer.getLLastMonthLastDay(request);
        lastLastMonth = llastDate!=null?llastDate:lastLastMonth;
        
        
        //  查询当前锁定月的上月份。
        LinkedHashMap dateMap = new LinkedHashMap();
    	dateMap.put("FLAG", "S");
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("PERSON_ID", admin.getPersonId());
		String arStartDateStr=this.infoApplyDao.getCurrentArDate(dateMap);
		GregorianCalendar LLASTMONTH1   = DateUtil.ParseGregorianCalendar(arStartDateStr);
		LLASTMONTH1.add(2, -1);
		 lastLastMonth=DatatypeFactory.newInstance().newXMLGregorianCalendar(LLASTMONTH1).toString().substring(0, 10);
		 modelMap.put("LLASTMONTH", lastLastMonth);
        
   
        
		List applyorList = this.affirmApplySer.getEditOtApplyList(request);
		Map otApplyInfo = (LinkedHashMap)applyorList.get(0);
		if(!"".equals(otApplyInfo.get("FROM_TIME"))){
			otApplyInfo.put("FROM_TIME_H", otApplyInfo.get("FROM_TIME").toString().substring(0, 2));
			otApplyInfo.put("FROM_TIME_M", otApplyInfo.get("FROM_TIME").toString().substring(3, 5));
		}
		if(!"".equals(otApplyInfo.get("TO_TIME"))){
			otApplyInfo.put("TO_TIME_H", otApplyInfo.get("TO_TIME").toString().substring(0, 2));
			otApplyInfo.put("TO_TIME_M", otApplyInfo.get("TO_TIME").toString().substring(3, 5));
		}
		modelMap.put("otApplyInfo", otApplyInfo);
		//List otDeductTimeList = this.infoApplySer.getOtDeductTimeList(request) ;
		//modelMap.put("otDeductTimeList", otDeductTimeList);
		List affirmorList = this.infoApplySer.getAffirmorByApplyNoList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		if("LGEQH".equals(admin.getCpnyId())){
			modelMap.put("hourParam", infoApplySer.getOtTimeByCpnyId(request,"0"));
			modelMap.put("muniteParam", infoApplySer.getOtTimeByCpnyId(request,"1"));
		}
		return new ModelAndView("/ess/affirmApply/viewEditPOtApplyInfo", modelMap);
	}
	
	/**
	 * 修改-P加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePOtApply")
	@ResponseBody
	public Map updatePOtApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.updateOvertimeApply(request);
			
			
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.person",request));//修改加班申请成功!
				map.put("statusCode", "200");
				//map.put("navTabId", "ess0232");
				map.put("navTabId", "ess0237");
				map.put("callbackType", "closeCurrent");
				map.put("formId", "viewPOtApplyInfoList");
				//modelMap.put("forwardUrl","/ess/affirmApply/viewEditPOtApplyList");
			}
			if(result == 12){
				 
				map.put("navTabId", "ess0237");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"保存加班申请成功!"
				map.put("statusCode", "200"); 
				map.put("callbackType", "closeCurrent");
				map.put("formId", "viewPOtApplyInfoList");
			}
			
			
			 
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_failure.person",request));//修改加班申请失败,请重新修改!
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 修改-L加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateLOtApply")
	@ResponseBody
	public Map updateLOtApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.updateOvertimeApply(request);
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.person",request));//修改加班申请成功!
				map.put("statusCode", "200");
				//map.put("navTabId", "ess0233");
				map.put("navTabId", "ess0236");
				map.put("callbackType", "closeCurrent");
				//modelMap.put("forwardUrl","/ess/affirmApply/viewEditPOtApplyList");
			}
			if(result == 12){
				 
				map.put("navTabId", "ess0236");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
			
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_failure.person",request));//修改加班申请失败,请重新修改!
			map.put("statusCode", "300");
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOvertimeAffirmList")
	public ModelAndView viewOvertimeAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("ovetimeApplyPersonList", this.affirmApplySer.getOvertimeAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOvertimeAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewOvertimeAffirmList",
				modelMap);
	}
	/**
	 * 休假申请列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveAffirmList")
	public ModelAndView viewLeaveAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.affirmApplySer
				.getLeaveApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getLeaveApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewLeaveAffirmList",
				modelMap);
	}

	/**
	 * 出差申请决裁列表(evection apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionAffirmList")
	public ModelAndView viewEvectionAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("evectionApplyPersonList", this.affirmApplySer
				.getEvectionApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getEvectionApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEvectionAffirmList",
				modelMap);
	}

	/**
	 * 外出申请决裁列表(egression apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEgressionAffirmList")
	public ModelAndView viewEgressionAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("egressionApplyPersonList", this.affirmApplySer
				.getEgeressionApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getEgressionApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEgressionAffirmList",
				modelMap);
	}

	

	/**
	 * 通过/否决加班申请(pass or reject overtime Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOvertimeApply")
	@ResponseBody
	public Map<String, Object> approveOvertimeApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0801");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirm_success",request));//"加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirm_fail",request));//"加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量通过/否决休假申请(batch pass or reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveLeaveApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveLeaveApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApplyInBatch(request);
			if (result == 1) {
				String navTabId=request.getParameter("navTabId");
				map.put("navTabId", navTabId);
				//map.put("navTabId", "ess0802");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirmInBatch_success",request));//"批量休假申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirmInBatch_fail",request));//"批量休假申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决休假申请(pass or reject leave Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveLeaveApply")
	@ResponseBody
	public Map<String, Object> approveLeaveApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApply(request);
			if (result == 1) {
				String navTabId=request.getParameter("navTabId");
				map.put("navTabId", navTabId);
				//map.put("navTabId", "ess0802");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirm_success",request));//"休假申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirm_fail",request));//"休假申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量通过/否决出差申请(batch pass or reject evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEvectionApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveEvectionApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0805");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirmInBatch_success",request));//"批量出差申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirmInBatch_fail",request));//"批量出差申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决出差申请(pass or reject evection Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEvectionApply")
	@ResponseBody
	public Map<String, Object> approveEvectionApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0805");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirm_success",request));//"出差申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirm_fail",request));//"出差申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量通过/否决外出申请(batch pass or reject egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEgressionApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveEgressionApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0811");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirmInBatch_success",request));//"批量外出申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirmInBatch_fail",request));//"批量外出申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决外出申请(pass or reject egression Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEgressionApply")
	@ResponseBody
	public Map<String, Object> approveEgressionApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = this.affirmApplySer.approveLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0811");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirm_success",request));//"外出申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirm_fail",request));//"外出申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}
	/**
	 * 喜丧假决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLikeLeaveAffirmList")
	public ModelAndView viewLikeLeaveAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.affirmApplySer
				.getLeaveApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getLeaveApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewLikeLeaveAffirmList",
				modelMap);
	}
	/**
	 * 调休决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkingDaysAffirmList")
	public ModelAndView viewWorkingDaysAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.affirmApplySer
				.getLeaveApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getLeaveApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewWorkingDaysAffirmList",
				modelMap);
	}
	
	/**
	 * 临时职入职审批列表查询
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewHireAffirmList")
	public ModelAndView viewHireAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		modelMap.put("hireAffirmList", this.affirmApplySer.getHireAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getHireAffirmListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
		modelMap.put("searchMap", paramMap);

		return new ModelAndView("/ess/affirmApply/viewHireAffirmList",modelMap);
	}

	/**
	 * 临时职入职查询(决裁)
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewHireAffirmDtlList")
	public ModelAndView viewHireAffirmDtlList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}

		modelMap.put("PERSON_ID", personId);
		List affirmorList = affirmApplySer.getHireAffirmByReqID(request);
		List checkorList = affirmApplySer.getCheckorByByReqID(request);
		List reqList = affirmApplySer.getHireAffirm(request);
		LinkedHashMap paraMap = new LinkedHashMap();
		paraMap.put("APPLY_TYPE", "23292329");
		paraMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySerOt.getEssFileList(paraMap);
		
		if(reqList.size()>0){
			modelMap.put("essAffirmReq", (LinkedHashMap)reqList.get(0));
		}
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(personId.equals(applyorMap.get("AFFIRMOR_ID").toString())){
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
		modelMap.put("fileList", fileList);
		List infoApplyHire = affirmApplySer.getHireAffirmListByReqID(request);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getHireAffirmListByReqIDCnt(request));
		modelMap.put("infoApplyHire", infoApplyHire);
		
		return new ModelAndView("/ess/affirmApply/viewHireAffirmDtlList",modelMap);

	}

	/**
	 * 临时职入职决裁
	 */
	@RequestMapping(value = "/affirmReqHire")
	@ResponseBody
	public Map<String, Object> affirmReqHire (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int result = affirmApplySer.approveApplyHire(request);
		if(result==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "ess0252");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 临时职离职职审批列表查询
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewResignAffirmList")
	public ModelAndView viewResignAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		modelMap.put("defaultCpny", admin.getCpnyId());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());	
			paramMap.put("AFFIRM_FLAG", 0);	
		}
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("searchMap", paramMap);
		modelMap.put("resignAffirmList", this.affirmApplySer.getResignAffirmList(paramMap, request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getResignAffirmListCnt(paramMap, request));
		return new ModelAndView("/ess/affirmApply/viewResignAffirmList",modelMap);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewResignAffirmDoList")
	public ModelAndView viewResignAffirmDoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		modelMap.put("PERSON_ID", personId);
		List affirmorList = affirmApplySer.getResignAffirmByReqID(request);
		List checkorList = affirmApplySer.getResignCheckorByByReqID(request);
		List reqList = affirmApplySer.getResignAffirm(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("APPLY_TYPE", "15823");
		paramMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySerOt.getEssFileList(paramMap);
		
		if(reqList.size()>0){
			modelMap.put("essAffirmReq", (LinkedHashMap)reqList.get(0));
		}
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(personId.equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		
		List infoApplyResign = affirmApplySer.getResignAffirmListByReqID(request);
		int infoApplyResignCnt 	= affirmApplySer.getResignAffirmListByReqIDCnt(request);
		modelMap.put("searchMap", paramMap);
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("fileList", fileList);
		modelMap.put("infoApplyResign", infoApplyResign);	
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyResignCnt);
		return new ModelAndView("/ess/affirmApply/viewResignAffirmDoList",modelMap);
	}
	/**
	 * 临时职离职发令决裁
	 */
	@RequestMapping(value = "/affirmReqResign")
	@ResponseBody
	public Map<String, Object> affirmReqResign (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int result = affirmApplySer.approveApplyResign(request);
		if(result==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "ess0253");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}
	

	/**
	 * 批量通过/否决进/出门刷卡申请(batch pass or reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveCwaApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveCwaApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveCwaApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0209");
				map.put("message", "审批成功");//"批量进/出门刷卡申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "审批失败");//"批量进/出门刷卡申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 实贩卖实绩审批查询列表 2015.01.19
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/viewSellOutAffirmList")
	public ModelAndView viewSellOutAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		modelMap.put("itemList",affirmApplySer.getSellOutAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, affirmApplySer.getSellOutAffirmListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personId", admin.getPersonId());
		if(!paramMap.containsKey("START_DATE")){
			modelMap.put("START_DATE", DateUtil.getCurrentMonthStrFormat()+"-01");
			modelMap.put("END_DATE", DateUtil.getSysdateStr());			
		}

		return new ModelAndView("/ess/affirmApply/viewSellOutAffirmList",modelMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewSellOutAffirmInfo")
	public ModelAndView viewSellOutAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap reqInfo =  this.promoterSer.getSelloutReqByReqId(request);
		List affirmList = this.promoterSer.getAffirmorListByReqId(request);
		
		String affirm_no = "";
		int dept_level = 0;
		for(int i=0; i < affirmList.size(); i++){
			LinkedHashMap paramMap = (LinkedHashMap)affirmList.get(i);
			if("0".equals(paramMap.get("AFFIRM_FLAG").toString())){
				if(admin.getPersonId().equals(paramMap.get("AFFIRMOR_ID").toString())){
					affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
					dept_level = i + 1;
				}
				break;
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmorListCnt", affirmList == null ? 0 : affirmList.size());
		modelMap.put("REQ_ID", request.getParameter("REQ_ID"));
		modelMap.put("personId", admin.getPersonId());
		modelMap.put("reqInfo", reqInfo);
		modelMap.put("toolbarInfo", "SELECTR") ;
		
		modelMap.put("menuThirdList", promoterSer.getMenuThirdList("",request));
		modelMap.put("tabsSelected",0);

		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("REQ_ID", request.getParameter("REQ_ID"));
		modelMap.put("itemList", promoterSer.getSelloutReqDtlList(request));
		modelMap.put("reqOver10List", promoterSer.getReqOver10List(paramMap));
		modelMap.put("reqOver9kList", promoterSer.getReqOver9kList(paramMap));
		modelMap.put("reqRatioList", promoterSer.getReqRatioList(paramMap));
		modelMap.put("reqExshopList", promoterSer.getReqExshopList(paramMap));
		int iCurrPageNum = 1, iNumPerPage = 10;
		if (UiUtil.getPageNum(request) > 0){
			iCurrPageNum =  UiUtil.getPageNum(request);
			iNumPerPage  = UiUtil.getNumPerPage(request);
		}
		modelMap.put("reqReportList", promoterSer.getReqReportList(paramMap, iCurrPageNum, iNumPerPage));
		modelMap.put("TOTALREQCNT", promoterSer.getReqReportListCnt(paramMap));
		
		paramMap.put("APPLY_TYPE", "278651");
		paramMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySerOt.getEssFileList(paramMap);
		modelMap.put("fileList", fileList);
		
		return new ModelAndView("/ess/affirmApply/viewSellOutAffirmInfo", modelMap);
	}
	
}