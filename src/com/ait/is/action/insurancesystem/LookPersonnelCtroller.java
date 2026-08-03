package com.ait.is.action.insurancesystem;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import com.ait.sys.bean.AdminBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ait.is.service.PersonSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.SessionUtil;

/**
 * 个人保险查看
 * 
 * @ClassName:LookPersonnelCtroller 
 * @Description: TODO
 * @author bai chenfeifei
 * 
 */

@Controller
@RequestMapping(value="/is/insurancesystem")
public class LookPersonnelCtroller {
	Logger logger = Logger.getLogger(ObjectMangementCtroller.class);
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PersonSer personSer;
	  /**
   * 跳转到个人保险信息查看显示页面
   * @param request
   * @param response
   * @param modelMap
   * @return
   * @throws Exception
   */
  @RequestMapping(value="/viewPersonnelInsurance")
  public ModelAndView viewPersonnelInsuranceList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124909")) ;
		return new ModelAndView("/is/insurancesystem/viewPersonnelInsurance",modelMap);
	}
  @RequestMapping(value="/viewEmpInfo")
  public ModelAndView viewEmpInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
	  List search_list =  this.personSer.getEmpList(request);
	  
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124909")) ;
		return new ModelAndView("/is/insurancesystem/viewPersonnelInsurance",modelMap);
	}
}
