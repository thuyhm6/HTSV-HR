package com.ait.pa.service.imp.workManagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.log4testng.Logger;

import com.ait.pa.dao.PaWorkerSalaryTableDao;
import com.ait.pa.service.workManagement.PaWorkerSalaryTableSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaWorkerSalaryTableSerImp implements PaWorkerSalaryTableSer {
	
	Logger logger = Logger.getLogger(PaWorkerSalaryTableSerImp.class);
	@Autowired
	private PaWorkerSalaryTableDao paWorkerSalaryTableDao;

	@Override
	public List paWorkerSalaryTableList(HttpServletRequest request) {
		List returnList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String paYear=request.getParameter("seach_paYear") == null ? year : request.getParameter("seach_paYear");
		String paMonth=request.getParameter("seach_paMonth") == null ? month : request.getParameter("seach_paMonth");
		
		paramMap.put("PA_MONTH", paYear+paMonth);
		paramMap.put("ACTIVITY", request.getParameter("seach_ACTIVITY"));
		returnList = paWorkerSalaryTableDao.paWorkerSalaryTableList(paramMap);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public int addPaWorkerSalaryTable(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		/*String[] paramData = request.getParameterValues("c1");*/
		this.paWorkerSalaryTableDao.updatePaWorkerSalaryTableAll(paramMap);
		for (int i = 1; i < 10; i++) {
			paramMap.put("DIVISION", request.getParameter("DIVISION_"+i));
			paramMap.put("BASIC", request.getParameter("BASIC_"+i));
			paramMap.put("FULL_ATTEND", request.getParameter("FULL_ATTEND_"+i));
			paramMap.put("TRANSPORTATION", request.getParameter("TRANSPORTATION_"+i));
			paramMap.put("LONG_ATTENDANCE", request.getParameter("LONG_ATTENDANCE_"+i));
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+i));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+i));
			
			this.paWorkerSalaryTableDao.addPaWorkerSalaryTable(paramMap);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int updatePaWorkerSalaryTable(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			paramMap.put("SEQ", request.getParameter("SEQ_"+paramData[i]));
			paramMap.put("BASIC", request.getParameter("BASIC_"+paramData[i]));
			paramMap.put("FULL_ATTEND", request.getParameter("FULL_ATTEND_"+paramData[i]));
			paramMap.put("TRANSPORTATION", request.getParameter("TRANSPORTATION_"+paramData[i]));
			paramMap.put("LONG_ATTENDANCE", request.getParameter("LONG_ATTENDANCE_"+paramData[i]));
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			
			this.paWorkerSalaryTableDao.updatePaWorkerSalaryTable(paramMap);
		}
		return 1;
	}

}
