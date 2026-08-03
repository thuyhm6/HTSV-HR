package com.ait.is.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface JoinInsureDao {

	public List getPaBenJoinInsureListBz(Object object) throws SQLException;

	public List getPaBenJoinInsureListBz(Object object, int pageNum,int numPerPage);

	public int getJoinInsureCnt(Object object);

	public void deleteJoinInsureInfo(Object object) throws SQLException;

	public void allowPaBenJoinInsureUpdate(String id);

	public List getAllowPaBenJoinInsureUpdate();

	public void backPaBenJoinInsureUpdateBz();

	public void updatePaBenManageAddInfoBz(Object object);

	public List getNOInsJoinNumList(Object object);
	/*----------------------------------------------------------------------*/

	public List findPIdByParam(Object object);

	public List<Map> checkPaBenhsJoin(Object object);

	public void updatePaBenhsJoin(Object object);

	public void insertPaBenhsJoin(Object object);

	public List<Map> getPaJoinImplList();

	public void deletePaImp();


}
