package com.ait.sys.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AffirmSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AffirmCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: zhouyq(zhouyeqing@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/sys/affirm")
public class AffirmCtroller {
	Logger logger = Logger.getLogger(AffirmCtroller.class);
	
	@Autowired
	private AffirmSer affirmSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;

	/**
	 * 页面跳转到决裁管理列表页面(Jump to the lists of management)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewAffirmList" )
	public ModelAndView viewAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List typeList=this.affirmSer.getCodeListByParentCodeAll(request);
		if(request.getParameter("seach_FLAG") != null && !"".equals(request.getParameter("seach_FLAG"))){
			List affirmList=this.affirmSer.getAffirmItemList_final(request,typeList);//从 SY_AFFIRM_RELATION_FINAL取决裁者
			int affirmCnt=this.affirmSer.getAffirmItemListCnt_final(request);
			modelMap.put("affirmList", affirmList);
			modelMap.put("typeList", typeList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME,  affirmCnt);
		}
		modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2343")) ;
		return new ModelAndView("/sys/affirm/viewAffirmList",modelMap);
	}
	
	/**
	 * 页面跳转到更新页面(Jump to the page for update)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateAffirmView" )
	public ModelAndView updateAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List getAffirmorsByEmpOrDeptAndType=this.affirmSer.getAffirmorsByEmpOrDeptAndType_final(request);//????
		if(getAffirmorsByEmpOrDeptAndType!=null&&getAffirmorsByEmpOrDeptAndType.size()>0){
			Map map=(Map)(getAffirmorsByEmpOrDeptAndType.get(0));
			modelMap.put("DEPTNO", map.get("DEPT_NO"));
			modelMap.put("DEPTNAME", map.get("DEPT_NAME"));
			modelMap.put("APPLY_TYPE_NAME", map.get("APPLY_TYPE_NAME"));
		}
		modelMap.put("AFFIRM_TYPE_NO", request.getParameter("AFFIRM_TYPE_NO"));
		modelMap.put("affirmorList", getAffirmorsByEmpOrDeptAndType);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/affirm/updateAffirmView",modelMap);
	}
	
	/**
	 * 添加页面根据决裁者的EMP_ID获取决裁者数据(search data  by the EMP_ID)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorsByAffirmIdAdd")
	public ModelAndView getAffirmorsByAffirmIdAdd(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List itemParameterList = this.affirmSer.getAffirmorList(request) ;
		modelMap.put("empList",itemParameterList);
		return new ModelAndView(modelMap);		
	} 
	
	/**
	 * 根据决裁者的EMP_ID获取决裁者数据(search data  by the EMP_ID)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorsByAffirmId")
	public ModelAndView getAffirmorsByAffirmId(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List itemParameterList = this.affirmSer.getAffirmorList(request) ;
		modelMap.put("empList",itemParameterList);
		return new ModelAndView(modelMap);		
	} 
	
	/**
	 * 根据申请者的EMP_ID获取决裁者数据(search data  by the EMP_ID of applyer)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorsByPersonId")
	public ModelAndView getAffirmorsByPersonId(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List affirmorList=this.affirmSer.getAffirmorsByEmpOrDeptAndType(request);
		modelMap.put("empList",affirmorList);
		return new ModelAndView(modelMap);		
	} 
		
	/**
	 * 跳转到每个员工的决裁者页面(jump to the page for a employee)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmerByEmpList")
	public ModelAndView viewAffirmerByEmpList(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		List typeList=this.affirmSer.getCodeListByParentCode(request);
		int typeCnt=this.affirmSer.getCodeListCntByParentCode(request);
		for(int j=0;j<typeList.size();j++){
			List list=this.affirmSer.getItemDetail(request,request.getParameter("empId"),((Map)typeList.get(j)).get("CODE_NO").toString());
			Map map=(Map)typeList.get(j) ;
			map.put("detailList",list);
		}
		modelMap.put("typeList", typeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,  typeCnt);
		modelMap.put("AFFIRMOR_ID", request.getParameter("empId"));
		return new ModelAndView("/sys/affirm/viewAffirmerByEmpList",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAffirmLevelInfo")
	@ResponseBody
	public Map deleteAffirmLevelInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.affirmSer.deleteAffirmLevelInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("navTabId", "sy0130");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
		}	
		return map;
	}
	
	/**
	 * 更新决裁者信息(update info of affirmors)
	 * Description:
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAffirmInfo")
	@ResponseBody
	public Map updateAffirmInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.affirmSer.updateAffirmInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//"修改成功"
			map.put("navTabId", "sy0130");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//"修改失败"
		}
		return map;		
	}
	
	/**
	 * 跳转到添加决裁者得页面(redirect to the page of add affirmors)
	 * Description:
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAffirmView")
	public ModelAndView addAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List typeList=this.affirmSer.getCodeListByParentCodeAll(request);
		modelMap.put("typeList",typeList) ;
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/affirm/addAffirmView", modelMap);
	}
	
	/**
	 * 根据部门的DEPTNO获取该部门下的人员(according the DEPTNO of the HR_DEPARTMENT,get the datas of this department)
	 * Description:
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getEmpByDeptId")
	public ModelAndView getEmpByDeptId(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		request.setCharacterEncoding("UTF-8");
		request.getParameter("DEPTNO");
		List empList=this.affirmSer.getEmpByDeptId(request);
		modelMap.put("empList",empList);
		writeJsonByAction(request,response,empList);
		return new ModelAndView(modelMap);		
	} 
	
	/**
	 * 实现部门和人员的联动
	 * Description:get employees according to the department
	 * @param request
	 * @param response
	 * @param empList
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void writeJsonByAction(HttpServletRequest request,HttpServletResponse response,List empList) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<select name='AFFIRM_OBJECT_EMP' id='emp_name' onchange=\"changeForAffirmor(this);\">");
		out.println("<option value=''>--"+TipMessage.getTipMessage("sys.affirm.title.choose",request)+"--</option>");
		for(int i=0;i<empList.size();i++){
			String personId=((Map)(empList.get(i))).get("PERSON_ID").toString();
			String name=((Map)(empList.get(i))).get("LOCAL_NAME").toString();
			out.println("<option value='"+personId+"'>"+name+"</option>");
		}
		out.println("</select>");
		out.flush();
		out.close();
	}
	
	/**
	 * 给部门或人员添加决裁流程
	 * Description:add an approval process
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertAffirmInfo")
	@ResponseBody
	public Map insertAffirmInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.affirmSer.insertAffirmInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));
			map.put("navTabId", "sy0130");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));
		}
		return map;		
	}
	
	/**
	 * 删除决裁流程
	 * Description:delete an approval process
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAffirmInfo")
	@ResponseBody
	public Map deleteAffirmInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int isDelete = this.affirmSer.deleteAffirmInfo(request);
		if(isDelete == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//"删除成功"
			map.put("navTabId", "sy0130");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//"删除失败"
		}	
		return map;
	}
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPersonCnt")
	@ResponseBody
	public Map getPersonCnt(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=this.affirmSer.getPidEidListFull(request);
		
		map.put("perCnt", pidEidList.size());
		if(pidEidList.size()==1){
			map.put("emp_type_code", ((Map)pidEidList.get(0)).get("EMP_TYPE_CODE"));
			map.put("personId", ((Map)pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map)pidEidList.get(0)).get("EMPID"));
			map.put("empName", ((Map)pidEidList.get(0)).get("LOCAL_NAME"));
			map.put("deptName", ((Map)pidEidList.get(0)).get("DEPTNAME"));
		}
		return map; 
	}
	

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPersonCntByEmpid")
	@ResponseBody
	public Map getPersonCntByEmpid(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=this.affirmSer.getPersonCntByEmpid(request);
		
		map.put("perCnt", pidEidList.size());
		if(pidEidList.size()==1){
			map.put("personId", ((Map)pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map)pidEidList.get(0)).get("EMPID"));
			map.put("empName", ((Map)pidEidList.get(0)).get("LOCAL_NAME"));
			map.put("deptLevelName", ((Map)pidEidList.get(0)).get("DEPT_LEVEL_NAME"));
			map.put("deptName", ((Map)pidEidList.get(0)).get("DEPTNAME"));
			map.put("POSITION_NAME", ((Map)pidEidList.get(0)).get("POSITION_NAME"));
			map.put("STATUS_NAME", ((Map)pidEidList.get(0)).get("STATUS_NAME"));
			map.put("POST_GRADE_NAME", ((Map)pidEidList.get(0)).get("POST_GRADE_NAME"));
			map.put("DATE_STARTED", ((Map)pidEidList.get(0)).get("DATE_STARTED"));
			map.put("dutyNo", ((Map)pidEidList.get(0)).get("DUTY_NO"));
			map.put("POST_FAMILY", ((Map)pidEidList.get(0)).get("POST_FAMILY_NO"));
			map.put("shiftName", ((Map)pidEidList.get(0)).get("SHIFT_NAME"));
			map.put("OFFICE_PHONE", ((Map)pidEidList.get(0)).get("OFFICE_PHONE"));
		}
		return map;
	}
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPersonCnt2")
	@ResponseBody
	public Map getPersonCnt2(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=this.affirmSer.getPidEidList2(request);
		
		map.put("perCnt", pidEidList.size());
		if(pidEidList.size()==1){
			map.put("personId", ((Map)pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map)pidEidList.get(0)).get("EMPID"));
			map.put("empName", ((Map)pidEidList.get(0)).get("LOCAL_NAME"));
			map.put("deptName", ((Map)pidEidList.get(0)).get("DEPTNAME"));
			map.put("dutyName",  ((Map)pidEidList.get(0)).get("DUTY_NO"));
			map.put("workArea",  (StringUtil.checkNull(((Map)pidEidList.get(0)).get("WORK_AREA"))));
			map.put("empTypeName",  ((Map)pidEidList.get(0)).get("EMPTYPENAME"));
		}
		return map;
	}
	
	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAffirmorsList")
	public ModelAndView viewAffirmorsEmpIdList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("turn_to_url",request.getParameter("turnToUrl"));
		modelMap.put("empList",this.affirmSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmSer.getEmpIdListCnt(request)) ;
		String empId=request.getParameter("empId_sy0482")==null?"":request.getParameter("empId_sy0482").toString();
		String empName=request.getParameter("empName_sy0482")==null?"":request.getParameter("empName_sy0482").toString();
		modelMap.put("empId", empId);
		String personId=request.getParameter("personId_sy0482")==null?"":request.getParameter("personId_sy0482").toString();
		modelMap.put("personId", personId);
		modelMap.put("empName", empName);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/affirm/viewAffirmorsList",modelMap);		
	} 
	/**
	 * 裁决委任查看(Jump to the lists of management)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/affirmAppoint" )
	public ModelAndView affirmAppoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List affirmList=this.affirmSer.affirmAppointList(request);
		modelMap.put("affirmList", affirmList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		System.out.println("%%%%******");
		return new ModelAndView("/sys/affirm/affirmAppoint",modelMap);
	}
	/**
	 * 裁决委任(Jump to the lists of management)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateaffirmAppoints" )
	@ResponseBody
	public Boolean updateaffirmAppoints(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		boolean flag;
		System.out.println("**");
		flag=this.affirmSer.updateaffirmAppoint(request);
		modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2343")) ;
		System.out.println("%%%%******");
		return flag;//new ModelAndView("/sys/affirm/affirmAppoint",modelMap);
	}
	/**
	 * 取消委任(Jump to the lists of management)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cancleAppoint" )
	@ResponseBody
	public String cancleAppoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		boolean flag;
		System.out.println("*********");
		String result = "";
		String affirm_no=request.getParameter("affirm_no");
		System.out.println("*********"+affirm_no);

		boolean bol = this.affirmSer.cancleAppoint(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**
	 * 导出最终确认设置页面数据
	 */
	@RequestMapping(value = "/viewAffirmFinalExcel")
	public ModelAndView viewAffirmFinalExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		    List typeList=this.affirmSer.getCodeListByParentCodeAll(request);
			List affirmList=this.affirmSer.getAffirmItemList_final(request,typeList);//从 SY_AFFIRM_RELATION_FINAL取决裁者
			int affirmCnt=this.affirmSer.getAffirmItemListCnt_final(request);
			modelMap.put("affirmList", affirmList);
			modelMap.put("typeList", typeList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME,  affirmCnt);
			modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2343")) ;
		return new ModelAndView("/sys/affirm/viewAffirmFinalExcel",modelMap);
	}
	

	/**
	 * 最终确认批量导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLAffirmModule")
	public void exportBatchLAffirmModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("审批对象");
		aliasNameList.add("审批类型");
		aliasNameList.add("审批等级");
		aliasNameList.add("审批者社号");
		aliasNameList.add("审批者姓名");
		aliasNameList.add(" ");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "XX部门");
		map.put("CELL1", "年假");
		map.put("CELL2", "1");
		map.put("CELL3", "12300000");
		map.put("CELL4", "王某某");
		map.put("CELL5", " ");
		list.add(map);
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		mapNameList.add("申请类型参考");
		mapList.add(" SELECT D.CONTENT FROM (SELECT A.*,  ROWNUM ORDER_NO FROM (SELECT A.CODE_NO, S.CONTENT FROM SY_CODE A, SY_CODE_PARAM SC, SY_GLOBAL_NAME S " + 
			       " WHERE PARENT_CODE_NO in (21,31) AND A.CODE_NO = S.NO(+) AND S.LANGUAGE(+) = 'zh' AND SC.CPNY_ID = '" + admin.getCpnyId() + "'" +
			       " AND A.CODE_NO = SC.CODE_NO AND A.ACTIVITY = 1 ORDER BY A.PARENT_CODE_NO) A UNION " + 
			       " SELECT B.*, ROWNUM + 20 ORDER_NO FROM (SELECT T.CODE_NO, U.CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM P " + 
			       " WHERE T.CODE_NO = U.NO(+) AND T.PARENT_CODE_NO = '16413' AND U.LANGUAGE(+) = 'zh' AND P.CODE_NO = T.CODE_NO AND P.CPNY_ID = '" + admin.getCpnyId() + "'" +
			       " AND T.ACTIVITY = 1 AND P.ACTIVITY = 1 AND T.CODE_NO NOT IN (21,31) order by parent_code_no) B ) D ORDER BY ORDER_NO ");
		mapNameList.add("部门参考");
		mapList.add(" SELECT ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' AND USE_YN = 'Y'");
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"aff_info");
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
	@RequestMapping(value = "/viewImportExcelAffirmDataList")
	public ModelAndView viewImportExcelAffirmDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paAffirmTempList = this.affirmSer.getAffirmTempList(request);
		int paAffirmTempCnt = this.affirmSer.getAffirmTempCnt(request , "T");
		int errorCnt = this.affirmSer.getAffirmTempCnt(request , "E");
		
		modelMap.put("paAffirmTempList", paAffirmTempList);
		modelMap.put("paAffirmTempCnt", paAffirmTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paAffirmTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paAffirmTempCnt);
		return new ModelAndView("/sys/affirm/viewImportExcelAffirmDataList", modelMap);
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
	@RequestMapping(value = "/submitImportExcelAffirmEmpData")
	@ResponseBody
	public Map submitImportExcelAffirmEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.affirmSer.submitImportExcelAffirmEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			//jo.put("navTabId", "sy0130");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 最终确认导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLAffirm")
	public void exportBatchLAffirm(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("审批对象");
		aliasNameList.add("审批类型");
		aliasNameList.add("审批等级");
		aliasNameList.add("审批者社号");
		aliasNameList.add("审批者姓名");
		aliasNameList.add(" ");

		List affirmList=this.affirmSer.getAffirmItemList_finalExcel(request);//从 SY_AFFIRM_RELATION_FINAL取决裁者
		List list = new ArrayList();
		if(affirmList != null && affirmList.size()> 0){
			LinkedHashMap affirmMap = null;
			for(int i=0;i<affirmList.size();i++){
				affirmMap = (LinkedHashMap)affirmList.get(i);
				LinkedHashMap map = new LinkedHashMap();
				map.put("CELL0", affirmMap.get("AFFIRM_OBJECT"));
				map.put("CELL1", affirmMap.get("AFFIRM_TYPE"));
				map.put("CELL2", affirmMap.get("AFFIRM_LEVEL"));
				map.put("CELL3", affirmMap.get("EMPID"));
				map.put("CELL4", affirmMap.get("LOCAL_NAME"));
				map.put("CELL5", " ");
				list.add(map);
			}
		}
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		mapNameList.add("申请类型参考");
		mapList.add(" SELECT D.CONTENT FROM (SELECT A.*,  ROWNUM ORDER_NO FROM (SELECT A.CODE_NO, S.CONTENT FROM SY_CODE A, SY_CODE_PARAM SC, SY_GLOBAL_NAME S " + 
			       " WHERE PARENT_CODE_NO in (21,31) AND A.CODE_NO = S.NO(+) AND S.LANGUAGE(+) = 'zh' AND SC.CPNY_ID = '" + admin.getCpnyId() + "'" +
			       " AND A.CODE_NO = SC.CODE_NO AND A.ACTIVITY = 1 ORDER BY A.PARENT_CODE_NO) A UNION " + 
			       " SELECT B.*, ROWNUM + 20 ORDER_NO FROM (SELECT T.CODE_NO, U.CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM P " + 
			       " WHERE T.CODE_NO = U.NO(+) AND T.PARENT_CODE_NO = '16413' AND U.LANGUAGE(+) = 'zh' AND P.CODE_NO = T.CODE_NO AND P.CPNY_ID = '" + admin.getCpnyId() + "'" +
			       " AND T.ACTIVITY = 1 AND P.ACTIVITY = 1 AND T.CODE_NO NOT IN (21,31) order by parent_code_no) B ) D ORDER BY ORDER_NO ");
		mapNameList.add("部门参考");
		mapList.add(" SELECT ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' AND USE_YN = 'Y'");
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"aff_info");
	}
}
