package com.ait.ar.action.attendanceSettings;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArEatCardAssociateSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ArEatCardAssociateCtroller {
	Logger logger = Logger.getLogger(ArCardAssociateCtroller.class);
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ArEatCardAssociateSer arEatCardAssociateSer;
	
	/**
	 * 查看工号卡号关系(view CardAssociate List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEatCardAssociate")
	public ModelAndView viewCardAssociateList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List eatCardAssociateList = this.arEatCardAssociateSer
				.getEatCardAssociateList(request);
		int eatCardAssociateCnt = this.arEatCardAssociateSer
				.getEatCardAssociateCnt(request);

		modelMap.put("eatCardAssociateList", eatCardAssociateList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, eatCardAssociateCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "23202"));
		return new ModelAndView("/ar/attendanceSettings/viewEatCardAssociate",
				modelMap);
	}

	/**
	 * 修改保存(update CardAssociate Info)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEatCardAssociateInfo")
	@ResponseBody
	public Map updateEatCardAssociateInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = arEatCardAssociateSer.updateEatCardAssociateInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 修改成功
			map.put("navTabId", "ar0311");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 修改失败
		}
		return map;
	}
}
