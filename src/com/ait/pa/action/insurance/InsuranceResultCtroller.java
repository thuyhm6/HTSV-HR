package com.ait.pa.action.insurance;

import java.util.HashMap;
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
import com.ait.pa.service.insurance.InsuranceResultSer;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceResultCtroller.java
 * @Description:
 * @Create date: 2012-2-17 下午02:49:11
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/insurance")
public class InsuranceResultCtroller {
	Logger logger = Logger.getLogger(InsuranceResultCtroller.class);
	
	@Autowired
	private InsuranceResultSer insuranceResultSer ;
	
	/**
	 * 保险计算结果（view Insurance Result）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceResult")
	public ModelAndView viewInsuranceCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.insuranceResultSer.getInsuranceResultAllItem(request)) ;
		
		return new ModelAndView("/pa/insurance/viewInsuranceResult",modelMap);
	}
	/**
	 * 保险计算结果2（view Insurance Result） 奖金维护
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceResultTwo")
	public ModelAndView viewInsuranceResultTwo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.insuranceResultSer.getInsuranceResultAllItem(request)) ;
		
		return new ModelAndView("/pa/insurance/viewInsuranceResultTwo",modelMap);
	}
	
	/**
	 * 保险结算（insurance Balance）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insuranceBalance")
	@ResponseBody
	public Map<String, Object> insuranceBalance(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap)throws Exception{
		
		String returnString = this.insuranceResultSer.insuranceBalance(request) ;
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
}
