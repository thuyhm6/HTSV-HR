package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.EssEmpInfoSer;
import com.ait.ess.service.TempEmpSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @fileName: EvsManageCtroller.java
 * @Create by: wzc(weizhengchen@ait.net.cn)
 * @version 5.5
 */
@Controller
@RequestMapping(value = "/ess/tempEmp")
public class TempEmpCtroller {
	Logger logger = Logger.getLogger(TempEmpCtroller.class);

	@Autowired
	private TempEmpSer tempEmpSer;

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private EssEmpInfoSer essEmpInfoSer;
	/**
	 * 是否验证
	 * 	1：发送
	 *  0：不发送
	 */
    @Value("${page.total.limit}")
	private int TOTAL_LIMIT;
    
	/**
	 * 小时工入离职list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpList")
	public ModelAndView viewRecruitBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewTempEmpList = this.tempEmpSer.viewTempEmpList(request,"viewTempEmpList");
		modelMap.put("viewTempEmpList", viewTempEmpList);
		modelMap.put("viewTempEmpListCnt", viewTempEmpList == null ? 0 : viewTempEmpList.size());
		
		return new ModelAndView("/ess/tempEmp/viewTempEmpList", modelMap);
	}
	
	/**
	 * 小时工入离职list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpConfirmList")
	public ModelAndView viewTempEmpConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception { 

		List viewTempEmpConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewTempEmpList");
		modelMap.put("viewTempEmpConfirmList", viewTempEmpConfirmList);
		modelMap.put("viewTempEmpConfirmListCnt", viewTempEmpConfirmList == null ? 0 : viewTempEmpConfirmList.size());
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);
			}
		}*/
		return new ModelAndView("/ess/tempEmp/viewTempEmpConfirmList", modelMap);
	}
	
	/**
	 * 小时工入职 添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddTempEmp")
	public ModelAndView viewAddTempEmp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/ess/tempEmp/viewAddTempEmp", modelMap);
	}

	
	/**
	 * 小时工入职 添加
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTempEmp")
	@ResponseBody
	public Map addTempEmp(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmp(request, "addTempEmp");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "ess3421");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 小时工离职 申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateTempEmp")
	@ResponseBody
	public Map updateTempEmp(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJson(request, "updateTempEmp");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "申请成功");
			map.put("navTabId", "ess3421");
		} else {
			map.put("statusCode", "300");
			map.put("message", "申请失败");
		}
		return map;
	}
	
	/**
	 * 小时工入离职 人事确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tempEmpConfirm")
	@ResponseBody
	public Map tempEmpConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJsonPro(request, "tempEmpConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "操作成功");
			map.put("formId", "viewTempEmpConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	/**
	 * 员工调店履历
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeShopHistoryList")
	public ModelAndView viewChangeShopHistoryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewChangeShopHistoryList = this.tempEmpSer.viewTempEmpList(request,"viewChangeShopHistoryList");
		modelMap.put("viewChangeShopHistoryList", viewChangeShopHistoryList);
		modelMap.put("viewChangeShopHistoryListCnt", viewChangeShopHistoryList == null ? 0 : viewChangeShopHistoryList.size());
		
		return new ModelAndView("/ess/tempEmp/viewChangeShopHistoryList", modelMap);
	}
	
	/**
	 * 员工调店
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeShopList")
	public ModelAndView viewChangeShopList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE DEPT_TYPE = '14015496' AND CPNY_ID = '" + admin.getCpnyId() + "' ";

		modelMap.put("dept" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(DEPT_NO_SQL)));  //部门
		int viewChangeShopListCnt = this.tempEmpSer.viewTempEmpCnt(request,"viewChangeShopListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewChangeShopListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewChangeShopList = this.tempEmpSer.viewTempEmpList(request,"viewChangeShopList");
			modelMap.put("viewChangeShopList", viewChangeShopList);
		}
		return new ModelAndView("/ess/tempEmp/viewChangeShopList", modelMap);
	}
	
	/**
	 * 员工调店确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeShopConfirmList")
	public ModelAndView viewChangeShopConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewChangeShopConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewChangeShopConfirmList");
		modelMap.put("viewChangeShopConfirmList", viewChangeShopConfirmList);
		
		return new ModelAndView("/ess/tempEmp/viewChangeShopConfirmList", modelMap);
	}

	/**
	 * 小时工入离职 人事确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeShopConfirm")
	@ResponseBody
	public Map changeShopConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJsonPro(request, "changeShopConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "操作成功");
			map.put("formId", "viewChangeShopConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveFixOtInfo")
	@ResponseBody 
	public Map saveFixOtInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.tempEmpSer.addTempEmpByJsonPro(request, "saveFixOtInfo");
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("formId", "viewSearchFixOtInfo");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return jo;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFixOtInfo")
	@ResponseBody
	public Map<String, Object> deleteFixOtInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = this.tempEmpSer.deleteFixOtInfo(request);
			if (result == 1) {
				map.put("message", "删除成功");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewSearchFixOtInfo");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "删除失败");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpForFixList")
	public ModelAndView viewEmpForFixList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("personList", this.tempEmpSer.getEmpListForFix(request)); 
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.tempEmpSer.getEmpListForFixCnt(request));
		String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		modelMap.put("searchForFlag", request.getParameter("searchForFlag"));
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("EmpOffice", EmpOffice);
		modelMap.put("refreshUrl", request.getParameter("refreshUrl"));
		modelMap.put("refreshMenuCode", request.getParameter("refreshMenuCode"));
		modelMap.put("refreshMenuName", request.getParameter("refreshMenuName"));
		return new ModelAndView("/pa/workManagement/viewEmpForFixList", modelMap);
	}
	
	/**
	 * 小时工离职 申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addChangeShopInfo")
	@ResponseBody
	public Map addChangeShopInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addChangeShopInfo(request, "addChangeShopInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "申请成功");
			map.put("formId", "viewChangeShopListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "申请失败");
		}
		return map;
	}
	
	/**
	 * 小时工离职 申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteChangeShopInfo")
	@ResponseBody
	public Map deleteChangeShopInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.deleteChangeShopInfo(request, "deleteChangeShopInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("formId", "viewChangeShopHistoryListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}
	
	/**
	 * 店铺员工排班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopShiftList")
	public ModelAndView viewShopShiftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);

		int viewShopShiftListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewShopShiftListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewShopShiftListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewShopShiftList = this.tempEmpSer.viewTempEmpList(request,"viewShopShiftList");
			modelMap.put("viewShopShiftList", viewShopShiftList);
			modelMap.put("viewShopShiftListCnt", viewShopShiftListCnt);
		}

		request.setAttribute("EMP_TYPE", "SHOP");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		return new ModelAndView("/ess/tempEmp/viewShopShiftList", modelMap);
	}
	
	
	/**
	 * 固定加班管理
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpFixOTList")
	public ModelAndView viewTempEmpFixOTList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		String type = StringUtil.checkNull(request.getParameter("type"));
		if(type != null && !"".equals(type)){
			List viewFixOtList = this.tempEmpSer.viewTempEmpList(request,"viewNullTempEmpFixOTList");
			modelMap.put("viewFixOtList", viewFixOtList);
		}else{
			this.tempEmpSer.deleteNullFixOtInfo(request);
			List viewFixOtList = this.tempEmpSer.viewTempEmpList(request,"viewTempEmpFixOTList");
			modelMap.put("viewFixOtList", viewFixOtList);
		}

		return new ModelAndView("/ess/tempEmp/viewTempEmpFixOTList", modelMap);
	}
	
	@RequestMapping(value = "/addFixOtInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = this.tempEmpSer.addFixOtInfo(request);
			if (result==1) {
				map.put("statusCode", "200");
				map.put("formId", "viewSearchFixOtInfo");
			}else{
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddTempEmpFixOTList")
	public ModelAndView viewAddTempEmpFixOTList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		/*List viewFixOtList = this.tempEmpSer.viewTempEmpList(request,"viewTempEmpFixOTList");
		modelMap.put("viewFixOtList", viewFixOtList);*/

		return new ModelAndView("/ess/tempEmp/viewAddTempEmpFixOTList", modelMap);
	}
	
	/**
	 * 工厂员工排班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFactoryShiftList")
	public ModelAndView viewFactoryShiftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);

		int viewFactoryShiftListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewFactoryShiftListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewFactoryShiftListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewFactoryShiftList = this.tempEmpSer.viewTempEmpList(request,"viewFactoryShiftList");
			modelMap.put("viewFactoryShiftList", viewFactoryShiftList);
		}

		request.setAttribute("EMP_TYPE", "FACTORY");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		
		return new ModelAndView("/ess/tempEmp/viewFactoryShiftList", modelMap);
	}

	
	/**
	 * 物流排班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception viewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWuLiuShiftList")
	public ModelAndView viewWuLiuShiftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);
		
		List viewWuLiuShiftList = this.tempEmpSer.viewTempEmpList(request,"viewWuLiuShiftList");
		modelMap.put("viewWuLiuShiftList", viewWuLiuShiftList);

		modelMap.put("shiftList", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftList")));
		modelMap.put("shiftNoList", this.tempEmpSer.viewTempEmpList(request,"getShiftList"));
		
		return new ModelAndView("/ess/tempEmp/viewShopShiftList", modelMap);
	}

	/**
	 * 门店安排(成本分开统计)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception viewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeShopBatchList")
	public ModelAndView viewChangeShopBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);
		
		List viewChangeShopBatchList = this.tempEmpSer.viewTempEmpList(request,"viewChangeShopBatchList");
		modelMap.put("viewChangeShopBatchList", viewChangeShopBatchList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,GET_DEPT_NAME(DEPTNO,'" + admin.getLanguage() + "') CODENAME FROM HR_DEPARTMENT WHERE DEPT_TYPE = '14015496' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL);
		
		modelMap.put("dept", JsonUtil.writeInternal(dept));
		modelMap.put("deptList", dept);
		
		return new ModelAndView("/ess/tempEmp/viewChangeShopBatchList", modelMap);
	}
	
	/**
	 * 店铺员工排班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addShopShift")
	@ResponseBody
	public Map addShopShift(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addShopShift");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			
			String typeFlag= StringUtil.checkNull(request.getParameter("typeFlag"));
			map.put("formId", "shop".equals(typeFlag) ? "viewShopShiftListForm":"viewFactoryShiftListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportShopShiftTempList")
	public ModelAndView viewImportDeptTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List shopShiftTempList = this.tempEmpSer.viewTempEmpList(request,"getShiftTempList");
		int shopShiftTempCnt = this.tempEmpSer.viewTempEmpCnt(request , "getTempCnt");
		int errorCnt = this.tempEmpSer.viewTempEmpCnt(request , "getTempErrorCnt");
		
		modelMap.put("shopShiftTempList", shopShiftTempList);
		modelMap.put("shopShiftTempCnt", shopShiftTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", shopShiftTempCnt);
		modelMap.put("navTabId", request.getParameter("navTabId"));
		return new ModelAndView("/ess/tempEmp/viewImportShopShiftTempList", modelMap);
	}

	/**
	 * 批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelShopShiftData")
	@ResponseBody
	public Map submitImportExcelShopShiftData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.tempEmpSer.submitImportExcelData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "ess3422");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 店铺员工日考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopDetailConfirmList")
	public ModelAndView viewShopDetailConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewShopDetailConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewShopDetailConfirmList");
		modelMap.put("viewShopDetailConfirmList", viewShopDetailConfirmList);
		
		if("".equals(StringUtil.checkNull(request.getParameter("seach_START_DATE"))) 
				&& "".equals(StringUtil.checkNull(request.getParameter("seach_END_DATE")))){
			
			Date date=new Date();//取时间
		    Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动 
		    date=calendar.getTime();
		     
			modelMap.put("START_DATE", new SimpleDateFormat("yyyy/MM/dd").format(date));
			modelMap.put("END_DATE", new SimpleDateFormat("yyyy/MM/dd").format(date));
		}

		request.setAttribute("EMP_TYPE", "SHOP");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		return new ModelAndView("/ess/tempEmp/viewShopDetailConfirmList", modelMap);
	}
	
	/**
	 * 店铺员工  日考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addShopShiftConfirm")
	@ResponseBody
	public Map addShopShiftConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addShopShiftConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewShopDetailConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 店铺员工  日考勤最终确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addShopShiftFinalConfirm")
	@ResponseBody
	public Map addShopShiftFinalConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addShopShiftFinalConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}

	/**
	 * 物流员工排班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addWuLiuShift")
	@ResponseBody
	public Map addWuLiuShift(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addWuLiuShift");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewWuLiuShiftListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}

	/**
	 * 门店安排(成本分开统计)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addChangeShopBatchShift")
	@ResponseBody
	public Map addChangeShopBatchShift(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJson(request, "addChangeShopBatchShift");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewChangeShopBatchListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}

	/**
	 * 店铺员工月考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopSummaryConfirmList")
	public ModelAndView viewShopSummaryConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewShopSummaryConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewShopSummaryConfirmList");
		modelMap.put("viewShopSummaryConfirmList", viewShopSummaryConfirmList);
		
		if("".equals(StringUtil.checkNull(request.getParameter("seach_START_DATE"))) 
				&& "".equals(StringUtil.checkNull(request.getParameter("seach_END_DATE")))||"".equals(StringUtil.checkNull(request.getParameter("AR_MONTH")))){
			
			modelMap.put("AR_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}
		
		return new ModelAndView("/ess/tempEmp/viewShopSummaryConfirmList", modelMap);
	}
	
	/**
	 * 店铺员工日考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopDetailPersonId")
	public ModelAndView viewShopDetailPersonId(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewShopDetailPersonId = this.tempEmpSer.viewTempEmpList(request,"viewShopDetailPersonId");
		modelMap.put("viewShopDetailPersonId", viewShopDetailPersonId);

		request.setAttribute("EMP_TYPE", "SHOP");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		return new ModelAndView("/ess/tempEmp/viewShopDetailPersonId", modelMap);
	}

	/**
	 * 店铺员工排班SH
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopShiftSHList")
	public ModelAndView viewShopShiftSHList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);

		int viewShopShiftSHListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewShopShiftSHListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewShopShiftSHListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewShopShiftSHList = this.tempEmpSer.viewTempEmpList(request,"viewShopShiftSHList");
			modelMap.put("viewShopShiftSHList", viewShopShiftSHList);
		}

		request.setAttribute("EMP_TYPE", "SHOP");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		return new ModelAndView("/ess/tempEmp/viewShopShiftSHList", modelMap);
	}
	
	/**
	 * 工厂员工排班SH
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFactoryShiftSHList")
	public ModelAndView viewFactoryShiftSHList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);

		int viewFactoryShiftSHListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewFactoryShiftSHListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewFactoryShiftSHListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewFactoryShiftSHList = this.tempEmpSer.viewTempEmpList(request,"viewFactoryShiftSHList");
			modelMap.put("viewFactoryShiftSHList", viewFactoryShiftSHList);
		}

		request.setAttribute("EMP_TYPE", "FACTORY");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		
		return new ModelAndView("/ess/tempEmp/viewFactoryShiftSHList", modelMap);
	}
	
	/**
	 * 工厂员工排班SH
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHouQinShiftSHList")
	public ModelAndView viewHouQinShiftSHList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);

		List viewHouQinShiftSHList = this.tempEmpSer.viewTempEmpList(request,"viewHouQinShiftSHList");
		modelMap.put("viewHouQinShiftSHList", viewHouQinShiftSHList);

		request.setAttribute("EMP_TYPE", "HOUQIN");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		
		return new ModelAndView("/ess/tempEmp/viewHouQinShiftSHList", modelMap);
	}

	
	/**
	 * 物流排班SH
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception viewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWuLiuShiftSHList")
	public ModelAndView viewWuLiuShiftSHList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);
		
		List viewWuLiuShiftSHList = this.tempEmpSer.viewTempEmpList(request,"viewWuLiuShiftSHList");
		modelMap.put("viewWuLiuShiftSHList", viewWuLiuShiftSHList);

		request.setAttribute("EMP_TYPE", "WULIU");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		
		return new ModelAndView("/ess/tempEmp/viewShopShiftSHList", modelMap);
	}

	/**
	 * 上海法人 item弹出框
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception viewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSHItemList")
	public ModelAndView viewSHItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		request.setAttribute("EMP_TYPE", "FACTORY");
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		
		return new ModelAndView("/ess/tempEmp/viewSHItemList", modelMap);
	}
	
	/**
	 * 店铺员工排班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addShopShiftSH")
	@ResponseBody
	public Map addShopShiftSH(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addShopShiftSH");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			
			String typeFlag= StringUtil.checkNull(request.getParameter("typeFlag"));
			String formId = "viewWuLiuShiftSHListForm";
			if("shop".equals(typeFlag)){
				formId = "viewShopShiftSHListForm";
			}else if("factory".equals(typeFlag)){
				formId = "viewFactoryShiftSHListForm";
			}
			map.put("formId", formId);
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 正式工离职list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpLeftList")
	public ModelAndView viewEmpLeftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEmpLeftList = this.tempEmpSer.viewTempEmpList(request,"viewEmpLeftList");
		modelMap.put("viewEmpLeftList", viewEmpLeftList);
		
		return new ModelAndView("/ess/tempEmp/viewEmpLeftList", modelMap);
	}
	
	/**
	 * 正式工离职确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpLeftConfirmList")
	public ModelAndView viewEmpLeftConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEmpLeftConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewEmpLeftConfirmList");
		modelMap.put("viewEmpLeftConfirmList", viewEmpLeftConfirmList);
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);
			}
		}*/
		return new ModelAndView("/ess/tempEmp/viewEmpLeftConfirmList", modelMap);
	}
	
	/**
	 * 临时工工资导入
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpSalaryList")
	public ModelAndView viewTempEmpSalaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewTempEmpSalaryList = this.tempEmpSer.viewTempEmpList(request,"viewTempEmpSalaryList");
		modelMap.put("viewTempEmpSalaryList", viewTempEmpSalaryList);
		if("".equals(StringUtil.checkNull(request.getParameter("seach_PA_MONTH")))){
			modelMap.put("PA_MONTH", DateUtil.formatDate(new Date(), "yyyyMM"));
		}
		return new ModelAndView("/ess/tempEmp/viewTempEmpSalaryList", modelMap);
	}
	
	/**
	 * 正式工离职确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddEmpLeftList")
	public ModelAndView viewAddEmpLeftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewAddEmpLeftList = this.tempEmpSer.viewTempEmpList(request,"viewAddEmpLeftList");
		modelMap.put("TRANS_RESOURCE_STR" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("210464", request)));
		modelMap.put("viewAddEmpLeftList", viewAddEmpLeftList);
		
		return new ModelAndView("/ess/tempEmp/viewAddEmpLeftList", modelMap);
	}

	
	/**
	 * 小时工入职 添加
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTempEmpLeft")
	@ResponseBody
	public Map addTempEmpLeft(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJson(request, "addTempEmpLeft");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "ess3446");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 小时工入离职 人事确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/empLeftConfirm")
	@ResponseBody
	public Map empLeftConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJsonPro(request, "empLeftConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "操作成功");
			String page = StringUtil.checkNull(request.getParameter("page"));
			if("essApply".equals(page)){
				map.put("formId", "viewEmpLeftListForm");
			}else{
				map.put("formId", "viewEmpLeftConfirmListForm");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	/**
	 * 员工调店安排-加盟店
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception viewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeShopFranchiseList")
	public ModelAndView viewChangeShopFranchiseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewWeekList = this.tempEmpSer.viewTempEmpList(request,"viewWeekList");
		modelMap.put("viewWeekList", viewWeekList);
		
		List viewChangeShopFranchiseList = this.tempEmpSer.viewTempEmpList(request,"viewChangeShopFranchiseList");
		modelMap.put("viewChangeShopFranchiseList", viewChangeShopFranchiseList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE DEPT_TYPE = '14015496' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL);
		
		modelMap.put("dept", JsonUtil.writeInternal(dept));
		modelMap.put("deptList", dept);
		
		return new ModelAndView("/ess/tempEmp/viewChangeShopFranchiseList", modelMap);
	}
	
	/**
	 * 店铺员工日考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopDetailConfirmSHList")
	public ModelAndView viewShopDetailConfirmSHList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {


		int viewShopDetailConfirmSHListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewShopDetailConfirmSHListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewShopDetailConfirmSHListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewShopDetailConfirmSHList = this.tempEmpSer.viewTempEmpList(request,"viewShopDetailConfirmSHList");
			modelMap.put("viewShopDetailConfirmSHList", viewShopDetailConfirmSHList);
		}
		
		if("".equals(StringUtil.checkNull(request.getParameter("seach_START_DATE"))) 
				&& "".equals(StringUtil.checkNull(request.getParameter("seach_END_DATE")))){
			
			Date date=new Date();//取时间
		    Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动 
		    date=calendar.getTime();
		     
			modelMap.put("START_DATE", new SimpleDateFormat("yyyy/MM/dd").format(date));
			modelMap.put("END_DATE", new SimpleDateFormat("yyyy/MM/dd").format(date));
		}

		request.setAttribute("EMP_TYPE", "SHOP");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		modelMap.put("shiftNoItem", this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"));
		
		return new ModelAndView("/ess/tempEmp/viewShopDetailConfirmSHList", modelMap);
	}
	
	/**
	 * 店铺员工  日考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addShopShiftConfirmSH")
	@ResponseBody
	public Map addShopShiftConfirmSH(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addShopShiftConfirmSH");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("formId", "viewShopDetailConfirmSHListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 店铺员工日考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShopDetailPersonIdSH")
	public ModelAndView viewShopDetailPersonIdSH(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewShopDetailPersonIdSH = this.tempEmpSer.viewTempEmpList(request,"viewShopDetailPersonIdSH");
		modelMap.put("viewShopDetailPersonIdSH", viewShopDetailPersonIdSH);

		request.setAttribute("EMP_TYPE", "SHOP");
		modelMap.put("shiftItem", JsonUtil.writeInternal(this.tempEmpSer.viewTempEmpList(request,"getShiftItemList")));
		return new ModelAndView("/ess/tempEmp/viewShopDetailPersonIdSH", modelMap);
	}
	
	/**
	 * 店铺员工  日考勤最终确认SH
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addShopShiftFinalConfirmSH")
	@ResponseBody
	public Map addShopShiftFinalConfirmSH(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addShopShiftByJson(request, "addShopShiftFinalConfirmSH");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthDetailSave")
	@ResponseBody
	public Map monthDetailSave(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addMonthDetaiByJson(request, "monthDetailSave");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	
	/**
	 * 月考勤确认申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMonthDetailApplyList")
	public ModelAndView viewMonthDetailApplyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String arMonth = StringUtil.checkNull(request.getParameter("seach_AR_MONTH"));
		if("".equals(arMonth)){
			request.setAttribute("AR_MONTH", com.ait.web.util.DateUtil.getLastMonthStr());
		}
		List viewMonthList = this.tempEmpSer.viewTempEmpList(request,"viewMonthList");
		modelMap.put("viewMonthList", viewMonthList);

		this.tempEmpSer.addEnsInfoProcedure(request, "addMonthConfirmInfo");
		int viewMonthDetailApplyListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewMonthDetailApplyListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewMonthDetailApplyListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewMonthDetailApplyList = this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailApplyList");
			modelMap.put("viewMonthDetailApplyList", viewMonthDetailApplyList);
		}
		return new ModelAndView("/ess/tempEmp/viewMonthDetailApplyList", modelMap);
	}
	
	/**
	 * 月考勤申请确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMonthDetailConfirmList")
	public ModelAndView viewMonthDetailConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String arMonth = StringUtil.checkNull(request.getParameter("seach_AR_MONTH"));
		if("".equals(arMonth)){
			request.setAttribute("AR_MONTH", com.ait.web.util.DateUtil.getLastMonthStr());
		}
		List viewMonthList = this.tempEmpSer.viewTempEmpList(request,"viewMonthList");
		modelMap.put("viewMonthList", viewMonthList);

		int viewMonthDetailConfirmListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewMonthDetailConfirmListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		if(viewMonthDetailConfirmListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewMonthDetailConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailConfirmList");
			modelMap.put("viewMonthDetailConfirmList", viewMonthDetailConfirmList);
		}
		return new ModelAndView("/ess/tempEmp/viewMonthDetailConfirmList", modelMap);
	}
	
	/**
	 * 月考勤确认申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthDetailApply")
	@ResponseBody
	public Map monthDetailApply(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String	result = this.tempEmpSer.addEnsInfoProcedure(request, "monthDetailApply");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", "申请成功");
			map.put("formId", "viewMonthDetailApplyListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "申请失败");
		}
		return map;
	}
	
	/**
	 * 月考勤确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthDetailConfirm")
	@ResponseBody
	public Map monthDetailConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String	result = this.tempEmpSer.addEnsInfoProcedure(request, "monthDetailConfirm");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", "操作成功");
			map.put("formId", "viewMonthDetailConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	/**
	 * 员工调店确认(岗位变化)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeDeptConfirmList")
	public ModelAndView viewChangeDeptConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewChangeDeptConfirmList = this.tempEmpSer.viewTempEmpList(request,"viewChangeDeptConfirmList");
		modelMap.put("viewChangeDeptConfirmList", viewChangeDeptConfirmList);
		
		return new ModelAndView("/ess/tempEmp/viewChangeDeptConfirmList", modelMap);
	}

	/**
	 * 小时工入离职 人事确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeDeptConfirm")
	@ResponseBody
	public Map changeDeptConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.tempEmpSer.addTempEmpByJsonPro(request, "changeShopConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "操作成功");
			map.put("formId", "viewChangeDeptConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	/**
	 * 月考勤汇总确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ExceptionviewTempEmpConfirmList
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMonthDetailSummaryList")
	public ModelAndView viewMonthDetailSummaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String arMonth = StringUtil.checkNull(request.getParameter("seach_AR_MONTH"));
		if("".equals(arMonth)){
			request.setAttribute("AR_MONTH", com.ait.web.util.DateUtil.getLastMonthStr());
		}
		List viewMonthList = this.tempEmpSer.viewTempEmpList(request,"viewMonthList");
		modelMap.put("viewMonthList", viewMonthList);

		this.tempEmpSer.addEnsInfoProcedure(request, "addMonthConfirmInfo");
		int viewMonthDetailApplyListCnt = this.tempEmpSer.viewTempEmpCnt(request, "viewMonthDetailApplyListCnt");
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入超过200条数据 不查询
		if(viewMonthDetailApplyListCnt <= TOTAL_LIMIT || !"1".equals(firstFlag)){
			List viewMonthDetailApplyList = null;
			if("SPC_NJ".equals(admin.getCpnyId())){
				viewMonthDetailApplyList = this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailApplyForExcelList");
			}else{
				viewMonthDetailApplyList = this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailApplyList");
			}
			modelMap.put("viewMonthDetailSummaryList", viewMonthDetailApplyList);
		}
		return new ModelAndView("/ess/tempEmp/viewMonthDetailSummaryList", modelMap);
	}
	
	/**
	 * 月考勤明细查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMonthDetailList")
	public ModelAndView viewMonthDetailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String arMonth = StringUtil.checkNull(request.getParameter("seach_AR_MONTH"));
		if("".equals(arMonth)){
			request.setAttribute("AR_MONTH", com.ait.web.util.DateUtil.getCurrentMonthStrFormatMY());
		}
		List viewMonthList = this.tempEmpSer.viewTempEmpList(request,"viewFixedDateList");
		modelMap.put("viewMonthList", viewMonthList);

		//this.tempEmpSer.addEnsInfoProcedure(request, "addMonthConfirmInfo");
		//int viewMonthDetailListCnt = this.tempEmpSer.getMonthDetailListCnt(request);
		String firstFlag = StringUtil.checkNull(request.getParameter("firstFlag"));
		//第一次进入或者超过200条数据 不查询
		//viewMonthDetailListCnt <= TOTAL_LIMIT || 
		if(!"Y".equals(firstFlag)){
			List viewMonthDetailList = this.tempEmpSer.viewTempEmpList(request,"getMonthDetailRealTimeList");
			modelMap.put("viewMonthDetailList", viewMonthDetailList);
		}
		return new ModelAndView("/ess/tempEmp/viewMonthDetailList", modelMap);
	}
	
}