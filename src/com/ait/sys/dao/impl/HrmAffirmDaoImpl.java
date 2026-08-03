package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.sys.dao.HrmAffirmDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName HrmAffirmDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:13:18
 * @version 5.0
 * 
 */
@Repository
public class HrmAffirmDaoImpl extends SqlMapClientSupport implements HrmAffirmDao{
	
	@SuppressWarnings("unchecked")
	public List getHrmAffirmList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getHrmAffirmList(object, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getHrmAffirmList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("sys.hrmAffirm.getHrmAffirmList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("sys.hrmAffirm.getHrmAffirmList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public int getHrmAffirmListCnt(Object object){
		int result=0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.hrmAffirm.getHrmAffirmListCnt", object)),
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
	public void saveHrmAffirmInfo(Object object,String[] postNoArr) throws Exception{
		if(((Map)object).get("TRANS_PARAM_NO")==null||(((Map)object).get("TRANS_PARAM_NO")!=null
				&&((Map)object).get("TRANS_PARAM_NO").toString().equals(""))){
			String essLeaveApplyParamNo=this.queryForObject("sys.hrmAffirm.getEssLeaveApplyParamNo").toString();
			((Map)object).put("TRANS_PARAM_NO", essLeaveApplyParamNo);
			this.insert("sys.hrmAffirm.saveHrmAffirmInfo", object);
		}else{
			this.update("sys.hrmAffirm.updateHrmAffirmInfo", object);
		}
		this.delete("sys.hrmAffirm.deleteHrmApplyAffirmParamInfo", object);
		if(postNoArr!=null&&postNoArr.length>0){
			for(int i=0;i<postNoArr.length;i++){
//				((Map)object).remove("AFFIRMOR_NO");
//				((Map)object).put("AFFIRMOR_NO", postNoArr[i]);
				((Map)object).remove("DUTY_NO");
				((Map)object).put("DUTY_NO", postNoArr[i]);
				((Map)object).remove("AFFIRM_LEVEL");
				((Map)object).put("AFFIRM_LEVEL", i+1);
				this.insert("sys.hrmAffirm.saveHrmAffirmParamInfo", object);
			}
		}
	}
	
	/**
	 * 用于获取申请类型
	 */
	@SuppressWarnings("unchecked")
	public List getApplyList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.hrmAffirm.getApplyListNew",object);
		}catch (SQLException e) {
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
			returnList = this.queryForList("sys.hrmAffirm.getDutyList",object);
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
			returnList = this.queryForList("sys.hrmAffirm.getPostList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDetailParamList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.hrmAffirm.getDetailParamList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void deleteHrmAffirmInfo(Object object) throws Exception{
		this.delete("sys.hrmAffirm.deleteHrmApplyInfo", object);
		this.delete("sys.hrmAffirm.deleteHrmApplyAffirmParamInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public Object getLeaveApplyParam(Object object){
		Object returnObj = new Object();
		List returnList = this.getHrmAffirmList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	public int validateExistsDutyApplyTypeCpnyId(Object object){
		int result=0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.hrmAffirm.validateExistsDutyApplyTypeCpnyId", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
