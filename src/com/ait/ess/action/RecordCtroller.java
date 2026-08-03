package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.PersonInfoSer;
import com.ait.ess.service.RecordTestSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
@Controller
@RequestMapping(value = "/ess/recordTest")
public class RecordCtroller {

	@Autowired
    private RecordTestSer recordTestSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	/**
	 * 添加打卡记录
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRecordTest")
	@ResponseBody
	public Map addRecordTest(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = recordTestSer.addRecordTest(request);
			if (result == 1) {
				map.put("navTabId", "ess1166");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//"保存成功!"
				map.put("statusCode", "200");
			}
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//"保存失败!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRecordTest")
	public ModelAndView viewLikeLeaveApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplySer.getSurplusAdjustRest(request));
		
		return new ModelAndView("/ess/recordTest/viewRecordTest", modelMap);
	}
	
}
