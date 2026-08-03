package com.ait.is.action.accumulationfund;

import java.util.ArrayList;
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

import com.ait.is.service.AccumulationFundManageSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;

/**
 * 公积金对象管理 (包括对象增加,减少 ,管理)
 * 
 * @ClassName: AccumulationFundManageCtroller
 * @Description: TODO
 * @author lwei liangwei@ait.net.cn
 * @date 2014-2-13 下午2:26:44
 * 
 */
@Controller
@RequestMapping(value = "/is/accumulationfundmanage")
public class AccumulationFundManageCtroller {

	@Autowired
	private AccumulationFundManageSer accumulationFundManageSer;
	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private ExcelUtilSer excelUtilSer;

	/**
	 * 公积金--对象增加List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-14 上午10:32:38 
	* @version V1.0
	 */
	@RequestMapping(value = "/ViewCPFJoinInsure")
	public ModelAndView ViewCPFJoinInsure(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获取CheckList
		// List checkList = paBenHsServices.getPaBenBaseNumCheckListBz();
		// request.setAttribute("checkList", checkList);
		String method = request.getParameter("method");// 方法区分

		// accumulationFundSer.backPaBenBaseNumUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
		List showList = accumulationFundManageSer.getPaBenBaseNumList(request);// 显示信息集合
		request.setAttribute("showList", showList);

		if ("create".equals(method)) {
			if (showList == null || showList.size() == 0) {
				request.setAttribute("message", "没有符合条件的人员信息！");
			}
		}
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124913"));
		return new ModelAndView("/is/accumulationfundmanage/ViewCPFJoinInsure", modelMap);
	}

	/**
	 * 公积金--对象减少List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-14 上午10:35:47 
	* @version V1.0
	 */
	@RequestMapping(value = "/ViewCPFStopInsure")
	public ModelAndView ViewCPFStopInsure(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		String method = request.getParameter("method");// 方法区分

		//paBenHsServices.backPaBenStopInsureUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
		List stopList = accumulationFundManageSer.getviewCPFStopInsure(request);//显示信息集合

		if ("create".equals(method)) {
			if (stopList == null || stopList.size() == 0) {
				request.setAttribute("message", "没有符合条件的人员信息！");
			}
		}

		request.setAttribute("stopList", stopList);

		return new ModelAndView("/is/accumulationfundmanage/ViewCPFStopInsure", modelMap);
	}
	

	/**
	 * 公积金--对象管理List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 上午10:35:47 
	* @version V1.0
	 */
	@RequestMapping(value = "/ViewBenshObjectManage")
	public ModelAndView ViewBenshObjectManage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		String method = request.getParameter("method");// 方法区分

		//paBenHsServices.backPaBenStopInsureUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
		List manageList = accumulationFundManageSer.getviewBenshObjectManage(request);//显示信息集合

		if ("create".equals(method)) {
			if (manageList == null || manageList.size() == 0) {
				request.setAttribute("message", "没有符合条件的人员信息！");
			}
		}

		request.setAttribute("manageList", manageList);

		return new ModelAndView("/is/accumulationfundmanage/ViewBenshObjectManage", modelMap);
	}


	@RequestMapping(value = "/downloadTemplete")
	@SuppressWarnings("unchecked")
	public void downloadTemplete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();

		aliasNameList.add("职号 *");
		aliasNameList.add("姓名 *");
		aliasNameList.add("开始缴纳月");
		aliasNameList.add("入社基数");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0","H0000003");
		map.put("CELL1","张三");
		map.put("CELL2","201203");
		map.put("CELL3","4000.5");

		list.add(map);
		String name = "JOIN_INSURE_DATE";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap, sqlContentmap, aliasNameList, null, name);
	}

	@RequestMapping(value = "/insureDel")
	public void insureDel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

		Map map = new HashMap();
		String[] ids = request.getParameterValues("check");
		if (ids != null && ids.length > 0) {
			accumulationFundManageSer.updatePaBenObjectMoveFlagBz(map, request);
			for (String id : ids) {
				map.put("id", id);
				accumulationFundManageSer.updatePaBenObjectMoveFlagBz1(map, request);
			}

		}
	}
	
	/**
	 * 公积金--对象增加 (修改) 进入页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @date 2014-2-20 下午2:03:38 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateBenshObjectManage")
	public ModelAndView updateBenshObjectManage(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		/*String[] ids = request.getParameterValues("check");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				paBenHsServices.allowPaBenBaseNumUpdate(id);
			}
		}
		List list = paBenHsServices.getupdateCPFBaseManagement();//预修改的信息集合
		request.setAttribute("allowUpdate", list);
		System.out.println(request.getParameter("ids"));*/
		int calFlaga =accumulationFundManageSer.allowBenshObjectManageNumUpdate(request);
		if(calFlaga==1){
			List list = accumulationFundManageSer.getAllowPaBenJoinInsureUpdate(request);//预修改的信息集合
			modelMap.put("allowUpdate", list);
		}
		return new ModelAndView("/is/accumulationfundmanage/updateBenshObjectManage",modelMap);
	}
	/**
	 * 公积金--对象增加 (修改保存)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @date 2014-2-21 下午2:30:50 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateBenshObjectManage1")
	@ResponseBody
	public Map updateBenshObjectManage1(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = accumulationFundManageSer.updatePaBenManageAddInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "bx0203");
			//map.put("PERSON_ID", request.getParameter("PERSON_ID"));
			
		//	map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl","/is/accumulationfundmanage/ViewCPFJoinInsure?navTabId=bx0203") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	/***
	 * 公积金--对象增加点击修改后不修改而返回
	 */
	@RequestMapping(value = "/AddBenshObjectManageNotUpdate")
	@ResponseBody
	public ModelAndView AddBenshObjectManageNotUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = accumulationFundManageSer.AddBenshObjectManageNotUpdate(request);
		// accumulationFundSer.backPaBenBaseNumUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
		List showList = accumulationFundManageSer.getPaBenBaseNumList(request);// 显示信息集合
		request.setAttribute("showList", showList);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124913"));
		return new ModelAndView("/is/accumulationfundmanage/ViewCPFJoinInsure", modelMap);
	}
	
	
	
	
	/**
	 * 公积金--对象减少 (修改) 进入页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @date 2014-2-20 下午2:03:38 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateStopBenshObjectManage")
	public ModelAndView updateStopBenshObjectManage(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		/*String[] ids = request.getParameterValues("check");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				paBenHsServices.allowPaBenBaseNumUpdate(id);
			}
		}
		List list = paBenHsServices.getupdateCPFBaseManagement();//预修改的信息集合
		request.setAttribute("allowUpdate", list);
		System.out.println(request.getParameter("ids"));*/
		int calFlaga =accumulationFundManageSer.allowStopBenshObjectManageNumUpdate(request);
		if(calFlaga==1){
			List list = accumulationFundManageSer.getAllowPaBenStopInsureUpdate(request);//预修改的信息集合
			modelMap.put("allowUpdate", list);
		}
		return new ModelAndView("/is/accumulationfundmanage/updateStopBenshObjectManage",modelMap);
	}
	/**
	 * 公积金--对象减少(修改保存)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @date 2014-2-21 下午2:30:50 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateStopBenshObjectManage1")
	@ResponseBody
	public Map updateStopBenshObjectManage1(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = accumulationFundManageSer.updatePaBenManageStopInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "bx0204");
			//map.put("PERSON_ID", request.getParameter("PERSON_ID"));
			
		//	map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl","/is/accumulationfundmanage/ViewCPFStopInsure?navTabId=bx0204") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/***
	 * 公积金--对象减少点击修改后不修改而返回
	 */
	@RequestMapping(value = "/StopBenshObjectManageNotUpdate")
	@ResponseBody
	public ModelAndView StopBenshObjectManageNotUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = accumulationFundManageSer.StopBenshObjectManageNotUpdate(request);
		List stopList = accumulationFundManageSer.getviewCPFStopInsure(request);//显示信息集合
		request.setAttribute("stopList", stopList);
		return new ModelAndView("/is/accumulationfundmanage/ViewCPFStopInsure", modelMap);
	}
	
	/**
	 * 公积金--对象增加 (删除)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:06:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBenshObjectManageAdd")
	@ResponseBody
	public Map deleteBenshObjectManageAdd(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int calFlag =this.accumulationFundManageSer.deleteBenshObjectManageAdd(request);
		request.setAttribute("message", "删除成功！");
		if(calFlag==1){
			map.put("statusCode", "200");
			map.put("message", "删除成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", "delete deleteBenshObjectManageAdd information Exception. ");
		}
		return map;
	}
	
	/**
	 * 公积金--对象减少 (删除)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:06:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBenshObjectManageDel")
	@ResponseBody
	public Map deleteBenshObjectManageDel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		int calFlag =this.accumulationFundManageSer.deleteBenshObjectManageDel(request);
		request.setAttribute("message", "删除成功！");
		if(calFlag==1){
			map.put("statusCode", "200");
			map.put("message", "删除成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", "delete deleteBenshObjectManageDel information Exception. ");
		}
		return map;
	}
}
