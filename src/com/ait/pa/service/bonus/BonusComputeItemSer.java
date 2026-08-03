package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusComputeItemSer.java
 * @Description:
 * @Create date: 2012-1-13 下午04:55:54
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusComputeItemSer {
	
	public Object getBonusComputeItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemList(HttpServletRequest request) ;
	
	public int getBonusComputeItemCnt(HttpServletRequest request);
	
	public int checkAddBonusComputeItemInfo(HttpServletRequest request);
	
	public int addBonusComputeItemInfo(HttpServletRequest request);
	
	public int updateBonusComputeItemInfo(HttpServletRequest request);
	
	public int checkDeleteBonusComputeItemInfo(HttpServletRequest request) ;
	
	public int deleteBonusComputeItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemNoParamList(HttpServletRequest request) ;
	
	public int getBonusComputeItemNoParamCnt(HttpServletRequest request);
}
