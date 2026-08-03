package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.sys.dao.ArAffirmDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ArAffirmDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:13:18
 * @version 5.0
 * 
 */
@Repository
public class ArAffirmDaoImpl extends SqlMapClientSupport implements ArAffirmDao{
	
	@SuppressWarnings("unchecked")
	public List getArAffirmList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArAffirmList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArAffirmList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.arAffirm.getArAffirmList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.arAffirm.getArAffirmList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getArAffirmListCnt(Object object){
		int result=0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.arAffirm.getArAffirmListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 用于保存考勤决裁流程
	 */
	@SuppressWarnings("unchecked")
	public void saveArAffirmInfo(Object object,String[] postNoArr) throws Exception{
		if(((Map)object).get("REFERENCN_TO_RELATION")!=null&&((Map)object).get("REFERENCN_TO_RELATION").equals("'<'")){
			((Map)object).put("REFERENCN_TO_RELATION", "<");
		}
		if(((Map)object).get("APPLY_PARAM_NO")==null||(((Map)object).get("APPLY_PARAM_NO")!=null
				&&((Map)object).get("APPLY_PARAM_NO").toString().equals(""))){
			String essLeaveApplyParamNo=this.queryForObject("sys.arAffirm.getEssLeaveApplyParamNo").toString();
			((Map)object).put("APPLY_PARAM_NO", essLeaveApplyParamNo);
			this.insert("sys.arAffirm.saveArAffirmInfo", object);
		}else{
			this.update("sys.arAffirm.updateArAffirmInfo", object);
		}
		this.delete("sys.arAffirm.deleteArApplyAffirmParamInfo", object);
		if(postNoArr!=null&&postNoArr.length>0){
			for(int i=0;i<postNoArr.length;i++){
//				((Map)object).remove("AFFIRMOR_NO");
//				((Map)object).put("AFFIRMOR_NO", postNoArr[i]);
				((Map)object).remove("DUTY_NO");
				((Map)object).put("DUTY_NO", postNoArr[i]);
				((Map)object).remove("AFFIRM_LEVEL");
				((Map)object).put("AFFIRM_LEVEL", i+1);
				this.insert("sys.arAffirm.saveArAffirmParamInfo", object);
			}
		}
	}
	
	/**
	 * 用于保存考勤决裁流程
	 */
	@SuppressWarnings("unchecked")
	public void saveArAffirmInfo(Object object) throws Exception{
		this.insert("sys.arAffirm.saveArAffirmInfo", object);	
	}
	
	/**
	 * 用于获取申请类型
	 */
	@SuppressWarnings("unchecked")
	public List getApplyList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirm.getApplyList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 用于获取职务类型
	 */
	@SuppressWarnings("unchecked")
	public List getPostList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirm.getPostList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 用于获职责类型
	 */
	@SuppressWarnings("unchecked")
	public List getDutyList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirm.getDutyList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDetailParamList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirm.getDetailParamList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void deleteArAffirmInfo(Object object) throws Exception{
		this.delete("sys.arAffirm.deleteArApplyInfo", object);
	}
	
	/**
	 * 用于更新考勤决裁流程
	 */
	@SuppressWarnings("unchecked")
	public void updateArAffirmInfo(Object object) throws Exception{
		this.update("sys.arAffirm.updateArAffirmInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public Object getLeaveApplyParam(Object object){
		Object returnObj = new Object();
		List returnList = this.getArAffirmList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	/**
	 * 获取申请类型 如：
	 * 加班类型、休假类型、出差类型、哺乳假类型、年假调整类型、考勤异常、漏刷卡类型、离职类型、合同续签、临促工资申请
	 */
	@SuppressWarnings("unchecked")
	public List getApplyTypeNoList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirm.getApplyTypeNoList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getApplyTypeCodeList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.arAffirm.getApplyTypeCodeList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public List getArAffirmFinalList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArAffirmFinalList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArAffirmFinalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.arAffirm.getArAffirmFinalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.arAffirm.getArAffirmFinalList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getArAffirmFinalListCnt(Object object){
		int result=0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.arAffirm.getArAffirmFinalListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用于保存考勤决裁流程
	 */
	@SuppressWarnings("unchecked")
	public void saveArAffirmFinalInfo(Object object) throws Exception{
		this.insert("sys.arAffirm.saveArAffirmFinalInfo", object);	
	}
	
	public Object getLeaveApplyFinalParam(Object object){
		Object returnObj = new Object();
		List returnList = this.getArAffirmFinalList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	/**
	 * 用于更新考勤决裁流程
	 */
	@SuppressWarnings("unchecked")
	public void updateArAffirmFinalInfo(Object object) throws Exception{
		this.update("sys.arAffirm.updateArAffirmFinalInfo", object);
	}
	
	public void deleteArAffirmFinalInfo(Object object) throws Exception{
		this.delete("sys.arAffirm.deleteArApplyInfoFinal", object);
	}
}
