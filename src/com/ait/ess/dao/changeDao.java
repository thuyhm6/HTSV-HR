package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface changeDao {
	

	@SuppressWarnings("unchecked")
	public void changePassword(Object object)throws Exception;
	public Object getPersonalInfoByPid(Object object);

}
