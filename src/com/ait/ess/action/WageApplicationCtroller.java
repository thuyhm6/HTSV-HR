package com.ait.ess.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.portlet.ModelAndView;

import com.ait.Interface.ParentCtroller;
import com.ait.ar.service.CycleSer;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.WageApplicationSer;
import com.ait.org.service.OrgManageSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AttendItemSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 员工自助费用申请
* @ClassName: WageApplicationCtroller 
* @Description: TODO
* @author yuanxq@ait.net.cn
* @date 2014-7-7 下午03:05:24 
*
 */
@Controller
@RequestMapping(value = "/ess/wageApplication")
@SuppressWarnings("unchecked")
public class WageApplicationCtroller extends ParentCtroller{
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private WageApplicationSer wageApplicationSer;
	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PaTempSalesSer paTempSalesSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private AffirmLeaveApplySer affirmApplySer;
	@Autowired
	private OrgManageSer orgManageSer;
	@Autowired
	private CycleSer cycleSer;
	
	/**
	 * 员工自助（费用）查看(view personal information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOtWageApplicationList")
	public ModelAndView viewWageApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = wageApplicationSer.getListByRequest(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("wageAppList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getListCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217886"));	//如果请求中没有菜单ID就强制设置为217886
		return new ModelAndView("/ess/wageApplication/viewOtWageApplicationList",modelMap);
	}
	/**
	 * 进入添加申请页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNewWageApplicationView")
	public ModelAndView viewaddNewWageApplicationViewList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		//设置决裁人
		List affirmorList = this.paTempSalesSer.getApplyFeeList(request);
		modelMap.put("affirmorList", affirmorList);
		//List excelList = paTempSalesSer.getExcelMessage(request);
		List messageList = wageApplicationSer.getApplicationByNoApplyNo(request);//上传人员列表
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getApplicationByNoApplyNoCnt(request));
		modelMap.put("excelList", messageList);
		List fileList = wageApplicationSer.getAppliFileList(request);//上传附件列表
		modelMap.put("fileList", fileList);
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 进入添加申请页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWageApplicationTempList")
	public ModelAndView viewWageApplicationTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = wageApplicationSer.getWageApplicationTempList(request);
		int impTotalCnt = wageApplicationSer.getWageApplicationTempCnt(request);	
		int impErrCnt   = wageApplicationSer.getWageApplicationTempErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/wageApplication/viewWageApplicationTempList", modelMap);
	}
	
	/**
	 * 费用申请导出数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportWageApplicationExcelTempListExcel")
	public void viewImportPaAllowanceExcelTempListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	    AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("费用发放时间");
		aliasNameList.add("费用结束时间");
		aliasNameList.add("金额");
		aliasNameList.add("费用类型");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List list = new ArrayList();
		List paTempSalesTempList = wageApplicationSer.getWageApplicationTempList(request);
		for(int i=0;i<paTempSalesTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)paTempSalesTempList.get(i);
			map.put("CELL0", map1.get("COSTEMP"));
			map.put("CELL1", map1.get("START_DATE"));
			map.put("CELL2", map1.get("END_DATE"));
			map.put("CELL3", map1.get("MONEY"));
			map.put("CELL4", map1.get("TYPENAME"));
			map.put("CELL5", map1.get("DEMO"));
			map.put("CELL6", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		mapNameList.add("费用类型");
		List mapList = new ArrayList();
		String deptSql = "SELECT REGEXP_SUBSTR(SALARY_NAME,'[^,]+',1,ROWNUM) CONTENT FROM (SELECT SALARY_NAME FROM PA_APPLICATION_FEE WHERE PERSON_ID = "+admin.getPersonId();
	       deptSql +=")CONNECT BY ROWNUM<= LENGTH(SALARY_NAME) - LENGTH(REPLACE(SALARY_NAME,',')) + 1";
	    mapList.add(deptSql);
	    modelMap.put("FileName", "temp_TempPayRequest");
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList);
	}
	
	
	/**
	 * 职责津贴导入数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportApplicationExcelTempListExcel")
	@ResponseBody
	public int createEvaluationDataImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = wageApplicationSer.importApplicationExcelTempExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 保存添加信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveNewWageApplicationView")
	@ResponseBody
	public Map saveNewWageApplicationView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = request.getParameter("FLAG");
		int errorInt = 0;
		if("1".equals(flag)){//保存数据
			errorInt = paTempSalesSer.saveWageApplication(request);
			wageApplicationSer.updateWageAppFile(request,errorInt);
		}else if ("0".equals(flag)) {//撤销删除数据
			errorInt = paTempSalesSer.deleteWageApplicationDetail(request);
			wageApplicationSer.deleteAllWageAppFile(request);
		}
		if (errorInt > 0) {
			map.put("statusCode", "200");
			if("1".equals(flag)){
				map.put("callbackType", "closeCurrent");
				map.put("message", "费用申请成功!");
			}
			/*else if ("0".equals(flag)) {
				map.put("message", "费用删除成功!");
			}*/
			map.put("navTabId", "ess06301");
		} else {
			map.put("statusCode", "300");
			if("1".equals(flag)){
				map.put("message", "费用申请失败!");
			}
			/*else if ("0".equals(flag)) {
				map.put("message", "费用删除失败!");
			}*/
		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217886"));
		return map;
	}
	
	/**
	 * 费用申请导出Excel模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportEssApplicationModule")
	public void exportEssApplicationModule(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");//社号
		aliasNameList.add("费用发放时间");//申请费用发放开始时间
		aliasNameList.add("费用结束时间");//申请费用发放结束时间
		aliasNameList.add("金额");//金额
		aliasNameList.add("费用类型");//费用类型
		aliasNameList.add("备注(最多100个字符)");//备注
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", admin.getCpnyId().substring(3)+"00001");
		map.put("CELL1", "201406");
		map.put("CELL2", "201409");
		map.put("CELL3", "8000");
		map.put("CELL4", "讲师费");
		map.put("CELL5", 12345);
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		mapNameList.add("费用类型");
		List mapList = new ArrayList();
		String deptSql = "SELECT REGEXP_SUBSTR(SALARY_NAME,'[^,]+',1,ROWNUM) CONTENT FROM (SELECT SALARY_NAME FROM PA_APPLICATION_FEE WHERE PERSON_ID = "+admin.getPersonId();
	       deptSql +=")CONNECT BY ROWNUM<= LENGTH(SALARY_NAME) - LENGTH(REPLACE(SALARY_NAME,',')) + 1";
	    mapList.add(deptSql);
	    modelMap.put("FileName", "temp_TempPayRequest");
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList);
	}
	/**
	 * 费用申请页的点击查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showWageAppliFileList")
	public ModelAndView viewShowWageAppliFileListList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List fileList = wageApplicationSer.getAppliFileList(request);//上传附件列表
		modelMap.put("fileList", fileList);
		return new ModelAndView("/ess/wageApplication/showWageAppliFileList",modelMap);
	}
	/**
	 * 费用申请页的点击查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showWageApplicationList")
	public ModelAndView viewWageApplicationExcelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List messageList = wageApplicationSer.getApplicationList(request);//上传人员列表SS
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getApplicationListCnt(request));
		List proveList = wageApplicationSer.getProveAppList(request);//决裁者列表
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("checkorList", checkorList);
		modelMap.put("messageList", messageList);
		modelMap.put("proveList", proveList);
		return new ModelAndView("/ess/wageApplication/showWageApplicationList",modelMap);
	}
	
	/**
	 * 费用审批列表(Application apply affirm list)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWageApplicationList")
	public ModelAndView viewDecisionApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int dataListCnt = wageApplicationSer.getWageApplicationCnt(request);
		modelMap.put("wageList", wageApplicationSer.getWageApplicationList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dataListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217886"));
		return new ModelAndView("/ess/wageApplication/viewWageApplicationList",modelMap);
	}
	
	/**
	 * 费用审批列表(Application apply affirm list)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchContent")
	public ModelAndView batchContent(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView("/ess/wageApplication/batchContent",modelMap);
	}
	
	/**
	 * 费用Check列表(Application apply affirm list)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCheckApplicationList")
	public ModelAndView viewCheckApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		int dataListCnt = wageApplicationSer.getProveCheckAppListCnt(request);
		modelMap.put("wageList", wageApplicationSer.getProveCheckAppList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dataListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218270"));
		return new ModelAndView("/ess/wageApplication/viewCheckApplicationList",modelMap);
	}
	
	/**
	 * 费用决裁页面查看详情
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/showApplicationList")
	public ModelAndView viewShowApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//查看信息list
		List messageList = wageApplicationSer.getApplicationList(request);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getApplicationListCnt(request));
		modelMap.put("messageList", messageList);
		return new ModelAndView("/ess/wageApplication/showApplicationList",modelMap);
	}
	
	/**
	 * 费用决裁页面审批
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/proveApplicationList")
	public ModelAndView viewProveApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		List messageList = wageApplicationSer.getApplicationList(request);//上传人员列表
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getApplicationListCnt(request));
		List proveList = wageApplicationSer.getProveAppList(request);//决裁者列表
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("checkorList", checkorList);
		modelMap.put("messageList", messageList);
		modelMap.put("proveList", proveList);
		return new ModelAndView("/ess/wageApplication/proveApplicationList",modelMap);
	}
	
	/**
	 * 决裁---费用申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveApplication")
	@ResponseBody
	public Map approveApplication(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = affirmApplySer.approveApplication(request);
			if (result == 1) {
				affirmApplySer.updateApplicatCheck(request);
				map.put("navTabId", "ess0244");
				map.put("callbackType","closeCurrent");
				map.put("message", "费用决裁成功!");//"保存费用申请成功!"
				map.put("statusCode", "200");
			}else {
				map.put("message", "费用决裁失败！请重新决裁");//"保存费用申请出错,请重新申请!"
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "费用决裁失败！请重新决裁");//"保存费用申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * check费用申请
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkApplicationInfo")
	public ModelAndView viewCheckApplicationInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//List messageList = wageApplicationSer.getApplicationList(request);
//		is_check 用来判断查看或提交权限
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());
		/*String names = "";
		String name = "";
		for(int i=0;i<messageList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)messageList.get(i);
			name = applyorMap.get("EMPNAME").toString();
			names = name + ",";
		}
		names = names.substring(0, names.length()-1);
		modelMap.put("names", names);*/
		List messageList = wageApplicationSer.getApplicationList(request);//上传人员列表
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getApplicationListCnt(request));
		List affirmorList = wageApplicationSer.getProveAppList(request);//决裁者列表
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("is_check", is_check);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("messageList", messageList);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("checkorList", checkorList);
		return new ModelAndView("/ess/wageApplication/checkApplicationInfo", modelMap);
	}
	
	/**
	 * 费用审批的批量操作
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-10 上午10:51:46 
	* @version V1.0
	 */
	@RequestMapping(value = "/batchSubmitApplication")
	@ResponseBody
	public Map<String, Object> batchSubmitApplication(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = wageApplicationSer.updateBatchSubmitApp(request);
			if (result == 1) {
				map.put("navTabId", "ess0244");
				map.put("message", "批量费用申请决裁操作成功!");
				map.put("statusCode", "200");
			}else{
				map.put("message", "批量费用申请决裁操作出错,请重新操作!");
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "批量费用申请决裁操作出错,请重新操作!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 费用申请的撤销
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-10 上午10:51:46 
	* @version V1.0
	 */
	@RequestMapping(value = "/backSubmitOtApplication")
	@ResponseBody
	public Map<String, Object> backSubmitOtApplication(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			//result = wageApplicationSer.getCancleApplicationState(request);
			result = wageApplicationSer.getCancleSigleApplicationState(request);
			if (result == 1) {
				int flag = wageApplicationSer.deleteOtApplication(request);
				if(flag == 1){
					map.put("navTabId", "ess0244");
					map.put("message", "费用申请撤销操作成功!");
					map.put("statusCode", "200");
				}else{
					map.put("message", "费用申请撤销操作出错,请重新操作!");
					map.put("statusCode", "300");
				}
			}else{
				map.put("message", "费用已发放,只能按人别撤销!");
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "费用申请撤销操作出错,请重新操作!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 费用申请的删除
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-10 上午10:51:46 
	* @version V1.0
	 */
	@RequestMapping(value = "/deleteOtApplication")
	@ResponseBody
	public Map<String, Object> deleteOtApplication(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int flag = 0;
		try {
			flag = wageApplicationSer.deleteOtApplication(request);
			if(flag == 1){
				map.put("navTabId", "ess0244");
				map.put("message", "费用申请删除操作成功!");
				map.put("statusCode", "200");
			}else{
				map.put("message", "费用申请删除操作出错,请重新操作!");
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "费用申请删除操作出错,请重新操作!");
			map.put("statusCode", "300");
		}
		map.put("result", flag);
		return map;
	}
	
	/**
	 * 费用申请撤销查看页面(个人)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPbWageApplicationList")
	public ModelAndView viewPbWageApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = wageApplicationSer.getPbWageList(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("wageAppList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, wageApplicationSer.getPbWageCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218309"));	//如果请求中没有菜单ID就强制设置为218309
		return new ModelAndView("/ess/wageApplication/viewPbWageApplicationList",modelMap);
	}
	
	/**
	 * 费用申请的撤销
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-10 上午10:51:46 
	* @version V1.0
	 */
	@RequestMapping(value = "/backPbOtApplication")
	@ResponseBody
	public Map<String, Object> backPbOtApplication(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = wageApplicationSer.getBackPbOtApplication(request);
			if (result == 1) {
				map.put("navTabId", "ess06302");
				map.put("message", "费用申请撤销操作成功!");
				map.put("statusCode", "200");
			}else{
				map.put("message", "费用申请撤销操作出错,请重新操作!");
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "费用申请撤销操作出错,请重新操作!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 查看费用申请是否关闭的状态
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-10 上午10:51:46 
	* @version V1.0
	 */
	@RequestMapping(value = "/checkApplyApplicationState")
	@ResponseBody
	public Map<String, Object> checkApplyApplicationState(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = wageApplicationSer.checkApplyApplicationState(request);
			if (result == 1) {
				map.put("statusCode", "200");
			}else{
				map.put("message", "本月费用申请已关闭,不可操作!");
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "费用申请状态错误,请重新操作!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 费用申请-上传附件
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadApplicationFile")
	public ModelAndView openFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView("/ess/wageApplication/uploadApplicationFile",modelMap);
	}
	/**
	 * 删除附件
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午11:25:39 
	* @version V1.0
	 */
	@RequestMapping(value = "/deleteApplicationFile")
	@ResponseBody
	public Map<String, Object> deleteApplicationFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = wageApplicationSer.deleteWageAppFile(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", "附件删除成功！");
			}else{
				map.put("message", "附件删除失败！");
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("message", "附件删除错误,请重新操作!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 附件上传
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn 
	* @date 2014-8-16 下午13:22:11 
	* @version V1.0
	 */
	@RequestMapping(value = "/submitApplicationFile")
	@ResponseBody
	public void submitApplicationFile(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(10485760);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch (MaxUploadSizeExceededException e) {
			}
		} else if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
		} 
		//long PARAMDATANO = new Date().getTime();
		/** 构建图片保存的目录 **/
		String logoPathDir = "/resources/uploadFile/wageApp/";
		/** 得到图片保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists()){
			logoSaveFile.mkdirs();
		}
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		/** 获取文件的后缀 **/
		//String suffix = multipartFile.getOriginalFilename().substring(
		//		multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		// 构建文件名称
		String oldName = multipartFile.getOriginalFilename();
		String newName = request.getParameter("name");//PARAMDATANO + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = logoRealPathDir + File.separator + newName;
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			map.put("sign", 2);
			map.put("statusCode", 200);
			map.put("oldName", oldName);
			map.put("fileUrl", logoPathDir+newName);
			wageApplicationSer.saveWageAppFile(request,map);
			//map.put("statusCode", "200");
		} catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("sign", -1);
			map.put("statusCode", 300);
		} catch (IOException e) {
			map.put("sign", -1);
			map.put("statusCode", 300);
			e.printStackTrace();
		}
	}
	/**
	 * 附件下载
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 下午06:11:30 
	* @version V1.0
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/downLoadFile")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		List fileList = wageApplicationSer.getAppliFileList(request);//上传附件列表
		if(fileList.size()>0){
			Map map = (Map) fileList.get(0);
			String fileName=request.getRealPath("")+map.get("FILEURL");
			String fileNameA = URLEncoder.encode(map.get("FILENAME").toString(), "UTF-8");
			response.setHeader("Content-Disposition", "attachment;fileName="+fileNameA);
			try {
				File file=new File(fileName);
				InputStream inputStream=new FileInputStream(file);
				OutputStream os=response.getOutputStream();
				byte[] b=new byte[1024];
				int length;
				while((length=inputStream.read(b))>0){
					os.write(b,0,length);
				}
				inputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 费用履历查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWageCheckList")
	public ModelAndView viewWageCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List feeList = this.wageApplicationSer.getWageCheckList(request);
		int feeCnt = this.wageApplicationSer.getWageCheckCnt(request);
		if(admin.getCpnyId()=="TSTO" || admin.getCpnyId().equals("TSTO")){
		    List payAreaInfoList = this.orgManageSer.getPayAreaInfoList(request);
		    modelMap.put("payAreaInfoList", payAreaInfoList);
		}
		modelMap.put("feeList", feeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, feeCnt);
		modelMap.put("cpny_id", admin.getCpnyId());
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217847"));
		return new ModelAndView(
				"/ess/wageApplication/viewWageCheckList", modelMap);
	}

}