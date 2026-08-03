package com.ait.evs.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface EvsManageSer {
	
	public List viewEvsInfoList(HttpServletRequest request,String target);

	/**
	 * 查询信息
	 */
	@SuppressWarnings("unchecked")
	public List viewEvsInfoList(Map param,String target);

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewEvsInfoCnt(HttpServletRequest request,String target);

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewEvsInfoCnt(Map param,String target) ;
	
	public int addEvsInfo(HttpServletRequest request,String target) ;

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addHrEvsInfo(HttpServletRequest request,String target);
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(HttpServletRequest request,String target);
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addEvsInfoByJson(HttpServletRequest request,String target) ;

	/**
	 * 执行存储
	 */
	@SuppressWarnings("unchecked")
	public String addEnsInfoProcedure(HttpServletRequest request,String target) ;
	
	/**
	 * 新增概要信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsResumeInfo(HttpServletRequest request);

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTarget(HttpServletRequest request) ;

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelf(HttpServletRequest request) ;

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfHTSV(HttpServletRequest request);

	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addEvsInfoByJsonPro(HttpServletRequest request,String target) ;

	/**
	 * TSTO一次考评
	 */
	@SuppressWarnings("unchecked")
	public int addEvsDetailInfoTSTO(HttpServletRequest request) ;

	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectInfo(HttpServletRequest request) ;

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfTSTOAbility(HttpServletRequest request);

	/**
	 * 力量1次考核
	 */
	@SuppressWarnings("unchecked")
	public int addEvsDetailInfoHTSVAbility(HttpServletRequest request);

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfSSTAbility(HttpServletRequest request) ;

	/**
	 * 目标确认
	 */
	@SuppressWarnings("unchecked")
	public int modifyObjectActivityForAffirm(HttpServletRequest request);

	/**
	 * 新增考核对象
	 */
	@SuppressWarnings("unchecked")
	public String addEvsObject(HttpServletRequest request) ;
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfoByJson(HttpServletRequest request);
	
	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectConfirmInfo(HttpServletRequest request);

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTargetProbation(HttpServletRequest request) ;

	/**
	 * 保存试用期考核信息
	 */
	@SuppressWarnings("unchecked")
	public int addProbationEvsAffirmInfo(HttpServletRequest request);
	/**
	 * 保存试用期考核信息
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(HttpServletRequest request);

	/**
	 * 资料室信息添加
	 */
	@SuppressWarnings("unchecked")
	public int addFileRoomInfo(HttpServletRequest request);
	
	/**
	 * 删除项目
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfo(HttpServletRequest request,String target) ;
	
	@SuppressWarnings("rawtypes")
	public String evsAffirmorImportDemo(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
	
	@SuppressWarnings("rawtypes")
	public String evsAffirmTargetImportDemo(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
}
