package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusPersonnelDao.java
 * @Description:
 * @Create date: 2012-2-10 下午06:10:54
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusPersonnelDao {

	@SuppressWarnings("unchecked")
	public List getBonusPersonnelList(Object object);
	
	public int getBonusPersonnelCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getBonusPersonnelList(Object object, int currentPage, int pageSize);
	
	public int updateBonusPersonnelInfo(Object obj);
	
//	public int deleteBonusPersonnelInfo(Object object) ;

//	public int checkAddBonusPersonnelInfo(Object object) ;
	
	public Object getBonusPersonnelInfo(Object object);

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
	public List getBonusObjectList(Object object, int pageNum,
			int numPerPage)throws Exception;

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
	public List getBonusObjectList(Object object)throws Exception;
	
	/**
	 * 获得所有奖金计算人员总数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:27:44 
	* @version V1.0
	 */
	public int getBonusObjectListCnt(Object object)throws Exception;

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:25:38 
	* @version V1.0
	 */
	public int updateBonusCalcFlagByPersonId(Object obj)throws Exception;

	@SuppressWarnings("unchecked")
	public Object getBonusObjectCtrollerInfo(LinkedHashMap paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public int updateBonusObjectInfo(LinkedHashMap paramMap)throws Exception;

}
