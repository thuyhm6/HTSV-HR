package com.ait.sys.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AffirmSpecialDao;
import com.ait.sys.service.AffirmSpecialSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class AffirmSpecialSerImpl implements AffirmSpecialSer {
	Logger logger = Logger.getLogger(AffirmSpecialSerImpl.class);

	@Autowired
	private AffirmSpecialDao affirmDao;

	@Override
	public Object getAffirmSpecialItemInfo(HttpServletRequest request) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmSpecialItemList(HttpServletRequest request,List typeList) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = affirmDao.getAffirmSpecialList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = affirmDao.getAffirmSpecialList(paramMap) ;
		}
		if(retrunList!=null){
			for(int i=0;i<retrunList.size();i++){
				Map map=(Map)retrunList.get(i) ;
				List allList=null;
				if(typeList!=null){
					for(int j=0;j<typeList.size();j++){
						List list=this.getItemDetail(request,((Map)retrunList.get(i)).get("PERSON_ID").toString(),((Map)typeList.get(j)).get("CODE_NO").toString());
						if(allList==null){
							allList=list;
						}else{
							allList.addAll(list);
						}
					}
				}
				map.put("detailList", allList);
			}
		}
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getAffirmSpecialItemListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return affirmDao.getAffirmSpecialListCnt(paramMap) ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmSpecialItemList_special(HttpServletRequest request,List typeList) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			//retrunList = affirmDao.getAffirmList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
			retrunList = affirmDao.getAffirmSpecialList_special(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
			
		}else{
		//	retrunList = affirmDao.getAffirmList(paramMap) ;
			retrunList = affirmDao.getAffirmSpecialList_special(paramMap) ;
		}
		if(retrunList!=null){
			for(int i=0;i<retrunList.size();i++){
				Map map=(Map)retrunList.get(i) ;
				List allList=null;
				if(typeList!=null){
					for(int j=0;j<typeList.size();j++){
						List list=this.getItemDetail_special(request,((Map)retrunList.get(i)).get("PERSON_ID").toString(),((Map)typeList.get(j)).get("CODE_NO").toString());
						if(allList==null){
							allList=list;
						}else{
							allList.addAll(list);
						}
					}
				}
				map.put("detailList", allList);
			}
		}
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getAffirmSpecialItemListCnt_special(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//return affirmDao.getAffirmListCnt(paramMap) ;
		return affirmDao.getAffirmSpecialListCnt_special(paramMap) ;
	}
	
	
	@Override
	public int updateAffirmSpecialItemInfo(HttpServletRequest request) {
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeListByParentCode(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PARENT_CODE_NO", "123313");
		if (UiUtil.getPageNum(request) > 0){
			retrunList = affirmDao.getCodeListByParentCode(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = affirmDao.getCodeListByParentCode(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeListByParentCodeAll(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
	//	paramMap.put("PARENT_CODE_NO", "14");
		paramMap.put("PARENT_CODE_NO", "16413");
		retrunList = affirmDao.getCodeListByParentCode(paramMap) ;
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getCodeListCntByParentCode(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PARENT_CODE_NO", "14");
		return affirmDao.getCodeListCntByParentCode(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getItemDetail(HttpServletRequest request,String paramStrPerson,String paramCode){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSONID", paramStrPerson);
		paramMap.put("CODENO", paramCode);
		return affirmDao.getItemDetail(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getItemDetail_special(HttpServletRequest request,String paramStrPerson,String paramCode){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSONID", paramStrPerson);
		paramMap.put("CODENO", paramCode);
		return affirmDao.getItemDetail_special(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List getAffirmorSpecialList(HttpServletRequest request) throws Exception {
		   Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		   return affirmDao.getAffirmorSpecialList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateAffirmSpecialInfo(HttpServletRequest request)  {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String affirmLevelInfo=paramMap.get("AFFIRM_LEVEL").toString();
			String[] infoArr=affirmLevelInfo.split(";");
			for(int i=0;i<infoArr.length;i++){
				String[] affirmInfo=infoArr[i].split(",");
				String level=affirmInfo[0];
				String empId=affirmInfo[1];
				String type=affirmInfo[2];
				 
				paramMap.put("LEVEL", level);
				paramMap.put("EMPID", empId);
				paramMap.put("TYPE", type);
				
				this.affirmDao.updateAffirmSpecialInfo(paramMap) ; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteAffirmSpecialLevelInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			this.affirmDao.deleteAffirmSpecialLevelInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1; 
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptTreeList(HttpServletRequest request){
		  Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		  AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		  paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		  return affirmDao.getDeptTreeList(paramMap);
	}
 
	@SuppressWarnings("unchecked")
	public List getEmpByDeptId(HttpServletRequest request){
		 Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		 return affirmDao.getEmpByDeptId(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType(HttpServletRequest request){
		 Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		 return affirmDao.getAffirmorsSpecialByEmpOrDeptAndType(paramMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsSpecialByEmpOrDeptAndType_special(HttpServletRequest request){
		 Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// return affirmDao.getAffirmorsByEmpOrDeptAndType(paramMap);
		 return affirmDao.getAffirmorsSpecialByEmpOrDeptAndType_special(paramMap);
	}
	
	/**
	 * 增加决裁者
	 */
	@SuppressWarnings("unchecked")
	public int insertAffirmSpecialInfo(HttpServletRequest request){
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//获取所有的决裁者
			String[] personIds=request.getParameterValues("PERSON_IDS");
			//获取所有的考勤类型
			String[] affirmTypeIds =request.getParameterValues("AFFIRM_TYPE_NO");
			//获取所有的决裁对象（是针对个人的）
			String[] personIdss=request.getParameterValues("PERSON_IDSS");
			String AFFIRM_TYPE_NO = "";
			for(int i=0;i<affirmTypeIds.length;i++){
				if( i == 0){
					AFFIRM_TYPE_NO += affirmTypeIds[i];
				}else{
					AFFIRM_TYPE_NO += "," + affirmTypeIds[i];
				}
				paramMap.put("AFFIRM_TYPE_NO", AFFIRM_TYPE_NO);
			}
			
			String persons = "";
			if(personIdss!=null && personIdss.length>0){
			    for(int i=0;i<personIdss.length;i++){
				    if( i == 0){
					    persons += "'" + personIdss[i] + "' ";
				    }else{
					    persons += ",'" + personIdss[i] + "' ";
				    }
			    }
			}
			
			if(persons!=null && !"".equals(persons)){
				String person_object = "";
				if(!"".equals(paramMap.get("deptNos")) && paramMap.get("deptNos")!=null){
				    person_object = paramMap.get("deptNos") + "," + persons;
				}else{
					person_object = persons.toString();
				}
				paramMap.put("deptNos", person_object);
			}
			paramMap.put("USER_PERSON_ID", admin.getPersonId()) ;
			paramMap.put("AFFIRM_OBJECT", paramMap.get("deptNos"));
			this.affirmDao.deleteAffirmSpecialInfo(paramMap);
			if(personIds != null && personIds.length > 0){
				for(int i=0;i<personIds.length;i++){
					paramMap.remove("AFFIRMOR_ID");
					paramMap.remove("AFFIRM_LEVEL");
					paramMap.put("AFFIRMOR_ID",personIds[i]);
					paramMap.put("AFFIRM_LEVEL",i+1);
					this.affirmDao.insertAffirmSpecialInfo(paramMap) ; 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmployeeByObjectId(HttpServletRequest request){
		  Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		  return affirmDao.getEmployeeByObjectId(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteAffirmSpecialInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.affirmDao.deleteAffirmSpecialInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		String empNameStr=request.getParameter("LOCAL_NAME");
		try {
			paramMap.put("LOCAL_NAME", java.net.URLDecoder.decode(empNameStr,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList =this.affirmDao.getPidEidList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList =this.affirmDao.getPidEidList(paramMap) ;
		}
		return retrunList ;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonCntByEmpid(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		retrunList =this.affirmDao.getPersonCntByEmpid(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidList2(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		String empNameStr=request.getParameter("LOCAL_NAME");
		try {
			paramMap.put("LOCAL_NAME", java.net.URLDecoder.decode(empNameStr,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList =this.affirmDao.getPidEidList2(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList =this.affirmDao.getPidEidList2(paramMap) ;
		}
		return retrunList ;
	}
	
	
	/**
	 * 根据EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpIdList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList =this.affirmDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = this.affirmDao.getEmpIdList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取员工信息个数(For the number of staff information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEmpIdListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = this.affirmDao.getEmpIdListCnt(paramMap) ;
		return retrunInt ;
	}
	/* @author xuehaifei
	 * 裁决委任
	 * 2014-7-11
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateAffirmSpecialAppoint(HttpServletRequest request) {
		Boolean flag=false;
		Map paramMap=ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("deptNo",admin.getDeptNo());
		 flag=this.affirmDao.updateAffirmSpecialAppoint(paramMap);
		return flag;
	}

	/* @author xuehaifei
	 * 委任信息查看
	 * 2014-7-13
	 * 
	 */
	@Override
	public List affirmSpecialAppointList(HttpServletRequest request) {
		List retrunList= new ArrayList();
		Map paramMap=ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("deptNo",admin.getDeptNo());
		retrunList=this.affirmDao.affirmSpecialAppointList(paramMap);
        return retrunList;
	}

	/* @author xuehaifei
	 * 
	 * 2014-7-18
	 * 
	 */
	@Override
	public Boolean cancleAppoint(HttpServletRequest request) {
		Boolean flag=false;
		Map paramMap=ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("deptNo",admin.getDeptNo());
		flag=this.affirmDao.cancleAppoint(paramMap);
		return flag;
	}	
	

	public List getAffirmItemList_specialExcel(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunList = affirmDao.getAffirmSpecialList_specialExcel(paramMap) ;
		return retrunList ;
	}
}
