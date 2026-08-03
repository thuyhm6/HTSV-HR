package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaProgressDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaProgressDaoImpl extends SqlMapClientSupport implements PaProgressDao {
	
	/**
	 * 取得工资锁定信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaProgressList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaProgressList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 取得所有人员账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaProgressList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.progress.getPaProgressListNew", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.progress.getPaProgressListNew", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有人员账户信息总数
	 * @param List
	 * @return
	 */
	public int getPaProgressCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.progress.getPaProgressCntNew", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	
	
	/**
	 * 取得预提工资锁定信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPawithholdingProgressList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPawithholdingProgressList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 取得预提工资信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPawithholdingProgressList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.progress.getPawithholdingProgressList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.progress.getPawithholdingProgressList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得预提工资信息总数
	 * @param List
	 * @return
	 */
	public int getPawithholdingProgressCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.progress.getPawithholdingProgressListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	
	
	/**
	 * 更新工资锁定信息
	 * @param List
	 * @return
	 */
	public int updatePaProgressInfo(Object obj) {
		try {
			this.update("pa.progress.updatePaProgressInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 根据登陆者的薪资权限获取改登陆者的部门区分权限
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDeptDistinguishList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.progress.getDeptDistinguishList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得门店工资锁定信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaProgressByDeptList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaProgressList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有门店人员账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaProgressByDeptList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.progress.getPaProgressByDeptList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.progress.getPaProgressByDeptList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有门店人员账户信息总数
	 * @param List
	 * @return
	 */
	public int getPaProgressByDeptCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.progress.getPaProgressByDeptCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 更新门店工资锁定信息
	 * @param List
	 * @return
	 */
	public int updatePaProgressByDept(Object obj) {
		try {
			this.update("pa.progress.updatePaProgressByDept", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 删除对应门店的韩方薪资锁定信息
	 * @param List
	 * @return
	 */
	public int deletePaProgressByDept(Object obj) {
		try {
			this.update("pa.progress.deletePaProgressByDept", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 取得所有门店人员账户信息总数
	 * @param List
	 * @return
	 */
	public int checkPaProgressByDeptCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.progress.checkPaProgressByDeptCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 按月份复制对应门店薪资锁定信息到下个月份
	 * @param List
	 * @return
	 */
	public int copyToNextMonthPaProgressByDept(Object obj) {
		try {
			this.insert("pa.progress.copyToNextMonthPaProgressByDept", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}

	@Override
	public List getSalaryLockDatePa(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.progress.getSalaryLockDatePa", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	
		
		return returnList ;
	}

	@Override
	public List getSalaryLockStatNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.progress.getSalaryLockStatNo", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return returnList ;
	}
}
