package com.ait.pa.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaComputeItemDao.java
 * @Description:
 * @Create date: 2012-1-16 下午06:45:36
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaComputeItemDao {

	public Object getPaComputeItemInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemList(Object object);
	
	public int getPaComputeItemCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemList(Object object, int currentPage, int pageSize);
	
	public int checkAddPaComputeItemInfo(Object object);
	
	public void addPaComputeItemInfo(Object object) throws Exception;
	
	public void addPaComputeItemInfoAffirm(Object object) throws Exception;
	
	public void updatePaComputeItemInfo(Object object) throws Exception;
	
	public int checkDeletePaComputeItemInfo(Object object) ;
	
	public void deletePaComputeItemInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updatePaComputeItemInfoCalOrder(List list) ;

	public int updatePCInfoByCalcuOrder(Object obj);

	public int updatePCInfoByParamNo(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemParamList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemParamListYN(Object object) ;
	@SuppressWarnings("unchecked")
	public List getEssPa(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaListYN(Object object) ;
	
//	public int getPaComputeItemParamListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemParamList(Object object, int currentPage, int pageSize);
	
	public int checkAddPaComputeItemParamInfo(Object object);
	
	public void addPaComputeItemParamInfo(Object object) throws Exception;
	
	public void updatePaComputeItemParam(Object object) throws Exception;
	
	public int upPaComputeItemParamView(Object object);
	
	public Object getPaComputeItemParamInfo(Object object);
	
	public int upPaComputeItemParamInfo(Object object);
	
	public int upPaComputeItemMappingInfo(Object object);
	
	public void deletePaComputeItemParamInfo(Object object) throws Exception;
	
	public int updatePaComputeItemParamInfoAll(String item_no) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPaItemListForFormula(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaItemListForDayFormula(Object object);
	
	public void addShowPaComputeItemParamList(Map paramMap) throws Exception;

	public List getPaComputeItemParamESSListYN(Object obj);

	public List getPaParamDateList(Object obj);

	public List getPaRemark(Map paramMap);
	
	/**
	 * 查Pn工资项目
	 * @param request
	 * @return
	 */
	public Object getLgepnPaInfo(Map obj) ;
	/**
	 * 查找PN输入项目的备注和具体数值
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLgepnPaParamInfo(Map object);

	/** 
	* @Title: getEmpTypeCodeForPersonId 
	* @Description: TODO 查询人员类型
	* @param @param paramMap
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getEmpTypeCodeForPersonId(Map paramMap);
}
