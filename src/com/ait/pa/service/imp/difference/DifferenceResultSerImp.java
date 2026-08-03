package com.ait.pa.service.imp.difference;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.DifferenceResultDao;
import com.ait.pa.service.difference.DifferenceResultSer;
import com.ait.web.util.ObjectBindUtil;

@Service
public class DifferenceResultSerImp implements DifferenceResultSer {

	Logger logger = Logger.getLogger(DifferenceResultSerImp.class);
	
	@Autowired
	private DifferenceResultDao differenceResultDao ;
	

	@SuppressWarnings("unchecked")
	public String differenceBalance(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnString = differenceResultDao.differenceBalance(paramMap) ;
		
		return returnString ;
	}
}
