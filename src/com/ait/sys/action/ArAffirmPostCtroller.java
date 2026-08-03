package com.ait.sys.action;

import java.util.HashMap;
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

import com.ait.hrm.service.EmpInfoSer;
import com.ait.org.service.OrgManageSer;
import com.ait.sys.service.AffirmSer;
import com.ait.sys.service.ArAffirmPostSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: AIT (c) Company: AIT
 * 
 * @fileName ArAffirmPostCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-13 上午10:13:40
 * @version 5.0
 * 
 */
@Controller
@RequestMapping(value = "/sys/arAffirmPost")
public class ArAffirmPostCtroller {

	@Autowired
	private ArAffirmPostSer arAffirmPostSer;

	@Autowired
	private OrgManageSer orgManageSer;

	@Autowired
	private AffirmSer affirmSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private EmpInfoSer empInfoSer;

	/**
	 * 查看决裁角色页面 Description:show Virtual duty
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArAffirmPostList")
	public ModelAndView viewArAffirmPostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List affirmPostList = this.arAffirmPostSer.getArAffirmDutyList(request);
		modelMap.put("affirmPostList", affirmPostList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3549"));
		return new ModelAndView("/sys/arAffirmPost/viewArAffirmPostList",
				modelMap);
	}

	/**
	 * 添加决裁角色页面 Description:the page of add a Virtual duty
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addArAffirmPostView")
	public ModelAndView addArAffirmPostView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		return new ModelAndView("/sys/arAffirmPost/addArAffirmPostView",
				modelMap);
	}

	/**
	 * 获取部门树的数据 Description:get all the department
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOrgInfoTreeData")
	@ResponseBody
	public List getOrgInfoTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List deptList = this.orgManageSer.getSelectDept(request);
		return deptList;
	}

	/**
	 * 添加决裁角色 Description:add a Virtual duty Description:
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveArAffirmPost")
	@ResponseBody
	public Map saveArAffirmPost(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.arAffirmPostSer.saveArAffirmPost(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0482");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 删除决裁角色 Description:delete a Virtual duty Description:
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteArAffirmPostInfo")
	@ResponseBody
	public Map deleteArAffirmInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arAffirmPostSer.deleteArAffirmPostInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0482");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 修改决裁角色页面 Description:the page of update a Virtual duty
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArAffirmPostView")
	public ModelAndView updateArAffirmPostView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object post = this.arAffirmPostSer.getAffirmPostById(request);
		modelMap.put("post", post);
		return new ModelAndView("/sys/arAffirmPost/updateArAffirmPostView",
				modelMap);
	}

	/**
	 * 修改决裁角色 Description:update a Virtual duty
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArAffirmPost")
	@ResponseBody
	public Map updateArAffirmPost(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arAffirmPostSer.updateArAffirmPost(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0482");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAffirmorsEmpIdList")
	public ModelAndView viewAffirmorsEmpIdList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("turn_to_url", request.getParameter("turnToUrl"));
		modelMap.put("empList", this.affirmSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmSer.getEmpIdListCnt(request));
		String empId = request.getParameter("empId_sy0482") == null ? "": request.getParameter("empId_sy0482").toString();
		modelMap.put("empId", empId);
		String personId = request.getParameter("personId_sy0482") == null ? "": request.getParameter("personId_sy0482").toString();
		modelMap.put("personId", personId);
		String empName = request.getParameter("empName_sy0482") == null ? "": request.getParameter("empName_sy0482").toString();
		modelMap.put("empName", empName);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/arAffirmPost/viewAffirmorsEmpIdList",modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validateArAffirmPostExist")
	@ResponseBody
	public void validateArAffirmPostExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int result = this.arAffirmPostSer.validateArAffirmPostExist(request);
		Map modelMap = new HashMap<String, Object>();
		if (result == 0) {
			modelMap.put("flagYn", "Y");
		} else {
			modelMap.put("flagYn", "N");
		}
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}
	
	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAffirmorsEmpIdListNew")
	public ModelAndView viewAffirmorsEmpIdListNew(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("turn_to_url", request.getParameter("turnToUrl"));
		modelMap.put("empList", this.affirmSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmSer.getEmpIdListCnt(request));
		String empId = request.getParameter("empId") == null ? "": request.getParameter("empId").toString();
		modelMap.put("empId", empId);
		String personId = request.getParameter("personId") == null ? "": request.getParameter("personId").toString();
		modelMap.put("personId", personId);
		String empName = request.getParameter("empName") == null ? "": request.getParameter("empName").toString();
		modelMap.put("empName", empName);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/arAffirmPost/viewAffirmorsEmpIdListNew",modelMap);
	}
}
