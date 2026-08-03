package com.ait.pa.service.imp.salary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.PortalDao;
import com.ait.pa.service.salary.PortalSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.impl.PostSerImpl;
import com.ait.web.util.BeanUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class PortalSerImp implements PortalSer {

	Logger logger = Logger.getLogger(PostSerImpl.class);
	
	@Autowired
	private PortalDao portalDao;
	
	@SuppressWarnings("unchecked")
	public List getPortalInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID",admin.getCpnyId());		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = portalDao.getPortalInfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;				 
		}else{
			retrunList = portalDao.getPortalInfoList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPortalInfoListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID",admin.getCpnyId());		
		retrunInt = portalDao.getPortalInfoListCnt(paramMap) ;
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public List getSapItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		retrunList = portalDao.getSapItemList(paramMap) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaSummaryInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("TABLE_NAME", "PA_SUMMARY_"+admin.getCpnyId());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		itemIdList = portalDao.getPaDataToSapList(paramMap);
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			paramMap.put("FIELD_ID", itemMap.get("FIELD_ID"));
			retrunList = portalDao.getPaSummaryInfoList(paramMap) ;
		
			for (int i = 0; i < retrunList.size(); i++) {
				Map map = (LinkedHashMap) retrunList.get(i);
				String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
									StringUtil.checkNull(map.get("PA_MONTH")),
									StringUtil.checkNull(map.get("GIVE_DATE")),
									StringUtil.checkNull(map.get("DEPTNO")),
									StringUtil.checkNull(map.get("ITEM_ID")),
									StringUtil.checkNull(map.get("EMP_TYPE_CODE")),
									StringUtil.checkNull(map.get("ITEM_SUM"))
									};
				paSummaryList.add(result);
			}
		}
		return paSummaryList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDepartMentInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID",admin.getCpnyId());
		retrunList = portalDao.getDepartMentInfoList(paramMap) ;
		
		List<String[]> departMentList = new ArrayList<String[]>();
		for (int i = 0; i < retrunList.size(); i++) {
			Map map = (LinkedHashMap) retrunList.get(i);
			String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
								StringUtil.checkNull(map.get("DEPTNO")),
								StringUtil.checkNull(map.get("DEPTNAME"))
								};
			departMentList.add(result);
		}
		return departMentList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaSummaryInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> itemIdList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paSummaryList = new ArrayList<String[]>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID",admin.getCpnyId());
		String createBy = request.getAttribute("CREATED_BY")!=null?request.getAttribute("CREATED_BY").toString():"SYS";
		String makeType = request.getAttribute("MAKE_TYPE")!=null?request.getAttribute("MAKE_TYPE").toString():"H";
		String sendDate = request.getAttribute("SAP_SEND_DATE")!=null?request.getAttribute("SAP_SEND_DATE").toString():
			(paramMap.get("SAP_GIVE_DATE")!=null?paramMap.get("SAP_GIVE_DATE").toString():"2012-01-01");
		paramMap.put("MAKE_TYPE", makeType);
		
		itemIdList = portalDao.getPaDataToSapList(paramMap);
		
		for (int k = 0;k<itemIdList.size();k++){
			Map itemMap = (LinkedHashMap)itemIdList.get(k);
			paramMap.put("FIELD_ID", itemMap.get("FIELD_ID"));
			retrunList = portalDao.getC01PaSummaryInfoList(paramMap) ;
			//将生成sap的工资信息插入pa_to_sap_summary表中
			if(retrunList.size() >= 1){
				for(LinkedHashMap paSummary: retrunList){
					try {
						int sendCount = 1;
						//获取sap中summary当天发送的次数
						try {
							sendCount = Integer.parseInt(portalDao.getC01SapSummarySendCount(paSummary));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						paSummary.put("SEND_COUNT", sendCount);
						paSummary.put("CREATED_BY", createBy);
						this.portalDao.addC01PaSummary(paSummary);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			for (int i = 0; i < retrunList.size(); i++) {
				Map map = (LinkedHashMap) retrunList.get(i);
				String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
									StringUtil.checkNull(map.get("YEAR")),
									StringUtil.checkNull(map.get("MONTH")),
									StringUtil.checkNull(map.get("SEND_DATE")),
									StringUtil.checkNull(map.get("DEPTNO")),
									StringUtil.checkNull(map.get("ITEM")),
									StringUtil.checkNull(map.get("EMP_TYPE")),
									StringUtil.checkNull(map.get("NUM")),
									StringUtil.checkNull(map.get("DEPT_DISTINGUISH_NO"))
									};
				paSummaryList.add(result);
			}
		}
		return paSummaryList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("sum_sapYear")!=null?paramMap.get("sum_sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return retrunList;
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = portalDao.getC01SapSummaryList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;				 
		}else{
			retrunList = portalDao.getC01SapSummaryList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryAllExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("sum_sapYear")!=null?paramMap.get("sum_sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return retrunList;
		}
		retrunList = portalDao.getC01SapSummaryList(paramMap) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getC01SapSummaryListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("sum_sapYear")!=null?paramMap.get("sum_sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return 0;
		}
		retrunInt = portalDao.getC01SapSummaryListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		List paActualList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("firm_sapYear")!=null?paramMap.get("firm_sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return retrunList;
		}
		if (UiUtil.getPageNum(request) > 0){
			paActualList = portalDao.getC01PaActualSalaryAllList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;				 
		}else{
			paActualList = portalDao.getC01PaActualSalaryAllList(paramMap) ;
		}
		//C01即将要发送的工资信息如果已经发送过则显示"Y--已发送"，否则"N--未发送"
		for (int i = 0; i < paActualList.size(); i++) {
			Map paActualMap = (LinkedHashMap) paActualList.get(i);
			paramMap.remove("CPNY_ID");
			paramMap.remove("EMPID");
			paramMap.remove("PA_MONTH");
			paramMap.remove("GIVE_DATE");
			paramMap.put("CPNY_ID", paActualMap.get("CPNY_ID").toString());
			paramMap.put("EMPID", paActualMap.get("EMPID").toString());
			paramMap.put("PA_MONTH", paActualMap.get("PA_MONTH").toString());
			paramMap.put("GIVE_DATE", paActualMap.get("GIVE_DATE").toString());
			
			int cnt =0;
			cnt = portalDao.getC01PaActualSalaryCntByEmpid(paramMap);
			if(cnt>=1){
				paActualMap.put("SEND_FLAG", "Y");
			}else{
				paActualMap.put("SEND_FLAG", "N");
			}
			retrunList.add(paActualMap);
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		List paActualList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("firm_sapYear")!=null?paramMap.get("firm_sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return retrunList;
		}
		paActualList = portalDao.getC01PaActualSalaryAllList(paramMap) ;
		
		//C01即将要发送的工资信息如果已经发送过则显示"Y--已发送"，否则"N--未发送"
		for (int i = 0; i < paActualList.size(); i++) {
			Map paActualMap = (LinkedHashMap) paActualList.get(i);
			paramMap.remove("CPNY_ID");
			paramMap.remove("EMPID");
			paramMap.remove("PA_MONTH");
			paramMap.remove("GIVE_DATE");
			paramMap.put("CPNY_ID", paActualMap.get("CPNY_ID").toString());
			paramMap.put("EMPID", paActualMap.get("EMPID").toString());
			paramMap.put("PA_MONTH", paActualMap.get("PA_MONTH").toString());
			paramMap.put("GIVE_DATE", paActualMap.get("GIVE_DATE").toString());
			
			int cnt =0;
			cnt = portalDao.getC01PaActualSalaryCntByEmpid(paramMap);
			if(cnt>=1){
				paActualMap.put("SEND_FLAG", "Y");
			}else{
				paActualMap.put("SEND_FLAG", "N");
			}
			retrunList.add(paActualMap);
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getC01PaActualSalaryAllListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("firm_sapYear")!=null?paramMap.get("firm_sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return 0;
		}
		retrunInt = portalDao.getC01PaActualSalaryAllListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("sapYear")!=null?paramMap.get("sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return retrunList;
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = portalDao.getC01PaActualSalaryAfterList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;				 
		}else{
			retrunList = portalDao.getC01PaActualSalaryAfterList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("sapYear")!=null?paramMap.get("sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return retrunList;
		}
		retrunList = portalDao.getC01PaActualSalaryAfterList(paramMap) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getC01PaActualSalaryAfterListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("sapYear")!=null?paramMap.get("sapYear").toString():"";
		if("".equals(year)){//不选择工资年月份返回空
			return 0;
		}
		retrunInt = portalDao.getC01PaActualSalaryAfterListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public List checkEmpSapInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		retrunList = portalDao.checkEmpSapInfoList(paramMap) ;
		
		return retrunList ;
	}		
	
	@SuppressWarnings("unchecked")
	public List getSapErrorEmpInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("firm_sapYear")!=null?paramMap.get("firm_sapYear").toString():"2011";
		String month = paramMap.get("firm_sapMonth")!=null?paramMap.get("firm_sapMonth").toString():"10";
		paramMap.put("PA_MONTH", year+month);
		
		retrunList = portalDao.checkEmpSapInfoList(paramMap) ;
		
		return retrunList ;
	}	
	
	/**
	 * 将选定月份的闸北、嘉兴店的人员信息转换成对应月份的现金发放方式
	 */
	@SuppressWarnings("unchecked")
	public int addZhaBeiAndJiaXingCash(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String createBy = request.getAttribute("CREATED_BY")!=null?request.getAttribute("CREATED_BY").toString():"SYS";
		paramMap.put("CREATED_BY",createBy);
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		List<LinkedHashMap> empPaInfoList = new ArrayList<LinkedHashMap>() ;
		String year = paramMap.get("firm_sapYear")!=null?paramMap.get("firm_sapYear").toString():"2011";
		String month = paramMap.get("firm_sapMonth")!=null?paramMap.get("firm_sapMonth").toString():"10";
		paramMap.put("PA_MONTH", year+month);
		
		empPaInfoList = portalDao.getZhaBeiAndJiaXingEmpPaInfoList(paramMap) ;
		try {
			this.portalDao.addZhaBeiAndJiaXingCash(empPaInfoList) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List getZhaBeiAndJiaXingEmpPaInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("firm_sapYear")!=null?paramMap.get("firm_sapYear").toString():"2011";
		String month = paramMap.get("firm_sapMonth")!=null?paramMap.get("firm_sapMonth").toString():"10";
		paramMap.put("PA_MONTH", year+month);
		
		retrunList = portalDao.getZhaBeiAndJiaXingEmpPaInfoList(paramMap) ;
		return retrunList ;
	}	
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryInfoList(HttpServletRequest request) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		List<String[]> paActualSalaryList = new ArrayList<String[]>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String createBy = request.getAttribute("CREATED_BY")!=null?request.getAttribute("CREATED_BY").toString():"SYS";
		String makeType = request.getAttribute("MAKE_TYPE")!=null?request.getAttribute("MAKE_TYPE").toString():"H";
		paramMap.put("MAKE_TYPE", makeType);
		
		retrunList = portalDao.getC01PaActualSalaryInfoList(paramMap) ;
		//将生成sap的工资信息插入pa_to_sap_summary表中
		if(retrunList.size() >= 1){
			for(LinkedHashMap paActual: retrunList){
				int sendCount = 1;
				try {
					//获取sap中ActualSalary当天发送的次数
					sendCount = Integer.parseInt(portalDao.getC01SapActualSalarySendCount(paActual));
					
					paActual.put("SEND_COUNT", sendCount);
					paActual.put("CREATED_BY", createBy);
					this.portalDao.addC01PaActual(paActual);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < retrunList.size(); i++) {
			Map map = (LinkedHashMap) retrunList.get(i);
			String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
								StringUtil.checkNull(map.get("PA_MONTH")),
								StringUtil.checkNull(map.get("SEND_DATE")),
								StringUtil.checkNull(map.get("DEPT_DISTINGUISH_NO")),
								StringUtil.checkNull(map.get("DEPT_DISTINGUISH_NAME")),
								StringUtil.checkNull(map.get("EMPID")),
								StringUtil.checkNull(map.get("EMP_NAME")),
								StringUtil.checkNull(map.get("ACTUAL_RELEASE_SALARY")),
								StringUtil.checkNull(map.get("BANK_ID")),
								StringUtil.checkNull(map.get("BANK_NAME")),
								StringUtil.checkNull(map.get("CARD_NO")),
								StringUtil.checkNull(map.get("BANK_BRANCH_CD")),
								StringUtil.checkNull(map.get("DEPTNO"))
								};
			paActualSalaryList.add(result);
		}
		return paActualSalaryList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01EmpInfoList(Map paramMap) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> empInfoListGet = new ArrayList<LinkedHashMap>();
		List<String[]> empInfoList = new ArrayList<String[]>();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String createBy =  paramMap.get("CREATED_BY")!=null?paramMap.get("CREATED_BY").toString():"SYS";
		String makeType = paramMap.get("MAKE_TYPE")!=null?paramMap.get("MAKE_TYPE").toString():"A";
		paramMap.put("MAKE_TYPE", makeType);
		
		empInfoListGet = portalDao.getC01EmpInfoList(paramMap) ;
		
		String sendType = paramMap.get("SEND_TYPE")!=null?paramMap.get("SEND_TYPE").toString():"";
		//如果发送类型是：SEND_TYPE = SEND_PART 选择性发送
		if(sendType!=null && !"".equals(sendType) && "SEND_PART".equals(sendType)){
			retrunList = empInfoListGet;
		}else{//如果是全部性发送，则还是按照修改更新时间来进行比较之后再发送
			for(LinkedHashMap nEmployee: empInfoListGet){
				paramMap.remove("CPNY_ID");
				paramMap.remove("EMPID");
				paramMap.remove("MAKE_TYPE");
				paramMap.put("CPNY_ID", nEmployee.get("CPNY_ID"));
				paramMap.put("EMPID", nEmployee.get("EMPID"));
				paramMap.put("MAKE_TYPE", nEmployee.get("MAKE_TYPE"));
				LinkedHashMap oEmployee = new LinkedHashMap(); 
				oEmployee = (LinkedHashMap)portalDao.getC01SapEmpByPersonid(paramMap) ;
				//如果员工信息在PA_DATA_TO_SAP_EMP表里没有则是新添加的，否则进行信息的比较
				if(oEmployee == null){
					retrunList.add(nEmployee);
				//员工信息在PA_DATA_TO_SAP_EMP表里已经存在了，比较其对应的字段信息，有不同之处才写入SAP文件，否则不写
				}else{
					//hr_employee表里的信息与PA_DATA_TO_SAP_EMP表里的存在不同之处
					int compareResult = -1;
					BeanUtil util = new BeanUtil();
					try {
						compareResult = util.compareObjectYN(nEmployee, oEmployee);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(compareResult == 1){
						retrunList.add(nEmployee);
					}
				}	
			}
		}
		//将生成sap的人事信息插入PA_DATA_TO_SAP_EMP表中
		if(retrunList.size() >= 1){
			for(LinkedHashMap emp: retrunList){
				try {
					emp.put("CREATED_BY", createBy);
					this.portalDao.addC01EmpInfo(emp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < retrunList.size(); i++) {
			Map map = (LinkedHashMap) retrunList.get(i);
			String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
								StringUtil.checkNull(map.get("EMPID")),
								StringUtil.checkNull(map.get("LOCAL_NAME")),
								StringUtil.checkNull(map.get("KOREAN_NAME")),
								StringUtil.checkNull(map.get("ENGLISH_NAME")),
								StringUtil.checkNull(map.get("DEPTNO")),
								StringUtil.checkNull(map.get("DEPT_NAME")),
								StringUtil.checkNull(map.get("DUTY_NO")),
								StringUtil.checkNull(map.get("DUTY_NAME")),
								
								StringUtil.checkNull(map.get("HOME_PHONE")),
								StringUtil.checkNull(map.get("CELLPHONE")),
								StringUtil.checkNull(map.get("IDCARD_NO")),
								StringUtil.checkNull(map.get("EMP_OFFICE")),
								StringUtil.checkNull(map.get("CARD_NO")),
								StringUtil.checkNull(map.get("BANK_ID")),
								StringUtil.checkNull(map.get("BANK_NAME"))
								};
			empInfoList.add(result);
		}
		return empInfoList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01EmpPostInfoList(Map paramMap) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> empPostListGet = new ArrayList<LinkedHashMap>();
		List<String[]> empPostList = new ArrayList<String[]>();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String createBy =  paramMap.get("CREATED_BY")!=null?paramMap.get("CREATED_BY").toString():"SYS";
		String makeType = paramMap.get("MAKE_TYPE")!=null?paramMap.get("MAKE_TYPE").toString():"A";
		//paramMap.put("MAKE_TYPE", makeType);
		
		empPostListGet = portalDao.getC01EmpPostInfoList(paramMap) ;
		//2012-11-27 GMD接口 去掉新旧数据比较的过程（用的时候直接恢复即可）
		/*
		String sendType = paramMap.get("SEND_TYPE")!=null?paramMap.get("SEND_TYPE").toString():"";
		//如果发送类型是：SEND_TYPE = SEND_PART 选择性发送
		if(sendType!=null && !"".equals(sendType) && "SEND_PART".equals(sendType)){
			retrunList = empPostListGet;
		}else{//如果是全部性发送，则还是按照修改更新时间来进行比较之后再发送
			for(LinkedHashMap nEmployee: empPostListGet){
				paramMap.remove("CPNY_ID");
				paramMap.remove("EMPID");
				paramMap.remove("MAKE_TYPE");
				paramMap.put("CPNY_ID", nEmployee.get("CPNY_ID"));
				paramMap.put("EMPID", nEmployee.get("EMPID"));
				paramMap.put("MAKE_TYPE", nEmployee.get("MAKE_TYPE"));
				
				LinkedHashMap oEmployee = new LinkedHashMap(); 
				oEmployee = (LinkedHashMap)portalDao.getC01SapEmpPostByEmpid(paramMap) ;
				//如果员工信息在PA_DATA_TO_SAP_EMPPOST表里没有则是新添加的，否则进行信息的比较
				if(oEmployee == null){
					retrunList.add(nEmployee);
				//员工信息在PA_DATA_TO_SAP_EMP表里已经存在了，比较其对应的字段信息，有不同之处才写入SAP文件，否则不写
				}else{
					//hr_employee表里的信息与PA_DATA_TO_SAP_EMPPOST表里的存在不同之处
					int compareResult = -1;
					BeanUtil util = new BeanUtil();
					try {
						compareResult = util.compareObjectYN(nEmployee, oEmployee);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(compareResult == 1){
						retrunList.add(nEmployee);
					}
				}
			}
		}*/
		retrunList = empPostListGet;
		//将生成sap的人事信息插入PA_DATA_TO_SAP_EMPPOST表中
		if(retrunList.size() >= 1){
			for(LinkedHashMap empPost: retrunList){
				try {
					empPost.put("CREATED_BY", createBy);
					this.portalDao.addC01EmpPost(empPost);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < retrunList.size(); i++) {
			Map map = (LinkedHashMap) retrunList.get(i);
			String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
								StringUtil.checkNull(map.get("EMPID")),
								StringUtil.checkNull(map.get("POSITION_NAME")),
								StringUtil.checkNull(map.get("DUTY_NAME")),
								StringUtil.checkNull(map.get("POST_NAME")),
								StringUtil.checkNull(map.get("DATE_LEFT")),
								StringUtil.checkNull(map.get("DEPT_NAME")),
								StringUtil.checkNull(map.get("EMP_NAME")),
								StringUtil.checkNull(map.get("DEPT_DISTINGUISH_NAME")),
								StringUtil.checkNull(map.get("DEPT_DISTINGUISH_NO"))
								};
			empPostList.add(result);
		}
		return empPostList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getC01DepartMentInfoList(Map paramMap) {
		List<LinkedHashMap> retrunList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> departMentListGet = new ArrayList<LinkedHashMap>();
		List<String[]> departMentList = new ArrayList<String[]>();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String createBy =  paramMap.get("CREATED_BY")!=null?paramMap.get("CREATED_BY").toString():"SYS";
		String makeType = paramMap.get("MAKE_TYPE")!=null?paramMap.get("MAKE_TYPE").toString():"A";
		paramMap.put("MAKE_TYPE", makeType);
		paramMap.put("CPNY_ID","C01");
		
		departMentListGet = portalDao.getC01DepartMentInfoList(paramMap) ;
		for(LinkedHashMap nDepart: departMentListGet){
			paramMap.remove("CPNY_ID");
			paramMap.remove("DEPTNO");
			paramMap.remove("MAKE_TYPE");
			paramMap.put("CPNY_ID", nDepart.get("CPNY_ID"));
			paramMap.put("DEPTNO", nDepart.get("DEPTNO"));
			paramMap.put("MAKE_TYPE", nDepart.get("MAKE_TYPE"));
			LinkedHashMap oDepart = new LinkedHashMap(); 
			oDepart = (LinkedHashMap)portalDao.getC01SapDeptByDeptno(paramMap) ;
			//如果员工信息在PA_DATA_TO_SAP_DEPARTMENT表里没有则是新添加的，否则进行信息的比较
			if(oDepart == null){
				retrunList.add(nDepart);
			//员工信息在PA_DATA_TO_SAP_DEPARTMENT表里已经存在了，比较其对应的字段信息，有不同之处才写入SAP文件，否则不写
			}else{
				//hr_department表里的信息与PA_DATA_TO_SAP_DEPARTMENT表里的存在不同之处
				int compareResult = -1;
				BeanUtil util = new BeanUtil();
				try {
					compareResult = util.compareObjectYN(nDepart, oDepart);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(compareResult == 1){
					retrunList.add(nDepart);
				}
			}	
		}
		//将生成sap的工资信息插入PA_DATA_TO_SAP_DEPARTMENT表中
		if(retrunList.size() >= 1){
			for(LinkedHashMap depart: retrunList){
				try {
					depart.put("CREATED_BY", createBy);
					this.portalDao.addC01DepartMent(depart);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		for (int i = 0; i < retrunList.size(); i++) {
			Map map = (LinkedHashMap) retrunList.get(i);
			String[] result = { StringUtil.checkNull(map.get("CPNY_ID")),
								StringUtil.checkNull(map.get("DEPTNO")),
								StringUtil.checkNull(map.get("DEPT_NAME")),
								StringUtil.checkNull(map.get("DATE_CREATED")),
								StringUtil.checkNull(map.get("DATE_ENDED")),
								StringUtil.checkNull(map.get("ACTIVITY")),
								StringUtil.checkNull(map.get("DEPT_LEVEL"))
								};
			departMentList.add(result);
		}
		return departMentList ;
	}
	
	@SuppressWarnings("unchecked")
	public int addPortalLog(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String createBy = request.getAttribute("CREATED_BY")!=null?request.getAttribute("CREATED_BY").toString():"SYS";
		paramMap.put("CREATED_BY",createBy);
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		List<String> portalResultList = new ArrayList<String>();
		portalResultList = (ArrayList)request.getAttribute("portalResultList");
		for(String result: portalResultList){
			try {
				String[] results = result.split(",");
				paramMap.put("MAKE_TYPE", "1");
				paramMap.put("MAKE_RESULT", results[0]);
				paramMap.put("PORTAL_NAME", results[1]);
				paramMap.put("REMARK_MESSAGE", results[2]);
				
				this.portalDao.addPortalLog(paramMap) ;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}
	
	//C01专用自动任务进行SAP日志记录
	@SuppressWarnings("unchecked")
	public int addPortalLog(List<String> portalResultList,String cpny_id) {
		//页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CREATED_BY","SYS");
		paramMap.put("CPNY_ID",cpny_id);
		
		for(String result: portalResultList){
			try {
				String[] results = result.split(",");
				paramMap.put("MAKE_TYPE", "0");
				paramMap.put("MAKE_RESULT", results[0]);
				paramMap.put("PORTAL_NAME", results[1]);
				paramMap.put("REMARK_MESSAGE", results[2]);
				
				this.portalDao.addPortalLog(paramMap) ;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteSAPItem(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID",admin.getCpnyId());
		try {
		
			this.portalDao.deleteSAPItem(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addSAPItem(HttpServletRequest request,List aliasList) {
		
		try {
			this.portalDao.addSAPItem(aliasList) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}
}
