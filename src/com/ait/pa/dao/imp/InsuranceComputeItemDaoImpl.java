package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.InsuranceComputeItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class InsuranceComputeItemDaoImpl extends SqlMapClientSupport implements InsuranceComputeItemDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/****
	 * 保险项目和老系统mapping关系修改
	 * @param request
	 * @return
	 */
	public int updateIsItemParamInfo(Object object){
		try {
			this.update("pa.insuranceComputeItem.updateIsItemParamInfo", object);
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 取得所有保险计算项目信息列表(get Insurance Compute Item Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getInsuranceComputeItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getInsuranceComputeItemList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有保险计算项目参数信息列表（get Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getInsuranceComputeItemParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getInsuranceComputeItemParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有保险计算项目信息列表(get Insurance Compute Item List)
	 * @param List
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getInsuranceComputeItemList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有保险计算项目参数信息列表（get Insurance Compute Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getInsuranceComputeItemParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 取得所有保险计算项目信息列表(get Insurance Compute Item List)
	 * @param List
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insuranceComputeItem.getInsuranceComputeItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insuranceComputeItem.getInsuranceComputeItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有保险计算项目参数信息列表（get Insurance Compute Item Param List）
	 * @param parameterObject
	 * @return  List
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insuranceComputeItem.getInsuranceComputeItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insuranceComputeItem.getInsuranceComputeItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有保险计算项目信息总数(get Insurance Compute Item Cnt)
	 * @param List
	 * @return int
	 */
	public int getInsuranceComputeItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceComputeItem.getInsuranceComputeItemCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有保险计算项目参数信息总数（get Insurance Compute Item Param List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 
	public int getInsuranceComputeItemParamListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceComputeItem.getInsuranceComputeItemParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}*/

	/**
	 * 插入保险计算项目信息(add Insurance Compute Item Info)
	 * @param List
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addInsuranceComputeItemInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.insuranceComputeItem.addInsuranceComputeItemInfo", object) ;	
	}
	
	/**
	 * 插入保险计算项目参数信息（add Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int addInsuranceComputeItemParamInfo(Object obj) {
		
		try {
			this.insert("pa.insuranceComputeItem.addInsuranceComputeItemParamInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 对插入保险计算项目信息进行是否重复验证(check Add Insurance Compute Item Info)
	 * @param List
	 * @return int
	 */
	public int checkAddInsuranceComputeItemInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceComputeItem.checkAddInsuranceComputeItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 对插入保险计算项目参数信息进行是否重复验证(check Add Insurance Compute Item Param Info)
	 * @param List
	 * @return int
	 */
	public int checkAddInsuranceComputeItemParamInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceComputeItem.checkAddInsuranceComputeItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 修改保险计算项目信息(update Insurance Compute Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void updateInsuranceComputeItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.insuranceComputeItem.updateInsuranceComputeItemInfo", obj) ;
	}
	/**
	 * 修改保险计算项目参数信息(update Insurance Compute Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public void updateInsuranceComputeItemParamInfo(Object obj) throws Exception{
		
		
		this.update("pa.insuranceComputeItem.updateInsuranceComputeItemParamInfo", obj) ;
	}
	
	/**
	 * 验证删除保险计算项目信息(check Delete Insurance Compute Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteInsuranceComputeItemInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceComputeItem.checkDeleteInsuranceComputeItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 验证删除保险计算项目参数信息(check Delete Insurance Compute Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteInsuranceComputeItemParamInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceComputeItem.checkDeleteInsuranceComputeItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除保险计算项目信息(delete Insurance Compute Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void deleteInsuranceComputeItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("pa.insuranceComputeItem.deleteInsuranceComputeItemInfo", obj) ;
	}
	/**
	 * 删除保险计算项目信息(delete Insurance Compute Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deleteInsuranceComputeItemParamInfo(Object obj) {
		
		try {
			this.delete("pa.insuranceComputeItem.deleteInsuranceComputeItemParamInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改计算顺序(update Insurance Compute Item Info CalOrder)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsuranceComputeItemInfoCalOrder(List list) {
		int returnInt = 0 ;
		
		try {
			 
			this.updateForList("pa.insuranceComputeItem.updateInsuranceComputeItemInfoCalOrder", list);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改CALCU_ORDER ByCalcuOrder(update ICInfo By CalcuOrder)
	 * @param List
	 * @return
	 */
	@Override
	public int updateICInfoByCalcuOrder(Object obj) {
		
		try {
			this.update("pa.insuranceComputeItem.updateICInfoByCalcuOrder", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 修改CALCU_ORDER ByItemNo(update ICInfo By ItemNo)
	 * @param List
	 * @return
	 */
	@Override
	public int updateICInfoByItemNo(Object obj) {
		
		try {
			this.update("pa.insuranceComputeItem.updateICInfoByParamNo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}

	
	
}
