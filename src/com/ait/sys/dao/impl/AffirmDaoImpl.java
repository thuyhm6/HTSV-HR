package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.sys.dao.AffirmDao;
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
public class AffirmDaoImpl extends SqlMapClientSupport implements AffirmDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAffirmList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.affirm.getAffirmList",obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirm.getAffirmList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmList_final(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAffirmList_final(object, -1, -1);
		return returnList;
	}

	@Override
	public int getAffirmListCnt_final(Object obj){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.affirm.getAffirmListCnt_final", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public List getAffirmList_final(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.affirm.getAffirmList_final",obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirm.getAffirmList_final",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getAffirmListCnt(Object obj){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.affirm.getAffirmListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public void updateAffirmInfo(Object object) throws Exception{
		this.update("sys.affirm.UpdateAffirm", object) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeListByParentCode(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirm.getCodeListByParentCode", obj);
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
					.queryForObject("sys.affirm.getCodeListCntByParentCode", obj)),
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
				returnList = this.queryForList("sys.affirm.getCodeListByParentCode",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.affirm.getCodeListByParentCode",
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
			returnList = this.queryForList("sys.affirm.getItemDetail", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getItemDetail_final(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirm.getItemDetail_final", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorList(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirm.getAffirmorList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public void deleteAffirmLevelInfo(Object obj) throws Exception {
		this.delete("sys.affirm.deleteAffirmLevelInfo", obj) ;
	}

	@SuppressWarnings("unchecked")
	public List getDeptTreeList(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirm.getDeptTreeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirm.getEmpByDeptIdList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirm.getAffirmorsByEmpOrDeptAndType", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType_final(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirm.getAffirmorsByEmpOrDeptAndType_final", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void insertAffirmInfo(Object object)throws Exception{
		this.insert("sys.affirm.insertAffirmInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList =this.queryForList("sys.affirm.getEmployeeByObjectId", object);//查看affirm_object是否指的是人员
			if(returnList==null){
				returnList =this.queryForList("sys.affirm.getDeptByObjectIdPersonNotExist", object);//affirm_object指的是部门
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void deleteAffirmInfo(Object object) throws Exception{
		this.delete("sys.affirm.deleteAffirmInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.affirm.getPidEidList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirm.getPidEidList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	public List getPersonCntByEmpid(Object obj) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("sys.affirm.getPersonCntByEmpid", obj);
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
				returnList = this.queryForList("sys.affirm.getPidEidList2", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirm.getPidEidList2", obj);
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
				returnList = this.queryForList("sys.affirm.getEmpIdList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirm.getEmpIdList", obj);
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
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.affirm.getEmpIdListCnt", obj)), Integer.class) ;
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
			returnList = this.queryForList("sys.affirm.getEmpIdList", obj);
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
	public boolean updateaffirmAppoint(Object object) {
		boolean flag;
		try {
			this.insert("sys.affirm.insertaffirmAppoint",object) ;
			this.update("sys.affirm.updateaffirmAppointtb",object);
			this.update("sys.affirm.updateaffirmAppointfn",object);
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
	public List affirmAppointList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.affirm.affirmAppointList", obj);
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
			this.update("sys.affirm.cancleAppoint",object) ;
			this.update("sys.affirm.cancleSyReTb",object) ;
			this.update("sys.affirm.cancleSyReFb",object) ;
		} catch (SQLException e) {
			flag=false;
			e.printStackTrace();
		}
		flag=true;
        return flag;
	}
	
	/**
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)包含离职
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidListFull(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPidEidListFull(obj, -1, -1) ;
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getPidEidListFull(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.affirm.getPidEidListFull", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.affirm.getPidEidListFull", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取最终确认批量导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmTempList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAffirmTempList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取最终确认批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.affirm.getAffirmTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.affirm.getAffirmTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取最终确认批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getAffirmTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.affirm.getAffirmTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的最终确认批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getAffirmTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.affirm.getAffirmTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	

	public List getAffirmList_finalExcel(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.affirm.getAffirmList_finalExcel",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
