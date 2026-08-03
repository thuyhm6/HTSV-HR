package com.ait.sys.service.impl;

import java.io.Reader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.NoticeManageDAO;
import com.ait.sys.service.NoticeManageSer;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class NoticeManageSerImpl implements NoticeManageSer{

	@Autowired
	private NoticeManageDAO noticeDAO;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;

	@Override
	public int delNoticeInfo(HttpServletRequest request) throws Exception {
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> list = ObjectBindUtil
				.getRequestJsonData(jsonString);
		LinkedHashMap<String, Object> map = new LinkedHashMap();
		map.put("ID", request.getParameter("ID"));
		list.add(map);
		return noticeDAO.delNoticeInfo(list);
	}

	@Override
	public List getNoticeInfo(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List noticeList = new ArrayList();
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if(authority == 0){
			map.put("CPNY_ID", admin.getCpnyId());
		}
		if(UiUtil.getPageNum(request)>0){
			noticeList = noticeDAO.getNoticeInfo(map,UiUtil.getPageNum(request),
					UiUtil.getNumPerPage(request));
		}else{
			noticeList =  noticeDAO.getNoticeInfo(map);
		}
		if(noticeList != null && noticeList.size() > 0){
			for(int i=0;i<noticeList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)noticeList.get(i);
				returnMap.put("APPLY_NO", returnMap.get("ID"));
				returnMap.put("APPLY_TYPE", "0303");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return noticeList;
	}

	@Override
	public Map getNoticeInfoById(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		map.put("ID", request.getParameter("ID"));
		return noticeDAO.getNoticeInfoById(map);
	}

	@Override
	public int insertNoticeInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String[] cpnyIds = request.getParameterValues("CPNY_ID_BATCH");
		String CPNY_ID = request.getParameter("CPNY_ID");
		String FILE_URL =  StringUtil.checkNull(map.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(map.get("fileName"));
		if(CPNY_ID == null || "".equals(CPNY_ID)){
			if(cpnyIds != null){
				for(int i=0;i<cpnyIds.length;i++){
					if(i == 0){
						CPNY_ID = cpnyIds[i];
					}else{
						CPNY_ID += "," + cpnyIds[i];
					}
				}
			}
		}
		map.put("TITLE", request.getParameter("TITLE"));
		map.put("CONTENT", request.getParameter("CONTENT"));
		map.put("FROM_DATE", request.getParameter("FROM_DATE"));
		map.put("TO_DATE", request.getParameter("TO_DATE"));
		map.put("CREATE_BY",admin.getPersonId());
		map.put("CPNY_ID",CPNY_ID);
		map.put("PERIOD", request.getParameter("PERIOD"));
		map.put("FILE_NAME", FILE_NAME);
		map.put("FILE_URL", FILE_URL);
		map.put("PERSON_ID_FILE", admin.getPersonId());
		map.put("COLOR_FLAG",request.getParameter("color_flag"));
		return noticeDAO.insertNoticeInfo(map);
	}

	@Override
	public int updateNoticeInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] cpnyIds = request.getParameterValues("CPNY_ID_BATCH");
		String CPNY_ID = request.getParameter("CPNY_ID");
		if(CPNY_ID == null || "".equals(CPNY_ID)){
			if(cpnyIds != null){
				for(int i=0;i<cpnyIds.length;i++){
					if(i == 0){
						CPNY_ID = cpnyIds[i];
					}else{
						CPNY_ID += "," + cpnyIds[i];
					}
				}
			}
		}
		map.put("ID", request.getParameter("ID"));
		map.put("TITLE", request.getParameter("TITLE"));
		map.put("CONTENT", request.getParameter("CONTENT"));
		map.put("FROM_DATE", request.getParameter("FROM_DATE"));
		map.put("TO_DATE", request.getParameter("TO_DATE"));
		map.put("ACTIVITY", request.getParameter("ACTIVITY"));
		map.put("UPDATE_BY",request.getParameter("UPDATED_BY"));
		map.put("CPNY_ID",CPNY_ID);
		map.put("PERIOD", request.getParameter("PERIOD"));
		String FILE_URL =  StringUtil.checkNull(map.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(map.get("fileName"));
		map.put("FILE_NAME", FILE_NAME);
		map.put("FILE_URL", FILE_URL);
		return noticeDAO.updateNoticeInfo(map);
	}

	@Override
	public int getNoticeInfoCn(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		return noticeDAO.getNoticeInfoCn(map);
	}

	@Override
	public String printClob(Object clob) throws Exception {
		if(clob==null){
			return "";
		}
		String clobStr = "";
		if(clob instanceof Clob){
			int y;
			char cs[] = new char[30];
			Reader reader = ((Clob)clob).getCharacterStream();
			if((y = reader.read(cs,0,30))>0){
				clobStr = new String(cs,0,y);
			}
		}
		return clobStr.toString()+"....";
	}

	@Override
	public int delOverNoticeInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		return noticeDAO.delOverNoticeInfo(map);
	}
	
	//上传附件
	public int uploadAtt(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String[] cpnyIds = request.getParameterValues("CPNY_ID_BATCH");
		String APPLY_NO = request.getParameter("APPLY_NO");
		String APPLY_TYPE = request.getParameter("APPLY_TYPE");
		String FILE_URL =  StringUtil.checkNull(map.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(map.get("fileName"));
		
		map.put("CREATE_BY",admin.getPersonId());
		map.put("FILE_NAME", FILE_NAME);
		map.put("FILE_URL", FILE_URL);
		map.put("APPLY_NO", APPLY_NO);
		map.put("APPLY_TYPE", APPLY_TYPE);
		return noticeDAO.uploadAtt(map);
	}
	
	//上传附件
		public int uploadAttPhoto(HttpServletRequest request) throws Exception {
			Map map = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			String[] cpnyIds = request.getParameterValues("CPNY_ID_BATCH");
			String APPLY_NO = request.getParameter("APPLY_NO");
			String APPLY_TYPE = request.getParameter("APPLY_TYPE");
			String FILE_URL =  StringUtil.checkNull(map.get("fileUrl"));
			String FILE_NAME = StringUtil.checkNull(map.get("fileName"));
			
			map.put("CREATE_BY",admin.getPersonId());
			map.put("CPNY_ID",admin.getCpnyId());
			map.put("FILE_NAME", FILE_NAME);
			map.put("FILE_URL", FILE_URL);
			map.put("APPLY_NO", APPLY_NO);
			map.put("APPLY_TYPE", APPLY_TYPE);
			return noticeDAO.uploadAttPhoto(map);
		}
}
