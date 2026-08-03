package com.ait.sys.action;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.NoticeManageSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: NoticeManageCtroller.java
 * @Description: 公告管理-公告信息
 * @Create date: 2014-3-6 下午01:55:16
 * @Create by: limeng(liemng@ait.net.cn)
 * @version 5.5
 */
@Controller
@RequestMapping(value="/sys/notice")
public class NoticeManageCtroller {
	
	@Autowired
	private NoticeManageSer noticeSer;
	
	@Autowired 
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	InfoApplySer infoApplySer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewNoticeInfo")
	public ModelAndView viewNoticeInfoList(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List noticeList = noticeSer.getNoticeInfo(request);
		//将Clob转成String
		if(noticeList.size()>0){
			for (Iterator iterator = noticeList.iterator(); iterator.hasNext();) {
				Map noticeInfo = (LinkedHashMap)iterator.next();
				noticeInfo.put("CONTENT", noticeSer.printClob(noticeInfo.get("CONTENT")));
			}
		}
		modelMap.put(UiUtil.PAGE_NUM_NAME, request.getParameter("pageNum")!=null?request.
				getParameter("pageNum"):"1");
		modelMap.put(UiUtil.NUM_PER_PAGE_NAME, request.getParameter("numPerPage")!=null?request.
			getParameter("numPerPage"):"10");
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,noticeSer.getNoticeInfoCn(request));
		modelMap.put("nList", noticeList);
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpnyId", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124977"));
		return new ModelAndView("/sys/notice/viewNoticeInfo",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addNoticeInfoView")
	public ModelAndView addNoticeInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//判断是否有超级管理员权限
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpnyId", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/sys/notice/addNoticeInfoView",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addNoticeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addNoticeInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.noticeSer.insertNoticeInfo(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 添加成功
			map.put("navTabId", "gg0101");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateNoticeInfoView")
	public ModelAndView updateNoticeInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map item = noticeSer.getNoticeInfoById(request);
		modelMap.put("item", item);
		modelMap.put("UPDATED_BY", item.get("CREATED_BY"));
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if(authority == 1){
			String[] cpnyIds = item.get("CPNY_ID").toString().split(",");
			List companyList = empInfoSer.getCompanyList(request);
			if(cpnyIds != null && cpnyIds.length > 0){
				for(int i=0;i<cpnyIds.length;i++){
					for(int j=0;j<companyList.size();j++){
						LinkedHashMap companyMap = (LinkedHashMap)companyList.get(j);
						if(cpnyIds[i].equals(companyMap.get("CPNY_ID").toString())){
							companyMap.put("SELECTED", 1);
							break;
						}
					}
				}
			}
			modelMap.put("companyList", companyList);
		}
		modelMap.put("defaultCpnyId", admin.getCpnyId());
		//判断是否有超级管理员权限
		modelMap.put("authority", authority);
		return new ModelAndView("/sys/notice/updateNoticeInfoView", modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateNoticeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateNoticeInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.noticeSer.updateNoticeInfo(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 修改成功
			map.put("navTabId", "gg0101");
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 修改失败
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delNoticeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delNoticeInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.noticeSer.delNoticeInfo(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "gg0101");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}
	
	/**
	 * 获取公告
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewNotice")
	public ModelAndView viewNotice(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map item = noticeSer.getNoticeInfoById(request);
		modelMap.put("notice", item);
		return new ModelAndView("/sys/notice/viewNotice", modelMap);
	}
	

	/**
	 * 跳转到附件上传页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadWindow")
	public ModelAndView uploadWindow(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String val =  StringUtil.checkNull(request.getParameter("val"));
		val = val.replace("$", "&");
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("val", val);
		modelMap.put("seq", request.getParameter("seq"));
		modelMap.put("applyType", request.getParameter("applyType"));
		return new ModelAndView("/sys/notice/uploadWindow", modelMap);
	}

	/**
	 * 跳转到附件上传页(新增)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadWindowInsert")
	public ModelAndView uploadWindowInsert(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String val =  StringUtil.checkNull(request.getParameter("val"));
		val = val.replace("$", "&");
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("val", val);
		modelMap.put("seq", request.getParameter("seq"));
		modelMap.put("applyType", request.getParameter("applyType"));
		return new ModelAndView("/sys/notice/uploadWindowInsert", modelMap);
	}
	
	/**
	 * 照片上传
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadWindowPhoto")
	public ModelAndView uploadWindowPhoto(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String val =  StringUtil.checkNull(request.getParameter("val"));
		val = val.replace("$", "&");
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("val", val);
		modelMap.put("seq", request.getParameter("seq"));
		modelMap.put("applyType", request.getParameter("applyType"));
		return new ModelAndView("/sys/notice/uploadWindowPhoto", modelMap);
	}
	
	
	@RequestMapping(value = "/uploadAtt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadAtt(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.noticeSer.uploadAtt(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.save_success", request));// "保存成功"
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// "保存失败"
		}
		return map;
	}
	
	@RequestMapping(value = "/uploadAttPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadAttPhoto(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.noticeSer.uploadAttPhoto(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.save_success", request));// "保存成功"
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// "保存失败"
		}
		return map;
	}
	
	/**
	 * 跳转到附件上传页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAffirmWindow")
	public ModelAndView addAffirmWindow(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("APPLY_TYPE", request.getParameter("APPLY_TYPE"));
		modelMap.put("divId", request.getParameter("divId"));
		modelMap.put("affirmIdStr", request.getParameter("affirmIdStr"));
		modelMap.put("affirmorList", this.infoApplySer.getAffirmorListByString(StringUtil.checkNull(request.getParameter("APPLY_TYPE")),StringUtil.checkNull(request.getParameter("PERSON_ID")), null, null, StringUtil.checkNull(request.getParameter("LANGUAGE"))));
		return new ModelAndView("/sys/notice/addAffirmWindow", modelMap);
	}
}
