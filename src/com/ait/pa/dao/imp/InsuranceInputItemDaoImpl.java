package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceInputItemDaoImpl.java
 * @Description:
 * @Create date: 2012-1-16 下午06:50:40
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
@SuppressWarnings("unchecked")
public class InsuranceInputItemDaoImpl extends SqlMapClientSupport implements InsuranceInputItemDao {
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 取得所有保险输入项目信息列表(get Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	public Object getInsuranceInputItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getInsuranceInputItemList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 获取保险输入项目参数信息（get Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getInsuranceInputItemParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getInsuranceInputItemParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有保险输入项目信息列表(get Insurance Input Item List)
	 * @param List
	 * @return
	 */
	public List getInsuranceInputItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getInsuranceInputItemList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getInsuranceInputItemParamList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getInsuranceInputItemParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getIsParamDataList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getIsParamDataList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getIsParamDataIsCpnyIdList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getIsParamDataIsCpnyIdList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getIsParamDataIsDeptNoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getIsParamDataIsDeptNoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getIsParamDataTwoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getIsParamDataTwoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有保险输入项目信息列表(get Insurance Input Item List)
	 * @param List
	 * @return
	 */
	public List getInsuranceInputItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insuranceInputItem.getInsuranceInputItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insuranceInputItem.getInsuranceInputItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getInsuranceInputItemParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insuranceInputItem.getInsuranceInputItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insuranceInputItem.getInsuranceInputItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有保险输入项目信息总数(get Insurance Input Item Cnt)
	 * @param List
	 * @return
	 */
	public int getInsuranceInputItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getInsuranceInputItemCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 取得所有保险输入项目参数信息总数（get Insurance Input Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getInsuranceInputItemParamCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getInsuranceInputItemParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 插入保险输入项目信息(add Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void addInsuranceInputItemInfo(Object obj) throws Exception{
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.insuranceInputItem.addInsuranceInputItemInfo", object) ;
	}
	/**
	 * 插入保险输入项目参数信息(add Insurance Input Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public int addInsuranceInputItemParamInfo(Object obj) {
		
		try {
			this.insert("pa.insuranceInputItem.addInsuranceInputItemParamInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 对插入保险输入项目信息进行是否重复验证(check Add Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	public int checkAddInsuranceInputItemInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.checkAddInsuranceInputItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 对插入保险输入项目参数信息进行是否重复验证(（check Add Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int checkAddInsuranceInputItemParamInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.checkAddInsuranceInputItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改保险输入项目信息(update Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void updateInsuranceInputItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.insuranceInputItem.updateInsuranceInputItemInfo", obj) ;
		
	}
	
	/****
	 * 修改和老系统保险项目的mapping关系
	 * @param object
	 * @return
	 */
	public int updateIsInputItemParamInfo(Object object) throws Exception{
		try {
			this.update("pa.insuranceInputItem.updateIsInputItemParamInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改保险输入项目参数信息(update Insurance Input Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public int updateInsuranceInputItemParamInfo(Object obj) {
		
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemParamInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 验证删除保险输入项目信息(check Delete Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteInsuranceInputItemInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.checkDeleteInsuranceInputItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除保险输入项目信息(delete Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void deleteInsuranceInputItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.deleteSyGlobalName(obj);
	
		this.delete("pa.insuranceInputItem.deleteInsuranceInputItemInfo", obj);
		
		//this.delete("pa.insuranceInputItem.deleteInsuranceInputItemDataInfo", obj);
	
	}
	
	/**
	 * 验证删除保险输入项目信息(check Delete Insurance Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteInsuranceInputItemParamInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.checkDeleteInsuranceInputItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 删除保险输入项目信息(delete Insurance Input Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deleteInsuranceInputItemParamInfo(Object obj) throws SQLException{
		
		try {
			this.delete("pa.insuranceInputItem.deleteInsuranceInputItemParamInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 取得所有区分项目信息列表(get Distinct Field List)
	 * @param List
	 * @return
	 */
	public List getDistinctFieldList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getDistinctFieldList", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputItemDataListDistinctFieldIsEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputItemDataListDistinctFieldIsEmpid(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * @param List
	 * @return
	 */
	public List getInsuranceInputItemDataListDistinctFieldIsDeptNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsDeptNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsDeptNo",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputItemDataListDistinctFieldIsDeptNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputItemDataListDistinctFieldIsDeptNo(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputItemDataListDistinctFieldIsCpnyId(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsCpnyId",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsCpnyId",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputItemDataListDistinctFieldIsCpnyId(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputItemDataListDistinctFieldIsCpnyId(obj,
				-1, -1);
		return returnList;
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputItemDataListDistinctFieldIsNotEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsNotEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsNotEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID(get Pa Input Item Data List Distinct Field Is Not Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputItemDataListDistinctFieldIsNotEmpid(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 删除保险输入项目数据信息(delete Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deleteInsuranceInputItemDataInfo(Object obj) {
		
		try {
			this.delete("pa.insuranceInputItem.deleteInsuranceInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除保险输入项目数据信息(delete Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deleteInsuranceInputItemDataInfoType(Object obj) {
		
		try {
			this.delete("pa.insuranceInputItem.deleteInsuranceInputItemDataInfoType", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除保险输入项目数据信息(delete Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deleteInsuranceInputItemDataBatchInfo(Object obj) {
		
		try {
			this.delete("pa.insuranceInputItem.deleteInsuranceInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除保险输入项目数据信息(delete Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deleteInsuranceInputItemDataBatchInfoType(Object obj) {
		
		try {
			this.delete("pa.insuranceInputItem.deleteInsuranceInputItemDataInfoType", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 验证删除保险输入项目数据信息（check Delete Insurance Input Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeleteInsuranceInputItemDataInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.checkDeleteInsuranceInputItemDataInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 验证删除保险输入项目数据信息（check Delete Insurance Input Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeleteInsuranceInputItemDataInfoType(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.checkDeleteInsuranceInputItemDataInfoType",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	
	/**
	 * 批量更新保险输入项目数据(update Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataInfo(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新保险输入项目数据(update Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataInfoMonth(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新保险输入项目数据(update Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataOtherInfoMonth(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataOtherInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 批量更新保险输入项目数据(update Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataInfoOther(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataInfoOther", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 保险输入项目初始化(create Insurance Input Item Info)
	 * @param Object
	 * @return
	 */
	public int createInsuranceInputItemInfo(Object obj) {
		
		try {
			this.insert("pa.insuranceInputItem.createInsuranceInputItemInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 初始化要添加的输入项目数据(create Add Insurance Input Item Data Info)
	 * @param Object
	 * @return
	 */
	public int createAddInsuranceInputItemDataInfo(Object obj) {
		
		try {
			this.insert("pa.insuranceInputItem.createAddInsuranceInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 取得所有输入项目所相关的添加数据,区分项目为EMPID(get Add Insurance Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	public List getAddInsuranceInputItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getAddInsuranceInputItemDataListDistinctFieldIsEmpid", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有输入项目所相关的添加数据,区分项目不为EMPID(get Add Insurance Input Item Data List Distinct Field Is Not Empid)
	 * @param List
	 * @return
	 */
	public List getAddInsuranceInputItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getAddInsuranceInputItemDataListDistinctFieldIsNotEmpid", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 通过参数号活的保险输入项目数据（get Insurance Input Item Data List By Param No）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getInsuranceInputItemDataListByParamNo(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getInsuranceInputItemDataListByParamNo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目数据信息（get Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getInsuranceInputItemDataInfo(Object obj) {
		
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getInsuranceInputItemDataListByParamNo(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}

	/**
	 * 批量插入保险输入项目数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addInsuranceInputItemDataInfo(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.addInsuranceInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量插入保险输入项目数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addInsuranceInputItemOtherDataInfo(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.addInsuranceInputItemOtherDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputItemDataListDistinctFieldIsEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputItemDataListDistinctFieldIsCpnyIdCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsCpnyIdCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputItemDataListDistinctFieldIsDeptNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsDeptNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分不为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据（get Insurance Input Item Data Person List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getInsuranceInputItemDataPersonList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataPersonList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputItemDataPersonList",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取保险输入项目数据个人列表个数（get Insurance Input Item Data Person List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputItemDataPersonListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputItemDataPersonListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取添加保险个人输入项目数据列表（get Insurance Input Item Data Person List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getInsuranceInputItemDataPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputItemDataPersonList(obj, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取添加保险个人输入列表（get Add Insurance Personal Input List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAddInsurancePersonalInputList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAddInsurancePersonalInputList(obj, -1, -1);
		return returnList;
	}

	
	/**
	 * 获取添加保险个人输入列表（get Add Insurance Personal Input List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAddInsurancePersonalInputList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getAddInsurancePersonalInputList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getAddInsurancePersonalInputList",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取添加保险个人输入项目列表（get Add Insurance Personal Input List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getAddInsurancePersonalInputListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getAddInsurancePersonalInputListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 检查批量更新奖金输入项目数据(check update Insurance Input Item Data Person Info)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkUpdateInsuranceInputItemDataPersonInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(
					this.queryForObject("pa.insuranceInputItem.checkUpdateInsuranceInputItemDataPersonInfo",obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 批量更新奖金输入项目数据(update Insurance Input Item Data Person Info)
	 * 
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataPersonInfo(Object object) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataPersonInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量更新保险输入项目数据(add Insurance Input Item Data Person Info)
	 * 
	 * @param List
	 * @return
	 */
	public int addInsuranceInputItemDataPersonInfo(Object object) {
		try {
			this.insert("pa.insuranceInputItem.addInsuranceInputItemDataPersonInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 根据项目的param_no和person_id删除数据
	 */
	public void deleteInsuranceInputItemDataPersonInfo(Object object) throws Exception{
		this.delete("pa.insuranceInputItem.deleteInsuranceInputItemDataPersonInfo",object);
	}
	
	
	
	
	//2013-09-01 lufeng
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputApplyDataListDistinctFieldIsEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputApplyDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputApplyDataListDistinctFieldIsEmpid(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputApplyDataListDistinctFieldIsDeptNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsDeptNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsDeptNo",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputApplyDataListDistinctFieldIsDeptNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputApplyDataListDistinctFieldIsDeptNo(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputApplyDataListDistinctFieldIsCpnyId(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsCpnyId",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsCpnyId",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputApplyDataListDistinctFieldIsCpnyId(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputApplyDataListDistinctFieldIsCpnyId(obj,
				-1, -1);
		return returnList;
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID
	 * 
	 * @param List
	 * @return
	 */
	public List getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID(get Pa Input Item Data List Distinct Field Is Not Empid)
	 * @param List
	 * @return
	 */
	@Override
	public List getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputApplyDataListDistinctFieldIsEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputApplyDataListDistinctFieldIsCpnyIdCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsCpnyIdCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputApplyDataListDistinctFieldIsDeptNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsDeptNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分不为EMPID（get Insurance Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputApplyDataListDistinctFieldIsNotEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputApplyDataListDistinctFieldIsNotEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 批量插入保险输入项目数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addInsuranceInputItemDataApply(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.addInsuranceInputItemDataApply", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新保险输入项目数据(update Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataApplyMonth(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataApplyMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量插入保险输入项目数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addInsuranceInputItemOtherDataApply(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.addInsuranceInputItemOtherDataApply", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新保险输入项目数据(update Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateInsuranceInputItemDataOtherApplyMonth(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataOtherApplyMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 通过/否决--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveInsDataApply(Object object) throws Exception {
		String tableName = ((Map)object).get("TABLE_NAME")!=null?((Map)object).get("TABLE_NAME").toString():"IS_PARAM_DATA_APPLY";
		String flag = ((Map) object).get("AFFIRM_FLAG") != null ? ((Map) object).get("AFFIRM_FLAG").toString() : "0";
		if("IS_PARAM_DATA_APPLY".equals(tableName)){
			this.update("pa.insuranceInputItem.updateInsApplyPersonDataFlag", object);
			// 如果决裁为通过，则需要插入到对应的输入项目数据表里
			if ("1".equals(String.valueOf(flag))) {
				this.insert("pa.insuranceInputItem.updateInsApplyPersonData", object);
				this.insert("pa.insuranceInputItem.insertInsApplyPersonData", object);
			}
		}else{
			this.update("pa.insuranceInputItem.updateInsApplyOtherDataFlag", object);
			// 如果决裁为通过，则需要插入到对应的输入项目数据表里
			if ("1".equals(String.valueOf(flag))) {
				this.insert("pa.insuranceInputItem.updateInsApplyOtherData", object);
				this.insert("pa.insuranceInputItem.insertInsApplyOtherData", object);
			}
		}
		return 1;
	}
	
	/**
	 * 删除--保险输入项目申请数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteInsDataApply(Object object) throws Exception {
		String tableName = ((Map)object).get("TABLE_NAME")!=null?((Map)object).get("TABLE_NAME").toString():"IS_PARAM_DATA_APPLY";
		if("IS_PARAM_DATA_APPLY".equals(tableName)){
			this.delete("pa.insuranceInputItem.deleteInsApplyPersonData", object);
		}else{
			this.delete("pa.insuranceInputItem.deleteInsApplyOtherData", object);
		}
		return 1;
	}

	@Override
	public int updateInsuranceApply(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceApply", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateInsuranceApplyOther(Object obj) {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceApplyOther", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
		
	}

	@Override
	public List getInsuranceInputItemDataForApplyName(Object obj) {
		List returnList = new ArrayList() ;
		try {
			
				returnList = this.queryForList("pa.insuranceInputItem.getInsuranceInputItemDataForApplyName", obj);
			
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 根据申请项目查询出相同所在地不同法人的相同的需要申请的项目
	 */
	@Override
	public List getUnifySuitCompanyList(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getUnifySuitCompanyList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@Override
	public List getUnifySuitCompanyList2(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getUnifySuitCompanyList2", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@Override
	public List getUnifySuitCompanyList3(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getUnifySuitCompanyList3", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@Override
	public List getUnifySuitCompanyList4(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getUnifySuitCompanyList4", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@Override
	public String getNextparamDataNo() {
		String returnString = "";
		try {
			returnString = ObjectUtils.toString(this
					.queryForObject("pa.insuranceInputItem.getNextparamDataNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 *  保存社保申请的附件信息
	 */
	@Override
	public void insertAccessory(Object object) throws Exception {
		this.insert("pa.insuranceInputItem.insertAccessory", object) ;
		
	}

	@Override
	public List getaccessoryList(Object obj) throws Exception {
		return this.queryForList("pa.insuranceInputItem.getaccessoryList", obj);
	}

	/**
	 *  查询登陆用户是否拥有法人管理权限  123293 否则返回0 只有C02 HQ才有此权限
	 */
	@Override
	public int getUserRolesGroupCnt(Object object) throws Exception {
		
		int returnInt = 0;

		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getUserRolesGroupCnt",object)), Integer.class);
		

		return returnInt;
	}

	@Override
	public List getItemNameList(Object obj) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getItemNameList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@Override
	public List getItemBatchImportList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getItemBatchImportList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getItemBatchImportList(obj,-1, -1);
		return returnList;
	}

	@Override
	public int getItemBatchImportListoCnt(Object object) throws Exception {
		int returnInt = 0;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getItemBatchImportListoCnt",object)), Integer.class);
		return returnInt;
	}
	
	/**
	*PA 工资输入项目
	 * 查找输入项目的NO和名称 
	 */
	@Override
	public List getItemNameListPa(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getItemNameListPa", object);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	*PA 工资输入项目带计算项目
	 * 查找输入项目的NO和名称 
	 */
	@Override
	public List getItemNameListPa2(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("pa.insuranceInputItem.getItemNameListPa2", object);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 获取福利地区
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-17 下午04:20:21 
	* @version V1.0
	 */
	@Override
	public List getWelfareArea(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.insuranceInputItem.getWelfareArea", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getItemBatchImportListPa(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportListPa",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportListPa",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getAddInsurancePersonalInputItemList(Map paramMap)
			throws Exception {
		return this.queryForList("pa.insuranceInputItem.getAddInsurancePersonalInputItemList", paramMap);
	}

	@Override
	public List getItemBatchImportListPa(Object object) {
		List returnList = new ArrayList();
		returnList = this.getItemBatchImportListPa(object,-1, -1);
		return returnList;
	}

	@Override
	public int getItemBatchImportListoCntPa(Object object) throws Exception{
		int returnInt = 0;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getItemBatchImportListoCntPa",object)), Integer.class);
		return returnInt;
	}

	@Override
	public List getItemBatchImportListPaBasis(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportListPaBasis",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportListPaBasis",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public List getItemBatchImportListPaBasisAndParam(Object object) {
		List returnList = new ArrayList();
		returnList = this.getItemBatchImportListPaBasisAndParam(object,-1, -1);
		return returnList;
	}
	/** 
	* @Title: getItemBatchImportListPaBasisAndParam 
	* @Description: TODO 查询基础及输入项目数据
	* @param @param obj
	* @param @param currentPage
	* @param @param pageSize
	* @param @return    
	* @return List    
	* @throws 
	*/
	@Override
	public List getItemBatchImportListPaBasisAndParam(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportListPaBasisAndParam",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportListPaBasisAndParam",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/* 
	* Title: updateItemBatchData
	* Description:提交修改的查询出的批量导入数据
	* @author 孙鹏  
	* @date 2015年4月21日 下午3:58:32  
	* @param obj
	* @return 
	* @see com.ait.pa.dao.InsuranceInputItemDao#updateItemBatchData(java.lang.Object) 
	*/
	@Override
	public int updateItemBatchDataForBasic(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.updateItemBatchDataForBasic", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@Override
	public int updateItemBatchDataForParam(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.updateItemBatchDataForParam", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@Override
	public List getItemBatchImportListPaBasis(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getItemBatchImportListPaBasis(obj,-1, -1);
		return returnList;
	}

	@Override
	public int getItemBatchImportListoCntPaBasis(Object obj) throws Exception {
		int returnInt = 0;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getItemBatchImportListoCntPaBasis",obj)), Integer.class);
		return returnInt;
	}

	@Override
	public List getInsuranceInputItemPersonTempAllList(Map paramMap)
			throws Exception {
		return this.queryForList("pa.insuranceInputItem.getInsuranceInputItemPersonTempAllList", paramMap);
	}

	@Override
	public int addApplyInform(Object obj) {
		try {
			this.insert("pa.insuranceInputItem.addApplyInform", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateInsuranceInputItemDataValue(LinkedHashMap paramMap)
			throws Exception {
		try {
			this.update("pa.insuranceInputItem.updateInsuranceInputItemDataValue", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt1(
			Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.insuranceInputItem.getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt1",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int getViewInsureSelfCnt(LinkedHashMap paramMap){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
						.queryForObject("pa.insuranceInputItem.getViewInsureSelfCnt",paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	@Override
	public List getViewInsureSelfList(Map paraMap) {
		List returnList = new ArrayList();
		returnList = this.getViewInsureSelfList(paraMap,-1, -1);
		return returnList;
	}
	/**
	 * 保险查看（个人别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:07:51 
	* @version V1.0
	 */
	@Override
	public List getViewInsureSelfList(Map paramMap, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.insuranceInputItem.getViewInsureSelfList",paramMap, currentPage, pageSize);
			} else {
				returnList = this.queryForList("pa.insuranceInputItem.getViewInsureSelfList",paramMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getViewInsureDeptCnt(LinkedHashMap paramMap){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
						.queryForObject("pa.insuranceInputItem.getViewInsureDeptCnt",paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public List getViewInsureDeptList(Map paraMap) {
		List returnList = new ArrayList();
		returnList = this.getViewInsureDeptList(paraMap,-1, -1);
		return returnList;
	}
	/**
	 * 保险查看（部门别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:08:17 
	* @version V1.0
	 */
	@Override
	public List getViewInsureDeptList(Map paramMap, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("pa.insuranceInputItem.getViewInsureDeptList",paramMap, currentPage, pageSize);
			} else {
				returnList = this.queryForList("pa.insuranceInputItem.getViewInsureDeptList",paramMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/*----------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempISParamDataList(LinkedHashMap paramMap,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insuranceInputItem.getImportExcelTempISParamDataList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.insuranceInputItem.getImportExcelTempISParamDataList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getImportExcelTempISParamDataList(LinkedHashMap paramMap) {
		return this.getImportExcelTempISParamDataList(paramMap, -1, -1);
	}
	@Override
	public int getImportExcelTempISParamDataListCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getImportExcelTempISParamDataListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempISParamDataListErrCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insuranceInputItem.getImportExcelTempISParamDataListErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 工资基础项目数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@Override
	public String importISParamDataExcelExcel(LinkedHashMap paramMap) {
	
			String returnString = "" ;		
			try {
				paramMap.put("message", "") ;
				this.insert("pa.insuranceInputItem.importISParamDataExcel", paramMap);			
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;			
				e.printStackTrace();
			}
			return returnString ;
		}

	/* 
	* Title: getItemBatchImportDataTemp
	* Description:获取临时表导入数据
	* @author 孙鹏  
	* @date 2015年2月27日 上午10:39:40  
	* @param paramMap
	* @return 
	* @see com.ait.pa.dao.InsuranceInputItemDao#getItemBatchImportDataTemp(java.util.LinkedHashMap) 
	*/
	@Override
	public List getItemBatchImportDataTemp(LinkedHashMap paramMap,int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportDataTemp", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.insuranceInputItem.getItemBatchImportDataTemp", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	public List getItemBatchImportDataTemp(LinkedHashMap paramMap) {
		List returnList = new ArrayList() ;
		returnList=this.getItemBatchImportDataTemp(paramMap,-1,-1);
		return returnList ;
	}

	/* 
	* Title: getItemBatchImportDataTempCnt
	* Description:获取临时表的数据量
	* @author 孙鹏  
	* @date 2015年2月28日 下午4:52:49  
	* @param paramMap
	* @param pageNum
	* @param numPerPage
	* @return 
	* @see com.ait.pa.dao.InsuranceInputItemDao#getItemBatchImportDataTempCnt(java.util.LinkedHashMap, int, int) 
	*/
	

	@Override
	public int getItemBatchImportDataTempCnt(LinkedHashMap paramMap) {
		int returnInt=0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.insuranceInputItem.getItemBatchImportDataTempCnt",paramMap)), Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt ;
	}
	/* 
	* Title: deleteErrorOldItemBatchData
	* Description:删除错误的旧数据
	* @author 孙鹏  
	* @date 2015年4月23日 上午9:46:16  
	* @param paramMap
	* @return 
	* @see com.ait.pa.dao.InsuranceInputItemDao#deleteErrorOldItemBatchData(java.util.LinkedHashMap) 
	*/
	public void deleteErrorOldItemBatchData(LinkedHashMap paramMap) {
		try {
			this.delete("pa.insuranceInputItem.deleteErrorOldItemBatchData", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	/** 
	* @Title: getItemBatchImportDataTempErrorCnt 
	* @Description: TODO 获取错误的数据量
	* @param @param paramMap
	* @param @return    
	* @return int    
	* @throws 
	*/
	@Override
	public int getItemBatchImportDataTempErrorCnt(LinkedHashMap paramMap) {
		int returnInt=0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.insuranceInputItem.getItemBatchImportDataTempErrorCnt",paramMap)), Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt ;
	}

	/* 
	* Title: submitItemBatchData
	* Description:提交临时表中的数据
	* @author 孙鹏  
	* @date 2015年3月2日 上午9:55:15  
	* @param paramMap
	* @return 
	* @see com.ait.pa.dao.InsuranceInputItemDao#submitItemBatchData(java.util.LinkedHashMap) 
	*/
	@Override
	public String submitItemBatchData(LinkedHashMap paramMap) throws SQLException {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.insuranceInputItem.submitItemBatchData", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}

	/* 
	* Title: delExceplImportLine
	* Description:删除导入临时表的错误数据 根据lineid
	* @author 孙鹏  
	* @date 2015年3月30日 下午4:26:10  
	* @param paramMap
	* @return 
	* @see com.ait.pa.dao.InsuranceInputItemDao#delExceplImportLine(java.util.Map) 
	*/
	@Override
	public boolean delExceplImportLine(Map paramMap) {
		Boolean flag = true;
		try {
			this.delete("pa.insuranceInputItem.delExceplImportLine", paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}

