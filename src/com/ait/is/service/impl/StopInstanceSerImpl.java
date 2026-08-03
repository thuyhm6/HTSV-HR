package com.ait.is.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.is.dao.StopInsureDao;
import com.ait.is.dao.StopInsureDao;
import com.ait.is.service.StopInsureSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Service
public class StopInstanceSerImpl implements StopInsureSer{
	@Autowired
	private StopInsureDao stopInsureDao;
	@SuppressWarnings("unchecked")
	@Override
	public List getStopInsureList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
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
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
					stopInsureDao.getPaBenStopInsureListBz(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = stopInsureDao.getPaBenStopInsureListBz(paramMap) ;
		}
		return returnList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getStopInsureCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("companyID", admin.getCpnyId());
		int i = stopInsureDao.getStopInsureCnt(paramMap) ;
		return i ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int deleteStopInsureInfo(HttpServletRequest request) throws SQLException{
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] ids = request.getParameterValues("check");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				paramMap.put("seq", id);
				this.stopInsureDao.deleteStopInsureInfo(paramMap);
			}
		}
		return 1;
	}

	@Override
	public void allowPaBenStopInsureUpdateBz(String id) {
		stopInsureDao.allowPaBenStopInsureUpdate(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAllowPaBenStopInsureUpdateBz(HttpServletRequest request) {
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
		return stopInsureDao.getAllowPaBenStopInsureUpdate(paramMap);
	}

	@Override
	public void backPaBenStopInsureUpdateBz() {
		
		stopInsureDao.backPaBenStopInsureUpdateBz();
		
	}
//修改
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBenManageAddInfoBz(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		int j = 0;
		String adminID = admin.getAdminID();//操作人
		String[] empIDs = request.getParameterValues("empid");//员工编号
		String[] years = request.getParameterValues("year");//开始缴纳年
		String[] months = request.getParameterValues("month");//开始缴纳月
		for (int i = 0; i < empIDs.length; i++) {
			@SuppressWarnings("unused")
			double StopValue = 0;
			String empID = empIDs[i];
			System.out.println(empID+"员工号。。。。。。。。。。。。。。。。");
			String year = years[i];
			String month = months[i];
			System.out.println(year+"年份。。。。。。。。。。。。。。。。");
			System.out.println(month+"月份。。。。。。。。。。。。。。。。");
			paramMap.put("empID", empID);
			paramMap.put("yearMonth", year+month);
			paramMap.put("adminID", adminID);//修改人
			stopInsureDao.updatePaBenManageAddInfoBz(paramMap);
			j=1;
	}
		return j;
		
}

	@SuppressWarnings("unchecked")
	@Override
	public List getInsStopNumInfoExcel(HttpServletRequest request) {
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
		retrunList = stopInsureDao.getNOInsStopNumList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 根据cpny_id查询保险地区列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceAreaListByCpnyId(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		String cpnyId = admin.getCpnyId();//操作人
		paramMap.put("CPNY_ID", cpnyId);
		
		returnList = stopInsureDao.getInsuranceAreaListByCpnyId(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 保险地区参数数据维护页面（添加、修改、导入）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceParamDataChList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
					stopInsureDao.getInsuranceParamDataChList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = stopInsureDao.getInsuranceParamDataChList(paramMap) ;
		}
		return returnList ;
	}

	/**
	 * 保险地区参数数据维护页面（添加、修改、导入），数据数量
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getInsuranceParamDataChCnt(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		ListCnt =  this.stopInsureDao.getInsuranceParamDataChCnt(paramMap);
		
		return ListCnt;
	}
	
	/**
	 * 添加保险参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addInsuranceParamData(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String cpnyId = admin.getCpnyId();
		String payAreaCd = paramMap.get("PAY_AREA_CD") != null ? paramMap.get("PAY_AREA_CD").toString() : "";
		String insrareaId= paramMap.get("INSRAREA_ID") != null ? paramMap.get("INSRAREA_ID").toString() : "";
		String insureId = paramMap.get("INSURE_ID") != null ? paramMap.get("INSURE_ID").toString() : "";
		String insureRate = paramMap.get("INSURE_RATE") != null ? paramMap.get("INSURE_RATE").toString() : "";
		String insureValue = paramMap.get("INSURE_VALUE") != null ? paramMap.get("INSURE_VALUE").toString() : "";
		String activity = paramMap.get("ACTIVITY_FLAG") != null ? paramMap.get("ACTIVITY_FLAG").toString() : "";
		String remark = paramMap.get("REMARK") != null ? paramMap.get("REMARK").toString() : "";
		String carry_way =paramMap.get("CARRY_WAY") !=null? paramMap.get("CARRY_WAY").toString() :"";
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap.put("CPNY_ID", cpnyId);
		dateMap.put("PAY_AREA_CD", payAreaCd);
		dateMap.put("INSRAREA_ID", insrareaId);
		dateMap.put("INSURE_ID", insureId);
		dateMap.put("INSURE_RATE", insureRate);
		dateMap.put("INSURE_VALUE", insureValue);
		dateMap.put("ACTIVITY", activity);
		dateMap.put("REMARK", remark);
		dateMap.put("CARRY_WAY", carry_way);
		dateMap.put("CREATED_BY", admin.getPersonId());
		
		this.stopInsureDao.addInsuranceParamData(dateMap);
		
		return 1;
	}
	
	/**
	 * 修改保险参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsuranceParamData(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String dateNo = paramMap.get("DATA_NO") != null ? paramMap.get("DATA_NO").toString() : "";
		String insureRate = paramMap.get("INSURE_RATE") != null ? paramMap.get("INSURE_RATE").toString() : "";
		String insureValue = paramMap.get("INSURE_VALUE") != null ? paramMap.get("INSURE_VALUE").toString() : "";
		String activity = paramMap.get("ACTIVITY_FLAG") != null ? paramMap.get("ACTIVITY_FLAG").toString() : "";
		String remark = paramMap.get("REMARK") != null ? paramMap.get("REMARK").toString() : "";
		String carray_way = paramMap.get("CARRY_WAY") != null ? paramMap.get("CARRY_WAY").toString() : "";//四舍五入放入map
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap.put("DATA_NO", dateNo);
		dateMap.put("INSURE_RATE", insureRate);
		dateMap.put("INSURE_VALUE", insureValue);
		dateMap.put("ACTIVITY", activity);
		dateMap.put("REMARK", remark);
		dateMap.put("CARRY_WAY", carray_way);
		dateMap.put("UPDATED_BY", admin.getPersonId());
		
		this.stopInsureDao.updateInsuranceParamData(dateMap);
		
		return 1;
	}
	
	/**
	 * 导入保险参数--查看信息列表(get insurance param data import list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getIsParamImportInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//导入的数据需要统一提交、取消，不需要分页
		/*if (UiUtil.getPageNum(request) > 0) {
			returnList = stopInsureDao.getIsParamImportInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = stopInsureDao.getIsParamImportInfoList(paramMap);
		}*/
		returnList = stopInsureDao.getIsParamImportInfoList(paramMap);
		return returnList;
	}
	
	/**
	 * 导入保险参数--查看信息列表总数(get insurance param data import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getIsParamImportInfoListCnt(HttpServletRequest request,String cntType) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		int cnt = 0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if("ERROR".equals(cntType)){
			cnt = this.stopInsureDao.getIsParamImportInfoListErrorCnt(paramMap);
		}else{
			cnt = this.stopInsureDao.getIsParamImportInfoListCnt(paramMap);
		}
		
		return cnt;
	}
	
	/**
	 * 删除保险参数信息(delete insurance param data information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delIsParamDataImport(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return stopInsureDao.delIsParamDataImport(paramMap);
	}
	
	/**
	 * 添加导入的保险参数数据(add insurance param data)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addImportIsParamData(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchIsParamDataList = new ArrayList();
		List importIsParamDataList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		importIsParamDataList = this.stopInsureDao.getIsParamImportInfoList(paramMap);
		int checkFlag = 0;
		//这里对所有导入保险参数临时表里的保险参数数据进行验证，并将验证结果存入保险参数信息临时表中
		checkFlag = checkImportIsParamData(importIsParamDataList,paramMap,request);
		//只有当所有的保险参数验证全部通过之后才能进行保险参数信息插入
		if(checkFlag==0){
			for (int k = 0; k <importIsParamDataList.size(); k++) {
				LinkedHashMap isParamMap = new LinkedHashMap();
				isParamMap = (LinkedHashMap)importIsParamDataList.get(k);
				String dataNo = isParamMap.get("DATA_NO")!=null?isParamMap.get("DATA_NO").toString():"";
				String cpnyId = isParamMap.get("CPNY_ID")!=null?isParamMap.get("CPNY_ID").toString():"";
				String payAreaCd = isParamMap.get("PAY_AREA_CD") != null ? isParamMap.get("PAY_AREA_CD").toString(): "";
				String insrareaName = isParamMap.get("INSRAREA_CHECK")!=null?isParamMap.get("INSRAREA_CHECK").toString():"";
				String insrareaId = "";
				String insureName = isParamMap.get("INSURE_CHECK")!=null?isParamMap.get("INSURE_CHECK").toString():"";
				String insureId = "";
				String insureRate = isParamMap.get("INSURE_RATE")!=null?isParamMap.get("INSURE_RATE").toString():"";
				String insureValue = isParamMap.get("INSURE_VALUE")!=null?isParamMap.get("INSURE_VALUE").toString():"";
				String activity = isParamMap.get("ACTIVITY")!=null?isParamMap.get("ACTIVITY").toString():"1";
				String remark = isParamMap.get("REMARK")!=null?isParamMap.get("REMARK").toString():"";
				
				LinkedHashMap dataMap = new LinkedHashMap();
				dataMap.put("DATA_NO", dataNo);
				dataMap.put("CPNY_ID", cpnyId);
				dataMap.put("PAY_AREA_CD", payAreaCd);
				//将福利地区名字转换成code
				dataMap.put("INSRAREA_CHECK", insrareaName);
				insrareaId = ((LinkedHashMap)stopInsureDao.getInsrareaCheckListByCpnyId(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("INSRAREA_ID", insrareaId);
				//将福利项目名字转换成code
				dataMap.put("INSURE_CHECK", insureName);
				insureId = ((LinkedHashMap)stopInsureDao.getInsureCheckListByCpnyId(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("INSURE_ID", insureId);
				
				dataMap.put("INSURE_RATE", insureRate);
				dataMap.put("INSURE_VALUE", insureValue);
				dataMap.put("ACTIVITY", activity);
				dataMap.put("REMARK", remark);
				dataMap.put("CREATED_BY", admin.getPersonId());
				
				batchIsParamDataList.add(dataMap);
			}
			this.stopInsureDao.addIsParamDataImport(batchIsParamDataList);
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 验证导入的保险参数数据(check insurance param data info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportIsParamData(List importIsParamDataList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		int result = 0;
		for (int k = 0; k <importIsParamDataList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = (LinkedHashMap)importIsParamDataList.get(k);
			dateMap.put("UPDATED_BY", admin.getPersonId());
			
			List payAreaList = new ArrayList();
			int payAreaFlag = 0;
			List insrareaList = new ArrayList();
			int insrareaFlag = 0;
			List insureList = new ArrayList();
			int insureFlag = 0;
			
			String errorContent = "";
			//1.验证大区编码是否存在，是否属于该法人
			payAreaList = this.stopInsureDao.getInsuranceAreaCheckListByCpnyId(dateMap);
			payAreaFlag = payAreaList!=null?payAreaList.size():0;
			if(payAreaFlag <= 0) {
				errorContent = "[该大区编码不存在!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}/* 2014-08-20 lufeng 存在同一个 大区编码对应两个部门名称的情况，按时不验证
			else if(payAreaFlag > 1){
				errorContent = "[该大区编码存在多个!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}*/
			//2.验证福利地区是否存在，是否属于该法人
			insrareaList = this.stopInsureDao.getInsrareaCheckListByCpnyId(dateMap);
			insrareaFlag = insrareaList!=null?insrareaList.size():0;
			if(insrareaFlag <= 0) {
				errorContent = "[该福利地区不存在!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;	
			}else if(insrareaFlag > 1){
				errorContent = "[该福利地区对应多个编号!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}
			//3.验证福利项目是否存在，是否属于该法人
			insureList = this.stopInsureDao.getInsureCheckListByCpnyId(dateMap);
			insureFlag = insureList!=null?insureList.size():0;
			if(insureFlag <= 0) {
				errorContent = "[该福利项目不存在!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}else if(insureFlag > 1){
				errorContent = "[该福利项目对应多个编号!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * 添加导入的保险参数数据(add insurance param data)---没有大区编码的法人
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addImportIsParamDataNch(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchIsParamDataList = new ArrayList();
		List importIsParamDataList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		importIsParamDataList = this.stopInsureDao.getIsParamImportInfoList(paramMap);
		int checkFlag = 0;
		//这里对所有导入保险参数临时表里的保险参数数据进行验证，并将验证结果存入保险参数信息临时表中
		checkFlag = checkImportIsParamDataNch(importIsParamDataList,paramMap,request);
		//只有当所有的保险参数验证全部通过之后才能进行保险参数信息插入
		if(checkFlag==0){
			for (int k = 0; k <importIsParamDataList.size(); k++) {
				LinkedHashMap isParamMap = new LinkedHashMap();
				isParamMap = (LinkedHashMap)importIsParamDataList.get(k);
				String dataNo = isParamMap.get("DATA_NO")!=null?isParamMap.get("DATA_NO").toString():"";
				String cpnyId = isParamMap.get("CPNY_ID")!=null?isParamMap.get("CPNY_ID").toString():"";
				String payAreaCd = isParamMap.get("PAY_AREA_CD") != null ? isParamMap.get("PAY_AREA_CD").toString(): "";
				String insrareaName = isParamMap.get("INSRAREA_CHECK")!=null?isParamMap.get("INSRAREA_CHECK").toString():"";
				String insrareaId = "";
				String insureName = isParamMap.get("INSURE_CHECK")!=null?isParamMap.get("INSURE_CHECK").toString():"";
				String insureId = "";
				String insureRate = isParamMap.get("INSURE_RATE")!=null?isParamMap.get("INSURE_RATE").toString():"";
				String insureValue = isParamMap.get("INSURE_VALUE")!=null?isParamMap.get("INSURE_VALUE").toString():"";
				String activity = isParamMap.get("ACTIVITY")!=null?isParamMap.get("ACTIVITY").toString():"1";
				String remark = isParamMap.get("REMARK")!=null?isParamMap.get("REMARK").toString():"";
				
				LinkedHashMap dataMap = new LinkedHashMap();
				dataMap.put("DATA_NO", dataNo);
				dataMap.put("CPNY_ID", cpnyId);
				dataMap.put("PAY_AREA_CD", payAreaCd);
				//将福利地区名字转换成code
				dataMap.put("INSRAREA_CHECK", insrareaName);
				insrareaId = ((LinkedHashMap)stopInsureDao.getInsrareaCheckListByCpnyId(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("INSRAREA_ID", insrareaId);
				//将福利项目名字转换成code
				dataMap.put("INSURE_CHECK", insureName);
				insureId = ((LinkedHashMap)stopInsureDao.getInsureCheckListByCpnyId(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("INSURE_ID", insureId);
				
				dataMap.put("INSURE_RATE", insureRate);
				dataMap.put("INSURE_VALUE", insureValue);
				dataMap.put("ACTIVITY", activity);
				dataMap.put("REMARK", remark);
				dataMap.put("CREATED_BY", admin.getPersonId());
				
				batchIsParamDataList.add(dataMap);
			}
			this.stopInsureDao.addIsParamDataImport(batchIsParamDataList);
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 验证导入的保险参数数据(check insurance param data info)---没有大区编码的法人
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportIsParamDataNch(List importIsParamDataList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		int result = 0;
		for (int k = 0; k <importIsParamDataList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = (LinkedHashMap)importIsParamDataList.get(k);
			dateMap.put("UPDATED_BY", admin.getPersonId());
			
			//List payAreaList = new ArrayList();
			//int payAreaFlag = 0;
			List insrareaList = new ArrayList();
			int insrareaFlag = 0;
			List insureList = new ArrayList();
			int insureFlag = 0;
			
			String errorContent = "";
			//1.验证大区编码是否存在，是否属于该法人
			/*payAreaList = this.stopInsureDao.getInsuranceAreaCheckListByCpnyId(dateMap);
			payAreaFlag = payAreaList!=null?payAreaList.size():0;
			if(payAreaFlag <= 0) {
				errorContent = "[该大区编码不存在!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}else if(payAreaFlag > 1){
				errorContent = "[该大区编码存在多个!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}*/
			//2.验证福利地区是否存在，是否属于该法人
			insrareaList = this.stopInsureDao.getInsrareaCheckListByCpnyId(dateMap);
			insrareaFlag = insrareaList!=null?insrareaList.size():0;
			if(insrareaFlag <= 0) {
				errorContent = "[该福利地区不存在!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}else if(insrareaFlag > 1){
				errorContent = "[该福利地区对应多个编号!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}
			//3.验证福利项目是否存在，是否属于该法人
			insureList = this.stopInsureDao.getInsureCheckListByCpnyId(dateMap);
			insureFlag = insureList!=null?insureList.size():0;
			if(insureFlag <= 0) {
				errorContent = "[该福利项目不存在!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}else if(insureFlag > 1){
				errorContent = "[该福利项目对应多个编号!]";
				dateMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.stopInsureDao.updateIsParamDataCheckResult(dateMap);
				result = result + 1;
			}
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * 删除临时表中所有导入的保险参数(delete insurance param data information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelIsParamDataImport(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		try {
			// 批量封装加班申请数据并处理
			this.stopInsureDao.cancelIsParamDataImport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 根据福利地区编号/名称查询福利地区信息(get the insrarea info by no or name)
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getInsAreaInfoListByKey(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		//paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = stopInsureDao.getInsAreaInfoListByKey(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = stopInsureDao.getInsAreaInfoListByKey(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 根据福利项目编号/名称查询福利项目信息(get the insure info by no or name)
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getInsureInfoListByKey(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = stopInsureDao.getInsureInfoListByKey(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = stopInsureDao.getInsureInfoListByKey(paramMap) ;
		}
		return retrunList ;
	}
}
