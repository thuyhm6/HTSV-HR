package com.ait.ar.action.attendanceMintenance;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.ar.service.ArEmailSer;
import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.MailManager;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArEmailCtroller {
	@Autowired
	private ArEmailSer arEmailSer;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private AttendanceKeeperSer attendanceKeeperSer ;
	/**
	 * 服务器ip:端口号
	 */
    @Value("${serverIp}")
	private String SERVER_IP;

	/**
	 * 明细计算页面发送邮件(sendArmail)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/sendArEmpEmail")
	@ResponseBody
	public String sendArEmpEmail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//促销员区间的不发邮件到个人
		if(!StringUtil.checkNull(request.getParameter("STAT_NO")).equals("219948")){
			List arHistoryinfo = this.arEmailSer.getArEmailList(request,null,null);
			for(int i=0;i<arHistoryinfo.size();i++){
				StringBuffer context=new StringBuffer();	
				Map map=(Map) arHistoryinfo.get(i);
	            String title = "个人月考勤汇总";
	            String address = StringUtil.checkNull(map.get("EMAIL")).toString();
	            if(address.equals(""))
	            	continue ;
	            if(!"TSTO".equals(admin.getCpnyId())){
	            context.append(
	    				"<H1>" + "您好:" + "</H1>" + "您"
	    						+ map.get("AR_MONTH")
	    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
	    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
	    						+ "<tr>"
	    						+ "<td colspan='21' style='text-align:center'>考勤基本信息</td>"
	    						+ "</tr>" + "<tr>"
	    						+ "<td width='4%' rowspan='2'>姓名:"+map.get("LOCAL_NAME")+"</td>"
	    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
	    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
	    						+ "<td colspan='3' style='text-align:center'>加班项目组</td>"
	    						+ "<td colspan='13' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
	    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
	    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班</td>" + "<td style='text-align:center'>法定加班</td>"
	    					    + "<td style='text-align:center'>事假</td>"
	    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
	    						+ "<td style='text-align:center'>夜班次数</td>"
	    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
	    						+ "<td style='text-align:center'>丧假</td>"
	    						+ "<td style='text-align:center'>工伤假</td>" 
	    						+ "<td style='text-align:center'>哺乳假</td>" 
	    						+ "<td style='text-align:center'>陪产假</td>" 
	    						+ "<td style='text-align:center'>流产假</td>" 
	    						+ "</tr>" 
	    						+ "<tr>" 
	    						+ "<td style='text-align:center'>"
	    						+ map.get("EMPID") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("AR_MONTH") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("ZS_CHIDAO_YIXIA") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("ZS_ZAOTUI_YIXIA") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("KUANGGONG_ZS") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("PAY_PINGSHI_OT_ZS") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("PAY_ZM_OT_ZS") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("PAY_FADING_OT_ZS_ZJ") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("SHIJIA_ZS") 
	    						+ "</td>"+ "<td style='text-align:center'>" 
	    						+ map.get("SHIYONG_NIANJIA_DAY") 
	    						+ "</td>"+ "<td style='text-align:center'>" 
	    						+ map.get("SHIYONG_FULI_DAY") 
	    						+ "</td>" + "<td style='text-align:center'>" 
	    						+ map.get("CHUCHAI_DAY") 
	    						+ "</td>" + "<td style='text-align:center'>" 
	    						+ map.get("YEBAN_CISHU") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("BINGJIA_ZS") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("HUNJIA_DAY") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("CHANJIA_DAY") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("SANGJIA_DAY") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("GONGSHANGJIA") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("BURUJIA") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("PEICHANJIA") 
	    						+ "</td>" + "<td style='text-align:center'>"
	    						+ map.get("LIUCHANJIA") 
	    						+ "</td>" + "</tr>"
	    						+ "</table>"+
	    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
	    					      "谢谢合作！祝您工作愉快！"+
	    					      "人事部");
	            }else{
	            	 context.append(
	 	    				"<H1>" + "您好:" + "</H1>" + "您"
	 	    						+ map.get("AR_MONTH")
	 	    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
	 	    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
	 	    						+ "<tr>"
	 	    						+ "<td colspan='20' style='text-align:center'>考勤基本信息</td>"
	 	    						+ "</tr>" + "<tr>"
	 	    						+ "<td width='4%' rowspan='2'>姓名:"+map.get("LOCAL_NAME")+"</td>"
	 	    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
	 	    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
	 	    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
	 	    						+ "<td colspan='3' style='text-align:center'>追溯加班项目组</td>"
	 	    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
	 	    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
	 	    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"
	 	    						+ "<td style='text-align:center'>法定加班</td>"
	 	    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>"
	 	    						+ "<td style='text-align:center'>追溯法定加班</td>"
	 	    					    + "<td style='text-align:center'>事假</td>"
	 	    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
	 	    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
	 	    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" + "<tr>" + "<td style='text-align:center'>"
	 	    						+ map.get("EMPID") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("AR_MONTH") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("KUANGGONG_ZS") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("PAY_ZM_OT_ZS") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" +"<td style='text-align:center'>"
	 	    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>" + "<td style='text-align:center'>"
	 	    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" +
	 	    						"<td style='text-align:center'>"
	 	    						+ map.get("SHIJIA_ZS") + "</td>"
	 	    						+ "<td style='text-align:center'>" 
	 	    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
	 	    						+ "<td style='text-align:center'>" 
	 	    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
	 	    						+ "<td style='text-align:center'>" 
	 	    						+ map.get("CHUCHAI_DAY") + "</td>" 
	 	    						+ "<td style='text-align:center'>"
	 	    						+ map.get("BINGJIA_ZS") + "</td>" 
	 	    						+ "<td style='text-align:center'>"
	 	    						+ map.get("HUNJIA_DAY") + "</td>" 
	 	    						+ "<td style='text-align:center'>"
	 	    						+ map.get("CHANJIA_DAY") + "</td>" 
	 	    						+ "<td style='text-align:center'>"
	 	    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
	 	    						+ "</table>"+
	 	    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
	 	    					      "谢谢合作！祝您工作愉快！"+
	 	    					      "人事部");
	            }
	            mailManager.sendMail(title,context.toString(),address);
			}
		}
		
		//查找CH所有的考勤员
		List<LinkedHashMap<String, Object>> attendanceKeeperList = this.attendanceKeeperSer.getAttendanceKeeperList(request) ;
		//根据部门和人员类型查出所有有权限管理的人
		if(attendanceKeeperList!=null && attendanceKeeperList.size()>0){
			for(int k=0;k<attendanceKeeperList.size();k++){
				String address = ""; //sendmap.get("RCVR_EMAIL_ADDR").toString();
				Map arMap = attendanceKeeperList.get(k);
				List arinfoList = this.arEmailSer.getArEmailList(request,arMap.get("PERSON_ID").toString(),null);
				if(arinfoList!=null && arinfoList.size()>0){
					StringBuffer context=new StringBuffer();	
					if(arinfoList.size()==1){
						Map map=(Map) arinfoList.get(0);
						if(!"TSTO".equals(admin.getCpnyId())){
			            context.append(
			    				"<H1>" + "您好:" + "</H1>" + "您"
			    						+ map.get("AR_MONTH")
			    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
			    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
			    						+ "<tr>"
			    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
			    						+ "</tr>" + "<tr>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
			    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
			    						+ "<td colspan='3' style='text-align:center'>加班项目组</td>"
			    						+ "<td colspan='13' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
			    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
			    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班</td>"
			    						+ "<td style='text-align:center'>法定加班</td>"
			    					    + "<td style='text-align:center'>事假</td>"
			    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
			    						+ "<td style='text-align:center'>夜班次数</td>"
			    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
			    						+ "<td style='text-align:center'>丧假</td>"
			    						+ "<td style='text-align:center'>工伤假</td>"
			    						+ "<td style='text-align:center'>哺乳假</td>"
			    						+ "<td style='text-align:center'>陪产假</td>"
			    						+ "<td style='text-align:center'>流产假</td>"
			    						+ "</tr>" 
			    						+ "<tr>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("LOCAL_NAME") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("EMPID") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("AR_MONTH") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("KUANGGONG_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("SHIJIA_ZS") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("CHUCHAI_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>" 
	    						        + map.get("YEBAN_CISHU") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("BINGJIA_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("HUNJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("CHANJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("SANGJIA_DAY") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("GONGSHANGJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("BURUJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PEICHANJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("LIUCHANJIA") + "</td>"
			    						+ "</tr>"
			    						+ "</table>"+
			    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
			    					      "谢谢合作！祝您工作愉快！"+
			    					      "人事部");
						}else{
							context.append(
				    				"<H1>" + "您好:" + "</H1>" + "您"
				    						+ map.get("AR_MONTH")
				    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
				    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
				    						+ "<tr>"
				    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
				    						+ "</tr>" + "<tr>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
				    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
				    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
				    						+ "<td colspan='4' style='text-align:center'>追溯加班项目组</td>"
				    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
				    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
				    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"
				    						+ "<td style='text-align:center'>法定加班</td>"
				    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>" 
				    						+ "<td style='text-align:center'>追溯法定加班</td>"
				    					    + "<td style='text-align:center'>事假</td>"
				    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
				    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
				    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" 
				    						+ "<tr>"
				    						+ "<td style='text-align:center'>"
					    					+ map.get("LOCAL_NAME") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("EMPID") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("AR_MONTH") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("KUANGGONG_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("SHIJIA_ZS") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("CHUCHAI_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("BINGJIA_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("HUNJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("CHANJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
				    						+ "</table>"+
				    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
				    					      "谢谢合作！祝您工作愉快！"+
				    					      "人事部");
						}
			            if(arMap.get("PERSON_ID").equals(map.get("PERSON_ID")) || arMap.get("PERSON_ID")==map.get("PERSON_ID")){
			            	address = StringUtil.checkNull(map.get("EMAIL")).toString();
						}    
					}else if(arinfoList.size()==2){
						if(!"TSTO".equals(admin.getCpnyId())){
						Map map=(Map) arinfoList.get(0);
			            context.append(
			    				"<H1>" + "您好:" + "</H1>" + "您"
			    						+ map.get("AR_MONTH")
			    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
			    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
			    						+ "<tr>"
			    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
			    						+ "</tr>" + "<tr>"
			    						+ "<td width='4%' rowspan='2'  style='text-align:center'>姓名</td>"
			    						+ "<td width='4%' rowspan='2'  style='text-align:center'>社号</td>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
			    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
			    						+ "<td colspan='3' style='text-align:center'>加班项目组</td>"
			    						+ "<td colspan='13' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
			    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
			    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班</td>"
			    						+ "<td style='text-align:center'>法定加班</td>"
			    					    + "<td style='text-align:center'>事假</td>"
			    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
			    						+ "<td style='text-align:center'>夜班次数</td>"
			    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
			    						+ "<td style='text-align:center'>丧假</td>" 
			    						+ "<td style='text-align:center'>工伤假</td>" 
			    						+ "<td style='text-align:center'>哺乳假</td>" 
			    						+ "<td style='text-align:center'>陪产假</td>" 
			    						+ "<td style='text-align:center'>流产假</td>" 
			    						+ "</tr>" 
			    						+ "<tr>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("LOCAL_NAME") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("EMPID") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("AR_MONTH") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("KUANGGONG_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("SHIJIA_ZS") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("CHUCHAI_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>" 
	    						        + map.get("YEBAN_CISHU") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("BINGJIA_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("HUNJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("CHANJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("SANGJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("GONGSHANGJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("BURUJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PEICHANJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("LIUCHANJIA") + "</td>"
			    						+ "</tr>"
			    						);
			            Map map1=(Map) arinfoList.get(1);
			            context.append(
			    						 "<tr>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("LOCAL_NAME") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("EMPID") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("AR_MONTH") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("ZS_CHIDAO_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("ZS_ZAOTUI_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("KUANGGONG_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PAY_PINGSHI_OT_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PAY_ZM_OT_ZS") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("SHIJIA_ZS") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map1.get("SHIYONG_NIANJIA_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map1.get("SHIYONG_FULI_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map1.get("CHUCHAI_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>" 
	    						        + map.get("YEBAN_CISHU") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("BINGJIA_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("HUNJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("CHANJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("SANGJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("GONGSHANGJIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("BURUJIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PEICHANJIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("LIUCHANJIA") + "</td>" 
			    						+ "</tr>"
			    						+ "</table>"+
			    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
			    					      "谢谢合作！祝您工作愉快！"+
			    					      "人事部");
			            if(arMap.get("PERSON_ID").equals(map.get("PERSON_ID")) || arMap.get("PERSON_ID")==map.get("PERSON_ID")){
			            	address = StringUtil.checkNull(map.get("EMAIL")).toString();
						}  
						if(arMap.get("PERSON_ID").equals(map1.get("PERSON_ID")) || arMap.get("PERSON_ID")==map1.get("PERSON_ID")){
			            	address = StringUtil.checkNull(map1.get("EMAIL")).toString();
						}  
						}else{
							Map map=(Map) arinfoList.get(0);
							context.append(
				    				"<H1>" + "您好:" + "</H1>" + "您"
				    						+ map.get("AR_MONTH")
				    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
				    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
				    						+ "<tr>"
				    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
				    						+ "</tr>" + "<tr>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
				    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
				    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
				    						+ "<td colspan='4' style='text-align:center'>追溯加班项目组</td>"
				    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
				    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
				    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"
				    						+ "<td style='text-align:center'>法定加班</td>"
				    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>" 
				    						+ "<td style='text-align:center'>追溯法定加班</td>"
				    					    + "<td style='text-align:center'>事假</td>"
				    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
				    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
				    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" 
				    						+ "<tr>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("LOCAL_NAME") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("EMPID") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("AR_MONTH") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("KUANGGONG_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("SHIJIA_ZS") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("CHUCHAI_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("BINGJIA_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("HUNJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("CHANJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
				    						);
							Map map1=(Map) arinfoList.get(1);
							context.append(
				    						  "<tr>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("LOCAL_NAME") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("EMPID") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("AR_MONTH") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("ZS_CHIDAO_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("ZS_ZAOTUI_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("KUANGGONG_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_ZM_OT_ZS_Z") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("Z_PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("SHIJIA_ZS") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map1.get("SHIYONG_NIANJIA_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map1.get("SHIYONG_FULI_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map1.get("CHUCHAI_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("BINGJIA_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("HUNJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("CHANJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("SANGJIA_DAY") + "</td>" + "</tr>"
				    						+ "</table>"+
				    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
				    					      "谢谢合作！祝您工作愉快！"+
				    					      "人事部");
							if(arMap.get("PERSON_ID").equals(map.get("PERSON_ID")) || arMap.get("PERSON_ID")==map.get("PERSON_ID")){
				            	address = StringUtil.checkNull(map.get("EMAIL")).toString();
							}  
							if(arMap.get("PERSON_ID").equals(map1.get("PERSON_ID")) || arMap.get("PERSON_ID")==map1.get("PERSON_ID")){
				            	address = StringUtil.checkNull(map1.get("EMAIL")).toString();
							}  
						}
			              
					}else if(arinfoList.size() > 2){
						if(!"TSTO".equals(admin.getCpnyId())){
						Map map=(Map) arinfoList.get(0);
			            context.append(
			    				"<H1>" + "您好:" + "</H1>" + "您"
			    						+ map.get("AR_MONTH")
			    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
			    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
			    						+ "<tr>"
			    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
			    						+ "</tr>" + "<tr>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
			    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
			    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
			    						+ "<td colspan='3' style='text-align:center'>加班项目组</td>"
			    						+ "<td colspan='13' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
			    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
			    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班</td>"
			    						+ "<td style='text-align:center'>法定加班</td>"
			    					    + "<td style='text-align:center'>事假</td>"
			    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
			    						+ "<td style='text-align:center'>夜班次数</td>" 
			    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
			    						+ "<td style='text-align:center'>丧假</td>" 
			    						+ "<td style='text-align:center'>工伤假</td>" 
			    						+ "<td style='text-align:center'>哺乳假</td>" 
			    						+ "<td style='text-align:center'>陪产假</td>" 
			    						+ "<td style='text-align:center'>流产假</td>" 
			    						+ "</tr>" 
			    						+ "<tr>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("LOCAL_NAME") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("EMPID") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("AR_MONTH") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("KUANGGONG_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("SHIJIA_ZS") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("CHUCHAI_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("YEBAN_CISHU") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("BINGJIA_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("HUNJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("CHANJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("SANGJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("GONGSHANGJIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("BURUJIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("PEICHANJIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map.get("LIUCHANJIA") + "</td>" 
			    						+ "</tr>"
			    						);
			            for(int m=1;m<arinfoList.size()-1;m++){
			            	Map paramMap=(Map) arinfoList.get(m);
			            	context.append("<tr>"
			            			+ "<td style='text-align:center'>"
		    						+ paramMap.get("LOCAL_NAME") + "</td>" 
			            	        + "<td style='text-align:center'>"
		    						+ paramMap.get("EMPID") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("AR_MONTH") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("ZS_CHIDAO_YIXIA") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("ZS_ZAOTUI_YIXIA") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("KUANGGONG_ZS") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("PAY_PINGSHI_OT_ZS") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("PAY_ZM_OT_ZS") + "</td>"
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("SHIJIA_ZS") + "</td>"
		    						+ "<td style='text-align:center'>" 
		    						+ paramMap.get("SHIYONG_NIANJIA_DAY") + "</td>"
		    						+ "<td style='text-align:center'>" 
		    						+ paramMap.get("SHIYONG_FULI_DAY") + "</td>"
		    						+ "<td style='text-align:center'>" 
		    						+ paramMap.get("CHUCHAI_DAY") + "</td>" 
		    						+ "<td style='text-align:center'>" 
			    					+ map.get("YEBAN_CISHU") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("BINGJIA_ZS") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("HUNJIA_DAY") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("CHANJIA_DAY") + "</td>" 
		    						+ "<td style='text-align:center'>"
		    						+ paramMap.get("SANGJIA_DAY") + "</td>"
		    						+ "<td style='text-align:center'>"
			    					+ paramMap.get("GONGSHANGJIA") + "</td>" 
			    					+ "<td style='text-align:center'>"
			    					+ paramMap.get("BURUJIA") + "</td>" 
			    					+ "<td style='text-align:center'>"
			    					+ paramMap.get("PEICHANJIA") + "</td>" 
			    					+ "<td style='text-align:center'>"
			    					+ paramMap.get("LIUCHANJIA") + "</td>" 
		    						+ "</tr>");
			            }
			            Map map1=(Map) arinfoList.get(arinfoList.size()-1);
			            context.append( "<tr>" 
			            		        + "<td style='text-align:center'>"
	    						        + map1.get("LOCAL_NAME") + "</td>" 
			                            + "<td style='text-align:center'>"
			    						+ map1.get("EMPID") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("AR_MONTH") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("ZS_CHIDAO_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("ZS_ZAOTUI_YIXIA") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("KUANGGONG_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PAY_PINGSHI_OT_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PAY_ZM_OT_ZS") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("SHIJIA_ZS") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map1.get("SHIYONG_NIANJIA_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map1.get("SHIYONG_FULI_DAY") + "</td>"
			    						+ "<td style='text-align:center'>" 
			    						+ map1.get("CHUCHAI_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>" 
			    						+ map.get("YEBAN_CISHU") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("BINGJIA_ZS") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("HUNJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("CHANJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("SANGJIA_DAY") + "</td>" 
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("GONGSHANGJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("BURUJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("PEICHANJIA") + "</td>"
			    						+ "<td style='text-align:center'>"
			    						+ map1.get("LIUCHANJIA") + "</td>"
			    						+ "</tr>"
			    						+ "</table>"+
			    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
			    					      "谢谢合作！祝您工作愉快！"+
			    					      "人事部");
			            for(int kk=0;kk<arinfoList.size();kk++){
			            	Map mapp=(Map) arinfoList.get(kk);
			            	if(arMap.get("PERSON_ID").equals(mapp.get("PERSON_ID")) || arMap.get("PERSON_ID")==mapp.get("PERSON_ID")){
				            	address = StringUtil.checkNull(mapp.get("EMAIL")).toString();
							}  
			            }
						}else{
							Map map=(Map) arinfoList.get(0);
							context.append(
				    				"<H1>" + "您好:" + "</H1>" + "您"
				    						+ map.get("AR_MONTH")
				    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
				    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
				    						+ "<tr>"
				    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
				    						+ "</tr>" + "<tr>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
				    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
				    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
				    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
				    						+ "<td colspan='4' style='text-align:center'>追溯加班项目组</td>"
				    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
				    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
				    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"
				    						+ "<td style='text-align:center'>法定加班</td>"
				    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>" 
				    						+ "<td style='text-align:center'>追溯法定加班</td>"
				    					    + "<td style='text-align:center'>事假</td>"
				    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
				    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
				    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" 
				    						+ "<tr>" + "<td style='text-align:center'>"
				    						+ map.get("LOCAL_NAME") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("EMPID") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("AR_MONTH") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("KUANGGONG_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("SHIJIA_ZS") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map.get("CHUCHAI_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("BINGJIA_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("HUNJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("CHANJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
				    					);
							for(int n=1;n<arinfoList.size()-1;n++){
								Map paramMap=(Map) arinfoList.get(n);
								context.append( "<tr>"+"<td style='text-align:center'>"
			    						        + paramMap.get("LOCAL_NAME") + "</td>" 
								                + "<td style='text-align:center'>"
					    						+ paramMap.get("EMPID") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("AR_MONTH") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("ZS_CHIDAO_YIXIA") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("ZS_ZAOTUI_YIXIA") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("KUANGGONG_ZS") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("PAY_PINGSHI_OT_ZS") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("PAY_ZM_OT_ZS") + "</td>"
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("PAY_ZM_OT_ZS_Z") + "</td>"
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("Z_PAY_ZM_OT_ZS") + "</td>"
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("SHIJIA_ZS") + "</td>"
					    						+ "<td style='text-align:center'>" 
					    						+ paramMap.get("SHIYONG_NIANJIA_DAY") + "</td>"
					    						+ "<td style='text-align:center'>" 
					    						+ paramMap.get("SHIYONG_FULI_DAY") + "</td>"
					    						+ "<td style='text-align:center'>" 
					    						+ paramMap.get("CHUCHAI_DAY") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("BINGJIA_ZS") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("HUNJIA_DAY") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("CHANJIA_DAY") + "</td>" 
					    						+ "<td style='text-align:center'>"
					    						+ paramMap.get("SANGJIA_DAY") + "</td>" + "</tr>"
					    						);
							}
							Map map1=(Map) arinfoList.get(arinfoList.size()-1);
							context.append( "<tr>" + "<td style='text-align:center'>"
		    						        + map1.get("LOCAL_NAME") + "</td>" 
							                + "<td style='text-align:center'>"
				    						+ map1.get("EMPID") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("AR_MONTH") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("ZS_CHIDAO_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("ZS_ZAOTUI_YIXIA") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("KUANGGONG_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_ZM_OT_ZS_Z") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("Z_PAY_ZM_OT_ZS") + "</td>"
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("SHIJIA_ZS") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map1.get("SHIYONG_NIANJIA_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map1.get("SHIYONG_FULI_DAY") + "</td>"
				    						+ "<td style='text-align:center'>" 
				    						+ map1.get("CHUCHAI_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("BINGJIA_ZS") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("HUNJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("CHANJIA_DAY") + "</td>" 
				    						+ "<td style='text-align:center'>"
				    						+ map1.get("SANGJIA_DAY") + "</td>" + "</tr>"
				    						+ "</table>"+
				    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
				    					      "谢谢合作！祝您工作愉快！"+
				    					      "人事部");
							for(int kk=0;kk<arinfoList.size();kk++){
				            	Map mapp=(Map) arinfoList.get(kk);
				            	if(arMap.get("PERSON_ID").equals(mapp.get("PERSON_ID")) || arMap.get("PERSON_ID")==mapp.get("PERSON_ID")){
					            	address = StringUtil.checkNull(mapp.get("EMAIL")).toString();
								}  
				            }
						}
			              
					}
					 String title = "月考勤汇总";
			            if(address.equals(""))
			            	continue ;
					 mailManager.sendMail(title,context.toString(),address);
				}
			}
		}
		
		//查找所有的正在用的所有的部门领导（目前只是ch）
		if("TSTO".equals(admin.getCpnyId())){
	     List<LinkedHashMap<String, Object>> orgList = this.arEmailSer.getOrgDeptList(request) ;
			//根据部门查出自己部门所有的人
			if(orgList!=null && orgList.size()>0){
				for(int k=0;k<orgList.size();k++){
						String address = ""; //sendmap.get("RCVR_EMAIL_ADDR").toString();
						Map arMap = orgList.get(k);
						List arinfoList = this.arEmailSer.getArEmailList(request,null,arMap.get("DEPTNO").toString());
						if(arinfoList!=null && arinfoList.size()>0){
							StringBuffer context=new StringBuffer();	
							if(arinfoList.size()==1){
								Map map=(Map) arinfoList.get(0);
									context.append(
						    				"<H1>" + "您好:" + "</H1>" + "您"
						    						+ map.get("AR_MONTH")
						    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
						    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
						    						+ "<tr>"
						    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
						    						+ "</tr>" + "<tr>"
						    						+ "<td width='4%' rowspan='2'>姓名</td>"
						    						+ "<td width='4%' rowspan='2'>社号</td>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
						    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
						    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
						    						+ "<td colspan='4' style='text-align:center'>追溯加班项目组</td>"
						    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
						    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
						    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"
						    						+ "<td style='text-align:center'>法定加班</td>"
						    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>" 
						    						+ "<td style='text-align:center'>追溯法定加班</td>"
						    					    + "<td style='text-align:center'>事假</td>"
						    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
						    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
						    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" 
						    						+ "<tr>" + "<td style='text-align:center'>"
						    						+ map.get("LOCAL_NAME") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("EMPID") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("AR_MONTH") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("KUANGGONG_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("SHIJIA_ZS") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("CHUCHAI_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("BINGJIA_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("HUNJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("CHANJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
						    						+ "</table>"+
						    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
						    					      "谢谢合作！祝您工作愉快！"+
						    					      "人事部");
					            if(arMap.get("PERSON_ID").equals(map.get("PERSON_ID")) || arMap.get("PERSON_ID")==map.get("PERSON_ID")){
					            	address = StringUtil.checkNull(map.get("EMAIL")).toString();
								}    
							}else if(arinfoList.size()==2){
									Map map=(Map) arinfoList.get(0);
									context.append(
						    				"<H1>" + "您好:" + "</H1>" + "您"
						    						+ map.get("AR_MONTH")
						    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
						    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
						    						+ "<tr>"
						    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
						    						+ "</tr>" + "<tr>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
						    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
						    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
						    						+ "<td colspan='4' style='text-align:center'>追溯加班项目组</td>"
						    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
						    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
						    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"						    						+ "<td style='text-align:center'>法定加班</td>"
						    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>" 
						    						+ "<td style='text-align:center'>追溯法定加班</td>"
						    					    + "<td style='text-align:center'>事假</td>"
						    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
						    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
						    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" 
						    						+ "<tr>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("LOCAL_NAME") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("EMPID") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("AR_MONTH") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("KUANGGONG_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("SHIJIA_ZS") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
					    			     		   	+ "<td style='text-align:center'>" 
						    						+ map.get("CHUCHAI_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("BINGJIA_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("HUNJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("CHANJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
						    						);
									Map map1=(Map) arinfoList.get(1);
									context.append(
						    						  "<tr>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("LOCAL_NAME") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("EMPID") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("AR_MONTH") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("ZS_CHIDAO_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("ZS_ZAOTUI_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("KUANGGONG_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
    					    						+ map1.get("PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_ZM_OT_ZS_Z") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("Z_PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("SHIJIA_ZS") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map1.get("SHIYONG_NIANJIA_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map1.get("SHIYONG_FULI_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map1.get("CHUCHAI_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("BINGJIA_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("HUNJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("CHANJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("SANGJIA_DAY") + "</td>" + "</tr>"
						    						+ "</table>"+
						    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
						    					      "谢谢合作！祝您工作愉快！"+
						    					      "人事部");
									if(arMap.get("PERSON_ID").equals(map.get("PERSON_ID")) || arMap.get("PERSON_ID")==map.get("PERSON_ID")){
						            	address = StringUtil.checkNull(map.get("EMAIL")).toString();
									}  
									if(arMap.get("PERSON_ID").equals(map1.get("PERSON_ID")) || arMap.get("PERSON_ID")==map1.get("PERSON_ID")){
						            	address = StringUtil.checkNull(map1.get("EMAIL")).toString();
									}  
					              
							}else if(arinfoList.size() > 2){
									Map map=(Map) arinfoList.get(0);
									context.append(
						    				"<H1>" + "您好:" + "</H1>" + "您"
						    						+ map.get("AR_MONTH")
						    						+ "月"+map.get("START_DATE")+"~"+map.get("END_DATE")+"的考勤汇总已经计算，请查看。"
						    						+ "<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"
						    						+ "<tr>"
						    						+ "<td colspan='22' style='text-align:center'>考勤基本信息</td>"
						    						+ "</tr>" + "<tr>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>姓名</td>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>社号</td>"
						    						+ "<td width='4%' rowspan='2' style='text-align:center'>考勤月</td>"
						    						+ "<td colspan='3' style='text-align:center'>专有项目组</td>"
						    						+ "<td colspan='4' style='text-align:center'>加班项目组</td>"
						    						+ "<td colspan='4' style='text-align:center'>追溯加班项目组</td>"
   	    				    						+ "<td colspan='8' style='text-align:center'>休假项目组</td>" + "</tr>" + "<tr>"
						    						+ "<td style='text-align:center'>迟到</td>" + "<td style='text-align:center'>早退</td>" + "<td style='text-align:center'>旷工</td>"
						    						+ "<td style='text-align:center'>平日加班</td>" + "<td style='text-align:center'>周末加班(付薪)</td>" + "<td style='text-align:center'>周末加班(调休)</td>"
						    						+ "<td style='text-align:center'>法定加班</td>"
						    						+ "<td style='text-align:center'>追溯平日加班</td>" + "<td style='text-align:center'>追溯周末加班(付薪)</td>" 
						    						+ "<td style='text-align:center'>追溯法定加班</td>"
						    					    + "<td style='text-align:center'>事假</td>"
						    						+ "<td style='text-align:center'>法定年假</td>" + "<td style='text-align:center'>福利年假</td>" + "<td style='text-align:center'>出差</td>"
						    						+ "<td style='text-align:center'>病假</td>" + "<td style='text-align:center'>婚假</td>" + "<td style='text-align:center'>产假</td>"
						    						+ "<td style='text-align:center'>丧假</td>" + "</tr>" 
						    						+ "<tr>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("LOCAL_NAME") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("EMPID") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("AR_MONTH") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("ZS_CHIDAO_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("ZS_ZAOTUI_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("KUANGGONG_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_ZM_OT_ZS_Z") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("SHIJIA_ZS") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("SHIYONG_NIANJIA_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("SHIYONG_FULI_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map.get("CHUCHAI_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("BINGJIA_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("HUNJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("CHANJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map.get("SANGJIA_DAY") + "</td>" + "</tr>"
						    					);
									for(int n=0;n<arinfoList.size()-1;n++){
										Map paramMap=(Map) arinfoList.get(n);
										context.append( "<tr>"
												        + "<td style='text-align:center'>"
					    						        + paramMap.get("LOCAL_NAME") + "</td>" 
										                + "<td style='text-align:center'>"
							    						+ paramMap.get("EMPID") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("AR_MONTH") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("ZS_CHIDAO_YIXIA") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("ZS_ZAOTUI_YIXIA") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("KUANGGONG_ZS") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("PAY_PINGSHI_OT_ZS") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("PAY_ZM_OT_ZS") + "</td>"
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("PAY_ZM_OT_ZS_Z") + "</td>"
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("Z_PAY_ZM_OT_ZS") + "</td>"
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("SHIJIA_ZS") + "</td>"
							    						+ "<td style='text-align:center'>" 
							    						+ paramMap.get("SHIYONG_NIANJIA_DAY") + "</td>"
							    						+ "<td style='text-align:center'>" 
							    						+ paramMap.get("SHIYONG_FULI_DAY") + "</td>"
							    						+ "<td style='text-align:center'>" 
							    						+ paramMap.get("CHUCHAI_DAY") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("BINGJIA_ZS") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("HUNJIA_DAY") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("CHANJIA_DAY") + "</td>" 
							    						+ "<td style='text-align:center'>"
							    						+ paramMap.get("SANGJIA_DAY") + "</td>" + "</tr>"
							    						);
									}
									Map map1=(Map) arinfoList.get(arinfoList.size()-1);
									context.append( "<tr>" 
											        + "<td style='text-align:center'>"
					    						    + map1.get("LOCAL_NAME") + "</td>" 
									                + "<td style='text-align:center'>"
						    						+ map1.get("EMPID") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("AR_MONTH") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("ZS_CHIDAO_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("ZS_ZAOTUI_YIXIA") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("KUANGGONG_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_ZM_OT_ZS_Z") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("Z_PAY_PINGSHI_OT_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("Z_PAY_ZM_OT_ZS") + "</td>"
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("Z_PAY_FADING_OT_ZS_ZJ") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("SHIJIA_ZS") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map1.get("SHIYONG_NIANJIA_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map1.get("SHIYONG_FULI_DAY") + "</td>"
						    						+ "<td style='text-align:center'>" 
						    						+ map1.get("CHUCHAI_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("BINGJIA_ZS") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("HUNJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("CHANJIA_DAY") + "</td>" 
						    						+ "<td style='text-align:center'>"
						    						+ map1.get("SANGJIA_DAY") + "</td>" + "</tr>"
						    						+ "</table>"+
						    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
						    					      "谢谢合作！祝您工作愉快！"+
						    					      "人事部");
									for(int kk=0;kk<arinfoList.size();kk++){
						            	Map mapp=(Map) arinfoList.get(kk);
						            	if(arMap.get("PERSON_ID").equals(mapp.get("PERSON_ID")) || arMap.get("PERSON_ID")==mapp.get("PERSON_ID")){
							            	address = StringUtil.checkNull(mapp.get("EMAIL")).toString();
										}  
						            }
					              
							}
							 String title = "部门员工月考勤汇总";
					            if(address.equals(""))
					            	continue ;
							 mailManager.sendMail(title,context.toString(),address);
						}
					}
				}
		}
		return "true";
	}

	/**
	 * 明细计算页面发送邮件(sendarmail1)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/findPersonByItem")
	@ResponseBody
	public List findPersonByItem(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List arSummaryList = this.arEmailSer.getArEmailList(request,null,null);
		return arSummaryList;
	}
	
	
	/**
	 * 明细计算页面发送邮件(sendArmail)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void sendArDetailEmail(Map paramMap) throws Exception {
		List arHistoryinfo = this.arEmailSer.getArDetailListEmail(paramMap,null);
		for(int i=0;i<arHistoryinfo.size();i++){
			StringBuffer context=new StringBuffer();	
			Map map=(Map) arHistoryinfo.get(i);
			if(!"LGEHZ".equals(paramMap.get("CPNY_ID")) && paramMap.get("CPNY_ID")!="LEGHZ"){
            context.append(
            		"<h1>您好:</h1>"+
                            "您"+map.get("AR_DATE_STR")+"日的考勤明细已经算完毕，请参考以下内容。"+
                            "<HTML>"+
  				    		"<body>"+
  				    		"<table width='80%' border='1'>"+
  				    		  "<tr>"+
  				    		    "<td width='10%' style='text-align:center'>姓名</td>"+
  				    		  "<td width='50%' style='text-align:center'>部门</td>"+
  				    		    "<td width='20%' style='text-align:center'>异常日期</td>"+
  				    		    "<td width='20%' style='text-align:center'>异常类型</td>"+
  				    		  "</tr>"+
  				    		  "<tr>"+
  				    		    "<td  style='text-align:center'>"+map.get("LOCAL_NAME")+"</td>"+
  				    		  "<td  style='text-align:center'>"+map.get("DEPTNAME")+"</td>"+
  				    		    "<td  style='text-align:center'>"+map.get("AR_DATE_STR")+"</td>"+
  				    		    "<td  style='text-align:center'>"+map.get("ITEM_NAME")+"</td>"+
  				    		  "</tr>"+
  				    		"</table>"+
  				    		"</body>"+
  				    		"</HTML>"+
    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
    					      "谢谢合作！祝您工作愉快！"+
    					      "人事部");
            String title = "日考勤明细";
            String address = map.get("EMAIL").toString();
            mailManager.sendMail(title,context.toString(),address);
		}
		}
		
		//查找CH所有的考勤员
		List<LinkedHashMap<String, Object>> attendanceKeeperList = this.arEmailSer.getAttKeeperList(paramMap) ;
		//根据部门和人员类型查出所有有权限管理的人
		if(attendanceKeeperList!=null && attendanceKeeperList.size()>0){
			for(int k=0;k<attendanceKeeperList.size();k++){
				String address = ""; //sendmap.get("RCVR_EMAIL_ADDR").toString();
				Map arMap = attendanceKeeperList.get(k);
				List arinfoList = this.arEmailSer.getArDetailListEmail(paramMap,arMap.get("PERSON_ID").toString());
				if(arinfoList!=null && arinfoList.size()>0){
					StringBuffer context=new StringBuffer();	
					for(int m=0;m<arinfoList.size();m++){
						Map map=(Map) arinfoList.get(m);
			            context.append(
			            		"<h1>您好:</h1>"+
			                            "您"+map.get("AR_DATE_STR")+"日的考勤明细已经算完毕，请参考以下内容。"+
			                            "<HTML>"+
			  				    		"<body>"+
			  				    		"<table width='80%' border='1'>"+
			  				    		  "<tr>"+
			  				    		    "<td width='10%' style='text-align:center'>姓名</td>"+
			  				    		  "<td width='50%' style='text-align:center'>部门</td>"+
			  				    		    "<td width='20%' style='text-align:center'>异常日期</td>"+
			  				    		    "<td width='20%' style='text-align:center'>异常类型</td>"+
			  				    		  "</tr>"+
			  				    		  "<tr>"+
			  				    		    "<td  style='text-align:center'>"+map.get("LOCAL_NAME")+"</td>"+
			  				    		  "<td  style='text-align:center'>"+map.get("DEPTNAME")+"</td>"+
			  				    		    "<td  style='text-align:center'>"+map.get("AR_DATE_STR")+"</td>"+
			  				    		    "<td  style='text-align:center'>"+map.get("ITEM_NAME")+"</td>"+
			  				    		  "</tr>"+
			  				    		"</table>"+
			  				    		"</body>"+
			  				    		"</HTML>"+
			    					      "请您认真核查上述汇总信息，如考勤汇总与实际情况不符，请及时在CHRS2.0系统中申请修改。"+
			    					      "谢谢合作！祝您工作愉快！"+
			    					      "人事部");
					}
					 address = arMap.get("EMAIL").toString();
					 String title = "日考勤明细";
					 mailManager.sendMail(title,context.toString(),address);
				}
			}
		}
	}
}
