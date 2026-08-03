package com.ait.is.action.insurancesystem;

import java.sql.SQLException;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ait.ess.service.InfoApplySer;
import com.ait.is.service.StopInsureSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 停保管理 (包括对象增加,减少 ,管理)
 * 
 * @ClassName:StopInstanceMangementCtroller
 * @Description: TODO
 * @author bai chenfeifei
 * 
 */

@Controller
@RequestMapping(value="/is/insurancesystem")
public class StopInstanceMangementCtroller {
	
	Logger logger = Logger.getLogger(StopInstanceMangementCtroller.class);
    @Autowired
	private  StopInsureSer stopInstanceSer;
    @Autowired
	private  ExcelUtilSer excelUtilSer;
    @Autowired
    private ToolMenuSer toolMenuSer;
    /**
     * 跳转到停保管理信息显示页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/viewStopInsure")
	public ModelAndView viewStopInsureList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
    	List stopInstanceList = this.stopInstanceSer.getStopInsureList(request);
		int stopInstanceCnt = this.stopInstanceSer.getStopInsureCnt(request);
		modelMap.put("stopList", stopInstanceList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, stopInstanceCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124905")) ;
		return new ModelAndView("/is/insurancesystem/ViewStopInsure",modelMap);
	}
    
    @RequestMapping(value="/deleteStopInsureInfo")
    @ResponseBody
	public Map<String, Object> deletestopInsureInfo(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    	Map<String, Object> map = new HashMap<String, Object>();
		int result = this.stopInstanceSer.deleteStopInsureInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "bx0104");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
	return map;
	}
    /**
	 * 进入修改页面
	 */
	@RequestMapping(value = "/updateStopInsuranceNum")
	public ModelAndView updateUserView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String str = request.getParameter("ids");
		String[] ids = str.split(",");
		for(String id:ids){
			this.stopInstanceSer.allowPaBenStopInsureUpdateBz(id);
		}
		@SuppressWarnings("rawtypes")
		List list = stopInstanceSer.getAllowPaBenStopInsureUpdateBz(request);//预修改的信息集合
		modelMap.put("allowUpdate", list);
		return new ModelAndView("/is/insurancesystem/updateStopInsuranceNum",modelMap);
	}
	  /**
		 * 恢复修改状态
	 * @throws Exception 
		 */
		@RequestMapping(value = "/editStopInsurance_a")
		@ResponseBody
		public Map callbackstopInsureState(HttpServletRequest request)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			stopInstanceSer.backPaBenStopInsureUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
			map.put("navTabId", "bx0103");
			return map;
		}
		
		 /**
		 * 修改提交上来的信息
	 * @throws Exception 
		 */
		@RequestMapping(value = "/saveUpdStopInsurance")
		@ResponseBody
		public Map saveUpdstopInsurance(HttpServletRequest request)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = stopInstanceSer.updatePaBenManageAddInfoBz(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "bx0104");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
			return map;
		}
		/**
		 * 列表的导出
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/insStopNumListExcel")
		public ModelAndView insBaseNumListExcel(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			
			List insBaseNumInfoList = this.stopInstanceSer.getInsStopNumInfoExcel(request) ;
			modelMap.put("itemList", insBaseNumInfoList);
			
			return new ModelAndView("/is/insurancesystem/viewNOInsStopNumInfoExcel",modelMap);
		}
		
		/**
		 * 保险地区参数维护页面（添加、修改、导入）
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewInsuranceParamDataChList")
		public ModelAndView viewInsuranceParamDataChList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List isAreaList = this.stopInstanceSer.getInsuranceAreaListByCpnyId(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			String firstFlag = request.getParameter("firstFlag");
			if(firstFlag != null){
				List isParamDataList = this.stopInstanceSer.getInsuranceParamDataChList(request) ;
				int isParamDataCnt = this.stopInstanceSer.getInsuranceParamDataChCnt(request);
				modelMap.put("isParamDataList", isParamDataList);
				modelMap.put(UiUtil.TOTAL_COUNT_NAME, isParamDataCnt);
			}
			modelMap.put("COMPANY_NAME", admin.getCpnyName());
			modelMap.put("defaultCpny", cpnyId);
			modelMap.put("isAreaList", isAreaList);
			modelMap.put("toolbarInfo",
					request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "589"));
			
			return new ModelAndView("/is/insurancesystem/viewInsuranceParamDataChList",modelMap);
		}
		
		/**
		 * 保险地区参数维护页面（添加、修改、导入）---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewInsuranceParamDataNChList")
		public ModelAndView viewInsuranceParamDataNChList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			
			List isParamDataList = this.stopInstanceSer.getInsuranceParamDataChList(request) ;
			int isParamDataCnt = this.stopInstanceSer.getInsuranceParamDataChCnt(request);
			modelMap.put("COMPANY_NAME", admin.getCpnyName());
			modelMap.put("defaultCpny", cpnyId);
			modelMap.put("isParamDataList", isParamDataList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, isParamDataCnt);
			modelMap.put("toolbarInfo",
					request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "590"));
			
			return new ModelAndView("/is/insurancesystem/viewInsuranceParamDataNChList",modelMap);
		}
		
		/**
		 * 导出保险参数数据
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewInsuranceParamDataChExcel")
		public ModelAndView viewInsuranceParamDataChExcel(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List isParamDataList = this.stopInstanceSer.getInsuranceParamDataChList(request) ;

			modelMap.put("isParamDataList", isParamDataList);
			return new ModelAndView("/is/insurancesystem/viewInsuranceParamDataChExcel", modelMap);
		}
		
		/**
		 * 导出保险参数数据---没有大区编码的法人
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewInsuranceParamDataNChExcel")
		public ModelAndView viewInsuranceParamDataNChExcel(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List isParamDataList = this.stopInstanceSer.getInsuranceParamDataChList(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("defaultCpny",admin.getCpnyId());
			modelMap.put("isParamDataList", isParamDataList);
			return new ModelAndView("/is/insurancesystem/viewInsuranceParamDataNChExcel", modelMap);
		}
		
		/**
		 * 跳转到保险参数添加页面(add insurance param date )
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addIsParamDataInfo")
		public ModelAndView addIsParamDataInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
			List isAreaList = this.stopInstanceSer.getInsuranceAreaListByCpnyId(request) ;
			modelMap.put("isAreaList", isAreaList);
			
			return new ModelAndView("/is/insurancesystem/addIsParamDataInfo", modelMap);
		}
		
		/**
		 * 根据福利地区编号/名称查询福利地区信息(get the insrarea info by no or name)
		 * @param request
		 * @return Map
		 * @throws Exception 
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/getInsrareaInfoListByKey")
		@ResponseBody
		public Map getInsAreaInfoListByKey(HttpServletRequest request, HttpServletResponse response) throws Exception  {
			Map<String, Object> map = new HashMap<String, Object>();
		 	List insAreaList=this.stopInstanceSer.getInsAreaInfoListByKey(request);
			if (insAreaList != null && insAreaList.size() > 0) {
				map.put("insAreaList", insAreaList);
			}  
			return map;
		}
		
		/**
		 * 根据福利项目编号/名称查询福利项目信息(get the insure info by no or name)
		 * @param request
		 * @return Map
		 * @throws Exception 
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/getInsureInfoListByKey")
		@ResponseBody
		public Map getInsureInfoListByKey(HttpServletRequest request, HttpServletResponse response) throws Exception  {
			Map<String, Object> map = new HashMap<String, Object>();
		 	List insureList=this.stopInstanceSer.getInsureInfoListByKey(request);
			if (insureList != null && insureList.size() > 0) {
				map.put("insureList", insureList);
			}  
			return map;
		}
		
		/**
		 * 跳转到保险参数添加页面(add insurance param date )---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addIsParamDataNoPayAreaInfo")
		public ModelAndView addIsParamDataNoPayAreaInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
			List isAreaList = this.stopInstanceSer.getInsuranceAreaListByCpnyId(request) ;
			modelMap.put("isAreaList", isAreaList);
			
			return new ModelAndView("/is/insurancesystem/addIsParamDataNoPayAreaInfo", modelMap);
		}
		
		/**
		 * 添加保险参数 (add insurance param data)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addInsuranceParamData")
		@ResponseBody
		public Map addInsuranceParamData(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				int result = stopInstanceSer.addInsuranceParamData(request);
				if (result == 1) {
					map.put("navTabId", "bx0121");
					map.put("message", "保险参数添加成功!");//保险参数添加成功!
					map.put("statusCode", "200");
					map.put("callbackType", "closeCurrent");
				}
			}catch (Exception e) {
				map.put("message", "保险参数添加失败!");//保险参数添加失败!
				map.put("statusCode", "300");
			}
			return map;
		}
		
		/**
		 * 添加保险参数 (add insurance param data)---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addInsuranceParamDataNoPayArea")
		@ResponseBody
		public Map addInsuranceParamDataNoPayArea(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				int result = stopInstanceSer.addInsuranceParamData(request);
				if (result == 1) {
					map.put("navTabId", "bx0122");
					map.put("message", "保险参数添加成功!");//保险参数添加成功!
					map.put("statusCode", "200");
					map.put("callbackType", "closeCurrent");
				}
			}catch (Exception e) {
				map.put("message", "保险参数添加失败!");//保险参数添加失败!
				map.put("statusCode", "300");
			}
			return map;
		}
		
		/**
		 * 跳转保险参数修改页面(view insurance param data for update)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewEditIsParamDataInfo")
		public ModelAndView viewEditIsParamDataInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
			
			List isParamDataList = this.stopInstanceSer.getInsuranceParamDataChList(request) ;
			modelMap.put("isParamMap", isParamDataList.get(0));
			
			return new ModelAndView("/is/insurancesystem/viewEditIsParamDataInfo", modelMap);
		}
		
		/**
		 * 跳转保险参数修改页面(view insurance param data for update)---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewEditIsParamDataNoPayAreaInfo")
		public ModelAndView viewEditIsParamDataNoPayAreaInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
			
			List isParamDataList = this.stopInstanceSer.getInsuranceParamDataChList(request) ;
			modelMap.put("isParamMap", isParamDataList.get(0));
			
			return new ModelAndView("/is/insurancesystem/viewEditIsParamDataNoPayAreaInfo", modelMap);
		}
		
		/**
		 * 修改-保险参数设置 (update insurance param data)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/updateInsuranceParamData")
		@ResponseBody
		public Map updateInsuranceParamData(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				int result = stopInstanceSer.updateInsuranceParamData(request);
				if (result == 1) {
					map.put("message", "修改保险参数成功!");//修改保险参数成功!
					map.put("statusCode", "200");
					map.put("navTabId", "bx0121");
					map.put("callbackType", "closeCurrent");
					//modelMap.put("forwardUrl","/ess/affirmApply/viewEditPOtApplyList");
				}
			}catch (Exception e) {
				map.put("message", "修改保险参数失败!");//修改保险参数失败!
				map.put("statusCode", "300");
			}
			return map;
		}
		
		/**
		 * 修改-保险参数设置 (update insurance param data)---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/updateInsuranceParamDataNoPayArea")
		@ResponseBody
		public Map updateInsuranceParamDataNoPayArea(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				int result = stopInstanceSer.updateInsuranceParamData(request);
				if (result == 1) {
					map.put("message", "修改保险参数成功!");//修改保险参数成功!
					map.put("statusCode", "200");
					map.put("navTabId", "bx0122");
					map.put("callbackType", "closeCurrent");
				}
			}catch (Exception e) {
				map.put("message", "修改保险参数失败!");//修改保险参数失败!
				map.put("statusCode", "300");
			}
			return map;
		}
		
		/**
		 * 保险参数模板下载
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/exportIsParamDataModule")
		public void exportIsParamDataModule(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String name = this.getTemplateInfo(request, aliasNameList, list , mapList, mapNameList);
			LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheetWithNo(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
			
		}
		
		/**
		 * 保险参数模板下载---没有大区编码的法人
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/exportIsParamDataNoPayAreaModule")
		public void exportIsParamDataNoPayAreaModule(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String name = this.getTemplateInfo(request, aliasNameList, list , mapList, mapNameList);
			LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheetWithNo(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
			
		}
		
		/**
		 * 组装保险参数模版信息
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		@SuppressWarnings("unchecked")
		public String getTemplateInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			//模版类型AREA_FLAG:有大区编码的法人 ；NO_AREA_FLAG： 没有大区编码的法人；
			String type = request.getParameter("type")!=null?request.getParameter("type"):"AREA_FLAG";
			//模版名称
			String name = "";
			if("AREA_FLAG".equals(type)){
				aliasNameList.add("大区编码*");
				aliasNameList.add("福利地区*");
				aliasNameList.add("福利项目*");
				aliasNameList.add("地区比率(数值)*");
				aliasNameList.add("地区金额(数值)*");
				if(admin.getCpnyId().equals("LGEQA")){
					aliasNameList.add("四舍五入方式（A表示向上进1位，B表示四舍五入留两位小数，C四舍五入取整，D向下取整，E四舍五入留一位小数）");
				}
				aliasNameList.add("备注");

				
	

				LinkedHashMap map = new LinkedHashMap();
				map.put("CELL0", "BJO");
				map.put("CELL1", "北京");
				map.put("CELL2", "医疗保险(个人)");
				map.put("CELL3", "0.02");
				map.put("CELL4", "1200");
				
				if(admin.getCpnyId().equals("LGEQA")){
					map.put("CELL5","A");
					map.put("CELL6", "文本");
				}else{
					map.put("CELL5", "文本");
				}
				list.add(map);
				
				LinkedHashMap map1 = new LinkedHashMap();
				map1.put("CELL0", "SHO");
				map1.put("CELL1", "上海");
				map1.put("CELL2", "医疗保险(公司)");
				map1.put("CELL3", "0.08");
				map1.put("CELL4", "1000");
				if(admin.getCpnyId().equals("LGEQA")){
					map.put("CELL5","B");
					map.put("CELL6", "文本");
				}else{
					map.put("CELL5", "文本");
				}
				list.add(map1);
				
				mapNameList.add("大区编码参考");
				mapNameList.add("福利地区参考");
				mapNameList.add("福利项目参考");
				
				mapList.add(" SELECT PAY_AREA_CD CODE_NO, HDN.CONTENT CODE_NAME FROM HR_DEPARTMENT HD, HR_DEPARTMENT_NAME HDN WHERE HD.DEPTNO = HDN.DEPTNO "
							+ " AND HDN.LANGUAGE = 'zh' AND PAY_AREA_CD IS NOT NULL AND CPNY_ID = '" + admin.getCpnyId() + "' " + " ORDER BY HDN.CONTENT ");
				mapList.add(" SELECT SP.CODE_NO CODE_NO, S1.CONTENT CODE_NAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME S1 WHERE T.PARENT_CODE_NO = '216736' "
						+ " AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' AND T.CODE_NO = S1.NO(+) AND S1.LANGUAGE(+) = 'zh' "
						+ " AND T.ACTIVITY = 1 ORDER BY S1.CONTENT ");
				mapList.add(" SELECT SP.CODE_NO CODE_NO, S1.CONTENT CODE_NAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME S1 WHERE T.PARENT_CODE_NO = '219677' "
						+ " AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' AND T.CODE_NO = S1.NO(+) AND S1.LANGUAGE(+) = 'zh' "
						+ " AND T.ACTIVITY = 1 ORDER BY S1.CONTENT ");
				
				name = "tempIsParamData";
			}else if("NO_AREA_FLAG".equals(type)){
				aliasNameList.add("福利地区*");
				aliasNameList.add("福利项目*");
				aliasNameList.add("地区比率(数值)*");
				aliasNameList.add("地区金额(数值)*");
				if(admin.getCpnyId().equals("LGEQA")){
					aliasNameList.add("四舍五入方式(A表示向上进1位，B表示四舍五入留两位小数，C四舍五入取整，D向下取整，E四舍五入留一位小数)");
				}
				aliasNameList.add("备注");
				
				LinkedHashMap map = new LinkedHashMap();
				map.put("CELL0", "北京");
				map.put("CELL1", "医疗保险(个人)");
				map.put("CELL2", "0.02");
				map.put("CELL3", "1200");
				if(admin.getCpnyId().equals("LGEQA")){
					map.put("CELL4","A");
					map.put("CELL5", "文本");
				}else{
					map.put("CELL4", "文本");
				}
				list.add(map);
				
				LinkedHashMap map1 = new LinkedHashMap();
				map1.put("CELL0", "上海");
				map1.put("CELL1", "医疗保险(公司)");
				map1.put("CELL2", "0.08");
				map1.put("CELL3", "1000");
				if(admin.getCpnyId().equals("LGEQA")){
					map.put("CELL4","B");
					map.put("CELL5", "文本");
				}else{
					map.put("CELL4", "文本");
				}
				list.add(map1);
				
				mapNameList.add("福利地区参考");
				mapNameList.add("福利项目参考");
				mapList.add(" SELECT SP.CODE_NO CODE_NO, S1.CONTENT CODE_NAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME S1 WHERE T.PARENT_CODE_NO = '216736' "
						+ " AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' AND T.CODE_NO = S1.NO(+) AND S1.LANGUAGE(+) = 'zh' "
						+ " AND T.ACTIVITY = 1 ORDER BY S1.CONTENT ");
				mapList.add(" SELECT SP.CODE_NO CODE_NO, S1.CONTENT CODE_NAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME S1 WHERE T.PARENT_CODE_NO = '219677' "
						+ " AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' AND T.CODE_NO = S1.NO(+) AND S1.LANGUAGE(+) = 'zh' "
						+ " AND T.ACTIVITY = 1 ORDER BY S1.CONTENT ");
			
				name = "tempIsParamData";
			}
			return name;
		}
		
		/**
		 * 显示导入的保险参数数据(view import insurance param data info)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewInsuranceImportDataChList")
		public ModelAndView viewInsuranceImportDataChList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List isParamImportList = this.stopInstanceSer.getIsParamImportInfoList(request);
			int isParamImportListCnt = this.stopInstanceSer.getIsParamImportInfoListCnt(request,"NORMAL");
			int errorCnt = this.stopInstanceSer.getIsParamImportInfoListCnt(request , "ERROR");
			
			modelMap.put("errCnt", errorCnt);
			modelMap.put("totalCnt", isParamImportListCnt);
			modelMap.put("isParamImportList", isParamImportList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, isParamImportListCnt);
			return new ModelAndView("/is/insurancesystem/viewInsuranceImportDataChList",modelMap);
		}
		
		/**
		 * 显示导入的保险参数数据(view import insurance param data info)---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewInsuranceImportDataNChList")
		public ModelAndView viewInsuranceImportDataNChList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List isParamImportList = this.stopInstanceSer.getIsParamImportInfoList(request);
			int isParamImportListCnt = this.stopInstanceSer.getIsParamImportInfoListCnt(request,"NORMAL");
			int errorCnt = this.stopInstanceSer.getIsParamImportInfoListCnt(request , "ERROR");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("errCnt", errorCnt);
			modelMap.put("totalCnt", isParamImportListCnt);
			modelMap.put("isParamImportList", isParamImportList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, isParamImportListCnt);
			modelMap.put("defaultCpny", cpnyId);
			return new ModelAndView("/is/insurancesystem/viewInsuranceImportDataNChList",modelMap);
		}
		
		/**
		 * 单条删除导入的保险参数数据( delete insurance param data apply)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/delIsParamDataImport")
		@ResponseBody
		public String delIsParamDataImport(HttpServletRequest request) throws Exception {
			String result = "";
			boolean bol = stopInstanceSer.delIsParamDataImport(request);
			if (bol) {
				result = "Y";
			} else {
				result = "N";
			}
			return result;
		}
		
		/**
		 * 保存导入的保险参数数据 (import insurance param data)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/saveIsParamDataImport")
		@ResponseBody
		public Map saveIsParamDataImport(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = 0;
			try {
				result = stopInstanceSer.addImportIsParamData(request);
				if (result == 1) {
					map.put("navTabId", "bx0121");
					map.put("message", "保存导入保险参数数据成功!");//保存导入保险参数数据成功!
					map.put("statusCode", "200");
					map.put("callbackType", "closeCurrent");
					//map.put("forwardUrl","/is/insurancesystem/viewInsuranceImportDataChList");
				}else{
					map.put("message", "导入的保险参数数据有错误,请修改!");//导入的保险参数数据有错误,请修改!
					map.put("statusCode", "200");
				}
			} catch (CommonException e) {
				map.put("message", e.getMessage());
				map.put("statusCode", "300");
			} catch (Exception e) {
				map.put("message", "保存保险参数数据失败,请重试!");//导入保险参数数据失败,请重试!
				map.put("statusCode", "300");
			}
			map.put("result", result);
			return map;
		}
		
		/**
		 * 保存导入的保险参数数据 (import insurance param data)---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/saveIsParamDataNchImport")
		@ResponseBody
		public Map saveIsParamDataNchImport(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = 0;
			try {
				result = stopInstanceSer.addImportIsParamDataNch(request);
				if (result == 1) {
					map.put("navTabId", "bx0122");
					map.put("message", "保存导入保险参数数据成功!");//保存导入保险参数数据成功!
					map.put("statusCode", "200");
					map.put("callbackType", "closeCurrent");
				}else{
					map.put("message", "导入的保险参数数据有错误,请修改!");//导入的保险参数数据有错误,请修改!
					map.put("statusCode", "200");
				}
			} catch (CommonException e) {
				map.put("message", e.getMessage());
				map.put("statusCode", "300");
			} catch (Exception e) {
				map.put("message", "保存保险参数数据失败,请重试!");//导入保险参数数据失败,请重试!
				map.put("statusCode", "300");
			}
			map.put("result", result);
			return map;
		}
		
		/**
		 * 取消所有导入的P加班申请( delete overtime apply import)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @author weizhengchen
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/deleteIsParamDataImport")
		@ResponseBody
		public Map deletePOtApplyImport(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			String msg= ""+this.stopInstanceSer.cancelIsParamDataImport(request);
			if("1".equals(msg)){
				map.put("statusCode", "200");
				map.put("message", "取消成功！");//取消成功
				map.put("navTabId", "bx0121");
				map.put("callbackType", "closeCurrent");
			}else{
				map.put("statusCode", "300");
				map.put("message", "取消失败！");//取消失败
			}
			return map;
		}
		
		/**
		 * 取消所有导入的P加班申请( delete overtime apply import)---没有大区编码的法人
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @author weizhengchen
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/deleteIsParamDataNchImport")
		@ResponseBody
		public Map deleteIsParamDataNchImport(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			String msg= ""+this.stopInstanceSer.cancelIsParamDataImport(request);
			if("1".equals(msg)){
				map.put("statusCode", "200");
				map.put("message", "取消成功！");//取消成功
				map.put("navTabId", "bx0122");
				map.put("callbackType", "closeCurrent");
			}else{
				map.put("statusCode", "300");
				map.put("message", "取消失败！");//取消失败
			}
			return map;
		}
		
		/**
		 * 导出保险参数临时表里的所有保险参数数据信息，进行修改，然后再导入
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewImportInsuranceParamDataChExcel")
		public void viewImportInsuranceParamDataChExcel(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List aliasNameList = new ArrayList();
			aliasNameList.add("大区编码");
			aliasNameList.add("福利地区");
			aliasNameList.add("福利项目");
			aliasNameList.add("地区比率(数值)");
			aliasNameList.add("地区金额(数值)");
			aliasNameList.add("备注");
			aliasNameList.add("正/异常");
			aliasNameList.add("错误提示");

			List dataList = new ArrayList();
			List isParamDataImportList = this.stopInstanceSer.getIsParamImportInfoList(request);
			//int isParamDataImportListCnt = this.stopInstanceSer.getIsParamImportInfoListCnt(request,"NORMAL");
			for(int i=0;i<isParamDataImportList.size();i++){
				LinkedHashMap isParamDataMap = new LinkedHashMap();
				isParamDataMap = (LinkedHashMap)isParamDataImportList.get(i);
				LinkedHashMap map = new LinkedHashMap();
				map.put("CELL0", isParamDataMap.get("PAY_AREA_CD")!=null?isParamDataMap.get("PAY_AREA_CD").toString():"");
				map.put("CELL1", isParamDataMap.get("INSRAREA_ID")!=null?isParamDataMap.get("INSRAREA_ID").toString():"");
				map.put("CELL2", isParamDataMap.get("INSURE_ID")!=null?isParamDataMap.get("INSURE_ID").toString():"");
				map.put("CELL3", isParamDataMap.get("INSURE_RATE")!=null?isParamDataMap.get("INSURE_RATE").toString():"");
				map.put("CELL4", isParamDataMap.get("INSURE_VALUE")!=null?isParamDataMap.get("INSURE_VALUE").toString():"");
				map.put("CELL5", isParamDataMap.get("REMARK")!=null?isParamDataMap.get("REMARK").toString():"");
				String checkFlag = "";
				if(isParamDataMap.get("CHECK_FLAG")!=null && "1".equals(isParamDataMap.get("CHECK_FLAG").toString())){
					checkFlag = "异常";
				}else{
					checkFlag = "正常";
				}
				map.put("CELL6", checkFlag);
				map.put("CELL7", isParamDataMap.get("CHECK_ERROR")!=null?isParamDataMap.get("CHECK_ERROR").toString():"无");
				
				dataList.add(map);
			}
			String fileName = "viewInsParamDataImport";
			LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
			this.excelUtilSer.exportIsParamDataExcel(request, response, modelMap,sqlContentmap, aliasNameList, null,fileName);
		}
		
		/**
		 * 导出保险参数临时表里的所有保险参数数据信息，进行修改，然后再导入---没有大区编码的法人
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewImportInsuranceParamDataNChExcel")
		public void viewImportInsuranceParamDataNChExcel(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List aliasNameList = new ArrayList();
			aliasNameList.add("福利地区");
			aliasNameList.add("福利项目");
			aliasNameList.add("地区比率(数值)");
			aliasNameList.add("地区金额(数值)");
			aliasNameList.add("备注");
			aliasNameList.add("正/异常");
			aliasNameList.add("错误提示");

			List dataList = new ArrayList();
			List isParamDataImportList = this.stopInstanceSer.getIsParamImportInfoList(request);
			//int isParamDataImportListCnt = this.stopInstanceSer.getIsParamImportInfoListCnt(request,"NORMAL");
			for(int i=0;i<isParamDataImportList.size();i++){
				LinkedHashMap isParamDataMap = new LinkedHashMap();
				isParamDataMap = (LinkedHashMap)isParamDataImportList.get(i);
				LinkedHashMap map = new LinkedHashMap();
				map.put("CELL0", isParamDataMap.get("INSRAREA_ID")!=null?isParamDataMap.get("INSRAREA_ID").toString():"");
				map.put("CELL1", isParamDataMap.get("INSURE_ID")!=null?isParamDataMap.get("INSURE_ID").toString():"");
				map.put("CELL2", isParamDataMap.get("INSURE_RATE")!=null?isParamDataMap.get("INSURE_RATE").toString():"");
				map.put("CELL3", isParamDataMap.get("INSURE_VALUE")!=null?isParamDataMap.get("INSURE_VALUE").toString():"");
				map.put("CELL4", isParamDataMap.get("REMARK")!=null?isParamDataMap.get("REMARK").toString():"");
				String checkFlag = "";
				if(isParamDataMap.get("CHECK_FLAG")!=null && "1".equals(isParamDataMap.get("CHECK_FLAG").toString())){
					checkFlag = "异常";
				}else{
					checkFlag = "正常";
				}
				map.put("CELL5", checkFlag);
				map.put("CELL6", isParamDataMap.get("CHECK_ERROR")!=null?isParamDataMap.get("CHECK_ERROR").toString():"无");
				
				dataList.add(map);
			}
			String fileName = "viewInsParamDataImport";
			LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
			this.excelUtilSer.exportIsParamDataExcel(request, response, modelMap,sqlContentmap, aliasNameList, null,fileName);
		}
}
