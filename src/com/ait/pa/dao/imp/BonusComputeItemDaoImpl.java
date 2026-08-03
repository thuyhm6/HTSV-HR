package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusComputeItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusComputeItemDaoImpl.java
 * @Description:
 * @Create date: 2012-1-13 下午05:10:57
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusComputeItemDaoImpl extends SqlMapClientSupport implements BonusComputeItemDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	/**
	 * 取得所有奖金计算项目信息列表（All bonus calculation project information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusComputeItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getBonusComputeItemNoParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有奖金计算项目信息列表（All bonus calculation project information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getBonusComputeItemList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金计算项目信息列表（All bonus calculation project information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.bonusComputeItem.getBonusComputeItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.bonusComputeItem.getBonusComputeItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金计算项目信息总数（All bonus calculation of total project information）
	 * @param List
	 * @return
	 */
	public int getBonusComputeItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusComputeItem.getBonusComputeItemCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入奖金计算项目信息（Insert the bonus calculation project information）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addBonusComputeItemInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);  
		this.insert("pa.bonusComputeItem.addBonusComputeItemInfo", object) ;	
	}
	
	/**
	 * 对插入奖金计算项目信息进行是否重复验证（To insert the bonus calculation project information whether repeated verification）
	 * @param List
	 * @return
	 */
	public int checkAddBonusComputeItemInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusComputeItem.checkAddBonusComputeItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改奖金计算项目信息（Modification of calculating bonus item information）
	 * @param List
	 * @return
	 */
	@Override
	public void updateBonusComputeItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.bonusComputeItem.updateBonusComputeItemInfo", obj) ;
	}
	
	/**
	 * 验证删除奖金计算项目信息（Validation of delete bonus calculation project information）
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteBonusComputeItemInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusComputeItem.checkDeleteBonusComputeItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除奖金计算项目信息（Delete bonus calculation project information）
	 * @param List
	 * @return
	 */
	@Override
	public void deleteBonusComputeItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("pa.bonusComputeItem.deleteBonusComputeItemInfo", obj) ;
		
	}
	
	/**
	 * 取得所有奖金计算项目信息列表（All bonus calculation project information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemNoParamList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getBonusComputeItemNoParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金计算项目信息列表（All bonus calculation project information list）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemNoParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.bonusComputeItem.getBonusComputeItemNoParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.bonusComputeItem.getBonusComputeItemNoParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有奖金计算项目信息总数（All bonus calculation of total project information）
	 * @param List
	 * @return
	 */
	public int getBonusComputeItemNoParamCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusComputeItem.getBonusComputeItemNoParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
}
