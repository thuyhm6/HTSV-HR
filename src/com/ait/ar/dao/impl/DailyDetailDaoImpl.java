package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.DailyDetailDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DailyDetailDaoImpl.java
 * @Description:
 * @Create date: 2012-2-6 上午10:54:03
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class DailyDetailDaoImpl extends SqlMapClientSupport implements DailyDetailDao {
	
	/**
	 * 查看个人日历(get DailyDetail List)
	 * @param obj
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDailyDetailList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.dailyDetail.getDailyDetailList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取班次信息(get Shift List)
	 * 
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList(Object obj) {
		List returnList = new ArrayList() ;
		try {
		
			returnList = this.queryForList("ar.dailyDetail.getShiftList", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 删除个人日历信息(delete DailyDetail Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteDailyDetailInfo(Object obj) {
		
		try {
			 
			this.delete("ar.dailyDetail.DeleteDailyDetailInfo", obj) ;
	        } catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 取个人日历信息是否存在(get DailyDetail Info)
	 * @param Object
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getDailyDetailInfo(Object obj) {
		int returnInt=0;
		try {
			 
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.dailyDetail.checkDailyDetailInfo", obj)), Integer.class) ;
	        } catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 个人日历页面修改(insert DailyDetail Info)
	 * @param List
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertDailyDetailInfo(List list) throws Exception {
		
		this.deleteForList("ar.dailyDetail.DeleteDailyDetailInfo", list) ;
		
		this.insertForList("ar.dailyDetail.insertDailyDetailInfo", list) ;
	}
	
	/**
	 * 取个人信息(get Emp List)
	 * 
	 * @param Object
	 * @return Object
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpInfo(Object obj) {
		
		Object object = "" ;
		try {
		
			object = this.queryForObject("ar.dailyDetail.getEmpInfo", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return object ;
	}
	
	/**
	 * 取班次信息(get Shift List)
	 * 
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getShiftName(Object obj) {
		String returnStr = "" ;
		try {
		
			returnStr = ObjectUtils.toString(this.queryForObject("ar.dailyDetail.getShiftName", obj));
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnStr ;
	}
	
	/**
	 * 获得人员列表(get EmpCalendar List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getDailyDetailPersonList(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.dailyDetail.getDailyDetailPersonList", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@Override
	public int getDailyDetailPersonCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.dailyDetail.getDailyDetailPersonCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
	
	/**
	 * 获得人员列表(getLeaveList)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getLeaveList(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.dailyDetail.getLeaveList", (Map)obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
}
