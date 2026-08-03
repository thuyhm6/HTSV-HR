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

import com.ait.pa.dao.PaBasicItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemDaoImpl.java
 * @Description:
 * @Create date: 2012-2-7 下午08:23:33
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaBasicItemDaoImpl extends SqlMapClientSupport implements PaBasicItemDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaBasicItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaBasicItemList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item Data Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaBasicItemDataInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaBasicItemDataList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaBasicItemList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item Data List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaBasicItemDataList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getPaBasicItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.basicItem.getPaBasicItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;

	}
	
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item Data List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getPaBasicItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.basicItem.getPaBasicItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;

	}
	
	/**
	 * 取得所有计算项目信息总数(get Pa Basic Item Cnt)
	 * @param List
	 * @return
	 */
	public int getPaBasicItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getPaBasicItemCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入计算项目信息(add Pa Basic Item Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addPaBasicItemInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("pa.basicItem.addPaBasicItemInfo", object) ;

	}
	
	public void addPaBasicItemInfoAffirm(Object object) throws Exception{
		this.insert("pa.basicItem.addPaBasicItemInfoAffirm", object) ;
	}
	
	/**
	 * 对插入计算项目信息进行是否重复验证
	 * @param List
	 * @return
	 */
	public int checkAddPaBasicItemInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkAddPaBasicItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改计算项目信息(update Pa Basic Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void updatePaBasicItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.basicItem.updatePaBasicItemInfo", obj) ;
		
	}
	
	/**
	 * 验证删除计算项目信息(check Delete Pa Basic Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeletePaBasicItemInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkDeletePaBasicItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除计算项目信息(delete Pa Basic Item Info)
	 * @param List
	 * @return
	 */
	public void deletePaBasicItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		
		this.delete("pa.basicItem.deletePaBasicItemInfo", obj) ;

		
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaBasicItemDataListDistinctFieldIsEmpid(obj,
				-1, -1);
		return returnList;
	}
	/**
	 * 取得所有基础项目所相关的数据,区分项目为EMPID(get Pa Basic Item Data List DistinctField Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsEmpid",
								obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有基础项目所相关的数据,区分项目为POSITION_NO(get Pa Basic Item Data List DistinctField Is POSITION_NO)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsPositionNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsPositionNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsPositionNo",
								obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有基础项目所相关的数据,区分项目为POSITION_NO(get Pa Basic Item Data List DistinctField Is DeptArea)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsDeptArea(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsDeptArea",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsDeptArea",
								obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicItemDataListDistinctFieldIsDeptNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaBasicItemDataListDistinctFieldIsDeptNo(obj,
				-1, -1);
		return returnList;
	}
	/**
	 * 取得所有基础项目所相关的数据,区分项目为EMPID(get Pa Basic Item Data List DistinctField Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsDeptNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsDeptNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsDeptNo",
								obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有基础项目所相关的数据,区分项目为EMPID(get Pa Basic Item Data List DistinctField Is Empid Cnt)
	 * @param List
	 * @return
	 */
	@Override
	public int getPaBasicItemDataListDistinctFieldIsDeptNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.getPaBasicItemDataListDistinctFieldIsDeptNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为POSITION_NO(get Pa Input Item Data List Distinct Field Is POSITION_NO)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicItemDataListDistinctFieldIsPositionNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaBasicItemDataListDistinctFieldIsPositionNo(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为POSITION_NO(get Pa Input Item Data List Distinct Field Is POSITION_NO)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicItemDataListDistinctFieldIsDeptArea(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaBasicItemDataListDistinctFieldIsDeptArea(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicItemDataListDistinctFieldIsCpnyId(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaBasicItemDataListDistinctFieldIsCpnyId(obj,
				-1, -1);
		return returnList;
	}
	/**
	 * 取得所有基础项目所相关的数据,区分项目为EMPID(get Pa Basic Item Data List DistinctField Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsCpnyId(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsCpnyId",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsCpnyId",
								obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有基础项目所相关的数据,区分项目为EMPID(get Pa Basic Item Data List DistinctField Is Empid Cnt)
	 * @param List
	 * @return
	 */
	@Override
	public int getPaBasicItemDataListDistinctFieldIsCpnyIdCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.getPaBasicItemDataListDistinctFieldIsCpnyIdCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID(get Pa Input Item Data List Distinct Field Is Not Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaBasicItemDataListDistinctFieldIsNotEmpid(obj,
				-1, -1);
		return returnList;
	}
	/**
	 * 取得所有基础项目所相关的数据,区分项目不为EMPID(get Pa Basic Item Data List DistinctField Is Not Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsNotEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsNotEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.basicItem.getPaBasicItemDataListDistinctFieldIsNotEmpid",
								obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 取得所有基础项目所相关的数据,区分项目为EMPID(get Pa Basic Item Data List DistinctField Is Empid Cnt)
	 * @param List
	 * @return
	 */
	@Override
	public int getPaBasicItemDataListDistinctFieldIsEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.getPaBasicItemDataListDistinctFieldIsEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 取得所有基础项目所相关的数据,区分项目不为EMPID(get Pa Basic Item Data List DistinctField Is Empid Cnt)
	 * @param List
	 * @return
	 */
	@Override
	public int getPaBasicItemDataListDistinctFieldIsNotEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.getPaBasicItemDataListDistinctFieldIsNotEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int getPaBasicItemDataListDistinctFieldIsPositionNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.getPaBasicItemDataListDistinctFieldIsPositionNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int getPaBasicItemDataListDistinctFieldIsDeptAreaCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.getPaBasicItemDataListDistinctFieldIsDeptAreaCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 批量更新基础项目数据(update Pa Basic Item Data Info)
	 * @param List
	 * @return
	 */
	public int updatePaBasicItemDataInfo(Object obj) {
		try {
			this.update("pa.basicItem.updatePaBasicItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 更新基础项目数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-10-21 下午09:02:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public int updatePaBasicItemData(LinkedHashMap map)throws Exception {
		try {
			this.update("pa.basicItem.updatePaBasicItemData", map) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量更新基础项目数据(update Pa Basic Item Data Info Other)
	 * @param List
	 * @return
	 */
	public int updatePaBasicItemDataInfoOther(Object obj) {
		try {
			this.update("pa.basicItem.updatePaBasicItemDataInfoOther", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 初始化要添加的基础项目数据(create Add Pa Basic Item Data Info)
	 * @param Object
	 * @return
	 */
	public int createAddPaBasicItemDataInfo(Object obj) {
		
		try {
			this.insert("pa.basicItem.createAddPaBasicItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 取得所有基础项目所相关的添加数据,区分项目为EMPID(get Add Pa Basic Item Data List DistinctField Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAddPaBasicItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getAddPaBasicItemDataListDistinctFieldIsEmpid", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有基础项目所相关的添加数据,区分项目不为EMPID(get Add Pa Basic Item Data List DistinctField Is Not Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAddPaBasicItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getAddPaBasicItemDataListDistinctFieldIsNotEmpid", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 批量插入基础项目数据(add Pa Basic Item Data Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addPaBasicItemDataInfo(List list) {
		try {
			this.updateForList("pa.basicItem.addPaBasicItemDataInfo", list) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 修改历史数据并且插入新的基础项目数据(update And Add Pa Basic Item Data Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateAndAddPaBasicItemDataInfo(List list) throws SQLException {
		
		this.update("pa.basicItem.updatePaBasicItemActivityDataInfo", list.get(0)) ;
		
		this.insert("pa.basicItem.addPaBasicItemDataInfo", list.get(1)) ;
			
		return 0;
	}
	

	/**
	 * 取得所有基础项目参数列表(get Pa Basic Item Param List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getPaBasicItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.basicItem.getPaBasicItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;

	}
	
	/**
	 * 取得所有础项目参数列表(get Pa Basic Item Param List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaBasicItemParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	
	/**
	 * 对插入基础项目参数信息进行是否重复验证(check Add Pa Basic Item Param Info)
	 * @param List
	 * @return
	 */
	public int checkAddPaBasicItemParamInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkAddPaBasicItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 插入基础项目参数信息(add Pa Basic Item Param Info)
	 * @param List
	 * @return
	 */
	@Override
	public int addPaBasicItemParamInfo(Object obj) {
		
		try {
			this.insert("pa.basicItem.addPaBasicItemParamInfo", obj) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
	}
	
public int updatePaBasicItemParam(Object obj) {
		
		try {
			this.update("pa.basicItem.updatePaBasicItemParam", obj) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
	}

	/**
	 * 获取工资基础项目参数个数（get Pa Basic Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPaBasicItemParamCnt(Object obj) {
		int returnInt = 0 ;

		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getPaBasicItemParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 获取工资基础项目参数信息（get Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBasicItemParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaBasicItemParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}

	/**
	 * 修改工资基础项目参数信息（update Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updatePaBasicItemParamInfo(Object obj) {
		try {
			this.update("pa.basicItem.updatePaBasicItemParamInfo", obj) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 修改工资基础项目参数信息（update Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updatePaBasicItemMappingInfo(Object obj) {
		try {
			this.update("pa.basicItem.updatePaBasicItemMappingInfo", obj) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 */
	public int updatePaBasicItemParamInfoAll(String item_no) throws SQLException {
	    try {
		    this.update("pa.basicItem.updatePaBasicItemParamInfoAll", item_no) ;
		    return 1;
	    } catch (RuntimeException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    return 0;
	    }
	}

	/**
	 * 验证删除工资基础项目参数信息（check Delete Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeletePaBasicItemParamInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkDeletePaBasicItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 删除工资基础项目数据信息（check Delete Pa Basic Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deletePaBasicItemParamInfo(Object obj) throws SQLException {
		try {
			this.delete("pa.basicItem.deletePaBasicItemParamInfo", obj) ;
			return 1;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 检查删除工资基础项目数据信息（check Delete Pa Basic Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int checkDeletePaBasicItemDataInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.checkDeletePaBasicItemDataInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 检查删除工资基础项目数据信息（check Delete Pa Basic Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeletePaBasicItemDataInfoType(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.basicItem.checkDeletePaBasicItemDataInfoType",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	

	/**
	 * 删除工资输入项目数据信息(delete Pa Input Item Data Info)
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaBasicItemDataInfo(Object obj) {
		
		try {
			this.delete("pa.basicItem.deletePaBasicItemDataInfo", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除奖金输入项目数据信息（delete Pa Basic Item Data Info Type）
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaBasicItemDataInfoType(Object obj) {

		try {
			this.delete("pa.basicItem.deletePaBasicItemDataInfoType",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 批量删除奖金输入项目数据信息（delete Pa Basic Item Data Batch Info）
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaBasicItemDataBatchInfo(Object obj) {

		try {
			this.delete("pa.basicItem.deletePaBasicItemDataInfo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量删除工资基础项目数据信息（delete Pa Basic Item Data Batch Info Type）
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaBasicItemDataBatchInfoType(Object obj) {

		try {
			this.delete("pa.basicItem.deletePaBasicItemDataInfoType",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 初始化要添加的输入项目数据(create Add Insurance Input Item Data Info)
	 * @param Object
	 * @return
	 */
	public int createAddPaBasicInputItemDataInfo(Object obj) {
		
		try {
			this.insert("pa.basicItem.createAddPaBasicInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataFieldOneList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getPaBasicParamDataFieldOneList", obj);
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
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataIsCpnyIDList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getPaBasicParamDataIsCpnyIDList", obj);
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
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataPositionNoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getPaBasicParamDataPositionNoList", obj);
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
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataDeptAreaList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getPaBasicParamDataDeptAreaList", obj);
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
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataIsDeptNoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getPaBasicParamDataIsDeptNoList", obj);
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
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataFieldTwoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.basicItem.getPaBasicParamDataFieldTwoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	

	/**
	 * 批量插入保险输入项目数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addPaBasicInputItemDataInfo(Object obj) {
		try {
			this.insert("pa.basicItem.addPaBasicInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 验证是否存在要添加的数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int checkAddPaBasicItemDataInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkAddPaBasicItemDataInfo", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	/**
	 * 验证是否存在要添加的数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int checkAddPaBasicItemDataInfoMonth(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkAddPaBasicItemDataInfoMonth", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	/**
	 * 验证是否存在要添加的数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int checkAddPaBasicItemDataOtherInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkAddPaBasicItemDataOtherInfo", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 验证是否存在要添加的数据(add Insurance Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int checkAddPaBasicItemDataOtherInfoMonth(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.checkAddPaBasicItemDataOtherInfoMonth", obj)),Integer.class) ;
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
	
	public int addPaBasicInputItemOtherDataInfo(Object obj) {
		try {
			this.insert("pa.basicItem.addPaBasicInputItemOtherDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 更新基础项目数据结束月(update Pa Basic Item Data Info Other)
	 * @param List
	 * @return
	 */
	public int updatePaBasicItemDataInfoMonth(Object obj) {
		try {
			this.update("pa.basicItem.updatePaBasicItemDataInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 更新基础项目数据结束月(update Pa Basic Item Data Info Other)
	 * @param List
	 * @return
	 */
	public int updatePaBasicItemDataOtherInfoMonth(Object obj) {
		try {
			this.update("pa.basicItem.updatePaBasicItemDataOtherInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicInputItemDataList(Map paramMap) throws Exception {
		return this.queryForList("pa.basicItem.getPaBasicInputItemDataList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List paBasicInputItemAllList(Map paramMap) throws Exception {
		return this.queryForList("pa.basicItem.paBasicInputItemAllList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBasicItemDataValue(LinkedHashMap paramMap)
			throws Exception {
		try {
			this.update("pa.basicItem.updatePaBasicItemDataValue", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 插入派遣地项目信息(add Pa SendToAdministration Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void addPaSendToAdministrationItemInfo(LinkedHashMap paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		this.insert("pa.basicItem.addPaSendToAdministrationItemInfo", paramMap) ;
		
	}
	
	/**
	 * 获取派遣地列表（get Pa SendToAdministration Item  List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public List getPaSendToAdministrationList(Map paramMap) throws Exception {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.basicItem.getPaSendToAdministrationList", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 获取派遣地列表数量（get Pa SendToAdministration Item  List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int getPaSendToAdministrationListCnt(Map paramMap) throws Exception {
		int result = 0;
		try {
			result = (Integer) this.queryForObject("pa.basicItem.getPaSendToAdministrationListCnt", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return result ;
	}
	
	/**
	 * 删除 派遣地项目数据信息（check Delete Pa Basic Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deletePaSendToAdministrationInfo(Object obj) throws SQLException {
		try {
			this.delete("pa.basicItem.deletePaSendToAdministrationInfo", obj) ;
			return 1;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List getImportExcelTempPaBasicItemList(LinkedHashMap paramMap,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaBasicItemList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaBasicItemList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getImportExcelTempPaBasicItemList(LinkedHashMap paramMap) {
		return this.getImportExcelTempPaBasicItemList(paramMap, -1, -1);
	}
	@Override
	public int getImportExcelTempPaBasicItemListCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaBasicItemListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempPaBasicItemListErrCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaBasicItemListErrCnt", paramMap)), Integer.class) ;
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
	public String importPaBasicItemExcelExcel(LinkedHashMap paramMap) {
	
			String returnString = "" ;		
			try {
				paramMap.put("message", "") ;
				this.insert("pa.basicItem.importPaBasicItemExcel", paramMap);			
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;			
				e.printStackTrace();
			}
			return returnString ;
		}
	/*---------------------------------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempPaParamList(LinkedHashMap paramMap,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaParamList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaParamList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/*---------------------------------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempPaParamMonthList(LinkedHashMap paramMap,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaParamMonthList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaParamMonthList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/*---------------------------------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempPaParamGradeList(LinkedHashMap paramMap,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaParamGradeList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.basicItem.getImportExcelTempPaParamGradeList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getImportExcelTempPaParamList(LinkedHashMap paramMap) {
		return this.getImportExcelTempPaParamList(paramMap, -1, -1);
	}
	@Override
	public List getImportExcelTempPaParamMonthList(LinkedHashMap paramMap) {
		return this.getImportExcelTempPaParamMonthList(paramMap, -1, -1);
	}
	@Override
	public List getImportExcelTempPaParamGradeList(LinkedHashMap paramMap) {
		return this.getImportExcelTempPaParamGradeList(paramMap, -1, -1);
	}
	@Override
	public int getImportExcelTempPaParamListCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaParamListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempPaParamMonthListCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaParamMonthListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempPaParamListGradeCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaParamListGradeCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempPaParamListErrCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaParamListErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempPaParamMonthListErrCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaParamMonthListErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempPaParamListErrGradeCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.basicItem.getImportExcelTempPaParamListErrGradeCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 输入基础项目数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@Override
	public String importPaParamExcel(LinkedHashMap paramMap) {
	
			String returnString = "" ;		
			try {
				paramMap.put("message", "") ;
				this.insert("pa.basicItem.importPaParamExcel", paramMap);			
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;			
				e.printStackTrace();
			}
			return returnString ;
		}
	/**
	 * 输入基础项目数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@Override
	public String importPaParamMonthExcel(LinkedHashMap paramMap) {
	
			String returnString = "" ;		
			try {
				paramMap.put("message", "") ;
				this.insert("pa.basicItem.importPaParamMonthExcel", paramMap);			
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;			
				e.printStackTrace();
			}
			return returnString ;
		}


}
