package com.ait.ess.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface TempEmpSer {
	
	public List viewTempEmpList(HttpServletRequest request,String target);
	
	public List viewTempEmpListWithparam(HttpServletRequest request,String target, String params);
	
	@SuppressWarnings("unchecked")
	public int addFixOtInfo(HttpServletRequest request);

	/**
	 * 查询信息
	 */
	@SuppressWarnings("unchecked")
	public List viewTempEmpList(Map param,String target);

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewTempEmpCnt(HttpServletRequest request,String target);

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewTempEmpCnt(Map param,String target) ;
	
	public int addTempEmp(HttpServletRequest request,String target) ;
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addHrTempEmp(HttpServletRequest request,String target);
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(HttpServletRequest request,String target);
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpByJson(HttpServletRequest request,String target) ;

	/**
	 * 执行存储
	 */
	@SuppressWarnings("unchecked")
	public String addEnsInfoProcedure(HttpServletRequest request,String target) ;
	
	/**
	 * 新增概要信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpResumeInfo(HttpServletRequest request);

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTarget(HttpServletRequest request) ;

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelf(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List viewPaNotImport(HttpServletRequest request);
	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTO(HttpServletRequest request);

	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpByJsonPro(HttpServletRequest request,String target) ;
	
	@SuppressWarnings("unchecked")
	public int deleteFixOtInfo(
			HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int deleteNullFixOtInfo(
			HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getEmpListForFixCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Object getEmpListForFix(HttpServletRequest request);

	/**
	 * TSTO一次考评
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTO(HttpServletRequest request) ;

	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectInfo(HttpServletRequest request) ;

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTOAbility(HttpServletRequest request);

	/**
	 * 力量1次考核
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTOAbility(HttpServletRequest request);

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfSSTAbility(HttpServletRequest request) ;

	/**
	 * 目标确认
	 */
	@SuppressWarnings("unchecked")
	public int modifyObjectActivityForAffirm(HttpServletRequest request);

	/**
	 * 新增考核对象
	 */
	@SuppressWarnings("unchecked")
	public String addTempEmpObject(HttpServletRequest request) ;
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteTempEmpByJson(HttpServletRequest request);
	
	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectConfirmInfo(HttpServletRequest request);

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTargetProbation(HttpServletRequest request) ;

	/**
	 * 保存试用期考核信息
	 */
	@SuppressWarnings("unchecked")
	public int addProbationTempEmpAffirmInfo(HttpServletRequest request);
	
	/**
	 * 保存试用期考核信息
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(HttpServletRequest request);
	
	/**
	 * excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String valImportExcelData(HttpServletRequest request);


	/**
	 * excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelData(HttpServletRequest request);
	
	/**
	 * 排班信息添加
	 */
	@SuppressWarnings("unchecked")
	public int addShopShiftByJson(HttpServletRequest request,String target);
	/**
	 * 
	 * 信息添加
	 * */
	@SuppressWarnings("unchecked")
	public int addMonthDetaiByJson(HttpServletRequest request,String target);
	
	/**
	 * 员工调店
	 */
	@SuppressWarnings("unchecked")
	public int addChangeShopInfo(HttpServletRequest request,String target);
	/**
	 * 删除员工调店
	 */
	@SuppressWarnings("unchecked")
	public int deleteChangeShopInfo(HttpServletRequest request,String target);
	
	@SuppressWarnings("unchecked")
	public List viewFactoryShiftExcelList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List viewShopShiftExcelList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List viewAllShiftExcelList(HttpServletRequest request,String postFamily);
	
	@SuppressWarnings("unchecked")
	public List getMonthDetailList(HttpServletRequest request);
	
	public int getMonthDetailListCnt(HttpServletRequest request) ;
}
