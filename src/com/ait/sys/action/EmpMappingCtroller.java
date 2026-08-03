package com.ait.sys.action;

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
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.CommonException;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 人员新旧社号mapping
 */
@Controller
@RequestMapping(value = "/sys/empMapping")
public class EmpMappingCtroller {
	Logger logger = Logger.getLogger(EmpMappingCtroller.class);

	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	/**
	 * 人员信息mapping查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpMappingList")
	public ModelAndView viewEmpMappingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("empInfo", empInfoSer.viewEmpMappingList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.viewEmpMappingListCnt(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219984")) ;
		modelMap.put("defaultCpny", admin.getCpnyId() );
		
		if(request.getParameter("seach_SYNC_FLAG") == null){
			modelMap.put("SYNC_FLAG", "0");
		}
		return new ModelAndView("/sys/empMapping/viewEmpMappingList", modelMap);
	}  
	
	/**
	 * 人员信息mapping 新增页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddEmpMapping")
	public ModelAndView viewAddEmpMapping(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView("/sys/empMapping/viewAddEmpMapping", modelMap);
	}  
	
	/**
	 * 人员信息mapping 新增
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEmpMapping")
	@ResponseBody
	public Map<String, Object> addEmpMapping(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.addEmpMapping(request);
		if( result == 1){
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "sy0490");
		}else{
			if(result == 2){
				map.put("message", "两个社号的身份证号不一致，无法mapping");
			}else if(result == 3){
				map.put("message", "两个社号已经mapping过，无需重复mapping");
			}else{
				map.put("message", "保存失败");
			}
			map.put("statusCode", "300");
		}
		return map;
	} 
	
	/**
	 * 人员信息mapping 修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncEmpMapping")
	public ModelAndView syncEmpMapping(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap empInfo = this.empInfoSer.getEmpMappingByNo(request);
		empInfo.put("PERSON_ID",empInfo.get("NEW_PERSON_ID"));
		empInfo.put("interCpnyID", empInfo.get("CPNY_ID"));
		modelMap.put("empVacInfoNew",this.empInfoSer.getEmpVacInfo(empInfo));
		empInfo.put("PERSON_ID",empInfo.get("OLD_PERSON_ID"));
		modelMap.put("empVacInfoOld",this.empInfoSer.getEmpVacInfo(empInfo));
		modelMap.put("empInfo",empInfo);
		return new ModelAndView("/sys/empMapping/viewUpdateEmpMapping", modelMap);
	}
	
	/**
	 * 人员信息mapping 修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEmpMapping")
	@ResponseBody
	public Map<String, Object> updateEmpMapping(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.updateEmpMapping(request);
		if( result == 1){
			map.put("statusCode", "200");
			map.put("message", "同步成功");
			map.put("navTabId", "sy0490");
		}else{
			map.put("statusCode", "300");
			map.put("message", "同步失败");
		}
		return map;
	}

	/**
	 * 人员信息mapping 删除
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEmpMapping")
	@ResponseBody
	public Map<String, Object> deleteEmpMapping(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "删除";
		if(!"1".equals(op_flag)){
			msg = "同步";
		}
		try {
			result = empInfoSer.syncEmpInfo(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
				map.put("message", msg + "成功！");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	

	/**
	 * 最终确认批量导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchMappingModule")
	public void exportBatchMappingModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("新社号");
		aliasNameList.add("姓名");
		aliasNameList.add("旧社号");
		aliasNameList.add(" ");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12200000");
		map.put("CELL1", "王某某");
		map.put("CELL2", "12100000");
		map.put("CELL3", " ");
		list.add(map);
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"emp_mapping");
	}
	
	/**
	 * 最终确认批量导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelMappingDataList")
	public ModelAndView viewImportExcelMappingDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paMappingTempList = this.empInfoSer.getMappingTempList(request);
		int paMappingTempCnt = this.empInfoSer.getMappingTempCnt(request , "T");
		int errorCnt = this.empInfoSer.getMappingTempCnt(request , "E");
		
		modelMap.put("paMappingTempList", paMappingTempList);
		modelMap.put("paMappingTempCnt", paMappingTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paMappingTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paMappingTempCnt);
		return new ModelAndView("/sys/empMapping/viewImportExcelMappingDataList", modelMap);
	}
	

	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelMappingEmpData")
	@ResponseBody
	public Map submitImportExcelMappingEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.empInfoSer.submitImportExcelMappingEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "sy0490");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
}
