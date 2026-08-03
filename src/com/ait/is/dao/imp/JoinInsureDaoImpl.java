package com.ait.is.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.is.dao.JoinInsureDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class JoinInsureDaoImpl extends SqlMapClientSupport  implements JoinInsureDao {
	@Autowired
	private SyLanguageDao syLanguageDao;
	@Override
	public List getPaBenJoinInsureListBz(Object obj) throws SQLException {
		
		List returnList = new ArrayList() ;
		returnList = this.getPaBenJoinInsureListBz(obj, -1, -1) ;
		return returnList ;
	}
	@Override
	public List getPaBenJoinInsureListBz(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			this.update("is.joinInstance.backPaBenJoinInsureUpdate");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("is.joinInstance.getPaBenJoinInsureList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("is.joinInstance.getPaBenJoinInsureList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public int getJoinInsureCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(
						ObjectUtils.toString(this.queryForObject("is.joinInstance.getPaBenJoinInsureCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	@Override
	public void deleteJoinInsureInfo(Object obj) throws SQLException {
		this.delete("is.joinInstance.deleteJoinInsureInfo", obj) ;
	}
	//点击修改出现可修改文本
	@Override
	public void allowPaBenJoinInsureUpdate(String id) {
		try {
			this.update("is.joinInstance.allowPaBenJoinInsureUpdate", id);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("allowPaBenJoinInsureUpdate information Exception. ", e);
		}
		
	}
	//获取要修改的参保对象信息List
	@Override
	public List getAllowPaBenJoinInsureUpdate() {
		List result;
		try {
			result = this.queryForList("is.joinInstance.getAllowPaBenJoinInsureUpdate");
		} catch (Exception e) {
			throw new GlRuntimeException("getAllowPaBenJoinInsureUpdate Exception. ", e);
		}
		return result;
	}
	/**
	 * 点击修改出现可修改文本(不修改而返回)
	 */
	@Override
	public void backPaBenJoinInsureUpdateBz() {
		try {
			this.update("is.joinInstance.backPaBenJoinInsureUpdate");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("backPaBenJoinInsureUpdate information Exception. ", e);
		}
	}
	@Override
	public void updatePaBenManageAddInfoBz(Object obj) {
		try {
			this.update("is.joinInstance.updatePaBenManageAddInfo", obj);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new GlRuntimeException("update PaBenManageAddInfo information Exception. ", e);
		}
	}
	@Override
	public List getNOInsJoinNumList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaBenJoinInsureListBz(obj, -1, -1) ;
		return returnList ;
	}
	/*--------------------------------------------------------------------------------------------------*/
	@Override
	public List findPIdByParam(Object object) {
		try {
			 return this.queryForList("is.joinInstance.findPIdByParam", object);
		 }catch (Exception e) {
			 e.printStackTrace();
			 return null;
	     }
	}
	@Override
	public List<Map> checkPaBenhsJoin(Object object) {
		 try {
			 return this.queryForList("is.joinInstance.checkPaBenhsJoin", object);
		 }catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}
	@Override
	public void updatePaBenhsJoin(Object object) {
		//发令
		 try {
			this.update("is.joinInstance.updatePaBenhsJoin", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void insertPaBenhsJoin(Object object) {
		try {
			this.insert("is.joinInstance.insertPaBenhsJoin", object);
			 }catch (Exception e) {
				 e.printStackTrace();
			}
	}
	@Override
	public List<Map> getPaJoinImplList() {
		try {
			return this.queryForList("is.joinInstance.getPaBehsJoinImpList");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//清空临时表：PA_BEN_MANAGE_ADD_IMP  
	@Override
	public void deletePaImp() {
				try {
					this.delete("is.joinInstance.deletePaJoinImp");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
