package com.ait.pa.service.imp.paView;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.service.paView.PaYearViewSer;
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
public class PaYearViewSerImp implements PaYearViewSer {

	Logger logger = Logger.getLogger(PaYearViewSerImp.class);

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
		if(paramMap.get("paYear") != null && !"".equals(paramMap.get("paYear").toString())){
			paramMap.put("PA_YEAR", paramMap.get("paYear").toString());
		}
		dataTable = viewOptionUtil.makeDataTable(paramMap);
		
		return dataTable;
	}
}
