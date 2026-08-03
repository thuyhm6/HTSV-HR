package com.ait.hrm.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.portlet.ModelAndView;

import bsh.This;

import com.ait.hrm.service.AddRecPageHubSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/hrm/recruit")
public class AddRecPageHubCtroller {
	Logger logger = Logger.getLogger(AddRecPageHubCtroller.class);
	@Autowired
	private AddRecPageHubSer addRecPageHubSer;
	
	/**
	 * 人事简历录入页面 (add RecPage hub)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRecPageHub")
	public ModelAndView viewAddRecPageHubList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		this.addRecPageHubSer.deleteRecPageList(request);
		List elist = this.addRecPageHubSer.getRecPageList(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("rec_employee_no", param.get("REC_EMPLOYEE_NO"));
		} else {
			modelMap.put("rec_employee_no", "0");
		}
		modelMap.put("viewRecPageList", elist);
        modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.addRecPageHubSer.getRecPageListCnt(request));
        modelMap.put("totalcount", this.addRecPageHubSer.getRecPageListCnt(request));
        String UPLOAD_STATUS = request.getParameter("UPLOAD_STATUS");
        modelMap.put("UPLOAD_STATUS", UPLOAD_STATUS);
        
		return new ModelAndView("/hrm/recruit/addRecPageHub", modelMap);
	}
	
	/**
	 * 人事简历录入详情页面 (add single RecPage)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSingleRecPageHub")
	public ModelAndView viewAddSingleRecPageHub(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		if(!"".equals(request.getParameter("REC_EMPLOYEE_NO")) && request.getParameter("REC_EMPLOYEE_NO")!=null){
			List list =  this.addRecPageHubSer.getRecPageInfo(request);
			if(list.size()!=0){
			modelMap.put("recPageInfo", (LinkedHashMap)list.get(0));
			}
			List listwork =  this.addRecPageHubSer.getRecPageworkInfo(request);
			if(listwork.size()!=0){
			modelMap.put("recPageworkInfo", (LinkedHashMap)listwork.get(0));
		    }
		}
		return new ModelAndView("/hrm/recruit/addSingleRecPageHub", modelMap);
	}
	
	/**
	 * 人事简历详情页面 (single RecPage info)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/singleRecPageHubInfo")
	public ModelAndView viewSingleRecPageHubInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		if(!"".equals(request.getParameter("REC_EMPLOYEE_NO")) && request.getParameter("REC_EMPLOYEE_NO")!=null){
			List list =  this.addRecPageHubSer.getRecPageInfo(request);
			if(list.size()!=0){
			modelMap.put("recPageInfo", (LinkedHashMap)list.get(0));
		    }		
		}
		return new ModelAndView("/hrm/recruit/singleRecPageHubInfo", modelMap);
	}
	
	/**
	 * 附件上传
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lipeng
	 * @date 2017-6-23 下午13:51:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatchRec")
	public ModelAndView uploadBatchRec(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String personId = request.getParameter("PERSON_ID");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest)
				&& multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(524288000);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch (MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
		} else {
			return null;
		}
		String uuid = String.valueOf(UUID.randomUUID());

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		/** 构建附件保存的目录 **/
		String logoPathDir = "/resources/temp/recFiles/" + personId;// dateformat.format(new Date());
		/** 得到附件保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		// 构建文件名称
		String logImageName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			modelMap.put("sign", 1);
			modelMap.put("logImageName", logImageName);
			modelMap.put("fileUrl", uuid + suffix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		} catch (IOException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		}
		return new ModelAndView("/hrm/recruit/uploadBatchRec", modelMap);
	}
	
	/**
	 * 应聘者信息填写 (add RecPage)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRecPageInfo")
	@ResponseBody
	public Map addRecPageInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = addRecPageHubSer.addRecPageInfo(request);
			if (result == 1) {
				map.put("navTabId", "hr3701");
				map.put("message", TipMessage.getTipMessage(//保存成功
						"ar.alert.message.addempshift.success", request));
				map.put("statusCode", "200");				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(//保存失败
						"alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 人事对应聘者信息填写修改 (add RecPage hub)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEditRecPageInfoHub")
	@ResponseBody
	public Map addEditRecPageInfoHub(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String cpny = request.getParameter("interCpnyID");
		if ((request.getParameter("fileName") == null || "".equals(request.getParameter("fileName"))) && (cpny == "HTSV" || "HTSV".equals(cpny))) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(//上传附件
					"hr.viewCondSql.title.UploadCV", request));
		} else {
		try {
			int result = addRecPageHubSer.addEditRecPageInfoHub(request);
			if (result == 1) {
				map.put("navTabId", "hr3701");
				map.put("message", TipMessage.getTipMessage(//保存成功
						"ar.alert.message.addempshift.success", request));
				map.put("statusCode", "200");				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(//保存失败
						"alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		}
		return map;
	}
	
	/**
	 * 删除应聘者信息 (delete RecPage hub)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRecPageInfo")
	@ResponseBody
	public Map deleteRecPageInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = addRecPageHubSer.deleteRecPageInfo(request);
			if (result == 1) {
				map.put("navTabId", "hr3701");
				map.put("message", TipMessage.getTipMessage(//删除成功
						"alert.message.delete_success", request));
				map.put("statusCode", "200");				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(//删除失败
						"alert.message.delete_fail", request));
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
}
