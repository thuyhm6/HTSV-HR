package com.ait.affirm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.SummaryItemSer;
import com.ait.ess.dao.EditionAffirmDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.WageApplicationDao;
import com.ait.hrm.service.ContractInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.inct.service.SalesmanIncentiveSer;
import com.ait.pa.dao.PaForLeftMenDao;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaForLeftMenSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AttendItemSer;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.ess.service.AffirmApplySer;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.ess.service.AnnualadjustmentInfoSer;
import com.ait.ess.service.ArMacRecordApplySer;
import com.ait.ess.service.DimissionEditionSer;
import com.ait.ess.service.EditionAffirmSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.promoter.service.PromoterSer;

@Controller
@RequestMapping(value = "/LGEP/affirm")
@SuppressWarnings("unchecked")
public class LGEPAffirmCtroller {
	Logger logger = Logger.getLogger(LGEPAffirmCtroller.class);
	@Autowired
	private PaForLeftMenSer paForleftMenSer;
	@Autowired
	private AttendItemSer attendItemSer;
	@Autowired
	private ItemsSer itemsSer;
	@Autowired
	private SummaryItemSer summaryItemSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private PaTempSalesSer paTempSalesSer;
	@Autowired
	private AffirmLeaveApplySer affirmApplySers;
	@Autowired
	private WageApplicationDao wageApplicationDao;
	@Autowired
	private AnnualadjustmentInfoSer annualadjustmentInfoSer;
	@Autowired
	private InfoApplyDao infoApplyDao;
	@Autowired
	private PaForLeftMenDao paForLeftMenDao ;
	@Autowired
	private EditionAffirmDao editionAffirmDao;
	@Autowired
	private ContractInfoSer contractInfoSer;
	@Autowired
	private ArMacRecordApplySer arMacRecordApplySer;
	@Autowired
	private InfoApplySer infoApplyOtSer;
	@Autowired
	private AffirmApplySer affirmApplySerOt;
	@Autowired
	private InfoApplyLeaveSer infoApplySer;
	@Autowired
	private InfoApplySer infoMacApplySer;
	@Autowired
	private AffirmLeaveApplySer affirmApplySer;
	@Autowired
	private EditionAffirmSer editionAffirmSer;
	@Autowired
	private SalesmanIncentiveSer salesmanIncentiveSer;
	@Autowired
	private salaryCodeSer salaryCodeSer;
	@Autowired
	private PaBasicItemSer paBasicItemSer;
	@Autowired
	private PaInputItemSer paInputItemSer;
	@Autowired
	private PaComputeItemSer paComputeItemSer;
	@Autowired
	private PaInputItemParamSer paInputItemParamSer;
	@Autowired
	private PromoterSer promoterSer;
	@Autowired
	private DimissionEditionSer dimissionEditionSer;

	@Autowired
	private ExcelUtilSer excelUtilSer;	
	
	/**
	 * check休假信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveCheck")
	public ModelAndView viewLeaveCheck(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		String essCheckNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(personId.equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("PERSON_ID", personId);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		LinkedHashMap infoApplyLeave = (LinkedHashMap) infoApplySer.getLeaveInfoByLeave(request);
		modelMap.put("infoApplyLeave", infoApplyLeave);
		
		if(infoApplyLeave != null && infoApplyLeave.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyLeave.get("APPLY_TYPE"))){
			modelMap.put("leaveBatchAffirmList", infoApplySer.getLeaveBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getLeaveBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/LGEP/affirm/viewLeaveCheck", modelMap);
	}
	
	/**
	 * Check---休假信息 (check overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkLeave",method = RequestMethod.POST)
	@ResponseBody
	public Map checkLeave(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", "Check成功！");//Check成功
		}else{
			map.put("statusCode", "300");
			map.put("message", "Check失败！");//Check失败
		}
		return map;
	}
	
	/**
	 * 临促工信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=30003784&EVENT_ID=1000160&pageNum=1
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleAffirm")
	public ModelAndView viewTempSaleAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap eventInfo =  this.paTempSalesSer.getTempSalesInfoByEventId(request);
		
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(request);
		List checkList = this.paTempSalesSer.getCheckListByEventId(request);
		
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		//裁决or check标识
		String affirmOrCheck = request.getParameter("affirmOrCheck");
		//查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		if("1".equals(affirmOrCheck)){
			for(int i=0; i < affirmList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)affirmList.get(i);
				if("0".equals(paramMap.get("AFFIRM_FLAG").toString())){
					if(personId.equals(paramMap.get("AFFIRMOR_ID").toString())){
						affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
						dept_level = i + 1;
					}
					break;
				}
			}
		}else{
			//查找当前需要check信息编号（第一条未check的信息）
			for(int i=0; i < checkList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)checkList.get(i);
				if("0".equals(paramMap.get("CHECK_FLAG").toString()) && personId.equals(paramMap.get("CHECKOR_ID").toString())){
					check_no = paramMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmOrCheck", affirmOrCheck);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt", affirmList == null ? 0 : affirmList.size());
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("accrualFlag", request.getParameter("accrualFlag"));
		modelMap.put("personId", request.getParameter("personId"));
		modelMap.put("eventInfo", eventInfo);
		if("Y".equals(eventInfo.get("ACCRUAL_FLAG").toString())){
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesAccuralInfoList(request));
		}else if("N".equals(eventInfo.get("ACCRUAL_FLAG").toString())){
			modelMap.put("paTempSalesEmpInfoList", this.paTempSalesSer.getTempSalesEmpInfoList(request));
			modelMap.put("tempSalInfo", this.paTempSalesSer.getTempSalesEmpInfoByEventId(request));
		}else if(eventInfo != null && "CONFIRM_N".equals(eventInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", eventInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"N"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"N"));
		}else if(eventInfo != null && "CONFIRM_Y".equals(eventInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", eventInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"Y"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"Y"));
		}

		return new ModelAndView("/LGEP/affirm/viewTempSaleAffirm", modelMap);
	}
	
	@RequestMapping(value = "/proveApplicationList")
	public ModelAndView proveApplicationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//以下顺序不可以换
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("APPLY_TYPE", "217886");
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("interLanguage", admin.getLanguage());
		List messageList = wageApplicationDao.getApplicationList(modelMap,-1,-1);//上传人员列表
		String personId = request.getParameter("PERSON_ID")==null?request.getParameter("personId"):request.getParameter("PERSON_ID");
		modelMap.put("PERSON_ID", personId);
		modelMap.put("ADMIN_ID", personId);
		List proveList = wageApplicationDao.getProveAppList(modelMap);//决裁者列表
		List checkorList = infoApplyDao.getCheckorByApplyNoList((Object)modelMap);
		modelMap.put("check", request.getParameter(""));
		
		modelMap.put("checkorList", checkorList);
		modelMap.put("messageList", messageList);
		modelMap.put("proveList", proveList);
		return new ModelAndView("/LGEP/affirm/proveApplicationList",modelMap);
	}
	/**
	 * 费用申请小页面审批
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-18 下午05:54:26 
	* @version V1.0
	 */
	@RequestMapping(value = "/approveApplication")
	@ResponseBody
	public Map<String, Object> approveApplication(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.approveApplication(request);
		if(result==1){
			map.put("message", "费用申请审批成功!");
		}else{
			map.put("message", "费用申请审批失败!");
		}
		map.put("result", result);
		return map;
	}
	/**
	 *加班 Check小页面审批
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wangqiang@ait.net.cn
	* @date 2014-8-18 下午05:54:26 
	* @version V1.0
	 */
	@RequestMapping(value = "/checkLGEPInfoOT")
	@ResponseBody
	public Map<String, Object> checkLGEPInfoOT(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySerOt.checkApplyInfo(request);
		if(result==1){
			map.put("message", "Check审批成功!");
		}else{
			map.put("message", "Check审批失败!");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * Check小页面审批
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-18 下午05:54:26 
	* @version V1.0
	 */
	@RequestMapping(value = "/checkLGEPInfo")
	@ResponseBody
	public Map<String, Object> checkApplicationInfo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);
		if(result==1){
			map.put("message", "Check审批成功!");
		}else{
			map.put("message", "Check审批失败!");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 临促决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmPaTempSales")
	@ResponseBody
	public Map<String, Object> affirmPaTempSales (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{


		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "审批";
		int errorInt= 0;
		//裁决or check标识
		String affirmOrCheck = request.getParameter("affirmOrCheck");
		if("1".equals(affirmOrCheck)){
			errorInt=this.paTempSalesSer.affirmPaTempSales(request);
		}else{
			errorInt=this.paTempSalesSer.checkPaTempSales(request);
			msg = "Check";
		}
		if(errorInt==1){
			map.put("statusCode", "200");
			
			map.put("message", msg + "成功");//保存成功
		}else{
			map.put("statusCode", "300");
			map.put("message", msg + "失败");//保存失败
		}
		return map;
	}
	
	/**
	 * 合同信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewContractAffirm?LGEP=LGEP&LANGUAGE=zh&personId=30003784&EVENT_ID=1000160&pageNum=1
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewContractAffirm")
	public ModelAndView viewContractAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		List affirmList = this.contractInfoSer.getAffirmorListByContractNo(request);
	    String ESS_AFFIRM_NO = "";
		for(int i=0;i<affirmList.size();i++){
			LinkedHashMap paramMap = ( LinkedHashMap)affirmList.get(i);
			if("0".equals(paramMap.get("AFFIRM_FLAG").toString())){
				if(personId.equals(paramMap.get("AFFIRMOR_ID").toString())){
					ESS_AFFIRM_NO = paramMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("ESS_AFFIRM_NO", ESS_AFFIRM_NO);

		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("contractInfo", contractInfoSer.getContractForUpdate(request));
		
		return new ModelAndView("/LGEP/affirm/viewContractAffirm",modelMap);

	}
	
	/**
	 * 合同决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmContract")
	@ResponseBody
	public Map<String, Object> affirmContract (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt=this.contractInfoSer.approveExpiredContract(request) ;
		if(errorInt==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "hr0306");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}

	/**
	 *加班信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=30003784&EVENT_ID=1000160&pageNum=1
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOtAffirm")
	public ModelAndView viewOtAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}

		modelMap.put("PERSON_ID", personId);
		 
		List affirmorList = infoApplyOtSer.getAffirmorByApplyNoListXiao(request);
		List checkorList = infoApplyOtSer.getCheckorByApplyNoListXiao(request);
		
	
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG")!=null?applyorMap.get("AFFIRM_FLAG").toString():"")){
				if(personId.equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
 
		LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplySer.getOtApplyPersonalXiao(request);
		

		LinkedHashMap ifdisplay = (LinkedHashMap) infoApplySer.getOtApplyPersonalIfDisplay(request);
		modelMap.put("infoApplyOt", infoApplyOt);
		modelMap.put("ifdisplay", ifdisplay);
		
		/*if(infoApplyLeave != null && infoApplyLeave.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyLeave.get("APPLY_TYPE"))){
		//	modelMap.put("leaveBatchAffirmList", infoApplyOtSer.);
			modelMap.put("leaveBatchAffirmList", infoApplySer.getLeaveBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getLeaveBatchAffirmInfoCnt(request));
		}*/
		

		if(infoApplyOt != null && infoApplyOt.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyOt.get("APPLY_TYPE"))){
			modelMap.put("OtBatchAffirmList", infoApplySer.getOtBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getOtBatchAffirmInfoCnt(request));

		}
		
		return new ModelAndView("/LGEP/affirm/viewOtAffirm",modelMap);

	}
	
	
	
	/**
	 * check 加班开始进行check
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/startApplyCheckInfo")
	public ModelAndView startApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap paramMap2 = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		String essCheckNo = "";
		modelMap.put("ADMIN_ID", paramMap2.get("personId"));
		LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplySer.getOtApplyPersonalXiao(request);
  
		modelMap.put("infoApplyOt", infoApplyOt);

		List applyorList = infoApplyOtSer.getApplyorByApplyNoList(request);
		
		List affirmorList = infoApplyOtSer.getAffirmorByApplyNoListXiao(request);
		List checkorList = infoApplyOtSer.getCheckorByApplyNoListXiao(request); 
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(personId.equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("PERSON_ID", personId);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		if(infoApplyOt != null && infoApplyOt.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyOt.get("APPLY_TYPE"))){
			modelMap.put("OtBatchAffirmList", infoApplySer.getOtBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getOtBatchAffirmInfoCnt(request));

		}
		
		return new ModelAndView("/LGEP/affirm/startApplyCheckInfo", modelMap);
	}
	
	
	/**
	 *加班信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=30003784&EVENT_ID=1000160&pageNum=1
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFullApplyRemarkInfoXiao")
	public ModelAndView viewFullApplyRemarkInfoXiao(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		//用于返回，找到应该刷新的页面
		
		if(paramMap.get("search_ot_time_type").equals("P")){
			modelMap.put("PAGE_FLAG", "P_OTAPPLY" );
		}else{
			modelMap.put("PAGE_FLAG", paramMap.get("PAGE_FLAG"));
		}
		 
		return new ModelAndView("/LGEP/affirm/viewFullApplyRemarkInfoXiao", modelMap);
		
		 

	}
	

	/**
	 * 加班决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmOt")
	@ResponseBody
	public Map<String, Object> affirmOt (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int result = affirmApplySerOt.approveOvertimeApply(request);
		if(result==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "hr0306");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}

 

	/**
	 * 决裁---  (add overtime apply)添加check
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addApplyCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addApplyCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
	 
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
		String page_flag = (String) (paramMap.get("PAGE_FLAG") == null ? "" : paramMap.get("PAGE_FLAG"));
		int result = affirmApplySerOt.addApplyCheckListXiao(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", "添加Checkor成功！");//添加Checkor者成功！
			
		}else{
			map.put("statusCode", "300");
			map.put("message", "添加Checkor失败！");//添加Checkor者失败！
		}
		return map;
	}
	
	/**
	 * 休假信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=30003784&EVENT_ID=1000160&pageNum=1
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLeaveAffirm")
	public ModelAndView viewLeaveAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}

		modelMap.put("PERSON_ID", personId);
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(personId.equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		LinkedHashMap infoApplyLeave = (LinkedHashMap) infoApplySer.getLeaveInfoByLeave(request);
		modelMap.put("infoApplyLeave", infoApplyLeave);
		
		if(infoApplyLeave != null && infoApplyLeave.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyLeave.get("APPLY_TYPE"))){
			modelMap.put("leaveBatchAffirmList", infoApplySer.getLeaveBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getLeaveBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/LGEP/affirm/viewLeaveAffirm",modelMap);

	}
	
	/**
	 * 休假决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmLeave")
	@ResponseBody
	public Map<String, Object> affirmLeave (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int result = affirmApplySer.approveApplyLeave(request);
		if(result==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "hr0306");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 离职信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewEditionAffirmsList?LGEP=LGEP&LANGUAGE=zh&personId=30003784&APPLY_NO=551&pageNum=1
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEditionAffirmsList")
	public ModelAndView viewEditionAffirmsList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}

		modelMap.put("PERSON_ID", personId);

		List applyorList = this.editionAffirmSer
				.getEditionApplyorByApplyNoList(request);
		Object applyorInfo = applyorList.get(0);

		List affirmList = this.editionAffirmSer
				.getEditionAffirmorByApplyNoList(request);

		List checkList = this.editionAffirmSer
				.getEditionCheckorByApplyNoList(request);
		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		String affirmFlag = "";
		String affirmPerson = "";
		LinkedHashMap paramMap = new LinkedHashMap();
		// 查找当前需要check信息编号（第一条未check的信息）
		for (int i = 0; i < affirmList.size(); i++) {
			paramMap = (LinkedHashMap) affirmList.get(i);
			if ("0".equals(paramMap.get("AFFIRM_FLAG").toString())) {
				affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
				affirmPerson = paramMap.get("AFFIRMOR_ID").toString();
				dept_level = i + 1;
				this.editionAffirmDao.affirmDimissionInfoAffirm(paramMap);
				break;
			}
		}
		if(affirmPerson != null && !"".equals(affirmPerson) && personId != null && !"".equals(personId)){
			if(affirmPerson.equals(personId)){
				affirmFlag = paramMap.get("AFFIRM_FLAG").toString();
			}else{
				affirmFlag = "1";
			}
		}
		
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("affirmFlag", affirmFlag);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("applyorInfo", applyorInfo);
		modelMap.put("affirmList", affirmList);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt",
				affirmList == null ? 0 : affirmList.size());
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));

		return new ModelAndView("/LGEP/affirm/viewEditionAffirmsList", modelMap);
	}
	
	
	/**
	 * 离职决裁
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-14 下午4:46:40
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/affirmDimissionApplyInfo")
	@ResponseBody
	public Map affirmDimissionApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt = 0;
		errorInt = this.editionAffirmSer.affirmDimisionInfo(request);
		if (errorInt == 1) {
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");// 保存成功
			jo.put("navTabId", "ess2017");
		} else {
			jo.put("statusCode", "300");
			jo.put("message","审批失败");// 保存失败
		}
		return jo;
	}
	
	
	
	/**
	 * 漏刷卡信息决裁
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewRecordAffirm")
	public ModelAndView viewRecordAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}
		
		List applyorList = arMacRecordApplySer.getArMacRecordAffirmViewListBySingle(request);

		Map recordInfo = null;
		if(applyorList != null && applyorList.size() > 0){
			recordInfo = (Map)applyorList.get(0);
			modelMap.put("RecordInfo", applyorList.get(0));
		}
		
		List affirmList = infoMacApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoMacApplySer.getCheckorByApplyNoList(request);
		
		String essAffirmNo = "";
		for(int i=0;i<affirmList.size();i++){
			LinkedHashMap paramMap1 = ( LinkedHashMap)affirmList.get(i);
			if("0".equals(paramMap1.get("AFFIRM_FLAG").toString())){
				if(personId.equals(paramMap1.get("AFFIRMOR_ID").toString())){
					essAffirmNo = paramMap1.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("paramMap", paramMap);
		
		if( recordInfo != null  && "Y".equals(StringUtil.checkNull(recordInfo.get("BATCH_YN")))){
			modelMap.put("arMacBatchAffirmList", arMacRecordApplySer.getArMacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/LGEP/affirm/viewRecordAffirm",modelMap);

	}
	
	/**
	 * 漏刷卡进入check页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/RecordCheck")
	public ModelAndView RecordCheck(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		List applyorList = arMacRecordApplySer.getArMacRecordAffirmViewListBySingle(request);
		List affirmList = infoMacApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoMacApplySer.getCheckorByApplyNoList(request);

		String essCheckNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(personId.equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		
		modelMap.put("essCheckNo", essCheckNo);

		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("PERSON_ID", personId);

		Map recordInfo = null;
		if(applyorList != null && applyorList.size() > 0){
			recordInfo = (Map)applyorList.get(0);
			modelMap.put("RecordInfo", applyorList.get(0));
		}
		if( recordInfo != null  && "Y".equals(StringUtil.checkNull(recordInfo.get("BATCH_YN")))){
			modelMap.put("arMacBatchAffirmList", arMacRecordApplySer.getArMacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, arMacRecordApplySer.getArMacBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/LGEP/affirm/RecordCheck",modelMap);

	}
	

	
	/**
	 * 漏刷卡决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmRecord")
	@ResponseBody
	public Map<String, Object> affirmRecord(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = arMacRecordApplySer.approveArMacRecordApplyEP(request);
			if (result == 1) {
				map.put("navTabId", "ess0209");
				map.put("message", "漏刷卡决裁成功！"); 
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "漏刷卡决裁出错，请重新操作！");//"加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	
	/**
	 * 进入年假信息决裁
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAnnuAffirm")
	public ModelAndView viewAnnuAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}
		
		List annulist = annualadjustmentInfoSer.getAnnuapplyListByApplyno(request);
		List affirmList = infoMacApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoMacApplySer.getCheckorByApplyNoList(request);
		 
		//List affirmList = this.contractInfoSer.getAffirmorListByContractNo(request);
		LinkedHashMap annuMap = (LinkedHashMap)annulist.get(0);
		String essAffirmNo = "";
		for(int i=0;i<affirmList.size();i++){
			LinkedHashMap paramMap1 = ( LinkedHashMap)affirmList.get(i);
			if("0".equals(paramMap1.get("AFFIRM_FLAG").toString())){
				if(personId.equals(paramMap1.get("AFFIRMOR_ID").toString())){
					essAffirmNo = paramMap1.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("annumap", (LinkedHashMap)annulist.get(0));
		modelMap.put("paramMap", paramMap);

		if( annuMap != null  && "Y".equals(StringUtil.checkNull(annuMap.get("BATCH_YN")))){
			modelMap.put("arVacBatchAffirmList", annualadjustmentInfoSer.getArVacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getArVacBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/LGEP/affirm/viewAnnuAffirm",modelMap);

	}
	
	
	
	/**
	 * 年假调整进入check页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/AnnuCheck")
	public ModelAndView AnnuCheck(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		List annulist = annualadjustmentInfoSer.getAnnuapplyListByApplyno(request);
		List affirmList = infoMacApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoMacApplySer.getCheckorByApplyNoList(request);

		LinkedHashMap annuMap = (LinkedHashMap)annulist.get(0);
		String essCheckNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(personId.equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("annumap", (LinkedHashMap)annulist.get(0));
		modelMap.put("PERSON_ID", personId);
		
		if( annuMap != null  && "Y".equals(StringUtil.checkNull(annuMap.get("BATCH_YN")))){
			modelMap.put("arVacBatchAffirmList", annualadjustmentInfoSer.getArVacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getArVacBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/LGEP/affirm/RecordCheck",modelMap);

	}
	
	/**
	 * 年假调整决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmAnnu")
	@ResponseBody
	public Map<String, Object> affirmAnnu(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySerOt.appAUUNApplyEP(request);
			if (result == 1) {
				map.put("navTabId", "ess0304");
				map.put("message", "年假调整决裁成功！");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "年假调整决裁出错，请重新决裁！" + e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	

	/**
	 * 进入考勤异常决裁页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCwaAbnormalAffirm")
	public ModelAndView viewCwaAbnormalAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}
		
		List applyorList = annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
		List affirmList = infoMacApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoMacApplySer.getCheckorByApplyNoList(request);
		 
		//List affirmList = this.contractInfoSer.getAffirmorListByContractNo(request);
		
		String essAffirmNo = "";
		for(int i=0;i<affirmList.size();i++){
			LinkedHashMap paramMap1 = ( LinkedHashMap)affirmList.get(i);
			if("0".equals(paramMap1.get("AFFIRM_FLAG").toString())){
				if(personId.equals(paramMap1.get("AFFIRMOR_ID").toString())){
					essAffirmNo = paramMap1.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("CwaInfo", (LinkedHashMap)applyorList.get(0));
		modelMap.put("paramMap", paramMap);
		Map applyorInfo = (Map) applyorList.get(0);
		if( applyorInfo != null  && "Y".equals(StringUtil.checkNull(applyorInfo.get("BATCH_YN")))){
			modelMap.put("arCwaBatchAffirmList", infoApplyOtSer.getArCwaBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyOtSer.getArCwaBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/LGEP/affirm/viewCwaAbnormalAffirm",modelMap);

	}
	
	
	
	/**
	 * 考勤异常进入check页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CwaCheck")
	public ModelAndView CwaCheck(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		List applyorList = annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
		List affirmList = infoMacApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoMacApplySer.getCheckorByApplyNoList(request);

		String essCheckNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(personId.equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("affirmorList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("CwaInfo", (LinkedHashMap)applyorList.get(0));
		modelMap.put("PERSON_ID", personId);

		Map applyorInfo = (Map) applyorList.get(0);
		if( applyorInfo != null  && "Y".equals(StringUtil.checkNull(applyorInfo.get("BATCH_YN")))){
			modelMap.put("arCwaBatchAffirmList", infoApplyOtSer.getArCwaBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyOtSer.getArCwaBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/LGEP/affirm/CwaCheck",modelMap);

	}
	
	/**
	 * 考勤异常决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmCwaAbnormal")
	@ResponseBody
	public Map<String, Object> affirmCwaAbnormal(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
		    result = affirmApplySers.approveApplyCwaEP(request);
			if (result == 1) {
				map.put("navTabId", "ess0209");
				map.put("message", "考勤异常决裁成功！"); 
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "考勤异常决裁出错，请重新操作！");//"加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	
	/**
	 * 临时职离职审批查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&personId=30003784&REQ_ID=7&pageNum=1
	 * @author penghaixia
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewReqResignAffirm")
	public ModelAndView viewReqResignAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}

		modelMap.put("PERSON_ID", personId);
		List affirmorList = affirmApplySerOt.getResignAffirmByReqID(request);
		List checkorList = affirmApplySerOt.getResignCheckorByByReqID(request);
		List reqList = affirmApplySerOt.getResignAffirm(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("APPLY_TYPE", "15823");
		paramMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySer.getEssFileList(paramMap);
		
		if(reqList.size()>0){
			modelMap.put("essAffirmReq", (LinkedHashMap)reqList.get(0));
		}
		
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(personId.equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		List infoApplyResign = affirmApplySerOt.getResignAffirmListByReqID(request);
		int infoApplyResignCnt 	= affirmApplySerOt.getResignAffirmListByReqIDCnt(request);
		modelMap.put("searchMap", paramMap);
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("fileList", fileList);
		modelMap.put("infoApplyResign", infoApplyResign);	
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyResignCnt);

		return new ModelAndView("/LGEP/affirm/viewReqResignAffirm", modelMap);
	}
	
	/**
	 * 营业员提成 调整审批
	* @author PENGHAIXIA 
	* @date 2014-8-20
	* @version V1.0
	 */
	@RequestMapping(value = "/affirmSalesmanInctCalcAdju")
	@ResponseBody
	public Map<String, Object> affirmSalesmanInctCalcAdju (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt=this.salesmanIncentiveSer.affirmSalesmanInctCalcAdju(request);
		if(errorInt==1){
			map.put("statusCode", "200");
			map.put("message", "审批成功!");//保存成功
			map.put("navTabId", "se0203");
		}else{
			map.put("statusCode", "300");
			map.put("message", "审批失败!");//保存失败
		}
		return map;
	}
	
	/**
	 * 添加check信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/addCheckInfo?LGEP=LGEP&LANGUAGE=zh&personId=30003784&REQ_ID=7&pageNum=1
	 * @author wangqiang
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCheckInfo")
	public ModelAndView addCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"));
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
		 
		modelMap.put("CHECKURL", paramMap.get("CHECKURL")); //这是发送小页面的地址，在上一个页面定义好，发送页面的地方就不用管了
		modelMap.put("AFFIRMOR_ID", paramMap.get("AFFIRMOR_ID"));
		modelMap.put("OT_TIME_TYPE", paramMap.get("search_ot_time_type"));  //加班用的
		modelMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE"));
		//search_ot_time_type用于返回，找到应该刷新的页面 只加班用
		
		if("P".equals(paramMap.get("search_ot_time_type"))){
			modelMap.put("PAGE_FLAG", "P_OTAPPLY" );
		}
		if("L".equals(paramMap.get("search_ot_time_type"))){
			modelMap.put("PAGE_FLAG", paramMap.get("PAGE_FLAG"));
		}

		return new ModelAndView("/LGEP/affirm/addCheckInfo", modelMap);
	}

	
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonCntXiao")
	@ResponseBody
	public Map getPersonCntXiao(HttpServletRequest request, HttpServletResponse response) throws Exception  {
	
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=this.empInfoSer.getPidEidListXiao(request);
		if (pidEidList != null && pidEidList.size() > 0) {
			map.put("pidEidList", pidEidList);
			 
		}  
	 	  return map;
	}
	
	/**
	 * 跳转到决裁页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmArItemInfo")
	public ModelAndView viewAffirmArItemInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List attendList = this.attendItemSer.getAffirmAttendList(request);
		Object arItemInfo = null;
		if(attendList.size()>0){
			arItemInfo = attendList.get(0);
		}
		String personId = request.getParameter("personId");
		String CPNY_ID = request.getParameter("CPNY_ID");
		modelMap.put("arItemInfo", arItemInfo);
		modelMap.put("personId", personId);
		modelMap.put("CPNY_ID", CPNY_ID);
		return new ModelAndView("/LGEP/affirm/viewAffirmAttendList",
				modelMap);
	}
	
	

	/**
	 * 进行决裁，如果决裁通过则考勤代码直接进入相应的考勤代码库中
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAffirmAttendInfo")
	@ResponseBody
	public Map updateAffirmAttendInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		String affirm_type = request.getParameter("ACTIVITY_TYPE");
		String flag = request.getParameter("flag");
		String item_no = request.getParameter("ITEM_NO");
		String cpny_id = request.getParameter("cpny");
		String group_no = "constant";
		if (flag == "1" || "1".equals(flag)) {
			if (affirm_type == "1" || "1".equals(affirm_type)) {
				int resultInt = this.attendItemSer
						.updateAffirmAttendInfo(request);
				if (resultInt == 1) {
					if (project_type == "1" || project_type.equals("1")) {
							int result = this.itemsSer.addItemInfo(request, cpny_id, item_no);
						    int errorNum = this.itemsSer.addItemParamInfo(request, cpny_id, item_no);
							if (result == 1 && errorNum==1) {
								map.put("statusCode", "200");
								map.put("message","审批成功");
							} else {
								map.put("statusCode", "300");
								map.put("message","审批失败");
							}
						} 
				else if (project_type == "2" || project_type.equals("2")) {
						
							int result = this.summaryItemSer.addSummaryItemInfoAffirm(request, cpny_id, item_no);
							int errorNum = this.summaryItemSer.addSummaryItemParamInfo(request, cpny_id, item_no);
							if (result == 1 && errorNum==1) {
								map.put("statusCode", "200");
								map.put("message","审批成功");
							} else {
								map.put("statusCode", "300");
								map.put("message","审批失败");
							}
						} 
				} else {
					map.put("statusCode", "300");
					map.put("message","审批失败");
				}
			} else {
				if (affirm_type == "2" || "2".equals(affirm_type)) {
					int resultInt = this.attendItemSer
							.updateAffirmAttendInfo(request);
					if (resultInt == 1) {
						if (project_type == "1" || project_type.equals("1")) {
							int errorInt = this.itemsSer.checkForItemParam(request, cpny_id, item_no, group_no);
							if (errorInt == 0) {
								int result = this.itemsSer
										.addItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message","审批成功");
								} else {
									map.put("statusCode", "300");
									map.put("message","审批失败");
								}
							} else {
								map.put("statusCode", "300");
								map.put("message","审批失败");
							}
						} else if (project_type == "2"
								|| project_type.equals("2")) {
							int errorInt = this.summaryItemSer
									.checkForItemParam(request, item_no, cpny_id, group_no);
							if (errorInt == 0) {
								int result = this.summaryItemSer
										.addSummaryItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message","审批成功");
								} else {
									map.put("statusCode", "300");
									map.put("message","审批失败");
								}
							} else {
								map.put("statusCode", "300");
								map.put("message","审批失败");
							}
						} 
					} else {
						map.put("statusCode", "300");
						map.put("message","审批失败");
					}
				}
			}
		}else{
			int resultInt = this.attendItemSer
					.updateAffirmAttendInfo(request);
			if(resultInt==1){
				map.put("statusCode", "200");
				map.put("message","审批成功");
			}else{
				map.put("statusCode", "300");
				map.put("message","审批失败");
			}
		}
		return map;
	}
	
	/**
	 * 跳转到决裁页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmSalaryItemInfo")
	public ModelAndView viewAffirmSalaryItemInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List salaryCodeList = this.salaryCodeSer.getAffirmSalaryList(request);
		Object salaryCodeInfo = null;
		if(salaryCodeList.size()>0){
			salaryCodeInfo = salaryCodeList.get(0);
		}
		String personId = request.getParameter("personId");
		String CPNY_ID = request.getParameter("CPNY_ID");
		modelMap.put("salaryCodeInfo", salaryCodeInfo);
		modelMap.put("personId", personId);
		modelMap.put("CPNY_ID", CPNY_ID);
		return new ModelAndView("/LGEP/affirm/viewAffirmSalaryItemInfo",
				modelMap);
	}

	/**
	 * 进行决裁，如果决裁通过则工资代码直接进入相应的工资代码库中
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAffirmSalaryInfo")
	@ResponseBody
	public Map updateAffirmSalaryInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		String affirm_type = request.getParameter("ACTIVITY_TYPE");
		String item_no = request.getParameter("ITEM_NO");
		String cpny_id = request.getParameter("cpny");
		String flag = request.getParameter("flag");
		if (flag == "1" || "1".equals(flag)) {
			if (affirm_type == "1" || "1".equals(affirm_type)) {
				int resultInt = this.salaryCodeSer
						.updateAffirmSalaryInfo(request);
				if (resultInt == 1) {
					if (project_type == "1" || project_type.equals("1")) {
						int errorInt = this.paBasicItemSer
								.checkAddPaBasicItemInfo(request);
						if (errorInt == 0) {
							int result = this.paBasicItemSer
									.addPaBasicItemInfo(request,item_no);
							if (result == 1) {
								int errorNum = paBasicItemSer.checkAddPaBasicItemParamInfo(request);
								if(errorNum == 0){
									int resultNum = this.paBasicItemSer.addPaBasicItemParamInfo(request);
									if(resultNum==1){
										map.put("statusCode", "200");
										map.put("message", "审批成功");
										map.put("navTabId", "pa1104jc");
									}else {
										map.put("statusCode", "300");
										map.put("message", "审批失败");
									}
								}
							} 
						} else {
							map.put("statusCode", "300");
							map.put("message", "审批失败");
						}
					} else if (project_type == "2" || project_type.equals("2")) {
						int errorInt = this.paInputItemSer
								.checkAddPaInputItemInfo(request);
						if (errorInt == 0) {
							int result = this.paInputItemSer
									.addPaInputItemInfo(request,item_no);
							if (result == 1) {
								int errorNum = this.paInputItemParamSer.checkAddPaInputItemParamInfo(request);
								if(errorNum==0){
									int resultNum = this.paInputItemParamSer.addPaInputItemParamInfo(request, cpny_id, item_no);
									if(resultNum ==1){
										map.put("statusCode", "200");
										map.put("message", "审批成功");
										map.put("navTabId", "pa1104jc");
									}else {
										map.put("statusCode", "300");
										map.put("message", "审批失败");
									}
								}
							} 
						} else {
							map.put("statusCode", "300");
							map.put("message", "审批失败");
						}
					} else if (project_type == "3" || project_type.equals("3")) {
						int errorNum = this.paComputeItemSer
								.checkAddPaComputeItemInfo(request);
						if (errorNum == 0) {
							int returnNum = this.paComputeItemSer
									.addPaComputeItemInfo(request,item_no);
							if (returnNum == 1) {
								int errorInt = this.paComputeItemSer.checkAddPaComputeItemParamInfo(request);
								if(errorInt==0){
									int resultNum = this.paComputeItemSer.addPaComputeItemParamInfo(request);
									if(resultNum==1){
										map.put("statusCode", "200");
										map.put("message", "审批成功");
										map.put("navTabId", "pa1104jc");
									} else {
										map.put("statusCode", "300");
										map.put("message","审批失败");
									}
								}
							}
						} else {
							map.put("statusCode", "300");
							map.put("message", "审批失败");
						}
					}
				} else {
					map.put("statusCode", "300");
					map.put("message", "审批失败");
				}
			} else {
				if (affirm_type == "2" || "2".equals(affirm_type)) {
					int resultInt = this.salaryCodeSer
							.updateAffirmSalaryInfo(request);
					if (resultInt == 1) {
						if (project_type == "1" || project_type.equals("1")) {
							int errorInt = this.paBasicItemSer
									.checkAddPaBasicItemParamInfo(request, cpny_id, item_no);
							if (errorInt == 0) {
								int result = this.paBasicItemSer
										.addPaBasicItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message", "审批成功");
									map.put("navTabId", "pa1104jc");
								} else {
									map.put("statusCode", "300");
									map.put("message","审批失败");
								}
							} else {
								map.put("statusCode", "300");
								map.put("message","审批失败");
							}
						} else if (project_type == "2"
								|| project_type.equals("2")) {
							int errorInt = this.paInputItemParamSer.checkAddPaInputItemParamInfo(request, cpny_id, item_no);
							if (errorInt == 0) {
								int result = this.paInputItemParamSer
										.addPaInputItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message","审批成功");
									map.put("navTabId", "pa1104jc");
								} else {
									map.put("statusCode", "300");
									map.put("message","审批失败");
								}
							} else {
								map.put("statusCode", "300");
								map.put("message","审批失败");
							}
						} else if (project_type == "3"
								|| project_type.equals("3")) {
							int errorNum = this.paComputeItemSer
									.checkAddPaComputeItemParamInfo(request, cpny_id, item_no);
							if (errorNum == 0) {
								int returnNum = this.paComputeItemSer.addPaComputeItemParamInfo(request, cpny_id, item_no);
								if (returnNum == 1) {
									map.put("statusCode", "200");
									map.put("message","审批成功");
									map.put("navTabId", "pa1104jc");
								} else {
									map.put("statusCode", "300");
									map.put("message","审批失败");
								}
							} else {
								map.put("statusCode", "300");
								map.put("message", "审批失败");
							}
						}
					} else {
						map.put("statusCode", "300");
						map.put("message","审批失败");
					}
				}
			}
		} else {
			int resultInt = this.salaryCodeSer.updateAffirmSalaryInfo(request);
			if (resultInt == 1) {
				map.put("statusCode", "200");
				map.put("message","审批成功");
				map.put("navTabId", "pa1104jc");
			} else {
				map.put("statusCode", "300");
				map.put("message", "审批失败");
			}
		}
		return map;
	}
	
	/**
	 * check离职信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkDimissionApplyInfo")
	public ModelAndView checkDimissionApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List applyorList = this.editionAffirmSer
				.getEditionApplyorByApplyNoList(request);
		Object applyorInfo = applyorList.get(0);

		String personId = request.getParameter("personId");
		List affirmList = this.editionAffirmSer
				.getEditionAffirmorByApplyNoList(request);

		List checkList = this.editionAffirmSer
				.getEditionCheckorByApplyNoList(request);
		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		String affirmPerson = "";
		String checkFlag = "";
		LinkedHashMap paramMap = new LinkedHashMap();
		// 查找当前需要check信息编号（第一条未check的信息）
		for (int i = 0; i < checkList.size(); i++) {
			paramMap = (LinkedHashMap) checkList.get(i);
			if ("0".equals(paramMap.get("CHECK_FLAG").toString()) && paramMap.get("PERSON_ID_C").equals(personId)) {
				check_no = paramMap.get("ESS_CHECK_NO").toString();
				affirmPerson = paramMap.get("CHECKOR_ID").toString();
				dept_level = i + 1;
				break;
			}
		}
		
		if(affirmPerson != null && !"".equals(affirmPerson) && personId != null && !"".equals(personId)){
			if(affirmPerson.equals(personId)){
				checkFlag = paramMap.get("CHECK_FLAG").toString();
			}else{
				checkFlag = "1";
			}
		}
		
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("checkFlag", checkFlag);
		modelMap.put("dept_level", dept_level);
		modelMap.put("personId", request.getParameter("personId"));
		modelMap.put("applyorInfo", applyorInfo);
		modelMap.put("affirmList", affirmList);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt",
				affirmList == null ? 0 : affirmList.size());
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));

		return new ModelAndView("/LGEP/affirm/checkDimissionApplyInfo",
				modelMap);
	}

	/**
	 * Check---离职申请 (check dimission apply)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkDimissionInfo")
	@ResponseBody
	public Map checkDimissionInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.editionAffirmSer.checkDimissionInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", "离职申请Check成功！");
		}else{
			map.put("statusCode", "300");
			map.put("message", "离职申请Check失败！");
		}
		return map;
	}

	/**
	 * 促销员实绩审批申请查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @url http://localhost/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=31462820&REQ_ID=28&affirmOrCheck=1
	 * @author CH.W.G
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSelloutAffirm")
	public ModelAndView viewSelloutAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap reqInfo =  this.promoterSer.getSelloutReqByReqId(request);
		
		List affirmList = this.promoterSer.getAffirmorListByReqId(request);
		List checkList = this.promoterSer.getCheckListByReqId(request);
		
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		//裁决or check标识
		String affirmOrCheck = request.getParameter("affirmOrCheck");
		//查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		if("1".equals(affirmOrCheck)){
			for(int i=0; i < affirmList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)affirmList.get(i);
				if("0".equals(paramMap.get("AFFIRM_FLAG").toString())){
					if(personId.equals(paramMap.get("AFFIRMOR_ID").toString())){
						affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
						dept_level = i + 1;
					}
					break;
				}
			}
		}else{
			//查找当前需要check信息编号（第一条未check的信息）
			for(int i=0; i < checkList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)checkList.get(i);
				if("0".equals(paramMap.get("CHECK_FLAG").toString()) && personId.equals(paramMap.get("CHECKOR_ID").toString())){
					check_no = paramMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmOrCheck", affirmOrCheck);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt", affirmList == null ? 0 : affirmList.size());
		modelMap.put("REQ_ID", request.getParameter("REQ_ID"));
		modelMap.put("accrualFlag", request.getParameter("accrualFlag"));
		modelMap.put("personId", request.getParameter("personId"));
		modelMap.put("reqInfo", reqInfo);
		modelMap.put("toolbarInfo", "SELECTR") ;
		
		modelMap.put("menuThirdList", promoterSer.getMenuThirdConfrimList("",request));
		modelMap.put("tabsSelected",0);
		
		modelMap.put("itemList", this.promoterSer.getSelloutReqDtl(request));
		modelMap.put("reqOver10List", promoterSer.getReqOver10List(modelMap));
		modelMap.put("reqOver9kList", promoterSer.getReqOver9kList(modelMap));
		modelMap.put("reqRatioList", promoterSer.getReqRatioList(modelMap));
		modelMap.put("reqExshopList", promoterSer.getReqExshopList(modelMap));
		modelMap.put("reqReportList", promoterSer.getReqReportList(modelMap, 1, 10));
		modelMap.put("TOTALREQCNT", promoterSer.getReqReportListCnt(modelMap));

		LinkedHashMap paraMap = new LinkedHashMap();
		paraMap.put("APPLY_TYPE", "278651");
		paraMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySer.getEssFileList(paraMap);
		modelMap.put("fileList", fileList);
		
		return new ModelAndView("/LGEP/affirm/viewSelloutAffirm", modelMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/expSelloutReqDtlExcelA")
	public void expSelloutReqDtlExcelA(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		modelMap.put("REQ_ID", paramMap.get("REQ_ID").toString());
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID").toString());
		modelMap.put("LANGUAGE", paramMap.get("LANGUAGE").toString());
		String[] colnames1 = { "大区","支社","产品类型","销售数量","支社核对","发票销量","截屏及凭证销量"};
		String[] colnames2 = { "门店编码","PR社番","销售时间","产品型号","汇总","办事处","卖场名称","门店等级","渠道等级","渠道","Bill to渠道","支付比例(%)","确认销量"};
		String[] colnames3 = { "办事处","社号","姓名","门店代码","主责商场","channel","主责产品","当月销售金额","全月销量","提成合计" };
		String[] colnames4 = { "支社","社号","姓名","门店代码","主责商场","channel","主责产品","本月销量","上月销量","提成合计","上月提成","伸张率%" };
		String[] colnames5 = { "支社","社号","姓名","门店代码","主责商场","channel","主责产品","本月销量","上月销量","提成合计","上月提成","伸张率%" };
		String[] colnames6 = { "COME_CODE","社号","姓名","月份","销售数量","产品类型","产品ID","支社","客户ID","门店名称","CHANNEL_CODE","EZ上报销量","支社确认销量" };
		List aliasNameList = new ArrayList();
		aliasNameList.add(colnames1);
		aliasNameList.add(colnames2);
		aliasNameList.add(colnames3);
		aliasNameList.add(colnames4);
		aliasNameList.add(colnames5);
		aliasNameList.add(colnames6);
		String[] columns1 = { "PAY_AREA_NM", "BRANCH_NM", "PROD_TP_NM","TOTAL_NUM","BRANCH_QTY","INVOICE_QTY","SCREEN_QTY"};
		String[] columns2 = { "SHOP_CD","PR_EMPID","SALE_DATE","PROD_ID","SALS_QTY","BRANCH","SHOP_NAME","SHOP_LEVEL","CHANNEL_GRADE","CHANNEL_NAME","BILL_TO_NAME","PAY_RATE","REAL_QTY"};
		String[] columns3 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "SALS_AMT", "SALS_QTY", "INC_AMT" };
		String[] columns4 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "SALS_QTY", "LAST_SALS_QTY", "INC_AMT", "LAST_INC_AMT", "RATIO" };
		String[] columns5 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "SALS_QTY", "LAST_SALS_QTY", "INC_AMT", "LAST_INC_AMT", "RATIO" };
		String[] columns6 = { "COM_CODE", "EMP_NO", "EMP_NM", "SALE_MONTH", "SALE_QTY", "MODEL_CATEGORY_CODE", "MODEL_CODE", "BRANCH", "SHIP_TO_CODE", "SHIP_TO_NAME", "CHANNEL_CODE", "SALE_QTY", "SALE_QTY" };
		List aliasColList = new ArrayList();
		aliasColList.add(columns1);
		aliasColList.add(columns2);
		aliasColList.add(columns3);
		aliasColList.add(columns4);
		aliasColList.add(columns5);
		aliasColList.add(columns6);
		
		List valueList1 = this.promoterSer.getSelloutReqDtl(request);
		List valueList2 = promoterSer.getReqOver10List(modelMap);
		List valueList3 = promoterSer.getReqOver9kList(modelMap);
		List valueList4 = promoterSer.getReqRatioList(modelMap);
		List valueList5 = promoterSer.getReqExshopList(modelMap);
		List valueList6 = promoterSer.getReqReportList(modelMap, -1, -1);
		
		List aliasValueList = new ArrayList();
		aliasValueList.add(valueList1);
		aliasValueList.add(valueList2);
		aliasValueList.add(valueList3);
		aliasValueList.add(valueList4);
		aliasValueList.add(valueList5);
		aliasValueList.add(valueList6);
		
		String name = "SelloutReqDtlExcel";
		String[] sheets = { "实贩卖核对全月说明", "团购、批发10台以上明细","提成9000元以上明细","对比上月伸张率（提成5000元以上，对比上月伸张率100%以上）","专卖店明细","上传明细" };
		this.excelUtilSer.exportExcelMoreTab(request, response, modelMap, aliasValueList, aliasNameList, aliasColList, name, sheets, modelMap);
	}

	/**
	 * 促销员实绩审批
	 * @author CH.W.G
	 */
	@RequestMapping(value = "/affirmSellout")
	@ResponseBody
	public Map<String, Object> affirmSellout (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = promoterSer.affirmSellout(request) ;
		String result = retMap.get("RET").toString();

		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "审批完成");
			map.put("navTabId", "ess0520");
			map.put("callbackType", "closeCurrent");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}
	
	/**
	 * 离职交接项目审批和查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmEditionItem")
	public ModelAndView viewAffirmEditionItem(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List affirmItemList = editionAffirmSer
				.getAffirmEditionItemList(modelMap, request);
		if(request.getParameter("personId")==null && "".equals(request.getParameter("personId"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("personId", admin.getPersonId());
		}else{
			modelMap.put("personId", request.getParameter("personId"));
		}
		LinkedHashMap affirmItemInfo = null;
		if(affirmItemList != null && affirmItemList.size() > 0){
			affirmItemInfo = (LinkedHashMap) affirmItemList.get(0);
		}
		modelMap.put("affirmItemInfo", affirmItemInfo);
		modelMap.put("affirm_fg", affirmItemInfo.get("AFFIRM_FG"));
		modelMap.put("affirmItemList", affirmItemList);
		return new ModelAndView("/LGEP/affirm/viewAffirmEditionItem",
				modelMap);
	}
	
	
	/**
	 *离职交接项目审批
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-7-14 下午4:46:40 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePersonEditionParamInfo")
	@ResponseBody
	public Map updatePersonEditionParamInfo (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				int result = this.dimissionEditionSer
						.updatepersonEditionParamInfo(request);
					map.put("statusCode", "200");
					map.put("message", 
							"确认成功");// 添加成功
					map.put("rel", "viewEditionItemTypeData");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("statusCode", "300");
			map.put("message",
					"确认失败");// 添加成功
			map.put("rel", "viewEditionItemTypeData");
		}
		return map;
	}
	

	/**
	 * 临时职入职查询(决裁)
	 */
	@RequestMapping(value = "/viewReqHireAffirm")
	public ModelAndView viewReqHireAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}

		modelMap.put("PERSON_ID", personId);
		List affirmorList = affirmApplySerOt.getHireAffirmByReqID(request);
		List checkorList = affirmApplySerOt.getCheckorByByReqID(request);
		List reqList = affirmApplySerOt.getHireAffirm(request);
		if(reqList.size()>0){
			modelMap.put("essAffirmReq", (LinkedHashMap)reqList.get(0));
		}
		LinkedHashMap paraMap = new LinkedHashMap();
		paraMap.put("APPLY_TYPE", "23292329");
		paraMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySer.getEssFileList(paraMap);
		
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(personId.equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("fileList", fileList);
		List infoApplyHire = affirmApplySerOt.getHireAffirmListByReqID(request);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, affirmApplySerOt.getHireAffirmListByReqIDCnt(request));
		modelMap.put("infoApplyHire", infoApplyHire);
		
		return new ModelAndView("/LGEP/affirm/viewReqHireAffirm",modelMap);

	}
	
	/**
	 * 临时职入职决裁
	 */
	@RequestMapping(value = "/affirmReqHire")
	@ResponseBody
	public Map<String, Object> affirmReqHire (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int result = affirmApplySerOt.approveApplyHire(request);
		if(result==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "hr0306");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}
	
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data,charset=UTF-8");
		
		String url=new String(request.getParameter("fileName").getBytes("ISO-8859-1"),"UTF-8");
		String fileNameAb = URLEncoder.encode(request.getParameter("file"), "UTF-8");
		String fileName=request.getRealPath("")+url;
		String fileNameA = new String(request.getParameter("file").getBytes("ISO-8859-1"),"UTF-8");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ URLEncoder.encode(request.getParameter("file"), "UTF-8"));
		
		try {
			File file=new File(fileName);
			InputStream inputStream=new FileInputStream(file);
			OutputStream os=response.getOutputStream();
			byte[] b=new byte[102400];
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
	/**
	 * 临时职离职决裁
	 */
	@RequestMapping(value = "/affirmReqResign")
	@ResponseBody
	public Map<String, Object> affirmReqResign (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int result = affirmApplySerOt.approveApplyResign(request);
		if(result==1){
			jo.put("statusCode", "200");
			jo.put("message", "审批成功");//保存成功
			jo.put("navTabId", "hr0206");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", "审批失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 离职人员工资补发
	 */
	/**
	 * 查看完整离职员工薪资补发申请事由信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullPaForLeftApplyAffirmorList")
	public ModelAndView viewFullPaForLeftApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			personId = admin.getPersonId();
		}
		modelMap.put("APPLY_NO", request.getParameter("BATCH_NO"));
		modelMap.put("BATCH_NO", request.getParameter("BATCH_NO"));
		modelMap.put("PERSON_ID",request.getParameter("personId"));
		List paForLeftApplyList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		LinkedHashMap paForLeftMap = new LinkedHashMap();
		if(paForLeftApplyList!=null && paForLeftApplyList.size()>0){
			paForLeftMap = (LinkedHashMap)paForLeftApplyList.get(0);
		}
		modelMap.put("paForLeftMap", paForLeftMap);
		List paForLeftDetailList = this.paForleftMenSer.getPaForLeftMenDetailList(request);
		int paForLeftDetailListCnt = this.paForleftMenSer.getPaForLeftMenDetailListCnt(request);
		modelMap.put("paForLeftDetailList", paForLeftDetailList);
		modelMap.put("paForLeftDetailListCnt", paForLeftDetailListCnt);
		
		List affirmorList = paForleftMenSer.getPaForLeftAffirmorByApplyNoList(request);
		List checkorList = paForleftMenSer.getPaForLeftCheckorByApplyNoList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		String affirmFlag = "";
		String affirmPerson = "";
		LinkedHashMap paramMap = new LinkedHashMap();
		// 查找当前需要check信息编号（第一条未check的信息）
		for (int i = 0; i < affirmorList.size(); i++) {
			paramMap = (LinkedHashMap) affirmorList.get(i);
			if ("0".equals(paramMap.get("AFFIRM_FLAG").toString())) {
				affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
				affirmPerson = paramMap.get("AFFIRMOR_ID").toString();
				dept_level = i + 1;
				this.paForLeftMenDao.updatePaForLetMenDataToPersonId(paramMap);
				break;
			}
		}
		if(affirmPerson != null && !"".equals(affirmPerson) && personId != null && !"".equals(personId)){
			if(affirmPerson.equals(personId)){
				affirmFlag = paramMap.get("AFFIRM_FLAG").toString();
			}else{
				affirmFlag = "1";
			}
		}
		
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("affirmFlag", affirmFlag);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		return new ModelAndView("/LGEP/affirm/viewFullPaForLeftApplyAffirmorList", modelMap);
	}
	
	/**
	 * 决裁---离职人员工资补发申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaForLeftAffirmInfo")
	@ResponseBody
	public Map addPaForLeftAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = request.getParameter("AFFIRM_FLAG");
		try {
			int result = this.paForleftMenSer.approvePaForLeftApply_ep(request);
			if (result == 1) {
				map.put("navTabId", "pa0706");
				if(flag.equals("1")){
					map.put("message", "通过离职员工薪资补发申请成功!");
				}
				if(flag.equals("2")){
					map.put("message", "否决离职员工薪资补发申请成功!");
				}
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "裁决离职员工薪资补发申请出错,请重新申请!");
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * check离职信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkLeftMenApplyInfo")
	public ModelAndView checkLeftMenApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List applyorList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		LinkedHashMap applyorInfo = (LinkedHashMap)applyorList.get(0);
		modelMap.put("applyorInfo", applyorInfo);
		List paForLeftDetailList = this.paForleftMenSer.getPaForLeftMenDetailList(request);
		int paForLeftDetailListCnt = this.paForleftMenSer.getPaForLeftMenDetailListCnt(request);
		modelMap.put("paForLeftDetailList", paForLeftDetailList);
		modelMap.put("paForLeftDetailListCnt", paForLeftDetailListCnt);
		
		List affirmList = this.paForleftMenSer.getPaForLeftAffirmorByApplyNoList(request);
		List checkList = this.paForleftMenSer.getPaForLeftCheckorByApplyNoList(request);
		
		String personId = request.getParameter("personId");
		// 查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		String affirmPerson = "";
		String checkFlag = "";
		LinkedHashMap paramMap = new LinkedHashMap();
		// 查找当前需要check信息编号（第一条未check的信息）
		for (int i = 0; i < checkList.size(); i++) {
			paramMap = (LinkedHashMap) checkList.get(i);
			if ("0".equals(paramMap.get("CHECK_FLAG").toString()) && personId.equals(paramMap.get("CHECKOR_ID").toString())) {
				check_no = paramMap.get("ESS_CHECK_NO").toString();
				affirmPerson = paramMap.get("CHECKOR_ID").toString();
				dept_level = i + 1;
				break;
			}
		}
		
		if(affirmPerson != null && !"".equals(affirmPerson) && personId != null && !"".equals(personId)){
			if(affirmPerson.equals(personId)){
				checkFlag = paramMap.get("CHECK_FLAG").toString();
			}else{
				checkFlag = "1";
			}
		}
		
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("checkFlag", checkFlag);
		modelMap.put("dept_level", dept_level);
		modelMap.put("personId", request.getParameter("personId"));
		modelMap.put("affirmList", affirmList);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt",
				affirmList == null ? 0 : affirmList.size());
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("BATCH_NO", request.getParameter("BATCH_NO"));

		return new ModelAndView("/LGEP/affirm/checkLeftMenApplyInfo",
				modelMap);
	}

	/**
	 * Check---离职申请 (check dimission apply)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkLefeMenInfo")
	@ResponseBody
	public Map checkLefeMenInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.affirmApplySer.checkApplyInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", "离职人员工资补发申请Check成功！");
		}else{
			map.put("statusCode", "300");
			map.put("message", "离职人员工资补发申请Check失败！");
		}
		return map;
	}
	
	
	
	
	
	
	
}
