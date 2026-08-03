package com.ait.is.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ObjMgtSer {

	public int getObjManagementCnt(HttpServletRequest request);

	public List getObjManagementList(HttpServletRequest request);

	public void allowPaBenObjInsureUpdateBz(String id);

	public List getAllowPaBenObjInsureUpdateBz(HttpServletRequest request);

	public int updatePaBenManageAddInfoBz(HttpServletRequest request);

	public List getInsObjNumInfoExcel(HttpServletRequest request);


}
