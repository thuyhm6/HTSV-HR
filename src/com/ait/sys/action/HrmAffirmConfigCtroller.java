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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.HrmAffirmConfigSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;


@Controller
@RequestMapping("/sys/hrmAffirmConfig")
public class HrmAffirmConfigCtroller {

	Logger logger = Logger.getLogger(HrmAffirmConfigCtroller.class);
	
	@Autowired
	private HrmAffirmConfigSer hrmAffirmConfigSer;
	
	/**
	 * 查询人事令页面配置选项
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewHrmAffirmConfigList")
	public ModelAndView viewHrmAffirmConfigList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("hrmAffirmConfigDistinctList", this.hrmAffirmConfigSer.getHrmAffirmConfigDistinctList(request));
		List hrmAffirmConfigTransCodeList=this.hrmAffirmConfigSer.getHrmAffirmConfigTransCodeList(request);
		modelMap.put("hrmAffirmConfigTransCodeList", hrmAffirmConfigTransCodeList);
		modelMap.put("preHrmAffirmConfigRecordList", this.hrmAffirmConfigSer.getPreHrmAffirmConfigRecordList(request,hrmAffirmConfigTransCodeList));
 		modelMap.put("COMPANY_ID", request.getParameter("COMPANY_ID")
										== null ? admin.getCpnyId() : request.getParameter("COMPANY_ID"));
		return new ModelAndView("/sys/hrmAffirmConfig/viewHrmAffirmConfigList",modelMap);
	}
	
	/**
	 * 保存人事令配置页面选项
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveHrmAffirmConfig")
	@ResponseBody
	public Map saveHrmAffirmConfig(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Integer result=this.hrmAffirmConfigSer.saveHrmAffirmConfig(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("message", "保存成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 保存人事令配置页面选项
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveHrmAffirmConfigByRow")
	@ResponseBody
	public Map saveHrmAffirmConfigByRow(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Integer result=this.hrmAffirmConfigSer.saveHrmAffirmConfig(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("message", "保存成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			map.put("message", "保存失败");
		}
		return map;
	}
}
