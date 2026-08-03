package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.is.dao.BaseManagementForSearchDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class BaseManagementForSearchDaoImpl extends SqlMapClientSupport implements BaseManagementForSearchDao{
	@Autowired
	private SyLanguageDao syLanguageDao;
   /**
    * 
    * 点击记录出现可编辑的文本框
    */
	@Override
	public int backInsuranceBaseNumUpdateBz() throws SQLException {
		int result;
		try {
			   result=(Integer) this.update("is.insuranceNumber.backInstanceBaseNumUpdate");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumCheckList information Exception. ", e);
		}
		return result;
	}
     /**
      * 
      * 获取checklist
      * 
      */
	@Override
	public List getInsuranceBaseNumCheckListBz() throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.insuranceNumber.getInstanceBaseNumCheckList");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getInstanceBaseNumCheckList information Exception. ", e);
		}
		return result;
	}
     /**
      * 
      * 获取显示的数据showList
      * 
      */
	@SuppressWarnings("unchecked")
	public List getInsuranceBaseNumListBz(Object obj) throws SQLException {
		 List returnList = new ArrayList() ;
			
			returnList = this.getInsuranceBaseNumListBz(obj, -1, -1) ;
			
			return returnList ;
		
	}
	/**
	 * 带分页的查询
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceBaseNumListBz(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("is.insuranceNumber.getInstanceNumList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("is.insuranceNumber.getInstanceNumList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 
	 * 数据的生成和原数据的清空
	 */
	@Override
	public int createDataInstancenBaseBz(Object object) throws SQLException {
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSerious
			this.delete("is.insuranceNumber.deleteDataFromInstanceBase");
			this.insert("is.insuranceNumber.copyDataInstanceBase", object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			return 0;
			//throw new GlRuntimeException("createDataToPaBenBase information Exception. ", e);
		}
		return 1;
	}
	/**
	 * 查找总记录数
	 */
	@Override
	public int getInsuranceBaseCnt(Object obj) {
        int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("is.insuranceNumber.getInsuranceBaseCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 数据导出
	 */
	@SuppressWarnings("unchecked")
	public List getNOInsBaseNumList(Object obj) throws SQLException {
		    List returnList = new ArrayList() ;
			
		    returnList = this.getInsuranceBaseNumListBz(obj, -1, -1); 
			
			return returnList ;
	}
	/**
	 * 保险基数的删除
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public void deleteInstanceaseManagement(Object obj) throws SQLException {
			
		 this.delete("is.insuranceNumber.deleteInstanceaseManagement", obj) ;
	}
	@Override
	public void allowInstanceBaseNumUpdate(Object obj) throws SQLException {
		this.update("is.insuranceNumber.allowInstanceBaseNumUpdate", obj);
		
	}
	@Override
	public void editInstanceBaseManagement(Object obj) throws SQLException {
		this.update("is.insuranceNumber.editInstanceBaseManagement", obj);
	}
	@Override
	public List getupdateInstanceBaseManagement(Object obj) throws SQLException {
		return this.queryForList("is.insuranceNumber.getupdateInstanceBaseManagement" ,obj);
	}
}
