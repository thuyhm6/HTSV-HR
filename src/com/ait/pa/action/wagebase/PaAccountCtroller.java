package com.ait.pa.action.wagebase;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.ait.ar.service.CycleSer;
import com.ait.pa.service.bonus.BonusPersonnelSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsurancePersonnelSer;
import com.ait.pa.service.wagebase.PaAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaAccountCtroller.java
 * @Description:
 * @Create date: 2012-1-7 下午02:30:31
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/wagebase")
public class PaAccountCtroller {
	Logger logger = Logger.getLogger(PaAccountCtroller.class);
	
	@Autowired
	private PaAccountSer paAccountSer ;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
    private CycleSer cycleSer;
	/**
	 * 查询出账户信息列表（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaAccount")
	public ModelAndView viewPaAccountList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//异常人员选择条件默认不选择
		if(request.getParameter("seach_EXCEPTION_PA_COUNT")==null){
			modelMap.put("EXCEPTION_PA_COUNT", "NO");
		}
		if(request.getParameter("do_DIMISSION_PA_COUNT")==null){
			modelMap.put("do_DIMISSION_PA_COUNT", "NO");
		}

        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		List paAccountList = this.paAccountSer.getPaAccountList(request,null) ;
    		int paAccountListCnt = this.paAccountSer.getPaAccountCnt(request,null) ;
    		modelMap.put("itemList", paAccountList);
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paAccountListCnt) ;
        }
        
		List empOfficeList=this.paAccountSer.getEmpOfficeList(request);
		modelMap.put("empOfficeList", empOfficeList);
		modelMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE") == null ? "" : request.getParameter("seach_EMP_OFFICE"));
		modelMap.put("IN_THE_DIFFERENCE", request.getParameter("seach_IN_THE_DIFFERENCE") == null ? "" : request.getParameter("seach_IN_THE_DIFFERENCE"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2561")) ;
		
		return new ModelAndView("/pa/wagebase/viewPaAccount",modelMap);
	}
	
	
	/**
	 * 查询出账户信息列表(备份情况目前只有TA)（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaAccountHistoryList")
	public ModelAndView viewPaAccountHistoryList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//异常人员选择条件默认不选择
		if(request.getParameter("seach_EXCEPTION_PA_COUNT")==null){
			modelMap.put("EXCEPTION_PA_COUNT", "NO");
		}
		List empOfficeList=this.paAccountSer.getEmpOfficeList(request);
		//List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		//List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		//modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		//modelMap.put("jobTypeGroupList", jobTypeGroupList);
		if(!modelMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			modelMap.put("YEAR", SALS_MON.substring(0, 4));
			modelMap.put("MONTH", SALS_MON.substring(4, 6));
		}
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag != null){
			List paAccountList = this.paAccountSer.getPaAccountList(request,"1") ;
			int paAccountListCnt = this.paAccountSer.getPaAccountCnt(request,"1") ;
			modelMap.put("itemList", paAccountList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paAccountListCnt) ;
		}
		modelMap.put("empOfficeList", empOfficeList);
		modelMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE") == null ? "" : request.getParameter("seach_EMP_OFFICE"));
		modelMap.put("IN_THE_DIFFERENCE", request.getParameter("seach_IN_THE_DIFFERENCE") == null ? "" : request.getParameter("seach_IN_THE_DIFFERENCE"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2561")) ;
		return new ModelAndView("/pa/wagebase/viewPaAccountHistoryList",modelMap);
	}
	
	@RequestMapping(value = "/updatePaCalcFlagByPersonId")
	@ResponseBody
	public String updatePaCalcFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.paAccountSer.updatePaCalcFlagByPersonId(request)==1){
			result = "Y";
		}else{
			result = "N";
		}
		return result;
	}
	
	/**
	 * 跳转到修改页面(updatePaComputeItemView)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaAccountCtrollerView")
	public ModelAndView updatePaComputeItemView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Object paAccountCtroller = this.paAccountSer.getPaAccountCtrollerInfo(request);
		
		Map map = new HashMap();
		map = (HashMap)paAccountCtroller;
		request.setAttribute("seach_BANK_ID", map.get("BANK_ID")!=null?map.get("BANK_ID").toString():"");
		request.setAttribute("seach_CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		
		List bankList = this.paAccountSer.getBankList(request);
		List bankBranchList = this.paAccountSer.getBankBranchList(request);
		
		modelMap.put("paAccountCtroller", paAccountCtroller);
		modelMap.put("bankList", bankList);
		modelMap.put("bankBranchList", bankBranchList);
		
		return new ModelAndView("/pa/wagebase/updatePaAccountCtrollerView",modelMap);
	}	

	/**
	 * 执行修改（updatePaAccountInfo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaAccountInfo")
	@ResponseBody
	public Map updatePaAccountInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt=this.paAccountSer.updatePaAccountInfo(request) ;
		if(errorInt==1){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request) );
			jo.put("navTabId", "pa0502");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request) );
		}
		return jo;
	}
	
	/**
	 * 根据银行查询该银行的级联支行（区域）信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getBankBranchByBankNo")
	@ResponseBody
	public List getBankBranchByBankNo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		List bankBranchList = this.paAccountSer.getBankBranchList(request);
		return bankBranchList;
	}
	
	/**
	 * 导出账户信息列表excel（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaAccountTranserExcel")
	public ModelAndView viewPaAccountTranserExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List paAccountList = this.paAccountSer.getPaAccountList(request,null) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("itemList", paAccountList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2561")) ;
		
		return new ModelAndView("/pa/wagebase/viewPaAccountTranserExcel",modelMap);
	}
	
	
	/**
	 * 导出账户信息列表历史 excel（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaAccountHistoryExcel")
	public void viewPaAccountHistoryExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=searchMap.get("interLanguage").toString();
		String name = "viewPaAccountHistoryExcel";
		List aliasNameList = new ArrayList();
		List aliasValueList = new ArrayList();
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();		
		//默认查询汇总list
			//设置excel header
			aliasNameList.add("工资月");
			aliasNameList.add("社号"); 
			aliasNameList.add("姓名");
			aliasNameList.add("帐号名");
			aliasNameList.add("部门");
			aliasNameList.add("入职日期");
			aliasNameList.add("离职日期");	
			aliasNameList.add("银行支行名称");
			aliasNameList.add("帐号");
			aliasNameList.add("修改原因");
			aliasNameList.add("计算标识");
			aliasNameList.add("修改人");
			aliasValueList	=  this.paAccountSer.getPaAccountList(request,"1") ;
			//列名
			String[] columns = { "PA_MONTH", "EMPID", "CHINESE_NAME", "CARD_NAME", "DEPTNAME",
					"JOIN_COMPANY_DATE", "DATE_LEFT", "BANK_BRANCH_NAME", "CARD_NO", "UPDATE_REMARK", "CALC_FLAG" ,"UPDATED_BY"};
			//数据集
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}
	
	
	/**
	 * 查询出工资对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:44:09 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewPaSalaryObject")
	public ModelAndView viewPaSalaryObjectList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(!"".equals(request.getParameter("seach_PA_GIVE_DATE")) && request.getParameter("seach_PA_GIVE_DATE") != null){
			modelMap.put("itemList", this.paAccountSer.getPaSalaryObjectList(request) );
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paAccountSer.getPaSalaryObjectCnt(request) ) ;
			modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
			modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123441")) ;
		}
		
		return new ModelAndView("/pa/wagebase/viewPaSalaryObject",modelMap);

	}
	
	/**
	 * 导出账户信息列表excel（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaSalaryObjectExcel")
	public ModelAndView viewPaSalaryObjectExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("itemList", this.paAccountSer.getPaSalaryObjectList(request) );
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123441")) ;
		
		return new ModelAndView("/pa/wagebase/viewPaObjectExcel",modelMap);
	}
	
	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午12:08:51 
	* @version V1.0
	 */
	@RequestMapping(value = "/updatePaSalaryCalcFlagByPersonId")
	@ResponseBody
	public String updatePaSalaryCalcFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		String empid=request.getParameter("empid");
		String numPerPage=request.getParameter("numPerPage");
		//待定
		if (this.paAccountSer.updatePaSalCalcFlagByPersonId(request)==1){//工资基础-->工资对象
				result = "Y"+","+empid+","+numPerPage;
			}else{
				result = "N"+","+empid+","+numPerPage;
			}
		return result;
	}
	
	/**
	 * 工资计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-15 下午11:38:42 
	* @version V1.0
	 */
	@RequestMapping("/salaryCalculationObject")
	@ResponseBody
	public void salaryCalculationObject(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paAccountSer.calculateObject(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(TipMessage.getTipMessage(returnString,request)));

		out.flush();
		out.close();
	}
	
	/**
	 * 跳转到修改页面(updatePaComputeItemView)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaObjectView")
	public ModelAndView updatePaObjectView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Object paObjectCtroller = this.paAccountSer.getPaObjectCtrollerInfo(request);
		
		Map map = new HashMap();
		map = (HashMap)paObjectCtroller;
		
		modelMap.put("paObjectCtroller", paObjectCtroller);
		modelMap.put("pageNum", request.getParameter("pageNum"));
		
		return new ModelAndView("/pa/wagebase/updatePaAccountCtrollerView",modelMap);
	}
	
	/**
	 * 执行修改（updatePaAccountInfo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatePaObjectInfo")
	@ResponseBody
	public Map updatePaObjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt=this.paAccountSer.updatePaObjectInfo(request) ;
		if(errorInt==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request) );
			map.put("forwardUrl", "/pa/wagebase/viewPaSalaryObject?pageNum="+request.getParameter("paPageNum")+"&menuNo=123441&navTabId=pa0507&seach_PA_GIVE_DATE="+request.getParameter("PA_GIVE_DATE")+"&seach_paYear="+request.getParameter("paPaYear")+"&seach_paMonth="+request.getParameter("paPaMonth"));
			map.put("navTabId", "pa0507");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request) );
		}
		return map;
	}
	
	/**
	 * 修改试用支付比例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateC_PROB_PAY_RAT",method = RequestMethod.POST)
	@ResponseBody
	public Map updateC_PROB_PAY_RAT(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt=this.paAccountSer.updateC_PROB_PAY_RAT(request);
		if(errorInt == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//删除成功
			map.put("navTabId", "pa0502");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//删除失败
		}	
		return map;
	}
}
