package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.InterviewProcessDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class InterviewProcessDaoImpl extends SqlMapClientSupport implements InterviewProcessDao {	
	/**
	 * 面试记录查询 
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getReadyToInterviewInfoList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList(
						"hrm.recruit.getReadyToInterviewInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 面试管面试信息查询 
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInterviewAffirmList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList(
						"hrm.recruit.getInterviewAffirmList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 简历审批
	 * 
	 * @param obj
	 * @param request
	 * @return void
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void interviewProcessApproval(HttpServletRequest request,LinkedHashMap obj) {
		// TODO Auto-generated method stub
		try{
			String[] isChecked = request.getParameterValues("hr3705Check");
			for (int i = 0; i < isChecked.length; i++) {
			    obj.put("REC_INTERVIEW_NO", obj.get("REC_INTERVIEW_NO_"+isChecked[i]));
			    obj.put("REC_AFFIRM_NO", obj.get("REC_AFFIRM_NO_"+isChecked[i]));
			    obj.put("AFFIRM_REMARK", obj.get("AFFIRM_REMARK_"+isChecked[i]));
			    obj.put("SCORE", obj.get("SCORE_"+isChecked[i]));
			    String affirmFlag = (String)obj.get("affirmFlag");
			    //修改审批表进行审批
			    this.update("hrm.recruit.interviewProcessApproval", obj);
			    //判断是否为最后一级决裁者，且已审批
			    if("1".equals(affirmFlag)){
				    int ret = NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this
							.queryForObject("hrm.recruit.isMaxAffirmLevel",obj),0)),
							Integer.class);
				    if(ret==1){
				    	//修改面试安排表
				    	this.update("hrm.recruit.updateFinalApprovalStatus", obj);
				    }
			    }else if("2".equals(affirmFlag)){
			    	this.update("hrm.recruit.updateFinalApprovalStatus", obj);
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
}
