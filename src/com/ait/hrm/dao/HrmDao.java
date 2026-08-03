package com.ait.hrm.dao;

import java.util.List;

public interface HrmDao {
	public Object getDeptById(Object object);
	@SuppressWarnings("unchecked")
	public List getDeptTree(String sm,Object object);
	@SuppressWarnings("unchecked")
	public List getBusiness(Object object);
}
