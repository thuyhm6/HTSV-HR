package com.ait.is.action.insurancesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.ait.is.service.BaseManagementForSearchSer;

import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;



@Controller
@RequestMapping(value="/is/issuranceNumber")
public class BaseManagementForSearchCtroller {
	
	Logger logger=Logger.getLogger(BaseManagementForSearchCtroller.class);
    @Autowired
	private BaseManagementForSearchSer baseManagementSer;
    @Autowired
    private ToolMenuSer toolMenuSer;
    @Autowired
	private ExcelUtilSer excelUtilSer;
    /**
     * 跳转到保险基数页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/viewBaseManagementForSeachList")
    public ModelAndView viewBaseManagementForSearchList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String method = request.getParameter("method");//方法区分
		
		List showList = this.baseManagementSer.ViewInsuranceBaseManagementForSearch(request);
		int insuranceBaseCnt = baseManagementSer.getInsuranceBaseCnt(request) ;//查找记录数
		request.setAttribute("showList", showList);
		
		if ("create".equals(method)) {
			if (showList == null || showList.size() == 0) {
				request.setAttribute("message", "没有符合条件的人员信息！");
			}
		}
		Map retunrMap=new HashMap<Object, Object>();
		modelMap.put("showList", showList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceBaseCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124902")) ;
		return new ModelAndView("/is/issuranceNumber/viewBaseManagementForSeachList",modelMap);
	}
    /**
     * responseBody表示该方法的返回内容直接写到http的响应实体
     * 数据的生成原数据的清空
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/createInstanceBaseManagement")
    public Map createInstanceBaseManagement(HttpServletRequest request
    		,HttpServletResponse response,ModelMap modelMap)throws Exception{
    	
    	LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
    	
    	int calFlag=baseManagementSer.createInstanceBaseManagement(request);
    	if(calFlag==1){
			map.put("statusCode", "200");
		}else{
			map.put("statusCode", "300");
			map.put("message", "createDataToInstanceBase information Exception. ");
		}
		return map;
    }
    /**
     * 保险基数信息导出
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/insBaseNumListExcel")
	public ModelAndView insBaseNumListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List insBaseNumInfoList = this.baseManagementSer.getInsBaseNumInfoExcel(request) ;
		
		modelMap.put("itemList", insBaseNumInfoList);
		
		return new ModelAndView("/is/issuranceNumber/viewNOInsBaseNumInfoExcel",modelMap);
	}
    
    /**
	 * 公积金基数的删除
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteInstanceaseManagement")
	@ResponseBody
	public Map deleteInstanceaseManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		int calFlag =this.baseManagementSer.deleteInstanceaseManagement(request);
		request.setAttribute("message", "删除成功！");
		if(calFlag==1){
			map.put("statusCode", "200");
			map.put("message", "删除成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", "delete deleteInstanceaseManagement information Exception. ");
		}
		return map;
	}
	/**
	 * 社会保险--基数管理 (修改) 进入页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-20 下午2:03:38 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateInstanceBaseNum")
	public ModelAndView updateCPFBaseManagement(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		int calFlaga =this.baseManagementSer.allowInstanceBaseNumUpdate(request);
		if(calFlaga==1){
			List list = this.baseManagementSer.getupdateInstanceBaseManagement(request);//预修改的信息集合
			modelMap.put("allowUpdate", list);
		}
		return new ModelAndView("/is/issuranceNumber/updateInstanceBaseManagement",modelMap);
	}
	/**
	 * 社会保险--基数管理 (修改保存)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-21 下午2:30:50 
	* @version V1.0
	 */
	@RequestMapping(value = "/editInstanceBaseManagement")
	@ResponseBody
	public Map editInstanceBaseManagement(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.baseManagementSer.editInstanceBaseManagement(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "bx0102");
			map.put("forwardUrl","/is/issuranceNumber/viewBaseManagementForSearchList?navTabId=bx0102") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
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
		String name = "IS_INSURENUMBER_DATE";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap, sqlContentmap, aliasNameList, null, name);
	}
}
