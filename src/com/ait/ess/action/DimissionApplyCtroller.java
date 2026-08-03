package com.ait.ess.action;

import java.util.HashMap;
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

import com.ait.ar.service.CycleSer;
import com.ait.ess.service.DimissionApplySer;
import com.ait.ess.service.InfoApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

@Controller
@RequestMapping(value="/ess/dimissionApply")
public class DimissionApplyCtroller {
	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

Logger logger = Logger.getLogger(ArMacRecordApplyCtroller.class);
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	InfoApplySer infoApplySer;
	@Autowired
	DimissionApplySer dimissionApplySer;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private CycleSer cycleSer;
	/***
	 * 跳转到离职申请List页面
	 */
	@RequestMapping(value = "/viewDimissionList")
	public ModelAndView viewDimissionList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		List dimissionList = this.dimissionApplySer.getDimissionList(request);
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		int dimissionCnt = this.dimissionApplySer.getDimissionCnt(request);
		modelMap.put("dimissionList", dimissionList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dimissionCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217992")) ;
		return new ModelAndView("/ess/dimissionApply/viewDimissionList", modelMap);
	}
	
	/**
	 * 跳转到离职申请页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDimissionView")
	public ModelAndView addDimissionView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("APPLY_NO", dimissionApplySer.getDimissionInfoSeq(request));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("authority", authorityUtil.isDimissionUser(admin.getPersonId()));
		List affirmorList = this.dimissionApplySer.getAffirmorList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		return new ModelAndView("/ess/dimissionApply/addDimissionView", modelMap);
	}

	/**
	 * 离职申请 (apply)提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDimissionInfoApply")
	@ResponseBody
	public Map addDimissionInfoApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.dimissionApplySer.checkAddDimissionInfo(request);
			if(errorInt==0){
				int result = dimissionApplySer.addDimissionInfoApply(request);
				if (result == 1) {
					map.put("navTabId", "ess2001");
					map.put("message", "离职申请成功!");
					map.put("statusCode", "200");
					map.put("callbackType", "closeCurrent");
				}
			}else{
				map.put("message", "离职信息已经存在,不能重复申请");//离职信息已经存在,不能重复申请!
				map.put("statusCode", "300");
			}
			
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "离职申请保存出错,请重新申请!");//离职申请保存出错,请重新申请!
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 删除还没有开始审批的信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteDimissionInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDimissionInfo(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorNum = this.dimissionApplySer.checkDimissionInfo(request);
			if(errorNum==1){
			    int result = this.dimissionApplySer
					.deleteDimissionInfo(request);
			    if (result == 1) {
				    map.put("statusCode", "200");
				    map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				    map.put("navTabId", "ess2001");
			    } else {
				    map.put("statusCode", "300");
				    map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));// 删除失败
			    }
			}else{
				map.put("message", "该信息已经开始审批，不能删除！");//离职信息已经存在,不能重复申请!
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
