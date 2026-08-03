package com.ait.pa.service.imp.workManagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaPayObjDao;
import com.ait.pa.service.workManagement.PaPayObjSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaPayObjSerImp implements PaPayObjSer {

	Logger logger = Logger.getLogger(PaPayObjSerImp.class);
	
	@Autowired
	private PaPayObjDao PaPayObjDao ;
	
	@SuppressWarnings("unchecked")
	public List getPaPayObjList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PAY_SCHEDULE_NO")!=null){
			retrunList = PaPayObjDao.getPaPayObjList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaPayObjCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PAY_SCHEDULE_NO")!=null){
			retrunInt = PaPayObjDao.getPaPayObjCnt(paramMap) ;
		}
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int addPaPayObjInfo(HttpServletRequest request){
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		try {
			
			this.PaPayObjDao.addPaPayObjInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPaPayObjInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.PaPayObjDao.getPaPayObjInfo(paramMap) ; 
	}
	/**
	 * 更新工资支付对象
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatePaPayObjInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {

			this.PaPayObjDao.updatePaPayObjInfo(request,paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	/**
	 * 删除工资支付对象信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doDeletePaPayObjInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.PaPayObjDao.doDeletePaPayObjInfo(request,paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpListForPop(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		//String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		//paramMap.put("EmpOffice", EmpOffice);
		
		retrunList = this.PaPayObjDao.getEmpListForPop(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpSHListForPop(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		paramMap.put("EmpOffice", EmpOffice);
		
		retrunList = this.PaPayObjDao.getEmpSHListForPop(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpListForPopCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		paramMap.put("EmpOffice", EmpOffice);
		
		return this.PaPayObjDao.getEmpListForPopCnt(paramMap) ;
	}
}
