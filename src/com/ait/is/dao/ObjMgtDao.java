package com.ait.is.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ObjMgtDao {

	public int getObjManagementCnt(Object obj);

	public List getObjManagementList(Object obj, int pageNum, int numPerPage);

	public List getObjManagementList(Object obj);

	public List getAllowPaBenObjInsureUpdate(Object obj);

	public void allowPaBenObjInsureUpdate(String id);

	public void updatePaBenManageAddInfoBz(Object obj);

	public List getNOInsStopNumList(Object obj);
}
