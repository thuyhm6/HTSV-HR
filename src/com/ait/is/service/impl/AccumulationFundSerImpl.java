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


import com.ait.is.dao.AccumulationFundDao;
import com.ait.is.service.AccumulationFundSer;


import com.ait.sys.bean.AdminBean;

import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class AccumulationFundSerImpl implements AccumulationFundSer{

	@Autowired
	private AccumulationFundDao accumulationFundDao;
	//公积金 --基准管理
	@Override
	public List getCPFBenchmarkManagementForSearch(HttpServletRequest request)
			throws SQLException {
		List retrunList = new ArrayList() ;
		
		String searchDate = request.getParameter("SearchDate");// 版本日期
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		
		paramMap.put("versionDate", searchDate);
		

		retrunList=accumulationFundDao.getCPFBenchmarkManagementForSearch(paramMap);
		return retrunList;
	}
	//公积金 --基准管理[版本信息]
	@Override
	public List getVersionDateListBz(HttpServletRequest request)
			throws SQLException {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return accumulationFundDao.getVersionDateListBz(paramMap);
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
		int result = accumulationFundDao.deleteBenchmarkStandardBz(map);
		
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
				int result1 = accumulationFundDao.updateBenchmarkStandardBz(map);
			}
		}
		request.setAttribute("message", "修改成功！");
		return result;
	}
	//公积金--基准管理（当前）
	@Override
	public List getCPFBenchmarkManagementForSearchDq(HttpServletRequest request)
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
		
		
	
		

		retrunList=accumulationFundDao.getCPFBenchmarkManagementForSearchDq(paramMap);
		return retrunList;
	}
	//查询条数  是否满足5
	@Override
	public int ifUpdatedVersionBz(HttpServletRequest request)
			throws SQLException {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		return accumulationFundDao.ifUpdatedVersion(paramMap);
	}
	
	//对象最大年月
	@Override
	public String getMaxManageCreateDate(HttpServletRequest request)
			throws SQLException {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return accumulationFundDao.getMaxManageCreateDate(paramMap);
	}
	//是否已经核算
	@Override
	public int selectPaBenFalgByFalg(Map map)
			throws SQLException {
		// TODO Auto-generated method stub
		
		return accumulationFundDao.selectPaBenFalgByFalg(map);
	}
	//生成版本
	@Override
	public int createBenchmarkStandardVersion(Map map) throws SQLException {
		// TODO Auto-generated method stub
		return accumulationFundDao.createBenchmarkStandardVersion(map);
	}
	
	//刷新上下线
	@Override
	public void freshPaBenManage(Map map) throws SQLException {
		// TODO Auto-generated method stub
		accumulationFundDao.freshPaBenManage(map);
	}
	//公积金--基数管理
	@Override
	public List ViewCPFBaseManagementForSearch(HttpServletRequest request)
				throws SQLException {
					
			
			//PaBenHsServices paBenHsServices = PaBenHsServices.getInstance();
			//String menu_code=StringUtil.checkNull(request.getParameter("menu_code"));
			//request.setAttribute("menu_code", menu_code);
			
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
			
			//if ("create".equals(method)) {
				//accumulationFundDao.createDataToPaBenBaseBz(paramMap);}
			/*else if ("delete".equals(method)) {
				String[] ids = request.getParameterValues("check");
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						paBenHsServices.deletePaBenBaseWrongEmpBz(id);
					}
				}
				request.setAttribute("message", "删除成功！");
			}else if ("update".equals(method)) {
				String[] ids = request.getParameterValues("check");
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						paBenHsServices.allowPaBenBaseNumUpdateBz(id);
					}
				}
				List list = paBenHsServices.getAllowPaBenBaseNumUpdateBz();//预修改的信息集合
				request.setAttribute("allowUpdate", list);
				return "pa/benHs/pa_benHs_basenum_update.jsp";
			}else if ("save".equals(method)) {
				String[] empIDs = request.getParameterValues("empID");
				String[] avgSalarys = request.getParameterValues("avgSalary");
				SimpleMap param = new SimpleMap();
				for (int i = 0; i < empIDs.length; i++) {
					double avgSalary = 0;
					String empID = empIDs[i];
					if (avgSalarys[i] != null && !"".equals(avgSalarys[i])) {
						avgSalary = Double.parseDouble(avgSalarys[i]);
					}
					param.put("empID", empID);
					param.put("avgSalary", avgSalary);
					paBenHsServices.updatePaBenBaseAvgSalaryBz(param);
				}
				request.setAttribute("message", "修改成功！");
			}else if ("order".equals(method)) {
				String yearMonth = paBenHsServices.getMaxYearMonthOfComputionBz(map);
				if (yearMonth != null && !"".equals(yearMonth)) {
					map.put("yearMonth", yearMonth);
					map.put("paType", "PaApplyType1");
					String affirm = paBenHsServices.afterComputationAffirmBz(map);
					if (affirm != null && "1".equals(affirm)) {//最大核算年月裁决通过
						int orderCount = paBenHsServices.paBenBaseNumOrderCountBz();//发令人数 
						int orderTrueCount = paBenHsServices.paBenBaseNumTrueOrderCountBz();//实发影响人数
						request.setAttribute("orderCount", orderCount);
						request.setAttribute("orderTrueCount", orderTrueCount);
						if (orderCount > orderTrueCount) {//如果发令有剩余，则显示剩余人员信息
							List falseOrder = paBenHsServices.getPaBenBaseNumOrderListBz(map);
							request.setAttribute("falseOrder", falseOrder);//违法令过去的人员信息
						}
						request.setAttribute("alertMsg", yearMonth.substring(0, 4)+"年"+yearMonth.substring(4, 6)+"月公积金数据已经通过工资裁决，数据将发令到下个月！");
						String flag = request.getParameter("flag");//确定发令标识
						if ("Y".equals(flag)) {
							if (paBenHsServices.copyDataToPaBenManageBz(map)) {
								request.setAttribute("message", "发令成功！");
							}else{
								request.setAttribute("message", "发令失败！");
							}
						}
					}else {
						request.setAttribute("message", yearMonth.substring(0, 4)+"年"+yearMonth.substring(4, 6)+"月数据核算后尚未裁决通过，不可发令！");
					}
				}else{
					int orderCount = paBenHsServices.paBenBaseNumOrderCountBz();//发令人数 
					int orderTrueCount = paBenHsServices.paBenBaseNumTrueOrderCountBz();//实发影响人数
					request.setAttribute("orderCount", orderCount);
					request.setAttribute("orderTrueCount", orderTrueCount);
					if (orderCount > orderTrueCount) {//如果发令有剩余，则显示剩余人员信息
						List falseOrder = paBenHsServices.getPaBenBaseNumOrderListBz(map);
						request.setAttribute("falseOrder", falseOrder);//违法令过去的人员信息
					}
					String flag = request.getParameter("flag");//确定发令标识
					if ("Y".equals(flag)) {
						if (paBenHsServices.copyDataToPaBenManageBz(map)) {
							request.setAttribute("message", "发令成功！");
						}else{
							request.setAttribute("message", "发令失败！");
						}
					}
				}
				return "pa/benHs/baseNumOrder.jsp"; 
			}else if ("compute".equals(method)) {
				SimpleMap param = new SimpleMap();
				//对象生成条件设定 参照某月的社保
				param.put("referMonth", request.getParameter("year1")+request.getParameter("month1"));
				//平均扣税工资计算期间 开始日期
				param.put("startMonth", request.getParameter("year2")+request.getParameter("month2"));
				//平均扣税工资计算期间 结束日期
				param.put("endMonth", request.getParameter("year3")+request.getParameter("month3"));
				//以前入社人员计算平均扣税工资
				param.put("beforDate", request.getParameter("beforeDate"));
				//入社人员，计算第二个月税前工资。（广州适用）开始日
				param.put("startDate", request.getParameter("startDate"));
				//入社人员，计算第二个月税前工资。（广州适用）结束日
				param.put("endDate", request.getParameter("endDate"));
				//某日后入社人员，按入社基数计算
				param.put("afterDate", request.getParameter("afterDate"));
				//companyID
				param.put("companyID", admin.getCompanyId());
				//输出message
				String message = "";
				param.setString("message", message);
				//调用存储
				message = StringUtil.checkNull(paBenHsServices.callPaBenBaseCalBz(param));
				//CheckList 存储
				SimpleMap param1 = new SimpleMap();
				String news = "";
				param1.setString("news", news);
				param1.put("companyID", admin.getCompanyId());
				news = StringUtil.checkNull(paBenHsServices.callPaBenBaseCheckCalBz(param1));
			}else if ("detail".equals(method)) {
				String back = request.getParameter("back");
				if (back == null) {
					String type = request.getParameter("type");
					String typeName = request.getParameter("typeName");
					SimpleMap maps = new SimpleMap();
					maps.put("type", type);
					
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
					maps.put("detailOrder", sb);
					
					List detailList = paBenHsServices.getPaBenBaseNumCheckListDetailBz(maps);
					request.setAttribute("detailList", detailList);
					request.setAttribute("typeName", typeName);
					request.setAttribute("type", type);
					return "pa/benHs/pa_benHs_benchmark_checklist_detail.jsp";
				}else{
					request.setAttribute("check", true);//显示CheckList条件
				}
			}*/
			//获取CheckList
			List checkList = accumulationFundDao.getPaBenBaseNumCheckListBz();
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
			Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap1.put("order", sb);
			paramMap1.put("interLanguage", admin.getLanguage());
			
			
			accumulationFundDao.backPaBenBaseNumUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
			List showList = accumulationFundDao.getPaBenBaseNumListBz(paramMap1);//显示信息集合
	//		int allDataCount = paBenHsServices.getAllPaBenBaseDataCountBz();//总计数量
	//		int rightDataCount = paBenHsServices.getRightPaBenBaseDataCountBz();//正常数量
	//		request.setAttribute("allDataCount", allDataCount);
	//		request.setAttribute("rightDataCount", rightDataCount);
			
			//return "pa/benHs/pa_benHs_benchmark_basenum.jsp";
			return showList;
		}
	// 数据生成+原数据清空
	@Override
	public int createDataToPaBenBaseBz(HttpServletRequest request) {
		int returnInt =0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("pabDate",(String) paramMap.get("year1")+paramMap.get("month1"));//选择年月
		paramMap.put("leftDate",paramMap.get("leftDate"));//离职日期
		paramMap.put("adminID", admin.getCpnyId());//操作人
		try {
			returnInt=accumulationFundDao.createDataToPaBenBaseBz(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}
	//公积金--基数管理 (删除)
	@Override
	public int deleteCPFBaseManagement(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		try {
			String[] ids = request.getParameterValues("checkid");
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					paramMap.put("id", id);
					accumulationFundDao.deleteCPFBaseManagement(paramMap);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		
		return 1;
	}
	//通过ids 将选中的信息属性 修改为Y
	@Override
	public int allowPaBenBaseNumUpdate(HttpServletRequest request) {
		String ids=request.getParameter("ids");
		ids=ids.substring(0, ids.length()-1);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ids", ids);
		try {
			accumulationFundDao.allowPaBenBaseNumUpdate(paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	//公积金--基数管理 (修改)查找要修改的数据List
	@Override
	public List getupdateCPFBaseManagement(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		try {
			return accumulationFundDao.getupdateCPFBaseManagement(paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	//公积金--基数管理 (修改保存)
	@Override
	public int editCPFBaseManagement(HttpServletRequest request) {
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
				accumulationFundDao.updatePaBenBaseAvgSalary(param);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		
		}
		return 1;
	}
	@Override
	public String computeCPFBaseManagement(HttpServletRequest request) {
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//对象生成条件设定 参照某月的社保
		param.put("referMonth", param.get("year1").toString()+param.get("month1").toString());
		//平均扣税工资计算期间 开始日期
		param.put("startMonth", param.get("year2").toString()+param.get("month2").toString());
		//平均扣税工资计算期间 结束日期
		param.put("endMonth", param.get("year3").toString()+param.get("month3").toString());
		/*//以前入社人员计算平均扣税工资
		param.put("beforDate", param.get("beforeDate").toString());
		//入社人员，计算第二个月税前工资。（广州适用）开始日
		param.put("startDate", param.get("startDate").toString());
		//入社人员，计算第二个月税前工资。（广州适用）结束日
		param.put("endDate", param.get("endDate").toString());
		
		//某日后入社人员，按入社基数计算
		param.put("afterDate", param.get("afterDate").toString());*/
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//CPNYID
		param.put("companyID", admin.getCpnyId());
		//输出message
		String message = "";
		//调用存储
		message = accumulationFundDao.callPaBenBaseCal(param);
		//CheckList 存储
		
		String news = "";
		
		news = accumulationFundDao.callPaBenBaseCheckCal(param);
		return news;
	}
	//查找最大月
	@Override
	public String getMaxYearMonthOfComputionBz(HttpServletRequest request) {
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String returnString="";
		try {
			returnString= accumulationFundDao.getMaxYearMonthOfComputionBz(param);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return returnString;
	}
	//判断核算后的申请裁决情况
	@Override
	public String afterComputationAffirmBz(HttpServletRequest request) {
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		param.put("yearMonth", request.getParameter("yearMonth"));
		param.put("paType", request.getParameter("paType"));
		String returnString="";
		try {
			returnString= accumulationFundDao.afterComputationAffirmBz(param);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return returnString;
	}
	//发令人数 
	@Override
	public int paBenBaseNumOrderCountBz() {
		int result=0;
		try {
			result= accumulationFundDao.paBenBaseNumOrderCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return result;
	}
	//实际发令影响人数
	@Override
	public int paBenBaseNumTrueOrderCountBz() {
		int result=0;
		try {
			result= accumulationFundDao.paBenBaseNumTrueOrderCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return result;
	}
	//如果发令有剩余，则显示剩余人员信息
	@Override
	public List getPaBenBaseNumOrderListBz(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		try {
			return accumulationFundDao.getPaBenBaseNumOrderList(paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	//公积金 --发令--发令
	@Override
	public int copyDataToPaBenManageBz(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String yearMonth = this.getMaxYearMonthOfComputionBz(request);//查询最大月
		List<Map> list = accumulationFundDao.getEmpidFromBaseAndManage(paramMap);//commonSQLMapAdapter.executeQueryForMulti("pa.benHs.getEmpidFromBaseAndManage");
		if (list != null && list.size() > 0) {
			String adminID = admin.getAdminID();//操作人
			for (int i = 0; i < list.size(); i++) {
				String empID = (String) list.get(i).get("PERSON_ID");//职号
				String asy = list.get(i).get("AVG_SALARY")!=null?list.get(i).get("AVG_SALARY").toString():null;
				String seq =  (String) list.get(i).get("PA_BENHS_MANAGE_SEQ");//序列
				double avgSalary = 0;
				if (asy != null) {
					avgSalary = Double.parseDouble(asy);
				}
				paramMap.put("empID", empID);
				paramMap.put("avgSalary", avgSalary);
				paramMap.put("adminID", adminID);
				paramMap.put("seq", seq);
				paramMap.put("yearMonth", yearMonth);
				//发令
				 accumulationFundDao.orderUpdatePaBenManageAvgSalary(paramMap);
			}
			//message = true;
			//发令成功后，清空base表
			accumulationFundDao.deletePaBenBase();
		}
		
		return 1;
	}
	
	//清空PA_BENHS_BASE_IMP
	public void deletePaBenImp() {
		accumulationFundDao.deletePaBenBaseImp();
	}
	
	// 查询所有的基数管理临时表数据
	public void getPaBehsBaseImplList(HttpServletRequest request) {
		List<Map> listImp = this.accumulationFundDao.getPaBehsBaseImplList();
		if (listImp != null && listImp.size() > 0) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String adminID = admin.getAdminID();// 操作人
			String cpnyId = admin.getCpnyId();// 操作人公司id

			for (int i = 0; i < listImp.size(); i++) {
				String empId = (String) listImp.get(i).get("PERSON_ID");
				String socialNo = (String) listImp.get(i).get("SOCIAL_NO");
				BigDecimal avgSalary = (BigDecimal) listImp.get(i).get(
						"AVG_SALARY");// 职号
				String Person_id = null;

				Map paramMap = ObjectBindUtil.getRequestParamData(request,
						"seach_");
				paramMap.put("CPNY_ID", cpnyId);
				paramMap.put("EMPID", empId);
				List listp = accumulationFundDao.findPIdByParam(paramMap);// 根据公司号和工号查找person_id
				if (listp.size() != 0 || listp != null) {
					Person_id = listp.toString().substring(12,listp.toString().length()-2);
				}
				if (Person_id != null && !"".equals(Person_id)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("PERSON_ID", Person_id);
					tempMap.put("SOCIAL_NO", socialNo);
					tempMap.put("AVG_SALARY", avgSalary);

					List<Map> paBaseList = this.accumulationFundDao
							.checkPaBenhsBase(tempMap);

					if (paBaseList != null && paBaseList.size() == 1) {
						this.accumulationFundDao.updatePaBenhsBase(tempMap);
					} else {
						tempMap.put("CREATED_BY", adminID);
						tempMap.put("ACTIVITY", 1);
						tempMap.put("SOCIAL_STATUS", 1);
						this.accumulationFundDao.insertPaBenhsBase(tempMap);
					}

				}
			}
		}

	}

}

