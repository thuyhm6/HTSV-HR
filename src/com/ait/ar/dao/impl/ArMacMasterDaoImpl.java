package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArMacMasterDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArMacMasterDaoImpl.java
 * @Description:
 * @Create date: 2013-8-26 下午15:49:39
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArMacMasterDaoImpl extends SqlMapClientSupport implements ArMacMasterDao {
	
	/*刷卡机--人事信息--接口*/
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面--导出信息用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArHrmMasterExcelList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.macRecord.getArHrmMasterExcelList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArHrmMasterList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArHrmMasterList(object, -1, -1);
		
		return returnList;
	}

	/**
	 * 考勤机传送人事Master信息，传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArHrmMasterList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.macRecord.getArHrmMasterList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.macRecord.getArHrmMasterList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面，查询数量
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArHrmMasterListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.macRecord.getArHrmMasterListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面--导出信息用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArHrmMasterPreExcelList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.macRecord.getArHrmMasterPreExcelList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机传送人事Master信息，传送过程
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void insertHrmMasterInfo(List list) throws SQLException {
		this.deleteForList("ar.macRecord.DeleteHrmMaster", list);
		this.insertForList("ar.macRecord.InsertHrmMaster", list);
	}
	
	/**
	 * 考勤机传送部门department信息，传送信息查询页面--导出信息用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArDeptMasterPreExcelList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.macRecord.getArDeptMasterPreExcelList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机传送部门department信息，传送过程
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void insertDeptMasterInfo(List list) throws SQLException {
		this.deleteForList("ar.macRecord.DeleteDeptMaster", list);
		this.insertForList("ar.macRecord.InsertDeptMaster", list);
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机接口部分--插入接口日志
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void addArMacLogInfo(Object obj) throws SQLException {
		this.insert("ar.macRecord.addArMacLogInfo", obj);
	}
	
	/*刷卡机--刷卡数据信息--接口*/
	/**
	 * 考勤机--读取刷卡数据（get the ar card data from mac）信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardMacNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.macRecord.getArCardMacNoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 勤机--读取刷卡数据（get the ar card data from mac），传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArCardRecordList(object, -1, -1);
		
		return returnList;
	}

	/**
	 * 勤机--读取刷卡数据（get the ar card data from mac），传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.macRecord.getArCardRecordList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.macRecord.getArCardRecordList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 勤机--读取刷卡数据（get the ar card data from mac），Excel导出
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordExcelList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.macRecord.getArCardRecordExcelList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 勤机--读取刷卡数据（get the ar card data from mac）信息查询页面，查询数量
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.macRecord.getArCardRecordListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * @throws SQLException 
	 * 勤机--读取刷卡数据（get the ar card data from mac），传送过程
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void insertCardRecordInfo(List list) throws SQLException {
		this.deleteForList("ar.macRecord.DeleteCardRecord", list);
		this.insertForList("ar.macRecord.InsertCardRecord", list);
		this.updateForList("ar.macRecord.UpdateSendFlag", list);
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac），传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArFingerPrintList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArFingerPrintList(object, -1, -1);
		
		return returnList;
	}

	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac），传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArFingerPrintList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.macRecord.getArFingerPrintList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.macRecord.getArFingerPrintList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac），导出用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArFingerPrintExcelList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.macRecord.getArFingerPrintExcelList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac）信息查询页面，查询数量
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArFingerPrintListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.macRecord.getArFingerPrintListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
}
