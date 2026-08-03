package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.InsurancePersonnelDao;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsurancePersonnelDaoImpl.java
 * @Description:
 * @Create date: 2012-2-10 下午06:17:25
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class InsurancePersonnelDaoImpl extends SqlMapClientSupport implements InsurancePersonnelDao {
	
	/**
	 * 取得所有参保人员信息列表（get Insurance Personnel Info）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getInsurancePersonnelInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getInsurancePersonnelList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有参保人员信息列表（get Insurance Personnel List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePersonnelList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getInsurancePersonnelList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有参保人员信息列表（get Insurance Personnel List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePersonnelList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insurancePersonnel.getInsurancePersonnelList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insurancePersonnel.getInsurancePersonnelList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有参保人员信息总数（get Insurance Personnel Cnt）
	 * @param List
	 * @return
	 */
	public int getInsurancePersonnelCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getInsurancePersonnelCnt", obj)), Integer.class) ;
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
	public int updateIsCalcFlagByPersonId(Object obj) {
		try {
			this.update("pa.insurancePersonnel.updateIsCalcFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 插入参保人员信息（add Insurance Personnel Info）
	 * @param List
	 * @return
	 */
	@Override
	public int updateInsurancePersonnelInfo(Object object) {
		
		try {
			this.update("pa.insurancePersonnel.updateInsurancePersonnelInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 验证添加参保人员（check Add Insurance Personnel Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 
	@Override
	public int checkAddInsurancePersonnelInfo(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"pa.insurancePersonnel.checkAddInsurancePersonnelInfo",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}*/

	/**
	 * 取得所有参保人员信息列表（get Insurance Personnel List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceObjectList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getInsuranceObjectList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有参保人员信息列表（get Insurance Personnel List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceObjectList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insurancePersonnel.getInsuranceObjectList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insurancePersonnel.getInsuranceObjectList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有参保人员信息总数（get Insurance Personnel Cnt）
	 * @param List
	 * @return
	 */
	public int getInsuranceObjectListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getInsuranceObjectListCnt", obj)), Integer.class) ;
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
	* @date 2013-9-5 下午02:18:41 
	* @version V1.0
	 */
	@Override
	public int updateInsCalcFlagByPersonId(Object obj)
			throws Exception {
		try {
			this.update("pa.insurancePersonnel.updateInsCalcFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	@Override
	public int updateInsCalcGJJFlagByPersonId(Object obj)
			throws Exception {
		try {
			this.update("pa.insurancePersonnel.updateInsCalcGJJFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object getInsuranceObjectInfo(LinkedHashMap paramMap)
			throws Exception {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getInsuranceObjectList(paramMap) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateInsuranceObjectInfo(LinkedHashMap paramMap) {
		try {
			this.update("pa.insurancePersonnel.updateInsCalcFlagByPersonId", paramMap) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 删除参保人员信息（delete Insurance Personnel Info）
	 * @param List
	 * @return
	*/
	@Override
	public int deleteInsurancePersonnelInfo(Object obj) {
		try {
			this.delete("pa.insurancePersonnel.deleteInsurancePersonnelInfo", obj) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	} 
	
	/**
	 * 初始化保险计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int initInsCalcObject(LinkedHashMap paramMap) throws Exception {
		try {
			this.delete("pa.insurancePersonnel.deleteFundCalcObjectPre", paramMap);
			this.insert("pa.insurancePersonnel.initFundCalcObjectFromLastMonth", paramMap) ;
			return 1;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaLockFlagByMonth(LinkedHashMap paramMap)throws Exception {
		Integer flag = 0;
		flag = (Integer)this.queryForObject("pa.insurancePersonnel.getPaLockFlagByMonth", paramMap);
		if(flag==null){
			flag = 0;
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List getInsCalcObjectTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getInsCalcObjectTempList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getInsCalcObjectTempList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insurancePersonnel.getInsCalcObjectTempList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insurancePersonnel.getInsCalcObjectTempList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getInsCalcObjectTempCnt(Object obj) {
		int returnInt = 0 ;	
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getInsCalcObjectTempCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getInsCalcObjectTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getInsCalcObjectTempErrorCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 单条删除保险计算对象( delete insurance object)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delInsCalcObject(Object object) throws Exception {
		Boolean flag = true;
		this.delete("pa.insurancePersonnel.deleteInsurancePersonnelInfo", object);
		return flag;
	}
	
	/**
	 * 批量保存导入的保险计算对象数据(add insurance object data of import)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addInsObjectDataImport(List list) throws Exception {
		this.insertForList("pa.insurancePersonnel.addInsObjectDataImport", list);
		this.deleteForList("pa.insurancePersonnel.delInsObjectDataImportBatch", list);
	}
	
	/**
	* 更新导入的保险计算对象的check结果(update insurance object data of import for check result)
	* 
	* @param object
	* @return
	* @throws Exception
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsObjectDataCheckFlag(List list) throws Exception {
	   int flag = 0;
	   this.updateForList("pa.insurancePersonnel.updateInsObjectDataCheckFlag", list);
	   return flag;
	}
	
	/**
	 * 更新导入的保险计算对象的check结果(update insurance object data of import for check result)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateInsObjectDataCheckResult(Object object) throws Exception {
		int flag = 0;
		this.update("pa.insurancePersonnel.updateInsObjectDataCheckResult", object);
		return flag;
	}
	
	
	@Override
	public int updateInsObjectDataFormalResult(Object object) throws Exception {
		int flag = 0;
		this.update("pa.insurancePersonnel.updateInsObjectDataFormalResult", object);
		return flag;
	}
	/**
	  * 取得正式表或者临时表里是否已有相同数据存在或者重复数据
	  * @param request
	  * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getInsObjectInfoExistList(Object obj) {
	  LinkedHashMap paramMap = (LinkedHashMap)obj;
	  String checkType = paramMap.get("CHECK_TYPE")!=null?paramMap.get("CHECK_TYPE").toString():"TEMP";
	  List returnList = new ArrayList() ;
	  try {
		  if(checkType!=null && "TEMP".equals(checkType)){//验证临时表里是否有重复的人员
			  returnList = this.queryForList("pa.insurancePersonnel.getInsObjectInfoExistTempList", paramMap);
		  }else if(checkType!=null && "TEMP_NO".equals(checkType)){//验证正式表里是否已经存在此人数据
			  returnList = this.queryForList("pa.insurancePersonnel.getInsObjectInfoExistList", paramMap);
		  }else if(checkType!=null && "EMPID".equals(checkType)){//验证该法人是否存在此工号
			  returnList = this.queryForList("pa.insurancePersonnel.getEmpidInfoExistList", paramMap);
		  }
	  } catch (SQLException e) {			
		  e.printStackTrace();
	  }
	  return returnList ;
	}
	
	/**
	 * 删除临时表中所有导入的保险计算对象申请(delete insurance object information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cancelInsObjectImport(Object object) throws Exception {
		Boolean falg = true;
		this.delete("pa.insurancePersonnel.delInsObjectDataImportBatch", object);
		return falg;
	}
	
	/**
	 * 删除导入保险计算对象申请(delete insurance object information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delInsObjectImport(Object object) throws Exception {
		Boolean flag = true;
		this.delete("pa.insurancePersonnel.delInsObjectImport", object);
		return flag;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 取得所有公积金计算人员信息列表（get fund Personnel List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFundObjectList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getFundObjectList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有公积金计算人员信息列表（get fund Personnel List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFundObjectList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insurancePersonnel.getFundObjectList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insurancePersonnel.getFundObjectList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有公积金计算人员信息总数（get fund Personnel Cnt）
	 * @param List
	 * @return
	 */
	public int getFundObjectListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getFundObjectListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 初始化公积金计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int initFundCalcObject(LinkedHashMap paramMap) throws Exception {
		try {
			this.delete("pa.insurancePersonnel.deleteInsCalcObjectPre", paramMap);
			this.insert("pa.insurancePersonnel.initIncCalcObjectFromLastMonth", paramMap) ;
			return 1;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 删除公积金人员信息（delete Insurance Personnel Info）
	 * @param List
	 * @return
	*/
	@Override
	public int deleteFundPersonnelInfo(Object obj) {
		try {
			this.delete("pa.insurancePersonnel.deleteFundPersonnelInfo", obj) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int updateFundCalcFlagByPersonId(Object obj)throws Exception {
		try {
			this.update("pa.insurancePersonnel.updateFundCalcFlagByPersonId", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	/**
	 * 单条删除公积金计算对象( delete fund object)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delFundCalcObject(Object object) throws Exception {
		Boolean flag = true;
		this.delete("pa.insurancePersonnel.deleteFundPersonnelInfo", object);
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getFundObjectInfo(LinkedHashMap paramMap)
			throws Exception {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getFundObjectList(paramMap) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateFundObjectInfo(LinkedHashMap paramMap) {
		try {
			this.update("pa.insurancePersonnel.updateFundCalcFlagByPersonId", paramMap) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getFundCalcObjectTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getFundCalcObjectTempList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getFundCalcObjectTempList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.insurancePersonnel.getFundCalcObjectTempList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.insurancePersonnel.getFundCalcObjectTempList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getFundCalcObjectTempCnt(Object obj) {
		int returnInt = 0 ;	
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getFundCalcObjectTempCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getFundCalcObjectTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.insurancePersonnel.getFundCalcObjectTempErrorCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	  * 取得正式表或者临时表里是否已有相同数据存在或者重复数据
	  * @param request
	  * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getFundObjectInfoExistList(Object obj) {
	  LinkedHashMap paramMap = (LinkedHashMap)obj;
	  String checkType = paramMap.get("CHECK_TYPE")!=null?paramMap.get("CHECK_TYPE").toString():"TEMP";
	  List returnList = new ArrayList() ;
	  try {
		  if(checkType!=null && "TEMP".equals(checkType)){//验证临时表里是否有重复的人员
			  returnList = this.queryForList("pa.insurancePersonnel.getFundObjectInfoExistTempList", paramMap);
		  }else if(checkType!=null && "TEMP_NO".equals(checkType)){//验证正式表里是否已经存在此人数据
			  returnList = this.queryForList("pa.insurancePersonnel.getFundObjectInfoExistList", paramMap);
		  }else if(checkType!=null && "EMPID".equals(checkType)){//验证该法人是否存在此工号
			  returnList = this.queryForList("pa.insurancePersonnel.getEmpidInfoExistList", paramMap);
		  }
	  } catch (SQLException e) {			
		  e.printStackTrace();
	  }
	  return returnList ;
	}
	
	/**
	 * 删除临时表中所有导入的公积金计算对象申请(delete fund object information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cancelFundObjectImport(Object object) throws Exception {
		Boolean falg = true;
		this.delete("pa.insurancePersonnel.delFundObjectDataImportBatch", object);
		return falg;
	}
	
	/**
	 * 删除导入公积金计算对象申请(delete fund object information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delFundObjectImport(Object object) throws Exception {
		Boolean flag = true;
		this.delete("pa.insurancePersonnel.delFundObjectImport", object);
		return flag;
	}
	
	/**
	 * 批量保存导入的公积金计算对象数据(add fund object data of import)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addFundObjectDataImport(List list) throws Exception {
		this.insertForList("pa.insurancePersonnel.addFundObjectDataImport", list);
		this.deleteForList("pa.insurancePersonnel.delFundObjectDataImportBatch", list);
	}
	
	/**
	* 更新导入的公积金计算对象的check结果(update fund object data of import for check result)
	* 
	* @param object
	* @return
	* @throws Exception
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int updateFundObjectDataCheckFlag(List list) throws Exception {
	   int flag = 0;
	   this.updateForList("pa.insurancePersonnel.updateFundObjectDataCheckFlag", list);
	   return flag;
	}
	
	/**
	 * 更新导入的公积金计算对象的check结果(update fund object data of import for check result)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateFundObjectDataCheckResult(Object object) throws Exception {
		int flag = 0;
		this.update("pa.insurancePersonnel.updateFundObjectDataCheckResult", object);
		return flag;
	}
}
