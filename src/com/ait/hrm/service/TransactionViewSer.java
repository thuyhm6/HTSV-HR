package com.ait.hrm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 发令查看(Transaction view)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionViewSer.java
 * @Description:
 * @Create date: Feb 27, 2012 1:41:17 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 27, 2012 1:41:17 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface TransactionViewSer {

	/**
	 * 调动发令列表(view transaction transaction list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransactionTransViewList(HttpServletRequest request)
			throws Exception;

	/**
	 * 调动发令总数(get transaction transaction total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getTransactionTransViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 入职发令列表(view entryTrans transaction list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransViewList(HttpServletRequest request)
			throws Exception;

	/**
	 * 入职发令总数(get entryTrans transaction total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getEntryTransViewListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量取消发令(batch cancel transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelHrExperienceInsideByNo(HttpServletRequest request)
			throws Exception;

	/**
	 * 查看员工历史发令信息列表(get employee transaction history list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeTransHistoryList(HttpServletRequest request)
			throws Exception;

	/**
	 * 查看员工历史发令信息总数(get employee transaction history total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getEmployeeTransHistoryListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 入职发令信息查看(view entry transaction information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Map viewEntryTransInfo(HttpServletRequest request) throws Exception;
	
	
	
	/**
	 * 转正发令列表(TransferNormal the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferNormalViewList(HttpServletRequest request)
			throws Exception;

	/**
	 * 转正发令总数(TransferNormal the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getTransferNormalViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量取消转正发令(batch cancel transaction for transferNormal)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelHrTransferNormalInsideByNo(HttpServletRequest request)
			throws Exception;
	
	
	/**
	 * 离职发令列表(Resignation the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResignationViewList(HttpServletRequest request)
			throws Exception;

	/**
	 * 离职发令总数(Resignation the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getResignationViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量取消离职发令(batch cancel transaction for Resignation)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelResignationInBatchInsideByNo(HttpServletRequest request)
			throws Exception;
	
	
	/**
	 * 晋升发令列表(view transaction transaction list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferPromoteForSearch(HttpServletRequest request)
			throws Exception;

	/**
	 * 晋升发令总数(get transaction transaction total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getTransferPromoteForSearchCnt(HttpServletRequest request)
			throws Exception;
	

	/**
	 * 兼职发令列表(Plurality the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityViewList(HttpServletRequest request)
			throws Exception;

	/**
	 * 兼职发令总数(Plurality the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPluralityViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量取消兼职发令(batch cancel transaction for Plurality)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelPluralityBatchInsideByNo(HttpServletRequest request)
			throws Exception;
	
	/**  
	 * 停职发令列表(Suspend the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendViewList(HttpServletRequest request)
			throws Exception;

	/**  
	 * 停职发令总数(Suspend the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getSuspendViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/** 
	 * 批量取消停职发令(batch cancel transaction for Suspend)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelSuspendBatchInsideByNo(HttpServletRequest request)
			throws Exception;
	/**
	 * 查看员工的停职历史记录
	 * Description:
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeSuspendHistoryList(HttpServletRequest request)
			throws Exception;

	/**  
	 * 奖励发令列表(Hortation the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHortationViewList(HttpServletRequest request)
			throws Exception;

	/**  
	 * 奖励发令总数(Hortation the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getHortationViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/** 
	 * 批量取消奖励发令(batch cancel transaction for Hortation)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelHortationBatchInsideByNo(HttpServletRequest request)
			throws Exception;
	
	
	/**  
	 * 惩戒发令列表(PunishMent the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentViewList(HttpServletRequest request)
			throws Exception;

	/**  
	 * 惩戒发令总数(PunishMent the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPunishMentViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/** 
	 * 批量取消惩戒发令(batch cancel transaction for PunishMent)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelPunishMentBatchInsideByNo(HttpServletRequest request)
			throws Exception;

	/**  
	 * 薪资调整发令列表(PaAdjust the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustViewList(HttpServletRequest request)
			throws Exception;

	/** 
	 * 薪资调整发令总数(PaAdjust the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPaAdjustViewListCnt(HttpServletRequest request)
			throws Exception;
	
	/** 
	 * 批量取消薪资调整发令(batch cancel transaction for PaAdjust)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelPaAdjustBatchInsideByNo(HttpServletRequest request)
			throws Exception;
	

	/**  
		 * 代理发令列表(Agent the starting list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getAgentViewList(HttpServletRequest request)
				throws Exception;

		/**  
		 * 代理发令总数(Agent the total)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public int getAgentViewListCnt(HttpServletRequest request)
				throws Exception;
		
		/** 
		 * 批量取消代理发令(batch cancel transaction for Agent)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public int cancelAgentBatchInsideByNo(HttpServletRequest request)
				throws Exception;

		/**
		 * 查看入职发令temp表的数据(view entry transaction)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public Object viewEntryTransTempInfo(HttpServletRequest request)throws Exception;

		@SuppressWarnings("rawtypes")
		public List getConfirmReqHire(HttpServletRequest request) throws Exception ;

		@SuppressWarnings("rawtypes")
		public List getApplyFeeList(String deptNo, HttpServletRequest request) throws Exception ;

}
