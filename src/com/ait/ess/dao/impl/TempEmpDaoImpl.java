package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ait.ess.dao.TempEmpDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class TempEmpDaoImpl extends SqlMapClientSupport implements TempEmpDao {

	/**
	 * 查询
	 * 
	 * @param Object
	 * @return
	 */
	public List viewTempEmpList(Object object,String target) {
		List result = null;
		try {
			result = this
					.queryForList("ess.tempEmp." + target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addFixOtInfo(Object object) throws Exception {
		
		this.insert("ess.tempEmp.addFixOtInfo",object);
	}
	
	/**
	 * 查询数量
	 * 
	 * @param Object
	 * @return
	 */
	public int viewTempEmpCnt(Object object,String target) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.tempEmp." + target, object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmp(Object object,String target) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("ess.tempEmp." + target, object));
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addTempEmpByJson(Object object,String target)throws Exception {
		this.updateForList("ess.tempEmp." + target,(List)object);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addTempEmpByJsonPro(Object object,String target)throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				this.insert("ess.tempEmp." + target, map) ;
			}
		}
		return 1;
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteFixOtInfo(List list) throws Exception {
	
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.delete("ess.tempEmp.deleteFixOtInfo",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteNullFixOtInfo(Object object) throws Exception {
	
		try {
				this.delete("ess.tempEmp.deleteNullFixOtInfo",object);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}


	/**
	 * 调用存储过程
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addEnsInfoProcedure(Object object,String target)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("ess.tempEmp." + target, paramMap) ;	
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage();			
			throw e;
		}
		return returnString ;
	}

	/**
	 * 新增概要信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpResumeInfo(Object object) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("ess.tempEmp.addResumeInfo", object));
		obj.put("SEQ", seq);
		//附件上传
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName"))) && !"".equals(seq) && !"1".equals(seq)) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "TempEmp_RESUME");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		//初始化评价配置信息
		String returnString = "OK" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("ess.tempEmp.initTempEmp", paramMap) ;	
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage();			
			return 0;
		}
		if(!"OK".equals(returnString)){
			return 0;
		}
		return 1;
	}

	/**
	 * 考核项目注册
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSSTTempEmpItem(List list,Map paramMap) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItem", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
				this.insert("ess.tempEmp.addSSTTempEmpItem", map) ;
			}
		}
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * 删除考核项目  
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteSSTTempEmpItem(Object object) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItem", object) ;
		return 1;
	}

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelf(List list,Map paramMap) throws Exception {
		this.updateForList("ess.tempEmp.modifySSTTempEmpItemBySelf", list) ;
		this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTO(List list,Map paramMap) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItem", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("ess.tempEmp.addSSTTempEmpItem", map) ;
			}
		}
		this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * TSTO一次考评
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTO(List list,Map paramMap) throws Exception {
		if(!"0".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.updateForList("ess.tempEmp.modifySSTTempEmpItemByAffirmor", list) ;
			this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		}
		if(!"2".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}
	
	/**
	 * 获得人员列表(get EmpCalendar List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getEmpListForFix(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.tempEmp.getEmpListForFix", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	

	/**
	 * 获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpListForFixCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.tempEmp.getEmpListForFixCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
	
	/**
	 * 保存评价人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectInfo(List list,Map paramMap) throws Exception {
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				paramMap.put("RESUME_SEQ", map.get("RESUME_SEQ"));
				paramMap.put("TempEmp_OBJECT_SEQ", map.get("TempEmp_OBJECT_SEQ"));
				//1级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID1"));
				paramMap.put("AFFIRM_LEVEL", "1");
				this.insert("ess.tempEmp.PR_MODIFY_AFFIRM_INFO", paramMap) ;
				//2级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID2"));
				paramMap.put("AFFIRM_LEVEL", "2");
				this.insert("ess.tempEmp.PR_MODIFY_AFFIRM_INFO", paramMap) ;
				//3级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID3"));
				paramMap.put("AFFIRM_LEVEL", "3");
				this.insert("ess.tempEmp.PR_MODIFY_AFFIRM_INFO", paramMap) ;
				

				this.insert("ess.tempEmp.modifyObjectInfo", map) ;
			}
		}
		return 1;
	}

	/**
	 * 力量自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTOAbility(List list,Map paramMap) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItemAbility", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("ess.tempEmp.addSSTTempEmpItemAbility", map) ;
			}
		}
		this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * 力量1次考核
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTOAbility(List list,Map paramMap) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItemAbility", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("ess.tempEmp.addSSTTempEmpItemAbility", map) ;
			}
		}
		if(!"0".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		}
		if(!"2".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * 力量自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfSSTAbility(List list,Map paramMap) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItemAbility", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("ess.tempEmp.addSSTTempEmpItemAbility", map) ;
			}
		}
		this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * 目标确认
	 */
	@SuppressWarnings("unchecked")
	public int modifyObjectActivityForAffirm(Object object,Map paramMap)throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				map.put("adminID", paramMap.get("adminID")) ;
				map.put("adminIP", paramMap.get("adminIP")) ;
				map.put("interCpnyID", paramMap.get("interCpnyID")) ;
				this.insert("ess.tempEmp.modifyTempEmpAffirmBySelf", map) ;
				this.insert("ess.tempEmp.modifyObjectActivity", map) ;
			}
		}
		return 1;
	}
	
	/**
	 * 新增考核对象
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addTempEmpObject(Map paramMap) throws Exception {
		paramMap.put("message", "");
		paramMap.put("AFFIRM_ID",paramMap.get("affirmor0"));
		this.insert("ess.tempEmp.PR_ADD_TempEmp_OBJECT", paramMap);
		String returnString = ObjectUtils.toString(paramMap.get("message")) ;
		paramMap.put("TempEmp_OBJECT_SEQ",returnString);
		if(StringUtil.isNumeric(returnString)){
			if(!"".equals(StringUtil.checkNull(paramMap.get("affirmor1")))){
				//1级评价人
				paramMap.put("AFFIRM_ID",paramMap.get("affirmor1"));
				paramMap.put("AFFIRM_LEVEL", "1");
				this.insert("ess.tempEmp.PR_MODIFY_AFFIRM_INFO", paramMap) ;
			}
			if(!"".equals(StringUtil.checkNull(paramMap.get("affirmor2")))){
				//2级评价人
				paramMap.put("AFFIRM_ID",paramMap.get("affirmor2"));
				paramMap.put("AFFIRM_LEVEL", "2");
				this.insert("ess.tempEmp.PR_MODIFY_AFFIRM_INFO", paramMap) ;
			}
			if(!"".equals(StringUtil.checkNull(paramMap.get("affirmor3")))){
				//3级评价人
				paramMap.put("AFFIRM_ID",paramMap.get("affirmor3"));
				paramMap.put("AFFIRM_LEVEL", "3");
				this.insert("ess.tempEmp.PR_MODIFY_AFFIRM_INFO", paramMap) ;
			}
			returnString = "OK";
		}
		return returnString;
	}
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteTempEmpByJson(Object object)throws Exception {
		this.updateForList("ess.tempEmp.deleteTempEmpObjectInfo",(List)object);
		this.updateForList("ess.tempEmp.deleteTempEmpAffirmInfo",(List)object);
		return 1;
	}
	
	/**
	 * 保存确认人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectConfirmInfo(List list,Map paramMap) throws Exception {
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				paramMap.put("RESUME_SEQ", map.get("RESUME_SEQ"));
				paramMap.put("TempEmp_OBJECT_SEQ", map.get("TempEmp_OBJECT_SEQ"));
				//1级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID1"));
				paramMap.put("AFFIRM_LEVEL", "1");
				this.insert("ess.tempEmp.PR_MODIFY_CONFIRM_INFO", paramMap) ;
				//2级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID2"));
				paramMap.put("AFFIRM_LEVEL", "2");
				this.insert("ess.tempEmp.PR_MODIFY_CONFIRM_INFO", paramMap) ;
				//3级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID3"));
				paramMap.put("AFFIRM_LEVEL", "3");
				this.insert("ess.tempEmp.PR_MODIFY_CONFIRM_INFO", paramMap) ;
			}
		}
		return 1;
	}


	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addHrTempEmp(Object object,String target) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("ess.tempEmp." + target, object));
		obj.put("SEQ", seq);
		//附件上传
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName"))) && !"".equals(seq) && !"1".equals(seq)) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "HR_RESUME");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		return 1;
	}


	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("ess.tempEmp." + target, object));
		obj.put("SEQ", seq);
		//附件上传
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName"))) && !"".equals(seq) && !"1".equals(seq)) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "HR_ACTIVITY");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		return 1;
	}

	/**
	 * 考核项目注册
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSSTTempEmpItemProbation(List list,Map paramMap) throws Exception {
		//考核项目设置
		this.delete("ess.tempEmp.deleteSSTTempEmpItem", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("ess.tempEmp.addSSTTempEmpItem", map) ;
			}
		}
		//更新自我评价
		this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap) ;
		//考核者设置
		this.delete("ess.tempEmp.deleteProbationTempEmpAffirm", paramMap) ;
		List<LinkedHashMap> affirmList = (List) paramMap.get("affirmList");
		for (int j = 0; j < affirmList.size(); j++) {
			Map affirmMap = (Map)affirmList.get(j);
			paramMap.put("AFFIRM_LEVEL", affirmMap.get("AFFIRM_LEVEL"));
			paramMap.put("AFFIRMOR_ID", affirmMap.get("AFFIRMOR_ID"));
			paramMap.put("AFFIRM_TYPE", affirmMap.get("AFFIRM_TYPE"));
			this.insert("ess.tempEmp.addProbationTempEmpAffirm", paramMap) ;
		}
		//更新状态
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.modifyProbationActivity", paramMap) ;
		}
		return 1;
	}

	/**
	 * 保存试用期考核信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addProbationTempEmpAffirmInfo(List list,Map paramMap) throws Exception {
		this.delete("ess.tempEmp.deleteSSTTempEmpItemAbility", paramMap);
		this.insertForList("ess.tempEmp.addSSTTempEmpItemAbility", list);

		this.update("ess.tempEmp.modifyTempEmpAffirmBySelf", paramMap);

		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("ess.tempEmp.PR_AFFIRM_EXECUTE", paramMap) ;
		}
		return 1;
	}

	/**
	 * 试用期考核结果保存
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(List list,Map paramMap) throws Exception {

		this.updateForList("ess.tempEmp.saveProbationAffirmRat", list);

		this.update("ess.tempEmp.saveProbationResult", paramMap) ;
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addShopShiftByJson(Object object,String target)throws Exception {
		List list = (List)object;
		this.updateForList("ess.tempEmp." + target,list);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				this.insert("ess.tempEmp.AR_DETAIL_CAL_TEMP_EMP", map) ;
			}
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addMonthDetaiByJson(Object object,String target)throws Exception {
		List list = (List)object;
		this.updateForList("ess.tempEmp." + target,list);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addChangeShopInfo(Object object,String target)throws Exception {
		this.updateForList("ess.tempEmp." + target,(List)object);
		this.updateForList("ess.tempEmp.addChangeShopHistoryInfo" ,(List)object);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteChangeShopInfo(Object object,String target)throws Exception {
		this.update("ess.tempEmp.deleteChangeShopHistoryInfo" ,object);
		this.update("ess.tempEmp." + target,object);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List viewFactoryShiftExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.tempEmp.viewFactoryShiftExcelList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List viewShopShiftExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.tempEmp.viewShopShiftExcelList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List viewPaNotImport(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.tempEmp.viewPaNotImport", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List viewAllShiftExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.tempEmp.viewAllShiftExcelList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询月考勤明细
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMonthDetailList(Object object) {
		List result = null;
		try {
			result = this
					.queryForList("ess.tempEmp.getMonthDetailList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询月考勤明细数量
	 * 
	 * @param Object
	 * @return
	 */
	public int getMonthDetailListCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.tempEmp.getMonthDetailListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
}