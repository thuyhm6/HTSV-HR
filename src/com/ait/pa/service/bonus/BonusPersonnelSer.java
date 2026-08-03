package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusPersonnelSer.java
 * @Description:
 * @Create date: 2012-2-10 下午06:08:45
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusPersonnelSer {
	
	@SuppressWarnings("unchecked")
	public List getBonusPersonnelList(HttpServletRequest request) ;
	
	public int getBonusPersonnelCnt(HttpServletRequest request);
	
	public int updateBonusPersonnelInfo(HttpServletRequest request);
	
//	public int deleteBonusPersonnelInfo(HttpServletRequest request);
	
	/**
	 * 获得所有奖金计算人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	public Object getBonusPersonnelInfo(HttpServletRequest request);

	/**
	 * 获得所有奖金计算人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getBonusObjectList(HttpServletRequest request)throws Exception;
	
	/**
	 * 获得所有奖金计算人员总数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	public int getBonusObjectListCnt(HttpServletRequest request)throws Exception;

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:25:38 
	* @version V1.0
	 */
	public int updateBonusCalcFlagByPersonId(HttpServletRequest request)throws Exception;

	public Object getBonusObjectCtrollerInfo(HttpServletRequest request)throws Exception;

	public int updateBonusObjectInfo(HttpServletRequest request)throws Exception;

}
