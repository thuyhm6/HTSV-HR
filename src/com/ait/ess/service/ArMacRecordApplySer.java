package com.ait.ess.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 信息申请
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: ArMacRecordApplySer.java
 * @Description:
 * @Create date: Feb 17, 2012 2:02:09 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 2:02:09 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface ArMacRecordApplySer {
	/**
	 * 添加进出门刷卡申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addArMacRecordApply(HttpServletRequest request) throws Exception;
	
	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorList(HttpServletRequest request) throws Exception;
	
	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addArMacRecordApplyInBatch(HttpServletRequest request) throws Exception;

	/**
	 * 人员查询(search person list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(HttpServletRequest request) throws Exception;
	
	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPersonListCnt(HttpServletRequest request) throws Exception;
	
	/**
	 * in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmList(HttpServletRequest request)
			throws Exception;
	
	/**
	 * in/out进出门刷卡数据信息申请数据查询数量(view ar mac record apply info count)
	 * 
	 * @param request
	 * @return
	 */
	public int getArMacRecordAffirmListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewList(HttpServletRequest request,String batchFlag)
			throws Exception;
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询数量(view ar mac record apply info count)
	 * 
	 * @param request
	 * @return
	 */
	public int getArMacRecordAffirmViewListCnt(HttpServletRequest request,String batchFlag)
			throws Exception;
	
	/**
	 * 通过/否决：in/out进出门刷卡申请(pass and reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveArMacRecordApply(HttpServletRequest request)
			throws Exception;
	
	public int approveArMacRecordApplyEP(HttpServletRequest request)
	        throws Exception;
	
	/**
	 * 批量通过/否决：in/out进出门刷卡申请(batch pass and reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveArMacRecordApplyInBatch(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 删除未审核：in/out进出门刷卡信息申请(delete ar mac record apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delArMacRecordApplyInfo(HttpServletRequest request)
			throws Exception;

	public int saveRecordAppFile(HttpServletRequest request, Map<String, Object> map);

	public List getCardRecordFileList(HttpServletRequest request);
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmCheckViewList(HttpServletRequest request)throws Exception ;
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请个数(view ar mac record apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getArMacRecordAffirmCheckViewListCnt(HttpServletRequest request)
			throws Exception ;
	
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewListBySingle(HttpServletRequest request)throws Exception ;
	
	/**
	 * 获取漏刷卡导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArMacTempList(HttpServletRequest request);
	
	/**
	 * 获取漏刷卡导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 漏刷卡批量申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssArMacEmpData(HttpServletRequest request);
	
	/**
	 * 漏刷卡批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacBatchAffirmInfoList(HttpServletRequest request) throws Exception ;
	
	/**
	 * 漏刷卡批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArMacBatchAffirmInfoCnt(HttpServletRequest request);
	
	/**
	 * 批量删除漏刷卡申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delArMacApplyInBatchForBatch(HttpServletRequest request)
			throws Exception ;
	
	/**
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getArMacRecordApplyInfoForDisplay(HttpServletRequest request)throws Exception ;
	
	/**
	 * 取消已审核通过的漏刷卡申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean cancelCardApply(HttpServletRequest request)throws Exception ;
	
}