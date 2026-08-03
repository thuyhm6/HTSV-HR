package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ait.ar.dao.CompanyCalendarDao;
import com.ait.ar.dao.EmpCalendarDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class CompanyCalendarDaoImpl extends SqlMapClientSupport implements CompanyCalendarDao {

	@Autowired
	private EmpCalendarDao empCalendarDao;
	/**
	 * 取得公司日历信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getCompanyCalendar(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getCompanyCalendarList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有公司日历信息列表(get CompanyCalendar List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.getCompanyCalendarList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有日历信息列表(get CompanyCalendar List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCalendarList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.getCalendarList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得个人日历信息列表(get CompanyCalendar List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalCalendarList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.getPersonalCalendarList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	/**
	 * 取得所有日历天数信息列表(get CompanyCalendar List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCalendarDayList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.getCalendarDayList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 插入公司日历信息(add CompanyCalendar Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfo(Object obj) {
		
		try {
			this.insert("ar.companyCalendar.addCompanyCalendarInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 更新公司日历信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateCompanyCalendarInfo(Object obj) {
		
		try {
			this.update("ar.companyCalendar.updateCompanyCalendarInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 删除公司日历信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteCompanyCalendarInfo(Object obj) {
		
		return 0 ;
	}

	@Override
	public int updateCompanyCalendarInfo(List list) {
		try {
			for(int i=0;i<list.size() ;i++ ){
				LinkedHashMap companyCalendarList =  (LinkedHashMap)list.get(i);
				String udate=(String) companyCalendarList.get("DDATE_STR");
				//String udates=empCalendarDao.getRealDate(udate.replace("-", "/"));
				String udates=udate.replace("-", "/");
				DateFormat dd=new SimpleDateFormat("yyyy/MM/dd");		
				Date date1=null;					
			    try {
					date1 = dd.parse(udates);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			    Date nowdate = new Date();
			    Boolean flag=date1.before(nowdate);
			    if(flag){
			    	try {
						this.update("ar.empCalendar.insertArShiftChangeByCompany", companyCalendarList) ;
					} catch (Exception e) {
						e.printStackTrace();
					}	
			    } 
			    //如果是节假日则同时更新班组日历、个人日历
			    if(companyCalendarList.get("TYPEID").toString().equals("1442")){
				    this.update("ar.companyCalendar.updateClassCalendarInfo", companyCalendarList) ;
				    this.update("ar.companyCalendar.updateClassCalendarInfoPersonal", companyCalendarList) ;
			    }
				this.update("ar.companyCalendar.updateCompanyCalendarInfo", companyCalendarList) ;
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List getShiftList(Map paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.getShiftList", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 插入班次日历信息(add CompanyCalendar Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfoBanCi(Object obj) {
		
		try {
			this.insert("ar.companyCalendar.addCompanyCalendarInfoBanCi", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	/**
	 * 更新法定节假日信息(add CompanyCalendar Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addStatutoryHolidaysInfo(Object obj) {
		
		try {
			//更新公司日历
			this.update("ar.companyCalendar.addStatutoryHolidaysInfo", obj) ;
			//先删除历史班组日历
			this.delete("ar.companyCalendar.deleteClassCalendarHistoryInfo", obj) ;
			//再插入历史班组日历
			this.insert("ar.companyCalendar.inserteArGroupHistoryInfo", obj) ;
			//再更新班组日历
			this.update("ar.companyCalendar.updateClassStatutoryHolidaysInfo", obj);
			
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	public int updateStatutoryHolidaysInfo(Map paramMap) {
		try {
			this.update("ar.companyCalendar.updateStatutoryHolidaysInfo", paramMap) ;
			//更新班组日历
			this.update("ar.companyCalendar.updateClassStatutoryHolidaysInfo", paramMap) ;

		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@Override
	public Object getOneStatutoryHolidayInfo(Map paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.getOneStatutoryHolidayInfo", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		LinkedHashMap returnObj = new LinkedHashMap() ;
		if(returnList.size()>0){
			returnObj=(LinkedHashMap)returnList.get(0);
		}
		return returnObj ;
	}
	public int deleteStatutoryHolidaysInfo(Map paramMap) {
		try {
			this.update("ar.companyCalendar.deleteStatutoryHolidaysInfo", paramMap) ;
			//更新班组日历deleteClassStatutoryHolidaysInfo
			//删除班组日历
			this.delete("ar.empCalendar.deleteClassCalendarInfo",paramMap);
			//插入班组日历
			this.insert("ar.companyCalendar.insertClassCalendarInfo", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取年假信息
	 */
	public List viewVacEmpList(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.viewVacEmpList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 清算上一年年假
	 */
	public String executeVacClear(Map paramMap) {
		String result = "ok";
		try {			
			this.insert("ar.companyCalendar.addVacEmpIntoPaParamData", paramMap);
			this.update("ar.companyCalendar.updateVacEmpAfterClear", paramMap);
		} catch (SQLException e) {
			result = "error";
			e.printStackTrace();
		}
		return result ;
	}

	/**
	 * 保存年假信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpVacInfo(Object object)throws Exception {
		this.updateForList("ar.companyCalendar.saveEmpVacInfo",(List)object);
		return 1;
	}
	/**
	 * 保存年假计划信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpPlanVacInfo(Object object)throws Exception {
		this.updateForList("ar.companyCalendar.saveEmpPlanVacInfo",(List)object);
		return 1;
	}
	
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpList(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.viewTxEmpList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpTSTOList(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.companyCalendar.viewTxEmpTSTOList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 保存倒休信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo(Object object)throws Exception {
		this.updateForList("ar.companyCalendar.saveEmpTxInfo",(List)object);
		return 1;
	}

	/**
	 * 保存倒休信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo2(Object object)throws Exception {
		this.updateForList("ar.companyCalendar.saveEmpTxInfo2",(List)object);
		return 1;
	}
}
