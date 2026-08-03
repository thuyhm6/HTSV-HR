package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusTypeDao.java
 * @Description:
 * @Create date: 2012-1-7 下午03:22:16
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusTypeDao {

	public int getBonusTypeNo();

	public Object getBonusTypeInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusTypeList(Object object);

	public int getBonusTypeCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusTypeList(Object object, int currentPage, int pageSize);

	public void addBonusTypeInfo(Object object) throws Exception;

	public void updateBonusTypeInfo(Object object) throws Exception;

	public void deleteBonusTypeInfo(Object object) throws Exception;

	public int checkDeleteBonusTypeInfo(Object obj);

	public int checkAddBonusTypeByTypeId(Object obj) throws SQLException;

	public Object getBonusTypeParamInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusTypeParamList(Object object);

	public int getBonusTypeParamCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusTypeParamList(Object object, int currentPage,
			int pageSize);

	public void addBonusTypeParamInfo(Object object) throws Exception;

	public void updateBonusTypeParamInfo(Object object) throws Exception;

	public void deleteBonusTypeParamInfo(Object object) throws Exception;
	
	public int checkAddBonusTypeParamInfo(Object obj) throws SQLException;
	
}
