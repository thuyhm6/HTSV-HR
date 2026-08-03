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

import com.ait.sys.service.ArAffirmPostSer;
import com.ait.sys.service.ArAffirmSer;
import com.ait.sys.service.HrmAffirmSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

/**
 * 发令决裁流程 Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName HrmAffirmCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:08:57
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/hrmAffirm")
public class HrmAffirmCtroller {

	@Autowired
	private ArAffirmSer arAffirmSer;
	@Autowired
	private HrmAffirmSer hrmAffirmSer;
	@Autowired
	private ArAffirmPostSer arAffirmPostSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 查看发令决裁流程 Description:show approval process of transaction
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHrmAffirmList")
	public ModelAndView viewHrmAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List hrmAffirmList=this.arAffirmSer.getArAffirmList(request,"pa");
		List applyList=this.hrmAffirmSer.getApplyList(request);
		modelMap.put("applyList",applyList) ;
		modelMap.put("hrmAffirmList", hrmAffirmList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,  this.arAffirmSer.getArAffirmListCnt(request,"pa"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "15081"));
		return new ModelAndView("/sys/hrmAffirm/viewHrmAffirmList", modelMap);
	}

	/**
	 * 添加发令决裁流程页面 Description:the page of add an approval process of transaction
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addHrmAffirmView")
	public ModelAndView addHrmAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List affirmPostList = this.arAffirmPostSer.getArAffirmDutyList(request);
		List applyDutyList = this.arAffirmPostSer.getApplyDutyList(request);
		List applyList = this.hrmAffirmSer.getApplyList(request);
		modelMap.put("applyList", applyList);
		modelMap.put("applyDutyList", applyDutyList);
		modelMap.put("affirmPostList",affirmPostList) ;
		return new ModelAndView("/sys/hrmAffirm/addHrmAffirmView", modelMap);
	}

	/**
	 * 添加发令流程 Description:add an approval process of transaction
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveHrmAffirmInfo")
	@ResponseBody
	public Map saveHrmAffirmInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int ifExist=this.hrmAffirmSer.validateExistsDutyApplyTypeCpnyId(request);
		if(ifExist==0){
			int result = this.arAffirmSer.saveArAffirmInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request) );
				map.put("navTabId", "sy0483");
				map.put("callbackType", "closeCurrent");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request) );
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", "该申请类型已经存在，不能重复添加。");//"符合该职责，该类型的发令类型已存在！"
		}
		return map;		
	}

	/**
	 * 更新发令决裁流程页面 Description:the page of add an approval process of transaction
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateHrmAffirmInfo")
	@ResponseBody
	public Map updateHrmAffirmInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int ifExist = this.hrmAffirmSer
				.validateExistsDutyApplyTypeCpnyId(request);
		if (ifExist == 0) {
			int result =this.arAffirmSer.updateArAffirmInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "sy0483");
				map.put("callbackType", "closeCurrent");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", "符合该职责，该类型的发令类型已存在！");
		}
		return map;
	}

	/**
	 * 在list页面查看详细 Description:show the detail approval process of transaction
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShowDetail")
	public ModelAndView getShowDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paramList = this.hrmAffirmSer.getDetailParamList(request);
		modelMap.put("paramList", paramList);
		return new ModelAndView(modelMap);
	}

	/**
	 * 删除发令流程 Description:delete the approval process of transaction
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteHrmAffirmInfo")
	@ResponseBody
	public Map deleteHrmAffirmInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arAffirmSer.deleteArAffirmInfo(request) ;
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0483");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 更新发令流程页面 Description:the page of update the approval process of
	 * transaction
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateHrmAffirmView")
	public ModelAndView updateHrmAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List applyList=this.hrmAffirmSer.getApplyList(request);
		LinkedHashMap leaveApply = (LinkedHashMap)this.arAffirmSer.getLeaveApplyParam(request);
		List affirmPostList = this.arAffirmPostSer.getArAffirmDutyList(request);
		List applyDutyList = this.arAffirmPostSer.getApplyDutyList(request);
		modelMap.put("applyList",applyList) ;
		modelMap.put("leaveApply",leaveApply) ;
		modelMap.put("affirmPostList",affirmPostList) ;
		modelMap.put("applyDutyList",applyDutyList) ;
		return new ModelAndView("/sys/hrmAffirm/updateHrmAffirmView", modelMap);
	}

	/**
	 * 根据已生成的流程，生成新的流程 Description:copy an old approval process of transaction
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/copyToNewAffirm")
	@ResponseBody
	public LinkedHashMap copyToNewAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		List detailList = this.hrmAffirmSer.getDetailParamList(request);
		LinkedHashMap resultMap = new LinkedHashMap();
		resultMap.put("detailLevelList", detailList);
		return resultMap;
	}
}
