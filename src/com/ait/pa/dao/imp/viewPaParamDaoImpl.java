package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.viewPaParamDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class viewPaParamDaoImpl extends SqlMapClientSupport implements
		viewPaParamDao {
	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemParamList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList(
					"pa.viewpaparam.getPaInputItemParamList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@Override
	public void cleanTempListById(Object object) throws SQLException {

		this.delete("pa.viewpaparam.cleanTempListById", object);

	}

	@Override
	public void checkTempListById(Object object) throws SQLException {

		this.delete("pa.viewpaparam.checkTempListById", object);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List getImportCompareList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();

		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.viewpaparam.getImportCompareList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.viewpaparam.getImportCompareList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getImportCompareList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getImportCompareList(obj, -1, -1);

		return returnList;
	}

	@Override
	public int getImportCompareListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(
				ObjectUtils.toString(this.queryForObject(
						"pa.viewpaparam.getImportCompareListCnt", obj)),
				Integer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getImportCompareNum(Object obj) {
		Object returnList = new Object();
		try {

			returnList = this.queryForObject(
					"pa.viewpaparam.getImportCompareNum", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@Override
	public void setPaParam(Object object) throws SQLException {

		this.insert("pa.viewpaparam.setPaParam", object);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.monthPersonCountInfoList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonIncreaseList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.monthPersonIncreaseList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonDecreaseList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.monthPersonDecreaseList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.monthPersonCountInfoSonList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPerPaInfo(Object obj) {
		Object returnList = new Object();
		try {

			returnList = this
					.queryForObject("pa.viewpaparam.getPerPaInfo", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public String savePaResult(LinkedHashMap paramMap) {
		String returnString = "";

		try {
			paramMap.put("message", "");
			this.insert("pa.viewpaparam.savePaResult", paramMap);

			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}

		return returnString;
	}

	@SuppressWarnings("unchecked")
	public String updatePaResult(LinkedHashMap paramMap) {
		String returnString = "";

		try {
			paramMap.put("message", "");
			this.update("pa.viewpaparam.updatePaResult", paramMap);

			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}

		return returnString;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getItemList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.getItemList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewVerificationList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewVerificationList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewPaResultList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.viewPaResultList",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewPaResultHISTORYList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaResultHISTORYList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewPaResultSSTList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaResultSSTList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewPaResultListSum(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaResultListSum", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
//历史数据
	@SuppressWarnings("unchecked")
	@Override
	public List viewPaResultHISTORYListSum(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaResultHISTORYListSum", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewPaResultSSTListSum(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaResultSSTListSum", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得工资支付计划信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.detailPersonCountInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得工资单数据列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmpSalaryInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			this.insert("pa.viewpaparam.callForEmpSalaryInfo", obj);
			returnList = this.queryForList(
					"pa.viewpaparam.getEmpSalaryInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得工资详细明细人员数
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPaDetailEmpInfoByPersonId(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"pa.viewpaparam.getPaDetailEmpInfoByPersonId", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 取得工资详细明细下方数据
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaDetailInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			this.insert("pa.viewpaparam.callForSalaryDetailInfo", obj);
			returnList = this.queryForList(
					"pa.viewpaparam.getPaDetailInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得月工资明细的右边数据
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailMonthCountInfoRight(Object obj) {
		List returnList = new ArrayList();
		try {
			Map<String, Object> paramMap = (Map<String, Object>) obj ;
			paramMap.put("ITEM_TYPE", 1);
			this.insert("pa.viewpaparam.callDetailMonthCountInfoRight", obj);
			paramMap.put("ITEM_TYPE", 2);
			this.insert("pa.viewpaparam.callDetailMonthCountInfoRight", obj);
			paramMap.put("ITEM_TYPE", 3);
			this.insert("pa.viewpaparam.callDetailMonthCountInfoRight", obj);
			returnList = this.queryForList(
					"pa.viewpaparam.detailMonthYearCountInfoRight", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得年工资明细的右边数据
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailYearCountInfoRight(Object obj) {
		List returnList = new ArrayList();
		try {
			Map<String, Object> paramMap = (Map<String, Object>) obj ;
			paramMap.put("ITEM_TYPE", 1);
			this.insert("pa.viewpaparam.callDetailYearCountInfoRight", obj);
			paramMap.put("ITEM_TYPE", 2);
			this.insert("pa.viewpaparam.callDetailYearCountInfoRight", obj);
			paramMap.put("ITEM_TYPE", 3);
			this.insert("pa.viewpaparam.callDetailYearCountInfoRight", obj);
			returnList = this.queryForList(
					"pa.viewpaparam.detailMonthYearCountInfoRight", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 取得个人别核对信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoRight(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.detailPersonCountInfoRight", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 *调用存储 把当前人的工资项目生成，而且删除之前的 插入的为临时表
	 * 
	 * @param List
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int callPayP(Object obj) {
		int rt = 1;
		try {
			this.insert("pa.viewpaparam.CALL_PA_PERSONAL_ITEM_VIEW", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			rt = 0;
			e.printStackTrace();
		}

		return rt;

	}

	/**
	 * 个人别工资核对 查处工资条件内的人员LIST
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoLeft(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.detailPersonCountInfoLeft", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 年工资明细左边数据
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailYearCountInfoLeft(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.detailYearCountInfoLeft", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void callProForPayMonthDif(Object object) throws SQLException {

		this.insert("pa.viewpaparam.callProForPayMonthDif", object);

	}

	/**
	 * 取得工资支付计划信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List detailItemCountInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.detailItemCountInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List itemValueInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.itemValueInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List checkViewPaResult(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.checkViewPaResult",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewDeptPaResultList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewDeptPaResultList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewDeptPaResultListSum(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewDeptPaResultListSum", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewDeptPaResultSSTList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewDeptPaResultSSTList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getLastMonthPAY_SCHEDULE_NO(Object obj) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"pa.viewpaparam.getLastMonthPAY_SCHEDULE_NO", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList0(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList0", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList1", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList2(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList2", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList3(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList3", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList4(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList4", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList5(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList5", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewResultConfirmSonList6(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmSonList6", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List paEmpAccount(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.paEmpAccount", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getParamList(Object obj, String target) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam."+target, obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 工资条员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoForEmpSalaryInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"pa.viewpaparam.getPersonalInfoForEmpSalaryInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object paOpenFlag(Object obj) {
		Object returnList = new Object();
		try {

			returnList = this.queryForObject("pa.viewpaparam.paOpenFlag", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List paPayScheduleNoByPersonId(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.paPayScheduleNoByPersonId", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList2Right(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmList2Right", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList2Bottom(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmList2Bottom", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int callProForPayMonthDifItem(Object object) {
		int result = 1;
		try {
			this.insert("pa.viewpaparam.callProForPayMonthDifItem", object);
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList3Right(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewResultConfirmList3Right", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int callItemList(Object obj) {
		int returnnum = 1;
		try {
			this.insert("pa.viewpaparam.callItemList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			returnnum = 0;
		}
		return returnnum;
	}

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaArSummarySearchList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchLowList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaArSummarySearchLowList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int callPaArSummarySearch(Object object) {
		int result = 1;
		try {
			this.insert("pa.viewpaparam.callPaArSummarySearch", object);
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchListSST(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaArSummarySearchListSST", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/*
	 * 获取所有的项目字段 (non-Javadoc)
	 * 
	 * @see com.ait.pa.dao.viewPaParamDao#viewPaArSummaryList(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List viewPaArSummaryList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.viewPaArSummaryList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/*
	 * 获取所有的项目字段 (non-Javadoc)
	 * 
	 * @see com.ait.pa.dao.viewPaParamDao#viewPaArSummaryList(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List exportPayDetailTxtReport(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.exportPayDetailTxtReport", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 年工资明细
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayScheduleList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.getPayScheduleList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List dayPersonCountInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"pa.viewpaparam.dayPersonCountInfoList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPayDetailList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.viewpaparam.getPayDetailList",obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public String getPayScheduleArDate(Object obj) {
		String returnStr = "";
		try {
			returnStr = (String)this.queryForObject("pa.viewpaparam.getPayScheduleArDate",obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	
	@Override
	public void updateArSummaryHTSV(Object obj, String target) throws SQLException {
		this.update("pa.viewpaparam."+target, obj);
	}
}
