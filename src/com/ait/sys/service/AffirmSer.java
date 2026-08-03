package com.ait.sys.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
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
public interface AffirmSer {
	
	public Object getAffirmItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAffirmItemList(HttpServletRequest request,List typeList) ;
	
	public int getAffirmItemListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAffirmItemList_final(HttpServletRequest request,List typeList) ;
	
	public int getAffirmItemListCnt_final(HttpServletRequest request) ;
	
	public int updateAffirmItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeAll(HttpServletRequest request);
	
	public int getCodeListCntByParentCode(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getItemDetail(HttpServletRequest request,String personNo,String codeNo);
	
	@SuppressWarnings("unchecked")
	public List getItemDetail_final(HttpServletRequest request,String personNo,String codeNo);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorList(HttpServletRequest request)throws Exception ;
	
	public int updateAffirmInfo(HttpServletRequest request);
	
	public int deleteAffirmLevelInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDeptTreeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType_final(HttpServletRequest request);
	
	public int insertAffirmInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(HttpServletRequest request);
	
	public int deleteAffirmInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPidEidList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPidEidList2(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(HttpServletRequest request) ;
	
	public int getEmpIdListCnt(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public boolean updateaffirmAppoint(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List affirmAppointList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public Boolean cancleAppoint(HttpServletRequest request);
	
	public List getPersonCntByEmpid(HttpServletRequest request);
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)包含离职人员
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPidEidListFull(HttpServletRequest request) ;
	
	/**
	 * 最终确认批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmTempList(HttpServletRequest request) ;
	
	/**
	 * 最终确认批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getAffirmTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 最终确认批量excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelAffirmEmpData(HttpServletRequest request);
	
	public List getAffirmItemList_finalExcel(HttpServletRequest request);
}
