package com.ait.hrm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.RecruitManageSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: RecruitManageCtroller.java 
 * @Create by: wzc(weizhengchen@ait.net.cn)
 * @version 5.5
 */
@Controller
@RequestMapping(value = "/hrm/recruitManage")
public class RecruitManageCtroller {
	Logger logger = Logger.getLogger(RecruitManageCtroller.class);

	@Autowired
	private RecruitManageSer recruitManageSer;
	
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	/**
	 * 招聘流程
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewRecruitIndex")
	public ModelAndView viewOrgIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/hrm/recruitManage/viewRecruitIndex", modelMap);
	}
	
	/**
	 * 招聘发令
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResumeList")
	public ModelAndView viewResumeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.recruitManageSer.viewResumeList(request);
		modelMap.put("resumeList", resumeList);

		LinkedHashMap param = null;
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("resumeSize", resumeList.size());
		}else{
			modelMap.put("resumeSize", 0);
		}
		return new ModelAndView("/hrm/recruitManage/viewResumeList", modelMap);
	}*/
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRecruitList")
	public ModelAndView viewRecruitList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
		modelMap.put("viewRecruitList", viewRecruitList);

		LinkedHashMap param = null;
		
		if(viewRecruitList!=null && viewRecruitList.size() > 0){
			param = (LinkedHashMap)viewRecruitList.get(0);
			modelMap.put("PERSON_ID", param.get("PERSON_ID"));
			modelMap.put("viewRecruitSize", viewRecruitList.size());
		}else{
			modelMap.put("viewRecruitSize", 0);
		}
		
		return new ModelAndView("/hrm/recruitManage/viewRecruitList", modelMap);
	}

	/**
	 * 概要改编修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddRecruitInfoPanel")
	public ModelAndView viewAddRecruitInfoPanel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else if("3".equals(currentIndex)){
			modelMap.put("currentIndex", 3);
		}else if("4".equals(currentIndex)){
			modelMap.put("currentIndex", 4);
		}else if("5".equals(currentIndex)){
			modelMap.put("currentIndex", 5);
		}else{
			modelMap.put("currentIndex", 0);
		}
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("SEQ2", request.getParameter("SEQ2"));
		modelMap.put("SEQ3", request.getParameter("SEQ3"));
		modelMap.put("SEQ4", request.getParameter("SEQ4"));

		return new ModelAndView("/hrm/recruitManage/viewAddRecruitInfoPanel", modelMap);
	}
	
	/**
	 * 历史组织 详细信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddRecruitInfo")
	public ModelAndView viewAddRecruitInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		String personId = request.getParameter("PERSON_ID");
		String seq = request.getParameter("seq");
		if("0".equals(currentIndex)){
			List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
			LinkedHashMap paramData = ObjectBindUtil.getRequestParamData(request,"seach_");
			List supplierList = this.recruitManageSer.viewPersonSupplier(paramData);
			LinkedHashMap param = null;
			if(viewRecruitList!=null && viewRecruitList.size() > 0){
				param = (LinkedHashMap)viewRecruitList.get(0);
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "HR_RESUME");
				fileParam.put("APPLY_NO", param.get("PERSON_ID"));
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				param.put("fileList", fileList);
			}
			modelMap.put("supplierList", supplierList);
			modelMap.put("recruitInfo", param);
			modelMap.put("currentIndex", 0);
		}else if("1".equals(currentIndex)){
			List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
			LinkedHashMap param = null;
			if(viewRecruitList!=null && viewRecruitList.size() > 0){
				param = (LinkedHashMap)viewRecruitList.get(0);
			}
			modelMap.put("recruitInfo", param);
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			List viewEducationList = this.recruitManageSer.viewEducationList(request);
			List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
			LinkedHashMap param = null;
			if(viewRecruitList!=null && viewRecruitList.size() > 0){
				param = (LinkedHashMap)viewRecruitList.get(0);
			}
			modelMap.put("employee_type", param);
			if(viewEducationList!=null && viewEducationList.size() > 0){
				if(seq != null && !"".equals(seq)){
					for(int i=0;i<viewEducationList.size();i++){
						LinkedHashMap singleMap = (LinkedHashMap)viewEducationList.get(i);
						if(seq.equals(StringUtil.checkNull(singleMap.get("SEQ")))){
							param = singleMap;
							break;
						}
					}
				}else{
					param = (LinkedHashMap)viewEducationList.get(0);
				}
				if(param != null){
					//加载教育信息附件
					LinkedHashMap fileParam = new LinkedHashMap();
					fileParam.put("APPLY_TYPE", "EDU_RECRUIT");
					fileParam.put("APPLY_NO", param.get("SEQ"));
					List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
					param.put("fileList",fileList);
				}
			}
			modelMap.put("recruitInfo", param);
			modelMap.put("viewEducationList", viewEducationList);
			modelMap.put("PERSON_ID", personId);
			modelMap.put("currentIndex", 2);
		}else if("3".equals(currentIndex)){
			List viewWorkExperienceList = this.recruitManageSer.viewWorkExperienceList(request);
			List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
			LinkedHashMap param = null;
			if(viewRecruitList!=null && viewRecruitList.size() > 0){
				param = (LinkedHashMap)viewRecruitList.get(0);
			}
			modelMap.put("employee_type", param);
			if(viewWorkExperienceList!=null && viewWorkExperienceList.size() > 0){
				if(seq != null && !"".equals(seq)){
					for(int i=0;i<viewWorkExperienceList.size();i++){
						LinkedHashMap singleMap = (LinkedHashMap)viewWorkExperienceList.get(i);
						if(seq.equals(StringUtil.checkNull(singleMap.get("SEQ")))){
							param = singleMap;
							break;
						}
					}
				}else{
					param = (LinkedHashMap)viewWorkExperienceList.get(0);
				}
			}
			modelMap.put("recruitInfo", param);
			modelMap.put("viewWorkExperienceList", viewWorkExperienceList);
			modelMap.put("PERSON_ID", personId);
			modelMap.put("currentIndex", 3);
		}else if("4".equals(currentIndex)){
			List viewFamilyList = this.recruitManageSer.viewFamilyList(request);
			List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
			LinkedHashMap param = null;
			if(viewRecruitList!=null && viewRecruitList.size() > 0){
				param = (LinkedHashMap)viewRecruitList.get(0);
			}
			modelMap.put("employee_type", param);
			if(viewFamilyList!=null && viewFamilyList.size() > 0){
				if(seq != null && !"".equals(seq)){
					for(int i=0;i<viewFamilyList.size();i++){
						LinkedHashMap singleMap = (LinkedHashMap)viewFamilyList.get(i);
						if(seq.equals(StringUtil.checkNull(singleMap.get("SEQ")))){
							param = singleMap;
							break;
						}
					}
				}else{
					param = (LinkedHashMap)viewFamilyList.get(0);
				}
			}
			modelMap.put("recruitInfo", param);
			modelMap.put("viewFamilyList", viewFamilyList);
			modelMap.put("PERSON_ID", personId);
			modelMap.put("currentIndex", 4);
		}else{
			List viewRecruitList = this.recruitManageSer.viewRecruitList(request);
			LinkedHashMap param = null;
			if(viewRecruitList!=null && viewRecruitList.size() > 0){
				param = (LinkedHashMap)viewRecruitList.get(0);
			}
			modelMap.put("recruitInfo", param);
			modelMap.put("currentIndex", 5);
		}
		
		return new ModelAndView("/hrm/recruitManage/viewAddRecruitInfo", modelMap);
	}

	/**
	 * 添加招聘发令信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRecruitInfo")
	@ResponseBody
	public Map addRecruitInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyID = admin.getCpnyId();

		if("HAE".equals(cpnyID) && !"".equals(StringUtil.checkNull(paramMap.get("EMPID"))) ){
			int result = this.recruitManageSer.IS_EXISTS_EMPID(request);
			if(result > 0 ){
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("hrm.recruitManage.EMPID_EXISTENCE.Z", request));
				return map;
			}
		}
		if("HTSV".equals(cpnyID)){
			int result = this.recruitManageSer.IS_EXISTS_EMPID(request);
			if(result > 0 ){
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("hrm.recruitManage.EMPID_EXISTENCE.Z", request));
				return map;
			}
		}
		
		int result = this.recruitManageSer.addRecruitInfo(request);
		if (result == 1) {
			if("SPC_SH".equals(cpnyID)){
				int result1 = this.recruitManageSer.IS_EXISTS_NAME(request);
				if(result1 > 1 ){
					map.put("statusCode", "200");
					map.put("message", "保存成功（姓名重复）"); 
					map.put("divId", "viewRecruitList_unit");
					map.put("divIdUrl", "/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=" + paramMap.get("PERSON_ID") +
											"&currentIndex=" + paramMap.get("currentIndex") +
											"&SEQ"  + paramMap.get("currentIndex") + "=" + paramMap.get("SEQ"));
					map.put("formId", "viewRecruitList_Form");
				}else {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));// 保存成功
					map.put("divId", "viewRecruitList_unit");
					map.put("divIdUrl", "/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=" + paramMap.get("PERSON_ID") +
											"&currentIndex=" + paramMap.get("currentIndex") +
											"&SEQ"  + paramMap.get("currentIndex") + "=" + paramMap.get("SEQ"));
					map.put("formId", "viewRecruitList_Form");
				}
			} else {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
				map.put("divId", "viewRecruitList_unit");
				map.put("divIdUrl", "/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=" + paramMap.get("PERSON_ID") +
										"&currentIndex=" + paramMap.get("currentIndex") +
										"&SEQ"  + paramMap.get("currentIndex") + "=" + paramMap.get("SEQ"));
				map.put("formId", "viewRecruitList_Form");
			}
			
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	
	/**
	 * 删除招聘发令
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRecruitInfo")
	@ResponseBody
	public Map deleteRecruitInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.deleteRecruitInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("formId", "viewRecruitList_Form");
			map.put("divId", "viewRecruitList_unit");
			map.put("divIdUrl", "/hrm/recruitManage/viewAddRecruitInfoPanel?PERSON_ID=" + paramMap.get("PERSON_ID") +
									"&currentIndex=" + paramMap.get("currentIndex"));
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}
	
	/**
	 * 招聘发令确认 、拉回
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeRecruit")
	@ResponseBody
	public Map executeRecruit(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultEmpid = this.recruitManageSer.IS_EXISTS_EMPID(request);
		if(resultEmpid > 0 ){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hrm.recruitManage.EMPID_EXISTENCE.Z" + request.getParameter("EMPID"), request));
			return map;
		}
		
		String result = this.recruitManageSer.executeRecruit(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.org.execute_success", request));//"执行成功"
			if("CONFIRM_BATCH".equals(request.getParameter("type") )){
				map.put("formId", "viewRecruitBatchListForm");
			}else if("EXP_BATCH".equals(request.getParameter("type") )){
				map.put("formId", "viewExperienceBatchListForm");
			}else{
				map.put("formId", "viewRecruitList_Form");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	

	/**
	 * ajax操作sql
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doSql")
	@ResponseBody
	public Map doSql(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List result = this.recruitManageSer.doSql(request);
		map.put("result", result);
		return map;
	}
	
	/**
	 * 
	 * 附件上传
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	public String upload(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String PERSON_ID = request.getParameter("PERSON_ID");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(20971520);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch(MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest){
			multipartRequest = (MultipartHttpServletRequest)request;
		} else {
			return null;
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		 /**构建图片保存的目录**/    
		 String logoPathDir = "/resources/photo/"+admin.getCpnyId();// dateformat.format(new Date());
		 /**得到图片保存目录的真实路径**/    
		 String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir); 
		/**根据真实路径创建目录**/    
		 File logoSaveFile = new File(logoRealPathDir);     
		 if(!logoSaveFile.exists()){
			 logoSaveFile.mkdirs();
		 }
		/**页面控件的文件流**/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		/**获取文件的后缀**/
		String suffix = multipartFile.getOriginalFilename().substring
		(multipartFile.getOriginalFilename().lastIndexOf("."));
		/**拼成完整的文件保存路径加文件**/
		String fileName = logoRealPathDir + File.separator   + PERSON_ID + suffix;
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			//上传成功，保存记录
			this.recruitManageSer.updateRecruitPhotoInfo(request, logoPathDir + "/"   + PERSON_ID + suffix);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("photoPath", logoPathDir + "/"   + PERSON_ID + suffix);
		 } catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		 } catch (IOException e) {
			e.printStackTrace();
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String content = mapper.writeValueAsString(map);
		 response.getWriter().write(content);
		 return null;
	}

	/**
	 * 招聘发令
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRecruitBatchList")
	public ModelAndView viewRecruitBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramData = ObjectBindUtil.getRequestParamData(request,"seach_");
		List viewRegisterInfoList = this.recruitManageSer.viewRegisterInfoList(request,1);
		modelMap.put("viewRegisterInfoList", viewRegisterInfoList);
		if(paramData.get("SEQ") == null || "".equals(StringUtil.checkNull(paramData.get("SEQ")))){
			if(viewRegisterInfoList!=null && viewRegisterInfoList.size() > 0){
				LinkedHashMap param = (LinkedHashMap)viewRegisterInfoList.get(0);
				modelMap.put("SEQ", param.get("SEQ"));
				modelMap.put("ACTIVITY", param.get("ACTIVITY"));
				paramData.put("SEQ", param.get("SEQ"));
			}
		}else{
			for(int i=0;i<viewRegisterInfoList.size();i++){
				Map subMap = (LinkedHashMap)viewRegisterInfoList.get(i);
				if(StringUtil.checkNull(paramData.get("SEQ")).equals(StringUtil.checkNull(subMap.get("SEQ")))){
					modelMap.put("SEQ", subMap.get("SEQ"));
					modelMap.put("ACTIVITY", subMap.get("ACTIVITY"));
					paramData.put("SEQ", subMap.get("SEQ"));
					break;
				}
			}
		}
		if(request.getParameter("FLAG") == null){
			this.recruitManageSer.deleteEmptyRecruitInfo(request);
		}else{
			this.recruitManageSer.addEmptyRecruitInfo(request);
		}
		List viewRecruitBatchList = this.recruitManageSer.viewRecruitBatchList(paramData);
		modelMap.put("viewRecruitBatchList", viewRecruitBatchList);
		modelMap.put("viewRecruitBatchListCnt", viewRecruitBatchList == null ? 0 : viewRecruitBatchList.size());
		
		/*String PARENT_CODE_NO_SQL = "SELECT T.CODE_NO || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY"
			+ " WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' "
			+ " AND T.ACTIVITY = 1 "
			+ " AND SY.NO IN ( '";*/
		String CODE_NO_SQL = "SELECT PAY_STEP || ' > ' || GET_GLOBAL_NAME(PAY_STEP, '" + admin.getLanguage() + "' " 
            + " ) DESCRIPTION, PAY_STEP, GET_GLOBAL_NAME(PAY_STEP, '" + admin.getLanguage() + "' " 
            + " ) CODENAME FROM PA_GRADE_HAOFENG"
			+ " WHERE CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND ACTIVITY = 1 "
			+ " AND POST_GRADE_NO IN (SELECT CODE_NO FROM SY_CODE WHERE PARENT_CODE_NO =  '";
		String PARENT_CODE_NO_SQL = "SELECT NVL(T.DESCRIPTION,T.CODE_NO) || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY"
			+ " WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' "
			+ " AND T.ACTIVITY = 1 "
			+ " AND T.PARENT_CODE_NO IN ( SELECT CODE_NO FROM SY_CODE WHERE PARENT_CODE_NO = '";
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String COST_CENTER_SQL = "SELECT CODE_NO || ' > ' || CODE_ENG_NAME DESCRIPTION, CODE_NO,CODE_ENG_NAME CODENAME FROM ORG_COST_CENTER WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";

		modelMap.put("rs" , JsonUtil.writeInternal(empInfoSer.getCodeList("1359", request))); //入社区分
		modelMap.put("rsxj" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(PARENT_CODE_NO_SQL + "1359' )"))); //入社详细区分
		modelMap.put("dept" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(DEPT_NO_SQL)));  //部门
		modelMap.put("dj" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(PARENT_CODE_NO_SQL + "14015812' )")));  //等级
		modelMap.put("zyyw" , JsonUtil.writeInternal(empInfoSer.getCodeList("400098", request)));  //主要业务
		modelMap.put("zz" , JsonUtil.writeInternal(empInfoSer.getCodeList("14014036", request)));  //职责
		modelMap.put("yglx" , JsonUtil.writeInternal(empInfoSer.getCodeList("13864", request)));  //员工类型
		modelMap.put("cbzx", JsonUtil.writeInternal(empInfoSer.getCodeListBySql(COST_CENTER_SQL))); //成本中心
		modelMap.put("zzxl" , JsonUtil.writeInternal(empInfoSer.getCodeList("13769", request)));  //学历
		modelMap.put("xb" , JsonUtil.writeInternal(empInfoSer.getCodeList("1324", request)));  //性别
		modelMap.put("gj" , JsonUtil.writeInternal(empInfoSer.getCodeList("870", request)));  //国籍
		modelMap.put("minzu" , JsonUtil.writeInternal(empInfoSer.getCodeList("210942", request)));//民族
		modelMap.put("jhqf" , JsonUtil.writeInternal(empInfoSer.getCodeList("1709", request)));  //结婚区分
		modelMap.put("zq" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015812", request)));  //职群
		modelMap.put("nxdj" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(CODE_NO_SQL + "14015815' )")));  //年薪等级
		modelMap.put("bycj" , JsonUtil.writeInternal(empInfoSer.getCodeList("14014324", request)));  //毕业成绩
		modelMap.put("wynl" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015514", request)));  //外语能力
		modelMap.put("banzu" , JsonUtil.writeInternal(empInfoSer.getCodeList("400223", request)));  //班组
		
		return new ModelAndView("/hrm/recruitManage/viewRecruitBatchList", modelMap);
	}
	
	/**
	 * 统一招聘发令注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddRegisterInfo")
	public ModelAndView viewAddRegisterInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("FLAG", request.getParameter("FLAG"));
		return new ModelAndView("/hrm/recruitManage/viewAddRegisterInfo", modelMap);
	}
	
	/**
	 * 统一招聘发令注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRegisterInfo")
	@ResponseBody
	public Map addRegisterInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.addRegisterInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			if("1".equals(request.getParameter("FLAG"))){
				map.put("navTabId", "hr0203");
			}else{
				map.put("navTabId", "hr0205");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 单条删除批量导入的人员信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRecruitBatchInfo")
	@ResponseBody
	public Map deleteRecruitBatchInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.deleteRecruitBatchInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request) );//"删除成功"
			map.put("formId", "viewRecruitBatchListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request) );//"删除失败"
		}
		return map;
	}

	/**
	 * 发令概要管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResumeList")
	public ModelAndView viewResumeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.recruitManageSer.viewResumeList(request);
		modelMap.put("resumeList", resumeList);

		LinkedHashMap param = null;
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("resumeSize", resumeList.size());
		}else{
			modelMap.put("resumeSize", 0);
		}
		return new ModelAndView("/hrm/recruitManage/viewResumeList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddResumeInfo")
	public ModelAndView viewAddResumeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.recruitManageSer.viewResumeList(request);

		LinkedHashMap param = new LinkedHashMap();
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);
		}
		//param.put("REGISTER_INFO", DateUtil.getSysdateStr("dd.MM.yyyy"));
		modelMap.put("resumeInfo", param);

		return new ModelAndView("/hrm/recruitManage/viewAddResumeInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addResumeInfo")
	@ResponseBody
	public Map addResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.addResumeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0206");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteResumeInfo")
	@ResponseBody
	public Map deleteResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.deleteResumeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request) );//"删除成功"
			map.put("navTabId", "hr0206");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request) );//"删除失败"
		}
		return map;
	}
	

	/**
	 * 统一发令
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewExperienceBatchList")
	public ModelAndView viewExperienceBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap paramData = ObjectBindUtil.getRequestParamData(request,"seach_");
		List viewRegisterInfoList = this.recruitManageSer.viewRegisterInfoList(request,2);
		modelMap.put("viewRegisterInfoList", viewRegisterInfoList);
		if(paramData.get("SEQ") == null || "".equals(StringUtil.checkNull(paramData.get("SEQ")))){
			if(viewRegisterInfoList!=null && viewRegisterInfoList.size() > 0){
				LinkedHashMap param = (LinkedHashMap)viewRegisterInfoList.get(0);
				modelMap.put("SEQ", param.get("SEQ"));
				modelMap.put("ACTIVITY", param.get("ACTIVITY"));
				paramData.put("SEQ", param.get("SEQ"));
			}
		}else{
			for(int i=0;i<viewRegisterInfoList.size();i++){
				Map subMap = (LinkedHashMap)viewRegisterInfoList.get(i);
				if(StringUtil.checkNull(paramData.get("SEQ")).equals(StringUtil.checkNull(subMap.get("SEQ")))){
					modelMap.put("SEQ", subMap.get("SEQ"));
					modelMap.put("ACTIVITY", subMap.get("ACTIVITY"));
					paramData.put("SEQ", subMap.get("SEQ"));
					break;
				}
			}
		}
		if(request.getParameter("FLAG") == null){
			this.recruitManageSer.deleteExperienceInfo(request);
		}
		List viewExperienceBatchList = this.recruitManageSer.viewExperienceBatchList(paramData);
		modelMap.put("viewExperienceBatchList", viewExperienceBatchList);
		modelMap.put("viewExperienceBatchListCnt", viewExperienceBatchList == null ? 0 : viewExperienceBatchList.size());
		
		return new ModelAndView("/hrm/recruitManage/viewExperienceBatchList", modelMap);
	}
	
	/**
	 * 单条删除批量导入的发令信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteExperienceBatchInfo")
	@ResponseBody
	public Map deleteExperienceBatchInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.deleteExperienceBatchInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message",TipMessage.getTipMessage(
					"alert.message.delete_success", request) );//"删除成功"
			map.put("formId", "viewExperienceBatchListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage(
					"alert.message.delete_fail", request) );//"删除失败"
		}
		return map;
	}
	
	/**
	 * 统一录用发令  保存
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRecruitBatchInfo")
	@ResponseBody
	public Map addRecruitBatchInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyID = admin.getCpnyId();
		int resultEmpid = this.recruitManageSer.IS_EXISTS_EMPID1(request);
		if(resultEmpid > 0 ){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hrm.recruitManage.EMPID_EXISTENCE.Z", request));
			return map;
		}
		
		
		int result = this.recruitManageSer.addRecruitBatchInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0203");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	
	/**
	 * 发令检索
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewExperienceList")
	public ModelAndView viewExperienceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null || "".equals(firstFlag)){
			List viewExperienceList = this.recruitManageSer.viewExperienceList(request);
			modelMap.put("viewExperienceList", viewExperienceList);
			modelMap.put("viewExperienceListCnt", viewExperienceList == null ? 0 : viewExperienceList.size());

			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap);
		}else{
			modelMap.put("START_DATE",DateUtil.getMonthStrAgo(3, "dd/MM/yyyy"));
			modelMap.put("END_DATE",DateUtil.getSysdateStr("dd/MM/yyyy"));
			modelMap.put("EMP_OFFICE_Multi","'15119'");
			modelMap.put("EMP_OFFICE_NAME",TipMessage.getTipMessage(
					"hrm.empinfo.JOB", request));
			modelMap.put("START_DATE_JOIN",DateUtil.getMonthStrAgo(3, "dd/MM/yyyy"));
			modelMap.put("END_DATE_JOIN",DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		
		modelMap.put("seach_SON_FLAG", request.getParameter("seach_SON_FLAG"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("seach_START_DATE", request.getParameter("seach_START_DATE"));
		modelMap.put("seach_END_DATE", request.getParameter("seach_END_DATE"));
		modelMap.put("seach_TRANS_CODE_NAME", request.getParameter("seach_TRANS_CODE_NAME"));
		modelMap.put("seach_TRANS_RESOURCE_NAME", request.getParameter("seach_TRANS_RESOURCE_NAME"));
		modelMap.put("seach_IDCARD_NO", request.getParameter("seach_IDCARD_NO"));
		modelMap.put("seach_DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("seach_POST_FAMILY_NAME", request.getParameter("seach_POST_FAMILY_NAME"));
		modelMap.put("seach_EMPLOYEE_BELONG_NAME", request.getParameter("seach_EMPLOYEE_BELONG_NAME"));
		modelMap.put("seach_EMP_TYPE_CODE_NAME", request.getParameter("seach_EMP_TYPE_CODE_NAME"));
		modelMap.put("seach_EMP_OFFICE_NAME", request.getParameter("seach_EMP_OFFICE_NAME"));
		modelMap.put("seach_START_DATE_JOIN", request.getParameter("seach_START_DATE_JOIN"));
		modelMap.put("seach_END_DATE_JOIN", request.getParameter("seach_END_DATE_JOIN"));
		
		return new ModelAndView("/hrm/recruitManage/viewExperienceList", modelMap);
	}

	@RequestMapping(value = "/viewExperienceListExport")
	public void viewExperienceListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("命令日期");
		aliasNameList.add("人事命令");
		aliasNameList.add("发令细节名");
		aliasNameList.add("姓名"); 
		aliasNameList.add("工号");
		aliasNameList.add("部门");
		aliasNameList.add("员工类型");
		aliasNameList.add("职级");
		aliasNameList.add("主要业务");
		aliasNameList.add("标准职务");
		aliasNameList.add("状态");
		aliasNameList.add("备注");

		List viewExperienceList = this.recruitManageSer.viewExperienceList(request);

		List list = new ArrayList();
		for(int i=0;i<viewExperienceList.size();i++){
			LinkedHashMap dataMap = (LinkedHashMap)viewExperienceList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", dataMap.get("START_DATE"));
			map.put("CELL1", dataMap.get("TRANS_CODE_NAME"));
			map.put("CELL2", dataMap.get("TRANS_REASON_NAME"));
			map.put("CELL3", dataMap.get("LOCAL_NAME"));
			map.put("CELL4", dataMap.get("EMPID"));
			map.put("CELL5", dataMap.get("DEPTNAME"));
			map.put("CELL6", dataMap.get("EMP_TYPE_CODE_NAME"));
			map.put("CELL7", dataMap.get("POST_GRADE_NO_NAME"));
			map.put("CELL8", dataMap.get("MAIN_BUSINESS_NAME"));
			map.put("CELL9", dataMap.get("STANDARD_POSITION_NAME"));
			map.put("CELL10", dataMap.get("EMP_OFFICE_NAME"));
			map.put("CELL11", dataMap.get("REMARK"));
			list.add(map);
		}

		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"Experience");

	}
	
	/**
	 * 主要业务说明书
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMainBusinessList")
	public ModelAndView viewMainBusinessList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.recruitManageSer.viewMainBusinessList(request);
		modelMap.put("resumeList", resumeList);

		LinkedHashMap param = null;
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);
			modelMap.put("CODE_NO", param.get("CODE_NO"));
			modelMap.put("resumeSize", resumeList.size());
		}else{
			modelMap.put("resumeSize", 0);
		}
		return new ModelAndView("/hrm/recruitManage/viewMainBusinessList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddMainBusinessInfo")
	public ModelAndView viewAddMainBusinessInfo(HttpServletRequest request,
			HttpServletResponse MainBusiness, ModelMap modelMap) throws Exception {

		List resumeList = this.recruitManageSer.viewMainBusinessList(request);

		LinkedHashMap param = new LinkedHashMap();
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);

			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "MAIN_BUSINESS");
			fileParam.put("APPLY_NO", param.get("CODE_NO"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList",fileList);
		}
		modelMap.put("resumeInfo", param);

		return new ModelAndView("/hrm/recruitManage/viewAddMainBusinessInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addMainBusinessInfo")
	@ResponseBody
	public Map addMainBusinessInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.addMainBusinessInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	
	/*获取供人公司*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonSupplier")
	public ModelAndView viewPersonSupplier (HttpServletRequest request,
			HttpServletResponse MainBusiness, ModelMap modelMap) throws Exception{
		LinkedHashMap paramData = ObjectBindUtil.getRequestParamData(request,"seach_");
		if(request.getParameter("FLAG") == null){
			this.recruitManageSer.deleteEmptyPersonSupplier(request);
		}else{
			this.recruitManageSer.addEmptyPersonSupplier(request);
		}
		List supplierList = this.recruitManageSer.viewPersonSupplier(paramData);
		
		modelMap.put("supplierList", supplierList);
		modelMap.put("seach_SON_FLAG", paramData.get("SON_FLAG"));

		return new ModelAndView("/hrm/recruitManage/viewPersonSupplier", modelMap);
	}
	
	/**
	 * 供人公司  保存
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPersonSupplier")
	@ResponseBody
	public Map addPersonSupplier(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyID = admin.getCpnyId();
		
		int result = this.recruitManageSer.addPersonSupplier(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0203");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	
	/**
	 * 单条删除批量导入的发令信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePersonSupplierInfo")
	@ResponseBody
	public Map deletePersonSupplierInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.recruitManageSer.deletePersonSupplierInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message",TipMessage.getTipMessage(
					"alert.message.delete_success", request) );//"删除成功"
			map.put("formId", "viewExperienceBatchListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage(
					"alert.message.delete_fail", request) );//"删除失败"
		}
		return map;
	}
}
