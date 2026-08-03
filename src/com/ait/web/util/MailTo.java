package com.ait.web.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 暂时有问题，肖
 * 
 * @author Administrator 414704270@
 * 
 */
public class MailTo {
	private static String host = "smtp.163.com";// 邮箱服务器地址 例如smtp.163.com
	private static String mailAddr = "YOUR_MAIL_ADDRESS";// 发送邮箱地址
	private static String mailPassWord = "YOUR_MAIL_PASSWORD";// 发送邮箱密码

	/*
	 * public static void main(String[] arg){ sendMail(); }
	 */
	public int sendMail(Map<String, Object> map) {
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		String email = (String) map.get("email"); // 收件人地址
		String title = (String) map.get("title"); // 收件人地址
		String mains = (String) map.get("mains"); // 邮件主题

		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(mailAddr));
			// 加载收件人地址，这里为群发
			String addressStr[] = { email };
			int len = addressStr.length;
			InternetAddress address[] = new InternetAddress[len];
			for (int i = 0; i < len; i++) {
				address[i] = new InternetAddress(addressStr[i]);
			}
			// 设置为群发
			message.addRecipients(Message.RecipientType.TO, address);
			String mailType = "text/plain"; // 邮件格式 text/html为网页格式
			// 设置邮件的文本内容
			message.setSentDate(new Date()); // 发送时间
			message.setSubject(title); // 邮件标题
			message.isMimeType(mailType); // 设置邮件发送格式
			MimeMultipart multipart = new MimeMultipart("mixed");
			// 邮件内容
			BodyPart messageBodyPart11 = new MimeBodyPart();
			messageBodyPart11.setContent("<html><head> " + title
					+ "</head><body>" + mains + "</body></html>",
					"text/html;charset=gbk");
			multipart.addBodyPart(messageBodyPart11);

			/*// 添加附件
			// /**
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			BodyPart messageBodyPart = new MimeBodyPart();
			// 设置邮件的格式
			messageBodyPart.isMimeType(mailType);
			// 文件地址
			DataSource source = new FileDataSource("e:\\1.jpg");
			// 添加附件的内容
			messageBodyPart.setDataHandler(new DataHandler(source));
			// 添加附件的标题(收到后附件显示的名字,必需有后缀)
			String affixName = "附件1.jpg";
			// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			messageBodyPart.setFileName("=?GBK?B?"
					+ enc.encode(affixName.getBytes()) + "?=");
			multipart.addBodyPart(messageBodyPart);*/
			// */
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱 163服务器地址,发送者邮箱地址,邮箱密码
			transport.connect(host, mailAddr, mailPassWord);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
}
