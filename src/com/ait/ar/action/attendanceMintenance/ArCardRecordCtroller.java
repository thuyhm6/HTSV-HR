package com.ait.ar.action.attendanceMintenance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArCardRecordSer;
import com.ait.ar.service.ArReadCardSer;
import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.ShiftSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArCardRecordCtroller.java
 * @Description:
 * @Create date: 2012-4-18 上午11:06:58
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArCardRecordCtroller {

	Logger logger = Logger.getLogger(ArCardRecordCtroller.class);

	@Autowired
	private ArCardRecordSer arCardRecordSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AttendanceKeeperSer attendanceKeeperSer;
	@Autowired
	private ArReadCardSer arReadCardSer;
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private ShiftSer shiftSer;

	/**
	 * 显示刷卡页面(view ArCardRecord)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArCardRecord")
	public ModelAndView viewArCardRecordList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String firstFlag= request.getParameter("firstFlag");
		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("STIME", DateUtil.getSysdateStr("dd.MM.yyyy"));
			modelMap.put("RTIME", DateUtil.getSysdateStr("dd.MM.yyyy"));
		}
    	List getArCardRecordList = this.arCardRecordSer.getArCardRecordList(request);
    	int getArCardRecordListCnt = this.arCardRecordSer.getArCardRecordListCnt(request);
    	modelMap.put("getArCardRecordList", getArCardRecordList);
    	modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArCardRecordListCnt);
    		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo2(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("SHIFT_NAME", request.getParameter("seach_SHIFT_NO"));
		modelMap.put("personInfo",linkMap);
			
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2386"));

		return new ModelAndView("/ar/attendanceMintenance/viewArCardRecord",modelMap);
	}
	

	/**
	 * 显示刷卡页面(view ArCardRecord)SST当天进出门
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArCardRecordDay")
	public ModelAndView viewArCardRecordDayList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("STIME", DateUtil.getSysdateStr("dd/MM/yyyy"));
			modelMap.put("RTIME", DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
    	List getArCardRecordList = this.arCardRecordSer.getArCardRecordDayList(request);
    	modelMap.put("getArCardRecordList", getArCardRecordList);
    	/*
    	if (getArCardRecordList.size() > 0) {
    		for (int i = 0; i < getArCardRecordList.size(); i++) {
    			if (getArCardRecordList.get(i) != null || !getArCardRecordList.get(i).equals("")) {
    				Map eatInfo = (Map) getArCardRecordList.get(i);
    				eatInfo.put("mealInfoList", arCardRecordSer.getArCardRecordCommon(eatInfo, "mealInfoList"));
    			}
    		}
    	}
    		*/
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo2(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("SHIFT_NAME", request.getParameter("seach_SHIFT_NO"));
		modelMap.put("personInfo",linkMap);

		return new ModelAndView("/ar/attendanceMintenance/viewArCardRecordDay",modelMap);
	}
	
	/**
	 * 显示刷卡页面(view ArCardRecord)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArCardRecordForSelf")
	public ModelAndView viewArCardRecordForSelfList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getArCardRecordList = this.arCardRecordSer.getArCardRecordForSelfList(request);
		modelMap.put("getArCardRecordList", getArCardRecordList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArCardRecordList.size());
		if ("".equals(request.getParameter("STIME")) || request.getParameter("STIME") == null) {
			modelMap.put("STIME", DateUtil.getSysdateStr("dd.MM.yyyy"));
		}
		
		if ("".equals(request.getParameter("RTIME")) || request.getParameter("RTIME") == null) {
			modelMap.put("RTIME", DateUtil.getSysdateStr("dd.MM.yyyy"));
		}
		
		if (request.getParameter("STIME") != null && request.getParameter("RTIME") != null) {
			modelMap.put("STIME", request.getParameter("STIME"));
			modelMap.put("RTIME", request.getParameter("RTIME"));
		}
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2386"));

		return new ModelAndView("/ar/attendanceMintenance/viewArCardRecordForSelf",modelMap);
	}

	/**
	 * 进入修改页面(update Cycle view)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArCardRecordView", method = RequestMethod.GET)
	public ModelAndView updateArCardRecordView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("arCardRecordInfo", arCardRecordSer.getArCardRecordInfo(request));

		return new ModelAndView("/ar/attendanceMintenance/updateArCardRecordView", modelMap);
	}

	/**
	 * 修改区间信息(update ArCardRecord Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArCardRecordInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateArCardRecordInfo(HttpServletRequest request)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arCardRecordSer.updateArCardRecordInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 修改成功
			map.put("formId", "viewarcardrecordForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 删除刷卡信息(delete ArCardRecord Info)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteArCardRecordInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteArCardRecordInfo(HttpServletRequest request)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arCardRecordSer.deleteArCardRecordInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功
			map.put("formId", "viewarcardrecordForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}
	
	/**
	 * 删除刷卡信息(delete ArCardRecord Info)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteArCardMealInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteArCardMealInfo(HttpServletRequest request)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arCardRecordSer.deleteArCardInfoCommon(request,"deleteArCardMealInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功
			map.put("formId", "viewarcardrecordForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 添加刷卡数据页面(view ArCardRecord)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArCardRecordInfoView")
	public ModelAndView addArCardRecordInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView(
				"/ar/attendanceMintenance/addArCardRecordInfoView", modelMap);
	}

	/**
	 * 保存区间信息(add ArCardRecord Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArCardRecordInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addArCardRecordInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arCardRecordSer.addArCardRecordInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 添加成功
			map.put("formId", "viewarcardrecordForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 获得人员列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewKeeperList")
	public ModelAndView viewKeeperList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List empTypeCodeList = this.cycleSer.getEmpTypeCodeListSUPERVISOR(request);
		List jobTypeGroupList=this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("empTypeCodeList", empTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("personList", attendanceKeeperSer.getPersonListView(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListCnt(request));

		return new ModelAndView("/ar/attendanceMintenance/viewKeeperList",modelMap);
	}
	
	/**
	 * 获得人员列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewKeeperList2")
	public ModelAndView viewKeeperList2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("personList", attendanceKeeperSer.getPersonListView2(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListCnt(request));

		return new ModelAndView("/ar/attendanceMintenance/viewKeeperList2",modelMap);
	}

	/**
	 *  刷卡数据导入（insertMacRecordListLGE）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertMacRecordListLGE")
	@ResponseBody
	public Map insertMacRecordListLGE(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String result = "";
		 
		if(admin.getCpnyId().equals("HTSV")){
			result = this.arReadCardSer.readMacRecordBJList(request); 
		}else if(admin.getCpnyId().equals("SPC_DL")){
			result = this.arReadCardSer.readMacRecordDLList(request);
		}else if(admin.getCpnyId().equals("HAE")){
			result = this.arReadCardSer.readMacRecordBJList(request);
		}else if(admin.getCpnyId().equals("SPC_SH")){
			result = this.arReadCardSer.readMacRecordHZList(request);
		}else if(admin.getCpnyId().equals("SPC_HZ")){
			result = this.arReadCardSer.readMacRecordHZList(request);
		}else if(admin.getCpnyId().equals("SPC_NJ")){
			result = this.arReadCardSer.readMacRecordHZList(request);
		}
		
			map.put("statusCode", "200");
			map.put("message",result );// 处理成功
			map.put("navTabId", "ar0104");
			map.put("callbackType", "closeCurrent");
		 
		map.put("result", result);

		return map;
	}
	
	/**
	 *  刷卡数据导入（insertMacRecordListLGE）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertMealRecordList")
	@ResponseBody
	public Map insertMealRecordList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String result = "";
		 
		result = this.arReadCardSer.readMacRecordDLList(request);
		
			map.put("statusCode", "200");
			map.put("message",result );// 处理成功
			map.put("navTabId", "ar1235");
			map.put("callbackType", "closeCurrent");
		 
		map.put("result", result);

		return map;
	}
	
/*---------------------------------------------------------------------------------------*/
	/**
	 * 刷卡维护数据导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempMacRecordsList")
	public ModelAndView viewImportExcelTempMacRecordsList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = arReadCardSer.getImportExcelTempMacRecordsList(request);
		int impTotalCnt = arReadCardSer.getImportExcelTempMacRecordsListCnt(request);
		int impErrCnt   = arReadCardSer.getImportExcelTempMacRecordsListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2386"));
		return new ModelAndView("/ar/attendanceMintenance/viewImportExcelTempMacRecordsList",modelMap);
	}
	
	/**
	 * 工资基础项目数据导出临时数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportMacRecordsListExcel")
	public void viewImportMacRecordsListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("部门");
		aliasNameList.add("时间");
		aliasNameList.add("类型(请填写sheet2中的数据)");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List MacRecordsTempList = arReadCardSer.getImportExcelTempMacRecordsList(request);
		for(int i=0;i<MacRecordsTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)MacRecordsTempList.get(i);
			map.put("CELL0", map1.get("EMPID"));
			map.put("CELL1", map1.get("CHINESENAME"));
			map.put("CELL2", map1.get("DEPT"));
			map.put("CELL2", map1.get("R_TIME"));
			map.put("CELL3", map1.get("DOOR_TYPE"));
			map.put("CELL4", map1.get("REMARK"));
			map.put("CELL5", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		String name = "viewMacRecordsListExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	/**
	 * 提交工资基础项目数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportMacRecordsListExcel")
	@ResponseBody
	public int createImportMacRecordsListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = arReadCardSer.importMacRecordsExcelExcel(request);
		
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 显示吃饭刷卡页面(view ArCardRecord)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArCardRecordMeal")
	public ModelAndView viewArCardRecordMealList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("STIME", DateUtil.getSysdateStr("dd.MM.yyyy"));
			modelMap.put("RTIME", DateUtil.getSysdateStr("dd.MM.yyyy"));
		}
    	List getArCardRecordMealList = this.arCardRecordSer.getArCardRecordMealList(request);
    	int getArCardRecordListMealCnt = getArCardRecordMealList.size();
    	modelMap.put("getArCardRecordMealList", getArCardRecordMealList);
    	modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArCardRecordListMealCnt);
    		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo2(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap);
			
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2386"));

		return new ModelAndView("/ar/attendanceMintenance/viewArCardRecordMeal",modelMap);
	}
	
}
