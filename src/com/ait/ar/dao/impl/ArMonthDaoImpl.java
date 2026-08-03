package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArMonthDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArMonthDaoImpl.java
 * @Description:
 * @Create date: 2012-2-11 下午03:10:49
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArMonthDaoImpl extends SqlMapClientSupport implements ArMonthDao {

	/**
	 * 取汇总项目列名(get ArColumns)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArColumns(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.arMonth.getArColumns", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	
	/**
	 * 取汇总项目列名(get ArColumns)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArColumnsYN(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.arMonth.getArColumnsYN", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getArColumnsParam(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.arMonth.getArColumnsParam");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List retrieveMonthlyStatusList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.arMonth.retrieveMonthlyStatusList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArMonthList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();

		returnList = this.getArMonthList(object, -1, -1);

		return returnList;
	}

	/**
	 * 取汇总项数据(get ArMonth List)
	 * @param Object
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArMonthList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.arMonth.getArMonthList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ar.arMonth.getArMonthList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getArMonthEssList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();

		returnList = this.getArMonthEssAllList(object, -1, -1);

		return returnList;
	}
	/**
	 * 取汇总项数据(get ArMonth List) 只为ess个人查看考勤信息服务
	 * @param Object
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArMonthEssAllList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.arMonth.getArMonthEssList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ar.arMonth.getArMonthEssList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public int getArMonthListCnt(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.arMonth.getArMonthListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 修改考勤汇总(update ArMonth Info)
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int updateArMonthInfo(Object object) {

		try {
			if(((List)object) != null){
				
				for(int i=0;i<((List)object).size();i++){
					this.update("ar.arMonth.updateArMonthInfo", ((List)object).get(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 取考勤锁定列表(get MonthlyStatus List)
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(Object parameterObject) {

		List list = null;
		try {

			list = this
					.queryForList("ar.arMonth.getMonthlyStatusList",
							parameterObject);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取得所查月份最后一天
	 * @param Object
	 * @return int
	 * @throws
	 */
	public Object getLastDayByMonth(Object object) {
		Object result = null;
		try {
			result = this.queryForObject("ar.arMonth.getLastDayByMonth",object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
