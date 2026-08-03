package com.ait.report.pa.dao;

import java.util.List;
import java.util.Map;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01Dao.java
 * @Description: interface Class PaReportC04Dao.java
 * @Create date: Sep 21, 2012 5:29:38 PM
 * @Create by: zhanghaiyuan (zhanghaiyuan@ait.net.cn)
 * @version 5.1
 */
public interface PaReportC04Dao {
	
	/**
	 * 导出工报盘报表的数据
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaJobOfferList(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List getEmpType(Object object);

	
	/**
	 * 获取工资发放表（正式工）的数据
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffList(Object object);

	/**
	 * 获取正式员工部门编号
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaDeptEmpIdList(Object object);

	/**
	 * 获取工资发放表（劳务工）的数据
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaLabourPayOffList(Object object);

	/**
	 * 获取所有部门的名称
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaAllDeptNameList(Object object);

	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffSumList(Object object);

	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffZongJiList(Object object);
	
	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaLabourPayOffSumList(Object object);
	
	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaLabourPayOffZongJiList(Object object);
	
	/**
	 * 工资单，added on 2012-10-09
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List retrievePaJasperReportPayrollData(Object object);

	/**
	 * 工资对照表
	 * @param object
	 * @return list
	 */
	public List getPacontrastList(Object object);

	/**
	 * 工资对照表(基本工资对照)
	 * @param object
	 * @return list
	 */
	public List getPacontrastListsum(Object Object);

	/**
	 * 个税
	 * @param object
	 * @return list
	 */
	public List getPersonaltaxList(Object Object);
	

	/**
	 * 工资支付现状（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaStatusByMonthPayOffList(Object object);
	
	
	
	

	/**
	 * 查询销售金额（按月份计算）导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaSalaryAmountList(Object object);

	/**
	 * 查询生产金额（按月份算）导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaProductionAmountList(Object object);
	
	/**
	 * 驻在员工资  导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	public List getPaItemSumByEmpType(Object object);
	
	//------------------累计------------------------
	/**
	 * 查询销售金额（按月份计算）导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaSalaryAmountSumList(Object object);

	/**
	 * 查询生产金额（按月份算）导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaProductionAmountSumList(Object object);
	
	/**
	 * 驻在员工资  导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	public List getPaItemSumByEmpTypeByYear(Object object);
	
	
	
	
	
	/**
	 * 查询销售金额总和（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaSumSalaryAmountList(Object object);
	
	
	
	/**
	 * 查询生产金额总和（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaSumProductionAmountList(Object object);
	
	/**
	 * 查询驻在员总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaSalaryZhuZaiYuanZongHeList(Object object);
	
	/**
	 * 工资支付现状（累计）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayOffStatusAddupPayOffList(Object object);

	/**
	 * 工资支付现状（累计） 变动数据比较，结果大于0返回相减的结果   小于0的返回-1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayOffStatusAddupPayOffResourceListGt(Object object);

	/**
	 * 工资支付现状（累计） 变动数据比较，结果小于0返回相减的结果   大于0的返回1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayOffStatusAddupPayOffResourceListLt(Object object);

	/**
	 * 工基金     导出Excel用
	 * @param request
	 * @return
	 */
	public List getpaWageFundList(Object object);

	/**
	 * 按部门找出员工工资  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaSummarizeList(Map paramMap);

	/**
	 * 正式工生产部工资小计   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getZhengShiShengChanXiaoJiList(Map paramMap);

	/**
	 * 根据父部门code找出子部门
	 * @param request
	 * @return
	 */
	public List getdeptNoListByParentNo(Map paramMap);

	/**
	 * 获取员工工资信息     正式工  管理部   间接       导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getZhengShiGuanLiList(Map paramMap);

	/**
	 * 获取员工工资信息     正式工  管理部      间接小计     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getZhengShiGuanLiXiaoJiList(Map paramMap);

	/**
	 * 获取员工工资信息    劳务工  生产部   直接小计  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getPaSummarizeLaoWuXiaoJiList(Map paramMap);

	/**
	 * 获取员工工资信息    劳务工  管你部   间接  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getLaoWuGuanLiJianJieList(Map paramMap);

	/**
	 * 获取员工工资信息    劳务工  管你部   间接小计   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getLaoWuGuanLiJianJieXiaoJiList(Map paramMap);

	/**
	 * 获取员工工资信息    驻在员   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	public List getZhuZaiYuanList(Map paramMap);

	/**
	 * 获取员工工资信息   全公司中方合计
	 * @param request
	 * @return
	 */
	public List getQuanGongSiZhongFangList(Map paramMap);

	/**
	 * 获取员工工资信息       生产部  
	 * @param request
	 * @return
	 */
	public List getShengChanList(Map paramMap);

	/**
	 * 获取员工工资信息    小计
	 * @param request
	 * @return
	 */
	public List getShengChanXiaoJiList(Map paramMap);

	/**
	 * 获取员工工资信息    生产直接合计
	 * @param request
	 * @return
	 */
	public List getShengChanZhiJieHeJiList(Map paramMap);

	/**
	 * 获取员工工资信息    生产直接合计
	 * @param request
	 * @return
	 */
	public List getShengChanZongHeList(Map paramMap);

	/**
	 * 驻在员  科别考勤表
	 * @param request
	 * @return
	 */
	public List getZhuZaiYuanMonthList(Map paramMap);

	/**
	 * 获取公司总计
	 * @param request
	 * @return
	 */
	public List getGongSiZongJiList(Map paramMap);

	/**
	 * 获取公司总计人数
	 * @param request
	 * @return
	 */
	public List getGongSiZongJiRenShuList(Map paramMap);

	/**
	 * 获取公司     总计的小计
	 * @param request
	 * @return
	 */
	public List getGongSiZongJiXiaoJiList(Map paramMap);

	/**
	 * 获取公司    销售金额
	 * @param request
	 * @return
	 */
	public List getXiaoShouJinEList(Map paramMap);


}
