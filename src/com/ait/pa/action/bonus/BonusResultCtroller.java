package com.ait.pa.action.bonus;

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
import com.ait.pa.service.bonus.BonusResultSer;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusResultCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午03:20:34
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusResultCtroller {
	Logger logger = Logger.getLogger(BonusResultCtroller.class);
	
	@Autowired
	private BonusResultSer bonusResultSer ;
	
	/**
	 * 方法说明（中文，英文） 计算结果1 奖金维护
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusResult")
	public ModelAndView viewBonusCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.bonusResultSer.getBonusResultAllItem(request)) ;
		
		return new ModelAndView("/pa/bonus/viewBonusResult",modelMap);
	}
	/**
	 * 方法说明（中文，英文） 计算结果2 奖金维护
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusResultTwo")
	public ModelAndView viewBonusResultTwo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.bonusResultSer.getBonusResultAllItem(request)) ;
		
		return new ModelAndView("/pa/bonus/viewBonusResultTwo",modelMap);
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bonusBalance")
	@ResponseBody
	public Map<String, Object> bonusBalance(HttpServletRequest request)throws Exception{
		
		String returnString = this.bonusResultSer.bonusBalance(request) ;
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
