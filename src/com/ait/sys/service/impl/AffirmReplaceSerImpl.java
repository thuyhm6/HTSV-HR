package com.ait.sys.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.impl.AffirmInfoToLGEPSerImpl;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AffirmReplaceDao;
import com.ait.sys.service.AffirmReplaceSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class AffirmReplaceSerImpl implements AffirmReplaceSer {
		Logger logger=Logger.getLogger(AffirmReplaceSerImpl.class);
		@Autowired
		private AffirmReplaceDao affirmreplacedao;
		@Autowired
		private AffirmInfoToLGEPSerImpl affirmInfoToLGEPSerImpl;
		/* 
		* Title: getAffirmReplaceList
		* Description:查询裁决者替换日志列表
		* @author 孙鹏  
		* @date 2014年12月1日 下午4:33:23  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#getAffirmReplaceList(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public List getAffirmReplaceList(HttpServletRequest request)
				throws Exception {
			List returnlist=new ArrayList();
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			Map paraMap=ObjectBindUtil.getRequestParamData(request,"seach_");
			paraMap.put("OLDCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));
			paraMap.put("NEWCHECK_PERSONID", request.getParameter("dwz.person.person_idnew"));
			//这样意味着页面选哪个法人都只会查当前法人
			paraMap.put("CPNY_ID",admin.getCpnyId());
			if(UiUtil.getPageNum(request)>0){
				returnlist=affirmreplacedao.getAffirmReplaceList(paraMap,UiUtil.getPageNum(request),UiUtil.getNumPerPage(request));
			}else{
				returnlist=affirmreplacedao.getAffirmReplaceList(paraMap);
			}
			return returnlist;
		}
		/* 
		* Title: getAffirmReplaceCnt
		* Description:查询裁决者替换日志列表行数
		* @author 孙鹏  
		* @date 2014年12月1日 下午4:33:25  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#getAffirmReplaceCnt(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int getAffirmReplaceCnt(HttpServletRequest request)
				throws Exception {
			Map paraMap =ObjectBindUtil.getRequestParamData(request, "seach_");
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			paraMap.put("OLDCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));
			paraMap.put("NEWCHECK_PERSONID", request.getParameter("dwz.person.person_idnew"));
			//这样意味着页面选哪个法人都只会查当前法人
			paraMap.put("CPNY_ID",admin.getCpnyId());
			int listcnt=0;
			listcnt=this.affirmreplacedao.getAffirmReplaceListCnt(paraMap);
			return listcnt;
		}
		/* 
		* Title: getAffirmEmpList
		* Description:查询裁决者emp和person等
		* @author 孙鹏  
		* @date 2014年12月2日 上午10:32:43  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#getAffirmEmpList(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public List getAffirmEmpList(HttpServletRequest request)
				throws Exception {
			List retrunList = new ArrayList() ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
			
			paramMap.put("CPNY_ID",admin.getCpnyId());
			
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
			
			retrunList = affirmreplacedao.getAffirmEmpList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
			
			return retrunList;
		}
		/* 
		* Title: getAffirmEmpCnt
		* Description:
		* @author 孙鹏  
		* @date 2014年12月2日 上午10:32:46  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#getAffirmEmpCnt(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int getAffirmEmpCnt(HttpServletRequest request) throws Exception {
			Map paramMap =ObjectBindUtil.getRequestParamData(request, "seach_");
			int listcnt=0;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CPNY_ID",admin.getCpnyId());
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
			listcnt=this.affirmreplacedao.getAffirmEmpCnt(paramMap);
			return listcnt;
		}
		/* 
		* Title: insertAffirmReplace
		* Description:裁决替换方法 废弃
		* @author 孙鹏  
		* @date 2014年12月2日 下午1:22:53  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#insertAffirmReplace(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int insertAffirmReplace(HttpServletRequest request)
				throws Exception {
			
			int returnInt =0;
			String final_no=null;
			String special_no = null;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("OLDCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));
			paramMap.put("NEWCHECK_PERSONID", request.getParameter("dwz.person.person_idnew"));
			paramMap.put("OLDFLAG", "0");
			paramMap.put("NEWFLAG", "0");

			String DEPTID=this.affirmreplacedao.getReplaceDeptid(paramMap);
			String HR_AFFRIM_NO=this.affirmreplacedao.getHRAffirmNo(paramMap);
			String ESS_AFFRIM_NO=this.affirmreplacedao.getESSAffirmNo(paramMap);
			paramMap.put("REPLACE_DEPTID", DEPTID);
			paramMap.put("HR_AFFRIM_NO", HR_AFFRIM_NO);
			paramMap.put("ESS_AFFRIM_NO", ESS_AFFRIM_NO);
			returnInt=this.affirmreplacedao.updateDepartmentAffirm(paramMap);
			if(!(returnInt>0)) return returnInt;
//			 暂时替换时不处理已经提交的申请。
//			returnInt=this.affirmreplacedao.updateHRAffirm(paramMap);
//			if(!(returnInt>0)) return returnInt;
//			returnInt=this.affirmreplacedao.updateESSAffirm(paramMap);
//			if(!(returnInt>0)) return returnInt;
//			
			
				returnInt=this.affirmreplacedao.insertAffirmReplace(paramMap);

			/**
			 * 创建委任：
			 * 	1、APROVAL_EMPID:当前审批人社号
			 *	   DELEGATE_EMPID：替换人社号
			 *	   START_DATE:创建时间 yyyy-mm-dd
			 */
			LinkedHashMap newparamMap=new LinkedHashMap();
			newparamMap.put("APROVAL_EMPID", paramMap.get("OLDCHECK_PERSONID"));
			newparamMap.put("DELEGATE_EMPID", paramMap.get("NEWCHECK_PERSONID"));
			newparamMap.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			this.affirmInfoToLGEPSerImpl.crateDelegate(newparamMap);
			
			return returnInt;
		}
		
		/* 
		* Title: insertAffirmReplace
		* Description:全部表裁决替换方法
		* @author 孙鹏  
		* @date 2015年2月4日 上午11:12:36  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#insertAffirmReplace(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int insertAffirmReplaceForALL(HttpServletRequest request) {
			
			int returnInt =0;
			String final_no="";
			String special_no = "";
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("OLDCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));
			paramMap.put("NEWCHECK_PERSONID", request.getParameter("dwz.person.person_idnew"));
			try{
				final_no = this.affirmreplacedao.getReplaceFinal(paramMap);
				special_no = this.affirmreplacedao.getReplaceSpecial(paramMap);
				paramMap.put("FINAL_NO", final_no);
				paramMap.put("SPECIAL_NO", special_no);
				if(!final_no.equals("")||!special_no.equals("")){
					returnInt=this.affirmreplacedao.insertAffirm(paramMap);					
				}
			}catch(Exception e){
				e.printStackTrace();
				return returnInt;
			}
			return returnInt;
		}
		
		/* 
		* Title: deleteAffirmReplace
		* Description:回退进行的替换。并且删除日志 废弃
		* @author 孙鹏  
		* @date 2014年12月2日 下午4:28:52  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#deleteAffirmReplace(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int deleteAffirmReplace(HttpServletRequest request)
				throws Exception {
			int returnInt =0;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnlist=affirmreplacedao.getAffirmReplaceList(paramMap);
			if(returnlist!=null){//将新旧id，标志对调
				paramMap=(Map) returnlist.get(0);
				if(paramMap.get("REPLACE_TYPE").equals("TH")){
				String newcheck_personid=(String) paramMap.get("NEWCHECK_PERSONID");
				paramMap.put("NEWCHECK_PERSONID", paramMap.get("OLDCHECK_PERSONID"));
				paramMap.put("OLDCHECK_PERSONID", newcheck_personid);
				paramMap.put("OLDFLAG", "0");
				}else{
				//终止新旧都是一个id
				paramMap.put("NEWCHECK_PERSONID", paramMap.get("OLDCHECK_PERSONID"));
				paramMap.put("OLDCHECK_PERSONID", paramMap.get("OLDCHECK_PERSONID"));
				paramMap.put("OLDFLAG", "2");
				}
				paramMap.put("NEWFLAG", "0");				
			}
			returnInt = this.affirmreplacedao.updateDepartmentAffirm(paramMap);
			/*暂时不处理已经提交的申请
			if(!(returnInt>0))return returnInt;
			returnInt=this.affirmreplacedao.updateESSAffirm(paramMap);
			if(!(returnInt>0))return returnInt;
			returnInt=this.affirmreplacedao.updateHRAffirm(paramMap);
			if(!(returnInt>0))return returnInt;
			*/
			returnInt=this.affirmreplacedao.deleteAffirmReplace(paramMap);
			/**
			 * 取消委任：
			 * 	1、APROVAL_EMPID:当前审批人社号
			 *	   DELEGATE_EMPID：替换人社号
			 *	   START_DATE:创建时间 yyyy-mm-dd
			 */
			LinkedHashMap newparamMap=new LinkedHashMap();
			newparamMap.put("APROVAL_EMPID", paramMap.get("OLDCHECK_PERSONID"));
			newparamMap.put("DELEGATE_EMPID", paramMap.get("NEWCHECK_PERSONID"));
			newparamMap.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			this.affirmInfoToLGEPSerImpl.deleteDelegate(newparamMap);
			return returnInt;
		}
		
		/* 
		* Title: deleteAffirmReplaceForAll
		* Description:替换裁决者的回退，将日志置为不可用
		* @author 孙鹏  
		* @date 2015年2月5日 上午10:22:46  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#deleteAffirmReplaceForAll(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int deleteAffirmReplaceForAll(HttpServletRequest request)
				 {
			int returnInt =0;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnlist=affirmreplacedao.getAffirmReplaceList(paramMap);
			try{
			if(returnlist!=null){//将新旧id，标志对调
				paramMap=(Map) returnlist.get(0);
				
				paramMap.put("NEWCHECK_PERSONID", paramMap.get("NEWCHECK_PERSONID"));
				paramMap.put("OLDCHECK_PERSONID", paramMap.get("OLDCHECK_PERSONID"));
				
			}
			returnInt = this.affirmreplacedao.deleteAffrim(paramMap);
			}catch (Exception e){
				e.printStackTrace();
				return returnInt;
			}
			return returnInt;
		}
		/* 
		* Title: insertStopAffirm
		* Description:裁决终止
		* @author 孙鹏  
		* @date 2014年12月3日 上午9:32:01  
		* @param request
		* @return
		* @throws Exception 
		* @see com.ait.sys.service.AffirmReplaceSer#insertStopAffirm(javax.servlet.http.HttpServletRequest) 
		*/
		@Override
		public int insertStopAffirm(HttpServletRequest request)
				throws Exception {
			int returnInt =0;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("OLDCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));//部门表的部门头需要修改
			paramMap.put("NEWCHECK_PERSONID", null);
//			paramMap.put("OLDFLAG", "0");
//			paramMap.put("NEWFLAG", "2");
			String DEPTID=this.affirmreplacedao.getReplaceDeptid(paramMap);
			paramMap.put("REPLACE_DEPTID", DEPTID);
			returnInt=this.affirmreplacedao.updateDepartmentAffirm(paramMap);
			if(!(returnInt>0)) return returnInt;
			returnInt=this.affirmreplacedao.insertAffirmReplace(paramMap);//插入日志表
			if(!(returnInt>0)) return returnInt;
			paramMap.put("NEWCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));//affirm表不需要改id
			returnInt=this.affirmreplacedao.updateHRAffirm(paramMap);
			if(!(returnInt>0)) return returnInt;
			returnInt=this.affirmreplacedao.updateESSAffirm(paramMap);
			/**
			 * 创建委任：
			 * 	1、APROVAL_EMPID:当前审批人社号
			 *	   DELEGATE_EMPID：替换人社号
			 *	   START_DATE:创建时间 yyyy-mm-dd
			 */
			LinkedHashMap newparamMap=new LinkedHashMap();
			newparamMap.put("APROVAL_EMPID", paramMap.get("OLDCHECK_PERSONID"));
			newparamMap.put("DELEGATE_EMPID", paramMap.get("NEWCHECK_PERSONID"));
			newparamMap.put("START_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			this.affirmInfoToLGEPSerImpl.crateDelegate(newparamMap);
			return returnInt;
		}
		
		@Override
		public int insertStopAffirmForAll(HttpServletRequest request)
				 {
			int returnInt =0;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("OLDCHECK_PERSONID", request.getParameter("dwz.person.person_idold"));//部门表的部门头需要修改
			paramMap.put("NEWCHECK_PERSONID", null);
			try{
				returnInt=this.affirmreplacedao.insertAffirm(paramMap);
			}catch (Exception e){
				e.printStackTrace();
				return returnInt;
			}
			return returnInt;
		}



}
