package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.CycleDao;
import com.ait.ar.service.CycleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleSerImp.java
 * @Description:
 * @Create date: 2012-1-6 下午02:29:58
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class CycleSerImp implements CycleSer {

	Logger logger = Logger.getLogger(CycleSerImp.class);
	
	@Autowired
	private CycleDao cycleDao;
	
	@SuppressWarnings("unchecked")
	public Object getCycle(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.cycleDao.getCycle(paramMap) ; 
	}
	
	/**
	 * 查询区间库信息(get getCycle List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getCycleList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				cycleDao.getCycleList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = cycleDao.getCycleList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 保存区间信息(add Cycle Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addCycleInfo(HttpServletRequest request){
		
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());

		try {
			
			this.cycleDao.addCycleInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}
	
	/**
	 * 更新区间信息(update Cycle Info)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateCycleInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			
			this.cycleDao.updateCycleInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	/**
	 * 删除区间信息(delete Cycle Info)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteCycleInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		try {
			
			this.cycleDao.deleteCycleInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}

	/**
	 * 取得区间条数(get Cycle count)
	 * @param request
	 * @return
	 */
	@Override
	public int getCycleCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		return cycleDao.getCycleCnt(paramMap) ;
	}

	/**
	 * 取得区间参数(get CycleParameter List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getCycleParameterList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = cycleDao.getCycleParamList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = cycleDao.getCycleParamList(paramMap) ;
		}
		return retrunList ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getCycleParameterCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		return cycleDao.getCycleParamCnt(paramMap) ;
	}

	/**
	 * 添加区间参数(add CycleParam Info)
	 * @param request
	 * @return int
	 */
	@Override
	public int addCycleParamInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String[] postNos=request.getParameterValues("isChecked");
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("postNos", postNos);
		
		try {
			
			this.cycleDao.addCycleParamInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}

	/**
	 * 修改区间参数(update CycleParam Info)
	 * @param request
	 * @return int
	 */
	@Override
	public int updateCycleParamInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String[] postNos=request.getParameterValues("isChecked");
		paramMap.put("postNos", postNos);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			
			this.cycleDao.updateCycleParamInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除区间参数(delete CycleParam Info)
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteCycleParamInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		try {
			
			this.cycleDao.deleteCycleParamInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 取得区间参数(get Cycle Param)
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getCycleParam(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.cycleDao.getCycleParam(paramMap) ; 
	}

	/**
	 * 检查唯一性(check CycleInfo Unique)
	 * @param request
	 * @return int
	 */
	@Override
	public int checkCycleInfoUnique(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.cycleDao.checkCycleInfoUnique(paramMap);
	}

	/**
	 * 删除检查(check Cycle For Delete)
	 * @param request
	 * @return int
	 */
	@Override
	public int checkCycleForDelete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.cycleDao.checkCycleForDelete(paramMap);
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpTypeCodeList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return cycleDao.getEmpTypeCodeList(paramMap);
	}
	 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpTypeCodeListSUPERVISOR(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return cycleDao.getEmpTypeCodeListSUPERVISOR(paramMap);
	}
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getStatisticList(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return cycleDao.getStatisticList(paramMap);
	}
	
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getKeeperEmpTypeCodeList(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return cycleDao.getKeeperEmpTypeCodeList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getKeeperEmpTypeCodeList(HttpServletRequest request,String person_id) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", person_id);
		return cycleDao.getKeeperEmpTypeCodeList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getDeptNameByDeptNo(HttpServletRequest request,String deptNo, String cpnyId) {
		Map paramMap =  new LinkedHashMap();
		paramMap.put("DEPTNO", deptNo);
		paramMap.put("CPNY_ID", cpnyId);
		return cycleDao.getDeptNameByDeptNo(paramMap);
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getPaSupervisorEmpTypeCodeList(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return cycleDao.getPaSupervisorEmpTypeCodeList(paramMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getJobTypeGroupList(HttpServletRequest request, String cpnyId) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", cpnyId);
		return cycleDao.getJobTypeGroupList(paramMap);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getKeeperJobGroupList(HttpServletRequest request) {
        Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return cycleDao.getKeeperJobGroupList(paramMap);
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getPaSupervisorJobTypeGroupCodeList(HttpServletRequest request) {
		 Map paramMap = ObjectBindUtil.getRequestParamData(request);
			
			return cycleDao.getPaSupervisorJobTypeGroupCodeList(paramMap);
	}
	/**
	 * 查询法定节假日信息(get StatutoryHolidays List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getStatutoryHolidaysList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year="";
		if( paramMap.get("year")== null){
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar.getTime());
			paramMap.put("year", year);
		}
		retrunList = cycleDao.getStatutoryHolidaysList(paramMap) ;
		
		return retrunList ;
	}
	/**
	 * 取得法定节假日条数(get Cycle count)
	 * @param request
	 * @return
	 */
	@Override
	public int getStatutoryHolidaysCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year="";
		if( paramMap.get("year")== null){
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar.getTime());
			paramMap.put("year", year);
		}
		return cycleDao.getStatutoryHolidaysCnt(paramMap) ;
	}
	
}
