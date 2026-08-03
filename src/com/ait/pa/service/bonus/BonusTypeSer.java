package com.ait.pa.service.bonus;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusTypeSer.java
 * @Description:
 * @Create date: 2012-1-13 下午04:11:48
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusTypeSer {

	public Object getBonusTypeInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getBonusTypeList(HttpServletRequest request);

	public int getBonusTypeCnt(HttpServletRequest request);

	public int addBonusTypeInfo(HttpServletRequest request);

	public int updateBonusTypeInfo(HttpServletRequest request);

	public int deleteBonusTypeInfo(HttpServletRequest request);

	public int checkDeleteBonusTypeInfo(HttpServletRequest request);

	public int checkAddBonusTypeByTypeId(HttpServletRequest request);

	public Object getBonusTypeParamInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getBonusTypeParamList(HttpServletRequest request);

	public int getBonusTypeParamCnt(HttpServletRequest request);

	public int addBonusTypeParamInfo(HttpServletRequest request);

	public int updateBonusTypeParamInfo(HttpServletRequest request);

	public int deleteBonusTypeParamInfo(HttpServletRequest request);
	
	public int checkAddBonusTypeParamInfo(HttpServletRequest request);
}
