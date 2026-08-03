package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.RecHrConfirmDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class RecHrConfirmDaoImpl extends SqlMapClientSupport implements RecHrConfirmDao {	

	/**
	 * 人事确认页面查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getReadyToHrConfirmInfoList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList(
						"hrm.recruit.getReadyToHrConfirmInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 简历人事确认
	 * 
	 * @param obj
	 * @param request
	 * @return void
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recruitmentHrConfirm(HttpServletRequest request,LinkedHashMap obj) {
		// TODO Auto-generated method stub
		try{
			String[] isChecked = request.getParameterValues("hr3706Check");
			for (int i = 0; i < isChecked.length; i++) {
			    obj.put("REC_INTERVIEW_NO", obj.get("REC_INTERVIEW_NO_"+isChecked[i]));
			    obj.put("FINAL_REMARK", obj.get("FINAL_REMARK_"+isChecked[i]));
			    this.update("hrm.recruit.updateFinalConfirmStatus", obj);
			    
			    String affirmFlag = (String)obj.get("affirmFlag");
			    if("1".equals(affirmFlag)){
				    Date dt = new Date();     
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");     
				    String temp_str = sdf.format(dt); 
					obj.put("DATE_STARTED", temp_str);
					obj.put("V_EMP_TYPE", "NORMAL");
				    String empid = this.getEmpId(obj);
					obj.put("EMPID", empid);
					String person_id = getPersonIDSeq();
					obj.put("PERSON_ID", person_id);
				    this.update("hrm.recruit.addEmployeeInfo", obj);
				    this.update("hrm.recruit.updateEmployeeInfo", obj);
				    this.update("hrm.recruit.addEducationInfo", obj);
				    this.update("hrm.recruit.updateEducationInfo", obj);
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getPersonIDSeq(){
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.recruit.getPersonIDSeq"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@SuppressWarnings("unchecked")
	public String getEmpId(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("recruit.manage.getEmpId", paramMap) ;	
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage();			
			throw e;
		}
		return returnString ;
	}
		
}
