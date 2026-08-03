package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.RecordTestListDao;
import com.ait.ar.service.RecordTestListSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Service
public class RecordTestListSerImpl implements RecordTestListSer{
	
    Logger logger = Logger.getLogger(CycleSerImp.class);
	
	@Autowired
	private RecordTestListDao recordTestListDao;

	/**查看打卡记录
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordTestList(HttpServletRequest request) {
		
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("Cpny_Id", admin.getCpnyId());
		Object KEYTAPE =  paramMap.get("KEYTAPE");
		Object RECORD_TEST_DATE = paramMap.get("RECORD_TEST_DATE");
		if(RECORD_TEST_DATE == null || "".equals(RECORD_TEST_DATE)){
			paramMap.put("RECORD_TEST_DATE", DateUtil.getSysdateStr());
		}
		if(KEYTAPE != null && !"".equals(KEYTAPE)){
			if("1".equals(KEYTAPE)){
				paramMap.put("KEYTAPE1", "KEYTAPE");
			}else if("2".equals(KEYTAPE)){
				paramMap.put("KEYTAPE2", "KEYTAPE");
			}
		}
		if (UiUtil.getPageNum(request) > 0){
			
			retrunList = 
					recordTestListDao.getRecordTestList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = recordTestListDao.getRecordTestList(paramMap) ;
		}
		
		return retrunList ;
	}
		/**
		 * 取得条数
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public int getRecordTestCnt(HttpServletRequest request) {
			
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("Cpny_Id", admin.getCpnyId());
			Object RECORD_TEST_DATE = paramMap.get("RECORD_TEST_DATE");
			if(RECORD_TEST_DATE == null || "".equals(RECORD_TEST_DATE)){
				paramMap.put("RECORD_TEST_DATE", DateUtil.getSysdateStr());
			}
			return recordTestListDao.getRecordTestCnt(paramMap) ;
		}
	
}
