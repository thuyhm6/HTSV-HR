package com.ait.paEcc.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;


import com.ait.paEcc.service.PaEccService;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.StringUtil;
/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: PaEccLookSalaryCtroller.java
 * @Description: 经济补偿金===个人平均工资查看
 * @Create date: 2014-1-17 下午02:55:16
 * @Create by: 
 * @version 5.5
 */
@Controller
@RequestMapping(value="/paEcc/lookavgsalary")
public class PaEccLookSalaryCtroller {

	@Autowired
	private PaEccService paEccService;
	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewAvgSalary")
	public ModelAndView viewAvgSalary(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		String empId = request.getParameter("dwz.person.empId");
		if(empId!=null&&empId!=""){
			Map empInfo = paEccService.getResignInfo(request);
			modelMap.put("empInfo", empInfo);
			modelMap.put("empId", request.getParameter("dwz.person.empId"));
			
			List<Map<String, Object>> list = null;
			if(empInfo!=null&&empInfo.size()>0){//&&StringUtil.checkNull(empInfo.get("MONTH_START").toString())!=""
				list = this.getEccEmpPaInfo(request, empInfo);
			}
			modelMap.put("list", list);
			if(empInfo==null){
				modelMap.put("nullInfo", "1");
			}
		}
		return new ModelAndView("/paEcc/lookavgsalary/viewAvgSalary",modelMap);
	}
	
	private int getTotal(Map map){
		int total = 0;
		Set set = map.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			try {
				String value = ObjectUtils.toString(map.get(key));
				total = total + Integer.parseInt(ObjectUtils.toString(map.get(key)));
			} catch (Exception e) {
				total = total + 0;
			}
		}
		return total;
	}
	
	private String addMonth(String dateStr,int count){
		if(dateStr.length()==6){
			dateStr = dateStr.substring(0, 4)+"-"+dateStr.substring(4, 6)+"-01";
		}
		Calendar c = DateUtil.ParseGregorianCalendar(dateStr);
		c.add(Calendar.MONTH, count);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(c.getTime());
	}
	
	private List getEccEmpPaInfo(HttpServletRequest request,Map empInfo){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String month = "";
		Map<String, Object> rowTitle = new HashMap<String, Object>();
		rowTitle.put("TYPE", TipMessage.getTipMessage("sys.affirm.title.type", request));//类型
		list.add(rowTitle);
		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("TYPE", TipMessage.getTipMessage("hr.viewWorkInfo.title.PAYROLL", request));//工资
		list.add(row1);
		Map<String, Object>  rowTotal = new HashMap<String, Object>();
		rowTotal.put("TYPE", TipMessage.getTipMessage("ess.viewpersonalpainfo.heji", request));//合计
		list.add(rowTotal);
		List<Map> monthData = new ArrayList();
		Map parameterMap = new LinkedHashMap();
		parameterMap.put("DATE_STARTED", empInfo.get("DATE_STARTED").toString());
		int dataCount = 0;
		for (int i = 0; i < 12; i++) {
			month = this.addMonth(empInfo.get("PAMONTH").toString(), -i);
			//得到工资开始年月
			String PA_MONTH_START = paEccService.getEmpPAStartMonth(parameterMap);
			parameterMap.put("PA_MONTH_START",PA_MONTH_START);
			parameterMap.put("PA_MONTH",month);
			monthData = paEccService.getEccEmpPaInfo(request,parameterMap);
			if(monthData.size() > 0&&monthData.get(0).get("BASE_SALARY")!=null){
				dataCount++;	
				Map map = monthData.get(0);
				rowTitle.put("MONTH"+(i+1), month);
				row1.put("MONTH"+(i+1), map.get("BASE_SALARY").toString());
				rowTotal.put("MONTH"+(i+1), this.getTotal(map));
			}else{
				rowTitle.put("MONTH"+(i+1), month);
				row1.put("MONTH"+(i+1), 0);
				rowTotal.put("MONTH"+(i+1), 0);
			}
		}
		//每行合计
		int i = 0;
		for (Map simpleMap2 : list) {
			if(i == 0){
				rowTitle.put("TOTAL", TipMessage.getTipMessage("ess.viewpersonalpainfo.heji", request));
				rowTitle.put("AVGSALARY",TipMessage.getTipMessage("display.pa.averagewage", request));
			}else{
				simpleMap2.put("TOTAL", this.getTotal(simpleMap2));
				if(dataCount == 0){
					simpleMap2.put("AVGSALARY", 0);
				}else{
					simpleMap2.put("AVGSALARY", new Integer(String.valueOf(simpleMap2.get("TOTAL")))/dataCount);
				}
			}
			i ++;
		}
		return list;
	}
	public static void main(String[] args) {
	}
}
