package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.EmpCalendarDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpCalendarDaoImpl.java
 * @Description:
 * @Create date: 2012-2-6 上午10:54:03
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class EmpCalendarDaoImpl  extends SqlMapClientSupport implements EmpCalendarDao {
	
	/**
	 * 查看个人日历(get EmpCalendar List)
	 * @param obj
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getEmpCalendarList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empCalendar.getEmpCalendarList", obj);
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
		
			returnList = this.queryForList("ar.empCalendar.getShiftList", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 取类型信息(get Shift List)
	 * 
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDateTypeList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empCalendar.getDateTypeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 删除个人日历信息(delete EmpCalendar Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmpCalendarInfo(Object obj) {
		
		try {
			 
			this.delete("ar.empCalendar.DeleteEmpCalendarInfo", obj) ;
	        } catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 取个人日历信息是否存在(get EmpCalendar Info)
	 * @param Object
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getEmpCalendarInfo(Object obj) {
		int returnInt=0;
		try {
			 
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.empCalendar.checkEmpCalendarInfo", obj)), Integer.class) ;
	        } catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 获取AR_DETAIL_TSTO_APPLY表中某一天是否生成了对应班的数据
	 * @param Object
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getArDetailTSTONUM(Object obj) {
		String returnValue = null ;
		try {
			returnValue = StringUtil.checkNull(this.queryForObject("ar.empCalendar.getArDetailTSTONUM", obj));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnValue ;
	}
	
	/**
	 * 个人日历页面修改(insert EmpCalendar Info)
	 * @param List
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertEmpCalendarInfo(List list) throws Exception {
		
		this.deleteForList("ar.empCalendar.DeleteEmpCalendarInfo", list) ;
		
		  this.insertForList("ar.empCalendar.insertEmpCalendarInfo", list) ;
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
		
			object = this.queryForObject("ar.empCalendar.getEmpInfo", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return object ;
	}
	/**
	 * 查询各大区
	 */
	@SuppressWarnings("unchecked")
	public List getBigDistinct(){
		List list = new ArrayList();
		try {
			list = queryForList("ar.empCalendar.getBigDistinct");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/* @author xuehaifei
	 * 班组日历查看
	 * 2014-7-9
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empCalendar.getArClassCalendarList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListForNormalShift(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empCalendar.getArClassCalendarListForNormalShift", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public String getDefaultGroup(Object object) {
		String returnValue = null ;
		try {
			returnValue = StringUtil.checkNull(this.queryForObject("ar.empCalendar.getDefaultGroup", object));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnValue ;
	}
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListGs(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empCalendar.getArClassCalendarListGs", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/* @author xuehaifei
	 * 
	 * 2014-7-28
	 * 班次日历修改
	 */
	@SuppressWarnings("unchecked")
	public void updateClassCalendarInfo(Object object) throws Exception {
		this.delete("ar.empCalendar.deleteClassCalendarInfo",object);
		this.insert("ar.empCalendar.updateClassCalendarInfo",object) ;
		
		//删除历史班次表
		this.delete("ar.empCalendar.deleteClassCalendarHistoryInfo",object);
		//插入历史班次表
		this.insert("ar.empCalendar.insertClassCalendarHistoryInfo",object) ;
		//由于班组变更，因此要同时改变已经生成的ar_detail
		//this.update("ar.empCalendar.updateArDetailOTCreateByNull",object) ;
		//this.update("ar.empCalendar.updateArDetailOTCreateByNotNull",object) ;
		//this.update("ar.empCalendar.updateArDetailLeaveCreateByNull",object) ;
		//this.update("ar.empCalendar.updateArDetailLeaveCreateByNotNull",object) ;
	}

	@Override
	public void insertArShiftChange(Object object) throws Exception {
        this.insert("ar.empCalendar.insertArShiftChange",object);		
	}

	public void insertArShiftChangeByShift(Object object) throws Exception {
        this.insert("ar.empCalendar.insertArShiftChangeByShift",object);		
	}
	public void insertArShiftChangeByCompany(Object object) throws Exception {
        this.insert("ar.empCalendar.insertArShiftChangeByCompany",object);		
	}
	@Override
	public List getShiftNo(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empCalendar.getShiftNo", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	

	/**
	 * 取个人日历真实日期
	 * @param Object
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getRealDate(Object obj) {
		String returnInt="9999/12/31";
		try {
			 
			returnInt = StringUtil.checkNull(this.queryForObject("ar.empCalendar.getRealDate", obj));
	        } catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	public void createArDEtailClassCalendarInfo(Object object) throws Exception {
        this.insert("ar.empCalendar.createArDEtailClassCalendarInfo",object);		
	}
	public void createArDEtailClassOverTimeLimit(Object object) throws Exception {
		this.insert("ar.empCalendar.createArDEtailClassOverTimeLimit",object);		
	}
	
	@SuppressWarnings("unchecked")
	public List viewEmpCalendarCheckInfoList (String target, Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.empCalendar."+target, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList;
	}
}
