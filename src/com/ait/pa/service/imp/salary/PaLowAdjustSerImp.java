package com.ait.pa.service.imp.salary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaLowAdjustDao;
import com.ait.pa.service.salary.PaLowAdjustSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.ReadFile;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

@Service
public class PaLowAdjustSerImp implements PaLowAdjustSer {

	Logger logger = Logger.getLogger(PaLowAdjustSerImp.class);
	
	@Autowired
	private PaLowAdjustDao paLowAdjustDao ;

	@Override
	public List getLowSalaryList(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paLowAdjustDao.getLowSalaryList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paLowAdjustDao.getLowSalaryList(paramMap) ;
		}
		return retrunList ;
	}

	@Override
	public int getLowSalaryListCnt(HttpServletRequest request) throws Exception {
		
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		retrunInt = paLowAdjustDao.getLowSalaryListCnt(paramMap) ;
		
		return retrunInt ;
	}

	@Override
	public int updateLowSalaryBatch(HttpServletRequest request) {
		
		int result = 0;
		
		try {
			// 批量封装代理发令数据并处理
			result = this.paLowAdjustDao.updateLowSalaryBatch(this
					.encapsulationLowSalaryBatch(request));
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 批量封装发令信息成List(encapsulation transaction from request to List for batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationLowSalaryBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String CPNY_ID = admin.getCpnyId();
			String[] paramData = request.getParameterValues("c1");
			
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
//				调整前最低工资标准，即系统原有值
				String ADJUST_BEF = paramMap.get(paramData[i] + "_ZUIDIGONGZIBIAOZHUN") != null ? paramMap.get(paramData[i] + "_ZUIDIGONGZIBIAOZHUN").toString(): "";
//				实际调整金额即为调整后金额
				String ADJUST_AFT = paramMap.get(paramData[i] + "_SHIJITIAOZHENGJINE") != null ? paramMap.get(paramData[i] + "_SHIJITIAOZHENGJINE").toString(): "";
				String CONTENT = paramMap.get(paramData[i] + "_CONTENT") != null ? paramMap.get(paramData[i] + "_CONTENT").toString(): "";
				String PA_MONTH = paramMap.get(paramData[i] + "_PA_MONTH") != null ? paramMap.get(paramData[i] + "_PA_MONTH").toString(): "";
				String QUFEN = paramMap.get(paramData[i] + "_QUFEN") != null ? paramMap.get(paramData[i] + "_QUFEN").toString(): "";
				map.put("PA_MONTH", PA_MONTH);
				map.put("CPNY_ID", CPNY_ID);
				map.put("PERSON_ID", paramData[i]);
				map.put("ADJUST_BEF", ADJUST_BEF);	
				map.put("ADJUST_AFT", ADJUST_AFT);
				map.put("CONTENT", CONTENT);
				map.put("QUFEN", QUFEN);
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 刷卡读取数据(get Card Interface data)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int cancellowadjust(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面参数
		String pa_month = request.getParameter("pa_month");
		String person_id = request.getParameter("person_id");
		String adjust_bef = request.getParameter("adjust_bef");
		String cpny_id = admin.getCpnyId();
		Map map = new LinkedHashMap();
		map.put("PA_MONTH", pa_month);
		map.put("PERSON_ID", person_id);
		map.put("ADJUST_BEF", adjust_bef);
		map.put("CPNY_ID", cpny_id);
		
		try {
			// 批量封装代理发令数据并处理
			this.paLowAdjustDao.cancellowadjust(map);
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	@Override
	public List getFeiCuLowSalaryList(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paLowAdjustDao.getFeiCuLowSalaryList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paLowAdjustDao.getFeiCuLowSalaryList(paramMap) ;
		}
		return retrunList ;
	}

	@Override
	public int getFeiCuLowSalaryListCnt(HttpServletRequest request) throws Exception {
		
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		retrunInt = paLowAdjustDao.getFeiCuLowSalaryListCnt(paramMap) ;
		
		return retrunInt ;
	}

	@Override
	public List getFeiCuLowSalaryForTAList(HttpServletRequest request)
			throws Exception {

		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paLowAdjustDao.getFeiCuLowSalaryForTAList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paLowAdjustDao.getFeiCuLowSalaryForTAList(paramMap) ;
		}
		return retrunList ;
	}

	@Override
	public List getLowSalaryForTAList(HttpServletRequest request)
			throws Exception {

		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paLowAdjustDao.getLowSalaryForTAList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paLowAdjustDao.getLowSalaryList(paramMap) ;
		}
		return retrunList ;
	}

	@Override
	public List getFeiCuLowSalaryForTRList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paLowAdjustDao.getFeiCuLowSalaryForTRList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paLowAdjustDao.getFeiCuLowSalaryForTRList(paramMap) ;
		}
		return retrunList ;
	}

	@Override
	public int getFeiCuLowSalaryListTRCnt(HttpServletRequest request) throws Exception {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH", paMonth);
		retrunInt = paLowAdjustDao.getFeiCuLowSalaryListTRCnt(paramMap) ;
		
		return retrunInt ;
	}

}
