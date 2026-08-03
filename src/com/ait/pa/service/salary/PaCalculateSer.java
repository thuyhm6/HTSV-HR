package com.ait.pa.service.salary;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PaCalculateSer {
		
	public String paCalculate(HttpServletRequest request) ;
	
	public String paWithholdingCalculate(HttpServletRequest request) ;
	
	public String paWithholdingArCalculate(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public String paSalaryConfirm(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String paSalaryTransfer(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String paSalaryTransferYuti(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String paApplyclosed(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String paSalaryConfirmApply(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public List getPaCalculateTypeList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getArStatisticList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaStatisticList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getArStatisticPatransferList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDeptAreaListByHr(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String getPaifClose(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String getPaifCloseYuti(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int getPaifClose2(HttpServletRequest request);
	
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(HttpServletRequest request) ;
	
	public int getCheckPaCalculateType(HttpServletRequest request) ;

	/**
	 *  保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 下午3:50:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryProvideDatePa(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSalaryProvideDatePa2(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int paapplyCloseOpen(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String paapplyCloseOpenstr(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String isapplyCloseOpenstr(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String updatepapaOpenFlag(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public String paWithholdingapplyCloseOpenstr(HttpServletRequest request);
	
	/**
	 * 
	 * 预提工资
	 *   营业员和促销员
	 * 
	 * 
	 */
	
	/**
	 * 取营业员和促销员类型ID
	 */
	@SuppressWarnings("unchecked")
	public Map getJobTypeAreaList(HttpServletRequest request) ;

	/** 
	* @Title: getPaConfirmMonth 
	* @Description: TODO 查询汇总工资
	* @param @param request
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getPaConfirmMonth(HttpServletRequest request);

	public int getPaConfirmMonthCnt(HttpServletRequest request);
	
}
