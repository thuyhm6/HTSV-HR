package com.ait.inct.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.AdminBean;
import com.ait.inct.dao.SalesmanIncentiveDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 
 * @fileName SalesmanIncentiveDaoImpl.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
@Repository
public class SalesmanIncentiveDaoImpl extends SqlMapClientSupport implements SalesmanIncentiveDao {
	/**
	 * 营业员提成
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getIncentiveCalcList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncCalculateList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncCalculateList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * 营业员提成
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getIncentiveCalcList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getIncentiveCalcList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 营业员提成
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getIncentiveCalcListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncCalculateListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 按季度取提成工资关帐标志
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getSalesInctCalcClosFlagByQuarter(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesInctCalcClosFlagByQuarter", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 按季度取预提工资关帐标志
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getSalesInctAccrualCalcClosFlagByQuarter(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesInctAccrualCalcClosFlagByQuarter", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 按月分取提成工资关帐标志
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getSalesInctCalcClosFlagByMonth(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesInctCalcClosFlagByMonth", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 按月分取预提工资关帐标志
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getSalesInctAccrualCalcClosFlagByMonth(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesInctAccrualCalcClosFlagByMonth", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 营业员提成计算执行
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public String callSalesmanIncentiveCalc(Map paramMap) throws Exception {
		String result="";
		this.insert("inct.salesman.callSalesmanIncentiveCalc", paramMap);	
		result =  ObjectUtils.toString(paramMap.get("MESSAGE")) ;
		return result;
	}
	
	/**
	 * 营业员提成  读取变动工资执行
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public String callSalesmanVariablePayRead(Map paramMap) throws Exception {
		String result="";
		this.insert("inct.salesman.callSalesmanVariablePayRead", paramMap);	
		result =  ObjectUtils.toString(paramMap.get("MESSAGE")) ;
		return result;
	}
	
	/**
	 * 营业员提成  excel导入
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncCalculateImportList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncCalculateImportList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncCalculateImportList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getSalesIncCalculateImportList(Object object) {
		List returnList = new ArrayList() ;		
		returnList = this.getSalesIncCalculateImportList(object, -1, -1) ;		
		return returnList ;
	}
	@Override
	public int getSalesIncCalculateImportListCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncCalculateImportListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getSalesIncCalculateImportErrCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncCalculateImportErrCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public String callSalesmanIncentiveCalcImport(Map paramMap)
			throws Exception {
		String result="";
		this.insert("inct.salesman.callSalesmanIncentiveCalcImport", paramMap);	
		result =  ObjectUtils.toString(paramMap.get("MESSAGE")) ;
		return result;
	}
	/**
	 * 营业员提成  社员别提成 查询， 修改
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getSalesIncentiveCalcItem(Object object) {
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getIncentiveCalcByItem", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}
	
	@Override
	public void updateSalesIncAdjustByEmp(Object object) throws Exception {
		this.update("inct.salesman.updateSalesIncAdjustByEmp",object);
	}
	@Override
	public int getIncCalcAdjuMstReqId(Object object) throws Exception {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getIncCalcAdjuMstReqId", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public void insertIncCalcAdjuMst(Object object) throws Exception {
		this.update("inct.salesman.insertIncCalcAdjuMst",object);
	}
	@Override
	public void insertIncCalcAdjuDtl(Object object) throws Exception {
		this.update("inct.salesman.insertIncCalcAdjuDtl",object);
	}
	@Override
	public void updateSalesIncCalculateByEmp(Object object) throws Exception {
		this.update("inct.salesman.updateSalesIncCalculateByEmp",object);
	}
	/*营业员提成调整*/
	@Override
	public List getIncentiveCalcAdjuList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncCalcAdjuList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncCalcAdjuList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getIncentiveCalcAdjuList(Object object) {
		List returnList = new ArrayList() ;		
		returnList = this.getIncentiveCalcList(object, -1, -1) ;		
		return returnList ;
	}
	@Override
	public int getIncentiveCalcAdjuListCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncCalcAdjuListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 添加决裁者
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@Override
	public void insertAffirmor(Object object)  throws Exception{
		this.insert("inct.salesman.insertAffirmor", object) ;
	}
	
	/**
	 * 修改决裁状态
	 * 
	 * @author PengHaixia
	 * @date 2014-8-20
	 * @version V1.0
	 */
	public void updateEssAffirm(Object object) throws Exception{
		this.update("inct.salesman.updateEssAffirm", object) ;
	}
	
	/**
	 * 删除决裁者
	 * 
	 * @author PengHaixia
	 * @date 2014-8-20
	 * @version V1.0
	 */
	@Override
	public void deleteAffirmor(Object object)  throws Exception{
		this.delete("inct.salesman.deleteAffirmor", object) ;
	}
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @author PENGHAIXIA
	 * @date 2014-8-20
	 * @version V1.0
	 */
	public void updateCheckFlagByEssAffirmNo(Object object)  throws Exception{
		this.update("inct.salesman.updateCheckFlagByEssAffirmNo", object) ;
	}
	
	/**
	 * 决裁营业员提成调整 修改审批线状态
	 * 
	 * @author penghaixia
	 * @date 2014-8-20
	 * @version V1.0
	 */
	public void affirmSalesmanInctCalcAdju(Object object)  throws Exception{
		this.insert("inct.salesman.affirmSalesmanInctCalcAdju", object) ;
	}
	
	/**
	 * 审批
	 * @param obj
	 * @return 
	 */
	@Override
	public void approveSalesmanInctCalcAdju(Object object) throws Exception {
		this.insert("inct.salesman.approveSalesmanInctCalcAdju", object);
		
	}
	
	/**
	 * 获取决裁情况
	 * 
	 * @author penghaixia
	 * @date 2014-8-19
	 * @version V1.0
	 */
	public List getAffirmorList(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("inct.salesman.getAffirmorList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	/**
	 * 获取CHECK人情况
	 * 
	 * @author penghaixia
	 * @date 2014-8-19
	 * @version V1.0
	 */
	public List getCheckList(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("inct.salesman.getCheckList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	@Override
	public List getIncentiveCalcAdjuDtlList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncCalcAdjuDtlList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncCalcAdjuDtlList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getIncentiveCalcAdjuDtlList(Object object) {
		return this.getIncentiveCalcList(object, -1, -1) ;		
	}
	@Override
	public int getIncentiveCalcAdjuDtlListCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncCalcAdjuDtlListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 营业员提成  单个申请别提成 查询
	 * @param obj
	 * @return List
	 * @Create date: 2014.08.10
	 */
	@Override
	public Map getIncentiveCalcAdjuListByReqId(Object object) {
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getSalesIncCalcAdjuList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}
}
