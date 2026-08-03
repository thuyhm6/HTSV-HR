package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.service.SyLanguageSer;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName BasicMaintenanceDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:20:43
 * @version 5.0
 *
 */
@Repository
public class BasicMaintenanceDaoImpl extends SqlMapClientSupport implements BasicMaintenanceDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@Autowired
	private SyLanguageSer syLanguageSer;
	/**
	 * 取得所有父级CODE列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getParentCodeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有CODE列表,依据PARENT_CODE
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.basicMaintenance.getCodeListByParentCode", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("sys.basicMaintenance.getCodeListByParentCode", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有CODE列表,依据PARENT_CODE
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeWithParam(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.basicMaintenance.getCodeListByParentCodeWithParam", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("sys.basicMaintenance.getCodeListByParentCodeWithParam", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object){
		List returnList = new ArrayList();
		returnList = this.getCodeListByParentCode(object, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeWithParam(Object object){
		List returnList = new ArrayList();
		returnList = this.getCodeListByParentCodeWithParam(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有PARAM CODE列表,依据CpnyID
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeListByCpnyID", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeListByCpnyID", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有PARAM CODE列表,依据CpnyID
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getParamCodeListByCpnyID", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 插入CODE信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addCodeInfo(Object obj) throws Exception{
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("sys.basicMaintenance.addCodeInfo", object) ;
	}
	
	/**
	 * 批量插入CODE信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addCodeInfo(List obj) {
		try {
			this.insertForList("sys.basicMaintenance.addCodeInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0 ;
	}
	
	/**
	 * 更新CODE信息
	 * @param Object
	 * @return
	 */
	public void updateCodeInfo(Object obj) throws Exception {
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("sys.basicMaintenance.updateCodeInfo",obj);
	}
	
	/**
	 * 批量更新CODE信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateCodeInfo(List obj) {
		try {
			this.updateForList("sys.basicMaintenance.updateCodeInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0 ;
	}
	
	/**
	 * 删除CODE信息
	 * @param Object
	 * @return
	 */
	public void deleteCodeInfo(Object obj) throws Exception  {
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("sys.basicMaintenance.deleteCodeInfo",obj);
	}
	
	/**
	 * 批量删除CODE信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteCodeInfo(List obj) {
		try {
			this.deleteForList("sys.basicMaintenance.deleteCodeInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0 ;
	}

	@Override
	public int getCodeListByParentCodeCnt(Object object) throws Exception{
		Integer temp=(Integer) this.queryForObject("sys.basicMaintenance.getCodeListByParentCodeCnt",object);
		return temp!=null?temp:0;
	}
	
	/**
	 * 查询Param CODE信息
	 * @param Object
	 * @return int
	 */
	@Override
	public int getParamCodeListByCpnyIDCnt(Object object) throws Exception{
		Integer temp=(Integer) this.queryForObject("sys.basicMaintenance.getParamCodeListByCpnyIDCnt",object);
		return temp!=null?temp:0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getCodeByCodeNo(Object object) throws Exception {
		Map temp=(Map) this.queryForObject("sys.basicMaintenance.getCodeByCodeNo", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeForAll(Object object) throws Exception {
		List temp=this.queryForList("sys.basicMaintenance.getCodeTreeForAll", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeByParentCode(Object object)throws Exception {
		List temp=this.queryForList("sys.basicMaintenance.getCodeTreeForAll", object);
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveCodeParam(Object object,HttpServletRequest request) throws Exception {
		List<String> temp=StringUtil.getSplitParams(((Map)object).get("CODE_NOS").toString(), ",");
		if(temp!=null&&temp.size()>0)
		for(String code_no:temp){
			((Map)object).put("CODE_NO", new Integer(code_no));
			Map codeParma=(Map) this.queryForObject("sys.basicMaintenance.getCodeParamByCpnyIdAndCodeNo",object);
			if(codeParma!=null){
				((Map)object).put("PARAM_NO", codeParma.get("PARAM_NO"));
			}
			if((Integer)this.queryForObject("sys.basicMaintenance.checkCodeParam", object)>0){
				this.update("sys.basicMaintenance.updataCodeParam",object);
			}else{
				this.insert("sys.basicMaintenance.saveCodeParam", object);
			}
			((Map)object).remove("NO");
			((Map)object).remove("LANGUAGE");
			((Map)object).remove("CONTENT");
		}else{
			return "代码参数设置保存失败，请选择代码！";
		}
		return "代码参数设置保存成功";
	}

	@SuppressWarnings("unchecked")
	public String saveCodeParamBeiFen(Object object,HttpServletRequest request) throws Exception {
		List<String> temp=StringUtil.getSplitParams(((Map)object).get("CODE_NOS").toString(), ",");
		if(temp!=null&&temp.size()>0)
		for(String code_no:temp){
			String no = "";
			no = this.syLanguageDao.getSyGlobalNameNo();
			List languageList = new ArrayList();
			languageList = this.syLanguageSer.getSyLanguageListByActivity();
			((Map)object).put("CODE_NO", new Integer(code_no));
			Map codeParma=(Map) this.queryForObject("sys.basicMaintenance.getCodeParamByCpnyIdAndCodeNo",object);
			if(codeParma!=null){
				codeParma.put("NO", codeParma.get("PARAM_NO"));
				this.delete("sys.language.deleteSyGlobalName",codeParma );
				no= codeParma.get("PARAM_NO").toString();
			}
			for (int i = 0; i < languageList.size(); i++) {
				LinkedHashMap languageMap = new LinkedHashMap();
				languageMap = (LinkedHashMap) languageList.get(i);
				String language = StringUtil.checkNull(languageMap.get("LANGUAGE"));
				Map aliasMap=StringUtil.getAliasSplitParams(request.getParameterValues("Alias"+language), "\\|");
				
				if(aliasMap.get(code_no)!=null){
					((Map)object).put("CONTENT", aliasMap.get(code_no));
					((Map)object).put("NO",no);
					((Map)object).put("LANGUAGE", language);
					if((Integer)this.queryForObject("sys.basicMaintenance.checkCodeParam", object)>0){
						this.insert("sys.language.insertSyGlobalName", object);
						this.update("sys.basicMaintenance.updataCodeParam",object);
					}else{
						this.insert("sys.language.insertSyGlobalName", object);
						this.insert("sys.basicMaintenance.saveCodeParam", object);
					}
				}
			}
			((Map)object).remove("NO");
			((Map)object).remove("LANGUAGE");
			((Map)object).remove("CONTENT");
		}else{
			return "代码参数设置保存失败，请选择代码！";
		}
		return "代码参数设置保存成功";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodePamersList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getCodePamersList", object,  currentPage,  pageSize);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@SuppressWarnings("unchecked")
	public List getCodePamersList(Object object){
		List returnList = new ArrayList();
		returnList = this.getCodePamersList(object, -1, -1);
		return returnList;
	}
	
	@Override
	public int getCodePamersListCnt(Object object) throws Exception {
		Integer temp=(Integer) this.queryForObject("sys.basicMaintenance.getCodePamersListCnt",object);
		return temp!=null?temp:0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getCodePamasByParamNo(Object object) throws Exception {
		Map temp=(Map) this.queryForObject("sys.basicMaintenance.getCodePamasByParamNo", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeForEditCodePamas(Object object) throws Exception {
		List temp=this.queryForList("sys.basicMaintenance.getCodePamasForSelect", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getcodeTreeForEdit(Object object) throws Exception {
		List temp=this.queryForList("sys.basicMaintenance.getCodePamasForSubTree", object);
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String editCodeParam(Object object,HttpServletRequest request) throws Exception {
		List<String> temp=StringUtil.getSplitParams(((Map)object).get("CODE_NOS").toString(), ",");
		this.delete("sys.basicMaintenance.deleteCodeParamByParentNo", object);
		if(temp!=null&&temp.size()>0){
			for(String code_no:temp){
				((Map)object).put("CODE_NO", new Integer(code_no));
				this.insert("sys.basicMaintenance.saveCodeParam", object);
			}
		}else{
			return "代码参数设置保存失败，请选择代码！";
		}
			return "代码参数设置保存成功";
	}

	/**
	 * 代码参数修改
	 */
	@SuppressWarnings("unchecked")
	public String editCodeParamBeiFen(Object object,HttpServletRequest request) throws Exception {
		List<String> temp=StringUtil.getSplitParams(((Map)object).get("CODE_NOS").toString(), ",");
		Map aliasMapZH=StringUtil.getAliasSplitParams(request.getParameterValues("AliasZH"), "\\|");
		Map aliasMapKR=StringUtil.getAliasSplitParams(request.getParameterValues("AliasKR"), "\\|");
		Map aliasMapEN=StringUtil.getAliasSplitParams(request.getParameterValues("AliasEN"), "\\|");
		if(temp!=null&&temp.size()>0){
			((Map)object).put("DEL_CODE_NO",temp.get(0));
			this.delete("sys.basicMaintenance.deleteCodeParams",object);
			for(String code_no:temp){
				((Map)object).put("CODE_NO",  code_no);
				if(aliasMapZH.get(code_no)!=null)
					((Map)object).put("ALIAS_ZH", aliasMapZH.get(code_no));
				if(aliasMapKR.get(code_no)!=null)
					((Map)object).put("ALIAS_KR", aliasMapKR.get(code_no));
				if(aliasMapEN.get(code_no)!=null)
					((Map)object).put("ALIAS_EN", aliasMapEN.get(code_no));
				this.insert("sys.basicMaintenance.saveCodeParam", object);
				((Map)object).remove("ALIAS_ZH");
				((Map)object).remove("ALIAS_KR");
				((Map)object).remove("ALIAS_EN");
			}
		}else{
			return "代码参数修改，请选择代码！";
		}
		return "代码参数修改成功";
	}
	/**
	 * 根据表明取得标签的内容
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParamCodeListByTableName(Object obj) {
		List returnList = new ArrayList() ;
		try {
			
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeListByTableName", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 根据法人获得deptName deptno
	 * @param List
	 * @return
	 */
	public List getDeptListByCpnyID(Object obj){
		List returnList = new ArrayList() ;
		try {
			
				returnList = this.queryForList("sys.basicMaintenance.getDeptListByCpnyID", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 根据法人获得父级部门下的 deptName deptno combo 用
	 * @param List
	 * @return
	 */
	public List getDeptListByCpnyIDCombo(Object obj,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("sys.basicMaintenance.getDeptListByCpnyIDCombo", obj);			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据法人获得 人员类型组 下的 人员类型
	 * @param List
	 * @return
	 */
	public List getJobTypeByCpnyIDCombo(Object obj,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("sys.basicMaintenance.getJobTypeByCpnyIDCombo", obj);			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据法人获得 sy_code 下的 营业员职责 
	 * @param List
	 * @return
	 */
	public List getJobPositionByCpnyIDCombo(Object obj,int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("sys.basicMaintenance.getJobPositionByCpnyIDCombo", obj);			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getParamCodeCombinListByCpnyID(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeCombinListByCpnyID", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeCombinListByCpnyID", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 	2014.7.16 added by PengHaixia
		将description作为code取出 
		sy_code表中的description列用于存放1.0版本里的cd值
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyCodeDescByCpnyID(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getSyCodeDescByCpnyID", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	
	/**
	 * 	2014.8.27 added by LI HUIHUA
	 * 取得所有PARAM CODE列表,依据CpnyID
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID2(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeListByCpnyID2", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("sys.basicMaintenance.getParamCodeListByCpnyID2", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	

	/**
	 * 取得人员类型
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmpType(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getEmpType", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得工资支付计划
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getScheduleNo(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getScheduleNo", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有非sy_code表中的 CODE列表,依据CpnyID
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNonSyCodeListByCpnyID(Map paramMap, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		String sqlStr="";
		if(paramMap.get("CODE_TYPE").equals("SHIFT_NO")){//班号
			sqlStr="sys.basicMaintenance.getShiftNoListByCpnyID";
		}else if(paramMap.get("CODE_TYPE").equals("PAY_GRADE")){//级号
			sqlStr="sys.basicMaintenance.getPayGradeListByCpnyID";
		}else if(paramMap.get("CODE_TYPE").equals("PAY_STEP")){//级号等级
			sqlStr="sys.basicMaintenance.getPayStepListByCpnyID";
		}else if(paramMap.get("CODE_TYPE").equals("POSITION_NO")){//职责
			sqlStr="sys.basicMaintenance.getPositionNoListByCpnyID";
		}else if(paramMap.get("CODE_TYPE").equals("TEMP_EMP_TYPE")){//临时职人员类型
			sqlStr="sys.basicMaintenance.getTempEmpTypeByCpnyID";
		}else if(paramMap.get("CODE_TYPE").equals("REG_TYPE_CODE")){//户口性质类型
			sqlStr="sys.basicMaintenance.getTRegTypeCodeByCpnyID";
		}else if(paramMap.get("CODE_TYPE").equals("AR_ITEM_CODE")){//考勤区分
			sqlStr="report.ar.getArCodeNameByCode";
		}
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList(sqlStr, paramMap,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList(sqlStr, paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得所有非sy_code表中的 CODE列表,依据CpnyID
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNonSyCodeListByCpnyID(Map paramMap) {
		List returnList = new ArrayList() ;
		returnList = this.getNonSyCodeListByCpnyID(paramMap,-1,-1);
		return returnList ;
	}

	/* 
	* Title: getCodeListForArItem
	* Description:查询 考勤表的代码列表
	* @author 孙鹏  
	* @date 2014年12月22日 下午4:11:47  
	* @param paramMap
	* @param i
	* @param j
	* @return 
	* @see com.ait.sys.dao.BasicMaintenanceDao#getCodeListForArItem(java.util.Map, int, int) 
	*/
	@Override
	public List getCodeListForArItem(Map paramMap, int i, int j) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.basicMaintenance.getCodeForArItem", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
}
