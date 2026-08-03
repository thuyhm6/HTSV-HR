package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArMonthCalculateDao;

import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArMonthCalculateDaoImpl.java
 * @Description:
 * @Create date: 2012-2-10 下午05:53:50
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArMonthCalculateDaoImpl extends SqlMapClientSupport implements
		ArMonthCalculateDao {

	/**
	 * 汇总计算(month Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String monthCalculate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.arMonthCalculate.monthCalculate", paramMap);
			//returnString = ObjectUtils.toString(this.insert("ar.arMonthCalculate.monthCalculate", paramMap));
			returnString = ObjectUtils.toString(paramMap.get("OUT_INFO"));
//			if(returnString.indexOf("计算完毕") != -1){
//				this.monthCalculateConfirmApply(paramMap);
//			}
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}
		return returnString;
	}
	/**
	 * 考勤确认(month Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String monthCalculateConfirm(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			
			String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
			String[] NOS = AR_DEPT_NOS .split("!");
			for (int i = 0; i < NOS.length; i++) {
				if(paramMap.get("interCpnyID").toString().equals("TSTO")){
					paramMap.put("AR_DEPT_NO", NOS[i]);
				}else{
					
					paramMap.put("AR_DEPT_NO", "");
				}
				this.insert("ar.arMonthCalculate.monthCalculateConfirm", paramMap);
				//returnString = ObjectUtils.toString(this.insert("ar.arMonthCalculate.monthCalculate", paramMap));
				returnString += ObjectUtils.toString(paramMap.get("OUT_INFO"));
			}
			
		
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}
		return returnString;
	}
	
	
	/**
	 * 汇总计算页面的 申请确认按钮功能(month Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String monthCalculateConfirmApply(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			
			 
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					if(paramMap.get("interCpnyID").toString().equals("TSTO")){
						paramMap.put("AR_DEPT_NO", NOS[i].replaceAll("'", ""));
					}else{
						
						paramMap.put("AR_DEPT_NO", "");
					}
					this.insert("ar.arMonthCalculate.monthCalculateConfirmApply", paramMap);
					returnString += ObjectUtils.toString(paramMap.get("OUT_INFO"));
				}
			 	
			
			//returnString = ObjectUtils.toString(this.insert("ar.arMonthCalculate.monthCalculate", paramMap));
			
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}
		return returnString;
	}


	/**
	 * 检查考勤锁定表(get MonthlyStatus Cnt)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getMonthlyStatusCnt(Object obj) {
		int result = 0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonthCalculate.getMonthlyStatusCnt",obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 检查门店锁定表(get MonthlyStatus Cnt)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getMonthlyStatusByDeptDisCnt(Object obj) {
		int result = 0;
		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonthCalculate.getMonthlyStatusByDeptDisCnt",obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新考勤锁定表(update AttStatus)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void updateAttStatus(Object obj) throws Exception {
		//this.update("ar.arMonthCalculate.updateDetailLock", obj);
		this.update("ar.arMonthCalculate.updateDailyAttStatus", obj);
		this.update("ar.arMonthCalculate.updateMonthlyAttStatus", obj);
	}

	/**
	 * 添加考勤锁定表(insert MonthlyStatus)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void insertMonthlyStatus(Object parameterObject) {
		try {
			this.insert("ar.arMonthCalculate.insertMonthlyStatus",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取考勤锁定列表(get MonthlyStatus List)
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(Object parameterObject) {
		List list = null;
		try {
			list = this.queryForList("ar.arMonthCalculate.getMonthlyStatusList",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据考勤员权限查询该考勤员权限内下的所有门店的部门区分
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDeptDistinguishListBySupervisior(Object parameterObject) {
		List list = null;
		try {
			list = this.queryForList("ar.arMonthCalculate.getDeptDistinguishListBySupervisior",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 江苏玛特专用取门店考勤锁定数量(get MonthlyStatus List by dept)
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getMonthlyStatusByDeptCnt(Object obj) {
		int cnt = 0;
		try {
			cnt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonthCalculate.getMonthlyStatusByDeptCnt",obj)), Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	/**
	 * 添加考勤锁定表(insert MonthlyStatus)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void insertMonthlyStatusByDept(Object parameterObject) {
		try {
			this.insert("ar.arMonthCalculate.insertMonthlyStatusByDept",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 锁定考勤锁定表(insert MonthlyStatus)
	 * 
	 * @param Object
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void updateMonthlyStatusByDept(Object parameterObject) {
		try {
			this.insert("ar.arMonthCalculate.updateMonthlyStatusByDept",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得考勤区间(get StatNo List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getStatNoList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.arMonthCalculate.getStatNoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得考勤员根据考勤权限可以看到的大区的名称列表
	 * @param request
	 * @return List
	 */
	@Override
	public List getDeptAreaList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.arMonthCalculate.getDeptAreaList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	
	/**
	 * 取部门区域(get dept_type List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getDeptTypeList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.arMonthCalculate.getDeptTypeList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public void updatearapplyCloseGuan(Object parameterObject) {
		try {
			this.insert("ar.arMonthCalculate.updatearapplyCloseGuan",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	public void updatepaapplyCloseGuan(Object parameterObject) {
		try {
			this.insert("ar.arMonthCalculate.updatepaapplyCloseGuan",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void deleteArPaSummaryapplyClose(Object parameterObject) {
		try {
			this.delete ("ar.arMonthCalculate.deleteArSummaryapplyClose",parameterObject);
			this.delete ("ar.arMonthCalculate.deletePaSummaryapplyClose",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void rowTranscolsPa(Object parameterObject) {
		try {
			this.insert("ar.arMonthCalculate.rowTranscolsPa",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getarEssNOApplyCount(Object parameterObject) {
		List list = new ArrayList();
		try {
			list= this.queryForList("ar.arMonthCalculate.getarEssNOApplyCount",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getpaEssNOApplyCount(Object parameterObject) {
		List list = new ArrayList();
		try {
			list= this.queryForList("ar.arMonthCalculate.getpaEssNOApplyCount",parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int updateArOffLeave(Object parameterObject) {
		int num = 0;
		try {
			this.update("ar.arMonthCalculate.updateLeaveAffirm",parameterObject);
			this.update("ar.arMonthCalculate.updateLeaveEp",parameterObject);
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updateLeave",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	@SuppressWarnings("unchecked")
	public int updateArOffLeaveP(Object parameterObject) {
		int num = 0;
		try {
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updateLeaveP",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public int updateArOffApplyOt(Object parameterObject) {
		int num = 0;
		try {
			this.update("ar.arMonthCalculate.updateArOffApplyOtAffirm",parameterObject);
			this.update("ar.arMonthCalculate.updateArOffApplyOtEp",parameterObject);
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updateArOffApplyOt",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public int updateArOffCwa(Object parameterObject) {
		int num = 0;
		try {
			this.update("ar.arMonthCalculate.updateArOffAffirm",parameterObject);
			this.update("ar.arMonthCalculate.updateArOffEp",parameterObject);
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updateArOffCwa",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public int updateArOffAnnu(Object parameterObject) {
		int num = 0;
		try {
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updateArOffAnnu",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public int updateArOffMac(Object parameterObject) {
		int num = 0;
		try {
			this.update("ar.arMonthCalculate.updateArOffMacAffirm",parameterObject);
			this.update("ar.arMonthCalculate.updateArOffMacEp",parameterObject);
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updateArOffMac",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getdeptNamebyno(Object parameterObject) {
		String deptname = "";
		try {
			deptname= StringUtil.checkNull(this.queryForObject("ar.arMonthCalculate.getdeptNamebyno",parameterObject));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptname;
	}
	
	@SuppressWarnings("unchecked")
	public String getATTMOLOCKFLAG(Object parameterObject) {
		String str = "";
		try {
			str = this.queryForObject("ar.arMonthCalculate.getATTMOLOCKFLAG",parameterObject).toString()!=null?this.queryForObject("ar.arMonthCalculate.getATTMOLOCKFLAG",parameterObject).toString():"";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	/**
	 * 获得考勤锁定标志(get PA_PROGRESS ,ATT_APPLY_LOCK_FLAG  by PERSON_ID AND MONTHSTR)
	 * 
	 * @param Object
	 * @param MONTHSTR format 'YYYYMM'
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getPaProgressLockFlag(Object obj) {
		int cnt = 0;
		try {
			cnt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonthCalculate.getPaProgressLockFlag",obj),"0"), Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	
	@SuppressWarnings("unchecked")
	public int updatepawage(Object parameterObject) {
		int num = 0;
		try {
			num = Integer.parseInt(StringUtil.checkNull(this.update("ar.arMonthCalculate.updatepawage",parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
}
