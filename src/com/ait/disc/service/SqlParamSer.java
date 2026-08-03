package com.ait.disc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

public interface SqlParamSer {

	int ParamToJsp(HttpServletRequest request) throws Exception;
	public ModelMap writeExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception;


	/**
	 * @return
	 * @return
	 * @throws
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public ModelMap writeExcelFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception ;
}
