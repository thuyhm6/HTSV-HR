package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.DimissionApplyDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class DimissionApplyDaoImpl extends SqlMapClientSupport implements
		DimissionApplyDao {
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	private static String APPLY_TYPE_NO = "218296";

	//离职申请决裁邀请名称
	private static String APPLY_TYPE_NAME = "离职审批邀请";

	public String getDimissionInfoSeq() throws Exception {
		return ObjectUtils.toString(this
				.queryForObject("ess.dimissionApply.getDimissionInfoSeq"));
	}
	
	/**
	 * 检查是否存在该人的离职信息
	 * @param object
	 * @return
	 */
	public int checkAddDimissionInfo(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.dimissionApply.checkAddDimissionInfo", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 离职申请(apply)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addDimissionInfoApply(LinkedHashMap map) throws Exception {
		if (map != null) {

			LinkedHashMap tempMap = new LinkedHashMap();
			LinkedHashMap tempMapuser = new LinkedHashMap();
			Object object = null;
			LinkedHashMap mapobj = new LinkedHashMap();
			if (map != null && map.get("PARAM_MAP") != null) {
				mapobj = (LinkedHashMap) map.get("PARAM_MAP");
				object = mapobj.get("PERSON_ID");
				tempMap.put("CREATED_BY", mapobj.get("CREATED_BY"));
				tempMap.put("APPLY_NO", mapobj.get("APPLY_NO"));
				try {
					this.insert("ess.dimissionApply.addDimissionInfoApply",mapobj);
					this.sendToLGEPInsert(mapobj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map != null && map.get("DISTINCT_LIST") != null) {
				List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
				if (aList != null && aList.size() > 0) {
					for (LinkedHashMap parmers : aList) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
						tempMap.put("APPLY_NO", mapobj.get("APPLY_NO"));

						try {
							this.insert("ess.dimissionApply.addDimissionApplyReviewer",tempMap);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewEditionAffirmsList?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewEditionAffirmsList?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	
	/**
	 * 查找离职申请的List详情数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getDimissionCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.dimissionApply.getDimissionCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 查找离职申请的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getDimissionList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getDimissionList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找离职申请的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getDimissionList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.dimissionApply.getDimissionList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.dimissionApply.getDimissionList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 删除还没有审批的离职申请信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public void deleteDimissionInfo(Object object) throws SQLException{
		LinkedHashMap map = (LinkedHashMap)object;
		map.put("APPLY_TYPE", APPLY_TYPE_NO);
		//删除发送LGEP
		affirmInfoToLGEPSer.deleteAffirm(map);
		
		this.delete("ess.dimissionApply.delDimissionAffirmByApplyNo", object);
		this.delete("ess.dimissionApply.delDimissionApplyByApplyNo", object);
	}
}
