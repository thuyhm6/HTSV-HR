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

import com.ait.pa.dao.PaInputItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaInputItemDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:14:35
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaInputItemDaoImpl extends SqlMapClientSupport implements PaInputItemDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 取得所有工资输入项目信息列表(get Pa Input Item Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaInputItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaInputItemList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有工资输入项目信息列表(get Pa Input Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaInputItemList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有工资输入项目信息列表(get Pa Input Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.paInputItem.getPaInputItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.paInputItem.getPaInputItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有工资输入项目信息总数(get Pa Input Item Cnt)
	 * @param List
	 * @return
	 */
	public int getPaInputItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.getPaInputItemCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入工资输入项目信息(add Pa Input Item Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addPaInputItemInfo(Object obj) throws Exception{
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.paInputItem.addPaInputItemInfo", object) ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void addPaInputItemInfoAffirm(Object object) throws Exception{
		this.insert("pa.paInputItem.addPaInputItemInfoAffirm", object) ;
	}
	
	/**
	 * 对插入工资输入项目信息进行是否重复验证(check Add Pa Input Item Info)
	 * @param List
	 * @return
	 */
	public int checkAddPaInputItemInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.checkAddPaInputItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改工资输入项目信息(update Pa Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void updatePaInputItemInfo(Object obj) throws Exception{ 
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.paInputItem.updatePaInputItemInfo", obj) ;
		
	}
	
	/**
	 * 验证删除工资输入项目信息(check Delete Pa Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeletePaInputItemInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.checkDeletePaInputItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除工资输入项目信息(delete Pa Input Item Info)
	 * @param List
	 * @return
	 */
	@Override
	public void deletePaInputItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		
		this.delete("pa.paInputItem.deletePaInputItemInfo", obj) ;
		
		//this.delete("pa.paInputItem.deletePaInputItemDataInfo", obj) ;
	
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsPositionNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsPositionNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsPositionNo",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsWorkAreaNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsWorkAreaNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsWorkAreaNo",
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaInputItemDataListDistinctFieldIsEmpid(obj,
				-1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataListDistinctFieldIsPositionNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaInputItemDataListDistinctFieldIsPositionNo(obj,
				-1, -1);
		return returnList;
	}
	
	@Override
	public int getPaInputItemDataListDistinctFieldIsEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getPaInputItemDataListDistinctFieldIsEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int getPaInputItemDataListDistinctFieldIsPositionNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getPaInputItemDataListDistinctFieldIsPositionNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsCpnyId(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsCpnyId",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsCpnyId",
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataListDistinctFieldIsCpnyId(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaInputItemDataListDistinctFieldIsCpnyId(obj,
				-1, -1);
		return returnList;
	}
	@Override
	public int getPaInputItemDataListDistinctFieldIsCpnyIdCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getPaInputItemDataListDistinctFieldIsCpnyIdCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsDeptNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsDeptNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsDeptNo",
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataListDistinctFieldIsDeptNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaInputItemDataListDistinctFieldIsDeptNo(obj,
				-1, -1);
		return returnList;
	}
	@Override
	public int getPaInputItemDataListDistinctFieldIsDeptNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getPaInputItemDataListDistinctFieldIsDeptNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsNotEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsNotEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataListDistinctFieldIsNotEmpid",
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaInputItemDataListDistinctFieldIsNotEmpid(obj,
				-1, -1);
		return returnList;
	}
	
	@Override
	public int getPaInputItemDataListDistinctFieldIsNotEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getPaInputItemDataListDistinctFieldIsNotEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	
	/**
	 * 批量更新工资输入项目数据(update Pa Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updatePaInputItemDataInfo(Object obj) {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 批量更新工资输入项目数据(update Pa Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updatePaInputItemDataInfoOther(Object obj) {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataInfoOther", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 工资输入项目初始化(create Pa Input Item Info)
	 * @param Object
	 * @return
	 */
	public int createPaInputItemInfo(Object obj) {
		
		try {
			this.insert("pa.paInputItem.createPaInputItemInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 初始化要添加的输入项目数据(create Add Pa Input Item Data Info)
	 * @param Object
	 * @return
	 */
	public int createAddPaInputItemDataInfo(Object obj) {
		
		try {
			this.insert("pa.paInputItem.createAddPaInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	/**
	 * 取得所有输入项目所相关的添加数据,区分项目为EMPID(get Add Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAddPaInputItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getAddPaInputItemDataListDistinctFieldIsEmpid", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有输入项目所相关的添加数据,区分项目不为EMPID(get Add Pa Input Item Data List Distinct Field Is Not Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAddPaInputItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getAddPaInputItemDataListDistinctFieldIsNotEmpid", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 批量插入工资输入项目数据(add Pa Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int addPaInputItemDataInfo(Object obj) {
		try {
			this.insert("pa.paInputItem.addPaInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量插入工资输入项目数据(add Pa Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int addPaInputItemOtherDataInfo(Object obj) {
		try {
			this.insert("pa.paInputItem.addPaInputItemOtherDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaParamDataList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaParamDataList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataWorkAreaList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaParamDataWorkAreaList", obj);
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
	public List getPaParamDataIsCpnyIdList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaParamDataIsCpnyIdList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataIsPositionNoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaParamDataIsPositionNoList", obj);
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
	public List getPaParamDataIsDeptNoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaParamDataIsDeptNoList", obj);
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
	public List getPaParamDataTwoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaParamDataTwoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为Param_no(get Pa Input Item Data List By ParamNo)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListByParamNo(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.paInputItem.getPaInputItemDataListByParamNo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 获取工资输入项目数据信息（get Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaInputItemDataInfo(Object obj) {
		
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaInputItemDataListByParamNo(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataPersonList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataPersonList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getPaInputItemDataPersonList",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getPaInputItemDataPersonListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getPaInputItemDataPersonListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPaInputItemDataPersonList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAddPaPersonalInputList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAddPaPersonalInputList(obj, -1, -1);
		return returnList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List getAddPaPersonalInputList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.paInputItem.getAddPaPersonalInputList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.paInputItem.getAddPaPersonalInputList",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getAddPaPersonalInputListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.getAddPaPersonalInputListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int checkUpdatePaInputItemDataPersonInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject(
							"pa.paInputItem.checkUpdatePaInputItemDataPersonInfo",obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int updatePaInputItemDataPersonInfo(Object object) {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataPersonInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int updatePaInputItemDataPersonInfoForMonth(Object object) {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataPersonInfoForMonth",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int addPaInputItemDataPersonInfo(Object object) {
		try {
			this.insert("pa.paInputItem.addPaInputItemDataPersonInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int checkDeletePaInputItemDataInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.checkDeletePaInputItemDataInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int checkDeletePaInputItemDataInfoType(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.paInputItem.checkDeletePaInputItemDataInfoType",
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
	public int deletePaInputItemDataInfo(Object obj) {
		
		try {
			this.delete("pa.paInputItem.deletePaInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除奖金输入项目数据信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaInputItemDataInfoType(Object obj) {

		try {
			this.delete("pa.paInputItem.deletePaInputItemDataInfoType",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除奖金输入项目数据信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int clearPaInputItemDataInfoType(Object obj) {

		try {
			this.delete("pa.paInputItem.clearPaInputItemDataInfoType",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量删除奖金输入项目数据信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaInputItemDataBatchInfo(Object obj) {

		try {
			this.delete("pa.paInputItem.deletePaInputItemDataInfo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量删除奖金输入项目数据信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deletePaInputItemDataBatchInfoType(Object obj) {

		try {
			this.delete("pa.paInputItem.deletePaInputItemDataInfoType",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 根据条件查询员工信息(According to the condition inquires the employee information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpIdList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getEmpIdList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工信息(According to the condition inquires the employee information)
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpIdList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.paInputItem.getEmpIdList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.paInputItem.getEmpIdList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工信息数量(According to the condition inquires the employee information count)
	 * @param List
	 * @return
	 */
	public int getEmpIdListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.getEmpIdListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	public void deletePaInputItemDataPersonInfo (Object object) throws Exception{
		this.delete("pa.paInputItem.deletePaInputItemDataPersonInfo", object) ;
	}
	
	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int updatePaInputItemDataInfoMonth(Object object) {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataInfoMonth",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int updatePaInputItemDataOtherInfoMonth(Object object) {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataOtherInfoMonth",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAddPaPersonalInputItemList(Map paramMap) throws Exception {
		return this.queryForList("pa.paInputItem.getAddPaPersonalInputItemList", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemPersonAllList(Map paramMap) throws Exception {
		return this.queryForList("pa.paInputItem.getPaInputItemPersonAllList", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updatePaInputItemDataValue(LinkedHashMap paramMap)
			throws Exception {
		try {
			this.update("pa.paInputItem.updatePaInputItemDataValue", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}

