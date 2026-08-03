package com.ait.pa.action.salaryCanShu;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.action.attendanceSettings.CycleCtroller;
import com.ait.ar.service.CycleSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.JobTypeSer;
import com.ait.is.service.StopInsureSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.salaryCanShu.SalaryCanShuSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 工资参数...
 * @author LXJ
 *
 */
@Controller
@RequestMapping(value = "/pa/salaryCanShu")
public class SalaryCanShuCtroller {

	Logger logger = Logger.getLogger(SalaryCanShuCtroller.class);
	
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private SalaryCanShuSer SalryCanShuSer;
	@Autowired
	private JobTypeSer jobTypeSer;
	@Autowired
	private  StopInsureSer stopInstanceSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private CycleSer cycleSer;
	
	/**
	 * 工资参数的派遣地管理--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaiQianDiGuanLiList")
	public ModelAndView viewPaiQianDiGuanLiList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		/*//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//城市等级
		modelMap.put("csdj", request.getParameter("seach_chengshidengji"));
		//地区名称
		modelMap.put("dqmc", request.getParameter("seach_diqumingcheng"));
		request.setAttribute("faren", request.getParameter("seach_faren"));
		request.setAttribute("csdj", request.getParameter("seach_chengshidengji"));
		request.setAttribute("dqmc", request.getParameter("seach_diqumingcheng"));*/
		//查询派遣地
		List paiQianDiList = this.SalryCanShuSer.getPaiQianDiList(request) ;		
		int paiQianDiCnt = this.SalryCanShuSer.getPaiQianDiCnt(request) ;
		
		modelMap.put("paiQianDiList", paiQianDiList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paiQianDiCnt) ;
		
		//复制
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217890")) ;
		return new ModelAndView("/pa/salaryCanShu/viewPaiQianDiGuanLiList",modelMap);
	}
	
	/**
	 * 工资参数的最低工资标准(非促销员)--查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanList")
	public ModelAndView viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List zuiDiGongZiBiaoZhunList = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunList(request) ;
		int zuiDiGongZiBiaoZhunListCnt = this.SalryCanShuSer.geZuiDiGongZiBiaoZhunListCnt(request) ;
		
		modelMap.put("paiQianDiList", zuiDiGongZiBiaoZhunList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, zuiDiGongZiBiaoZhunListCnt) ;
		
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218560")) ;
		return new ModelAndView("/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanList",modelMap);
	}
	
	/**
	 * 工资参数的最低工资标准(促销员)--查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewZuiDiGongZiBiaoZhunCuXiaoYuanList")
	public ModelAndView viewZuiDiGongZiBiaoZhunCuXiaoYuanList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
			List zuiDiGongZiBiaoZhunList = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunCuXiaoYuanList(request) ;
			int zuiDiGongZiBiaoZhunListCnt = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt(request) ;
			modelMap.put("paiQianDiList", zuiDiGongZiBiaoZhunList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, zuiDiGongZiBiaoZhunListCnt) ;
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218561")) ;
		return new ModelAndView("/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunCuXiaoYuanList",modelMap);
	}
	
	/**
	 * 工资参数的派遣津贴标准导入临时结果页面--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaiQianDiJinTieBiaoZhunExcelResultList")
	public ModelAndView viewPaiQianDiJinTieBiaoZhunExcelResultList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		//把中文信息更新成NO
		this.SalryCanShuSer.updatePaiQianDiJinTieBiaoZhunExcelResultToNos(request);
		
		//查询派遣地--临时表
		List paiQianDiJinTieBiaoZhunTempList = this.SalryCanShuSer.getPaiQianDiJinTieBiaoZhunTempList(request) ;
		int paiQianDiJinTieBiaoZhunTempCnt = this.SalryCanShuSer.getPaiQianDiJinTieBiaoZhunTempListCnt(request) ;
		int errorCnt = this.SalryCanShuSer.getPaiQianDiJinTieBiaoZhunTempErrorCnt(request) ;
		
		modelMap.put("paiQianDiList", paiQianDiJinTieBiaoZhunTempList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paiQianDiJinTieBiaoZhunTempCnt);
		modelMap.put("errCnt", errorCnt);
		
		return new ModelAndView("/pa/salaryCanShu/viewPaiQianDiJinTieBiaoZhunExcelResultList",modelMap);
	}
	
	/**
	 * 工资参数的派遣地管理导入临时结果页面--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaiQianDiGuanLiExcelResultList")
	public ModelAndView viewPaiQianDiGuanLiExcelResultList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		@SuppressWarnings("unused")
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//把中文信息更新成NO----lufeng----不需要更新，导入数据进行提交的时候再进行更新
		//this.SalryCanShuSer.updatePaiQianDiGuanLiExcelResultToNos(request);
		List paiQianDiTempList = this.SalryCanShuSer.getPaiQianDiTempList(request) ;
		int paiQianDiTempCnt = this.SalryCanShuSer.getPaiQianDiTempCnt(request) ;
		int errorCnt = this.SalryCanShuSer.getPaiQianDiTempErrorCnt(request) ;
		
		modelMap.put("paiQianDiList", paiQianDiTempList);
		modelMap.put("errCnt", errorCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paiQianDiTempCnt);
		
		return new ModelAndView("/pa/salaryCanShu/viewPaiQianDiGuanLiExcelResultList",modelMap);
	}
	
	/**
	 * 保存导入的派遣地数据 (import pai qian di data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savePaiQianDiDataImport")
	@ResponseBody
	public Map savePaiQianDiDataImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = SalryCanShuSer.addImportPaiQianDiData(request);
			if (result == 1) {
				map.put("navTabId", "pa0801");
				map.put("message", "保存导入派遣地数据成功!");//保存导入派遣地数据成功!
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				//map.put("forwardUrl","/pa/salaryCanShu/viewPaiQianDiGuanLiExcelResultList");
			}else{
				map.put("message", "导入的派遣地数据有错误,请修改!");//导入的派遣地数据有错误,请修改!
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "保存派遣地数据失败,请重试!");//导入派遣地数据失败,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 导出派遣地临时表里的所有派遣地数据信息，进行修改，然后再导入---带有参数表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportPqdDataExcel")
	public void viewImportPqdDataExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getPaiQianDiImportInfo(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportPaiQianDiModelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 导出派遣地临时表里的所有派遣地数据信息，进行修改，然后再导入--不带有参考表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportPqdDataExcel2")
	public void viewImportPqdDataExcel2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("法人");
		aliasNameList.add("城市等级");
		aliasNameList.add("省份");
		aliasNameList.add("城市名称");
		aliasNameList.add("地区名称");
		aliasNameList.add("正/异常");
		aliasNameList.add("错误提示");

		List dataList = new ArrayList();
		List pqdDataImportList = this.SalryCanShuSer.getPaiQianDiImportInfoList(request);
		for(int i=0;i<pqdDataImportList.size();i++){
			LinkedHashMap pqdDataMap = new LinkedHashMap();
			pqdDataMap = (LinkedHashMap)pqdDataImportList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", pqdDataMap.get("PQD_FAREN")!=null?pqdDataMap.get("PQD_FAREN").toString():"");
			map.put("CELL1", pqdDataMap.get("PQD_CHENGSHIDENGJI")!=null?pqdDataMap.get("PQD_CHENGSHIDENGJI").toString():"");
			map.put("CELL2", pqdDataMap.get("PQD_SHENGFEN")!=null?pqdDataMap.get("PQD_SHENGFEN").toString():"");
			map.put("CELL3", pqdDataMap.get("PQD_CHENGSHIMINGCHENG")!=null?pqdDataMap.get("PQD_CHENGSHIMINGCHENG").toString():"");
			map.put("CELL4", pqdDataMap.get("PQD_DIQUMINGCHENG")!=null?pqdDataMap.get("PQD_DIQUMINGCHENG").toString():"");
			String checkFlag = "";
			if(pqdDataMap.get("PQD_DAORU_RESULT")!=null && "1".equals(pqdDataMap.get("PQD_DAORU_RESULT").toString())){
				checkFlag = "异常";
			}else{
				checkFlag = "正常";
			}
			map.put("CELL5", checkFlag);
			map.put("CELL6", pqdDataMap.get("CHECK_ERROR")!=null?pqdDataMap.get("CHECK_ERROR").toString():"无");
			
			dataList.add(map);
		}
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 工资参数的派遣地管理导入临时结果页面--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultList")
	public ModelAndView viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//导入之后显示之前要把福利地区改成对应NO数
		this.SalryCanShuSer.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos(request);
		List paiZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(request) ;
		int zuiDiGongZiBiaoZhunFeiCuXiaoYuanTempCnt = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt(request) ;
		int errorCnt = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt(request) ;
		
		modelMap.put("paiQianDiList", paiZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList);
		modelMap.put("errCnt", errorCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, zuiDiGongZiBiaoZhunFeiCuXiaoYuanTempCnt);
		
		return new ModelAndView("/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultList",modelMap);
	}
	
	@RequestMapping(value = "/viewZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultList")
	public ModelAndView viewZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//导入之后显示之前要把福利地区改成对应NO数
		this.SalryCanShuSer.updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos(request);
		
		List paiZuiDiGongZiBiaoZhunCuXiaoYuanTempList = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(request) ;
		int zuiDiGongZiBiaoZhunCuXiaoYuanTempCnt = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt(request) ;
		int errorCnt = this.SalryCanShuSer.getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt(request) ;
		
		modelMap.put("paiQianDiList", paiZuiDiGongZiBiaoZhunCuXiaoYuanTempList);
		modelMap.put("errCnt", errorCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, zuiDiGongZiBiaoZhunCuXiaoYuanTempCnt);
		
		return new ModelAndView("/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultList",modelMap);
	}
	
	/**
	 * 预提对象管理--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewYuTiDuiXiangGuanLiList")
	public ModelAndView viewYuTiDuiXiangGuanLiList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//法人       
		modelMap.put("faren", request.getParameter("seach_faren"));
		
		modelMap.put("daqu", request.getParameter("seach_daqu"));
		//人员类型组
		modelMap.put("renyuanleixingzu", request.getParameter("seach_renyuanleixingzu"));
		//是否参与预提
		modelMap.put("shifoucanyuyuti", request.getParameter("seach_shifoucanyuyuti"));
		//是否启用
		modelMap.put("shifouqiyong", request.getParameter("seach_shifouqiyong"));
		
		request.setAttribute("faren", request.getParameter("seach_faren"));
		request.setAttribute("daqu", request.getParameter("seach_daqu"));
		request.setAttribute("renyuanleixingzu", request.getParameter("seach_renyuanleixingzu"));
		request.setAttribute("shifoucanyuyuti", request.getParameter("seach_shifoucanyuyuti"));
		request.setAttribute("shifouqiyong", request.getParameter("seach_shifouqiyong"));
		//查询预提对象
		List yuTiDuiXiangGuanList = this.SalryCanShuSer.getYuTiDuiXiangGuanList(request) ;
		modelMap.put("paiQianDiList", yuTiDuiXiangGuanList);
		
		int yuTiDuiXiangGuanListCnt = this.SalryCanShuSer.getYuTiDuiXiangGuanListCnt(request) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, yuTiDuiXiangGuanListCnt) ;
		//人员类型组
		List jobTypeGroupNameList = this.jobTypeSer.getJobTypeGroupNameList(request) ;
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		//大区名称
		List daQuNamesList = this.SalryCanShuSer.getDaQuNamesList(request) ;
		if(null != daQuNamesList){
			modelMap.put("daQuNamesList", daQuNamesList);
		}
		
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218317")) ;
		return new ModelAndView("/pa/salaryCanShu/viewYuTiDuiXiangGuanLiList",modelMap);
	}
	
	/**
	 * 年终奖预提--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewNianZhongJiangYuTiList")
	public ModelAndView viewNianZhongJiangYuTiList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		//年度
		modelMap.put("niandu", request.getParameter("seach_niandu"));
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
		request.setAttribute("niandu", request.getParameter("seach_niandu") == null ? df.format(new Date()) : request.getParameter("seach_niandu"));
		if( 1 != authority){
			request.setAttribute("faren", admin.getCpnyId());
			modelMap.put("faren", admin.getCpnyId());
		}else{
			request.setAttribute("faren",request.getParameter("seach_faren"));
		}
		
		//查询年终奖计提
		List nianZhongJiangJiTiList = this.SalryCanShuSer.getNianZhongJiangJiTiList(request) ;
		modelMap.put("paiQianDiList", nianZhongJiangJiTiList);
		int nianZhongJiangJiTiListCnt = this.SalryCanShuSer.getNianZhongJiangJiTiListCntCnt(request) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, nianZhongJiangJiTiListCnt) ;
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218267")) ;
		modelMap.put("authority", authority);
		modelMap.put("companyList", SalryCanShuSer.getCompanyList(request));
		return new ModelAndView("/pa/salaryCanShu/viewNianZhongJiangYuTiList",modelMap);
	}
	
	/**
	 * 年终奖预提计算--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * <ait:SyCompany selected="${faren}" name="seach_faren" target="login" language="${interLanguage}" limit="ALL" activity="1"/>
	    				 <ait:date yearName="seach_zhifunian" yearSelected="${zhifunian}" monthName="seach_zhifuyue" monthSelected="${zhifuyue}"/>	
	    				 <input name="seach_bumen" value="${bumen}">
	    				  <input name="seach_shehaoxingming" value="${shehaoxingming}">
	 */
	@RequestMapping(value = "/viewNianZhongJiangYuTiJiSuanList")
	public ModelAndView viewNianZhongJiangYuTiJiSuanList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		//查询年终奖计提计算的数据
		List nianZhongJiangJiTiJiSuanList = this.SalryCanShuSer.getNianZhongJiangJiTiJiSuanList(request) ;
		modelMap.put("paiQianDiList", nianZhongJiangJiTiJiSuanList);
		int nianZhongJiangJiTiJiSuanListCnt = this.SalryCanShuSer.getNianZhongJiangJiTiJiSuanListCnt(request) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, nianZhongJiangJiTiJiSuanListCnt) ;
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218268")) ;

		modelMap.put("companyList", SalryCanShuSer.getCompanyList(request));
		return new ModelAndView("/pa/salaryCanShu/viewNianZhongJiangYuTiJiSuanList",modelMap);
	}
	
	/**
	 * 正规预提计算--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * <ait:SyCompany selected="${faren}" name="seach_faren" target="login" language="${interLanguage}" limit="ALL" activity="1"/>
	    				 <ait:date yearName="seach_zhifunian" yearSelected="${zhifunian}" monthName="seach_zhifuyue" monthSelected="${zhifuyue}"/>	
	    				 <input name="seach_bumen" value="${bumen}">
	    				  <input name="seach_shehaoxingming" value="${shehaoxingming}">
	 */
	@RequestMapping(value = "/viewZhengGuiYuTiJiSuanList")
	public ModelAndView viewZhengGuiYuTiJiSuanList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//支付年
		modelMap.put("zhifunian", request.getParameter("seach_zhifunian"));
		//支付月
		modelMap.put("zhifuyue", request.getParameter("seach_zhifuyue"));
		//部门
		modelMap.put("bumen", request.getParameter("seach_bumen"));
		//社号/姓名
		modelMap.put("shehaoxingming", request.getParameter("seach_shehaoxingming"));
		//人员类型
		modelMap.put("seach_leixing", request.getParameter("seach_leixing"));
		
		modelMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO"));
		
		if( 1 != authority){
			request.setAttribute("faren", admin.getCpnyId());
			modelMap.put("faren", admin.getCpnyId());
		}else{
			request.setAttribute("faren",request.getParameter("seach_faren"));
		}
		 
		request.setAttribute("zhifunian", request.getParameter("seach_zhifunian"));
		request.setAttribute("zhifuyue", request.getParameter("seach_zhifuyue"));
		request.setAttribute("bumen", request.getParameter("seach_bumen"));
		request.setAttribute("shehaoxingming", request.getParameter("seach_shehaoxingming"));
		request.setAttribute("seach_leixing", request.getParameter("seach_leixing"));
		
		//查询正规预提计算的数据
		List nianZhongJiangJiTiJiSuanList = this.SalryCanShuSer.viewZhengGuiYuTiJiSuanList(request) ;
		modelMap.put("paiQianDiList", nianZhongJiangJiTiJiSuanList);
		int nianZhongJiangJiTiJiSuanListCnt = this.SalryCanShuSer.viewZhengGuiYuTiJiSuanListCn(request) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, nianZhongJiangJiTiJiSuanListCnt) ;
		toolMenuSer.getToolMenu(request);
		modelMap.put("authority", authority);
		modelMap.put("companyList", SalryCanShuSer.getCompanyListYuti(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218409")) ;
		return new ModelAndView("/pa/salaryCanShu/viewZhengGuiYuTiJiSuanList",modelMap);
	}
	
	/**
	 * 派遣地津贴标准
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaiQianDiJinTieBiaoZhunList")
	public ModelAndView viewPaiQianDiJinTieBiaoZhunList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//职责
		modelMap.put("zhize", request.getParameter("seach_zhize"));
		//城市等级
		modelMap.put("csdj", request.getParameter("seach_chengshidengji"));
		//地区名称
		modelMap.put("dqmc", request.getParameter("seach_diqumingcheng"));
		request.setAttribute("faren", request.getParameter("seach_faren"));
		request.setAttribute("zhize", request.getParameter("seach_zhize"));
		request.setAttribute("csdj", request.getParameter("seach_chengshidengji"));
		request.setAttribute("dqmc", request.getParameter("seach_diqumingcheng"));
		//获取职责信息
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		//查询派遣地津贴标准
		List paiQianDiJtBzList = this.SalryCanShuSer.getPaiQianDiJtBzList(request) ;
		modelMap.put("paiQianDiList", paiQianDiJtBzList);
		int paiQianDiJtBzCnt = this.SalryCanShuSer.getPaiQianDiJtBzCnt(request) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paiQianDiJtBzCnt) ;
		modelMap.put("dqmcList", empInfoSer.getDqmcList(request));
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218180")) ;
		return new ModelAndView("/pa/salaryCanShu/viewPaiQianDiJinTieBiaoZhunList",modelMap);
	}
	/**
	 * 号俸管理
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHaoFengSetList")
	public ModelAndView viewHaoFengSetList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("POST_GRADE_NO", request.getParameter("seach_ZHIJI"));
		modelMap.put("PAY_STEP", request.getParameter("seach_HAOFENG"));
		modelMap.put("ACTIVITY", request.getParameter("seach_ACTIVITY"));
		
		
		request.setAttribute("seach_JIQUN",request.getParameter("seach_ZHIJI"));
		request.setAttribute("seach_HAOFENG", request.getParameter("seach_HAOFENG"));
		request.setAttribute("seach_ACTIVITY", request.getParameter("seach_ACTIVITY"));

		List  haoGrade = (List) this.SalryCanShuSer.viewHaoFengSetListGrade(request);
		
		List  haoGradeNo = (List) this.SalryCanShuSer.viewHaoFengSetListGradeNo(request);
		List haoFengInfo =(List) SalryCanShuSer.viewHaoFengSetListfenye(request);
		modelMap.put("haoFengInfo", haoFengInfo);
		
		modelMap.put("haoGradeNoList", haoGradeNo);
		modelMap.put("haoGradeList", haoGrade);
		modelMap.put("PAY_STEP_NAME",SalryCanShuSer.viewHaoFengNAME(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		int paiQianDiJtBzCnt = this.SalryCanShuSer.getHaoFengSheZhiListCnt(request) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paiQianDiJtBzCnt) ;
		toolMenuSer.getToolMenu(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218365"));
		
		return new ModelAndView("/pa/salaryCanShu/viewHaoFengSetList",modelMap);
	}
	/**
	 * 号俸管理
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHaoFengSetListExcel")
	public ModelAndView viewHaoFengSetListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		
		modelMap.put("POST_GRADE_NO", request.getParameter("seach_ZHIJI"));
		modelMap.put("PAY_STEP", request.getParameter("seach_HAOFENG"));
		modelMap.put("ACTIVITY", request.getParameter("seach_ACTIVITY"));
		
		request.setAttribute("seach_ZHIJI",request.getParameter("seach_ZHIJI"));
		request.setAttribute("seach_HAOFENG", request.getParameter("seach_HAOFENG"));
		request.setAttribute("seach_ACTIVITY", request.getParameter("seach_ACTIVITY"));
		
		List  haoGrade = (List) this.SalryCanShuSer.viewHaoFengSetListGrade(request);
		
		List  haoGradeNo = (List) this.SalryCanShuSer.viewHaoFengSetListGradeNo(request);
		List haoFengInfo =(List) SalryCanShuSer.viewHaoFengSetListfenyeExcel(request);
		modelMap.put("haoFengInfo", haoFengInfo);
		
		modelMap.put("haoGradeNoList", haoGradeNo);
		modelMap.put("haoGradeList", haoGrade);
	 
		
		return new ModelAndView("/pa/salaryCanShu/viewHaoFengSetListExcel",modelMap);
	}

	/**
	 * 派遣地修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaiQianDiGuanLiView",method = RequestMethod.GET)
	public ModelAndView updatePaiQianDiGuanLiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("paiQianDiInfo", SalryCanShuSer.getPaiQianDiInfo(request));
		return new ModelAndView("/pa/salaryCanShu/updatePaiQianDiGuanLiView",modelMap);
	}
	
	/**
	 * 派遣地修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanView",method = RequestMethod.GET)
	public ModelAndView updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("paiQianDiInfo", SalryCanShuSer.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo(request));
		return new ModelAndView("/pa/salaryCanShu/updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanView",modelMap);
	}
	
	@RequestMapping(value = "/updateZuiDiGongZiBiaoZhunCuXiaoYuanView",method = RequestMethod.GET)
	public ModelAndView updateZuiDiGongZiBiaoZhunCuXiaoYuanView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("paiQianDiInfo", SalryCanShuSer.getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo(request));
		return new ModelAndView("/pa/salaryCanShu/updateZuiDiGongZiBiaoZhunCuXiaoYuanView",modelMap);
	}
	
	/**
	 * 预提对象管理的修改页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateYuTiDuiXiangGuanLiView",method = RequestMethod.GET)
	public ModelAndView updateYuTiDuiXiangGuanLiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("paiQianDiInfo", SalryCanShuSer.getYuTiDuiXiangGuanLiInfo(request));
		
		//大区名称
		List daQuNamesList = this.SalryCanShuSer.getDaQuNamesList(request) ;
		if(null != daQuNamesList){
			modelMap.put("daQuNamesList", daQuNamesList);
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("interCpnyID", admin.getCpnyId());
		
		return new ModelAndView("/pa/salaryCanShu/updateYuTiDuiXiangGuanLiView",modelMap);
	}
	/**
	 * 派遣地修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNianZhongJiangYuTiView",method = RequestMethod.GET)
	public ModelAndView updateNianZhongJiangYuTiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("paiQianDiInfo", SalryCanShuSer.getNianZhongJiangYuTiInfo(request));
		return new ModelAndView("/pa/salaryCanShu/updateNianZhongJiangYuTiView",modelMap);
	}
	
	/**
	 * ...
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaiQianDiJinTieBiaoZhunView",method = RequestMethod.GET)
	public ModelAndView updatePaiQianDiJinTieBiaoZhunView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("paiQianDiInfo", SalryCanShuSer.getPaiQianDiJinTieBiaoZhunInfo(request));
		modelMap.put("dqmcList", empInfoSer.getDqmcListNew(request));
		return new ModelAndView("/pa/salaryCanShu/updatePaiQianDiJinTieBiaoZhunView",modelMap);
	}

	/**
	 * ...号俸设置 修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHaoFengSheZhiView",method = RequestMethod.GET)
	public ModelAndView updateHaoFengSheZhiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List  haoGrade = (List) this.SalryCanShuSer.viewHaoFengSetListGrade(request);
		
		List  haoGradeNo = (List) this.SalryCanShuSer.viewHaoFengSetListGradeNo(request);
	 
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		request.setAttribute("CPNY_ID", admin.getCpnyId());
		modelMap.put("haoGradeNoList", haoGradeNo);
		modelMap.put("haoGradeList", haoGrade);
		modelMap.put("haoFengSheZhiInfo", SalryCanShuSer.getHaoFengSheZhiInfo(request));
		return new ModelAndView("/pa/salaryCanShu/updateHaoFengSheZhiView",modelMap);
	}
	
	/**
	 * 派遣地管理--修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaiQianDiGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePaiQianDiGuanLiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updatePaiQianDiGuanLiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa0801");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.update_fail_repart_yt", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 最低工资标准非促销员--修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa0807");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	@RequestMapping(value = "/updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa0808");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 派遣地管理--修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateYuTiDuiXiangGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateYuTiDuiXiangGuanLiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updateYuTiDuiXiangGuanLiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa0803");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail_repart_yt", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 年终奖预提计算的调用存储
	 * @param request
	 * @return
	 * @throws Exception
	 * TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request)
	 */
	@RequestMapping(value = "/callNianZhongJiangYuTiJiSuanProduce")
	@ResponseBody
	public Map callNianZhongJiangYuTiJiSuanProduce(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
        String result = this.SalryCanShuSer.callNianZhongJiangYuTiJiSuanProduce(request);
        if("OK".equals(result )){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.jiSuanChengGong",
    				request));
			map.put("navTabId", "pa0705");
        }else{
			map.put("statusCode", "300");
			map.put("message", result);
        }
		return map;
	}
	/**
	 * 正规职预提计算的调用存储
	 * @param request
	 * @return
	 * @throws Exception
	 * TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request)
	 */
	@RequestMapping(value = "/callZhengGuiYuTiJiSuanProduce")
	@ResponseBody
	public void callZhengGuiYuTiJiSuanProduce(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String returnString = "Error";
        int resultNum = this.SalryCanShuSer.callZhengGuiYuTiJiSuanProduce(request);
        if(resultNum == 1){
        	returnString = TipMessage.getTipMessage("pa.salary.canShu.jiSuanChengGong",
    				request);
        }else{
        	returnString = TipMessage.getTipMessage("pa.salary.canShu.jiSuanShiBai",
    				request);
        }
        PrintWriter out = response.getWriter();
        out.println(JsonUtil.writeInternal(returnString));
		out.flush();
		out.close();
	}
	
	/**
	 * 删除派遣地信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaiQianDiGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePaiQianDiGuanLiInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.SalryCanShuSer.deletePaiQianDiGuanLiInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "pa0801");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}
	
	/**
	 * 删除预提对象管理的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteYuTiDuiXiangGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteYuTiDuiXiangGuanLiInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.SalryCanShuSer.deleteYuTiDuiXiangGuanLiInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "pa0803");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}
	/**
	 * 年终奖预提--修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNianZhongJiangYuTiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateNianZhongJiangYuTiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updateNianZhongJiangYuTiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa0704");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * ...
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaiQianDiJinTieBiaoZhunInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updatePaiQianDiJinTieBiaoZhunInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa0802");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else if(resultNum == 333){
					map.put("statusCode", "300");
					map.put("message", "在派遣地管理中不存在此信息！请先添加。");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	/**
	 * 
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateHaoFengDayMonAjax",method = RequestMethod.POST)
	@ResponseBody
	public void updateHaoFengDayMonAjax(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;chaset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		int returnString =0;
		int resultNum = this.SalryCanShuSer.getHaoFengDayMonAjax(request);
		 
		if(resultNum>0){
			returnString = 1;
		}
        PrintWriter out = response.getWriter();
        out.println(JsonUtil.writeInternal(returnString));
		out.flush();
		out.close();		
	}
	/**
	 * ...
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHaoFengSheZhiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateHaoFengSheZhiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.updateHaoFengSheZhiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.save_success", request));
					map.put("formId", "postName");
				} else if (resultNum == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
					map.put("navTabId", "pa0804");
				} else if(resultNum == 5){
					
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"pa.salary.canShu.riyueshenjiao", request));
					
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	/**
	 * 到派遣地管理--添加画面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaiQianDiJinTieBiaoZhunView",method = RequestMethod.GET)
	public ModelAndView addPaiQianDiJinTieBiaoZhunView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("dqmcListNew", empInfoSer.getDqmcListNew(request));
		//modelMap.put("dqmcList", empInfoSer.getDqmcList(request));
		return new ModelAndView("/pa/salaryCanShu/addPaiQianDiJinTieBiaoZhunView",modelMap);
	}
	/**
	 * 号俸管理--添加画面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHaoFengGuanLi",method = RequestMethod.GET)
	public ModelAndView addHaoFengGuanLi(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List  haoGrade = (List) this.SalryCanShuSer.viewHaoFengSetListGrade(request);
		
		List  haoGradeNo = (List) this.SalryCanShuSer.viewHaoFengSetListGradeNo(request);
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		request.setAttribute("CPNY_ID", admin.getCpnyId());
		modelMap.put("haoGradeNoList", haoGradeNo);
		modelMap.put("haoGradeList", haoGrade);
		return new ModelAndView("/pa/salaryCanShu/addHaoFengGuanLi",modelMap);
	}
	
	/**
	 * ...
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaiQianDiGuanLiView",method = RequestMethod.GET)
	public ModelAndView addPaiQianDiGuanLiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		return new ModelAndView("/pa/salaryCanShu/addPaiQianDiGuanLiView",modelMap);
	}
	
	/**
	 * 最低工资标准菲促销员--添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addZuiDiGongZiBiaoZhunFeiCuXiaoYuanView",method = RequestMethod.GET)
	public ModelAndView addZuiDiGongZiBiaoZhunFeiCuXiaoYuanView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		return new ModelAndView("/pa/salaryCanShu/addZuiDiGongZiBiaoZhunFeiCuXiaoYuanView",modelMap);
	}
	
	/**
	 * 最低工资标准促销员--添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addZuiDiGongZiBiaoZhunCuXiaoYuanView",method = RequestMethod.GET)
	public ModelAndView addZuiDiGongZiBiaoZhunCuXiaoYuanView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		return new ModelAndView("/pa/salaryCanShu/addZuiDiGongZiBiaoZhunCuXiaoYuanView",modelMap);
	}
	
	/**
	 * 预提对象管理--添加
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addYuTiDuiXiangGuanLiView",method = RequestMethod.GET)
	public ModelAndView addYuTiDuiXiangGuanLiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//大区名称
		List daQuNamesList = this.SalryCanShuSer.getDaQuNamesList(request) ;
		if(null != daQuNamesList){
			modelMap.put("daQuNamesList", daQuNamesList);
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("interCpnyID", admin.getCpnyId());
		
		return new ModelAndView("/pa/salaryCanShu/addYuTiDuiXiangGuanLiView",modelMap);
	}
	
	/**
	 * 年终奖预提--添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNianZhongJiangYuTiView",method = RequestMethod.GET)
	public ModelAndView addNianZhongJiangYuTiView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if( 1 != authority){
			modelMap.put("CPNY_ID", admin.getCpnyId());
		}else{
			modelMap.put("companyList", SalryCanShuSer.getCompanyList(request));
		}
		modelMap.put("authority", authority);
		return new ModelAndView("/pa/salaryCanShu/addNianZhongJiangYuTiView",modelMap);
	}
	
	
	/**
	 * 年终奖预提--添加操作
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNianZhongJiangYuTiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addNianZhongJiangYuTiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.addNianZhongJiangYuTiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0704");
				} else if(resultNum == 10){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_fr_null", request));
				}else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	
	/**
	 * 派遣地管理--添加
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaiQianDiGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaiQianDiGuanLiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.addPaiQianDiGuanLiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0801");
				} else if(resultNum == 10){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_fr_null", request));
				}else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 最低工资标准津贴非促销员--添加
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0807");
				} else if(resultNum == 10){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_fr_null", request));
				}else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 最低工资标准津贴促销员--添加
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addZuiDiGongZiBiaoZhunCuXiaoYuanInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0808");
				} else if(resultNum == 10){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_fr_null", request));
				}else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 派遣地管理--添加
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addYuTiDuiXiangGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addYuTiDuiXiangGuanLiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.addYuTiDuiXiangGuanLiInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0803");
				} else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaiQianDiJinTieBiaoZhunInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.SalryCanShuSer.addPaiQianDiJinTieBiaoZhunInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0802");
				} else if(resultNum == 10){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_fr_null", request));}
//				 else if(resultNum == 333){
//					map.put("statusCode", "300");
//					map.put("message", "在派遣地管理中不存在此信息！请先添加。");
//				}
					else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHaoFengGuanLiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addHaoFengGuanLiInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String  JIQUN  =request.getParameter("JIQUN");
		String  HAOFENG = request.getParameter("HAOFENG");
		int resultNum =0;
		if("".equals(JIQUN)||"".equals(HAOFENG)){
			resultNum=600;
		}else{
			  resultNum = this.SalryCanShuSer.addHaoFengGuanLiInfo(request);
		}
		 
	
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0804");
				} else if(resultNum == 5){
					
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"pa.salary.canShu.GuangMingLeiLuo", request));
					
				}else if(resultNum == 6){
					
					map.put("statusCode", "300");
					map.put("message",TipMessage.getTipMessage(
							"pa.salary.canShu.GuangMingLeiLuo", request));
					
				}else if(resultNum == 10){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_fr_null", request));
				}else if(resultNum == 100){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_repart", request));
				}else if(resultNum == 600){
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail_JIQUN_HAOFENG_strMon", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 删除派遣津贴标准
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaiQianDiJinTieBiaoZhunInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.SalryCanShuSer.deletePaiQianDiJinTieBiaoZhunInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "pa0802");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 号俸设置页面删除
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHaoFengSheZhiInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHaoFengSheZhiInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.SalryCanShuSer.deleteHaoFengSheZhiInfo(request);
		
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"pa.salary.canShu.caozuo_success", request));// 操作成功
			map.put("formId", "postName");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"pa.salary.canShu.caozuo_success", request));// 操作失败
		}
		return map;
	}

	/**
	 * check当前年度未添加基准的法人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkAnnualBonusParamSetup",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAnnualBonusParamSetup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.SalryCanShuSer.checkAnnualBonusParamSetup(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", "所有法人都已设置年终奖基准。");// 删除成功
		} else if("error".equals(result)){
			map.put("statusCode", "300");
			map.put("message", "系统错误，请与管理员联系。");// 删除失败
		}else{
			map.put("statusCode", "200");
			map.put("message", "以下法人还未设置年终奖基准：" + result + "。");// 删除成功
		}
		return map;
	}
	

	/**
	 * 年终奖预提计算--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewNianZhongJiangYuTiJiSuanExcel")
	public void viewNianZhongJiangYuTiJiSuanExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//查询年终奖计提计算的数据

		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List aliasValueList = this.SalryCanShuSer.getNianZhongJiangJiTiJiSuanList(request) ;
		List aliasNameList = new ArrayList();
		//提取导出数据列表
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("法人");
		aliasNameList.add("人员类型组");
		aliasNameList.add("AU CODE");
		aliasNameList.add("部门");
		aliasNameList.add("支付月份");
		aliasNameList.add("计算基数");
		aliasNameList.add("计提比率");
		aliasNameList.add("计提金额");
		String[] columns = {"SH","XM","FR","EMPTYPE_GROUP_NAME",
				"AU_CODE","BM","ZFYF","JSJS","JTBL","JTJE"};
				
		String name = "paAnnualBonusInfo";
		this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/viewHaoFeng")
	public Map viewHaoFeng(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			List elist = SalryCanShuSer.viewHaoFeng(request);
			map.put("viewHaoFeng", elist);
			return map;
	}
}


