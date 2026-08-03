package com.ait.ess.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.AffirmApplySer;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.ess.service.EditionAffirmSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/ess/editionAffirm")
public class EditionAffirmCtroller {

	@Autowired
	private EditionAffirmSer editionAffirmSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AffirmLeaveApplySer affirmApplySer;

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description:需要审批的所有离职申请的List
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionAffirmList")
	public ModelAndView viewEditionAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"),
				admin.getCpnyId());
		modelMap.put("defaultCpny", cpny_id);

		List editionAffirmList = this.editionAffirmSer
				.getEditionAffirmList(request);
		int editionAffirmCnt = this.editionAffirmSer
				.getEditionAffirmCnt(request);
		modelMap.put("editionAffirmList", editionAffirmList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionAffirmCnt);
		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
			modelMap.put("AFFIRM_FLAG", "10");
		}
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2556"));

		return new ModelAndView("/ess/editionAffirm/viewEditionAffirmList",
				modelMap);
	}

	/**
	 * 查看离职申请决裁情况
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionCheckInfo")
	public ModelAndView viewEditionCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List editionAffirmList = this.editionAffirmSer
				.getEditionAffirmList(request);
		List applyorList = editionAffirmSer
				.getEditionApplyorByApplyNoList(request);
		Object applyorInfo = applyorList.get(0);
		modelMap.put("applyorInfo", applyorInfo);
		List affirmorList = editionAffirmSer
				.getEditionAffirmorByApplyNoList(request);
		List checkorList = editionAffirmSer.getEditionCheckorByApplyNoList(request);
		modelMap.put("editionAffirmList", editionAffirmList);

		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		for (int i = 0; i < checkorList.size(); i++) {
			LinkedHashMap paramMap = (LinkedHashMap) checkorList.get(i);
			if ("0".equals(paramMap.get("CHECK_FLAG").toString())) {
				check_no = paramMap.get("ESS_AFFIRM_NO").toString();
				dept_level = i + 1;
				break;
			}
		}
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("check_no", check_no);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		return new ModelAndView("/ess/editionAffirm/viewEditionCheckInfo",
				modelMap);
	}

	/**
	 * 到离职申请审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionAffirmsList")
	public ModelAndView viewEditionAffirmsList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List editionAffirmList = this.editionAffirmSer
				.getEditionAffirmList(request);
		List applyorList = this.editionAffirmSer
				.getEditionApplyorByApplyNoList(request);
		Object applyorInfo = applyorList.get(0);

		String personId = admin.getPersonId();
		List affirmList = this.editionAffirmSer
				.getEditionAffirmorByApplyNoList(request);

		List checkList = this.editionAffirmSer
				.getEditionCheckorByApplyNoList(request);
		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		// 查找当前需要check信息编号（第一条未check的信息）
		for (int i = 0; i < affirmList.size(); i++) {
			LinkedHashMap paramMap = (LinkedHashMap) affirmList.get(i);
			if ("0".equals(paramMap.get("AFFIRM_FLAG").toString())) {
				affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
				dept_level = i + 1;
				break;
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("personId", admin.getPersonId());
		modelMap.put("applyorInfo", applyorInfo);
		modelMap.put("affirmList", affirmList);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt",
				affirmList == null ? 0 : affirmList.size());
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("editionAffirmList", editionAffirmList);

		return new ModelAndView("/ess/editionAffirm/viewEditionAffirmsList",
				modelMap);
	}

	/**
	 * 跳转到添加Check人页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddCheckEssDimission")
	public ModelAndView viewAddCheckEssDimission(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("AFFIRM_NO", request.getParameter("AFFIRM_NO"));
		return new ModelAndView("/ess/editionAffirm/viewAddCheckEssDimission",
				modelMap);
	}

	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2013-10-14 下午4:46:40
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCheckAffirmDimission")
	@ResponseBody
	public Map addCheckAffirmDimission(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.editionAffirmSer.addCheckAffirmDimission(request);

		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation",
					request));// 保存成功
			jo.put("navTabId", "ess2017_affirm");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
		}
		return jo;
	}

	/**
	 * 决裁
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-14 下午4:46:40
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/affirmDimissionApplyInfo")
	@ResponseBody
	public Map affirmDimissionApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = 0;
		errorInt = this.editionAffirmSer.affirmDimisionInfo(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message","审批成功");// 保存成功
			jo.put("navTabId", "ess2017");
			jo.put("callbackType", "closeCurrent");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");// 保存失败
		}
		return jo;
	}

	/**
	 * 获取需要check的离职申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionCheckorList")
	public ModelAndView viewEditionCheckorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List editionCheckList = this.editionAffirmSer
				.getDimissionCheckList(request);
		int editionCheckCnt = this.editionAffirmSer
				.getDimissionCheckCnt(request);

		modelMap.put("editionCheckList", editionCheckList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionCheckCnt);
		return new ModelAndView("/ess/editionAffirm/viewEditionCheckorList",
				modelMap);
	}

	/**
	 * 离职交接项目审批和查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmEditionItem")
	public ModelAndView viewAffirmEditionItem(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List affirmItemList = editionAffirmSer
				.getAffirmEditionItemList(modelMap, request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap affirmItemInfo = null;
		if(affirmItemList != null && affirmItemList.size() > 0){
			affirmItemInfo = (LinkedHashMap) affirmItemList.get(0);
		}
		modelMap.put("affirmItemInfo", affirmItemInfo);
		if(affirmItemInfo!=null){
			modelMap.put("affirm_fg", affirmItemInfo.get("AFFIRM_FG"));
		}
		modelMap.put("affirmItemList", affirmItemList);
		modelMap.put("pId", admin.getPersonId());
		return new ModelAndView("/ess/editionAffirm/viewAffirmEditionItem",
				modelMap);
	}
	
	

	/**
	 * 离职交接项目审批和查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmEditionItemcheck")
	public ModelAndView viewAffirmEditionItemcheck(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List affirmItemList = editionAffirmSer
				.getAffirmEditionItemList(modelMap, request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object affirmItemInfo = null;
		if(affirmItemList != null && affirmItemList.size() > 0){
			affirmItemInfo = affirmItemList.get(0);
		}
		modelMap.put("affirmItemInfo", affirmItemInfo);
		modelMap.put("affirmItemList", affirmItemList);
		modelMap.put("personId", admin.getPersonId());
		return new ModelAndView("/ess/editionAffirm/viewAffirmEditionItemcheck",
				modelMap);
	}


	/**
	 * check离职信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkDimissionApplyInfo")
	public ModelAndView checkDimissionApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        String flag_check = request.getParameter("seach_IS_CHECK");
		List editionAffirmList = this.editionAffirmSer
				.getEditionAffirmList(request);
		List applyorList = this.editionAffirmSer
				.getEditionApplyorByApplyNoList(request);
		Object applyorInfo = applyorList.get(0);

		String personId = admin.getPersonId();
		List affirmList = this.editionAffirmSer
				.getEditionAffirmorByApplyNoList(request);

		List checkList = this.editionAffirmSer
				.getEditionCheckorByApplyNoList(request);
		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		// 查找当前需要check信息编号（第一条未check的信息）
		for (int i = 0; i < checkList.size(); i++) {
			LinkedHashMap paramMap = (LinkedHashMap) checkList.get(i);
			if ("0".equals(paramMap.get("CHECK_FLAG").toString()) && paramMap.get("PERSON_ID_C").equals(personId)) {
				check_no = paramMap.get("ESS_CHECK_NO").toString();
				dept_level = i + 1;
				break;
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("flag_check", flag_check);
		modelMap.put("dept_level", dept_level);
		modelMap.put("personId", admin.getPersonId());
		modelMap.put("applyorInfo", applyorInfo);
		modelMap.put("affirmList", affirmList);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt",
				affirmList == null ? 0 : affirmList.size());
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("editionAffirmList", editionAffirmList);

		return new ModelAndView("/ess/editionAffirm/checkDimissionApplyInfo",
				modelMap);
	}

	/**
	 * Check---离职申请 (check dimission apply)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkDimissionInfo")
	@ResponseBody
	public Map checkDimissionInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", "离职申请Check成功！");
			map.put("navTabId", "ess2018");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", "离职申请Check失败！");
		}
		return map;
	}
}
