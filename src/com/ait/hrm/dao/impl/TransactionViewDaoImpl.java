package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.TransactionViewDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * 发令查看(Transaction view)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionViewDaoImpl.java
 * @Description:
 * @Create date: Feb 27, 2012 1:44:28 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 27, 2012 1:44:28 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Repository
public class TransactionViewDaoImpl extends SqlMapClientSupport implements
		TransactionViewDao {
	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getTransactionTransViewList(Object obj) throws Exception {
		return this.getTransactionTransViewList(obj, -1, -1);
	}

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getTransactionTransViewList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getTransactionTransViewList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getTransactionTransViewList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 调动决裁总数(get transaction transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTransactionTransViewListCnt(Object obj) throws Exception {
		try {
			return NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transView.getTransactionTransViewListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 入职发令列表(view entry transaction list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getEntryTransViewList(Object obj) throws Exception {
		return this.getEntryTransViewList(obj, -1, -1);
	}

	/**
	 * 入职发令列表(view entry transaction list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getEntryTransViewList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getEntryTransViewList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getEntryTransViewList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 调动决裁总数(get view transaction total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEntryTransViewListCnt(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transView.getEntryTransViewListCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 取消发令 入职/调动/晋升/降职共用(cancel HrExperienceInside)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public int cancelHrExperienceInsideByNo(List list) throws Exception {
		Object obj = null;
		try {
			obj = this.updateForList(
					"hrm.transView.cancelHrExperienceInsideByNo", list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 查看员工历史发令信息列表(get employee transaction history list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getEmployeeTransHistoryList(Object obj) throws Exception {
		return this.getEmployeeTransHistoryList(obj, -1, -1);
	}

	/**
	 * 查看员工历史发令信息列表(get employee transaction history list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getEmployeeTransHistoryList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getEmployeeTransHistoryList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getEmployeeTransHistoryList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 员工历史发令信息总数(get employee transaction history total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEmployeeTransHistoryListCnt(Object obj) throws Exception {
		try {
			return NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transView.getEmployeeTransHistoryListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得单个发令里决裁过的总数(get transaction affirmed total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPassHrAffirmCountByExpInsideNo(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transView.getPassHrAffirmCountByExpInsideNo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ifEffectByExpInsideNo(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transView.getHrExperienceInsideActivityByExpInsideNo", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 入职发令信息查看(view entry transaction information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map viewEntryTransInfo(Object obj) throws Exception {
		try {
			return (Map) this.queryForObject(
					"hrm.transView.viewEntryTransInfoNew", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 入职发令信息查看temp表里的数据(view entry transaction information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map viewEntryTransTempInfo(Object obj) throws Exception {
		try {
			return (Map) this.queryForObject(
					"hrm.transView.viewEntryTransTempInfoNew", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 转正决裁列表(view transaction TransferNormal affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getTransferNormalViewList(Object obj) throws Exception {
		return this.getTransferNormalViewList(obj, -1, -1);
	}

	/**
	 * 转正决裁列表(view transaction TransferNormal affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getTransferNormalViewList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getTransferNormalViewList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getTransferNormalViewList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 转正决裁总数(get transaction TransferNormal affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTransferNormalViewListCnt(Object obj) throws Exception {
		try {
			return NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transView.getTransferNormalViewListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 取消发令 转正(cancel HrTransferNormalInside)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public int cancelHrTransferNormalInsideByNo(List list) throws Exception {
		Object obj = null;
		try {
			obj = this.updateForList(
					"hrm.transView.cancelHrTransferNormalInsideByNo", list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for TransferNormal)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ifEffectByExpInsideNoForTransferNormal(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transView.ifEffectByExpInsideNoForTransferNormal", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	/**
	 * 离职决裁列表(view transaction TransferNormal affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getResignationViewList(Object obj) throws Exception {
		return this.getResignationViewList(obj, -1, -1);
	}

	/**
	 * 离职决裁列表(view transaction TransferNormal affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getResignationViewList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getResignationViewList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getResignationViewList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 离职决裁总数(get transaction TransferNormal affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getResignationViewListCnt(Object obj) throws Exception {
		try {
			return NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transView.getResignationViewListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect for Resignation)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ifEffectByExpInsideNoForResignation(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transView.ifEffectByExpInsideNoForResignation", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 取消发令 离职(cancel HrResignation)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public int cancelHrResignationInsideByNo(List list) throws Exception {
		Object obj = null;
		try {
			obj = this.updateForList(
					"hrm.transView.cancelHrResignationInsideByNo", list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 晋升决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getTransferPromoteForSearch(Object obj) throws Exception {
		return this.getTransferPromoteForSearch(obj, -1, -1);
	}

	/**
	 * 晋升决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferPromoteForSearch(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getTransferPromoteForSearch", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getTransferPromoteForSearch", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 晋升决裁总数(get transaction transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTransferPromoteForSearchCnt(Object obj) throws Exception {
		try {
			return NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transView.getTransferPromoteForSearchCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 兼职决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPluralityViewList(Object object) throws Exception {
		return this.getPluralityViewList(object, -1, -1);
	}
	/**
	 * 惩戒决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPluralityViewList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transView.getPluralityViewList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transView.getPluralityViewList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 兼职决裁列表数量(select count)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public int getPluralityViewListCnt(Object object) throws Exception {
		try {
			return NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transView.getPluralityViewListCnt",
													object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获得单个发令里决裁是否生效(get transaction if effect)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ifEffectByExpInsidePluralityNo(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transView.ifEffectByExpInsidePluralityNo", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 取消发令 兼职(cancel HrPlurality)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelHrPluralityByNo(List list) throws Exception {
		try {
			 this.updateForList(
					"hrm.transView.cancelHrPluralityByNo", list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
		 * 停职决裁列表(view Suspend transaction affirm list)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getSuspendViewList(Object object) throws Exception {
			return this.getSuspendViewList(object, -1, -1);
		}
		/**
		 * 停职决裁列表(view Suspend transaction affirm list)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getSuspendViewList(Object object, int currentPage,
				int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList(
							"hrm.transView.getSuspendViewList", object,
							currentPage, pageSize);
				} else {
					returnList = this.queryForList(
							"hrm.transView.getSuspendViewList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		/**
		 * 停职决裁列表数量(select count)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@Override
		public int getSuspendViewListCnt(Object object) throws Exception {
			try {
				return NumberUtils
						.parseNumber(
								ObjectUtils
										.toString(this
												.queryForObject(
														"hrm.transView.getSuspendViewListCnt",
														object)), Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		/**
		 * 获得单个发令里决裁是否生效(get transaction if effect)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int ifEffectByExpInsideSuspendNo(Object obj) throws Exception {
			try {
				return NumberUtils.parseNumber(ObjectUtils
						.toString(this.queryForObject(
								"hrm.transView.ifEffectByExpInsideSuspendNo", obj)),
						Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		/**
		 * 取消发令 停职(cancel HrSuspend)
		 * 
		 * @param list
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int cancelHrSuspendByNo(List list) throws Exception {
			try {
				 this.updateForList(
						"hrm.transView.cancelHrSuspendByNo", list);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
		/**
		 * 奖励决裁列表(view Plurality transaction affirm list)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getHortationViewList(Object object) throws Exception {
			return this.getHortationViewList(object, -1, -1);
		}
		/**
		 * 奖励决裁列表(view transaction transaction affirm list)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getHortationViewList(Object object, int currentPage,
				int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList(
							"hrm.transView.getHortationViewList", object,
							currentPage, pageSize);
				} else {
					returnList = this.queryForList(
							"hrm.transView.getHortationViewList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		/**
		 * 奖励决裁列表数量(select count)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@Override
		public int getHortationViewListCnt(Object object) throws Exception {
			try {
				return NumberUtils
						.parseNumber(
								ObjectUtils
										.toString(this
												.queryForObject(
														"hrm.transView.getHortationViewListCnt",
														object)), Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		/**
		 * 获得单个发令里决裁是否生效(get transaction if effect)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int ifEffectByExpInsideHortationNo(Object obj) throws Exception {
			try {
				return NumberUtils.parseNumber(ObjectUtils
						.toString(this.queryForObject(
								"hrm.transView.ifEffectByExpInsideHortationNo", obj)),
						Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		/**
		 * 取消发令 奖励(cancel HrHortation)
		 * @param list
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int cancelHrHortationByNo(List list) throws Exception {
			try {
					this.updateForList(
						"hrm.transView.cancelHrHortationByNo", list);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
		/**
		 * 惩戒决裁列表(view transaction PunishMent affirm list)
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getPunishMentViewList(Object object) throws Exception {
			return this.getPunishMentViewList(object, -1, -1);
		}
		
		/**
		 * 惩戒决裁列表(view transaction transaction affirm list)
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getPunishMentViewList(Object object, int currentPage,
				int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList(
							"hrm.transView.getPunishMentViewList", object,
							currentPage, pageSize);
				} else {
					returnList = this.queryForList(
							"hrm.transView.getPunishMentViewList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		/**
		 * 惩戒决裁列表数量(select count)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@Override
		public int getPunishMentViewListCnt(Object object) throws Exception {
			try {
				return NumberUtils
						.parseNumber(
								ObjectUtils
										.toString(this
												.queryForObject(
														"hrm.transView.getPunishMentViewListCnt",
														object)), Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		/**
		 * 获得单个发令里决裁是否生效(get transaction if effect)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int ifEffectByExpInsidePunishMentNo(Object obj) throws Exception {
			try {
				return NumberUtils.parseNumber(ObjectUtils
						.toString(this.queryForObject(
								"hrm.transView.ifEffectByExpInsidePunishMentNo", obj)),
						Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		/**
		 * 取消发令 兼职(cancel HrPunishMent)
		 * 
		 * @param list
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int cancelHrPunishMentByNo(List list) throws Exception {
			try {
				 this.updateForList(
						"hrm.transView.cancelHrPunishMentByNo", list);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
		/**
		 * 薪资调整决裁列表(view PaAdjust transaction affirm list)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getPaAdjustViewList(Object object) throws Exception {
			return this.getPaAdjustViewList(object, -1, -1);
		}
		/**
		 * 薪资调整决裁列表(view transaction transaction affirm list)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getPaAdjustViewList(Object object, int currentPage,
				int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList(
							"hrm.transView.getPaAdjustViewList", object,
							currentPage, pageSize);
				} else {
					returnList = this.queryForList(
							"hrm.transView.getPaAdjustViewList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		/**
		 * 薪资调整决裁列表数量(select count)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@Override
		public int getPaAdjustViewListCnt(Object object) throws Exception {
			try {
				return NumberUtils
						.parseNumber(
								ObjectUtils
										.toString(this
												.queryForObject(
														"hrm.transView.getPaAdjustViewListCnt",
														object)), Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		/**
		 * 获得单个发令里决裁是否生效(get transaction if effect)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int ifEffectByExpInsidePaAdjustNo(Object obj) throws Exception {
			try {
				return NumberUtils.parseNumber(ObjectUtils
						.toString(this.queryForObject(
								"hrm.transView.ifEffectByExpInsidePaAdjustNo", obj)),
						Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		/**
		 * 取消发令 薪资调整(cancel HrPaAdjust)
		 * 
		 * @param list
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int cancelHrPaAdjustByNo(List list) throws Exception {
			try {
				 this.updateForList(
						"hrm.transView.cancelHrPaAdjustByNo", list);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
		/**
		 * 代理决裁列表(view transaction transaction affirm list)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getAgentViewList(Object object) throws Exception {
			return this.getAgentViewList(object, -1, -1);
		}
		/**
		 * 代理决裁列表(view Agent transaction affirm list)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getAgentViewList(Object object, int currentPage,
				int pageSize) {
			List returnList = new ArrayList();
			try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList(
							"hrm.transView.getAgentViewList", object,
							currentPage, pageSize);
				} else {
					returnList = this.queryForList(
							"hrm.transView.getAgentViewList", object);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
		/**
		 * 代理决裁列表数量(select count)
		 * 
		 * @param obj
		 * @param currentPage
		 * @param pageSize
		 * @return
		 */
		@Override
		public int getAgentViewListCnt(Object object) throws Exception {
			try {
				return NumberUtils
						.parseNumber(
								ObjectUtils
										.toString(this
												.queryForObject(
														"hrm.transView.getAgentViewListCnt",
														object)), Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		/**
		 * 获得单个发令里决裁是否生效(get transaction if effect)
		 * 
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		@Override
		public int ifEffectByExpInsideAgentNo(Object obj) throws Exception {
			try {
				return NumberUtils.parseNumber(ObjectUtils
						.toString(this.queryForObject(
								"hrm.transView.ifEffectByExpInsideAgentNo", obj)),
						Integer.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		/**
		 * 取消发令 代理(cancel HrAgent)
		 * 
		 * @param list
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int cancelHrAgentByNo(List list) throws Exception {
			try {
				this.updateForList(
						"hrm.transView.cancelHrAgentByNo", list);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}

}