package com.ait.pa.action.salary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.ait.ar.service.ArMonthSer;
import com.ait.ar.service.CycleSer;
import com.ait.ess.action.PersonalPaInfoCtroller;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.ess.service.PersonalPaInfoSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.workManagement.viewPaParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaComputeItemCtroller.java
 * @Description:
 * @Create date: 2012-1-16 下午06:56:45
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PaComputeItemCtroller {
	Logger logger = Logger.getLogger(PaComputeItemCtroller.class);

	@Autowired
	private PaComputeItemSer paComputeItemSer;

	@Autowired
	private ArMonthSer arMonthSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	// 校验工资是否开放
	@Autowired
	private PersonalPaInfoSer personalpaInfoSer;

	@Autowired
	private viewPaParamSer viewPaParamSer;
	@Autowired
	private EssEmpInfoSer empInfoSer;

	/**
	 * 查询所有计算项目库 from PA_ITEM
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaComputeItem")
	public ModelAndView viewPaComputeItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paComputeItemList = this.paComputeItemSer
				.getPaComputeItemList(request);
		int paComputeItemCnt = this.paComputeItemSer
				.getPaComputeItemCnt(request);

		modelMap.put("itemList", paComputeItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paComputeItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2408"));

		return new ModelAndView("/pa/salary/viewPaComputeItem", modelMap);
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaComputeItemView")
	public ModelAndView addPaComputeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/pa/salary/addPaComputeItemView", modelMap);
	}

	/**
	 * 执行添加INTO PA_ITEM 添加前判断项目(ITEM_ID)是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaComputeItemInfo")
	@ResponseBody
	public Map<String, Object> addPaComputeItemInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.paComputeItemSer.checkAddPaComputeItemInfo(request);
		if (errorNum == 0) {
			int returnNum = this.paComputeItemSer.addPaComputeItemInfo(request);
			if (returnNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0202");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_conflict", request));
		}
		return map;
	}

	/**
	 * 跳转到修改页面 根据ITEM_NO查询出PA_ITEM对象(paComputeItemInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaComputeItemView")
	public ModelAndView updatePaComputeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paComputeItemInfo = this.paComputeItemSer
				.getPaComputeItemInfo(request);
		modelMap.put("paComputeItemInfo", paComputeItemInfo);

		return new ModelAndView("/pa/salary/updatePaComputeItemView", modelMap);
	}

	/**
	 * 执行修改 执行前根据ITEM_ID判断是否已经存在此ID
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaComputeItemInfo")
	@ResponseBody
	public Map<String, Object> updatePaComputeItemInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> jo = new HashMap<String, Object>();
		int errorNum = this.paComputeItemSer.checkAddPaComputeItemInfo(request);
		if (errorNum == 0) {
			int returnNum = this.paComputeItemSer
					.updatePaComputeItemInfo(request);
			if (returnNum == 1) {
				jo.put("statusCode", "200");
				jo.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				jo.put("navTabId", "pa0202");
			} else {
				jo.put("statusCode", "300");
				jo.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.ID_update_conflict", request));
		}
		return jo;
	}

	/**
	 * 执行删除 删除前判断在PA_FORMULAR和USER_TAB_COLS是否有关联数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaComputeItemInfo")
	@ResponseBody
	public Map<String, Object> deletePaComputeItemInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> jo = new HashMap<String, Object>();
		int errorNum = this.paComputeItemSer
				.checkDeletePaComputeItemInfo(request);
		if (errorNum > 0) {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		} else {
			int result = this.paComputeItemSer.deletePaComputeItemInfo(request);
			if (result == 1) {
				jo.put("statusCode", "200");
				jo.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				jo.put("navTabId", "pa0202");
			} else {
				jo.put("statusCode", "300");
				jo.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		}
		return jo;
	}

	@RequestMapping(value = "/updatePaComputeItemInfoCalOrder")
	@ResponseBody
	public String updatePaComputeItemInfoCalOrder(HttpServletRequest request)
			throws Exception {

		String returnString = "Y";

		this.paComputeItemSer.updatePaComputeItemInfoCalOrder(request);

		return returnString;
	}

	/**
	 * 修改计算顺序CALCU_ORDER
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePCInfoByCalcuOrder")
	@ResponseBody
	public Map<String, Object> updatePCInfoByCalcuOrder(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String Param_no = request.getParameter("param_no");
		String calcu_order = request.getParameter("calcu_order");

		int c = this.paComputeItemSer.updatePCInfoByCalcuOrder(request, type,
				Param_no, calcu_order);
		int d = this.paComputeItemSer.updatePCInfoByParamNo(request, type,
				Param_no, calcu_order);

		Map<String, Object> jo = new HashMap<String, Object>();
		if (c + d == 2) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("navTabId", "pa0822");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return jo;

	}

	/**
	 * 点击检索 页面循环里的超链到后台查询数据 根据超链里的rel对应div的id 返回到页面显示结果
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaComputeItemParamList")
	public ModelAndView viewPaComputeItemParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paItemPramList = this.paComputeItemSer
				.getPaComputeItemParamList(request);
		// int paItemPramListCnt =
		// this.paComputeItemSer.getPaComputeItemParamListCnt(request);
		modelMap.put("paItemPramList", paItemPramList);
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME, paItemPramListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2571"));

		return new ModelAndView("/pa/salary/viewPaComputeItemParamList",
				modelMap);
	}

	/**
	 * 工资个人信息画面(view ArMonth)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaMonthPersonInfo")
	public ModelAndView viewPaMonthPersonInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		/**
		 * HttpSession session = request.getSession(); AdminBean admin =
		 * SessionUtil.getLoginUserFromSession(request); String statNo =
		 * request.getParameter("seach_STAT_NO") !=null ?
		 * request.getParameter("seach_STAT_NO") : ""; String supervisorId =
		 * admin.getPersonId();
		 * 
		 * 
		 * 
		 * String arMonth = this.getToday("yyyy") + this.getToday("MM");
		 * 
		 * if (request.getParameter("seach_year_ar0106") != null &&
		 * request.getParameter("seach_month_ar0106") != null) {
		 * 
		 * arMonth = request.getParameter("seach_year_ar0106") +
		 * request.getParameter("seach_month_ar0106"); }PAY_DATE
		 * 
		 * List getPaMonthListYN = new ArrayList(); // 获取显示汇总项目列名 List
		 * getPaColumnsListYN =
		 * this.paComputeItemSer.getPaComputeItemParamListYN(request); //取考勤区间
		 * //List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		 * String empid =
		 * request.getParameter("seach_condition")!=null?request.getParameter
		 * ("seach_condition"):""; //request.setAttribute("numPerPage", 0);
		 * if(!"".equals(empid) && empid != null){ getPaMonthListYN =
		 * this.paComputeItemSer.getPaMonthListYN(request, getPaColumnsListYN,
		 * arMonth); }
		 * 
		 * String datetable = arMonthSer.makeTableHTML("", getPaColumnsListYN,
		 * getPaMonthListYN);
		 * 
		 * // modelMap.put("STAT_NO", statNo) ; modelMap.put("condition", empid)
		 * ; //modelMap.put("statnoList", statnoList) ; modelMap.put("arMonth",
		 * arMonth); modelMap.put("supervisorId", supervisorId);
		 * modelMap.put("datetable", datetable);
		 **/

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		String statNo = request.getParameter("seach_STAT_NO") != null ? request
				.getParameter("seach_STAT_NO") : "";
		String supervisorId = admin.getPersonId();
		String CPNY_ID = admin.getCpnyId();
		// paramMap.put("PERSON_ID", admin.getPersonId());//只能查询自己的

		String arMonth = this.getToday("yyyy") + this.getToday("MM");
		String changeMonth = "";
		String year = request.getParameter("seach_year_ar0106");
		String month = request.getParameter("seach_month_ar0106");
		if (request.getParameter("seach_year_ar0106") != null
				&& request.getParameter("seach_month_ar0106") != null) {

			arMonth = request.getParameter("seach_year_ar0106")
					+ request.getParameter("seach_month_ar0106");
		} else {
			// 开启页面时，查询最近一年最后工资开放月
			Calendar c = Calendar.getInstance();

			changeMonth = new SimpleDateFormat("yyyyMM").format(c.getTime())
					.toString();
			paramMap.put("PA_MONTH", changeMonth);
			arMonth = changeMonth;
		}
		List getHrMonthListYN = new ArrayList();
		// 获取人事项目项目列名
		List getHrColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "1");
		List getArMonthListYN = new ArrayList();
		// 获取考勤项目项目列名
		List getArColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "2");
		List getPaMonthListYN = new ArrayList();
		// 获取工资项目项目列名
		List getPaColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "3");
		List getIsMonthListYN = new ArrayList();
		// 获取保险项目项目列名
		List getIsColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "4");

		getPaMonthListYN = this.paComputeItemSer.getEssPa(request,
				getPaColumnsListYN, arMonth, "2");
		// }
		// 如果有项目才进行处理。没有直接跳过
		if (getPaColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getPaMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getPaMonthListYN.get(i);
				for (int j = 0; j < getPaColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getPaColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = (BigDecimal) datamap.get(namemap
							.get("COLUMN_NAME"));// 根据id取值
					// 判断是否为0，不是0四舍五入
					String i1 = "0";

					if (null == value)
						value = "0";
					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getPaColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getPaMonthListYN.remove(i);
				getPaMonthListYN.add(datamap);
			}
		}
		if (getPaMonthListYN.size() == 0) {
			getPaColumnsListYN.clear();
		}

		String datePatable = arMonthSer.makeTableHTML("工资项目列表 ",
				getPaColumnsListYN, getPaMonthListYN);

		getHrMonthListYN = this.paComputeItemSer.getEssPa(request,
				getHrColumnsListYN, arMonth, "1");

		if (getHrColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getHrMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getHrMonthListYN.get(i);
				for (int j = 0; j < getHrColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getHrColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = "";
					if ("E.DEPTNO".equals(namemap.get("COLUMN_NAME"))) {
						value = datamap.get("DEPTNAME");// 根据id取值
					} else {
						value = datamap.get(namemap.get("COLUMN_NAME"));// 根据id取值
					}

					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					if (null == value)
						value = "0";

					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getHrColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						if ("DEPTNO".equals(namemap.get("COLUMN_NAME"))) {
							datamap.put("DEPTNO", (datamap.get(namemap
									.get("DEPTNAME"))));
						}
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getHrMonthListYN.remove(i);
				getHrMonthListYN.add(datamap);
			}
		}

		String dateHrtable = arMonthSer.makeTableHTML("人员基本信息",
				getHrColumnsListYN, getHrMonthListYN);

		getArMonthListYN = this.paComputeItemSer.getEssPa(request,
				getArColumnsListYN, arMonth, "2");

		if (getArColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getArMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getArMonthListYN.get(i);
				for (int j = 0; j < getArColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getArColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = (BigDecimal) datamap.get(namemap
							.get("COLUMN_NAME"));// 根据id取值
					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					if (null == value)
						value = "0";
					// if(Integer.valueOf(String.valueOf(value))==0){
					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getArColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getArMonthListYN.remove(i);
				getArMonthListYN.add(datamap);
			}
		}
		if (getArMonthListYN.size() == 0) {
			getArColumnsListYN.clear();
		}

		String dateArtable = arMonthSer.makeTableHTML("考勤基本信息",
				getArColumnsListYN, getArMonthListYN);

		getIsMonthListYN = this.paComputeItemSer.getEssPa(request,
				getIsColumnsListYN, arMonth, "2");

		if (getIsColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getIsMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getIsMonthListYN.get(i);
				for (int j = 0; j < getIsColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getIsColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = (BigDecimal) datamap.get(namemap
							.get("COLUMN_NAME"));// 根据id取值
					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					if (null == value)
						value = "0";

					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getIsColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getIsMonthListYN.remove(i);
				getIsMonthListYN.add(datamap);
			}
		}
		if (getIsMonthListYN.size() == 0) {
			getIsColumnsListYN.clear();
		}

		String dateIstable = arMonthSer.makeTableHTML("保险福利及税金扣款项目 ",
				getIsColumnsListYN, getIsMonthListYN);

		// 获取工资输入项目明细 目前只显示工资调整加和减
		List getPaParamDateList = this.paComputeItemSer
				.getPaParamDateList(request, arMonth, paramMap
						.get("dwz.person.personId") != null ? paramMap.get(
						"dwz.person.personId").toString() : admin.getPersonId());

		// 获取工资调整的备注暂未启用
		// List getPaRemark =
		// this.paComputeItemSer.getPaRemark(request,arMonth,admin.getPersonId());

		modelMap.put("arMonth", arMonth);
		modelMap.put("empid", paramMap.get("dwz.person.empId"));
		modelMap.put("dwz.person.empId", paramMap.get("dwz.person.empId"));
		modelMap.put("personid", paramMap.get("dwz.person.personId"));
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("dateHrtable", dateHrtable);
		modelMap.put("dateArtable", dateArtable);
		modelMap.put("datePatable", datePatable);
		modelMap.put("dateIstable", dateIstable);
		modelMap.put("paParamDateList", getPaParamDateList);
		modelMap.put("CPNY_ID", CPNY_ID);
		return new ModelAndView("/pa/salary/viewPaMonthPersonInfo", modelMap);
	}

	/**
	 * 工资个人信息画面(view ArMonth) 为ess服务，只能看本人的信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaMonthPersonInfoEssList")
	public ModelAndView viewPaMonthPersonInfoEssList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		modelMap.put("paPayScheduleNoByPersonId", viewPaParamSer.paPayScheduleNoByPersonId(request));

		if (request.getParameter("PERSON_ID") != null
				&& !request.getParameter("PERSON_ID").equals("")) {
			modelMap
					.put("personInfo", viewPaParamSer.getPersonalInfoForEmpSalaryInfo(request));
			modelMap.put("paEmpAccount", viewPaParamSer.paEmpAccount(request));

			// 工资细节明细
			if (request.getParameter("PAY_SCHEDULE_NO") != null
					&& !request.getParameter("PAY_SCHEDULE_NO").equals("")) {
				List detailPersonCountInfoList = this.viewPaParamSer.getEmpSalaryInfoList(request);
				
				modelMap.put("payStubList", detailPersonCountInfoList);

//				modelMap.put("paSpecialItem", viewPaParamSer.paOpenFlag(request));
				modelMap.put("paInputTaxItem", viewPaParamSer.getEmpSalaryInfoListWithPamram(request, "getPaInputTaxItem"));

			}
		}

		modelMap
				.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));

		return new ModelAndView("/pa/salary/viewPaMonthPersonInfoEssList",
				modelMap);
	}

	/**
	 * 工资系统-工资查看-查看员工工资
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaMonthPersonPaInfoList")
	public ModelAndView viewPaMonthPersonPaInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String statNo = request.getParameter("seach_STAT_NO") != null ? request
				.getParameter("seach_STAT_NO") : "";
		String supervisorId = admin.getPersonId();
		String CPNY_ID = admin.getCpnyId();

		String arMonth = this.getToday("yyyy") + this.getToday("MM");
		String changeMonth = "";
		String year = request.getParameter("seach_year_ar0106");
		String month = request.getParameter("seach_month_ar0106");
		if (request.getParameter("seach_year_ar0106") != null
				&& request.getParameter("seach_month_ar0106") != null) {

			arMonth = request.getParameter("seach_year_ar0106")
					+ request.getParameter("seach_month_ar0106");
		} else {
			// 开启页面时，查询最近一年最后工资开放月
			Calendar c = Calendar.getInstance();
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("GIVE_DATE", request.getParameter("GIVE_DATE"));
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("STAT_NO", admin.getStatNo());
			paramMap.put("AR_DEPT_NO", admin.getDeptNo());
			for (int i = 0; i < 12; i++) {
				changeMonth = new SimpleDateFormat("yyyyMM")
						.format(c.getTime()).toString();
				paramMap.put("PA_MONTH", changeMonth);
				int count = personalpaInfoSer.getSalaryDisparkNew(paramMap);
				if (count > 0) {
					year = new SimpleDateFormat("yyyy").format(c.getTime())
							.toString();
					month = new SimpleDateFormat("MM").format(c.getTime())
							.toString();
					modelMap.put("year_ar0106", year);
					modelMap.put("month_ar0106", month);
					arMonth = year + month;
					break;
				}
				c.add(Calendar.MONTH, -1);
				if (i == 11 && count == 0) {
					return new ModelAndView(
							"/pa/salary/viewPaMonthPersonPaInfoList", modelMap);
				}
			}
		}
		// SY法人临时职不允许看个人工资
		if (admin.getCpnyId().equals("LGESY")) {
			List getEmpTypeCodeList = this.paComputeItemSer
					.getEmpTypeCodeForPersonId(request, admin.getPersonId());
			Map map = (Map) getEmpTypeCodeList.get(0);
			if (map.get("EMP_TYPE_CODE").equals("211292")
					|| (map.get("EMP_TYPE_NAME").equals("临时职"))) {
				return new ModelAndView(
						"/pa/salary/viewPaMonthPersonPaInfoList", modelMap);
			}
		}
		List getHrMonthListYN = new ArrayList();
		// 获取人事项目项目列名
		List getHrColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "1");
		List getArMonthListYN = new ArrayList();
		// 获取考勤项目项目列名
		List getArColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "2");
		List getPaMonthListYN = new ArrayList();
		// 获取工资项目项目列名
		List getPaColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "3");
		List getIsMonthListYN = new ArrayList();
		// 获取保险项目项目列名
		List getIsColumnsListYN = this.paComputeItemSer
				.getPaComputeItemParamESSListYN(request, "4");

		getPaMonthListYN = this.paComputeItemSer.getEssPa(request,
				getPaColumnsListYN, arMonth, "2");
		// 如果有项目才进行处理。没有直接跳过
		if (getPaColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getPaMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getPaMonthListYN.get(i);
				for (int j = 0; j < getPaColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getPaColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = (BigDecimal) datamap.get(namemap
							.get("COLUMN_NAME"));// 根据id取值
					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					// if(Integer.valueOf(String.valueOf(value))==0){
					if (null == value)
						value = "0";
					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getPaColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getPaMonthListYN.remove(i);
				getPaMonthListYN.add(datamap);
			}
		}
		if (getPaMonthListYN.size() == 0) {
			getPaColumnsListYN.clear();
		}
		// }
		String datePatable = arMonthSer.makeTableHTML("工资项目列表 ",
				getPaColumnsListYN, getPaMonthListYN);

		getHrMonthListYN = this.paComputeItemSer.getEssPa(request,
				getHrColumnsListYN, arMonth, "1");
		// }
		if (getHrColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getHrMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getHrMonthListYN.get(i);
				for (int j = 0; j < getHrColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getHrColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = "";
					if ("E.DEPTNO".equals(namemap.get("COLUMN_NAME"))) {
						value = datamap.get("DEPTNAME");// 根据id取值
					} else {
						value = datamap.get(namemap.get("COLUMN_NAME"));// 根据id取值
					}

					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					if (null == value)
						value = "0";
					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getHrColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						if ("DEPTNO".equals(namemap.get("COLUMN_NAME"))) {
							datamap.put("DEPTNO", (datamap.get(namemap
									.get("DEPTNAME"))));
						}
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getHrMonthListYN.remove(i);
				getHrMonthListYN.add(datamap);
			}
		}
		String dateHrtable = arMonthSer.makeTableHTML("人员基本信息",
				getHrColumnsListYN, getHrMonthListYN);

		getArMonthListYN = this.paComputeItemSer.getEssPa(request,
				getArColumnsListYN, arMonth, "2");
		if (getArColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getArMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getArMonthListYN.get(i);
				for (int j = 0; j < getArColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getArColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = (BigDecimal) datamap.get(namemap
							.get("COLUMN_NAME"));// 根据id取值
					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					if (null == value)
						value = "0";
					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getArColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getArMonthListYN.remove(i);
				getArMonthListYN.add(datamap);
			}
		}
		if (getArMonthListYN.size() == 0) {
			getArColumnsListYN.clear();
		}
		String dateArtable = arMonthSer.makeTableHTML("考勤基本信息",
				getArColumnsListYN, getArMonthListYN);
		getIsMonthListYN = this.paComputeItemSer.getEssPa(request,
				getIsColumnsListYN, arMonth, "2");
		if (getIsColumnsListYN.size() != 0) {
			// 根据查出的考勤项目名称，校验查出的数值为0的移除这一项,并将数值四舍五入保留两位小数
			for (int i = 0; i < getIsMonthListYN.size(); i++) {
				LinkedHashMap datamap = (LinkedHashMap) getIsMonthListYN.get(i);
				for (int j = 0; j < getIsColumnsListYN.size(); j++) {
					LinkedHashMap namemap = (LinkedHashMap) getIsColumnsListYN
							.get(j);// 取出这一项英文id
					Object value = (BigDecimal) datamap.get(namemap
							.get("COLUMN_NAME"));// 根据id取值
					// 判断是否为0，不是0四舍五入
					String i1 = "0";
					if (null == value)
						value = "0";
					if (i1.equals(value.toString())
							|| "".equals(value.toString())) {
						getIsColumnsListYN.remove(j);// 删除项目列表中此项，不再生产页面项
						j--;// remove之后。list的size会变化
						datamap.remove(namemap.get("COLUMN_NAME"));// 删除数值中为0的此项
					} else {
						datamap.put(namemap.get("COLUMN_NAME"), (datamap
								.get(namemap.get("COLUMN_NAME"))));
					}
				}
				// 重新将一条修改后的链表map放入list
				getIsMonthListYN.remove(i);
				getIsMonthListYN.add(datamap);
			}
		}
		if (getIsMonthListYN.size() == 0) {
			getIsColumnsListYN.clear();
		}
		String dateIstable = arMonthSer.makeTableHTML("保险福利及税金扣款项目 ",
				getIsColumnsListYN, getIsMonthListYN);

		// 获取工资输入项目明细 目前只显示工资调整加和减
		List getPaParamDateList = this.paComputeItemSer.getPaParamDateList(
				request, arMonth, admin.getPersonId());

		// 获取工资调整的备注暂未启用
		// List getPaRemark =
		// this.paComputeItemSer.getPaRemark(request,arMonth,admin.getPersonId());

		modelMap.put("arMonth", arMonth);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("dateHrtable", dateHrtable);
		modelMap.put("dateArtable", dateArtable);
		modelMap.put("datePatable", datePatable);
		modelMap.put("dateIstable", dateIstable);
		modelMap.put("paParamDateList", getPaParamDateList);
		modelMap.put("CPNY_ID", CPNY_ID);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/pa/salary/viewPaMonthPersonPaInfoList",
				modelMap);
	}

	/**
	 * 工资个人信息画面(view ArMonth) 为ess服务，只能看本人的信息 PN专用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaMonthPersonInfoEssLgepnList")
	public ModelAndView viewPaMonthPersonInfoEssLgepnList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String paMonth = "";
		if (!"".equals(paramMap.get("seach_year_ar0106"))
				&& paramMap.get("seach_year_ar0106") != null
				&& !"".equals(paramMap.get("seach_month_ar0106"))
				&& paramMap.get("seach_month_ar0106") != null) {
			paMonth = paramMap.get("seach_year_ar0106").toString()
					+ paramMap.get("seach_month_ar0106").toString();
		} else {
			paMonth = this.getToday("yyyy") + this.getToday("MM");
		}
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("".equals(paramMap.get("PA_MONTH"))
				|| paramMap.get("PA_MONTH") == null) {
			paramMap.put("PA_MONTH", paMonth);
		}
		paramMap.put("STAT_NO", admin.getStatNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		int count = personalpaInfoSer.getSalaryDisparkNew(paramMap);
		Map objectPn = (Map) this.paComputeItemSer.getLgepnPaInfo(request);
		if (count > 0) {
			modelMap.put("PnItem", objectPn);
		}
		modelMap.put("count", count);
		return new ModelAndView("/pa/salary/viewPaMonthPersonInfoEssLgepnList",
				modelMap);
	}

	/**
	 * 根据格式参数返回当前日期
	 * 
	 * @param pOutformat
	 * @return String
	 */
	private String getToday(String pOutformat) {

		SimpleDateFormat pOutformatter = new SimpleDateFormat(pOutformat,
				java.util.Locale.CHINA);

		String rDateString = null;
		Date vDate = new Date();

		try {
			rDateString = pOutformatter.format(vDate);

		} catch (Exception e) {
		}

		return rDateString;
	}

	/**
	 * 跳转到添加页面 并且把公司集合,带入添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaComputeItemParamView")
	public ModelAndView addPaInputItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		request.setAttribute("INPUT_ITEM_PARAM", "1");// 输入项目参数 添加区分
		List paComputeItemList = this.paComputeItemSer
				.getPaComputeItemList(request);
		modelMap.put("itemList", paComputeItemList);
		return new ModelAndView("/pa/salary/addPaComputeItemParamView",
				modelMap);
	}

	/**
	 * 执行添加 添加前判断PA_ITEM_PARAM表里是否有此
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> addPaComputeItemParamInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int errorNum = this.paComputeItemSer
				.checkAddPaComputeItemParamInfo(request);
		if (errorNum == 0) {
			int returnNum = this.paComputeItemSer
					.addPaComputeItemParamInfo(request);

			if (returnNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0213");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_conflict", request));
		}
		return map;
	}

	/**
	 * 跳转到修改页面根据PARAM_NO查询出PA_ITEM_PARAM对象(paComputeItemParam),查询出公司list带入添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaComputeItemParamView")
	public ModelAndView upPaComputeItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paComputeItemParam = this.paComputeItemSer
				.getPaComputeItemParamInfo(request);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("paComputeItemParam", paComputeItemParam);

		return new ModelAndView("/pa/salary/updatePaComputeItemParamView",
				modelMap);
	}

	/**
	 * 执行修改 修改前判断PA_ITEM_PARAM表里是否有此ITEM_NO 并且 PARAM_NO不是当前PARAM_NO
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upPaComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> upPaComputeItemParamInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.paComputeItemSer
				.checkAddPaComputeItemParamInfo(request);
		if (errorNum == 1) {
			int returnNum = this.paComputeItemSer
					.upPaComputeItemParamInfo(request);
			if (returnNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0213");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 执行删除 删除前判断PA_FORMULAR表USER_TAB_COLS有没有使用的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> deletePaComputeItemParamInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.paComputeItemSer
				.checkDeletePaComputeItemInfo(request);

		if (errorNum > 0) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		} else {
			int resultNum = this.paComputeItemSer
					.deletePaComputeItemParamInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0213");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_info_use", request));
			}
		}
		return map;
	}

	/**
	 * 保存显示工资计算项目
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewShowPaComputeItemParamList", method = RequestMethod.POST)
	@ResponseBody
	public Map viewShowPaComputeItemParamList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paComputeItemSer
				.addShowPaComputeItemParamList(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "修改成功");
			map.put("navTabId", "pa0213");
		} else {
			map.put("statusCode", "300");
			map.put("message", "修改失败");
		}

		return map;
	}

}
