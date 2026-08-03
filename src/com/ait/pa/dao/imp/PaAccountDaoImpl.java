package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaAccountDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
@SuppressWarnings("unchecked")
public class PaAccountDaoImpl extends SqlMapClientSupport implements PaAccountDao {
	/**
	 * 取得所有人员账户信息列表
	 * @param List
	 * @return
	 */
	public List getPaAccountList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaAccountList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 取得所有人员账户信息列表
	 * @param List
	 * @return
	 */
	public List getPaAccountList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		Map object = (LinkedHashMap)obj;
		try {
			if("1".equals(object.get("flag"))){
				if (currentPage > -1 && pageSize > -1){
				    returnList = this.queryForList("pa.account.getPaAccountHistoryList", obj, currentPage, pageSize);
			    }else{
				    returnList = this.queryForList("pa.account.getPaAccountHistoryList", obj);
			    }
			}else{
			    if (currentPage > -1 && pageSize > -1){
				    returnList = this.queryForList("pa.account.getPaAccountList", obj, currentPage, pageSize);
			    }else{
				    returnList = this.queryForList("pa.account.getPaAccountList", obj);
			    }
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有人员账户信息总数
	 * @param List
	 * @return
	 */
	public int getPaAccountCnt(Object obj) {
		int returnInt = 0 ;
		Map object = (LinkedHashMap)obj;
		try {
			if("1".equals(object.get("flag"))){
			    returnInt = 
				     NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getPaAccountHistoryCnt", obj)), Integer.class) ;
			}else{
				returnInt = 
					 NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getPaAccountCnt", obj)), Integer.class) ;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 更新员工工资计算标志
	 * @param List
	 * @return
	 */
	public int updatePaCalcFlagByPersonId(Object obj) {
		try {
			this.update("pa.account.updatePaCalcFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 修改工资账户信息
	 * @param List
	 * @return
	 */
	@Override
	public int updatePaAccountInfo(Object objcet) {
		try {
			this.update("pa.account.updatePaAccountInfo", objcet) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
	}
	/**
	 * 查询账户信息根据PERSONID
	 * @param List
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public Object getPaAccountInfoByPersonid(Object objcet) throws SQLException {
		return  this.queryForObject("pa.account.getPaAccountInfoByPersonid", objcet);
	}
	public int savePaAccountInfo(Object object) throws SQLException{
		  this.insert("pa.account.savePaAccountInfo", object);
		  return 1;
	}
	/**
	 * 修改银行和卡号
	 * @param List
	 * @return
	 */
	@Override
	public Object getPaAccountCtrollerInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getPaAccountList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
		
	}
	
	/**
	 * 取得所有人员账户信息列表
	 * @param List
	 * @return
	 */
	public List getBankList(Object obj) {
		List returnList = new ArrayList() ;
		try{
			returnList =  this.queryForList("pa.account.getBankList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有银行区域（支行）信息列表
	 * @param List
	 * @return
	 */
	public List getBankBranchList(Object obj) {
		List returnList = new ArrayList() ;
		try{
			returnList =  this.queryForList("pa.account.getBankBranchList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询出工资对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:44:09 
	* @version V1.0
	 */
	public List getPaSalaryObjectList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaSalaryObjectList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查询出工资对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:44:09 
	* @version V1.0
	 */
	public List getPaSalaryObjectList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.account.getPaSalaryObjectList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.account.getPaSalaryObjectList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查询出工资对象列表数量
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:44:09 
	* @version V1.0
	 */
	@Override
	public int getPaSalaryObjectCnt(Object obj) throws Exception {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getPaSalaryObjectCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:03:05 
	* @version V1.0
	 */
	@Override
	public int updatePaSalCalcFlagByPersonId(Object obj) throws Exception {
		try {
			this.update("pa.account.updatePaSalCalcFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}

	/**
	 * 查询员工状态
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-6 下午03:58:09 
	* @version V1.0
	 */
	@Override
	public List getEmpOfficeList(LinkedHashMap paramMap) throws Exception {
		return this.queryForList("sys.basicMaintenance.getParamCodeListByCpnyID", paramMap);
	}

	/**
	 * 计算对象(奖金,工资,保险)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-15 下午11:21:52 
	* @version V1.0
	 */
	@Override
	public String calculateObject(LinkedHashMap paramMap) throws Exception {
		String returnStr = "" ;
		
		try {
			//this.insert("pa.account.salaryCalculationObject", paramMap) ;
			this.insert("pa.account.copyLastMonthData", paramMap) ;
			String calType=paramMap.get("CALTYPE").toString();
			if("BN".equals(calType)){
				returnStr = "zxc.paBonus.title.SUCCESS_EATABLISH_BONUS_OBJECT" ;
			}else if("PA".equals(calType)){
				returnStr = "zxc.paSalary.title.SUCCESS_EATABLISH_SALARY_OBJECT" ;
			}else{
				returnStr = "zxc.paInsurance.title.SUCCESS_EATABLISH_INSURANCE_OBJECT" ;
			}
		} catch (SQLException e) {	
			returnStr = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnStr ;
	}

	@Override
	public Object getPaObjectCtrollerInfo(LinkedHashMap paramMap)
			throws Exception {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getPaSalaryObjectList(paramMap) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj;
	}

	@Override
	public int updatePaObjectInfo(LinkedHashMap paramMap) throws Exception {
		try {
			this.update("pa.account.updatePaObjectInfo", paramMap) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List getPersonType(LinkedHashMap paramMap){
		List list = new ArrayList();
		try {
			list = this.queryForList("pa.account.getPersonType",paramMap) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List getPositionList(LinkedHashMap paramMap){
		List list = new ArrayList();
		try {
			list = this.queryForList("pa.account.getPositionList",paramMap) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List getPersonAllowance(LinkedHashMap paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.account.getPersonAllowance", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public List getPersonAllowance(LinkedHashMap paramMap, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.account.getPersonAllowance", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.account.getPersonAllowance", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取职责津贴个人参数个数（get Pa Basic Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPersonAllowanceCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getPersonAllowanceCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public Object getPaAllowanceSelf(LinkedHashMap paramMap){
		LinkedHashMap returnObj = new LinkedHashMap();
		try {
			returnObj = (LinkedHashMap) this.queryForObject("pa.account.getPaAllowanceSelf",paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnObj;
	}
	@Override
	public int updatePaAllowanceSelfInfo(LinkedHashMap paramMap){
		try {
			this.update("pa.account.updatePaAllowanceSelfInfo", paramMap) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 根据Id号查找职责津贴标准
	 */
	@Override
	public Object getPaAllowance(LinkedHashMap paramMap){
		LinkedHashMap returnObj = new LinkedHashMap();
		try {
			returnObj = (LinkedHashMap) this.queryForObject("pa.account.getPaAllowance",paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnObj;
	}
	/**
	 * 新增职责津贴标准
	 */
	@Override
	public int insertPaAllowanceInfo(LinkedHashMap paramMap){
		try {
			this.insert("pa.account.savePaAllowanceInfo", paramMap);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  return 0;
	}
	/**
	 * 更新职责津贴标准
	 */
	@Override
	public int updatePaAllowanceInfo(LinkedHashMap paramMap){
		try {
			this.update("pa.account.updatePaAllowanceInfo", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	@Override
	public List getAllowance(LinkedHashMap paramMap, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.account.getAllowance", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.account.getAllowance", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@Override
	public List getAllowance(LinkedHashMap paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.account.getAllowance", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public int getAllowanceCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getAllowanceCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int deletePaAllowanceInfo(LinkedHashMap paramMap){
			try {
				this.delete("pa.account.deletePaAllowanceInfo", paramMap) ;
				return 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
	}
	
	public List getEvaluationDataImportResultList(LinkedHashMap paramMap,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.account.getEvaluationDataImportResultList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.account.getEvaluationDataImportResultList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	public List getEvaluationDataImportResultList(LinkedHashMap paramMap){
		return this.getEvaluationDataImportResultList(paramMap,-1,-1);
	}

	public int getEvaluationDataImportResultListCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getEvaluationDataImportResultListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	public int getEvaluationDataImportErrCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getEvaluationDataImportErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 评价数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	public String importPaAllowanceExcelTempExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.account.importPaAllowanceExcelTempExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**
	 * 职责津贴个人数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	public String importPaAllowanceExcelSelfExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.account.importPaAllowanceExcelSelfExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**
	 * 工资计算对象数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	public String importPaAccountExcelExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.account.importPaAccountExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	
	
	
	public List getPaAccountDataImportResultList(LinkedHashMap paramMap,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.account.getPaAccountDataImportResultList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.account.getPaAccountDataImportResultList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	public List getPaAccountDataImportResultList(LinkedHashMap paramMap){
		return this.getPaAccountDataImportResultList(paramMap, -1, -1);
	}
	
	public List getEvaluationDataImportResultSelfList(LinkedHashMap paramMap,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.account.getEvaluationDataImportResultSelfList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.account.getEvaluationDataImportResultSelfList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	public List getEvaluationDataImportResultSelfList(LinkedHashMap paramMap){
		return this.getEvaluationDataImportResultSelfList(paramMap, -1, -1);
	}

	public int getEvaluationDataImportResultListSelfCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getEvaluationDataImportResultListSelfCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	public int getEvaluationDataImportErrSelfCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getEvaluationDataImportErrSelfCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	public int getPaAccountDataImportResultListCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getPaAccountDataImportResultListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	public int getPaAccountDataImportResultListErrCnt(LinkedHashMap paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.account.getPaAccountDataImportResultListErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * sy工资计算状态页面修改试用支付比例
	 * @param request
	 * @return
	 */
	public int updateC_PROB_PAY_RAT(LinkedHashMap paramMap){
		try {
			this.update("pa.account.updateC_PROB_PAY_RAT", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
}
