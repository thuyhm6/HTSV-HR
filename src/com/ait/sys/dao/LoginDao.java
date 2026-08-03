package com.ait.sys.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:24
 * @version 5.0
 *
 */
public interface LoginDao {
	public Object findUser(Object object);
	
	public Object findUserSpecial(Object object);
	
	public Object findCpnyIdByUser(Object object);
	
	public Object findPasswordByUser(Object object);
	@SuppressWarnings("unchecked")
	public List getRoleGroupListByPersonId(Object object);
	@SuppressWarnings("unchecked")
	public List getLeftMenu(Object object);
	@SuppressWarnings("unchecked")
	public List getLeftMenuForPartner(Object object);
	
	public String getMenuLoad(Object object);
	
	public void addLoginInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLimitsMap(Object object);
	
	@SuppressWarnings("unchecked")
	public List getTopMenu(Object object);
	
	@SuppressWarnings("unchecked")
	public List getTopMenuByRoleGroupId(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDefaultMenu(Object object);
	
	@SuppressWarnings("unchecked")
	public List getProbationList(Object object);
	@SuppressWarnings("unchecked")
	public int getBecomeRegulerWarn(Object object);
	
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
	public int getSickLeaveProofConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getOtConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getArExConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getTempConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getStartedLeftConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getChangeDeptConfirmCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getIsCanInHub(Object object);
	
	@SuppressWarnings("unchecked")
	public int getIsCanInHubByIP(Object object);
	
	@SuppressWarnings("unchecked")
	public int getNotExistsContractCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getHrCredentialList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPersonInfo(Object object);
	
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
	public List getNotExistsContractList(Object object);
	
	public String activityLanguage(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLanguageList(Object object);
	
	public int getCountOT(Object object);
	
	public int getCountLeave(Object object);
	
	public boolean checkPermission(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAlertDaysList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getMenuMemoList(Object object);

	/** 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author liangwei@ait.net.cn 
	* @date 2013-7-25 上午10:22:29 
	* @version V1.0   
	*/
	@SuppressWarnings("unchecked")
	public Map getTitleName(Object object);
	
	/** 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lipeng@ait.net.cn 
	* @date 2017-9-12 下午14:56:29 
	* @version V1.0   
	*/
	@SuppressWarnings("unchecked")
	public Map getTitleNamePartner(Object object);
	
	/** 
	 * 获取公告列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author    weizhengchen@ait.net.cn 
	*/
	@SuppressWarnings("unchecked")
	public List getNoticeList(Object object);
	
	/**
	 * 查询附件
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEssFileList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List viewApprovalInfo(Object obj);
	@SuppressWarnings("unchecked")
	public List viewBatchApprovalInfo(Object obj);
	@SuppressWarnings("unchecked")
	public List viewNoticeedEmail(Object obj);
	@SuppressWarnings("unchecked")
	public List viewAttendanceEx(Object obj);
	@SuppressWarnings("unchecked")
	public List viewAttendanceExCoor(Object obj);
	@SuppressWarnings("unchecked")
	public List viewAttendanceManagement(Object obj);
	@SuppressWarnings("unchecked")
	public List viewOTManagement(Object obj);
	
	@SuppressWarnings("unchecked")
	public List viewApplyList(Object obj);
	@SuppressWarnings("unchecked")
	public List viewApplyListCoor(Object obj);
	@SuppressWarnings("unchecked")
	public void updateUserData(Object obj, String target);
}
