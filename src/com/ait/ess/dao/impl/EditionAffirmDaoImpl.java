package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.EditionAffirmDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class EditionAffirmDaoImpl extends SqlMapClientSupport implements EditionAffirmDao{
	/**
	 * 查找离职申请数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionAffirmCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.editionAffirm.getEditionAffirmCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 决裁页面
	 */
	public int getDimissionAffirmorCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.editionAffirm.getDimissionAffirmorCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	public List getDimissionAffirmorList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.editionAffirm.getDimissionAffirmorList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.editionAffirm.getDimissionAffirmorList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 分页查找离职申请List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEditionAffirmList(Object object, int currentPage, int pageSize)throws SQLException{
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.editionAffirm.getDimissionAffirmorList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.editionAffirm.getDimissionAffirmorList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查找离职申请List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEditionAffirmList(Object object)throws SQLException{
		List returnList = new ArrayList() ;
		returnList = this.getEditionAffirmList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 根据离职申请NO查询申请的人(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionApplyorByApplyNoList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.editionAffirm.getEditionApplyorByApplyNoList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 根据离职申请NO决裁信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionAffirmorByApplyNoList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.editionAffirm.getEditionAffirmorByApplyNoList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 离职申请check信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionCheckorByApplyNoList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.editionAffirm.getEditionCheckorByApplyNoList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 删除离职审批决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void deleteDimissionAffirmor(Object object)  throws Exception{
		this.delete("ess.editionAffirm.deleteDimissionAffirmor", object) ;
	}
	
	/**
	 * 添加离职决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void insertDimissionAffirmor(Object object)  throws Exception{
		this.insert("ess.editionAffirm.insertDimissionAffirmor", object) ;
	}
	
	/**
	 * 修改决裁状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void updateDimissionEssAffirm(Object object) throws Exception{
		this.update("ess.editionAffirm.updateDimissionEssAffirm", object) ;
	}
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void updateCheckFlagByDimissionEssAffirmNo(Object object)  throws Exception{
		this.update("ess.editionAffirm.updateCheckFlagByDimissionEssAffirmNo", object) ;
	}
	
	/**
	 * 决裁离职申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void affirmDimissionInfo(Object object)  throws Exception{
		this.update("ess.editionAffirm.affirmDimissionInfo", object) ;
	}
	public void affirmDimissionInfoAffirm(Object object)  throws Exception{
		this.update("ess.editionAffirm.affirmDimissionInfoAffirm", object) ;
	}
	public void affirmDimissionItemInfo(Object object)  throws Exception{
		this.update("ess.editionAffirm.affirmDimissionItemInfo", object) ;
	}
	
	/**
	 * 修改check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateDimissionEssCheck(Object object) throws Exception{
		this.update("ess.editionAffirm.updateDimissionEssCheck", object) ;
	}
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public List getDimissionAffirmorList(Object object){
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("ess.editionAffirm.getDimissionAffirmorList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public List getDimissionAffirmor(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.editionAffirm.getDimissionAffirmor", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	return returnList ;
	}
	
	/**
	 * 获取需要check的离职申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	public List getDimissionCheckList(Object object){
		return getDimissionCheckList(object, -1, -1);
	}
	
	/**
	 * 获取需要check的离职申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	public List getDimissionCheckList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.editionAffirm.getDimissionCheckList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.editionAffirm.getDimissionCheckList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取check信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDimissionCheckCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.editionAffirm.getDimissionCheckCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void addCheckAffirmDimission(Object object)  throws Exception{
		this.insert("ess.editionAffirm.addCheckAffirmDimission", object) ;
	}
	
	/**
	 * 查找离职交接项目明细List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getAffirmEditionItemList(Object object)throws SQLException{
		List returnList = new ArrayList() ;
		returnList = this.queryForList("ess.editionAffirm.getAffirmEditionItemList", object);
		return returnList;
	}
	
	/**
	 * 根据person——id查找该员工的部门信息
	 */
	public List getDeptByPersonId(Object object)throws SQLException{
		List returnList = new ArrayList();
		returnList = this.queryForList("ess.editionAffirm.getDeptByPersonId",object);
		return returnList;
	}
	
	/**
	 * 根据离职申请NO查询此次（个人/代）申请的所有人(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDimissionByApplyNoList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.editionAffirm.getDimissionByApplyNoList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 根据离职申请人的personId查找这个人对应的离职交接模版的详细内容
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionItemByPidList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.editionAffirm.getEditionItemByPidList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 添加离职申请者的个人离职模版信息
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void addEditionEditionPidInfo(Object object)  throws Exception{
		this.insert("ess.editionAffirm.addEditionEditionPidInfo", object) ;
	}
}
