package com.ait.pa.service.salary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaInputItemSer.java
 * @Description:
 * @Create date: 2012-1-19 下午07:51:50
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaInputItemSer {
	
	public Object getPaInputItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemList(HttpServletRequest request) ;
	
	public int getPaInputItemCnt(HttpServletRequest request);
	
	public int checkAddPaInputItemInfo(HttpServletRequest request);
	
	public int addPaInputItemInfo(HttpServletRequest request);
	
	public int addPaInputItemInfo(HttpServletRequest request,String item_no);
	
	public int updatePaInputItemInfo(HttpServletRequest request);
	
	public int checkDeletePaInputItemInfo(HttpServletRequest request) ;
	
	public int deletePaInputItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataList(HttpServletRequest request);
	
	public int createPaInputItemInfo(HttpServletRequest request) ;
	
	public int createAddPaInputItemDataInfo(HttpServletRequest request) ;
	
	public int updatePaInputItemDataInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAddPaInputItemDataList(HttpServletRequest request) ;
	
	public int addPaInputItemDataInfo(HttpServletRequest request);
	
	public int addPaInputItemOtherDataInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataTwoList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListByParamNo(HttpServletRequest request);
	
	public int getPaInputItemDataListByParamNoCnt(HttpServletRequest request);
	
	public Object getPaInputItemDataInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataPersonList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataPersonListNoPage(HttpServletRequest request);
	
	public int getPaInputItemDataPersonListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAddPaPersonalInputList(HttpServletRequest request);
	
	public int getAddPaPersonalInputListCnt(HttpServletRequest request);
	
	public int updatePaInputItemDataPersonInfo(HttpServletRequest request) throws Exception;
	
	public int deletePaInputItemDataBatchInfo(HttpServletRequest request);
	
	public int deletePaInputItemDataBatchInfoType(HttpServletRequest request);
	
	public int checkDeletePaInputItemDataInfo(HttpServletRequest request);
	
	public int checkDeletePaInputItemDataInfoType(HttpServletRequest request);
	
	public int deletePaInputItemDataInfo(HttpServletRequest request);
	
	public int deletePaInputItemDataInfoType(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(HttpServletRequest request) ;
	
	public int getEmpIdListCnt(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getAddPaPersonalInputItemList(HttpServletRequest request)throws Exception;

	@SuppressWarnings("unchecked")
	public List getPaInputItemPersonAllList(HttpServletRequest request)throws Exception;

	public int insertOrUpdatePaInputItemData(HttpServletRequest request)throws Exception;
}
