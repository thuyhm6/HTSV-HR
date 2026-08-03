package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.is.dao.CompanyMaintainDAO;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class CompanyMaintainDAOImpl extends SqlMapClientSupport implements CompanyMaintainDAO{

	@Override
	public int addIsCompanyInfo(Object parameterObject) throws Exception {
		int returnInt = 1;
		try {
			this.insert("is.companymaintain.addIsCompanyInfo", parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}

	@Override
	public int deleteIsCompanyInfo(List list) throws Exception {
		int returnInt = 1;
		try {
			this.deleteForList("is.companymaintain.deleteIsCompanyInfo", list);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}

	@Override
	public int updateIsCompanyInfo(Object parameterObject) throws Exception {
		int returnInt = 1;
		try {
			this.update("is.companymaintain.updateIsCompanyInfo", parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}

	@Override
	public List getIsCorpInfo(Object parameterObject) throws SQLException {
		List returnList = new ArrayList() ;
		returnList = this.getIsCorpInfo(parameterObject, -1, -1) ;
		return returnList;
	}
	
	@Override
	public List getIsCorpInfoNotCH(Object parameterObject) throws SQLException {
		List returnList = new ArrayList() ;
		returnList = this.getIsCorpInfoNotCH(parameterObject, -1, -1) ;
		return returnList;
	}


	@Override
	public List getIsCorpInfo(Object obj, int currentPage, int pageSize)
			throws SQLException {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("is.companymaintain.getIsCorpInfo",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("is.companymaintain.getIsCorpInfo",
						obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getIsCorpInfoNotCH(Object obj, int currentPage, int pageSize)
			throws SQLException {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("is.companymaintain.getIsCorpInfoNotCH",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("is.companymaintain.getIsCorpInfoNotCH",
						obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getIsCorpCnt(Object parameterObject) throws SQLException {
		int returnInt=0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("is.companymaintain.getIsCorpCnt", parameterObject)),
					Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public int getIsCorpCntNotCH(Object parameterObject) throws SQLException {
		int returnInt=0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("is.companymaintain.getIsCorpCntNotCH", parameterObject)),
					Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	
}
