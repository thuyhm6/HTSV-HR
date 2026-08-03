package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;


public interface PaArSummaryManageSer {
	
	@SuppressWarnings("unchecked")
	public List getPaArSummaryForManageList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int updatePaArSummaryForManageInfo(HttpServletRequest request) ;

	public ModelMap getPaArSummaryForManageExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap);
}
