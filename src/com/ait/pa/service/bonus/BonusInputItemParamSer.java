package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemParamSer.java
 * @Description:
 * @Create date: 2012-1-14 下午02:32:10
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusInputItemParamSer {
	@SuppressWarnings("unchecked")
	public List viewBonusInputItemParamListForData(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List viewBonusInputItemParamList(HttpServletRequest request);

	public int getPaBonusInputItemParamCnt(HttpServletRequest request);

	public Object getPaBonusInputItemParamInfo(HttpServletRequest request);

	public int checkAddPaBonusInputItemParamInfo(HttpServletRequest request);

	public int addPaBonusInputItemParamInfo(HttpServletRequest request);

	public int updatePaBonusInputItemParamInfo(HttpServletRequest request);

	public int deletePaBonusInputItemParamInfo(HttpServletRequest request);

	public int checkDeletePaBonusInputItemParamInfo(HttpServletRequest request);

	public int checkPaBonusInputItemParamByParamItemNo(
			HttpServletRequest request);
}
