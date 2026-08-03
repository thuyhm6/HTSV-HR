package com.ait.pa.action.salary;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.ess.service.AffirmApplySer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.salary.PaForLeftMenSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LGE Company: LGE
 * 
 * @fileName: PaForLeftMenCtroller.java
 * @Description:
 * @Create date: 2014-9-6 下午16:54:03
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PaForLeftMenCtroller {
	Logger logger = Logger.getLogger(PaForLeftMenCtroller.class);

	@Autowired
	private PaForLeftMenSer paForleftMenSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private AffirmApplySer affirmApplySer;
	@Autowired
	private CycleSer cycleSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddPaForleftMen")
	public ModelAndView  viewAddPaForleftMen(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List statList=null;
		statList = this.paForleftMenSer.getPaAllItemList(request);
		modelMap.put("paitemlist",statList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		
		return new ModelAndView("/pa/salary/viewAddPaForleftMen", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitPaForleftMen")
	@ResponseBody
	public Map  submitAddPaForleftMen(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map messMap = new LinkedHashMap();
		messMap = (LinkedHashMap) this.paForleftMenSer.submitAddPaForleftMen(request);
		if(messMap.get("scode").toString().equals("1")) {
			map.put("message", TipMessage.getTipMessage("alert.message.pa.salary.add_success",request));//修改成功
		}else if (messMap.get("scode").toString().equals("0")) {
			map.put("message", TipMessage.getTipMessage("alert.message.pa.salary.add_fail",request));//修改失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaForLeftMenList")
	public ModelAndView viewPaForLeftMenList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		
		String year = request.getParameter("YEAR")!=null?request.getParameter("YEAR").toString():b[0].trim().toString();
		String month = request.getParameter("MONTH")!=null?request.getParameter("MONTH").toString():b[1].trim().toString();
		modelMap.put("YEAR", year);
		modelMap.put("MONTH", month);
		
		List paForLeftDetailList = new ArrayList();
		int paForLeftDetailCnt = 0;
		paForLeftDetailList = this.paForleftMenSer.getPaForLeftMenDetailList(request);
		paForLeftDetailCnt = this.paForleftMenSer.getPaForLeftMenDetailListCnt(request);
		
		modelMap.put("paForLeftDetailList", paForLeftDetailList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paForLeftDetailCnt);
		modelMap.put("defaultCpny", admin.getCpnyId());
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217868")) ;
		
		return new ModelAndView("/pa/salary/viewPaForLeftMenList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaForLeftMenApplyList")
	public ModelAndView viewPaForLeftMenApplyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		
		String year = request.getParameter("YEAR")!=null?request.getParameter("YEAR").toString():b[0].trim().toString();
		String month = request.getParameter("MONTH")!=null?request.getParameter("MONTH").toString():b[1].trim().toString();
		modelMap.put("YEAR", year);
		modelMap.put("MONTH", month);
		
		List paForLeftApplyList = new ArrayList();
		int paForLeftApplyCnt = 0;
		paForLeftApplyList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		paForLeftApplyCnt = this.paForleftMenSer.getPaForLeftMenApplyListCnt(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		
		modelMap.put("paForLeftApplyList", paForLeftApplyList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paForLeftApplyCnt);
		modelMap.put("defaultCpny", admin.getCpnyId());
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217868")) ;
		
		return new ModelAndView("/pa/salary/viewPaForLeftMenApplyList", modelMap);
	}
	
	/**
	 * 进入添加申请页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/viewAddPaInfoForEmpLeftList")
	public ModelAndView viewAddPaInfoForEmpLeftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		//设置决裁人
		List affirmorList = this.paForleftMenSer.getApplyFeeList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		//List excelList = paTempSalesSer.getExcelMessage(request);
		List paInfoTempList = this.paForleftMenSer.getPaForLetMenTempList(request);//上传人员列表
		modelMap.put("paInfoTempList", paInfoTempList);
		modelMap.put("paInfoTempListCnt", this.paForleftMenSer.getPaForLetMenTempListCnt(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paForleftMenSer.getPaForLetMenTempListCnt(request));
		
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 批量删除未审核离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delPaForLeftApplyInBatch")
	@ResponseBody
	public Map<String, Object> delPaForLeftApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = this.paForleftMenSer.delPaForLeftApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "pa0708");
				map.put("message", "批量删除离职员工薪资补发申请决裁成功!");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量删除离职员工薪资补发申请决裁出错,请重新操作!");//批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 单条删除未审核离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delPaForLeftApply")
	@ResponseBody
	public String delPaForLeftApply(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = this.paForleftMenSer.delPaForLeftApply(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**
	 * 保存添加信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savePaInfoForEmpLeft")
	@ResponseBody
	public Map savePaInfoForEmpLeft(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = request.getParameter("FLAG");
		int errorInt = 0;
		if("1".equals(flag)){//保存数据
			errorInt = paForleftMenSer.savePaForLeftMenInfo(request);
		}else if ("0".equals(flag)) {//撤销删除数据
			errorInt = paForleftMenSer.delPaForLeftMenInfo(request);
		}
		if (errorInt == 1) {
	        map.put("navTabId", "ess0213");
	        map.put("message", "保存批量导入加班申请成功!");//保存批量导入加班申请成功!
	        map.put("statusCode", "200");
	        map.put("callbackType", "closeCurrent");
	        //modelMap.put("forwardUrl","/ess/infoApply/viewPOtImportList");
		}else{
	        map.put("message", "导入的加班申请有错误,请修改!");//导入的加班申请有错误,请修改!
	        map.put("statusCode", "200");
	    }
		if (errorInt > 0) {
			map.put("statusCode", "200");
			if("1".equals(flag)){
				map.put("callbackType", "closeCurrent");
				map.put("message", "离职员工薪资补发申请成功!");
			}
			map.put("navTabId", "pa0708");
		} else {
			map.put("statusCode", "300");
			if("1".equals(flag)){
				map.put("message", "离职员工薪资补发申请失败!");
			}
		}
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217868"));
		return map;
	}*/
	
	/**
	   * 导入离职员工薪资补发申请 (import batch ot apply)
	   * 
	   * @param request
	   * @param response
	   * @param modelMap
	   * @return
	   * @throws Exception
	   */
	  @SuppressWarnings("unchecked")
	  @RequestMapping(value = "/savePaInfoForEmpLeft")
	  @ResponseBody
	  public Map savePaInfoForEmpLeft(HttpServletRequest request,
	      HttpServletResponse response, ModelMap modelMap) throws Exception {
	    Map<String, Object> map = new HashMap<String, Object>();
	    int result = 0;
	    try {
	      result = paForleftMenSer.savePaForLeftMenInfo(request);
//	      if (result == 0) {
//	    	  map.put("message", "保存批量导入离职员工薪资补发申请成功!");//保存导入离职员工薪资补发申请成功!
//	    	  map.put("statusCode", "200");
//	    	  map.put("callbackType", "closeCurrent");
//	    	  map.put("navTabId", "pa0708");
//	    	 // modelMap.put("forwardUrl","/pa/salary/viewAddPaInfoForEmpLeftList");
//	      }else 
	      if(result == 0){
	    	  map.put("message", "批量导入离职员工薪资补发申请成功!");//导入离职员工薪资补发申请有错误,请检查!
	    	  map.put("statusCode", "200");
	    	  map.put("callbackType", "closeCurrent");
	    	  map.put("navTabId", "pa0708");
	    	  result = 0;
	    	  //modelMap.put("forwardUrl","/pa/salary/viewAddPaInfoForEmpLeftList");
	      }else{
	    	  map.put("message", "导入的离职员工薪资补发申请有错误,请修改!");//导入的离职员工薪资补发申请有错误,请修改!
	    	  map.put("statusCode", "200");
	    	  result = 1;//出错
	      }
	    } catch (CommonException e) {
	    	map.put("message", e.getMessage());
	    	map.put("statusCode", "300");
	    	result = 3;//出错
	    } catch (Exception e) {
	    	System.out.println(e.toString());
	    	map.put("message", "批量保存离职员工薪资补发申请失败,请重试!");//导入离职员工薪资补发申请,请重试!
	    	map.put("statusCode", "300");
	    	result = 3;//出错
	    }
	    map.put("result", result);
	    return map;
	  }
	
	  /**
	   * 取消所有导入的离职员工薪资补发申请( delete overtime apply import)
	   * 
	   * @param request
	   * @param response
	   * @param modelMap
	   * @author weizhengchen
	   * @return
	   * @throws Exception
	   */
	  @SuppressWarnings("unchecked")
	  @RequestMapping(value = "/deletePaForLeftApplyImport")
	  @ResponseBody
	  public Map deletePaForLeftApplyImport(HttpServletRequest request,
	      HttpServletResponse response, ModelMap modelMap) throws Exception{
	    Map<String, Object> map = new HashMap<String, Object>();
	    String msg= ""+this.paForleftMenSer.cancelPaForLeftApplyImport(request);
	    if("1".equals(msg)){
	    	map.put("statusCode", "200");
	    	map.put("message", "取消成功！");//取消成功
	    	map.put("navTabId", "pa0708");
	    	map.put("callbackType", "closeCurrent");
	    	//modelMap.put("forwardUrl","/pa/salary/viewAddPaInfoForEmpLeftList");
	    }else{
	    	map.put("statusCode", "300");
	    	map.put("message", "取消失败！");//取消失败
	    }
	    return map;
	  }
	  
	  /**
	   * 离职员工补发薪资模版下载
	   * @param request
	   * @param response
	   * @param modelMap
	   * @return ModelAndView
	   * @throws Exception
	   */
	
	  @SuppressWarnings({ "unchecked" })
	  @RequestMapping(value = "/exportPaForLeftMenModle")
	  public void exportPaForLeftMenModle(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = this.paForleftMenSer.getPaForLeftMenModleInfo(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportPaForLeftMenModelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	  }
	
	/**
	 * 离职员工补发薪资Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/exportPaForLeftMenInfoList")
	public void exportPaForLeftMenInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = this.paForleftMenSer.getPaForLeftMenInfoList(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportPaForLeftMenModelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 离职员工补发薪资Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	/*@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaForLeftMenInfoList2")
	public void exportPaForLeftMenInfoList2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		@SuppressWarnings("unused")
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");//社号
		aliasNameList.add("姓名");//
		aliasNameList.add("项目类型");//项目类型
		aliasNameList.add("补发月份");//
		aliasNameList.add("补发项目");//
		aliasNameList.add("金额");//
		aliasNameList.add("发放月份");//
		aliasNameList.add("备注");//
		aliasNameList.add("正/异常");//
		aliasNameList.add("错误提示");//错误提示

		List list = new ArrayList();
		String sqlDetailInfo= " SELECT PA.PA_MONTH,PA.EMPID,PA.LOCAL_NAME,PA.CPNY_ID,PA.PA_MONTH_FOR, "
				       + " PA.ITEM_TYPE,PA.ITEM_NO,PA.ITEM_DATA,PA.REMARK,PA.CHECK_FLAG,PA.CHECK_ERROR "
				       + " FROM PA_LEFTMEN_ADDING_TEMP PA "
				       + " WHERE PA.CPNY_ID = '"+admin.getCpnyId()+ "'"
				       + " AND PA.CREATED_BY = '"+admin.getPersonId()+"' ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap empInfo = new LinkedHashMap();
			empInfo = (LinkedHashMap) itemDetailList.get(i);
			list.add(empInfo);
		}
		String name = "viewPaForLeftMenInfoExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,sqlContentmap, aliasNameList, null, null, name);
	}*/
	
	/**
	 * 离职员工薪资补发申请--决裁列表(pa info of emp for left apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaForLeftMenAffirmList")
	public ModelAndView viewPaForLeftMenAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("paForLeftAffirmList", this.paForleftMenSer.getPaForLeftMenAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paForleftMenSer.getPaForLeftMenAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/pa/salary/viewPaForLeftMenAffirmList",modelMap);
	}
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
	@RequestMapping(value = "/viewPaForLeftApplyContentInfo")
	public ModelAndView viewPaForLeftApplyContentInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List paForLeftApplyList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		LinkedHashMap applyMap = (LinkedHashMap)paForLeftApplyList.get(0);
		modelMap.put("BATCH_NO", applyMap.get("BATCH_NO").toString());
		modelMap.put("APPLY_CONTENT", applyMap.get("APPLY_CONTENT")==null?"":(String)applyMap.get("APPLY_CONTENT"));
		
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/pa/salary/viewPaForLeftApplyContentInfo", modelMap);
	}
	
	/**
	 * 查看-离职员工薪资补发申请决裁信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullPaForLeftApplyCheckInfo")
	public ModelAndView viewFullPaForLeftApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		List paForLeftApplyList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		LinkedHashMap paForLeftMap = (LinkedHashMap)paForLeftApplyList.get(0);
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
		
		return new ModelAndView("/pa/salary/viewFullPaForLeftApplyCheckInfo", modelMap);
	}
	
	/**
	 * 查看完整离职员工薪资补发申请决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullPaForLeftApplyAffirmInfo")
	public ModelAndView viewFullApplyAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = paForleftMenSer.getPaForLeftAffirmorByApplyNoList(request);
		List checkorList = paForleftMenSer.getPaForLeftCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		//modelMap.put("infoApplyLeave", (LinkedHashMap) infoApplyLeaveSer.getLeaveInfoByLeave(request));
		
		return new ModelAndView("/pa/salary/viewFullPaForLeftApplyAffirmInfo", modelMap);
	}
		
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
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		List paForLeftApplyList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		LinkedHashMap paForLeftMap = (LinkedHashMap)paForLeftApplyList.get(0);
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
		
		modelMap.put("APPLY_NO", request.getParameter("BATCH_NO"));
		modelMap.put("BATCH_NO", request.getParameter("BATCH_NO"));
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
				break;
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("affirmFlag", affirmFlag);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		
		return new ModelAndView("/pa/salary/viewFullPaForLeftApplyAffirmorList", modelMap);
	}
	
	/**
	 * 决裁---加班申请 (add overtime apply)
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
	 * 批量通过/否决加班申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePaForLeftApplyInBatch")
	@ResponseBody
	public Map<String, Object> approvePaForLeftApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = this.paForleftMenSer.approvePaForLeftApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "pa0706");
				map.put("message", "离职员工薪资补发申请审批成功!");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "离职员工薪资补发申请审批失败!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 添加check信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaForLeftCheckView")
	public ModelAndView addPaForLeftCheckView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		
		return new ModelAndView("/pa/salary/addPaForLeftCheckView", modelMap);
	}
	
	/**
	 * 添加Checkor---离职员工薪资补发申请 (add checkor for apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaForLeftApplyCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaForLeftApplyCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		int result = this.paForleftMenSer.addApplyCheckList(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("添加Checkor者成功！",request));//添加Checkor者成功！
			map.put("navTabId", "pa0708_affirm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("添加Checkor者失败！",request));//添加Checkor者失败！
		}
		return map;
	}
	
	/**
	 * 离职员工薪资补发申请check列表(pa info for emp of left apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaForLeftCheckList")
	public ModelAndView viewPaForLeftCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", admin.getPersonId());
		
		modelMap.put("paForLeftCheckList", this.paForleftMenSer.getPaForLeftCheckList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paForleftMenSer.getPaForLeftCheckListCnt(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "363")) ;
		
		return new ModelAndView("/pa/salary/viewPaForLeftCheckList",modelMap);
	}
	
	/**
	 * check离职员工薪资补发申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkPaForLeftApplyInfo")
	public ModelAndView checkPaForLeftApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());
		
		List paForLeftApplyList = this.paForleftMenSer.getPaForLeftMenApplyList(request);
		LinkedHashMap paForLeftMap = (LinkedHashMap)paForLeftApplyList.get(0);
		modelMap.put("paForLeftMap", paForLeftMap);
		List paForLeftDetailList = this.paForleftMenSer.getPaForLeftMenDetailList(request);
		int paForLeftDetailListCnt = this.paForleftMenSer.getPaForLeftMenDetailListCnt(request);
		modelMap.put("paForLeftDetailList", paForLeftDetailList);
		modelMap.put("paForLeftDetailListCnt", paForLeftDetailListCnt);
		
		List affirmorList = this.paForleftMenSer.getPaForLeftAffirmorByApplyNoList(request);
		List checkorList = this.paForleftMenSer.getPaForLeftCheckorByApplyNoList(request);
		
		modelMap.put("is_check", is_check);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/pa/salary/checkPaForLeftApplyInfo", modelMap);
	}
	
	/**
	 * Check---离职员工薪资补发申请  (check pa info for emp of left apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkPaForLeftApply",method = RequestMethod.POST)
	@ResponseBody
	public Map checkPaForLeftApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.affirmApplySer.checkPaForLeftApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.check",request));//Check成功
			map.put("navTabId", "pa0718");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_failure.check",request));//Check失败
		}
		return map;
	}
}
