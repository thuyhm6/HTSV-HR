package com.ait.is.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.is.dao.JoinInsureDao;
import com.ait.is.service.JoinInsureSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Service
public class JoinInsureSerImpl implements JoinInsureSer{
	@Autowired
	private JoinInsureDao joinInsureDao;
	@Override
	public List getJoinInsureList(HttpServletRequest request) throws SQLException {
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
					joinInsureDao.getPaBenJoinInsureListBz(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = joinInsureDao.getPaBenJoinInsureListBz(paramMap) ;
		}
		return returnList ;
		
	}

	@Override
	public int getJoinInsureCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int i = joinInsureDao.getJoinInsureCnt(paramMap) ;
		return i ;
	}

	@Override
	public int deleteJoinInsureInfo(HttpServletRequest request) throws SQLException {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] ids = request.getParameterValues("check");
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					paramMap.put("seq", id);
					this.joinInsureDao.deleteJoinInsureInfo(paramMap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public void allowPaBenJoinInsureUpdateBz(String id) {
		joinInsureDao.allowPaBenJoinInsureUpdate(id);
	}

	@Override
	public List getAllowPaBenJoinInsureUpdateBz() {
		return joinInsureDao.getAllowPaBenJoinInsureUpdate();
	}

	@Override
	public void backPaBenJoinInsureUpdateBz() {
		joinInsureDao.backPaBenJoinInsureUpdateBz();
		
	}
//修改
	@Override
	public int updatePaBenManageAddInfoBz(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		int j = 0;
		String adminID = admin.getAdminID();//操作人
		String[] empIDs = request.getParameterValues("empid");//员工编号
		String[] joinValues = request.getParameterValues("joinValue");//入社基数
		String[] years = request.getParameterValues("year");//开始缴纳年
		String[] months = request.getParameterValues("month");//开始缴纳月
		System.out.println(empIDs+":"+joinValues+"--"+years+";;;"+months);
		for (int i = 0; i < empIDs.length; i++) {
			double joinValue = 0;
			String empID = empIDs[i];
			String year = years[i];
			String month = months[i];
			if (joinValues[i] != null && !"".equals(joinValues[i])) {
				joinValue = Double.parseDouble(joinValues[i]);
			}
			paramMap.put("empID", empID);
			paramMap.put("joinValue", joinValue);
			paramMap.put("yearMonth", year+month);
			paramMap.put("adminID", adminID);//修改人
			joinInsureDao.updatePaBenManageAddInfoBz(paramMap);
			j=1;
	}
		return j;
		
}

	@Override
	public List getInsJoinNumInfoExcel(HttpServletRequest request) {
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
		retrunList = joinInsureDao.getNOInsJoinNumList(paramMap) ;
		return retrunList ;
	}
	//清空临时表
	public void deletePaImp() {
		joinInsureDao.deletePaImp();
		}
	// 查询所有的参保管理临时表数据
	public void getPaJoinImplList(HttpServletRequest request) {
		List<Map> listImp = this.joinInsureDao.getPaJoinImplList();
		if (listImp != null && listImp.size() > 0) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String adminID = admin.getAdminID();// 操作人
			String cpnyId = admin.getCpnyId();// 操作人公司id

			for (int i = 0; i < listImp.size(); i++) {
				String empId = (String) listImp.get(i).get("PERSON_ID");
				String cname = (String) listImp.get(i).get("CHINESENAME");
				String startDate = (String) listImp.get(i).get("START_DATE");
				BigDecimal avgSalary = (BigDecimal) listImp.get(i).get(
						"JOIN_VALUE");
				String Person_id = null;

				Map paramMap = ObjectBindUtil.getRequestParamData(request,
						"seach_");
				paramMap.put("CPNYID", cpnyId);
				paramMap.put("EMPID", empId);
				List listp = joinInsureDao.findPIdByParam(paramMap);// 根据公司号和工号查找person_id
				if (listp.size() != 0 || listp != null) {
					Person_id = listp.toString().substring(12,listp.toString().length()-2);
				}
				if (Person_id != null && !"".equals(Person_id)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("PERSON_ID", Person_id);
					tempMap.put("START_DATE", startDate);
					tempMap.put("JOIN_VALUE", avgSalary);

					List<Map> paBaseList = this.joinInsureDao
							.checkPaBenhsJoin(tempMap);

					if (paBaseList != null && paBaseList.size() == 1) {
						this.joinInsureDao.updatePaBenhsJoin(tempMap);
					} else {
						tempMap.put("CREATED_BY", adminID);
						tempMap.put("ACTIVITY", 1);
						tempMap.put("SOCIAL_STATUS", 1);
						this.joinInsureDao.insertPaBenhsJoin(tempMap);
					}

				}
			}
		}

	}
}
