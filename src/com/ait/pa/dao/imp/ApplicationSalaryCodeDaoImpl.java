package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.ApplicationSalaryCodeDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ApplicationSalaryCodeDaoImpl extends SqlMapClientSupport implements ApplicationSalaryCodeDao{
	
	@Override
	public List getApplicationSalaryCodeList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getApplicationSalaryCodeList(object, -1, -1);
		return returnList;
	}

	@Override
	public List getApplicationSalaryCodeList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.salarycode.getApplicationSalaryCodeList",object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.salarycode.getApplicationSalaryCodeList",object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getApplicationSalaryCodeCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.salarycode.getApplicationSalaryCodeCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	@Override
	public List findSalaryNameByItemNo(String item_no) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salarycode.findSalaryNameByItemNo",item_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addApplicationSalaryInfo(Object object)throws Exception{
		this.insert("pa.salarycode.addApplicationSalaryInfo", object);
	}
	/**
	 * 检查该人员是否有相应的记录  如果有则不能添加  请修改
	 * @param object
	 * @return
	 */
	
	public int checkApplicationSalaryInfo(Object object){
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.checkApplicationSalaryInfo", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteApplicationSalaryInfo(Object object) throws Exception{
		this.delete("pa.salarycode.deleteApplicationSalaryInfo", object);
	}
	
	/**
	 * 修改人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateApplicationSalaryInfo(Object object)throws Exception{
		this.update("pa.salarycode.updateApplicationSalaryInfo", object) ;
	}
	
	/**
	 * 取得人员和工资匹配的详细信息
	 * @param Object
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Map getApplicationSalaryInfo(Object object) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getApplicationSalaryCodeList(object);
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}
	
	/***
	 * 添加页面的代码表
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemApplicationList(Object object){
		List returnList = new ArrayList() ;
		try {
		   returnList = this.queryForList("pa.salarycode.getPaInputItemApplicationList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}
