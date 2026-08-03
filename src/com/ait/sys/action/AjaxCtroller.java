package com.ait.sys.action;

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

import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AjaxSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AjaxCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @author : hanzhe(hanzhe@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/sys/ajax")
public class AjaxCtroller {
Logger logger = Logger.getLogger(AjaxCtroller.class);
	
	@Autowired
	private AjaxSer ajaxSer;
	
	/**
	 * 查询所有公司信息(query all the company info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCompanyInfoList")
	@ResponseBody
	public List getCompanyInfoList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List companyList = this.ajaxSer.getCompanyInfoList(request);
		if(null!=request.getParameter("isSelect") && "1".equals(request.getParameter("isSelect"))){ 
			LinkedHashMap map=new LinkedHashMap();
			map.put("CPNY_ID","");
			map.put("COMPANY_NAME",TipMessage.getTipMessage("pa.insurance.title.pleaseChoose",request));//请选择
			companyList.add(0, map);
		}
		return companyList;
	} 
	
	/**
	 * 根据法人Cpny_id查询工资月份信息（query the pa_month info list by cpny_id）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPaMonthList")
	@ResponseBody
	public List getPaMonthList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List getPaMonthList = ajaxSer.getPaMonthList(request);
		 if(null!=request.getParameter("isSelect") && "1".equals(request.getParameter("isSelect"))){ 
			 LinkedHashMap map=new LinkedHashMap();
			  map.put("PA_MONTH",TipMessage.getTipMessage("pa.insurance.title.pleaseChoose",request));//请选择
			  getPaMonthList.add(0, map);
			}
		return getPaMonthList;
	} 
	
	/**
	 * 根据法人Cpny_id、工资月份查询薪资发放日期（query the GIVE_DATE info list by cpny_id 、pa_month）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPaGiveDateList")
	@ResponseBody
	public List getPaGiveDateList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List getPaGiveDateList = ajaxSer.getPaGiveDateList(request);
		 if(null!=request.getParameter("isSelect") && "1".equals(request.getParameter("isSelect"))){ 
			 LinkedHashMap map=new LinkedHashMap();
			  map.put("GIVE_DATE",TipMessage.getTipMessage("pa.insurance.title.pleaseChoose",request));//请选择
			  getPaGiveDateList.add(0, map);
			}
		return getPaGiveDateList;
	} 
	
	/**
	 * 根据法人Cpny_id查询工资项目信息（query the pa item info list by cpny_id）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPaItemInfoList")
	@ResponseBody
	public List getPaItemInfoList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		List getPaItemInfoList = ajaxSer.getPaItemInfoList(request);
		 if(null!=request.getParameter("isSelect") && "1".equals(request.getParameter("isSelect"))){ 
			 LinkedHashMap map=new LinkedHashMap();
			  map.put("ITEM_NO","");
			  map.put("ITEM_NAME",TipMessage.getTipMessage("pa.insurance.title.pleaseChoose",request));//请选择
			  getPaItemInfoList.add(0, map);
			}
		return getPaItemInfoList;
	} 
	
	/**
	 * 根据公司ID查询该公司的部门树(query the deptTree by the company id)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOrgInfoTreeDate")
	@ResponseBody
	public List getOrgInfoTreeDate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{  
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//部门菜单
		List deptInfoTreeList = this.ajaxSer.getDeptInfoTree(paramMap);
		
		return deptInfoTreeList ;
	}
}
