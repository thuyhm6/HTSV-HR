package com.ait.is.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.is.dao.AccumulationFundDao;
import com.ait.is.dao.AccumulationFundManageDao;
import com.ait.is.service.AccumulationFundManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class AccumulationFundSerManageImpl implements AccumulationFundManageSer {

	@Autowired
	private AccumulationFundManageDao accumulationFundManageDao;

	@Autowired
	private AccumulationFundDao accumulationFundDao;

	// 公积金--对象增加List
	@Override
	public List getPaBenBaseNumList(HttpServletRequest request)
			throws SQLException {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID();// 操作人
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		/*
		 * request.setAttribute("companyID", admin.getCompanyId());
		 * request.setAttribute("year1", request.getParameter("year1"));
		 * request.setAttribute("month1", request.getParameter("month1"));
		 * request.setAttribute("year2", request.getParameter("year2"));
		 * request.setAttribute("month2", request.getParameter("month2"));
		 * request.setAttribute("year3", request.getParameter("year3"));
		 * request.setAttribute("month3", request.getParameter("month3"));
		 */
		paramMap.put("companyID", admin.getCpnyId());
		paramMap.put("year1", request.getParameter("year1"));
		paramMap.put("month1", request.getParameter("month1"));
		paramMap.put("year2", request.getParameter("year2"));
		paramMap.put("month2", request.getParameter("month2"));
		paramMap.put("year3", request.getParameter("year3"));
		paramMap.put("month3", request.getParameter("month3"));

		String order = request.getParameter("order");// 排序区分
		StringBuffer sb = new StringBuffer("ORDER BY ");
		if ("upDept".equals(order)) {
			sb.append("E.DEPTID DESC");// 按部门降序排列
			request.setAttribute("dept", "desc");
		} else if ("downDept".equals(order)) {
			sb.append("E.DEPTID ASC");// 按部门升序排列
			request.setAttribute("dept", "asc");
		} else if ("upEmp".equals(order)) {
			sb.append("T.EMPID DESC");// 按职号降序排列
			request.setAttribute("emp", "desc");
		} else if ("downEmp".equals(order)) {
			sb.append("T.EMPID ASC");// 按职号升序排列
			request.setAttribute("emp", "asc");
		} else if ("upCoef".equals(order)) {
			sb.append("C1.CODE_ID DESC");// 按职系降序排列
			request.setAttribute("coef", "desc");
		} else if ("downCoef".equals(order)) {
			sb.append("C1.CODE_ID ASC");// 按职系升序排列
			request.setAttribute("coef", "asc");
		} else if ("upReg".equals(order)) {
			sb.append("C.CODE_ID DESC");// 按户口性质降序排列
			request.setAttribute("reg", "desc");
		} else if ("downReg".equals(order)) {
			sb.append("C.CODE_ID ASC");// 按户口性质升序排列
			request.setAttribute("reg", "asc");
		} else if ("upStatus".equals(order)) {
			sb.append("C2.CODE_ID DESC");// 按在职类型降序排列
			request.setAttribute("status", "desc");
		} else if ("downStatus".equals(order)) {
			sb.append("C2.CODE_ID ASC");// 按在职类型升序排列
			request.setAttribute("status", "asc");
		} else if ("upStart".equals(order)) {
			sb.append("E.DATE_STARTED DESC");// 按入社日期降序排列
			request.setAttribute("start", "desc");
		} else if ("downStart".equals(order)) {
			sb.append("E.DATE_STARTED ASC");// 按入社日期升序排列
			request.setAttribute("start", "asc");
		} else if ("upEnd".equals(order)) {
			sb.append("E.DATE_LEFT DESC");// 按离社日期降序排列
			request.setAttribute("end", "desc");
		} else if ("downEnd".equals(order)) {
			sb.append("E.DATE_LEFT ASC");// 按离社日期升序排列
			request.setAttribute("end", "asc");
		} else if ("upPay".equals(order)) {
			sb.append("T.PAY_SALARY DESC");// 按平均扣税工资降序排列
			request.setAttribute("pay", "desc");
		} else if ("downPay".equals(order)) {
			sb.append("T.PAY_SALARY ASC");// 按平均扣税工资升序排列
			request.setAttribute("pay", "asc");
		} else if ("upAvg".equals(order)) {
			sb.append("T.AVG_SALARY DESC");// 按年度基数降序排列
			request.setAttribute("avg", "desc");
		} else if ("downAvg".equals(order)) {
			sb.append("T.AVG_SALARY ASC");// 按年度基数升序排列
			request.setAttribute("avg", "asc");
		} else if ("upType".equals(order)) {
			sb.append("T.DATE_TYPE DESC");// 按数据生成状态降序排列
			request.setAttribute("type", "desc");
		} else if ("downType".equals(order)) {
			sb.append("T.DATE_TYPE ASC");// 按数据生成状态升序排列
			request.setAttribute("type", "asc");
		} else if ("upError".equals(order)) {
			sb.append("T.ERROR_REMARK DESC");// 按错误提示降序排列
			request.setAttribute("error", "desc");
		} else if ("downError".equals(order)) {
			sb.append("T.ERROR_REMARK ASC");// 按错误提示升序排列
			request.setAttribute("error", "asc");
		} else {
			sb.append("E.DEPTNO, T.EMPID ASC");// 默认按部门，职号升序排序
		}
		paramMap.put("order", sb);
		// paBenHsServices.backPaBenBaseNumUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
		List showList = accumulationFundManageDao.getPaBenBaseNumList(paramMap);// 显示信息集合
		return showList;
	}

	// 公积金--对象减少List
	@Override
	public List getviewCPFStopInsure(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID();// 操作人
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String order = request.getParameter("order");// 排序区分
		StringBuffer sb = new StringBuffer("ORDER BY ");
		if ("upDept".equals(order)) {
			sb.append("E.DEPTID DESC");// 按部门降序排列
			request.setAttribute("dept", "desc");
		} else if ("downDept".equals(order)) {
			sb.append("E.DEPTID ASC");// 按部门升序排列
			request.setAttribute("dept", "asc");
		} else if ("upEmp".equals(order)) {
			sb.append("T.EMPID DESC");// 按职号降序排列
			request.setAttribute("emp", "desc");
		} else if ("downEmp".equals(order)) {
			sb.append("T.EMPID ASC");// 按职号升序排列
			request.setAttribute("emp", "asc");
		} else if ("upStatus".equals(order)) {
			sb.append("C2.CODE_ID DESC");// 按在职类型降序排列
			request.setAttribute("status", "desc");
		} else if ("downStatus".equals(order)) {
			sb.append("C2.CODE_ID ASC");// 按在职类型升序排列
			request.setAttribute("status", "asc");
		} else if ("upStart".equals(order)) {
			sb.append("E.DATE_STARTED DESC");// 按入社日期降序排列
			request.setAttribute("start", "desc");
		} else if ("downStart".equals(order)) {
			sb.append("E.DATE_STARTED ASC");// 按入社日期升序排列
			request.setAttribute("start", "asc");
		} else if ("upEnd".equals(order)) {
			sb.append("E.DATE_LEFT DESC");// 按离社日期降序排列
			request.setAttribute("end", "desc");
		} else if ("downEnd".equals(order)) {
			sb.append("E.DATE_LEFT ASC");// 按离社日期升序排列
			request.setAttribute("end", "asc");
		} else if ("upStop".equals(order)) {
			sb.append("T.END_DATE DESC");// 按终止缴纳月降序排列
			request.setAttribute("stop", "desc");
		} else if ("downStop".equals(order)) {
			sb.append("T.END_DATE ASC");// 按终止缴纳月升序排列
			request.setAttribute("stop", "asc");
		} else if ("upType".equals(order)) {
			sb.append("T.DATE_TYPE DESC");// 按数据生成状态降序排列
			request.setAttribute("type", "desc");
		} else if ("downType".equals(order)) {
			sb.append("T.DATE_TYPE ASC");// 按数据生成状态升序排列
			request.setAttribute("type", "asc");
		} else if ("upError".equals(order)) {
			sb.append("T.ERROR_REMARK DESC");// 按错误提示降序排列
			request.setAttribute("error", "desc");
		} else if ("downError".equals(order)) {
			sb.append("T.ERROR_REMARK ASC");// 按错误提示升序排列
			request.setAttribute("error", "asc");
		} else {
			sb.append("E.DEPTNO, T.EMPID ASC");// 默认按部门，职号升序排序
		}

		paramMap.put("order", sb);
		List stopList = accumulationFundManageDao
				.getviewCPFStopInsure(paramMap);// 显示信息集合
		return stopList;
	}

	// 公积金--对象管理List
	@Override
	public List getviewBenshObjectManage(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID();// 操作人
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String order = request.getParameter("order");// 排序区分
		StringBuffer sb = new StringBuffer("ORDER BY ");
		if ("upDept".equals(order)) {
			sb.append("E.DEPTID DESC");// 按部门降序排列
			request.setAttribute("dept", "desc");
		} else if ("downDept".equals(order)) {
			sb.append("E.DEPTID ASC");// 按部门升序排列
			request.setAttribute("dept", "asc");
		} else if ("upEmp".equals(order)) {
			sb.append("T.EMPID DESC");// 按职号降序排列
			request.setAttribute("emp", "desc");
		} else if ("downEmp".equals(order)) {
			sb.append("T.EMPID ASC");// 按职号升序排列
			request.setAttribute("emp", "asc");
		} else if ("upStatus".equals(order)) {
			sb.append("C2.CODE_ID DESC");// 按在职类型降序排列
			request.setAttribute("status", "desc");
		} else if ("downStatus".equals(order)) {
			sb.append("C2.CODE_ID ASC");// 按在职类型升序排列
			request.setAttribute("status", "asc");
		} else if ("upStart".equals(order)) {
			sb.append("E.DATE_STARTED DESC");// 按入社日期降序排列
			request.setAttribute("start", "desc");
		} else if ("downStart".equals(order)) {
			sb.append("E.DATE_STARTED ASC");// 按入社日期升序排列
			request.setAttribute("start", "asc");
		} else if ("upEnd".equals(order)) {
			sb.append("E.DATE_LEFT DESC");// 按离社日期降序排列
			request.setAttribute("end", "desc");
		} else if ("downEnd".equals(order)) {
			sb.append("E.DATE_LEFT ASC");// 按离社日期升序排列
			request.setAttribute("end", "asc");
		} else if ("upStop".equals(order)) {
			sb.append("T.END_DATE DESC");// 按终止缴纳月降序排列
			request.setAttribute("stop", "desc");
		} else if ("downStop".equals(order)) {
			sb.append("T.END_DATE ASC");// 按终止缴纳月升序排列
			request.setAttribute("stop", "asc");
		} else if ("upType".equals(order)) {
			sb.append("T.DATE_TYPE DESC");// 按数据生成状态降序排列
			request.setAttribute("type", "desc");
		} else if ("downType".equals(order)) {
			sb.append("T.DATE_TYPE ASC");// 按数据生成状态升序排列
			request.setAttribute("type", "asc");
		} else if ("upError".equals(order)) {
			sb.append("T.ERROR_REMARK DESC");// 按错误提示降序排列
			request.setAttribute("error", "desc");
		} else if ("downError".equals(order)) {
			sb.append("T.ERROR_REMARK ASC");// 按错误提示升序排列
			request.setAttribute("error", "asc");
		} else {
			sb.append("E.DEPTNO, T.EMPID ASC");// 默认按部门，职号升序排序
		}

		paramMap.put("order", sb);
		List manageList = accumulationFundManageDao
				.getviewBenshObjectManage(paramMap);// 显示信息集合
		return manageList;
	}

	// 公积金--对象增加(检索) 数据生成+原数据清空
	@Override
	public int createDataToPaBenManageAddBz(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String str = "";
		str = (String) accumulationFundManageDao.getMaxMonthPaParamItem();// ("pa.benHs.getMaxMonthPaParamItem");
		paramMap.put("paMonth", str);
		try {
			accumulationFundManageDao.createDataToPaBenManageAddBz(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public void updatePaBenObjectMoveFlagBz(Object map,
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID();// 操作人

		((Map) map).put("adminID", adminID);

	}

	/****
	 * 把选中的（对象增加）的行，使其变成可修改状态
	 */
	@Override
	public int allowPaBenJoinInsureUpdate(HttpServletRequest request)
			throws Exception {
		String ids = request.getParameter("ids");
		ids = ids.substring(0, ids.length() - 1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ids", ids);
		try {
			accumulationFundManageDao.allowPaBenJoinInsureUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	public List getAllowPaBenJoinInsureUpdate(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		try {
			return accumulationFundManageDao
					.getAllowPaBenJoinInsureUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int allowBenshObjectManageNumUpdate(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		ids = ids.substring(0, ids.length() - 1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ids", ids);
		try {
			accumulationFundManageDao.allowBenshObjectManageNumUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int updatePaBenManageAddInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] empIDs = request.getParameterValues("empID");
		String[] avgSalarys = request.getParameterValues("avgSalary");
		Map param = ObjectBindUtil.getRequestParamData(request, "seach_");
		for (int i = 0; i < empIDs.length; i++) {
			double avgSalary = 0;
			String empID = empIDs[i];
			if (avgSalarys[i] != null && !"".equals(avgSalarys[i])) {
				avgSalary = Double.parseDouble(avgSalarys[i]);
			}
			param.put("PERSON_ID", empID);
			param.put("ENDOWMENT_BASE", avgSalary);
			param.put("joinValue", 1);
			param.put("yearMonth", "201305");
			param.put("adminID", admin.getAdminID());

			try {
				accumulationFundManageDao.updatePaBenManageAddInfo(param);
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}

		}
		return 1;
	}

	@Override
	public void updatePaBenObjectMoveFlagBz1(Object map,
			HttpServletRequest request) {

	}

	@Override
	public int allowPaBenStopInsureUpdate(HttpServletRequest request)
			throws Exception {
		String ids = request.getParameter("ids");
		ids = ids.substring(0, ids.length() - 1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ids", ids);
		try {
			accumulationFundManageDao.allowPaBenStopInsureUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List getAllowPaBenStopInsureUpdate(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		try {
			return accumulationFundManageDao
					.getAllowPaBenStopInsureUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updatePaBenManageStopInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] empIDs = request.getParameterValues("empID");
		String[] avgSalarys = request.getParameterValues("avgSalary");
		Map param = ObjectBindUtil.getRequestParamData(request, "seach_");
		for (int i = 0; i < empIDs.length; i++) {
			double avgSalary = 0;
			String empID = empIDs[i];
			if (avgSalarys[i] != null && !"".equals(avgSalarys[i])) {
				avgSalary = Double.parseDouble(avgSalarys[i]);
			}
			param.put("PERSON_ID", empID);
			param.put("ENDOWMENT_BASE", avgSalary);
			param.put("joinValue", 1);
			param.put("yearMonth", "201305");
			param.put("adminID", admin.getAdminID());

			try {
				accumulationFundManageDao.updatePaBenManageStopInfo(param);
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}

		}
		return 1;
	}

	@Override
	public int allowStopBenshObjectManageNumUpdate(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		ids = ids.substring(0, ids.length() - 1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ids", ids);
		try {
			accumulationFundManageDao
					.allowStopBenshObjectManageNumUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int AddBenshObjectManageNotUpdate(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		ids = ids.substring(0, ids.length() - 1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ids", ids);
		try {
			accumulationFundManageDao.AddBenshObjectManageNotUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int StopBenshObjectManageNotUpdate(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		ids = ids.substring(0, ids.length() - 1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ids", ids);
		try {
			accumulationFundManageDao.StopBenshObjectManageNotUpdate(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 公积金--对象增加 (删除)
	@Override
	public int deleteBenshObjectManageAdd(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		try {
			String[] ids = request.getParameterValues("checkid");
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					paramMap.put("id", id);
					accumulationFundManageDao
							.deleteBenshObjectManageAdd(paramMap);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 公积金--对象减少 (删除)
	@Override
	public int deleteBenshObjectManageDel(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		try {
			String[] ids = request.getParameterValues("checkid");

			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					paramMap.put("id", id);
					accumulationFundManageDao
							.deleteBenshObjectManageDel(paramMap);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 清空PA_BENHS_MANAGE_ADD_IMP
	public void deleteManageAddImp() {
		accumulationFundManageDao.deleteManageAddImp();
	}

	// 清空PA_BENHS_MANAGE_Del_IMP
	public void deleteManageDelImp() {
		accumulationFundManageDao.deleteManageDelImp();
	}
	
	// 清空PA_BENHS_MANAGE_IMP
	public void deleteManageImp() {
		accumulationFundManageDao.deleteManageImp();
	}

	// 查询所有的基数管理临时表数据
	public void getManageAddImplList(HttpServletRequest request) {
		List<Map> listImp = this.accumulationFundManageDao
				.getManageAddImplList();
		if (listImp != null && listImp.size() > 0) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String adminID = admin.getAdminID();// 操作人
			String cpnyId = admin.getCpnyId();// 操作人公司id

			for (int i = 0; i < listImp.size(); i++) {
				String empId = (String) listImp.get(i).get("PERSON_ID");
				String socialNo = (String) listImp.get(i).get("START_DATE");
				BigDecimal avgSalary = (BigDecimal) listImp.get(i).get(
						"ENDOWMENT_BASE");// 职号
				String Person_id = null;

				Map paramMap = ObjectBindUtil.getRequestParamData(request,
						"seach_");
				paramMap.put("CPNY_ID", cpnyId);
				paramMap.put("EMPID", empId);
				List listp = accumulationFundDao.findPIdByParam(paramMap);// 根据公司号和工号查找person_id
				if (listp.size() != 0 || listp != null) {
					Person_id = listp.toString().substring(12,
							listp.toString().length() - 2);
				}
				if (Person_id != null && !"".equals(Person_id)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("PERSON_ID", Person_id);
					tempMap.put("START_DATE", socialNo);
					tempMap.put("ENDOWMENT_BASE", avgSalary);

					List<Map> paBaseList = this.accumulationFundManageDao
							.checkManageAdd(tempMap);

					if (paBaseList != null && paBaseList.size() == 1) {
						this.accumulationFundManageDao.updateManageAdd(tempMap);
					} else {
						tempMap.put("CREATED_BY", adminID);
						tempMap.put("ACTIVITY", 1);
						tempMap.put("JOIN_VALUE", 1);
						tempMap.put("MOVE_FLAG", 1);
						this.accumulationFundManageDao.insertManageAdd(tempMap);
					}

				}
			}
		}

	}

	// 查询所有的对象减少临时表数据
	public void getManageDelImplList(HttpServletRequest request) {
		List<Map> listImp = this.accumulationFundManageDao
				.getManageDelImplList();
		if (listImp != null && listImp.size() > 0) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String adminID = admin.getAdminID();// 操作人
			String cpnyId = admin.getCpnyId();// 操作人公司id

			for (int i = 0; i < listImp.size(); i++) {
				String empId = (String) listImp.get(i).get("PERSON_ID");
				String socialNo = (String) listImp.get(i).get("END_DATE");
				BigDecimal avgSalary = (BigDecimal) listImp.get(i).get(
						"ENDOWMENT_BASE");// 职号
				String Person_id = null;

				Map paramMap = ObjectBindUtil.getRequestParamData(request,
						"seach_");
				paramMap.put("CPNY_ID", cpnyId);
				paramMap.put("EMPID", empId);
				List listp = accumulationFundDao.findPIdByParam(paramMap);// 根据公司号和工号查找person_id
				if (listp.size() != 0 || listp != null) {
					Person_id = listp.toString().substring(12,
							listp.toString().length() - 2);
				}
				if (Person_id != null && !"".equals(Person_id)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("PERSON_ID", Person_id);
					tempMap.put("END_DATE", socialNo);
					tempMap.put("ENDOWMENT_BASE", avgSalary);

					List<Map> paBaseList = this.accumulationFundManageDao
							.checkManageDel(tempMap);

					if (paBaseList != null && paBaseList.size() == 1) {
						this.accumulationFundManageDao.updateManageDel(tempMap);
					} else {
						tempMap.put("CREATED_BY", adminID);
						tempMap.put("ACTIVITY", 1);
						tempMap.put("JOIN_VALUE", 1);
						this.accumulationFundManageDao.insertManageDel(tempMap);
					}

				}
			}
		}

	}
	
	// 查询所有的对象管理临时表数据
		public void getManageImplList(HttpServletRequest request) {
			List<Map> listImp = this.accumulationFundManageDao
					.getManageImplList();
			if (listImp != null && listImp.size() > 0) {
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				String adminID = admin.getAdminID();// 操作人
				String cpnyId = admin.getCpnyId();// 操作人公司id

				for (int i = 0; i < listImp.size(); i++) {
					String empId = (String) listImp.get(i).get("PERSON_ID");
					String socialNo = (String) listImp.get(i).get("SOCIAL_NO");
					BigDecimal avgSalary = (BigDecimal) listImp.get(i).get(
							"ENDOWMENT_BASE");// 职号
					String Person_id = null;

					Map paramMap = ObjectBindUtil.getRequestParamData(request,
							"seach_");
					paramMap.put("CPNY_ID", cpnyId);
					paramMap.put("EMPID", empId);
					List listp = accumulationFundDao.findPIdByParam(paramMap);// 根据公司号和工号查找person_id
					if (listp.size() != 0 || listp != null) {
						Person_id = listp.toString().substring(12,
								listp.toString().length() - 2);
					}
					if (Person_id != null && !"".equals(Person_id)) {
						LinkedHashMap tempMap = new LinkedHashMap();
						tempMap.put("PERSON_ID", Person_id);
						tempMap.put("SOCIAL_NO", socialNo);
						tempMap.put("ENDOWMENT_BASE", avgSalary);

						List<Map> paBaseList = this.accumulationFundManageDao
								.checkManage(tempMap);

						if (paBaseList != null && paBaseList.size() == 1) {
							this.accumulationFundManageDao.updateManage(tempMap);
						} else {
							tempMap.put("CREATED_BY", adminID);
							tempMap.put("ACTIVITY", 1);
							tempMap.put("JOIN_VALUE", 1);
							this.accumulationFundManageDao.insertManage(tempMap);
						}

					}
				}
			}

		}


}
