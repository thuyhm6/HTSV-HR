package com.ait.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ait.ar.service.ArReadCardSer;

/**
 *	读取人员打卡数据
 * 	@author weizhengchen
 *
 */
@Component
public class ReadCardInfoTask {
	
	@Autowired
	private ArReadCardSer arReadCardSer;
	
	/**
	 * 是否验证
	 * 	1：发送
	 *  0：不发送
	 */
    @Value("${mail.send.flag}")
	private String MAIL_SEND_FLAG;

	/**
	 *  读取人员打卡数据
	 */
	//@Scheduled(cron = "0 0 0/1 * * ?")
	public void readMacRecordList() {
		if("1".equals(MAIL_SEND_FLAG)){
			//读取 打卡数据 北京
			arReadCardSer.readMacRecordBJList("HTSV");
			//读取 打卡数据 天津
//			arReadCardSer.readMacRecordBJList("HAE");
			//读取 打卡数据 大连
//			arReadCardSer.readMacRecordDLList("SPC_DL");
//
//			//读取 打卡数据 上海
//			arReadCardSer.readMacRecordHZList("SPC_SH");
//			
//			//读取 打卡数据 杭州
//			arReadCardSer.readMacRecordHZList("SPC_HZ");
//			
//			//读取 打卡数据 南京
//			arReadCardSer.readMacRecordHZList("SPC_NJ");
		}
	}
	
	@Scheduled(cron = "0 59 1,6,12,23 * * ?")
	public void readMealRecordList() {
		if("1".equals(MAIL_SEND_FLAG)){
			//读取 打卡数据 北京
			arReadCardSer.readMacRecordDLList("HTSV");
		}
	}

}
