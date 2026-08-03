package com.ait.is.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.is.dao.ObjMgtDao;
import com.ait.is.service.ObjMgtSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Service
public class ObjMgtSerImpl implements ObjMgtSer{
	@Autowired
	private ObjMgtDao objMgtDao;

	@Override
	public int getObjManagementCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int i = objMgtDao.getObjManagementCnt(paramMap) ;
		return i ;
	}

	@Override
	public List getObjManagementList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		String adminID = admin.getAdminID();//操作人
		paramMap.put("joinp_CONTRACT_DATE", request.getParameter("joinp_CONTRACT_DATE"));//入社前发令
		paramMap.put("joinl_CONTRACT_DATE", request.getParameter("joinl_CONTRACT_DATE"));//当前在职
		paramMap.put("adminID", adminID);//操作人
		paramMap.put("companyID", admin.getCpnyId());
		paramMap.put("deptNo", admin.getDeptNo());
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		}
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
					objMgtDao.getObjManagementList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = objMgtDao.getObjManagementList(paramMap) ;
		}
		return returnList ;
	}

	@Override
	public void allowPaBenObjInsureUpdateBz(String id) {
		objMgtDao.allowPaBenObjInsureUpdate(id);
	}

	@Override
	public List getAllowPaBenObjInsureUpdateBz(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		String adminID = admin.getAdminID();//操作人
		paramMap.put("Stopp_CONTRACT_DATE", request.getParameter("Stopp_CONTRACT_DATE"));//入社前发令
		paramMap.put("Stopl_CONTRACT_DATE", request.getParameter("Stopl_CONTRACT_DATE"));//当前在职
		paramMap.put("adminID", adminID);//操作人
		paramMap.put("companyID", admin.getCpnyId());
		paramMap.put("deptNo", admin.getDeptNo());
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		}
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		return objMgtDao.getAllowPaBenObjInsureUpdate(paramMap);
	}

	@Override
	public int updatePaBenManageAddInfoBz(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		int j = 0;
		String adminID = admin.getAdminID();//操作人
		String[] seqs = request.getParameterValues("seq");
		String[] empIDs = request.getParameterValues("empID_u");//员工编号
		System.out.println(empIDs+"empid=================");
		
		String[] socialNos = request.getParameterValues("socialNo");
		String[] joinValues = request.getParameterValues("joinValue");
		String[] socialStatuss = request.getParameterValues("socialStatus1");
		for (int i = 0; i < empIDs.length; i++) {
			double joinValue = 0;
			String seq = seqs[i];
			String empID = empIDs[i];
			String socialNo = socialNos[i];
			String socialStatus = socialStatuss[i];
			if (joinValues[i] != null && !"".equals(joinValues[i])) {
				joinValue = Double.parseDouble(joinValues[i]);
			}
			paramMap.put("seq", seq);
			paramMap.put("empID", empID);
			paramMap.put("socialNo", socialNo);
			paramMap.put("socialStatus", socialStatus);
			paramMap.put("joinValue", joinValue);
			paramMap.put("adminID", adminID);//修改人
			objMgtDao.updatePaBenManageAddInfoBz(paramMap);
			j=1;
	}
		return j;
		
	}

	@Override
	public List getInsObjNumInfoExcel(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		String adminID = admin.getAdminID();//操作人
		paramMap.put("startDate", request.getParameter("startDate"));//增加人员时间条件
		paramMap.put("leftDate", request.getParameter("leftDate"));//增加人员时间条件
		paramMap.put("adminID", adminID);//操作人
		paramMap.put("companyID", admin.getCpnyId());
		paramMap.put("deptNo", admin.getDeptNo());
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		}
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("language", Messages.getLanguage(request));
		retrunList = objMgtDao.getNOInsStopNumList(paramMap) ;
		return retrunList ;
	}
	
}
