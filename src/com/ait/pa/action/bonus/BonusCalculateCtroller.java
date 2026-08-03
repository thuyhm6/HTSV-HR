package com.ait.pa.action.bonus;

import java.io.PrintWriter;
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
import com.ait.pa.service.bonus.BonusCalculateSer;
import com.ait.pa.service.bonus.BonusTypeSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusCalculateCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午03:49:57
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusCalculateCtroller {
	Logger logger = Logger.getLogger(BonusCalculateCtroller.class);

	@Autowired
	private BonusCalculateSer bonusCalculateSer;

	@Autowired
	private BonusTypeSer bonusTypeSer;

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewBonusCalculate")
	public ModelAndView viewBonusCalculate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List bonusTypeList = this.bonusTypeSer.getBonusTypeList(request);
		modelMap.put("bonusTypeList", bonusTypeList);
		return new ModelAndView("/pa/bonus/viewBonusCalculate", modelMap);
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bonusCalculate")
	@ResponseBody
	public void bonusCalculate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");

		String returnString = this.bonusCalculateSer.bonusCalculate(request);

		PrintWriter out = response.getWriter();
        
	    out.println(JsonUtil.writeInternal(returnString));
	        
		out.flush();
		out.close();
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCheckPaCalculateType")
	@ResponseBody
	public String getCheckPaCalculateType(HttpServletRequest request)
			throws Exception {
		String returnString = "Y";
		int errorInt = this.bonusCalculateSer.getCheckPaCalculateType(request);
		if (errorInt > 0) {
			returnString = TipMessage.getTipMessage(
					"alert.message.pa.bonus.salaryUseMergerPlanDutyWay",
					request);
		}
		return returnString;
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
	@RequestMapping(value = "/getSalaryProvideDateBn")
	@ResponseBody
	public Map getSalaryProvideDateBn (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getSalaryProvideDateBnList=this.bonusCalculateSer.getSalaryProvideDateBn(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			if(getSalaryProvideDateBnList.size()==0){
				map.put("","当前月没有符合发放日期");
			}else{
				for(int i=0;i<getSalaryProvideDateBnList.size();i++){
					map.put((String)((Map) getSalaryProvideDateBnList.get(i)).get("GIVE_DATE"), ((Map) getSalaryProvideDateBnList.get(i)).get("GIVE_DATE"));
				}
			}
			return map;
		
	}
	
}
