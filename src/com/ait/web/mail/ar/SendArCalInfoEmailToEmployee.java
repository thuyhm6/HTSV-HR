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
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ait.ar.service.ArDetailSer;
import com.ait.web.util.MailManager;
/**
 * 给所有员工发送邮件
 * @author xeuhaifei
 * 
 */
public class SendArCalInfoEmailToEmployee  implements Runnable  {
	

	private HtmlEmail mailSender;
	private MailManager mailManager;
	private final static String PROFILE = "/system.properties";
	private String pa_month = null;
	private String tableName = null;
	private String cpny_id = null;
	private String dateStr = null;
	private Map<String, String> map;
	private List list;
	
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
	
	public SendArCalInfoEmailToEmployee(){
		mailSender = new HtmlEmail();
	}

	public SendArCalInfoEmailToEmployee(String pa_month, String cpny_id){
		mailSender = new HtmlEmail();
		setPaMonth(pa_month);
		setTableName(cpny_id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void run() { 
		for(int i=0;i<list.size();i++){
			StringBuffer context=new StringBuffer();	
			 Map map=(Map) list.get(i);
             System.out.println(map);
             Map sendmap= new HashMap<String, String>();
             System.out.println(map.get("AR_MONTH")+"%%%%%%%%%");
            sendmap.put("ARMONTH", (String) map.get("AR_MONTH"));
            sendmap.put("EMPID", (String) map.get("EMPID"));
            sendmap.put("RCVR_EMAIL_ADDR",(String) map.get("EMAIL"));
            sendmap.put("ACTUAL_WORK_DAYS",(String) map.get("ACTUAL_WORK_DAYS"));
            sendmap.put("PAY_ZM_OT_ZS",(String) map.get("PAY_ZM_OT_ZS"));
            sendmap.put("ZS_CHIDAO_YIXIA",(String) map.get("ZS_CHIDAO_YIXIA"));
            sendmap.put("ZS_ZAOTUI_YIXIA",(String) map.get("ZS_ZAOTUI_YIXIA"));
            sendmap.put("KUANGGONG_ZS",(String) map.get("KUANGGONG_ZS"));
            sendmap.put("PAY_ZM_OT_ZS",(String) map.get("PAY_ZM_OT_ZS"));
            sendmap.put("PAY_PINGSHI_OT_ZS",(String) map.get("PAY_PINGSHI_OT_ZS"));
            sendmap.put("PAY_FADING_OT_ZS_ZJ",(String) map.get("PAY_FADING_OT_ZS_ZJ"));
            sendmap.put("ZHOUMO_OT_TIAOXIU",(String) map.get("ZHOUMO_OT_TIAOXIU"));
            sendmap.put("SHIJIA_ZS",(String) map.get("SHIJIA_ZS"));
            sendmap.put("SHIYONG_NIANJIA_DAY",(String) map.get("SHIYONG_NIANJIA_DAY"));
            sendmap.put("SHIYONG_FULI_DAY",(String) map.get("SHIYONG_FULI_DAY"));
            sendmap.put("CHUCHAI_DAY",(String) map.get("CHUCHAI_DAY"));
            sendmap.put("BINGJIA_ZS",(String) map.get("BINGJIA_ZS"));
            sendmap.put("HUNJIA_DAY",(String) map.get("HUNJIA_DAY"));
            sendmap.put("CHANJIA_DAY",(String) map.get("CHANJIA_DAY"));
            sendmap.put("SANGJIA_DAY",(String) map.get("SANGJIA_DAY"));
            context.append("<H1>"+"您好:"+"</H1>"
    		+"您"+map.get("ARMONTH")+"月份的考勤明细已经计算，请查看。"+
    		"<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"+
    "<tr>"+
     "<td colspan='18' style='text-align:center'>考勤基本信息</td>"+
    "</tr>"+
    "<tr>"+
      "<td width='4%' rowspan='2'>姓名</td>"+
      "<td width='4%' rowspan='2'>考勤月</td>"+
      "<td colspan='3'>专有项目组</td>"+
      "<td colspan='4'>加班项目组</td>"+
      "<td colspan='9'>休假项目组</td>"+
      "</tr>"+
      "<tr>"+
      "<td >迟到</td>"+
      "<td >早退</td>"+
      "<td >旷工</td>"+
      "<td >平日</td>"+
      "<td >周加</td>"+
      "<td >法定</td>"+
      "<td >调休</td>"+
      "<td>事假</td>"+
      "<td>已用调休</td>"+
      "<td>法定年假</td>"+
      "<td>福利年假</td>"+
      "<td>出差</td>"+
      "<td>病假</td>"+
      "<td>婚假</td>"+
      "<td>产假</td>"+
      "<td>丧假</td>"+
      "</tr>"+
      "<tr>"+
      "<td>"+sendmap.get("EMPID")+"</td>"+
      "<td>"+sendmap.get("ARMONTH")+"</td>"+
      "<td>"+sendmap.get("ZS_CHIDAO_YIXIA")+"</td>"+
      "<td>"+sendmap.get("ZS_ZAOTUI_YIXIA")+"</td>"+
      "<td>"+sendmap.get("KUANGGONG_ZS")+"</td>"+
      "<td>"+sendmap.get("PAY_PINGSHI_OT_ZS")+"</td>"+
      "<td>"+sendmap.get("PAY_ZM_OT_ZS")+"</td>"+
      "<td>"+sendmap.get("PAY_FADING_OT_ZS_ZJ")+"</td>"+
      "<td>"+sendmap.get("ZHOUMO_OT_TIAOXIU")+"</td>"+
      "<td>"+sendmap.get("SHIJIA_ZS")+"</td>"+
      "<td>0</td>"+
      "<td>"+sendmap.get("SHIYONG_NIANJIA_DAY")+"</td>"+
      "<td>"+sendmap.get("SHIYONG_FULI_DAY")+"</td>"+
      "<td>"+sendmap.get("CHUCHAI_DAY")+"</td>"+
      "<td>"+sendmap.get("BINGJIA_ZS")+"</td>"+
      "<td>"+sendmap.get("HUNJIA_DAY")+"</td>"+
      "<td>"+sendmap.get("CHANJIA_DAY")+"</td>"+
      "<td>"+sendmap.get("SANGJIA_DAY")+"</td>"+
    "</tr>"+
    "</table>");
            Map maps=new HashMap<String, String>();
            maps.put("context", context);
            maps.put("titile", "考勤明细");
            maps.put("EmailAdress",sendmap.get("RCVR_EMAIL_ADDR"));
            mailManager.sendmail(maps);//2014-08-28
            
//            try {
//				this.sendArMail(sendmap);
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
//					addressList.add(new InternetAddress("haifeiforwork@sina.com"));
//					mailSender.setTo(addressList);
//					// 标题
//					mailSender.setSubject("测试邮件");
//					// 邮件内容
//					//mailSender.setMsg("您好："+"</br>"+map.get("empid")+"您"+map.get("armonth")+"月份的考勤明细已经算出来了 "+"<br>"+"实际出勤天数："+map.get("ACTUAL_WORK_DAYS")+" 平日加班天数："+map.get("PAY_PINGSHI_OT_ZS")+" 周末加班天数："+map.get("PAY_ZM_OT_ZS"));
//				    mailSender.setMsg("<H1>"+"您好:"+"</H1>"
//				    		+"您"+map.get("ARMONTH")+"月份的考勤明细已经计算，请查看。"+
//				    		"<table width='100%' border='1' bordercolor='#000000' cellpadding='0' cellspacing='0'>"+
//				    "<tr>"+
//				     "<td colspan='18' style='text-align:center'>考勤基本信息</td>"+
//				    "</tr>"+
//				    "<tr>"+
//				      "<td width='4%' rowspan='2'>姓名</td>"+
//				      "<td width='4%' rowspan='2'>考勤月</td>"+
//				      "<td colspan='3'>专有项目组</td>"+
//				      "<td colspan='4'>加班项目组</td>"+
//				      "<td colspan='9'>休假项目组</td>"+
//				      "</tr>"+
//				      "<tr>"+
//				      "<td >迟到</td>"+
//				      "<td >早退</td>"+
//				      "<td >旷工</td>"+
//				      "<td >平日</td>"+
//				      "<td >周加</td>"+
//				      "<td >法定</td>"+
//				      "<td >调休</td>"+
//				      "<td>事假</td>"+
//				      "<td>已用调休</td>"+
//				      "<td>法定年假</td>"+
//				      "<td>福利年假</td>"+
//				      "<td>出差</td>"+
//				      "<td>病假</td>"+
//				      "<td>婚假</td>"+
//				      "<td>产假</td>"+
//				      "<td>丧假</td>"+
//				      "</tr>"+
//				      "<tr>"+
//				      "<td>"+map.get("EMPID")+"</td>"+
//				      "<td>"+map.get("ARMONTH")+"</td>"+
//				      "<td>"+map.get("ZS_CHIDAO_YIXIA")+"</td>"+
//				      "<td>"+map.get("ZS_ZAOTUI_YIXIA")+"</td>"+
//				      "<td>"+map.get("KUANGGONG_ZS")+"</td>"+
//				      "<td>"+map.get("PAY_PINGSHI_OT_ZS")+"</td>"+
//				      "<td>"+map.get("PAY_ZM_OT_ZS")+"</td>"+
//				      "<td>"+map.get("PAY_FADING_OT_ZS_ZJ")+"</td>"+
//				      "<td>"+map.get("ZHOUMO_OT_TIAOXIU")+"</td>"+
//				      "<td>"+map.get("SHIJIA_ZS")+"</td>"+
//				      "<td>0</td>"+
//				      "<td>"+map.get("SHIYONG_NIANJIA_DAY")+"</td>"+
//				      "<td>"+map.get("SHIYONG_FULI_DAY")+"</td>"+
//				      "<td>"+map.get("CHUCHAI_DAY")+"</td>"+
//				      "<td>"+map.get("BINGJIA_ZS")+"</td>"+
//				      "<td>"+map.get("HUNJIA_DAY")+"</td>"+
//				      "<td>"+map.get("CHANJIA_DAY")+"</td>"+
//				      "<td>"+map.get("SANGJIA_DAY")+"</td>"+
//				    "</tr>"+
//				    "</table>");
//					mailSender.send();
//				    flag = true;
//		return flag;
//	}

	
	
}
