package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceCalculateDao.java
 * @Description:
 * @Create date: 2012-2-17 下午02:56:56
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceCalculateDao {

	@SuppressWarnings("unchecked")
	public String insuranceCalculate(LinkedHashMap object) ;
	/**
	 * 新保险计算
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-13 下午4:50:21 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public String insuranceCalculateNew(LinkedHashMap paramMap);
	
	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 上午10:46:44 
	* @version V1.0
	 */
	public List getSalaryProvideDate(LinkedHashMap object);
}
