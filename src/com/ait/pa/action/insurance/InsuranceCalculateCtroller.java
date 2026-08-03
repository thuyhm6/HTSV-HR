package com.ait.pa.action.insurance;

import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import com.ait.pa.service.insurance.InsuranceCalculateSer;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceCalculateCtroller.java
 * @Description:
 * @Create date: 2012-2-17 下午02:55:16
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/insurance")
public class InsuranceCalculateCtroller {
	Logger logger = Logger.getLogger(InsuranceCalculateCtroller.class);
	
	@Autowired
	private InsuranceCalculateSer insuranceCalculateSer ;
	
	@Autowired
	private PaCalculateSer paCalculateSer;
	/**
	 * 跳转到保险计算页面（view Insurance Calculate）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceCalculate")
	public ModelAndView viewInsuranceCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
//		List paSupervisorList  = this.paCalculateSer.getPaSupervisorList(request);
//		HttpSession session = request.getSession() ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String supervisorId = admin.getPersonId();
//		modelMap.put("supervisorlist", paSupervisorList) ;
//		modelMap.put("supervisorId", supervisorId) ;
		

		List statList = this.paCalculateSer.getPaStatisticList(request);
		modelMap.put("statList", statList);
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		
		return new ModelAndView("/pa/insurance/viewInsuranceCalculate",modelMap);
	}
	
	/**
	 * 保险计算（insurance Calculate）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insuranceCalculate")
	@ResponseBody
	public void insuranceCalculate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
		String returnString = this.insuranceCalculateSer.insuranceCalculate(request) ;
		
		PrintWriter out = response.getWriter();
	        
	    out.println(JsonUtil.writeInternal(returnString));
	        
		out.flush();
		out.close();	
	}
	/**
	 * 新保险计算（insurance Calculate）2013-09-13
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insuranceCalculateNew")
	@ResponseBody
	public void insuranceCalculateNew(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
		response.setHeader("Cache-Control", "no-cache");
		
		String returnString = this.insuranceCalculateSer.insuranceCalculateNew(request) ;
		
		PrintWriter out = response.getWriter();
		
		out.println(JsonUtil.writeInternal(returnString));
		
		out.flush();
		out.close();	
	}
	
	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-18 下午6:56:53 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryProvideDate")
	@ResponseBody
	public Map getSalaryProvideDate (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getSalaryProvideDateList=this.insuranceCalculateSer.getSalaryProvideDate(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			if(getSalaryProvideDateList.size()==0){
				map.put("","当前月没有符合发放日期");
			}else{
				for(int i=0;i<getSalaryProvideDateList.size();i++){
					map.put((String)((Map) getSalaryProvideDateList.get(i)).get("GIVE_DATE"), ((Map) getSalaryProvideDateList.get(i)).get("GIVE_DATE"));
				}
			}
			return map;
		
	}
	
	/**
	 * 保险关闭和解除
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/isApplyClosed")
	@ResponseBody
	public void isApplyClosed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		
		String result  = this.paCalculateSer.isapplyCloseOpenstr(request);
		
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(result));

		out.flush();
		out.close();
	}
}
