package com.ait.evs.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ait.evs.dao.EvsManageDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class EvsManageDaoImpl extends SqlMapClientSupport implements
		EvsManageDao {

	/**
	 * 查询
	 * 
	 * @param Object
	 * @return
	 */
	public List viewEvsList(Object object,String target) {
		List result = null;
		try {
			result = this
					.queryForList("evs.manage." + target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询数量
	 * 
	 * @param Object
	 * @return
	 */
	public int viewEvsCnt(Object object,String target) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("evs.manage." + target, object)), Integer.class) ;
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
	public int addEvsInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("evs.manage." + target, object));
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
					fileMap.put("APPLY_TYPE", "EVS_RESUME");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addEvsInfoByJson(Object object,String target)throws Exception {
		this.updateForList("evs.manage." + target,(List)object);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addEvsInfoByJsonPro(Object object,String target)throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				this.insert("evs.manage." + target, map) ;
			}
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
			this.insert("evs.manage." + target, paramMap) ;	
			paramMap.put("AFFIRM_LEVEL", paramMap.get("LEVEL"));
			this.update("evs.manage.updateAffirmComment", paramMap) ;
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
	public int addEvsResumeInfo(Object object) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("evs.manage.addResumeInfo", object));
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
					fileMap.put("APPLY_TYPE", "EVS_RESUME");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		//初始化评价配置信息
		String returnString = "OK" ;	
		if (obj.get("COPY_OBJECT") != null || !"".equals(obj.get("COPY_OBJECT"))) {
			try {
				LinkedHashMap paramMap = (LinkedHashMap)object;
				paramMap.put("message", "") ;
				this.insert("evs.manage.initEvsInfo", paramMap) ;	
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage();			
				return 0;
			}
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
	public int addSSTEvsItem(List list,Map paramMap) throws Exception {
		this.delete("evs.manage.deleteSSTEvsItem", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
				this.insert("evs.manage.addSSTEvsItem", map) ;
			}
		}
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyObjectActivity", paramMap) ;
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
	public int deleteSSTEvsItem(Object object) throws Exception {
		this.delete("evs.manage.deleteSSTEvsItem", object) ;
		return 1;
	}

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelf(List list,Map paramMap) throws Exception {
		this.updateForList("evs.manage.modifySSTEvsItemBySelf", list) ;
		this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyObjectActivity", paramMap) ;
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
	public int addEvsBySelfHTSV(List list,Map paramMap) throws Exception {
		this.delete("evs.manage.deleteSSTEvsItem", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("evs.manage.addSSTEvsItem", map) ;
			}
		}
		this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyObjectActivity", paramMap) ;
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
	public int addEvsDetailInfoTSTO(List list,Map paramMap) throws Exception {
		if(!"0".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.updateForList("evs.manage.modifySSTEvsItemByAffirmor", list) ;
			this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
		}
		if(!"2".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyObjectActivity", paramMap) ;
		}
		return 1;
	}
	
	/**
	 * 保存评价人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectInfo(List list,Map paramMap) throws Exception {
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				paramMap.put("RESUME_SEQ", map.get("RESUME_SEQ"));
				paramMap.put("EVS_OBJECT_SEQ", map.get("EVS_OBJECT_SEQ"));
				//1级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID1"));
				paramMap.put("AFFIRM_LEVEL", "1");
				this.insert("evs.manage.PR_MODIFY_AFFIRM_INFO", paramMap) ;
				//2级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID2"));
				paramMap.put("AFFIRM_LEVEL", "2");
				this.insert("evs.manage.PR_MODIFY_AFFIRM_INFO", paramMap) ;
				//3级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID3"));
				paramMap.put("AFFIRM_LEVEL", "3");
				this.insert("evs.manage.PR_MODIFY_AFFIRM_INFO", paramMap) ;
				

				this.insert("evs.manage.modifyObjectInfo", map) ;
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
	public int addEvsBySelfTSTOAbility(List list,Map paramMap) throws Exception {
		this.delete("evs.manage.deleteSSTEvsItemAbility", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("evs.manage.addSSTEvsItemAbility", map) ;
			}
		}
		this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyObjectActivity", paramMap) ;
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
	public int addEvsDetailInfoHTSVAbility(List list,Map paramMap) throws Exception {
		if (paramMap.get("EVS_TYPE").equals("Performance")) {
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Map map = (Map)list.get(j);
					this.insert("evs.manage.modifySSTEvsItemScore", map) ;
				}
			}
			if(!"0".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
				this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
			}
			if(!"2".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
				this.insert("evs.manage.modifyObjectActivity", paramMap) ;
			}
		}else {
			this.delete("evs.manage.deleteSSTEvsItemAbility", paramMap) ;
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Map map = (Map)list.get(j);
					this.insert("evs.manage.addSSTEvsItemAbility", map) ;
				}
			}
			if(!"0".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
				this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
			}
			if(!"2".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
				this.insert("evs.manage.modifyObjectActivity", paramMap) ;
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
	public int addEvsBySelfSSTAbility(List list,Map paramMap) throws Exception {
		this.delete("evs.manage.deleteSSTEvsItemAbility", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("evs.manage.addSSTEvsItemAbility", map) ;
			}
		}
		this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyObjectActivity", paramMap) ;
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
				this.insert("evs.manage.modifyEvsAffirmBySelf", map) ;
				this.insert("evs.manage.modifyObjectActivity", map) ;
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
	public String addEvsObject(Map paramMap) throws Exception {
		paramMap.put("message", "");
		paramMap.put("AFFIRM_ID",paramMap.get("affirmor0"));
		this.insert("evs.manage.PR_ADD_EVS_OBJECT", paramMap);
		String returnString = ObjectUtils.toString(paramMap.get("message")) ;
		paramMap.put("EVS_OBJECT_SEQ",returnString);
		if(StringUtil.isNumeric(returnString)){
			if(!"".equals(StringUtil.checkNull(paramMap.get("affirmor1")))){
				//1级评价人
				paramMap.put("AFFIRM_ID",paramMap.get("affirmor1"));
				paramMap.put("AFFIRM_LEVEL", "1");
				this.insert("evs.manage.PR_MODIFY_AFFIRM_INFO", paramMap) ;
			}
			if(!"".equals(StringUtil.checkNull(paramMap.get("affirmor2")))){
				//2级评价人
				paramMap.put("AFFIRM_ID",paramMap.get("affirmor2"));
				paramMap.put("AFFIRM_LEVEL", "2");
				this.insert("evs.manage.PR_MODIFY_AFFIRM_INFO", paramMap) ;
			}
			if(!"".equals(StringUtil.checkNull(paramMap.get("affirmor3")))){
				//3级评价人
				paramMap.put("AFFIRM_ID",paramMap.get("affirmor3"));
				paramMap.put("AFFIRM_LEVEL", "3");
				this.insert("evs.manage.PR_MODIFY_AFFIRM_INFO", paramMap) ;
			}
			returnString = "OK";
		}
		return returnString;
	}
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfoByJson(Object object)throws Exception {
		this.updateForList("evs.manage.deleteEvsObjectInfo",(List)object);
		this.updateForList("evs.manage.deleteEvsAffirmInfo",(List)object);
		return 1;
	}
	
	/**
	 * 保存确认人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectConfirmInfo(List list,Map paramMap) throws Exception {
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				paramMap.put("RESUME_SEQ", map.get("RESUME_SEQ"));
				paramMap.put("EVS_OBJECT_SEQ", map.get("EVS_OBJECT_SEQ"));
				//1级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID1"));
				paramMap.put("AFFIRM_LEVEL", "1");
				this.insert("evs.manage.PR_MODIFY_CONFIRM_INFO", paramMap) ;
				//2级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID2"));
				paramMap.put("AFFIRM_LEVEL", "2");
				this.insert("evs.manage.PR_MODIFY_CONFIRM_INFO", paramMap) ;
				//3级评价人
				paramMap.put("AFFIRM_ID", map.get("AFFIRM_ID3"));
				paramMap.put("AFFIRM_LEVEL", "3");
				this.insert("evs.manage.PR_MODIFY_CONFIRM_INFO", paramMap) ;
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
	public int addHrEvsInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("evs.manage." + target, object));
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
		String seq = StringUtil.checkNull(this.insert("evs.manage." + target, object));
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
	public int addSSTEvsItemProbation(List list,Map paramMap) throws Exception {
		//考核项目设置
		this.delete("evs.manage.deleteSSTEvsItem", paramMap) ;
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map)list.get(j);
				this.insert("evs.manage.addSSTEvsItem", map) ;
			}
		}
		//更新自我评价
		this.update("evs.manage.modifyEvsAffirmBySelf", paramMap) ;
		//考核者设置
		this.delete("evs.manage.deleteProbationEvsAffirm", paramMap) ;
		List<LinkedHashMap> affirmList = (List) paramMap.get("affirmList");
		for (int j = 0; j < affirmList.size(); j++) {
			Map affirmMap = (Map)affirmList.get(j);
			paramMap.put("AFFIRM_LEVEL", affirmMap.get("AFFIRM_LEVEL"));
			paramMap.put("AFFIRMOR_ID", affirmMap.get("AFFIRMOR_ID"));
			paramMap.put("AFFIRM_TYPE", affirmMap.get("AFFIRM_TYPE"));
			this.insert("evs.manage.addProbationEvsAffirm", paramMap) ;
		}
		//更新状态
		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.modifyProbationActivity", paramMap) ;
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
	public int addProbationEvsAffirmInfo(List list,Map paramMap) throws Exception {
		this.delete("evs.manage.deleteSSTEvsItemAbility", paramMap);
		this.insertForList("evs.manage.addSSTEvsItemAbility", list);

		this.update("evs.manage.modifyEvsAffirmBySelf", paramMap);

		if("1".equals(StringUtil.checkNull(paramMap.get("FLAG")))){
			this.insert("evs.manage.PR_AFFIRM_EXECUTE", paramMap) ;
		}
		return 1;
	}

	/**
	 * 试用期考核结果保存
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(List list,Map paramMap) throws Exception {

		this.updateForList("evs.manage.saveProbationAffirmRat", list);

		this.update("evs.manage.saveProbationResult", paramMap) ;
		
		return 1;
	}

	/**
	 * 资料室信息添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addFileRoomInfo(Object object) throws Exception {
		Map obj=(Map)object;
		String seq = StringUtil.checkNull(this.insert("evs.manage.addFileRoomInfo", object));
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
					fileMap.put("APPLY_TYPE", "FILE_ROOM");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		return 1;
	}
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfo(Object object ,String target)throws Exception {
		this.updateForList("evs.manage." + target,(List)object);
		return 1;
	}
}