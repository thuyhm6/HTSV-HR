package com.ait.sys.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.PostDao;
import com.ait.sys.service.PostSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class PostSerImpl implements PostSer {

	Logger logger = Logger.getLogger(PostSerImpl.class);
	
	@Autowired
	private PostDao postDao;
	
	@SuppressWarnings("unchecked")
	public Object getPostItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("NO")!=null){
			paramMap.put("POST_NO", paramMap.get("NO"));
		}
		returnObj = postDao.getPost(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPostGroupItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = postDao.getPostGroup(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPostGradeItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = postDao.getPostGrade(paramMap) ;
		return returnObj ;
	}
	
	/**
	 * 得到要修改的DUTY对象
	 */
	@SuppressWarnings("unchecked")
	public Object getDutyInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		returnObj = postDao.getDutyInfo(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(request.getParameter("cpnyInitId")!=null){
			paramMap.put("cpnyInitId", request.getParameter("cpnyInitId"));
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = postDao.getPostList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = postDao.getPostList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 加载职级管理修改页面中的职务（SELECT FROM HR_POST）
	 * Description:
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPostItemSelectList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return postDao.getPostList(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGroupItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = this.setGetPostItemParam(request) ;
		if (paramMap.get("page") != null && paramMap.get("pagesize") != null){
			retrunList = 
				postDao.getPostGroupList(paramMap , 
							NumberUtils.parseNumber(ObjectUtils.toString(paramMap.get("page")), Integer.class), 
							NumberUtils.parseNumber(ObjectUtils.toString(paramMap.get("pagesize")), Integer.class) 
						) ;
		}else{
			retrunList = postDao.getPostGroupList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGradeItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(request.getParameter("cpnyInitId")!=null){//用于职位添加页
			paramMap.put("cpnyInitId", request.getParameter("cpnyInitId"));
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = postDao.getPostGradeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = postDao.getPostGradeList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPostItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		LinkedHashMap paramMap = this.setGetPostItemParam(request) ;
		retrunInt = postDao.getPostListCnt(paramMap) ;
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetPostItemParam(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return paramMap ;
	}
	
	@SuppressWarnings("unchecked")
	public int addPostItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.postDao.addPostInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addPostGroupItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.postDao.addPostGroupInfo(paramMap) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public int addPostGradeItemInfo(HttpServletRequest request) {
		int returnInt=0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("DUTY_NOS", request.getParameterValues("DUTY_NOS"));
		try {
			this.postDao.addPostGradeInfo(paramMap) ;
			returnInt=1;
		} catch (Exception e) {
			e.printStackTrace();
			returnInt=0;
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public int updatePostItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			paramMap.put("CREATED_BY", admin.getAdminID()) ;
			this.postDao.updatePostInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updatePostGroupItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		this.postDao.updatePostGroupInfo(paramMap) ;
		return 0;
	}
	
	/**
	 * 修改职级
	 */
	@SuppressWarnings("unchecked")
	public int updatePostGradeItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("DUTY_NOS", request.getParameterValues("DUTY_NOS"));
		try {
			this.postDao.updatePostGradeInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deletePostItemInfo(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("NO")!=null){
			paramMap.put("POST_NO", paramMap.get("NO"));
		}
		try {
			this.postDao.deletePostInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deletePostGroupItemInfo(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.postDao.deletePostGroupInfo(paramMap) ;
	}
	
	/**
	 * 删除职级信息（DELETE FROM HR_POST_GRADE）
	 */
	@SuppressWarnings("unchecked")
	public int deletePostGradeItemInfo(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			 this.postDao.deletePostGradeInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取职等（sy_code）
	 */
	@SuppressWarnings("unchecked")
	public List getPostGradeLevelList(HttpServletRequest request,int parentNo){
		List returnList = new ArrayList() ;
		LinkedHashMap paramMap = this.setGetPostItemParam(request) ;
		paramMap.put("PARENT_CODE_NO", parentNo);
		returnList = this.postDao.getPostGradeLevelList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 职级修改或添加页面查询DUTY
	 */
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request){
		List returnList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.postDao.getDutyList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 职级查看页面查询选中的DUTY
	 */
	@SuppressWarnings("unchecked")
	public List getCheckDutyList(HttpServletRequest request,Object obj){
		List returnList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("POST_GRADE_NO", obj);
		returnList = this.postDao.getCheckDutyList(paramMap) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDutyItemList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = postDao.getDutyItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = postDao.getDutyItemList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getDutyItemListCnt(HttpServletRequest request){
		int result=0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		result = this.postDao.getDutyItemListCnt(paramMap) ;
		return result ;
	}
	
	@SuppressWarnings("unchecked")
	public int saveDutyInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.postDao.saveDutyInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteDutyInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.postDao.deleteDutyInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int getPostGradeItemListCnt(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return this.postDao.getPostGradeItemListCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deletePositionInfo(HttpServletRequest request) {
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("NO")!=null){
			paramMap.put("POSITION_NO", paramMap.get("NO"));
		}
		try {
			this.postDao.deletePositionInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPositionInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("NO")!=null){
			paramMap.put("POSITION_NO", paramMap.get("NO"));
		}
		returnObj = postDao.getPositionInfo(paramMap) ;
		return returnObj ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPositionList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = postDao.getPositionList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = postDao.getPositionList(paramMap) ;
		}
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getPositionListCnt(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return this.postDao.getPositionListCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int savePositionInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.postDao.savePositionInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
