package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.AbsenteeismDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * @fileName: AbsenteeismCtroller
 * @Description:旷工申请 
 */
@Repository
public class AbsenteeismDaoImpl extends SqlMapClientSupport implements AbsenteeismDao {

	
	private static String APPLY_TYPE_NO = "300012";

	private static String APPLY_TYPE_NAME = "旷工审批邀请";
	

	/**
	 * 查询旷工申请 信息列表
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAbsenteeismList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAbsenteeismList(obj, -1, -1);
		return returnList;
	}

	/**
	 *  查询旷工申请 信息列表-判断是否分页
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAbsenteeismList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.absenteeism.getLeaveAffirmInfoList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.absenteeism.getLeaveAffirmInfoList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 *  查询旷工申请 信息列表总数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getAbsenteeismListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.absenteeism.getLeaveAffirmInfoListCnt", obj)),Integer.class);
	}
}