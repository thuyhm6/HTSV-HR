package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaEmpAccountDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaEmpAccountDaoImpl extends SqlMapClientSupport implements PaEmpAccountDao {
	
	/**
	 * 取得工资账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.workManagement.getPaEmpAccountList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.workManagement.getPaEmpAccountList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得工资账户信息总数
	 * @param List
	 * @return
	 */
	public int getPaEmpAccountListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPaEmpAccountListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public List getPaEmpAccountTempList(Object obj) throws Exception{
		return  this.queryForList("pa.workManagement.getPaEmpAccountTempList", obj);
	}
	
	@Override
	public List getPaEmpAccountTempList1(Object obj) throws Exception{
		return  this.queryForList("pa.workManagement.getTrainList", obj);
	}
	
	@Override
	public int getPaEmpAccountTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPaEmpAccountTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getPaEmpAccountTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPaEmpAccountTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 保存工资账户信息(addPaEmpAccountInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addPaEmpAccountInfo(Object obj)throws Exception {
		
		this.insert("pa.workManagement.addPaEmpAccountInfo", obj) ;
		
	}
	/**
	 * 取得工资账户信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaEmpAccountInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaEmpAccountList(obj,-1,-1) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	/**
	 * 更新工资账户信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updatePaEmpAccountInfo(Object obj)throws Exception {
		
		this.update("pa.workManagement.updatePaEmpAccountInfo", obj) ;
		
	}
	/**
	 * 删除工资账户信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doDeletePaEmpAccountInfo(Object obj)throws Exception {
		
		this.update("pa.workManagement.doDeletePaEmpAccountInfo", obj) ;
		
	}
}
