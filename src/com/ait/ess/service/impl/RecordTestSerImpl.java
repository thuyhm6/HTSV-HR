package com.ait.ess.service.impl;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.RecordTestDao;
import com.ait.ess.service.RecordTestSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
@Service
public class RecordTestSerImpl implements RecordTestSer{
	//@Autowired
	Logger logger = Logger.getLogger(RecordTestSerImpl.class);
	@Autowired
	private RecordTestDao recordTestDao;
	
	/**
	 * 添加打卡记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	
	public int addRecordTest(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			paramMap.put("RECORD_TEST_DATE", request.getParameter("RECORD_TEST_DATE"));
			paramMap.put("RECORD_TEST_REMARK", request.getParameter("RECORD_TEST_REMARK"));
			paramMap.put("RECORD_TEST_ADD", request.getParameter("RECORD_TEST_ADD"));
			this.recordTestDao.addRecordTest(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;

	}

	
	
}
