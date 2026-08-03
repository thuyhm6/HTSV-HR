package com.ait.hrm.dao;

import java.util.List;
import java.util.Map;

/**
 * 发令查看(Transaction view)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionViewDao.java
 * @Description:
 * @Create date: Feb 27, 2012 1:45:59 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 27, 2012 1:45:59 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface TransactionViewDao {
	/**
	 * 调动发令列表(view transaction transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransactionTransViewList(Object object) throws Exception;

	/**
	 * 调动发令列表(view transaction transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransactionTransViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 调动发令总数(get transaction transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getTransactionTransViewListCnt(Object object) throws Exception;

	/**
	 * 入职发令列表(view Entry transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransViewList(Object object) throws Exception;

	/**
	 * 入职发令列表(view Entry transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 入职发令总数(get Entry transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getEntryTransViewListCnt(Object object) throws Exception;

	/**
	 * 取消发令 入职/调动/晋升/降职共用(cancel HrExperienceInside)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int cancelHrExperienceInsideByNo(List list) throws Exception;

	/**
	 * 查看员工历史发令信息列表(get employee transaction history list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeTransHistoryList(Object object) throws Exception;

	/**
	 * 查看员工历史发令信息列表(get employee transaction history list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeTransHistoryList(Object object, int currentPage,
			int pageSize);

	/**
	 * 查看员工历史发令信息总数(get employee transaction history total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getEmployeeTransHistoryListCnt(Object object) throws Exception;

	/**
	 * 获得单个发令里决裁过的总数(get transaction affirmed total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPassHrAffirmCountByExpInsideNo(Object obj) throws Exception;

	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsideNo(Object obj) throws Exception;

	/**
	 * 入职发令信息查看(view entry transaction information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Map viewEntryTransInfo(Object obj) throws Exception;

	
	/**
	 * 转正发令列表(TransferNormal the starting list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferNormalViewList(Object object) throws Exception;

	/**
	 * 转正发令列表(TransferNormal the starting list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferNormalViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 转正发令总数(TransferNormal the total)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getTransferNormalViewListCnt(Object object) throws Exception;
	
	/**
	 * 取消发令 转正(cancel HrTransferNormal)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int cancelHrTransferNormalInsideByNo(List list) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for TransferNormal)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsideNoForTransferNormal(Object obj) throws Exception;

	
	
	/**
	 * 离职发令列表(resignation the starting list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResignationViewList(Object object) throws Exception;

	/**
	 * 离职发令列表(resignation the starting list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getResignationViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 离职发令总数(resignation the total)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getResignationViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for Resignation)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsideNoForResignation(Object obj) throws Exception;
	
	
	/**
	 * 取消发令 离职(cancel HrResignation)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int cancelHrResignationInsideByNo(List list) throws Exception;
	
	/**
	 * 晋升发令列表(view transaction transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferPromoteForSearch(Object object) throws Exception;

	/**
	 * 晋升发令列表(view transaction transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferPromoteForSearch(Object object, int currentPage,
			int pageSize);

	/**
	 * 晋升发令总数(get transaction transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getTransferPromoteForSearchCnt(Object object) throws Exception;
	
	/**
	 * 兼职发令列表(view Suspend transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityViewList(Object object) throws Exception;

	/**
	 * 兼职发令列表(view Plurality transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 兼职发令总数(get Plurality transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPluralityViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for Plurality)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsidePluralityNo(Object obj) throws Exception;
	
	/**
	 * 取消兼职发令(cancel HrPlurality)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrPluralityByNo(List list) throws Exception;
	
	/**
	 * 停职发令列表(view Suspend transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendViewList(Object object) throws Exception;

	/**
	 * 停职发令列表(view Suspend transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 停职发令总数(get Suspend transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getSuspendViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for Suspend)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsideSuspendNo(Object obj) throws Exception;
	
	/**
	 * 取消停职发令(cancel HrSuspend)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrSuspendByNo(List list) throws Exception;
	
	/**
	 * 奖励发令列表(view Hortation transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHortationViewList(Object object) throws Exception;

	/**
	 * 奖励发令列表(view Hortation transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getHortationViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 奖励发令总数(get Hortation transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getHortationViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for Hortation)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsideHortationNo(Object obj) throws Exception;
	
	/**
	 * 取消奖励发令(cancel HrHortation)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrHortationByNo(List list) throws Exception;

	/**
	 * 惩戒发令列表(view transaction PunishMent list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentViewList(Object object) throws Exception;

	/**
	 * 惩戒发令列表(view PunishMent transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 惩戒发令总数(get PunishMent transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPunishMentViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for PunishMent)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsidePunishMentNo(Object obj) throws Exception;
	
	/**
	 * 取消惩戒发令(cancel HrPunishMent)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrPunishMentByNo(List list) throws Exception;

	/**
	 * 薪资调整发令列表(view PaAdjust transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustViewList(Object object) throws Exception;

	/**
	 * 薪资调整发令列表(view PaAdjust transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 薪资调整发令总数(get PaAdjust transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPaAdjustViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for PaAdjust)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsidePaAdjustNo(Object obj) throws Exception;
	
	/**
	 * 取消薪资调整发令(cancel HrPaAdjust)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrPaAdjustByNo(List list) throws Exception;

	/**
	 * 代理发令列表(view transaction transaction list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAgentViewList(Object object) throws Exception;

	/**
	 * 代理发令列表(view Agent transaction list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 代理发令总数(get Agent transaction total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getAgentViewListCnt(Object object) throws Exception;
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for Agent)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int ifEffectByExpInsideAgentNo(Object obj) throws Exception;
	
	/**
	 * 取消代理发令(cancel HrAgent)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrAgentByNo(List list) throws Exception;

	/**
	 * 查看入职发令temp表的数据(view entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map viewEntryTransTempInfo(Object obj)throws Exception;

}
