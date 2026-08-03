package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName AffirmSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:22
 * @version 5.0
 *
 */
public interface AffirmSpecialSer {
	
	public Object getAffirmSpecialItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAffirmSpecialItemList(HttpServletRequest request,List typeList) ;
	
	public int getAffirmSpecialItemListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAffirmSpecialItemList_special(HttpServletRequest request,List typeList) ;
	
	public int getAffirmSpecialItemListCnt_special(HttpServletRequest request) ;
	
	public int updateAffirmSpecialItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeAll(HttpServletRequest request);
	
	public int getCodeListCntByParentCode(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getItemDetail(HttpServletRequest request,String personNo,String codeNo);
	
	@SuppressWarnings("unchecked")
	public List getItemDetail_special(HttpServletRequest request,String personNo,String codeNo);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorSpecialList(HttpServletRequest request)throws Exception ;
	
	public int updateAffirmSpecialInfo(HttpServletRequest request);
	
	public int deleteAffirmSpecialLevelInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDeptTreeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType_special(HttpServletRequest request);
	
	public int insertAffirmSpecialInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(HttpServletRequest request);
	
	public int deleteAffirmSpecialInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPidEidList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPidEidList2(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(HttpServletRequest request) ;
	
	public int getEmpIdListCnt(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public boolean updateAffirmSpecialAppoint(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List affirmSpecialAppointList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public Boolean cancleAppoint(HttpServletRequest request);
	
	public List getPersonCntByEmpid(HttpServletRequest request);
	
	public List getAffirmItemList_specialExcel(HttpServletRequest request);

}
