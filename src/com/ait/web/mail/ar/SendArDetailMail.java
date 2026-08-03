package com.ait.web.mail.ar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.ait.ar.service.ArDetailSer;
import com.ait.web.util.MailManager;
/**
 * 给所有员工发送邮件
 * @author xeuhaifei
 * 
 */
public class SendArDetailMail  implements Runnable  {
	
	private HtmlEmail mailSender;
	@Autowired
    MailManager mailManager;
	private final static String PROFILE = "/system.properties";
	private String pa_month = null;
	private String tableName = null;
	private String cpny_id = null;
	private String dateStr = null;
	private Map<String, String> map;
	private List list;
	private ArDetailSer arDetailSer;
	
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
	
	public SendArDetailMail(){
		mailSender = new HtmlEmail();
	}

	public SendArDetailMail(String pa_month, String cpny_id){
		mailSender = new HtmlEmail();
		setPaMonth(pa_month);
		setTableName(cpny_id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void run() { 
		//ArDetailSer arDetailSer=new ArDetailSer();

		for (int i = 0; i < list.size(); i++) {
			StringBuffer context=new StringBuffer();	
			Map map = (Map) list.get(i);
			Map ardetailmap = new HashMap<String, String>();
			System.out.println(map.get("PERSON_ID")+"**********");
			ardetailmap.put("person_id", map.get("PERSON_ID"));
			ardetailmap.put("short_name", map.get("SHORT_NAME"));
			ardetailmap.put("quantity", map.get("QUANTITY"));
			ardetailmap.put("RCVR_EMAIL_ADDR", map.get("EMAIL"));
			ardetailmap.put("ar_date_str", map.get("AR_DATE_STR"));
			context.append("<h1>您好:</h1>"+
                          "您"+ardetailmap.get("ar_date_str")+"日的考勤明细已经算完毕，请参考以下内容。"+
                          "<HTML>"+
				    		"<body>"+
				    		"<table width='50%' border='1'>"+
				    		  "<tr>"+
				    		    "<td width='10%' style='text-align:center'>姓名</td>"+
				    		    "<td width='30%' style='text-align:center'>异常日期</td>"+
				    		    "<td width='30%' style='text-align:center'>异常类型</td>"+
				    		  "</tr>"+
				    		  "<tr>"+
				    		    "<td  style='text-align:center'>"+ardetailmap.get("person_id")+"</td>"+
				    		    "<td  style='text-align:center'>"+ardetailmap.get("ar_date_str")+"</td>"+
				    		    "<td  style='text-align:center'>"+ardetailmap.get("short_name")+"</td>"+
				    		  "</tr>"+
				    		"</table>"+
				    		"</body>"+
				    		"</HTML>"
                          );
            Map maps=new HashMap<String, String>();
            maps.put("context", context);
            maps.put("titile", "考勤明细");
            maps.put("EmailAdress",ardetailmap.get("RCVR_EMAIL_ADDR"));
            try {           
            	MailManager m=new MailManager();
            	boolean flag=mailManager.sendmail(maps);//2014-7-28
                } catch (Exception e) {
				// TODO: handle exception
			}
//			try {
//				//this.sendArMail(ardetailmap);
//			} catch (AddressException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (EmailException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

		}


	
	
//	public  boolean sendArMail(Map<String, String> map) throws EmailException, AddressException{
//		boolean flag = false;
//
//		List<InternetAddress> addressList = new ArrayList<InternetAddress>();
//		mailSender.setHostName("smtp.sohu.com");
//		mailSender.setCharset("UTF-8");
//		try {
//			mailSender.setFrom("posco-china@sohu.com");
//		} catch (EmailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		mailSender.setAuthentication(("posco-china"),("bjait2007"));
//		//addressList.add(new InternetAddress(map.get("RCVR_EMAIL_ADDR")));
//		addressList.add(new InternetAddress("haifeiforwork@sina.com"));
//		//mailSender.setTo(addressList);
//		mailSender.setTo(addressList);
//		// 标题
//		mailSender.setSubject("考勤异常提醒");
//		// 邮件内容
//		mailSender.setMsg("<h1>您好:</h1>"+
//                          "您"+map.get("ar_date_str")+"日的考勤明细已经算完毕，请参考以下内容。"+
//                          "<HTML>"+
//				    		"<body>"+
//				    		"<table width='50%' border='1'>"+
//				    		  "<tr>"+
//				    		    "<td width='10%' style='text-align:center'>姓名</td>"+
//				    		    "<td width='30%' style='text-align:center'>异常日期</td>"+
//				    		    "<td width='30%' style='text-align:center'>异常类型</td>"+
//				    		  "</tr>"+
//				    		  "<tr>"+
//				    		    "<td  style='text-align:center'>"+map.get("person_id")+"</td>"+
//				    		    "<td  style='text-align:center'>"+map.get("ar_date_str")+"</td>"+
//				    		    "<td  style='text-align:center'>"+map.get("short_name")+"</td>"+
//				    		  "</tr>"+
//				    		"</table>"+
//				    		"</body>"+
//				    		"</HTML>"
//                          );
//		mailSender.send();
//		flag = true;
//		return flag;
//	}
	
	
	
}
