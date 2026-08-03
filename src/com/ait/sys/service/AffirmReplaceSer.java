package com.ait.sys.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/** 
* @ClassName: AffirmReplaceSer 
* @Description: TODO 决裁者替换与终止的service层接口
* @author 孙鹏
* @date 2014年12月1日 下午3:30:59 
*  
*/
public interface AffirmReplaceSer {
	public List getAffirmReplaceList(HttpServletRequest request) throws Exception;
	public int getAffirmReplaceCnt(HttpServletRequest request)throws Exception;
	public List getAffirmEmpList(HttpServletRequest request)throws Exception;
	public int getAffirmEmpCnt(HttpServletRequest request)throws Exception;
	public int insertAffirmReplace(HttpServletRequest request)throws Exception;
	public int deleteAffirmReplace(HttpServletRequest request)throws Exception;
	public int insertStopAffirm(HttpServletRequest request)throws Exception;
	int insertAffirmReplaceForALL(HttpServletRequest request) ;

	int deleteAffirmReplaceForAll(HttpServletRequest request) ;
	int insertStopAffirmForAll(HttpServletRequest request);

}
