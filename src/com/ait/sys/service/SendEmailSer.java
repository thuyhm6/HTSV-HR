package com.ait.sys.service;

import java.util.Map;

/**
 * 发送审批邮件
 *
 */
public interface SendEmailSer {
	
	public void sendAffirmEmail();

	public void sendAffirmEmailHTSV(int affirmLevel);
	
	/**
	 * 发送社会活动邮件
	 */
	public void sendActivityEmail(Map mapParameter);
	public void sendEvsEmail();
	public void sendOtInfoEmailHTSV(int level);
}
