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

import com.ait.pa.dao.PaAvgItemDao;
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
public class PaAvgItemDaoImpl extends SqlMapClientSupport implements PaAvgItemDao {
	
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
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 */
	public int deletePaBasicItemParamInfoAll(String item_no) throws SQLException {
	    try {
		    this.delete("pa.basicItem.deletePaBasicItemParamInfoAll", item_no) ;
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
	

}
