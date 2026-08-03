package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.ExcelUtilDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:  LDCC (c)
 * Company:     LDCC
 * @fileName ExcelUtilDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-29 下午08:35:33
 * @version 5.0
 * 
 */
@Repository
public class ExcelUtilDaoImpl extends SqlMapClientSupport implements ExcelUtilDao{

	public int getCodeNoByName(Object name){
		int returnInt = -1;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.getCodeNoByName", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public String  queryAgreeno(){
	     String str="";
		try {
			str = ObjectUtils.toString(this
					.queryForObject("pa.excelUtil.queryAgreeno"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public void insertEduTrainAgreement(Object obj) {

		try {
			this.insert("pa.excelUtil.insertEduTrainAgreement", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public int insertOTLimit(Object obj) {
		
		try {
			this.update("pa.excelUtil.insertOTLimit", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	public int insertTrainResult(Object obj) {
		
		try {
			this.update("pa.excelUtil.insertTrainResult", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	public int insertTeacherEvaluate(Object obj) {
		
		try {
			this.update("pa.excelUtil.insertTeacherEvaluate", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	public int insertVacPlan(Object obj) {
		
		try {
			this.update("pa.excelUtil.insertVacPlan", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public void updateEduFreeEmployee(Object obj) {

		try {
			this.update("pa.excelUtil.updateEduFreeEmployee", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void updateEduTrainSyllabus(Object obj) {

		try {
			this.update("pa.excelUtil.updateEduTrainSyllabus", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void deleteEduTrainSyllabus(Object obj) throws Exception{
		this.delete("pa.excelUtil.deleteEduTrainSyllabus", obj);
	}
	
	public void deleteWithTarget(Object obj, String target) {

		try {
			this.delete("pa.excelUtil."+target, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void insertWithTarget(Object obj, String target) {

		try {
			this.insert("pa.excelUtil."+target, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public List getContentNoByFiled(Object filed){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.excelUtil.getContentNoByFiled",filed);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void insertExcelData(Object obj) throws Exception{
		this.insertForList("pa.excelUtil.insertIsParamDataOther", (List) ((Map) obj).get("addList"));
	}
	
	@SuppressWarnings("unchecked")
	public void insertExcelDataOtApply(Object obj) throws Exception{
		this.delete("pa.excelUtil.deleteTempDataOtApplyImport", obj);
		this.insertForList("pa.excelUtil.insertExcelDataOtApply", (List) ((Map) obj).get("addList"));
	}
	
	@SuppressWarnings("unchecked")
	public void insertExcelDataPaForLeftApply(Object obj) throws Exception{
		this.delete("pa.excelUtil.deleteTempDataPaForLeftApplyImport", obj);
		this.insertForList("pa.excelUtil.insertExcelDataOtApply", (List) ((Map) obj).get("addList"));
	}
	
	@SuppressWarnings("unchecked")
	public void insertExcelDataInsObject(Object obj) throws Exception{
		this.delete("pa.excelUtil.deleteTempDataInsObjectImport", obj);
		this.insertForList("pa.excelUtil.insertExcelDataOtApply", (List) ((Map) obj).get("addList"));
	}
	
	@SuppressWarnings("unchecked")
	public void insertExcelDataFundObject(Object obj) throws Exception{
		this.delete("pa.excelUtil.deleteTempDataFundObjectImport", obj);
		this.insertForList("pa.excelUtil.insertExcelDataOtApply", (List) ((Map) obj).get("addList"));
	}
	
	public int checkAddItemDataInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.excelUtil.checkAddItemDataInfo", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	public int checkAddItemDataInfoMonth(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.excelUtil.checkAddItemDataInfoMonth", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	/**
	 * 更新基础项目数据结束月(update Pa Basic Item Data Info Other)
	 * @param List
	 * @return
	 */
	public int updateItemDataInfoMonth(Object obj) {
		try {
			this.update("pa.excelUtil.updateItemDataInfoMonth", obj) ;
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
	public int updatePaBasicItemDataInfoMonth(Object obj) {
		try {
			this.update("pa.excelUtil.updatePaBasicItemDataInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int checkAddItemDataOtherInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.excelUtil.checkAddItemDataOtherInfo", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public int checkAddItemDataOtherInfoMonth(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.excelUtil.checkAddItemDataOtherInfoMonth", obj)),Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public int checkAddPaEccInfo(Object object){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.excelUtil.checkAddPaEccInfo",
					object)), Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	/**
	 * 更新基础项目数据结束月(update Pa Basic Item Data Info Other)
	 * @param List
	 * @return
	 */
	public int updateItemDataOtherInfoMonth(Object obj) {
		try {
			this.update("pa.excelUtil.updateItemDataOtherInfoMonth", obj) ;
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
			this.update("pa.excelUtil.updatePaBasicItemDataOtherInfoMonth", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List getExcelExportDataList(Object obj){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.excelUtil.getExcelExportDataList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	//历史工资信息导出Excel专用2012-09-10(Lufeng)
	@SuppressWarnings("unchecked")
	public List getPaHistoryDataList(Object obj){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.excelUtil.getPaHistoryDataList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	public List trainAgreementTemp(Object obj){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.excelUtil.trainAgreementTemp",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public String getArItemID(Object name){
		String returnInt = "";
		try {
			
			returnInt=this.queryForObject("pa.excelUtil.getArItemID", name).toString();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public int checkPaProgressLock(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaProgressLock", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 删除考勤汇总信息(delete Cycle Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteSummaryInfo(Object obj) throws Exception {
		
		this.deleteForList("pa.excelUtil.deleteSummaryInfo", (List)obj) ;
		
	}
	
	/**
	 * 删除排班信息(delete ArSchedule Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteScheduleInfo(Object obj) throws Exception {
		
		/*for(int i=0;i<((List)obj).size();i++){
			this.delete("pa.excelUtil.deleteScheduleInfo", ((List)obj).get(i));
		}*/
			this.deleteForList("pa.excelUtil.deleteScheduleInfo", (List)obj);
	
	}
	
	
	
	/**
	 * 检查人员EMPID及考勤权限(check PersonalInfo)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkPersonalInfo(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPersonalInfo", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检查此人本月是否已经导入过F类型的数据
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkSpecialEmpImportCnt(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkSpecialEmpImportCnt", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 查询人员列表(export Personal List)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List exportPersonalList(Object obj){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.excelUtil.exportPersonalList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 删除刷卡信息(delete Cord Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteCordInfo(Object obj) throws Exception {
		
		this.deleteForList("pa.excelUtil.deleteCordInfo", (List)obj) ;
		
	}
	
	public String getDeptNo(Object name){
		String returnInt = "";
		
		try {
			
			returnInt=ObjectUtils.toString(this.queryForObject("pa.excelUtil.getDeptNo", name));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public int getColumnCnt(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.getColumnCnt", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 删除刷卡信息(delete Cord Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteCordInfoByCardNo(Object obj) throws Exception {
		
		this.deleteForList("pa.excelUtil.deleteCordInfoByCardNo", (List)obj) ;
		
	}
	/**
	 * 删除刷卡信息(delete Cord Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteCordInfoByCardNo2(Object obj) throws Exception {
		
		this.deleteForList("pa.excelUtil.deleteCordInfoByCardNo2", (List)obj) ;
		
	}
	
	/**
	 * 检查人员cardNo及考勤权限(check CardNo)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkCardNo(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkCardNo", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检查人员cardNo及考勤权限(check CardNo)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkCardNo3(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkCardNo3", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检查人员饭卡号cardNo及考勤权限(check CardNo)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkCardNo2(Object name){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkCardNo2", name);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检测要到如的数据是否已经存在
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkPaiQianDiGuanLiInfo(Object obj){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaiQianDiGuanLiInfo", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检测法人代码是否合理
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int checkPaiQianDiGuanLiFrInfo(Object obj){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaiQianDiGuanLiFrInfo", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检测城市等级
	 * @param obj
	 * @return
	 */
	public int checkPaiQianDiGuanLiCsdjInfo(Object obj){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaiQianDiGuanLiCsdjInfo", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检测省份
	 * @param obj
	 * @return
	 */
	public int checkPaiQianDiGuanLiSfInfo(Object obj){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaiQianDiGuanLiSfInfo", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检测城市名称
	 * @param obj
	 * @return
	 */
	public int checkPaiQianDiGuanLiCsmcInfo(Object obj){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaiQianDiGuanLiCsmcInfo", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 检测地区名称
	 * @param obj
	 * @return
	 */
	public int checkPaiQianDiGuanLiDqmcInfo(Object obj){
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.checkPaiQianDiGuanLiDqmcInfo", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	/**
	 * 删除已经存在的关系
	 * @param Object
	 * @return
	 * throws Exception
	 */
	public int deletePaiQianDiGuanLiInfo(Object obj){
		int returnInt = 0;
		try {
			this.delete("pa.excelUtil.deletePaiQianDiGuanLiInfo", obj);
			returnInt = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
	/**
	 * 删除刷卡信息(delete ArMacRecords Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteArMacRecordsInfo(Object obj) throws Exception {
		this.deleteForList("pa.excelUtil.deleteArMacRecordsInfo", (List)obj) ;
	}
	
	/**
	 * 删除刷卡信息(delete ArMacRecords Info)
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteSapSpecialCaseAndWaitEmp(Object obj) throws Exception {	
		this.deleteForList("pa.excelUtil.deleteSapSpecialCaseAndWaitEmp", (List)obj) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertSelectedOptionsByExprotExcel(Object object)
			throws Exception {
		for(int i=0;i<((List)object).size();i++){
			this.insert("pa.excelUtil.insertSelectedOptionsByExprotExcel", ((List)object).get(i));
		}
		
	}

	/**
	 * 查询excel导出需要填充的下拉列表值
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午02:57:43 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExcelExportTypeList(LinkedHashMap paramMap) throws Exception {
		return this.queryForList("pa.excelUtil.getExcelExportTypeList", paramMap);
	}

	@SuppressWarnings("unchecked")
	public void insertExcelDataForTransferOrder(Object obj) throws Exception{
		this.insertForList("pa.excelUtil.insertExcelDataForTransferOrder", (List) ((Map) obj).get("addList"));
	}	

	@SuppressWarnings("unchecked")
	public void insertPaiQianDiExcelDataForTransferOrder(Object obj) throws Exception{
		this.insertForList("pa.excelUtil.insertPaiQianDiExcelDataForTransferOrder", (List) ((Map) obj).get("addList"));
	}	
	
	@SuppressWarnings("unchecked")
	public void insertPaForLeftMenImportTemp(Object obj) throws Exception{
		this.insertForList("pa.excelUtil.insertPaiQianDiExcelDataForTransferOrder", (List) ((Map) obj).get("addList"));
	}	
	
	@SuppressWarnings("unchecked")
	public void insertZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelDataForTransferOrder(Object obj) throws Exception{
		this.insertForList("pa.excelUtil.insertZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelDataForTransferOrder", (List) ((Map) obj).get("addList"));
	}	
	
	/**
	 * 查询当前员工的部门编号，职位编号，职务编号
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午10:34:09 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCurrentEmpInfo(LinkedHashMap tempMap) throws Exception {
		return this.queryForList("pa.excelUtil.getCurrentEmpInfo", tempMap);
	}

	/**
	 * 获得导入Excel的类型配置集合
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 上午01:34:51 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getImportTransferOrderTypeConfigList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("pa.excelUtil.getImportTransferOrderTypeConfigList", paramMap);
	}

	/**
	 * 获得导入Excel的类型code
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 上午02:31:50 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getImportExcelTransferOrderType(LinkedHashMap paramMap)
			throws Exception {
		return (String) this.queryForObject("pa.excelUtil.getImportExcelTransferOrderType", paramMap);
		
	}

	/**
	 * 查询当前调令类型是否在HR_EXPERIENCE_INSIDE_SAVE表中有相应记录
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 下午03:25:46 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer preHasRecordInCurrentType(LinkedHashMap tempMap)
			throws Exception {
		return (Integer) this.queryForObject("pa.excelUtil.preHasRecordInCurrentType", tempMap);
	}

	/**
	 * 查询显示不修改且可查看显示的字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-19 上午02:54:35 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderTypeByParams(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("pa.excelUtil.getTransferOrderTypeByParams", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer preHasRecordForPunishment(LinkedHashMap tempMap)
			throws Exception {
		return (Integer) this.queryForObject("pa.excelUtil.preHasRecordForPunishment", tempMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer preHasRecordForReward(LinkedHashMap tempMap)
			throws Exception {
		return (Integer) this.queryForObject("pa.excelUtil.preHasRecordForReward", tempMap);
	}

	@Override
	public int insertPaEccInfo(Object object) throws Exception {
		int returnInt = 0 ;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.insert("pa.excelUtil.insertPaEccInfo", 
						object)),Integer.class);
		return returnInt;
	}

	@Override
	public String checkEccImportHasEmp(Object object) throws Exception{
		String empId = null;
		empId = ObjectUtils.toString(this.queryForObject("pa.excelUtil.checkEccImportHasEmp", object));
		return empId;
	}

	@Override
	public int updatePaEccInfo(Object parameterObject) throws Exception {
		int returnInt = -1;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.update("pa.excelUtil.updatePaEccInfo", 
				parameterObject)),Integer.class);
		return returnInt;
	}
	
	@Override
	public int insertHrLaborUnion(Object parameterObject) throws Exception {
		int returnInt = -1;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.update("pa.excelUtil.insertHrLaborUnion", 
				parameterObject)),Integer.class);
		return returnInt;
	}
	
	/**
	 * 营业员评价数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportSalesEvalTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("inct.salesman.deleteImportSalesEvalTemp", paramMap);
	}
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("pa.excelUtil.deleteImportTemp", paramMap);
	}
	/**
	 * 表数据修改 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void updateImportTemp(LinkedHashMap paramMap) throws Exception{
		this.update("pa.excelUtil.updateImportTemp", paramMap);
	}
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiImportTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("pa.excelUtil.deletePaiQianDiImportTemp", paramMap);
	}
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deletePaForLeftMenImportTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("pa.excelUtil.deletePaiQianDiImportTemp", paramMap);
	}
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteZuiDiGongZiBiaoZhunFeiCuXiaoYuanImportTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("pa.excelUtil.deleteZuiDiGongZiBiaoZhunFeiCuXiaoYuanImportTemp", paramMap);
	}
	
	/**
	 * 根据导入的报表数据 更新对应的信息
	 * @param List
	 * @author weizhengchen
	 * @return
	 */
	public int updateImportData(Object obj) {
		try {
			this.updateForList("pa.excelUtil.updateImportData", (List) ((Map) obj).get("addList"));
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 营业员提成数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public void deleteImportSalesInctTemp(LinkedHashMap paramMap) throws Exception {
		this.delete("inct.salesman.deleteImportSalesInctTemp", paramMap);
	}

	@Override
	public int insertEssLeaveApplyTbTemp(Object object) throws Exception {
		int returnInt = -1;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.update("pa.excelUtil.insertEssLeaveApplyTbTemp", 
				object)),Integer.class);
		return returnInt;
	}

	/**
	 * 教育实绩数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportPromotoGradeTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("pa.excelUtil.deleteImportPromotoGradeTemp", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public void insertExcelDataIsParam(Object obj) throws Exception{
		this.delete("pa.excelUtil.deleteTempDataIsParamImport", obj);
		this.insertForList("pa.excelUtil.insertExcelDataIsParam", (List) ((Map) obj).get("addList"));
	}
	
	/**
	 * 临时人员离职数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.10.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportTempEmpResignTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("hrm.transferOrder.deleteImportTempEmpResignTemp", paramMap);
	}
	/**
	 * 临时人员发令数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.10.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportTempEmpTransferOrderTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("hrm.transferOrder.deleteImportTempEmpTransferOrderTemp", paramMap);
	}
	/**
	 * 删除已存在的保险信息
	 * @param Object
	 * @return
	 * throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteIsParamDateInfo(Object obj) throws Exception{
		 
			this.deleteForList("pa.excelUtil.deleteIsParamDateInfo", (List)obj) ;
			 
	}

	@Override
	public int selectPersonidCount(Object obj) {
		int returnInt = 0;
		try {
			Object returnObj=this.queryForObject("pa.excelUtil.selectPersonidCount", obj);
			if(returnObj!=null&&!returnObj.equals("")){
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(returnObj),Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public void insertParamTempBatch(List<Object> list) throws Exception {
		this.startTransaction();
		this.insertForList("pa.excelUtil.insertParamTemp", list);
		this.endTransation();
	}
	
	@Override
	public void insertPaAccountTempBatch(List<Object> list) throws Exception {
		this.startTransaction();
		this.insertForList("pa.excelUtil.insertPaAccountTemp", list);
		this.endTransation();
	}
	
	@Override
	public void changeParamDataBatch(Map<String, Object> param) throws Exception {
		this.queryForObject("pa.excelUtil.changeParamDataBatch", param);
	}
	
	@Override
	public void createTempTable(Map<String, Object> param) throws Exception{
		this.queryForObject("pa.excelUtil.createTempTable", param);
	}
	
	@Override
	public void dropTempTable(Map<String, Object> param) throws Exception{
		this.queryForObject("pa.excelUtil.dropTempTable", param);
	}

	

}