/**xuehaifei
 * 2014
 */
package com.ait.web.mail.ar;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


/**
 * @author xuehaifei
 *
 * 2014下午12:09:30
 */
public class SendEmailUtil {
	private HtmlEmail mailSender = new HtmlEmail();

	public HtmlEmail getMailSender() {
		return mailSender;
	}

	public void setMailSender(HtmlEmail mailSender) {
		this.mailSender = mailSender;
	}
	
	public Boolean sendmail(Map<String, String> map) throws AddressException, EmailException {
       Boolean flag=false;
        List<InternetAddress> addressList = new ArrayList<InternetAddress>();
		mailSender.setHostName("smtp.sina.com");
		mailSender.setCharset("UTF-8");
		try {
			mailSender.setFrom("tsto_sst_chrs@sina.com");
		} catch (EmailException e) {
			e.printStackTrace();
		}
		mailSender.setAuthentication(("tsto_sst_chrs"),("wei19900114"));
		addressList.add(new InternetAddress(map.get("EmailAdress")));
		mailSender.setTo(addressList);
		mailSender.setSubject(map.get("titile"));
	    mailSender.setMsg(map.get("context"));
		try {
			mailSender.send();	
		} catch (EmailException e) {
			e.printStackTrace();
		}
		flag=true;
		return flag;
	}

	public static void main(String[] args){
		SendEmailUtil util = new SendEmailUtil();
		Map map = new LinkedHashMap();
		map.put("EmailAdress", "xingpeng@ait.net.cn");
		map.put("titile", "titile234");
		map.put("context", "context123");
		try {
			util.sendmail(map);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
