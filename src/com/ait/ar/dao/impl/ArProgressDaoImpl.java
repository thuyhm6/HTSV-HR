package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArProgressDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArProgressDaoImpl.java
 * @Description:
 * @Create date: 2012-2-12 下午12:05:17
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArProgressDaoImpl extends SqlMapClientSupport implements
		ArProgressDao {

	/**
	 * 查询月考勤锁定(get ArProgress List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArProgressList(Object obj) {
		
		List returnList = new ArrayList();

		returnList = this.getArProgressList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 查询月考勤锁定(get ArProgress List)
	 * 
	 * @param Object
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArProgressList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.arProgress.getArProgressList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ar.arProgress.getArProgressList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得月考勤列表条数(get Cycle count)
	 * @param List
	 * @return int
	 */
	@Override
	public int getArProgressCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arProgress.getArProgressCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 更新考勤锁定(update ArProgress Info)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int updateArProgressInfo(Object obj) {
		try {

			this.update("ar.arProgress.updateArProgressInfo", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
