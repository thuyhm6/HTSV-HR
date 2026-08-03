package com.ait.web.util;

import hanwha.neo.branch.ss.common.vo.WsAttachFile;
import hanwha.neo.branch.ss.mail.service.MailServiceProxy;
import hanwha.neo.branch.ss.mail.service.WsMailInfo;
import hanwha.neo.branch.ss.mail.service.WsRecipient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.mail.internet.InternetAddress;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.EmpInfoDao;

/**
 * 邮件发送
 * @author weizhengchen
 *
 */
@Service
public class MailManager {
	
	private static final Logger logger = Logger.getLogger(MailManager.class);
	
	private HtmlEmail mailSender;
	
	private static String MAIL_PROPERTIES_FILE = "/mail.properties";
	
	private String PASS = "通过";
	
	private String REJECT = "否决";
	
	/**
	 * 服务器ip:端口号
	 */
    @Value("${serverIp}")
	private String SERVER_IP;
	
	/**
	 * 是否验证
	 * 	1：发送
	 *  0：不发送
	 */
    @Value("${mail.send.flag}")
	private String MAIL_SEND_FLAG;
	
	/**
	 * 是否验证
	 */
    @Value("${mail.smtp.auth}")
	private String MAIL_SMTP_AUTH;
	
	/**
	 * 传输协议
	 */
    @Value("${mail.transport.protocol}")
	private String MAIL_TRAINSPORT_PROTOCOL;
	
	/**
	 * 邮件服务器地址
	 */
    @Value("${smtp.server}")
	private String SMTP_SERVER;
    
    /**
     * 邮件服务器验证用户名
     */
    @Value("${mail.username}")
    private String MAIL_USERNAME;
    
    /**
     * 邮件服务器验证密码
     */
    @Value("${mail.password}")
    private String MAIL_PASSWORD;
    
    /**
     * 发件人地址
     */
    @Value("${mail.from}")
    private String MAIL_FROM;

    /**
     * 收件人地址
     */
    @Value("${mail.to}")
    private String MAIL_TO;

    /**
     * 发件人地址
     */
    @Value("${mail.login.host}")
    private String MAIL_LOGIN_HOST;

    /**
     * web service url
     */
    @Value("${webservice.url}")
    private String WEBSERVICE_URL;
    
    @Autowired
    private EmpInfoDao empInfoDao;

	PropertiesConfiguration config = null;
	
	public MailManager(){
        try {
        	config = new PropertiesConfiguration(MailManager.class.getResource(MAIL_PROPERTIES_FILE));
        	mailSender = new HtmlEmail();
        } catch (Exception ex) {
        	logger.error("加载mail.properties配置文件出错！！",ex);
        }
    }
    
	/**
	 * 发送邮件，true:成功;false:失败
	 * @param title		标题
	 * @param content	内容
	 * @param address	收件人地址
	 * @return result   true(成功)/false(失败)
	 * @throws Exception 
	 */
	public boolean sendMail(String title,String content,String address) {
		boolean result = false;
		try{
			//替换单点登录地址
			content = content.replace("{MAIL_LOGIN_HOST}",MAIL_LOGIN_HOST);
			//mail.properties里面配置
			if("1".equals(MAIL_SEND_FLAG)){
				this.sendMailWebService(title, content, address);
			}else if("2".equals(MAIL_SEND_FLAG)){
				this.sendMailBak(title, content, address);
			}
			result = true;
		} catch(Exception e){
			logger.error("邮件发送异常,emailAddress:" + address,e);
			result = false;
		}
		return result;
	}
	
	/**
	 * 发送邮件，true:成功;false:失败(通过webservice 发送)
	 * @param title		标题
	 * @param content	内容
	 * @param address	收件人地址
	 * @return result   true(成功)/false(失败)
	 * @throws Exception 
	 */
	public boolean sendMailWebService(String title,String content,String address) {
		boolean result = false;
		try{
			// Proxy对象生成（end Point设置）
			MailServiceProxy mailServiceProxy = new MailServiceProxy();
			mailServiceProxy.setEndpoint(WEBSERVICE_URL);
			
			/* 1. 发送测试邮件*/
			// 默认的邮件信息处理
			WsMailInfo mailInfo = new WsMailInfo();
			mailInfo.setSubject(title);								// 邮件标题
			mailInfo.setSenderEmail(MAIL_FROM);				// 发信人的邮件地址
			mailInfo.setHtmlContent(true);									// 邮件文本类型（true：HTML，false：Text）
			mailInfo.setMhtContent(false);									// 本文MHT是否使用（true：MHT）
			mailInfo.setImportant(false);									// 重要的邮件（true：是否重要，false：一般）
			
			// 邮件收件人处理
			WsRecipient[] receivers = new WsRecipient[1];
			receivers[0] = new WsRecipient();
			receivers[0].setSeqID(1);										// 序号。
			receivers[0].setRecvType("TO");									// 收件人类型（TO：接收，CC：参照，BCC :）
			receivers[0].setRecvEmail(address);		// 收件人的邮件地址
//			receivers[0].setRecvEmail(MAIL_TO);
			receivers[0].setDept(false);									// 部门（单位：收件人是否true，false：用户）

			// 附件
			mailInfo.setAttachCount(0);										// 附件数
			WsAttachFile[] attachFiles = new WsAttachFile[0];
			
			String resultMsg = mailServiceProxy.sendMISMail(content, mailInfo, receivers, attachFiles);
			
			result = true;
		} catch(Exception e){
			e.printStackTrace();
			logger.error("邮件发送异常,emailAddress:" + address,e);
			result = false;
		}
		return result;
	}
	

	/**
	 * 发送邮件，true:成功;false:失败
	 * @param title		标题
	 * @param content	内容
	 * @param address	收件人地址
	 * @return result   true(成功)/false(失败)
	 * @throws Exception 
	 */
	public boolean sendMailBak(String title,String content,String address) {
		boolean result = false;
		try{
			//替换单点登录地址
			content = content.replace("{MAIL_LOGIN_HOST}",MAIL_LOGIN_HOST);
			//mail.properties里面配置
			if("2".equals(MAIL_SEND_FLAG)){
				//初始化参数配置
		        List<InternetAddress> addressList = new ArrayList<InternetAddress>();
				mailSender.setHostName(SMTP_SERVER);
				mailSender.setCharset("UTF-8");
				try {
					mailSender.setFrom(MAIL_FROM);
				} catch (EmailException e) {
					e.printStackTrace();
				}
				mailSender.setAuthentication((MAIL_USERNAME),(MAIL_PASSWORD));
				addressList.add(new InternetAddress(MAIL_TO));
				mailSender.setTo(addressList);
				mailSender.setSubject(title);
			    mailSender.setHtmlMsg(content);
				mailSender.send();	
			}
			result = true;
		} catch(Exception e){
			e.printStackTrace();
			logger.error("邮件发送异常,emailAddress:" + address,e);
			result = false;
		}
		return result;
	}
	
	/**
	 *  根据申请类型和personId给裁决者发送审批邮件
	 * @param applyTypeNo
	 * @param personId
	 * @return
	 */
	public boolean sendAffirmMail(String applyTypeNo, String personId){
		return this.send(applyTypeNo, personId, 1);
	}
	
	/**
	 *  根据申请类型和personId给裁决者发送check邮件
	 * @param applyTypeNo
	 * @param personId
	 * @return
	 */
	public boolean sendCheckMail(String applyTypeNo, String personId){
		return this.send(applyTypeNo, personId, 2);
	}
	
	/**
	 *  根据申请类型和personId给申请者发送裁决结果邮件
	 * @param applyTypeNo
	 * @param personId
	 * @return
	 */
	public boolean sendResultMail(String applyTypeNo, String personId, int affirmFlag){
		String content = null;
		String title = null;
		String address = getEmailByPersonId(personId);
		title = config.getString("TITLE_" + applyTypeNo);
		content = config.getString("RESULT_CONTENT_" + applyTypeNo);
		if(affirmFlag == 1){
			content = content.replace("{resutl}", PASS);
		}else{
			content = content.replace("{resutl}", REJECT);
		}
		if(address == null || "".equals(address)){
			return false;
		}else{
			String empid = StringUtil.checkNull(getEmpidByPersonId(personId));
			try {
				content = content.replace("{serverIp}", SERVER_IP).replace("{emp_number}", empid).replace("{emp_encoding}", GetPassword.getEncodePasswd(empid));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return this.sendMail(title, content, address);
		}
	}
	
	/**
	 * 根据封装的map信息发送邮件
	 * @param map
	 * @return
	 */
	public boolean sendmail(Map<String, String> map){
		return this.sendMail(map.get("EMAIL_TITLE"), map.get("EMAIL_CONTENT"), map.get("EMAIL"));
	}
	
	/**
	 * 根据person_id 获取email信息
	 * @param personId
	 * @return
	 */
	private String getEmailByPersonId(String personId){
		String email = null;
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("PERSON_ID", personId);
		List mailList = this.empInfoDao.getEmailByPersonId(paramMap);
		if(mailList != null && mailList.size() > 0){
			email = ((LinkedHashMap) mailList.get(0)).get("EMAIL") == null ? null :((LinkedHashMap) mailList.get(0)).get("EMAIL").toString();
		}
		return email;
	}
	/**
	 * 根据person_id 获取empid信息
	 * @param personId
	 * @return
	 */
	private String getEmpidByPersonId(String personId){
		String empid = null;
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("PERSON_ID", personId);
		List mailList = this.empInfoDao.getEmailByPersonId(paramMap);
		if(mailList != null && mailList.size() > 0){
			empid = ((LinkedHashMap) mailList.get(0)).get("EMPID") == null ? null :((LinkedHashMap) mailList.get(0)).get("EMPID").toString();
		}
		return empid;
	}
	
	/**
	 * 发送邮件
	 * @param String
	 * @return
	 */
	private boolean send(String applyTypeNo, String personId, int flag){
		String title = null;
		String content = null;
		String address = this.getEmailByPersonId(personId);
		if(flag == 1){
			title = config.getString("TITLE_A_" + applyTypeNo);
			content = config.getString("AFFIRM_CONTENT_" + applyTypeNo);
		}else{
			title = config.getString("TITLE_C_" + applyTypeNo);
			content = config.getString("CHECK_CONTENT_" + applyTypeNo);
		}
		if(address == null || "".equals(address)){
			return false;
		}else{
			String empid = StringUtil.checkNull(getEmpidByPersonId(personId));
			try {
				content = content.replace("{serverIp}", SERVER_IP).replace("{emp_number}", empid).replace("{emp_encoding}", GetPassword.getEncodePasswd(empid));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return this.sendMail(title, content, address);
		}
	}
	
	/**
	 *  根据申请类型和personId给申请者发送裁决结果邮件
	 * @param applyTypeNo
	 * @param personId
	 * @return
	 */
	public boolean sendResultMailWithReqDetail(
			String applyTypeNo
			, String personId
			, int affirmFlag
			, LinkedHashMap reqMap){
		String content = null;
		String title = null;
		String address = getEmailByPersonId(personId);
		title = config.getString("TITLE_" + applyTypeNo);
		content = config.getString("RESULT_CONTENT_" + applyTypeNo);
		if(affirmFlag == 1){
			content = content.replace("{resutl}", PASS);
			title   = title.replace("{resutl}", PASS);
		}else{
			content = content.replace("{resutl}", REJECT);
			title   = title.replace("{resutl}", REJECT);
		}
		content = content.replace("{reqMst}", reqMap.get("reqMst").toString());
		content = content.replace("{reqDtl}", reqMap.get("reqDtl").toString());
		if(address == null || "".equals(address)){
			return false;
		}else{
			String empid = StringUtil.checkNull(getEmpidByPersonId(personId));
			try {
				content = content.replace("{serverIp}", SERVER_IP).replace("{emp_number}", empid).replace("{emp_encoding}", GetPassword.getEncodePasswd(empid));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return this.sendMail(title, content, address);
		}
	}
	
	/**
	 * 组装模板
	 * @param type
	 * @param paramMap
	 * @return
	 */
	public static String composeTemplate(String type,Map paramMap){
		String template = MailManager.readTemplate(type);
		Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(template);
        while(matcher.find()){
        	template = template.replace(matcher.group(), StringUtil.checkNull((paramMap.get(matcher.group(1)))));
        }
		return template;
	}

	/**
	 * 组装模板
	 * @param type
	 * @param paramMap
	 * @return
	 */
	public static String composeTemplateByParam(String emailContent,Map paramMap){
		String template = emailContent;
		Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(template);
        while(matcher.find()){
        	template = template.replace(matcher.group(), StringUtil.checkNull((paramMap.get(matcher.group(1)))));
        }
		return template;
	}
	
	/**
     * 读取模板，以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
	public static  String readTemplate(String type) {
		StringBuffer template = new StringBuffer();
		String templateName = "";
		String file_real_path= MailManager.class.getResource("").getPath().split("WEB-INF")[0]; 
		
		if("OT".equals(type)){
			templateName = file_real_path + "resources/template/mail/approvalOt.htm";
		}else if("LEAVE".equals(type)){
			templateName = file_real_path + "resources/template/mail/approvalLeave.htm";
		}else if("VAC".equals(type)){
			templateName = file_real_path + "resources/template/mail/approvalVac.htm";
		}else{
			templateName = file_real_path + "resources/template/mail/" + type + ".htm";
		}
		if(!"".equals(templateName)){
	        File file = new File(templateName);
	        Reader reader = null;
	        try {
	            // 一次读多个字符
	            char[] tempchars = new char[1024];
	            int charread = 0;
	            reader = new InputStreamReader(new FileInputStream(templateName),"UTF-8");
	            // 读入多个字符到字符数组中，charread为一次读取字符数
	            while ((charread = reader.read(tempchars)) != -1) {
	                // 同样屏蔽掉\r不显示
	                if ((charread == tempchars.length)
	                        && (tempchars[tempchars.length - 1] != '\r')) {
	                	template.append(tempchars);
	                } else {
	                    for (int i = 0; i < charread; i++) {
	                        if (tempchars[i] == '\r') {
	                            continue;
	                        } else {
	    	                	template.append(tempchars[i]);
	                        }
	                    }
	                }
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		}
		return template.toString();
    }
	
	public static void main(String[] args){
		//MailManager.composeTemplate("OT", new HashMap());
		String file_real_path= MailManager.class.getResource("").getPath(); 
		System.out.print(file_real_path.split("WEB-INF")[0]);
	}
}
