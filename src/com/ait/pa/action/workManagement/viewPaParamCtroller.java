package com.ait.pa.action.workManagement;

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

import com.ait.disc.action.RetrieveMasterListCtroller;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.EssApplyInfoSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.pa.service.workManagement.viewPaParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.writeTxtUtil;

@Controller
@RequestMapping(value = "/pa/workManagement")
public class viewPaParamCtroller {
	Logger logger = Logger.getLogger(viewPaParamCtroller.class);
	@Autowired
	private EssApplyInfoSer essApplyInfoSer;
	@Autowired
	private viewPaParamSer viewPaParamSer;
	@Autowired
	private EssEmpInfoSer empInfoSer;
	@Autowired
	private PaPayScheduleSer paPayScheduleSer;
	@Autowired
	private PaInputItemParamSer paInputItemParamSer;
	@Autowired
	private EmpInfoSer  empInfoSer2;
	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private RetrieveMasterListCtroller RetrieveMasterListCtroller;

	@Autowired
	private writeTxtUtil writeTxtUtil;

	/**
	 * 工资数据批量导入
	 * 
	 */
	@RequestMapping(value = "/viewPaParamDownloud")
	public ModelAndView viewPaParamDownloud(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap
				.put("compList", this.companySer.getCompanyItemAllList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		request.setAttribute("FSE_FLAG", "N");
		List proList = this.viewPaParamSer.getPaInputItemParamList(request);
		modelMap.put("proList", proList);
		modelMap.put("itemType", request.getParameter("itemType"));

		return new ModelAndView("/pa/workManagement/viewPaParamDownloud",
				modelMap);

	}

	/**
	 *导入后对比操作
	 * 
	 */
	@RequestMapping(value = "/viewPaParamUploudList")
	public ModelAndView viewPaParamUploudList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap
				.put("compList", this.companySer.getCompanyItemAllList(request));

		// 标识 是第一到此页面 第二次进入此页面不需要重复进行验证
		String firstFlag = request.getParameter("firstFlage");
		// 数据验证'
		int ret = 1;
		if (firstFlag != null && firstFlag.equals("yes")) {
			ret = viewPaParamSer.checkTempListById(request);
		}

		if (ret == 1) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil
					.getRequestParamData(request, "seach_");
			modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
					.getCpnyId() : paramMap.get("CPNY_ID").toString());

			request.setAttribute("FSE_FLAG", "N");
			List proList = this.viewPaParamSer.getPaInputItemParamList(request);

			List afterList = this.viewPaParamSer.getImportCompareList(request);// 导入后的数据
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.viewPaParamSer
					.getImportCompareListCnt(request));// 分页参数

			modelMap.put("proList", proList);
			modelMap.put("afterList", afterList);

			modelMap.put("afterErrorNum2",
					(HashMap<String, Object>) this.viewPaParamSer
							.getImportCompareNum(request));

			modelMap.put("itemType", request.getParameter("itemType"));
		} else {
			modelMap.put("error", "1");// 若数据验证错误 不能确认
		}

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218096"));
		return new ModelAndView("/pa/workManagement/viewPaParamUploudList",
				modelMap);

	}

	/**
	 *确认添加
	 * 
	 */
	@RequestMapping(value = "/setPaParam")
	@ResponseBody
	public Map<String, Object> setPaParam(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String afterErrorNum = request.getParameter("afterErrorNum");
		int result = 0;
		if (afterErrorNum != null && !afterErrorNum.equals("")) {
			int Num = Integer.parseInt(afterErrorNum);

			if (Num > 0) {
				result = 0;
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.Importing_data_contains_error_data.b", request));//导入数据含有错误数据，更改后再次上传!
			} else {
				result = viewPaParamSer.setPaParam(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("ar.alert.message.excelimport.importsuccess", request));//导入成功!
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("ar.alert.message.excelimport.importfail", request));//导入失败!
				}
			}
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.Importing_data_error_please_again.b", request));//导入失败，请再次上传!
		}

		map.put("result", result);
		return map;
	}

	/**
	 * 月别工资现况
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthPersonCountInfoList")
	public ModelAndView monthPersonCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");

		if (request.getParameter("PAY_DATE") != null) {

			List monthPersonCountInfoList = this.viewPaParamSer
					.monthPersonCountInfoList(request);

			List monthPersonIncreaseList = this.viewPaParamSer
					.monthPersonIncreaseList(request);

			List monthPersonDecreaseList = this.viewPaParamSer
					.monthPersonDecreaseList(request);

			modelMap.put("monthPersonCountInfoList", monthPersonCountInfoList);

			modelMap.put("monthPersonIncreaseList", monthPersonIncreaseList);
			modelMap.put("monthPersonDecreaseList", monthPersonDecreaseList);
			modelMap.put("currentIndex", 0);
		}
		// 工资支付计划

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));

		modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));
		modelMap.put("PAY_DATE_PRO", request.getParameter("PAY_DATE_PRO"));
		modelMap.put("SALARY_DISTIN_NO", request
				.getParameter("SALARY_DISTIN_NO"));

		return new ModelAndView("/pa/workManagement/monthPersonCountInfoList",
				modelMap);
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthPersonCountInfoSonList")
	public ModelAndView monthPersonCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		List monthPersonCountInfoSonList = this.viewPaParamSer
				.monthPersonCountInfoSonList(request);

		modelMap
				.put("monthPersonCountInfoSonList", monthPersonCountInfoSonList);

		modelMap.put("PERSON_ID_ID", request.getParameter("PERSON_ID_ID"));

		// 增加人员 减少人员标识
		modelMap.put("strFlag", request.getParameter("strFlag"));

		if (request.getParameter("currentIndex") != null
				&& !request.getParameter("currentIndex").equals("")) {
			modelMap.put("currentIndex", request.getParameter("currentIndex"));
		} else {
			modelMap.put("currentIndex", 0);

		}
		// 月环比人员
		return new ModelAndView(
				"/pa/workManagement/monthPersonCountInfoSonList", modelMap);
	}

	/**
	 * monthPersonCountInfoSonList 页面导出
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthPersonCISListExport")
	public void monthPersonCISListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List monthPersonCountInfoSonList = this.essApplyInfoSer.monthPersonCountInfoSonList(request);

		modelMap.put("monthPersonCountInfoSonList", monthPersonCountInfoSonList);
		modelMap.put("XLS_NAME", "p_file");
		modelMap.put("XLS_IN", "monthPersonCISListExport");

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}
	/**
	 * monthPersonCountInfoSonList 页面导出
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthPersonCISListExport1")
	public void monthPersonCISListExport1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List monthPersonCountInfoSonList = this.essApplyInfoSer.monthPersonCountInfoSonList1(request);

		modelMap.put("monthPersonCountInfoSonList", monthPersonCountInfoSonList);
		modelMap.put("XLS_NAME", "p_file");
		modelMap.put("XLS_IN", "monthPersonCISListExport");

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	// / /pa/workManagement/monthPersonCountInfoList

	@RequestMapping(value = "/savePaResult")
	@ResponseBody
	public Map<String, Object> savePaResult(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String returnString = this.viewPaParamSer.savePaResult(request);

		Map<String, Object> map = new HashMap<String, Object>();
		if (returnString == "") {
			map.put("statusCode", "200");
			map.put("message", returnString);
		} else {
			map.put("statusCode", "300");
			map.put("message", returnString);
		}
		return map;
	}

	/**
	 * 发令核查
	 * 
	 */
	@RequestMapping(value = "/viewVerificationList")
	public ModelAndView viewVerificationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);

		String PAY_SCHEDULE_NO = request.getParameter("PAY_SCHEDULE_NO");

		if (PAY_SCHEDULE_NO != null && !PAY_SCHEDULE_NO.equals("")) {
			List viewVerificationList = this.viewPaParamSer
					.viewVerificationList(request);

			modelMap.put("viewVerificationList", viewVerificationList);
		}
		modelMap.put("PAY_SCHEDULE_NO", PAY_SCHEDULE_NO);
		return new ModelAndView("/pa/workManagement/viewVerificationList",
				modelMap);

	}

	/**
	 * 支付合计（个人）
	 * 
	 */
	@RequestMapping(value = "/viewPaResultList")
	public ModelAndView viewPaResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllWithPaConfirmList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		List viewPaResultList = this.viewPaParamSer
				.viewPaResultList(request);
		modelMap.put("viewPaResultList", viewPaResultList);
		List viewPaResultListSum = this.viewPaParamSer.viewPaResultListSum(request);
		modelMap.put("viewPaResultListSum", viewPaResultListSum);
		return new ModelAndView("/pa/workManagement/viewPaResultList", modelMap);

	}

	/**
	 * 支付合计（个人） 导出
	 * 
	 */
	@RequestMapping(value = "/viewPaResultListExport")
	public void viewPaResultListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewPaResultList = this.viewPaParamSer.viewPaResultList(request);
		String SALARY_DISTIN = request.getParameter("seach_SALARY_DISTIN");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (admin.getCpnyId().equals("TSTO")) {
			if (SALARY_DISTIN.equals("春节奖")) {
				modelMap.put("viewPaResultList", viewPaResultList);
				modelMap.put("XLS_NAME", "支付合计-春节奖(个人)");
				modelMap.put("XLS_IN", "viewPaResultListYearRewardExport");

			} else {
				modelMap.put("viewPaResultList", viewPaResultList);
				modelMap.put("XLS_NAME", "支付合计(个人)");
				modelMap.put("XLS_IN", "viewPaResultListExport");

			}

		} else if (admin.getCpnyId().equals("SST")) {
			if (SALARY_DISTIN.equals("春节奖")) {
				modelMap.put("viewPaResultList", viewPaResultList);
				modelMap.put("XLS_NAME", "支付合计-春节奖(个人)");
				modelMap.put("XLS_IN", "viewPaResultListYearRewardExportSST");
			} else {

				modelMap.put("viewPaResultList", viewPaResultList);
				modelMap.put("XLS_NAME", "支付合计(个人)");
				modelMap.put("XLS_IN", "viewPaResultListExportSST");
			}
		}
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	/**
	 * 工资细节明细
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailPersonCountInfo")
	public ModelAndView detailPersonCountInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
			modelMap
					.put("personInfo", viewPaParamSer.getPaDetailEmpInfoByPersonId(request));

			// 工资细节明细
			if (request.getParameter("PAY_SCHEDULE_NO") != null
					&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
				List detailPersonCountInfoList = this.viewPaParamSer
						.getPaDetailInfoList(request);

				modelMap.put("detailPersonCountInfoList",
						detailPersonCountInfoList);

			}
		}

		modelMap.put("empInfoShow", request.getParameter("empInfoShow"));
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPT_NO", request.getParameter("pa1016_seachDept"));
		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));

		return new ModelAndView("/pa/workManagement/detailPersonCountInfo",
				modelMap);
	}

	/**
	 * 工资条
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payStub")
	public ModelAndView payStub(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		List payInfolist = new ArrayList();
		if(request.getParameter("PAY_SCHEDULE_NO") != null
				&& !request.getParameter("PAY_SCHEDULE_NO").equals("")){
			payInfolist = this.viewPaParamSer.getPayrollPersonList(request);
		}
		
		modelMap.put("payInfolist", payInfolist);
//		String currentIndex = request.getParameter("currentIndex");
		//String personid=request.getParameter("PERSON_ID");
		/*if (personid != null && !personid.equals("")) {
			modelMap
					.put("personInfo", viewPaParamSer.getPersonalInfoForEmpSalaryInfo(request));
			modelMap.put("paEmpAccount", viewPaParamSer.paEmpAccount(request));

			// 工资细节明细
			if (request.getParameter("PAY_SCHEDULE_NO") != null
					&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
				List detailPersonCountInfoList = this.viewPaParamSer
						.getEmpSalaryInfoList(request);

				modelMap.put("payStubList", detailPersonCountInfoList);
//				modelMap.put("paSpecialItem", viewPaParamSer
//						.paOpenFlag(request));

			}
		}*/

		modelMap.put("empInfoShow", request.getParameter("empInfoShow"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("DEPTNO_Multi", request.getParameter("seach_DEPTNO_Multi"));
		modelMap.put("DEPT_NAME", request.getParameter("seach_DEPT_NAME"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("POST_FAMILY", request.getParameter("seach_WORKER_FLAG"));
		modelMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE"));
		modelMap.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		return new ModelAndView("/pa/workManagement/payStub", modelMap);
	}

	/**
	 * 个人别核对 左边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailPersonCountInfoLeft")
	public ModelAndView detailPersonCountInfoLeft(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		String currentIndex = request.getParameter("currentIndex");

		// 工资月内发工资的人员信息list
		if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
			modelMap
					.put("personInfo", empInfoSer.getPersonalInfoByPid(request));
		}

		if (request.getParameter("PAY_SCHEDULE_NO") != null
				&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
			List detailPersonCountInfoLeft = this.viewPaParamSer
					.detailPersonCountInfoLeft(request);

			modelMap
					.put("detailPersonCountInfoLeft", detailPersonCountInfoLeft);

		}

		modelMap.put("empInfoShow", request.getParameter("empInfoShow"));

		modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO"));
		modelMap.put("POST_FAMILY_Multi", request.getParameter("seach_POST_FAMILY_Multi"));
		modelMap.put("POST_FAMILY_NAME", request.getParameter("seach_POST_FAMILY_NAME"));
		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/workManagement/detailPersonCountInfoLeft",
				modelMap);
	}

	/**
	 * 个人别核对 右边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailPersonCountInfoRight")
	public ModelAndView detailPersonCountInfoRight(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
//		String currentIndex = request.getParameter("currentIndex");
		if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
//			modelMap
//					.put("personInfo", empInfoSer.getPersonalInfoByPid(request));

			// 个人别核对
			if (request.getParameter("PAY_SCHEDULE_NO") != null
					&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
				List detailPersonCountInfoList = this.viewPaParamSer
						.getPaDetailInfoList(request);

				modelMap.put("detailPersonCountInfoList",
						detailPersonCountInfoList);

			}
		}
//		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
//		modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));

		return new ModelAndView(
				"/pa/workManagement/detailPersonCountInfoRight", modelMap);
	}

	/**
	 * 日别工资明细
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dayPersonCountInfoList")
	public ModelAndView dayPersonCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);

		modelMap.put("IS_CAL_BY_DAY", request.getParameter("IS_CAL_BY_DAY"));
		String PAY_SCHEDULE_NO = request.getParameter("PAY_SCHEDULE_NO");
		modelMap.put("PAY_SCHEDULE_NO", PAY_SCHEDULE_NO);
		if (PAY_SCHEDULE_NO != null && !PAY_SCHEDULE_NO.equals("")) {
			List dayPersonCountInfoList = this.viewPaParamSer
					.dayPersonCountInfoList(request);
			modelMap.put("dayPersonCountInfoList", dayPersonCountInfoList);
		}

		return new ModelAndView("/pa/workManagement/dayPersonCountInfoList",
				modelMap);
	}

	/**
	 *调用存储一个存储生成当月的和前月的工资统计
	 * 
	 */
	@RequestMapping(value = "/callProForPayMonthDif")
	@ResponseBody
	public Map<String, Object> callProForPayMonthDif(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		int result = 0;

		result = viewPaParamSer.callProForPayMonthDif(request);
		if (result == 1) {
			map.put("statusCode", "200");

		} else {
			map.put("statusCode", "300");

		}

		map.put("result", result);
		return map;
	}

	/**
	 * 项目别工资明细
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailItemCountInfo")
	public ModelAndView detailItemCountInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		String currentIndex = request.getParameter("currentIndex");
		// 获取项目列表

		List itemValueInfo = this.viewPaParamSer.itemValueInfo(request);

		modelMap.put("itemValueInfo", itemValueInfo);

		// 工资细节明细
		if (request.getParameter("ITEM_ID") != null
				&& !request.getParameter("ITEM_ID").equals("")) {
			if (request.getParameter("PAY_SCHEDULE_NO") != null
					&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
				List detailPersonCountInfoList = this.viewPaParamSer
						.detailItemCountInfoList(request);

				modelMap.put("detailItemCountInfoList",
						detailPersonCountInfoList);

			}
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("ITEM_ID", request.getParameter("ITEM_ID"));
		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));

		return new ModelAndView("/pa/workManagement/detailItemCountInfo",
				modelMap);

	}

	/**
	 *查询工被选中的项目
	 * 
	 */
	@RequestMapping(value = "/checkViewPaResult")
	@ResponseBody
	public Map<String, Object> checkViewPaResult(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("checkViewPaResult", viewPaParamSer.checkViewPaResult(request));

		return map;
	}

	/**
	 * 支付合计（部门）
	 * 
	 */
	@RequestMapping(value = "/viewDeptPaResultList")
	public ModelAndView viewDeptPaResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		List viewPaResultList = this.viewPaParamSer
				.viewDeptPaResultList(request);
		modelMap.put("viewDeptPaResultList", viewPaResultList);
		List viewDeptPaResultListSum = this.viewPaParamSer.viewDeptPaResultListSum(request);
        modelMap.put("viewDeptPaResultListSum", viewDeptPaResultListSum);
		return new ModelAndView("/pa/workManagement/viewDeptPaResultList",
				modelMap);
	}

	/**
	 * 支付合计（部门） 导出页面
	 * 
	 */
	@RequestMapping(value = "/viewDeptPaResultListExport")
	public void viewDeptPaResultListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		List viewPaResultList = this.viewPaParamSer
				.viewDeptPaResultList(request);
		modelMap.put("viewDeptPaResultList", viewPaResultList);
		String SALARY_DISTIN = request.getParameter("seach_SALARY_DISTIN");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (admin.getCpnyId().equals("TSTO")) {
			if (SALARY_DISTIN.equals("春节奖")) {
				modelMap.put("XLS_NAME", "支付合计-春节奖（部门）");
				modelMap.put("XLS_IN", "viewDeptPaResultListYearRewardExport");
			} else {
				modelMap.put("XLS_NAME", "支付合计(部门)");
				modelMap.put("XLS_IN", "viewDeptPaResultListExport");
			}
		} else if (admin.getCpnyId().equals("SST")) {
			if (SALARY_DISTIN.equals("春节奖")) {
				modelMap.put("XLS_NAME", "支付合计-春节奖（部门）");
				modelMap.put("XLS_IN",
						"viewDeptPaResultListYearRewardExportSST");
			} else {
				modelMap.put("XLS_NAME", "支付合计(部门)");
				modelMap.put("XLS_IN", "viewDeptPaResultListExportSST");
			}
		}
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);
	}

	/**
	 * 月别工资明细左边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailmonthCountInfoLeft")
	public ModelAndView detailmonthCountInfoLeft(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		String currentIndex = request.getParameter("ITEM_TYPE");

		// 工资月内发工资的人员信息list
		/*if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
			modelMap
					.put("personInfo", empInfoSer.getPersonalInfoByPid(request));
		}*/
		if (request.getParameter("PAY_SCHEDULE_NO") != null
				&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
			List detailPersonCountInfoLeft = this.viewPaParamSer
					.detailPersonCountInfoLeft(request);

			modelMap.put("detailmonthCountInfoLeft", detailPersonCountInfoLeft);

		}

		// modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));

		return new ModelAndView("/pa/workManagement/detailmonthCountInfoLeft",
				modelMap);
	}

	/**
	 * 年工资明细左边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailYearCountInfoLeft")
	public ModelAndView detailYearCountInfoLeft(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// 得到符合条件的paPay

		String currentIndex = request.getParameter("ITEM_TYPE");
		modelMap.put("SALARY_DISTIN_NO", request
				.getParameter("SALARY_DISTIN_NO"));
		// 工资月内发工资的人员信息list
		if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
			modelMap
					.put("personInfo", empInfoSer.getPersonalInfoByPid(request));

//			List getPayScheduleList = this.viewPaParamSer
//					.getPayScheduleList(request);
//			modelMap.put("paPayScheduleList", getPayScheduleList);

		}
		if ((request.getParameter("PAY_DATE_PRO") != null
				&& !request.getParameter("PAY_DATE_PRO").equals(""))&&
				(request.getParameter("PAY_DATE") != null
						&& !request.getParameter("PAY_DATE").equals(""))) {
			List detailPersonCountInfoLeft = this.viewPaParamSer
					.detailYearCountInfoLeft(request);

			modelMap.put("detailYearCountInfoLeft", detailPersonCountInfoLeft);

		}
		String name = request.getParameter("PAY_DATE");
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("PAY_DATE_PRO", request.getParameter("PAY_DATE_PRO"));
		modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));
		modelMap
				.put("empInfoShow", request.getParameter("empInfoShow"));

		return new ModelAndView("/pa/workManagement/detailYearCountInfoLeft",
				modelMap);
	}
	/**
	 * 月/年工资明细 右边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailYearCountInfoRight")
	public ModelAndView detailYearCountInfoRight(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
//		List paPayScheduleList = this.paPayScheduleSer
//				.getPayScheduleAllList(request);
//		modelMap.put("paPayScheduleList", paPayScheduleList);
//		String currentIndex = request.getParameter("currentIndex");
		if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
			modelMap
					.put("personInfo", empInfoSer.getPersonalInfoByPid(request));

			// 工资细节明细
			if(request.getParameter("pFrom") != null
					&& request.getParameter("pFrom").equals("month")){
				List detailMonthCountInfoRightList = this.viewPaParamSer
						.detailMonthCountInfoRight(request);

				modelMap.put("detailMYCountInfoRightList",
						detailMonthCountInfoRightList);
			}else{
				List detailYearCountInfoRightList = this.viewPaParamSer
						.detailYearCountInfoRight(request);

				modelMap.put("detailMYCountInfoRightList",
						detailYearCountInfoRightList);
			}
		}

		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));

		return new ModelAndView("/pa/workManagement/detailYearCountInfoRight",
				modelMap);
	}

	/**
	 * 项目别工资明细
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailItemDifCountInfo")
	public ModelAndView detailItemDifCountInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		String currentIndex = request.getParameter("currentIndex");
		// 获取项目列表

		List itemValueInfo = this.viewPaParamSer.itemValueInfo(request);

		modelMap.put("itemValueInfo", itemValueInfo);

		// 工资细节明细

		if (request.getParameter("PAY_DATE") != null
				&& !request.getParameter("PAY_DATE").equals("")) {
			this.viewPaParamSer.viewResultConfirmSonList3(request); // 调取一下存储

			List viewResultConfirmList3Right = this.viewPaParamSer
					.viewResultConfirmList3Right(request);// 取相应的数值

			modelMap.put("detailItemDifCountInfo", viewResultConfirmList3Right);

		}

		modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("ITEM_ID", request.getParameter("ITEM_ID"));
		modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));

		return new ModelAndView("/pa/workManagement/detailItemDifCountInfo",
				modelMap);

	}

	/**
	 * 项目别工资明细
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailItemDifCountInfoExport")
	public void detailItemDifCountInfoExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		if (request.getParameter("PAY_DATE") != null
				&& !request.getParameter("PAY_DATE").equals("")) {
			this.viewPaParamSer.viewResultConfirmSonList3(request); // 调取一下存储

			List viewResultConfirmList3Right = this.viewPaParamSer
					.viewResultConfirmList3Right(request);// 取相应的数值
			modelMap.put("detailItemDifCountInfo", viewResultConfirmList3Right);
			modelMap.put("XLS_NAME", "changeProject_check");
			modelMap.put("XLS_IN", "detailItemDifCountInfoExport");

			RetrieveMasterListCtroller.runForPageWrite(request, response,
					modelMap);

		}
	}

	/**
	 * 结果确认
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResultConfirmList")
	public ModelAndView viewResultConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);

		List itemValueInfo = this.viewPaParamSer.itemValueInfo(request);

		modelMap.put("itemValueInfo", itemValueInfo);
		modelMap.put("ITEM_ID", request.getParameter("ITEM_ID"));

		String currentIndex = request.getParameter("currentIndex");
		if ("0".equals(currentIndex)) {
			modelMap.put("currentIndex", 5);
		} else if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			modelMap.put("currentIndex", 2);
		} else if ("3".equals(currentIndex)) {
			modelMap.put("currentIndex", 3);
		} else if ("4".equals(currentIndex)) {
			modelMap.put("currentIndex", 4);
		} else {
			modelMap.put("currentIndex", 0);
		}

		return new ModelAndView("/pa/workManagement/viewResultConfirmList",
				modelMap);
	}

	/**
	 * 结果确认
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResultConfirmSonList")
	public ModelAndView viewResultConfirmSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if ("0".equals(currentIndex)) {

			modelMap.put("viewResultConfirmSonList0", this.viewPaParamSer
					.viewResultConfirmSonList0(request));
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
			modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));
			modelMap.put("viewResultConfirmSonList3", this.viewPaParamSer
					.viewResultConfirmSonList3(request));
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			modelMap.put("viewResultConfirmSonList2", this.viewPaParamSer
					.viewResultConfirmSonList2(request));
			modelMap.put("currentIndex", 2);
		} else if ("3".equals(currentIndex)) {
			modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));
			modelMap.put("viewResultConfirmSonList3", this.viewPaParamSer
					.viewResultConfirmSonList3(request));
			modelMap.put("currentIndex", 3);
		} else if ("4".equals(currentIndex)) {
			modelMap.put("viewResultConfirmSonList4", this.viewPaParamSer
					.viewResultConfirmSonList4(request));
			modelMap.put("currentIndex", 4);
		} else if ("6".equals(currentIndex)) {
			modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));
			modelMap.put("SALARY_DISTIN_NO", request
					.getParameter("SALARY_DISTIN_NO"));
			modelMap.put("DEPT_NO", request
					.getParameter("seach_DEPT_NO"));
			modelMap.put("viewResultConfirmSonList6", this.viewPaParamSer
					.viewResultConfirmSonList6(request));
			modelMap.put("currentIndex", 6);
		} else {
			modelMap.put("PA_DATE", request.getParameter("PA_DATE"));
			modelMap.put("KEY", request.getParameter("KEY"));
			modelMap.put("SALARY_DISTIN_NO", request
					.getParameter("SALARY_DISTIN_NO"));
			modelMap.put("viewResultConfirmSonList5", this.viewPaParamSer
					.viewResultConfirmSonList5(request));
			modelMap.put("currentIndex", 5);
		}

		return new ModelAndView("/pa/workManagement/viewResultConfirmSonList",
				modelMap);
	}

	/**
	 * 右边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResultConfirmList2Right")
	public ModelAndView viewResultConfirmList2Right(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewResultConfirmList2Right = this.viewPaParamSer
				.viewResultConfirmList2Right(request);

		modelMap
				.put("viewResultConfirmList2Right", viewResultConfirmList2Right);

		return new ModelAndView(
				"/pa/workManagement/viewResultConfirmList2Right", modelMap);
	}

	/**
	 * 下边
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResultConfirmList2Bottom")
	public ModelAndView viewResultConfirmList2Bottom(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List viewResultConfirmList2Bottom = this.viewPaParamSer
				.viewResultConfirmList2Bottom(request);

		modelMap.put("viewResultConfirmList2Bottom",
				viewResultConfirmList2Bottom);

		return new ModelAndView(
				"/pa/workManagement/viewResultConfirmList2Bottom", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResultConfirmList3Right")
	public ModelAndView viewResultConfirmList3Right(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewResultConfirmList3Right = this.viewPaParamSer
				.viewResultConfirmList3Right(request);

		modelMap
				.put("viewResultConfirmList3Right", viewResultConfirmList3Right);
		modelMap.put("currentIndex", request.getParameter("currentIndex"));

		modelMap.put("ITEM_ID", request.getParameter("ITEM_ID"));
		modelMap.put("PAGE_TYPE", request.getParameter("PAGE_TYPE"));
		modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("SELECT_TYPE", request.getParameter("SELECT_TYPE"));

		return new ModelAndView(
				"/pa/workManagement/viewResultConfirmList3Right", modelMap);
	}

	/**
	 * 这是员工人数导出页面
	 * 
	 */
	@RequestMapping(value = "/viewResultConfirmList3RightExport")
	public void viewResultConfirmList3RightExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewResultConfirmList3Right = this.viewPaParamSer
				.viewResultConfirmList3Right(request);

		modelMap
				.put("viewResultConfirmList3Right", viewResultConfirmList3Right);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("XLS_NAME", "PersonNum");
		modelMap.put("XLS_IN", "viewResultConfirmList3RightExport");

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportTest")
	public void exportTest(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewResultConfirmList3Right = this.viewPaParamSer
				.viewResultConfirmList3Right(request);
		modelMap.put("XLS_NAME", "工资报表");
		modelMap.put("XLS_IN", "test");
		modelMap
				.put("viewResultConfirmList3Right", viewResultConfirmList3Right);

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaArSummarySearchList")
	public ModelAndView viewPaArSummarySearchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		String pa = request.getParameter("PAY_SCHEDULE_NO");
		for(int i=0;i<paPayScheduleList.size();i++){
			Map subMap = (LinkedHashMap)paPayScheduleList.get(i);
				modelMap.put("PAY_SCHEDULE_NO", subMap.get("PAY_SCHEDULE_NO"));
				break;
		}
		if (pa != null && !pa.equals("")) {
			//List viewPaArSummaryList = this.viewPaParamSer.viewPaArSummaryList(request);

			List viewPaArSummarySearchList = this.viewPaParamSer.viewPaArSummarySearchList(request);
			//modelMap.put("viewPaArSummaryList", viewPaArSummaryList);

			modelMap.put("viewPaArSummarySearchList", viewPaArSummarySearchList);
		}
		return new ModelAndView("/pa/workManagement/viewPaArSummarySearchList",	modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaArSummarySearchListReport")
	public void viewPaArSummarySearchListReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String pa = request.getParameter("PAY_SCHEDULE_NO");
		if (pa != null && !pa.equals("")) {
			List viewPaArSummaryList = this.viewPaParamSer.viewPaArSummaryList(request);

			List viewPaArSummarySearchList = this.viewPaParamSer.viewPaArSummarySearchList(request);
			modelMap.put("viewPaArSummaryList", viewPaArSummaryList);

			modelMap.put("viewPaArSummarySearchList", viewPaArSummarySearchList);
		}
		modelMap.put("XLS_NAME", "HTSV_Attendance");
		modelMap.put("XLS_IN", "viewPaArSummarySearchListReport");

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportPayDetailTxtReport")
	public void exportPayDetailTxtReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String pa = request.getParameter("PAY_SCHEDULE_NO");
		if (pa != null && !pa.equals("")) {
			List viewPaArSummaryList = this.viewPaParamSer
					.viewPaArSummaryList(request);

			List exportPayDetailTxtReport = this.viewPaParamSer
					.exportPayDetailTxtReport(request);
			modelMap.put("exportPayDetailTxtReport", exportPayDetailTxtReport);

		}
		modelMap.put("XLS_NAME", "工资详细TXT");
		modelMap.put("XLS_IN", "exportPayDetailTxtReport");
		// txt导出
		writeTxtUtil.runPageWriteTxt(request, response, modelMap);

	}

}
