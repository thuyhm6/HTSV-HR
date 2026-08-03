package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaPayObjDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaPayObjDaoImpl extends SqlMapClientSupport implements PaPayObjDao {
	
	/**
	 * 取得工资支付对象信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayObjList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.workManagement.getPaPayObjList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.workManagement.getPaPayObjList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得工资支付对象信息总数
	 * @param List
	 * @return
	 */
	public int getPaPayObjCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPaPayObjCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 保存工资支付对象信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addPaPayObjInfo(Object obj)throws Exception {
		
		this.insert("pa.workManagement.addPaPayObjInfo", obj) ;
		
	}
	/**
	 * 取得工资支付对象信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayObjInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaPayObjList(obj,-1,-1) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	/**
	 * 更新工资支付对象信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updatePaPayObjInfo(HttpServletRequest request,LinkedHashMap object)
			throws Exception {
		String[] isChecked = request.getParameterValues("viewCheck");
		for(int i = 0; i < isChecked.length; i++){
			object.put("PERSON_ID", object.get("PERSON_ID" + "_" + isChecked[i]));
			object.put("INCLUDE_TYPE", object.get("INCLUDE_TYPE" + "_" + isChecked[i]));
			//object.put("WAGE_TYPE", object.get("WAGE_TYPE" + "_" + isChecked[i]));
			object.put("PAY_SCHEDULE_NO", object.get("PAY_SCHEDULE_NO" + "_" + isChecked[i]));
			this.update("pa.workManagement.updatePaPayObjInfo", object) ;
		}

		
		
		// TODO Auto-generated method stub
		

	}

	/**
	 * 删除工资支付对象信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doDeletePaPayObjInfo(HttpServletRequest request,LinkedHashMap object)throws Exception {
		
		//this.update("pa.workManagement.doDeletePaPayObjInfo", obj) ;
		String[] isChecked = request.getParameterValues("viewCheck");
		for(int i = 0; i < isChecked.length; i++){
			object.put("PERSON_ID", object.get("PERSON_ID" + "_" + isChecked[i]));
			//object.put("INCLUDE_TYPE", object.get("INCLUDE_TYPE" + "_" + isChecked[i]));
			//object.put("WAGE_TYPE", object.get("WAGE_TYPE" + "_" + isChecked[i]));
			object.put("PAY_SCHEDULE_NO", object.get("PAY_SCHEDULE_NO" + "_" + isChecked[i]));
			this.update("pa.workManagement.doDeletePaPayObjInfo", object) ;
		}
		
	}
	
	/**
	 * 获得人员列表(get EmpCalendar List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getEmpListForPop(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.workManagement.getEmpListForPop", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@Override
	public List getEmpSHListForPop(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.workManagement.getEmpSHListForPop", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpListForPopCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getEmpListForPopCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
}
