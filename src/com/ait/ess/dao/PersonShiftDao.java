package com.ait.ess.dao;

import java.util.List;
import java.util.Map;


public interface PersonShiftDao {

	@SuppressWarnings("unchecked")
	List viewPersonShiftList(Object object, int pageNum, int numPerPage);

	@SuppressWarnings("unchecked")
	List viewPersonShiftList(Object object);

	int viewPersonShiftListCnt(Object object);

	List viewArShiftGroupList(Object object);

}
