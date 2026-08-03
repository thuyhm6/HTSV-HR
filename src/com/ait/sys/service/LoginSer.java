package com.ait.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:39
 * @version 5.0
 *
 */
public interface LoginSer {
	
	public String findUser(HttpServletRequest request,HttpServletResponse response);
	
	public String findUserChage(HttpServletRequest request,HttpServletResponse response);
	
	@SuppressWarnings("unchecked")
	public List getRoleGroupListByPersonId(HttpServletRequest request,HttpServletResponse response);
	
	@SuppressWarnings("unchecked")
	public List getLeftMenu(HttpServletRequest request,HttpServletResponse response);
	
	@SuppressWarnings("unchecked")
	public List getLeftMenuForPartner(HttpServletRequest request,HttpServletResponse response);
	
	public String getMenuLoad(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getTopMenu(HttpServletRequest request,HttpServletResponse response);
	
	@SuppressWarnings("unchecked")
	public List getTopMenuByRoleGroupId(HttpServletRequest request,HttpServletResponse response);
	
	@SuppressWarnings("unchecked")
	public List getDefaultMenu(HttpServletRequest request,HttpServletResponse response);
	
	@SuppressWarnings("unchecked")
	public List getProbationList(Object object);
	@SuppressWarnings("unchecked")
	public int getBecomeRegulerWarn(Object obj);
	@SuppressWarnings("unchecked")
	public List getUserpass(Object object);
	
	@SuppressWarnings("unchecked")
	public int getProbationCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getContractList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getContractCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getIdCardCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaNotImportCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getLeaveConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getOtConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getTempConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getStartedLeftConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getChangeDeptConfirmCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getPersonInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getHrCredentialList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getHrCredentialCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getUpgradeList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getUpgradeCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getHealthList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getHealthCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getBirthdayList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsurancePowerList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsurancePowerManList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLanguageList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getNotExistsContractList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getNotExistsContractCnt(Object object);
	
	public boolean checkPermission(HttpServletRequest request,String flag);
	
	@SuppressWarnings("unchecked")
	public List getAlertDaysList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getMenuMemoList(Object object);

	/** 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author    liangwei@ait.net.cn 
	* @date 2013-7-25 上午10:14:57 
	* @version V1.0   
	*/
	public Map getTitleName(HttpServletRequest request);
	
	/** 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author    lipeng@ait.net.cn 
	* @date 2017-9-12 下午14:55:57 
	* @version V1.0   
	*/
	public Map getTitleNamePartner(HttpServletRequest request);
	
	/** 
	 * 获取公告列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author    weizhengchen@ait.net.cn 
	*/
	@SuppressWarnings("unchecked")
	public List getNoticeList(Object object);
	//待决裁
	@SuppressWarnings("unchecked")
	public List viewApprovalInfo(HttpServletRequest request) throws Exception;
	//通知
	@SuppressWarnings("unchecked")
	public List viewNoticeedEmail(HttpServletRequest request) throws Exception;
	//考勤异常
	@SuppressWarnings("unchecked")
	public List viewAttendanceEx(HttpServletRequest request) throws Exception;
	//考勤员下批量考勤异常
	@SuppressWarnings("unchecked")
	public List viewAttendanceExCoor(HttpServletRequest request) throws Exception;
	//部门长下考勤异常
	@SuppressWarnings("unchecked")
	public List viewAttendanceExManagement(HttpServletRequest request) throws Exception;
	//部门长权限下查询考勤申请
	@SuppressWarnings("unchecked")
	public List viewAttendanceManagement(HttpServletRequest request) throws Exception;
	//部门长权限下查询加班申请
	@SuppressWarnings("unchecked")
	public List viewOTManagement(HttpServletRequest request) throws Exception;
	//加班和考勤申请信息
	@SuppressWarnings("unchecked")
	public List viewApplyList(HttpServletRequest request) throws Exception;
	//考勤员批量申请的考勤加班信息
	@SuppressWarnings("unchecked")
	public List viewApplyListCoor(HttpServletRequest request) throws Exception;
	
	public void updateUserData(HttpServletRequest request, String target);
}
