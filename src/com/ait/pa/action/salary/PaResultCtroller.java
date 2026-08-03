package com.ait.pa.action.salary;

import java.util.HashMap;
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

import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.pa.service.salary.PaResultSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaResultCtroller.java
 * @Description:
 * @Create date: 2012-6-8 上午11:40:18
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PaResultCtroller {
	Logger logger = Logger.getLogger(PaResultCtroller.class);
	
	@Autowired
	private PaResultSer paResultSer ;

	@Autowired
	private PaCalculateSer paCalculateSer;
	
	@Autowired
	private PaPayScheduleSer paPayScheduleSer;
	
	//工资计算结果
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaResult")
	public ModelAndView viewPaCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.paResultSer.getPaResultAllItem(request)) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
//		List statList = this.paCalculateSer.getPaStatisticList(request);
//		modelMap.put("statList", statList);
		
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		
		
		return new ModelAndView("/pa/salary/viewPaResult",modelMap);
	}
	
	//工资计算结果
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaYutiResult")
	public ModelAndView viewPaCalculateYuti(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.paResultSer.getPaResultAllItem(request)) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		List statList = this.paCalculateSer.getPaStatisticList(request);
		modelMap.put("statList", statList);
		
		
		return new ModelAndView("/pa/salary/viewPaYutiResult",modelMap);
	}
	//工资计算结果
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaResultTwo")
	public ModelAndView viewPaResultTwo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.paResultSer.getPaResultAllItem(request)) ;
		
		return new ModelAndView("/pa/salary/viewPaResultTwo",modelMap);
	}
	//工资结算
	@RequestMapping(value = "/paBalance")
	@ResponseBody
	public Map<String, Object> paBalance(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap)throws Exception{
		String returnString = this.paResultSer.paBalance(request) ;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(returnString.indexOf("成功")!=-1){
			map.put("statusCode", "200");
			map.put("message", returnString);
		}else{
			map.put("statusCode", "300");
			map.put("message", returnString);
		}
		return map;		
	}
	//工资历史数据(部门工资)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaHistoryResult")
	public ModelAndView viewPaHistoryResult(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.addAllAttributes(this.paResultSer.getPaHistoryAllItem(request)) ;
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		modelMap.put("giveDateList", this.paResultSer.getPaGiveDate(request));
		return new ModelAndView("/pa/salary/viewPaHistoryResult",modelMap);
	}
	//工资历史数据(部门工资)2
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaHistoryResultTwo")
	public ModelAndView viewPaHistoryResultTwo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.addAllAttributes(this.paResultSer.getPaHistoryAllItem(request)) ;
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		modelMap.put("giveDateList", this.paResultSer.getPaGiveDate(request));
		return new ModelAndView("/pa/salary/viewPaHistoryResultTwo",modelMap);
	}
	
	/**
	 * 查询工资发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-20 下午06:10:07 
	* @version V1.0
	 */
	@RequestMapping("/getGiveDateOfCurrentPaMonth")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map getGiveDateOfCurrentPaMonth(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("giveDateList", this.paResultSer.getPaGiveDate(request));
		return map;
	}
}
