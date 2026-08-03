package com.ait.evs.dao;

import java.util.List;
import java.util.Map;

public interface EvsManageDao {
	
	public List viewEvsList(Object object,String target) ;
	
	/**
	 * 查询数量
	 * 
	 * @param Object
	 * @return
	 */
	public int viewEvsCnt(Object object,String target) ;
	
	public int addEvsInfo(Object object,String target)throws Exception ;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(Object object,String target) throws Exception;
	
	public int addEvsInfoByJson(Object object,String target)throws Exception ;

	@SuppressWarnings("unchecked")
	public int addEvsInfoByJsonPro(Object object,String target)throws Exception;
	/**
	 * 调用存储过程
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addEnsInfoProcedure(Object object,String target)  throws Exception;


	/**
	 * 新增概要信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsResumeInfo(Object object) throws Exception ;

	/**
	 * 考核项目注册
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSSTEvsItem(List list,Map paramMap) throws Exception;

	/**
	 * 删除考核项目  
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteSSTEvsItem(Object object) throws Exception;

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelf(List list,Map paramMap) throws Exception ;

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfHTSV(List list,Map paramMap) throws Exception;

	/**
	 * TSTO一次考评
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsDetailInfoTSTO(List list,Map paramMap) throws Exception;

	/**
	 * 保存评价人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectInfo(List list,Map paramMap) throws Exception ;

	/**
	 * 力量自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfTSTOAbility(List list,Map paramMap) throws Exception ;

	/**
	 * 力量1次考核
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsDetailInfoHTSVAbility(List list,Map paramMap) throws Exception ;

	/**
	 * 力量自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEvsBySelfSSTAbility(List list,Map paramMap) throws Exception;

	/**
	 * 目标确认
	 */
	@SuppressWarnings("unchecked")
	public int modifyObjectActivityForAffirm(Object object,Map paramMap)throws Exception ;

	/**
	 * 新增考核对象
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addEvsObject(Map paramMap) throws Exception;
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteEvsInfoByJson(Object object)throws Exception;
	
	/**
	 * 保存确认人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveEvsObjectConfirmInfo(List list,Map paramMap) throws Exception ;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addHrEvsInfo(Object object,String target) throws Exception ;

	/**
	 * 考核项目注册
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSSTEvsItemProbation(List list,Map paramMap) throws Exception;

	/**
	 * 保存试用期考核信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addProbationEvsAffirmInfo(List list,Map paramMap) throws Exception ;

	/**
	 * 试用期考核结果保存
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(List list,Map paramMap) throws Exception;

	/**
	 * 资料室信息添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addFileRoomInfo(Object object) throws Exception;
	
	public int deleteEvsInfo(Object object,String target)throws Exception ;
}