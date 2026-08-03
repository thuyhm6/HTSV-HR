package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArEatMealCountDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class ArEatMealCountDaoImpl extends SqlMapClientSupport implements ArEatMealCountDao {
	/**
	 * 将初始化时间按天分解并封装到LIST(decompose apply date for list)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getInitDateList(Object obj) throws Exception {
		try {
			return this.queryForList("ar.eatMealCount.getInitDateList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountC12(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("ar.eatMealCount.getEatMealCountC12", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查看某天某员工的食堂可刷卡数量
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getEmpMealCountByDateList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("ar.eatMealCount.getEmpMealCountByDateList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查看某天某员工的食堂可刷卡数量
	 * @param Object
	 * @return int
	 * @throws
	 */
	public int getEmpMealCountByDateCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.eatMealCount.getEmpMealCountByDateCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 初始化员工吃饭次数--C12
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void initMealCount(Object object) throws Exception {
		this.deleteForList("ar.eatMealCount.deleteEmpEatMealCountByDate", (List)object) ;
		this.insertForList("ar.eatMealCount.insertEmpEatMealCountByDate", (List)object) ;
	}
	
	/**
	 * 查看指定日期里员工的食堂就餐打卡信息(get eat meal count List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEatMealCountList(object, -1, -1);
		return returnList;
	}

     /**
	 * 查看指定日期里员工的食堂就餐打卡信息(get eat meal count List),分页显示
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.eatMealCount.getEatMealCountList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.eatMealCount.getEatMealCountList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 查看指定日期里员工的食堂就餐打卡信息(get eat meal count cnt),数量
	 * @param Object
	 * @return int
	 * @throws
	 */
	public int getEatMealCountCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.eatMealCount.getEatMealCountCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 查找指定日期里可添加食堂就餐打卡信息的员工名单(get the person info List for add eat meal count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountPersonList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEatMealCountPersonList(object, -1, -1);
		return returnList;
	}

     /**
	 * 查找指定日期里可添加食堂就餐打卡信息的员工名单(get the person info List for add eat meal count),分页显示
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountPersonList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.eatMealCount.getEatMealCountPersonList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.eatMealCount.getEatMealCountPersonList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 查找指定日期里可添加食堂就餐打卡信息的员工名单(get the person info List for add eat meal count),数量
	 * @param Object
	 * @return int
	 * @throws
	 */
	public int getEatMealCountPersonListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.eatMealCount.getEatMealCountPersonListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 删除食堂刷卡次数信息--C12
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteArEatCountInfo(Object object) throws Exception {
		this.deleteForList("ar.eatMealCount.deleteEmpEatMealCountByDataNo", (List)object) ;
	}
	
	/**
	 * 修改食堂刷卡次数信息--C12
	 * @param Object
	 * @return
	 */
	public void updateArEatCountInfo(Object object) throws Exception {
		this.update("ar.eatMealCount.updateEmpEatMealCountByDataNo", object) ;
	}
}
