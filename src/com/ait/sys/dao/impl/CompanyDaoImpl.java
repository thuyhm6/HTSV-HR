package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.sys.dao.CompanyDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName CompanyDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:20:50
 * @version 5.0
 */
@Repository
public class CompanyDaoImpl extends SqlMapClientSupport implements CompanyDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	public Object getCompany(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getCompanyList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	public List getCompanyList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getCompanyList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemAllList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.company.getCompanyItemAllList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getCompanyItemAllListHome(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.company.getCompanyItemAllListHome",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getEmpInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getPaInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaInfoLxjLgechList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getPaInfoLxjLgechList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaInfoLxjLgetaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getPaInfoLxjLgetaList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getYearInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getYearInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getpayDetilInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getpayDetilInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getpayDetilInfoLxjLgechList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getpayDetilInfoLxjLgechList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getpayDetilInfoLxjLgetaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getpayDetilInfoLxjLgetaList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getotherPayInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getotherPayInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getotherPayInfoLxjLgechList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getotherPayInfoLxjLgechList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getotherPayInfoLxjLgetaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getotherPayInfoLxjLgetaList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getwelfarePayInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getwelfarePayInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getwelfarePayInfoLxjLgechList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getwelfarePayInfoLxjLgechList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getwelfarePayInfoLxjLgetaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getwelfarePayInfoLxjLgetaList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getadministrationPayInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getadministrationPayInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getadministrationPayInfoLxjLgechList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getadministrationPayInfoLxjLgechList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getadministrationPayInfoLxjLgetaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getadministrationPayInfoLxjLgetaList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getpaManuallyInfoLxjList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getpaManuallyInfoLxjList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getpaManuallyInfoLxjLgechList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getpaManuallyInfoLxjLgechList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getpaManuallyInfoLxjLgetaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.empSummary.getpaManuallyInfoLxjLgetaList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得所有公司列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.company.getCompanyList",
						obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.company.getCompanyList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public int getCompanyListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.company.getCompanyListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
 
	public String getRoleID(Object object){
		String returnInt = "";
		try{
			
			returnInt =  (String) this
					.queryForObject("sys.company.getRoleID", object);
			 
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnInt;
		
	}
	/**
	 * 插入公司信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addCompanyInfo(Object obj) throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		int a=1;
		object.put("CPNY_POSTALCODE", "");//邮编
		this.insert("sys.company.InsertCompany", object);
	}
	
	/**
	 * 修改公司信息
	 * @param List
	 * @return
	 */
	public void updateCompanyInfo(Object obj) throws Exception {
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("sys.company.UpdateCompany", obj) ;
	}
	
	/**
	 * 删除公司信息
	 * @param List
	 * @return
	 */
	public void deleteCompanyInfo(Object obj) throws Exception {
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("sys.company.deleteCompany", obj) ;
	}

	@Override
	public int checkCompanyIdExsit(Object object) {
			int returnInt = 0 ;
			try {
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.company.isCnpyIdExsit", object)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return returnInt ;
		
	}
	
	@Override
	public List getHrOpeationList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.company.getHrOpeationList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	public List getCompanyBouns(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.company.getCompanyBouns",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	public List getCompanyYuti(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.company.getCompanyYuti",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}