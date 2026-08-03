package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.PortalDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ControlDaoImpl.java
 * @author lufeng(lufeng@ait.net.cn)
 * @Date 2012-3-7 下午05:22:19
 * @version 5.0
 *
 */
@SuppressWarnings("unused")
@Repository
public class PortalDaoImpl extends SqlMapClientSupport implements PortalDao {
	
	/**
	 * 取得所有接口同步信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPortalInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPortalInfoList(obj, -1, -1);
		return returnList;
	}
	
	/**
	 * 取得所有Portal信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPortalInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.portal.getPortalList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.portal.getPortalList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getPortalInfoListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.portal.getPortalListCnt", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getSapItemList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getPaDataToSapList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void addPortalLog(Object obj) throws Exception {
		this.insert("sys.portal.insertPortalLog", obj);
	}
	
	public void deleteSAPItem(Object obj) throws Exception {
		this.delete("sys.portal.deleteSAPItem", obj);
	}
	
	@SuppressWarnings("unchecked")
	public void addSAPItem(List aliasList) throws Exception {
		this.insertForList("sys.portal.addSAPItem", aliasList);
	}
	
	@SuppressWarnings("unchecked")
	public List getPaDataToSapList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getPaDataToSapList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaSummaryInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getPaSummaryInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDepartMentInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getDepartMentInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaSummaryInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getC01PaSummaryInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void addC01PaSummary(Object obj) throws Exception {
		this.insert("sys.portal.insertC01PaSummary", obj);
	}
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.portal.getC01SapSummaryList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.portal.getC01SapSummaryList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getC01SapSummaryList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getC01SapSummaryListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.portal.getC01SapSummaryListCnt", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public String getC01SapSummarySendCount(Object obj) {
		String returnInt = "0";
		try {
			returnInt = ObjectUtils.toString(this.queryForObject("sys.portal.getC01SapSummarySendCount", obj));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getC01PaActualSalaryAllList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.portal.getC01PaActualSalaryAllList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.portal.getC01PaActualSalaryAllList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getC01PaActualSalaryAllListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.portal.getC01PaActualSalaryAllListCnt", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public String getC01SapActualSalarySendCount(Object obj) {
		String returnInt = "0";
		try {
			returnInt = ObjectUtils.toString(this.queryForObject("sys.portal.getC01SapActualSalarySendCount", obj));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public int getC01PaActualSalaryCntByEmpid(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.portal.getC01PaActualSalaryCntByEmpid", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getC01PaActualSalaryAfterList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.portal.getC01PaActualSalaryAfterList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.portal.getC01PaActualSalaryAfterList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getC01PaActualSalaryAfterListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(
					this.queryForObject("sys.portal.getC01PaActualSalaryAfterListCnt", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List checkEmpSapInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.checkEmpSapInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void addZhaBeiAndJiaXingCash(List empPaInfoList) throws Exception {
		this.deleteForList("sys.portal.deleteZhaBeiAndJiaXingCash", empPaInfoList);
		this.insertForList("sys.portal.addZhaBeiAndJiaXingCash", empPaInfoList);
	}
	
	@SuppressWarnings("unchecked")
	public List getZhaBeiAndJiaXingEmpPaInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getZhaBeiAndJiaXingEmpPaInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getC01PaActualSalaryInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void addC01PaActual(Object obj) throws Exception {
		this.insert("sys.portal.insertC01PaActual", obj);
	}
	
	@SuppressWarnings("unchecked")
	public List getC01EmpInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getC01EmpInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getC01SapEmpByPersonid(Object obj){
		Object result = new Object();
		try {
			result = this.queryForObject("sys.portal.getC01SapEmpByPersonid",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void addC01EmpInfo(Object obj) throws Exception {
		this.delete("sys.portal.deleteC01EmpInfo", obj);
		this.insert("sys.portal.insertC01EmpInfo", obj);
	}
	
	@SuppressWarnings("unchecked")
	public List getC01EmpPostInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getC01EmpPostInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getC01SapEmpPostByEmpid(Object obj){
		Object result = new Object();
		try {
			result = this.queryForObject("sys.portal.getC01SapEmpPostByEmpid",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void addC01EmpPost(Object obj) throws Exception {
		this.delete("sys.portal.deleteC01EmpPost", obj);
		this.insert("sys.portal.insertC01EmpPost", obj);
	}
	
	@SuppressWarnings("unchecked")
	public List getC01DepartMentInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.portal.getC01DepartMentInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getC01SapDeptByDeptno(Object obj){
		Object result = new Object();
		try {
			result = this.queryForObject("sys.portal.getC01SapDeptByDeptno",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void addC01DepartMent(Object obj) throws Exception {
		this.delete("sys.portal.deleteC01DepartMent", obj);
		this.insert("sys.portal.insertC01DepartMent", obj);
	}
	
	
}
