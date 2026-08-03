package com.ait.ar.action.attendanceMintenance;

import java.io.PrintWriter;
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

 
import com.ait.ar.service.ArDetailCalulateSer;
import com.ait.ar.service.ArMonthCalculateSer;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMonthCalculateCtroller.java
 * @Description:
 * @Create date: 2012-2-10 下午05:50:29
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArMonthCalculateCtroller {
	Logger logger = Logger.getLogger(ArMonthCalculateCtroller.class);
	
	@Autowired
	private ArMonthCalculateSer arMonthCalculateSer;
	@Autowired
	private PaCalculateSer paCalculateSer;
	
	@Autowired
	private ArDetailCalulateSer arDetailCalulateSer;
	
	/**
	 * 汇总计算页面(view ArMonth Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonthCalculate",method = RequestMethod.GET)
	public ModelAndView viewArMonthCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		
		List deptList = this.arMonthCalculateSer.getDeptAreaList(request) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		
		modelMap.put("statnoList", statnoList) ;
		modelMap.put("deptList", deptList) ;
	    return new ModelAndView("/ar/attendanceMintenance/viewArMonthCalculate",modelMap);
	}
	
	/**
	 * 汇总计算页面的 申请确认按钮 (view ArMonth Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonthKaoqinConfirm",method = RequestMethod.GET)
	public ModelAndView viewArMonthKaoqinConfirm(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		
		modelMap.put("statnoList", statnoList) ;
		modelMap.put("deptList", deptList) ;
	    return new ModelAndView("/ar/attendanceMintenance/viewArMonthKaoqinConfirm",modelMap);
	}
	
	/**
	 * 汇总计算(detail Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthCalculate")
	@ResponseBody
	public void detailCalculate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = this.arMonthCalculateSer.monthCalculate(request) ;
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	/**
	 * 考勤确认页面  (detail Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthCalculateConfirm")
	@ResponseBody
	public void monthCalculateConfirm(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = this.arMonthCalculateSer.monthCalculateConfirm(request) ;
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	/**
	 * 汇总计算页面  申请确认按钮功能 (detail Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthCalculateConfirmApply")
	@ResponseBody
	public void monthCalculateConfirmApply(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = this.arMonthCalculateSer.monthCalculateConfirmApply(request) ;
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	
	/**
	 * 考勤申请 关闭 (applyClose_Guan)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyCloseGuan")
	public ModelAndView viewApplyCloseGuan(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List EssCntlist =  this.arMonthCalculateSer.arEssNOApplyCount(request);
        LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
        String STAT_NO = StringUtil.checkNull(paramMap.get("STAT_NO"));
        String arMonth = StringUtil.checkNull(paramMap.get("arMonth"));
        String AR_DEPT_NO= StringUtil.checkNull(paramMap.get("AR_DEPT_NO"));
		String flag = paramMap.get("FLAG").toString();
		modelMap.put("STAT_NO", STAT_NO);
		modelMap.put("arMonth", arMonth);
		modelMap.put("AR_DEPT_NO", AR_DEPT_NO);
		modelMap.put("FLAG", flag);
		
		modelMap.put("EssCntlist", EssCntlist);
		return new ModelAndView("/ar/attendanceMintenance/viewApplyCloseGuan", modelMap);
	}
	
	
	/**
	 * 考勤申请 关闭 (applyClose_Guan)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arOFF")
	@ResponseBody
	public Map arOFF(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			String flag = StringUtil.checkNull(paramMap.get("FLAG"));
			 if(flag.equals("2")){
			 	int result = this.arMonthCalculateSer.arOFF(request);
			 
					if (result == 1) {
						String returnString  = this.arMonthCalculateSer.arapplyCloseGuanstr(request);
						this.arDetailCalulateSer.detailShiHouCalculate(request);
						map.put("message", "否决成功成功!"+returnString);//否决成功成功!
						map.put("statusCode", "200");
						//map.put("callbackType", "closeCurrent");
					}else{
						map.put("message", "操作出错,请重新申请!");//刷卡申请保存出错,请重新申请!
						map.put("statusCode", "300");
					}
			 }else{
				    map.put("message", "邮件发送成功成功!");//否决成功成功!
					map.put("statusCode", "200");
					//map.put("callbackType", "closeCurrent");
			 }
		}  catch (Exception e) {
			e.printStackTrace();
			map.put("message", "操作出错,请重新申请!");//刷卡申请保存出错,请重新申请!
			map.put("statusCode", "300");
		}
		return map;
	}
	
	
	
	/**
	 * 考勤申请 关闭 (applyClose_Guan)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/applyCloseGuanOpen")
	@ResponseBody
	public void applyCloseGuanOpen(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
        response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
        String returnString  = "";
		String flag = paramMap.get("FLAG").toString();
		
		
		if(flag.equals("1"))
			returnString  = this.arMonthCalculateSer.arapplyCloseGuanstr(request);
		else
			returnString = this.arMonthCalculateSer.arapplyCloseOpenstr(request);
		
		
		 PrintWriter out = response.getWriter(); 
		 out.println(JsonUtil.writeInternal(returnString));
	        
		 out.flush();
		 out.close();
		
	}
	
	
}
