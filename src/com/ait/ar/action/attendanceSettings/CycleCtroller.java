package com.ait.ar.action.attendanceSettings;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleCtroller.java
 * @Description:
 * @Create date: 2012-1-6 下午02:17:58
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class CycleCtroller {
	Logger logger = Logger.getLogger(CycleCtroller.class);
	
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AuthorityUtil authorityUtil;

	/**
	 * 显示区间库页面(show cycleList view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCycle")
	public ModelAndView viewCycleList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List cycleList = this.cycleSer.getCycleList(request) ;
		int cycleCnt = this.cycleSer.getCycleCnt(request) ;
		
		modelMap.put("cycleList", cycleList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, cycleCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2348")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewCycle",modelMap);
	}
	
	/**
	 * 进入添加画面(show addCycle view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCycleView",method = RequestMethod.GET)
	public ModelAndView addCycleView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/ar/attendanceSettings/addCycleView",modelMap);
	}
	
	/**
	 * 保存区间信息(add Cycle Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCycleInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addCycleInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.cycleSer.addCycleInfo(request);
	
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
//			map.put("message", "保存成功");
			map.put("navTabId", "ar0301");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
//			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 进入修改页面(update Cycle view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCycleView",method = RequestMethod.GET)
	public ModelAndView updateCycleView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("cycleInfo", cycleSer.getCycle(request)) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateCycleView",modelMap);
	}
	
	/**
	 * 修改区间信息(update Cycle Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCycleInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateCycleInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.cycleSer.updateCycleInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0301");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 删除区间信息(delete Cycle Info)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCycleInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCycleInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int isDelete = this.cycleSer.checkCycleForDelete(request);
		if(isDelete == 0){
			int result = this.cycleSer.deleteCycleInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "ar0301");
			}else{	
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
			}	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_info_use",request));//该信息使用中,不能删除
		}
		return map;
	}
	
	/**
	 * 区间参数 查询分页 页面(show CycleParameter view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewCycleParameter")
	public ModelAndView viewCycleParameterList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List cycleParamList = this.cycleSer.getCycleParameterList(request) ;
		int cycleParamCnt = this.cycleSer.getCycleParameterCnt(request) ;
		
		modelMap.put("paramList", cycleParamList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, cycleParamCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2563")) ;
		
//		List cpnyList = this.companySer.getCompanyItemList(request);
//		List cpnyList = this.companySer.getCompanyItemAllList(request);
		
//		modelMap.put("cpnyList", cpnyList) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewCycleParameter",modelMap);
	}
	
	/**
	 * 进入区间参数保存信息(add CycleParam View)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCycleParamView")
	public ModelAndView addCycleParamView(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		//员工类别List
		//List empTypeCodeList = this.cycleSer.getEmpTypeCodeList(request) ;
		
		modelMap.put("defaultCpnyID", admin.getCpnyId()) ;
		modelMap.put("cycleList", cycleSer.getCycleList(request));
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		//modelMap.put("empTypeCodeList", empTypeCodeList);
		return new ModelAndView("/ar/attendanceSettings/addCycleParamView",modelMap);
	}
	
	/**
	 * 保存区间参数信息(add Cycle ParamInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCycleParamInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addCycleParamInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int unique = this.cycleSer.checkCycleInfoUnique(request);
		
		if(unique == 0){
			int result = this.cycleSer.addCycleParamInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
				map.put("navTabId", "ar0308");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_exits",request));//该信息已经存在,不能重复添加
		}
		
		
		return map;
	}
	
	/**
	 * 进入修改页面(update CycleParam View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCycleParamView",method = RequestMethod.GET)
	public ModelAndView updateCycleParamView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//员工类别List
		//List empTypeCodeList = this.cycleSer.getEmpTypeCodeList(request) ;
		//已关联类别
		//List statisticList = this.cycleSer.getStatisticList(request) ; 
		
		modelMap.put("cycleParamInfo", cycleSer.getCycleParam(request)) ;
		modelMap.put("cycleList", cycleSer.getCycleList(request));
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		//modelMap.put("empTypeCodeList", empTypeCodeList);
		//modelMap.put("statisticList", statisticList);
		return new ModelAndView("/ar/attendanceSettings/updateCycleParamView",modelMap);
	}
	
	/**
	 * 修改区间信息(update CycleParam Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCycleParamInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateCycleParamInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int unique = this.cycleSer.checkCycleInfoUnique(request);
		
		if(unique == 0){
			int result = this.cycleSer.updateCycleParamInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0308");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_conflict",request));//所修改信息与之前已存在信息冲突,要求不能重复！
		}
		
		return map;
	}
	
	/**
	 * 删除区间信息(delete CycleParam Info)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCycleParamInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteCycleParamInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.cycleSer.deleteCycleParamInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0308");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		return map;
	}
	/**
	 * 进入考勤系统导航页面(show Attendance system Navigation page)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArNavigationPage")
	public ModelAndView viewArNavigationPage(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		/*List cycleList = this.cycleSer.getCycleList(request) ;
		int cycleCnt = this.cycleSer.getCycleCnt(request) ;
		
		modelMap.put("cycleList", cycleList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, cycleCnt) */;
		//2018/11 Start 新增考勤申请查看 页面
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int isSuperUser = authorityUtil.isSuperUser(admin.getPersonId());
		int isSuperHrUser = authorityUtil.isSuperHrUser(admin.getPersonId());
		int isArUser = authorityUtil.isHrArUser(admin.getPersonId());
		int isGaViewUser = authorityUtil.isGaViewUser(admin.getPersonId());
		modelMap.put("isSuperUser", isSuperUser);
		modelMap.put("isSuperHrUser", isSuperHrUser);
		modelMap.put("isArUser", isArUser);
		modelMap.put("isGaViewUser", isGaViewUser);
		//2018/11 End 新增考勤申请查看 页面
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2348")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewArNavigationPage",modelMap);
	}
	
	/**
	 * 显示法定节假日页面(show StatutoryHolidays view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewStatutoryHolidays")
	public ModelAndView viewStatutoryHolidaysList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List statutoryHolidaysList = this.cycleSer.getStatutoryHolidaysList(request) ;
		modelMap.put("statutoryHolidaysList", statutoryHolidaysList) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "14013732")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewStatutoryHolidays",modelMap);
	}
}
