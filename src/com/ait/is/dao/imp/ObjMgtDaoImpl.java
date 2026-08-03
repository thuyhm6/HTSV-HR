package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.is.dao.ObjMgtDao;
import com.ait.is.dao.StopInsureDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class ObjMgtDaoImpl extends SqlMapClientSupport implements ObjMgtDao {
	@Autowired
	private SyLanguageDao syLanguageDao;

	@Override
	public int getObjManagementCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(
						ObjectUtils.toString(this.queryForObject("is.objManagement.getObjManagementCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	@Override
	public List getObjManagementList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			this.update("is.objManagement.backPaBenObjInsureUpdate");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("is.objManagement.getObjManagementList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("is.objManagement.getObjManagementList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getObjManagementList(Object obj) {

		List returnList = new ArrayList() ;
		returnList = this.getObjManagementList(obj, -1, -1) ;
		return returnList ;
	}

	@Override
	public List getAllowPaBenObjInsureUpdate(Object obj) {
		List result;
		try {
			result = this.queryForList("is.objManagement.getAllowPaBenObjInsureUpdate",obj);
		} catch (Exception e) {
			throw new GlRuntimeException("getAllowPaBenStopInsureUpdate Exception. ", e);
		}
		return result;
	}

	@Override
	public void allowPaBenObjInsureUpdate(String id) {
		try {
			this.update("is.objManagement.allowPaBenObjInsureUpdate", id);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("allowPaBenStopInsureUpdate information Exception. ", e);
		}
	}

	@Override
	public void updatePaBenManageAddInfoBz(Object obj) {
		try {
			this.update("is.objManagement.updatePaBenManage", obj);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("update PaBenManageAddInfo information Exception. ", e);
		}
	}

	@Override
	public List getNOInsStopNumList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getObjManagementList(obj, -1, -1) ;
		return returnList ;
	}
	
}
