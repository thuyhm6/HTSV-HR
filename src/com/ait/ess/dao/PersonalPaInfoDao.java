package com.ait.ess.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PersonalPaInfoDao {

	@SuppressWarnings("unchecked")
	public List getAddProList(Map paramMap);
	@SuppressWarnings("unchecked")
	public List getMiProList(Map paramMap) ;
	
	/**
	 * 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 下午8:07:16 
	* @version V1.0
	 */
	public List getSalaryProvideDateEss(Map paramMap);
	
	/**
	 * 查询或打印前验证当前月工资是否开放
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-11-6 下午8:26:35 
	* @version V1.0
	 */
	public int getSalaryDispark(Map paramMap);
}
