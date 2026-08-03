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

import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AffirmDao;
import com.ait.sys.service.AffirmSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class AffirmSerImpl implements AffirmSer {
	Logger logger = Logger.getLogger(AffirmSerImpl.class);

	@Autowired
	private AffirmDao affirmDao;

	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	
	@Override
	public Object getAffirmItemInfo(HttpServletRequest request) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmItemList(HttpServletRequest request,List typeList) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = affirmDao.getAffirmList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = affirmDao.getAffirmList(paramMap) ;
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
	public int getAffirmItemListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return affirmDao.getAffirmListCnt(paramMap) ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmItemList_final(HttpServletRequest request,List typeList) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			//retrunList = affirmDao.getAffirmList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
			retrunList = affirmDao.getAffirmList_final(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
			
		}else{
		//	retrunList = affirmDao.getAffirmList(paramMap) ;
			retrunList = affirmDao.getAffirmList_final(paramMap) ;
		}
		if(retrunList!=null){
			for(int i=0;i<retrunList.size();i++){
				Map map=(Map)retrunList.get(i) ;
				List allList=null;
				if(typeList!=null){
					for(int j=0;j<typeList.size();j++){
						List list=this.getItemDetail_final(request,((Map)retrunList.get(i)).get("PERSON_ID").toString(),((Map)typeList.get(j)).get("CODE_NO").toString());
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
	public int getAffirmItemListCnt_final(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//return affirmDao.getAffirmListCnt(paramMap) ;
		return affirmDao.getAffirmListCnt_final(paramMap) ;
	}
	
	
	@Override
	public int updateAffirmItemInfo(HttpServletRequest request) {
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
	public List getItemDetail_final(HttpServletRequest request,String paramStrPerson,String paramCode){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSONID", paramStrPerson);
		paramMap.put("CODENO", paramCode);
		return affirmDao.getItemDetail_final(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		   Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		   return affirmDao.getAffirmorList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateAffirmInfo(HttpServletRequest request)  {
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
				
				this.affirmDao.updateAffirmInfo(paramMap) ; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteAffirmLevelInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			this.affirmDao.deleteAffirmLevelInfo(paramMap) ;
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
	public List getAffirmorsByEmpOrDeptAndType(HttpServletRequest request){
		 Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		 return affirmDao.getAffirmorsByEmpOrDeptAndType(paramMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public List getAffirmorsByEmpOrDeptAndType_final(HttpServletRequest request){
		 Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// return affirmDao.getAffirmorsByEmpOrDeptAndType(paramMap);
		 return affirmDao.getAffirmorsByEmpOrDeptAndType_final(paramMap);
	}
	
	/**
	 * 增加决裁者
	 */
	@SuppressWarnings("unchecked")
	public int insertAffirmInfo(HttpServletRequest request){
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String[] personIds=request.getParameterValues("PERSON_IDS");
			String[] affirmTypeIds =request.getParameterValues("AFFIRM_TYPE_NO");
			String AFFIRM_TYPE_NO = "";
			for(int i=0;i<affirmTypeIds.length;i++){
				if( i == 0){
					AFFIRM_TYPE_NO += affirmTypeIds[i];
				}else{
					AFFIRM_TYPE_NO += "," + affirmTypeIds[i];
				}
				paramMap.put("AFFIRM_TYPE_NO", AFFIRM_TYPE_NO);
			}
			paramMap.put("USER_PERSON_ID", admin.getPersonId()) ;
			this.affirmDao.deleteAffirmInfo(paramMap);
			if(personIds != null && personIds.length > 0){
				for(int i=0;i<personIds.length;i++){
					paramMap.remove("AFFIRMOR_ID");
					paramMap.remove("AFFIRM_LEVEL");
					paramMap.put("AFFIRMOR_ID",personIds[i]);
					paramMap.put("AFFIRM_LEVEL",i+1);
					this.affirmDao.insertAffirmInfo(paramMap) ; 
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
	public int deleteAffirmInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			paramMap.put("deptNos", paramMap.get("AFFIRM_OBJECT"));
			this.affirmDao.deleteAffirmInfo(paramMap);
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
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getAdminID());
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
	public boolean updateaffirmAppoint(HttpServletRequest request) {
		Boolean flag=false;
		Map paramMap=ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("deptNo",admin.getDeptNo());
		 flag=this.affirmDao.updateaffirmAppoint(paramMap);
		return flag;
	}

	/* @author xuehaifei
	 * 委任信息查看
	 * 2014-7-13
	 * 
	 */
	@Override
	public List affirmAppointList(HttpServletRequest request) {
		List retrunList= new ArrayList();
		Map paramMap=ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("deptNo",admin.getDeptNo());
		retrunList=this.affirmDao.affirmAppointList(paramMap);
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
	

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)包含离职人员
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPidEidListFull(HttpServletRequest request) {
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
			retrunList =this.affirmDao.getPidEidListFull(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList =this.affirmDao.getPidEidListFull(paramMap) ;
		}
		return retrunList ;
	}
	

	/**
	 * 最终确认批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = affirmDao.getAffirmTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = affirmDao.getAffirmTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 最终确认批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getAffirmTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = affirmDao.getAffirmTempErrorCnt(paramMap);
		}else{
			retrunInt = affirmDao.getAffirmTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 最终确认批量excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelAffirmEmpData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_SY_AFFIRM_EXCEL_IMP.PR_IMPORT_AFFIRM_FINAL_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	

	public List getAffirmItemList_finalExcel(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunList = affirmDao.getAffirmList_finalExcel(paramMap) ;
		return retrunList ;
	}
}
