package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.ait.sys.dao.LoginDao;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class LoginDaoImpl extends SqlMapClientSupport implements LoginDao {
	
	public Object findUser(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("sys.login.findUser",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}
	
	public Object findUserSpecial(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("sys.login.findUserSpecial",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}
	
	public Object findCpnyIdByUser(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("sys.login.findCpnyIdByUser",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	public Object findPasswordByUser(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("sys.login.findPasswordByUser",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 * 验证是否有hub权限
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getIsCanInHub(Object object) {		
		int flag = 0;
		try {
			flag = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getIsCanInHub", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 验证IP地址是否可以访问hub
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getIsCanInHubByIP(Object object) {		
		int flag = 0;
		try {
			flag = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getIsCanInHubByIP", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List getRoleGroupListByPersonId(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getRoleGroupListByPersonId", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List getLeftMenu(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getLeftMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List getLeftMenuForPartner(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getLeftMenuForPartner", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	public String getMenuLoad(Object object) {		
		String temp="";
		try {
			temp = this.queryForObject("sys.login.getMenuLoad", object).toString();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getLimitsMap(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getLimitsMap", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	public void addLoginInfo(Object object) {
		try {
			this.insert("sys.login.addLoginInfo", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	/**
	 * 取一级菜单列表(get Top Menu by role group id)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getTopMenuByRoleGroupId(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getTopMenuByRoleGroupId", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 取一级菜单列表(get Top Menu)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getTopMenu(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getTopMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取默认左侧菜单(get Default Menu)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getDefaultMenu(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getDefaultMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取预转正日期(get ExpiredProbation)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getProbationList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getPerConversionList", object, 1, Integer.parseInt(((Map)object).get("viewProbationDays").toString()));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 *  
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getUserpass(Object object) {		
		List list = new ArrayList();
		try { 
			list = this.queryForList("sys.login.getUserpass", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取预转正日期条数(get ExpiredProbation count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getProbationCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getPerConversionListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	/**
	 * 取到期合同(get ExpiredContract)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getContractList", object, 1, Integer.parseInt(((Map)object).get("viewContractDays").toString()));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取到期合同条数(get ExpiredContract count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getLeaveConfirmCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getLeaveConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	/**
	 * 取未提交病假证明病假数(get SickLeave count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getSickLeaveProofConfirmCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getSickLeaveProofConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getContractCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getContractCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getIdCardCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getIdCardCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaNotImportCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getPaNotImportCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getBecomeRegulerWarn(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getBecomeRegulerWarn", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	
	@SuppressWarnings("unchecked")
	public int getOtConfirmCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getOtConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getArExConfirmCnt(Object object) {		
		int countEx = 0;
		try {
			countEx = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getArExConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countEx;
	}
	
	@SuppressWarnings("unchecked")
	public int getTempConfirmCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getTempConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getStartedLeftConfirmCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getStartedLeftConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	@SuppressWarnings("unchecked")
	public int getChangeDeptConfirmCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getChangeDeptConfirmCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	/**
	 * 到期证件(get HrCredential List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrCredentialList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getHrCredentialList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getPersonInfo(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.rolesGroup.getPersonInfo", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 到期证件条数(get HrCredential List count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getHrCredentialCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getHrCredentialCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	/**
	 * 取人事令列表(get Upgrade List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getUpgradeList(Object object) {		
		List list = new ArrayList();
		try {
			
			list = this.queryForList("sys.login.getAssignmentList", object);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取人事令列表条数(get Upgrade List count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getUpgradeCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getUpgradeCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	/**
	 * 取健康证令列表(get Health List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHealthList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getHealthList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取健康证令列表条数(get Health List count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getHealthCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getHealthCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	 
	
	
	
	/**
	 * 取生日列表(get Health List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getBirthdayList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getBirthdayList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 取保险申请表 是否有权限查看列表
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePowerList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getInsurancePowerList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	

	/**
	 * 取保险申请表 是否有权限查看列表
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePowerManList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getInsurancePowerManList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取保险申请表  列表
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getInsuranceList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 国际化语言活跃信息(get ExpiredProbation)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public String activityLanguage(Object object) {		
		int returnInt = 0 ;
		String language = ((LinkedHashMap)object).get("language").toString();
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.activityLanguage", object)), Integer.class) ;
			
			if(returnInt == 0){
				
				Map hm = new LinkedHashMap();
				hm.put("language","en");
				
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.activityLanguage", hm)), Integer.class) ;
				
				if(returnInt == 0){
					language = "zh";
				}else{
					language = "en";
				}
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return language;
	}
	
	/**
	 * 取语言列表(get Language List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getLanguageList(Object object) {		
		List list = new ArrayList();
		try {
			//sys.login.getBirthdayList
			list = this.queryForList("sys.login.getLanguageList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 未签合同(get NotExists ContractList)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getNotExistsContractList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getNotExistsContractListForLogin", object, 1, Integer.parseInt(((Map)object).get("viewNotContractDays").toString()));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 未签合同条数(get NotExists ContractCnt)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getNotExistsContractCnt(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.login.getNotExistsContractCntForLogin", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	
	
	/**
	 * 加班待决裁(get Count OT)
	 * @param Object
	 * @return List
	 */
	public int getCountOT(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.myhome.getCountOT", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	
	/**
	 * 休假待决裁(get Count Leave)
	 * @param Object
	 * @return List
	 */
	public int getCountLeave(Object object) {		
		int countOT = 0;
		try {
			countOT = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.myhome.getCountLeave", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return countOT;
	}
	@SuppressWarnings("unchecked")
	public boolean checkPermission(Object object){
		boolean showFlag=false;
		try { 
			Object mainParamInfo=new Object();
			List returnList = new ArrayList();
			returnList = this.queryForList("sys.myhome.getParamValue",object);
			if (returnList.size() > 0) {
				mainParamInfo = returnList.get(0);
				if(mainParamInfo!=null&&((Map)mainParamInfo).get("PARAM_VALUE")!=null&&!((Map)mainParamInfo).get("PARAM_VALUE").toString().equals("")){
					 String paramValue=((Map)mainParamInfo).get("PARAM_VALUE").toString();
					 String[] roleIds=paramValue.split(",");
					 for(String roleId:roleIds){
						((Map)object).put("role_id", roleId);
						int countOT = 
								NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.myhome.checkPermission", object)), Integer.class) ;
						 if(countOT>0){
							 showFlag=true;
							 break;
						 }
					 }
				}
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return showFlag;
	}
	
	/**
	 * 提醒天数(get AlertDays List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getAlertDaysList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getAlertDaysList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 提醒天数(get AlertDays List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getMenuMemoList(Object object) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getMenuMemoList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查找对应语言的title名称
	 */
	@SuppressWarnings("unchecked")
	public Map getTitleName(Object obj) {
		Map returnStr = null ;
		try {
		
			returnStr = (Map) this.queryForObject("sys.login.getTitleName", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnStr ;
		
	}
	
	/**
	 * 查找对应语言的title名称
	 */
	@SuppressWarnings("unchecked")
	public Map getTitleNamePartner(Object obj) {
		Map returnStr = null;
		try {
		
			returnStr = (Map) this.queryForObject("sys.login.getTitleNamePartner", obj);
	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnStr ;
		
	}
	
	/** 
	 * 获取公告列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author    weizhengchen@ait.net.cn 
	*/
	@SuppressWarnings("unchecked")
	public List getNoticeList(Object obj) {		
		List list = new ArrayList();
		try {
			list = this.queryForList("sys.login.getNoticeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询附件
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssFileList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getEssFileList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApprovalInfo(Object obj
			) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewApprovalInfo",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewBatchApprovalInfo(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewBatchApprovalInfo",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewNoticeedEmail(Object obj
			) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewNoticeedEmail",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceEx(Object obj
			) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewAttendanceEx",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceExCoor(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewAttendanceExCoor",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceManagement(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewAttendanceManagement",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewOTManagement(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewOTManagement",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewApplyList",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyListCoor(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("sys.login.viewApplyListCoor",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public void updateUserData(Object object, String target) {
		try {
			this.update("sys.login."+target, object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}
}
