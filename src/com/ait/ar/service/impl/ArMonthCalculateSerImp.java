package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.ArMonthCalculateDao;
import com.ait.ar.service.ArMonthCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArMonthCalculateSerImp.java
 * @Description:
 * @Create date: 2012-2-10 下午05:52:37
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArMonthCalculateSerImp implements ArMonthCalculateSer {
	Logger logger = Logger.getLogger(ArMonthCalculateSerImp.class);

	@Autowired
	private ArMonthCalculateDao arMonthCalculateDao;

	/**
	 * 汇总计算(month Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String monthCalculate(HttpServletRequest request) {
		//计算失败
		String returnString = "["+TipMessage.getTipMessage("ar.viewararmonthcalculate.title.jisuanshibai",request)+"]";
		//对不起，月考勤已锁定，汇总计算不能进行
		if (!this.validateMonthlyStatus(request)) {
			return "<font color='Red'>"+TipMessage.getTipMessage("ar.viewararmonthcalculate.title.kaoqinsuoding",request)+"</font>";
		}
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("OUT_INFO", "");
	    paramMap.put("AR_DEPT_NO", request.getParameter("AR_DEPT_NO")); //大区  111,222,333
		returnString = arMonthCalculateDao.monthCalculate(paramMap);

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
	public String monthCalculateConfirm(HttpServletRequest request) {
		//计算失败
		String returnString = "";
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("OUT_INFO", "");
	    paramMap.put("AR_DEPT_NO", request.getParameter("AR_DEPT_NO")); //大区  111,222,333
		returnString = arMonthCalculateDao.monthCalculateConfirm(paramMap);

		return returnString;
	}
	/**
	 * 汇总计算页面 申请确认功能(month Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String monthCalculateConfirmApply(HttpServletRequest request) {
		//计算失败
		String returnString = "";
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("OUT_INFO", "");
	    paramMap.put("AR_DEPT_NO", request.getParameter("AR_DEPT_NO")); //大区  111,222,333
		returnString = arMonthCalculateDao.monthCalculateConfirmApply(paramMap);

		return returnString;
	}
	/**
	 * 检查考勤锁定(validate MonthlyStatus)
	 * 
	 * @param request
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean validateMonthlyStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH_STR", request.getParameter("arMonth"));
		paramMap.put("STAT_NO", request.getParameter("STAT_NO"));
		paramMap.put("LOCK_USER_ID", admin.getPersonId());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		String AR_DEPT_NOS=request.getParameter("AR_DEPT_NO") ;
		String[] NOS = AR_DEPT_NOS .split("!");
		boolean cnt=false ;
		int temp=0;
		for (int i = 0; i < NOS.length; i++) {
			paramMap.put("AR_DEPT_NO", NOS[i].replaceAll("'", ""));
		  try {
			if (arMonthCalculateDao.getMonthlyStatusCnt(paramMap) > 0) {
				//arMonthCalculateDao.updateAttStatus(paramMap);
			} else {
				//插入公司锁定信息
				arMonthCalculateDao.insertMonthlyStatus(paramMap);
			}
			 
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		List list = arMonthCalculateDao.getMonthlyStatusList(paramMap);
	 
		 temp = temp + Integer.parseInt(ObjectUtils.toString(((LinkedHashMap) list.get(0)).get("ATT_MO_LOCK_FLAG"))) ;
		
		 
		}
		cnt= temp == 0? true : false;
		return cnt;
	}
	
	/**
	 * 取考勤区间(get StatNo List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getStatNoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		retrunList = arMonthCalculateDao.getStatNoList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 取考勤员所可见的大区列表
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunList = arMonthCalculateDao.getDeptAreaList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 取部门区域(get dept_type List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTypeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		retrunList = arMonthCalculateDao.getDeptTypeList(paramMap) ;
		
		return retrunList ;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public int arapplyCloseGuan(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
		String[] NOS = AR_DEPT_NOS .split("!");
		for (int i = 0; i < NOS.length; i++) {
			paramMap.put("DEPT_NO", NOS[i]);
			arMonthCalculateDao.updatearapplyCloseGuan(paramMap);
		}
		return 1 ;
	}
	


	@SuppressWarnings("unchecked")
	public String arapplyCloseGuanstr(HttpServletRequest request) {
		String ret= "";
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					paramMap.put("DEPT_NO", NOS[i]);
					paramMap.put("FLAG", "1");
					arMonthCalculateDao.updatearapplyCloseGuan(paramMap);
					
					arMonthCalculateDao.deleteArPaSummaryapplyClose(paramMap);
			    }
			ret = "申请关闭成功！";
			}else{
				paramMap.put("FLAG", "1");
				arMonthCalculateDao.updatearapplyCloseGuan(paramMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = "申请关闭失败！";
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public String paapplyCloseGuanstr(HttpServletRequest request) {
		String ret= "";
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					paramMap.put("FLAG", "1");
					if(paramMap.get("interCpnyID").toString().equals("TSTO")){
						paramMap.put("DEPT_NO", NOS[i]);
					}
					arMonthCalculateDao.updatepaapplyCloseGuan(paramMap);
				}
				ret = "申请关闭成功！";
			}else{
				paramMap.put("FLAG", "1");
				arMonthCalculateDao.updatepaapplyCloseGuan(paramMap);
			}
			//关闭工资后行转列插入预提所需工资项目到工资临时表PAY_PAY_HIST,计算完后直接插入了。
		//	arMonthCalculateDao.rowTranscolsPa(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = "申请关闭失败！";
		}
		return ret;
	}
	
	
	@SuppressWarnings("unchecked")
	public int arapplyCloseOpen(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int num = 0;
		String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
		String[] NOS = AR_DEPT_NOS .split("!");
		for (int i = 0; i < NOS.length; i++) {
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				paramMap.put("DEPT_NO", NOS[i]);
			}
			String moflag = arMonthCalculateDao.getATTMOLOCKFLAG(paramMap);
			if(moflag.equals("0")){
			  arMonthCalculateDao.updatearapplyCloseGuan(paramMap);
			  num = 1;
			}
		}
		return num ;
	}
	
	
	@SuppressWarnings("unchecked")
	public String arapplyCloseOpenstr(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String ret = "";
		if(paramMap.get("interCpnyID").toString().equals("TSTO")){
			String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
			String[] NOS = AR_DEPT_NOS .split("!");
			for (int i = 0; i < NOS.length; i++) {
				if(paramMap.get("interCpnyID").toString().equals("TSTO")){
					paramMap.put("DEPT_NO", NOS[i]);
				}
				try {
					String moflag = arMonthCalculateDao.getATTMOLOCKFLAG(paramMap);
					if(moflag.equals("0")){
					  arMonthCalculateDao.updatearapplyCloseGuan(paramMap);
					  ret = "申请关闭解除成功！";
					}else{
						ret = "考勤已经申请确认，无法申请关闭解除！";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ret = "申请关闭解除失败，请重试！";
				}
			}
		}else{
			try {
				String moflag = arMonthCalculateDao.getATTMOLOCKFLAG(paramMap);
				if(moflag.equals("0")){
				  arMonthCalculateDao.updatearapplyCloseGuan(paramMap);
				  ret = "申请关闭解除成功！";
				}else{
					ret = "考勤已经申请确认，无法申请关闭解除！";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret = "申请关闭解除失败，请重试！";
			}
		}
		
		return ret ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List arEssNOApplyCount(HttpServletRequest request) {
		List listcount = new ArrayList();
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					LinkedHashMap deptnoapply = new LinkedHashMap();//每个大区对应所有类型的未决裁信息
					paramMap.put("DEPT_NO", NOS[i]);
					deptnoapply.put("DEPT_NO", paramMap.get("DEPT_NO"));
					deptnoapply.put("DEPT_NAME", arMonthCalculateDao.getdeptNamebyno(paramMap));
					List typeshu = arMonthCalculateDao.getarEssNOApplyCount(paramMap);	
					deptnoapply.put("TYPESHU", typeshu);
					listcount.add(deptnoapply);
				}
			}else{
					LinkedHashMap deptnoapply = new LinkedHashMap();//每个大区对应所有类型的未决裁信息
					deptnoapply.put("DEPT_NO", paramMap.get("DEPT_NO"));
					deptnoapply.put("DEPT_NAME", arMonthCalculateDao.getdeptNamebyno(paramMap));
					List typeshu = arMonthCalculateDao.getarEssNOApplyCount(paramMap);	
					deptnoapply.put("TYPESHU", typeshu);
					listcount.add(deptnoapply);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcount;
	}
	
	
	@SuppressWarnings("unchecked")
	public List paEssNOApplyCount(HttpServletRequest request) {
		List listcount = new ArrayList();
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					LinkedHashMap deptnoapply = new LinkedHashMap();//每个大区对应所有类型的未决裁信息
					paramMap.put("DEPT_NO", NOS[i]);
					deptnoapply.put("DEPT_NO", paramMap.get("DEPT_NO"));
					deptnoapply.put("DEPT_NAME", arMonthCalculateDao.getdeptNamebyno(paramMap));
					List typeshu = arMonthCalculateDao.getpaEssNOApplyCount(paramMap);	
					deptnoapply.put("TYPESHU", typeshu);
					listcount.add(deptnoapply);
				}
			}else{
				LinkedHashMap deptnoapply = new LinkedHashMap();//每个大区对应所有类型的未决裁信息
				deptnoapply.put("DEPT_NO", paramMap.get("DEPT_NO"));
				deptnoapply.put("DEPT_NAME", arMonthCalculateDao.getdeptNamebyno(paramMap));
				List typeshu = arMonthCalculateDao.getpaEssNOApplyCount(paramMap);	
				deptnoapply.put("TYPESHU", typeshu);
				listcount.add(deptnoapply);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcount;
	}
	
	
	@SuppressWarnings("unchecked")
	public int arOFF(HttpServletRequest request) {
		List listcount = new ArrayList();
		int num = 1;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					if(paramMap.get("interCpnyID").toString().equals("TSTO")){
						paramMap.put("DEPT_NO", NOS[i]); 
					}
					 //否决休假
					arMonthCalculateDao.updateArOffLeave(paramMap);	
					//arMonthCalculateDao.updateArOffLeaveP(paramMap);	
					//否决加班
					arMonthCalculateDao.updateArOffApplyOt(paramMap);	
					//否决考勤异常
					arMonthCalculateDao.updateArOffCwa(paramMap);	
					//否决年假调整
					//arMonthCalculateDao.updateArOffAnnu(paramMap);	
					//否决漏刷卡
					arMonthCalculateDao.updateArOffMac(paramMap);	
				}
			}else{
				 //否决休假
				arMonthCalculateDao.updateArOffLeave(paramMap);	
				//arMonthCalculateDao.updateArOffLeaveP(paramMap);	
				//否决加班
				arMonthCalculateDao.updateArOffApplyOt(paramMap);	
				//否决考勤异常
				arMonthCalculateDao.updateArOffCwa(paramMap);	
				//否决年假调整
				//arMonthCalculateDao.updateArOffAnnu(paramMap);	
				//否决漏刷卡
				arMonthCalculateDao.updateArOffMac(paramMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			num = 0;
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public int paOFF(HttpServletRequest request) {
		List listcount = new ArrayList();
		int num = 1;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("AR_DEPT_NO").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					if(paramMap.get("interCpnyID").toString().equals("TSTO")){
						paramMap.put("DEPT_NO", NOS[i]);
					}
					arMonthCalculateDao.updatepawage(paramMap);	
					 
				}
			}else{
				arMonthCalculateDao.updatepawage(paramMap);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			num = 0;
		}
		return num;
	}
	
}
