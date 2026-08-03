package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.CycleDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.dao.impl.SyLanguageDaoImpl;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleDaoImpl.java
 * @Description:
 * @Create date: 2012-1-6 下午02:25:35
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class CycleDaoImpl extends SqlMapClientSupport implements CycleDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得周期信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getCycle(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getCycleList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有周期列表(get Cycle List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getCycleList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getCycleList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有周期列表条数(get Cycle count)
	 * @param List
	 * @return int
	 */
	@Override
	public int getCycleCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.getCycleCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有周期列表(get Cycle List)
	 * @param Object
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getCycleList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.cycle.getCycleList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.cycle.getCycleList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 保存区间信息(add Cycle Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addCycleInfo(Object obj)throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("ar.cycle.addCycleInfo", object) ;
		
	}
	
	/**
	 * 更新周期信息(update Cycle Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateCycleInfo(Object obj)throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		
		this.update("ar.cycle.updateCycleInfo", obj) ;
		
	}
	
	/**
	 * 删除周期信息(delete Cycle Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteCycleInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		
		this.delete("ar.cycle.deleteCycleInfo", obj) ;
		
	}
	
	/**
	 * 区间参数查询 带分页(get CycleParam List)
	 * @param Object
	 * @return List
	 */
	@Override
	public List getCycleParamList(Map obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.cycle.getCycleParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.cycle.getCycleParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 区间参数查询全部
	 * @param Object
	 * @return
	 */
	@Override
	public List getCycleParamList(Map obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		
		returnList = this.getCycleParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 区间参数统计记录总数
	 * @param Object
	 * @return
	 */
	@Override
	public int getCycleParamCnt(Map obj) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.getCycleParamCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 添加区间参数(add CycleParam Info)
	 * @param LinkedHashMap
	 * @return
	 */
	@Override
	public void addCycleParamInfo(LinkedHashMap obj) throws Exception {
		
		this.insert("ar.cycle.addCycleParamInfo", obj) ;
		
		/*if(obj.get("postNos")!=null&&((String [])obj.get("postNos")).length>0){
			
			for(String emptype :((String [])obj.get("postNos")) ){
				Map tempP=new LinkedHashMap();
				tempP.put("EMP_TYPE_CODE", emptype);
				tempP.put("STAT_NO", obj.get("STAT_NO"));
				tempP.put("CPNY_ID", obj.get("interCpnyID"));
				tempP.put("CREATED_BY", obj.get("CREATED_BY"));
				this.insert("ar.cycle.addStatisticEmpTypeCode",tempP);
			}
		}*/
		
	}
	
	/**
	 * 更新信息
	 * @param List
	 * @return
	 */
	@Override
	public void updateCycleParamInfo(LinkedHashMap obj)throws Exception {
		
		this.update("ar.cycle.updateCycleParamInfo", obj);
		
		//this.delete("ar.cycle.deleteStatisticEmpTypeCodeByNo", obj);
		
	/*	if(obj.get("postNos")!=null&&((String [])obj.get("postNos")).length>0){
			
			for(String emptype :((String [])obj.get("postNos")) ){
				Map tempP=new LinkedHashMap();
				tempP.put("EMP_TYPE_CODE", emptype);
				tempP.put("STAT_NO", obj.get("STAT_NO"));
				tempP.put("CPNY_ID", obj.get("interCpnyID"));
				tempP.put("UPDATED_BY", obj.get("UPDATED_BY"));
				this.insert("ar.cycle.addStatisticEmpTypeCode",tempP);
			}
		}*/
		
	}
	
	/**
	 * 删除区间参数(delete CycleParam Info)
	 * @param List
	 * @return
	 */
	@Override
	public void deleteCycleParamInfo(LinkedHashMap obj) throws Exception {
		
		this.delete("ar.cycle.deleteCycleParamInfo", obj) ;
		
		//this.delete("ar.cycle.deleteStatisticEmpTypeCode", obj) ;
		
	}

	@Override
	public Object getCycleParam(Map obj) {
		// TODO Auto-generated method stub
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getCycleParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}

	/**
	 * 检查唯一性(check CycleInfo Unique)
	 * @param LinkedHashMap
	 * @return int
	 */
	@Override
	public int checkCycleInfoUnique(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.checkCycleUnique", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 删除检查(check Cycle For Delete)
	 * @param Map
	 * @return int
	 */
	@Override
	public int checkCycleForDelete(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.checkCycleForDelete", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getEmpTypeCodeList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getEmpTypeCodeList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getEmpTypeCodeListSUPERVISOR(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getEmpTypeCodeListarSUPERVISOR", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getStatisticList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getStatisticList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getKeeperEmpTypeCodeList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getKeeperEmpTypeCodeList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public String getDeptNameByDeptNo(Map obj) {
		
		String result = "";
		
		try {
			
			Object ob = this.queryForObject("ar.cycle.getDeptNameByDeptNo", obj);
			result = ob == null ? "" : ob.toString();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result ;
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getPaSupervisorEmpTypeCodeList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getPaSupervisorEmpTypeCodeList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得员工类组
	 * @param request
	 * @return List
	 */
	@Override
	public List getJobTypeGroupList(Map obj) {
		 List returnList = new ArrayList() ;
			
			try {
				
				returnList = this.queryForList("ar.cycle.getJobTypeGroupList", obj);
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return returnList ;
	}

	@Override
	public List getKeeperJobGroupList(Object obj) {
        List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getKeeperJobGroupList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getPaSupervisorJobTypeGroupCodeList(Map obj) {
		  List returnList = new ArrayList() ;
			
			try {
				
				returnList = this.queryForList("ar.cycle.getPaSupervisorJobTypeGroupCodeList", obj);
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return returnList ;
	}
	/**
	 * 取得所有周期列表(get Cycle List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getStatutoryHolidaysList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getStatutoryHolidaysList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 取得所有法定节假日列表(get Cycle List)
	 * @param Object
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getStatutoryHolidaysList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.cycle.getStatutoryHolidaysLists", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.cycle.getStatutoryHolidaysLists", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 取得法定节假日条数(get Cycle count)
	 * @param List
	 * @return int
	 */
	@Override
	public int getStatutoryHolidaysCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.getStatutoryHolidaysCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
}
