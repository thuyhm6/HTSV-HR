package com.ait.is.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AccumulationFundDao {

	/**
	 * 公积金--基准管理（CPF BENCHMARK MANAGEMENT）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-22 上午11:49:24 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getCPFBenchmarkManagementForSearch(Object object) throws SQLException;
	
	/**
	 * 公积金--基准管理[版本日期]（CPF BENCHMARK MANAGEMENT[VERSION_DATE]）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-23 上午10:55:10 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getVersionDateListBz(Object object) throws SQLException;
	
	
	@SuppressWarnings("unchecked")
	int deleteBenchmarkStandardBz(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	int updateBenchmarkStandardBz(Object object) throws SQLException;

	/**
	 * 公积金--基准管理[当前]（CPF BENCHMARK MANAGEMENT）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-22 上午11:49:24 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getCPFBenchmarkManagementForSearchDq(Object object) throws SQLException;

	/**
	 * 查询记录条数 
	 *  公积金 需要记录满足5条
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午2:44:20 
	* @version V1.0
	 */
	int ifUpdatedVersion(Object object) throws SQLException;

	/**
	 * 对象最大年月
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午2:58:19 
	* @version V1.0
	 */
	String getMaxManageCreateDate(Object object) throws SQLException;

	/**
	 * 是否已经核算
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午3:24:55 
	* @version V1.0
	 */
	int selectPaBenFalgByFalg(Object object) throws SQLException;

	/**
	 * 生成版本
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午3:32:23 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public int createBenchmarkStandardVersion(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public void freshPaBenManage(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public int createDataToPaBenBaseBz(Object object) throws SQLException;


	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumCheckListBz() throws SQLException;

	@SuppressWarnings("unchecked")
	public int backPaBenBaseNumUpdateBz() throws SQLException;

	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumListBz(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	void deleteCPFBaseManagement(Object object) throws SQLException;
	/**
	 * 通过ids 将选中的信息属性 修改为Y
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-20 下午2:15:50 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	void allowPaBenBaseNumUpdate(Object object) throws SQLException;
	
	/**
	 * 公积金--基数管理 (修改)查找要修改的数据List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-20 下午4:57:44 
	* @version V1.0
	 */
	List getupdateCPFBaseManagement(Object object)throws SQLException;
	
	/**
	 *  公积金--基数管理 (修改保存)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-21 下午2:43:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	void updatePaBenBaseAvgSalary(Object object)throws SQLException;

	/**
	 * 计算工资1
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-25 下午2:05:55 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String callPaBenBaseCal(LinkedHashMap paramMap);
	/**
	 *  计算工资2
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-25 下午2:18:43 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String callPaBenBaseCheckCal(LinkedHashMap param);

	/**
	 * 查找最大月
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-27 下午3:44:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String getMaxYearMonthOfComputionBz(Object object)throws SQLException;

	/**
	 * 判断核算后的申请裁决情况
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 上午10:12:16 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String afterComputationAffirmBz(Object object)throws SQLException;

	/**
	 * 发令人数 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 上午10:19:13 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int paBenBaseNumOrderCount()throws SQLException;

	/**
	 * 实际发令影响人数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午1:07:14 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int paBenBaseNumTrueOrderCount()throws SQLException;

	/**
	 * 如果发令有剩余，则显示剩余人员信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午1:48:58 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getPaBenBaseNumOrderList(Object object)throws SQLException;

	/**
	 * 要发令的集合
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午5:04:33 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List<Map> getEmpidFromBaseAndManage(Object object);
	/**
	 * 发令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午5:10:49 
	* @version V1.0
	 */
	void orderUpdatePaBenManageAvgSalary(Object object);

	/**
	 * 发令成功后，清空base表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午5:11:59 
	* @version V1.0
	 */
	void deletePaBenBase();
	
	/**
	 * 导入临时表时，清空baseImp表（公积金-基数管理）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-11 下午5:11:59 
	* @version V1.0
	 */
	public void deletePaBenBaseImp();

	/**
	 * 查询baseImp表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-5-11 下午5:11:59 
	* @version V1.0
	 */
	public List<Map> getPaBehsBaseImplList();
	
	public void updatePaBenhsBase(Object object);
	
	public void insertPaBenhsBase(Object object);
	
	public List<Map> checkPaBenhsBase(Object object);
	
	/****
	 * 根据公司id和工号查找person_id
	 */
	public List findPIdByParam(Object object);

}
