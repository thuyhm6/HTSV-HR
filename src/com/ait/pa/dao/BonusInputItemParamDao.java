package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemParamDao.java
 * @Description:
 * @Create date: 2012-1-16 上午09:52:23
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusInputItemParamDao {

	public void addPaBonusInputItemParamInfo(Object object) throws Exception;

	public int checkAddPaBonusInputItemParamInfo(Object object);

	public void deletePaBonusInputItemParamInfo(Object object) throws Exception;

	public int getPaBonusInputItemParamCnt(Object object);

	public Object getPaBonusInputItemParamInfo(Object object);

	@SuppressWarnings("unchecked")
	public List viewBonusInputItemParamList(Object object);

	@SuppressWarnings("unchecked")
	public List viewBonusInputItemParamList(Object object, int currentPage,
			int pageSize);

	public void updatePaBonusInputItemParamInfo(Object object) throws Exception;

	public int checkDeletePaBonusInputItemParamInfo(Object object);

	public int checkPaBonusInputItemParamByParamItemNo(Object object);
}
