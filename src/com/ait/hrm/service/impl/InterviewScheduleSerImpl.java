package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.AddRecPageHubDao;
import com.ait.hrm.dao.InterviewScheduleDao;
import com.ait.hrm.service.AddRecPageHubSer;
import com.ait.hrm.service.InterviewScheduleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class InterviewScheduleSerImpl implements InterviewScheduleSer {
	Logger logger = Logger.getLogger(InterviewScheduleSerImpl.class);
	@Autowired
	private InterviewScheduleDao interviewScheduleDao;
	
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("PERSON_ID")==null || "".equals(paramMap.get("PERSON_ID"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return paramMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getInterviewScheduleList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		returnList = this.interviewScheduleDao.getInterviewScheduleList(paramMap);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addRecAffirmInfo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("createBy", admin.getAdminID());
		
		List affirmList = new ArrayList();
		List affirmId = new ArrayList();
		int num = 0;
		for(int i=1;i<6;i++){
			String affirmer = request.getParameter("AFFIRMOR_ID"+i);
			if(affirmer!=null && !"".equals(affirmer)){
				affirmId.add(affirmer);
			}else{
				num++;
			}
			if(num==5){
				throw new CommonException("Select Interviewer, Please");
			}
		}
		
		//添加决裁者 
		for(int i=0; i<affirmId.size(); i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
			affirmMap.put("AFFIRM_LEVEL", i+1);
			affirmList.add(affirmMap);
		}
		paramMap.put("affirmList", affirmList);
		//获取应聘者
		String[] candidates = request.getParameterValues("SINGLE_LEAVE");
		String[] INTERVIEW_TIME = request.getParameterValues("INTERVIEW_TIME_301");
		String aat = request.getParameter("INTERVIEW_TIME_301");
		String[] INTERVIEW_ADDRESS = request.getParameterValues("INTERVIEW_ADDRESS");
		for(int i=0;i<candidates.length;i++){
			paramMap.put("REC_EMPLOYEE_NO", candidates[i]);
			paramMap.put("INTERVIEW_TIME", request.getParameter("INTERVIEW_TIME_"+candidates[i]));
			paramMap.put("INTERVIEW_ADDRESS", request.getParameter("INTERVIEW_ADDRESS_"+candidates[i]));
			//插入面试审核表
			this.interviewScheduleDao.addRecAffirmInfo(paramMap);
			//修改简历信息表rec_type标志
			this.interviewScheduleDao.updateRecPageFlag(paramMap);
		}
		
        return 1;
	}

}
