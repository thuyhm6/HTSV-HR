package com.ait.ess.service.impl;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.impl.AffirmInfoToLGEPSerImpl;
import com.ait.ess.dao.WageApplicationDao;
import com.ait.ess.service.WageApplicationSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
@SuppressWarnings("unchecked")
public class WageApplicationSerImpl implements WageApplicationSer {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private WageApplicationDao wageApplicationDao;
	@Autowired
	private AffirmInfoToLGEPSerImpl affirmInfoToLGEPSerImpl;
	@Autowired
	private AuthorityUtil util;
	@Override
	public List getListByRequest(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(util.isWageUser(admin.getPersonId())==1){//工资担当查所管辖的所有部门的人
			paramMap.put("DEPTNO", "DEPTNO");
			paramMap.put("USEROLE", admin.getUserNo());
		}else {//不是工资担当的人按照正常状态查
			paramMap.put("DEPTNO", "");
		}
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		// 按分页查找
		list = wageApplicationDao.getListByEmp(paramMap,
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		return list;
	}
	/**
	 * 查找总数 
	 */
	@Override
	public int getListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(util.isWageUser(admin.getPersonId())==1){//工资担当查所管辖的所有部门的人
			paramMap.put("DEPTNO", "DEPTNO");
			paramMap.put("USEROLE", admin.getUserNo());
		}else {//不是工资担当的人按照正常状态查
			paramMap.put("DEPTNO", "");
		}
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		int countNum = wageApplicationDao.getListCnt(paramMap);
		return countNum;
	}
	
	@Override
	public List getApplicationList(HttpServletRequest request) {
		List list = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// 按分页查找
		list = wageApplicationDao.getApplicationList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		return list;
	}
	
	@Override
	public int getApplicationListCnt(HttpServletRequest request) {
		int count = 0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// 按分页查找
		count = wageApplicationDao.getApplicationListCnt(paramMap);
		return count;
	}
	
	public List getProveAppList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("APPLY_TYPE", "217886");
		return wageApplicationDao.getProveAppList(paramMap);
	}
	
	public List getProveCheckAppList(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("APPLY_TYPE", "217886");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return wageApplicationDao.getProveCheckAppList(paramMap,
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
	}
	
	@Override
	public int getProveCheckAppListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		int countNum = wageApplicationDao.getProveCheckAppListCnt(paramMap);
		return countNum;
	}
	
	public int updateBatchSubmitApp(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("AFFIRM_CONTENT", request.getParameter("AFFIRM_CONTENT"));
		String[] affirms = request.getParameterValues("c1");
		int flag = 0;
		for(int i=0;i<affirms.length;i++){
			paramMap.put("APPLY_TYPE", "217886");
			paramMap.put("AFFIRM_NO", affirms[i]);
			flag = wageApplicationDao.updateBatchSubmitApp(paramMap);
			if(flag == 0){
				break;
			}
		}
		return flag; 
	}
	
	public int getCancleApplicationState(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("APPLY_TYPE", "217886");
		StringBuffer buffer = new StringBuffer();
		String[] affirms = request.getParameterValues("c1");
		if(affirms.length>=1){
			for (int i = 0; i < affirms.length-1; i++) {
				buffer.append(affirms[i]).append(",");
			}
			buffer.append(affirms[affirms.length-1]);
		}
		buffer = (StringBuffer) paramMap.get("c1");
		paramMap.put("IDS", buffer.toString());
		return wageApplicationDao.getCancleApplicationState(paramMap);
	}
	
	public int getCancleSigleApplicationState(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("APPLY_TYPE", "217886");
		return wageApplicationDao.getCancleApplicationState(paramMap);
	}
	
	public int deleteOtApplication(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("APPLY_TYPE", "217886");
		String[] affirms = request.getParameterValues("c1");
		if(affirms.length>0){
			for (int i = 0; i < affirms.length; i++) {
				paramMap.put("APPLY_NO", affirms[i]);
				wageApplicationDao.deleteOtApplication(paramMap);
				//向LGEP发送删除指令
				affirmInfoToLGEPSerImpl.deleteAffirm(paramMap);
			}
			return 1;
		}
		return 0;
	}
	
	public List getPbWageList(HttpServletRequest request){
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		list = wageApplicationDao.getPbWageList(paramMap,
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		return list;
	}
	
	public int getPbWageCnt(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		int countNum = wageApplicationDao.getPbWageCnt(paramMap);
		return countNum;
	}
	
	public int getBackPbOtApplication(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("APPLY_TYPE", "217886");
		String[] affirms = request.getParameterValues("c1");
		int Flag = 0;
		for (int i = 0; i < affirms.length; i++) {
			paramMap.put("WAGEID", affirms[i]);
			Flag = wageApplicationDao.getBackPbOtApplication(paramMap);
			if(Flag == 0){
				break;
			}
		}
		return Flag;
	}
	
	public List getWageApplicationTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = wageApplicationDao.getWageApplicationTempList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = wageApplicationDao.getWageApplicationTempList(paramMap) ;
		}
		return retrunList;
	}
	
	public int getWageApplicationTempCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = wageApplicationDao.getWageApplicationTempCnt(paramMap) ;
		return retrunInt ;
	}

	public int getWageApplicationTempErrCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = wageApplicationDao.getWageApplicationTempErrCnt(paramMap) ;
		return retrunInt ;
	}
	
	public String importApplicationExcelTempExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = wageApplicationDao.importApplicationExcelTempExcel(paramMap) ;
		return retrunInt;
	}
	@Override
	public int getWageApplicationCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		int countNum = wageApplicationDao.getWageApplicationCnt(paramMap);
		return countNum;
	}
	@Override
	public List getWageApplicationList(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		list = wageApplicationDao.getWageApplicationList(paramMap,
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		return list;
	}
	@Override
	public int checkApplyApplicationState(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if("TSTO".equals(admin.getCpnyId())){
			paramMap.put("AREA_ID", "AREA_ID");//LGECH法人有大区区分，其它法人没有
		}
		int countNum = wageApplicationDao.checkApplyApplicationState(paramMap);
		return countNum;
	}
	
	@Override
	public List getApplicationByNoApplyNo(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		list = wageApplicationDao.getApplicationByNoApplyNo(paramMap,
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		return list;
	}
	@Override
	public int getApplicationByNoApplyNoCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return wageApplicationDao.getApplicationByNoApplyNoCnt(paramMap);
	}
	public int saveWageAppFile(HttpServletRequest request,Map<String, Object> map){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("FILEURL", map.get("fileUrl"));
		paramMap.put("OLDNAME", map.get("oldName"));
		return wageApplicationDao.saveWageAppFile(paramMap);
	}
	public List getAppliFileList(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else if (paramMap.get("APPLY_NO")==null) {
			paramMap.put("APPLY_NO", "");
		}
		return wageApplicationDao.getAppliFileList(paramMap);
	}
	public int deleteWageAppFile(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		int flag = wageApplicationDao.deleteWageAppFile(paramMap);
		if(flag == 1){
			String logoPathDir = "/resources/uploadFile/wageApp/";
			String realPath = request.getSession().getServletContext().getRealPath(logoPathDir);
			File file = new File(realPath+File.separator+paramMap.get("NEWNAME"));
			if(file.exists()){
				file.delete();
			}
		}
		return flag; 
	}
	public void updateWageAppFile(HttpServletRequest request, int wageNo){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("WAGENO", wageNo);
		wageApplicationDao.updateWageAppFile(paramMap);
	}
	public void deleteAllWageAppFile(HttpServletRequest request){
		List list = getAppliFileList(request);
		for (int i = 0; i < list.size(); i++) {
			Map paramMap = (Map) list.get(i);
			paramMap.put("CREATED_BY", paramMap.get("CREATED_BY"));
			int flag = wageApplicationDao.deleteWageAppFile(paramMap);
			if(flag == 1){
				String logoPathDir = "/resources/uploadFile/wageApp/";
				String realPath = request.getSession().getServletContext().getRealPath(logoPathDir);
				File file = new File(realPath+File.separator+paramMap.get("NEWNAME"));
				if(file.exists()){
					file.delete();
				}
			}
		}
	}
	
	/**
	 * 费用履历查看  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWageCheckList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					wageApplicationDao.getWageCheckList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = wageApplicationDao.getWageCheckList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 *费用履历查看  数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getWageCheckCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = wageApplicationDao.getWageCheckCnt(paramMap) ;
		return retrunInt ;
	}
}