package com.ait.pa.dao.tempsale.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
@SuppressWarnings("unchecked")
public class PaTempSalesDAOImpl extends SqlMapClientSupport implements PaTempSalesDAO{

	//临促项目代码
	private static String APPLY_TYPE_NO = "218064";
	
	//临促邀请名称
	private static String APPLY_TYPE_NAME = "决裁邀请";

	//临促邀请名称
	private static String APPLY_TITLE_SUFFIX = "决裁邀请";

	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	/**
	 * 获取临促工资人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getPaTempSalesEmpInfoCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getPaTempSalesEmpInfoCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getPaTempSalesCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getPaTempSalesCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 获取需要决裁的临促工资数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesAffirmCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesAffirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaTempSalesList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTempSalesList(obj, -1, -1) ; 
		return returnList ;
	}
	
	public List getPaTempSalesList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("pa.tempsale.getPaTempSalesList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("pa.tempsale.getPaTempSalesList", object);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}

	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesAffirmList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getPaTempSalesEmpInfoList(object, -1, -1) ; 
		return returnList ;
	}
	
	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesAffirmList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){ 
				returnList = this.queryForList("pa.tempsale.getPaTempSalesAffirmList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.tempsale.getPaTempSalesAffirmList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaTempSalesEmpInfoList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTempSalesEmpInfoList(obj, -1, -1) ; 
		return returnList ;
	}
	
	public List getPaTempSalesEmpInfoList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("pa.tempsale.getPaTempSalesEmpInfoList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("pa.tempsale.getPaTempSalesEmpInfoList", object);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	@Override
	public void deletePaTempSales(Object object)  throws Exception{
		this.delete("pa.tempsale.deletePaTempSales", object) ;
	}

	@Override
	public void insertPaTempSales(Object object)  throws Exception{
		LinkedHashMap obj = (LinkedHashMap) object;
		this.insert("pa.tempsale.insertPaTempSales", object) ;
		//保存附件
				if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
					String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
					String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
					if (fileUrl != null && fileUrl.length > 0) {
						for (int j=0;j<fileUrl.length ;j++) {
							LinkedHashMap fileMap = new LinkedHashMap();
							fileMap.put("fileName", fileName[j]);
							fileMap.put("fileUrl", "/resources/temp/apply/tempsale/" + fileUrl[j]);
							fileMap.put("APPLY_NO", obj.get("EVENT_ID"));
							fileMap.put("APPLY_TYPE", "218064");//218064  在ESS_FILE里面代表是临促工资
							fileMap.put("CREATED_BY", obj.get("PERSON_ID_FILE"));
							this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
						}
					}
				}
	}
	/**
	 * 新增临促工资人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void insertPaTempSalesEmpInfo(Object object) throws Exception{
		this.insert("pa.tempsale.insertPaTempSalesEmpInfo", object) ;
	}
	
	@Override
	public void updatePaTempSales(Object object) throws Exception {
		Map obj = (Map) object;
		this.update("pa.tempsale.updatePaTempSales", object);
		// 保存附件
		if (obj.get("FILE_NAME") != null
				&& !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
			String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME"))
					.split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(
					";");
			LinkedHashMap fileMap1 = new LinkedHashMap();
			fileMap1.put("APPLY_NO", obj.get("EVENT_ID").toString());
			fileMap1.put("APPLY_TYPE", obj.get("APPLY_TYPE_NO"));
			this.insert("ess.infoApplyLeave.deleteEssFile", fileMap1);
			if (fileName != null && fileName.length > 0) {
				for (int j = 0; j < fileUrl.length; j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/tempsale/"
							+ fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("EVENT_ID"));
					fileMap.put("APPLY_TYPE", obj.get("APPLY_TYPE_NO"));
					fileMap.put("CREATED_BY", obj.get("PERSON_ID_FILE"));
					this.insert("ess.infoApplyLeave.insertEssFile", fileMap);
				}
			}
		}
	}

	@Override
	public String getPaTempSalesSeq() throws Exception{
		return ObjectUtils.toString(this.queryForObject("pa.tempsale.getPaTempSalesSeq"));
	}
	
	/**
	 * 添加临促决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@Override
	public void insertAffirmor(Object object)  throws Exception{
		this.insert("pa.tempsale.insertAffirmor", object) ;
	}
	
	/**
	 * 删除临促决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@Override
	public void deleteAffirmor(Object object)  throws Exception{
		this.delete("pa.tempsale.deleteAffirmor", object) ;
	}
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorList(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("pa.tempsale.getAffirmorList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	/**
	 * 获取check
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.tempsale.getCheckList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 批量提交未提交的临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void submitTempSalary(Object object) throws Exception{
		this.update("pa.tempsale.submitTempSalary", object) ;
	}
	
	/**
	 * 批量删除临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void deleteTempSalary(Object object) throws Exception{
		this.delete("pa.tempsale.deleteTempSalary", object) ;
		this.delete("pa.tempsale.deleteTempSalaryEmp", object) ;
		this.delete("pa.tempsale.deleteTempSalaryAccrual", object) ;
		Map obj = (LinkedHashMap)object;
		String[] eventIds = obj.get("EVENT_ID_STR").toString().split(",");
		for(int i=0;i<eventIds.length;i++){
			obj.put("APPLY_NO",eventIds[i]);
			obj.put("APPLY_TYPE", APPLY_TYPE_NO);
			this.insert("ess.infoApplyLeave.deleteEssFile",obj);
		}
	}
	
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByEventId(Object object){
		try {
			return  (LinkedHashMap)this.queryForList("pa.tempsale.getTempSalesEmpInfoByEventId", object).get(0);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}
	
	@Override
	public List getExcelMessage(LinkedHashMap paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.tempsale.getExcelMessage", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public int saveWageApplication(LinkedHashMap paramMap){
		int maxId = 0;
		try {
			maxId = Integer.parseInt(this.insert("pa.tempsale.insertWageApplication", paramMap).toString());
			if(maxId > 0){
				paramMap.put("APPLYID", maxId);
				this.update("pa.tempsale.updateWageApplicationDetail", paramMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxId;
	}
	
	public int deleteWageApplicationDetail(LinkedHashMap paramMap){
		try {
			this.update("pa.tempsale.deleteWageApplicationDetail", paramMap);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 根据infono获取临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByInfoNo(Object object){
		try {
			return  (LinkedHashMap)this.queryForList("pa.tempsale.getTempSalesEmpInfoByInfoNo", object).get(0);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 人员信息变动后自动更新EVNET人员，工资信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void updateEventInfoByEmoInfo(Object object)  throws Exception{
		this.update("pa.tempsale.updateEventInfoByEmoInfo", object) ;
	}
	
	/**
	 * 更新临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSalesEmpInfo(Object object) throws Exception{
		this.update("pa.tempsale.updatePaTempSalesEmpInfo", object) ;
	}
	
	/**
	 * 删除临促工人员信息
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void deletePaTempSalesEmpInfo(Object object)  throws Exception{
		this.delete("pa.tempsale.deletePaTempSalesEmpInfo", object) ;
	}
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void addCheckPaTempSales(Object object)  throws Exception{
		this.insert("pa.tempsale.addCheckPaTempSales", object) ;
	}
	
	/**
	 * 决裁临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void affirmPaTempSales(Object object)  throws Exception{
		this.insert("pa.tempsale.affirmPaTempSales", object) ;
	}
	
	/**
	 * 修改决裁状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateEssAffirm(Object object) throws Exception{
		this.update("pa.tempsale.updateEssAffirm", object) ;
	}
	
	/**
	 * 修改决裁状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSalesStatus(Object object) throws Exception{
		this.update("pa.tempsale.updatePaTempSalesStatus", object) ;
	}
	
	/**
	 * 修改check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateEssCheck(Object object) throws Exception{
		this.update("pa.tempsale.updateEssCheck", object) ;
	}
	
	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesCheckList(Object object){
		return getTempSalesCheckList(object, -1, -1);
	}
	
	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesCheckList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.tempsale.getTempSalesCheckList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.tempsale.getTempSalesCheckList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取check信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getPaTempSalesCheckCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesCheckCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateCheckFlagByEssAffirmNo(Object object)  throws Exception{
		this.update("pa.tempsale.updateCheckFlagByEssAffirmNo", object) ;
	}
	
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempList(Object object){
		return this.getTempSalesTempList(object, 1, 10);
	}
	
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.tempsale.getTempSalesTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.tempsale.getTempSalesTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 获取出错的临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取临促人员导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesEmpTempList(Object object){
		return this.getTempSalesEmpTempList(object, 1, 10);
	}
	
	/**
	 * 获取临促人员导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesEmpTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.tempsale.getTempSalesEmpTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.tempsale.getTempSalesEmpTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取临促人员导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesEmpTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesEmpTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 获取出错的临促人员导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesEmpTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesEmpTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFromExcel(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("pa.tempsale.importInfoFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}

	
	/**
	 * 派遣津贴标准提交 
	 */
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempPqdJtbzData(Object object)  throws Exception{
		String returnString = "Error" ;	
		try {
			this.startTransaction();
			LinkedHashMap paramMap = (LinkedHashMap)object;
			//开始验证导入临时表的数据是否有错误的
//			"PERSON_ID"//创建者   
			List errorNosList = this.queryForList("pa.tempsale.getErrorPaiQianDiJinTieBiaoZhunNosList", paramMap);
			int errorInt = errorNosList.size();
			//获取导入数据和正式库数据重复的NOs
			List repartNosList = this.queryForList("pa.tempsale.getRepartPaiQianDiJinTieBiaoZhunNosList", paramMap);
			int repartInt = repartNosList.size();
			//获取临时表中数据重复的NOs
			List repartTempNosList = this.queryForList("pa.tempsale.getRepartTempPaiQianDiJinTieBiaoZhunNosList", paramMap);
			int repartTempInt = repartTempNosList.size();
			//清空验证结果
			this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunQingKongYzResult", paramMap);
			//把重复的数据和错误的数据都标识上符号
			for(int i = 0; i< errorInt; i++){//E  
				paramMap.put("errorNos", errorNosList.get(i));
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunErrorYzResult", paramMap);
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunErrorYzResultFaRenCsdjDqmc", paramMap);//法人、城市等级、地区名称跟派遣地管理匹配
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunErrorYzResultFaRen", paramMap);//法人
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhuniErrorYzResultChengShiDengJi", paramMap);//城市等级
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunErrorYzResultZhiZe", paramMap);//职责
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunErrorYzResultDiQuMingCheng", paramMap);//地区名称
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunErrorYzResultShuZhi", paramMap);//数值
			}
			
			for(int i = 0; i< repartInt; i++){//E 
				paramMap.put("repartNos", repartNosList.get(i));
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunRepartYzResult", paramMap);
			}
			
			for(int i = 0; i< repartTempInt; i++){//E
				paramMap.put("repartTempNos", repartTempNosList.get(i));
				this.update("pa.tempsale.updatePaiQianDiJinTieBiaoZhunRepartTempYzResult", paramMap);
			}
			
			//最后把剩余的更新为N 
			this.update("pa.tempsale.updatePaiQianDiGuanLiOkYzResult", paramMap);
			
			int errorData = Integer.parseInt(this.queryForObject("pa.tempsale.submitImportExcelTempPqdJinTieBiaoZhunData", paramMap).toString()) ;
			if(errorData > 0){
				returnString = "Error";
			}else{
				//插入正确的数据
				this.insert("pa.tempsale.submitInsertImportExcelTempPqdJinTieBiaoZhunData", paramMap);
				returnString = "OK";
			}
			this.commitTransation();
		} catch (SQLException e) {	
			returnString = "Error";
			throw e;
		} finally {
			this.endTransation();
		}
		return returnString ;
	}
	
	/**
	 * 派遣地管理的提交功能
	 */
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempPqdGuanLiData(Object object)  throws Exception{
		String returnString = "Error" ;	
		try {
			this.startTransaction();
			LinkedHashMap paramMap = (LinkedHashMap)object;
			//开始验证导入临时表的数据是否有错误的
			//"PERSON_ID"//创建者  PaiQianDiGuanLi
			List errorNosList = this.queryForList("pa.tempsale.getErrorPaiQianDiGuanLiNosList", paramMap);
			int errorInt = errorNosList.size();
			//获取导入数据和正式库数据重复的NOs
			List repartNosList = this.queryForList("pa.tempsale.getRepartPaiQianDiGuanLiNosList", paramMap);
			int repartInt = repartNosList.size();
			//获取临时表中数据重复的NOs
			List repartTempNosList = this.queryForList("pa.tempsale.getRepartTempPaiQianDiGuanLiNosList", paramMap);
			int repartTempInt = repartTempNosList.size();
			//清空验证结果
			this.update("pa.tempsale.updatePaiQianDiGuanLiQingKongYzResult", paramMap);
			//把重复的数据和错误的数据都标识上符号
			for(int i = 0; i< errorInt; i++){//E  
				paramMap.put("errorNos", errorNosList.get(i));
				this.update("pa.tempsale.updatePaiQianDiGuanLiErrorYzResult", paramMap);
				this.update("pa.tempsale.updatePaiQianDiGuanLiErrorYzResultFaRen", paramMap);//法人
				this.update("pa.tempsale.updatePaiQianDiGuanLiErrorYzResultChengShiDengJi", paramMap);//城市等级
				this.update("pa.tempsale.updatePaiQianDiGuanLiErrorYzResultShengFen", paramMap);//省份
				this.update("pa.tempsale.updatePaiQianDiGuanLiErrorYzResultChengShiMingCheng", paramMap);//城市名称
				this.update("pa.tempsale.updatePaiQianDiGuanLiErrorYzResultDiQuMingCheng", paramMap);//地区名称
			}
			
			for(int i = 0; i< repartInt; i++){//E  PaiQianDiGuanLi
				paramMap.put("repartNos", repartNosList.get(i));
				this.update("pa.tempsale.updatePaiQianDiGuanLiRepartYzResult", paramMap);
			}
			
			for(int i = 0; i< repartTempInt; i++){//E
				paramMap.put("repartTempNos", repartTempNosList.get(i));
				this.update("pa.tempsale.updatePaiQianDiGuanLiRepartTempYzResult", paramMap);
			}
			
			//最后把剩余的更新为N PaiQianDiGuanLi
			this.update("pa.tempsale.updatePaiQianDiGuanLiOkYzResult", paramMap);
			
			int errorData = Integer.parseInt(this.queryForObject("pa.tempsale.submitImportExcelTempPqdGuanLiData", paramMap).toString()) ;
			if(errorData > 0){
				returnString = "Error";
			}else{
				//插入正确的数据
				this.insert("pa.tempsale.submitInsertImportExcelTempPqdGuanLiData", paramMap);
				returnString = "OK";
			}
			this.commitTransation();
		} catch (SQLException e) {	
			returnString = "Error";
			throw e;
		} finally {
			this.endTransation();
		}
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData(Object object)  throws Exception{
		String returnString = "Error" ;		
		try {
			this.startTransaction();
			LinkedHashMap paramMap = (LinkedHashMap)object;
			//开始验证导入临时表的数据是否有错误的
//			"PERSON_ID"//创建者
			List errorNosList = this.queryForList("pa.tempsale.getErrorZuiDiGongZiBiaoZhunFeiCuXiaoYuanNosList", paramMap);
			int errorInt = errorNosList.size();
			//获取导入数据和正式库数据重复的NOs
			List repartNosList = this.queryForList("pa.tempsale.getRepartZuiDiGongZiBiaoZhunFeiCuXiaoYuanNosList", paramMap);
			int repartInt = repartNosList.size();
			
			//获取临时表中数据重复的NOs
			List repartTempNosList = this.queryForList("pa.tempsale.getRepartTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanNosList", paramMap);
			int repartTempInt = repartTempNosList.size();
			//清空验证结果
			this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanQingKongYzResult", paramMap);
			//把重复的数据和错误的数据都标识上符号
			for(int i = 0; i< errorInt; i++){//E
				paramMap.put("errorNos", errorNosList.get(i));
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResult", paramMap);
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultFaRen", paramMap);//法人
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultNianDuFei", paramMap);//年度
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultFuLiDiQu", paramMap);//福利地区
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultZuiDiGongZi", paramMap);//最低工资
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultShePingGongZi", paramMap);//社平工资
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultZuiXiaoJiShu", paramMap);//最小基数
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultZuiDaJiShu", paramMap);//最大基数
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultQuFenFei", paramMap);//区分
			}
			
			for(int i = 0; i< repartInt; i++){//R
				paramMap.put("repartNos", repartNosList.get(i));
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanRepartYzResult", paramMap);
			}
			
			for(int i = 0; i< repartTempInt; i++){//R
				paramMap.put("repartTempNos", repartTempNosList.get(i));
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanRepartTempYzResult", paramMap);
			}
			
			
			
			//最后把剩余的更新为N
			this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanOkYzResult", paramMap);
			
			int errorData = Integer.parseInt(this.queryForObject("pa.tempsale.queryImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData", paramMap).toString()) ;
			if(errorData > 0){
				returnString = "Error";
			}else{
				//插入正确的数据
				this.insert("pa.tempsale.submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData", paramMap);
				returnString = "OK";
			}
			this.commitTransation();
		} catch (SQLException e) {	
			returnString = "Error";
			throw e;
		} finally {
			this.endTransation();
		}
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData(Object object)  throws Exception{
		String returnString = "Error" ;		
		try {
			this.startTransaction();
			LinkedHashMap paramMap = (LinkedHashMap)object;
			//开始验证导入临时表的数据是否有错误的
			List errorNosList = this.queryForList("pa.tempsale.getErrorZuiDiGongZiBiaoZhunCuXiaoYuanNosList", paramMap);
			int errorInt = errorNosList.size();
			//获取导入数据和正式库数据重复的NOs
			List repartNosList = this.queryForList("pa.tempsale.getRepartZuiDiGongZiBiaoZhunCuXiaoYuanNosList", paramMap);
			int repartInt = repartNosList.size();
			
			//获取临时表中数据重复的NOs
			List repartTempNosList = this.queryForList("pa.tempsale.getRepartTempZuiDiGongZiBiaoZhunCuXiaoYuanNosList", paramMap);
			int repartTempInt = repartTempNosList.size();
			//清空验证结果
			this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunCuXiaoYuanQingKongYzResult", paramMap);
			//把重复的数据和错误的数据都标识上符号
			for(int i = 0; i< errorInt; i++){//E
				paramMap.put("errorNos", errorNosList.get(i));
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunCuXiaoYuanErrorYzResult", paramMap);//EEEE
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultNianDu", paramMap);//年度
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunCuXiaoYuanErrorYzResultZuiDiGongZi", paramMap);//最低工资
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultShePingChengShiMingCheng", paramMap);//城市名称
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultShePingCityCd", paramMap);//CITYCD
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanErrorYzResultZuiXiaoQuFen", paramMap);//区分
			}
			
			for(int i = 0; i< repartInt; i++){//R
				paramMap.put("repartNos", repartNosList.get(i));
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunCuXiaoYuanRepartYzResult", paramMap);
			}
			
			for(int i = 0; i< repartTempInt; i++){//R
				paramMap.put("repartTempNos", repartTempNosList.get(i));
				this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunCuXiaoYuanRepartTempYzResult", paramMap);
			}
			
			
			
			//最后把剩余的更新为N
			this.update("pa.tempsale.updateZuiDiGongZiBiaoZhunCuXiaoYuanOkYzResult", paramMap);
			
			int errorData = Integer
			.parseInt(this
					.queryForObject(
							"pa.tempsale.queryImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData",
							paramMap).toString());
			
			if(errorData > 0){
				returnString = "Error";
			}else{
				//插入正确的数据
				this.insert("pa.tempsale.submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData", paramMap);
				returnString = "OK";
			}
			this.commitTransation();
		} catch (SQLException e) {	
			returnString = "Error";
			throw e;
		} finally {
			this.endTransation();
		}
		return returnString ;
	}
	
	
	/**
	 * 获取门店信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getSpmsShopList(Object object) {
		List returnList = new ArrayList() ;
			try {
					returnList = this.queryForList("pa.tempsale.getSpmsShopList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	/**
	 * 根据部门id获取共同社编
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getCommonEmpId(Object object){
		try {
			return  (LinkedHashMap)this.queryForObject("pa.tempsale.getCommonEmpId", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}
	

	public List getPaTempSalesEmpList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTempSalesEmpList(obj, -1, -1) ; 
		return returnList ;
	}
	
	public List getPaTempSalesEmpList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("pa.tempsale.getPaTempSalesEmpList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("pa.tempsale.getPaTempSalesEmpList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return returnList ;
	}
	
	public int getPaTempSalesEmpCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getPaTempSalesEmpCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据IdCard获取临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByIdCard(LinkedHashMap object){
		try {
			return  (LinkedHashMap)this.queryForList("pa.tempsale.getTempSalesEmpInfoByIdCard", object).get(0);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 更新临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSalesEmp(Object object) throws Exception{
		this.update("pa.tempsale.updatePaTempSalesEmp", object) ;
	}
	
	/**
	 * 临促汇总
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSummary(Object object) throws Exception{

		LinkedHashMap paramMap = (LinkedHashMap)object;
		paramMap.put("message", "") ;
		this.insert("pa.tempsale.viewTempSaleSummary", paramMap) ;		
		String returnString = ObjectUtils.toString(paramMap.get("message")) ;
		return returnString;
	}
	/**
	 * 临促汇总明细查询
	 */
	public List getPaTempSalesSummaryList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTempSalesSummaryList(obj, -1, -1) ; 
		return returnList ;
	}
	
	public List getPaTempSalesSummaryList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("pa.tempsale.getPaTempSalesSummaryList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("pa.tempsale.getPaTempSalesSummaryList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return returnList ;
	}
	
	public int getPaTempSalesSummaryCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getPaTempSalesSummaryCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取决裁线
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorInfo(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("pa.tempsale.getAffirmorInfo", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	

	public List getTempSalesAccuralInfoList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getTempSalesAccuralInfoList(obj, -1, -1) ; 
		return returnList ;
	}
	
	public List getTempSalesAccuralInfoList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("pa.tempsale.getTempSalesAccuralInfoList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("pa.tempsale.getTempSalesAccuralInfoList", object);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	public int getTempSalesAccuralInfoCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getTempSalesAccuralInfoCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesInfoByEventId(Object object){
		try {
			return  (LinkedHashMap)this.queryForList("pa.tempsale.getTempSalesInfoByEventId", object).get(0);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 获取check
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListToLgep(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.tempsale.getCheckListToLgep", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	

	/**
	 * 获取支社列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getBranchList(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("pa.tempsale.getBranchList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}

	/**
	 * 临促汇总明细查询
	 */
	public List getPaTempSalesSendList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTempSalesSendList(obj, -1, -1) ; 
		return returnList ;
	}
	
	public List getPaTempSalesSendList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("pa.tempsale.getPaTempSalesSendList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("pa.tempsale.getPaTempSalesSendList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return returnList ;
	}
	
	public int getPaTempSalesSendCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.getPaTempSalesSendCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 临促传送财务
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSend(Object object) throws Exception{

		LinkedHashMap paramMap = (LinkedHashMap)object;
		paramMap.put("message", "") ;
		this.insert("pa.tempsale.viewTempSaleSend", paramMap) ;		
		String returnString = ObjectUtils.toString(paramMap.get("message")) ;
		return returnString;
	}
	

	/**
	 * 发送给大区担当审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleConfirm(Object object) throws Exception{
		List returnList = this.queryForList("pa.tempsale.getConfirmInfo", object);
		LinkedHashMap paramMap = (LinkedHashMap)object;
		paramMap.put("message", "") ;
		this.insert("pa.tempsale.viewTempSaleConfirm", paramMap) ;
		String returnString = ObjectUtils.toString(paramMap.get("message")) ;
		if("OK".equals(returnString)){
			//先删除旧的审批
			if(returnList != null && returnList.size() > 0){
				LinkedHashMap lgepMap = (LinkedHashMap)returnList.get(0);
				String eventId = String.valueOf(lgepMap.get("EVENT_ID"));
				paramMap.put("APPLY_NO", eventId);
				paramMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				affirmInfoToLGEPSer.deleteAffirm(paramMap);
			}
			//发送新的审批
			List newList = this.queryForList("pa.tempsale.getConfirmInfo", object);
			if(newList != null && newList.size() > 0){
				LinkedHashMap lgepMap = (LinkedHashMap)newList.get(0);
				this.sendToLGEP(lgepMap);
			}
		}
		return returnString;
	}

	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap lgepMap){
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", lgepMap.get("EVENT_ID"));
			lgepMap.put("APPLY_TYPE_NAME", lgepMap.get("EVENT_NAME") + APPLY_TITLE_SUFFIX);
			lgepMap.put("APPLY_TITLE", lgepMap.get("EVENT_NAME") + APPLY_TITLE_SUFFIX);
			lgepMap.put("APPLY_EMPID", lgepMap.get("CREATED_BY"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	
	/**
	 * 临促月别总计
	 * @param request
	 * @return
	 */
	public List getTempSalesSum(Object object) {
		List returnList = new ArrayList() ;
			try {
					returnList = this.queryForList("pa.tempsale.getPaTempSalesSum", object);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return returnList ;
	}

	/**
	 * 审批成功，更新progress
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateTempSalesProgress(Object object)  throws Exception{
		this.update("pa.tempsale.updateTempSalesProgress", object) ;
	}
	

	/**
	 * 临促工资状态查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List viewTempSaleState(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.tempsale.viewTempSaleState", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 手机项目直接提交，无需审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateTsmStatus(Object object) throws Exception{
		this.update("pa.tempsale.updateTsmStatus", object) ;
	}
	

	/**
	 * 手机项目直接提交，无需审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateTsmStatusAffirm(Object object) throws Exception{
		this.update("pa.tempsale.updateTsmStatusAffirm", object) ;
	}
	

	/**
	 * 查看是否是手机项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int isTsm(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.tempsale.isTsm", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取支社列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getBranchListHR(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("pa.tempsale.getBranchListHR", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	/**
	 * 预提添加附件信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateYuTy(Object object){
		try {
			this.update("pa.tempsale.updateYuTy", object) ;
			LinkedHashMap obj = (LinkedHashMap) object;
			//保存附件
					if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
						String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
						String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
						if (fileUrl != null && fileUrl.length > 0) {
							for (int j=0;j<fileUrl.length ;j++) {
								LinkedHashMap fileMap = new LinkedHashMap();
								fileMap.put("fileName", fileName[j]);
								fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + fileUrl[j]);
								fileMap.put("APPLY_NO", obj.get("EVENT_ID"));
								fileMap.put("APPLY_TYPE", "218064");//218064  在ESS_FILE里面代表是临促工资
								fileMap.put("CREATED_BY", obj.get("PERSON_ID_FILE"));
								this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
							}
						}
					}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 根据月份、大区、预提标示 获取临促汇总信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesSummary(Object object){
		try {
			return  (LinkedHashMap)this.queryForList("pa.tempsale.getTempSalesSummary", object).get(0);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}
}