package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusInputItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemDaoImpl.java
 * @Description:
 * @Create date: 2012-1-9 上午10:49:03
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusInputItemDaoImpl extends SqlMapClientSupport implements
		BonusInputItemDao {
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 添加奖金输入项目信息（Added bonus enter project information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void  addPaBonusInputItemInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.bonusInputItem.addPaBonusInputItemInfo", object);
	}

	/**
	 * 检查添加奖金输入项目信息（Check the project information added bonus input）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkAddPaBonusInputItemInfo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"pa.bonusInputItem.checkAddPaBonusInputItemInfo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 检查奖金输入项目参数个数（Check the bonus enter the item number of parameters）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkCountPaBonusInputItemByParamItemNo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"pa.bonusInputItem.checkCountPaBonusInputItemByParamItemNo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 删除奖金输入项目（get the bonus items to delete）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deletePaBonusInputItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);                         
		this.delete("pa.bonusInputItem.deletePaBonusInputItemInfo", obj);
	}

	/**
	 * 获取奖金输入项目个数（get the item number to get bonus）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPaBonusInputItemCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.bonusInputItem.getPaBonusInputItemCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 获取奖金输入项目信息（get the project information to get bonus）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBonusInputItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getPaBonusInputItemList(obj);
		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 获取奖金输入项目集合（get the bonus items for collection）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBonusInputItemList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.bonusInputItem.getPaBonusInputItemList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.bonusInputItem.getPaBonusInputItemList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改奖金输入项目信息（Enter the project information to modify prize）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updatePaBonusInputItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.bonusInputItem.updatePaBonusInputItemInfo", obj);
	}

	/**
	 * 获取奖金输入项目集合（get the bonus items for collection）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBonusInputItemList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPaBonusInputItemList(obj, -1, -1);

		return returnList;
	}
}
