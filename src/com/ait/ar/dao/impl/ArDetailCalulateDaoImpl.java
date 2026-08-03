package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.ar.dao.ArDetailCalulateDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArDetailCalulateDaoImpl.java
 * @Description:
 * @Create date: 2012-2-7 下午12:01:59
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArDetailCalulateDaoImpl extends SqlMapClientSupport implements
		ArDetailCalulateDao {

	/**
	 * 查询考勤员列表(get ArSupervisor List)
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArSupervisorList(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList("ar.arDetailCalulate.getArSupervisorList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 明细计算(detail Calculate)
	 * @param LinkedHashMap
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String detailCalculate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.arDetailCalulate.detailCalculate", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("empid"));
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	/**
	 * 事后计算(detailShihou Calculate)
	 * @param LinkedHashMap
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String detailShiHouCalculate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.arDetailCalulate.detailShiHouCalculate", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("VAR_OUTSTR"));
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	
	/**
	 * 追溯计算(detailShihou Calculate)
	 * @param LinkedHashMap
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String detailLastMonthCalculate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.arDetailCalulate.detailLastMonthCalculate", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("VAR_OUTSTR"));
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	
	
	/* (non-Javadoc)
	 * @see com.ait.ar.dao.ArDetailCalulateDao#getardetailsendview(java.lang.Object)
	 */
	@Override
	public List getardetailsendview(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList("ar.arDetailCalulate.getardetailsendview", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

}
