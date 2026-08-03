package com.ait.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.sys.service.SendEmailSer;

/**
 *  发送审批邮件
 */
@Component
public class SendEmailTask {
	
	@Autowired
	private SendEmailSer sendEmailSer;

	
	/**
	 * 是否验证
	 * 	1：发送
	 *  0：不发送
	 */
    @Value("${mail.send.flag}")
	private String MAIL_SEND_FLAG;
    
	/**
	 *   发送审批邮件SST
	 */
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void SendAffirmEmailTask() {
		if("0".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			//sendEmailSer.sendAffirmEmail();
		}
	}
    
	/**
	 *   发送评价邮件
	 */
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void SendEvsEmailTask() {
		if("0".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			//sendEmailSer.sendEvsEmail();
		}
	}
	
	/**
	 *   <!-- 获取coodinator申请加班 -->
	 */
	//@Scheduled(cron = "0 0 14 * * ?")
	public void SendAffirmEmailHTSV4Task() {
		if("1".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			sendEmailSer.sendAffirmEmailHTSV(4);
		}
	}
	
	/**
	 *   <!-- 获取coodinator申请加班 -->
	 */
	//@Scheduled(cron = "0 0 14-16 * * ?")
	public void SendAffirmEmailHTSV0Task() {
		if("1".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			sendEmailSer.sendAffirmEmailHTSV(0);
		}
	}
	
	/**
	 *   发送审批邮件HTSV(一次决裁人15:30) 
	 */
	//@Scheduled(cron = "0 0 14-16 * * ?")
	public void SendAffirmEmailHTSV1Task() {
		if("1".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			sendEmailSer.sendAffirmEmailHTSV(1);
		}
	}
	
	/**
	 *   发送审批邮件HTSV(二次决裁人15:50) 
	 */
	//@Scheduled(cron = "0 20 14-16 * * ?")
	public void SendAffirmEmailHTSV2Task() {
		if("1".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			sendEmailSer.sendAffirmEmailHTSV(2);
		}
	}
	
	/**
	 *   发送审批邮件HTSV(三次决裁人16:10) 
	 */
	//@Scheduled(cron = "0 10 16 * * ?")
	public void SendAffirmEmailHTSV3Task() {
		if("1".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			sendEmailSer.sendAffirmEmailHTSV(3);
		}
	}
	
	/**1
	 *   发送审批邮件HTSV(三次决裁人16:10) 
	 */
	//@Scheduled(cron = "0 0 15 * * ?")
	public void SendOtInfoEmailHTSV() {
		if("1".equals(MAIL_SEND_FLAG)){
			//SST发送审批邮件
			sendEmailSer.sendOtInfoEmailHTSV(3);
		}
	}
	
	/**
	 *   发送审批邮件HTSV(三次决裁人16:10)(test)
	 */
	public void SendAffirmEmailHTSVTestTask() {
		//SST发送审批邮件
		//sendEmailSer.sendAffirmEmailTSTO(1);
		//sendEmailSer.sendAffirmEmailTSTO(2);
		//sendEmailSer.sendAffirmEmailTSTO(3);
	}
}
