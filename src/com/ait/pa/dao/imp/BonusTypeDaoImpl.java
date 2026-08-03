package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusTypeDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusTypeDaoImpl.java
 * @Description:
 * @Create date: 2012-1-13 下午05:11:59
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusTypeDaoImpl extends SqlMapClientSupport implements BonusTypeDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 取得奖金类型序号（Get a bonus type number）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getBonusTypeNo() {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusType.getBonusTypeNo", new LinkedHashMap())), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得奖金类型信息（Get a bonus type information）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusTypeInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getBonusTypeList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有奖金类型信息列表（Obtain all types of bonus information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusTypeList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getBonusTypeList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金类型信息列表（Obtain all types of bonus information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusTypeList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.bonusType.getBonusTypeList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.bonusType.getBonusTypeList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金类型信息总数（Obtain all types of bonus information number）
	 * @param List
	 * @return
	 */
	public int getBonusTypeCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusType.getBonusTypeCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入奖金类型信息（Insert the bonus type information）
	 * @param List
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public void addBonusTypeInfo(Object obj) throws Exception{
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("pa.bonusType.addBonusTypeInfo", object) ;
	}
	
	/**
	 * 修改奖金类型信息（Modified bonus type information）
	 * @param List
	 * @return
	 */
	@Override
	public void updateBonusTypeInfo(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.bonusType.updateBonusTypeInfo", obj) ;
		
	}
	
	/**
	 * 删除奖金类型信息（Delete bonus type information）
	 * @param List
	 * @return
	 */
	@Override
	public void deleteBonusTypeInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		
		this.delete("pa.bonusType.deleteBonusTypeInfo", obj) ;
		
	}
	
	/**
	 * 验证删除奖金类型信息（Validation of delete bonus type information）
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteBonusTypeInfo(Object obj){
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusType.checkDeleteBonusTypeInfo", obj), "0"), Integer.class) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnInt = 1;
		}
		return returnInt ;
	}
	
	/**
	 * 验证是否存在同一个类型的奖金类型记录（Verify the existence of the same type of bonus type record）
	 * @param List
	 * @return
	 */
	@Override
	public int checkAddBonusTypeByTypeId(Object obj) throws SQLException {
		int returnInt = 0 ;
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusType.checkAddBonusTypeByTypeId", obj), "0"), Integer.class) ;
		
		return returnInt ;
	}
	
	/**
	 * 取得奖金类型参数信息（Get a bonus type parameter information）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusTypeParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getBonusTypeParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有奖金类型参数信息列表（All bonus type parameter list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusTypeParamList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getBonusTypeParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金类型参数信息列表（All bonus type parameter list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusTypeParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.bonusType.getBonusTypeParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.bonusType.getBonusTypeParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金类型参数信息总数（All bonus type parameter information number）
	 * @param List
	 * @return
	 */
	public int getBonusTypeParamCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusType.getBonusTypeParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入奖金类型参数信息（Insert the bonus type parameter information）
	 * @param List
	 * @return
	 */
	@Override
	public void addBonusTypeParamInfo(Object obj) throws Exception{ 
		
		this.insert("pa.bonusType.addBonusTypeParamInfo", obj) ;
	}
	
	/**
	 * 修改奖金类型参数信息（Modified bonus type parameter information）
	 * @param List
	 * @return
	 */
	@Override
	public void updateBonusTypeParamInfo(Object obj) throws Exception{
		
		this.update("pa.bonusType.updateBonusTypeParamInfo", obj) ;	
	}
	
	/**
	 * 删除奖金类型参数信息（Delete bonus type parameter information）
	 * @param List
	 * @return
	 */
	@Override
	public void deleteBonusTypeParamInfo(Object obj) throws Exception{
		
		this.delete("pa.bonusType.deleteBonusTypeParamInfo", obj) ;
	}
	
	/**
	 * 验证删除奖金类型参数信息（Validation of delete bonus type parameter information）
	 * @param List
	 * @return
	 */
	@Override
	public int checkAddBonusTypeParamInfo(Object obj) throws SQLException {
		int returnInt = 0 ;
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusType.checkAddBonusTypeParamInfo", obj), "0"), Integer.class) ;
		
		return returnInt ;
	}
}
