package com.ait.pa.service.salary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaComputeItemSer.java
 * @Description:
 * @Create date: 2012-1-16 下午06:40:58
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaComputeItemSer {
	
	public Object getPaComputeItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemList(HttpServletRequest request) ;
	
	public int getPaComputeItemCnt(HttpServletRequest request);
	
	public int checkAddPaComputeItemInfo(HttpServletRequest request);
	
	public int addPaComputeItemInfo(HttpServletRequest request);
	
	public int addPaComputeItemInfo(HttpServletRequest request,String item_no);
	
	public int updatePaComputeItemInfo(HttpServletRequest request);
	
	public int checkDeletePaComputeItemInfo(HttpServletRequest request) ;
	
	public int deletePaComputeItemInfo(HttpServletRequest request);
	
	public int updatePaComputeItemInfoCalOrder(HttpServletRequest request);
	
	
	public int updatePCInfoByCalcuOrder(HttpServletRequest request,int type,String pa_item_no,String calcu_order);
	
	public int updatePCInfoByParamNo(HttpServletRequest request,int type,String ParamNo,String calcu_order);
	
	@SuppressWarnings("unchecked")
	public List getPaComputeItemParamList(HttpServletRequest request) ;
	
	public List getPaComputeItemParamListYN(HttpServletRequest request) ;
	public List getEssPa(HttpServletRequest request, List getArColumnsList, String arMonth,String str) ;//获取个人工资根据personid
	public int addShowPaComputeItemParamList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
    public List getPaMonthListYN(HttpServletRequest request, List list, String str);
	
//	public int getPaComputeItemParamListCnt(HttpServletRequest request);
	
	public int checkAddPaComputeItemParamInfo(HttpServletRequest request);
	
	public int checkAddPaComputeItemParamInfo(HttpServletRequest request,String cpny_id,String item_no);

	public int addPaComputeItemParamInfo(HttpServletRequest request);
	
	public int addPaComputeItemParamInfo(HttpServletRequest request ,String cpny_id,String item_no);
	
	public int updatePaComputeItemParamInfo(HttpServletRequest request ,String cpny_id,String item_no);
	
	public int upPaComputeItemParamView(HttpServletRequest request);
	
	public Object getPaComputeItemParamInfo(HttpServletRequest request) ;
	
	public int upPaComputeItemParamInfo(HttpServletRequest request);
	
	public int upPaComputeItemMappingInfo(HttpServletRequest request);
	
	public int deletePaComputeItemParamInfo(HttpServletRequest request);
	
	public int updatePaComputeItemParamInfoAll(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPaItemListForFormula(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaItemListForDayFormula(HttpServletRequest request) ;

	List getPaComputeItemParamESSListYN(HttpServletRequest request,String str) throws Exception;
	
	public List getPaParamDateList(HttpServletRequest request, String arMonth,
			String personId);

	public List getPaRemark(HttpServletRequest request, String arMonth,
			String personId);
	
	/**
	 * 查Pn工资项目
	 * @param request
	 * @return
	 */
	public Object getLgepnPaInfo(HttpServletRequest request) ;

	/** 
	* @Title: getEmpTypeCodeForPersonId 
	* @Description: TODO 查询人员类型
	* @param @param request
	* @param @param personId
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getEmpTypeCodeForPersonId(HttpServletRequest request,
			String personId);
}
