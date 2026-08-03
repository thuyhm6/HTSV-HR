package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;


import com.ait.is.dao.AccumulationFundDao;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class AccumulationFundDaoImpl extends SqlMapClientSupport implements AccumulationFundDao {

	//公积金--基准管理
	@Override
	public List getCPFBenchmarkManagementForSearch(Object object)
			throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.accumulationfund.getCPFBenchmarkManagementForSearch", object);
	}

	//公积金--基准管理[版本日期]
	@Override
	public List getVersionDateListBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.accumulationfund.getVersionDateListBz", object);
	}

	@Override
	public int deleteBenchmarkStandardBz(Object object) throws SQLException {
		int result = 1;
		try {
			this.delete("is.accumulationfund.deleteBenchmarkStandard", object);
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
			throw new GlRuntimeException("updateBenchmarkStandard1 information Exception. ", e);
		}
		return result;
	}

	@Override
	public int updateBenchmarkStandardBz(Object object) throws SQLException {
		int result = 1;
		try {
			this.delete("is.accumulationfund.insertBenchmarkStandard", object);
			//commonSQLMapAdapter.insert("pa.ben.insertBenchmarkStandard", parameterObject);
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
			throw new GlRuntimeException("updateBenchmarkStandard1 information Exception. ", e);
		}
		return result;
	}

	//公积金--基准管理[当前]
	@Override
	public List getCPFBenchmarkManagementForSearchDq(Object object)
			throws SQLException {
		// TODO Auto-generated method stub
		return this.queryForList("is.accumulationfund.getCPFBenchmarkManagementForSearchDq", object);
	}

	// 判断是否修改过版本（result==5 为修改过）
	@Override
	public int ifUpdatedVersion(Object object) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer)this.queryForObject("is.accumulationfund.ifUpdatedVersion", object);
	}

	//对象最大年月
	@Override
	public String getMaxManageCreateDate(Object object) throws SQLException {
		return (String)this.queryForObject("is.accumulationfund.getMaxManageCreateDate", object);
	}

	// 判断是否已经核算
	@Override
	public int selectPaBenFalgByFalg(Object object) throws SQLException {
		// TODO Auto-generated method stub  selectPaBenFalgByFalg
		return (Integer)this.queryForObject("is.accumulationfund.selectPaBenFalgByFalg", object);
	}
	//生成版本
	@Override
	public int createBenchmarkStandardVersion(Object object)
			throws SQLException {
		// TODO Auto-generated method stub
		int result = 1;
		try {
			this.delete("is.accumulationfund.deleteExistMaxVersionDate");
			this.insert("is.accumulationfund.createStandardVersion",object);
			this.delete("is.accumulationfund.deleteVersionIsNull");
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
			throw new GlRuntimeException("createBenchmarkStandardVersion information Exception. ", e);
		}
	
		return result;
	}
	
	@Override
	public void freshPaBenManage(Object object) throws SQLException {
		// TODO Auto-generated method stub
		 this.update("is.accumulationfund.freshPaBenManage", object);
	}
	
	// 数据生成+原数据清空
	@Override
	public int createDataToPaBenBaseBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSerious
			this.delete("is.accumulationfund.deleteDataFromPaBenBase");
			this.insert("is.accumulationfund.copyDataToPaBenBase", object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			return 0;
			//throw new GlRuntimeException("createDataToPaBenBase information Exception. ", e);
		}
		return 1;
		
	}
	//TODO 获取CheckList
	@Override
	public List getPaBenBaseNumCheckListBz() throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.accumulationfund.getPaBenBaseNumCheckList");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumCheckList information Exception. ", e);
		}finally{
			
		}
		return result;
	}
	
	// 点击修改出现可修改文本(不修改而返回)
	@Override
	public int backPaBenBaseNumUpdateBz() throws SQLException {
		int result;
		try {
			   result=(Integer) this.update("is.accumulationfund.backPaBenBaseNumUpdate");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumCheckList information Exception. ", e);
		}finally{		
		}
		return result;
	}
	
	//TODO 获取基本显示信息List
	@Override
	public List getPaBenBaseNumListBz(Object object) throws SQLException {
		// TODO Auto-generated method stub
		List result;
		try {
			   result=this.queryForList("is.accumulationfund.getPaBenBaseNumList",object);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("getPaBenBaseNumList information Exception. ", e);
		}finally{
			
		}
		return result;
		
		
	}

	@Override
	public void deleteCPFBaseManagement(Object object) throws SQLException {
		
		this.delete("is.accumulationfund.deleteCPFBaseManagement", object);
	}

	@Override
	public void allowPaBenBaseNumUpdate(Object object) throws SQLException {
		this.update("is.accumulationfund.allowPaBenBaseNumUpdate", object);
		
	}
	//throws SQLException;
	@Override
	public List getupdateCPFBaseManagement(Object object) throws SQLException{
		return this.queryForList("is.accumulationfund.getupdateCPFBaseManagement" ,object);
	}

	//公积金--基数管理 (修改保存)
	@Override
	public void updatePaBenBaseAvgSalary(Object object) throws SQLException {
		this.update("is.accumulationfund.updatePaBenBaseAvgSalary", object);
		
	}
	//公积金--基数管理 (计算工资1)
	@Override
	public String callPaBenBaseCal(LinkedHashMap paramMap) {
		
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("is.accumulationfund.callPaBenBaseCal", paramMap) ;
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}

	@Override
	public String callPaBenBaseCheckCal(LinkedHashMap param) {
        String returnString = "" ;
		
		try {
			param.put("message", "") ;
			this.insert("is.accumulationfund.callPaBenBaseCheckCal", param) ;
			returnString = ObjectUtils.toString(param.get("news")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	//查找最大月
	@Override
	public String getMaxYearMonthOfComputionBz(Object object)
			throws SQLException {
		return (String)this.queryForObject("is.accumulationfund.getMaxYearMonthOfComputionBz", object);
	}

	//判断核算后的申请裁决情况
	@Override
	public String afterComputationAffirmBz(Object object) throws SQLException {
		return (String)this.queryForObject("is.accumulationfund.afterComputationAffirmBz", object);
	}

	//发令人数 
	@Override
	public int paBenBaseNumOrderCount() throws SQLException {
		return (Integer)this.queryForObject("is.accumulationfund.paBenBaseNumOrderCount");
	}

	//实际发令影响人数
	@Override
	public int paBenBaseNumTrueOrderCount() throws SQLException {
		return (Integer)this.queryForObject("is.accumulationfund.paBenBaseNumTrueOrderCount");
	}

	//如果发令有剩余，则显示剩余人员信息
	@Override
	public List getPaBenBaseNumOrderList(Object object) throws SQLException {
		return this.queryForList("is.accumulationfund.getPaBenBaseNumOrderList", object);
	}

	//要发令的集合
	@Override
	public List<Map> getEmpidFromBaseAndManage(Object object) {
		try {
			return this.queryForList("is.accumulationfund.getEmpidFromBaseAndManage", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//发令
	@Override
	public void orderUpdatePaBenManageAvgSalary(Object object) {
		//发令
		 try {
			this.update("is.accumulationfund.freshPaBenManage", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	//发令成功后，清空base表
	@Override
	public void deletePaBenBase() {
		try {
			this.delete("is.accumulationfund.deletePaBenBase");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//清空临时表：PA_BENHS_BASE_IMP  @author wendi
	@Override
	public void deletePaBenBaseImp() {
			try {
				this.delete("is.accumulationfund.deletePaBenBaseImp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	//查询所有的基数管理临时表数据
	@Override
	public List<Map> getPaBehsBaseImplList() {
		try {
			return this.queryForList("is.accumulationfund.getPaBehsBaseImpList");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//更新基数管理数据
	@Override
	public void updatePaBenhsBase(Object object) {
		//发令
		 try {
			this.update("is.accumulationfund.updatePaBenhsBase", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertPaBenhsBase(Object object){
		 try {
		this.insert("is.accumulationfund.insertPaBenhsBase", object);
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	@Override
	public List<Map> checkPaBenhsBase(Object object){
		 try {
			 return this.queryForList("is.accumulationfund.checkPaBenhsBase", object);
		 }catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}

	@Override
	public List findPIdByParam(Object object) {
		try {
			 return this.queryForList("is.accumulationfund.findPIdByParam", object);
		 }catch (Exception e) {
			 e.printStackTrace();
			 return null;
	     }
	}
	
}
