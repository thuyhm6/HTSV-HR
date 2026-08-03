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
import com.ait.sys.dao.ArAffirmPostDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ArAffirmPostDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-13 上午10:19:03
 * @version 5.0
 */
@Repository
public class ArAffirmPostDaoImpl extends SqlMapClientSupport implements ArAffirmPostDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	@Override
	public List getArAffirmDutyList(Object object, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.arAffirmPost.getArAffirmDutyList",
						object, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.arAffirmPost.getArAffirmDutyList",
						object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getArAffirmPostList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirmPost.getDutyInfoByCpnyId",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getArAffirmDutyList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArAffirmDutyList(object, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getApplyDutyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirmPost.getApplyDutyList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public int getArAffirmDutyListCnt(Object object) {
		int result=0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.arAffirmPost.getArAffirmDutyListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void saveArAffirmPost(Object object) throws Exception{
		this.insert("sys.arAffirmPost.saveArAffirmPost", object);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteArAffirmPostInfo(Object object) throws Exception{
			this.delete("sys.arAffirmPost.deleteArAffirmPost", object);
	}
	@SuppressWarnings("unchecked")
	public Object getAffirmPostById(Object object){
		Object returnObj = new Object();
		List returnList = this.getArAffirmPostList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	public void updateArAffirmPost(Object object) throws Exception{
		this.update("sys.arAffirmPost.updateArAffirmPost", object);
	}
	
	@SuppressWarnings("unchecked")
	public List getSortByParentNo(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirmPost.getSortByParentNo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDutyListByCpnyId (Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirmPost.getDutyListByCpnyId",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void saveArAffirmPostInside (Object object)  throws Exception{
		this.insert("sys.arAffirmPost.saveArAffirmPostInside", object);
	}
	@SuppressWarnings("unchecked")
	public int validateArAffirmPostExist(Object object){
		int result=0;
		try {
			if(((Map)object).get("POST_SORT").toString().equals("inside")){
				result = NumberUtils.parseNumber(ObjectUtils.toString(this
						.queryForObject("sys.arAffirmPost.validateArAffirmPostExistInside", object)),
						Integer.class);
			}else if(((Map)object).get("POST_SORT").toString().equals("outside")){
//				result = NumberUtils.parseNumber(ObjectUtils.toString(this
//						.queryForObject("sys.arAffirmPost.validateArAffirmPostExistOutside", object)),
//						Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
