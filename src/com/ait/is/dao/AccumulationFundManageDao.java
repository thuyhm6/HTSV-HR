package com.ait.is.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface AccumulationFundManageDao {

	/**
	 * 公积金--对象增加List
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2014-2-13 上午11:40:26
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumList(Object object) throws SQLException;
	
	/**
	 * 公积金--对象减少List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-14 下午2:09:48 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getviewCPFStopInsure(Object object) throws SQLException;
	

	/**
	 * 公积金--对象管理List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午2:09:48 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getviewBenshObjectManage(Object object) throws SQLException;

	/**
	 * 公积金--对象增加(检索) 数据生成+原数据清空
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-3-11 下午3:40:20 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public void createDataToPaBenManageAddBz(Object object) throws SQLException;

	/**
	 * 工资项目最大月
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-3-11 下午3:46:51 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public String getMaxMonthPaParamItem();
	
	/**
	 * 点击修改出现可修改文本
	 * @param str
	 * @throws GlRuntimeException
	 */
	public void allowPaBenJoinInsureUpdate(Object object) throws Exception;
	
	/**
	 * 获取要修改对象的List
	 * @return
	 * @throws GlRuntimeException
	 */
	public List getAllowPaBenJoinInsureUpdate(Object object) throws Exception;
	
	/**
	 * 
	 * TODO 修改ManageAdd信息
	 * @param param
	 */
	public void updatePaBenManageAddInfo(Object param) throws Exception ;
	/**
	 * 通过ids 将选中的信息属性 修改为Y
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @date 2014-2-20 下午2:15:50 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	void allowBenshObjectManageNumUpdate(Object object) throws SQLException;

	/***
	 * 公积金--对象增加点击修改后不修改而返回
	 */
	@SuppressWarnings("unchecked")
	void AddBenshObjectManageNotUpdate(Object object) throws SQLException;
	
	
	
	/**
	 * 点击修改出现可修改文本(对象减少)
	 * @param str
	 * @throws GlRuntimeException
	 */
	public void allowPaBenStopInsureUpdate(Object object) throws Exception;
	
	/**
	 * 获取要修改对象的List（对象减少）
	 * @return
	 * @throws GlRuntimeException
	 */
	public List getAllowPaBenStopInsureUpdate(Object object) throws Exception;
	
	/**
	 * 
	 * TODO 修改（对象减少）信息
	 * @param param
	 */
	public void updatePaBenManageStopInfo(Object param) throws Exception ;
	/**
	 * 通过ids 将选中的信息属性 修改为Y（对象减少）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @date 2014-2-20 下午2:15:50 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	void allowStopBenshObjectManageNumUpdate(Object object) throws SQLException;
	
	/***
	 * 公积金--对象减少点击修改后不修改而返回
	 */
	@SuppressWarnings("unchecked")
	void StopBenshObjectManageNotUpdate(Object object) throws SQLException;
	
	/****
	 * 删除（对象增加）
	 * @param object
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	void deleteBenshObjectManageAdd(Object object) throws SQLException;
	
	
	/****
	 * 删除（对象减少）
	 * @param object
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	void deleteBenshObjectManageDel(Object object) throws SQLException;
	
	/**
	 * 导入临时表时，清空manageaddImp表（公积金-对象增加）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午5:11:59 
	* @version V1.0
	 */
	public void deleteManageAddImp();
	/**
	 * 导入临时表时，清空manageImp表（公积金-对象管理）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午5:11:59 
	* @version V1.0
	 */
	public void deleteManageImp();
	
	
	/**
	 * 查询ManageAddImp表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午5:11:59 
	* @version V1.0
	 */
	public List<Map> getManageAddImplList();
	
	public List<Map> checkManageAdd(Object object);
	
	public void updateManageAdd(Object object);
	
	public void insertManageAdd(Object object);
	
	
	/**
	 * 导入临时表时，清空manageaddImp表（公积金-对象减少）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午5:11:59 
	* @version V1.0
	 */
	public void deleteManageDelImp();
	
	
	/**
	 * 查询ManageDelImp表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午5:11:59 
	* @version V1.0
	 */
	public List<Map> getManageDelImplList();
	
	/**
	 * 查询ManageImp表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-14 下午5:11:59 
	* @version V1.0
	 */
	public List<Map> getManageImplList();
	
	public List<Map> checkManageDel(Object object);
	
	public List<Map> checkManage(Object object);
	
	public void updateManageDel(Object object);
	
	public void updateManage(Object object);
	
	public void insertManageDel(Object object);
	
	public void insertManage(Object object);

	
}
