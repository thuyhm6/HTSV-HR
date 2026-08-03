package com.ait.report.pa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ait.report.pa.dao.PaReportC04Dao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaReportC04DaoImpl  extends SqlMapClientSupport  implements PaReportC04Dao {	
	
	
	/**
	 * 导出工报盘报表的数据
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaJobOfferList(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac04.getPaJobOfferList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	
	
	/**
	 * 查询员工类型
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpType(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac04.getEmpType", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 获取工资发放表（正式工）的数据
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaOfficialPayOffList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}



	/**
	 * 获取正式员工部门编号
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaDeptEmpIdList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaDeptEmpIdList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 获取工资发放表（劳务工）的数据
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaLabourPayOffList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaLabourPayOffList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据法人获取部门
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaAllDeptNameList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaAllDeptNameList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}


	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffSumList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaOfficialPayOffSumList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffZongJiList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaOfficialPayOffZongJiList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaLabourPayOffSumList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaLabourPayOffSumList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaLabourPayOffZongJiList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaLabourPayOffZongJiList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 工资单，added on 2012-10-09
	 * @param object
	 * @return list
	 */
	public List retrievePaJasperReportPayrollData(Object parameterObject) {

		List list = null;
		try {
			//list = this.queryForList("report.pac04.retrievePaJasperReportPayrollData",parameterObject);
			  list = this.queryForList("report.pac04.exportDateSql",parameterObject);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			
		}
		return list;
	}

	/**
	 * 工资对照
	 */
	@Override
	public List getPacontrastList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPacontrastList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}



	/**
	 * 工资对照(合计)
	 */
	@Override
	public List getPacontrastListsum(Object Object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPacontrastListsum",Object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}



	/**
	 * 个税
	 */
	@Override
	public List getPersonaltaxList(Object Object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPersonaltaxList",Object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 工资支付现状（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaStatusByMonthPayOffList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac04.getPaStatusByMonthPayOffList",object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 查询销售金额总和（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSumSalaryAmountList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSumSalaryAmountList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询驻在员总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSalaryZhuZaiYuanZongHeList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSalaryZhuZaiYuanZongHeList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	/**
	 * 查询销售金额（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSalaryAmountList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSalaryAmountList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询生产金额（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaProductionAmountList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaProductionAmountList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 按员工类型、薪资项目查询项目合计  导出Excel用
	 * @param request
	 * @return
	 */
	@Override
	public List getPaItemSumByEmpType(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaItemSumByEmpType",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	//------------------累计------------------------
	/**
	 * 查询销售金额（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSalaryAmountSumList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSalaryAmountSumList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询生产金额（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaProductionAmountSumList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaProductionAmountSumList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 按员工类型、薪资项目查询项目合计  导出Excel用
	 * @param request
	 * @return
	 */
	@Override
	public List getPaItemSumByEmpTypeByYear(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaItemSumByEmpTypeByYear",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	/**
	 * 查询生产金额总和（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSumProductionAmountList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSumProductionAmountList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 工资支付现状（累计）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaPayOffStatusAddupPayOffList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaPayOffStatusAddupPayOffList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}



	/**
	 * 工资支付现状（累计） 变动数据比较，结果大于0返回相减的结果   小于0的返回-1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaPayOffStatusAddupPayOffResourceListGt(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaPayOffStatusAddupPayOffResourceListGt",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 工资支付现状（累计） 变动数据比较，结果小于0返回相减的结果   大于0的返回1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaPayOffStatusAddupPayOffResourceListLt(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaPayOffStatusAddupPayOffResourceListLt",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}




	@Override
	public List getpaWageFundList(Object object) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getpaWageFundList",object);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 按部门找出员工工资  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSummarizeList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSummarizeList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}



	/**
	 * 正式工生产部工资小计   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getZhengShiShengChanXiaoJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getZhengShiShengChanXiaoJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}



	/**
	 * 根据父部门code找出子部门
	 * @param request
	 * @return
	 */
	@Override
	public List getdeptNoListByParentNo(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getdeptNoListByParentNo",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}



	/**
	 * 获取员工工资信息     正式工  管理部   间接        导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	public List getZhengShiGuanLiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getZhengShiGuanLiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息     正式工  管理部       间接小计     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	public List getZhengShiGuanLiXiaoJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getZhengShiGuanLiXiaoJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息    劳务工  生产部   直接小计    导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	public List getPaSummarizeLaoWuXiaoJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getPaSummarizeLaoWuXiaoJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息    劳务工  管你部   间接      导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	public List getLaoWuGuanLiJianJieList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getLaoWuGuanLiJianJieList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息    劳务工  管你部   间接小计      导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	public List getLaoWuGuanLiJianJieXiaoJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getLaoWuGuanLiJianJieXiaoJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 获取员工工资信息    驻在员     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */

	@Override
	public List getZhuZaiYuanList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getZhuZaiYuanList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息   全公司中方合计
	 * @param request
	 * @return
	 */
	@Override
	public List getQuanGongSiZhongFangList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getQuanGongSiZhongFangList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息   全公司中方合计
	 * @param request
	 * @return
	 */
	@Override
	public List getShengChanList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getShengChanList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息   小计
	 * @param request
	 * @return
	 */
	@Override
	public List getShengChanXiaoJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getShengChanXiaoJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息    生产直接合计
	 * @param request
	 * @return
	 */
	@Override
	public List getShengChanZhiJieHeJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getShengChanZhiJieHeJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取员工工资信息     生产总和
	 * @param request
	 * @return
	 */
	@Override
	public List getShengChanZongHeList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getShengChanZongHeList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}



	/**
	 * 驻在员  科别考勤表
	 * @param request
	 * @return
	 */
	@Override
	public List getZhuZaiYuanMonthList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getZhuZaiYuanMonthList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 获取公司总总计
	 * @param request
	 * @return
	 */
	@Override
	public List getGongSiZongJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getGongSiZongJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取公司总总计
	 * @param request
	 * @return
	 */
	@Override
	public List getGongSiZongJiRenShuList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getGongSiZongJiRenShuList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取公司总总计
	 * @param request
	 * @return
	 */
	@Override
	public List getGongSiZongJiXiaoJiList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getGongSiZongJiXiaoJiList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取公司销售金额
	 * @param request
	 * @return
	 */
	@Override
	public List getXiaoShouJinEList(Map paramMap) {
		List list = null;
		try {
			list = this.queryForList("report.pac04.getXiaoShouJinEList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

}
