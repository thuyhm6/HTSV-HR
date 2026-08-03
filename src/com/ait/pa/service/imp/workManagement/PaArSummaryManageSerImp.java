package com.ait.pa.service.imp.workManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.disc.dao.RetrieveSqlMasterDao;
import com.ait.hrm.dao.HrmDao;
import com.ait.pa.dao.PaArSummaryManageDao;
import com.ait.pa.service.workManagement.PaArSummaryManageSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.StringUtil;

@Service
public class PaArSummaryManageSerImp implements PaArSummaryManageSer {

	Logger logger = Logger.getLogger(PaArSummaryManageSerImp.class);
	
	@Autowired
	private PaArSummaryManageDao paArSummaryManageDao ;
	@Autowired
	private HrmDao hrmDao;		
	@Autowired
	private RetrieveSqlMasterDao retrievesqlmasterdao;
	
	@SuppressWarnings("unchecked")
	public List getPaArSummaryForManageList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PAY_SCHEDULE_NO")!=null){
			retrunList = paArSummaryManageDao.getPaArSummaryForManageList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 更改考勤总计管理例外信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatePaArSummaryForManageInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {

			this.paArSummaryManageDao.updatePaArSummaryForManageInfo(request,paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	public ModelMap getPaArSummaryForManageExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap){
		List retrunList = null ;
		List aliasNameList = new ArrayList();
		List sqlResult = null;
		String sql = " SELECT EMPID employeeNo, LOCAL_NAME employeeName, DEPT_NAME departmentName";
		String subSql = " FROM (SELECT HR.PERSON_ID, HR.EMPID, HR.LOCAL_NAME, HR.DEPTNO, GET_DEPT_NAME(HR.DEPTNO,'vi') DEPT_NAME ";
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", paramMap.get("interCpnyID"));
		
		List codeList = hrmDao.getDeptTree("pa.workManagement.getSelectCodeMultiArSummaryList",paramMap);
		for(int i=0;i<codeList.size();i++){
			Map map = (Map)codeList.get(i);
			String name = map.get("DEPTNAME").toString();
			name = name.length() > 10 ? name.substring(0, 10) : name;
			sql += ", MAX(ITEM_" + map.get("DEPTNO") + ") " + name.replace("%", "").replace(" ", "_");
			subSql += ", DECODE(AR.ITEM_NO, '" + map.get("DEPTNO") + "', DECODE(FINAL_VALUE, '', CAL_VALUE, FINAL_VALUE), 0) ITEM_" + map.get("DEPTNO");
		}
		sql += subSql + " FROM AR_SUMMARY_MANAGE_" + paramMap.get("interCpnyID") + " AR, HR_EMPLOYEE HR ";
		sql += " WHERE AR.PERSON_ID = HR.PERSON_ID AND AR.PAY_SCHEDULE_NO = '" + paramMap.get("PAY_SCHEDULE_NO") + "'  ";
		
		if(!"".equals(StringUtil.checkNull(paramMap.get("DEPTNO")))){
			sql += " AND EXISTS( SELECT 1 FROM HR_DEPARTMENT WHERE HR_DEPARTMENT.DEPTNO = HR.DEPTNO"
				+ " START WITH HR_DEPARTMENT.DEPTNO = '" + paramMap.get("DEPTNO") + "' "
				+ " CONNECT BY PRIOR HR_DEPARTMENT.DEPTNO = HR_DEPARTMENT.PARENT_DEPT_NO ) ";
		}
		if(!"".equals(StringUtil.checkNull(paramMap.get("KEY")))){
			sql += " AND ( HR.EMPID LIKE '%" + paramMap.get("KEY") + "%'  or HR.LOCAL_NAME LIKE '%" + paramMap.get("KEY") + "%'  ) ";
		}
		sql += " ) GROUP BY EMPID, LOCAL_NAME,DEPTNO,DEPT_NAME ORDER BY DEPTNO";
		paramMap.put("querySql", sql);
		try {
			sqlResult = this.retrievesqlmasterdao.querySql(paramMap);
			if(sqlResult != null && sqlResult.size() > 0){
				Map indexMap = (Map) sqlResult.get(0);
				Set set = indexMap.keySet();// 用接口实例接口
				Iterator iter = set.iterator();
				while (iter.hasNext()) {// 遍历二次,速度慢
					aliasNameList.add((String) iter.next());
				}
			}
			modelMap.put("NameList", aliasNameList);
			modelMap.put("ValueList", sqlResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelMap;
	}
}
