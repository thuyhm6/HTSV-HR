package com.ait.hrm.service.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.hrm.dao.JobTypeDao;
import com.ait.hrm.service.JobTypeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class JobTypeSerImpl implements JobTypeSer{

	Logger logger = Logger.getLogger(JobTypeSerImpl.class);
	
	@Autowired
	public JobTypeDao jobTypeDao;

	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("interLanguage", "zh");
		String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = jobTypeDao.getJobTypeList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = jobTypeDao.getJobTypeList(paramMap) ;
		}
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getJobTypeCnt(HttpServletRequest request) {
        Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
        String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		return jobTypeDao.getJobTypeCnt(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addJobTypeInfo(HttpServletRequest request) {
        LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.jobTypeDao.addJobTypeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateJobTypeInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.jobTypeDao.updateJobTypeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteJobTypeInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
        paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		try {
			String[] ids = request.getParameterValues("check_hr0034");
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					paramMap.put("id", id);
					this.jobTypeDao.deleteJobTypeInfo(paramMap);
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getJobType(HttpServletRequest request) {
        Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.jobTypeDao.getJobType(paramMap) ; 
	}

	@Override
	public List getJobTypeNameList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", "zh");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = new ArrayList() ;
		retrunList = jobTypeDao.getJobTypeNameList(paramMap) ;
		return retrunList ;
	}

	@Override
	public List getJobTypeGroupNameList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", "zh");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = new ArrayList() ;
		retrunList = jobTypeDao.getJobTypeGroupNameList(paramMap) ;
		return retrunList ;
	}

	@Override
	public int checkJobType(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(request.getParameter("NO")!=null && "".equals(request.getParameter("NO"))){
			paramMap.put("NO", request.getParameter("NO"));
		}
	    return this.jobTypeDao.checkJobType(paramMap) ;
	}
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = jobTypeDao.getJobTypeTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = jobTypeDao.getJobTypeTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = jobTypeDao.getJobTypeTempErrorCnt(paramMap);
		}else{
			retrunInt = jobTypeDao.getJobTypeTempCnt(paramMap);
		}
		return retrunInt;
	}
	
	/**
	 * 人员类型excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelJobTypeData(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "pkg_jobtype_excel_imp.pr_import_temp_jobtype_data");
		try {
			return this.jobTypeDao.importJobTypeFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	/**
	 * 根绝人员类型组CODE 获取人员类型
	 * @param paramMap
	 * @return
	 */
	public List getEmpJobTypeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		retrunList = jobTypeDao.getEmpJobTypeList(paramMap) ;
		return retrunList ;
	}
	
	/** 
	* @Title: getEmpForGroupToList 
	* @Description: TODO 根据人员类型组查询人员类型，为联动查询服务，11.20修改
	* @param @param object
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getEmpTypeForGroupToList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNYID", ("".equals(paramMap.get("CPNYID"))||null==paramMap.get("CPNYID"))?admin.getCpnyId():paramMap.get("CPNYID"));
		retrunList = jobTypeDao.getEmpTypeForGroupToList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeImportTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = jobTypeDao.getJobTypeImportTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = jobTypeDao.getJobTypeImportTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeImportTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = jobTypeDao.getJobTypeImportTempErrorCnt(paramMap);
		}else{
			retrunInt = jobTypeDao.getJobTypeImportTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 组装临促模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getJobTypelateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{

		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		
		//模版名称
		String name = "";
		aliasNameList.add("公司ID *");
		aliasNameList.add("人员类型组NO *");
		aliasNameList.add("人员类型 NO*");
		aliasNameList.add("验证结果");
		List jobTypeTempList = this.jobTypeDao.getJobTypeImportTempList(paramMap, -1, -1);
		for(int i=0;i<jobTypeTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)jobTypeTempList.get(i);
			map.put("CELL0", map1.get("CPNY_ID") == null ? "" : map1.get("CPNY_ID"));
			map.put("CELL1", map1.get("JOBTYPE_GROUP_NO") == null ? "" : map1.get("JOBTYPE_GROUP_NO"));
			map.put("CELL3", map1.get("JOBTYPE_NO") == null ? "" : map1.get("JOBTYPE_NO"));
			map.put("CELL10", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			mapNameList.add("公司名称参考");
			mapNameList.add("人员类型组参考");
			mapNameList.add("人员类型参考");
			mapList.add("SELECT '[' || T.CPNY_ID || ']' || T.CPNY_LOCATION CONTENT FROM HR_COMPANY T");
			mapList.add("SELECT '[' || T.CODE_NO || ']' || U.CONTENT CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 211807");
			mapList.add("SELECT '[' || T.CODE_NO || ']' || U.CONTENT CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 1368");
			name = "JOB_TYPE_DATE";
		}
		return name;
	}
}
 