package com.ait.hrm.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.hrm.dao.ContractInfoDao;
import com.ait.hrm.service.ContractInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: CycleSer.java
 * @Create date: Jan 6, 2012 3:43:13 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/contractInfo")
public class ContractInfoCtroller {

	Logger logger = Logger.getLogger(ContractInfoCtroller.class);

	@Autowired
	private ContractInfoSer contractInfoSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private ExcelUtilSer excelUtilSer;

	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private ContractInfoDao contractInfoDao;
	@Autowired
	private CycleSer cycleSer;

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractInfoForSearch")
	public ModelAndView viewContractInfoForSearchList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		/*String EmpOffice = request.getParameter("seach_EmpOffice") == null ? "15119"
				: request.getParameter("seach_EmpOffice");
		modelMap.put("EmpOffice", EmpOffice);*/
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag !=null && !"".equals(firstFlag)) {
			List contractInfoList = this.contractInfoSer.getContractInfoListForSearch(request);
			modelMap.put("itemList", contractInfoList);
		}
		return new ModelAndView("/hrm/contractInfo/viewContractInfoForSearch",
				modelMap);
	}

	/**
	 * 合同查询 (Contract inquires)(update 修改)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateContractInfoForUpdate")
	@ResponseBody
	public Map updateContractInfoForUpdate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.contractInfoSer.updateContractInfoForUpdate(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");// 成功
		} else {

			map.put("statusCode", "300");
			map.put("message", "保存失败");// 失败
		}
		return map;
	}

	/**
	 * 合同变更
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractChangeList")
	public ModelAndView viewContractChangeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag !=null && !"".equals(firstFlag)) {
			modelMap.put("htlx" , JsonUtil.writeInternal(empInfoSer.getCodeList("123199", request)));  //合同类型
			List contractInfoList = this.contractInfoSer.getContractChangeList(request);
			modelMap.put("itemList", contractInfoList);
		}
		return new ModelAndView("/hrm/contractInfo/viewContractChangeList",
				modelMap);
	}
	
	/**
	 * 合同修改删除
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractUpdateList")
	public ModelAndView viewContractUpdateList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		String type = request.getParameter("type");
		if (firstFlag !=null && !"".equals(firstFlag)) {
			List contractInfoList = new ArrayList();
			modelMap.put("htxz" , JsonUtil.writeInternal(empInfoSer.getCodeList("14016095", request)));  //合同性质
			modelMap.put("htlx" , JsonUtil.writeInternal(empInfoSer.getCodeList("123199", request)));  //合同类型
			if(type !=null && !"".equals(type)){
				 contractInfoList = this.contractInfoSer.getNullContractUpdateList(request);
			}else{
				this.contractInfoSer.deleteContractUpdateList(request);
				 contractInfoList = this.contractInfoSer.getContractUpdateList(request);
			}
			modelMap.put("itemList", contractInfoList);
		}else{
			this.contractInfoSer.deleteContractUpdateList(request);
		}
		
		return new ModelAndView("/hrm/contractInfo/viewContractUpdateList",
				modelMap);
	}
	
	@RequestMapping(value = "/viewContractAddList")
	public ModelAndView viewAddTempEmp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/hrm/contractInfo/viewContractAddList", modelMap);
	}

	/**
	 * 合同审批情况查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractAffirmInfo")
	public ModelAndView viewContractAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List affirmList = this.contractInfoSer
				.getAffirmorListByContractNo(request);
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("contractInfo", contractInfoSer
				.getContractForUpdate(request));

		return new ModelAndView("/hrm/contractInfo/viewContractAffirmInfo",
				modelMap);
	}

	/**
	 * 合同审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractAffirm")
	public ModelAndView viewContractAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		List affirmList = this.contractInfoSer
				.getAffirmorListByContractNo(request);
		String ESS_AFFIRM_NO = "";
		for (int i = 0; i < affirmList.size(); i++) {
			LinkedHashMap paramMap = (LinkedHashMap) affirmList.get(i);
			if ("0".equals(paramMap.get("AFFIRM_FLAG").toString())) {
				if (personId.equals(paramMap.get("AFFIRMOR_ID").toString())) {
					ESS_AFFIRM_NO = paramMap.get("ESS_AFFIRM_NO").toString();
					break;
				}
			}
		}
		modelMap.put("ESS_AFFIRM_NO", ESS_AFFIRM_NO);

		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("contractInfo", contractInfoSer
				.getContractForUpdate(request));

		return new ModelAndView("/hrm/contractInfo/viewContractAffirm",
				modelMap);
	}

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: 到期合同查询 ：HQ 法人管理者 查看所有法人的 到期合同信息
	 * @author yorio youjia@ait.net.cn
	 * @date Aug 23, 2013 6:22:27 PM
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractInfoForSearchALL")
	public ModelAndView viewContractInfoForSearchALLList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		modelMap.put("defaultCpny", cpny_id);

		List contractInfoList = this.contractInfoSer
				.getContractInfoListForSearchALL(request);
		int contractCnt = this.contractInfoSer
				.getContractCntForSearchALL(request);

		modelMap.put("itemList", contractInfoList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, contractCnt);

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2556"));

		return new ModelAndView(
				"/hrm/contractInfo/viewContractInfoForSearchALL", modelMap);
	}

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: 未签合同查询 ：HQ 法人管理者 查看所有法人的 未签合同信息
	 * @author yorio youjia@ait.net.cn
	 * @date Aug 23, 2013 6:22:27 PM
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewNOContractInfo")
	public ModelAndView viewNOContractInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List contractInfoList = this.contractInfoSer.getNOContractInfoList(request);

		modelMap.put("itemList", contractInfoList);
		
		modelMap.put("htlx" , JsonUtil.writeInternal(empInfoSer.getCodeList("123199", request)));  //合同类型
		//modelMap.put("htxz" , JsonUtil.writeInternal(empInfoSer.getCodeList("14016095", request)));  //合同性质
		modelMap.put("gongshi" , JsonUtil.writeInternal(empInfoSer.getCodeList("123200", request)));  //工时
		
		return new ModelAndView("/hrm/contractInfo/viewNOContractInfo",
				modelMap);
	}

	/**
	 * 合同查询导出 (Contract inquires)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractInfoForSearchExcel")
	public ModelAndView viewContractInfoForSearchExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List contractInfoList = this.contractInfoSer
				.getContractInfoListForSearchExcel(request);

		modelMap.put("itemList", contractInfoList);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView(
				"/hrm/contractInfo/viewContractInfoForSearchExcel", modelMap);
	}

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: 报表到期合同查询 ：HQ 法人管理者 查看所有法人的 到期合同信息
	 * @author yorio youjia@ait.net.cn
	 * @date Aug 24, 2013 5:35:15 PM
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractInfoForSearchALLExcel")
	public ModelAndView viewContractInfoForSearchALLExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List contractInfoList = this.contractInfoSer
				.getContractInfoListForSearchALLExcel(request);

		modelMap.put("itemList", contractInfoList);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView(
				"/hrm/contractInfo/viewContractInfoForSearchALLExcel", modelMap);
	}

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: 未签合同查询 ：HQ 法人管理者 查看所有法人的 未签合同信息
	 * @author yorio youjia@ait.net.cn
	 * @date Aug 24, 2013 5:35:15 PM
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewNOContractInfoExcel")
	public ModelAndView viewNOContractInfoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List contractInfoList = this.contractInfoSer
				.getNOContractInfoExcel(request);

		modelMap.put("itemList", contractInfoList);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/hrm/contractInfo/viewNOContractInfoExcel",
				modelMap);
	}

	/**
	 * 执行修改合同(update contract)
	 * 
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateContractInfo")
	@ResponseBody
	public Map updateContractInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.updateContractInfo(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("formId", "viewChangeContractInfo");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return jo;
	}
	
	/**
	 * 执行修改合同(update contract)
	 * 
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateContractInfo1")
	@ResponseBody 
	public Map updateContractInfo1(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.updateContractInfo1(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("formId", "viewUpdateContractInfo");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return jo;
	}
	
	@RequestMapping(value = "/addContractInfo1", method = RequestMethod.POST)
	@ResponseBody
	public Map addAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = contractInfoSer.addContractInfo1(request);
			if (result==1) {
				map.put("statusCode", "200");
				map.put("formId", "viewContractUpdateList");
			}else{
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;

	}
	
	/**
	 * 批量删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteContractInfo1")
	@ResponseBody
	public Map<String, Object> delAttendanceApplyInfoForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = this.contractInfoSer.deleteContractInfo1(request);
			if (result == 1) {
				map.put("message", "删除成功");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewUpdateContractInfo");
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
	 * 跳转到修改合同页面(Jump to update contract page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	/**
	 * *
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yorio youjia@ait.net.cn
	 * @date Jul 11, 2013 6:41:01 PM
	 * @version V1.0
	 */
	@RequestMapping(value = "/updateContract")
	public ModelAndView updateContract(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String contractNo = request.getParameter("NO");
		String personId = request.getParameter("PERSON_ID");
		String KINDS = request.getParameter("KINDS");
		modelMap.put("CONTRACT_NO", contractNo);
		modelMap.put("KINDS", KINDS);
		modelMap.put("PERSON_ID", personId);
		modelMap.put("contractTypeList", empInfoSer.getCodeList("123199",
				request));

		Map conractInfo = (LinkedHashMap) this.contractInfoSer
				.getContractForUpdate(request);
		modelMap.put("contract", conractInfo);

		// 세부계약유형 목록 확보 필요...
		logger.debug("jjy:::contractInfo:::" + conractInfo);
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMP_TYPE_CODE", conractInfo.get("EMP_TYPE_CODE"));
		paramMap.put("interLanguage", admin.getLanguage());
		List getRenLiAndQiYueList = contractInfoDao
				.getRenLiAndQiYueList(paramMap);
		modelMap.put("detailList", getRenLiAndQiYueList);
		logger.debug("jjy:::modelMap2:::" + modelMap);

		return new ModelAndView("/hrm/contractInfo/updateContract", modelMap);
	}

	/**
	 * 签订合同(sign a contract)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewContractByInsert")
	public ModelAndView viewContractByInsertList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List ContractByInsertList = this.contractInfoSer
				.getContractByInsertForGrid(request);
		int ContractByInsertCnt = this.contractInfoSer
				.getContractByInsertCntForSearch(request);

		modelMap.put("itemList", ContractByInsertList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, ContractByInsertCnt);

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2559"));
		// logger.debug("jjy:::viewContractByInsertList:::modelMap:::"+modelMap);
		return new ModelAndView("/hrm/contractInfo/viewContractByInsert",
				modelMap);
	}

	/**
	 * 签订合同导出Excel
	 * 
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewContractByInsertForSearchExcel")
	public ModelAndView viewContractByInsertForSearchExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List ContractByInsertList = this.contractInfoSer
				.getContractByInsertForSearchExcel(request);
		modelMap.put("itemList", ContractByInsertList);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView(
				"/hrm/contractInfo/viewContractByInsertForSearchExcel",
				modelMap);
	}

	/**
	 * 跳转到添加签订合同页面(Jump to add sign a contract page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateContractByInsert")
	public ModelAndView updateContractByInsert(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("NO");

		modelMap.put("PERSONID", personId);
		// 123199契约类型
		empInfoSer.getCodeList("123199", request);
		modelMap.put("contractTypeList", this.contractInfoSer.getCodeList(
				"123199", request));

		Map perInfo = (LinkedHashMap) this.contractInfoSer
				.getPersonalInfoForContract(request);
		modelMap.put("personInfo", perInfo);
		// modelMap.put("personInfo",
		// this.contractInfoSer.getPersonalInfoForContract(request));

		// 세부계약유형 목록 확보 필요...
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMP_TYPE_CODE", perInfo.get("EMP_TYPE_CODE"));
		paramMap.put("interLanguage", admin.getLanguage());
		List getRenLiAndQiYueList = contractInfoDao
				.getRenLiAndQiYueList(paramMap);
		modelMap.put("detailList", getRenLiAndQiYueList);
		// logger.debug("jjy:::modelMap2:::"+modelMap);

		return new ModelAndView("/hrm/contractInfo/updateContractByInsert",
				modelMap);
	}

	/**
	 * 执行添加签订合同(Executive add sign the contract)
	 * 
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateContractByInsertInfo")
	@ResponseBody
	public Map updateContractByInsert(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.updateContract(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("navTabId", "hr0302");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return jo;
	}

	/**
	 * 续签合同(Contract Renewal)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewExpiredContract")
	public ModelAndView viewExpiredContractList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List renewContractList = this.contractInfoSer.getRenewContractForGrid(request);
		
		modelMap.put("itemList", renewContractList);
		
		modelMap.put("htlx" , JsonUtil.writeInternal(empInfoSer.getCodeList("123199", request)));  //合同类型
		//modelMap.put("htxz" , JsonUtil.writeInternal(empInfoSer.getCodeList("14016095", request)));  //合同性质
		modelMap.put("gongshi" , JsonUtil.writeInternal(empInfoSer.getCodeList("123200", request)));  //工时
		return new ModelAndView("/hrm/contractInfo/viewExpiredContract",
				modelMap);
	}
	/**
	 *身份证到期(Contract Renewal)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewExpiredIdCard")
	public ModelAndView viewExpiredIdCardList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List expiredIdCardList = this.contractInfoSer.getExpiredIdCardList(request);
		
		modelMap.put("expiredIdCardList", expiredIdCardList);
		
		return new ModelAndView("/hrm/contractInfo/viewExpiredIdCard",
				modelMap);
	}
	
	/**
	 *转正提醒
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBecomeRegularWarn")
	public ModelAndView getBecomeRegularWarn(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List becomeRegularList = this.contractInfoSer.getBecomeRegularWarn(request);
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
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
		}
		modelMap.put("becomeRegularList", becomeRegularList);
		
		return new ModelAndView("/hrm/contractInfo/viewBecomeRegularWarn",
				modelMap);
	}
	
	/**
	 * 部门长转正提醒评价
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertBecomeRegularEvaluate")
	@ResponseBody
	public Map insertBecomeRegularEvaluate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.insertBecomeRegularEvaluate(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("formId", "viewExpiredContractInfo");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
			jo.put("formId", "viewExpiredContractInfo");
		}
		return jo;
	}
	/**
	 * 部门长转正提醒评价
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/becomeRegularEvaluate")
	public ModelAndView becomeRegularEvaluate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List becomeRegularList = this.contractInfoSer.getBecomeRegularWarn(request);

		modelMap.put("becomeRegularList", becomeRegularList);
		
		return new ModelAndView("/hrm/contractInfo/becomeRegularEvaluate",
				modelMap);
	}
	/**
	 *工资未导入(Contract Renewal)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaNotImport")
	public ModelAndView viewPaNotImportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List notImportList = this.contractInfoSer.getPaNotImportList(request);
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
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
		}
		modelMap.put("notImportList", notImportList);
		
		return new ModelAndView("/hrm/contractInfo/viewPaNotImport",
				modelMap);
	}

	/**
	 * 续签合同导出Excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewExpiredContractForSearchExcel")
	public ModelAndView viewExpiredContractForSearchExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List renewContractList = this.contractInfoSer
				.getExpiredContractForSearchExcel(request);
		modelMap.put("itemList", renewContractList);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView(
				"/hrm/contractInfo/viewExpiredContractForSearchExcel", modelMap);
	}

	/**
	 * 跳转到续签订合同页面(Jump to sign a contract renewal page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateRenewContractByInsert")
	public ModelAndView updateRenewContractByInsert(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("NO");
		modelMap.put("PERSONID", personId);
		Map perInfo = (LinkedHashMap) this.contractInfoSer
				.getPersonalInfoForContract(request);
		modelMap.put("personInfo", perInfo);

		modelMap.put("contractTypeList", empInfoSer.getCodeList("123199",
				request));

		// 세부계약유형 목록 확보 필요...
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMP_TYPE_CODE", perInfo.get("EMP_TYPE_CODE"));
		paramMap.put("interLanguage", admin.getLanguage());
		List getRenLiAndQiYueList = contractInfoDao
				.getRenLiAndQiYueList(paramMap);
		modelMap.put("detailList", getRenLiAndQiYueList);
		// logger.debug("jjy:::modelMap2:::"+modelMap);

		return new ModelAndView(
				"/hrm/contractInfo/updateRenewContractByInsert", modelMap);
	}

	/**
	 * 执行添加续签合同(Executive add sign the contract)
	 * 
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateRenewContractByInsertInfo")
	@ResponseBody
	public Map updateRenewContractByInsert(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.insertContract(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("formId", "viewExpiredContractInfo");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
			jo.put("formId", "viewExpiredContractInfo");
		}
		return jo;
	}

	/**
	 * 详细人力区分 关联 详细合同类型
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-10-14 下午4:46:40
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRenLiAndQiYue")
	@ResponseBody
	public Map getRenLiAndQiYue(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List getRenLiAndQiYueList = this.contractInfoSer
				.getRenLiAndQiYueList(request);

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < getRenLiAndQiYueList.size(); i++) {
			map
					.put((String) ((Map) getRenLiAndQiYueList.get(i))
							.get("CODE_NO"),
							((Map) getRenLiAndQiYueList.get(i))
									.get("CODE_NAME"));
		}
		return map;

	}

	/**
	 * 签订合同
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author weizhengchen@ait.net.cn
	 * @date 2013-10-14 下午4:46:40
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertContract")
	@ResponseBody
	public Map insertContract(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.insertContract(request);

		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			jo.put("formId", "viewNOContractInfo");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
			jo.put("formId", "viewNOContractInfo");
		}
		return jo;
	}

	/**
	 * 续签审批
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewExpiredContractApproveList")
	public ModelAndView viewExpiredContractApproveList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List expiredContractApproveList = this.contractInfoSer
				.expiredContractApprove(request);
		int expiredContractApproveCnt = this.contractInfoSer
				.expiredContractApproveCnt(request);
		modelMap.put("itemList", expiredContractApproveList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, expiredContractApproveCnt);

		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView(
				"/hrm/contractInfo/viewExpiredContractApproveList", modelMap);
	}

	/**
	 * 签订合同
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author weizhengchen@ait.net.cn
	 * @date 2013-10-14 下午4:46:40
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/approveExpiredContract")
	@ResponseBody
	public Map approveExpiredContract(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = this.contractInfoSer.approveExpiredContract(request);

		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"hr.contract.title.shenpi.chenggong", request));// 保存成功
			jo.put("navTabId", "hr0306");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"hr.contract.title.shenpi.cshibai", request));// 保存失败
		}
		return jo;
	}

	/**
	 * 修改兼卖产品信息(Modify trade information)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkContractNo")
	@ResponseBody
	public Map checkContractNo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.contractInfoSer.checkContractNo(request);
		if (result == 1) {
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 营业员个人评价结果 列表的导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewContractInfoExcel")
	public void viewContractInfoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		List aliasNameList = new ArrayList();
		// 提取导出数据列表
		aliasNameList.add("合同次数");
		aliasNameList.add("合同编号");
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		aliasNameList.add("工作地区");
		aliasNameList.add("合同类型");
		aliasNameList.add("合同版本");
		aliasNameList.add("起始日期");
		aliasNameList.add("终止日期");
		aliasNameList.add("签订意见");
		aliasNameList.add("审批情况");
		List aliasValueList = this.contractInfoSer
				.getContractInfoListForSearchExcel(request);
		String[] columns = { "TOTAL_PERIOD", "CONTRACT_NUMBER", "EMPID",
				"LOCAL_NAME", "DEPARTMENT_NAME", "WORK_AREA_NAME",
				"CONTRACT_TYPE", "CONTRACT_VERSION", "CONTRACTSTARTDATE",
				"CONTRACTENDDATE", "REMARK", "ACTIVITY_NAME" };

		String name = "contractInfoList";
		this.excelUtilSer.exportExcelByNamePwd(request, response, modelMap,
				aliasValueList, aliasNameList, columns, name, searchMap);
	}

	/**
	 * 合同模板下载
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadExcelNoContract")
	public void downloadExcelTemplate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = contractInfoSer.getNotSignContractInfo(request,
				aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	/**
	 * 合同导入数据导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadExcelTempTemplate")
	public void downloadExcelTempTemplate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = contractInfoSer.getNotSignContractTempInfo(request,
				aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	/**
	 * 续签合同模板下载
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadExcelContract")
	public void downloadExcelContract(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = contractInfoSer.getContractInfo(request, aliasNameList,
				list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	/**
	 * 续签审批
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelContractDataList")
	public ModelAndView viewImportExcelContractDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List contractTempList = this.contractInfoSer
				.getContractTempList(request);
		int contractTempCnt = this.contractInfoSer.getContractTempCnt(request,
				"T");
		int errorCnt = this.contractInfoSer.getContractTempCnt(request, "E");

		modelMap.put("contractTempList", contractTempList);
		modelMap.put("contractTempCnt", contractTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", contractTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, contractTempCnt);
		return new ModelAndView(
				"/hrm/contractInfo/viewImportExcelContractDataList", modelMap);
	}

	/**
	 * 续签审批
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelContractDataList2")
	public ModelAndView viewImportExcelContractData2List(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List contractTempList = this.contractInfoSer
				.getContractTempList(request);
		int contractTempCnt = this.contractInfoSer.getContractTempCnt(request,
				"T");
		int errorCnt = this.contractInfoSer.getContractTempCnt(request, "E");

		modelMap.put("contractTempList", contractTempList);
		modelMap.put("contractTempCnt", contractTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", contractTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, contractTempCnt);
		return new ModelAndView(
				"/hrm/contractInfo/viewImportExcelContractDataList2", modelMap);
	}

	/**
	 * 未签合同excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelContractData")
	@ResponseBody
	public Map submitImportExcelContractData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg = this.contractInfoSer
				.submitImportExcelContractData(request);
		if ("OK".equals(msg)) {
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");// 保存成功
			jo.put("navTabId", "hr0305");
			jo.put("callbackType", "closeCurrent");
		} else {
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");// 保存失败
		}
		return jo;
	}

	/**
	 * 续签合同excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelContractData2")
	@ResponseBody
	public Map submitImportExcelContractData2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg = this.contractInfoSer
				.submitImportExcelContractData2(request);
		if ("OK".equals(msg)) {
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");// 保存成功
			jo.put("navTabId", "hr0301");
			jo.put("callbackType", "closeCurrent");
		} else {
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");// 保存失败
		}
		return jo;
	}
	

	/**
	 * 合同变更履历查询 (Contract inquires)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeContractHistoryList")
	public ModelAndView viewChangeContractHistoryList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag !=null && !"".equals(firstFlag)) {
			List contractInfoList = this.contractInfoSer.viewChangeContractHistoryList(request);
			modelMap.put("itemList", contractInfoList);
		}
		return new ModelAndView("/hrm/contractInfo/viewChangeContractHistoryList",
				modelMap);
	}
}
