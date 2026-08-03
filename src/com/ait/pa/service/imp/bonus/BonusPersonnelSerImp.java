package com.ait.pa.service.imp.bonus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusPersonnelDao;
import com.ait.pa.service.bonus.BonusPersonnelSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusPersonnelSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:17:20
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusPersonnelSerImp implements BonusPersonnelSer {

	Logger logger = Logger.getLogger(BonusPersonnelSerImp.class);

	@Autowired
	private BonusPersonnelDao bonusPersonnelDao;


	/**
	 * 获取奖金计算人员（get Bonus Personnel List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusPersonnelList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusPersonnelDao.getBonusPersonnelList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = bonusPersonnelDao.getBonusPersonnelList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获取奖金计算人员个数（get Bonus Personnel Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusPersonnelCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		retrunInt = bonusPersonnelDao.getBonusPersonnelCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 获取保险输入项目信息（get Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusPersonnelInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		returnObj = bonusPersonnelDao.getBonusPersonnelInfo(paramMap) ;
		
		return returnObj ;
	}

	/**
	 * 获取奖金计算人员参数（set Get Bonus Personnel Param）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetBonusPersonnelParam(HttpServletRequest request) {
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		// 判读PA_MONTH参数是否为空,为空赋予当前月
		String paMonth = ObjectUtils.toString(paramMap.get("PA_MONTH"));
		if (paMonth.length() == 0) {
			paMonth = DateUtil.getCurrentMonthStr();
			paramMap.put("PA_MONTH", paMonth);
		}

		//动态的将表名作为判断是否存在工资历史信息的参数 规则为PA_SUMMARY_ +公司法人ID
		paramMap.put("paSummeryTable", "PA_SUMMARY_" + admin.getCpnyId());
		

		// 判断是否存在工资历史信息
		int checkPaHistroyFlag = this.paHistoryDao
				.getCheckPaHistoryFlag(paramMap);
		if (checkPaHistroyFlag == 0) {
			paramMap.put("DATA_SOURCE", "HR_EMPLOYEE");
		} else {
			paramMap.put("DATA_SOURCE", "PA_SUMMARY");
		}
		return paramMap;
	} */

	/**
	 * 添加奖金计算人员（add Bonus Personnel Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusPersonnelInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getPersonId());
		
		this.bonusPersonnelDao.updateBonusPersonnelInfo(paramMap);
		
		return 0;
	}

	/**
	 * 删除奖金计算人员（delete Bonus Personnel Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	
	@SuppressWarnings("unchecked")
	@Override
	public int deleteBonusPersonnelInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);

		this.bonusPersonnelDao.deleteBonusPersonnelInfo(paramMap);

		return 0;
	} */

	/**
	 * 获取奖金计算人员（get Bonus Personnel List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusObjectList(HttpServletRequest request)throws Exception {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		if(!paramMap.containsKey("bonusYear")&&!paramMap.containsKey("bonusMonth")){
			String date = new SimpleDateFormat("yyyy-MM-dd").format((new Date(System.currentTimeMillis())));
			paramMap.put("bonusYear", date.substring(0, date.indexOf("-")));
			paramMap.put("bonusMonth", date.substring(date.indexOf("-")+1, date.lastIndexOf("-")));
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusPersonnelDao.getBonusObjectList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = bonusPersonnelDao.getBonusObjectList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获取奖金计算人员个数（get Bonus Personnel Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusObjectListCnt(HttpServletRequest request)throws Exception {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		if(!paramMap.containsKey("bonusYear")&&!paramMap.containsKey("bonusMonth")){
			String date = new SimpleDateFormat("yyyy-MM-dd").format((new Date(System.currentTimeMillis())));
			paramMap.put("bonusYear", date.substring(0, date.indexOf("-")));
			paramMap.put("bonusMonth", date.substring(date.indexOf("-")+1, date.lastIndexOf("-")));
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		retrunInt = bonusPersonnelDao.getBonusObjectListCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:25:38 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusCalcFlagByPersonId(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("UPDATED_BY", admin.getPersonId() == null ? "" : admin.getPersonId());

		return this.bonusPersonnelDao.updateBonusCalcFlagByPersonId(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getBonusObjectCtrollerInfo(HttpServletRequest request)
			throws Exception {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.bonusPersonnelDao.getBonusObjectCtrollerInfo(paramMap) ;
		return returnObj ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusObjectInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		paramMap.put("CALC_FLAG", request.getParameter("CALC_FLAG"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return this.bonusPersonnelDao.updateBonusObjectInfo(paramMap) ;
	}
}
