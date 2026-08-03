package com.ait.pa.service.imp.paView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaChainStafftDao;
import com.ait.pa.dao.PaEmpAccountDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.pa.service.paView.PaChainStafftSer;
import com.ait.pa.service.salary.PaProgressSer;
import com.ait.pa.service.workManagement.PaEmpAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaChainStafftSerImp implements PaChainStafftSer {

	Logger logger = Logger.getLogger(PaChainStafftSerImp.class);
	
	@Autowired
	private PaChainStafftDao paChainStafftDao ;
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		// 第一次进入页面不查询
		if(paramMap.get("firstView")==null){
			retrunList = paChainStafftDao.getPaEmpAccountList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		return retrunList ;
	}
	

}
