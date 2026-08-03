package com.ait.is.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.ContractInfoDao;
import com.ait.is.dao.InsuranceSystemCalculateDao;
import com.ait.is.service.InsuranceSystemCalculateSer;
import com.ait.org.dao.OrgManageDao;
import com.ait.org.service.OrgManageSer;




import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;


import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil1;
import com.ait.web.util.UiUtil;

@Service
public class InsuranceSystemCalculateSerImpl  implements InsuranceSystemCalculateSer {
	Logger logger = Logger.getLogger(InsuranceSystemCalculateSerImpl.class);
	@Autowired
	private InsuranceSystemCalculateDao insuranceSystemCalculateDao;
	
	@Autowired
	private LoginDao loginDao;
	
	//保险系统-基准管理
	@Override
	public List getInsuranceSystemInfoListForSearch(HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		retrunList=insuranceSystemCalculateDao.getInsuranceSystemInfoListForSearch(paramMap);
		return retrunList;
	}

	@Override
	public List getVersionDateListBz(HttpServletRequest request)
			throws SQLException {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		retrunList=insuranceSystemCalculateDao.getVersionDateListBz(paramMap);
		return retrunList;
	}

	@Override
	public List getModifyStandardSeriousBz(HttpServletRequest request)
			throws SQLException {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		retrunList=insuranceSystemCalculateDao.getModifyStandardSeriousBz(paramMap);
		
		return retrunList;
	}

	@Override
	public int ifUpdatedVersionBz(HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return insuranceSystemCalculateDao.ifUpdatedVersion(paramMap);
	}

	@Override
	public String getMaxManageCreateDateBz(HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return insuranceSystemCalculateDao.getMaxManageCreateDate(paramMap);
	}

	@Override
	public int selectPaBenFalgByFalgBz(Map map)
			throws SQLException {
		// TODO Auto-generated method stub
		
		return insuranceSystemCalculateDao.selectPaBenFalgByFalg(map);
	}

	@Override
	public int createBenchmarkStandardVersionBz(Map map) throws SQLException {
		// TODO Auto-generated method stub
		return insuranceSystemCalculateDao.createBenchmarkStandardVersionBz(map);
	}

	@Override
	public void freshPaBenManageBz(Map map) throws SQLException {
		// TODO Auto-generated method stub
		 insuranceSystemCalculateDao.freshPaBenManageBz(map);
	}

	@Override
	public int updateBenchmarkManagement(HttpServletRequest request)
			throws SQLException {
		Map map = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("adminID", admin.getAdminID());//操作人
		String[] insturanceCodes = request.getParameterValues("insturanceCode");
		String[] regTypeCodes = request.getParameterValues("regTypeCode");
		String[] upperLimits = request.getParameterValues("upperLimit");
		String[] lowerLimits = request.getParameterValues("lowerLimit");
		String[] corRates = request.getParameterValues("corRate");
		String[] perRates = request.getParameterValues("perRate");
		String[] corValues = request.getParameterValues("corValue");
		String[] perValues = request.getParameterValues("perValue");
		int result = insuranceSystemCalculateDao.deleteBenchmarkStandardBz(map);
		
		if (result == 1) {
			for(int i = 0; i < insturanceCodes.length; i++){
				String insturanceCode = insturanceCodes[i];
				String regTypeCode = regTypeCodes[i];
				double upperLimit = 0;
				if (upperLimits[i] != null && !"".equals(upperLimits[i])) {
					upperLimit = Double.parseDouble(upperLimits[i]);
				}
				double lowerLimit = 0;
				if (lowerLimits[i] != null && !"".equals(lowerLimits[i])) {
					lowerLimit = Double.parseDouble(lowerLimits[i]);
				}
				double corRate = 0;
				if (corRates[i] != null && !"".equals(corRates[i])) {
					corRate = Double.parseDouble(corRates[i]);
				}
				double perRate = 0;
				if (perRates[i] != null && !"".equals(perRates[i])) {
					perRate = Double.parseDouble(perRates[i]);
				}
				double corValue = 0;
				if (corValues[i] != null && !"".equals(corValues[i])) {
					corValue = Double.parseDouble(corValues[i]);
				}
				double perValue = 0;
				if (perValues[i] != null && !"".equals(perValues[i])) {
					perValue = Double.parseDouble(perValues[i]);
				}
				map.put("insturanceCode", insturanceCode);
				map.put("regTypeCode", regTypeCode);
				map.put("upperLimit", upperLimit);
				map.put("lowerLimit", lowerLimit);
				map.put("corRate", corRate);
				map.put("perRate", perRate);
				map.put("corValue", corValue);
				map.put("perValue", perValue);
				int result1 = insuranceSystemCalculateDao.updateBenchmarkStandardBz1(map);
			}
		}
		request.setAttribute("message", "修改成功！");
		return result;
	}

	@Override
	public List getPaBenStandardNotSeriousBz(HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		String searchDate = request.getParameter("SearchDate");// 版本日期
		
		
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		paramMap.put("versionDate", searchDate);
		retrunList=insuranceSystemCalculateDao.getPaBenStandardNotSeriousBz(paramMap);
		return retrunList;
	}

	@Override
	public List getPaBenStandardSeriousBz(HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		String searchDate = request.getParameter("SearchDate");// 版本日期
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("versionDate", searchDate);
		retrunList=insuranceSystemCalculateDao.getPaBenStandardSeriousBz(paramMap);
		return retrunList;
	}
	@Override
	public Map ViewBaseManagementForSearch(HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		String returnMessage="";
		int returnStatue=200;
		String searchDate = request.getParameter("SearchDate");// 版本日期
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		request.setAttribute("companyID", admin.getCpnyId());
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
		if ("create".equals(method)) {
			insuranceSystemCalculateDao.createDataToPaBenBaseBz(paramMap);
		}else if ("delete".equals(method)) {
			String[] ids = request.getParameterValues("check");
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					insuranceSystemCalculateDao.deletePaBenBaseWrongEmpBz(id);
				}
			}
			//request.setAttribute("message", "删除成功！");
			returnStatue=300;
			returnMessage=TipMessage.getTipMessage("alert.message.delete_success",request);//删除成功
		}else if ("save".equals(method)) {
			String[] empIDs = request.getParameterValues("empID");
			String[] avgSalarys = request.getParameterValues("avgSalary");
			for (int i = 0; i < empIDs.length; i++) {
				double avgSalary = 0;
				String empID = empIDs[i];
				if (avgSalarys[i] != null && !"".equals(avgSalarys[i])) {
					avgSalary = Double.parseDouble(avgSalarys[i]);
				}
				paramMap.put("empID", empID);
				paramMap.put("avgSalary", avgSalary);
				insuranceSystemCalculateDao.updatePaBenBaseAvgSalaryBz(paramMap);
			}
			//request.setAttribute("message", "修改成功！");
			returnStatue=300;
			returnMessage=TipMessage.getTipMessage("display.emp.statistics.mes191",request);//修改成功
			
		}else if ("order".equals(method)) {
			String yearMonth = insuranceSystemCalculateDao.getMaxYearMonthOfComputionBz(paramMap);
			if (yearMonth != null && !"".equals(yearMonth)) {
				paramMap.put("yearMonth", yearMonth);
				paramMap.put("paType", "PaApplyType1");
				String affirm = insuranceSystemCalculateDao.afterComputationAffirmBz(paramMap);
				if (affirm != null && "1".equals(affirm)) {//最大核算年月裁决通过
					//发令判断
					int orderCount = insuranceSystemCalculateDao.paBenBaseNumOrderCountBz();//发令人数 
					int orderTrueCount = insuranceSystemCalculateDao.paBenBaseNumTrueOrderCountBz();//实发影响人数
					request.setAttribute("orderCount", orderCount);
					request.setAttribute("orderTrueCount", orderTrueCount);
					if (orderCount > orderTrueCount) {//如果发令有剩余，则显示剩余人员信息
						List falseOrder = insuranceSystemCalculateDao.getPaBenBaseNumOrderListBz(paramMap);
						request.setAttribute("falseOrder", falseOrder);//未发令过去的人员信息
					}
					request.setAttribute("alertMsg", yearMonth.substring(0, 4)+"年"+yearMonth.substring(4, 6)+"月保险数据已经通过工资裁决，数据将发令到下个月！");
					String flag = request.getParameter("flag");//确定发令标识
					if ("Y".equals(flag)) {
						if (insuranceSystemCalculateDao.copyDataToPaBenManageBz(paramMap)) {
							request.setAttribute("message", "发令成功！");
						}else{
							request.setAttribute("message", "发令失败！");
						}
					}
				}else {
					request.setAttribute("message", yearMonth.substring(0, 4)+"年"+yearMonth.substring(4, 6)+"月数据核算后尚未裁决通过，不可发令！");
				}
			}else{
				//发令判断
				int orderCount = insuranceSystemCalculateDao.paBenBaseNumOrderCountBz();//发令人数 
				int orderTrueCount = insuranceSystemCalculateDao.paBenBaseNumTrueOrderCountBz();//实发影响人数
				request.setAttribute("orderCount", orderCount);
				request.setAttribute("orderTrueCount", orderTrueCount);
				if (orderCount > orderTrueCount) {//如果发令有剩余，则显示剩余人员信息
					List falseOrder = insuranceSystemCalculateDao.getPaBenBaseNumOrderListBz(paramMap);
					request.setAttribute("falseOrder", falseOrder);//未发令过去的人员信息
				}
				String flag = request.getParameter("flag");//确定发令标识
				if ("Y".equals(flag)) {
					if (insuranceSystemCalculateDao.copyDataToPaBenManageBz(paramMap)) {
						request.setAttribute("message", "发令成功！");
					}else{
						request.setAttribute("message", "发令失败！");
					}
				}
			}
			//return "pa/ben/baseNumOrder.jsp"; 
		}else if ("compute".equals(method)) {
			
			//对象生成条件设定 参照某月的社保
			paramMap.put("referMonth", request.getParameter("year1")+request.getParameter("month1"));
			//平均扣税工资计算期间 开始日期
			paramMap.put("startMonth", request.getParameter("year2")+request.getParameter("month2"));
			//平均扣税工资计算期间 结束日期
			paramMap.put("endMonth", request.getParameter("year3")+request.getParameter("month3"));
			//以前入社人员计算平均扣税工资
			paramMap.put("beforDate", request.getParameter("beforeDate"));
			//入社人员，计算第二个月税前工资。（广州适用）开始日
			paramMap.put("startDate", request.getParameter("startDate"));
			//入社人员，计算第二个月税前工资。（广州适用）结束日
			paramMap.put("endDate", request.getParameter("endDate"));
			//某日后入社人员，按入社基数计算
			paramMap.put("afterDate", request.getParameter("afterDate"));
			//companyID
			paramMap.put("companyID", admin.getAdminID());
			//输出message
			String message = "";
			paramMap.put("message", message);
			//调用存储
			message = StringUtil1.checkNull(insuranceSystemCalculateDao.callPaBenBaseCalBz(paramMap));
			//request.setAttribute("message", message);
			//CheckList 存储
			//SimpleMap param1 = new SimpleMap();
			String news = "";
			paramMap.put("news", news);
			paramMap.put("companyID", admin.getCpnyId());
			news = StringUtil1.checkNull(insuranceSystemCalculateDao.callPaBenBaseCheckCalBz(paramMap));
			//request.setAttribute("news", news);
		}else if ("detail".equals(method)) {
			String back = request.getParameter("back");
			if (back == null) {
				String type = request.getParameter("type");
				String typeName = request.getParameter("typeName");
				//SimpleMap maps = new SimpleMap();
				paramMap.put("type", type);
				
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
					sb.append("A.VALUE DESC");//按基数参照工资降序排列
					request.setAttribute("avg", "desc");
				}else if ("downAvg".equals(order)) {
					sb.append("A.VALUE ASC");//按基数参照工资升序排列
					request.setAttribute("avg", "asc");
				}else{
					sb.append("E.DEPTID, T.EMPID ASC");//默认按部门，职号升序排序
				}
				paramMap.put("detailOrder", sb);
				
				List detailList = insuranceSystemCalculateDao.getPaBenBaseNumCheckListDetailBz(paramMap);
				request.setAttribute("detailList", detailList);
				request.setAttribute("typeName", typeName);
				request.setAttribute("type", type);
				//return "pa/ben/pa_ben_benchmark_checklist_detail.jsp";
			}else{
				request.setAttribute("check", true);//显示CheckList条件
			}
		}
				//获取CheckList
				List checkList = insuranceSystemCalculateDao.getPaBenBaseNumCheckListBz();
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
					sb.append("E.DEPTID, T.EMPID ASC");//默认按部门，职号升序排序
				}
				Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				paramMap1.put("order", sb);
				paramMap1.put("interLanguage", admin.getLanguage());
				
				insuranceSystemCalculateDao.backPaBenBaseNumUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
				List showList = insuranceSystemCalculateDao.getPaBenBaseNumListBz(paramMap1);//显示信息集合
				
				if ("create".equals(method)) {
					if (showList == null || showList.size() == 0) {
						request.setAttribute("message", "没有符合条件的人员信息！");
					}
				}
				
				Map retunrMap=new HashMap<Object, Object>();
				retunrMap.put("list", showList);
				
//				int allDataCount = paBenServices.getAllPaBenBaseDataCountBz();//总计数量
//				int rightDataCount = paBenServices.getRightPaBenBaseDataCountBz();//正常数量
//				request.setAttribute("allDataCount", allDataCount);
//				request.setAttribute("rightDataCount", rightDataCount);
				request.setAttribute("showList", showList);
				//return "pa/ben/pa_ben_benchmark_basenum.jsp";
				return retunrMap;
		
	}
	
	
}
