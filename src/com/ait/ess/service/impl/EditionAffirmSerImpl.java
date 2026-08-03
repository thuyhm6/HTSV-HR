package com.ait.ess.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bsh.util.Util;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.DimissionEditionDao;
import com.ait.ess.dao.EditionAffirmDao;
import com.ait.ess.service.EditionAffirmSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
@Service
public class EditionAffirmSerImpl implements EditionAffirmSer{
	
	@Autowired
	private EditionAffirmDao editionAffirmDao;
	
	@Autowired
	private AffirmApplyDao affirmApplyDao;
	@Autowired
	PaTempSalesDAO paTempSalesDAO;
	
	//离职申请类型代码
	private static String APPLY_TYPE_NO = "218296";
	
	//离职交接决裁邀请名称
	private static String APPLY_TYPE_NAME = "离职交接审批邀请";
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	
	/**
	 * 查找离职申请数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionAffirmCnt(HttpServletRequest request)throws SQLException{
		int retrunInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunInt = editionAffirmDao.getDimissionAffirmorCnt(paramMap) ;
		return retrunInt ;
	}
	
	/**
	 * 查找离职申请List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEditionAffirmList(HttpServletRequest request)throws SQLException{
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
//		if(request.getParameter("checkflag")=="1" || "1".equals(request.getParameter("checkflag"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
//		}else{
//			paramMap.remove("PERSON_ID");
//		}
	    if(request.getParameter("m")=="1" || "1".equals(request.getParameter("m"))){
		}else{
			if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
				paramMap.put("AFFIRM_FLAG", "10");
			}
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = editionAffirmDao.getDimissionAffirmorList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = editionAffirmDao.getDimissionAffirmorList(paramMap) ;
		}
		return retrunList ;
	}
	
	public List getEditionLgepAffirmList(HttpServletRequest request)throws SQLException{
		List retrunList = new ArrayList() ;
		String interLanguage = request.getParameter("LANGUAGE");
		String personId = request.getParameter("personId");
		Map paramMap = null;
		if(interLanguage == null || "".equals(interLanguage)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", interLanguage);
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("PERSON_ID", personId);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = editionAffirmDao.getDimissionAffirmorList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = editionAffirmDao.getDimissionAffirmorList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 根据NO查询此次申请的人(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionApplyorByApplyNoList(HttpServletRequest request) throws Exception{
		String interLanguage = request.getParameter("LANGUAGE");
		Map paramMap = new LinkedHashMap();
		if(interLanguage == null || "".equals(interLanguage)){
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
		}else{
			paramMap.put("interLanguage", interLanguage);
		}
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = editionAffirmDao.getEditionApplyorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 根据离职申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionAffirmorByApplyNoList(HttpServletRequest request) throws Exception{
		String personId = request.getParameter("personId");
		 Map paramMap = new LinkedHashMap();
		if(personId == null || "".equals(personId)){
			 paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		     AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		     paramMap.put("PERSON_ID", admin.getPersonId());
		}else{
			String interLanguage = request.getParameter("LANGUAGE");
		    paramMap.put("PERSON_ID",personId);
		    paramMap.put("interLanguage",interLanguage);
		}
		String apply_no = request.getParameter("APPLY_NO");
		if(apply_no!=null && !"".equals(apply_no)){
			paramMap.put("APPLY_NO", apply_no);
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = editionAffirmDao.getEditionAffirmorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 离职申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionCheckorByApplyNoList(HttpServletRequest request) throws Exception{
		Map paramMap = new LinkedHashMap();
		String personId = request.getParameter("personId");
		String interLanguage = request.getParameter("LANGUAGE");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}else{
			String APPLY_NO = request.getParameter("APPLY_NO");
			paramMap.put("ADMIN_ID", personId);
			paramMap.put("interLanguage", interLanguage);
			paramMap.put("APPLY_NO", APPLY_NO);
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = editionAffirmDao.getEditionCheckorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 决裁离职申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public int affirmDimisionInfo(HttpServletRequest request){
		AdminBean admin = null;
		LinkedHashMap paramMap = new LinkedHashMap();
		if(request.getParameter("personId") == null || "".equals(request.getParameter("personId"))){
			admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(paramMap.get("interLanguage")==null || "".equals(paramMap.get("interLanguage"))){
			paramMap.put("interLanguage", "zh");
		}
		paramMap.put("FLAG", paramMap.get("FLAG"));
		paramMap.put("UPDATED_BY", admin == null ? request.getParameter("personId") :admin.getAdminID());
		paramMap.put("CREATED_BY", admin == null ? request.getParameter("personId") :admin.getAdminID());
		paramMap.put("PERSON_ID", admin == null ? request.getParameter("personId") : admin.getAdminID());
		paramMap.put("CURRENT_AFFIRM_ID",  "");
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		paramMap.put("APPLY_NO",request.getParameter("APPLY_NO"));
		int dept_level = Integer.parseInt(request.getParameter("dept_level").toString());
		paramMap.put("dept_level", dept_level + 1);
		try {
			//删除旧的决裁者
			editionAffirmDao.deleteDimissionAffirmor(paramMap);
			//添加决裁者
			for(int i=1; i<affirmId.length; i++){
				paramMap.put("AFFIRMOR_ID", affirmId[i]);
				paramMap.put("AFFIRM_LEVEL", i+dept_level);
				editionAffirmDao.insertDimissionAffirmor(paramMap);
			}
			if(request.getParameter("personId")!= null && !"".equals(request.getParameter("personId"))){
				this.addNewAffirmorList(paramMap, request);
			}
			//决裁
			this.editionAffirmDao.updateDimissionEssAffirm(paramMap);
			if(!"2".equals(paramMap.get("FLAG"))){//否决直接修改标志位,通过继续下面操作
				//获取决裁者列表
				paramMap.put("ACTIVITY", "0");
				List<LinkedHashMap> affirmList = this.editionAffirmDao.getDimissionAffirmor(paramMap);
				if (affirmList != null && affirmList.size() > 0) {
					for (LinkedHashMap parmers : affirmList) {
						if("0".equals(parmers.get("AFFIRM_FLAG").toString())){
							paramMap.put("CURRENT_AFFIRM_ID",  parmers.get("AFFIRMOR_ID"));
							paramMap.put("AFFIRM_LEVEL",  parmers.get("AFFIRM_LEVEL"));
							this.editionAffirmDao.affirmDimissionInfoAffirm(paramMap);
							break;
						}
						paramMap.put("AFFIRM_LEVEL",  affirmList.size());
					}
				}
				/**根据申请类型（APPLY_TYPE_NO）和APPLY_NO来查找该离职申请对应的决裁人  判断是不是最高级的决裁人决裁  
				如果是最后的决裁者决裁 状态就是已通过*/
				List listAffirm = this.editionAffirmDao.getDimissionAffirmor(paramMap);
				paramMap.put("AFFIRM_FLAG1","1");
				List listAffirmor = this.editionAffirmDao.getDimissionAffirmor(paramMap);
				if(listAffirm.size()>0 && listAffirmor.size()>0 && listAffirm.size()==listAffirmor.size()){
					paramMap.put("FLAG", "1");
					/**在离职审批通过以后，将该员工的离职模版出入到离职模版表中SYS_DIMISION_EDITION
					1.根据该员工的人员类型查找到该员工适用的离职交接模版  模版项目类型  和交接项目*/
					paramMap.put("PID", request.getParameter("PERSON_ID"));
					List<LinkedHashMap> listItem = this.editionAffirmDao.getEditionItemByPidList(paramMap);
					//2.如果listItem不是空值，就把该信息插入到SYS_EDITION_PERSON表里面  这样就是该离职申请者的离职模版
					if(listItem.size()>0){
						LinkedHashMap para = new LinkedHashMap();
						for(LinkedHashMap paramter : listItem){
							this.editionAffirmDao.addEditionEditionPidInfo(paramter);
							para = paramter;
						}
						//查找插入的审批者  如果是离职申请人员本部门的人则发送LGEP
						Map mater = new LinkedHashMap();
						paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
						List<LinkedHashMap> itemAffirmorList = this.getAffirmEditionItemList(para,request);
							if(itemAffirmorList!=null && itemAffirmorList.size()>0){
								for(int i=0;i<itemAffirmorList.size();i++){
							        mater = itemAffirmorList.get(i);
							        para.put("APPLY_NO", request.getParameter("APPLY_NO"));
							        para.put("PERSON_ID", para.get("APPLY_PERSON_ID"));
							        para.put("CURRENT_AFFIRM_ID", mater.get("AFFIRM_PERSON_ID"));
							        para.put("EDITION_PERSON_NO", mater.get("EDITION_ITEM_NO"));
							        this.sendToLGEPInsert(para);
							   }
						    }
					    }
				}else{
					paramMap.put("FLAG", "0");
				}
			}
			//check信息修改为已check
			sendToLGEPCheckBatch(paramMap);
			this.editionAffirmDao.updateCheckFlagByDimissionEssAffirmNo(paramMap);
			this.editionAffirmDao.affirmDimissionInfo(paramMap);
			//审批发送LGEP
			paramMap.put("AFFIRM_FLAG", paramMap.get("FLAG"));
			sendToLGEP(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 审批后发送LGEP
	 * @param eventId/**
	 * check：
	 *  APPLY_NO:申请的seq
	 *  APPLY_TYPE：申请类型（数字代码）
	 *	AFFIRM_LEVEL：ess_check_no
	 *	AFFIRM_FLAG：传1，表示通过
	 *	AFFIRM_EMPID：check人person_id
	 *	AAI_URL:check结果查看页面url
	 */
	private void sendToLGEPCheckBatch(LinkedHashMap paramMap){
		List checkList = this.paTempSalesDAO.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/checkDimissionApplyInfo?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	/**
	 * 封装要插入的申请离职数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private int addNewAffirmorList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
		int result = 1;
		List addAffirmList = new ArrayList();
		List personList=new ArrayList();
		Enumeration e = request.getParameterNames() ;
		//获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			if(key.indexOf("dwz.person.personId")>-1){
				String dwzName="dwz.person.personId";
				if(key.equals("dwz.person.personId")){
					personList.add(0);	
				}else{
					personList.add(Integer.parseInt(key.substring(dwzName.length())));
				}
			}
		}
		//对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for(int i=0;i<personList.size();i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			String keyName="";
			String affirmLevel = personList.get(i).toString(); 
			if(affirmLevel.equals(0)){
				keyName="dwz.person.personId";
			}else{
				keyName="dwz.person.personId"+affirmLevel;
			}
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String personId=paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
			affirmMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE").toString());
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("CREATED_BY",request.getParameter("personId"));
			affirmMap.put("UPDATED_BY",request.getParameter("personId"));
			
			addAffirmList.add(affirmMap);
		}
		LinkedHashMap addAffirmorMap = new LinkedHashMap();
		int affirmorCnt = 0;
		for(int j=0;j<addAffirmList.size();j++){
			addAffirmorMap = (LinkedHashMap)addAffirmList.get(j);
			affirmorCnt = this.affirmApplyDao.getAffirmorCntByApplyNo(addAffirmorMap);
			if(affirmorCnt==0){
				this.affirmApplyDao.addNewApplyAffirmor(addAffirmorMap);
			}
		}
		
		return result;
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if("0".equals(paramMap.get("FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成\
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", Integer.parseInt(paramMap.get("dept_level").toString())-1);
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewEditionAffirmsList?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO"));
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewEditionAffirmsList?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewEditionAffirmsList?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO"));
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CREATED_BY", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", "1103");
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	

	
	/**
	 * check离职申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public int checkDimissionInfo(HttpServletRequest request){
		
		LinkedHashMap paramMap = new LinkedHashMap();
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", admin.getAdminID());
		}else{
			paramMap.put("UPDATED_BY", personId);
			paramMap.put("PERSON_ID", personId);
			paramMap.put("ESS_CHECK_NO", request.getParameter("ESS_CHECK_NO"));
		}
		paramMap.put("CHECK_CONTENT",request.getParameter("CHECK_CONTENT"));
		try {
			this.editionAffirmDao.updateDimissionEssCheck(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDimissionCheckList(HttpServletRequest request){
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = editionAffirmDao.getDimissionCheckList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = editionAffirmDao.getDimissionCheckList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author WENDI@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDimissionCheckCnt(HttpServletRequest request) {
		int retrunInt = 0;


		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);

		retrunInt = editionAffirmDao.getDimissionCheckCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addCheckAffirmDimission(HttpServletRequest request){
		// 页面提交数据
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				paramMap.put("PERSON_ID", admin.getAdminID());
				paramMap.put("CHECKOR_ID", paramMap.get("dwz.person.personId"));
				try {
					this.editionAffirmDao.addCheckAffirmDimission(paramMap);
					//给Check人发送邮件
					//this.mailManager.sendCheckMail(APPLY_TYPE_NO, paramMap.get("dwz.person.personId").toString());
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
				return 1;
	}
	
	/**
	 * 交接项目明细审批和查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmEditionItemList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception{
		String personId = request.getParameter("personId");
		String deptno = (String) paramMap.get("DEPTNO");
		paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		if(personId == null || "".equals(personId)){
			paramMap.put("interLanguage","zh");
			paramMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
			if(deptno!=null){
				paramMap.put("DEPTNO", deptno);
			}
		}else{
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			paramMap.put("interLanguage","zh");
			paramMap.put("APPLY_NO",request.getParameter("APPLY_NO"));
			if(deptno!=null){
				paramMap.put("DEPTNO", deptno);
			}
		}
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = editionAffirmDao.getAffirmEditionItemList(paramMap);
		return returnList;
	}
	
	/**
	 * 根据离职申请NO查询此次（个人/代）申请的所有人(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDimissionByApplyNoList(HttpServletRequest request) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = editionAffirmDao.getDimissionByApplyNoList(paramMap);
		
		return returnList;
	}
}
