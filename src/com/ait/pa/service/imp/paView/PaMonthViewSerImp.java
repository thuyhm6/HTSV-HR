package com.ait.pa.service.imp.paView;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.service.paView.PaMonthViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaMonthViewSerImp.java
 * @Description:
 * @Create date: 2012-5-23 下午04:25:05
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaMonthViewSerImp implements PaMonthViewSer {

	Logger logger = Logger.getLogger(PaMonthViewSerImp.class);
	
	@Autowired
	private ViewOptionUtil viewOptionUtil;
	
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
		
		if(paramMap.get("paYear") != null && !"".equals(paramMap.get("paYear").toString())
				&& paramMap.get("paMonth") != null && !"".equals(paramMap.get("paMonth").toString())){
			
			paramMap.put("paMonth", paramMap.get("paYear").toString()+paramMap.get("paMonth").toString());
		}
		
		dataTable = viewOptionUtil.makeDataTable(paramMap);
		
		return dataTable;
	}
}
