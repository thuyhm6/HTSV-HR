package com.ait.pa.service.imp.salary;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaHistoryDao;
import com.ait.pa.service.salary.PaHistorySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class PaHistorySerImp implements PaHistorySer {

	Logger logger = Logger.getLogger(PaHistorySerImp.class);
	
	@Autowired
	private PaHistoryDao paHistoryDao;
	
	@SuppressWarnings("unchecked")
	public int getCheckPaHistoryFlag(HttpServletRequest request) {
		int retrunInt = 0 ;
	
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("AR_ADMIN_ID", admin.getAdminID()) ;

		retrunInt = paHistoryDao.getCheckPaHistoryFlag(paramMap) ;
		
		return retrunInt ;
	}
}
