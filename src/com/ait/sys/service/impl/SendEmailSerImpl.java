package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.evs.dao.EvsManageDao;
import com.ait.sys.service.SendEmailSer;
import com.ait.web.util.DateUtil;
import com.ait.web.util.MailManager;
import com.ait.web.util.StringUtil;

/**
 * 发送审批邮件
 *
 */
@Service
public class SendEmailSerImpl implements SendEmailSer{

	Logger logger = Logger.getLogger(SendEmailSer.class);

	@Autowired
	private InfoApplyDao infoApplyDao;
	@Autowired
	MailManager mailManger;
	@Autowired
	private EvsManageDao evsManageDao;
	
	public void sendAffirmEmail(){
		boolean flag = true;
		List sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "sendAffirmEmail");
		if(sendAffirmEmailList != null && sendAffirmEmailList.size() > 0){
			for (int i = 0;i < sendAffirmEmailList.size(); i++){
				Map map = (Map)sendAffirmEmailList.get(i);
				//0：如果是待发送状态 直接发送    3：需要先封装模板的，先读取模板再发送
				if("3".equals(StringUtil.checkNull(map.get("SEND_FLAG")))){
					if("31".equals(StringUtil.checkNull(map.get("APPLY_TYPE_CODE")))){//加班
						this.composeOtMailTemplate(map);
					}else if("21".equals(StringUtil.checkNull(map.get("APPLY_TYPE_CODE")))){//考勤
						this.composeLeaveMailTemplate(map);
					}
				}
				flag = mailManger.sendmail(map);
				//更新邮件发送状态
				try {
					if(flag){
						map.put("SEND_FINISH_FLAG", 1);
					}else{
						map.put("SEND_FINISH_FLAG", 4);
					}
					infoApplyDao.viewModifyApprovalInfo(map, "updateMailSendStatus");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 封装加班信息
	 * @param map
	 * @return
	 */
	private void composeOtMailTemplate(Map map){
		Map resultMap = null;
		StringBuffer affirmContent = new StringBuffer();
		List otApplyInfo = infoApplyDao.viewApprovalInfo(map, "getSSTOtInfoList");
		if(otApplyInfo != null && otApplyInfo.size() > 0){
			resultMap = (Map)otApplyInfo.get(0);
		}
		if(resultMap != null){
			//获取当前审批者
			List viewAffirmList = infoApplyDao.viewApprovalInfo(map, "viewAffirmList");
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					if(i == 0){
						resultMap.put("TITLE", StringUtil.checkNull(affirmMap.get("TITLE")));
						resultMap.put("APPLY_PERSON_INFO", StringUtil.checkNull(affirmMap.get("APPLY_PERSON_INFO")));
					}
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_LEVEL")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_TYPE_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_FLAG_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_CONTENT")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("UPDATE_DATE")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("DEPTNAME")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT", affirmContent.toString());
			resultMap.put("MAIL_LOGIN_EMPID", map.get("MAIL_LOGIN_EMPID"));
			map.put("EMAIL_CONTENT", MailManager.composeTemplate("OT", resultMap));
		}
	}
	
	/**
	 * 封装休假信息
	 * @param map
	 * @return
	 */
	private void composeLeaveMailTemplate(Map map){
		Map resultMap = null;
		StringBuffer affirmContent = new StringBuffer();
		List LeaveApplyInfo = infoApplyDao.viewApprovalInfo(map, "getSSTLeaveAffirmInfoList");
		if(LeaveApplyInfo != null && LeaveApplyInfo.size() > 0){
			resultMap = (Map)LeaveApplyInfo.get(0);
		}
		if(resultMap != null){
			//获取当前审批者
			List viewAffirmList = infoApplyDao.viewApprovalInfo(map, "viewAffirmList");
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					if(i == 0){
						resultMap.put("TITLE", StringUtil.checkNull(affirmMap.get("TITLE")));
						resultMap.put("APPLY_PERSON_INFO", StringUtil.checkNull(affirmMap.get("APPLY_PERSON_INFO")));
					}
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_LEVEL")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_TYPE_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_FLAG_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("AFFIRM_CONTENT")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("UPDATE_DATE")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("DEPTNAME")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT", affirmContent.toString());
			resultMap.put("MAIL_LOGIN_EMPID", map.get("MAIL_LOGIN_EMPID"));
			//年假 封装年假使用情况
			if("26".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
				Map vacTempMap = new LinkedHashMap();
				vacTempMap.put("APPLY_PERSON_ID", map.get("APPLY_PERSON_ID"));
				vacTempMap.put("YEAR", StringUtil.checkNull(map.get("YEAR")));
				List empVacInfoList = infoApplyDao.viewApprovalInfo(vacTempMap, "getEmpVacInfoSSTForDisplay");
				if(empVacInfoList != null && empVacInfoList.size() > 0){
					Map vacMap = (Map)empVacInfoList.get(0);
					resultMap.put("TOTAL_VAC", vacMap.get("TOTAL_VAC"));
					resultMap.put("USE_VAC", vacMap.get("USE_VAC"));
					resultMap.put("LEAVE_VAC", vacMap.get("LEAVE_VAC"));
				}else{
					resultMap.put("TOTAL_VAC", "0");
					resultMap.put("USE_VAC", "0");
					resultMap.put("LEAVE_VAC", "0");
				}
				map.put("EMAIL_CONTENT", MailManager.composeTemplate("VAC", resultMap));
			}else{
				map.put("EMAIL_CONTENT", MailManager.composeTemplate("LEAVE", resultMap));
			}
		}
	}
	
	/**
	 * HTSV审批邮件发送
	 */
	public void sendAffirmEmailHTSV(int affirmLevel){

		//读取模板信息
		String template = MailManager.readTemplate("approvalHTSV");
		
		if (affirmLevel == 0) {
			template = MailManager.readTemplate("remindApprovalHTSV");
		} else if (affirmLevel == 4)  {
			template = MailManager.readTemplate("submitOt");
		} 
		

		List isWeekendList = infoApplyDao.viewApprovalInfo(null, "isWeekend");
		Map isWeekendmap = (Map)isWeekendList.get(0);
		//获取需要接受邮件的审批人
		List sendAffirmEmailList = new ArrayList();
		if(affirmLevel == 1){
			sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "getAffirmDeptManger1");
		}else if(affirmLevel == 2){
			sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "getAffirmDeptManger2");
		}else if(affirmLevel == 3){
			sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "getAffirmSuper");
		} else if(affirmLevel == 0){
			sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "getAffirmDeptManger0");
		} else if(affirmLevel == 4){
			sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "getEmployeeEmail");
		}
		
		//封装邮件信息并发送
		if(sendAffirmEmailList != null && sendAffirmEmailList.size() > 0){
			for (int i = 0;i < sendAffirmEmailList.size(); i++){
				Map map = (Map)sendAffirmEmailList.get(i);
				if(affirmLevel == 2){
					map.put("MAIL_LANGUAGE", "ko");
				}else{
					map.put("MAIL_LANGUAGE", "vi");
				}
				if(StringUtil.checkNull(isWeekendmap.get("CURRENT_DATE")).equals(StringUtil.checkNull(isWeekendmap.get("WORKDAY")))){
					if(!StringUtil.checkNull(isWeekendmap.get("CURRENT_DATE")).equals(StringUtil.checkNull(isWeekendmap.get("END_DATE")))){
						map.put("EMAIL_TITLE", "Approve Info Weekend【YHR】Date：" + isWeekendmap.get("START_DATE") + "~" + isWeekendmap.get("END_DATE"));
						map.put("WEEKEND_FLAG", "WEEKEND");
						map.put("START_DATE", isWeekendmap.get("START_DATE"));
						map.put("END_DATE", isWeekendmap.get("END_DATE"));
						//封装模板信息
						this.composeMailTemplate(template,map);
						//发送
						mailManger.sendmail(map);
					}
					map.put("EMAIL_TITLE", "Approve Info【YHR】Date：" + isWeekendmap.get("CURRENT_DATE"));
					map.put("WEEKEND_FLAG", "WORKDAY");
					//封装模板信息
					this.composeMailTemplate(template,map);
					//发送
					mailManger.sendmail(map);
				}
			}
		}
	}

	/**
	 * 封装审批信息TSTO
	 * @param map
	 * @return
	 */
	private void composeMailTemplate(String template,Map map){
		Map resultMap = new LinkedHashMap();
		if (StringUtil.checkNull(map.get("OT_FLAG")).equals("OT_THAN_20")) {
			getOtExceed20Info(map,resultMap);
		}else if (StringUtil.checkNull(map.get("OT_FLAG")).equals("OT_THAN_200")) {
			getOtExceed200Info(map,resultMap);
		} else {
		//只有工作日才有必要查询申请的休假信息
		if(StringUtil.checkNull(map.get("WEEKEND_FLAG")).equals("WORKDAY")){
			composeMailTemplateAtt(map,resultMap);
		}
		composeMailTemplateOt(map,resultMap);
		composeMailTemplateOtTotal(map,resultMap);
		}
		resultMap.put("MAIL_LOGIN_EMPID", map.get("MAIL_LOGIN_EMPID"));
//		resultMap.put("MAIL_LANGUAGE", map.get("MAIL_LANGUAGE"));
		map.put("EMAIL_CONTENT", MailManager.composeTemplateByParam(template, resultMap));
		map.put("EMAIL_CONTENT", map.get("EMAIL_CONTENT").toString().replace("{MAIL_LANGUAGE}",map.get("MAIL_LANGUAGE").toString()));
	}

	/**
	 * 封装考勤信息HTSV
	 * @param map
	 * @return
	 */
	private void composeMailTemplateAtt(Map map,Map resultMap){
		StringBuffer affirmContent = new StringBuffer();
		//工作日 周末 区分标示
		//String weekFlag = StringUtil.checkNull(map.get("WEEKEND_FLAG"));
		if(resultMap != null){
			//封装考勤信息HTSV
			List viewAffirmList = null;
			//if("WEEKEND".equals(weekFlag)){
			//	viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateChuchaiWEEKEND");
			//}else{
			//获取考勤申请信息
				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateAtt");
			//}
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + (i + 1) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: left\">" + StringUtil.checkNull(affirmMap.get("ORG_NAME_LOCAL")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("EMP_CNT")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT_CHUCHAI", affirmContent.toString());
		}
	}
	
	/**
	 * 封装加班信息HTSV
	 * @param map
	 * @return
	 */
	private void composeMailTemplateOt(Map map,Map resultMap){
		StringBuffer affirmContent = new StringBuffer();
		//工作日 周末 区分标示
		String weekFlag = StringUtil.checkNull(map.get("WEEKEND_FLAG"));
		if(resultMap != null){
			//获取封装加班信息TSTO
			List viewAffirmList = null;
			if("WEEKEND".equals(weekFlag)){
				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtWEEKEND");
			}else{
				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOt");
			}
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + (i + 1) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: left\">" + StringUtil.checkNull(affirmMap.get("ORG_NAME_LOCAL")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("EMP_CNT")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT_OT", affirmContent.toString());
		}
	}
	
	/**
	 * 封装加班月统计信息TSTO
	 * @param map
	 * @return
	 */
	private void composeMailTemplateOtTotal(Map map,Map resultMap){
		StringBuffer affirmContent = new StringBuffer();
		//工作日 周末 区分标示
//		String weekFlag = StringUtil.checkNull(map.get("WEEKEND_FLAG"));
		if(resultMap != null){
			//要封装的信息
			List viewAffirmList = null;
//			if("WEEKEND".equals(weekFlag)){
//				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtTotalWEEKEND");
//			}else{
				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtTotal");
//			}
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + (i + 1) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: left\">" + StringUtil.checkNull(affirmMap.get("ORG_NAME_LOCAL")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("EMP_CNT")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("OT_LENGTH")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("LENGTH_FLAG20")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("LENGTH_FLAG30")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT_OT_TOTAL", affirmContent.toString());
		}
	}
	
	/**
	 * 发送社会活动邮件
	 */
	public void sendActivityEmail(Map mapParameter){
		boolean flag = true;
		List sendAffirmEmailList = evsManageDao.viewEvsList(mapParameter, "viewActivityEmailList");
		if(sendAffirmEmailList != null && sendAffirmEmailList.size() > 0){
			for (int i = 0;i < sendAffirmEmailList.size(); i++){
				Map map = (Map)sendAffirmEmailList.get(i);
				flag = mailManger.sendmail(map);
			}
		}
	}
	
	public void sendEvsEmail(){
		boolean flag = true;
		List sendAffirmEmailList = infoApplyDao.viewApprovalInfo(null, "sendEvsEmail");
		if(sendAffirmEmailList != null && sendAffirmEmailList.size() > 0){
			for (int i = 0;i < sendAffirmEmailList.size(); i++){
				Map map = (Map)sendAffirmEmailList.get(i);
				flag = mailManger.sendmail(map);
				//更新邮件发送状态
				try {
					if(flag){
						map.put("SEND_FINISH_FLAG", 1);
					}else{
						map.put("SEND_FINISH_FLAG", 4);
					}
					infoApplyDao.viewModifyApprovalInfo(map, "updateEvsMailSendStatus");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//hms 2019/03/19  把加班超过20小时的员工讯息发达Team Leader
	public void sendOtInfoEmailHTSV(int level) {
		//读取模板信息
		String template = MailManager.readTemplate("approvalOt20Hours");
		List isWeekendList = infoApplyDao.viewApprovalInfo(null, "isWeekend");
		Map isWeekendmap = (Map)isWeekendList.get(0);
		//获取需要接受邮件的审批人
		List affirmList = new ArrayList();
		affirmList  = infoApplyDao.viewApprovalInfo(null, "getLeaderList");
		
		//封装邮件信息并发送
		if (affirmList != null && affirmList.size() > 0) {
			for (int i = 0; i < affirmList.size(); i++) {
				Map map = (Map)affirmList.get(i);
				map.put("MAIL_LANGUAGE", "ko");
				
				List viewAffirmList =  infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtExceed20Hours");
				if(StringUtil.checkNull(isWeekendmap.get("CURRENT_DATE")).equals(StringUtil.checkNull(isWeekendmap.get("WORKDAY"))) && viewAffirmList != null && viewAffirmList.size() > 0){
					map.put("EMAIL_TITLE", "Employees who have overtime over 20 hours this month");
					map.put("WEEKEND_FLAG", "WORKDAY");
					map.put("OT_FLAG", "OT_THAN_20");
					//封装模板信息
					this.composeMailTemplate(template,map);
					//发送
					mailManger.sendmail(map);
				}
				Calendar calendar = Calendar.getInstance();
				int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				if (dayOfWeek == Calendar.MONDAY) {
					List viewAffirmList200 =  infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtExceed200Hours");
					if(viewAffirmList200 != null && viewAffirmList200.size() > 0){
						map.put("EMAIL_TITLE", "Employees who have overtime over 200 hours this year");
						map.put("WEEKEND_FLAG", "WORKDAY");
						map.put("OT_FLAG", "OT_THAN_200");
						//封装模板信息
						this.composeMailTemplate(template,map);
						//发送
						mailManger.sendmail(map);
					}
				}
			}
		}
	}
	
	private void getOtExceed20Info(Map map, Map resultMap) {
		StringBuffer affirmContent = new StringBuffer();
		//工作日 周末 区分标示
		if(resultMap != null){
			//要封装的信息
			List viewAffirmList = null;
				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtExceed20Hours");
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + (i + 1) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: left\">" + StringUtil.checkNull(affirmMap.get("EMPID")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("LOCAL_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("DEPT_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("OT_LIMT")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("OT_TOTAIL_MONTH")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT_OT", affirmContent.toString());
		}
	}
	
	private void getOtExceed200Info(Map map, Map resultMap) {
		StringBuffer affirmContent = new StringBuffer();
		//工作日 周末 区分标示
		if(resultMap != null){
			//要封装的信息
			List viewAffirmList = null;
				viewAffirmList = infoApplyDao.viewApprovalInfo(map, "composeMailTemplateOtExceed200Hours");
			if(viewAffirmList != null && viewAffirmList.size() > 0){
				for(int i=0;i<viewAffirmList.size();i++){
					Map affirmMap = (Map)viewAffirmList.get(i);
					affirmContent.append("<tr>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + (i + 1) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: left\">" + StringUtil.checkNull(affirmMap.get("EMPID")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("LOCAL_NAME")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("DEPT_NAME")) + "</td>");
					//affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("OT_LIMT")) + "</td>");
					affirmContent.append("<td class=\"td_type\" style=\"text-align: center\">" + StringUtil.checkNull(affirmMap.get("OT_TOTAIL_YEAR")) + "</td>");
					affirmContent.append("</tr>");
				}
			}
			resultMap.put("AFFIRM_CONTENT_OT", affirmContent.toString());
		}
	}
}
