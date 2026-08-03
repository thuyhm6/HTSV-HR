package com.ait.hrm.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.ait.ess.service.AffirmApplySer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.hrm.dao.HrmDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.TransactionViewSer;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.PostSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: TransferOrderCtroller.java
 * @Description:
 * @Create date: 2012-2-22 下午03:36:56
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/transferOrder")
public class TransferOrderCtroller {

	Logger logger = Logger.getLogger(TransferOrderCtroller.class);
	
	@Autowired
	private PostSer postSer;
	@Autowired
	private TransferOrderSer transferOrderSer;
	@Autowired
	private HrmDao hrmDao;	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	
	@Autowired
	private TransferOrderDao transferOrderDao;	
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private CycleSer  cycleSer;
	@Autowired
	private TransactionViewSer transactionViewSer;

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private AffirmApplySer affirmApplySer;
	@Autowired
	private InfoApplyLeaveSer infoApplySerOt;

	/**
	 * 跳转到入职发令页面（view Hire）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewHire")
	public ModelAndView viewHire(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("cpnyId", admin.getCpnyId());
		if(admin.getCpnyId().equals("TSTO")){
			paramMap.put("SHIFT_NO", "CH_W2");
		}
		paramMap.put("nationalCode", admin.getOperationCodeNo());//默认国籍	
		//List getZhiDengList=this.transferOrderSer.getCodeList("3683",request);//获取职等list 职等: 3683 
		//List employmentTypeList=this.transferOrderSer.getCodeList("123195",request);//123195雇佣类型
		//List whetherForeignersList=this.transferOrderSer.getCodeList("123212",request);//是否外国人123212
		//List anmeldenProvinceCodeList=this.transferOrderSer.getCodeList("4602",request);//户口所在地 省份 4602
		//List whetherCommunList=this.transferOrderSer.getCodeList("123208",request);//是否党员 123208
		//List jobTypeList=this.transferOrderSer.getCodeList("123228",request);//是否党员 123228
		//List getRecSourceList=this.transferOrderSer.getCodeList("3306",request);//雇佣路径3306
		//List degreeCodeList=this.transferOrderSer.getCodeList("1665",request);//学历1665
		//List subjectClassifyList=this.transferOrderSer.getCodeList("123412",request);//专业分类123412
		//List siteProvinceList=this.transferOrderSer.getCodeList("4602",request);//所在省份774 -> 소속지구4602
		modelMap.put("personInfo", paramMap);
		modelMap.put("empTypeList", empInfoSer.getEmpTypeList(request));
		//学历
		//modelMap.put("degreeCodeList",degreeCodeList);
		//modelMap.put("subjectClassifyList",subjectClassifyList);//专业分类
		//modelMap.put("siteProvinceList",siteProvinceList);//所在地省
		//modelMap.put("anmeldenProvinceCodeList",anmeldenProvinceCodeList);
		//modelMap.put("whetherCommunList",whetherCommunList);
		//modelMap.put("employmentTypeList",employmentTypeList);
		//modelMap.put("whetherForeignersList",whetherForeignersList);
		//modelMap.put("getZhiDengList", getZhiDengList);
		//modelMap.put("jobTypeList", jobTypeList);
		//modelMap.put("getRecSourceList", getRecSourceList);
		List codeList = empInfoSer.getCodeList("211424",request);
		modelMap.put("codeList", codeList);
		return new ModelAndView("/hrm/transferOrder/viewHire", modelMap);
	}

	/**
	 * 提交入职发令（save Transfer Order Hire）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveTransferOrderHire")
	@ResponseBody
	public Map<String, Object> saveTransferOrderHire(HttpServletRequest request)
			throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List retrunList = new ArrayList();
		// 如果入职人员的入职类型为再入职，则判断此人的员工状态必须为离职 1375表示离职 12281表示再入职
		
		String joinTypeCode = request.getParameter("JOIN_TYPE_CODE");//入职类型
		joinTypeCode = (joinTypeCode==null)?"":joinTypeCode;
		//NATIONALITY_CODE国籍
//		String nationality = request.getParameter("NATIONALITY_CODE")!=null?request.getParameter("NATIONALITY_CODE").toString():"";
		if(Integer.parseInt(this.checkedIdcardNoReqStatus(request).get("IDCARDCOUNT").toString())>0){
			map.put("statusCode", "300");
			map.put("message","此身份证号已经在申请审批中,不能重复申请！");
			return map;
		}
		
		if(joinTypeCode.equals("12280")){
			//国籍是871中国，873中国台湾，874中国香港时验证身份证号是否重复。
//			if("871".equals(nationality) || "873".equals(nationality) || "874".equals(nationality)){
				if(Integer.parseInt(this.checkedIdcardNo(request).get("IDCARDCOUNT").toString())>0){
					map.put("statusCode", "300");
					map.put("message","此身份证号已经存在,因此不能是首次入职！");
					return map;
				}
			//国籍是外国人时验证外国人身份证号或者护照号 (08-23 页面取消字段 此处 不验证)
//			}
//			else{
//				if(Integer.parseInt(this.checkedPassportNo(request).get("PASSPORTCOUNT").toString())>0){
//					map.put("statusCode", "300");
//					map.put("message","此外国人身份证号或者护照号已经存在,因此不能是首次入职！");
//					return map;
//				}
//			}
		}
		if (joinTypeCode.equals("12281")) {
			retrunList = this.transferOrderSer.checkIdcardNoAgains(request);			
			if(retrunList.size() == 0){
				map.put("statusCode", "300");
				map.put("message", "未查到此人信息，所以无法再入职！");
				return map;
			}else{
				LinkedHashMap m = (LinkedHashMap) retrunList.get(0);
				if (!m.get("STATUS_CODE").equals("1375")) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.noinhire", request));// 此人是在职状态，因此不能做再入职发令
					return map;
				}
			}			
		}
		int tempEmpid = transferOrderSer.getValidationEmpid(request);//工号是否被占用 新入职 这里 工号NULL
		if (tempEmpid == 0) {
			int result = transferOrderSer.saveTransferOrderHire(request);//保存
			if (result == 1) {
				//判断此法人是否需要入职发令决裁 0为不需要 1为需要
//				if (transferOrderSer.getParamInfoValue(request) != 1) {
//					//判断此法人是否入职发令成功后 弹出 薪资录入页面  0=否 1=是
//					if (transferOrderSer.getPopMarkFlag(request) == 1 ) {
//						map.put("popMark",1);
//						map.put("navTabId", "hr0201");
//						Map paramMap = ObjectBindUtil.getRequestParamData(request);
//						map.put("IDCARD_NO", paramMap.get("IDCARD_NO"));//身份证
//						map.put("FOREIGNER_IDCARD_NO", paramMap.get("FOREIGNER_IDCARD_NO"));//外国人身份证号码(08-23页面上没有该字段)
//						map.put("PASSPORT_NO", paramMap.get("PASSPORT_NO"));//护照号(08-23页面上没有该字段)
//						map.put("JOIN_COMPANY_DATE", paramMap.get("JOIN_COMPANY_DATE"));//入职日期
//					}else{
//						map.put("popMark",0);
//					}
//						
//				}
				
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0504");
				
				
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者

			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.empid_occpuy", request));// 入职发令失败,工号已经被占用
		}

		return map;
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewUpgrade")
	public ModelAndView viewUpgradeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List upGradeList = transferOrderSer.getViewUpgradeList(request);
		int upGradeCnt = transferOrderSer.getViewUpgradeCnt(request);

		Map map=new LinkedHashMap();
		map.put("PARENT_CODE_NO","1365");
		map.put("language",Messages.getLanguage(request));
		map.put("interLanguage",admin.getLanguage());
		map.put("CPNY_ID",admin.getCpnyId());
		
		List transCodeList = basicMaintenanceDao.getParamCodeListByCpnyID(map, -1, -1) ;
		
		List positionList = this.transferOrderSer.getPositionList(request);
		List dutyList = this.transferOrderSer.getDutyList(request);
		List postGradeList = this.transferOrderSer.getPostGradeList(request);
		List workAreaList = this.transferOrderSer.getWorkAreaList(request);

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		List deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap);
		String deptJson = JsonUtil.writeInternal(deptList);

		modelMap.put("deptJson", deptJson);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("transCodeList", transCodeList);
		modelMap.put("positionList", positionList);
		modelMap.put("dutyList", dutyList);
		modelMap.put("workAreaList", workAreaList);
		modelMap.put("postGradeList", postGradeList);
		modelMap.put("upGradeList", upGradeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, upGradeCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewUpgrade", modelMap);
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPostNoByPostGradeNo")
	@ResponseBody
	public List getPostNoByPostGradeNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List postList = this.transferOrderSer.getPostListByPostGradeNo(request);

		return postList;
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDutyNoByPostGradeNo")
	@ResponseBody
	public List getDutyNoByPostGradeNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List dutyList = this.transferOrderSer.getDutyListByPostGradeNo(request);

		return dutyList;
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getWorkAreaByDept")
	@ResponseBody
	public Map getWorkAreaByDept(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List workAreaList = this.transferOrderSer.getWorkAreaByDept(request);
		for (int j = 0; j < workAreaList.size(); j++) {
			map = (Map) workAreaList.get(j);
			map.put("WORK_AREA", map.get("WORK_AREA"));
			map.put("WORKAREA_NAME", map.get("WORKAREA_NAME"));
		}
		return map;
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getIdCard")
	@ResponseBody
	public Map getIdCard(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = transferOrderSer.getValidationIdCardNo(request);
		if (result != 0) {
			map.put("JOIN_TYPE_CODE", "12281");
		}
		return map;
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSocialSecurityAreaByWorkArea")
	@ResponseBody
	public Map getSocialSecurityAreaByWorkArea(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List linkageList = this.transferOrderSer
				.getSocialSecurityAreaByWorkArea(request);
		map.put("TAX_AREA", ((Map) linkageList.get(0)).get("NO"));
		map.put("ENTRY_AREA", ((Map) linkageList.get(0)).get("NO"));
		map.put("SOCIAL_SECURITY_AREA", ((Map) linkageList.get(0)).get("NO"));
		return map;
	}

	/**
	 * 跳转到调动发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRecSourceDetailByRecSource")
	@ResponseBody
	public Map getRecSourceDetailByRecSource(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		List linkageList = this.transferOrderSer
		.getRecSourceDetailByRecSource(request);
		for(int i=0;i<linkageList.size();i++){
			map.put((String)((Map) linkageList.get(i)).get("WORKAREA_NAME"), ((Map) linkageList.get(i)).get("WORK_AREA"));
		}
		return map;
	}
	

	/**
	 * 跳转到转正发令页面（view TransferNormal）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTransferNormal")
	public ModelAndView viewTransferNormalList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List transferNormalList = transferOrderSer
				.getTransferNormalList(request);
		int transferNormalCnt = transferOrderSer.getTransferNormalCnt(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("transferNormalList", transferNormalList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, transferNormalCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("/hrm/transferOrder/viewTransferNormal",
				modelMap);
	}

	/**
	 * 提交转正发令（save Transfer Order Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTransferNormal")
	@ResponseBody
	public Map<String, Object> saveTransferNormal(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSaveTransferNormal(request) == 0) {
			int result = transferOrderSer.saveTransferNormal(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0210");
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}
		return map;
	}

	/**
	 * 跳转到晋升/降职发令页面（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTransferPromote")
	public ModelAndView viewTransferPromoteList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List transferPromoteList = transferOrderSer
				.getTransferPromoteList(request);
		int transferPromoteCnt = transferOrderSer
				.getTransferPromoteCnt(request);

		List positionList = this.transferOrderSer.getPositionList(request);
		List dutyList = this.transferOrderSer.getDutyList(request);
		List postGradeList = this.transferOrderSer.getPostGradeList(request);
		List postList = this.transferOrderSer.getPostList(request);
		List workAreaList = this.transferOrderSer.getWorkAreaList(request);

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		List deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap);
		String deptJson = JsonUtil.writeInternal(deptList);

		modelMap.put("deptJson", deptJson);
		modelMap.put("positionList", positionList);
		modelMap.put("dutyList", dutyList);
		modelMap.put("workAreaList", workAreaList);
		modelMap.put("postGradeList", postGradeList);
		modelMap.put("postList", postList);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("transferPromoteList", transferPromoteList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, transferPromoteCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewTransferPromote",
				modelMap);
	}

	/**
	 * 提交晋升/降职发令（save Transfer Order Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTransferPromote")
	@ResponseBody
	public Map<String, Object> saveTransferPromote(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSaveTransferPromote(request) == 0) {
			//String empid = transferOrderSer.checkSaveTransferPormote(request);
			//if (empid == "" && empid.length() <= 0) {
				int result = transferOrderSer.saveTransferPromote(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.dekreti_success", request));// 发令成功
					map.put("navTabId", "hr0211");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.dekreti_fail", request));// 发令失败
				}
//			} else {
//				map.put("statusCode", "300");
//				map.put("message",
//						TipMessage.getTipMessage(
//								"hr.alert.message.in_this_start_date", request)
//								+ empid
//								+ TipMessage
//										.getTipMessage(
//												"hr.alert.message.have_about_dekreti_change_start_date",
//												request));
//				// "在此生效日期 工号："+ empid +" 有相关发令,发令失败！建议更换生效日期再重试
//			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.transorderunimp", request));// 此人已有过相关法令(未生效法令或此生效日期已生效发令)

		}
		return map;

	}

	/**
	 * 员工搜索
	 */
	@RequestMapping(value = "/searchEmp")
	public ModelAndView searchEmp(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		modelMap.put("type", request.getParameter("type"));
		return new ModelAndView("/hrm/transferOrder/searchEmp", modelMap);
	}

	/**
	 * 搜索派遣进行结束发令
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDispatchInfo")
	@ResponseBody
	public Map getDispatchInfo(HttpServletRequest request) throws Exception {
		Map temp = transferOrderSer.getDispatchInfo(request);
		return temp;
	}

	/**
	 * 跳转到离职发令页面（view Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResign")
	public ModelAndView viewResignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		if(!paramMap.containsKey("ORDERDATEF")){
			paramMap.put("ORDERDATEF", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("ORDERDATET", DateUtil.getCurrentMonthLastDayStr());			
		}	
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("EMPID", paramMap.get("dwz.person.empId"));	
		
		modelMap.put("searchMap", paramMap);

        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		List resignList = transferOrderSer.getViewResignList(paramMap, request);
    		int resignListCnt = transferOrderSer.getViewResignCnt(paramMap, request);
    		modelMap.put("resignList", resignList);		
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, resignListCnt);
        }
        
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278687")) ;
		return new ModelAndView("/hrm/transferOrder/viewResign", modelMap);
	}
	
	/**
	 * 添加离职发令页面查询（view Add Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignAddList")
	public ModelAndView viewResignAddList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("MGT_SYSTEM", "C");  //G：取G系统管理的员工，C：取C系统管理的员工
		paramMap.put("TYPE", "resign");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		
		List resignEmpList = transferOrderSer.getViewTempEmpList(paramMap, request);
		int resignEmpListCnt = transferOrderSer.getViewTempEmpCnt(paramMap, request);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("resignEmpList", resignEmpList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, resignEmpListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278687")) ;
		return new ModelAndView("/hrm/transferOrder/viewResignAddList", modelMap);
	}

	/**
	 * 保存离职发令（save Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveResignation")
	@ResponseBody
	public Map<String, Object> saveResignation(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = transferOrderSer.saveResignation(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功！");
			map.put("navTabId", "hr0206");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		} 
		return map;
	}
	
	/**
	 * 提交离职发令申请
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmResignation")
	@ResponseBody
	public Map<String, Object> confirmResignation(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map rtn = transferOrderSer.confirmResignation(request);
		int result = (Integer)rtn.get("RET");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_success", request));// 发令成功
			map.put("navTabId", "hr0206");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
		} else if (result == 3){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_fail", request));// 发令失败
		} 
		return map;
	}

	/**
	 * 跳转到兼职发令页面（view Plurality）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPlurality")
	public ModelAndView viewPluralityList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List pluralityList = transferOrderSer.getViewPluralityList(request);
		int pluralityListCnt = transferOrderSer.getViewPluralityCnt(request);

		List positionList = this.transferOrderSer.getPositionList(request);// 职岗位LIST
		List dutyList = this.transferOrderSer.getDutyList(request);// 职责LIST

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		List deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap);
		String deptJson = JsonUtil.writeInternal(deptList);

		modelMap.put("deptJson", deptJson);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("pluralityList", pluralityList);
		modelMap.put("positionList", positionList);
		modelMap.put("dutyList", dutyList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, pluralityListCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));

		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewPlurality", modelMap);
	}

	/**
	 * 提交兼职发令（save Plurality）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePlurality")
	@ResponseBody
	public Map<String, Object> savePlurality(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSavePlurality(request) == 0) {
			int result = transferOrderSer.savePlurality(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0204");
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}
		return map;
	}

	/**
	 * 跳转到停职发令页面（view Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSuspend")
	public ModelAndView viewSuspendList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List suspendList = transferOrderSer.getSuspendList(request);
		int suspendListCnt = transferOrderSer.getSuspendListCnt(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("suspendList", suspendList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, suspendListCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewSuspend", modelMap);
	}

	/**
	 * 提交停职发令（save suspend）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSuspend")
	@ResponseBody
	public Map<String, Object> saveSuspend(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSaveSuspend(request) == 0) {
			int result = transferOrderSer.saveSuspend(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0204");
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}
		return map;
	}

	/**
	 * 跳转到奖励发令页面（view reward）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHortation")
	public ModelAndView viewHortationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List rewardList = transferOrderSer.getRewardList(request);
		int rewardListCnt = transferOrderSer.getRewardListCnt(request);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("rewardList", rewardList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, rewardListCnt);

		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewHortation", modelMap);
	}
	
	/**
	 * 根据输入的员工编号查询指定员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getEmployeeByEmpId")
	@ResponseBody
	public Map getEmployeeByEmpId(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List deptNoList=this.transferOrderSer.getEmployeeByEmpId(request);
	 	map.put("perCnt", deptNoList.size());
		if(deptNoList.size()==1){
			map.put("empinfo", deptNoList.get(0));
		}
		if(deptNoList.size()>1){
			map.remove("empinfo");
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
	@RequestMapping(value = "/viewTransferOrderEmpList")
	public ModelAndView viewTransferOrderEmpList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("sortNameNoList",this.transferOrderSer.getOrderParmList(request,"18706"));
		modelMap.put("empList",this.transferOrderSer.getTransferOrderEmpList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transferOrderSer.getTransferOrderEmpListCnt(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("FLAG", request.getParameter("FLAG"));
		modelMap.put("ROW", request.getParameter("ROW"));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/hrm/transferOrder/viewTransferOrderEmpList",modelMap);		
	} 
	
	/**
	 *  保存“奖励”发令信息(临时储存)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/storeReward")
	@ResponseBody
	public Map<String,Object> storeReward(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = transferOrderSer.storeReward(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"zxc.hr.alert.message.SAVE_SUCCESS", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"zxc.hr.alert.message.SAVE_FAILED", request));// 保存失败
		} 
		return map;
	}
	
	/**
	 * 提交奖励发令（save reward）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveReward")
	@ResponseBody
	public Map<String, Object> saveReward(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSaveReward(request) == 0) {
			int result = transferOrderSer.saveReward(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0209");
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.existing_reward_in_this_time", request));// 该时间此人受过奖励,请从新修改奖励日期再进行操作
		}
		return map;
	}

	/**
	 * 跳转到惩戒发令页面（view punishment）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPunishMent")
	public ModelAndView viewPunishMentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List punishMentList = transferOrderSer.getPunishMentList(request);
		int punishMentListCnt = transferOrderSer.getPunishMentListCnt(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("punishMentList", punishMentList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, punishMentListCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewPunishMent", modelMap);
	}
	
	/**
	 * 保存"惩罚"发令信息(临时储存)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/storePunishment")
	@ResponseBody
	public Map<String,Object> storePunishment(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = transferOrderSer.storePunishment(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"zxc.hr.alert.message.SAVE_SUCCESS", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"zxc.hr.alert.message.SAVE_FAILED", request));// 保存失败
		} 
		return map;
	}
	
	
	/**
	 * 提交惩戒发令（save punishMent）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePunishMent")
	@ResponseBody
	public Map<String, Object> savePunishMent(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSavePunishMent(request) == 0) {
			int result = transferOrderSer.savePunishMent(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0208");
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.disciplinary_type_or_time_conflict",
					request));// 惩戒类型或时间冲突,请重修改再次进行发令
		}
		return map;
	}

	/**
	 * 跳转到薪资调整发令页面（view Payrise）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPayrise")
	public ModelAndView viewPayriseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List payriseList = transferOrderSer.getPayriseList(request);
		int payriseListCnt = transferOrderSer.getPayriseListCnt(request);

		List paBasicItemList = transferOrderSer.paBasicItemList(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("payriseList", payriseList);
		modelMap.put("paBasicItemList", paBasicItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, payriseListCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));

		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewPayrise", modelMap);
	}

	/**
	 * 跳转到薪资调整发令页面（view payrise）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getReturnValueByItemNo")
	@ResponseBody
	public Map getReturnValueByItemNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List returnValueList = this.transferOrderSer
				.getReturnValueByItemNo(request);
		map.put("returnValue",
				((Map) returnValueList.get(0)).get("RETURN_VALUE"));
		return map;
	}

	/**
	 * 提交薪资调整发令（save payrise）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePayrise")
	@ResponseBody
	public Map<String, Object> savePayrise(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if (transferOrderSer.checkSavePayrise(request) == 0) {

			if (transferOrderSer.checkSavePayriseByStartDate(request) > 0) {
				int result = transferOrderSer.savePayrise(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.dekreti_success", request));// 发令成功
					map.put("navTabId", "hr0214");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.dekreti_fail", request));// 发令失败
				}
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.checkPayrise", request));// 当前生效日期之后有过调整，因此不能发令
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}

		return map;
	}

	/**
	 * 跳转到代理发令页面（view agent）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAgent")
	public ModelAndView viewAgentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List agentList = transferOrderSer.getAgentList(request);
		int agentCnt = transferOrderSer.getAgentCnt(request);

		List positionList = this.transferOrderSer.getPositionList(request);
		List dutyList = this.transferOrderSer.getDutyList(request);
		List postGradeList = this.transferOrderSer.getPostGradeList(request);
		List workAreaList = this.transferOrderSer.getWorkAreaList(request);

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		List deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap);
		String deptJson = JsonUtil.writeInternal(deptList);

		modelMap.put("deptJson", deptJson);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("positionList", positionList);
		modelMap.put("dutyList", dutyList);
		modelMap.put("workAreaList", workAreaList);
		modelMap.put("postGradeList", postGradeList);

		modelMap.put("agentList", agentList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, agentCnt);

		try {
			if (request.getParameter("eids") != null
					|| request.getParameter("eids").length() > 0) {
				modelMap.put("eids", request.getParameter("eids"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new ModelAndView("/hrm/transferOrder/viewAgent", modelMap);
	}

	/**
	 * 提交代理发令（save Transfer Order agent）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTransferOrderAgent")
	@ResponseBody
	public Map<String, Object> saveTransferOrderAgent(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (transferOrderSer.checkSaveTransferOrderAgent(request) == 0) {
			String empid = transferOrderSer.checkSaveTransferAgent(request);
			if (empid == "" && empid.length() <= 0) {

				int result = transferOrderSer.saveTransferOrderAgent(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.dekreti_success", request));// 发令成功
					map.put("navTabId", "hr0216");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
				} else if (result == 3) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.check_agent_start_date", request));// 取消代理生效日期不能早于代理发令生效日期

				} else if (result == 4) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.have_cancle_the_agent", request));// 此人已有取消代理发令，因此不能再做取消代理发令

				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.dekreti_fail", request));// 发令失败
				}

			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage(
								"hr.alert.message.in_this_start_date", request)
								+ empid
								+ TipMessage
										.getTipMessage(
												"hr.alert.message.have_about_dekreti_change_start_date",
												request));
				// "在此生效日期 工号："+ empid +" 有相关发令,发令失败！建议更换生效日期再重试
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}
		return map;
	}

	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpSearchList")
	public ModelAndView viewEmpSearchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("empList", this.transferOrderSer.getEmpSearchList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,
				this.transferOrderSer.getEmpSearchCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("theType", request.getParameter("theType"));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		if (request.getParameter("eidsForSearch") != null
				&& request.getParameter("eidsForSearch").length() > 0) {
			String eidsForSearch = request.getParameter("eidsForSearch");
			paramMap.put("eidsForSearch", eidsForSearch);
			paramMap.put("eidsForSearch1", eidsForSearch);
			modelMap.put("eidsForSearch", eidsForSearch);
			modelMap.put("eidsForSearch1", eidsForSearch);
		}

		if (request.getParameter("eidsForSearch1") != null
				&& request.getParameter("eidsForSearch1").length() > 0) {
			String eidsForSearch1 = request.getParameter("eidsForSearch1");
			paramMap.put("eidsForSearch", eidsForSearch1);
			paramMap.put("eidsForSearch1", eidsForSearch1);
			modelMap.put("eidsForSearch", eidsForSearch1);
			modelMap.put("eidsForSearch1", eidsForSearch1);
		}

		paramMap.put("ADMINID", admin.getAdminID());

		if (paramMap.get("eidsForSearch") != null
				&& paramMap.get("eidsForSearch").toString().length() > 0) {
			List eidList = this.transferOrderSer.getEidListForSearch(request);
			modelMap.put("eidList", eidList);
		}

		return new ModelAndView("/hrm/empinfo/viewEmpSearchList", modelMap);
	}

	/**
	 * 提交代理发令（save Transfer Order agent）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEidsForSearch")
	@ResponseBody
	public Map<String, Object> saveEidsForSearch(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		return map;
	}

	@RequestMapping(value = "/checkedIdcardNoReqStatus")
	@ResponseBody
	public Map<String, Object> checkedIdcardNoReqStatus(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int idcount = this.transferOrderSer.checkedIdcardNoReqStatus(request);
		map.put("IDCARDCOUNT", idcount);
		return map;
	}

	@RequestMapping(value = "/checkedIdcardNo")
	@ResponseBody
	public Map<String, Object> checkedIdcardNo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int idcount = this.transferOrderSer.checkIdcardNo(request);
		map.put("IDCARDCOUNT", idcount);
		return map;
	}
	
	@RequestMapping(value = "/checkedPassportNo")
	@ResponseBody
	public Map<String, Object> checkedPassportNo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int idcount = this.transferOrderSer.checkedPassportNo(request);
		map.put("PASSPORTCOUNT", idcount);
		return map;
	}
	
	/**
	 * 跳转到号奉 发令发令页面（view PayStep）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPayStep")
	public ModelAndView viewPayStepList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List payStepList = transferOrderSer.getPayStepList(request);
		int payStepListCnt = transferOrderSer.getPayStepListCnt(request);
		List haofengList = transferOrderSer.getHaoFengList(request);
		List oldGradeNoList = transferOrderSer.getOldPostGradeList2(request);
		modelMap.put("oldGradeNoList", oldGradeNoList);
		modelMap.put("haofengList", haofengList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("payStepList", payStepList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, payStepListCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));

		return new ModelAndView("/hrm/transferOrder/viewPayStep", modelMap);
	}
	
	
	@RequestMapping(value = "/determineThePop")
	@ResponseBody
	public Map<String, Object> asdasd(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String idcardNo = request.getParameter("IDCARD_NO");
		int idcount = this.transferOrderSer.checkIdcardNo(request);
		map.put("IDCARDCOUNT", idcount);
		return map;
	}
	
	/**
	 * 提交调整号俸发令（save payStep）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePayStep")
	@ResponseBody
	public Map<String, Object> savePayStep(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String empid = transferOrderSer.haveEalierPayStepData(request);
		if(!empid.equals("")){
			map.put("statusCode", "300");
			map.put("message",
					TipMessage.getTipMessage(
							"hr.alert.message.in_this_start_date", request)
							+ empid
							+ TipMessage
									.getTipMessage(
											"hr.alert.message.have_about_dekreti_change_start_date",
											request));
			// "在此生效日期 工号："+ empid +" 有相关发令,发令失败！建议更换生效日期再重试
			return map;
		}
		if (transferOrderSer.checkSavePayStep(request) == 0) {
			int result = transferOrderSer.savePayStep(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_success", request));// 发令成功
				map.put("navTabId", "hr0217");
			} else if (result == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.dekreti_fail", request));// 发令失败
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}
		return map;
	}
	
	/**
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPopMarkToHire")
	public ModelAndView viewPopMarkToHireList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List saParamItemParamList = transferOrderSer.getSaParamItemParamList(request);
		List bnParamItemParamList = transferOrderSer.getBnParamItemParamList(request);
		List inParamItemParamList = transferOrderSer.getInParamItemParamList(request);
		List paBasicItemParamList = transferOrderSer.getPaBasicItemParamList(request);
		
		
		modelMap.put("saParamItemParamList", saParamItemParamList);
		modelMap.put("bnParamItemParamList", bnParamItemParamList);
		modelMap.put("inParamItemParamList", inParamItemParamList);
		modelMap.put("paBasicItemParamList", paBasicItemParamList);
		
		modelMap.put("IDCARD_NO", request.getParameter("IDCARD_NO"));
		modelMap.put("FOREIGNER_IDCARD_NO", request.getParameter("FOREIGNER_IDCARD_NO"));
		modelMap.put("PASSPORT_NO", request.getParameter("PASSPORT_NO"));
		modelMap.put("JOIN_COMPANY_DATE", request.getParameter("JOIN_COMPANY_DATE"));
		
		
		return new ModelAndView("/hrm/transferOrder/viewPopMarkToHire", modelMap);
	}
	
	
	
	/**
	 * 提交工资保险奖金数据
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaBnIn")
	@ResponseBody
	public Map<String, Object> saveSaBnIn(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = transferOrderSer.saveSaBnIn(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_success", request));// 发令成功
			map.put("navTabId", "hr0201");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_fail", request));// 发令失败
		}
		return map;
	}
	
	
	/**
	 * 跳转到调动发令页面,显示职等信息（view Upgrade）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGradeLevelNoByPostGradeNo")
	@ResponseBody
	public Map getGradeLevelNoByPostGradeNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		List gradeLevelList = this.transferOrderSer.getGradeLevelNoByPostGradeNoList(request);
		for(int i=0;i<gradeLevelList.size();i++){
			map.put((String)((Map) gradeLevelList.get(i)).get("GRADE_NAME"), ((Map) gradeLevelList.get(i)).get("GRADE_LEVEL"));
		}
		return map;
	}
	/**
	 * 跳转到入职发令页面（view Hire）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrderOperation")
	public ModelAndView viewOrderOperation(HttpServletRequest request, ModelMap modelMap)
			throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		/*List positionList = this.transferOrderSer.getPositionList(request);
		List dutyList = this.transferOrderSer.getDutyList(request);
		List postGradeList = this.transferOrderSer.getPostGradeList(request);
		List postList = this.transferOrderSer.getPostList(request);
		List workAreaList = this.transferOrderSer.getWorkAreaList(request);
		List oldPostGradeList = this.transferOrderSer
				.getOldPostGradeList(request);
		List getHaoFenglist = this.transferOrderSer.getHaoFengLists(request);
		List getRecSourceList = this.transferOrderSer.getRecSourceList(request);
		
		
		// modelMap.put("empid",this.transferOrderSer.getNextEmpid(request));
		
		modelMap.put("nationalCode", admin.getOperationCodeNo());
		modelMap.put("positionList", positionList);
		modelMap.put("dutyList", dutyList);
		modelMap.put("postGradeList", postGradeList);
		modelMap.put("postList", postList);
		modelMap.put("workAreaList", workAreaList);
		modelMap.put("oldPostGradeList", oldPostGradeList);
		modelMap.put("getHaoFengLists", getHaoFenglist);
		modelMap.put("getRecSourceList", getRecSourceList);*/
		List postGradeList = this.postSer.getPostGradeItemList(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("postGradeList", postGradeList);
		return new ModelAndView("/hrm/transferOrder/viewOrderOperation", modelMap);
	}
	
	
	/**
	 * 跳转到派遣地发令页面（view SendAndSendOff）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSendAndSendOff")
	public ModelAndView viewSendAndSendOffList(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)
			throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getHrDispatchList = this.transferOrderSer.getHrDispatch(request);//撤销
		int hrDispatchCnt=this.transferOrderSer.getHrDispatchCnt(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("getHrDispatchList", getHrDispatchList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, hrDispatchCnt) ;
		return new ModelAndView("/hrm/transferOrder/viewSendAndSendOff", modelMap);
	}
	/**
	 *  派遣地发令修改页面（view SendAndSendOff）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSendAndSendOff")
	public ModelAndView viewupdateSendAndSendOffList(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)
			throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//List paiQians=this.transferOrderSer.getHrDispatch2(request);
		List paiQianTypes=this.transferOrderSer.getHrDispatch3(request);
		//modelMap.put("paiQians", paiQians);
		modelMap.put("paiQianTypes", paiQianTypes);
		List getHrDispatchList = this.transferOrderSer.getHrDispatchUpdate(request);//撤销
		int hrDispatchCnt=this.transferOrderSer.getHrDispatchUpdateCnt(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("getHrDispatchList", getHrDispatchList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, hrDispatchCnt) ;
		return new ModelAndView("/hrm/transferOrder/updateSendAndSendOff", modelMap);
	}
	
	/**
	 *  派遣地发令修改页面(新)（view SendAndSendOff）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSendAndSendOffNew")
	public ModelAndView updateSendAndSendOffNew(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//List paiQians=this.transferOrderSer.getHrDispatch2(request);
		List paiQianTypes=this.transferOrderSer.getHrDispatch3(request);
		//modelMap.put("paiQians", paiQians);
		modelMap.put("paiQianTypes", paiQianTypes);
		Map hrDispatchInfo = null;
		List getHrDispatchList = this.transferOrderSer.getHrDispatchUpdate(request);
		if(getHrDispatchList!=null && getHrDispatchList.size()>0){
			hrDispatchInfo = (Map) getHrDispatchList.get(0);
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrDispatchInfo", hrDispatchInfo);
		return new ModelAndView("/hrm/transferOrder/updateSendAndSendOffNew", modelMap);
	}
	
	/**
	 * 派遣地发令修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSendAndSendOffNew",method = RequestMethod.POST)
	@ResponseBody
	public Map updateSendAndSendOffNew(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
	    int result = this.transferOrderSer.updateSendAndSendOffNew(request);
		if(result == 1){
		    map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0919");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddSendAndSendOff")
	
	public ModelAndView viewAddSendAndSendOff(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
			List paiQians=this.transferOrderSer.getHrDispatch2(request);
			List paiQianTypes=this.transferOrderSer.getHrDispatch3(request);
			modelMap.put("paiQians", paiQians);
			modelMap.put("paiQianTypes", paiQianTypes);
			modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
			return new ModelAndView("/hrm/transferOrder/viewAddSendAndSendOff", modelMap);
		//	return statList;
		//	return messMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddSendAndSendOffpaiQianTypes")
	public void viewAddSendAndSendOffpaiQianTypes(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
 		List paiQianTypes=this.transferOrderSer.getHrDispatch3(request);
		  
        PrintWriter out = response.getWriter();
        String str="";
       
        for(int i=0;i<paiQianTypes.size();i++){
        	LinkedHashMap map = (LinkedHashMap) paiQianTypes.get(i);
        	str+="<option value="+map.get("CODE_NO")+ ">" +map.get("CODENAME")+"</option>";
		}
        out.println(JsonUtil.writeInternal(str));
        
		out.flush();
		out.close();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddSendAndSendOffpaiQians")
	public void viewAddSendAndSendOffpaiQians(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        List paiQians=this.transferOrderSer.getHrDispatch2(request);
 		  
        PrintWriter out = response.getWriter();
        String str="";
       
        for(int i=0;i<paiQians.size();i++){
        	LinkedHashMap map = (LinkedHashMap) paiQians.get(i);
        	str+="<option value="+map.get("PQD_DIQUMINGCHENGNO")+ ">" +map.get("PQD_DIQUMINGCHENG")+"</option>";
		}
        out.println(JsonUtil.writeInternal(str));
        
		out.flush();
		out.close();
	}

	
	
	
	/**
	* 根据条件查询人员 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrderList")
	@ResponseBody
	public Map viewOrderList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
	/*	AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());*/
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		List gradeLevelList = this.transferOrderSer.getHrEmployeeList(request);
		//logger.debug("gradeLevelList.size:::"+gradeLevelList.size());
		if(gradeLevelList.size()>0){
//		for(int i=0;i<gradeLevelList.size();i++){
//			map1=(Map)gradeLevelList.get(i);
//			String value=map1.get("PERSON_ID")+","+map1.get("EMPID");
//			map.put(map1.get("LOCAL_NAME").toString(), value.toString());
//		}

			//jjy
			String transCode = request.getParameter("code")==null ? "" : (String)request.getParameter("code");
			for(int i=0;i<gradeLevelList.size();i++){
				map1=(Map)gradeLevelList.get(i);
				String value=map1.get("PERSON_ID")+","+map1.get("EMPID");
				map.put(map1.get("EMPID")+" - "+map1.get("LOCAL_NAME").toString(), value.toString());
				//logger.debug("gradeLevelList["+i+"]:::"+ map1.get("EMPID")+" - "+map1.get("LOCAL_NAME").toString() );
				
/*
				
				if(transCode.equals("123346")){
					//복직인 경우는 휴직자만 조회
					if( ((String)map1.get("EMP_OFFICE")).equals("15119") || ((String)map1.get("EMP_OFFICE")).equals("123450")){
						logger.debug("EXCLUDE MEMBER:::"+(String)map1.get("EMP_OFFICE")+",CNAME:"+(String)map1.get("CHINESE_PINYIN")+",KNAME:"+(String)map1.get("KOREAN_NAME"));
					}else{
						//EMP_OFFICE(15118) ::: 休职 : 123450, 在职: 15119  , : 15120  
						//logger.debug("EMP_OFFICE:::"+(String)map1.get("EMP_OFFICE"));
						String value=map1.get("PERSON_ID")+","+map1.get("EMPID");
						map.put(map1.get("LOCAL_NAME").toString(), value.toString());
					}
				}else{
					//재직자만 조회
					if( ((String)map1.get("EMP_OFFICE")).equals("15119") || ((String)map1.get("EMP_OFFICE")).equals("123450")){
						//EMP_OFFICE(15118) ::: 休职 : 123450, 在职: 15119  , : 15120  
						//logger.debug("EMP_OFFICE:::"+(String)map1.get("EMP_OFFICE"));
						String value=map1.get("PERSON_ID")+","+map1.get("EMPID");
						map.put(map1.get("LOCAL_NAME").toString(), value.toString());
					}else{
						logger.debug("EXCLUDE MEMBER:::"+(String)map1.get("EMP_OFFICE")+",CNAME:"+(String)map1.get("CHINESE_PINYIN")+",KNAME:"+(String)map1.get("KOREAN_NAME"));
					}
				}
*/			
			}

		
			

		}else{
			map.put("len", "0");
		}
		request.setAttribute("list", gradeLevelList);
		
		return map;
	}
	/**
	* 根据条件查询调令表头
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTranferOrderTitile")
	@ResponseBody
	public LinkedHashMap getTranferOrderTitile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, String> mapTitle = new LinkedHashMap<String, String>();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		List list=this.transferOrderSer.getTranferOrderTitile(request);
		String disFild=" ";
		String content=" ";
		for(int i=0;i<list.size();i++){
			map1=(Map)list.get(i);
			if(map1.get("DISTINCT_FIELD")!=null&&!map1.get("DISTINCT_FIELD").equals("")){
				disFild=map1.get("DISTINCT_FIELD").toString();
			}
			if(map1.get("CONTENT")!=null&&!map1.get("CONTENT").equals("")){
				content=map1.get("CONTENT").toString();
			}
			mapTitle.put(disFild, content);
		}
		modelMap.put("mapTitle", mapTitle);
		modelMap.put("list1", list);
		request.setAttribute("list", mapTitle);
		String table="<table><tr>";
		for(int i=0;i<list.size();i++){
			map1=(Map)list.get(i);
			if(map1.get("CONTENT")!=null&&!map1.get("CONTENT").equals("")){
				content=map1.get("CONTENT").toString();
			}
			table+="<td>"+content+"</td>";
		}
		table+="</tr></table>";
		request.setAttribute("table", table);
		//计算当前类型最大调令编号
		int maxTransNum=transferOrderSer.getHrExperienceInsideTrans_Num(request);
		mapTitle.put("maxTransNum", String.valueOf(maxTransNum));
		return mapTitle;
	}
	/**
	* 根据调令类型查询人员的调令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio   heran@ait.net.cn 
	* @date Aug 13, 2013 10:44:15 AM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHrExperienceInsideByPersonId")
	@ResponseBody
	public LinkedHashMap getHrExperienceInsideByPersonId(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap<String, String> mapTitle = new LinkedHashMap<String, String>();
		List listTitle=this.transferOrderSer.getTranferOrderTitile(request);
	
		List list=this.transferOrderSer.getHrExperienceInsideByPersonId(request);
		int cloumeNum=Integer.valueOf(request.getParameter("cloumeNum").toString());
		String td="";
		String key="";
		
		for(int i=0;i<list.size();i++){
			Map m=(Map)list.get(i);
			Set set=m.keySet();
			Iterator iter = set.iterator();
			 while (iter.hasNext()) {
				 key+=iter.next()+",";
			 }
			 String keys1 [] =key.split(",");
			 	
				for(int j=0;j<listTitle.size();j++){
					Map m1=(Map)listTitle.get(j);
					String dField=m1.get("DISTINCT_FIELD").toString();
					if(dField.equals("")){}
					boolean bool=false; 
					for(int  k=0;k<keys1.length;k++){
						if(dField.equals(keys1[k])){
							bool=true;
							break;
						}else{
							bool=false;
						}
					}
					//1.TRANS_CONFIG_FLAG 调令处理类型( 0:不显示;  1:显示不修改;  2:显示且修改) hr_trans_config
					//2. OUTPUT_TYPE
					//调令处理类型( 输出类型： 1表示文本框；2表示下拉选框；3表示日期控件。) hr_trans_config_param
					String TransConfigFlag=m1.get("TRANS_CONFIG_FLAG").toString();
					
					if(bool==true){
						
							String textType=m1.get("OUTPUT_TYPE").toString();
							if(textType.equals("5")){
								String codeText=request.getParameter("codeText");
								td="<td class='td_title' id='tr_"+cloumeNum+"'><input type='hidden' value='"+m.get("PERSON_ID")+"' name='cloumeNumValue'><img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)'/></td><td class='td_center'>"+codeText+"</td><td class='td_center'><input type='checkbox' name='chexkbox' id='box_"+cloumeNum+"' value='"+m.get("PERSON_ID")+","+cloumeNum+"'/></td>" ;
								td+="<input type='hidden' value='"+m.get("PERSON_ID")+"' name='personid'/>";
								td+=	"</td>";
							}
							else if(textType.equals("1")){
								if(TransConfigFlag.equals("2")){
									if(m1.get(dField)!=null){
										td+="<td class='td_center'><input size='10' name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' type='text' value='"+m.get(dField).toString()+"'/></td>";
									}else{
										td+="<td class='td_center'><input size='10' name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' type='text'/></td>";
									}
								}else{
									td+="<td class='td_center'>"+m.get(dField).toString();
									td+="<input type='hidden' id='"+dField+"_"+cloumeNum+"' value='"+m.get(dField).toString()+"' name='"+dField+"_"+cloumeNum+"' ></td>";
								}
							}else if(textType.equals("2")){	
								String tableName=m1.get("PARENT_TABLE_NAME").toString();
								if(TransConfigFlag.equals("2")){	
								
								Map paramMap=new LinkedHashMap();
								paramMap.put("PARENT_CODE_NO",m1.get("PARENT_CODE_NO").toString());
								paramMap.put("language",admin.getLanguage());
								paramMap.put("interLanguage",admin.getLanguage());
								paramMap.put("CPNY_ID",admin.getCpnyId());
								//paramMap.put("ORDER_TYPE",orderType);
								paramMap.put("ADMINID", admin.getAdminID()) ;
								paramMap.put("CPNYID", admin.getCpnyId());
								paramMap.put("userNo", admin.getUserNo());
								paramMap.put("deptNo", admin.getDeptNo());
								paramMap.put("specialParam", admin.getSpecialParam());
								if(tableName.equals("HR_DEPARTMENT")){
									List codeList = basicMaintenanceDao.getDeptListByCpnyID(paramMap);
									td+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									//td+="<option>请选择</option>";
									
									for(Object map : codeList){
										Map temp = (Map) map;
										String tableId=m1.get("PARENT_TABLE_FIELD").toString();
										String tableCodeNo=m.get(tableId).toString();
										int level=Integer.parseInt(temp.get("DEPTLEVEL").toString());
										td+=("<option ");
										if(tableCodeNo.equals(temp.get("CODE_NO"))){
											td+="selected='selected'";
										}
										
										td+="value='"+temp.get("CODE_NO")+"'";
										td+=">";
										for(int f=0;f<level;f++){
											td+=("&nbsp;&nbsp;");
										}
										td+=temp.get("CODE_NAME")+"</option>";
									}
									td+="</select></td>";
								}
								//tableName为sy_code表的
								else if(tableName.equals("SY_CODE")||tableName.equals("0")){
									List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
									td+="<td class='td_center'><select  ";
									String code="";
									if(request.getParameter("code")!=null){
										code=request.getParameter("code");
									}else{
										code=request.getParameter("OrderType");
									}
									if(code.equals("123351")){
										td+=" ";
									}
									else if(dField.equals("GRADE_LEVEL")){
										td+="onchange='getZhiDengAndZhiJi(\""+dField+"_"+cloumeNum+"\","+cloumeNum+")'";
									}else if(dField.equals("POST_GRADE_NO")){
										td+="onchange='getZhiJiAndZeToMing1(\""+dField+"_"+cloumeNum+"\","+cloumeNum+")'";
									}
									else{
										td+=" "; 
										//td+="onchange='getZhiDengAndZhiJi(\""+dField+"_"+cloumeNum+"\","+cloumeNum+")'";
										//td+="onchange='changName("+dField+"_"+cloumeNum+",this.id,this.value,"+cloumeNum+")'";
									}
									td+="name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									if(!paramMap.get("PARENT_CODE_NO").equals("123444")&&!paramMap.get("PARENT_CODE_NO").equals("1365")){
										//td+="<option>请选择</option>";
									}
								
									for(Object map : codeList){
										Map temp = (Map) map;
										String tableId=m1.get("PARENT_TABLE_FIELD").toString();
										//String tableId=m1.get("PARENT_TABLE_FIELD").toString();
										//String tableCodeNo=m.get(tableId).toString();
										String tableCodeNo=m.get(dField).toString();
										td+="<option ";
										if(tableCodeNo.equals(temp.get("CODE_NO"))){
											td+="selected='selected'";
										}
										td+=(" value='"+temp.get("CODE_NO")+"'");
										
										td+=(">"+temp.get("CODE_NAME")+"</option>");
									}
									td+="</select></td>";
								}else{
									paramMap.put("TABLENAME",tableName);
									paramMap.put("PARENTTABLEFIELD",m1.get("PARENT_TABLE_FIELD"));
									String sql="";
									if(tableName.equals("HR_DEPARTMENT")){
										sql=" get_dept_name(DEPTNO,'"+admin.getLanguage()+"') CODE_NAME";
										
									}else{
										sql="get_global_name("+m1.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
									}
									paramMap.put("SQL",sql);
									List codeList = basicMaintenanceDao.getParamCodeListByTableName(paramMap) ;
									String code="";
									if(request.getParameter("code")!=null){
										code=request.getParameter("code");
									}else{
										code=request.getParameter("OrderType");
									}
									//td+="<td><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									td+="<td class='td_center'><select ";
									//||code.equals("123360"
									if(code.equals("123351")){
										td+=" ";
									}
									else if( (code.equals("123359")&&dField.equals("DUTY_NO"))||(code.equals("123360")&&dField.equals("DUTY_NO"))){
										td+="onchange='dutySelect("+cloumeNum+")' ";
									}
									else if(dField.equals("GRADE_LEVEL")){
										td+="onchange='getZhiDengAndZhiJi(\""+dField+"_"+cloumeNum+"\","+cloumeNum+")'";
									}else if(dField.equals("POST_GRADE_NO")){
										td+="onchange='getZhiJiAndZeToMing1(\""+dField+"_"+cloumeNum+"\","+cloumeNum+")'";
									}
									else{
										td+=" "; 
										//td+="onchange='getZhiDengAndZhiJi("+dField+"_"+cloumeNum+","+cloumeNum+")'";
										//td+="onchange='changName("+dField+"_"+cloumeNum+",this.id,this.value,"+cloumeNum+")'";
									}
									td+="name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									//td+="<option>请选择</option>";
									//td+="<option>请选择</option>";
									for(Object map : codeList){
										Map temp = (Map) map;
										String tableId=m1.get("PARENT_TABLE_FIELD").toString();
										String tableCodeNo=m.get(tableId).toString();
										
										td+="<option ";
										if(tableCodeNo.equals(temp.get("CODE_NO"))){
											td+="selected='selected'";
										}
										td+=(" value='"+temp.get("CODE_NO")+"'");
										
										td+=(">"+temp.get("CODE_NAME")+"</option>");
									}
									td+="</select></td>";
								
								  }
								}
								else{
									Map paramMap=new LinkedHashMap();
									paramMap.put("language",admin.getLanguage());
									paramMap.put("interLanguage",admin.getLanguage());
									paramMap.put("CPNY_ID",admin.getCpnyId());
									paramMap.put("TABLENAME",tableName);
									paramMap.put("PARENTTABLEFIELD",m1.get("PARENT_TABLE_FIELD"));
									String sql="";
									if(tableName.equals("HR_DEPARTMENT")){
										paramMap.put("TYPE", tableName);
										sql=" get_dept_name(DEPTNO,'"+admin.getLanguage()+"') CODE_NAME";
										
									}else if(tableName.equals("SY_CODE")){
										paramMap.put("TYPE", tableName);
										paramMap.put("PARENT_CODE_NO", m1.get("PARENT_CODE_NO"));
										sql="get_global_name("+m1.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
									}else{
										paramMap.put("TYPE", tableName);
										sql="get_global_name("+m1.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
									}
									paramMap.put("SQL",sql);
									
									List codeList = basicMaintenanceDao.getParamCodeListByTableName(paramMap) ;
									String deptname="";
									String deotNameId="";
									for(Object map : codeList){
										Map temp = (Map) map;
										String tableId= ( m.get(dField)==null ? "" : m.get(dField) ) .toString();
										String tableCodeNo="";
										/*if(temp.get("CODE_NO")!=null){
											 tableCodeNo=temp.get(tableId).toString();
										}else{
											tableCodeNo="";
										}*/
										if(tableId.equals(temp.get("CODE_NO"))){
											deptname=temp.get("CODE_NAME").toString();
											deotNameId=tableId;
											break;
										}
									}	
									td+="<td class='td_center'>"+deptname+"";
									td+="<input type='hidden' id='"+dField+"_"+cloumeNum+"' value='"+deotNameId+"' name='"+dField+"_"+cloumeNum+"'/></td>";
								
									
									//td+="<td><input type='text' value='"+m.get(dField)+"' id='"+dField+"_"+cloumeNum+"' name='"+dField+"_"+cloumeNum+"' /><input type='hidden' id='"+dField+"_"+cloumeNum+"' value='"+m.get(dField)+"' name='"+dField+"_"+cloumeNum+"'/></td>";
									
									
								}	
								//td+="<td class='td_center'><ait:SelectSyCodeByCpnyID name='OrderType' parentNo='123313'cnpyID='"+admin.getCpnyId()+"' limit='all' onChangeName='titleName(this.value)' /></td>";
							}else if(textType.equals("3")){
								if(m.get(dField)!=null){
									td+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+m.get(dField).toString()+"'/></td>";
								}else{
									if(dField.equals("TRANS_ORDER_DATE")){
										td+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+request.getParameter("diaolingDate")+"'/></td>";
									}else{
										td+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);'/></td>";
									}
									
								}
							}else if(textType.equals("4")){
								LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
								paramMap.put("ADMINID", admin.getAdminID()) ;
								paramMap.put("CPNYID", admin.getCpnyId());
								paramMap.put("userNo", admin.getUserNo());
								paramMap.put("deptNo", "deptno");
								paramMap.put("DEPTNO1", "deptno");
								paramMap.put("specialParam", admin.getSpecialParam());
								
								
								List	deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
								List	deptList1 = deptList.subList(1,deptList.size());
								String lastNo="";
								String newNo="";
								td+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
								for(int l=0;l<deptList.size();l++){
									Map m2=(Map)deptList.get(l);
									lastNo=m2.get("DEPTNO").toString();
									newNo=m2.get("PARENT_DEPT_NO").toString();
									boolean bool1=false;
									for(int f=0;f<deptList1.size();f++){
										Map m3=(Map)deptList.get(l);
										if(m3.get("PARENT_DEPT_NO").equals(lastNo)){
											//td+="<option value='0'>请选择</option>";
											td+="<option ";
											if(m.get("DEPTNO1").equals(m3.get("DEPTNO"))){
												td+="selected='selected'";
											}
											
											td+="value='"+m2.get("DEPTNO")+"'>&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;"+m2.get("DEPTNAME")+"</option>";
											bool1=true;
											break;
										}					
									}
									if(bool1==false){
										td+="<option ";
										if(m.get("DEPTNO1").equals(m2.get("DEPTNO"))){
											td+="selected='selected'";
										}
										td+="value='"+m2.get("DEPTNO")+"'>&nbsp;&nbsp;"+m2.get("DEPTNAME")+"</option>";
									}
								}
								td+="</select></td>";
								
								
								
								
								
								
							}
						
					}
					//分开
					else{
						
						String textType=m1.get("OUTPUT_TYPE").toString();
						if(textType.equals("5")){
							String codeText=request.getParameter("codeText");
							td="<td class='td_center' id='tr_"+cloumeNum+"'><input type='hidden' value='"+cloumeNum+"' name='cloumeNumValue'><img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)' /></td><td class='td_center'><input type='checkbox' name='chexkbox' id='box_"+cloumeNum+"' value='"+m.get("PERSON_ID")+","+cloumeNum+"'/></td><td class='td_center'>"+codeText+"" ;
							td+="<input type='hidden' value='"+m.get("PERSON_ID")+"' name='personid'/>";
							td+=	"</td>";
							
						}
						else if(textType.equals("1")){
							if(m1.get(dField)!=null){
								td+="<td class='td_center'><input size='10' name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' type='text' value='"+m.get(dField).toString()+"'/></td>";
							}else{
								td+="<td class='td_center'><input size='10' name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' type='text'/></td>";
							}
						}else if(textType.equals("2")){		
							Map paramMap=new LinkedHashMap();
							paramMap.put("PARENT_CODE_NO",m1.get("PARENT_CODE_NO").toString());
							paramMap.put("language",admin.getLanguage());
							paramMap.put("interLanguage",admin.getLanguage());
							paramMap.put("CPNY_ID",admin.getCpnyId());
							//paramMap.put("ORDER_TYPE",orderType);
							String tableName=m1.get("PARENT_TABLE_NAME").toString();
							//tableName为sy_code表的
							if(tableName.equals("HR_DEPARTMENT")){
								List deptList=new ArrayList();
								paramMap.put("ADMINID", admin.getAdminID()) ;
								paramMap.put("CPNYID", admin.getCpnyId());
								paramMap.put("interCpnyID", admin.getCpnyId());
								paramMap.put("userNo", admin.getUserNo());
								paramMap.put("deptNo", admin.getDeptNo());
								paramMap.put("specialParam", admin.getSpecialParam());
								deptList=hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
								//List codeList = basicMaintenanceDao.getDeptListByCpnyID(paramMap);
								List codeList=deptList;
								td+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
								
								if(dField.equals("PARTTIME_DEPT")){
							    	td+="<option value=' '>请选择</option>";
							    }
									//td+="<option>请选择</option>";
							
							
								
								for(Object map : codeList){
									Map temp = (Map) map;
									int level=Integer.parseInt(temp.get("DEPT_LEVEL").toString());
									td+=("<option ");
									if(m.get(dField)!=null){
										if(m.get(dField).equals(temp.get("DEPTNO"))){
											td+="selected='selected' ";
										}
									}
									
									
									td+="value='"+temp.get("DEPTNO")+"'";
									td+=">";
									for(int f=0;f<level;f++){
										td+="&nbsp;&nbsp;";
									}
									td+=temp.get("DEPTNAME")+"</option>";
								}
								td+="</select></td>";
							}
							else if(tableName.equals("SY_CODE")||tableName.equals("0")){
								List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
								td+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
								
								if(!paramMap.get("PARENT_CODE_NO").equals("123444")&&!paramMap.get("PARENT_CODE_NO").equals("1365")){
									//td+="<option>请选择</option>";
								}
								if(dField.equals("PARTTIME_DUTY")||dField.equals("PARTTIME_POSITION_NO")){
							    	td+="<option value=' '>请选择</option>";
							    }
								for(Object map : codeList){
									Map temp = (Map) map;
									td+=("<option value='"+temp.get("CODE_NO")+"'");
									
									td+=(">"+temp.get("CODE_NAME")+"</option>");
								}
								td+="</select></td>";
							}else{
								paramMap.put("TABLENAME",tableName);
								paramMap.put("PARENTTABLEFIELD",m1.get("PARENT_TABLE_FIELD"));
								String sql="";
								if(tableName.equals("HR_DEPARTMENT")){
									sql=" get_dept_name(DEPTNO,'"+admin.getLanguage()+"') CODE_NAME";
									
								}else{
									sql="get_global_name("+m1.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
								}
								paramMap.put("SQL",sql);
								
								
								List codeList = basicMaintenanceDao.getParamCodeListByTableName(paramMap) ;
								td+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									//td+="<option>请选择</option>";
								if(dField.equals("PARTTIME_DUTY")||dField.equals("PARTTIME_POSITION_NO")){
							    	td+="<option value=' '>请选择</option>";
							    }
								for(Object map : codeList){
									Map temp = (Map) map;
									td+=("<option value='"+temp.get("CODE_NO")+"'");
									
									td+=(">"+temp.get("CODE_NAME")+"</option>");
								}
								td+="</select></td>";
							
							}
							
							
							//td+="<td class='td_center'><ait:SelectSyCodeByCpnyID name='OrderType' parentNo='123313'cnpyID='"+admin.getCpnyId()+"' limit='all' onChangeName='titleName(this.value)' /></td>";
						}else if(textType.equals("3")){
							if(m.get(dField)!=null){
								td+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+m.get(dField).toString()+"'/></td>";
							}else{
								if(dField.equals("TRANS_ORDER_DATE")){
									td+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+request.getParameter("diaolingDate")+"'/></td>";
								}else{
									td+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);'/></td>";
								}
							}
						}else if(textType.equals("4")){
							LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
							paramMap.put("ADMINID", admin.getAdminID()) ;
							paramMap.put("CPNYID", admin.getCpnyId());
							paramMap.put("userNo", admin.getUserNo());
							paramMap.put("deptNo", "deptno");
							paramMap.put("specialParam", admin.getSpecialParam());
							
							
							List	deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
							td+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
							for(int l=0;l<deptList.size();l++){
								Map m2=(Map)deptList.get(l);
								td+="<option value='"+m2.get("DEPTNO")+"'>"+m2.get("DEPTNAME")+"</option>";
							}
							td+="</select></td>";
							
						}
						
						
						
						//td+="<td class='td_center'></td>";
					}
				}
				td+="<td class='td_center'><input type='hidden' value='"+m.get("PERSON_ID")+"'  name='personid_"+cloumeNum+"' id='personid_"+cloumeNum+"'/>";
				td+="<input type='hidden' id='EMPID_"+cloumeNum+"' name='EMPID_"+cloumeNum+"' value='"+m.get("EMPID")+"'/>" +
						"<input type='button' value='History' onclick='falingHistory("+cloumeNum+")'/>" +
						"</td>";
				//td+="<td  class='td_center'><input type='button' value='History' onclick='historyPersonId("+m.get("PERSON_ID")+")'/></td>";
				mapTitle.put((String.valueOf(i)),td);
				td="";
				cloumeNum++;
		}
		return mapTitle;
	}
	/**
	* 根据条件查询调令表头
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTranferOrderTitile1")
	@ResponseBody
	public LinkedHashMap getTranferOrderTitile1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, String> mapTitle = new LinkedHashMap<String, String>();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		List list=this.transferOrderSer.getTranferOrderTitile(request);
		String code="";
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}else{
			code=request.getParameter("OrderType");
		}
		for(int i=0;i<list.size();i++){
			map1=(Map)list.get(i);
			String value=map1.get("CONTENT").toString()+","+map1.get("PARENT_CODE_NO").toString()+","+map1.get("PARENT_TABLE_NAME").toString()+","+map1.get("PARENT_TABLE_FIELD").toString()+","+map1.get("OUTPUT_TYPE").toString()+","+map1.get("TRANS_CONFIG_FLAG").toString()+","+code;
			mapTitle.put(map1.get("DISTINCT_FIELD").toString(), value);
		}
		
		
		return mapTitle;
	}
	/**
	* 根据条件查询调令表头
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0  new ModelAndView("/hrm/transferOrder/viewOrderOperation", modelMap); ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectTag")
	public String selectTag(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			if(request.getParameter("flag")!=null && !"".equals(request.getParameter("flag"))){
				request.setAttribute("id", request.getParameter("flag")+"_OrderType_"+request.getParameter("id")+"");
			}
		}
		
		request.setAttribute("defaultCpny", admin.getCpnyId());
		request.setAttribute("table",  request.getParameter("table"));
		request.setAttribute("parameter", request.getParameter("parameter"));
		String table=request.getParameter("table").toString();
		String parameter=request.getParameter("parameter").toString();
		String parentno=request.getParameter("parentno");
		request.setAttribute("language", admin.getLanguage());
		String type=request.getParameter("type").toString();
		String tag="";
		request.setAttribute("parentNo", request.getParameter("parentNo"));
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("cloumeNum1", request.getParameter("cloumeNum1"));
		if(request.getParameter("key")!=null){
			//PARTTIME_DEPT PARTTIME_DUTY PARTTIME_POSITION_NO
			String key=request.getParameter("key");
			if(key.equals("PARTTIME_DEPT")||key.equals("PARTTIME_DUTY")||key.equals("PARTTIME_POSITION_NO")){
			String keyName=key.substring(0,8);
			if(keyName.equals("PARTTIME")){ 
				request.setAttribute("limit", "all");
			}
			}
		}
		if(table.equals("HR_DEPARTMENT")){
			request.setAttribute("type","3") ;
			request.setAttribute("user",admin.getPersonId()) ;
			//request.setAttribute("time",request.getParameter("time")) ;
			tag= "/hrm/transferOrder/selectTag2";
		}
		else if(table.equals("SY_CODE")||table.equals("0")){
			if(parentno!=null){
				if(parentno.equals("1365")||parentno.equals("123344")){
					request.setAttribute("limit", request.getParameter("all"));
				}
			}
			tag= "/hrm/transferOrder/selectTag";
		}else if(parameter.equals("DEPTNO")){
			request.setAttribute("type","1") ;
			tag= "/hrm/transferOrder/selectTag1";
		}
		else{
			request.setAttribute("type","2") ;
			tag= "/hrm/transferOrder/selectTag1";
		}
		return tag;
	}
	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpIdList")
	public ModelAndView viewEmpIdList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		/*modelMap.put("sortNameNoList",this.empInfoSer.getOrderParmList(request,"18706"));*/
		modelMap.put("turn_to_url",request.getParameter("turnToUrl"));
		modelMap.put("empList",this.transferOrderSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transferOrderSer.getEmpIdListCnt(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("seach_NAVID", request.getParameter("seach_NAVID"));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("EMP", request.getParameter("empid"));
		String viewEmpIdListColnum="0";
		if(request.getParameter("viewEmpIdListColnum")!=null){
			
			viewEmpIdListColnum=request.getParameter("viewEmpIdListColnum");
		}else{
			viewEmpIdListColnum="0";
		}
		modelMap.put("viewEmpIdListColnum",request.getParameter("viewEmpIdListColnum"));
		return new ModelAndView("/hrm/transferOrder/viewEmpIdList",modelMap);		
	}
	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * 查询所有员工包括离职员工
	 */
	@RequestMapping(value = "/viewEmpIdList1")
	public ModelAndView viewEmpIdList1(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		/*modelMap.put("sortNameNoList",this.empInfoSer.getOrderParmList(request,"18706"));*/
		modelMap.put("turn_to_url",request.getParameter("turnToUrl"));
		modelMap.put("empList",this.transferOrderSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transferOrderSer.getEmpIdListCnt(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("EMP", request.getParameter("empid"));
		String viewEmpIdListColnum="0";
		if(request.getParameter("viewEmpIdListColnum")!=null){
			
			viewEmpIdListColnum=request.getParameter("viewEmpIdListColnum");
		}else{
			viewEmpIdListColnum="0";
		}
		modelMap.put("viewEmpIdListColnum",request.getParameter("viewEmpIdListColnum"));
		return new ModelAndView("/hrm/transferOrder/viewEmpIdList",modelMap);		
	}

	
	
	
	
	/**
	 * 根据工号，查询对应的调令信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHrExperiencInsideByEmpIdAndTransNo")
	public ModelAndView getHrExperiencInsideByEmpIdAndTransNo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list=this.transferOrderSer.getHrExperienceInsideByPersonId(request);
		
		return new ModelAndView("/hrm/transferOrder/viewEmpIdList",modelMap);		
	} 

	/**
	* 调令保存
	* 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0  new ModelAndView("/hrm/transferOrder/HrExperienceInside", modelMap); ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveHrExperienceInside")
	@ResponseBody
	public Map SaveHrExperienceInside(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		String type=request.getParameter("type");
		int result=-1;
		if(type.equals("2")){
			//jjy
			/*
			String[] personid=request.getParameterValues("personid");
			if(request.getParameterValues("personid")!=null&&personid.length>0){
				
				result=transferOrderSer.deleteHrExperienceInsideSaveByPersonIds(request);
			}
			if(result!=-1){
			 result=transferOrderSer.SaveHrExperienceInsideSave(request);
			}
			*/                             
			String aa=request.getParameter("CUR_DEPTNO_1");
			result=transferOrderSer.SaveHrExperienceInsideSave(request);
			 if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("zxc.hr.alert.message.SAVE_SUCCESS", request));// 发令成功
					//map.put("navTabId", "hr0202");
				} else if (result == 2) {
					map.put("statusCode", "300");
					// 没有决裁者，请先配置决裁者
					map.put("message", TipMessage.getTipMessage("hr.alert.message.no_affiram", request));
				} else if (result > 2) {
					map.put("statusCode", "300");
					//此result员工未变更部门或职责，不允许做职责变更发令！
					map.put("message", result + TipMessage.getTipMessage("hr.transfer.message.changeDutynoOrDeptnoPlease", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_fail", request));// 发令失败
				}
		}else if(type.equals("1")){
			
			/*//1. 验证一下所有人的发令日期是否早于其入职日期
			String eid = transferOrderSer.checkPersonId(request);
			//1.1 如果有人的发令日期是否早于其入职日期，给出提示
			if (eid != null) {
				map.put("statusCode", "300");
				//此工号的发令日期早于此人的入职日期因此发令失败
				map.put("message",TipMessage.getTipMessage("hr.alert.message.this_empid",request)
					+ eid + TipMessage.getTipMessage("hr.alert.message.data_question_fail",request));
				
				return map;
			}*/
			//2 .判断此人是否有未生效的调动发令
			//2.1  如果没有才允许做下一次调动发令
			int cloumeNum =Integer.parseInt(request.getParameter("cloumeNum").toString())-1;
			int num=-1;
			String[] personid1=request.getParameterValues("personid");
			LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request);
			String chexkBoxValue=request.getParameter("checkBoxValue");
			int checkBoxValueNum=chexkBoxValue.lastIndexOf(",");
			String [] chexkBoxValues=chexkBoxValue.substring(0,checkBoxValueNum).split(",");
			//for(int i=0;i<chexkBoxValues.length;i++){
				//paramMap1.put("TRANSNO", request.getParameter("OrderType"));
				//paramMap1.put("personid", chexkBoxValues[i]);
			int truePersonid=transferOrderSer.checkSaveTransferOrderUpgrade1(paramMap1);
			if (1==1) {
				//2.1.1 对此人进行调动发令saveTransferOrderUpgrade
				 result=transferOrderSer.SaveHrExperienceInsideSave1(request);
				 //int result = transferOrderSer.saveTransferOrderUpgrade(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_success", request));// 发令成功
					map.put("navTabId", "hr0202");
				} else if (result == 2) {
					map.put("statusCode", "300");
					// 没有决裁者，请先配置决裁者
					map.put("message", TipMessage.getTipMessage("hr.alert.message.no_affiram", request));
				} else if (result > 2) {
					map.put("statusCode", "300");
					//此result员工未变更部门或职责，不允许做职责变更发令！
					map.put("message", result + TipMessage.getTipMessage("hr.transfer.message.changeDutynoOrDeptnoPlease", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_fail", request));// 发令失败
				}
		
				/*} else {
					map.put("statusCode", "300");
					map.put("message",TipMessage.getTipMessage("hr.alert.message.in_this_start_date", request)
							+ empid + TipMessage.getTipMessage("hr.alert.message.have_about_dekreti_change_start_date",request));
					"在此生效日期 工号："+ empid +" 有相关发令,发令失败！建议更换生效日期再重试
				}*/
			//2.2 此人已有未生效发令,请生效后再进行发令
			} else {
				map.put("statusCode", "300");
				// 此人已有未生效发令,请生效后再进行发令
				map.put("message", TipMessage.getTipMessage("hr.alert.message.have_unimplemented", request));
			}
		  
		}
//		System.out.println(result);
		//"
		//return  new ModelAndView("/hrm/transferOrder/viewOrderOperation",modelMap);	
		return map;	
	}

	/**
	 * 通过职等 关联职级
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 上午11:57:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getZhiDengAndZhiJi")
	@ResponseBody
	public Map getZhiDengAndZhiJi (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getZhiDengAndZhiJiList=this.transferOrderSer.getZhiDengAndZhiJi(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<getZhiDengAndZhiJiList.size();i++){
				map.put((String)((Map) getZhiDengAndZhiJiList.get(i)).get("POST_GRADE_NO"), ((Map) getZhiDengAndZhiJiList.get(i)).get("POST_GRADE_NAME"));
			}
			return map;
		
	}
	/**
	 * 通过职级关联职责
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午3:41:32 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getZhiJiAndZhiZe")
	@ResponseBody
	public Map getZhiJiAndZhiZe (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List getZhiDengAndZhiJiList=this.transferOrderSer.getZhiJiAndZhiZe(request);
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<getZhiDengAndZhiJiList.size();i++){
			map.put((String)((Map) getZhiDengAndZhiJiList.get(i)).get("DUTY_NO"), ((Map) getZhiDengAndZhiJiList.get(i)).get("DUTY_NAME"));
		}
		return map;
		
	}


	/**
	 * 通过职级关联职级名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午4:02:39 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getZhiJiAndZhiJiMing")
	@ResponseBody
	public Map getZhiJiAndZhiJiMing (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List getZhiDengAndZhiJiList=this.transferOrderSer.getZhiJiAndZhiJiMing(request);
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<getZhiDengAndZhiJiList.size();i++){
			map.put((String)((Map) getZhiDengAndZhiJiList.get(i)).get("POST_NO"), ((Map) getZhiDengAndZhiJiList.get(i)).get("POST_NAME"));
		}
		return map;
		
	}



	/**
	 * 转到奖励/惩罚页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewRewardAndPunishment")
	public ModelAndView viewRewardAndPunishment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String tag= StringUtil.checkNull(request.getParameter("transferOrder_type"));
		List storedList=null;
		Integer maxTransNo=null;
		//“1”表示奖励
		if("1".equals(tag)){
			storedList=this.transferOrderSer.getStoredRewardList(request);
			modelMap.put("rewardList", storedList);
			modelMap.put("rewardListCnt", storedList.size());
			maxTransNo=transferOrderSer.getRewardAndPunishmentInsideTransNum(request);
		}
		//“2”表示惩罚
		if("2".equals(tag)){
			storedList=this.transferOrderSer.getStoredPunishmentList(request);
			modelMap.put("punishmentList", storedList);
			modelMap.put("punishmentListCnt", storedList.size());
			maxTransNo=transferOrderSer.getRewardAndPunishmentInsideTransNum(request);
		}
		modelMap.put("tag", tag);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("maxTransNo", maxTransNo);
		return new ModelAndView("/hrm/transferOrder/viewRewardAndPunishment",modelMap);
	}
	
	/**
	* 根据条件查询调令表头
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0  new ModelAndView("/hrm/transferOrder/selectTagForTransferOrder", modelMap); ModelAndView
	 */
	@RequestMapping(value = "/selectTagForTransferOrder")
	public String selectTagForTransferOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			if(request.getParameter("flag")!=null && !"".equals(request.getParameter("flag"))){
				request.setAttribute("id", request.getParameter("flag")+"_OrderType_"+request.getParameter("id")+"");
			}
		}
		request.setAttribute("parentNo", request.getParameter("parentNo"));
		request.setAttribute("defaultCpny", admin.getCpnyId());
		request.setAttribute("name", request.getParameter("selectName"));
		return "/hrm/transferOrder/selectTag";
	}
	/**
	* 查询旧的调令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 30, 2013 10:45:16 AM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHrExperienceInsideSaveByTransCode")
	@ResponseBody
	public LinkedHashMap getHrExperienceInsideSaveByTransCode(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, String> mapTitle = new LinkedHashMap<String, String>();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		List list=this.transferOrderSer.getTranferOrderTitile(request);
		String code="";
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}else{
			code=request.getParameter("OrderType");
		}
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//查询hr_experience_inside_save 调令
		
		String tr="";
		List list1=transferOrderSer.getHrExperienceInsideSaveByTransCode(request);
		int cloumeNum=-1;
		for(int i=0;i<list1.size();i++){
			tr="<tr>";
			
			for(int j=0;j<list.size();j++){
				Map mapTitile=(Map)list.get(j);
				String nameTitle=mapTitile.get("DISTINCT_FIELD").toString();
				String nameType= mapTitile.get("OUTPUT_TYPE").toString();
				Map mapL=(Map)list1.get(i);
				if(request.getParameter("cloumeNum")!=null){
					cloumeNum=Integer.valueOf(request.getParameter("cloumeNum").toString());
				}else{
					cloumeNum=Integer.valueOf(i);
				}
				boolean bool=false;
				String dField=mapTitile.get("DISTINCT_FIELD").toString();
				for(Object obj:mapL.keySet()){
					String name=obj.toString();
					//输出类型： 1表示文本框；2表示下拉选框；3表示日期控件；4部门树。
					String TransConfigFlag=mapTitile.get("TRANS_CONFIG_FLAG").toString();
					if(name.equals(nameTitle)){					 
						if(nameType.equals("5")){
							String codeText=request.getParameter("codeText");
							
							tr+="<td class='td_center' id='tr_"+cloumeNum+"'><input type='hidden' name='cloumeNumValue' value='"+cloumeNum+"' /><img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)'/></td>";
							tr+="<td class='td_center'><input type='checkbox' value='"+mapL.get("PERSON_ID")+","+cloumeNum+"' name='chexkbox' id='box_"+cloumeNum+"'/></td>";
							tr+="<td class='td_center'>" +
							"<input type='hidden' id='EXP_INSIDE_NO_"+cloumeNum+"' name='EXP_INSIDE_NO' value='"+mapL.get("EXP_INSIDE_NO")+"'/>"+
							"<input type='hidden' name='personid' value='"+mapL.get("PERSON_ID")+"'/>"+codeText+
									"</td>";
						}
						else if(nameType.equals("1")){
							//显示及修改
							if(TransConfigFlag.equals("2")){
								if(mapL.get(name)!=null){
									//size control;;;
									if(nameTitle.equals("REMARK")){
										tr+="<td class='td_center'><input id="+dField+"_"+cloumeNum+" name="+dField+"_"+cloumeNum+" type='text' value='"+mapL.get(name)+"' size='10' />" ;
										tr+="</td>";
									}else{
										tr+="<td class='td_center'><input id="+dField+"_"+cloumeNum+" name="+dField+"_"+cloumeNum+" type='text' value='"+mapL.get(name)+"'/>" ;
										tr+="</td>";
									}
								}else{
									//size control;;;
									if(nameTitle.equals("REMARK")){
									    tr+="<td class='td_center'><input id="+dField+"_"+cloumeNum+" name="+dField+"_"+cloumeNum+" type='text' value='' size='10' /></td>" ;
									}else{
										tr+="<td class='td_center'><input id="+dField+"_"+cloumeNum+" name="+dField+"_"+cloumeNum+" type='text' value=''/></td>" ;
									}
								}
							}
							//显示
							else{
								tr+="<td class='td_center'>"+mapL.get(name)+"";
								tr+="<input type='hidden' value='"+mapL.get(name)+"' id='"+dField+"_"+cloumeNum+"' name='"+dField+"_"+cloumeNum+" ' /></td>";
							}
													
						}else if(nameType.equals("2")){
							Map paramMap=new LinkedHashMap();
							paramMap.put("PARENT_CODE_NO",mapTitile.get("PARENT_CODE_NO").toString());
							paramMap.put("language",admin.getLanguage());
							paramMap.put("interLanguage",admin.getLanguage());
							paramMap.put("CPNY_ID",admin.getCpnyId());
							
							//paramMap.put("ORDER_TYPE",orderType);
							String tableName=mapTitile.get("PARENT_TABLE_NAME").toString();
							if(TransConfigFlag.equals("2")){	
							//tableName为sy_code表的
								if(tableName.equals("HR_DEPARTMENT")){
									List deptList=new ArrayList();
									paramMap.put("ADMINID", admin.getAdminID()) ;
									paramMap.put("interCpnyID", admin.getCpnyId());
									paramMap.put("CPNYID", admin.getCpnyId());
									paramMap.put("userNo", admin.getUserNo());
									paramMap.put("deptNo", admin.getDeptNo());
									paramMap.put("specialParam", admin.getSpecialParam());
									deptList=hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
									//List codeList = basicMaintenanceDao.getDeptListByCpnyID(paramMap);
									List codeList=deptList;
									tr+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									
									if(dField.equals("PARTTIME_DEPT")){
										tr+="<option value=' '>请选择</option>";
								    }
										//td+="<option>请选择</option>";
								
								
									
									for(Object map : codeList){
										Map temp = (Map) map;
										int level=-1;
										if(temp.get("DEPT_LEVEL")==null){
											level=0;
										}else{
											level=Integer.parseInt(temp.get("DEPT_LEVEL").toString());
										}
										tr+=("<option ");
										if(mapL.get(dField)!=null){
											if(mapL.get(dField).equals(temp.get("DEPTNO"))){
												tr+="selected='selected' ";
											}
										}
										
										
										tr+="value='"+temp.get("DEPTNO")+"'";
										tr+=">";
										for(int f=0;f<level;f++){
											tr+="&nbsp;&nbsp;";
										}
										tr+=temp.get("DEPTNAME")+"</option>";
									}
									tr+="</select></td>";
								}
							else if(tableName.equals("SY_CODE")||tableName.equals("0")){
								List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
								tr+="<td><select  ";
								if(code.equals("123351")){
									tr+=" ";
								}else{
									//if(dField.equals("GRADE_LEVEL")){
										//tr+=" onchange='getZhiDengAndZhiJi("+dField+"_"+cloumeNum+","+cloumeNum+")'";
									//}
									//tr+=" onchange='changName("+dField+"_"+cloumeNum+",this.id,this.value,"+cloumeNum+")'";
								}
								tr+=" name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
								
								if(!"123444".equals(paramMap.get("PARENT_CODE_NO").toString())&&!"1365".equals(paramMap.get("PARENT_CODE_NO").toString())){
									//tr+="<option>请选择</option>";
								}
								if(dField.equals("PARTTIME_DUTY")||dField.equals("PARTTIME_POSITION_NO")){
							    	tr+="<option value=' '>请选择</option>";
							    }
								for(Object map : codeList){
									Map temp = (Map) map;
									tr+=("<option ");
									if(paramMap.get("PARENT_CODE_NO").equals("123444")){
										dField="TRANS_CODE";
									}
									if(paramMap.get("PARENT_CODE_NO").equals("1365")){
										dField="TRANS_CODE";
									}
									if(paramMap.get("PARENT_CODE_NO").equals("1360")){
										dField="TRANS_CODE";
									}
									if(mapL.get(dField)!=null&&mapL.get(dField).equals(temp.get("CODE_NO"))){
										tr+="selected='selected' ";
									}
									
									tr+="value='"+temp.get("CODE_NO")+"'";
									tr+=(">"+temp.get("CODE_NAME")+"</option>");
								}
								tr+="</select></td>";
							}else{
								
								
								
								paramMap.put("TABLENAME",tableName);
								paramMap.put("PARENTTABLEFIELD",mapTitile.get("PARENT_TABLE_FIELD"));
								String sql="";
								if(tableName.equals("HR_DEPARTMENT")){
									sql=" get_dept_name(DEPTNO,'"+admin.getLanguage()+"') CODE_NAME";
									
								}
								else if(tableName.equals("HR_POST_GRADE")){
									sql="distinct(get_global_name("+mapTitile.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"')) CODE_NAME";
								}
								else{
									sql="get_global_name("+mapTitile.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
								}
								paramMap.put("SQL",sql);
								List codeList = basicMaintenanceDao.getParamCodeListByTableName(paramMap) ;
								tr+="<td class='td_center'><select ";
								//||code.equals("123360"
								if(code.equals("123351")){
									tr+=" ";
								}else if((code.equals("123359")&&dField.equals("DUTY_NO"))  ||(code.equals("123360")&&dField.equals("DUTY_NO"))){
									tr+="onchange='dutySelect("+cloumeNum+")' ";
								}else{
									tr+="onchange='changName("+dField+"_"+cloumeNum+",this.id,this.value,"+cloumeNum+")'";
								}
								tr+="name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
									//tr+="<option>请选择</option>";
								if(dField.equals("PARTTIME_DUTY")||dField.equals("PARTTIME_POSITION_NO")){
							    	tr+="<option value=' '>请选择</option>";
							    }
								
								
								for(Object map : codeList){
									Map temp = (Map) map;
									String tableId=mapTitile.get("PARENT_TABLE_FIELD").toString();
									String tableCodeNo="";
									if(mapL.get(dField)!=null){
										 tableCodeNo=mapL.get(dField).toString();
									}else{
										tableCodeNo="";
									}
									
									
									tr+="<option ";
									if(tableCodeNo.equals(temp.get("CODE_NO"))){
										tr+="selected='selected'";
									}
									tr+=(" value='"+temp.get("CODE_NO")+"'");
									
									tr+=(">"+temp.get("CODE_NAME")+"</option>");
								}
								tr+="</select></td>";
							}
							
						}else{
							paramMap.put("TABLENAME",tableName);
							paramMap.put("PARENTTABLEFIELD",mapTitile.get("PARENT_TABLE_FIELD"));
							String sql="";
							if(tableName.equals("HR_DEPARTMENT")){
								paramMap.put("TYPE", tableName);
								paramMap.put("ADMINID", admin.getAdminID()) ;
								paramMap.put("CPNYID", admin.getCpnyId());
								paramMap.put("userNo", admin.getUserNo());
								paramMap.put("deptNo", admin.getDeptNo());
								paramMap.put("specialParam", admin.getSpecialParam());
								
								sql=" get_dept_name(DEPTNO,'"+admin.getLanguage()+"') CODE_NAME";
								
							}else if(tableName.equals("SY_CODE")){
								paramMap.put("TYPE", tableName);
								paramMap.put("PARENT_CODE_NO", mapTitile.get("PARENT_CODE_NO"));
								sql="get_global_name("+mapTitile.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
							}else{
								paramMap.put("TYPE", tableName);
								sql="get_global_name("+mapTitile.get("PARENT_TABLE_FIELD")+",'"+admin.getLanguage()+"') CODE_NAME";
							}
							paramMap.put("SQL",sql);
							
							List codeList = basicMaintenanceDao.getParamCodeListByTableName(paramMap) ;
							String deptname="";
							
							
							for(Object map : codeList){
								Map temp = (Map) map;
								String tableId=mapTitile.get("PARENT_TABLE_FIELD").toString();
								String tableCodeNo="";
								//dField
								if(mapL.get(dField)!=null){
									 tableCodeNo=mapL.get(dField).toString();
								}else{
									tableCodeNo="";
								}
								if(tableCodeNo.equals(temp.get("CODE_NO"))){
									deptname=temp.get("CODE_NAME").toString();
								}
								
								
								
							}	
							
							
							
							
							tr+="<td class='td_center'>"+deptname+"";
							tr+="<input type='hidden' id='"+dField+"_"+cloumeNum+"' value='"+(mapL.get(dField))+"' name='"+dField+"_"+cloumeNum+"'/></td>";
						}		
							
							
							
							
							
							
							
							
						}else if(nameType.equals("3")){
							String data="";
							if(mapL.get(dField)==null){
								data="";
							}else{
								data=mapL.get(dField).toString().substring(0,10);
							}
							
							if(TransConfigFlag.equals("2")){
								if(mapL.get(name)!=null){
									if(dField.equals("TRANS_ORDER_ENDDATE")){
									    tr+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+data+"'/></td>";
									}else{
										tr+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+data+"'/></td>";
									}
								}else{
									if(dField.equals("TRANS_ORDER_ENDDATE")){
									    tr+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+data+"'/></td>";
									  //tr+="<td class='td_center'><input id="+dField+"_"+cloumeNum+" name="+dField+"_"+cloumeNum+" type='text' value=''/></td>" ;
									}else{
										tr+="<td class='td_center'><input name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"' d='END_DATE0' name='END_DATE0' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);' value='"+data+"'/></td>";
										//tr+="<td class='td_center'><input id="+dField+"_"+cloumeNum+" name="+dField+"_"+cloumeNum+" type='text' value=''/></td>" ;
									}
								}
							}
							//显示
							else{
								tr+="<td class='td_center'>"+data+"";
								tr+="<input type='hidden' value='"+data+"' id='"+dField+"_"+cloumeNum+" name='"+dField+"_"+cloumeNum+" ' /></td>";
							}
						}else if(nameType.equals("4")){
							LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
							paramMap.put("ADMINID", admin.getAdminID()) ;
							paramMap.put("CPNYID", admin.getCpnyId());
							paramMap.put("userNo", admin.getUserNo());
							paramMap.put("deptNo", "deptno");
							paramMap.put("specialParam", admin.getSpecialParam());
							
							
							List	deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
							tr+="<td class='td_center'><select name='"+dField+"_"+cloumeNum+"' id='"+dField+"_"+cloumeNum+"'>";
							//tr+="<option>请选择</option>";
							for(int l=0;l<deptList.size();l++){
								Map m2=(Map)deptList.get(l);
								tr+="<option value='"+m2.get("DEPTNO")+"'>"+m2.get("DEPTNAME")+"</option>";
							}
							tr+="</select></td>";
						}
						
						bool=true;
						break;
						}
					
				}
				if(bool==false){
					tr+="<td class='td_center'>" +
							"" +
							"</td>";
				}
				
				
				
			}
			
			
			
			tr+="<td class='td_center'>" +
					"<input type='button' value='History' onclick='falingHistory("+cloumeNum+")'/>" +
					"</td>";
			
			
			
			
			
			tr+="</tr>";
			mapTitle.put(String.valueOf(i), tr);
		}
		return mapTitle;
	}
	/**
	 * 跳转到入职发令页面
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrderExamine")
	public ModelAndView viewOrderExamineList(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)
			throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		//modelMap.put("postGradeList", postGradeList);
		if(paramMap1.get("OrderType")!=null&&!request.getParameter("seach_OrderType").equals("")){
			paramMap1.put("CODE", paramMap1.get("OrderType"));
			paramMap1.put("LAN",admin.getLanguage());
			paramMap1.put("CPNYID", admin.getCpnyId());
			paramMap1.put("TRANS_SEARCH_FLAG", "1");
			List listTitle=this.transferOrderDao.getTranferOrderTitileInside(paramMap1);
			modelMap.put("listTitle", listTitle);
			List insideList=this.transferOrderSer.viewTranferOrderinsideList(request);
			modelMap.put("insideList", insideList);
			
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transferOrderSer.getTranferOrderinsideListCnt(request,paramMap1));
		}
		
		
		return new ModelAndView("/hrm/transferOrder/viewOrderExamine", modelMap);
	}
	
	/**
	 * 导出职发令页面Excel
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-3 上午1:38:34 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrderExamineExcel")
	public ModelAndView viewOrderExamineExcelList(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		//modelMap.put("postGradeList", postGradeList);
		if(paramMap1.get("OrderType")!=null&&!request.getParameter("seach_OrderType").equals("")){
			paramMap1.put("CODE", paramMap1.get("OrderType"));
			paramMap1.put("LAN",admin.getLanguage());
			paramMap1.put("CPNYID", admin.getCpnyId());
			paramMap1.put("TRANS_SEARCH_FLAG", "1");
			List listTitle=this.transferOrderDao.getTranferOrderTitileInside(paramMap1);
			modelMap.put("listTitle", listTitle);
			List insideList=this.transferOrderSer.viewTranferOrderinsideList(request);
			modelMap.put("insideList", insideList);
			
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transferOrderSer.getTranferOrderinsideListCnt(request,paramMap1));
		}
		
		
		return new ModelAndView("/hrm/transferOrder/viewOrderExamineExcel", modelMap);
	}
	
	/**
	 * 批量取消调令(batch cancel Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelPlurality")
	@ResponseBody
	public Map<String, Object> cancelPlurality(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		request.getParameter("seach_biaohao");
		int result = this.transferOrderSer.cancelPluralityBatchInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0503");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
			map.put("statusCode", "200");
			map.put("type",request.getParameter("seach_biaohao"));
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	/**
	 * 查询旧的法令页面
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrderExamineSerach")
	public ModelAndView getOrderExamineListSerach(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)
			throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		//modelMap.put("postGradeList", postGradeList);
		
			paramMap1.put("CODE",request.getParameter("code"));
			paramMap1.put("LAN",admin.getLanguage());
			paramMap1.put("CPNYID", admin.getCpnyId());
			paramMap1.put("TRANS_SEARCH_FLAG", "1");
			List listTitle=this.transferOrderDao.getTranferOrderTitile(paramMap1);
			modelMap.put("listTitle", listTitle);
			List insideList=this.transferOrderSer.viewTranferOrderinsideListSerach(request);
			modelMap.put("insideList", insideList);
			
			//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.TransferOrderSer.getTranferOrderinsideListCnt(request,paramMap1));
	
		return new ModelAndView("/hrm/transferOrder/viewOrderExamineSerach", modelMap);
	}
	
	/**
	 * 根据选择的员工编号查询指定员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSelectedEmpList")
	@ResponseBody
	public Map getSelectedEmpList(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
	 	List deptNoList=this.transferOrderSer.getSelectedEmpList(request);
	 	map.put("perCnt", deptNoList.size());
		map.put("empinfo", deptNoList);
		
		return map;
	}
	/**
	* 根据职责级联职级名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dutySelect")
	@ResponseBody
	public Map dutySelect(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		//职责duty
		List dutyList=this.transferOrderSer.getPostGradeNoByDuty(request);
		for(int i=0;i<dutyList.size();i++){
			Map m=(Map)dutyList.get(i);
			map.put(m.get("POSTNO").toString(),m.get("POSTNAME").toString());
		}
		return map;
	}
	/**
	* 根据职级获得职级名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2013 10:45:16 AM 
	* @version V1.0  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPositionInfoByPostGradeNo")
	@ResponseBody
	public Map getPositionInfoByPostGradeNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		
		List postList=this.transferOrderSer.getPositionInfoByPostGradeNo(request);
		for(int i=0;i<postList.size();i++){
			Map m=(Map)postList.get(i);
			map.put(m.get("POST_NO").toString(),m.get("POSTNAME").toString());
		}
		return map;
	}
	
	/**
	 * 根据职种获得职位
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-16 下午9:28:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getZhiZhongAndZhiWei")
	@ResponseBody
	public Map getZhiZhongAndZhiWei (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getZhiZhongAndZhiWeiList=this.transferOrderSer.getZhiZhongAndZhiWei(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<getZhiZhongAndZhiWeiList.size();i++){
				map.put((String)((Map) getZhiZhongAndZhiWeiList.get(i)).get("POSITION_NO"), ((Map) getZhiZhongAndZhiWeiList.get(i)).get("POSITION_NO_NAME"));
			}
			return map;
		
	}
	/**
	 * 根据ID获得级联
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-24下午8:45:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSelectTag")
	@ResponseBody
	public Map viewSelectTag (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getZhiZhongAndZhiWeiList=this.transferOrderSer.viewSelectTag(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<getZhiZhongAndZhiWeiList.size();i++){
				Map m=(Map)getZhiZhongAndZhiWeiList.get(i);
				map.put(m.get("CODE_NO").toString(),m.get("CODE_NAME"));
			}
			return map;
		
	}
	
	/**
	 * delete InsideSaveData
	* @Copyright:   LDCC (c)
	* @Company:     LDCC
	* @Description: delete InsideSaveData
	* @author jjy
	* @date 2013-9-24下午8:45:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DeleteEachExperienceInside")
	@ResponseBody
	public Map DeleteEachExperienceInside(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		String type=request.getParameter("type");
		int result=-1;
		String[] personid=request.getParameterValues("personid");
		String exp_inside_no = request.getParameter("exp_inside_no");
		
		result=transferOrderSer.deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(request);
		logger.debug("result:::"+result);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			//map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_success", request));// 发令成功
			//map.put("navTabId", "hr0202");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		
		return map;	
	}
	
	/**
	 * 删除奖励或惩戒的一行数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-29 下午04:12:20 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteCurrentRewardOrPunishment")
	@ResponseBody
	public Map deleteCurrentRewardOrPunishment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		result = this.transferOrderSer.deleteCurrentRewardOrPunishment(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;	
	}
	
	/**
	* 根据调令类型查询人员的调令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio   heran@ait.net.cn 
	* @date Aug 13, 2013 10:44:15 AM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHrExperienceInsideByPersonId1")
	@ResponseBody
	public LinkedHashMap getHrExperienceInsideByPersonId1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap<String, String> mapTitle = new LinkedHashMap<String, String>();
		List listTitle=this.transferOrderSer.getTranferOrderTitile(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		//paramMap.put("PARENT_CODE_NO" "");
		paramMap.put("PARENT_CODE_NO", "125231");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String transDate=request.getParameter("diaolingDate");
		List list=this.transferOrderSer.getHrExperienceInsideByPersonId(request);
		int cloumeNum=Integer.valueOf(request.getParameter("cloumeNum").toString());
		String td="";
		String key="";
		//[{PERSON_ID=30070018, EMPID=CH000010, LOCAL_NAME=李映岱, ENGLISH_NAME=LILI YING DAI, 
		//KOREAN_NAME=null, DEPTNO=LGECHQHLBJ, DEPTNO1=LGECHQHLBJ, CODE_NAME=唐山, DEPT_NAME=唐山, 
		//GRADE_LEVEL=null, GRADE_LEVEL_NAME=null, DUTY_NO=null, DUTY_NO_NAME=null, POST_GRADE_NO=G2, 
		//POST_GRADE_NO_NAME=null, POST_NO=null, POST_NO_NAME=null, POST_NAME=null, 
		//DATE_STARTED=2001-12-01 00:00:00.0, STATUS_CODE=null, STATUS_NAME=null, STATUS_CODE_NAME=null, 
		//EMP_OFFICE=15119, EMP_OFFICE_NAME=在职, NOW_DEPARTMENT_DATE=null, PROMOTION_DATE=null,
		//DATE_LEFT=2001-12-02 00:00:00.0, END_PROBATION_DATE=2001-12-01 00:00:00.0, EMPLOYMENT_TYPE=null, 
		//EMPLOYMENT_TYPE_NAME=null, CONTRACT_TYPE=null, CONTRACT_TYPE_NAME=null, WORKING_TIME=null, WORKING_TIME_NAME=null,
		//EMP_TYPE_CODE= , EMP_TYPE_NAME=null, EMP_TYPE_CODE_NAME=null, NATIONALITY_CODE= , NATIONALITY_NAME=null, 
		//NATIONALITY_CODE_NAME=null, JOB_TYPE=null, JOB_TYPE_NAME=null, SOCIAL_SECURITY_AREA=null, 
		//SOCIAL_SECURITY_AREA_NAME=null, POSITION_NO=null, POSITION_NAME=null, POSITION_NO_NAME=null, 
		//IN_THE_DIFFERENCE=null, INSURANCE_TYPE_CODE=null, INSURANCE_TYPE_NAME=null, INSURANCE_TYPE_CODE_NAME=null, REG_TYPE_CODE=null, REG_TYPE_CODE_NAME=null, WORK_AREA= , WORK_AREA_NAME=null, CPNY_ID=LGECH, DEPT_DISTINGUISH_NO=null, DEPT_DISTINGUISH_NAME=null, DEPT_TYPE=null, DEPT_TYPE_NAME=null, OLD_POST_GRADE_NO=null, OLD_POST_GRADE_NAME=null, PAY_STEP=null, PAY_STEP_NAME=null, CALC_FLAG=1, IS_CALC_FLAG=1, BN_CALC_FLAG=1, JOB_CLASS=null, JOB_CLASS_NAME=null, SERVICES_BELONG=null, SINGLETON_FEMALE_CARD=null, MAN_HOUR_SYSTEM=null, MAN_HOUR_SYSTEM_NAME=null, ENTRY_AREA_NAME=null, TAX_AREA_NAME=null, STAT_NO=null, CUR_DEPTNO=LGECHQHLBJ, CUR_GRADE_LEVEL=null, CUR_DUTY_NO=null, CUR_POST_GRADE_NO=G2, CUR_POST_NO=null, CUR_POSITION_NO=null}]
		for(int i=0;i<list.size();i++){
		
		Map map1=(Map) list.get(i);		
		td+="<td class='td_center'><input type='checkbox' name='T1' id='' value='"+cloumeNum+"'/></td>";//checkbox
		//td+="<input type='text' name='' id=''/>";
		td+="<td class='td_center'><input name='sdate_"+cloumeNum+"' id='sdate_"+cloumeNum+"' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);'/></td>";//发令日期
		td+="<td class='td_center'><input name='edate_"+cloumeNum+"' id='edate_"+cloumeNum+"' class='date required' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' value='"+transDate+"' onClick='setdate(this);'/></td>"; //生效日期
		td+="<td class='td_center'>"+map1.get("EMPID")+"<input type='hidden' name='personid_"+cloumeNum+"' id='' value='"+map1.get("PERSON_ID")+"'/></td>";
		td+="<td class='td_center'>"+map1.get("LOCAL_NAME")+"</td>";
		td+="<td class='td_center'>"+map1.get("DEPT_NAME")+"<input type='hidden' name='DEPTNO_"+cloumeNum+"' value='"+map1.get("DEPTNO")+"'/></td>";//以后修改，读取数据可能不对
		td+="<td class='td_center'>"+map1.get("POSITION_NO")+"<input type='hidden' name='POSITION_NO"+cloumeNum+"' value='"+map1.get("POSITION_NO")+"'/></td>";//以后修改，读取数据可能不对
		td+="<td class='td_center'>"+map1.get("DUTY_NO")+"<input type='hidden' name='DUTY_NO"+cloumeNum+"' value='"+map1.get("DUTY_NO")+"'/></td>";//以后修改，读取数据可能不对
		td+="<td class='td_center'>"+map1.get("POST_GRADE_NO")+"<input type='hidden' name='POSITION_NO"+cloumeNum+"' value='"+map1.get("POSITION_NO")+"'/></td>";//以后修改，读取数据可能不对
		td+="<td class='td_center'>"+map1.get("EMP_TP")+"<input type='hidden' name='EMP_TP"+cloumeNum+"' value='"+map1.get("EMP_TP")+"'/></td>";//以后修改，读取数据可能不对
		td+="<td class='td_center'>"+map1.get("WORK_AREA")+"<input type='hidden' name='WORK_AREA"+cloumeNum+"' value='"+map1.get("WORK_AREA")+"'/></td>";//以后修改，读取数据可能不对
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		td+="<td class='td_center'><select name='saddress_"+cloumeNum+"'>";
			for(int j=0;j<codeList.size();j++){
				Map map=(Map) codeList.get(j);
				td+="<option value='"+map.get("CODE_NO")+"'>"+map.get("CODE_NAME")+"</option>";
				
			}
		td+="</select></td>";	
		td+="<td class='td_center'><input type='text' name='remark_"+cloumeNum+"'/></td>";
		mapTitle.put(String.valueOf(i), td);
		td="";
		cloumeNum++;
		}
		
		return mapTitle;
	}
	/**
	* 调令保存(临时保存)
	* 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2014 10:45:16 AM 
	* @version V1.0  new ModelAndView("/hrm/transferOrder/HrExperienceInside_send_1", modelMap); ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveHrExperienceInside_send_1")
	@ResponseBody
	public Map SaveHrExperienceInside_send(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result=transferOrderSer.SaveHrExperienceInsideSave_send_1(request);
		 if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("zxc.hr.alert.message.SAVE_SUCCESS", request));// 发令成功
				//map.put("navTabId", "hr0202");
			} else if (result == 2) {
				map.put("statusCode", "300");
				// 没有决裁者，请先配置决裁者
				map.put("message", TipMessage.getTipMessage("hr.alert.message.no_affiram", request));
			} else if (result > 2) {
				map.put("statusCode", "300");
				//此result员工未变更部门或职责，不允许做职责变更发令！
				map.put("message", result + TipMessage.getTipMessage("hr.transfer.message.changeDutynoOrDeptnoPlease", request));
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_fail", request));// 发令失败
			}
		return map;
	}
	
	
	/**
	* 调令保存(临时保存)
	* 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 13, 2014 10:45:16 AM 
	* @version V1.0  new ModelAndView("/hrm/transferOrder/HrExperienceInside_send_1", modelMap); ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveHrExperienceInside_send_2")
	@ResponseBody
	public Map SaveHrExperienceInside_send_2(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result=transferOrderSer.SaveHrExperienceInsideSave_send_2(request);
		 if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("zxc.hr.alert.message.SAVE_SUCCESS", request));// 发令成功
				//map.put("navTabId", "hr0202");
			} else if (result == 2) {
				map.put("statusCode", "300");
				// 没有决裁者，请先配置决裁者
				map.put("message", TipMessage.getTipMessage("hr.alert.message.no_affiram", request));
			} else if (result > 2) {
				map.put("statusCode", "300");
				//此result员工未变更部门或职责，不允许做职责变更发令！
				map.put("message", result + TipMessage.getTipMessage("hr.transfer.message.changeDutynoOrDeptnoPlease", request));
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_fail", request));// 发令失败
			}
		return map;
	}
	
	/**
	* 查询旧的调令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio  heran@ait.net.cn 
	* @date Aug 30, 2013 10:45:16 AM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHrExperienceInsideSaveByTransCode_1")
	@ResponseBody
	public LinkedHashMap getHrExperienceInsideSaveByTransCode_1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, String> mapTitle = new LinkedHashMap<String, String>();
		String code="";
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}else{
			code=request.getParameter("OrderType");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//查询hr_experience_inside_save 调令
		String tr="";
		List list1=transferOrderSer.getHrExperienceInsideSaveByTransCode(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");	
		paramMap.put("PARENT_CODE_NO", "125231");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String transDate=request.getParameter("diaolingDate");
		int cloumeNum=-1;
		for(int i=0;i<list1.size();i++){
			Map map1=(Map) list1.get(i);		
			tr+="<td class='td_center'><input type='checkbox' name='T1' id='' value='"+i+"'/><input type='hidden' value='"+map1.get("EXP_INSIDE_NO")+"' id='EXPNO_"+i+"' /></td>";//checkbox
			//td+="<input type='text' name='' id=''/>";
			tr+="<td class='td_center'><input name='sdate_"+i+"' id='sdate_"+i+"' value='"+map1.get("TRANS_ORDER_DATE").toString().substring(0,10)+"' readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' onClick='setdate(this);'/></td>";//发令日期
			tr+="<td class='td_center'><input name='edate_"+i+"' id='edate_"+i+"' value='"+map1.get("TRANS_ORDER_ENDDATE").toString().substring(0,10)+"'  readonly='true' format='yyyy-MM-dd'   type='text' yearstart='-50' yearend='5' value='"+transDate+"' onClick='setdate(this);'/></td>"; //生效日期
			tr+="<td class='td_center'>"+map1.get("EMPID")+"<input type='hidden' name='personid_"+i+"' id='' value='"+map1.get("PERSON_ID")+"'/></td>";
			tr+="<td class='td_center'>"+map1.get("LOCAL_NAME")+"</td>";
			tr+="<td class='td_center'>"+map1.get("DEPT_NAME")+"<input type='hidden' name='DEPTNO_"+i+"' value='"+map1.get("DEPTNO")+"'/></td>";//以后修改，读取数据可能不对
			tr+="<td class='td_center'>"+map1.get("POSITION_NO")+"<input type='hidden' name='POSITION_NO"+i+"' value='"+map1.get("POSITION_NO")+"'/></td>";//以后修改，读取数据可能不对
			tr+="<td class='td_center'>"+map1.get("DUTY_NO")+"<input type='hidden' name='DUTY_NO"+i+"' value='"+map1.get("DUTY_NO")+"'/></td>";//以后修改，读取数据可能不对
			tr+="<td class='td_center'>"+map1.get("POST_GRADE_NO")+"<input type='hidden' name='POSITION_NO"+i+"' value='"+map1.get("POSITION_NO")+"'/></td>";//以后修改，读取数据可能不对
			tr+="<td class='td_center'>"+map1.get("EMP_TP")+"<input type='hidden' name='EMP_TP"+i+"' value='"+map1.get("EMP_TP")+"'/></td>";//以后修改，读取数据可能不对
			tr+="<td class='td_center'>"+map1.get("WORK_AREA")+"<input type='hidden' name='WORK_AREA"+i+"' value='"+map1.get("WORK_AREA")+"'/></td>";//以后修改，读取数据可能不对
			List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
			tr+="<td class='td_center'><select name='saddress_"+i+"'>";
			for(int j=0;j<codeList.size();j++){
					Map map=(Map) codeList.get(j);
					tr+="<option value='"+map.get("CODE_NO")+"'";
					if(map1.get("SENDADDRESS_NO").equals(map.get("CODE_NO"))){
						tr+=" selected = 'selected' ";
					}
					tr+=">"+map.get("CODE_NAME")+"</option>";
				}
				tr+="</select></td>";	
				tr+="<td class='td_center'><input type='text' name='remark_"+i+"' value="+map1.get("REMARK")+"></td>";
			mapTitle.put(String.valueOf(i), tr);
			tr="";
		}
		return mapTitle;
	}
	/**
	 * 跳转到导入错误页面（view Hire）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/errorExcel")
	public ModelAndView errorExcel(HttpServletRequest request,HttpServletResponse response ,ModelMap modelMap)
			throws Exception {		 AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		 String name=request.getParameter("errorExcelValue");
		 String  errorExcelValue="";
		  try{  
	          errorExcelValue = URLDecoder.decode(name , "utf-8");  
	      }catch(Exception e){  
	        e.printStackTrace();  
	     }  
		modelMap.put("errorExcelValue", errorExcelValue);
		 
		 
		return new ModelAndView("/hrm/transferOrder/errorExcel", modelMap);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitSendAndSendOff")
	@ResponseBody
	public Map submitSendAndSendOff(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List statList=null;
		//	List statList = this.paForleftMenSer.getArStatisticList(request);
			

		Map<String, Object> map = new HashMap<String, Object>();

		Map messMap = new LinkedHashMap();
		messMap = (LinkedHashMap)this.transferOrderSer.submitAddHrDispatch(request);
		//messMap = (LinkedHashMap) this.paForleftMenSer.submitAddPaForleftMen(request);
		if (messMap.get("scode").toString().equals("1")) {
			map.put("statusCode", "200");
			//map.put("navTabId", "hr0919");
			//map.put("callbackType", "closeCurrent");
		//	map.put("callbackType", "forward");
			map.put("callbackType", "closeCurrent");
			map.put("navTabId", "hr0919");
		//	map.put("forwardUrl", "/hrm/transferOrder/viewSendAndSendOff?pageNum=1&menuNo=125227&navTabId=hr0919");
			map.put("message", TipMessage.getTipMessage("alert.message.pa.salary.add_success",request));//修改成功
			
		} else if (messMap.get("scode").toString().equals("0")) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.pa.salary.add_fail.dispatch",request));//修改失败
		} 
		//map.put("result", messMap.get("scode"));
		return map;
	}
	
	/**************************************************************************************************************/
	// @Create date: 2014.06.11
	@RequestMapping(value = "/getListBySelect")
	public String getListBySelect(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("parentNo", request.getParameter("parentNo"));
		request.setAttribute("selected", request.getParameter("selected"));
		request.setAttribute("seq", request.getParameter("seq"));
		return "/hrm/transferOrder/SelectStateTagDispatch";
	}

	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatesubmitSendAndSendOff")
	@ResponseBody
	public Map updatesubmitSendAndSendOff(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List statList=null;
		//	List statList = this.paForleftMenSer.getArStatisticList(request);
			

		Map<String, Object> map = new HashMap<String, Object>();

		Map messMap = new LinkedHashMap();
		messMap = (LinkedHashMap)this.transferOrderSer.updatesubmitSendAndSendOff(request);
		//messMap = (LinkedHashMap) this.paForleftMenSer.submitAddPaForleftMen(request);
		if (messMap.get("scode").toString().equals("1")) {
			map.put("statusCode", "200");
			//map.put("navTabId", "hr0919");
			//map.put("callbackType", "closeCurrent");
		//	map.put("callbackType", "forward");
			map.put("callbackType", "closeCurrent");
			map.put("navTabId", "hr0919");
		//	map.put("forwardUrl", "/hrm/transferOrder/viewSendAndSendOff?pageNum=1&menuNo=125227&navTabId=hr0919");
			map.put("message", TipMessage.getTipMessage("alert.message.pa.salary.add_success",request));//修改成功
			
		} else if (messMap.get("scode").toString().equals("0")) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.pa.salary.add_fail",request));//修改失败
		} 
		//map.put("result", messMap.get("scode"));
		return map;
	}
	
	/**
	 * 下载离职人员导入模板
	 */
	@RequestMapping(value = "/downloadResignationTemplate")
	public void downloadResignationTemplate(HttpServletRequest request
			, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		List aliasNameList = new ArrayList();
		aliasNameList.add("社编*");
		aliasNameList.add("离职日期*");
		aliasNameList.add("离职类型Cd*");
		aliasNameList.add("离职原因Cd*");
		
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "CH000002");
		map.put("CELL1", "20141021");
		map.put("CELL2", "1313");
		map.put("CELL3", "23605");
		
		list.add(map);
		
		paramMap.put("language", paramMap.get("interLanguage") != null ? paramMap.get("interLanguage"):"zh");
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else if(paramMap.get("CPNY_ID")==null || paramMap.get("CPNY_ID").equals("")){
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}
		List tipList = new ArrayList();		
		//离职类型code TIP
		paramMap.put("PARENT_CODE_NO", 643);
		List codeList = this.transferOrderSer.getCodeListByParam(paramMap);
        Object[] TypeList = codeList.toArray();
        String resignTpHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	resignTpHeader = resignTpHeader + "\n"+ ((Map)TypeList[i]).get("CODE")+":"+((Map)TypeList[i]).get("CODE_NAME");
        }
        LinkedHashMap tipMap_Eval = new LinkedHashMap();
        tipMap_Eval.put("TIP_COLUMN", "离职类型Cd*");
        tipMap_Eval.put("TIP_CONTENT", resignTpHeader);
        tipList.add(tipMap_Eval);
        //离职原因code TIP
        paramMap.put("PARENT_CODE_NO", 4265);
  		List resignReasonList = this.transferOrderSer.getCodeListByParam(paramMap);
  		Object[] rrTypeList = resignReasonList.toArray();
        String resignReasonHeader = "说明:";
        for (int i = 0; i < rrTypeList.length; i++) {
        	resignReasonHeader = resignReasonHeader + "\n"+ ((Map)rrTypeList[i]).get("CODE")+":"+((Map)rrTypeList[i]).get("CODE_NAME");
        }  
        LinkedHashMap tipMap_Area = new LinkedHashMap();
        tipMap_Area.put("TIP_COLUMN", "离职原因Cd*");
        tipMap_Area.put("TIP_CONTENT", resignReasonHeader);
        tipList.add(tipMap_Area);
		
		String name = "TempEmpResignImport";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByNameWithHeaderTip(
				request, response, modelMap, sqlContentmap, aliasNameList, null, name, tipList);
	}
	
	/**
	 * 临时职人员离职批量数据导入结果查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignationImpResultList")
	public ModelAndView viewResignationImpResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("CPNY_ID", admin.getCpnyId());
		searchMap.put("UPDT_USER", admin.getEmpID());		
		List itemList = this.transferOrderSer.getTempEmpResignDataImportResultList(request, searchMap);
		int impTotalCnt = this.transferOrderSer.getTempEmpResignDataImportResultListCnt(request, searchMap);	
		int impErrCnt   = this.transferOrderSer.getTempEmpResignDataImportResultListErrCnt(request, searchMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215992"));
		return new ModelAndView("/hrm/transferOrder/viewResignationImpResultList",modelMap);
	}
	
	/**
	 * 临时职人员离职数据导入  导入的数据列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewResignationImpResultListExcel")
	public ModelAndView viewResignationImpResultListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("CPNY_ID", admin.getCpnyId());
		List itemList = this.transferOrderSer.getTempEmpResignDataImportResultListExcel(request, searchMap);		
		modelMap.put("searchMap", searchMap);
		modelMap.put("MDATA", itemList);
		return new ModelAndView("/hrm/transferOrder/viewResignationImpResultListExcel",modelMap);
	}
	
	/**
	 * 离职人员数据导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createTempEmpResignDataImportResult")
	@ResponseBody
	public int createTempEmpResignDataImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("IMP_EMPNO", admin.getEmpID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		String result = this.transferOrderSer.importTempEmpResignDataRAWFromExcel(request,paramMap);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 添加离职发令页面查询（view EDIT Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignEditList")
	public ModelAndView viewResignEditList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("STATE", 10);//取暂存状态列表
		List resignEditList = transferOrderSer.getViewResignEditList(paramMap, request);
		int resignEditListCnt = transferOrderSer.getViewResignEditCnt(paramMap, request);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("resignEditList", resignEditList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, resignEditListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278687")) ;
		return new ModelAndView("/hrm/transferOrder/viewResignEditList", modelMap);
	}
	/**
	 * 离职发令申请
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignReqList")
	public ModelAndView viewResignReqList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}	
		if(!paramMap.containsKey("RESIGN_NOS")){
			String   ls = "";
			String[] isChecked = request.getParameterValues("resignReqCKB");
			if(isChecked != null)
			{
				for(int i = 0; i < isChecked.length; i++){
					ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
				}
			}
			paramMap.put("RESIGN_NOS", ls);
		}
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("STATE", 20);//取保存状态列表
		List resignReqList 		= transferOrderSer.getViewResignEditList(paramMap, request);
		int resignReqListCnt 	= transferOrderSer.getViewResignEditCnt(paramMap, request);	
		String APPLY_TYPE_NO = "15823";//离职发令决裁线
		List affirmorList 		= transferOrderSer.getAffirmorList(APPLY_TYPE_NO, request);
		List resignPersonIdList = transferOrderSer.getViewResignEditListAll(paramMap, request);
		// 判断是否需要决裁1为开，0为关
		Map paramValueMap = new LinkedHashMap();
		paramValueMap.put("CPNY_ID", admin.getCpnyId());
		paramValueMap.put("TYPE", "resign");	
		int affirmFlag = transferOrderDao.getParamInfoValue(paramValueMap);
		paramMap.put("affirmFlag", affirmFlag);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("resignReqList", resignReqList);
		modelMap.put("resignPersonIdList", resignPersonIdList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, resignReqListCnt);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278687")) ;
		return new ModelAndView("/hrm/transferOrder/viewResignReqList", modelMap);
	}	
	
	/**
	 * 添加发令页面查询（view Add transfer order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpTransferOrderAddList")
	public ModelAndView viewTempEmpTransferOrderAddList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("navTabId", "hr0515");
		paramMap.put("MGT_SYSTEM", "C");  //G：取G系统管理的员工，C：取C系统管理的员工
		paramMap.put("TYPE", "upGrade");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

		List tempEmpList = transferOrderSer.getViewTempEmpList(paramMap, request);
		int tempEmpListCnt = transferOrderSer.getViewTempEmpCnt(paramMap, request);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("tempEmpList", tempEmpList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, tempEmpListCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278703")) ;
		return new ModelAndView("/hrm/transferOrder/viewTempEmpTransferOrderAddList", modelMap);
	}
	
	/**
	 * 临时职人员信息变更发令（用于部门、班号，级号的变更）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTransferOrderUpgrade")
	@ResponseBody
	public Map<String, Object> saveTransferOrderUpgrade(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = transferOrderSer.saveTransferOrderUpgrade(request);		
		return map;
	}
	/**
	 * 人员类型变更发令
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEmpTypeTransferOrderUpgrade")
	@ResponseBody
	public Map<String, Object> saveEmpTypeTransferOrderUpgrade(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn = transferOrderSer.saveEmpTypeTransferOrderUpgrade(request);		
		int result = (Integer)rtn.get("RET");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_success", request));// 发令成功
			map.put("navTabId", "hr0516");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
		} else if (result == 3){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}else {
			map.put("statusCode", "300");
			map.put("message",rtn.get("message"));// 发令失败
		} 
		return map;
	}
	
	/**
	 * 查看临时职发令列表(view transfer order list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpTransferOrderList")
	public ModelAndView viewTempEmpTransferOrderList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("ORDERDATEF")){
			paramMap.put("ORDERDATEF", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("ORDERDATET", DateUtil.getCurrentMonthLastDayStr());			
		}else{
			paramMap.put("EMPID",paramMap.get("dwz.person.empId"));
		}			
		paramMap.put("defaultCpny", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo());		
		modelMap.put("searchMap", paramMap);

        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		List transList = transferOrderSer.getTempEmpTransferOrderList(paramMap, request);
    		int transListCnt = this.transferOrderSer.getTempEmpTransferOrderListCnt(paramMap,request);
    		modelMap.put("transList", transList);
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, transListCnt);
        }
        
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278703")) ;
		return new ModelAndView("/hrm/transferOrder/viewTempEmpTransferOrderList",modelMap);
	}
	
	/**
	 * 修改发令页面查询（view Add transfer order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpTransferOrderEditList")
	public ModelAndView viewTempEmpTransferOrderEditList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());		
		paramMap.put("TRANS_NO", "1365");
		paramMap.put("navTabId", "hr0515");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

		List editTrList = transferOrderSer.getTempEmpTransferOrderEditList(paramMap, request);
		int editTrListCnt = transferOrderSer.getTempEmpTransferOrderEditListCnt(paramMap, request);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("editTrList", editTrList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editTrListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278703")) ;
		return new ModelAndView("/hrm/transferOrder/viewTempEmpTransferOrderEditList", modelMap);
	}
	
	/**
	 * 发令页面查询（view transfer order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpTransferOrderDetail")
	public ModelAndView viewTempEmpTransferOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("navTabId", "hr0515");
		paramMap.put("MGT_SYSTEM", "C");  //G：取G系统管理的员工，C：取C系统管理的员工
		paramMap.put("TYPE", "upGrade");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

		List tempEmpTrList = transferOrderSer.getTempEmpTransferOrderList(paramMap, request);
		Map tempEmpTr = (Map) tempEmpTrList.get(0);
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("tempEmpTr", tempEmpTr);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278703")) ;
		return new ModelAndView("/hrm/transferOrder/viewTempEmpTransferOrderDetail", modelMap);
	}
	
	/**
	 * 下载临时职人员发令导入模板
	 */
	@RequestMapping(value = "/downloadTempEmpTransferOrderTemplate")
	public void downloadTempEmpTransferOrderTemplate(HttpServletRequest request
			, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//模板header&模板content
		List aliasNameList = new ArrayList();
		aliasNameList.add("社编*");
		aliasNameList.add("发令日期*");
		aliasNameList.add("发令原因*");
		aliasNameList.add("新部门NM");
		aliasNameList.add("新人员类型NM");
		aliasNameList.add("新班号NM");	
		aliasNameList.add("新职责NM");				
		aliasNameList.add("新工资级号NM");
		aliasNameList.add("新工资级号等级NM");
		aliasNameList.add("新基本工资");
		aliasNameList.add("新变动工资");
		aliasNameList.add("新年薪");
		aliasNameList.add("新ID卡号");
		aliasNameList.add("新职务");
		

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "CH000002");
		map.put("CELL1", "20141121");
		map.put("CELL2", "部门变更");
		map.put("CELL3", "[113145]山西支社");
		map.put("CELL4", "情报通信PS");
		map.put("CELL5", "");
		map.put("CELL6", "");
		map.put("CELL7", "");
		map.put("CELL8", "");
		map.put("CELL9", "");
		map.put("CELL10", "");
		map.put("CELL11", "");		
		map.put("CELL12", "");		
		map.put("CELL13", "");	
		
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//备注sheet
		List mapNameList = new ArrayList();
		List mapList = new ArrayList();
		mapNameList.add("部门参考");
		mapNameList.add("人员类型参考");
		mapNameList.add("职责参考");
		mapNameList.add("班号参考");
		mapNameList.add("工资级号参考");
		mapNameList.add("工资级号等级参考");
		mapNameList.add("职务参考");
		mapList.add("SELECT DEPTNO CONTENT, '['||DEPTNO||']'||ORG_NAME_LOCAL AS CONTENT1 FROM HR_DEPARTMENT WHERE ACTIVITY=1 AND CPNY_ID = '"+admin.getCpnyId()+"'ORDER BY DEPTNO");
		mapList.add("SELECT T.TEMP_EMPTYPE AS CONTENT, SY.CONTENT AS CONTENT1 FROM HR_TEMP_EMPTYPE   T,SY_GLOBAL_NAME SY WHERE T.TEMP_EMPTYPE = SY.NO(+) AND SY.LANGUAGE(+) = 'zh' AND CPNY_NAME = '"+admin.getCpnyId()+"' AND T.ACTIVITY = 1 AND NVL(T.TRANSFER_FLAG,'N')!='Y'");
		mapList.add("SELECT DISTINCT POSITION_NO CONTENT, POSITION_NO AS CONTENT1 FROM HR_EMPLOYEE WHERE CPNY_ID = '"+admin.getCpnyId()+"' AND POSITION_NO IS NOT NULL ORDER BY POSITION_NO");
		mapList.add("SELECT distinct SHIFT_NO AS CONTENT, SHIFT_NO AS CONTENT1 FROM HR_EMPLOYEE WHERE CPNY_ID = '"+admin.getCpnyId()+"' AND SHIFT_NO IS NOT NULL ORDER BY SHIFT_NO");
		mapList.add("SELECT distinct A.PAY_GRADE AS CONTENT, A.PAY_GRADE AS CONTENT1 FROM HR_EMP_PA_INFO A, HR_EMPLOYEE B WHERE A.PERSON_ID = B.PERSON_ID  AND B.CPNY_ID   = '"+admin.getCpnyId()+"' AND A.PAY_GRADE IS NOT NULL ORDER BY A.PAY_GRADE");
		mapList.add("SELECT distinct A.PAY_STEP AS CONTENT, A.PAY_STEP AS CONTENT1 FROM HR_EMP_PA_INFO A, HR_EMPLOYEE B WHERE A.PERSON_ID = B.PERSON_ID  AND B.CPNY_ID   = '"+admin.getCpnyId()+"' AND A.PAY_STEP IS NOT NULL ORDER BY A.PAY_STEP");
		mapList.add("SELECT distinct A.DESCRIPTION AS CONTENT, A.CONTENT AS CONTENT1 FROM V_SY_CODE A WHERE A.CPNY_ID   = '"+admin.getCpnyId()+"' AND A.PARENT_CODE_NO=14013573 AND A.LANGUAGE='zh' AND A.DESCRIPTION IS NOT NULL ORDER BY A.DESCRIPTION");
		String name = "tempEmpTranserOrderTemplate";
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 下载临时职人员发令导入模板
	 */
	@RequestMapping(value = "/downloadEmpTypeTransferOrderTemplate")
	public void downloadReguEmpTransferOrderTemplate(HttpServletRequest request
			, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//模板header
		List aliasNameList = new ArrayList();
		aliasNameList.add("社编*");
		aliasNameList.add("发令日期*");
		aliasNameList.add("发令原因*");		
		aliasNameList.add("新人员类型NM");
		//模板content
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "CH000002");
		map.put("CELL1", "20141121");
		map.put("CELL2", "人员类型变更");
		map.put("CELL3", "情报通信PS");
				
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//备注sheet
		List mapNameList = new ArrayList();
		List mapList = new ArrayList();
		mapNameList.add("人员类型参考");	
		String cdSql = "SELECT SP.CODE_NO AS CONTENT, S1.CONTENT AS CONTENT1";
			   cdSql+=" FROM SY_CODE T, SY_CODE_PARAM  SP, SY_GLOBAL_NAME S1 ";
			   cdSql+=" WHERE T.PARENT_CODE_NO = 1368 AND T.CODE_NO = SP.CODE_NO AND T.CODE_NO = S1.NO(+) ";
			   cdSql+=" AND S1.LANGUAGE(+) = 'zh' AND T.ACTIVITY = 1 AND SP.CPNY_ID = '"+admin.getCpnyId()+"'";
			   cdSql+=" AND T.CODE_NO NOT IN ( SELECT HTE.TEMP_EMPTYPE FROM HR_TEMP_EMPTYPE HTE WHERE HTE.CPNY_NAME= '"+admin.getCpnyId()+"'";
			   cdSql+=" 			AND NVL(HTE.TRANSFER_FLAG,'N')='Y' AND HTE.ACTIVITY=1)";
		mapList.add(cdSql);
		String name = "empTypeTranserOrderTemplate";
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 临时职人员批量发令数据导入结果查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempEmpTransferOrderResultList")
	public ModelAndView viewTempEmpTransferOrderResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("CPNY_ID", admin.getCpnyId());
		searchMap.put("UPDT_USER", admin.getEmpID());		
		List itemList = this.transferOrderSer.getTransferOrderImpResultList(request, searchMap);
		int impTotalCnt = this.transferOrderSer.getTransferOrderImpResultListCnt(request, searchMap);	
		int impErrCnt   = this.transferOrderSer.getTransferOrderImpResultListErrCnt(request, searchMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278703"));
		return new ModelAndView("/hrm/transferOrder/viewTempEmpTransferOrderResultList",modelMap);
	}
	
	/**
	 * 临时职人员批量发令数据导入结果查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewReguEmpTransferOrderResultList")
	public ModelAndView viewReguEmpTransferOrderResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("CPNY_ID", admin.getCpnyId());
		searchMap.put("UPDT_USER", admin.getEmpID());		
		List itemList = this.transferOrderSer.getTransferOrderImpResultList(request, searchMap);
		int impTotalCnt = this.transferOrderSer.getTransferOrderImpResultListCnt(request, searchMap);	
		int impErrCnt   = this.transferOrderSer.getTransferOrderImpResultListErrCnt(request, searchMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278703"));
		return new ModelAndView("/hrm/transferOrder/viewReguEmpTransferOrderResultList",modelMap);
	}
	
	/**
	 * 临时职人员发令数据导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createTempEmpTransferOrderImportResult")
	@ResponseBody
	public int createTempEmpTransferOrderImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("IMP_EMPNO", admin.getEmpID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		String result = this.transferOrderSer.importTransferOrderImpRAWFromExcel(request,paramMap);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 人员类型变更 发令数据导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createEmpTypeTransferOrderImportResult")
	@ResponseBody
	public int createReguEmpTransferOrderImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("IMP_EMPNO", admin.getEmpID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		String result = this.transferOrderSer.importEmpTypeTransferOrderImpRAWFromExcel(request,paramMap);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 临时职人员发令数据导入  导入的数据列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempEmpTransferOrderResultListExcel")
	public ModelAndView viewTempEmpTransferOrderResultListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("CPNY_ID", admin.getCpnyId());
		List itemList = this.transferOrderSer.getTransferOrderImpResultListExcel(request, searchMap);		
		modelMap.put("searchMap", searchMap);
		modelMap.put("MDATA", itemList);
		return new ModelAndView("/hrm/transferOrder/viewTempEmpTransferOrderResultListExcel",modelMap);
	}
	
	/**
	 * 人员类型变更发令数据导入  导入的数据列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewReguEmpTransferOrderResultListExcel")
	public ModelAndView viewReguEmpTransferOrderResultListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("CPNY_ID", admin.getCpnyId());
		List itemList = this.transferOrderSer.getTransferOrderImpResultListExcel(request, searchMap);		
		modelMap.put("searchMap", searchMap);
		modelMap.put("MDATA", itemList);
		return new ModelAndView("/hrm/transferOrder/viewReguEmpTransferOrderResultListExcel",modelMap);
	}
	
	/**
	 * 撤消临时职发令（cancel ）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTransferOrderUpgrade")
	@ResponseBody
	public Map<String, Object> deleteTransferOrderUpgrade(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		paramMap.put("UPDATED_BY", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		map = transferOrderSer.deleteTransferOrderUpgrade(paramMap);
		int result = Integer.parseInt(map.get("result").toString());
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功！
			map.put("navTabId", "hr0515");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
		} else if (result == 3){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		} else if (result == 4){
			map.put("statusCode", "300");
			map.put("message", "不可以删除生效的发令！");
		}else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_fail", request));// 发令失败
		} 
		return map;
	}
	
	/**
	 * 查看正规职发令列表(view transfer order list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewReguEmpTransferOrderList")
	public ModelAndView viewReguEmpTransferOrderList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("ORDERDATEF")){
			paramMap.put("ORDERDATEF", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("ORDERDATET", DateUtil.getCurrentMonthLastDayStr());			
		}else{
			paramMap.put("EMPID",paramMap.get("dwz.person.empId"));
		}
		paramMap.put("defaultCpny", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo());
		modelMap.put("searchMap", paramMap);

        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		List transList = transferOrderSer.getReguEmpTransferOrderList(paramMap, request);
    		int transListCnt = this.transferOrderSer.getReguEmpTransferOrderListCnt(paramMap,request);
    		modelMap.put("transList", transList);
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, transListCnt);
        }
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278719")) ;
		return new ModelAndView("/hrm/transferOrder/viewReguEmpTransferOrderList",modelMap);
	}
	
	/**
	 * 正规职添加发令页面查询（view Add transfer order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewReguEmpTransferOrderAddList")
	public ModelAndView viewReguEmpTransferOrderAddList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("navTabId", "hr0516");
		paramMap.put("TYPE", "upGrade");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("trCd", "278706");
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		paramMap.put("authority", authority);//超级用户权限
		
		List tempEmpList = transferOrderSer.getViewTempEmpList(paramMap, request);
		int tempEmpListCnt = transferOrderSer.getViewTempEmpCnt(paramMap, request);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("tempEmpList", tempEmpList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, tempEmpListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278703")) ;
		return new ModelAndView("/hrm/transferOrder/viewReguEmpTransferOrderAddList", modelMap);
	}
	
	/**
	 * 正规职修改发令页面查询（view Add transfer order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewReguEmpTransferOrderEditList")
	public ModelAndView viewReguEmpTransferOrderEditList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());		
		paramMap.put("TRANS_NO", "1365");
		paramMap.put("trCd", "278706");
		paramMap.put("navTabId", "hr0516");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

		List editTrList = transferOrderSer.getReguEmpTransferOrderEditList(paramMap, request);
		int editTrListCnt = transferOrderSer.getReguEmpTransferOrderEditListCnt(paramMap, request);	
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("editTrList", editTrList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editTrListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278719")) ;
		return new ModelAndView("/hrm/transferOrder/viewReguEmpTransferOrderEditList", modelMap);
	}
	/**
	 * 发令页面查询（view transfer order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewReguEmpTransferOrderDetail")
	public ModelAndView viewReguEmpTransferOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}			
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("navTabId", "hr0515");
		paramMap.put("MGT_SYSTEM", "C");  //G：取G系统管理的员工，C：取C系统管理的员工
		paramMap.put("TYPE", "upGrade");
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

		List reguEmpTrList = transferOrderSer.getReguEmpTransferOrderList(paramMap, request);
		Map reguEmpTr = (Map) reguEmpTrList.get(0);
		
		modelMap.put("searchMap", paramMap);
		modelMap.put("reguEmpTr", reguEmpTr);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278703")) ;
		return new ModelAndView("/hrm/transferOrder/viewReguEmpTransferOrderDetail", modelMap);
	}

	@RequestMapping(value = "/viewConfirmReqHire")
	public ModelAndView viewConfirmReqHire(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List itemList = transactionViewSer.getConfirmReqHire(request);
		modelMap.put("itemList", itemList);
		modelMap.put("PERSON_ID", admin.getPersonId());
		String  ls_deptNo="";
		if(itemList.size()>0){
			ls_deptNo = (((Map)itemList.get(0)).get("DEPTNO")).toString();
		}
		modelMap.put("affirmorList", transactionViewSer.getApplyFeeList(ls_deptNo, request));
		
		return new ModelAndView("/hrm/transferOrder/viewConfirmReqHire", modelMap);
	}

	@RequestMapping(value = "/confirmReqHire")
	@ResponseBody
	public Map confirmReqHire(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = transferOrderSer.confirmReqHire(request) ;
		String result = retMap.get("RET").toString();

		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "提交申请成功");
			map.put("navTabId", "hr0504");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	@RequestMapping(value = "/delTempEmpInfo")
	@ResponseBody
	public Map delTempEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = transferOrderSer.delTempEmpInfo(request) ;
		String result = retMap.get("RET").toString();

		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("navTabId", "hr0504");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}
	/**
	 * 删除离职发令（delete Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteResign")
	@ResponseBody
	public Map<String, Object> deleteResign(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		String   ls = "";
		String[] isChecked = request.getParameterValues("resignReqCKB");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		paramMap.put("RESIGN_NOS", ls);
		paramMap.put("UPDATED_BY", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		map = transferOrderSer.deleteResignation(paramMap);
		int result = Integer.parseInt(map.get("result").toString());
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功！");// 发令成功
			map.put("navTabId", "hr0206");
		}else{
			map.put("statusCode", "300");
			map.put("message", "删除失败！");// 发令失败
		} 
		return map;
	}
	/**
	 * 离职发令查询页面
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignInquiryList")
	public ModelAndView viewResignInquiryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}	
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		List resignInqList 		= transferOrderSer.getViewResignEditList(paramMap, request);
		int resignInqListCnt 	= transferOrderSer.getViewResignEditCnt(paramMap, request);	
		if(resignInqList.size()>0){
			modelMap.put("resignMst", (LinkedHashMap)resignInqList.get(0));
		}
		String APPLY_TYPE_NO = "15823";//离职发令决裁线
		List affirmorList = affirmApplySer.getResignAffirmByReqID(request);
		
		paramMap.put("APPLY_TYPE", "15823");
		paramMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySerOt.getEssFileList(paramMap);
		// 判断是否需要决裁1为开，0为关
		Map paramValueMap = new LinkedHashMap();
		paramValueMap.put("CPNY_ID", admin.getCpnyId());
		paramValueMap.put("TYPE", "resign");	
		int affirmFlag = transferOrderDao.getParamInfoValue(paramValueMap);
		paramMap.put("affirmFlag", affirmFlag);
		modelMap.put("searchMap", paramMap);
		modelMap.put("resignInqList", resignInqList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, resignInqListCnt);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("fileList", fileList);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278687")) ;
		return new ModelAndView("/hrm/transferOrder/viewResignInquiryList", modelMap);
	}
	
	/**
	 * 离职发令撤销申请
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignRevokeReqList")
	public ModelAndView viewResignRevokeReqList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(!paramMap.containsKey("CPNY_ID")){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else {
			if (paramMap != null && paramMap.get("CPNY_ID") == null) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
		}	
		if(!paramMap.containsKey("RESIGN_NOS")){
			String   ls = "";
			String[] isChecked = request.getParameterValues("revokeResignCKB");
			if(isChecked != null)
			{
				for(int i = 0; i < isChecked.length; i++){
					ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
				}
			}
			paramMap.put("RESIGN_NOS", ls);
		}
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		List resignRevokeReqList 		= transferOrderSer.getViewResignEditList(paramMap, request);
		int resignRevokeReqListCnt 	= transferOrderSer.getViewResignEditCnt(paramMap, request);	
		String APPLY_TYPE_NO = "15823";//离职发令决裁线
		List affirmorList 		= transferOrderSer.getAffirmorList(APPLY_TYPE_NO, request);
		List resignPersonIdList = transferOrderSer.getViewResignEditListAll(paramMap, request);
		// 判断是否需要决裁1为开，0为关
		Map paramValueMap = new LinkedHashMap();
		paramValueMap.put("CPNY_ID", admin.getCpnyId());
		paramValueMap.put("TYPE", "resign");	
		int affirmFlag = transferOrderDao.getParamInfoValue(paramValueMap);
		paramMap.put("affirmFlag", affirmFlag);
		modelMap.put("searchMap", paramMap);
		modelMap.put("resignRevokeReqList", resignRevokeReqList);
		modelMap.put("revokeResignPersonIdList", resignPersonIdList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, resignRevokeReqListCnt);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "278687")) ;
		return new ModelAndView("/hrm/transferOrder/viewResignRevokeReqList", modelMap);
	}
	/**
	 * 撤销离职发令（cancel Resign）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmRevokeResignation")
	@ResponseBody
	public Map<String, Object> confirmRevokeResignation(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map rtn = transferOrderSer.confirmRevokeResignation(request);
		int result = (Integer)rtn.get("RET");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_success", request));// 发令成功
			map.put("navTabId", "hr0206");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
		} else if (result == 3){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.dekreti_fail", request));// 发令失败
		} 
		return map;
	}
	
	/**
	 * 删除未生效临时职发令（cancel tempEmpTransferOrder）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelTransferOrderInBatch")
	@ResponseBody
	public Map<String, Object> cancelTransferOrderInBatch(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map rtn = transferOrderSer.cancelTransferOrderInBatch(request);
		int result = (Integer)rtn.get("RET");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功!");// 发令成功
			map.put("navTabId", "hr0515");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
		} else if (result == 3){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}else {
			map.put("statusCode", "300");
			map.put("message", rtn.get("MESSAGE"));// 发令失败
		} 
		return map;
	}
	
	/**
	 * 删除未生效正规职发令（cancel reguEmpTransferOrder）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelReguEmpTransferOrder")
	@ResponseBody
	public Map<String, Object> cancelReguEmpTransferOrder(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map rtn = transferOrderSer.cancelReguEmpTransferOrder(request);
		int result = (Integer)rtn.get("RET");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功!");// 发令成功
			map.put("navTabId", "hr0515");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.no_affiram", request));// 没有决裁者，请先配置决裁者
		} else if (result == 3){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"hr.alert.message.have_unimplemented", request));// 此人已有未生效发令,请生效后再进行发令
		}else {
			map.put("statusCode", "300");
			map.put("message", rtn.get("MESSAGE"));// 发令失败
		} 
		return map;
	}
}
