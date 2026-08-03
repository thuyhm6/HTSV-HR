package com.ait.ar.action.attendanceSettings;

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

import com.ait.ar.service.ArEatMealCountSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.CommonException;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ArEatMealCountCtroller {
	Logger logger = Logger.getLogger(ArEatMealCountCtroller.class);
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ArEatMealCountSer arEatMealCountSer;
	@Autowired
	private DynamicGroupSer dynamicGroupSer;
	
	/*------------------威海百货--食堂吃饭刷卡--信息-----------begin-----------*/
	/**
	 * 显示员工每天吃饭打卡信息(view eat count info every day)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArEatCountInfoList")
	public ModelAndView viewArEatCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		List list = this.arEatMealCountSer.getEatMealCountList(request);
		int cnt = this.arEatMealCountSer.getEatMealCountCnt(request);
		
		modelMap.put("BEGIN_DATE_STR", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("END_DATE_STR", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		modelMap.put("eatMealCountList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, cnt);
 		
		return new ModelAndView("/ar/attendanceSettings/viewArEatCountInfoList",modelMap);
	}
	
	/**
	 * 显示员工每天吃饭打卡信息(view eat count info every day)，导出用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArEatCountInfoListExcel")
	public ModelAndView viewArFingerPrintExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List eatMealCountList = this.arEatMealCountSer.getEatMealCountExcelList(request);
		modelMap.put("eatMealCountList",eatMealCountList);
		
		return new ModelAndView("/ar/attendanceSettings/viewArEatCountInfoListExcel", modelMap);
	}
	
	/**
	 * 显示批量添加员工食堂打卡信息(view batch eat meal count info)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArEatCountBatchPersonList")
	public ModelAndView viewOtBatchApplyPersonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		String dataFlag = request.getParameter("seach_DATA_FLAG");
		List list = new ArrayList();
		int cnt = 0;
		if("Y".equals(dataFlag)){
			list = arEatMealCountSer.getEatMealCountPersonList(request);
			cnt = arEatMealCountSer.getEatMealCountPersonListCnt(request);
		}
		modelMap.put("TODAY_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		
		modelMap.put("personList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, cnt);
 		
		return new ModelAndView("/ar/attendanceSettings/viewArEatCountBatchPersonList",modelMap);
	}
	
	/**
	 * 批量添加员工食堂打卡信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBatchEatCount")
	@ResponseBody
	public Map addBatchEatCount(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "0";
		try {
			String resultStr = "";
			String[] results = {""}; 
			resultStr = arEatMealCountSer.addBatchEatCount(request);
			results = resultStr.split(":");
			result = results[0];
			if("1".equals(result)) {
				map.put("navTabId", "ar0216");
				map.put("message", "批量添加员工食堂就餐信息成功!");//批量添加员工食堂就餐信息成功!
				map.put("statusCode", "200");
			}else if("-3".equals(result)){
				map.put("navTabId", "ar0216");
				map.put("message", "不允许添加今天之前的食堂刷卡信息!");//员工XXXX年XX月XX日信息锁定，不允许再添加！
				map.put("statusCode", "300");
			}else if("-12".equals(result)){
				map.put("navTabId", "ar0216");
				map.put("message", results[1]);//员工XXXX年XX月XX日信息锁定，不允许再添加！
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量添加员工食堂就餐信息失败,请重试!");//批量添加员工食堂就餐信息失败,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 删除员工食堂打卡信息
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteArEatCountInfo")
	@ResponseBody
	public Map deleteArEatCountInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = arEatMealCountSer.deleteArEatCountInfo(request);
		if(result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功!");// 删除成功
			map.put("navTabId", "ar0216");
		}else if(result == -3){
			map.put("statusCode", "300");
			map.put("message", "删除失败，不允许删除今天之前的（历史）的就餐刷卡信息!");// 删除失败
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败!");// 删除失败
		}
		return map;
	}
	
	/**
	 * 跳转到员工食堂打卡信息修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArEatCountView")
	public ModelAndView updateArEatCountView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap dataMap = new LinkedHashMap();
		List dataList = this.arEatMealCountSer.getEmpMealCountByDateList(request);
		if(dataList.size()>0){
			dataMap = (LinkedHashMap)dataList.get(0);
		}
		modelMap.put("eatCountData", dataMap);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "496"));
		return new ModelAndView("/ar/attendanceSettings/updateArEatCountView",modelMap);
	}

	/**
	 * 员工食堂打卡信息修改
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArEatCount")
	@ResponseBody
	public Map updateArEatCount(HttpServletRequest request)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = arEatMealCountSer.updateArEatCountInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "修改成功!");// 修改成功
			map.put("navTabId", "ar0216");
		}else if(result == -3){
			map.put("statusCode", "300");
			map.put("message", "修改失败，不允许修改今天之前的（历史）就餐刷卡信息!");// 修改失败
		} else {
			map.put("statusCode", "300");
			map.put("message", "修改失败!");// 修改失败
		}
		return map;
	}
	/*------------------威海百货--食堂吃饭刷卡--信息-----------end-----------*/
}
