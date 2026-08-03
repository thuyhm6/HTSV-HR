package com.ait.is.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
public interface JoinInsureSer {


	public List getJoinInsureList(HttpServletRequest request) throws SQLException;

	public int getJoinInsureCnt(HttpServletRequest request);

	public int deleteJoinInsureInfo(HttpServletRequest request) throws SQLException;

	public void allowPaBenJoinInsureUpdateBz(String id);

	public List getAllowPaBenJoinInsureUpdateBz();

	public void backPaBenJoinInsureUpdateBz();

	public int updatePaBenManageAddInfoBz(HttpServletRequest request);

	public List getInsJoinNumInfoExcel(HttpServletRequest request);


}
