package com.ait.web.mail.ar;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.ait.web.util.MailManager;
/**
 * 给所有员工发送邮件
 * @author xeuhaifei
 * 
 */
public class SendArDetailMailToDeptManager  implements Runnable  {
	

	private HtmlEmail mailSender;
	private MailManager mailManager;
	private final static String PROFILE = "/system.properties";
	private static final String String = null;
	private String pa_month = null;
	private String tableName = null;
	private String cpny_id = null;
	private String dateStr = null;
	private Map<String, String> map;
	private List list;
	private  String ArMailToManager;
	private  String StartDate;
	private  String EndDate;
	private  String DeptName;	

	
	
	

	
	public String getArMailToManager() {
		return ArMailToManager;
	}

	public void setArMailToManager(String arMailToManager) {
		ArMailToManager = arMailToManager;
	}

	public HtmlEmail getMailSender() {
		return mailSender;
	}

	public void setMailSender(HtmlEmail mailSender) {
		this.mailSender = mailSender;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void setPaMonth(String pa_month) {
		this.pa_month = pa_month;
		dateStr = pa_month.substring(0,4) + "年" + pa_month.substring(4,6) + "月";
	}

	public void setTableName(String cpny_id){
		this.cpny_id = cpny_id;
		this.tableName = "pa_history_" + cpny_id;
	}
	
	public SendArDetailMailToDeptManager(){
		mailSender = new HtmlEmail();
	}

	public SendArDetailMailToDeptManager(String pa_month, String cpny_id){
		mailSender = new HtmlEmail();
		setPaMonth(pa_month);
		setTableName(cpny_id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void run() { 
		StringBuffer Message=new StringBuffer();
		StringBuffer context=new StringBuffer();
        for (int i=0;i<list.size();i++){
            Map map=(Map) list.get(i);
			Map ardetailmap = new HashMap<String, String>();
            ardetailmap.put("person_id", map.get("person_id"));
			ardetailmap.put("SHORT_NAME", map.get("SHORT_NAME"));
			ardetailmap.put("quantity", map.get("quantity"));
			ardetailmap.put("ar_date_str", map.get("ar_date_str"));
			   Message.append("<tr>");
		       Message.append("<td>"+map.get("person_id")+"</td>");
		       Message.append("<td>"+map.get("ar_date_str")+"</td>");
		       Message.append("<td>"+map.get("quantity")+"</td>");	
		       Message.append("</tr>");	
		       }
		context.append("<h1>您好</h1>"+StartDate+"日"+
							"至"+StartDate+"日"+"考勤已经计算完毕，请参考一下内容，如有尚未裁决的信息，请到ep系统中尽快审批，谢谢"
							+
				    		"<body>"+
				    		"<table width='50%' border='1'>"+
				    		  "<tr>"+
				    		    "<td width='10%' style='text-align:center'>姓名</td>"+
				    		    "<td width='30%' style='text-align:center'>异常日期</td>"+
				    		    "<td width='30%' style='text-align:center'>异常类型</td>"+
				    		  "</tr>"+
				    		  Message+
				    		"</table>"+
				    		"</body>");
                Map maps=new HashMap<String, String>();
                maps.put("context", context);
                maps.put("titile", "部门考勤异常提醒");
                maps.put("EmailAdress",this.getArMailToManager());
                Boolean flag=mailManager.sendmail(maps);//2014-08-28
                System.out.println(flag+"***********************");

//				try {
//					this.sendArMailToMa(Message);
//				} catch (AddressException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (EmailException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		}
//	public  boolean sendArMailToMa(StringBuffer message) throws EmailException, AddressException{
//		boolean flag = false;
//	
//				
//					List<InternetAddress> addressList = new ArrayList<InternetAddress>();
//					mailSender.setHostName("smtp.sohu.com");
//					mailSender.setCharset("UTF-8");
//					try {
//						mailSender.setFrom("posco-china@sohu.com");
//					} catch (EmailException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					mailSender.setAuthentication(("posco-china"),("bjait2007"));
//					//addressList.add(new InternetAddress(map.get("RCVR_EMAIL_ADDR")));
//					addressList.add(new InternetAddress(ArMailToManager));
//					mailSender.setTo(addressList);
//					// 标题
//					mailSender.setSubject("测试邮件");
//					// 邮件内容
//					//mailSender.setMsg("您好："+"</br>"+map.get("empid")+"您"+map.get("armonth")+"月份的考勤明细已经算出来了 "+"<br>"+"实际出勤天数："+map.get("ACTUAL_WORK_DAYS")+" 平日加班天数："+map.get("PAY_PINGSHI_OT_ZS")+" 周末加班天数："+map.get("PAY_ZM_OT_ZS"));
//					mailSender.setHtmlMsg("<h1>您好</h1>"+StartDate+"日"+
//							"至"+StartDate+"日"+"考勤已经计算完毕，请参考一下内容，如有尚未裁决的信息，请到ep系统中尽快审批，谢谢"
//							+
//				    		"<body>"+
//				    		"<table width='50%' border='1'>"+
//				    		  "<tr>"+
//				    		    "<td width='10%' style='text-align:center'>姓名</td>"+
//				    		    "<td width='30%' style='text-align:center'>异常日期</td>"+
//				    		    "<td width='30%' style='text-align:center'>异常类型</td>"+
//				    		  "</tr>"+
//				    		  message+
//				    		"</table>"+
//				    		"</body>"
//				    		
//							
//					);
//						mailSender.send();
//					    flag = true;
//
//		return flag;
//	}
	
}
