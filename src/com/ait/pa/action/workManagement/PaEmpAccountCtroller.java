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

import com.ait.pa.service.workManagement.PaEmpAccountSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/workManagement")
public class PaEmpAccountCtroller {
	Logger logger = Logger.getLogger(PaEmpAccountCtroller.class);

	@Autowired
	private PaEmpAccountSer paEmpAccountSer;

	/**
	 * 工资账户信息
	 * 
	 */
	@RequestMapping(value = "/viewPaEmpAccount")
	public ModelAndView viewPaEmpAccountList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paEmpAccountList = this.paEmpAccountSer
				.getPaEmpAccountList(request);
		int paEmpAccountCnt = this.paEmpAccountSer.getPaEmpAccountListCnt(request);

		modelMap.put("paEmpAccountList", paEmpAccountList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paEmpAccountCnt);
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("null_NO", request.getParameter("search_null_NO"));
		modelMap.put("START_DATE_STARTED", request.getParameter("seach_START_DATE_STARTED"));
		modelMap.put("END_DATE_STARTED", request.getParameter("seach_END_DATE_STARTED"));
		modelMap.put("ACCOUNT_TYPE", request.getParameter("seach_ACCOUNT_TYPE"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE"));
		
		return new ModelAndView("/pa/workManagement/viewPaEmpAccount", modelMap);

	}
	
	/**
	 * 导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author mxq
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportPaEmpAccountList") 
	public ModelAndView viewImportPaEmpAccountList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List paEmpAccountTempList = this.paEmpAccountSer.getPaEmpAccountTempList(request);
		int paEmpAccountTempCnt = this.paEmpAccountSer.getPaEmpAccountTempCnt(request , "T"); 
		int errorCnt = this.paEmpAccountSer.getPaEmpAccountTempCnt(request , "E");
		modelMap.put("paEmpAccountTempList", paEmpAccountTempList);
		modelMap.put("paEmpAccountTempCnt", paEmpAccountTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEmpAccountTempCnt);
		return new ModelAndView("/pa/workManagement/viewImportPaEmpAccountList", modelMap);
	}
	
	
	/**
	 * 导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author mxq
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportTrainList") 
	public ModelAndView viewImportTrainList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List paEmpAccountTempList = this.paEmpAccountSer.getPaEmpAccountTempList1(request);
		int paEmpAccountTempCnt = this.paEmpAccountSer.getPaEmpAccountTempCnt(request , "T"); 
		int errorCnt = this.paEmpAccountSer.getPaEmpAccountTempCnt(request , "E");
		modelMap.put("paEmpAccountTempList", paEmpAccountTempList);
		modelMap.put("paEmpAccountTempCnt", paEmpAccountTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEmpAccountTempCnt);
		return new ModelAndView("/pa/workManagement/viewImportTrainList", modelMap);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelPaEmpAccountData")
	@ResponseBody
	public Map submitImportExcelAttendanceData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.paEmpAccountSer.submitImportExcelPaEmpAccountData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("alert.message.Submit_Success.b", request));//提交成功
			jo.put("navTabId", "org0203");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("alert.message.Submit_Fail.b", request));//提交失败
		}
		return jo;
	}

	/**
	 * 工资账户信息 添加页面
	 * 
	 */
	@RequestMapping(value = "/addPaEmpAccount",method = RequestMethod.GET)
	public ModelAndView addPaEmpAccount(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		return new ModelAndView("/pa/workManagement/addPaEmpAccount", modelMap);

	}

	/**
	 * 工资账户信息 保存信息
	 * 
	 */
	@RequestMapping(value = "/addPaEmpAccountInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaEmpAccountInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paEmpAccountSer.addPaEmpAccountInfo(request);
	
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
//			map.put("message", "保存成功");
			map.put("formId", "searchViewPaEmpAccountForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
//			map.put("message", "保存失败");
			map.put("formId", "searchViewPaEmpAccountForm");
		}
		return map;

	}
	
	/**
	 * 进入修改页面(update PaEmpAccount view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaEmpAccountView",method = RequestMethod.GET)
	public ModelAndView updatePaEmpAccountView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("empBaseInfo", request.getParameter("empBaseInfo"));
		
		modelMap.put("paEmpAccountInfo", this.paEmpAccountSer.getPaEmpAccountInfo(request)) ;
		
		return new ModelAndView("/pa/workManagement/updatePaEmpAccountView",modelMap);
	}
	
	/**
	 * 修改工资账户信息(update PaEmpAccount Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaEmpAccountInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePaEmpAccountInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paEmpAccountSer.updatePaEmpAccountInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaEmpAccountForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaEmpAccountForm");
		}
		return map;
	}
	
	/**
	 * 删除工资账户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDeletePaEmpAccountInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map doDeletePaEmpAccountInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paEmpAccountSer.doDeletePaEmpAccountInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("formId", "searchViewPaEmpAccountForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
			map.put("formId", "searchViewPaEmpAccountForm");
		}
		return map;
	}
}
