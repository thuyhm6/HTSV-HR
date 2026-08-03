package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaPayObjStdDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaPayObjStdDaoImpl extends SqlMapClientSupport implements PaPayObjStdDao {
	
	/**
	 * 取得工资对象基准信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayObjStdList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.workManagement.getPayObjStdList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.workManagement.getPayObjStdList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 取得工资对象基准信息总数
	 * @param List
	 * @return
	 */
	public int getPayObjStdCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPayObjStdCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 保存工资对象基准信息(addPayObjStdInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addPayObjStdInfo(Object obj)throws Exception {
		
		this.insert("pa.workManagement.addPayObjStdInfo", obj) ;
		
	}
	/**
	 * 取得工资对象基准信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPayObjStdInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPayObjStdList(obj,-1,-1) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	/**
	 * 更新工资对象基准信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updatePayObjStdInfo(Object obj)throws Exception {
		
		this.update("pa.workManagement.updatePayObjStdInfo", obj) ;
		
	}
	/**
	 * 删除工资对象基准信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doDeletePayObjStdInfo(Object obj)throws Exception {
		
		this.update("pa.workManagement.doDeletePayObjStdInfo", obj) ;
		
	}
}
