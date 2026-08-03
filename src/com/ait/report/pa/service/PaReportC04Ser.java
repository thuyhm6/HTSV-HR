package com.ait.report.pa.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PaReportC04Ser {
	/**
	 * 导出工报盘报表的数据
	 * @param object
	 * @return list
	 */
	
	@SuppressWarnings("unchecked")
	public List getPaJobOfferList(HttpServletRequest request) ;
	

	@SuppressWarnings("unchecked")
	public List getEmpType(HttpServletRequest request) ;

	
	
	
	/**
	 * 获取工资发放表（正式工）的数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffList(HttpServletRequest request);

	/**
	 * 获取正式员工部门编号
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaDeptEmpIdList(HttpServletRequest request);
	/**
	 * 获劳务派遣员工部门编号
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaLabourDeptEmpIdList(HttpServletRequest request);

	/**
	 * 获取工资发放表（劳务工）的数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaLabourPayOffList(HttpServletRequest request);

	/**
	 * 获取所有部门的名称
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaAllDeptNameList(HttpServletRequest request);

	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaOfficialPayOffSumList(HttpServletRequest request);

	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaLabourPayOffSumList(HttpServletRequest request);
	
	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaLabourPayOffZongJiList(HttpServletRequest request);
	
	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaOfficialPayOffZongJiList(HttpServletRequest request);
	
	/**
	 * 工资单导出pdf
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object retrievePaJasperReportPayrollData(LinkedHashMap paramMap);
	/**
	 * 工资对照表
	 * @param request
	 * @return
	 */
	public List getPacontrastList(HttpServletRequest request);

	/**
	 * 工资对照表（基本工资合计）	 
	 * @param request
	 * @return
	 */
	public List getPacontrastListsum(HttpServletRequest request);

	/**
	 * 个税	 
	 * @param request
	 * @return
	 */
	public List getPersonaltaxList(HttpServletRequest request);

	
	
	
	
	
	
	/**
	 * 查询销售金额（按月份计算）导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSalaryAmountMap(HttpServletRequest request);

	/**
	 * 查询生产金额（按月份算）导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaProductionAmountMap(HttpServletRequest request);
	
	/**
	 * 驻在员工资  导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	public Object getPaSalaryZhuZaiYuanMap(HttpServletRequest request);
	
	/**
	 * 中方合计  导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	public Object getPaSalaryTotalChinaMap(HttpServletRequest request);
	
	/**
	 * 公司总计  导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	public Object getPaSalaryTotalCompanyMap(HttpServletRequest request);
	
	/**
	 * 正式员工（按月份计算）导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNormalEmpSalaryList(HttpServletRequest request);
	
	/**
	 * 派遣员工（按月份计算）导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianEmpSalaryList(HttpServletRequest request);
	
	/**
	 * 查询销售金额（按月份计算）导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSalaryAmountSumMap(HttpServletRequest request);

	/**
	 * 查询生产金额（按月份算）导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaProductionAmountSumMap(HttpServletRequest request);
	
	/**
	 * 驻在员工资  导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	public Object getPaSalaryZhuZaiYuanSumMap(HttpServletRequest request);
	
	/**
	 * 中方合计  导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	public Object getPaSalaryTotalChinaSumMap(HttpServletRequest request);
	
	/**
	 * 公司总计  导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	public Object getPaSalaryTotalCompanySumMap(HttpServletRequest request);
	
	/**
	 * 正式员工（按月份计算）导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNormalEmpSalarySumList(HttpServletRequest request);
	
	/**
	 * 派遣员工（按月份计算）导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianEmpSalarySumList(HttpServletRequest request);
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询销售金额总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSumSalaryAmountList(HttpServletRequest request);
	
	/**
	 * 查询生产金额总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSumProductionAmountList(HttpServletRequest request);
	
	/**
	 * 查询驻在员总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSalaryZhuZaiYuanZongHeList(HttpServletRequest request);
	
	/**
	 * 工资支付现状（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaStatusByMonthPayOffList(HttpServletRequest request);

	
	/**
	 * 工资支付现状（累计），导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayOffStatusAddupPayOffList(HttpServletRequest request);

	
	/**
	 * 工资支付现状（累计） 变动数据比较，结果大于0返回相减的结果   小于0的返回-1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayOffStatusAddupPayOffResourceListGt(
			HttpServletRequest request);

	
	/**
	 * 工资支付现状（累计） 变动数据比较，降低部分的条数     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayOffStatusAddupPayOffResourceListLtCount(
			HttpServletRequest request);
	
	/**
	 * 工资支付现状（累计） 变动数据比较，提高部分的条数     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayOffStatusAddupPayOffResourceListGtCount(
			HttpServletRequest request);
	
	/**
	 * 工资支付现状（累计） 变动数据比较，结果小于0返回相减的结果   大于0的返回1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayOffStatusAddupPayOffResourceListLt(
			HttpServletRequest request);

	/**
	 * 公积金  导出Excel用
	 * @param request
	 * @return
	 */

	public Object getpaWageFundList(HttpServletRequest request);

	

	/**
	 * 按部门找出员工工资  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getPaSummarizeList(HttpServletRequest request);

	/**
	 * 正式工生产部工资小计  直接   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getZhengShiShengChanXiaoJiList(HttpServletRequest request);

	/**
	 * 正式工生产部工资小计   其他   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getPaSummarizeQiTaList(HttpServletRequest request);

	/**
	 * 获取员工工资信息     正式工  管理部   间接        导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getZhengShiGuanLiList(HttpServletRequest request);

	/**
	 * 获取员工工资信息     正式工  管理部      间接小计     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getZhengShiGuanLiXiaoJiList(HttpServletRequest request);

	/**
	 * 劳务工生产部直接 工资小计     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getPaSummarizeLaoWuList(HttpServletRequest request);

	/**
	 * 获取员工工资信息    劳务工  生产部   直接小计    导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getPaSummarizeLaoWuXiaoJiList(HttpServletRequest request);

	/**
	 * 获取员工工资信息    劳务工  生产部   间接    导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getLaoWuShengChanJianJieList(HttpServletRequest request);

	/**
	 * 获取员工工资信息    劳务工  管你部   间接   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getLaoWuGuanLiJianJieList(HttpServletRequest request);

	/**
	 * 获取员工工资信息    劳务工  管你部   间接小计   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getLaoWuGuanLiJianJieXiaoJiList(HttpServletRequest request);

	/**
	 * 获取员工工资信息    驻在员   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public Object getZhuZaiYuanList(HttpServletRequest request);

	/**
	 * 获取员工工资信息   全公司中方合计
	 * @param request
	 * @return
	 */
	public Object getQuanGongSiZhongFangList(HttpServletRequest request);

	/**
	 * 获取员工工资信息        
	 * @param request
	 * @return
	 */
	public Object getShengChanList(HttpServletRequest request);

	/**
	 * 获取员工工资信息     小计
	 * @param request
	 * @return
	 */
	public Object getShengChanXiaoJiList(HttpServletRequest request);

	/**
	 * 获取员工工资信息   部门  小计
	 * @param request
	 * @return
	 */
	public Object getShengChanBuMenXiaoJiList(HttpServletRequest request);

	/**
	 * 获取员工工资信息    部门合计里面的小计
	 * @param request
	 * @return
	 */
	public Object getShengChanBuMenHeJiXiaoJiList(HttpServletRequest request);

	/**
	 *  获取员工工资信息    生产直接合计
	 * @param request
	 * @return
	 */
	public Object getShengChanZhiJieHeJiList(HttpServletRequest request);

	/**
	 *  获取员工工资信息    生产直接合计里面的小计
	 * @param request
	 * @return
	 */
	public Object getShengChanZhiJieHeJiXiaoJiList(HttpServletRequest request);

	/**
	 *  获取员工工资信息    生产总和
	 * @param request
	 * @return
	 */
	public Object getShengChanZongHeList(HttpServletRequest request);

	/**
	 *  获取员工工资信息    生产总和小计
	 * @param request
	 * @return
	 */
	public Object getShengChanZongHeXiaoJiList(HttpServletRequest request);

	/**
	 *  获取总务人事小计
	 * @param request
	 * @return
	 */
	public Object getZongWuRenShiXiaoJiList(HttpServletRequest request);

	/**
	 *  获取总务人事小计 总小计
	 * @param request
	 * @return
	 */
	public Object getZongWuRenShiZongXiaoJiList(HttpServletRequest request);

	/**
	 *  管理部合计       正式 劳务 的
	 * @param request
	 * @return
	 */
	public Object getGuanLiBuHeJiList(HttpServletRequest request);

	/**
	 *  管理部合计   总小计
	 * @param request
	 * @return
	 */
	public Object getGuanLiBuHeJiXiaoJiList(HttpServletRequest request);

	/**
	 *  获取驻在员
	 * @param request
	 * @return
	 */
	public Object getZhuZaiYuanMonthList(HttpServletRequest request);


	/**
	 * 获取公司总总计  正式 劳务 分别 合计
	 * @param request
	 * @return
	 */
	public Object getGongSiZongJiList(HttpServletRequest request);

	/**
	 * 获取公司总总计人数
	 * @param request
	 * @return
	 */
	public Object getGongSiZongJiRSList(HttpServletRequest request);

	/**
	 * 获取公司     总计的小计
	 * @param request
	 * @return
	 */
	public Object getGongSiZongJiXiaoJiList(HttpServletRequest request);

	/**
	 * 获取公司     销售金额
	 * @param request
	 * @return
	 */
	public Object getXiaoShouJinEList(HttpServletRequest request);


}
