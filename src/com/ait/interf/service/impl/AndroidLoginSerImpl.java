package com.ait.interf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.interf.service.AndroidLoginSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;
import com.ait.sys.dao.MyHomeDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
@Service
public class AndroidLoginSerImpl implements AndroidLoginSer{

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private MyHomeDao myHomeDao;
	@Override
	public String findUser(HttpServletRequest request) {
		String language = request.getLocale().getLanguage();
		Map hm = new LinkedHashMap();
		hm.put("username",request.getParameter("username"));
		hm.put("password",request.getParameter("password"));
		hm.put("cpny_id",request.getParameter("cpny_id"));
		hm.put("language",request.getParameter("lang"));
		AdminBean user = (AdminBean) loginDao.findUserSpecial(hm);
		String key = request.getParameter("key");
		if(!key.equals("EHR_SD_ANDRIOD")){
			return "loginFail";
		}
		if (user==null){
			return "loginFail";
		}else{
			if(user.getSpecialParam()!=null&&!user.getSpecialParam().equals("administrator")&&user.getPersonId()!=null){
				user=(AdminBean) loginDao.findUser(hm);
				if (user==null){
					return "loginFail";
				}
			}
			LinkedHashMap info = new LinkedHashMap();
			String IP = this.getRemortIP(request);
			info.put("PERSON_ID", user.getPersonId()==null?"":(user.getPersonId()));
			info.put("ip", IP);
			info.put("mac", "Android User Login");
			
			loginDao.addLoginInfo(info);
			user.setLanguage(language);
			request.getSession().setAttribute("LoginUser", user);
			return "OK";
		}
	}
	
	private String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	@Override
	public Map getTips(HttpServletRequest request) {
		Map rMap = new HashMap();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("USERNO", admin.getUserNo()) ;
		paramMap.put("language", admin.getLanguage());
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		/**
		 * 
		//个人查看
		int total_count_viewInfo = 0;
		//进出门
		total_count_viewInfo += myHomeDao.getTipsCnt("getPersonalInfoInOutApplyCount", paramMap);
		//出差
		total_count_viewInfo += myHomeDao.getTipsCnt("getPersonalApplyErvectionCount", paramMap);
		//加班 
		total_count_viewInfo += myHomeDao.getTipsCnt("getInfoViewCount", paramMap);
		//休假,喜丧假,调休
		rMap.put("total_count_viewInfo", total_count_viewInfo);
		 */
		
		//待决裁(调令)
		int total_count_peraffirmInfo = 0;
		total_count_peraffirmInfo += myHomeDao.getTipsCnt("getRewardCount", paramMap);
		total_count_peraffirmInfo += myHomeDao.getTipsCnt("getPunishmentCount", paramMap);
		total_count_peraffirmInfo += myHomeDao.getTipsCnt("getTransferOrderCountForQuickMenu", paramMap);
		rMap.put("total_count_peraffirmInfo", total_count_peraffirmInfo);
		
		//待决裁
		int total_count_affirmInfo = 0;
		//加班
		total_count_affirmInfo += myHomeDao.getTipsCnt("getCountOt", paramMap);
		//休假,出差,进出门 
		total_count_affirmInfo += myHomeDao.getTipsCnt("getEssAffirmInfoCnt", paramMap);
		rMap.put("total_count_affirmInfo", total_count_affirmInfo);
				
		//待确认
		int total_count_confirmInfo = 0;
		//个人
		total_count_confirmInfo += myHomeDao.getTipsCnt("getEssPersonalInfoCnt", paramMap);
		//加班确认
		total_count_confirmInfo +=  myHomeDao.getTipsCnt("getEssOtInfoCnt", paramMap);
		//休假确认,...
		total_count_confirmInfo +=  myHomeDao.getTipsCnt("getEssConfirmInfoCnt", paramMap);
		rMap.put("total_count_confirmInfo", total_count_confirmInfo);
		
		return rMap;
	}

	@Override
	public List getInfoNotAffirm(HttpServletRequest request) throws Exception {
		Map pMap = ObjectBindUtil.getRequestParamData(request);
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		pMap.put("PERSON_ID", admin.getPersonId());
		list.addAll(myHomeDao.getInfoNotAffirm(pMap));
		list.addAll(myHomeDao.getOtInfoNotAffirm(pMap));
		return list;
	}

	@Override
	public List getInfoNotConfirm(HttpServletRequest request) throws Exception{
		Map pMap = ObjectBindUtil.getRequestParamData(request);
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		pMap.put("PERSON_ID", admin.getPersonId());
		list.addAll(myHomeDao.getInfoNotConfirm(pMap));
		list.addAll(myHomeDao.getOtInfoNotConfirm(pMap));
		list.addAll(myHomeDao.getPersonInfoNotConfirm(pMap));
		return list;
	}

	@Override
	public List getTransferOrderList(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		list.addAll(myHomeDao.getTransferOrderList(paramMap));
		list.addAll(myHomeDao.getRewardNoAffirmInfo(paramMap));
		list.addAll(myHomeDao.getPunishNoAffirmInfo(paramMap));
		return list;
	}
}
