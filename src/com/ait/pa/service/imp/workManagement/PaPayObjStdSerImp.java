package com.ait.pa.service.imp.workManagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaPayObjStdDao;
import com.ait.pa.service.workManagement.PaPayObjStdSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaPayObjStdSerImp implements PaPayObjStdSer {

	Logger logger = Logger.getLogger(PaPayObjStdSerImp.class);
	
	@Autowired
	private PaPayObjStdDao paPayObjStdDao ;
	
	@SuppressWarnings("unchecked")
	public List getPayObjStdList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		// 判断工资区分参数是否为空,则不查数据
		if(paramMap.get("SALARY_DISTIN_NO")!=null){
			retrunList = paPayObjStdDao.getPayObjStdList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPayObjStdCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		// 判断工资区分参数是否为空,则不查数据
		if(paramMap.get("SALARY_DISTIN_NO")!=null){
			retrunInt = paPayObjStdDao.getPayObjStdCnt(paramMap) ;
		}
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int addPayObjStdInfo(HttpServletRequest request){
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		try {
			
			this.paPayObjStdDao.addPayObjStdInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPayObjStdInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paPayObjStdDao.getPayObjStdInfo(paramMap) ; 
	}
	/**
	 * 更新区间信息(update PaPayObjStd Info)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatePayObjStdInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paPayObjStdDao.updatePayObjStdInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	/**
	 * 删除工资对象基准信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doDeletePayObjStdInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paPayObjStdDao.doDeletePayObjStdInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
}
