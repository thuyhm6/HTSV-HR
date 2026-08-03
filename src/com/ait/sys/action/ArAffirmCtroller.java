package com.ait.sys.action;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.InfoApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ArAffirmPostSer;
import com.ait.sys.service.ArAffirmSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 考勤决裁流程
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName ArAffirmCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:08:57
 * @version 5.0
 * 
 */
@Controller
@RequestMapping(value = "/sys/arAffirm")
public class ArAffirmCtroller {
	
	@Autowired
	private ArAffirmSer arAffirmSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private ArAffirmPostSer arAffirmPostSer;
	/**
	 * 查看考勤决裁流程
	 * Description:show approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewArAffirmList" )
	public ModelAndView viewArAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List arAffirmList=this.arAffirmSer.getArAffirmList(request,"ar");
		List applyList=this.arAffirmSer.getApplyList(request);
		modelMap.put("applyList",applyList) ;
		modelMap.put("arAffirmList", arAffirmList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,  this.arAffirmSer.getArAffirmListCnt(request,"ar"));
		modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "3426")) ;
		return new ModelAndView("/sys/arAffirm/viewArAffirmList",modelMap);
	}
	
	/**
	 * 添加考勤决裁流程页面
	 * Description:the page of add an approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addArAffirmView")
	public ModelAndView addArAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List affirmPostList = this.arAffirmPostSer.getArAffirmDutyList(request);
		List applyDutyList = this.arAffirmPostSer.getApplyDutyList(request);
		List applyList=this.arAffirmSer.getApplyList(request);
		modelMap.put("applyDutyList",applyDutyList) ;
		modelMap.put("applyList",applyList) ;
		modelMap.put("affirmPostList",affirmPostList) ;
		return new ModelAndView("/sys/arAffirm/addArAffirmView", modelMap);
	}
	
	/**
	 * 添加考勤流程
	 * Description:add an approval process of attendance
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveArAffirmInfo")
	@ResponseBody
	public Map saveArAffirmInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.arAffirmSer.saveArAffirmInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//"添加成功"
			map.put("navTabId", "sy0481");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//"添加失败"
		}
		return map;		
	}
	
	/**
	 * 更新考勤流程页面
	 * Description:the page of update the approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArAffirmView")
	public ModelAndView updateArAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List applyList=this.arAffirmSer.getApplyList(request);
		List applyDutyList = this.arAffirmPostSer.getApplyDutyList(request);
		LinkedHashMap leaveApply = (LinkedHashMap)this.arAffirmSer.getLeaveApplyParam(request);
		List affirmPostList = this.arAffirmPostSer.getArAffirmDutyList(request);
		
		modelMap.put("applyList",applyList) ;
		modelMap.put("applyDutyList",applyDutyList) ;
		modelMap.put("leaveApply",leaveApply) ;
		modelMap.put("affirmPostList",affirmPostList) ;
		
		return new ModelAndView("/sys/arAffirm/updateArAffirmView", modelMap);
	}
	
	/**
	 * 更新考勤决裁流程页面
	 * Description:the page of add an approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArAffirmInfo")
	@ResponseBody
	public Map updateArAffirmInfo(HttpServletRequest request){
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.arAffirmSer.updateArAffirmInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//"修改成功"
			map.put("navTabId", "sy0481");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//"修改失败"
		}
		return map;		
	}
	
	/**
	 * 在list页面查看详细
	 * Description:show the detail approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShowDetail")
	public ModelAndView getShowDetail(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List paramList=this.arAffirmSer.getDetailParamList(request);
		modelMap.put("paramList",paramList);
		return new ModelAndView(modelMap);		
	} 
	
	/**
	 * 删除考勤流程
	 * Description:delete the approval process of attendance
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteArAffirmInfo")
	@ResponseBody
	public Map deleteArAffirmInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arAffirmSer.deleteArAffirmInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//"删除成功"
			map.put("navTabId", "sy0481");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//"删除失败"
		}	
		return map;
	}
	
	/**
	 * 决裁线查询
	 * Description:show approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewAffirmSearchList" )
	public ModelAndView viewAffirmSearchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List applyTypeNoList=this.arAffirmSer.getApplyTypeNoList(request);
		List applyTypeCodeList=this.arAffirmSer.getApplyTypeCodeList(request);
		
		String applyTypeNo = request.getParameter("APPLY_TYPE_NO");
		String personId = request.getParameter("dwz.person.personId");
		String applyTypeCode = request.getParameter("APPLY_TYPE_CODE");
		String applyLength = request.getParameter("APPLY_LENGTH");
		
		if(applyTypeNo!=null&&!applyTypeNo.equals("")&&personId!=null&&!personId.equals("")){
			List affirmorList = this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, 
					this.infoApplySer.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),applyTypeCode), applyLength, admin.getLanguage());
			modelMap.put("affirmorList", affirmorList);
		}
		
		String empId = request.getParameter("dwz.person.empId");
		
		modelMap.put("applyTypeNoList", applyTypeNoList);
		modelMap.put("applyTypeCodeList", applyTypeCodeList);
		
		modelMap.put("Apply_Type_No", applyTypeNo);
		modelMap.put("Person_Id", personId);
		modelMap.put("Emp_Id", empId);
		modelMap.put("Apply_Type_Code", applyTypeCode);
		modelMap.put("Apply_Length", applyLength);
		
		return new ModelAndView("/sys/arAffirm/viewAffirmSearchList",modelMap);
	}
	
	/**
	 * ajax
	 * Description:delete the approval process of attendance
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getApplyTypeCodeList")
	@ResponseBody
	public List getApplyTypeCodeList(HttpServletRequest request){
		List applyTypeCodeList=this.arAffirmSer.getApplyTypeCodeList(request);
		return applyTypeCodeList;
	}
	
	/**
	 * 查看考勤决裁流程
	 * Description:show approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewArAffirmFinalList" )
	public ModelAndView viewArAffirmFinalList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List arAffirmList=this.arAffirmSer.getArAffirmFinalList(request,"ar");
		List applyList=this.arAffirmSer.getApplyList(request);
		modelMap.put("applyList",applyList) ;
		modelMap.put("arAffirmList", arAffirmList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,  this.arAffirmSer.getArAffirmFinalListCnt(request,"ar"));
		modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "300015")) ;
		return new ModelAndView("/sys/arAffirm/viewArAffirmFinalList",modelMap);
	}
	
	/**
	 * 添加考勤决裁流程页面
	 * Description:the page of add an approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addArAffirmFinalView")
	public ModelAndView addArAffirmFinalView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List applyList=this.arAffirmSer.getApplyList(request);
		modelMap.put("applyList",applyList) ;
		return new ModelAndView("/sys/arAffirm/addArAffirmView", modelMap);
	}
	
	/**
	 * 添加考勤流程
	 * Description:add an approval process of attendance
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveArAffirmFinalInfo")
	@ResponseBody
	public Map saveArAffirmFinalInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.arAffirmSer.saveArAffirmFinalInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//"添加成功"
			map.put("navTabId", "sy0486");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//"添加失败"
		}
		return map;		
	}
	
	/**
	 * 更新考勤流程页面
	 * Description:the page of update the approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArAffirmFinalView")
	public ModelAndView updateArAffirmFinalView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List applyList=this.arAffirmSer.getApplyList(request);
		LinkedHashMap leaveApply = (LinkedHashMap)this.arAffirmSer.getLeaveApplyFinalParam(request);
		
		modelMap.put("applyList",applyList) ;
		modelMap.put("leaveApply",leaveApply) ;
		
		return new ModelAndView("/sys/arAffirm/updateArAffirmFinalView", modelMap);
	}
	
	/**
	 * 更新考勤决裁流程页面
	 * Description:the page of add an approval process of attendance
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArAffirmFinalInfo")
	@ResponseBody
	public Map updateArAffirmFinalInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.arAffirmSer.updateArAffirmFinalInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//"修改成功"
			map.put("navTabId", "sy0486");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//"修改失败"
		}
		return map;		
	}
	
	/**
	 * 删除考勤流程
	 * Description:delete the approval process of attendance
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteArAffirmFinalInfo")
	@ResponseBody
	public Map deleteArAffirmFinalInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arAffirmSer.deleteArAffirmFinalInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//"删除成功"
			map.put("navTabId", "sy0486");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//"删除失败"
		}	
		return map;
	}
}
