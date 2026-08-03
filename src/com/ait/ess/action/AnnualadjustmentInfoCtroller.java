package com.ait.ess.action;

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

import com.ait.ar.dao.ArMonthCalculateDao;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.ess.service.AnnualadjustmentInfoSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.PersonInfoSer;
import com.ait.hrm.action.TransferOrderCtroller;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;


@Controller
@RequestMapping(value = "/ess/annualadjustment")
public class AnnualadjustmentInfoCtroller {


	Logger logger = Logger.getLogger(TransferOrderCtroller.class);

	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired 
	private ToolMenuSer toolMenuSer;
	@SuppressWarnings("unused")
	@Autowired
	private PersonInfoSer personInfoSer;

	@Autowired
	private InfoApplySer infoApplySer;
	
	@Autowired
	private InfoApplyLeaveSer infoapplyleaveser;
	
	@Autowired
	private AnnualadjustmentInfoSer annualadjustmentInfoSer;
	 
	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;

	@Autowired
	private DynamicGroupSer dynamicGroupSer ;
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	
	
	@Autowired
	private ArMonthCalculateDao arMonthCalculateDao;
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	/**
	 * 年假调整查看页面(view Annual adjustment information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAnnualadjustmentInfo")
	public ModelAndView viewAnnualadjustmentInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
	    modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("annualadjustmentInfoList",annualadjustmentInfoSer.getAnnualadjustmentAffirmList(request,"N"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getAnnualadjustmentAffirmListCnt(request,"N"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "216675"));
		return new ModelAndView("/ess/annualadjustment/viewAnnualadjustmentInfo", modelMap); 
	}
	

	/**
	 * 批量删除年假调整申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAnnuApplyInBatch")
	@ResponseBody
	public Map<String, Object> delAnnuApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = annualadjustmentInfoSer.delAnnuApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0303");
				map.put("message", "删除年假调整成功！"); 
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量删除年假调整失败，请重新操作！"); 
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 进入年休假调整申请页面(view Annual adjustment Apply )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAnnualadjustmentApply")
	public ModelAndView viewAnnualadjustmentApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		modelMap.put("nowday", df.format(new Date())); // new Date()为获取当前系统时间
		List affirmorList = this.infoApplySer.getAnnualAffirmorList(request);
		
		 
	    modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfo(request));
	    modelMap.put("affirmorList", affirmorList);
	    modelMap.put("affirmorListCnt", affirmorList.size());
	    modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		return new ModelAndView("/ess/annualadjustment/viewAnnualadjustmentApply", modelMap);
	}
	
	
	/**
	 * 年假调整申请页面 (add Annualadjustment apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAnnualadjustmentApply")
	@ResponseBody
	public Map addAnnualadjustmentApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			String PERSON_ID = paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():"";
			String MONTHSTR = paramMap.get("SHENQINGDAY")!=null?paramMap.get("SHENQINGDAY").toString():"";
			String month  = MONTHSTR.substring(0, 4)+MONTHSTR.substring(5, 7);
			paramMap.put("MONTHSTR", month);
			paramMap.put("PERSON_ID", PERSON_ID);
			
			
			int result = annualadjustmentInfoSer.addAnnualadjustmentApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0303");
				map.put("callbackType", "closeCurrent");
				map.put("message", "年假调整申请成功!");//年假调整申请
				map.put("statusCode", "200");
				map.put("formId", "viewAnnualadjustmentInfopageForm");
			}
		
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "年假调整申请保存出错,请重新申请!");//年假调整申请保存出错,请重新申请!
			map.put("statusCode", "300");
		}
		
		return map;
	}
	
	
	/**
	 * 查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullAnnuAffirmInfo")
	public ModelAndView viewFullAnnuAffirmInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	

		LinkedHashMap annuMap = null;
		List annulist = annualadjustmentInfoSer.getAnnuapplyListByApplyno(request);
		if(annulist.size() > 0){
			annuMap = (LinkedHashMap)annulist.get(0);
			modelMap.put("annumap", annuMap);
		}
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( annuMap != null  && "Y".equals(StringUtil.checkNull(annuMap.get("BATCH_YN")))){
			modelMap.put("arVacBatchAffirmList", annualadjustmentInfoSer.getArVacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getArVacBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/ess/annualadjustment/viewFullAnnuAffirmInfo", modelMap);
	}


	

	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAnnApplyInBatch")
	@ResponseBody
	public Map<String, Object> delAnnApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = annualadjustmentInfoSer.delAnnApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0303");
				map.put("message", TipMessage.getTipMessage("删除申请成功！",request));
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("删除申请失败，请重新操作！",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	
	/**
	 * check
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkAnnApplyCheckInfo")
	public ModelAndView checkAnnApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List annulist = annualadjustmentInfoSer.getAnnuapplyListByApplyno(request);
		if(annulist.size() > 0){
			LinkedHashMap annuMap = (LinkedHashMap)annulist.get(0);
			modelMap.put("annumap", annuMap);
		}
//		is_check 用来判断查看或提交权限
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());
		//annualadjustmentInfoSer
		List affirmorList = annualadjustmentInfoSer.getAffirmorByApplyNoList(request);
		List checkorList = annualadjustmentInfoSer.getCheckorByApplyNoList(request);

		String essCheckNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(admin.getPersonId().equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
				}
				break;
			}
		}
		modelMap.put("is_check", is_check);
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/infoApply/checkAnnApplyCheckInfo", modelMap);
	}
	
	/**
	 * 年假调整查看页面(view Annual adjustment information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAnnualadjustmentBatchInfo")
	public ModelAndView viewAnnualadjustmentBatchInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("admin", admin);
	    modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("annualadjustmentInfoList",annualadjustmentInfoSer.getAnnualadjustmentAffirmList(request,"Y"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getAnnualadjustmentAffirmListCnt(request,"Y"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "216675"));
		return new ModelAndView("/ess/annualadjustment/viewAnnualadjustmentBatchInfo", modelMap); 
	}
	

	
	/**
	 * 批量年假调整申请导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLArVacModule")
	public void exportBatchLArVacModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("申请人");
		aliasNameList.add("调整天数");
		aliasNameList.add("备注");
		aliasNameList.add(" ");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000003");
		map.put("CELL1", "张XX");
		map.put("CELL2", "2");
		map.put("CELL3", "年假调整");
		map.put("CELL4", " ");
		list.add(map);
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"ess_vac");
	}
	

	/**
	 * 漏刷卡导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelEssArVacDataList")
	public ModelAndView viewImportExcelEssArVacDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paEssArVacTempList = this.annualadjustmentInfoSer.getEssArVacTempList(request);
		int paEssArVacTempCnt = this.annualadjustmentInfoSer.getEssArVacTempCnt(request , "T");
		int errorCnt = this.annualadjustmentInfoSer.getEssArVacTempCnt(request , "E");
		
		modelMap.put("paEssArVacTempList", paEssArVacTempList);
		modelMap.put("paEssArVacTempCnt", paEssArVacTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEssArVacTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paEssArVacTempCnt);
		return new ModelAndView("/ess/recordApply/viewImportExcelEssArVacDataList", modelMap);
	}
	

	/**
	 * 年假调整批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelEssArVacEmpData")
	@ResponseBody
	public Map submitImportExcelEssArVacEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.annualadjustmentInfoSer.submitImportExcelEssArVacEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "ess0201");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	

	/**
	 * 批量删除年假调整申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delArVacApplyInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delArVacApplyInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "删除";
		if("1".equals(op_flag)){
			msg = "提交";
		}
		try {
			result = annualadjustmentInfoSer.delAracApplyInBatchForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0201");
				map.put("message", msg + "成功！");//"批量删除休假申请审批成功!"
				map.put("statusCode", "200");
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} 
		map.put("result", result);
		return map;
	}
	

	/**
	 * 查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullAnnuAffirmInfo1")
	public ModelAndView viewFullAnnuAffirmInfo1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	

		LinkedHashMap annuMap = null;
		List annulist = annualadjustmentInfoSer.getAnnuapplyListByApplyno(request);
		if(annulist.size() > 0){
			annuMap = (LinkedHashMap)annulist.get(0);
			modelMap.put("annumap", annuMap);
		}
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( annuMap != null  && "Y".equals(StringUtil.checkNull(annuMap.get("BATCH_YN")))){
			modelMap.put("arVacBatchAffirmList", annualadjustmentInfoSer.getArVacBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getArVacBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/ess/annualadjustment/viewFullAnnuAffirmInfo1", modelMap);
	}
	

	/**
	 * 批量加班审批者调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyAffirmorForBatchArVac")
	@ResponseBody
	public Map modifyAffirmorForBatchArVac(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifyAffirmorForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0201");
				map.put("message", "操作成功");//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "操作失败");
			map.put("statusCode", "300");
		}
		return map;
	}
}
