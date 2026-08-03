package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemSer.java
 * @Description:
 * @Create date: 2012-1-14 下午02:12:37
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusInputItemSer {
	@SuppressWarnings("unchecked")
	public List getPaBonusInputItemList(HttpServletRequest request);

	public int getPaBonusInputItemCnt(HttpServletRequest request);

	public Object getPaBonusInputItemInfo(HttpServletRequest request);

	public int checkAddPaBonusInputItemInfo(HttpServletRequest request);

	public int checkCountPaBonusInputItemByParamItemNo(
			HttpServletRequest request);

	public int addPaBonusInputItemInfo(HttpServletRequest request);

	public int updatePaBonusInputItemInfo(HttpServletRequest request);

	public int deletePaBonusInputItemInfo(HttpServletRequest request);
}
