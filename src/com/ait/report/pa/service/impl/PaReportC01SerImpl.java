package com.ait.report.pa.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.report.pa.dao.PaReportC01Dao;
import com.ait.report.pa.service.PaReportC01Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01SerImpl.java
 * @Description: implement Class PaReportC01SerImpl.java
 * @Create date: Sep 14, 2012 5:29:38 PM
 * @Create by: lufeng (lufeng@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaReportC01SerImpl implements PaReportC01Ser {
	Logger logger = Logger.getLogger(PaReportSerImpl.class);
	@Autowired
	private PaReportC01Dao paReportDao;
	
	/**
	 * 跳转到乐天玛特实发薪资汇总表（总公司）报表页面，Excel导出用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaActualSalaryExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportDao.getPaActualSalaryExcelList(paramMap) ;
		return returnList ;
	}

	/**
	 * 跳转到乐天玛特薪资成本汇总表（总公司）报表页面，Excel导出用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaPayrollCostsExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportDao.getPaPayrollCostsExcelList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getSysdate(HttpServletRequest request) {	
		return paReportDao.getSysdate();
	}
	
	/**
	 * 查询上月月份 (query the sysdate )
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getLastMonthStr(HttpServletRequest request) {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		return paReportDao.getLastMonthStr(paramMap);
	}
	
	/**
	 * 按月份查询异常明细里的人员异常明细情况
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAbnormalEmpExcelList(HttpServletRequest request) {	
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"C01");
		
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"2012";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"01";
		paramMap.put("PA_MONTH", year+month);
		String lastMonth = ((LinkedHashMap)paReportDao.getLastMonthStr(paramMap)).get("LAST_MONTHSTR")!=null?
				((LinkedHashMap)paReportDao.getLastMonthStr(paramMap)).get("LAST_MONTHSTR").toString():"SYSDATE";	
		String distinguisno = paramMap.get("DEPT_DISTINGUISH_NO")!=null?paramMap.get("DEPT_DISTINGUISH_NO").toString():"000";
		String distinguishName = (String)paReportDao.getDistinguishNameByNo(paramMap).toString();
		
        List lastEmpList = new ArrayList();
        List sysEmpList = new ArrayList();
        
        List diaoRuEmpList = new ArrayList();
        List diaoChuEmpList = new ArrayList();
        
        List ruZhiEmpList = new ArrayList();
        List liZhiEmpList = new ArrayList();
        List liZhiFirstEmpList = new ArrayList();
        List jiXinList = new ArrayList();
        
        List normalEmpList = new ArrayList();
        List laborEmpList = new ArrayList();
        List hourEmpList = new ArrayList();
        List totalEmpList = new ArrayList();
        
        List disInEmpList = new ArrayList();
        List disOutEmpList = new ArrayList();
        
        List actualSalaryZero = new ArrayList();
        
		//查询上月末在职人数
        paramMap.put("PA_MONTH", lastMonth);
        lastEmpList = paReportDao.getEmpInfoListByMonth(paramMap);
		//查询本月末在职人数
        paramMap.remove("PA_MONTH");
        paramMap.put("PA_MONTH", year+month);
        sysEmpList = paReportDao.getEmpInfoListByMonth(paramMap);
        
        //查询本月离职人数
        liZhiEmpList = paReportDao.getLiZhiList(paramMap);
        //查询本月1日离职生效人数
        paramMap.put("FIRST_DAY", year+month);
        liZhiFirstEmpList = paReportDao.getLiZhiList(paramMap);
        //查询本月入职人数
        ruZhiEmpList = paReportDao.getRuZhiList(paramMap);
        
		//查询本月调入人数
        paramMap.put("DIAORU_NO", distinguisno);
        diaoRuEmpList = paReportDao.getDiaoRuList(paramMap);
		//查询本月调出人数
        paramMap.remove("DIAORU_NO");
        paramMap.put("DIAOCHU_NO", distinguisno);
        diaoChuEmpList = paReportDao.getDiaoChuList(paramMap);
        
        //查询本月计薪人数
        jiXinList = paReportDao.getPaInfoList(paramMap);
        
        //查询本月、合同工在职人数
        paramMap.put("EMP_TYPE", "CONTRACT");
        normalEmpList = paReportDao.getEmpInfoListByEmpType(paramMap);
        //查询本月、劳务工在职人数
        paramMap.remove("EMP_TYPE");
        paramMap.put("EMP_TYPE", "LOBAR");
        laborEmpList = paReportDao.getEmpInfoListByEmpType(paramMap);
        //查询本月、小时工在职人数
        paramMap.remove("EMP_TYPE");
        paramMap.put("EMP_TYPE", "HOUR");
        hourEmpList = paReportDao.getEmpInfoListByEmpType(paramMap);
        //查询本月、总共（店内小计）在职人数
        paramMap.remove("EMP_TYPE");
        totalEmpList = paReportDao.getEmpInfoListByEmpType(paramMap);
        
        //查询本月、派入在职人数
        paramMap.put("DISPATCH_TYPE", "DISPATCH_IN");
        disInEmpList = paReportDao.getDispatchEmpInfoList(paramMap);
        //查询本月、派出在职人数
        paramMap.remove("DISPATCH_TYPE");
        paramMap.put("DISPATCH_TYPE", "DISPATCH_OUT");
        disOutEmpList = paReportDao.getDispatchEmpInfoList(paramMap);
        //本月实发工资为0的人数
        actualSalaryZero = paReportDao.getActualSalaryZeroList(paramMap);
        
        returnMap.put("DISTINGUISH_NAME", distinguishName);
        //上月末、本月末在职人数
        returnMap.put("lastEmpList", lastEmpList);
        returnMap.put("lastEmpListCnt", lastEmpList!=null?lastEmpList.size():0);
        returnMap.put("sysEmpList", sysEmpList);
        returnMap.put("sysEmpListCnt", sysEmpList!=null?sysEmpList.size():0);
        //本月入职、离职、本月1日离职生效人数
        returnMap.put("ruZhiEmpList", ruZhiEmpList);
        returnMap.put("ruZhiEmpListCnt", ruZhiEmpList!=null?ruZhiEmpList.size():0);
        returnMap.put("liZhiEmpList", liZhiEmpList);
        returnMap.put("liZhiEmpListCnt", liZhiEmpList!=null?liZhiEmpList.size():0);
        returnMap.put("liZhiFirstEmpList", liZhiFirstEmpList);
        returnMap.put("liZhiFirstEmpListCnt", liZhiFirstEmpList!=null?liZhiFirstEmpList.size():0);
        //本月调入、调出人数
        returnMap.put("diaoRuEmpList", diaoRuEmpList);
        returnMap.put("diaoRuEmpListCnt", diaoRuEmpList!=null?diaoRuEmpList.size():0);
        returnMap.put("diaoChuEmpList", diaoChuEmpList);
        returnMap.put("diaoChuEmpListCnt", diaoChuEmpList!=null?diaoChuEmpList.size():0);
        //本月计薪人数
        returnMap.put("jiXinList", jiXinList);
        returnMap.put("jiXinListCnt", jiXinList!=null?jiXinList.size():0);
        //店内合同工、劳务工、小时工及店内总人数
        returnMap.put("normalEmpList", normalEmpList);
        returnMap.put("normalEmpListCnt", normalEmpList!=null?normalEmpList.size():0);
        returnMap.put("laborEmpList", laborEmpList);
        returnMap.put("laborEmpListCnt", laborEmpList!=null?laborEmpList.size():0);
        returnMap.put("hourEmpList", hourEmpList);
        returnMap.put("hourEmpListCnt", hourEmpList!=null?hourEmpList.size():0);
        returnMap.put("totalEmpList", totalEmpList);
        returnMap.put("totalEmpListCnt", totalEmpList!=null?totalEmpList.size():0);
        //店内某月派入、派出人数
        returnMap.put("disInEmpList", disInEmpList);
        returnMap.put("disInEmpListCnt", disInEmpList!=null?disInEmpList.size():0);
        returnMap.put("disOutEmpList", disOutEmpList);
        returnMap.put("disOutEmpListCnt", disOutEmpList!=null?disOutEmpList.size():0);
        //店内某月实发工资为0的人
        returnMap.put("actualSalaryZero", actualSalaryZero);
        returnMap.put("actualSalaryZeroCnt", actualSalaryZero!=null?actualSalaryZero.size():0);
        
		return returnMap;
	}
	
	/**
	 * 跳转到乐天玛特异常明细--保险情况明细报表页面，Excel导出用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAbnormalInsExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		String lastMonth = ((LinkedHashMap)paReportDao.getLastMonthStr(paramMap)).get("LAST_MONTHSTR")!=null?
				((LinkedHashMap)paReportDao.getLastMonthStr(paramMap)).get("LAST_MONTHSTR").toString():"SYSDATE";
		paramMap.put("LAST_MONTH", lastMonth);	
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportDao.getAbnormalInsExcelList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 乐天玛特--外派人员薪资信息报表信息,Excel导出用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDispatchEmpPaExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportDao.getDispatchEmpPaExcelList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 乐天玛特--外派人员保险信息报表信息,Excel导出用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDispatchEmpInsExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_INS")!=null?paramMap.get("YEAR_INS").toString():"";
		String month = paramMap.get("MONTH_INS")!=null?paramMap.get("MONTH_INS").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportDao.getDispatchEmpInsExcelList(paramMap) ;
		return returnList ;
	}
}

