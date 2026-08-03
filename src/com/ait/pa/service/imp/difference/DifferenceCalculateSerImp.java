package com.ait.pa.service.imp.difference;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.DifferenceCalculateDao;
import com.ait.pa.service.difference.DifferenceCalculateSer;
import com.ait.web.util.ObjectBindUtil;

@Service
public class DifferenceCalculateSerImp implements DifferenceCalculateSer {

	Logger logger = Logger.getLogger(DifferenceCalculateSerImp.class);
	
	@Autowired
	private DifferenceCalculateDao differenceCalculateDao ;
	
	@SuppressWarnings("unchecked")
	public String differenceCalculate(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnString = differenceCalculateDao.differenceCalculate(paramMap) ;
		
		return returnString ;
	}
}
