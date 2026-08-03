package com.ait.report.hr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jofc2.OFCException;
import jofc2.model.Chart;
import jofc2.model.axis.Label;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.LineChart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ait.report.hr.dao.HrReportDao;
import com.ait.report.hr.service.HrReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportSerImpl.java
 * @Description: implement Class HrReportSerImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Service
public class HrReportSerImpl implements HrReportSer {
	Logger logger = Logger.getLogger(HrReportSerImpl.class);
	@Autowired
	private HrReportDao hrReportDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map viewReport(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		
		String reportName = paramMap.get("reportName").toString();
		logger.info("reportName:"+reportName);
		
		if("humanResources".equals(reportName)){
			hrReportDao.calReport(paramMap);
		}
		
		return hrReportDao.viewReport(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String viewReportChart(HttpServletRequest request) throws Exception {
		
        //y轴数据集合-使用数量   
        List<Number> dataSet = new ArrayList<Number>();   
        //x轴数据集合-浏览器类型   
        List<Label> xLabel = new ArrayList<Label>();   
        //获取需要显示的数据集   
        
        List browserList = new ArrayList(20);
        Map a = new HashMap();
        a.put("statCount", 6);
        a.put("statVar", "Safari");
        browserList.add(a);
        
        Map b = new HashMap();
        b.put("statCount", 16);
        b.put("statVar", "MSIE6X");
        browserList.add(b);
        
        Map c = new HashMap();
        c.put("statCount", 73);
        c.put("statVar", "MSIE7X");
        browserList.add(c);
        
        Map d = new HashMap();
        d.put("statCount", 183);
        d.put("statVar", "MSIE8X");
        browserList.add(d);
        
        Map e = new HashMap();
        e.put("statCount", 7);
        e.put("statVar", "Firefox");
        browserList.add(e);
        
        Map f = new HashMap();
        f.put("statCount", 7);
        f.put("statVar", "Chrome");
        browserList.add(f);
        
        for (int i = 0; i < browserList.size(); i++) {   
            Map map = (Map) browserList.get(i);   
            //填充x轴   
            dataSet.add((Integer) map.get("statCount"));   
            //填充y轴   
            xLabel.add(new Label((String) map.get("statVar")));   
        }   
        //设置X轴内容   
        XAxis labels = new XAxis();   
        labels.addLabels(xLabel);   
        //设置Y轴显示值域:Range的三个参数含义为：坐标最小值，最大值和步进值   
        YAxis range = new YAxis();   
        range.setRange(0, 200, 10);   
        //OFC折线图设置   
        LineChart lineChart = new LineChart(LineChart.Style.NORMAL);   
        lineChart.addValues(dataSet);   
        lineChart.setColour("#6666FF");   
        lineChart.setText("user number");   
        //图表设置   
        Chart chart = new Chart("bower");   
        chart.setXAxis(labels);   
        chart.setYAxis(range);   
        chart.addElements(lineChart);   
        //打印JSON格式的文本   
        System.out.print(chart.toString());   
        /*
        response.setContentType("application/json-rpc;charset=utf-8");   
        response.setHeader("Cache-Control", "no-cache");   
        response.setHeader("Expires", "0");   
        response.setHeader("Pragma", "No-cache");   
        response.getWriter().write(chart.toString());   */
        return chart.toString();  
        
	}	
	
	/**
	 * 员工在职证明信息(Certificate of Employment info query)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCertificateEmploymentList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				hrReportDao.getCertificateEmploymentList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = hrReportDao.getCertificateEmploymentList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 员工在职证明信息数量(Certificate of Employment info count)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getCertificateEmploymentListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		returnInt = hrReportDao.getCertificateEmploymentListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 员工在职证明信息(Certificate of Employment info query)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCertificateEmploymentExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		returnList = hrReportDao.getCertificateEmploymentList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 员工人事信息(person info query)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonRecodeList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				hrReportDao.getPersonRecodeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = hrReportDao.getPersonRecodeList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 员工人事信息数量(query the person info count)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonRecodeListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		returnInt = hrReportDao.getPersonRecodeListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getSysdate(HttpServletRequest request) {	
		return hrReportDao.getSysdate();
	}
	
	/**
	 * 员工人事记录卡信息 (personal record info )
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonRecordByPid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		return hrReportDao.getPersonRecordByPid(paramMap);
	}
	
	/**
	 * 根据person_id查询教育背景信息(query the education info by the person_id)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRecordEducationList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		returnList = hrReportDao.getRecordEducationList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 根据person_id查询合同信息(query the contract info by the person_id)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRecordContractList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		returnList = hrReportDao.getRecordContractList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 根据person_id查询社内经历信息(query the experience inside info by the person_id)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRecordExperienceInsideList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		returnList = hrReportDao.getRecordExperienceInsideList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 根据person_id查询社外经历信息(query the experience outside info by the person_id)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRecordExperienceOutsideList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		returnList = hrReportDao.getRecordExperienceOutsideList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 根据person_id查询家庭信息(query the family info by the person_id)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRecordFamilyList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		returnList = hrReportDao.getRecordFamilyList(paramMap) ;
		
		return returnList ;
	}	
	
	
	
	
	/**
	 * 人员查询 (employee inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForInterfaceList() {

		List retrunList = new ArrayList() ;
		
		hrReportDao.getHrEmployeeForInterfaceList() ;
		
		return retrunList ;
		
	}
	
	/**
	 * 人员查询 (employee inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForTxt( ) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		
		
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrEmployeeForInterfaceList();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("EMP_OFFICE")),
								StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_CODE")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_NAME"))
								};
			paSummaryList.add(result);
			
		}
		return paSummaryList ;
	}
	
	
	/**
	 * 人员数量查询 (employee count inquires)
	 * @param request
	 * @return int
	 */
	@Override
	public int getHrEmployeeForInterfaceCnt( ) {
		return hrReportDao.getHrEmployeeForInterfaceCnt();
	}
	
	
	/**
	 * HR TO 考勤食堂 部门
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrDeptForMess() {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		
		
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrDeptForMess();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("PARENT_DEPT_NO")),
								StringUtil.checkNull(itemMap.get("DATE_CREATED")),
								StringUtil.checkNull(itemMap.get("DATE_ENDED")),
								StringUtil.checkNull(itemMap.get("ACTIVITY"))
								};
			paSummaryList.add(result);
			
		}
		return paSummaryList ;
	}
	
	
	/**
	 * HR TO 考勤食堂 人事
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMess( ) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		
		
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrUserInfoForMess();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DATE_LEFT")),
								StringUtil.checkNull(itemMap.get("EMP_OFFICE")),
								StringUtil.checkNull(itemMap.get("CARD_NO")),
								StringUtil.checkNull(itemMap.get("CARD_NO_ME"))
								};
			paSummaryList.add(result);
			
		}
		return paSummaryList ;
	}
	
	
	
	/**
	 * HR TO SAP 人事信息
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSap( ) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrPerInfoForSap();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("COUNTRY")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("IDCARD_NO")),
								StringUtil.checkNull(itemMap.get("BANKCODE")),
								StringUtil.checkNull(itemMap.get("CARD_NO"))
								};
			paSummaryList.add(result);
			
		}
		return paSummaryList ;
	}
	
	
	/**
	 * HR TO SAP 奖金
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSap( ) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getMoneyAwardForSap();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("YEAR")),
								StringUtil.checkNull(itemMap.get("MONTH")),
								StringUtil.checkNull(itemMap.get("EXPORTDATE")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_CODE")),
								StringUtil.checkNull(itemMap.get("PROCODE")),
								StringUtil.checkNull(itemMap.get("PAPROREMARK")),
								StringUtil.checkNull(itemMap.get("MONEY"))
								};
			paSummaryList.add(result);
			
		}
		return paSummaryList ;
	}
	
	
	
	
	@Override
	public int getPersonCount( ) {
		return hrReportDao.getPersonCount();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List getPersonList( ) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> personList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getPersonList();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("MEAL_NUM"))
								};
			personList.add(result);
			
		}
		return personList ;
	}

	
	/**
	 * 人事登记卡C11   导出Excel
	 * @param request
	 * @return List
	 */
	@Override
	@SuppressWarnings("unchecked")
	
	public List getPersonBasicInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("DEPTNO",paramMap.get("seach_DEPTNO").toString());
		paramMap.put("EMPID",paramMap.get("seach_EMPID").toString());
		retrunList = hrReportDao.getPersonBasicInfo(paramMap);

		return retrunList;
	}
	
	//C12--------------------start-----------------------C12
	/**
	 * C12 HR TO 考勤食堂 部门
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC12() {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrDeptForMessC12();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("PARENT_DEPT_NO")),
								StringUtil.checkNull(itemMap.get("DATE_CREATED")),
								StringUtil.checkNull(itemMap.get("DATE_ENDED")),
								StringUtil.checkNull(itemMap.get("ACTIVITY"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C12 HR TO 考勤食堂 人事
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC12( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrUserInfoForMessC12();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DATE_LEFT")),
								StringUtil.checkNull(itemMap.get("EMP_OFFICE")),
								StringUtil.checkNull(itemMap.get("CARD_NO")),
								StringUtil.checkNull(itemMap.get("CARD_NO_ME"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	//C12
	@Override
	public int getPersonCountC12New( ) {
		return hrReportDao.getPersonCountC12New();
	}
	
	//C12
	@Override
	public int getPersonCountC12( ) {
		return hrReportDao.getPersonCountC12();
	}
	//C12
	@SuppressWarnings("unchecked")
	public List getPersonListC12( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> personList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getPersonListC12();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("MEAL_NUM"))
								};
			personList.add(result);
			
		}
		return personList ;
	}
	
	//C12
	@SuppressWarnings("unchecked")
	public List getPersonListC12New( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> personList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getPersonListC12New();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("MEAL_NUM"))
								};
			personList.add(result);
			
		}
		return personList ;
	}
	
	/**
	 * C12 HR TO SAP 人事信息
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC12( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrPerInfoForSapC12();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("COUNTRY")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("IDCARD_NO")),
								StringUtil.checkNull(itemMap.get("BANKCODE")),
								StringUtil.checkNull(itemMap.get("CARD_NO"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C12 HR TO SAP 奖金
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC12( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getMoneyAwardForSapC12();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("YEAR")),
								StringUtil.checkNull(itemMap.get("MONTH")),
								StringUtil.checkNull(itemMap.get("EXPORTDATE")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_CODE")),
								StringUtil.checkNull(itemMap.get("PROCODE")),
								StringUtil.checkNull(itemMap.get("PAPROREMARK")),
								StringUtil.checkNull(itemMap.get("MONEY"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C12 人员查询 (employee inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForTxtC12( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrEmployeeC12ForInterfaceList();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("EMP_OFFICE")),
								StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_CODE")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_NAME"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C12 人员查询 (employee inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC12ForInterfaceList() {
		List retrunList = new ArrayList() ;
		hrReportDao.getHrEmployeeC12ForInterfaceList() ;
		return retrunList ;
	}
	
	/**
	 * C12 人员数量查询 (employee count inquires)
	 * @param request
	 * @return int
	 */
	@Override
	public int getHrEmployeeC12ForInterfaceCnt( ) {
		return hrReportDao.getHrEmployeeC12ForInterfaceCnt();
	}
	//C12----------------------end-----------------------C12
	
	//C13----------------------start---------------------C13
	/**
	 * C13 HR TO 考勤食堂 部门
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC13() {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrDeptForMessC13();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("PARENT_DEPT_NO")),
								StringUtil.checkNull(itemMap.get("DATE_CREATED")),
								StringUtil.checkNull(itemMap.get("DATE_ENDED")),
								StringUtil.checkNull(itemMap.get("ACTIVITY"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C13 HR TO 考勤食堂 人事
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC13( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrUserInfoForMessC13();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DATE_LEFT")),
								StringUtil.checkNull(itemMap.get("EMP_OFFICE")),
								StringUtil.checkNull(itemMap.get("CARD_NO")),
								StringUtil.checkNull(itemMap.get("CARD_NO_ME"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	//C13
	@Override
	public int getPersonCountC13( ) {
		return hrReportDao.getPersonCountC13();
	}
	//C13
	@SuppressWarnings("unchecked")
	public List getPersonListC13( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> personList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getPersonListC13();
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("PERSON_ID")),
								StringUtil.checkNull(itemMap.get("MEAL_NUM"))
								};
			personList.add(result);
			
		}
		return personList ;
	}
	
	/**
	 * C13 HR TO SAP 人事信息
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC13( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrPerInfoForSapC13();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("COUNTRY")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("IDCARD_NO")),
								StringUtil.checkNull(itemMap.get("BANKCODE")),
								StringUtil.checkNull(itemMap.get("CARD_NO"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C13 HR TO SAP 奖金
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC13( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getMoneyAwardForSapC13();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("YEAR")),
								StringUtil.checkNull(itemMap.get("MONTH")),
								StringUtil.checkNull(itemMap.get("EXPORTDATE")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_CODE")),
								StringUtil.checkNull(itemMap.get("PROCODE")),
								StringUtil.checkNull(itemMap.get("PAPROREMARK")),
								StringUtil.checkNull(itemMap.get("MONEY"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C13 人员查询 (employee inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForTxtC13( ) {
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		itemIdList = hrReportDao.getHrEmployeeC13ForInterfaceList();
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			String[] result = { StringUtil.checkNull(itemMap.get("EMPID")),
								StringUtil.checkNull(itemMap.get("LOCAL_NAME")),
								StringUtil.checkNull(itemMap.get("EMP_OFFICE")),
								StringUtil.checkNull(itemMap.get("CPNY_ID")),
								StringUtil.checkNull(itemMap.get("DEPTNO")),
								StringUtil.checkNull(itemMap.get("DEPTNAME")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_CODE")),
								StringUtil.checkNull(itemMap.get("EMP_TYPE_NAME"))
								};
			paSummaryList.add(result);
		}
		return paSummaryList ;
	}
	
	/**
	 * C13 人员查询 (employee inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC13ForInterfaceList() {
		List retrunList = new ArrayList() ;
		hrReportDao.getHrEmployeeC13ForInterfaceList() ;
		return retrunList ;
	}
	
	/**
	 * C13 人员数量查询 (employee count inquires)
	 * @param request
	 * @return int
	 */
	@Override
	public int getHrEmployeeC13ForInterfaceCnt( ) {
		return hrReportDao.getHrEmployeeC13ForInterfaceCnt();
	}
	
	//C13----------------------C13-----------------------C13
}
