package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.sys.dao.AffirmSpecialDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName AffirmDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:20:37
 * @version 5.0
 *
 */
@Repository
public class AffirmSpecialDaoImpl extends SqlMapClientSupport implements AffirmSpecialDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmSpecialList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAffirmSpecialList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmSpecialList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.affirmSpecial.getAffirmSpecialList",obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirmSpecial.getAffirmSpecialList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmSpecialList_special(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAffirmSpecialList(object, -1, -1);
		return returnList;
	}

	@Override
	public int getAffirmSpecialListCnt_special(Object obj){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.affirmSpecial.getAffirmSpecialListCnt_special", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmSpecialList_special(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.affirmSpecial.getAffirmSpecialList_special",obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirmSpecial.getAffirmSpecialList_special",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getAffirmSpecialListCnt(Object obj){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.affirmSpecial.getAffirmSpecialListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public void updateAffirmSpecialInfo(Object object) throws Exception{
		this.update("sys.affirmSpecial.UpdateAffirmSpecial", object) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeListByParentCode(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirmSpecial.getCodeListByParentCode", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getCodeListCntByParentCode(Object obj){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.affirmSpecial.getCodeListCntByParentCode", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeListByParentCode(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.affirmSpecial.getCodeListByParentCode",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.affirmSpecial.getCodeListByParentCode",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getItemDetail(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirmSpecial.getItemDetail", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getItemDetail_special(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirmSpecial.getItemDetail_special", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorSpecialList(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirmSpecial.getAffirmorSpecialList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public void deleteAffirmSpecialLevelInfo(Object obj) throws Exception {
		this.delete("sys.affirmSpecial.deleteAffirmSpecialLevelInfo", obj) ;
	}

	@SuppressWarnings("unchecked")
	public List getDeptTreeList(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirmSpecial.getDeptTreeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirmSpecial.getEmpByDeptIdList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirmSpecial.getAffirmorsSpecialByEmpOrDeptAndType", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType_special(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirmSpecial.getAffirmorsSpecialByEmpOrDeptAndType_special", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void insertAffirmSpecialInfo(Object object)throws Exception{
		this.insert("sys.affirmSpecial.insertAffirmSpecialInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirmSpecial.getEmployeeByObjectId", object);//查看affirm_object是否指的是人员
			if(returnList==null){
				returnList =this.queryForList("sys.affirmSpecial.getDeptByObjectIdPersonNotExist", object);//affirm_object指的是部门
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void deleteAffirmSpecialInfo(Object object) throws Exception{
		this.delete("sys.affirmSpecial.deleteAffirmSpecialInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.affirmSpecial.getPidEidList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirmSpecial.getPidEidList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	public List getPersonCntByEmpid(Object obj) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("sys.affirmSpecial.getPersonCntByEmpid", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList2(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.affirmSpecial.getPidEidList2", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirmSpecial.getPidEidList2", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPidEidList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList2(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPidEidList2(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工信息(According to the condition inquires the employee information)
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpIdList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.affirmSpecial.getEmpIdList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirmSpecial.getEmpIdList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工信息数量(According to the condition inquires the employee information count)
	 * @param List
	 * @return
	 */
	public int getEmpIdListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.affirmSpecial.getEmpIdListCnt", obj)), Integer.class) ;
		}catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据条件查询员工信息(According to the condition inquires the employee information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpIdList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirmSpecial.getEmpIdList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/* @author xuehaifei
	 * 裁决委任
	 * 2014-7-11
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateAffirmSpecialAppoint(Object object) {
		boolean flag;
		try {
			this.insert("sys.affirmSpecial.insertaffirmSpecialAppoint",object) ;
			this.update("sys.affirmSpecial.updateaffirmSpecialAppointtb",object);
			this.update("sys.affirmSpecial.updateaffirmSpecialAppointfn",object);
		} catch (SQLException e) {
			flag=false;
			e.printStackTrace();
		}
		flag=true;
        return flag;
	}

	/* @author xuehaifei
	 * 
	 * 2014-7-17
	 * 
	 */
	@Override
	public List affirmSpecialAppointList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirmSpecial.AffirmSpecialAppointList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/* @author xuehaifei
	 * 
	 * 2014-7-18
	 * 
	 */
	@Override
	public boolean cancleAppoint(Object object) {
		boolean flag;
		try {
			this.update("sys.affirmSpecial.cancleAppoint",object) ;
			this.update("sys.affirmSpecial.cancleSyReTb",object) ;
			this.update("sys.affirmSpecial.cancleSyReFb",object) ;
		} catch (SQLException e) {
			flag=false;
			e.printStackTrace();
		}
		flag=true;
        return flag;
	}
	

	public List getAffirmSpecialList_specialExcel(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.affirmSpecial.getAffirmSpecialList_specialExcel",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
