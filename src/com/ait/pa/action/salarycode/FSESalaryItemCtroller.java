package com.ait.pa.action.salarycode;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.JobTypeSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.salarycode.FSESalaryItemSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Controller
@RequestMapping(value = "/pa/salarycode")
public class FSESalaryItemCtroller {
	
	@Autowired
	private FSESalaryItemSer fseSalarySer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
   
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private EmpInfoSer empInfoSer;
	
    
	/**
	 * 区间参数 查询分页 页面(show CycleParameter view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFSESalaryItemList")
	public ModelAndView viewFSESalaryItemList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List jobTypeList = this.fseSalarySer.getFSESalaryItemList(request) ;
		int jobTypeCnt = this.fseSalarySer.getFSESalaryItemCnt(request) ;
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		modelMap.put("jobTypeList", jobTypeList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, jobTypeCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2563")) ;
		return new ModelAndView("/pa/salarycode/viewFSESalaryItemList",modelMap);
	}
	
	/**
	 * 下在导入模版
	 */
	@RequestMapping(value = "/downloadTempleteFSESalary")
	@SuppressWarnings("unchecked")
	public void downloadTempleteJobType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add("公司*");
		aliasNameList.add("社号*");
		aliasNameList.add("工资月*");
		aliasNameList.add("人员类型*");
		aliasNameList.add("PA_AREA_CD*");
		aliasNameList.add("工资项目*");
		aliasNameList.add("项目金额*");
		aliasNameList.add("ADD_FLAG*");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0","TSTO");
		map.put("CELL1","CH259926");
		map.put("CELL2","201409");
		map.put("CELL3","FSE");
		map.put("CELL4","TSTO");
		map.put("CELL5","FSE应发工资");
		map.put("CELL6","1234.56");
		map.put("CELL7","Y");

		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		mapNameList.add("公司(法人)参考");
		mapNameList.add(admin.getCpnyId()+"计算项目参考");
		mapNameList.add(admin.getCpnyId()+"输入项目参考");
		mapNameList.add(admin.getCpnyId()+"基础项目参考");
		
		List mapList = new ArrayList();
		String cpnySql = "SELECT T.CPNY_ID CONTENT, T.CPNY_LOCATION CONTENT1 FROM HR_COMPANY T WHERE T.ACTIVITY =1";
		String salarySql = "SELECT T.ITEM_ID CONTENT, SY.CONTENT CONTENT1 FROM PA_ITEM T,SY_GLOBAL_NAME SY,PA_ITEM_PARAM PT WHERE T.ITEM_NO = PT.ITEM_NO AND T.ITEM_NO = SY.NO AND SY.LANGUAGE = 'zh' AND PT.CPNY_ID = '"+admin.getCpnyId()+"'";
		String salarySql1 = "SELECT T.PARAM_ITEM_ID CONTENT, SY.CONTENT CONTENT1 FROM PA_PARAM_ITEM T,SY_GLOBAL_NAME SY,PA_PARAM_ITEM_PARAM PT WHERE T.PARAM_ITEM_NO = PT.PARAM_ITEM_NO AND T.PARAM_ITEM_NO = SY.NO AND SY.LANGUAGE = 'zh' AND PT.CPNY_ID = '"+admin.getCpnyId()+"'";
		String salarySql2 = "SELECT T.ITEM_ID CONTENT, SY.CONTENT CONTENT1 FROM PA_BASIC_ITEM T,SY_GLOBAL_NAME SY,PA_BASIC_ITEM_PARAM PT WHERE T.ITEM_NO = PT.ITEM_NO AND T.ITEM_NO = SY.NO AND SY.LANGUAGE = 'zh' AND PT.CPNY_ID = '"+admin.getCpnyId()+"'";
		mapList.add(cpnySql);
		mapList.add(salarySql);
		mapList.add(salarySql1);
		mapList.add(salarySql2);
		
		String name = "payFSE_ATM_Import";
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}

	/**
	 * 导入临时保存画面  FSE人员工资
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportFSESalaryDataList")
	public ModelAndView viewImportFSESalaryDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List fseSalaryTempList = this.fseSalarySer.getFSESalaryImportTempList(request);
		int fseSalaryTempCnt = this.fseSalarySer.getFSESalaryImportTempCnt(request, "T");
		int errorCnt = this.fseSalarySer.getFSESalaryImportTempCnt(request, "E");
		
		modelMap.put("fseSalaryTempList", fseSalaryTempList);
		modelMap.put("fseSalaryTempCnt", fseSalaryTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", fseSalaryTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, fseSalaryTempCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2563")) ;
		return new ModelAndView("/pa/salarycode/viewImportFSESalaryDataList", modelMap);
	}
	
	
	/**
	 * 人员类型excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelFSESalaryData")
	@ResponseBody
	public Map submitImportExcelFSESalaryData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.fseSalarySer.submitImportExcelFSESalaryData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "pa2011");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	/**
	 * 人员类型模板下载（验证后）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadFSESalarylateByExcelData")
	public void downloadFSESalarylateByExcelData(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = fseSalarySer.getFSESalarylateInfoByExcelData(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		
	}
}
