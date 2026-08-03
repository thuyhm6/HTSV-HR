package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface PaCalculateDao {

	@SuppressWarnings("unchecked")
	public String paCalculate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public String paWithholdingCalculate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public String paWithholdingArCalculate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public int paWithholdingMonthlyStatusCnt(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public void insertpaWithholdingMonthlyStatus(Object parameterObject);
	
	@SuppressWarnings("unchecked")
	public List getpaWithholdingMonthlyStatusList(Object parameterObject);
	
	@SuppressWarnings("unchecked")
	public String paSalaryConfirm(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public String paSalaryTransfer(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public List getpaEmpGoup(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public String paSalaryTransferYuti(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public String paSalaryConfirmApply(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaCalculateTypeList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArStatisticList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaStatisticList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArStatisticPatransferList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public String getPaifClose(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public String getPaifCloseYuti(LinkedHashMap object) ;

	@SuppressWarnings("unchecked")
	public int getPaifClose2(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(Object object) ;
	@SuppressWarnings("unchecked")
	public List getDeptAreaListByHr(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(Object object);
	
	public int getCheckPaCalculateType(Object object) ;

	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 下午3:53:37 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryProvideDatePa(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public List getSalaryProvideDatePa2(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public void updatepaapplyCloseOpen(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public void updateisapplyCloseOpen(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public void updatepapaOpenFlag(LinkedHashMap object);
	@SuppressWarnings("unchecked")
	public String getpaapplyCloseOpen(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public String getpaapplyCloseOpenIsNo(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public String getpaapplyDEPT_Name(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public void updatepaWithholdingapplyCloseOpen(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public String getpaWithholdingapplyCloseOpen(LinkedHashMap object);
	
	@SuppressWarnings("unchecked")
	public Object getpaDepeNameAndEmpTypeName(LinkedHashMap object) throws SQLException;
	
	/**
	 * 
	 * 预提工资
	 *   营业员和促销员
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * 取营业员和促销员类型ID
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeAreaList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeAreaList(Object object) ;

	/** 
	* @Title: getPaConfrimMonth 
	* @Description: TODO PN工资确认页面 查看工资汇总项目
	* @param @param paramMap
	* @param @param pageNum
	* @param @param numPerPage
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getPaConfrimMonth(Map paramMap, int pageNum, int numPerPage);

	public int getPaConfrimMonthCnt(Map paramMap);

	public List getPaConfrimMonth(Map paramMap);
	
}
