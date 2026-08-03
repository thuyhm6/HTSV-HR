package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusPersonnelDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusPersonnelDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:16:58
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusPersonnelDaoImpl extends SqlMapClientSupport implements
		BonusPersonnelDao {

	/**
	 * 取得所有计算奖金人员信息列表(get Bonus Personnel Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusPersonnelInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getBonusPersonnelList(obj);
		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 取得所有计算奖金人员信息列表(get Bonus Personnel List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusPersonnelList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getBonusPersonnelList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有计算奖金人员信息列表(get Bonus Personnel List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusPersonnelList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.bonusPersonnel.getBonusPersonnelList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.bonusPersonnel.getBonusPersonnelList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有计算奖金信息总数(get Bonus Personnel Cnt)
	 * 
	 * @param List
	 * @return
	 */
	public int getBonusPersonnelCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.bonusPersonnel.getBonusPersonnelCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 插入计算奖金人员信息(add Bonus Personnel Info)
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBonusPersonnelInfo(Object obj) {

		try {
			this.insert("pa.bonusPersonnel.updateBonusPersonnelInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得所有奖金计算人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBonusObjectList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.bonusPersonnel.getBonusObjectList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.bonusPersonnel.getBonusObjectList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 获得所有奖金计算人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBonusObjectList(Object obj) throws Exception {
		List returnList = new ArrayList();

		returnList = this.getBonusObjectList(obj, -1, -1);

		return returnList;
	}
	
	/**
	 * 获得所有奖金计算人员总数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	@Override
	public int getBonusObjectListCnt(Object obj) throws Exception {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.bonusPersonnel.getBonusObjectListCnt",obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:25:38 
	* @version V1.0
	 */
	@Override
	public int updateBonusCalcFlagByPersonId(Object obj) throws Exception {
		try {
			this.update("pa.bonusPersonnel.updateBonusCalcFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getBonusObjectCtrollerInfo(LinkedHashMap paramMap)
			throws Exception {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getBonusObjectList(paramMap) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusObjectInfo(LinkedHashMap paramMap) throws Exception {
		try {
			this.update("pa.bonusPersonnel.updateBonusCalcFlagByPersonId", paramMap) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 删除计算奖金人员信息(delete Bonus Personnel Info)
	 * 
	 * @param List
	 * @return
	 
	@Override
	public int deleteBonusPersonnelInfo(Object obj) {

		try {
			this.delete("pa.bonusPersonnel.deleteBonusPersonnelInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}*/
	
	/**
	 * 检查奖金计算人员是否重复添加(check Add Bonus Personnel Info)
	 * @param obj
	 * @return
	 
	@Override
	public int checkAddBonusPersonnelInfo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"pa.bonusPersonnel.checkAddBonusPersonnelInfo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}*/
}
