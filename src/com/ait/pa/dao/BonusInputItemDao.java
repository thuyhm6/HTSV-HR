package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemDao.java
 * @Description:
 * @Create date: 2012-1-9 上午10:12:45
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusInputItemDao {
	public void addPaBonusInputItemInfo(Object object) throws Exception;

	public int checkAddPaBonusInputItemInfo(Object object);

	public int checkCountPaBonusInputItemByParamItemNo(Object object);

	public void deletePaBonusInputItemInfo(Object object) throws Exception;

	public int getPaBonusInputItemCnt(Object object);

	public Object getPaBonusInputItemInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getPaBonusInputItemList(Object object);

	@SuppressWarnings("unchecked")
	public List getPaBonusInputItemList(Object object, int currentPage,
			int pageSize);

	public void updatePaBonusInputItemInfo(Object object) throws Exception;

}
