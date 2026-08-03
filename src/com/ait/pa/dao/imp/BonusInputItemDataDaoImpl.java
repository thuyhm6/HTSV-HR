package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusInputItemDataDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class BonusInputItemDataDaoImpl extends SqlMapClientSupport implements
		BonusInputItemDataDao {

	@Override
	public int checkUpdateBonusInputItemDataPersonInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.checkUpdateBonusInputItemDataPersonInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	@Override
	public int checkBonusInputItemDataInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.checkBonusInputItemDataInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int checkBonusInputItemDataInfoType(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.checkBonusInputItemDataInfoType",
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
	public List getBonusInputItemDataListDistinctFieldIsEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBonusInputItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getBonusInputItemDataListDistinctFieldIsEmpid(obj,
				-1, -1);
		return returnList;
	}

	@Override
	public int getBonusInputItemDataListDistinctFieldIsEmpidCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID(get Pa Input Item Data List Distinct Field Is Empid)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBonusInputItemDataListDistinctFieldIsDeptNo(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getBonusInputItemDataListDistinctFieldIsDeptNo(obj,
				-1, -1);
		return returnList;
	}
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsDeptNo(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsDeptNo",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsDeptNo",
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
	public List getBonusInputItemDataListDistinctFieldIsCpnyId(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getBonusInputItemDataListDistinctFieldIsCpnyId(obj,
				-1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsCpnyId(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsCpnyId",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsCpnyId",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getBonusInputItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getBonusInputItemDataListDistinctFieldIsNotEmpid(obj,
				-1, -1);
		return returnList;
	}

	/**
	 * 取得所有输入项目所相关的数据,区分项目不为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsNotEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsNotEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsNotEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Bonus Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getBonusInputItemDataListDistinctFieldIsCpnyIdCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsCpnyIdCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 获取保险输入项目数据列表个数,区分为EMPID（get Bonus Input Item Data List DistinctField Is Not Empid Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getBonusInputItemDataListDistinctFieldIsDeptNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsDeptNoCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public int getBonusInputItemDataListDistinctFieldIsNotEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.getBonusInputItemDataListDistinctFieldIsNotEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 删除奖金输入项目数据信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int deleteBonusInputItemDataInfo(Object obj) {

		try {
			this.delete("pa.bonusInputItemData.deleteBonusInputItemDataInfo",
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
	public int deleteBonusInputItemDataInfoType(Object obj) {

		try {
			this.delete("pa.bonusInputItemData.deleteBonusInputItemDataInfoType",
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
	public int deleteBonusInputItemDataBatchInfo(Object obj) {

		try {
			this.delete("pa.bonusInputItemData.deleteBonusInputItemDataBatchInfo",
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
	public int deleteBonusInputItemDataBatchInfoType(Object obj) {

		try {
			this.delete("pa.bonusInputItemData.deleteBonusInputItemDataBatchInfoType",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int updateBonusInputItemDataInfo(Object object) {
		try {
			this.update("pa.bonusInputItemData.updateBonusInputItemDataInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	public int updateBonusInputItemDataInfoOther(Object object) {
		try {
			this.update("pa.bonusInputItemData.updateBonusInputItemDataInfoOther",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 * 奖金输入项目初始化
	 * 
	 * @param Object
	 * @return
	 */
	public int createBonusInputItemInfo(Object obj) {

		try {
			this.insert("pa.bonusInputItemData.createBonusInputItemInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 初始化要添加的输入项目数据
	 * 
	 * @param Object
	 * @return
	 */
	public int createAddBonusInputItemDataInfo(Object obj) {

		try {
			this.insert(
					"pa.bonusInputItemData.createAddBonusInputItemDataInfo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 取得所有输入项目所相关的添加数据,区分项目为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataListDistinctFieldIsEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getAddBonusInputItemDataListDistinctFieldIsEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getAddBonusInputItemDataListDistinctFieldIsEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAddBonusInputItemDataListDistinctFieldIsEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAddBonusInputItemDataListDistinctFieldIsEmpid(obj,
				-1, -1);
		return returnList;
	}

	@Override
	public int getAddBonusInputItemDataListDistinctFieldIsEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.pa.bonusInputItemData.getAddBonusInputItemDataListDistinctFieldIsEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAddBonusInputItemDataListDistinctFieldIsNotEmpid(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAddBonusInputItemDataListDistinctFieldIsNotEmpid(
				obj, -1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有输入项目所相关的添加数据,区分项目不为EMPID
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataListDistinctFieldIsNotEmpid(Object obj,
			int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getAddBonusInputItemDataListDistinctFieldIsNotEmpid",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getAddBonusInputItemDataListDistinctFieldIsNotEmpid",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getAddBonusInputItemDataListDistinctFieldIsNotEmpidCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.pa.bonusInputItemData.getAddBonusInputItemDataListDistinctFieldIsNotEmpidCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 批量插入奖金输入项目数据
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addBonusInputItemDataInfo(List list) {
		try {
			this.updateForList(
					"pa.bonusInputItemData.addBonusInputItemDataInfo", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Bonus Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamDataList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.bonusInputItemData.getBnParamDataList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Bonus Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamDataIsCpnyIdList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.bonusInputItemData.getBnParamDataIsCpnyIdList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Bonus Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamDataIsDeptNoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.bonusInputItemData.getBnParamDataIsDeptNoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Bonus Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamDataTwoList(Object obj) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.bonusInputItemData.getBnParamDataTwoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@Override
	public int checkAddPaBonusInputItemDataInfo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.checkAddPaBonusInputItemDataInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBonusInputItemDataPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getBonusInputItemDataPersonList(obj, -1, -1);
		return returnList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List getBonusInputItemDataPersonList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataPersonList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getBonusInputItemDataPersonList",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 批量插入保险输入项目数据(add Bonus Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addBonusInputItemDataInfo(Object obj) {
		try {
			this.insert("pa.bonusInputItemData.addBonusInputItemDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量插入保险输入项目数据(add Bonus Input Item Data Info)
	 * @param List
	 * @return
	 */
	
	public int addBonusInputItemOtherDataInfo(Object obj) {
		try {
			this.insert("pa.bonusInputItemData.addBonusInputItemOtherDataInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新保险输入项目数据(update Bonus Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateBonusInputItemDataInfoMonth(Object obj) {
		try {
			this.update("pa.bonusInputItemData.updateBonusInputItemDataInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量更新保险输入项目数据(update Bonus Input Item Data Info)
	 * @param List
	 * @return
	 */
	public int updateBonusInputItemDataOtherInfoMonth(Object obj) {
		try {
			this.update("pa.bonusInputItemData.updateBonusInputItemDataOtherInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getBonusInputItemDataPersonCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.getBonusInputItemDataPersonCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAddBonusPersonalInputList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAddBonusPersonalInputList(obj, -1, -1);
		return returnList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List getAddBonusPersonalInputList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getAddBonusPersonalInputList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusInputItemData.getAddBonusPersonalInputList",
								obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getAddBonusPersonalInputListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemData.getAddBonusPersonalInputListCnt",
													obj)), Integer.class);
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
	public int updateBonusInputItemDataPersonInfo(Object object) {
		try {
			this.update("pa.bonusInputItemData.updateBonusInputItemDataPersonInfo",
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
	public int addBonusInputItemDataPersonInfo(Object object) {
		try {
			this.insert("pa.bonusInputItemData.addBonusInputItemDataPersonInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
