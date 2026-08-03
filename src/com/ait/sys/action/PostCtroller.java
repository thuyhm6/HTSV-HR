package com.ait.sys.action;

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

import com.ait.org.service.OrgManageSer;
import com.ait.sys.service.PostSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName PostCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:19:39
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/postManagement")
public class PostCtroller {

	Logger logger = Logger.getLogger(PostCtroller.class);

	@Autowired
	private PostSer postSer;

	@Autowired
	private OrgManageSer orgManageSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPostList")
	public ModelAndView viewPostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List postList = this.postSer.getPostItemList(request);
		//int postCnt = this.postSer.getPostItemCnt(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("postList", postList);
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, postCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2472"));
		return new ModelAndView("/sys/postManagement/viewPostList", modelMap);
	}

	/**
	 * 职责页面(HR_DUTY) Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDutyList")
	public ModelAndView viewDutyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List dutyList = this.postSer.getDutyItemList(request);
		int dutyListCnt = this.postSer.getDutyItemListCnt(request);
		modelMap.put("dutyList", dutyList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dutyListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3689"));
		return new ModelAndView("/sys/postManagement/viewDutyList", modelMap);
	}

	/**
	 * 职责页面(HR_DUTY) Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDutyView")
	public ModelAndView updateDutyView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List dutyList = this.postSer.getDutyItemList(request);
		int dutyListCnt = this.postSer.getDutyItemListCnt(request);
		modelMap.put("dutyList", dutyList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dutyListCnt);
		modelMap.put("dutyInfo", this.postSer.getDutyInfo(request));
		return new ModelAndView("/sys/postManagement/updateDutyView", modelMap);
	}

	/**
	 * 跳转到添加职责页面(HR_DUTY) Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDutyView")
	public ModelAndView addDutyView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cpnyList", this.orgManageSer
				.getCpnyNameForChoose(request));
		return new ModelAndView("/sys/postManagement/addDutyView", modelMap);
	}

	/**
	 * 添加职责(HR_DUTY) INSERT INTO HR_DUTY Description:
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveDutyInfo")
	@ResponseBody
	public Map saveDutyInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.postSer.saveDutyInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0340");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 修改职责(HR_DUTY) UPDATE HR_DUTY Description:
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDutyInfo")
	@ResponseBody
	public Map updateDutyInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.postSer.saveDutyInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0340");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 删除职责(HR_DUTY) DELETE FROM HR_DUTY Description:
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteDutyInfo")
	@ResponseBody
	public Map deleteDutyInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.postSer.deleteDutyInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0340");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPostGroup")
	public ModelAndView viewPostGroup(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List postGroupList = this.postSer.getPostGroupItemList(request);
		modelMap.put("postGroupList", postGroupList);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("toolbarInfo", toolMenuSer.getToolMenu(request));
		return new ModelAndView("/sys/postManagement/viewPostGroup", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPostGradeList")
	public ModelAndView viewPostGradeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List postGradeList = this.postSer.getPostGradeItemList(request);
		int postGradeListCnt = this.postSer.getPostGradeItemListCnt(request);
		for (Object postGrade : postGradeList) {
			Map map = (Map) postGrade;
			map.put("dutyList", this.postSer.getCheckDutyList(request, map
					.get("POST_GRADE_NO")));
		}
		modelMap.put("postGradeList", postGradeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, postGradeListCnt);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2339"));
		return new ModelAndView("/sys/postManagement/viewPostGradeList",
				modelMap);
	}

	@RequestMapping(value = "/addPostView")
	public ModelAndView addPostItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("postInfo", postSer.getPostItemInfo(request));
		modelMap.put("cpnyList", this.orgManageSer
				.getCpnyNameForChoose(request));
		return new ModelAndView("/sys/postManagement/addPostView", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPostInfo")
	@ResponseBody
	public Map addPostInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.postSer.addPostItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0320");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	@RequestMapping(value = "/addPostGroupView")
	public ModelAndView addPostGroupItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("postGroupInfo", postSer.getPostGroupItemInfo(request));
		return new ModelAndView("/sys/postManagement/addPostView", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPostGroupInfo")
	@ResponseBody
	public Map addPostGroupInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.postSer.addPostGroupItemInfo(request);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0310");
			map.put("forwardUrl", "/sys/postManagement/viewPostGroup");
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	@RequestMapping(value = "/addPostGradeView")
	public ModelAndView addPostGradeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cpnyList", this.orgManageSer
				.getCpnyNameForChoose(request));
		modelMap.put("postItemList", this.postSer.getPostItemList(request));
		modelMap.put("levelList", this.postSer.getPostGradeLevelList(request,
				3683));
		modelMap.put("dutyList", this.postSer.getDutyList(request));
		return new ModelAndView("/sys/postManagement/addPostgradeView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPostGradeInfo")
	@ResponseBody
	public Map addPostGradeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.postSer.addPostGradeItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0330");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到职级名称(职务)的修改页面(UPDATE HR_POST) Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePostView")
	public ModelAndView updatePostView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("postInfo", postSer.getPostItemInfo(request));
		return new ModelAndView("/sys/postManagement/updatePostView", modelMap);
	}

	/**
	 * 更新职级名称(职务)(UPDATE HR_POST) Description:
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePostInfo")
	@ResponseBody
	public Map updatePostInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.updatePostItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0320");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到职(岗)位的查看页面(SELECT * FROM HR_POSITION) Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPositionList")
	public ModelAndView viewPositionList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List positionList = this.postSer.getPositionList(request);
		int positionListCnt = this.postSer.getPositionListCnt(request);
		modelMap.put("positionList", positionList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, positionListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3895"));
		return new ModelAndView("/sys/postManagement/viewPositionList",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPositionView")
	public ModelAndView addPositionView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List postGradeList = this.postSer.getPostGradeItemList(request);
		List gategoryList = this.postSer.getPostGradeLevelList(request, 3324);
		modelMap.put("postGradeList", postGradeList);
		modelMap.put("gategoryList", gategoryList);
		modelMap.put("cpnyList", this.orgManageSer
				.getCpnyNameForChoose(request));
		return new ModelAndView("/sys/postManagement/addPositionView", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePositionView")
	public ModelAndView updatePositionView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List postGradeList = this.postSer.getPostGradeItemList(request);
		List gategoryList = this.postSer.getPostGradeLevelList(request, 3324);
		modelMap.put("postGradeList", postGradeList);
		modelMap.put("gategoryList", gategoryList);
		modelMap.put("postGradeList", postGradeList);
		modelMap.put("positionInfo", postSer.getPositionInfo(request));
		return new ModelAndView("/sys/postManagement/updatePositionView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePositionInfo")
	@ResponseBody
	public Map updatePositionInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.savePositionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0350");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPositionInfo")
	@ResponseBody
	public Map addPositionInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.savePositionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0350");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePositionInfo")
	@ResponseBody
	public Map deletePositionInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.deletePositionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0350");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	@RequestMapping(value = "/updatePostGroupView")
	public ModelAndView updatePostGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("postGroupInfo", postSer.getPostGroupItemInfo(request));
		return new ModelAndView("/sys/postManagement/updatePostGroupView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePostGroupInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updatePostGroupInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			postSer.updatePostGroupItemInfo(request);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "ar0304");
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到修改职级信息页面(UPDATE HR_POST_GRADE) Description:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePostGradeView")
	public ModelAndView updatePostGradeView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("postItemList", this.postSer
				.getPostItemSelectList(request));
		modelMap.put("levelList", this.postSer.getPostGradeLevelList(request,
				3683));
		modelMap.put("dutyList", this.postSer.getDutyList(request));
		modelMap.put("postGradeInfo", postSer.getPostGradeItemInfo(request));
		return new ModelAndView("/sys/postManagement/updatePostGradeView",
				modelMap);
	}

	/**
	 * 修改职级信息(UPDATE HR_POST_GRADE) Description:
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePostGradeInfo")
	@ResponseBody
	public Map updatePostGradeInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.updatePostGradeItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0330");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 职级名称（职务）的删除操作(DELETE FROM HR_POST) Description:
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePost")
	@ResponseBody
	public Map deletePost(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.deletePostItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0320");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePostGroup")
	@ResponseBody
	public Map deletePostGroup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			postSer.deletePostGroupItemInfo(request);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "ar0304");
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 删除职级信息（DELETE FROM HR_POST_GRADE）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePostGrade")
	@ResponseBody
	public Map deletePostGrade(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = postSer.deletePostGradeItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0330");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 职级添加时,职级名称,职等,职责与法人联动 Description:
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPostDetailsByCpnyId")
	@ResponseBody
	public void getPostDetailsByCpnyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map modelMap = new HashMap<String, Object>();
		modelMap.put("postItemList", this.postSer.getPostItemList(request));
		modelMap.put("levelList", this.postSer.getPostGradeLevelList(request,
				3683));
		modelMap.put("dutyList", this.postSer.getDutyList(request));
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGradeDetailsByCpnyId")
	@ResponseBody
	public void getGradeDetailsByCpnyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map modelMap = new HashMap<String, Object>();
		modelMap.put("postGradeList", this.postSer
				.getPostGradeItemList(request));
		modelMap.put("gategoryList", this.postSer.getPostGradeLevelList(
				request, 3324));
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}
}
