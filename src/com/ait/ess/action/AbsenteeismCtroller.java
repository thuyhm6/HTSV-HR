package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArDetailSer;
import com.ait.ess.service.AbsenteeismSer;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

/**
 * @fileName: AbsenteeismCtroller
 * @Description:旷工申请 
 */
@Controller
@RequestMapping(value = "/ess/absenteeism")
public class AbsenteeismCtroller {

	Logger logger = Logger.getLogger(AbsenteeismCtroller.class);


	@Autowired
	private ArDetailSer arDetailSer;
	
	/**
	 * 旷工申请查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAbsenteeismList")
	public ModelAndView viewOvertimeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if(paramMap.get("deptID") != null && paramMap.get("deptID") != "" ){
			modelMap.put("deptID", paramMap.get("deptID").toString());
		}
		modelMap.put("itemNo", paramMap.get("itemNo"));
		
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		modelMap.put("sDate", df.format(new Date(d.getTime() - 24*60*60*1000)));
	    modelMap.put("eDate", df.format(new Date(d.getTime() - 24*60*60*1000))); 
	     
		modelMap.put("ABSENT", "ABSENT");
		List getArDetailList = this.arDetailSer.getArDetailList(request,modelMap);
		modelMap.put("arDetailList", getArDetailList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.arDetailSer.getArDetailListCnt(request, modelMap));
		return new ModelAndView("/ess/absenteeism/viewAbsenteeismList", modelMap);
	}
}