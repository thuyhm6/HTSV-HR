package com.ait.pa.action.workManagement;

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

import com.ait.pa.service.workManagement.PaPayObjSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/workManagement")
public class PaPayObjCtroller {
	Logger logger = Logger.getLogger(PaPayObjCtroller.class);

	@Autowired
	private PaPayObjSer PaPayObjSer;
	
	@Autowired
	private PaPayScheduleSer paPayScheduleSer;

	/**
	 * 工资支付对象
	 * 
	 */
	@RequestMapping(value = "/viewPaPayObj")
	public ModelAndView viewPaPayObjList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);

		List PaPayObjList = this.PaPayObjSer.getPaPayObjList(request);
		int PaPayObjCnt = this.PaPayObjSer.getPaPayObjCnt(request);

		modelMap.put("PaPayObjList", PaPayObjList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, PaPayObjCnt);
		modelMap.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		modelMap.put("empInfoShow", request.getParameter("empInfoShow"));
		
		return new ModelAndView("/pa/workManagement/viewPaPayObj", modelMap);

	}

	/**
	 * 工资支付对象 添加页面
	 * 
	 */
	@RequestMapping(value = "/addPaPayObj",method = RequestMethod.GET)
	public ModelAndView addPaPayObj(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		modelMap.put("schedualName", request.getParameter("schedualName"));
		
		return new ModelAndView("/pa/workManagement/addPaPayObj", modelMap);

	}

	/**
	 * 工资支付对象 保存信息
	 * 
	 */
	@RequestMapping(value = "/addPaPayObjInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaPayObjInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PaPayObjSer.addPaPayObjInfo(request);
	
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
//			map.put("message", "保存成功");
			map.put("formId", "searchViewPaPayObjForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
//			map.put("message", "保存失败");
			map.put("formId", "searchViewPaPayObjForm");
		}
		return map;

	}
	
	/**
	 * 进入修改页面(update PaPayObj view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaPayObjView",method = RequestMethod.GET)
	public ModelAndView updatePaPayObjView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PaPayObjInfo", this.PaPayObjSer.getPaPayObjInfo(request)) ;
		
		return new ModelAndView("/pa/workManagement/updatePaPayObjView",modelMap);
	}
	
	/**
	 * 修改区间信息(update PaPayObj Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaPayObjInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePaPayObjInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PaPayObjSer.updatePaPayObjInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaPayObjForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaPayObjForm");
		}
		return map;
	}
	
	/**
	 * 删除工资支付计划信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDeletePaPayObjInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map doDeletePaPayObjInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PaPayObjSer.doDeletePaPayObjInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaPayObjForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaPayObjForm");
		}
		return map;
	}
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpForPopList")
	public ModelAndView getEmpForPopList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		
		if("SPC_SH".equals(admin.getCpnyId())){
			modelMap.put("personList", this.PaPayObjSer.getEmpSHListForPop(request));
		}else{
			modelMap.put("personList", this.PaPayObjSer.getEmpListForPop(request));
		}
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.PaPayObjSer.getEmpListForPopCnt(request));
		if(request.getParameter("PAY_SCHEDULE_NO") != null){
			modelMap.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		}
		//String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		modelMap.put("searchForFlag", request.getParameter("searchForFlag"));
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice"));
		modelMap.put("refreshUrl", request.getParameter("refreshUrl"));
		modelMap.put("refreshMenuCode", request.getParameter("refreshMenuCode"));
		modelMap.put("refreshMenuName", request.getParameter("refreshMenuName"));
		return new ModelAndView("/pa/workManagement/viewEmpForPopList", modelMap);
	}

	/**
	 * 获得人员列表(view emp List)添加考勤用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpForArAddPopList")
	public ModelAndView getEmpForArAddPopList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personList", this.PaPayObjSer.getEmpListForPop(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.PaPayObjSer.getEmpListForPopCnt(request));
		if(request.getParameter("PAY_SCHEDULE_NO") != null){
			modelMap.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		}
		String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		modelMap.put("searchForFlag", request.getParameter("searchForFlag"));
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("EmpOffice", EmpOffice);
		modelMap.put("empidStr", request.getParameter("empidStr"));
		modelMap.put("personidStr", request.getParameter("personidStr"));
		return new ModelAndView("/pa/workManagement/viewEmpForArAddPopList", modelMap);
	}
}
