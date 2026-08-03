package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.PostDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName PostDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:21:20
 * @version 5.0
 *
 */
@Repository
public class PostDaoImpl extends SqlMapClientSupport implements PostDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	public Object getPost(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getPostList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	@SuppressWarnings("unchecked")
	public Object getDutyInfo(Object obj){
		Object returnObj = new Object();
		List returnList =this.getDutyItemList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPostGroup(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getPostGroupList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPostGrade(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getPostGradeList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	public List getPostList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPostList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGroupList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPostGroupList(obj, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGradeList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPostGradeList(obj, -1, -1);
		return returnList;
	}
	
	public int getPostGradeItemListCnt(Object object){
		int returnInt=0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.post.getPostGradeItemListCnt", object)),
					Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 取得所有职务列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPostList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.post.getPostListPerPage",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.post.getPostListPerPage",
						obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.post.getPostGroup",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.post.getPostGroup",
						obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGradeList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.post.getPostGrade",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.post.getPostGrade",
						obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	
	public List getPositionInfoByPostGradeNo(Object object) {
		List returnList = new ArrayList();
		try {
			
				returnList = this.queryForList("sys.post.getPostGradeListPostGradeNo",object);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	

	public int getPostListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.post.getPostListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 插入职务信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addPostInfo(Object obj) throws  Exception {
		LinkedHashMap paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("sys.post.InsertPost", paramMap);
	}

	/**
	 * 插入职群信息
	 * 
	 * @param Object
	 * @return
	 */
	public int addPostGroupInfo(Object obj) throws SQLException {
		try {
			this.insert("sys.post.InsertPostGroup", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 插入职級信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addPostGradeInfo(Object obj) throws Exception {
			LinkedHashMap paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
			this.insert("sys.post.InsertPostGrade", paramMap);
			Object dutyNos=((Map)obj).get("DUTY_NOS");
			Object postNo=((Map)obj).get("HR_POST_NO");
			if(dutyNos!=null){
				String[] dutyNoArr=(String[])dutyNos;
				for(int i=0;i<dutyNoArr.length;i++){
					LinkedHashMap pMap =new LinkedHashMap();
					pMap.put("DUTY_NO", dutyNoArr[i]);
					pMap.put("POST_GRADE_NO", paramMap.get("NO"));
					pMap.put("CREATED_BY", paramMap.get("CREATED_BY"));
					pMap.put("interCpnyID",  paramMap.get("interCpnyID"));
					this.insert("sys.post.InsertPostGradeDutyRelation", pMap);
				}
			}
			if(postNo!=null){
				LinkedHashMap pMap =new LinkedHashMap();
				pMap.put("POST_NO", postNo);
				pMap.put("POST_GRADE_NO", paramMap.get("NO"));
				pMap.put("CREATED_BY", paramMap.get("CREATED_BY"));
				this.insert("sys.post.InsertPostGradePostRelation", pMap);
			}
	}
	
	/**
	 * 修改职务信息
	 * @param List
	 * @return
	 */
	public void updatePostInfo(Object obj) throws Exception {
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("sys.post.UpdatePost", obj) ;
	}
	
	/**
	 * 修改职群信息
	 * @param List
	 * @return
	 */
	public int updatePostGroupInfo(Object obj) {
		try {
			this.update("sys.post.UpdatePostGroup", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 修改职级信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatePostGradeInfo(Object obj) throws Exception{
		int resultInt=0;
		try {
			LinkedHashMap paramMap=(LinkedHashMap)obj;
			this.syLanguageDao.updateSyGlobalName(obj);
			this.update("sys.post.UpdatePostGrade", obj) ;
			Object dutyNos=((Map)obj).get("DUTY_NOS");
			this.delete("sys.post.deletePostGradeDutyRelation",obj);
			if(dutyNos!=null){
				String[] dutyNoArr=(String[])dutyNos;
				for(int i=0;i<dutyNoArr.length;i++){
					LinkedHashMap pMap =new LinkedHashMap();
					pMap.put("DUTY_NO", dutyNoArr[i]);
					pMap.put("POST_GRADE_NO", paramMap.get("NO"));
					pMap.put("CREATED_BY", paramMap.get("UPDATED_BY"));
					pMap.put("UPDATED_BY", paramMap.get("UPDATED_BY"));
					pMap.put("interCpnyID", paramMap.get("interCpnyID"));
					this.insert("sys.post.InsertPostGradeDutyRelation", pMap);
				}
			}
			resultInt=1;
		} catch (SQLException e) {			
			e.printStackTrace();
			resultInt=0;
		}
		return resultInt;
	}
	
	/**
	 * 删除职务信息
	 * @param List
	 * @return
	 */
	public void deletePostInfo(Object obj) throws Exception {
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("sys.post.deletePost", obj) ;
	}
	
	/**
	 * 删除职群信息
	 * @param List
	 * @return
	 */
	public int deletePostGroupInfo(Object obj) {
		try {
			this.delete("sys.post.deleteGroupPost", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除职级信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deletePostGradeInfo(Object obj) throws Exception {
		try {
			if(((Map)obj).get("NO")!=null){
				((Map)obj).put("POST_GRADE_NO", ((Map)obj).get("NO"));
			}
			this.syLanguageDao.deleteSyGlobalName(obj);
			this.delete("sys.post.deletePostGrade", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGradeLevelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList=this.queryForList("sys.post.getCodeListByParentNo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDutyList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList=this.queryForList("sys.post.getDutyList", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 职责管理列表(HR_DUTY)
	 */
	@SuppressWarnings("unchecked")
	public List getDutyItemList(Object object){
		List returnList = new ArrayList();

		returnList = this.getDutyItemList(object, -1, -1);

		return returnList;
	}
	
	/**
	 * 职责管理列表总数(HR_DUTY)
	 */
	public int getDutyItemListCnt(Object object){
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.post.getDutyItemListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getCheckDutyList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList=this.queryForList("sys.post.getCheckDutyList", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 职责管理列表(HR_DUTY)
	 */
	@SuppressWarnings("unchecked")
	public List getDutyItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.post.getDutyItemList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.post.getDutyItemList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * INSERT INTO HR_DUTY
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void saveDutyInfo(Object object) throws Exception{
		if(((Map)object).get("NO")!=null){
			this.syLanguageDao.updateSyGlobalName(object);
			this.update("sys.post.updateDutyInfo", object) ;
		}else{
			LinkedHashMap paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
			this.insert("sys.post.addDutyInfo", paramMap) ;
		}
	}
	
	/**
	 * DELETE FROM HR_DUTY
	 * @throws Exception 
	 */
	public void deleteDutyInfo(Object object)throws Exception{
		this.syLanguageDao.deleteSyGlobalName(object);
		this.delete("sys.post.deleteDutyInfo", object) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deletePositionInfo(Object obj) throws Exception {
		try {
			if(((Map)obj).get("NO")!=null){
				((Map)obj).put("POST_GRADE_NO", ((Map)obj).get("NO"));
			}
			this.syLanguageDao.deleteSyGlobalName(obj);
			this.delete("sys.post.deletePositionInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getPositionInfo(Object object) {
		Object returnObj = new Object();
		List returnList = this.getPositionList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPositionList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.post.getPositionList",
						object, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.post.getPositionList",
						object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPositionList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getPositionList(object, -1, -1);
		return returnList;
	}
	
	@Override
	public int getPositionListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.post.getPositionListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void savePositionInfo(Object object) throws Exception {
		if(((Map)object).get("NO")!=null){
			this.syLanguageDao.updateSyGlobalName(object);
			this.update("sys.post.updatePositionInfo", object) ;
		}else{
			LinkedHashMap paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
			this.insert("sys.post.addPositionInfo", paramMap) ;
		}
	}
	
	
}
