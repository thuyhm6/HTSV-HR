package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.WageApplicationDao;
import com.ait.web.util.SqlMapClientSupport;

@SuppressWarnings("unchecked")
@Repository
public class WageApplicationDaoImpl extends SqlMapClientSupport implements
WageApplicationDao {

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List getListByEmp(Map paramMap, Integer currentPage, Integer pageSize) {
		List list = new ArrayList();
		try {
			if(currentPage > -1 && pageSize > -1){
				list = this.queryForList("ess.WageApp.listByEmp",paramMap, currentPage, pageSize);
			}
			else{
				list = this.queryForList("ess.WageApp.listByEmp",paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int getListCnt(Map paramMap) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.listByEmpCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public int updateBatchSubmitApp(Map paramMap) {
		try {
			this.update("ess.WageApp.updateBatchSubmitApp", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List getApplicationList(Map paramMap, Integer currentPage, Integer pageSize) {
		List list = new ArrayList();
		try {
			if(currentPage > -1 && pageSize > -1){
				list = this.queryForList("ess.WageApp.getApplicationList",paramMap, currentPage, pageSize);
			}
			else{
				list = this.queryForList("ess.WageApp.getApplicationList",paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	public int getApplicationListCnt(Map paramMap){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getApplicationListCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	@Override
	public List getProveAppList(Map paramMap){
		List list = new ArrayList();
		try {
			list = this.queryForList("ess.WageApp.getProveAppList",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	public List getProveCheckAppList(Map paramMap,Integer currentPage,Integer pageSize){
		List list = new ArrayList();
		try {
			if(currentPage > -1 && pageSize > -1){
				list = this.queryForList("ess.WageApp.getProveCheckAppList",paramMap, currentPage, pageSize);
			}
			else{
				list = this.queryForList("ess.WageApp.getProveCheckAppList",paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int getProveCheckAppListCnt(Map paramMap) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getProveCheckAppListCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public int getCancleApplicationState(Map paramMap) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getCancleApplicationState", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public int deleteOtApplication(Map paramMap) {
		try {
			this.delete("ess.WageApp.deleteOtApplication2", paramMap);//删除审批人
			this.delete("ess.WageApp.deleteOtApplication3", paramMap);//删除标题
			this.delete("ess.WageApp.deleteOtApplication4", paramMap);//删除明细
			this.delete("ess.WageApp.deleteOtApplication1", paramMap);//删除check
			this.delete("ess.WageApp.deleteOtApplication5", paramMap);//删除PA_PARAM_DATA数据
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@Override
	public int getBackPbOtApplication(Map paramMap) {
		try {
			this.queryForObject("ess.WageApp.getBackPbOtApplication", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public List getPbWageList(Map paramMap, int pageNum, int numPerPage){
		List list = new ArrayList();
		try {
			if(pageNum > -1 && numPerPage > -1){
				list = this.queryForList("ess.WageApp.getPbWageList",paramMap, pageNum, numPerPage);
			}
			else{
				list = this.queryForList("ess.WageApp.getPbWageList",paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	public int getPbWageCnt(Map paramMap){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getPbWageCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public List getWageApplicationTempList(LinkedHashMap paramMap,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.WageApp.getWageApplicationTempList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ess.WageApp.getWageApplicationTempList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	public List getWageApplicationTempList(LinkedHashMap paramMap){
		return this.getWageApplicationTempList(paramMap, -1, -1);
	}

	public int getWageApplicationTempCnt(Map<String, Object> paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.WageApp.getWageApplicationTempCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	public int getWageApplicationTempErrCnt(Map<String, Object> paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.WageApp.getWageApplicationTempErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 费用数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	public String importApplicationExcelTempExcel(Map<String, Object> paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("ess.WageApp.importApplicationExcelTempExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}

	@Override
	public int getWageApplicationCnt(Map paramMap) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getWageApplicationCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public List getWageApplicationList(Map paramMap, int currentPage, int pageSize) {
		List list = new ArrayList();
		try {
			if(currentPage > -1 && pageSize > -1){
				list = this.queryForList("ess.WageApp.getWageApplicationList",paramMap, currentPage, pageSize);
			}
			else{
				list = this.queryForList("ess.WageApp.getWageApplicationList",paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int checkApplyApplicationState(Map paramMap) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.checkApplyApplicationState", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public List getApplicationByNoApplyNo(Map paramMap, int currentPage, int pageSize) {
		List list = new ArrayList();
		try {
			if(currentPage > -1 && pageSize > -1){
				list = this.queryForList("ess.WageApp.getApplicationByNoApplyNo",paramMap, currentPage, pageSize);
			}
			else{
				list = this.queryForList("ess.WageApp.getApplicationByNoApplyNo",paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int getApplicationByNoApplyNoCnt(Map paramMap) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getApplicationByNoApplyNoCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	@Override
	public int saveWageAppFile(Map paramMap){
		int returnInt = 0;
		try {
			this.insert("ess.WageApp.saveWageAppFile", paramMap);
			returnInt = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}
	@Override
	public List getAppliFileList(Map paramMap){
		List list = new ArrayList();
		try {
			list = this.queryForList("ess.WageApp.getAppliFileList",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int deleteWageAppFile(Map paramMap){
		try {
			this.delete("ess.WageApp.deleteWageAppFile", paramMap);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public void updateWageAppFile(Map paramMap){
		try {
			this.update("ess.WageApp.updateWageAppFile", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 费用履历查看  分页
	 * @param object
	 * @return
	 */
	public List getWageCheckList(Map paramMap, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.WageApp.getWageCheckList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ess.WageApp.getWageCheckList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 费用履历查看  不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWageCheckList(Map paramMap){
		return this.getWageCheckList(paramMap, -1, -1);
	}
	
	/**
	 *费用履历查看  数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getWageCheckCnt(Map paramMap){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.WageApp.getWageCheckCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
}