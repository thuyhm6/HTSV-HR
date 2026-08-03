package com.ait.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class SendMail {  
        
    private  String smtp="";        //邮件服务器主机名  
    private  String protocol="";        //邮件传输协议  
    private  String username="";        //登录用户名  
    private  String password="";        //登录密码  
    private  String from="";        //发件人地址  
    private  String to="";          //收件人地址  
    private  String subject="";     //邮件主题  
    private  String body="";        //邮件内容  
      
    //一个有规则的map，用作嵌入图片  
    Map<String,String> map;  
    //存放附件  
    List<String> list;  
      
      
    public SendMail(Map<String,String> map,List<String> filelist,Map<String,String> image){  
        this.smtp=map.get("smtp");  
        this.protocol=map.get("protocol");  
        this.username=map.get("username");  
        this.password=map.get("password");  
        this.from=map.get("from");  
        this.to=map.get("to");  
        this.subject=map.get("subject");  
        this.body=map.get("body");  
 
        this.list=filelist;  
        this.map=image;  
    }  
 
    public void send()throws Exception{  
        Properties pros=new Properties();  
        pros.setProperty("mail.transport.protocol", this.protocol);  
        pros.setProperty("mail.host", this.smtp);  
        pros.put("mail.smtp.auth", "true");  
        MySendMailAuthenticator ma=new MySendMailAuthenticator(this.username,this.password);  
        Session session=Session.getInstance(pros,ma);  
        session.setDebug(false);  
          
        MimeMessage msg=createMessage(session);  
          
        Transport ts=session.getTransport();  
        ts.connect();  
        ts.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));  
        ts.close();  
        System.out.println("发送成功");
    }  
      
    public MimeMessage createMessage(Session session)throws Exception{  
          
        MimeMessage message=new MimeMessage(session);  
        message.setFrom(new InternetAddress(this.from));  
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  
        message.setSubject(this.subject);  
          
        MimeMultipart allMultipart=new MimeMultipart();  
          
        //创建代表邮件正文和附件的各个MimeBodyPart对象  
        MimeBodyPart contentpart=createContent(this.body);  
        allMultipart.addBodyPart(contentpart);  
          
          
        //创建用于组合邮件正文和附件的MimeMultipart对象  
        for(int i=0;i<list.size();i++){  
            allMultipart.addBodyPart(createAttachment(list.get(i)));  
        }  
          
        //设置整个邮件内容为最终组合出的MimeMultipart对象  
        message.setContent(allMultipart);  
        message.saveChanges();  
        return message;  
    }  
      
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public MimeBodyPart createContent(String body)throws Exception{  
        //创建代表组合Mime消息的MimeMultipart对象，将该MimeMultipart对象保存到MimeBodyPart对象  
        MimeBodyPart contentPart=new MimeBodyPart();  
        MimeMultipart contentMultipart=new MimeMultipart("related");  
          
        //创建用于保存HTML正文的MimeBodyPart对象，并将它保存到MimeMultipart中  
        MimeBodyPart htmlbodypart=new MimeBodyPart();  
        htmlbodypart.setContent(this.body,"text/html;charset=UTF-8");  
        contentMultipart.addBodyPart(htmlbodypart);  
          
        if(map!=null && map.size()>0) {  
             Set<Entry<String, String>> set=map.entrySet();  
             for (Iterator iterator = set.iterator(); iterator.hasNext();) {  
                Entry<String, String> entry = (Entry<String, String>) iterator.next();  
                  
                //创建用于保存图片的MimeBodyPart对象，并将它保存到MimeMultipart中  
                MimeBodyPart gifBodyPart=new MimeBodyPart();  
                FileDataSource fds=new FileDataSource(entry.getValue());//图片所在的目录的绝对路径  
                  
                gifBodyPart.setDataHandler(new DataHandler(fds));  
                gifBodyPart.setContentID(entry.getKey());   //cid的值  
                contentMultipart.addBodyPart(gifBodyPart);  
            }  
        }  
          
        //将MimeMultipart对象保存到MimeBodyPart对象  
        contentPart.setContent(contentMultipart);  
        return contentPart;  
    }  
      
    public MimeBodyPart createAttachment(String filename)throws Exception {  
        //创建保存附件的MimeBodyPart对象，并加入附件内容和相应的信息  
        MimeBodyPart attachPart=new MimeBodyPart();  
        FileDataSource fsd=new FileDataSource(filename);  
        attachPart.setDataHandler(new DataHandler(fsd));  
        attachPart.setFileName(fsd.getName());  
        return attachPart;  
    }
    
  //向邮件服务器提交认证信息  
    class MySendMailAuthenticator extends Authenticator{  
        String username="";  
        String password="";  
        public MySendMailAuthenticator(String user,String pass){  
            this.username=user;  
            this.password=pass;  
        }  
        protected PasswordAuthentication getPasswordAuthentication() {  
            return new PasswordAuthentication(username,password);  
        }  
    }
    
    public static void main(String[] args)throws Exception {  
        String content="<table border='1' align='center' cellpadding='0' cellspacing='0'><tr><td>"+"<a href='http://www.3swin.com'>行销宝激活</a><br/><a href='http://www.3swin.com/'><img src='cid:a00000001'></a><br/><br/><img src='cid:a00000002'></td></tr></table>";  
        Map<String,String> map=new HashMap<String,String>();  
        //邮件服务器主机名(smtp服务器地址)  
        //如：qq的smtp服务器地址：smtp.qq.com  
        map.put("smtp", "smtp.qq.com");  
        //邮件传输协议：如smtp  
        map.put("protocol", "smtp");  
        //登录邮箱的用户名  
        map.put("username", "765923345@qq.com");  
        //登录邮箱的密码  
        map.put("password", "..");  
        //发送人的帐号  
        map.put("from", "765923345@qq.com");  
        //接收人的帐号，多个以","号隔开  
        map.put("to", "765923345@qq.com,765923345@qq.com");  
        //邮件主题  
        map.put("subject", "这个邮件有点牛逼");  
        //邮件内容  
        map.put("body", content);  
          
        //内嵌了多少张图片，如果没有，则new一个不带值的Map  
        Map<String,String> image=new HashMap<String,String>();  
        image.put("a00000001", "E:/1.jpg");  
        image.put("a00000002", "E:/2.png");  
          
        //附件的绝对路径,不发附件则new一个不带值的List  
        List<String> list=new ArrayList<String>();  
        list.add("E:/1.jpg");  
//        list.add("E:/test.xml");  
          
        //创建实例  
        SendMail sm=new SendMail(map,list,image);  
        //执行发送  
        sm.send();  
    }
}  
