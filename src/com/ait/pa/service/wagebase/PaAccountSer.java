package com.ait.pa.service.wagebase;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public interface PaAccountSer {
	public List getPaAccountList(HttpServletRequest request,String flag);

	public int getPaAccountCnt(HttpServletRequest request,String flag);

	public int updatePaAccountInfo(HttpServletRequest request)
			throws SQLException;

	public Object getPaAccountCtrollerInfo(HttpServletRequest request);

	public List getBankList(HttpServletRequest request);

	public List getBankBranchList(HttpServletRequest request);

	public int updatePaCalcFlagByPersonId(HttpServletRequest request);

	/**
	 * 查询出工资对象列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-4 上午11:46:30
	 * @version V1.0
	 */
	public List getPaSalaryObjectList(HttpServletRequest request)
			throws Exception;

	/**
	 * 查询出工资对象列表数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-4 上午11:56:03
	 * @version V1.0
	 */
	public int getPaSalaryObjectCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 更新计算标识
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-5 下午01:55:39
	 * @version V1.0
	 */
	public int updatePaSalCalcFlagByPersonId(HttpServletRequest request)
			throws Exception;

	/**
	 * 查询员工状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-6 下午03:58:09
	 * @version V1.0
	 */
	public List getEmpOfficeList(HttpServletRequest request) throws Exception;

	/**
	 * 计算对象(奖金,工资,保险)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-15 下午11:21:52
	 * @version V1.0
	 */
	public String calculateObject(HttpServletRequest request) throws Exception;

	public Object getPaObjectCtrollerInfo(HttpServletRequest request)
			throws Exception;

	public int updatePaObjectInfo(HttpServletRequest request) throws Exception;

	public List getPersonType(HttpServletRequest request);

	public List getPersonAllowance(HttpServletRequest request) throws Exception;

	public int getPersonAllowanceCnt(HttpServletRequest request);

	public List getPositionList(HttpServletRequest request);

	/**
	 * 查找职责津贴个人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 上午10:59:29
	 * @version V1.0
	 */
	public Object getPaAllowanceSelf(HttpServletRequest request);

	/**
	 * 保存职责津贴个人的修改
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 上午11:48:42
	 * @version V1.0
	 */
	public int updatePaAllowanceSelfInfo(HttpServletRequest request);

	/**
	 * 按照Id号查找职责津贴标准
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 下午02:10:28
	 * @version V1.0
	 */
	public Object getPaAllowance(HttpServletRequest request);

	/**
	 * 新增职责津贴标准
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 下午02:11:08
	 * @version V1.0
	 */
	public int insertPaAllowanceInfo(HttpServletRequest request);

	/**
	 * 更新职责津贴标准
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 下午02:11:08
	 * @version V1.0
	 */
	public int updatePaAllowanceInfo(HttpServletRequest request);

	/**
	 * 获取职责津贴列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 下午03:17:16
	 * @version V1.0
	 */
	public List getAllowanceList(HttpServletRequest request);

	/**
	 * 获取职责津贴的总条数
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 下午03:24:38
	 * @version V1.0
	 */
	public int getAllowanceCnt(HttpServletRequest request);

	/**
	 * 删除职责津贴标准
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-6-26 下午05:23:24
	 * @version V1.0
	 */
	public int deletePaAllowanceInfo(HttpServletRequest request);

	public List getEvaluationDataImportResultList(HttpServletRequest request);

	public int getEvaluationDataImportResultListCnt(HttpServletRequest request);

	public int getEvaluationDataImportErrCnt(HttpServletRequest request);

	public String importPaAllowanceExcelTempExcel(HttpServletRequest request);

	public List getEvaluationDataImportResultSelfList(HttpServletRequest request);
	public List getPaAccountDataImportResultList(HttpServletRequest request);

	public int getEvaluationDataImportResultSelfListCnt(HttpServletRequest request);
	
	public int getPaAccountDataImportResultListCnt(HttpServletRequest request);

	public int getEvaluationDataImportErrSelfCnt(HttpServletRequest request);
	public int getPaAccountDataImportResultListErrCnt(HttpServletRequest request);

	public String importPaAllowanceExcelSelfExcel(HttpServletRequest request);

	public String importPaAccountExcelExcel(HttpServletRequest request);
    
	/**
	 * sy工资计算状态页面修改试用支付比例
	 * @param request
	 * @return
	 */
	public int updateC_PROB_PAY_RAT(HttpServletRequest request);
}
