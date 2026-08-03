package com.ait.is.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.is.dao.BaseManagementForSearchDao;
import com.ait.is.service.BaseManagementForSearchSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class BaseManagementForSearchSerImpl implements BaseManagementForSearchSer{
    @Autowired
	private BaseManagementForSearchDao basemagementForSearcherDao;

	@Override
	public List ViewInsuranceBaseManagementForSearch(HttpServletRequest request) throws SQLException {
		String returnMessage="";//返回消息
		int returnStatue=200;//返回状态
		String searchDate = request.getParameter("SearchDate");// 版本日期
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		request.setAttribute("companyID",  admin.getCpnyId());
		request.setAttribute("year1", request.getParameter("year1"));
		request.setAttribute("month1", request.getParameter("month1"));
		request.setAttribute("year2", request.getParameter("year2"));
		request.setAttribute("month2", request.getParameter("month2"));
		request.setAttribute("year3", request.getParameter("year3"));
		request.setAttribute("month3", request.getParameter("month3"));
		String method = request.getParameter("method");//方法区分
		paramMap.put("pabDate", request.getParameter("year1")+request.getParameter("month1"));//选择年月
		paramMap.put("leftDate", request.getParameter("leftDate"));//离职日期
		paramMap.put("adminID", admin.getCpnyId());//操作人
		
		//获取CheckList
		List checkList = basemagementForSearcherDao.getInsuranceBaseNumCheckListBz();
		request.setAttribute("checkList", checkList);
		
		String order = request.getParameter("order");//排序区分
		StringBuffer sb = new StringBuffer("ORDER BY ");
		if ("upDept".equals(order)) {
			sb.append("E.DEPTID DESC");//按部门降序排列
			request.setAttribute("dept", "desc");
		}else if ("downDept".equals(order)) {
			sb.append("E.DEPTID ASC");//按部门升序排列
			request.setAttribute("dept", "asc");
		}else if ("upEmp".equals(order)) {
			sb.append("T.EMPID DESC");//按职号降序排列
			request.setAttribute("emp", "desc");
		}else if ("downEmp".equals(order)) {
			sb.append("T.EMPID ASC");//按职号升序排列
			request.setAttribute("emp", "asc");
		}else if ("upCoef".equals(order)) {
			sb.append("C1.CODE_ID DESC");//按职系降序排列
			request.setAttribute("coef", "desc");
		}else if ("downCoef".equals(order)) {
			sb.append("C1.CODE_ID ASC");//按职系升序排列
			request.setAttribute("coef", "asc");
		}else if ("upReg".equals(order)) {
			sb.append("C.CODE_ID DESC");//按户口性质降序排列
			request.setAttribute("reg", "desc");
		}else if ("downReg".equals(order)) {
			sb.append("C.CODE_ID ASC");//按户口性质升序排列
			request.setAttribute("reg", "asc");
		}else if ("upStatus".equals(order)) {
			sb.append("C2.CODE_ID DESC");//按在职类型降序排列
			request.setAttribute("status", "desc");
		}else if ("downStatus".equals(order)) {
			sb.append("C2.CODE_ID ASC");//按在职类型升序排列
			request.setAttribute("status", "asc");
		}else if ("upStart".equals(order)) {
			sb.append("E.DATE_STARTED DESC");//按入社日期降序排列
			request.setAttribute("start", "desc");
		}else if ("downStart".equals(order)) {
			sb.append("E.DATE_STARTED ASC");//按入社日期升序排列
			request.setAttribute("start", "asc");
		}else if ("upEnd".equals(order)) {
			sb.append("E.DATE_LEFT DESC");//按离社日期降序排列
			request.setAttribute("end", "desc");
		}else if ("downEnd".equals(order)) {
			sb.append("E.DATE_LEFT ASC");//按离社日期升序排列
			request.setAttribute("end", "asc");
		}else if ("upPay".equals(order)) {
			sb.append("T.PAY_SALARY DESC");//按平均扣税工资降序排列
			request.setAttribute("pay", "desc");
		}else if ("downPay".equals(order)) {
			sb.append("T.PAY_SALARY ASC");//按平均扣税工资升序排列
			request.setAttribute("pay", "asc");
		}else if ("upAvg".equals(order)) {
			sb.append("T.AVG_SALARY DESC");//按年度基数降序排列
			request.setAttribute("avg", "desc");
		}else if ("downAvg".equals(order)) {
			sb.append("T.AVG_SALARY ASC");//按年度基数升序排列
			request.setAttribute("avg", "asc");
		}else if ("upType".equals(order)) {
			sb.append("T.DATE_TYPE DESC");//按数据生成状态降序排列
			request.setAttribute("type", "desc");
		}else if ("downType".equals(order)) {
			sb.append("T.DATE_TYPE ASC");//按数据生成状态升序排列
			request.setAttribute("type", "asc");
		}else if ("upError".equals(order)) {
			sb.append("T.ERROR_REMARK DESC");//按错误提示降序排列
			request.setAttribute("error", "desc");
		}else if ("downError".equals(order)) {
			sb.append("T.ERROR_REMARK ASC");//按错误提示升序排列
			request.setAttribute("error", "asc");
		}else{
			sb.append("E.DEPTNO, T.PERSON_ID ASC");//默认按部门，职号升序排序
		}
		List showList;
		Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap1.put("order", sb);
		paramMap1.put("interLanguage", admin.getLanguage());
		if (UiUtil.getPageNum(request) > 0){
			showList = 
				basemagementForSearcherDao.getInsuranceBaseNumListBz(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}else {
			showList = basemagementForSearcherDao.getInsuranceBaseNumListBz(paramMap1);//显示信息集合
		}
		basemagementForSearcherDao.backInsuranceBaseNumUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
		
		return showList;
	}
      /**
       * 
       * 
       * 数据的生成和清空
       */
	@Override
	public int createInstanceBaseManagement(HttpServletRequest request){
		int returnInt =0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("pabDate",(String) paramMap.get("year1")+paramMap.get("month1"));//选择年月
		paramMap.put("leftDate",paramMap.get("leftDate"));//离职日期
		paramMap.put("adminID", admin.getCpnyId());//操作人
		try {
			returnInt=basemagementForSearcherDao.createDataInstancenBaseBz(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}
	/**
	 * 查找总记录数
	 */
	@Override
	public int getInsuranceBaseCnt(HttpServletRequest request) {
		 Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			
			return basemagementForSearcherDao.getInsuranceBaseCnt(paramMap) ;
	}
	/**
	 * 数据导出
	 * @throws SQLException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getInsBaseNumInfoExcel(HttpServletRequest request) throws SQLException {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		retrunList = basemagementForSearcherDao.getNOInsBaseNumList(paramMap) ;
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public int deleteInstanceaseManagement(HttpServletRequest request) {
	        LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
	        paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			try {
				String[] ids = request.getParameterValues("check");
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						paramMap.put("id", id);
						basemagementForSearcherDao.deleteInstanceaseManagement(paramMap);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			return 1;
	}
	/**
	 * 
	 * 获取社会保险中允许修改的字段
	 */
	@SuppressWarnings("unchecked")
	public int allowInstanceBaseNumUpdate(HttpServletRequest request){
		String ids=request.getParameter("ids");
		ids=ids.substring(0, ids.length()-1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ids", ids);
		try {
			basemagementForSearcherDao.allowInstanceBaseNumUpdate(paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 编辑字段
	 */
	@SuppressWarnings("unchecked")
	public int editInstanceBaseManagement(HttpServletRequest request) {
		String[] empIDs = request.getParameterValues("empID");
		String[] avgSalarys = request.getParameterValues("avgSalary");
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		for (int i = 0; i < empIDs.length; i++) {
			double avgSalary = 0;
			String empID = empIDs[i];
			if (avgSalarys[i] != null && !"".equals(avgSalarys[i])) {
				avgSalary = Double.parseDouble(avgSalarys[i]);
			}
			param.put("empID", empID);
			param.put("avgSalary", avgSalary);
			try {
				basemagementForSearcherDao.editInstanceBaseManagement(param);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		
		}
		return 1;
	}
	@SuppressWarnings("unchecked")
	public List getupdateInstanceBaseManagement(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		try {
			return basemagementForSearcherDao.getupdateInstanceBaseManagement(paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
