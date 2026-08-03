package com.ait.pa.action.excelUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.dao.EmpInfoDao;
import com.ait.hrm.dao.RecruitManageDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Controller
@RequestMapping(value = "/pa/fileImport")
@SuppressWarnings({ "unchecked", "static-access" })
public class FileImportCtroller {
	
	Logger logger = Logger.getLogger(FileImportCtroller.class);
	@Autowired
	private EmpInfoDao empInfoDao;
	@Autowired
	private RecruitManageDao recruitManageDao;
	
	@RequestMapping(value = "/importFile")
	public ModelAndView importFile(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("type", request.getParameter("type"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("applyType", request.getParameter("applyType"));
		modelMap.put("applyNo", request.getParameter("applyNo"));
		return new ModelAndView("/pa/fileImport/importFile",modelMap);		
	} 
	
	@RequestMapping(value = "/uploadPhoto")
	public ModelAndView uploadPhoto(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(20971520);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch(MaxUploadSizeExceededException e) {
				//return null;
			}
		} else if (request instanceof MultipartHttpServletRequest){
			multipartRequest = (MultipartHttpServletRequest)request;
		} else {
			return null;
		}
		List<MultipartFile> fileList = multipartRequest.getFiles("proveFileName");
		for (MultipartFile multipartFile : fileList) {
		String uuid = String.valueOf(UUID.randomUUID());
		
		 /**构建图片保存的目录**/    
		 String logoPathDir = "/resources/photo/" + admin.getCpnyId();// +PARAMDATANO;dateformat.format(new Date());     
		 /**得到图片保存目录的真实路径**/    
		 String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);     
		/**根据真实路径创建目录**/    
		 File logoSaveFile = new File(logoRealPathDir);     
		 if(!logoSaveFile.exists())     
		logoSaveFile.mkdirs();           
		/**页面控件的文件流**/    
		//MultipartFile multipartFile = multipartRequest.getFile("proveFileName");     
		String logImageName = multipartFile.getOriginalFilename();
		/**获取文件的后缀**/    
		String suffix = multipartFile.getOriginalFilename().substring  
		(multipartFile.getOriginalFilename().lastIndexOf("."));     
		String empId = multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().lastIndexOf("."));
		 /**使用UUID生成文件名称**/    
		// String logImageName = request.getParameter("PARAM_DATA_NO").toString()+ suffix;
		//构建文件名称     
		/**拼成完整的文件保存路径加文件**/    
		//String fileName = logoRealPathDir + File.separator   + logImageName;                
		String photoURL = logoPathDir + "/" + uuid + suffix;
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		File file = new File(fileName);           
		paramMap.put("EMPID", empId);
		paramMap.put("interCpnyID", admin.getCpnyId());
		int cnt = recruitManageDao.IS_EXISTS_EMPID(paramMap);
		if(cnt == 0){
			throw new Exception(empId + " not exist in system!");//没有审批者,不能申请
		} else {
			try {     
				paramMap.put("EMPID", empId);
				paramMap.put("PHOTOURL", photoURL);
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.empInfoDao.updatePersonInfoPhoto(paramMap);
			  multipartFile.transferTo(file);  
			  modelMap.put("sign",2);
			  modelMap.put("logImageName", logImageName);
			  modelMap.put("fileUrl", uuid + suffix);
			 } catch (IllegalStateException e) {     
			 e.printStackTrace(); 
			 modelMap.put("sign", -1);
			} catch (IOException e) {            
			 e.printStackTrace();  
			 modelMap.put("sign", -1);
			}
    		
			 catch (Exception e) {            
			 e.printStackTrace();  
			 modelMap.put("sign", -1);
			 }   
		}
		}
		
		return new ModelAndView("/pa/fileImport/uploadPhoto",modelMap);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fileUploading")
	public ModelAndView fileUploading(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(20971520);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch(MaxUploadSizeExceededException e) {
				//return null;
			}
		} else if (request instanceof MultipartHttpServletRequest){
			multipartRequest = (MultipartHttpServletRequest)request;
		} else {
			return null;
		}
		String uuid = String.valueOf(UUID.randomUUID());
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");     
		 /**构建图片保存的目录**/    
		 String date = dateformat.format(new Date());     
		 /**构建图片保存的目录**/    
		 String filePathDir = "/resources/temp/apply/applyleave/" + admin.getCpnyId() + "/" +date;
		 
		 String applyNo = request.getParameter("applyNo");
		 String applyType = request.getParameter("applyType");
		 /**得到图片保存目录的真实路径**/    
		 String logoRealPathDir = request.getSession().getServletContext().getRealPath(filePathDir);     
		/**根据真实路径创建目录**/    
		 File logoSaveFile = new File(logoRealPathDir);     
		 if(!logoSaveFile.exists())     
		logoSaveFile.mkdirs();           
		/**页面控件的文件流**/    
		MultipartFile multipartFile = multipartRequest.getFile("proveFileName");     
		String logFileName = multipartFile.getOriginalFilename();
		/**获取文件的后缀**/    
		String suffix = multipartFile.getOriginalFilename().substring  
		(multipartFile.getOriginalFilename().lastIndexOf("."));     
		String empId = multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().lastIndexOf("."));
		 /**使用UUID生成文件名称**/    
		// String logImageName = request.getParameter("PARAM_DATA_NO").toString()+ suffix;
		//构建文件名称     
		/**拼成完整的文件保存路径加文件**/    
		//String fileName = logoRealPathDir + File.separator   + logImageName;                
		String fileUrl = filePathDir + "/" + uuid + suffix;
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		File file = new File(fileName);           
		try {     
		  multipartFile.transferTo(file);  
		  LinkedHashMap fileMap = new LinkedHashMap();
			fileMap.put("fileName", logFileName);
			fileMap.put("fileUrl", fileUrl);
			fileMap.put("APPLY_NO", applyNo);
			fileMap.put("APPLY_TYPE", applyType);
			fileMap.put("CREATED_BY", admin.getCpnyId());
			this.empInfoDao.insertEssFile(fileMap);
			
		  modelMap.put("sign",2);
		 } catch (IllegalStateException e) {     
		 e.printStackTrace(); 
		 modelMap.put("sign", -1);
		} catch (IOException e) {            
		 e.printStackTrace();  
		 modelMap.put("sign", -1);
		 }   
		
		return new ModelAndView("/pa/fileImport/fileUploading",modelMap);
		
	}

}
