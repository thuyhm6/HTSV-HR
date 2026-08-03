package com.ait.pa.action.empSummary;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.Interface.ParentCtroller;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;

@Controller
@RequestMapping(value = "/pa/empSummary")
public class EmpSummary extends ParentCtroller{
	@Autowired
	private EmpInfoSer empInfoSer;
	/**
	 * 用于显示首个页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/viewEmpSummary")
	public ModelAndView viewEmpSummary(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		initMenu(request, modelMap);
		search(modelMap,request);
		return new ModelAndView( modelMap);
	}
	/**
	 * 用于显示首个页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/viewMakeEmpSummary")
	public ModelAndView viewMakeEmpSummary(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		initMenu(request, modelMap);
		search(modelMap,request);
		return new ModelAndView( modelMap);
	}
	/**
	 * 查找方法
	 * @param modelMap
	 * @param request 
	 * @return
	 */
	private ModelMap search(ModelMap modelMap, HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personId", admin.getPersonId());
		if(modelMap.get("KEY")==null)
			modelMap.put("KEY", admin.getEmpID());
		String interCpnyID = modelMap.get("interCpnyID").toString();
//		String empInfoUrl = "pa.empSummary.getEmpInfoList";//员工信息
//		String paInfoUrl = "pa.empSummary.paInfo";//考勤信息
//		String paYearInfoUrl = "pa.empSummary.paYearInfo";//年假信息
//		String payDetilInfoUrl = "pa.empSummary.payDetilInfo";//发款项详细列表
//		String otherPayInfoUrl = "pa.empSummary.otherPayInfo";//其他扣款详细列表
//		String welfarePayInfoUrl = "pa.empSummary.welfarePayInfo";//保险福利扣款列表
//		String administrationPayInfoUrl = "pa.empSummary.administrationPayInfo";//管理费用,税金,其他,实发工资
//		String paManuallyInfoUrl = "pa.empSummary.paManuallyInfo";//手工调整项目列表
//		
		String[][] sqlNames = {{"empInfo","pa.empSummary.getEmpInfoList"}	//员工信息
								,{"paInfo","pa.empSummary.paInfo"}	//考勤信息
								,{"paYearInfo","pa.empSummary.paYearInfo"}	//年假信息
								,{"payDetilInfo","pa.empSummary.payDetilInfo"}	//发款项详细列表
								,{"otherPayInfo","pa.empSummary.otherPayInfo"}	//其他扣款详细列表
								,{"welfarePayInfo","pa.empSummary.welfarePayInfo"}	//保险福利扣款列表
								,{"administrationPayInfo","pa.empSummary.administrationPayInfo"}	//管理费用,税金,其他,实发工资
								,{"paManuallyInfo","pa.empSummary.paManuallyInfo"}	//手工调整项目列表
								};
		
//		Object o = null;
//		Map paramMap=new LinkedHashMap();
//		paramMap.putAll(modelMap);
//		for(String[] names : sqlNames){
//			o = toolMenuSer.getLinkMapByName(paramMap, names[1]);
//			if(o!=null)
//				modelMap.put(names[0], o);
//		}
		try {
			modelMap.put("companyList", empInfoSer.getCompanyList(request));
			//员工信息
			modelMap.put("empInfo", empInfoSer.getEmpInfoLxjList(request));
			//年假信息
			modelMap.put("paYearInfo", empInfoSer.getYearInfoLxjList(request));
			
			if("TSTO".equals(interCpnyID)){
				//				,{"paInfo","pa.empSummary.paInfo"}	//考勤信息
				modelMap.put("paInfo", empInfoSer.getPaInfoLxjLgechList(request));
				//				,{"payDetilInfo","pa.empSummary.payDetilInfo"}	//发款项详细列表
				modelMap.put("payDetilInfo", empInfoSer.getpayDetilInfoLxjLgechList(request));
				//				,{"otherPayInfo","pa.empSummary.otherPayInfo"}	//其他扣款详细列表
				modelMap.put("otherPayInfo", empInfoSer.getotherPayInfoLxjLgechList(request));
				//				,{"welfarePayInfo","pa.empSummary.welfarePayInfo"}	//保险福利扣款列表
				modelMap.put("welfarePayInfo", empInfoSer.getwelfarePayInfoLxjLgechList(request));
				//				,{"administrationPayInfo","pa.empSummary.administrationPayInfo"}	//管理费用,税金,其他,实发工资
				modelMap.put("administrationPayInfo", empInfoSer.getadministrationPayInfoLxjLgechList(request));
				//				,{"paManuallyInfo","pa.empSummary.paManuallyInfo"}	//手工调整项目列表
				modelMap.put("paManuallyInfo", empInfoSer.getpaManuallyInfoLxjLgechList(request));
			}else if("SST".equals(interCpnyID)){
				//				,{"paInfo","pa.empSummary.paInfo"}	//考勤信息
				modelMap.put("paInfo", empInfoSer.getPaInfoLxjLgetaList(request));
				//				,{"payDetilInfo","pa.empSummary.payDetilInfo"}	//发款项详细列表
				modelMap.put("payDetilInfo", empInfoSer.getpayDetilInfoLxjLgetaList(request));
				//				,{"otherPayInfo","pa.empSummary.otherPayInfo"}	//其他扣款详细列表
				modelMap.put("otherPayInfo", empInfoSer.getotherPayInfoLxjLgetaList(request));
				//				,{"welfarePayInfo","pa.empSummary.welfarePayInfo"}	//保险福利扣款列表
				modelMap.put("welfarePayInfo", empInfoSer.getwelfarePayInfoLxjLgetaList(request));
				//				,{"administrationPayInfo","pa.empSummary.administrationPayInfo"}	//管理费用,税金,其他,实发工资
				modelMap.put("administrationPayInfo", empInfoSer.getadministrationPayInfoLxjLgetaList(request));
				//				,{"paManuallyInfo","pa.empSummary.paManuallyInfo"}	//手工调整项目列表
				modelMap.put("paManuallyInfo", empInfoSer.getpaManuallyInfoLxjLgetaList(request));
				
			}else{
				//				,{"paInfo","pa.empSummary.paInfo"}	//考勤信息
				modelMap.put("paInfo", empInfoSer.getPaInfoLxjList(request));
				//				,{"payDetilInfo","pa.empSummary.payDetilInfo"}	//发款项详细列表
				modelMap.put("payDetilInfo", empInfoSer.getpayDetilInfoLxjList(request));
				//				,{"otherPayInfo","pa.empSummary.otherPayInfo"}	//其他扣款详细列表
				modelMap.put("otherPayInfo", empInfoSer.getotherPayInfoLxjList(request));
				//				,{"welfarePayInfo","pa.empSummary.welfarePayInfo"}	//保险福利扣款列表
				modelMap.put("welfarePayInfo", empInfoSer.getwelfarePayInfoLxjList(request));
				//				,{"administrationPayInfo","pa.empSummary.administrationPayInfo"}	//管理费用,税金,其他,实发工资
				modelMap.put("administrationPayInfo", empInfoSer.getadministrationPayInfoLxjList(request));
				//				,{"paManuallyInfo","pa.empSummary.paManuallyInfo"}	//手工调整项目列表
				modelMap.put("paManuallyInfo", empInfoSer.getpaManuallyInfoLxjList(request));
			}
//			,{"paInfo","pa.empSummary.paInfo"}	//考勤信息
//			,{"paYearInfo","pa.empSummary.paYearInfo"}	//年假信息
//			,{"payDetilInfo","pa.empSummary.payDetilInfo"}	//发款项详细列表
//			,{"otherPayInfo","pa.empSummary.otherPayInfo"}	//其他扣款详细列表
//			,{"welfarePayInfo","pa.empSummary.welfarePayInfo"}	//保险福利扣款列表
//			,{"administrationPayInfo","pa.empSummary.administrationPayInfo"}	//管理费用,税金,其他,实发工资
//			,{"paManuallyInfo","pa.empSummary.paManuallyInfo"}	//手工调整项目列表
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelMap;
	}
}
