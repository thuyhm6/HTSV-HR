package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaPayObjStdDao;
import com.ait.pa.dao.PaWorkFlowDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaWorkFlowDaoImpl extends SqlMapClientSupport implements PaWorkFlowDao {
	
	
	/**
	 * 取得工资工作流程信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaWorkFlowInfoByScheduleNo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		try {
			returnObj = (LinkedHashMap)this
					.queryForObject(
							"pa.workManagement.getPaWorkFlowInfoByScheduleNo",
							obj) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return returnObj ;
	}
	/**
	 * 工资流程执行
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String execPaWorkFlow(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("pa.workManagement.execPaWorkFlow", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}
	/**
	 * 取得工资支付对象总数
	 * @param List
	 * @return
	 */
	public int getPayObjNumByScheduleNo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPayObjNumByScheduleNo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 取得工资流程操作记录列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaWorkFlowOperationRecordList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.workManagement.getPaWorkFlowOperationRecordList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.workManagement.getPaWorkFlowOperationRecordList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得工资流程操作记录总数
	 * @param List
	 * @return
	 */
	public int getPaWorkFlowOperationRecordListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPaWorkFlowOperationRecordListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
}
