package com.ait.sys.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.ait.ess.dao.InfoApplyDao;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AffirmSpecialSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AffirmSpecialCtroller.java
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/sys/affirmSpecial")
public class AffirmSpecialCtroller {
	Logger logger = Logger.getLogger(AffirmSpecialCtroller.class);
	
	@Autowired
	private AffirmSpecialSer affirmSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private InfoApplyDao infoApplyDao;
	
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
	@RequestMapping(value="/viewAffirmSpecialList" )
	public ModelAndView viewAffirmSpecialList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List typeList=this.affirmSer.getCodeListByParentCodeAll(request);
	//	List affirmList=this.affirmSer.getAffirmItemList(request,typeList);
	//	int affirmCnt=this.affirmSer.getAffirmItemListCnt(request);
		String firstFlag = request.getParameter("firstFlag");
			List affirmSpecialList=this.affirmSer.getAffirmSpecialItemList_special(request,typeList);//从 SY_AFFIRM_RELATION_special取决裁者
			int affirmSpecialCnt=this.affirmSer.getAffirmSpecialItemListCnt_special(request);
			modelMap.put("affirmSpecialList", affirmSpecialList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME,  affirmSpecialCnt);
		modelMap.put("typeList", typeList);
		modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2343")) ;
		return new ModelAndView("/sys/affirmSpecial/viewAffirmSpecialList",modelMap);
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
	@RequestMapping(value="/updateAffirmSpecialView" )
	public ModelAndView updateAffirmSpecialView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getAffirmorsSpecialByEmpOrDeptAndType=this.affirmSer.getAffirmorsSpecialByEmpOrDeptAndType_special(request);//????
		if(getAffirmorsSpecialByEmpOrDeptAndType!=null&&getAffirmorsSpecialByEmpOrDeptAndType.size()>0){
			Map map=(Map)(getAffirmorsSpecialByEmpOrDeptAndType.get(0));
			modelMap.put("DEPTNO", map.get("DEPT_NO"));
			if(!"".equals(map.get("DEPT_NAME")) && map.get("DEPT_NAME")!=null){
			    modelMap.put("DEPTNAME", map.get("DEPT_NAME"));
			}else{
				//如果部门名称不存在就说明是人员  根据person_id查找姓名
				Map mapObject = new LinkedHashMap();
				Object object = null;
				mapObject.put("CPNY_ID", admin.getCpnyId());
				mapObject.put("PERSON_ID", map.get("DEPT_NO"));
				mapObject.put("interLanguage", map.get("interLanguage"));
				object = mapObject;
				Map person_object_object = (Map) infoApplyDao.getPersonalInfoByPid(object);
				modelMap.put("DEPTNAME", person_object_object.get("LOCAL_NAME"));
			}
			modelMap.put("APPLY_TYPE_NAME", map.get("APPLY_TYPE_NAME"));
		}
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("AFFIRM_TYPE_NO", request.getParameter("AFFIRM_TYPE_NO"));
		modelMap.put("affirmorSpecialList", getAffirmorsSpecialByEmpOrDeptAndType);
		return new ModelAndView("/sys/affirmSpecial/updateAffirmSpecialView",modelMap);
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
	@RequestMapping(value = "/getAffirmorsSpecialByAffirmIdAdd")
	public ModelAndView getAffirmorsByAffirmIdAdd(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List itemParameterList = this.affirmSer.getAffirmorSpecialList(request) ;
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
	@RequestMapping(value = "/getAffirmorsSpecialByAffirmId")
	public ModelAndView getAffirmorsSpecialByAffirmId(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List itemParameterList = this.affirmSer.getAffirmorSpecialList(request) ;
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
	@RequestMapping(value = "/getAffirmorsSpecialByPersonId")
	public ModelAndView getAffirmorsSpecialByPersonId(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List affirmorList=this.affirmSer.getAffirmorsSpecialByEmpOrDeptAndType(request);
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
	@RequestMapping(value = "/viewAffirmorSpecialByEmpList")
	public ModelAndView viewAffirmorSpecialByEmpList(HttpServletRequest request,
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
		return new ModelAndView("/sys/affirmSpecial/viewAffirmorSpecialByEmpList",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAffirmSpecialLevelInfo")
	@ResponseBody
	public Map deleteAffirmSpecialLevelInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.affirmSer.deleteAffirmSpecialLevelInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("navTabId", "sy0010sp");
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
	@RequestMapping(value = "/updateAffirmSpecialInfo")
	@ResponseBody
	public Map updateAffirmSpecialInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.affirmSer.updateAffirmSpecialInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//"修改成功"
			map.put("navTabId", "sy0010sp");
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
	@RequestMapping(value = "/addAffirmSpecialView")
	public ModelAndView addAffirmSpecialView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List typeList=this.affirmSer.getCodeListByParentCodeAll(request);
		modelMap.put("typeList",typeList) ;
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/affirmSpecial/addAffirmSpecialView", modelMap);
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
	@RequestMapping(value = "/insertAffirmSpecialInfo")
	@ResponseBody
	public Map insertAffirmSpecialInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.affirmSer.insertAffirmSpecialInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));
			map.put("navTabId", "sy0010sp");
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
	@RequestMapping(value = "/deleteAffirmSpecialInfo")
	@ResponseBody
	public Map deleteAffirmSpecialInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int isDelete = this.affirmSer.deleteAffirmSpecialInfo(request);
		if(isDelete == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//"删除成功"
			map.put("navTabId", "sy0010sp");
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
		
	 	List pidEidList=this.affirmSer.getPidEidList(request);
		
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
	@RequestMapping(value = "/viewAffirmorsSpecialList")
	public ModelAndView viewAffirmorsSpecialEmpIdList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("turn_to_url",request.getParameter("turnToUrl"));
		modelMap.put("empList",this.affirmSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmSer.getEmpIdListCnt(request)) ;
		String empId=request.getParameter("empId_sy0130_add")==null?"":request.getParameter("empId_sy0130_add").toString();
		String empName=request.getParameter("empName_sy0130_add")==null?"":request.getParameter("empName_sy0130_add").toString();
		modelMap.put("empId", empId);
		String personId=request.getParameter("personId_sy0130_add")==null?"":request.getParameter("personId_sy0130_add").toString();
		modelMap.put("personId", personId);
		modelMap.put("empName", empName);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/affirmSpecial/viewAffirmorsSpecialList",modelMap);		
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
	@RequestMapping(value="/affirmSpecialAppoint" )
	public ModelAndView affirmSpecialAppoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List affirmList=this.affirmSer.affirmSpecialAppointList(request);
		modelMap.put("affirmList", affirmList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		System.out.println("%%%%******");
		return new ModelAndView("/sys/affirmSpecial/affirmSpecialAppoint",modelMap);
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
	@RequestMapping(value="/updateaffirmSpecialAppoints" )
	@ResponseBody
	public Boolean updateaffirmSpecialAppoints(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		boolean flag;
		System.out.println("**");
		flag=this.affirmSer.updateAffirmSpecialAppoint(request);
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
	 * 特殊审批线导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLAffirmSpecial")
	public void exportBatchLAffirmSpecial(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("审批对象");
		aliasNameList.add("审批类型");
		aliasNameList.add("审批等级");
		aliasNameList.add("审批者社号");
		aliasNameList.add("审批者姓名");
		aliasNameList.add(" ");

		List affirmList=this.affirmSer.getAffirmItemList_specialExcel(request);//从 SY_AFFIRM_RELATION_FINAL取决裁者
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
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"aff_info");
	}
}
