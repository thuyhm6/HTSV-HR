package com.ait.pa.service.imp.paView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaWageComItemDao;
import com.ait.pa.service.paView.PaWageComViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicViewSerImp.java
 * @Description:
 * @Create date: 2012-5-23 下午02:56:08
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaWageComViewSerImp implements PaWageComViewSer {

	Logger logger = Logger.getLogger(PaWageComViewSerImp.class);
	
	@Autowired
	private ViewOptionUtil viewOptionUtil;
	@Autowired
	private PaWageComItemDao paWageComItemDao;
	
	/**
	 * 工资基础(make DataTable)
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String menuNo) {
		
		String dataTable = "";
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PA_SUPERVISIOR_INFO", admin.getPersonId());
		
		paramMap.put("MENU_NO", menuNo);
		
		if(paramMap.get("paYearfrom") != null && !"".equals(paramMap.get("paYearfrom").toString())
				&& paramMap.get("paMonthfrom") != null && !"".equals(paramMap.get("paMonthfrom").toString())){
			
			paramMap.put("paMonthfrom", paramMap.get("paYearfrom").toString()+paramMap.get("paMonthfrom").toString());
		}
		if(paramMap.get("paYearto") != null && !"".equals(paramMap.get("paYearto").toString())
				&& paramMap.get("paMonthto") != null && !"".equals(paramMap.get("paMonthto").toString())){
			
			paramMap.put("paMonthto", paramMap.get("paYearto").toString()+paramMap.get("paMonthto").toString());

		}
		
		dataTable = viewOptionUtil.makeDataTablePa(paramMap);
		;
		return dataTable;
	}

	@Override
	public List getPaWageComList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		retrunList=null;
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
       List itemList =this.paWageComItemDao.getPaItem2(request);
       if(itemList!=null && itemList.size()>0){
           paramMap.put("id", ((Map)itemList.get(0)).get("ID"));
           paramMap.put("name", ((Map)itemList.get(0)).get("NAME"));
       }
       
		if(paramMap.get("Yearfrom") != null && !"".equals(paramMap.get("Yearfrom").toString())
				&& paramMap.get("Monthfrom") != null && !"".equals(paramMap.get("Monthfrom").toString())){
			
			paramMap.put("Monthfrom", paramMap.get("Yearfrom").toString()+paramMap.get("Monthfrom").toString());
		}
		if(paramMap.get("Yearto") != null && !"".equals(paramMap.get("Yearto").toString())
				&& paramMap.get("Monthto") != null && !"".equals(paramMap.get("Monthto").toString())){
			
			paramMap.put("Monthto", paramMap.get("Yearto").toString()+paramMap.get("Monthto").toString());

		}
		if(paramMap.get("TYPE2").equals("da")){
			paramMap.put("TYPE2", ">");
		}else if 
		(paramMap.get("TYPE2").equals("xiao")){
			paramMap.put("TYPE2", ">");
			}
		String seach =request.getParameter("seach_TYPE1")!=null?request.getParameter("seach_TYPE1"):"";
		if(seach.equals("shang"))
		{
			retrunList=paWageComItemDao.getPaContrastList(paramMap);
		}else if (seach.equals("ben")){
			retrunList=paWageComItemDao.getPaScreeningList(paramMap);
		}
		
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getWmpTypeLikst(String cpnyId) {
		List retrunList = new ArrayList();
		retrunList=paWageComItemDao.getEmpType(cpnyId);
		return retrunList;
	}

	@Override
	public List getPaItem2(HttpServletRequest request) {
		return paWageComItemDao.getPaItem2(request);
	}



}
