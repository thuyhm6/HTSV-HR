package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.EssApplyInfoDao;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AffirmDao;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.dao.LoginDao;
import com.ait.sys.dao.MyHomeDao;
import com.ait.sys.service.MyHomeSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class MyHomeSerImpl implements MyHomeSer {
	
	@Autowired
	private MyHomeDao myHomeDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private EssApplyInfoDao essApplyInfoDao;
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;

	@SuppressWarnings("unchecked")
	public void updateModel(HttpServletRequest request) {
		
		List list = new ArrayList();
		Enumeration en = request.getParameterNames();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		String empid = admin.getAdminID() ;
		String page = request.getParameter("page");
		
		while (en.hasMoreElements()) {
			
			String key = (String) en.nextElement();
			String mcontent = request.getParameter(key);
			if(key.endsWith("Model")&&mcontent!=null&&!mcontent.equals("")){
				Map user= new LinkedHashMap();
				user.put("empid", empid);
				user.put("mid", key);
				user.put("mcontent", mcontent);
				user.put("page", page);
				
				if(request.getParameter(key).toLowerCase().contains("display: none")){
					user.put("mstate", "0");
				}else{
					user.put("mstate", "1");
				}				
				list.add(user);
			}
		}

		myHomeDao.deleteModel(list);
		myHomeDao.insertModel(list);
		
		String shorttemp = request.getParameter("short");
		if(shorttemp!=null&&!shorttemp.equals("")){
			List stlist = new ArrayList();
			String[] st = shorttemp.split(",");
			for(int a=0;a<st.length;a++){
				Map user= new LinkedHashMap();
				user.put("empid", empid);
				user.put("menu", st[a]);
				user.put("page", page);
				
				stlist.add(user);
			}
			
			myHomeDao.deleteShort(stlist);
			myHomeDao.insertShort(stlist);
			
			
		}
		
	}

	@SuppressWarnings("unchecked")
	public List getModel(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("page", request.getParameter("page"));
		
		return myHomeDao.getModel(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Object getHomePurview(HttpServletRequest request) {
		StringBuffer show = new StringBuffer();
		StringBuffer select = new StringBuffer();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("page", request.getParameter("page"));
		List purviewList = myHomeDao.getHomePurview(paramMap);
		
		for(Object object : purviewList) {
			Map temp = (Map)object;
			select.append("<p style='height:25px;'><input type='checkbox' value='"+temp.get("MID").toString()
					+"' onclick='check(this)' ");
			if (temp.get("MSTATE").toString().equals("1")) {
				select.append("checked=checked");
			}
			select.append("/>"+temp.get("MNAME").toString()+"</p>");
			
			show.append(temp.get("MDIV").toString());
			if(temp.get("MID").toString().equals("clockModel")){
				show.append("<embed wmode='transparent' width='100%' height='100%' " +
						"src='/resources/images/clock.swf'/>");				
			}else if(temp.get("MID").toString().equals("shortcutModel")
					&&temp.get("MSTATE").toString().equals("1")){
				
				List shortList = myHomeDao.getShort(paramMap);
				show.append(Messages.getMessage(request, "shortcutBar")
						+"<br><p id=\"shortcutinfo\"></p><table>");
				for (int j = 0; j < shortList.size(); j++) {
					Map tp = (Map) shortList.get(j);
					show.append("<tr style='height:25px;'><td><a href=\"#\" " +
							"onclick=\"goTab('"+tp.get("MENU")+"','"+
							tp.get("MENU_INTRO")+"','"+
							tp.get("MENU_URL")+
							"')\">"+tp.get("MENU_INTRO")+"</a></td></tr>");						
				}
				show.append("</table>");
							
			}else if(temp.get("MID").toString().equals("birthdayModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//生日列表
				
				paramMap.put("model", "birthdayModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-25;
					}
				}
				paramMap.remove("model");

				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#birthday\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30,width:"+width*5/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70,width:"+width*12/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70,width:"+width*12/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*35/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*12/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*12/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.birthDate")+"', name: 'DOB', minWidth: 70,width:"+width*12/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getBirthday',"+
		               " sortName: 'DOB', "+
		               " dataAction: 'server',"+	               
		               " usePager: false,width: '100%',height:"+height+",heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#birthday').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"birthdaylist")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}else if(temp.get("MID").toString().equals("monthExperienceModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//月内人事令
				
				paramMap.put("model", "monthExperienceModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-25;
					}
				}
				paramMap.remove("model");

				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#monthExperience\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30,width:"+width*4/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.ccommType")+"', name: 'TRANSTYPE', minWidth: 70,width:"+width*11/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70,width:"+width*10/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70,width:"+width*11/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*31/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*11/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*10/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.effectDate")+"', name: 'STARTDATE', minWidth: 70,width:"+width*12/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getMonthExperience',"+
		               " sortName: 'STARTDATE', "+
		               " dataAction: 'server',"+	               
		               " usePager: false,width: '100%',height:"+height+",heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#monthExperience').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"monthExperience")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}else if(temp.get("MID").toString().equals("expiredContractModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//到期合同
				
				paramMap.put("model", "expiredContractModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-27;
					}
				}
				paramMap.remove("model");

				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#expiredContract\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30 ,width:"+width*4/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70 ,width:"+width*9/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70 ,width:"+width*9/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*24/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*9/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*9/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.endDate")+"', name: 'ENDCONTRACTDATE', minWidth: 100,width:"+width*18/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "countdownDays")+"', name: 'DAYS', minWidth: 120,width:"+width*18/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getExpiredContract',"+
		               " sortName: 'DAYS', "+
		               " dataAction: 'server',"+	               
		               " usePager: false,width: '100%',height:'"+height+"',heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#expiredContract').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"expiredContract")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}else if(temp.get("MID").toString().equals("notExistContractModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//未签合同
				
				paramMap.put("model", "notExistContractModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-25;
					}
				}
				paramMap.remove("model");
				
				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#notExistContract\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30 ,width:"+width*5/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70,width:"+width*11/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70,width:"+width*11/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*35/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*11/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*11/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.hireDate")+"', name: 'JOINDATE',minWidth: 100,width:"+width*16/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getNotExistContract',"+
		               " sortName: 'JOINDATE', "+
		               " dataAction: 'server',"+	               
		               " usePager: false,width: '100%',height:'"+height+"',heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#notExistContract').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"notExistContract")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}else if(temp.get("MID").toString().equals("expiredProbationModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//转正提示

				paramMap.put("model", "expiredProbationModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-30;
					}
				}
				paramMap.remove("model");
				
				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#expiredProbation\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30 ,width:"+width*5/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70,width:"+width*11/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70,width:"+width*11/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*35/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*11/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*11/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.endProbationDate")+"', name: 'ENDPROBATIONDATE',minWidth: 120,width:"+width*16/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getExpiredProbation',"+
		               " sortName: 'ENDPROBATIONDATE', "+
		               " usePager: false,width: '100%',height:'"+height+"',heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#expiredProbation').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"expiredProbation")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}else if(temp.get("MID").toString().equals("dispatchModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//派遣人员

				paramMap.put("model", "dispatchModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-30;
					}
				}
				paramMap.remove("model");
				
				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#dispatch\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30,width:"+width*3/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70,width:"+width*8/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70,width:"+width*8/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*17/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*8/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*8/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dispatchDistinguish")+"', name: 'DISDIFFENT' ,minWidth: 70,width:"+width*8/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dispatchOffice")+"', name: 'CONTENTS' ,minWidth: 70,width:"+width*12/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.startDate")+"', name: 'STARTDATE' ,minWidth: 120,width:"+width*14/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.ProposeEndDate")+"', name: 'PROPOSEENDDATE',minWidth: 120,width:"+width*14/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getDispatchInfo',"+
		               " sortName: 'STARTDATE', "+
		               " dataAction: 'server',"+	               
		               " usePager: false,width: '100%',height:'"+height+"',heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#dispatch').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"dispatch")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}else if(temp.get("MID").toString().equals("eduOutsideModel")
					&&temp.get("MSTATE").toString().equals("1")){
				//行外培训

				paramMap.put("model", "eduOutsideModel");
				String content = ((Map)myHomeDao.getModel(paramMap).get(0)).get("MCONTENT").toString();
				
				String[] templist = content.split(";");
				float height=0;
				float width=0;
				for (int j = 0; j < templist.length; j++) {
					if(templist[j].toLowerCase().contains("height")){
						height = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim());
					}
					if(templist[j].toLowerCase().contains("width")){
						width = Integer.parseInt(((templist[j].split(":"))[1]).split("px")[0].trim())-27;
					}
				}
				paramMap.remove("model");
				
				show.append("<SCRIPT type=\"text/javascript\">");
				show.append("$(function(){$(\"#eduOutside\").ligerGrid({"+											               
		               " columns: ["+
		               " 	{ display: 'No.', name: 'RN', minWidth: 30,width:"+width*4/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.empid")+"', name: 'EMPID', minWidth: 70,width:"+width*9/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.name")+"', name: 'CHINESENAME', minWidth: 70,width:"+width*9/100+" },"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.dept")+"', name: 'DEPARTMENT' ,minWidth: 150,width:"+width*24/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.position")+"', name: 'POSITION' ,minWidth: 70,width:"+width*9/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.postGrade")+"', name: 'POSTGRADE' ,minWidth: 70,width:"+width*9/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.startDate")+"', name: 'PLANSTARTDATE' ,minWidth: 120,width:"+width*18/100+"},"+
		               " 	{ display: '"+Messages.getMessage(request, "hrm.endDate")+"', name: 'PLANENDDATE',minWidth: 120,width:"+width*18/100+"}"+
		               " ],"+ 
		               " url: '/myhome/getapp?type=getEduOutside',"+
		               " sortName: 'PLANSTARTDATE',sortOrder:'desc', "+
		               " dataAction: 'server',"+	               
		               " usePager: false,width: '100%',height:'"+height+"',heightDiff:-24"+ 
		               "}); " +
		               "  $('.l-panel-header','#eduOutside').before('<div style=\"text-align:center;background-color:#E8F4F1;\">"+
		               "<font size=3 color=#4C1B6D>"+Messages.getMessage(request,"eduOutside")+"	</font></div>');" +
		               " });");
				show.append("</SCRIPT>");
											
			}
			show.append("</DIV>");
			
			
		}

		Map temp = new HashMap();
		temp.put("show", show.toString());
		temp.put("select", select.toString());
		
		return temp;
	}

	@SuppressWarnings("unchecked")
	public Object getHomePage(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		
		return myHomeDao.getHomePage(paramMap);
	}

	@SuppressWarnings("unchecked")
	public void updateApp(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
				
		myHomeDao.updateApp(paramMap);
		
		String page = ObjectUtils.toString(paramMap.get("page")) ;
		if(page != null && page.length() > 0){
			myHomeDao.deletePage(paramMap);
			myHomeDao.updatePage(paramMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getApp(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		
		String type = request.getParameter("type");
		
		return myHomeDao.getApp(type , paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List getSyMenu(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("page", request.getParameter("page"));
		
		return myHomeDao.getSyMenu(paramMap);
	}
	public Object getManualMenu(HttpServletRequest request) {
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
		
		int total_count_viewInfo = 0;
		List pMenuList = myHomeDao.getManualMenu(paramMap);
		if(pMenuList != null && pMenuList.size()!=0){
			for (Object object : pMenuList) {
				Map temp = (Map)object;
				rMap.put(temp.get("MENU_CODE"), 1);
			}
		}
		
		return rMap;
	}
	/**
	 * 1	ess0209	漏刷卡审批	1
	*	2	ess0239	加班审批(CH)	1
	*	3	ess0305	考勤异常审批	1
	*	4	pa0903	临促工资预提审批	1
	*	5	ess0244	费用审批	1
	*	6	pa0907	临促工资汇总确认	1
	*	7	ess0304	年假调整审批	1
	*	8	ess0242	休假审批	1
	*	9	ess2017	离职审批	1
	*	10	pa0901	临促工资审批	1
	*	11	hr0306	续签审批	1
	 */
	@SuppressWarnings("unchecked")
	public Object getTips(HttpServletRequest request) {
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
		
		int total_count_viewInfo = -1;
		List pMenuList = myHomeDao.getTipsMenu(paramMap);
		if(pMenuList.size()==0){
			rMap.put("pview", "false");
		}else{
			total_count_viewInfo = 0;
			rMap.put("pview", "true");
			
			
			for (Object object : pMenuList) {
				Map temp = (Map)object;
				int num =-1;
				if(admin.getCpnyId().equals("HTSV") || admin.getCpnyId().equals("HAE")){
					if (temp.get("MENU_CODE").equals("ess0601")) {
						num = loginDao.viewApprovalInfo(paramMap) != null?loginDao.viewApprovalInfo(paramMap).size():0;
					}else if(temp.get("MENU_CODE").equals("ess3436")){
						num = myHomeDao.getTipsCnt("getAttendanceForDayCount", paramMap);
					}else if(temp.get("MENU_CODE").equals("ess3444")){
						num = myHomeDao.getTipsCnt("getChangShopConfirmCount", paramMap);
					}
				}else{
					if (temp.get("MENU_CODE").equals("ess0601")) {
						num = loginDao.viewApprovalInfo(paramMap) != null?loginDao.viewApprovalInfo(paramMap).size():0;
					}else if(temp.get("MENU_CODE").equals("ess3438")){
						num = myHomeDao.getTipsCnt("getAttendanceForDayShCount", paramMap);
					}else if(temp.get("MENU_CODE").equals("ess3444")){
						num = myHomeDao.getTipsCnt("getChangShopConfirmCount", paramMap);
					}
				}
				if(num != -1 ){
					rMap.put(temp.get("MENU_CODE"), num);
					total_count_viewInfo += num;
				}
			}
		}
		rMap.put("total_count_viewInfo", total_count_viewInfo);
		
		return rMap;
	}
	
	@SuppressWarnings("unchecked")
	public Object getTips_home(HttpServletRequest request) {
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
		
		int total_count_viewInfo = -1;
		List pMenuList = myHomeDao.getTipsMenu(paramMap);
		if(pMenuList.size()==0){
			rMap.put("pview", "false");
		}else{
			total_count_viewInfo = 0;
			rMap.put("pview", "true");
			
			for (Object object : pMenuList) {
				Map temp = (Map)object;
				int num =-1;
			if(temp.get("MENU_CODE").equals("sy0501")){
				Map map = (Map) essApplyInfoDao.getApplyNumber(paramMap);
				num = Integer.parseInt(map.get("NUMB").toString()) ;
			}else if(temp.get("MENU_CODE").equals("ar0901")){
				num = loginDao.getArExConfirmCnt(paramMap);
			}else if(temp.get("MENU_CODE").equals("ar0903")){
				num = loginDao.getLeaveConfirmCnt(paramMap);
			}else if(temp.get("MENU_CODE").equals("ar0909")){
				num = loginDao.getSickLeaveProofConfirmCnt(paramMap);
			}/*else if(temp.get("MENU_CODE").equals("ar0902")){
				num = loginDao.getOtConfirmCnt(paramMap);
			}else if(temp.get("MENU_CODE").equals("ar0904")){
				num = loginDao.getTempConfirmCnt(paramMap);
			}else if(temp.get("MENU_CODE").equals("ar0906")){
				num = loginDao.getStartedLeftConfirmCnt(paramMap);
			}else if(temp.get("MENU_CODE").equals("ess3444")){
				num = myHomeDao.getTipsCnt("getChangShopConfirmCount", paramMap);
			}else if(temp.get("MENU_CODE").equals("ar0907")){
				if(paramMap.get("CPNY_ID").equals("SPC_SH")){
					num = loginDao.getChangeDeptConfirmCnt(paramMap);
				}else{
					num = 0;
				}
			}*/
				if(num != -1 ){
					rMap.put(temp.get("MENU_CODE"), num);
					total_count_viewInfo += num;
				}
			}
		}
		rMap.put("total_count_viewInfo", total_count_viewInfo);
		
		return rMap;
	}
	
	@SuppressWarnings("unchecked")
	public Object getTipsForLogin(HttpServletRequest request) {
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
		
		int total_count_viewInfo = -1;
		List pMenuList = myHomeDao.getTipsMenu(paramMap);
		if(pMenuList.size()==0){
			rMap.put("pview", "false");
		}else{
			total_count_viewInfo = 0;
			rMap.put("pview", "true");
			for (Object object : pMenuList) {
				Map temp = (Map)object;
				int num =-1;
				if(admin.getCpnyId().equals("HTSV") || admin.getCpnyId().equals("HAE")){
					if (temp.get("MENU_CODE").equals("ess0601")) {
						num = loginDao.viewApprovalInfo(paramMap) != null?loginDao.viewApprovalInfo(paramMap).size():0;
					}else if(temp.get("MENU_CODE").equals("ess0603")){
						num = loginDao.viewBatchApprovalInfo(paramMap) != null?loginDao.viewBatchApprovalInfo(paramMap).size():0;
					}else if(temp.get("MENU_CODE").equals("ess3436")){
						num = myHomeDao.getTipsCnt("getAttendanceForDayCount", paramMap);
					}else if(temp.get("MENU_CODE").equals("ess3444")){
						num = myHomeDao.getTipsCnt("getChangShopConfirmCount", paramMap);
					}
				}else{
					if (temp.get("MENU_CODE").equals("ess0601")) {
						num = loginDao.viewApprovalInfo(paramMap) != null?loginDao.viewApprovalInfo(paramMap).size():0;
					}else if(temp.get("MENU_CODE").equals("ess3438")){
						num = myHomeDao.getTipsCnt("getAttendanceForDayShCount", paramMap);
					}else if(temp.get("MENU_CODE").equals("ess3444")){
						num = myHomeDao.getTipsCnt("getChangShopConfirmCount", paramMap);
					}
				}
				if(num != -1 ){
					rMap.put(temp.get("MENU_CODE"), num);
					total_count_viewInfo += num;
				}
			}
		}
		rMap.put("total_count_viewInfo", total_count_viewInfo);
		
		return rMap;
	}
	
	@SuppressWarnings("unchecked")
	public Object getTipsForLogin_home(HttpServletRequest request) {
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
		
		int total_count_viewInfo = -1;
		List pMenuList = myHomeDao.getTipsMenu(paramMap);
		if(pMenuList.size()==0){
			rMap.put("pview", "false");
		}else{
			total_count_viewInfo = 0;
			rMap.put("pview", "true");
			for (Object object : pMenuList) {
				Map temp = (Map)object;
				int num =-1;
				if(temp.get("MENU_CODE").equals("sy0501")){
					Map map = (Map) essApplyInfoDao.getApplyNumber(paramMap);
					num = Integer.parseInt(map.get("NUMB").toString()) ;
				}else if(temp.get("MENU_CODE").equals("ar0901")){
					num = loginDao.getArExConfirmCnt(paramMap);
				}else if(temp.get("MENU_CODE").equals("ar0903")){
					num = loginDao.getLeaveConfirmCnt(paramMap);
				}else if(temp.get("MENU_CODE").equals("ar0909")){
					num = loginDao.getSickLeaveProofConfirmCnt(paramMap);
				}/*else if(temp.get("MENU_CODE").equals("ar0902")){
					num = loginDao.getOtConfirmCnt(paramMap);
				}else if(temp.get("MENU_CODE").equals("ar0904")){
					num = loginDao.getTempConfirmCnt(paramMap);
				}else if(temp.get("MENU_CODE").equals("ar0906")){
					num = loginDao.getStartedLeftConfirmCnt(paramMap);
				}else if(temp.get("MENU_CODE").equals("ess3444")){
					num = myHomeDao.getTipsCnt("getChangShopConfirmCount", paramMap);
				}else if(temp.get("MENU_CODE").equals("ar0907")){
					if(paramMap.get("CPNY_ID").equals("SPC_SH")){
						num = loginDao.getChangeDeptConfirmCnt(paramMap);
					}else{
						num = 0;
					}
				}*/
				if(num != -1 ){
					rMap.put(temp.get("MENU_CODE"), num);
					total_count_viewInfo += num;
				}
			}
		}
		rMap.put("total_count_viewInfo", total_count_viewInfo);
		
		return rMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map getSystemDate(LinkedHashMap paramMap){
		Map result = myHomeDao.getSystemDate(paramMap) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int createLoginInfo(HttpServletRequest request, LinkedHashMap paramMap){
		int resultInt=0;
		int cnt = Integer.parseInt(paramMap.get("cnt").toString());
		try {
			if(cnt>1){
				this.myHomeDao.updateLoginInfo(paramMap);
	        }else{
	        	this.myHomeDao.addLoginInfo(paramMap);
	        }
			resultInt = 1;
		} catch (Exception e) {
			resultInt = 0;
			e.printStackTrace();
		}
		return resultInt;
	}
	
	@SuppressWarnings("unchecked")
	public Map getSealControl(LinkedHashMap paramMap){
		Map result = myHomeDao.getSealControl(paramMap) ;
		return result;
	}

}
