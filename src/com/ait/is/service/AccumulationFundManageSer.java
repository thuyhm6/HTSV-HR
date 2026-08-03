package com.ait.is.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface AccumulationFundManageSer {
	/**
	 * 公积金--对象增加List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-13 上午11:28:36 
	* @version V1.0
	 */
	List getPaBenBaseNumList(HttpServletRequest request) throws SQLException;

	/**
	 * 公积金--对象减少List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-14 下午2:04:46 
	* @version V1.0
	 */
	List getviewCPFStopInsure(HttpServletRequest request) throws SQLException;
	
	/**
	 * 公积金--对象管理List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午2:04:46 
	* @version V1.0
	 */
	List getviewBenshObjectManage(HttpServletRequest request) throws SQLException;



	void updatePaBenObjectMoveFlagBz(Object map,HttpServletRequest request);
	void updatePaBenObjectMoveFlagBz1(Object map,HttpServletRequest request);


	/**
	 * 公积金--对象增加(检索) 数据生成+原数据清空
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-3-11 下午3:35:43 
	* @version V1.0
	 */
	int createDataToPaBenManageAddBz(HttpServletRequest request);
	
	/**
	 * 点击修改出现可修改文本
	 * @param str
	 * @throws GlRuntimeException
	 */
	public int allowPaBenJoinInsureUpdate(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取要修改对象的List
	 * @return
	 * @throws GlRuntimeException
	 */
	public List getAllowPaBenJoinInsureUpdate(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * TODO 修改ManageAdd信息
	 * @param param
	 */
	public int updatePaBenManageAddInfo(HttpServletRequest request) throws Exception ;
	
	
	/**
	 * 通过ids 将选中的信息属性 修改为Y
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:09:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int allowBenshObjectManageNumUpdate(HttpServletRequest request);
	/**
	 * 通过ids 将选中的信息属性 修改为''
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:09:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int AddBenshObjectManageNotUpdate(HttpServletRequest request);
	
	
	
	
	/**
	 * 点击修改出现可修改文本(对象减少)
	 * @param str
	 * @throws GlRuntimeException
	 */
	public int allowPaBenStopInsureUpdate(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取要修改对象的List(对象减少)
	 * @return
	 * @throws GlRuntimeException
	 */
	public List getAllowPaBenStopInsureUpdate(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * TODO 修改ManageAdd信息(对象减少)
	 * @param param
	 */
	public int updatePaBenManageStopInfo(HttpServletRequest request) throws Exception ;
	
	
	/**
	 * 通过ids 将选中的信息属性 修改为Y(对象减少)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:09:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int allowStopBenshObjectManageNumUpdate(HttpServletRequest request);
	/**
	 * 通过ids 将选中的信息属性 修改为''
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:09:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int StopBenshObjectManageNotUpdate(HttpServletRequest request);
	/**
	 * 公积金--对象增加 (删除)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:38:25 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int deleteBenshObjectManageAdd(HttpServletRequest request);
	
	/**
	 * 公积金--对象减少 (删除)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi
	* @date 2014-5-12 下午2:38:25 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int deleteBenshObjectManageDel(HttpServletRequest request);
	
	/**
	 * 删除所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-14  下午4:44:53 
	* @version V1.0
	 */
	void deleteManageAddImp();
	
	
	/**
	 * 查询所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-14  下午4:44:53 
	* @version V1.0
	 */
	void getManageAddImplList(HttpServletRequest request);
	
	
	/**
	 * 删除所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-14  下午4:44:53 
	* @version V1.0
	 */
	void deleteManageDelImp();
	
	
	/**
	 * 查询所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-14  下午4:44:53 
	* @version V1.0
	 */
	void getManageDelImplList(HttpServletRequest request);
	
	
	/**
	 * 删除所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-14  下午4:44:53 
	* @version V1.0
	 */
	void deleteManageImp();
	
	/**
	 * 查询所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-14  下午4:44:53 
	* @version V1.0
	 */
	void getManageImplList(HttpServletRequest request);

}
