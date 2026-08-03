package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;


import com.ait.is.dao.InsuranceSystemCalculateDao;
import com.ait.pa.dao.InsuranceCalculateDao;

import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   AIT5.5
 * Company:     AIT5.5
 * @fileName: InsuranceSystemCalculateDaoImpl.java
 * @Description:
 * @Create date: 2014-1-21 下午02:57:07
 * @Create by: heran(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class InsuranceSystemCalculateDaoImpl extends SqlMapClientSupport implements InsuranceSystemCalculateDao {
	/**
	 * 保险系统-基准管理 (Contract inquires)
	 * @param obj
	 * @return List
	 * @throws SQLException 
	 * @throws Exception
	 */
	@Override
	public List getInsuranceSystemInfoListForSearch(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.insuranceSystemCalculate.getInsuranceSystemInfoList", object);
		
	}

	@Override
	public List getVersionDateListBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.insuranceSystemCalculate.getVersionDateListBz", object);
	}

	@Override
	public List getModifyStandardSeriousBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.insuranceSystemCalculate.getModifyStandardSeriousBz", object);
	}
	/**
	 * 判断是否修改过版本（result==30 为修改过）
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public int ifUpdatedVersion(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer)this.queryForObject("is.insuranceSystemCalculate.ifUpdatedVersion", object);
	}
	
	/**
	 * 
	* @Title: getMaxManageCreateDate
	* @Description: 获取管理对象的最大年月
	* @param @param param
	* @param @return
	* @return String
	* @throws
	 */
	@Override
	public String getMaxManageCreateDate(Object object) throws SQLException {
		// TODO Auto-generated method stub   getMaxManageCreateDate
		return (String)this.queryForObject("is.insuranceSystemCalculate.getMaxManageCreateDate", object);
	}
	/**
	 * 
	 * TODO 判断是否已经核算
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public int selectPaBenFalgByFalg(Object object) throws SQLException {
		// TODO Auto-generated method stub  selectPaBenFalgByFalg
		return (Integer)this.queryForObject("is.insuranceSystemCalculate.selectPaBenFalgByFalg", object);
	}
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 生成版本
	* @author songshuangjiang   songshuangjiang@ait.net.cn 
	* @date 2012-4-23 下午05:39:32 
	* @version V1.0
	 */
	@Override
	public int createBenchmarkStandardVersionBz(Object object)
			throws SQLException {
		// TODO Auto-generated method stub
		int result = 1;
		try {
			this.delete("is.insuranceSystemCalculate.deleteExistMaxVersionDate");
			this.insert("is.insuranceSystemCalculate.createStandardNotSeriousVersion",object);
			this.insert("is.insuranceSystemCalculate.createStandardSeriousVersion",object);
			this.delete("is.insuranceSystemCalculate.deleteVersionIsNull");
			//commonSQLMapAdapter.delete("pa.ben.deleteExistMaxVersionDate");
			//commonSQLMapAdapter.insert("pa.ben.createStandardNotSeriousVersion", parameterObject);
			//commonSQLMapAdapter.insert("pa.ben.createStandardSeriousVersion", parameterObject);
			//commonSQLMapAdapter.delete("pa.ben.deleteVersionIsNull");
			
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
			throw new GlRuntimeException("createBenchmarkStandardVersion information Exception. ", e);
		}
	
		return result;
	}
	/**
	 * @return 
	 * 
	* @Title: freshPaBenManage
	* @Description:刷新基准
	* @param @param parameterObject
	* @param @throws GlRuntimeException
	* @return void
	* @throws
	 */
	@Override
	public void freshPaBenManageBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		 this.update("is.insuranceSystemCalculate.freshPaBenManage", object);
	}
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 删除版本号为空的数据
	* @author songshuangjiang   songshuangjiang@ait.net.cn 
	* @date 2012-4-23 下午05:39:32 
	* @version V1.0
	 */
	@Override
	public int deleteBenchmarkStandardBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		
		int result = 1;
		try {
			this.delete("is.insuranceSystemCalculate.deleteBenchmarkStandard1", object);
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
			throw new GlRuntimeException("updateBenchmarkStandard1 information Exception. ", e);
		}
		return result;
	}
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 重新生成当前版本
	* @author songshuangjiang   songshuangjiang@ait.net.cn 
	* @date 2012-4-23 下午05:39:32 
	* @version V1.0
	 */
	@Override
	public int updateBenchmarkStandardBz1(Object object) throws SQLException {
		// TODO Auto-generated method stub
		int result = 1;
		try {
			this.delete("is.insuranceSystemCalculate.insertBenchmarkStandard", object);
			//commonSQLMapAdapter.insert("pa.ben.insertBenchmarkStandard", parameterObject);
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
			throw new GlRuntimeException("updateBenchmarkStandard1 information Exception. ", e);
		}
		return result;
	}

	@Override
	public List getPaBenStandardNotSeriousBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.insuranceSystemCalculate.getPaBenStandardNotSerious", object);
	}

	@Override
	public List getPaBenStandardSeriousBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.insuranceSystemCalculate.getPaBenStandardSerious", object);
	}
	
	/**
	 * 
	 * TODO 数据生成+原数据清空
	 * @param parameterObject
	 * @throws GlRuntimeException
	 */
	@Override
	public void createDataToPaBenBaseBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSerious
			this.delete("is.insuranceSystemCalculate.deleteDataFromPaBenBase");
			this.insert("is.insuranceSystemCalculate.copyDataToPaBenBase", object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("createDataToPaBenBase information Exception. ", e);
		}finally{
			
		}
	}
	/**
	 * 
	 * TODO 从base表中删除人员信息
	 * @param parameterObject
	 * @throws GlRuntimeException
	 */
	@Override
	public void deletePaBenBaseWrongEmpBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		try {
			this.delete("is.insuranceSystemCalculate.deletePaBenBaseWrongEmp",object);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("deletePaBenBaseWrongEmpBz information Exception. ", e);
		}finally{
			
		}
	}
	/**
	 * 
	 * TODO 修改平均扣税工资
	 * @param param
	 * @throws GlRuntimeException
	 */
	@Override
	public void updatePaBenBaseAvgSalaryBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		try {
			this.delete("is.insuranceSystemCalculate.updatePaBenBaseAvgSalary");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("updatePaBenBaseAvgSalaryBz information Exception. ", e);
		}finally{
			
		}
	}
	/**
	 * 
	* @Title: getMaxYearMonthOfCompution
	* @Description: 最大核算月份
	* @param @param param
	* @param @return
	* @return String
	* @throws
	 */
	@Override
	public String getMaxYearMonthOfComputionBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		String resultString="";
		try {
			   resultString=(String)this.queryForObject("is.insuranceSystemCalculate.updatePaBenBaseAvgSalary");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getMaxYearMonthOfComputionBz information Exception. ", e);
		}finally{
			
		}
		return resultString;
	}
	
	/**
	 * 
	* @Title: afterComputationAffirm
	* @Description: 判断核算后的申请裁决情况
	* @param @param param
	* @param @return
	* @return String
	* @throws
	 */
	public String afterComputationAffirmBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		String result="";
		try {
			   result=(String)this.queryForObject("is.insuranceSystemCalculate.afterComputationAffirm");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("afterComputationAffirm information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	* @Title: paBenBaseNumOrderCount
	* @Description: 发令人数
	* @param @return
	* @param @throws GlRuntimeException
	* @return int
	* @throws
	 */
	public int paBenBaseNumOrderCountBz() throws SQLException {
		// TODO Auto-generated method stub
		int result=-1;
		try {
			   result=(Integer)this.queryForObject("is.insuranceSystemCalculate.paBenBaseNumOrderCount");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("afterComputationAffirm information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	* @Title: paBenBaseNumTrueOrderCount
	* @Description: 实际发令影响人数
	* @param @return
	* @param @throws GlRuntimeException
	* @return int
	* @throws
	 */
	public int paBenBaseNumTrueOrderCountBz() throws SQLException {
		// TODO Auto-generated method stub
		int result=-1;
		try {
			   result=(Integer)this.queryForObject("is.insuranceSystemCalculate.paBenBaseNumTrueOrderCount");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("afterComputationAffirm information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	 * TODO 获取未发令过去的人员信息
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public List getPaBenBaseNumOrderListBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		List result=null;
		try {
			   result=this.queryForList("is.insuranceSystemCalculate.getPaBenBaseNumOrderList");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumOrderList information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	@Override
	public boolean copyDataToPaBenManageBz(Map object) throws SQLException {
		// TODO Auto-generated method stub
		List<Map> list;
		Map<Object, Object> map = new HashMap<Object, Object>(); 
		boolean message = false;
		try {
			//获取对象管理和基数管理里面共同的人员
//			/list = commonSQLMapAdapter.executeQueryForMulti("pa.ben.getEmpidFromBaseAndManage");
			list=this.queryForList("is.insuranceSystemCalculate.getEmpidFromBaseAndManage");
			if (list != null && list.size() > 0) {
				String adminID = (String) object.get("adminID");//操作人
				for (int i = 0; i < list.size(); i++) {
					String empID = (String) list.get(i).get("EMPID");//职号
					String asy = list.get(i).get("AVG_SALARY").toString();
					String seq = (String) list.get(i).get("PA_BEN_MANAGE_SEQ");//序列
					double avgSalary = 0;
					if (asy != null) {
						avgSalary = Double.parseDouble(asy);
					}
					map.put("empID", empID);
					map.put("avgSalary", avgSalary);
					map.put("adminID", adminID);
					map.put("seq", seq);
					//发令
					//commonSQLMapAdapter.update("pa.ben.orderUpdatePaBenManageAvgSalary", map);
					this.update("is.insuranceSystemCalculate.getEmpidFromBaseAndManage",map);
				}
				message = true;
				//发令成功后，清空base表
				//commonSQLMapAdapter.delete("pa.ben.deletePaBenBase");
				this.delete("is.insuranceSystemCalculate.deletePaBenBase");
			}
			//commonSQLMapAdapter.commitTransation();
			return message;
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("copyDataToPaBenManage1 information Exception. ", e);
		}finally{
			
		}
	}
	/**
	 * 
	* @Title: paBenBaseNumOrderCount
	* @Description: 发令人数
	* @param @return
	* @param @throws GlRuntimeException
	* @return int
	* @throws
	 */
	@Override
	public int paBenBaseNumOrderCountBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		int result=-1;
		try {
			   result=(Integer)this.queryForObject("is.insuranceSystemCalculate.paBenBaseNumOrderCount1");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("paBenBaseNumOrderCount1 information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	* @Title: paBenBaseNumTrueOrderCount
	* @Description: 实际发令影响人数
	* @param @return
	* @param @throws GlRuntimeException
	* @return int
	* @throws
	 */
	@Override
	public int paBenBaseNumTrueOrderCountBz(Object object) throws SQLException {
		// TODO Auto-generated method stub  paBenBaseNumTrueOrderCount1
		int result=-1;
		try {
			   result=(Integer)this.queryForObject("is.insuranceSystemCalculate.paBenBaseNumTrueOrderCount1");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("paBenBaseNumOrderCount1 information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	 * TODO 调用存储：PA_BEN_BASE_CHECK_CAL
	 * @param parameterObject
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public Object callPaBenBaseCalBz(Object object) throws SQLException {
		// TODO Auto-generated method stub  paBenBaseNumTrueOrderCount1
		Object result=-1;
		try {
			   result=(Integer)this.queryForObject("is.insuranceSystemCalculate.callPaBenBaseCheckCal");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("paBenBaseNumOrderCount1 information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	 * TODO 调用存储：PA_BEN_BASE_CHECK_CAL
	 * @param parameterObject
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public Object callPaBenBaseCheckCalBz(Object object) throws SQLException {
		// TODO Auto-generated method stub  paBenBaseNumTrueOrderCount1
		Object result=-1;
		try {
			   result=(Integer)this.queryForObject("is.insuranceSystemCalculate.callPaBenBaseCheckCal");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("callPaBenBaseCheckCal information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	/**
	 * 
	 * TODO 获取（扣税工资类别）详细信息
	 * @param param
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public List getPaBenBaseNumCheckListDetailBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.insuranceSystemCalculate.callPaBenBaseCheckCal");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("callPaBenBaseCheckCal information Exception. ", e);
		}finally{
			
		}
		return result;
		
		
	}
	/**
	 * 
	 * TODO 获取CheckList
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public List getPaBenBaseNumCheckListBz() throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.insuranceSystemCalculate.getPaBenBaseNumCheckList");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumCheckList information Exception. ", e);
		}finally{
			
		}
		return result;
		
		
	}
/*	*//**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 点击修改出现可修改文本(不修改而返回)
	* @author songshuangjiang   songshuangjiang@ait.net.cn 
	* @date 2012-4-24 下午03:42:37 
	* @version V1.0
	 *//*
	@Override
	public List backPaBenBaseNumUpdateBz() throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.insuranceSystemCalculate.backPaBenBaseNumUpdate");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumCheckList information Exception. ", e);
		}finally{
			
		}
		return result;
		
		
	}*/
	
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 点击修改出现可修改文本(不修改而返回)
	* @author songshuangjiang   songshuangjiang@ait.net.cn 
	* @date 2012-4-24 下午03:42:37 
	* @version V1.0
	 */
	@Override
	public int backPaBenBaseNumUpdateBz() throws SQLException {
		// TODO Auto-generated method stub
		int result;
		try {
			   result=(Integer) this.update("is.insuranceSystemCalculate.backPaBenBaseNumUpdate");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumCheckList information Exception. ", e);
		}finally{
			
		}
		return result;
		
		
	}
	/**
	 * 
	 * TODO 获取基本显示信息List
	 * @return
	 * @throws GlRuntimeException
	 */
	@Override
	public List getPaBenBaseNumListBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.insuranceSystemCalculate.getPaBenBaseNumList");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumList information Exception. ", e);
		}finally{
			
		}
		return result;
		
		
	}
	
}
