package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusComputeItemDao.java
 * @Description:
 * @Create date: 2012-1-13 下午05:07:17
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusComputeItemDao {

	public Object getBonusComputeItemInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemList(Object object);

	public int getBonusComputeItemCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemList(Object object, int currentPage,
			int pageSize);

	public int checkAddBonusComputeItemInfo(Object object);

	public void addBonusComputeItemInfo(Object object) throws Exception;

	public void updateBonusComputeItemInfo(Object object) throws Exception;

	public int checkDeleteBonusComputeItemInfo(Object object);

	public void deleteBonusComputeItemInfo(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemNoParamList(Object object);

	public int getBonusComputeItemNoParamCnt(Object object);
}
