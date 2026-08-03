package com.ait.ess.dao;

import java.util.List;
import java.util.Map;

public interface TempEmpDao {
	
	public List viewTempEmpList(Object object,String target) ;
	
	@SuppressWarnings("unchecked")
	public void addFixOtInfo(Object object)throws Exception;
	
	/**
	 * 查询数量
	 * 
	 * @param Object
	 * @return
	 */
	public int viewTempEmpCnt(Object object,String target) ;
	
	public int addTempEmp(Object object,String target)throws Exception ;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(Object object,String target) throws Exception;
	
	public int addTempEmpByJson(Object object,String target)throws Exception ;

	@SuppressWarnings("unchecked")
	public int addTempEmpByJsonPro(Object object,String target)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int deleteFixOtInfo(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public List viewPaNotImport(Object object);
	
	@SuppressWarnings("unchecked")
	public int deleteNullFixOtInfo(Object object) throws Exception;
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
	public int addTempEmpResumeInfo(Object object) throws Exception ;

	/**
	 * 考核项目注册
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSSTTempEmpItem(List list,Map paramMap) throws Exception;

	/**
	 * 删除考核项目  
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteSSTTempEmpItem(Object object) throws Exception;

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelf(List list,Map paramMap) throws Exception ;

	/**
	 * 自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTO(List list,Map paramMap) throws Exception;

	/**
	 * TSTO一次考评
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTO(List list,Map paramMap) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmpListForFix(Map paramMap, int pageNum, int numPerPage);
	
	@SuppressWarnings("unchecked")
	public int getEmpListForFixCnt(Map paramMap);

	/**
	 * 保存评价人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectInfo(List list,Map paramMap) throws Exception ;

	/**
	 * 力量自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTOAbility(List list,Map paramMap) throws Exception ;

	/**
	 * 力量1次考核
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTOAbility(List list,Map paramMap) throws Exception ;

	/**
	 * 力量自我评价
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfSSTAbility(List list,Map paramMap) throws Exception;

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
	public String addTempEmpObject(Map paramMap) throws Exception;
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteTempEmpByJson(Object object)throws Exception;
	
	/**
	 * 保存确认人
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectConfirmInfo(List list,Map paramMap) throws Exception ;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addHrTempEmp(Object object,String target) throws Exception ;

	/**
	 * 考核项目注册
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSSTTempEmpItemProbation(List list,Map paramMap) throws Exception;

	/**
	 * 保存试用期考核信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addProbationTempEmpAffirmInfo(List list,Map paramMap) throws Exception ;

	/**
	 * 试用期考核结果保存
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(List list,Map paramMap) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int addShopShiftByJson(Object object,String target)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int addMonthDetaiByJson(Object object,String target)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int addChangeShopInfo(Object object,String target)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public int deleteChangeShopInfo(Object object,String target)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public List viewFactoryShiftExcelList(Object object);
	@SuppressWarnings("unchecked")
	public List viewShopShiftExcelList(Object object);
	@SuppressWarnings("unchecked")
	public List viewAllShiftExcelList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getMonthDetailList(Object object);
	
	public int getMonthDetailListCnt(Object object);
}