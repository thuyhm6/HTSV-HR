package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ShiftDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ShiftDaoImpl extends SqlMapClientSupport implements ShiftDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得班次信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getShift(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getShiftList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有班次信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList(Object obj) {
		List returnList = new ArrayList() ;
		
		return this.getShiftList(obj, -1, -1) ;
	}
	
	/**
	 * 取得所有班次信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.shift.getShiftList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.shift.getShiftList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit(Object obj) {
		List returnList = new ArrayList() ;
		try {
			
				returnList = this.queryForList("ar.shift.getWorkTimeLsit", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDateTypeLsit(Object obj) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("ar.shift.getDateTypeLsit", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit1(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.shift.getWorkTimeLsit1", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit2(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.shift.getWorkTimeLsit2", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 插入班次信息(add Shift Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addShiftInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("ar.shift.addShiftInfo", object) ;
		
		Map paramMap = (Map)obj ;
		List insertShiftParameterList = (List)paramMap.get("insertShiftParameterList") ; 
		List paramList = new ArrayList() ;
		for(int i=0;i<insertShiftParameterList.size();i++){
			((LinkedHashMap)insertShiftParameterList.get(i)).put("NO", object.get("NO"));
		}
			 
		this.insertForList("ar.shift.addShiftParameterInfo", insertShiftParameterList) ;
		
	}
	
	/**
	 * 更新班次信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateShiftInfo(Object obj) {
		
		int result = 1;
		try {
			this.startTransaction() ;
			
			this.update("ar.shift.updateShiftInfo", obj) ;
			
			this.delete("ar.shift.deleteShiftParameterInfo", obj) ;
			
			Map paramMap = (Map)obj ;
			List insertShiftParameterList = (List)paramMap.get("insertShiftParameterList") ; 
				 
			this.insertForList("ar.shift.addShiftParameterInfo", insertShiftParameterList) ;
			
			this.commitTransation() ;
		} catch (SQLException e) {			
			e.printStackTrace();
			result = 0;
		}
		finally {
			try {
				this.endTransation() ;
			} catch (Exception e) {
				e.printStackTrace();
				result = 0;
			}
		 }
		
		return result;
	}
	
	/**
	 * 更新班次信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateShiftInfoByNO(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("ar.shift.updateShiftIDInfo", obj) ;
	}
	
	/**
	 * 删除班次信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteShiftInfo(Object obj) throws Exception {
		
		this.update("ar.shift.updateShiftInfo", obj) ;
		this.update("ar.shift.updateShiftParameterInfo", obj) ;
	}
	
	/**
	 * 取出班次参数信息(get ShiftParameter List)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getShiftParameterList(Object object){
		
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.shift.getShiftParameterList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@Override
	public int getShiftCnt(Object object) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.shift.getShitfCnt", object)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	@Override
	public int getShiftNewSeq(Object object) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.shift.getShitfNewSeq", object)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 检查班次信息并返回错误代码
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkShiftInfo(Object object) {
		int returnInt = 0 ;
		
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.shift.getShiftCheckShiftId", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;	
	}

	/**
	 * 查询班次列表(get Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public List getItemList(Map paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.shift.getItemList", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * G/P平均值(get Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public List calculateAvg(Map paramMap) {
		 List returnList = new ArrayList() ;
		try {
			returnList =  this.queryForList("ar.shift.calculateAvg", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询部门区分(get Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public String getDeptDistinguishNo(Object obj) {
		// TODO Auto-generated method stub
		Object tempObj = null;
		String returnList = "" ;
		try {
			tempObj = this.queryForObject("ar.shift.getDeptDistinguishNo", obj);
			returnList = tempObj == null ? "" : tempObj.toString();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}
