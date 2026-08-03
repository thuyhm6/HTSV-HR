package com.ait.is.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AccumulationFundSer {

	/**
	 * 公积金--基准管理（CPF BENCHMARK MANAGEMENT）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-22 上午11:50:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getCPFBenchmarkManagementForSearch(HttpServletRequest request) throws SQLException ;
	
	/**
	 * 公积金--基准管理[版本日期]（CPF BENCHMARK MANAGEMENT[VERSION_DATE]）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-23 上午10:45:34 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getVersionDateListBz(HttpServletRequest request) throws SQLException;

	/**
	 * 公积金--基准管理[修改保存] （CPF BENCHMARK MANAGEMENT[UPDATE]）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-26 下午2:44:55 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int updateBenchmarkManagement(HttpServletRequest request) throws SQLException;

	/**
	 * 公积金--基准管理（当前）（CPF BENCHMARK MANAGEMENT）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-1-22 上午11:50:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getCPFBenchmarkManagementForSearchDq(HttpServletRequest request) throws SQLException;
	
	/**
	 * 查询记录条数 
	 *  公积金 需要记录满足5条
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午2:41:58 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int ifUpdatedVersionBz(HttpServletRequest request)throws SQLException;

	/**
	 * 对象最大年月
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午2:58:05 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public String getMaxManageCreateDate(HttpServletRequest request) throws SQLException ;

	/**
	 * 是否已经核算
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午3:22:58 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public int selectPaBenFalgByFalg(Map map) throws SQLException ;

	/**
	 * 生成版本
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午3:30:23 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public int createBenchmarkStandardVersion(Map map) throws SQLException ;
	
	/**
	 * 刷新上下线
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午3:39:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public void freshPaBenManage(Map map) throws SQLException ;

	/**
	 * 公积金--基数管理
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-12 上午10:02:02 
	* @version V1.0
	 */
	List ViewCPFBaseManagementForSearch(HttpServletRequest request)  throws SQLException;

	/**
	 * 数据生成+原数据清空
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-18 上午10:24:13 
	* @version V1.0
	 */
	int createDataToPaBenBaseBz(HttpServletRequest request);

	/**
	 * 公积金--基数管理 (删除)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-19 下午2:38:25 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int deleteCPFBaseManagement(HttpServletRequest request);

	/**
	 * 通过ids 将选中的信息属性 修改为Y
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-20 下午2:09:26 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int allowPaBenBaseNumUpdate(HttpServletRequest request);

	/**
	 * 公积金--基数管理 (修改)查找要修改的数据List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-20 下午4:55:09 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getupdateCPFBaseManagement(HttpServletRequest request);
	
	/**
	 * 公积金--基数管理 (修改保存)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-21 下午2:31:11 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int editCPFBaseManagement(HttpServletRequest request);

	/**
	 * 工资计算
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-25 下午1:58:07 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String computeCPFBaseManagement(HttpServletRequest request);

	/**
	 * 查询最大月
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-27 下午3:42:25 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String getMaxYearMonthOfComputionBz(HttpServletRequest request);

	/**
	 * 判断核算后的申请裁决情况
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 上午10:08:29 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	String afterComputationAffirmBz(HttpServletRequest request);

	/**
	 * 发令人数 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 上午10:15:29 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	int paBenBaseNumOrderCountBz();

	//实际发令影响人数
	@SuppressWarnings("unchecked")
	int paBenBaseNumTrueOrderCountBz();

	/**
	 * 如果发令有剩余，则显示剩余人员信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午1:41:09 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	List getPaBenBaseNumOrderListBz(HttpServletRequest request);

	/**
	 * 公积金 --发令--发令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午4:44:53 
	* @version V1.0
	 */
	int copyDataToPaBenManageBz(HttpServletRequest request);

	/**
	 * 删除所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-11  下午4:44:53 
	* @version V1.0
	 */
	void deletePaBenImp();
	/**
	 * 查询所有的临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn    
	* @date 2014-5-11  下午4:44:53 
	* @version V1.0
	 */
	void getPaBehsBaseImplList(HttpServletRequest request);

}
